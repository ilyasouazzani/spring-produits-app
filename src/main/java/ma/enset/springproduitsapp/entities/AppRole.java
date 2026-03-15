package ma.enset.springproduitsapp.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "app_roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppRole {

    @Id
    private String roleId;

    @Column(unique = true, nullable = false)
    private String roleName;

    @PrePersist
    public void prePersist() {
        if (this.roleId == null) {
            this.roleId = UUID.randomUUID().toString();
        }
    }
}
