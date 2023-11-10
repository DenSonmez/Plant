package dk.exam.model;

import dk.exam.dto.Del2.PlantDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

@Getter

@NoArgsConstructor


@Entity
@Table(name = "plant")
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plant_id")
    private int id;

    @Setter
    @Column(name = "plant_type", nullable = false)
    private String plantType;
    @Setter
    @Column(name = "plant_name", nullable = false)
    private String name;
    @Setter
    @Column(name = "max_height", nullable = false)
    private int maxHeight;
    @Setter
    @Column(name = "price", nullable = false)
    private float price;

    @ManyToOne
    @JoinColumn(name = "reseller_id")
    private Reseller reseller;


    public Plant(String plantType, String name, int maxHeight, float price) {
        this.plantType = plantType;
        this.name = name;
        this.maxHeight = maxHeight;
        this.price = price;
    }

    //det her er en constructor der tager imod en PlantDTO og laver den om til en Plant(konverterer)
    //for
    public Plant(PlantDTO plant) {
        this.plantType = plant.getPlantType();
        this.name = plant.getName();
        this.maxHeight = plant.getMaxHeight();
        this.price = plant.getPrice();
    }


    // her laver vi en metode der tager imod en liste af Plant og laver dem om til en liste af PlantDTO
    public void addReseller(Reseller reseller) {
        if (reseller != null) {
            this.reseller = reseller;
            reseller.addPlant(this);
        }
    }


    public void setReseller(Reseller reseller) {
        if (this.reseller != reseller) {
            this.reseller = reseller;
            if (reseller != null) {
                reseller.getPlants().add(this);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Plant plant)) return false;
        return Objects.equals(getId(), plant.getId()) && getPlantType() == plant.getPlantType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPlantType());
    }


}
