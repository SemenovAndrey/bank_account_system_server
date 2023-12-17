package ru.mai.information_system.service;

import ru.mai.information_system.dao.BankAccountTypeDAO;
import ru.mai.information_system.dao.BankAccountTypeDAOImpl;
import ru.mai.information_system.entity.BankAccountType;

import java.util.List;

public class BankAccountTypeServiceImpl implements BankAccountTypeService {

    private final BankAccountTypeDAO bankAccountTypeDAO = new BankAccountTypeDAOImpl();

    @Override
    public List<BankAccountType> getAllBankAccountTypes() {
        return bankAccountTypeDAO.getAllBankAccountTypes();
    }

    @Override
    public BankAccountType getBankAccountTypeById(int id) {
        return bankAccountTypeDAO.getBankAccountTypeById(id);
    }

    @Override
    public void saveBankAccountType(BankAccountType bankAccountType) {
        bankAccountTypeDAO.saveBankAccountType(bankAccountType);
    }

    @Override
    public void deleteBankAccountTypeById(int id) {
        bankAccountTypeDAO.deleteBankAccountTypeById(id);
    }
}
