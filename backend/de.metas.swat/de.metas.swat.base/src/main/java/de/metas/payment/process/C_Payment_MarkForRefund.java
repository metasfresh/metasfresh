/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.payment.process;

import de.metas.payment.api.IPaymentBL;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryFilter;
import org.compiere.model.I_C_Payment;

public class C_Payment_MarkForRefund extends JavaProcess implements IProcessPrecondition
{
	final IPaymentBL paymentBL = Services.get(IPaymentBL.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No Selection");
		}

		final IQueryFilter<I_C_Payment> selectedPaymentsFilter = context.getQueryFilter(I_C_Payment.class);

		final boolean anyUnallocatedPayment = paymentBL.anyUnallocatedPayment(selectedPaymentsFilter);

		if (!anyUnallocatedPayment)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No unallocated payments were selected");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final IQueryFilter<I_C_Payment> selectedPaymentsFilter = getProcessInfo().getQueryFilterOrElse(ConstantQueryFilter.of(false));
		paymentBL.markForRefund(selectedPaymentsFilter);

		return MSG_OK;
	}
}
