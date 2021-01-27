package ru.anbazhan.bindingvalidation.validators

import ru.anbazhan.bindingvalidation.message.EditMessageType

interface EditValidator<T> {

    val text: String
    val type: EditMessageType

    fun validate(value: T): Boolean
}
