package rp.testing.ui.constants;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum FilterParameterCondition {

    CONTAINS("cnt", "Contains"),
    NOT_CONTAINS("!cnt", "Not contains"),
    EQUALS("eq", "Equals"),
    NOT_EQUALS("!eq", "Not equals"),
    ALL("all", "All"),
    WITHOUT_ALL("!all", "Without all"),
    ANY("any", "Any"),
    WITHOUT_ANY("!any", "Without any"),
    LESS_THAN_OR_EQUAL("≤", "Less than or equal"),
    GREATER_THAN_OR_EQUAL("≥", "Greater than or equal");

    private final String selectedName;
    private final String dropdownName;

    FilterParameterCondition(String selectedName, String dropdownName) {
        this.selectedName = selectedName;
        this.dropdownName = dropdownName;
    }

    public static FilterParameterCondition getByName(String conditionName) {
        return Arrays.stream(FilterParameterCondition.values())
                .filter(condition -> condition.getDropdownName().equalsIgnoreCase(conditionName))
                .findFirst().orElse(null);
    }

}
