package ru.mai.information_system.dao;

import ru.mai.information_system.entity.Support;

import java.util.List;

public interface SupportDAO {

    public List<Support> getAllSupports();

    public Support getSupportById(int id);

    public void saveSupport(Support support);

    public void deleteSupportById(int id);
}
