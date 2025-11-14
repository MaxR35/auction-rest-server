package com.rougeux.projet.auction.dal.sql;

import com.rougeux.projet.auction.bo.Category;
import com.rougeux.projet.auction.dal.ICategoryDao;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("sql")
public class CategoryDaoSql implements ICategoryDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CategoryDaoSql(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Category> findAll() {
        String query = """
                SELECT category_id as id, slug, label
                FROM CATEGORIES
                """;

        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Category.class));
    }

    @Override
    public void save(Category category) {
        String query = """
                INSERT INTO CATEGORIES(slug, label)
                VALUES(:slug, :label)
                """;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("slug", category.getSlug());
        params.addValue("label", category.getLabel());

        jdbcTemplate.update(query, params);
    }

    @Override
    public Integer checkData() {
        String query = """
                SELECT COUNT(*) FROM CATEGORIES
                """;

        return jdbcTemplate.query(query, rs -> (rs.next()) ?  rs.getInt(1) : 0);
    }
}
