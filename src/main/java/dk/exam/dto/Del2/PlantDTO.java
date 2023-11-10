package dk.exam.dto.Del2;

import dk.exam.model.Plant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class PlantDTO {

    private int id;
    private String plantType;
    private String name;
    private int maxHeight;
    private float price;
    private int resellerId;


    public PlantDTO(String plantType, String name, int maxHeight, float price) {
        this.plantType = plantType;
        this.name = name;
        this.maxHeight = maxHeight;
        this.price = price;
    }

    public PlantDTO(Plant plant) {
       this.id = plant.getId();
        this.plantType = plant.getPlantType();
        this.name = plant.getName();
        this.maxHeight = plant.getMaxHeight();
        this.price = plant.getPrice();
        if (plant.getReseller() != null) {
            this.resellerId = plant.getReseller().getId();
        }
    }


    //den her metode konverterer en liste af Plant objekter til en liste af PlantDTO objekter
    public static List<PlantDTO> convertList(List<Plant> plants) {
        return plants.stream().map(PlantDTO::new).collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlantDTO plantDTO)) return false;
        return getId() == plantDTO.getId() && Objects.equals(getPlantType(), plantDTO.getPlantType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPlantType());
    }
}


