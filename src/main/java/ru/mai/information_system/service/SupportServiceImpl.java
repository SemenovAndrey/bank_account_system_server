package ru.mai.information_system.service;

import ru.mai.information_system.dao.SupportDAO;
import ru.mai.information_system.dao.SupportDAOImpl;
import ru.mai.information_system.entity.Support;

import java.util.List;

public class SupportServiceImpl implements SupportService {

    private final SupportDAO supportDAO = new SupportDAOImpl();

    @Override
    public List<Support> getAllSupports() {
        return supportDAO.getAllSupports();
    }

    @Override
    public Support getSupportById(int id) {
        return supportDAO.getSupportById(id);
    }

    @Override
    public void saveSupport(Support support) {
        supportDAO.saveSupport(support);
    }

    @Override
    public void deleteSupportById(int id) {
        supportDAO.deleteSupportById(id);
    }
}
