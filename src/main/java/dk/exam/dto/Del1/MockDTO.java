package dk.exam.dto.Del1;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class MockDTO {
        private int id;
        private String plantType;
        private String name;
        private int maxHeight;
        private float price;
        private int resellerId;


        public MockDTO(int id, String plantType, String name, int maxHeight, float price) {
            this.id = id;
            this.plantType = plantType;
            this.name = name;
            this.maxHeight = maxHeight;
            this.price = price;
        }


    }
