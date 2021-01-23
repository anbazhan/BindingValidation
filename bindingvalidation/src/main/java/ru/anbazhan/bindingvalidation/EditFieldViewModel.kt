package ru.anbazhan.bindingvalidation

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

open class EditFieldViewModel<T>(
    val name: String,
    val field: MutableLiveData<T>,
    validatorList: MutableList<EditValidator<T>>
) {
    val validator: ComplexValidator<T> =
        ComplexValidator(
            field,
            validatorList
        )

    val error: LiveData<EditBottomMessage>
        get() = validator.errorValue

    val onFocusChangeListener = View.OnFocusChangeListener { _, _ -> field.notify() }

    open fun getValue() = field.value
}

fun <T> MutableLiveData<T>.notify() {
    this.value = this.value
}
