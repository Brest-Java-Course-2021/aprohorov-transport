package by.prohor.service.impl;

import by.prohor.dao.jdbc.TransportDaoImpl;
import by.prohor.model.Transport;
import by.prohor.model.type.FuelType;
import by.prohor.model.type.TransportType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransportServiceImplTest {

    @InjectMocks
    private TransportServiceImpl transportServiceImpl;

    @Mock
    private TransportDaoImpl transportDao;

    @Test
    void testFindById_whenTransportExistInDb() {
        when(transportDao.findById(any(Integer.class))).thenReturn(new Transport());
        assertNotNull(transportServiceImpl.findById(5));
    }

    @Test
    void testFindById_whenTransportExistInDb_thenThrowEmptyResultDataAccessException() {
        when(transportDao.findById(any(Integer.class))).thenThrow(EmptyResultDataAccessException.class);
        assertThrows(EmptyResultDataAccessException.class, () -> transportServiceImpl.findById(0));
    }

    @Test
    void testGetAll_whenDaoReturnAllTransportIsEmpty() {
        when(transportDao.getAllTransport()).thenReturn(Collections.emptyList());
        assertNotNull(transportServiceImpl.getAllTransport());
        assertEquals(0, transportServiceImpl.getAllTransport().size());
    }

    @Test
    void testGetAll_whenDaoReturnAllTransportsWithSizeThree() {
        List<Transport> transports = Arrays.asList(
                new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "2356 AB-1", 45, LocalDate.of(2020, 2, 12)),
                new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "2356 AB-1", 45, LocalDate.of(2020, 2, 12)),
                new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "2356 AB-1", 45, LocalDate.of(2020, 2, 12)));
        when(transportDao.getAllTransport()).thenReturn(transports);
        assertEquals(3, transportServiceImpl.getAllTransport().size());
    }

    @Test
    void testSave_whenTransportIsCorrect() {
        Transport transport = new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "2356 AB-1", 45, LocalDate.of(2020, 2, 12));
        when(transportDao.save(transport)).thenReturn(transport);
        assertEquals(transportServiceImpl.save(transport), transport);
    }

    @Test
    void testSave_save_whenObjectTransportIsEmpty_thenThrowDataIntegrityViolationException() {
        Transport transport = new Transport();
        when(transportDao.save(transport)).thenThrow(DataIntegrityViolationException.class);
        assertThrows(DataIntegrityViolationException.class, () -> transportServiceImpl.save(transport));
    }

    @Test
    void testDelete_whenTransportIsCorrect() {
        when(transportDao.delete(any(Integer.class))).thenReturn(1);
        assertEquals(1, transportServiceImpl.delete(4));
    }

    @Test
    void testDelete_whenTransportNotExistInDb() {
        when(transportDao.delete(0)).thenReturn(0);
        assertEquals(0, transportServiceImpl.delete(0));
    }

    @Test
    void testUpdate_whenTransportIsCorrect() {
        Transport transport = new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "2356 AB-1", 45, LocalDate.of(2020, 2, 12));
        transport.setCapacity(78);
        when(transportDao.update(any(Transport.class))).thenReturn(1);
        assertEquals(1, transportServiceImpl.update(transport));
    }

    @Test
    void testUpdate_whenTransportNotExistInDb() {
        when(transportDao.update(any(Transport.class))).thenReturn(0);
        assertEquals(0, transportServiceImpl.update(new Transport()));
    }

    @Test
    void findByNumberRoute_whenDaoReturnAllTransportsWithNumberRouteIsEmpty() {
        when(transportDao.findByNumberRoute(any(Integer.class))).thenReturn(Collections.emptyList());
        assertNotNull(transportServiceImpl.findAllTransportWithNumberRoute(0));
        assertEquals(0, transportServiceImpl.getAllTransport().size());
    }

    @Test
    void testGetAll_whenDaoReturnAllTransportsWithNumberRouteAndSizeThree() {
        List<Transport> transports = Arrays.asList(
                new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "2356 AB-1", 45, LocalDate.of(2020, 2, 12)),
                new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "2356 AB-1", 45, LocalDate.of(2020, 2, 12)),
                new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "2356 AB-1", 45, LocalDate.of(2020, 2, 12)));
        when(transportDao.findByNumberRoute(5)).thenReturn(transports);
        assertEquals(3, transportServiceImpl.findAllTransportWithNumberRoute(5).size());
    }

    @Test
    void testGetAll_whenDaoReturnAllNumberRoutesIsEmpty() {
        when(transportDao.getAllAvailableNumberRoutes()).thenReturn(Collections.emptyList());
        assertNotNull(transportServiceImpl.getAllAvailableNumberRoutes());
        assertEquals(0, transportServiceImpl.getAllAvailableNumberRoutes().size());
    }
}