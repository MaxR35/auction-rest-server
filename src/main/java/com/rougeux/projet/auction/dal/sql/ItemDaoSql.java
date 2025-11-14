package com.rougeux.projet.auction.dal.sql;

import com.rougeux.projet.auction.bo.Category;
import com.rougeux.projet.auction.bo.Item;
import com.rougeux.projet.auction.dal.IItemDao;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@Profile("sql")
public class ItemDaoSql implements IItemDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ItemDaoSql(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Item> findAll() {
        String query = """
                SELECT i.item_id, i.name, i.description, i.image, c.category_id, c.slug, c.label
                FROM ITEMS i
                LEFT OUTER JOIN CATEGORIES c ON i.category_id = c.category_id
                """;

        return jdbcTemplate.query(query, new ItemRowMapper());
    }

    @Override
    public void save(Item item) {
        String query = """
                INSERT INTO ITEMS(name, description, image, category_id)
                VALUES(:name, :description, :image, :category_id)
                """;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", item.getName());
        params.addValue("description", item.getDescription());
        params.addValue("image", item.getImage());
        params.addValue("category_id", item.getCategory().getId());

        jdbcTemplate.update(query, params);
    }

    @Override
    public Integer checkData() {
        String query = """
                SELECT COUNT(*) FROM ITEMS
                """;

        return jdbcTemplate.query(query, rs -> (rs.next()) ?  rs.getInt(1) : 0);
    }

    private static class ItemRowMapper implements RowMapper<Item> {

        @Override
        public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
            Item item = new Item();

            item.setId(rs.getString("item_id"));
            item.setName(rs.getString("name"));
            item.setDescription(rs.getString("description"));
            item.setImage(rs.getString("image"));

            Category category = new Category();

            category.setId(rs.getString("category_id"));
            category.setSlug(rs.getString("slug"));
            category.setLabel(rs.getString("label"));

            item.setCategory(category);
            return item;
        }
    }
}
