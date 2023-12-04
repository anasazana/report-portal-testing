package rp.testing.api.model.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rp.testing.api.model.filter.enums.Type;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UpdateUserFilterRQ {

    private List<UserFilterCondition> conditions;
    private String description;
    private String name;
    private List<Order> orders;
    private Type type;
    private String owner;
    private String id;

}
