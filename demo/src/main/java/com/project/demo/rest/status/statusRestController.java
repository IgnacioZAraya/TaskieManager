package com.project.demo.rest.status;

import com.project.demo.logic.entity.status.Status;
import com.project.demo.logic.entity.status.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/status")
public class statusRestController {
    @Autowired
    private StatusRepository StatusRepository;


    @GetMapping
    @PreAuthorize("hasAnyRole('ASSOCIATE', 'SUPER_ADMIN', 'BASE')")
    public List<Status> getAllStatus() {
        return StatusRepository.findAll();
    }

    @PostMapping
    public Status addStatus(@RequestBody Status status) {
        return StatusRepository.save(status);
    }

    @GetMapping("/{id}")
    public Status getStatusById(@PathVariable Long id) {
        return StatusRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @PutMapping("/{id}")
    public Status updateStatus(@PathVariable Long id, @RequestBody Status status) {
        return StatusRepository.findById(id)
                .map(existingStatus -> {
                    existingStatus.setName(existingStatus.getName());
                    existingStatus.setDescription(existingStatus.getDescription());
                    return StatusRepository.save(existingStatus);
                })
                .orElseGet(() -> {
                    status.setId(id);
                    return StatusRepository.save(status);
                });
    }
    @DeleteMapping("/{id}")
    public void deleteStatus(@PathVariable Long id) {
        StatusRepository.deleteById(id);
    }

}
