package dk.exam.dto.Del2;

import dk.exam.model.Reseller;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ResellerDTO {
        private int id;
        private String name;
        private String address;
        private String phone;
        private Set<PlantDTO> plants = new HashSet<>();


        public ResellerDTO(String name, String address, String phone) {
            this.name = name;
            this.address = address;
            this.phone = phone;
        }

    public ResellerDTO(Reseller reseller) {
        this.id = reseller.getId();
        this.name = reseller.getName();
        this.address = reseller.getAddress();
        this.phone = reseller.getPhone();

    }

}
