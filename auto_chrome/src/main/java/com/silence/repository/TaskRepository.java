//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.silence.repository;

import com.silence.entity.Task;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, String> {
    List<Task> findAll(Sort var1);

    List<Task> findByCountName(String var1, Sort var2);

    List<Task> findByCountNameAndStatus(String var1, Integer var2, Sort var3);

    List<Task> findByStatus(Integer var1, Sort var2);

    int countByStatus(Integer var1);
}
