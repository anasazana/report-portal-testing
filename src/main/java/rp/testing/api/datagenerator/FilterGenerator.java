package rp.testing.api.datagenerator;

import rp.testing.api.model.filter.Order;
import rp.testing.api.model.filter.UpdateUserFilterRQ;
import rp.testing.api.model.filter.UserFilterCondition;
import rp.testing.api.model.filter.enums.Type;

import java.util.List;
import java.util.Random;

import static rp.testing.api.model.filter.enums.FilteringField.DESCRIPTION;
import static rp.testing.api.model.filter.enums.FilteringField.LAUNCH_NAME;
import static rp.testing.ui.constants.FilterParameterCondition.CONTAINS;

public class FilterGenerator {

    private static final Random RANDOM = new Random();

    public static UpdateUserFilterRQ generateTestFilter() {
        String filterName = "TestFilter-" + Math.abs(RANDOM.nextInt());
        List<UserFilterCondition> defaultConditions = List.of(
                new UserFilterCondition(LAUNCH_NAME, CONTAINS.getSelectedName(), "Demo"),
                new UserFilterCondition(DESCRIPTION, CONTAINS.getSelectedName(), "launch")
        );
        List<Order> defaultOrders = List.of(
                new Order("number", false),
                new Order("startTime", false)
        );
        return UpdateUserFilterRQ.builder()
                .name(filterName)
                .owner("default")
                .description("New test filter")
                .conditions(defaultConditions)
                .orders(defaultOrders)
                .type(Type.LAUNCH).build();
    }
}
