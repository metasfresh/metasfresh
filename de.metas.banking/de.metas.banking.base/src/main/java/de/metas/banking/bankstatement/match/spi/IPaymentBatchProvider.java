package de.metas.banking.bankstatement.match.spi;

import org.compiere.model.I_C_Payment;

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
 * Implementations of this interface are responsible for providing the {@link IPaymentBatch} for {@link I_C_Payment}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IPaymentBatchProvider
{
	IPaymentBatch retrievePaymentBatch(final I_C_Payment payment);

	/**
	 * Link the bank statement line to given payment batch.
	 *
	 * @param paymentBatch
	 * @param bankStatementLineRef
	 */
	void linkBankStatementLine(final IPaymentBatch paymentBatch, final I_C_BankStatementLine_Ref bankStatementLineRef);
}
