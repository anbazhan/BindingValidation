package ru.anbazhan.bindingvalidation

import ru.anbazhan.bindingvalidation.message.EditMessageType
import ru.anbazhan.bindingvalidation.validators.EditValidator

class EmptyValidator(
    override val text: String = "",
    override val type: EditMessageType = EditMessageType.HELPER
) : EditValidator<String> {

    override fun validate(value: String): Boolean =
        value.isNotEmpty()
}