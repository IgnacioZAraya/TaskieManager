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
                        break;
                    }
                    case 2 : {
                        cosmeticToCreate.setName("CLASSY_OUTFIT");
                        break;
                    }
                    case 3 : {
                        cosmeticToCreate.setName("BEACH_OUTFIT");
                        break;
                    }
                    case 4 : {
                        cosmeticToCreate.setName("ROYAL_OUTFIT");
                        break;
                    }
                    default: {
                        cosmeticToCreate.setName("");
                        break;
                    }
                }

                cosmeticToCreate.setSprite("");
                cosmeticRepository.save(cosmeticToCreate);
            });

            count ++;
        }
    }

}
