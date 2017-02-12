package de.metas.banking.service;

import java.util.Date;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;
import java.util.Properties;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.I_C_Payment;

import de.metas.banking.interfaces.I_C_BankStatementLine_Ref;

public interface IBankStatementDAO extends ISingletonService
{
	<T extends I_C_BankStatementLine> List<T> retrieveLines(I_C_BankStatement bankStatement, Class<T> clazz);

	List<I_C_BankStatementLine_Ref> retrieveLineReferences(I_C_BankStatementLine bankStatementLine);

	/**
	 * Checks if given payment is present on any {@link I_C_BankStatementLine} or {@link I_C_BankStatementLine_Ref}.
	 * 
	 * This method is NOT checking the {@link I_C_Payment#isReconciled()} flag but instead is doing a lookup in bank statement lines.
	 * 
	 * @param payment
	 * @return true if given payment is present on any bank statement line or reference.
	 */
	boolean isPaymentOnBankStatement(I_C_Payment payment);

	/**
	 * Retrieve all the BankStatement documents that are marked as posted but do not actually have fact accounts.
	 * 
	 * Exclude the entries that have trxAmt = 0. These entries will produce 0 in posting
	 * 
	 * @param ctx
	 * @param startTime
	 * @return
	 */
	List<I_C_BankStatement> retrievePostedWithoutFactAcct(Properties ctx, Date startTime);
}
