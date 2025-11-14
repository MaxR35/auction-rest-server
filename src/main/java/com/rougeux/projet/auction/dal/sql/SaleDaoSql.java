package com.rougeux.projet.auction.dal.sql;

import com.rougeux.projet.auction.bo.Category;
import com.rougeux.projet.auction.bo.Item;
import com.rougeux.projet.auction.bo.Sale;
import com.rougeux.projet.auction.bo.User;
import com.rougeux.projet.auction.dal.ISaleDao;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Profile("sql")
public class SaleDaoSql implements ISaleDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public SaleDaoSql(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Sale> findAll() {
        String query = """
                SELECT s.sale_id, s.slug as sale_slug, s.create_at, s.start_at, s.end_at, s.starting_price,
                       s.current_price, s.likes,
                       u.user_id, u.slug as user_slug, u.firstname, u.lastname, u.image, u.create_at,
                       i.item_id, i.name, i.image, i.description,
                       c.category_id, c.slug as category_slug, c.label
                FROM SALES s
                LEFT OUTER JOIN BIDS b ON s.slug = b.slug
                LEFT OUTER JOIN USERS u ON s.seller_id = u.user_id
                LEFT OUTER JOIN ITEMS i ON s.item_id = i.item_id
                LEFT OUTER JOIN CATEGORIES c ON i.category_id = c.category_id
                WHERE s.closed = 0;
                """;

        return jdbcTemplate.query(query, new SaleRowMapper());
    }

    @Override
    public List<Sale> findAllByEndAt() {
        String query = """
                SELECT s.sale_id, s.slug as sale_slug, s.create_at, s.start_at, s.end_at, s.starting_price,
                       s.current_price, s.likes,
                       u.user_id, u.slug as user_slug, u.firstname, u.lastname, u.image, u.create_at,
                       i.item_id, i.name, i.image, i.description,
                       c.category_id, c.slug as category_slug, c.label
                FROM SALES s
                LEFT OUTER JOIN BIDS b ON s.slug = b.slug
                LEFT OUTER JOIN USERS u ON s.seller_id = u.user_id
                LEFT OUTER JOIN ITEMS i ON s.item_id = i.item_id
                LEFT OUTER JOIN CATEGORIES c ON i.category_id = c.category_id
                WHERE s.end_at < GETDATE();
                """;

        return  jdbcTemplate.query(query, new SaleRowMapper());
    }

    @Override
    public Sale findById(String itemSlug) {
        String query = """
                SELECT s.sale_id, s.slug as sale_slug, s.create_at, s.start_at, s.end_at, s.starting_price,
                       s.current_price, s.likes,
                       u.user_id, u.slug as user_slug, u.firstname, u.lastname, u.image, u.create_at,
                       i.item_id, i.name, i.image, i.description,
                       c.category_id, c.slug as category_slug, c.label
                 FROM SALES s
                 LEFT OUTER JOIN USERS u ON s.seller_id = u.user_id
                 LEFT OUTER JOIN ITEMS i ON s.item_id = i.item_id
                 LEFT OUTER JOIN CATEGORIES c ON i.category_id = c.category_id
                 WHERE s.slug = :slug
                """;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("slug", itemSlug);

        try {
            return jdbcTemplate.queryForObject(query, params, new SaleRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void save(Sale sale) {
        String query = """
        MERGE INTO SALES AS target
        USING (SELECT :slug AS slug) AS source
        ON target.slug = source.slug
        WHEN MATCHED THEN
            UPDATE SET start_at = :start_at,
                       end_at = :end_at,
                       current_price = :current_price,
                       likes = :likes
        WHEN NOT MATCHED THEN
            INSERT (slug, create_at, start_at, end_at, starting_price, current_price, likes, seller_id, item_id)
            VALUES (:slug, :create_at, :start_at, :end_at, :starting_price, :current_price, :likes, :seller_id, :item_id);
        """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("slug", sale.getSlug())
                .addValue("create_at", sale.getCreateAt())
                .addValue("start_at", sale.getStartAt())
                .addValue("end_at", sale.getEndAt())
                .addValue("starting_price", sale.getStartingPrice())
                .addValue("current_price", sale.getCurrentPrice())
                .addValue("likes", sale.getLikes())
                .addValue("seller_id", sale.getSeller().getId())
                .addValue("item_id", sale.getItem().getId());

        jdbcTemplate.update(query, params);
    }

    @Override
    public Integer checkData() {
        String query = """
                SELECT COUNT(*) FROM SALES
                """;

        return jdbcTemplate.query(query, rs -> (rs.next()) ?  rs.getInt(1) : 0);
    }

    private static class SaleRowMapper implements RowMapper<Sale> {
        @Override
        public Sale mapRow(ResultSet rs, int rowNum) throws SQLException {
            Category category = new Category();
            category.setId(rs.getString("category_id"));
            category.setSlug(rs.getString("category_slug"));
            category.setLabel(rs.getString("label"));

            Item item = new Item();
            item.setId(rs.getString("item_id"));
            item.setName(rs.getString("name"));
            item.setImage(rs.getString("image"));
            item.setDescription(rs.getString("description"));
            item.setCategory(category);

            User user = new User();
            user.setId(rs.getString("user_id"));
            user.setSlug(rs.getString("user_slug"));
            user.setFirstname(rs.getString("firstname"));
            user.setLastname(rs.getString("lastname"));
            user.setImage(rs.getString("image"));
            user.setCreateAt(rs.getObject("create_at", LocalDateTime.class));

            Sale sale = new Sale();
            sale.setId(rs.getString("sale_id"));
            sale.setSlug(rs.getString("sale_slug"));
            sale.setCreateAt(rs.getObject("create_at", LocalDateTime.class));
            sale.setStartAt(rs.getObject("start_at", LocalDateTime.class));
            sale.setEndAt(rs.getObject("end_at", LocalDateTime.class));
            sale.setStartingPrice(rs.getInt("starting_price"));
            sale.setCurrentPrice(rs.getInt("current_price"));
            sale.setLikes(rs.getInt("likes"));
            sale.setSeller(user);
            sale.setItem(item);

            return sale;
        }
    }
}
