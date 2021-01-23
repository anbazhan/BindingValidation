package ru.anbazhan.bindingvalidation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import java.util.TreeMap
import kotlin.math.max

open class ValidationController {

    private val validationErrors: MediatorLiveData<Set<String>> = MediatorLiveData()
    protected val changeCount: MediatorLiveData<Int> = MediatorLiveData()

    protected val originalFields: MutableMap<String, Pair<LiveData<*>, String>> = mutableMapOf()

    private val isDataChanged: LiveData<Boolean> = Transformations.map(changeCount) {
        isRequireAmountOfDataChanged()
    }

    val isDataCorrect: LiveData<Boolean> = Transformations.map(validationErrors) {
        it.isEmpty()
    }

    val isDataValid = CombineListLiveData(
        listOf(isDataChanged, isDataCorrect)
    ) {
        it.all { element -> element == true }
    }

    private val validatorMap: MutableMap<String, LiveData<EditBottomMessage>> = TreeMap()
    private val hardValidatorMap: MutableMap<String, LiveData<EditBottomMessage>> = TreeMap()

    protected open fun isRequireAmountOfDataChanged(): Boolean {
        return originalFields.size != changeCount.value ?: 0
    }

    fun <T> addValidator(
        name: String,
        validator: LiveData<EditBottomMessage>,
        field: LiveData<T>? = null,
        isHardValidator: Boolean = false
    ) {
        if (isHardValidator) {
            hardValidatorMap[name] = validator
        } else {
            validatorMap[name] = validator
        }
        field?.let { addChangedData(name, it) }
        validationErrors.addError(name, validator)
    }

    protected open fun <T> addChangedData(name: String, field: LiveData<T>) {
        if (!originalFields.contains(name)) {
            changeCount.addSource(field) {
                if (!originalFields.containsKey(name)) {
                    originalFields[name] = Pair(field, field.value?.toString().orEmpty())
                    changeCount.value = (changeCount.value ?: 0) + 1
                } else if (originalFields[name]?.second != it.toString()) {
                    changeCount.value = (changeCount.value ?: 0) + 1
                }
            }
        }
    }

    fun <T> addField(
        editFieldViewModel: EditFieldViewModel<T>,
        isHardValidator: Boolean = false
    ) {
        if (isHardValidator) {
            hardValidatorMap[editFieldViewModel.name] = editFieldViewModel.error
        } else {
            validatorMap[editFieldViewModel.name] = editFieldViewModel.error
        }
        addChangedData(editFieldViewModel.name, editFieldViewModel.field)
        validationErrors.addError(editFieldViewModel.name, editFieldViewModel.error)
    }

    fun hardClear() {
        softClear()
        for (entry in hardValidatorMap) {
            removeField(entry.key, entry.value)
        }
        changeCount.value = 0
    }

    fun softClear() {
        for (entry in validatorMap) {
            removeField(entry.key, entry.value)
        }
    }

    private fun removeField(key: String, value: LiveData<EditBottomMessage>) {
        validationErrors.removeError(key, value)
        originalFields[key]?.first?.let { originalField ->
            changeCount.removeSource(originalField)
            changeCount.value = changeCount.value?.let { max(0, it - 1) } ?: 0
            originalFields.remove(key)
        }
    }
}

fun MediatorLiveData<Set<String>>.addError(
    errorName: String,
    error: LiveData<EditBottomMessage>
) {
    removeError(errorName, error)
    addSource(error) {
        this.value =
            (this.value ?: setOf()).toMutableSet().apply {
                if (it.type == EditBottomMessageType.NONE) remove(errorName)
                else add(errorName)
            }
    }
}

fun MediatorLiveData<Set<String>>.removeError(
    errorName: String,
    error: LiveData<EditBottomMessage>
) {
    removeSource(error)
    this.value =
        (this.value ?: setOf()).toMutableSet().apply {
            remove(errorName)
        }
}
