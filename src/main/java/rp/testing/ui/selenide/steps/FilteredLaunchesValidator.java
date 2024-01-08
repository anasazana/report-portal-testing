package rp.testing.ui.selenide.steps;

import com.codeborne.selenide.SelenideElement;
import lombok.experimental.UtilityClass;
import org.assertj.core.api.SoftAssertions;
import rp.testing.ui.constants.FilterParameterCondition;

import java.util.List;

import static rp.testing.ui.steps.FilteredLaunchesValidator.checkIfCorrectNumber;

@UtilityClass
public class FilteredLaunchesValidator {

    public static void validateLaunchNumbers(FilterParameterCondition condition,
                                             List<SelenideElement> filteredLaunches,
                                             int number) {
        SoftAssertions softly = new SoftAssertions();
        for (SelenideElement filteredLaunch : filteredLaunches) {
            String launchNumber = filteredLaunch
                    .findElement(rp.testing.ui.steps.FilteredLaunchesValidator.LAUNCH_NUMBER_LABEL)
                    .getText().replace("#", "");
            int filteredLaunchNumber = Integer.parseInt(launchNumber);
            boolean isCorrectNumber = checkIfCorrectNumber(condition, filteredLaunchNumber, number);
            softly.assertThat(isCorrectNumber)
                    .as(String.format("Expected launch number: %s %d. Launch number found: %s",
                            condition.getDropdownName(),
                            number,
                            launchNumber)
                    ).isTrue();
        }
        softly.assertAll();
    }

}
