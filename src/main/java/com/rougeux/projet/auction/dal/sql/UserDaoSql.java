package com.rougeux.projet.auction.dal.sql;

import com.rougeux.projet.auction.bo.User;
import com.rougeux.projet.auction.dal.IUserDao;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("sql")
public class UserDaoSql implements IUserDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserDaoSql(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> findAll() {
        String query = """
                SELECT user_id as id, username, password, slug, firstname,
                       lastname, image, phone, credit, create_at
                FROM USERS
                """;

        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public User findByUsername(String username) {
        String query = """
                SELECT u.user_id as id, u.username, u.password, u.slug, u.firstname, u.lastname,
                       u.image, u.phone, u.credit, u.admin, u.enabled, u.create_at
                FROM USERS u
                WHERE username = :username
                """;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("username", username);

        try {
            return jdbcTemplate.queryForObject(query, params, new BeanPropertyRowMapper<>(User.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public User findBySlug(String slug) {
        String query = """
                SELECT user_id as id, username, slug, firstname, lastname, image, phone, credit, create_at
                FROM USERS u
                WHERE slug = :slug
                """;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("slug", slug);

        try {
            return jdbcTemplate.queryForObject(query, params, new BeanPropertyRowMapper<>(User.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void save(User user) {
        String query = """
                MERGE INTO USERS AS target
                USING (SELECT :slug AS slug) AS source
                ON target.slug = source.slug
                WHEN MATCHED THEN
                    UPDATE SET
                        firstname = :firstname, lastname = :lastname, image = :image,
                        phone = :phone, credit = :credit
                WHEN NOT MATCHED THEN
                    INSERT (username, password, slug, firstname, lastname, image, phone, credit, admin, enabled, create_at)
                    VALUES (:username, :password, :slug, :firstname, :lastname, :image, :phone, :credit, :admin, :enabled, :create_at);
        """;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("username", user.getUsername());
        params.addValue("password", user.getPassword());
        params.addValue("slug", user.getSlug());
        params.addValue("firstname", user.getFirstname());
        params.addValue("lastname", user.getLastname());
        params.addValue("image", user.getImage());
        params.addValue("phone", user.getPhone());
        params.addValue("credit", user.getCredit());
        params.addValue("admin", user.isAdmin());
        params.addValue("enabled", user.isEnabled());
        params.addValue("create_at", user.getCreateAt());

        jdbcTemplate.update(query, params);
    }

    @Override
    public Integer checkData() {
        String query = """
                SELECT COUNT(*) FROM USERS
                """;

        return jdbcTemplate.query(query, rs -> (rs.next()) ?  rs.getInt(1) : 0);
    }
}
