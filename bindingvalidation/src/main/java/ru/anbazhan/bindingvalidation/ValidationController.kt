package ru.anbazhan.bindingvalidation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import ru.anbazhan.bindingvalidation.extensions.LiveDataExtensions.addError
import ru.anbazhan.bindingvalidation.extensions.LiveDataExtensions.removeError
import ru.anbazhan.bindingvalidation.message.EditMessage
import java.util.TreeMap

open class ValidationController {

    private val validationErrors: MediatorLiveData<Set<String>> = MediatorLiveData()

    val isValid: LiveData<Boolean> = Transformations.map(validationErrors) {
        it.isEmpty()
    }

    private val validatorMap: MutableMap<String, LiveData<EditMessage>> = TreeMap()
    private val hardValidatorMap: MutableMap<String, LiveData<EditMessage>> = TreeMap()

    fun <T> addValidator(
            name: String,
            validator: LiveData<EditMessage>,
            isHardValidator: Boolean = false
    ) {
        if (isHardValidator) {
            hardValidatorMap[name] = validator
        } else {
            validatorMap[name] = validator
        }
        validationErrors.addError(name, validator)
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
        validationErrors.addError(editFieldViewModel.name, editFieldViewModel.error)
    }

    fun hardClear() {
        softClear()
        for (entry in hardValidatorMap) {
            removeField(entry.key, entry.value)
        }
    }

    fun softClear() {
        for (entry in validatorMap) {
            removeField(entry.key, entry.value)
        }
    }

    private fun removeField(key: String, value: LiveData<EditMessage>) {
        validationErrors.removeError(key, value)
    }
}
