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
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Payment;
import org.compiere.model.ModelValidator;

import de.metas.banking.model.I_C_BankStatementLine;
import de.metas.banking.model.I_C_BankStatementLine_Ref;
import de.metas.banking.payment.IBankStatmentPaymentBL;
import de.metas.banking.service.IBankStatementBL;
import de.metas.banking.service.IBankStatementDAO;
import de.metas.banking.service.IBankStatementListenerService;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentBL;
import de.metas.util.Services;

@Interceptor(I_C_BankStatementLine.class)
public class C_BankStatementLine
{
	public static final C_BankStatementLine instance = new C_BankStatementLine();

	private C_BankStatementLine()
	{
		super();
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = I_C_BankStatementLine.COLUMNNAME_C_Payment_ID)
	public void updatePaymentDependentFields(final I_C_BankStatementLine bankStatementLine, final ModelChangeType changeType)
	{
		final PaymentId paymentId = PaymentId.ofRepoIdOrNull(bankStatementLine.getC_Payment_ID());

		//
		// Do nothing if we are dealing with a new line which does not have an C_Payment_ID
		if (changeType.isNew() && paymentId == null)
		{
			return;
		}

		final IBankStatmentPaymentBL bankStatmentPaymentBL = Services.get(IBankStatmentPaymentBL.class);

		if (paymentId != null)
		{
			final IPaymentBL paymentService = Services.get(IPaymentBL.class);

			final I_C_Payment payment = paymentService.getById(paymentId);
			bankStatmentPaymentBL.setPayment(bankStatementLine, payment);

			paymentService.markReconciledAndSave(payment);
		}
		else
		{
			bankStatmentPaymentBL.setPayment(bankStatementLine, (PaymentId)null);
		}

	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
	public void onBeforeDelete(final I_C_BankStatementLine bankStatementLine)
	{
		//
		// Notify listeners that our bank statement line will become void (i.e. we are deleting it)
		Services.get(IBankStatementListenerService.class)
				.getListeners()
				.onBankStatementLineVoiding(bankStatementLine);

		//
		// Delete all bank statement line references
		for (final I_C_BankStatementLine_Ref lineRef : Services.get(IBankStatementDAO.class).retrieveLineReferences(bankStatementLine))
		{
			IBankStatementBL.DYNATTR_DisableBankStatementLineRecalculateFromReferences.setValue(lineRef, true);
			InterfaceWrapperHelper.delete(lineRef);
		}
	}
}
