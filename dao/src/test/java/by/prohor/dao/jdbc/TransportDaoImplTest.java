package by.prohor.dao.jdbc;

import by.prohor.dao.TransportDao;
import by.prohor.dao.config.DaoConfiguration;
import by.prohor.model.Transport;
import by.prohor.model.type.FuelType;
import by.prohor.model.type.TypeTransport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DaoConfiguration.class)
public class TransportDaoImplTest {

    @Autowired
    private TransportDao transportDao;


    @Test
    public void getAll() {
        List<Transport> routes = transportDao.getAll();
        assertNotNull(routes);
        assertTrue(routes.size() > 0);
    }

    @Test
    public void save() {
        Transport transport = transportDao.save(new Transport(TypeTransport.TROLLEY, FuelType.GASOLINE, "2356 AB-1", 45, Date.valueOf("2020-02-12"), 5));
        assertNotNull(transport);
        assertEquals(transportDao.findById(transport.getTransportId()), transport);
    }

    @Test
    public void delete() {
        assertEquals(1, (int) transportDao.delete(2));
    }

    @Test
    public void update() {
        Transport transport = transportDao.findById(3);
        transport.setCapacity(555);
        assertEquals(1, (int) transportDao.update(transport));
        assertEquals(transport.getCapacity(), transportDao.findById(3).getCapacity());
    }

    @Test
    public void findById() {
        assertNotNull(transportDao.findById(7));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void findByNumberRoute_thanEmptyResultDataAccessException() {
        transportDao.findById(9999);
    }
}