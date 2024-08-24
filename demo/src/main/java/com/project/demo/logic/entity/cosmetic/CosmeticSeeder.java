package com.project.demo.logic.entity.cosmetic;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CosmeticSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final CosmeticRepository cosmeticRepository;


    public CosmeticSeeder(CosmeticRepository cosmeticRepository) {
        this.cosmeticRepository = cosmeticRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.loadRoles();
    }

    private void loadRoles() {
        int count = 1;

        while (count <= 4){
            Optional<Cosmetic> optionalCosmetic = cosmeticRepository.findById((long) count);

            int finalCount = count;
            optionalCosmetic.ifPresentOrElse(System.out::println, () -> {
                Cosmetic cosmeticToCreate = new Cosmetic();

                switch (finalCount){
                    case 1 : {
                        cosmeticToCreate.setName("3D_GLASSES");
                        cosmeticToCreate.setSprite("../../../assets/cosmeticsV1/Glasses.png");
                        break;
                    }
                    case 2 : {
                        cosmeticToCreate.setName("CLASSY_OUTFIT");
                        cosmeticToCreate.setSprite("../../../assets/cosmeticsV1/Classy.png");
                        break;
                    }
                    case 3 : {
                        cosmeticToCreate.setName("BEACH_OUTFIT");
                        cosmeticToCreate.setSprite("../../../assets/cosmeticsV1/Beach.png");
                        break;
                    }
                    case 4 : {
                        cosmeticToCreate.setName("ROYAL_OUTFIT");
                        cosmeticToCreate.setSprite("../../../assets/cosmeticsV1/Royal.png");
                        break;
                    }
                    default: {
                        cosmeticToCreate.setName("");
                        cosmeticToCreate.setSprite("");
                        break;
                    }
                }


                cosmeticRepository.save(cosmeticToCreate);
            });

            count ++;
        }
    }

}
