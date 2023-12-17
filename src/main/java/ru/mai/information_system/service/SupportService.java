package ru.mai.information_system.service;

import ru.mai.information_system.entity.Support;

import java.util.List;

public interface SupportService {

    public List<Support> getAllSupports();

    public Support getSupportById(int id);

    public void saveSupport(Support support);

    public void deleteSupportById(int id);
}
