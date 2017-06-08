package de.metas.banking.bankstatement.match.spi.impl;

import org.adempiere.util.Services;
import org.compiere.model.I_C_Payment;

import de.metas.banking.bankstatement.match.spi.IPaymentBatch;
import de.metas.banking.bankstatement.match.spi.IPaymentBatchProvider;
import de.metas.banking.bankstatement.match.spi.PaymentBatch;
import de.metas.banking.model.I_C_BankStatementLine_Ref;
import de.metas.payment.esr.api.IESRImportDAO;
import de.metas.payment.esr.model.I_ESR_Import;

/*
 * #%L
 * de.metas.payment.esr
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

/**
 * This implementation retrieves the {@link I_ESR_Import} for a given payment and wraps it in an {@link IPaymentBatch}. 
 * It's rather simple because {@link I_ESR_Import} has an explicit {@code C_Payment_ID} column.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class ESRPaymentBatchProvider implements IPaymentBatchProvider
{

	@Override
	public IPaymentBatch retrievePaymentBatch(final I_C_Payment payment)
	{
		final I_ESR_Import esrImport = Services.get(IESRImportDAO.class).retrieveESRImportForPayment(payment);
		if (esrImport == null)
		{
			return null;
		}

		return PaymentBatch.builder()
				.setRecord(esrImport)
				.setDate(esrImport.getDateDoc())
				.setPaymentBatchProvider(this)
				.build();
	}

	@Override
	public void linkBankStatementLine(final IPaymentBatch paymentBatch, final I_C_BankStatementLine_Ref bankStatementLineRef)
	{
		// nothing
	}
}
