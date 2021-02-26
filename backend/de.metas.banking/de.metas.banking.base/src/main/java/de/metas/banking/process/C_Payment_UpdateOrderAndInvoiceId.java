/*
 * #%L
 * de.metas.banking.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.banking.process;

import de.metas.document.engine.DocStatus;
import de.metas.payment.api.IPaymentBL;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_C_Payment;

import java.util.List;
import java.util.stream.Collectors;

public class C_Payment_UpdateOrderAndInvoiceId extends JavaProcess implements IProcessPrecondition
{

	private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No Selection");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		List<I_C_Payment> payments = getSelectedPayments();

		List<I_C_Payment> filteredPayments = payments
				.stream()
				.filter(p -> !p.isAllocated() && DocStatus.ofCode(p.getDocStatus()).isCompleted())
				.collect(Collectors.toList());

		paymentBL.setPaymentOrderAndInvoiceIdsAndAllocateItIfNecessary(filteredPayments);

		return MSG_OK;
	}

	private List<I_C_Payment> getSelectedPayments()
	{
		return retrieveSelectedRecordsQueryBuilder(I_C_Payment.class)
				.create()
				.list();
	}

}
