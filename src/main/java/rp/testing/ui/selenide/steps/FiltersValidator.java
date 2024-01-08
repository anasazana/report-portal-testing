package rp.testing.ui.selenide.steps;

import com.codeborne.selenide.SelenideElement;
import lombok.experimental.UtilityClass;
import org.openqa.selenium.By;
import rp.testing.ui.selenide.pageobjects.ReportPortalLaunchesPage;
import rp.testing.utils.WaiterUtils;

import java.util.List;

@UtilityClass
public class FiltersValidator {

    public static boolean isFilterDisplayed(ReportPortalLaunchesPage launchesPage, String filterName) {
        List<SelenideElement> displayedFilters = launchesPage.launch().getDisplayedFilters();
        WaiterUtils.pause();
        for (SelenideElement filter : displayedFilters) {
            if (filter.find(By.className("filterItem__name--jHeQX")).getText().equals(filterName)) {
                return true;
            }
        }
        return false;
    }
}
