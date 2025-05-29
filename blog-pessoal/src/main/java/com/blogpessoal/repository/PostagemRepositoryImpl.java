package com.blogpessoal.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import com.blogpessoal.dto.StatsResponse;

@Repository
public class PostagemRepositoryImpl implements PostagemRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<StatsResponse> getPostCountByWeekDay() {
        String sql = """
            SELECT DAYNAME(p.data) AS day, COUNT(*) AS count
            FROM tb_postagens p
            GROUP BY DAYNAME(p.data)
            """;

        Query query = entityManager.createNativeQuery(sql);

        List<Object[]> results = query.getResultList();

        return results.stream()
                .map(obj -> new StatsResponse(
                        traduzirDia((String) obj[0]),
                        ((Number) obj[1]).longValue()))
                .collect(Collectors.toList());
    }

    private String traduzirDia(String dayNameEnglish) {
        return switch (dayNameEnglish.toLowerCase()) {
            case "monday" -> "Segunda-feira";
            case "tuesday" -> "Terça-feira";
            case "wednesday" -> "Quarta-feira";
            case "thursday" -> "Quinta-feira";
            case "friday" -> "Sexta-feira";
            case "saturday" -> "Sábado";
            case "sunday" -> "Domingo";
            default -> dayNameEnglish;
        };
    }
}
