package ru.anbazhan.bindingvalidation.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import ru.anbazhan.bindingvalidation.message.EditMessageType
import ru.anbazhan.bindingvalidation.message.EditMessage

object LiveDataExtensions {

    fun <T> MutableLiveData<T>.notify() {
        this.value = this.value
    }

    fun MediatorLiveData<Set<String>>.addError(
            errorName: String,
            error: LiveData<EditMessage>
    ) {
        removeError(errorName, error)
        addSource(error) {
            this.value =
                    (this.value ?: setOf()).toMutableSet().apply {
                        if (it.type == EditMessageType.NONE) remove(errorName)
                        else add(errorName)
                    }
        }
    }

    fun MediatorLiveData<Set<String>>.removeError(
            errorName: String,
            error: LiveData<EditMessage>
    ) {
        removeSource(error)
        this.value =
                (this.value ?: setOf()).toMutableSet().apply {
                    remove(errorName)
                }
    }
}