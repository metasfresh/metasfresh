package de.metas.payment.api.impl;

/*
 * #%L
 * de.metas.swat.base
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

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.invoice.service.IInvoiceDAO;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Payment;
import org.compiere.model.X_C_Payment;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.currency.ICurrencyDAO;
import de.metas.currency.impl.PlainCurrencyDAO;

public class PaymentBLTest
{
	@Rule
	public AdempiereTestWatcher testWatcher = new AdempiereTestWatcher();

	private POJOLookupMap db;
	private Properties ctx;
	private I_C_Payment payment;
	private I_C_Invoice invoice;
	private I_C_Currency currencyEUR;
	private I_C_Currency currencyCHF;
	private PlainCurrencyDAO currencyDAO;

	private I_C_Order order;

	protected IInvoiceDAO dao;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		ctx = Env.getCtx();
		Env.setContext(ctx, "#AD_Client_ID", 1);
		Env.setContext(ctx, "#AD_Org_ID", 1);
		Env.setContext(ctx, "#AD_Role_ID", 1);
		Env.setContext(ctx, "#AD_User_ID", 1);

		db = POJOLookupMap.get();

		currencyEUR = db.newInstance(I_C_Currency.class);
		currencyEUR.setISO_Code("EUR");
		currencyEUR.setStdPrecision(2);
		currencyEUR.setIsEuro(true);
		db.save(currencyEUR);
		POJOWrapper.enableStrictValues(currencyEUR);

		currencyCHF = db.newInstance(I_C_Currency.class);
		currencyCHF.setISO_Code("CHF");
		currencyCHF.setStdPrecision(2);
		currencyCHF.setIsEuro(false);
		db.save(currencyCHF);
		POJOWrapper.enableStrictValues(currencyCHF);

		currencyDAO = (PlainCurrencyDAO)Services.get(ICurrencyDAO.class);
		currencyDAO.setRate(currencyEUR, currencyCHF, new BigDecimal(2.0));
		currencyDAO.setRate(currencyCHF, currencyEUR, new BigDecimal(0.5));

		invoice = db.newInstance(I_C_Invoice.class);
		invoice.setAD_Org_ID(1);
		invoice.setC_Currency_ID(currencyEUR.getC_Currency_ID());
		invoice.setGrandTotal(new BigDecimal(50.0));
		invoice.setIsSOTrx(true);
		invoice.setProcessed(true);
		db.save(invoice);

		order = db.newInstance(I_C_Order.class);
		order.setAD_Org_ID(1);
		order.setC_Currency_ID(currencyEUR.getC_Currency_ID());
		order.setGrandTotal(new BigDecimal(50.0));
		order.setIsSOTrx(true);
		order.setProcessed(true);
		db.save(order);

		POJOWrapper.setDefaultStrictValues(false); // trying to fix a test fail that occurs when running on dejes901, but not when running on PC13
	}

	@Test
	public void testUpdateAmounts()
	{
		final PaymentBL paymentBL = new PaymentBL(); // the class under test
		final Timestamp today = SystemTime.asDayTimestamp();

		payment = db.newInstance(I_C_Payment.class);
		payment.setDateTrx(today); // needed for default C_ConversionType_ID retrieval
		paymentBL.updateAmounts(payment, I_C_Payment.COLUMNNAME_C_Currency_ID, false);
		db.save(payment);

		payment.setDiscountAmt(BigDecimal.ONE);
		payment.setWriteOffAmt(BigDecimal.ONE);
		payment.setOverUnderAmt(BigDecimal.ONE);
		paymentBL.updateAmounts(payment, I_C_Payment.COLUMNNAME_C_DocType_ID, false);

		Assert.assertEquals("Incorrect over/under amount", BigDecimal.ZERO, payment.getOverUnderAmt());
		Assert.assertEquals("Incorrect discount amount", BigDecimal.ZERO, payment.getDiscountAmt());
		Assert.assertEquals("Incorrect writteoff amount", BigDecimal.ZERO, payment.getWriteOffAmt());

		paymentBL.updateAmounts(payment, I_C_Payment.COLUMNNAME_C_DocType_ID, false);

		Assert.assertEquals("Incorrect over/under amount", BigDecimal.ZERO, payment.getOverUnderAmt());
		Assert.assertEquals("Incorrect discount amount", BigDecimal.ZERO, payment.getDiscountAmt());
		Assert.assertEquals("Incorrect writeoff amount", BigDecimal.ZERO, payment.getWriteOffAmt());

		payment.setDiscountAmt(BigDecimal.ONE);
		payment.setWriteOffAmt(BigDecimal.ONE);
		payment.setOverUnderAmt(BigDecimal.ONE);
		paymentBL.updateAmounts(payment, I_C_Payment.COLUMNNAME_C_Currency_ID, false);

		Assert.assertEquals("Incorrect over/under amount", BigDecimal.ZERO, payment.getOverUnderAmt());
		Assert.assertEquals("Incorrect discount amount", BigDecimal.ZERO, payment.getDiscountAmt());
		Assert.assertEquals("Incorrect writteoff amount", BigDecimal.ZERO, payment.getWriteOffAmt());
		payment.setC_Currency_ID(currencyEUR.getC_Currency_ID());
		db.save(payment);

		payment.setDiscountAmt(BigDecimal.ONE);
		payment.setWriteOffAmt(BigDecimal.ONE);
		payment.setOverUnderAmt(BigDecimal.ONE);
		paymentBL.onCurrencyChange(payment);

		Assert.assertEquals("Incorrect over/under amount", BigDecimal.ZERO, payment.getOverUnderAmt());
		Assert.assertEquals("Incorrect discount amount", BigDecimal.ZERO, payment.getDiscountAmt());
		Assert.assertEquals("Incorrect writteoff amount", BigDecimal.ZERO, payment.getWriteOffAmt());

		paymentBL.updateAmounts(payment, I_C_Payment.COLUMNNAME_C_ConversionType_ID, false);

		Assert.assertEquals("Incorrect over/under amount", BigDecimal.ZERO, payment.getOverUnderAmt());
		Assert.assertEquals("Incorrect discount amount", BigDecimal.ZERO, payment.getDiscountAmt());
		Assert.assertEquals("Incorrect writeoff amount", BigDecimal.ZERO, payment.getWriteOffAmt());

		Assert.assertEquals("Incorrect over/under amount", BigDecimal.ZERO, payment.getOverUnderAmt());
		Assert.assertEquals("Incorrect discount amount", BigDecimal.ZERO, payment.getDiscountAmt());
		Assert.assertEquals("Incorrect writeoff amount", BigDecimal.ZERO, payment.getWriteOffAmt());

		payment.setDiscountAmt(BigDecimal.ONE);
		payment.setWriteOffAmt(BigDecimal.ONE);
		payment.setOverUnderAmt(BigDecimal.ONE);
		paymentBL.updateAmounts(payment, I_C_Payment.COLUMNNAME_C_Currency_ID, false);

		Assert.assertEquals("Incorrect over/under amount", BigDecimal.ZERO, payment.getOverUnderAmt());
		Assert.assertEquals("Incorrect discount amount", BigDecimal.ZERO, payment.getDiscountAmt());
		Assert.assertEquals("Incorrect writteoff amount", BigDecimal.ZERO, payment.getWriteOffAmt());
		payment.setC_Currency_ID(currencyEUR.getC_Currency_ID());
		payment.setC_Invoice_ID(db.getRecords(I_C_Invoice.class).get(0).getC_Invoice_ID());
		invoice.setC_Currency_ID(currencyCHF.getC_Currency_ID());

		db.save(invoice);
		db.save(payment);

		paymentBL.updateAmounts(payment, I_C_Payment.COLUMNNAME_C_Currency_ID, false);

		Assert.assertEquals("Incorrect over/under amount", 0, payment.getOverUnderAmt().signum());
		Assert.assertEquals("Incorrect discount amount", 0, payment.getDiscountAmt().signum());
		Assert.assertEquals("Incorrect writteoff amount", 0, payment.getWriteOffAmt().signum());

		payment.setAD_Org_ID(1);
		// NOTE: Trick to reset the cached invoice, else payment.getC_Invoice() will return the cached invoice
		// and not "invoice" which we will modify below
		payment.setC_Invoice_ID(-1);
		payment.setC_Invoice_ID(db.getRecords(I_C_Invoice.class).get(0).getC_Invoice_ID());
		payment.setC_Currency_ID(currencyEUR.getC_Currency_ID());
		payment.setDiscountAmt(BigDecimal.ZERO);
		payment.setWriteOffAmt(BigDecimal.ZERO);
		payment.setOverUnderAmt(BigDecimal.ZERO);
		payment.setDocStatus(X_C_Payment.DOCSTATUS_Drafted);
		payment.setDocAction(X_C_Payment.DOCACTION_Complete);
		db.save(payment);

		// Test writeoff completion
		payment.setPayAmt(new BigDecimal(40.0));
		db.save(payment);
		invoice.setC_Currency_ID(currencyEUR.getC_Currency_ID());
		invoice.setGrandTotal(new BigDecimal(50.0));
		db.save(invoice);

		paymentBL.updateAmounts(payment, I_C_Payment.COLUMNNAME_IsOverUnderPayment, false);

		Assert.assertEquals("Incorrect writeoff amount", new BigDecimal(10.0), payment.getWriteOffAmt());

		// Test over/under completion
		payment.setPayAmt(new BigDecimal(60.0));
		payment.setIsOverUnderPayment(true);
		db.save(payment);

		paymentBL.updateAmounts(payment, I_C_Payment.COLUMNNAME_IsOverUnderPayment, false);

		Assert.assertEquals("Incorrect over/under amount", new BigDecimal(-10.0), payment.getOverUnderAmt());

		Assert.assertEquals("Incorrect payment amount in EUR", new BigDecimal(60.0), payment.getPayAmt());

		// Test currency change
		payment.setC_Currency_ID(currencyCHF.getC_Currency_ID());
		db.save(payment);

		paymentBL.updateAmounts(payment, I_C_Payment.COLUMNNAME_C_Currency_ID, false);

		Assert.assertEquals("Incorrect pay amount", 0, new BigDecimal(120.0).compareTo(payment.getPayAmt()));

		paymentBL.updateAmounts(payment, null, false); // B==D

		Assert.assertEquals("Incorrect pay amount", 0, new BigDecimal(120.0).compareTo(payment.getPayAmt()));

		currencyDAO.setRate(currencyEUR, currencyCHF, new BigDecimal(2.0));
		currencyDAO.setRate(currencyCHF, currencyEUR, new BigDecimal(0.5));

		// Called manually because we can't test properly with "creditMemoAdjusted" true
		paymentBL.onPayAmtChange(payment, false);

		Assert.assertTrue("Incorrect payment amount in CHF", new BigDecimal(120.0).compareTo(payment.getPayAmt()) == 0);

		payment.setC_Invoice_ID(0);
		payment.setC_Order_ID(order.getC_Order_ID());
		payment.setC_Currency_ID(currencyEUR.getC_Currency_ID());
		payment.setPayAmt(new BigDecimal(30.0));
		db.save(payment);

		paymentBL.updateAmounts(payment, I_C_Payment.COLUMNNAME_IsOverUnderPayment, false);

		payment.setDocStatus(X_C_Payment.DOCSTATUS_Completed);
		payment.setDocAction(X_C_Payment.DOCACTION_Close);
		db.save(payment);

		paymentBL.onPayAmtChange(payment, false);

	}

	@Test(expected = AdempiereException.class)
	public void exceptionTest()
	{
		final PaymentBL paymentBL = new PaymentBL(); // the class under test

		payment = db.newInstance(I_C_Payment.class);
		payment.setAD_Org_ID(1);
		payment.setC_Invoice_ID(db.getRecords(I_C_Invoice.class).get(0).getC_Invoice_ID());
		payment.setC_Currency_ID(currencyEUR.getC_Currency_ID());
		payment.setDiscountAmt(BigDecimal.ZERO);
		payment.setWriteOffAmt(BigDecimal.ZERO);
		payment.setOverUnderAmt(BigDecimal.ZERO);
		payment.setDocStatus(X_C_Payment.DOCSTATUS_Drafted);
		payment.setDocAction(X_C_Payment.DOCACTION_Complete);
		invoice.setC_Currency_ID(currencyCHF.getC_Currency_ID());

		db.save(invoice);
		db.save(payment);

		currencyDAO.setRate(currencyEUR, currencyCHF, new BigDecimal(0.0));
		currencyDAO.setRate(currencyCHF, currencyEUR, new BigDecimal(0.0));

		paymentBL.updateAmounts(payment, null, false);

	}
}
