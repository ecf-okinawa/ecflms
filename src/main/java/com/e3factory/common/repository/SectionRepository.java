package com.e3factory.common.repository;

import org.apache.ibatis.annotations.Mapper;

import com.e3factory.common.dto.Section;

@Mapper
public interface SectionRepository {
	public int getSectionCount();
	public void insert(Section section);
}
