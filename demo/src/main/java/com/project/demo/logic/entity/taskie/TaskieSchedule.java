
package com.project.demo.logic.entity.taskie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class TaskieSchedule {
    @Autowired
    private TaskieRepository taskieRepository;

    @Scheduled(fixedRate = 1000)
    @Transactional
    public void reduceTaskieStats() {
        List<Taskie> taskies = taskieRepository.findAll();
        for (Taskie taskie : taskies) {
            int reduceLifeBy = 0;

            if (taskie.getCleanse() > 0) {
                taskie.setCleanse(Math.max(taskie.getCleanse() - 1, 0));
            } else {
                reduceLifeBy++;
            }

            if (taskie.getHunger() > 0) {
                taskie.setHunger(Math.max(taskie.getHunger() - 1, 0));
            } else {
                reduceLifeBy++;
            }

            if (taskie.getEnergy() > 0) {
                taskie.setEnergy(Math.max(taskie.getEnergy() - 1, 0));
            } else {
                reduceLifeBy++;
            }

            if (reduceLifeBy > 0 && taskie.getLife() > 0) {
                taskie.setLife(Math.max(taskie.getLife() - reduceLifeBy, 0));
            }

            if (taskie.getCleanse() > 50 && taskie.getHunger() > 50 && taskie.getEnergy() > 50 && taskie.getLife() < 100) {
                taskie.setLife(Math.min(taskie.getLife() + 1, 100));
            }

            taskieRepository.save(taskie);
        }
    }
}