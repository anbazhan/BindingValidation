package ru.anbazhan.bindingvalidation

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.anbazhan.bindingvalidation.fields.StringFieldViewModel
import ru.anbazhan.bindingvalidation.message.EditMessageType

class SimpleViewModel : ViewModel() {

    val field1 = StringFieldViewModel(
        name = "field1",
        validatorList = mutableListOf(
            EmptyValidator("Mandatory")
        )
    )

    val field2 = StringFieldViewModel(
        name = "field2",
        enabled = Transformations.map(field1.field) {
            it.isNotEmpty()
        },
        validatorList = mutableListOf(
            EmptyValidator("Optional", EditMessageType.NONE),
            RegexValidator("Only digits", regex = Regex("[0-9]+"))
        )
    )

    val field3 = StringFieldViewModel(
        name = "field3",
        validatorList = mutableListOf(
            EmptyValidator(),
            RegexValidator("Only letters", regex = Regex("[a-zA-Z]+"))
        )
    )

    val field4 = StringFieldViewModel(
        name = "field4",
        validatorList = mutableListOf(
            EmptyValidator("Mandatory")
        )
    )

    val validationController: ValidationController = ValidationController()

    fun initData() {
        validationController.addField(field1)
        validationController.addField(field2)
        validationController.addField(field3)
        validationController.addField(field4)
    }
}
