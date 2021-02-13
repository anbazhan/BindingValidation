package ru.anbazhan.bindingvalidation

import ru.anbazhan.bindingvalidation.message.EditMessageType
import ru.anbazhan.bindingvalidation.validators.EditValidator

class RegexValidator(
    override val text: String,
    override val type: EditMessageType = EditMessageType.ERROR,
    val regex: Regex
) : EditValidator<String> {

    override fun validate(value: String): Boolean =
        regex.matches(value)
}