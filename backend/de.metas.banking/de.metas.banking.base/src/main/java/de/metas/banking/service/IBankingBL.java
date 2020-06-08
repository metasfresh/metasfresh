package de.metas.banking.service;

import java.util.List;

import org.adempiere.banking.model.I_C_Invoice;

import de.metas.util.ISingletonService;

public interface IBankingBL extends ISingletonService
{
	List<I_C_Invoice> createInvoicesForRecurrentPayments(String trxName);
}
