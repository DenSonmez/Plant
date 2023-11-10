package dk.exam.dao.del1;

import dk.exam.dto.Del1.MockDTO;

import dk.exam.exception.ApiException;

import java.util.ArrayList;
import java.util.List;

public class MockDAO {

    static List<MockDTO> plants = new ArrayList<>(
    List.of(
            new MockDTO(1, "Bush", "Albertine", 60, 199.50f),
            new MockDTO(2, "Rosa", "Gallicanae", 35, 299.0f),
            new MockDTO(4, "Rhododendron", "Red Tulip", 20, 5.99f),
            new MockDTO(4, "Rosa", "Purple Rhodo", 40, 269.50f),
            new MockDTO(5, "Fruitandberries", "AromaApple", 100, 199.50f)
    ));

    public static List<MockDTO> getAll() {
        return plants;
    }

    public static MockDTO getById(int id) throws ApiException {
        for (MockDTO plant : plants) {
            if (plant.getId() == id) {
                return plant;
            }
        }
        throw new ApiException(404, "No plant with id " + id + " found");

    }

    public static List<MockDTO> getByType(String type) {
        List<MockDTO> plantsByType = new ArrayList<>();
        for (MockDTO plant : plants) {
            if (plant.getPlantType().equalsIgnoreCase(type)) {
                plantsByType.add(plant);
            }
        }
        return plantsByType;
    }

    public static MockDTO create(MockDTO plant) {
        int id = plants.size() + 1;
        plant.setId(id);
        plants.add(plant);
        return plant;
    }

    public static void delete(int id) {
        for (MockDTO plant : plants) {
            if (plant.getId() == id) {
                plants.remove(plant);
                return;
            }
        }
    }

    public static MockDTO update(int id, MockDTO updatedPlant) throws ApiException {
        for (MockDTO p : plants) {
            if (p.getId() == id) {
                p.setPlantType(updatedPlant.getPlantType());
                p.setName(updatedPlant.getName());
                p.setPrice(updatedPlant.getPrice());
                p.setMaxHeight(updatedPlant.getMaxHeight());
                return p;
            }
        }
        throw new ApiException(404, "Plant with id: " + id + " couldn't be found.");
    }

    //returns a list of plants with a maximum height of 100 cm using the stream API, filter() and a
    //predicate function.
    public static List<MockDTO> getByMaxHeight(int maxHeight) {
        return plants.stream()
                .filter(plant -> plant.getMaxHeight() <= maxHeight)
                .toList();
    }

    //maps / converts a list of PlantDTOs to a list of Strings containing the plant names. Again use the stream
    //API and the map function
    public static List<String> getPlantNames() {
        return plants.stream()
                .map(MockDTO::getName)
                .toList();
    }
    //sorts a list of PlantDTOs by name using streams, sorted(), and a Comparator.
    public static List<MockDTO> sortByName() {
        return plants.stream()
                .sorted((p1, p2) -> p1.getName().compareTo(p2.getName()))
                .toList();
    }
}
