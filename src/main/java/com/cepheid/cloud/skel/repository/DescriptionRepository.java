package com.cepheid.cloud.skel.repository;

import com.cepheid.cloud.skel.model.Description;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DescriptionRepository extends JpaRepository<Description, Long> {

    @Modifying
    @Query("update Description d set d.descriptionComment = :descriptionComment where d.id = :id")
    void updateById(@Param("descriptionComment") String descriptionComment,
                    @Param("id") Long id);
}
