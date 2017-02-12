package de.metas.banking.bankstatement.match.spi;

import java.util.Date;

import org.adempiere.util.lang.ITableRecordReference;

import de.metas.banking.bankstatement.match.api.IPaymentBatchFactory;
import de.metas.banking.model.I_C_BankStatementLine_Ref;

/*
 * #%L
 * de.metas.banking.swingui
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

/**
 * Payment batch used for bank statement - payment matching.
 * 
 * A payment batch it's an abstraction of a document which is grouping some payments in batches (e.g. Pay Selection, ESR Import etc).
 *
 * NOTE:
 * <ul>
 * <li>To acquire a {@link IPaymentBatch} please use {@link IPaymentBatchFactory}.
 * </ul>
 * 
 * NOTE to developer: please consider extending {@link PaymentBatch}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IPaymentBatch
{
	String getId();

	Date getDate();

	@Override
	String toString();

	ITableRecordReference getRecord();

	/**
	 * Link the bank statement line to this payment batch.
	 * 
	 * @param bankStatementLineRef
	 */
	void linkBankStatementLine(I_C_BankStatementLine_Ref bankStatementLineRef);
}
