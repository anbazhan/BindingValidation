package ru.anbazhan.bindingvalidation

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

open class ComplexValidator<T>(
    val data: LiveData<T>,
    private val validatorList: List<EditValidator<T>>,
    var preconditionState: Boolean = true
) {

    val errorValue: LiveData<EditBottomMessage> = Transformations.map(data) {
        if (!preconditionState) {
            EditBottomMessage()
        } else {
            it?.let {
                var resultMessage: EditBottomMessage? = null
                for (validator in validatorList) {
                    if (!validator.validate(it) && resultMessage == null) {
                        resultMessage = EditBottomMessage(validator.text, validator.type)
                    }
                }
                resultMessage ?: EditBottomMessage()
            } ?: EditBottomMessage()
        }
    }
}
