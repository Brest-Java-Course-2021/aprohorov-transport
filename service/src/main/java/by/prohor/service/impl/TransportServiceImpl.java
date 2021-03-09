package by.prohor.service.impl;

import by.prohor.dao.TransportDao;
import by.prohor.model.Transport;
import by.prohor.service.TransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Artsiom Prokharau 04.03.2021
 */

@Service
@Transactional
public class TransportServiceImpl implements TransportService {

    private final TransportDao transportDao;

    public TransportServiceImpl(TransportDao transportDao) {
        this.transportDao = transportDao;
    }


    @Override
    public Transport findById(Integer Id) {
        return transportDao.findById(Id);
    }

    @Override
    public List<Transport> getAll() {
        return transportDao.getAll();
    }

    @Override
    public Transport save(Transport model) {
        return transportDao.save(model);
    }

    @Override
    public Integer delete(Integer id) {
        return transportDao.delete(id);
    }

    @Override
    public Integer update(Transport model) {
        return transportDao.update(model);
    }
}
