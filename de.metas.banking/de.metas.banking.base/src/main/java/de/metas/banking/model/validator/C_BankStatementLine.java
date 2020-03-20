package de.metas.banking.model.validator;

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

import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.I_C_Payment;
import org.compiere.model.ModelValidator;

import de.metas.banking.model.BankStatementLineId;
import de.metas.banking.payment.IBankStatmentPaymentBL;
import de.metas.banking.service.IBankStatementBL;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentBL;
import de.metas.util.Services;

@Interceptor(I_C_BankStatementLine.class)
public class C_BankStatementLine
{
	public static final C_BankStatementLine instance = new C_BankStatementLine();

	private C_BankStatementLine()
	{
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = I_C_BankStatementLine.COLUMNNAME_C_Payment_ID)
	public void updatePaymentDependentFields(final I_C_BankStatementLine bankStatementLine, final ModelChangeType changeType)
	{
		final IPaymentBL paymentBL = Services.get(IPaymentBL.class);

		//
		// Do nothing if we are dealing with a new line which does not have an C_Payment_ID
		final PaymentId paymentId = PaymentId.ofRepoIdOrNull(bankStatementLine.getC_Payment_ID());

		if (changeType.isNew() && paymentId == null)
		{
			return;
		}

		final IBankStatmentPaymentBL bankStatmentPaymentBL = Services.get(IBankStatmentPaymentBL.class);

		if (paymentId != null)
		{
			final I_C_Payment payment = paymentBL.getById(paymentId);
			bankStatmentPaymentBL.setC_Payment(bankStatementLine, payment);

			paymentBL.markReconciled(payment);
		}
		else
		{
			bankStatmentPaymentBL.setC_Payment(bankStatementLine, null);
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void onBeforeDelete(final I_C_BankStatementLine bankStatementLine)
	{
		final IBankStatementBL bankStatementBL = Services.get(IBankStatementBL.class);

		final BankStatementLineId bankStatementLineId = BankStatementLineId.ofRepoId(bankStatementLine.getC_BankStatementLine_ID());
		bankStatementBL.deleteReferences(bankStatementLineId);
	}
}
