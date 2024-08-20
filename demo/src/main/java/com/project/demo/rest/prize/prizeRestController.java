package com.project.demo.rest.prize;

import com.project.demo.logic.entity.prize.Prize;
import com.project.demo.logic.entity.prize.PrizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prize")
public class prizeRestController {
    @Autowired
    private PrizeRepository PrizeRepository;


    @GetMapping
    @PreAuthorize("hasAnyRole('ASSOCIATE', 'SUPER_ADMIN', 'BASE')")
    public List<Prize> getAllPrize() {
        return PrizeRepository.findAll();
    }

    @PostMapping
    public Prize addPrize(@RequestBody Prize prize) {
        return PrizeRepository.save(prize);
    }

    @GetMapping("/{id}")
    public Prize getPrizeById(@PathVariable Long id) {
        return PrizeRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @PutMapping("/{id}")
    public Prize updatePrize(@PathVariable Long id, @RequestBody Prize prize) {
        return PrizeRepository.findById(id)
                .map(existingPrize -> {
                    existingPrize.setExperience(existingPrize.getExperience());
                    existingPrize.setFood(existingPrize.getFood());
                    return PrizeRepository.save(existingPrize);
                })
                .orElseGet(() -> {
                    prize.setId(id);
                    return PrizeRepository.save(prize);
                });
    }
    @DeleteMapping("/{id}")
    public void deletePrize(@PathVariable Long id) {
        PrizeRepository.deleteById(id);
    }

}
