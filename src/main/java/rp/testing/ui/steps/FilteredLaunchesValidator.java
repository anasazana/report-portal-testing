package rp.testing.ui.steps;

import lombok.experimental.UtilityClass;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import rp.testing.ui.constants.FilterParameterCondition;

import java.util.List;

@UtilityClass
public class FilteredLaunchesValidator {

    private static final By LAUNCH_NUMBER_LABEL = By.xpath("//span[@class='itemInfo__number--uvCUK']");

    public static void validateLaunchNumbers(FilterParameterCondition condition,
                                             List<WebElement> filteredLaunches,
                                             int number) {
        SoftAssertions softly = new SoftAssertions();
        for (WebElement filteredLaunch : filteredLaunches) {
            String launchNumber = filteredLaunch
                    .findElement(LAUNCH_NUMBER_LABEL)
                    .getText().replace("#", "");
            boolean isCorrectNumber = false;
            int filteredLaunchNumber = Integer.parseInt(launchNumber);
            switch (condition) {
                case EQUALS:
                    isCorrectNumber = filteredLaunchNumber == number;
                    break;
                case LESS_THAN_OR_EQUAL:
                    isCorrectNumber = filteredLaunchNumber <= number;
                    break;
                case GREATER_THAN_OR_EQUAL:
                    isCorrectNumber = filteredLaunchNumber >= number;
                    break;
                default:
                    break;
            }
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
