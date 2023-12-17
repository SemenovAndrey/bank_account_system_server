package ru.mai.information_system.dao;

import ru.mai.information_system.entity.BankAccountType;

import java.util.List;

public interface BankAccountTypeDAO {

    public List<BankAccountType> getAllBankAccountTypes();

    public BankAccountType getBankAccountTypeById(int id);

    public void saveBankAccountType(BankAccountType bankAccountType);

    public void deleteBankAccountTypeById(int id);
}
