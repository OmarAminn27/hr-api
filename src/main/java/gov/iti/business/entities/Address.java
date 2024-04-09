package gov.iti.business.entities;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Address {
    private String street;
    private String zone;

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", zone='" + zone + '\'' +
                '}';
    }
}
