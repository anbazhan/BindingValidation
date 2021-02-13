package ru.anbazhan.bindingvalidation

import ru.anbazhan.bindingvalidation.message.EditMessageType
import ru.anbazhan.bindingvalidation.validators.EditValidator

class LengthValidator(
    override val text: String,
    override val type: EditMessageType = EditMessageType.ERROR,
    val length: Int,
    val less: Boolean
) : EditValidator<String> {

    override fun validate(value: String): Boolean =
        less && value.length <= length ||
        !less && value.length >= length
}