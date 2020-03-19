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

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.ModelValidator;

import de.metas.banking.model.BankStatementAndLineAndRefId;
import de.metas.banking.model.BankStatementLineId;
import de.metas.banking.model.I_C_BankStatementLine_Ref;
import de.metas.banking.service.IBankStatementBL;
import de.metas.banking.service.IBankStatementDAO;
import de.metas.banking.service.IBankStatementListenerService;
import de.metas.payment.PaymentId;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * Code moved here form de.metas.swat de.metas.banking.model.MBankStatementLineRef.
 * 
 * @author ts
 *
 */
@Interceptor(I_C_BankStatementLine_Ref.class)
public class C_BankStatementLine_Ref
{
	public static final C_BankStatementLine_Ref instance = new C_BankStatementLine_Ref();

	private C_BankStatementLine_Ref()
	{
		super();
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(final I_C_BankStatementLine_Ref bankStatementLineRef)
	{
		final boolean newRecord = InterfaceWrapperHelper.isNew(bankStatementLineRef);

		if (newRecord)
		{
			final BankStatementLineId bankStatementLineId = BankStatementLineId.ofRepoId(bankStatementLineRef.getC_BankStatementLine_ID());
			final I_C_BankStatementLine bankStatementLine = Services.get(IBankStatementDAO.class).getLineById(bankStatementLineId);
			final PaymentId paymentId = PaymentId.ofRepoIdOrNull(bankStatementLine.getC_Payment_ID());
			if (paymentId != null)
			{
				throw new AdempiereException("@C_Payment_ID@"); // TODO: AD_Message
			}
		}

		if (bankStatementLineRef.getC_Invoice_ID() > 0)
		{
			final I_C_Invoice invoice = bankStatementLineRef.getC_Invoice();
			if (bankStatementLineRef.getC_BPartner_ID() > 0)
			{
				// ensure that bpartner matches the invoices bpartner
				if (invoice.getC_BPartner_ID() != bankStatementLineRef.getC_BPartner_ID())
				{
					throw new AdempiereException("Invoice has different BPartner");
				}
			}
			else
			{
				bankStatementLineRef.setC_BPartner_ID(invoice.getC_BPartner_ID());
			}
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void afterSave(final I_C_BankStatementLine_Ref bankStatementLineRef)
	{
		recalculateStatementLineAmountsIfNotDisabled(bankStatementLineRef);
	}

	private final void recalculateStatementLineAmountsIfNotDisabled(final I_C_BankStatementLine_Ref bankStatementLineRef)
	{
		//
		// Skip updating the bank statement line if recalculation is disabled
		final boolean recalculationDisabled = IBankStatementBL.DYNATTR_DisableBankStatementLineRecalculateFromReferences.getValue(bankStatementLineRef, false);
		if (recalculationDisabled)
		{
			return;
		}

		final BankStatementLineId bankStatementLineId = BankStatementLineId.ofRepoId(bankStatementLineRef.getC_BankStatementLine_ID());
		final I_C_BankStatementLine bankStatementLine = Services.get(IBankStatementDAO.class).getLineById(bankStatementLineId);

		Services.get(IBankStatementBL.class).recalculateStatementLineAmounts(bankStatementLine);
	}

	private static BankStatementAndLineAndRefId extractBankStatementAndLineAndRefId(@NonNull final I_C_BankStatementLine_Ref record)
	{
		return BankStatementAndLineAndRefId.ofRepoIds(
				record.getC_BankStatement_ID(),
				record.getC_BankStatementLine_ID(),
				record.getC_BankStatementLine_Ref_ID());
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
	public void beforeDelete(final I_C_BankStatementLine_Ref bankStatementLineRef)
	{
		final BankStatementLineId bankStatementLineId = BankStatementLineId.ofRepoId(bankStatementLineRef.getC_BankStatementLine_ID());
		final I_C_BankStatementLine bankStatementLine = Services.get(IBankStatementDAO.class).getLineById(bankStatementLineId);
		final PaymentId paymentId = PaymentId.ofRepoIdOrNull(bankStatementLine.getC_Payment_ID());

		// Avoid deleting the bank statement line reference if there is a payment set
		if (paymentId != null)
		{
			throw new AdempiereException("@C_Payment_ID@"); // TODO: AD_Message
		}

		//
		// Notify listeners that our bank statement line reference will become void (i.e. we are deleting it)
		Services.get(IBankStatementListenerService.class)
				.getListeners()
				.onBankStatementLineRefVoiding(extractBankStatementAndLineAndRefId(bankStatementLineRef));
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_DELETE })
	protected void afterDelete(final I_C_BankStatementLine_Ref bankStatementLineRef)
	{
		recalculateStatementLineAmountsIfNotDisabled(bankStatementLineRef);
	}
}
