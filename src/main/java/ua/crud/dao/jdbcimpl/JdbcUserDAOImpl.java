package ua.crud.dao.jdbcimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.crud.dao.UserDAO;
import ua.crud.model.User;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JdbcUserDAOImpl implements UserDAO {

    @Value(value = "${ALL_USER_SELECT_QUERY}")
    private String selectAllUser;

    @Value(value = "${DELETE_USER_QUERY}")
    private String DELETE_USER;

    @Value(value = "${INSERT_USER}")
    private String INSERT_USER;

    @Value(value = "${SELECT_USER}")
    private String SELECT_USER;

    @Value(value = "${UPDATE_USER}")
    private String UPDATE_USER;

    @Value(value = "${FIND_USER_BY_FIO}")
    private String FIND_USER_BY_FIO;

    private NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcUserDAOImpl() {
    }

    @Autowired
    public JdbcUserDAOImpl(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public User findUserById(Long id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        return jdbcTemplate.queryForObject(SELECT_USER, params, new UserRowMapper());
    }

    @Override
    public List<User> findUserByFio(String fio) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("fio",fio + "%");

        System.out.println(FIND_USER_BY_FIO);
        return jdbcTemplate.query(FIND_USER_BY_FIO, params, new UserRowMapper());
    }

    @Override
    public void deleteUser(Long id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        jdbcTemplate.update(DELETE_USER, params);
    }

    @Override
    public void updateUser(User user) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", user.getId());
        params.addValue("username", user.getLogin());
        params.addValue("password", user.getPassword());
        params.addValue("fio", user.getFio());

        jdbcTemplate.update(UPDATE_USER, params);
    }

    @Override
    public void createUser(User user) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("username", user.getLogin());
        params.addValue("password", user.getPassword());
        params.addValue("fio", user.getFio());

        jdbcTemplate.update(INSERT_USER, params);
    }

    @Override
    public List<User> findAll() {
        System.out.println(selectAllUser);
        return jdbcTemplate.query(selectAllUser, new UserRowMapper());
    }

    private static final class UserRowMapper implements RowMapper<User>{

        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setId(resultSet.getLong("id"));
            user.setLogin(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            user.setFio(resultSet.getString("fio"));
            return user;
        }
    }
}
