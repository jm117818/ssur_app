package pl.urz.ssur_app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Set<Groups> groups = new HashSet<>();
}
