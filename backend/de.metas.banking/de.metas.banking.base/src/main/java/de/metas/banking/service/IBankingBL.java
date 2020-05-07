package de.metas.banking.service;

import java.util.List;

import org.adempiere.banking.model.I_C_Invoice;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_BP_BankAccount;

public interface IBankingBL extends ISingletonService
{
	List<I_C_Invoice> createInvoicesForRecurrentPayments(String trxName);

	String createBankAccountName(I_C_BP_BankAccount bankAccount);
}
