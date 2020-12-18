package com.lrm.service;

import com.lrm.po.Tag;
import com.lrm.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {

    Tag saveTag(Tag Tag);

    Tag getTag(Long id);

    Tag getTagByName(String name);

    Page<Tag> listTag(Pageable pageable);

    List<Tag> listTag();

    List<Tag> listTag(String ids);

    List<Tag> getTagTop(Integer size);

    Tag updateTag(Long id, Tag Tag);

    void deleteTag(Long id);
}
