package by.prohor.service.impl;

import by.prohor.dao.config.DaoConfiguration;
import by.prohor.service.RouteService;
import by.prohor.service.config.ServiceConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServiceConfiguration.class)
public class RouteServiceImplTest {


    @Autowired
    public RouteService routeService;

    @Test
    public void findByNumberRoute() {
        assertNotNull(routeService.findByNumberRoute(2));
    }

    @Test
    public void getAll() {
    }

    @Test
    public void save() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void update() {
    }

    @Test
    public void findById() {
    }
}