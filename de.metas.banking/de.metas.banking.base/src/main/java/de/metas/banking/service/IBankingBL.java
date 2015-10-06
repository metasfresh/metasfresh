package de.metas.banking.service;

/*
 * #%L
 * de.metas.banking.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.io.File;
import java.util.List;
import java.util.Map;

import org.adempiere.banking.model.I_C_Invoice;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.MPaySelection;
import org.jdtaus.banking.dtaus.Transaction;

public interface IBankingBL extends ISingletonService
{

	/**
	 * creates the dta-transactions for a given PaySelection for two payment rules: Direct Debit and Direct Deposit
	 * 
	 * @param paySelections
	 */
	Map<String, List<Transaction>> createOrders(MPaySelection paySelections);

	List<I_C_Invoice> createInvoicesForRecurrentPayments(String trxName);

	void createDtaFile(List<Transaction> transactionList, File outputFile);

	String createBankAccountName(I_C_BP_BankAccount bankAccount);
}
