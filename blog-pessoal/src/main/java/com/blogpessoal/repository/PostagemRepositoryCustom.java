package com.blogpessoal.repository;

import java.util.List;
import com.blogpessoal.dto.StatsResponse;

public interface PostagemRepositoryCustom {
    List<StatsResponse> getPostCountByWeekDay();
}
