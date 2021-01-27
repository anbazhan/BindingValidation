package ru.anbazhan.bindingvalidation.fields

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.anbazhan.bindingvalidation.EditFieldViewModel
import ru.anbazhan.bindingvalidation.extensions.LiveDataExtensions.notify
import ru.anbazhan.bindingvalidation.validators.EditValidator

/**
 * ViewModel для редактирования строкового поля
 */
class StringFieldViewModel(
    name: String,
    field: MutableLiveData<String> = MutableLiveData(""),
    enabled: LiveData<Boolean> = MutableLiveData(true),
    validatorList: MutableList<EditValidator<String>> = mutableListOf()
) : EditFieldViewModel<String>(name, field, enabled, validatorList) {

    /**
     * Оповестить поле об изменении
     */
    fun notifyLiveData() {
        field.notify()
    }
}
