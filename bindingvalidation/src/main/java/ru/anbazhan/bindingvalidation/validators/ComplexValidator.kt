package ru.anbazhan.bindingvalidation.validators

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ru.anbazhan.bindingvalidation.message.EditMessage

open class ComplexValidator<T>(
        val data: LiveData<T>,
        private val validatorList: List<EditValidator<T>>,
        var preconditionState: Boolean = true
) {

    val errorValue: LiveData<EditMessage> = Transformations.map(data) {
        if (!preconditionState) {
            EditMessage()
        } else {
            it?.let {
                var resultMessage: EditMessage? = null
                for (validator in validatorList) {
                    if (!validator.validate(it) && resultMessage == null) {
                        resultMessage = EditMessage(validator.text, validator.type)
                    }
                }
                resultMessage ?: EditMessage()
            } ?: EditMessage()
        }
    }
}
