package rp.testing.ui.steps;

import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rp.testing.ui.constants.FilterParameterCondition;

import java.util.List;

public class FilteredLaunchesValidator {

    private static final Logger logger = LoggerFactory.getLogger(FilteredLaunchesValidator.class);
    private static final By LAUNCH_NAME_LABEL = By.xpath("//div[@class='tooltip__tooltip-trigger--FBBdw itemInfo__name--Nz97v']/span");
    private static final By LAUNCH_NUMBER_LABEL = By.xpath("//span[@class='itemInfo__number--uvCUK']");



    public static void validateLaunchNameContains(List<WebElement> filteredLaunches, String nameContent) {
        if (filteredLaunches != null) {
            SoftAssertions softly = new SoftAssertions();
            for (WebElement filteredLaunch : filteredLaunches) {
                String launchName = filteredLaunch
                        .findElement(LAUNCH_NAME_LABEL)
                        .getText();
                softly.assertThat(launchName.contains(nameContent))
                        .as(String.format("Each launch name must contain: %s", nameContent))
                        .isTrue();
            }
            softly.assertAll();
        }
    }

    public static void validateLaunchNumbers(FilterParameterCondition condition,
                                             List<WebElement> filteredLaunches,
                                             String number) {
        SoftAssertions softly = new SoftAssertions();
        for (WebElement filteredLaunch : filteredLaunches) {
            String launchNumber = filteredLaunch
                    .findElement(LAUNCH_NUMBER_LABEL)
                    .getText().replaceAll("#", "");
            boolean isCorrectNumber = false;
            int expectedNumber = Integer.parseInt(number);
            int filteredLaunchNumber = Integer.parseInt(launchNumber);
            switch (condition) {
                case EQUALS:
                    isCorrectNumber = launchNumber.equals(number);
                    break;
                case LESS_THAN_OR_EQUAL:
                    isCorrectNumber = filteredLaunchNumber <= expectedNumber;
                    break;
                case GREATER_THAN_OR_EQUAL:
                    isCorrectNumber = filteredLaunchNumber >= expectedNumber;
                    break;
            }
            softly.assertThat(isCorrectNumber)
                    .as(String.format("Expected launch number: %s %s. Launch number found: %s",
                            condition.getDropdownName(),
                            number,
                            launchNumber)
                    ).isTrue();
        }
        softly.assertAll();
    }

}
