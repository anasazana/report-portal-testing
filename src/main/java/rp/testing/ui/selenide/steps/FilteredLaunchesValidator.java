package rp.testing.ui.selenide.steps;

import com.codeborne.selenide.SelenideElement;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.By;
import rp.testing.ui.constants.FilterParameterCondition;

import java.util.List;

public class FilteredLaunchesValidator {

    private static final By LAUNCH_NUMBER_LABEL = By.xpath("//span[@class='itemInfo__number--uvCUK']");

    public static void validateLaunchNumbers(FilterParameterCondition condition,
                                             List<SelenideElement> filteredLaunches,
                                             int number) {
        SoftAssertions softly = new SoftAssertions();
        for (SelenideElement filteredLaunch : filteredLaunches) {
            String launchNumber = filteredLaunch
                    .findElement(LAUNCH_NUMBER_LABEL)
                    .getText().replaceAll("#", "");
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
