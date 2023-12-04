package rp.testing.api.model.filter;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserFilterBatch {

    private ArrayList<UpdateUserFilterRQ> content;
    private Page page;
}
