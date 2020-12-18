package com.lrm.dao;

import com.lrm.po.Blog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {

    @Query("select b from Blog b where b.recommend = true")
    List<Blog> findTop(Pageable pageable);

    @Query(value = "select t_blog.* from t_blog where t_blog.type_id = :id", nativeQuery = true)
    List<Blog> findAllByTypeId(@Param("id") Long id);

    @Query(value = "select t_blog.* from t_blog where id in (select blogs_id from t_blog_tags where tags_id = :id)", nativeQuery = true)
    List<Blog> findAllByTagId(@Param("id") Long id);
}
