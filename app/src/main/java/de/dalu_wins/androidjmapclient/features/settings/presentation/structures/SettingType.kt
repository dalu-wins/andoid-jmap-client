package de.dalu_wins.androidjmapclient.features.settings.presentation.structures

data class RadioOption<T>(
    val value: T,
    val label: String
)

sealed interface SettingType {
    val id: String

    interface RadioGroupItem : SettingType {
        val title: String?
        val options: List<RadioOption<*>>
        val selectedValue: Any?
        fun onAnyOptionSelected(value: Any?)
    }

    data class RadioGroup<T>(
        override val id: String,
        override val title: String? = null,
        override val options: List<RadioOption<T>>,
        override val selectedValue: T,
        val onOptionSelected: (T) -> Unit
    ) : RadioGroupItem {
        @Suppress("UNCHECKED_CAST")
        override fun onAnyOptionSelected(value: Any?) {
            onOptionSelected(value as T)
        }
    }

    data class Toggle(
        override val id: String,
        val title: String,
        val subtitle: String? = null,
        val isChecked: Boolean,
        val onToggle: (Boolean) -> Unit
    ) : SettingType

    data class Action(
        override val id: String,
        val title: String,
        val subtitle: String? = null,
        val onClick: () -> Unit
    ) : SettingType
}
