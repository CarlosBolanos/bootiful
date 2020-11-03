package srb.bootstrap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class Customer {
    private @Getter @Setter Long id;
    private @Getter @Setter String name;
}
