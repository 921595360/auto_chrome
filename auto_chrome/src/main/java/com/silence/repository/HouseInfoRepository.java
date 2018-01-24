
package com.silence.repository;

import com.silence.entity.HouseInfo;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface HouseInfoRepository extends CrudRepository<HouseInfo, String> {
    List<HouseInfo> findAll();

    Page<HouseInfo> findAll(Pageable var1);

    Page<HouseInfo> findByCountName(Pageable var1, String var2);

    List<HouseInfo> findByCountName(String var1);
}
