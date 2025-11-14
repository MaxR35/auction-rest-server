package com.rougeux.projet.auction.dal.sql;

import com.rougeux.projet.auction.bo.Bid;
import com.rougeux.projet.auction.bo.User;
import com.rougeux.projet.auction.dal.IBidDao;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
public class BidDaoSql implements IBidDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public BidDaoSql(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Bid> findBids(String itemSlug) {
        String query = """
                SELECT b.bid_id, b.amount, b.time, b.slug,
                       u.user_id, u.username, u.slug as user_slug, u.firstname, u.lastname, u.image, u.create_at
                FROM BIDS b
                LEFT OUTER JOIN USERS u ON b.user_id = u.user_id
                WHERE b.slug = :slug
                ORDER BY b.amount DESC;
                """;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("slug", itemSlug);

        return jdbcTemplate.query(query, params, new BidRowMapper());
    }

    @Override
    public Bid getByUserId(String userId) {
        String query = """
                SELECT b.amount
                FROM BIDS b
                WHERE b.user_id = :id
                """;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", userId);

        try {
            return jdbcTemplate.queryForObject(query, params, new BeanPropertyRowMapper<>(Bid.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void save(Bid bid) {
        String query = """
                INSERT INTO BIDS(amount, time, slug, user_id)
                VALUES (:amount, :time, :slug, :user_id)
                """;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("amount", bid.getAmount());
        params.addValue("time", bid.getTime());
        params.addValue("slug", bid.getSlug());
        params.addValue("user_id", bid.getUser().getId());

        jdbcTemplate.update(query, params);
    }

    @Override
    public Integer checkData() {
        String query = """
                SELECT COUNT(*) FROM BIDS
                """;

        return jdbcTemplate.query(query, rs -> (rs.next()) ? rs.getInt(1) : 0);
    }

    private static class BidRowMapper implements RowMapper<Bid> {
        @Override
        public Bid mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getString("user_id"));
            user.setUsername(rs.getString("username"));
            user.setSlug(rs.getString("user_slug"));
            user.setFirstname(rs.getString("firstname"));
            user.setLastname(rs.getString("lastname"));
            user.setImage(rs.getString("image"));
            user.setCreateAt(rs.getObject("create_at", LocalDateTime.class));

            Bid bid = new Bid();
            bid.setUser(user);
            bid.setId(rs.getString("bid_id"));
            bid.setAmount(rs.getInt("amount"));
            bid.setTime(rs.getObject("time", LocalDateTime.class));
            bid.setSlug(rs.getString("slug"));

            return bid;
        }
    }
}
