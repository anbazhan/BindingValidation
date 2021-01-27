package ru.anbazhan.bindingvalidation

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.anbazhan.bindingvalidation.extensions.LiveDataExtensions.notify
import ru.anbazhan.bindingvalidation.message.EditMessage
import ru.anbazhan.bindingvalidation.validators.ComplexValidator
import ru.anbazhan.bindingvalidation.validators.EditValidator

open class EditFieldViewModel<T>(
    val name: String,
    val field: MutableLiveData<T>,
    val enabled: LiveData<Boolean>,
    validatorList: MutableList<EditValidator<T>>
) {
    val validator: ComplexValidator<T> =
        ComplexValidator(
            field,
            validatorList
        )

    val error: LiveData<EditMessage>
        get() = validator.errorValue

    val onFocusChangeListener = View.OnFocusChangeListener { _, _ -> field.notify() }

    open fun getValue() = field.value
}

