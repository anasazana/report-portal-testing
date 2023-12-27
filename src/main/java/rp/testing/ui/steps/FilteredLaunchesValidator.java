package rp.testing.ui.steps;

import lombok.experimental.UtilityClass;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import rp.testing.ui.constants.FilterParameterCondition;

import java.util.List;

@UtilityClass
public class FilteredLaunchesValidator {

    public static final By LAUNCH_NUMBER_LABEL = By.xpath("//span[@class='itemInfo__number--uvCUK']");

    public static void validateLaunchNumbers(FilterParameterCondition condition,
                                             List<WebElement> filteredLaunches,
                                             int number) {
        SoftAssertions softly = new SoftAssertions();
        for (WebElement filteredLaunch : filteredLaunches) {
            String launchNumber = filteredLaunch
                    .findElement(LAUNCH_NUMBER_LABEL)
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

    public static boolean checkIfCorrectNumber(FilterParameterCondition condition, int filteredLaunchNumber,
                                                  int number) {
        switch (condition) {
            case EQUALS:
                return filteredLaunchNumber == number;
            case LESS_THAN_OR_EQUAL:
                return filteredLaunchNumber <= number;
            case GREATER_THAN_OR_EQUAL:
                return filteredLaunchNumber >= number;
            default:
                return false;
        }
    }

}
