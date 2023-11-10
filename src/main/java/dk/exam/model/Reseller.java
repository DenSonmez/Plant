package dk.exam.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "reseller")
public class Reseller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reseller_id")
    private int id;

    @Setter
    @Column(name = "reseller_name", nullable = false)
    private String name;

    @Setter
    @Column(name = "reseller_address", nullable = false)
    private String address;

    @Setter
    @Column(name = "reseller_phone", nullable = false)
    private String phone;

    @Setter
    @OneToMany(mappedBy = "reseller", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<Plant> plants = new HashSet<>();

    /*@OneToMany
    @JoinTable(name = "reseller_plant",
            joinColumns = @JoinColumn(name = "reseller_id"),
            inverseJoinColumns = @JoinColumn(name = "plant_id"))
    private Set<Plant> plants;*/

    public Reseller(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public Reseller(int id) {
        this.id = id;
    }

    public void addPlant(Plant plant) {
        if (plant != null) {
            plant.setReseller(this);
            this.plants.add(plant);
        }
    }

    public void setPlants(Set<Plant> plants) {
        if (plants != null) {
            this.plants = plants;
            for (Plant p : plants) {
                p.setReseller(this);
            }
        }
    }
}
