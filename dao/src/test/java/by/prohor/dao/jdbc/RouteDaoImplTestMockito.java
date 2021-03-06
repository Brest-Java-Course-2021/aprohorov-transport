package by.prohor.dao.jdbc;

import by.prohor.model.Route;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * Created by Artsiom Prokharau 05.03.2021
 */

@RunWith(MockitoJUnitRunner.class)
public class RouteDaoImplTestMockito {

    @InjectMocks
    private RouteDaoImpl routeDao;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Test
    public void findAllTest() {
        List<Route> all = routeDao.getAll();
        Assertions.assertNotNull(all);
    }

    @Test
    public void findbyNumberRoute() {
        Route byNumberRoute = routeDao.findByNumberRoute(2);
        System.out.println(byNumberRoute);
    }


}
