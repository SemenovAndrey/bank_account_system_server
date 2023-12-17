package ru.mai.information_system.service;

import ru.mai.information_system.entity.BankAccount;
import ru.mai.information_system.entity.BankAccountType;

import java.util.List;

public interface BankAccountTypeService {

    public List<BankAccountType> getAllBankAccountTypes();

    public BankAccountType getBankAccountTypeById(int id);

    public void saveBankAccountType(BankAccountType bankAccountType);

    public void deleteBankAccountTypeById(int id);
}
