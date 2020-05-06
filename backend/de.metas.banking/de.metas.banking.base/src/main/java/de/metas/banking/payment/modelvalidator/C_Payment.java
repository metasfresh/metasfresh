/**
 *
 */
package de.metas.banking.payment.modelvalidator;

import org.adempiere.ad.modelvalidator.IModelValidationEngine;

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

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.CopyRecordFactory;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.compiere.model.ModelValidator;

import de.metas.banking.service.IBankStatementDAO;
import de.metas.banking.service.ICashStatementBL;
import de.metas.document.IDocTypeDAO;
import de.metas.payment.api.IPaymentBL;
import de.metas.payment.api.IPaymentDAO;
import de.metas.util.Services;

/**
 * @author cg
 *
 */
@Interceptor(I_C_Payment.class)
public class C_Payment
{
	public static final transient C_Payment instance = new C_Payment();

	@Init
	public void init(final IModelValidationEngine engine)
	{

		CopyRecordFactory.enableForTableName(I_C_Payment.Table_Name);
	}

	private C_Payment()
	{
		super();
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = { I_C_Payment.COLUMNNAME_C_Currency_ID, I_C_Payment.COLUMNNAME_C_ConversionType_ID })
	public void onCurrencyChange(final I_C_Payment payment)
	{
		Services.get(IPaymentBL.class).onCurrencyChange(payment);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = I_C_Payment.COLUMNNAME_IsOverUnderPayment)
	public void onIsOverUnderPaymentChange(final I_C_Payment payment)
	{
		Services.get(IPaymentBL.class).onIsOverUnderPaymentChange(payment, true);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = I_C_Payment.COLUMNNAME_PayAmt)
	public void onPayAmtChange(final I_C_Payment payment)
	{
		Services.get(IPaymentBL.class).onPayAmtChange(payment, true);
	}

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_VOID })
	public void onBeforeVoid(final I_C_Payment payment)
	{
		//
		// Make sure we are not allowing a payment, which is on bank statement, to be voided.
		// It shall be reversed if really needed.
		if (Services.get(IBankStatementDAO.class).isPaymentOnBankStatement(payment))
		{
			throw new AdempiereException("@void.payment@");
		}

	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_REVERSECORRECT })
	public void onAfterReverse(final I_C_Payment payment)
	{
		//
		// Auto-reconcile the payment and it's reversal if the payment is not present on bank statements
		if (!Services.get(IBankStatementDAO.class).isPaymentOnBankStatement(payment))
		{
			payment.setIsReconciled(true);
			InterfaceWrapperHelper.save(payment);

			final I_C_Payment reversal = payment.getReversal();
			reversal.setIsReconciled(true);
			InterfaceWrapperHelper.save(reversal);
		}
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_COMPLETE })
	public void createCashStatementLineIfNeeded(final I_C_Payment payment)
	{
		if (!Services.get(IPaymentBL.class).isCashTrx(payment))
		{
			return;
		}

		if (!Services.get(ISysConfigBL.class).getBooleanValue("CASH_AS_PAYMENT", true, payment.getAD_Client_ID()))
		{
			return;
		}

		Services.get(ICashStatementBL.class).createCashStatementLine(payment);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = { I_C_Payment.COLUMNNAME_DateTrx })
	public void onDateChange(final I_C_Payment payment)
	{
		final I_C_Invoice invoice = payment.getC_Invoice();
		if (invoice == null)
		{
			return;
		}
		else
		{
			final IPaymentDAO paymentDAO = Services.get(IPaymentDAO.class);
			final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
			paymentDAO.updateDiscountAndPayment(payment, invoice.getC_Invoice_ID(), docTypeDAO.getById(invoice.getC_DocType_ID()));
		}
	}

}
