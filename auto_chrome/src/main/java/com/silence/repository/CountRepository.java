
package com.silence.repository;

import com.silence.entity.Count;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface CountRepository extends CrudRepository<Count, String> {
    List<Count> findAll();

    Count findByCountName(String countName);
}
