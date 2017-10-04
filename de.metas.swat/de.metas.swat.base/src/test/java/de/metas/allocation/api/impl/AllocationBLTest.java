package de.metas.allocation.api.impl;

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


import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.invoice.service.IInvoiceDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.Services;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_Payment;
import org.compiere.model.X_C_DocType;
import org.compiere.model.X_C_Payment;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

import de.metas.adempiere.model.I_C_Currency;
import de.metas.adempiere.model.I_C_Invoice;
import de.metas.allocation.api.IAllocationBL;
import de.metas.allocation.api.IAllocationDAO;
import de.metas.document.engine.IDocumentBL;
import de.metas.document.engine.impl.PlainDocumentBL;
import de.metas.interfaces.I_C_BPartner;
import de.metas.interfaces.I_C_DocType;
import de.metas.payment.api.IPaymentBL;
import de.metas.payment.api.IPaymentDAO;
import de.metas.payment.api.impl.PlainPaymentDAO;

public class AllocationBLTest
{
	/** Watches current test and dumps the database to console in case of failure */
	@Rule
	public final TestWatcher testWatcher = new AdempiereTestWatcher();
	
	@BeforeClass
	public static void staticInit()
	{
		AdempiereTestHelper.get().staticInit();
	}

	private Properties ctx;
	protected POJOLookupMap db;
	protected PlainPaymentDAO dao;

	protected IPaymentBL paymentBL;

	@Before
	public final void beforeTest()
	{
		AdempiereTestHelper.get().init();
		dao = (PlainPaymentDAO)Services.get(IPaymentDAO.class);

		db = dao.getDB();

		paymentBL = Services.get(IPaymentBL.class);

		// Config PlainDocActionBL
		final PlainDocumentBL docActionBL = (PlainDocumentBL)Services.get(IDocumentBL.class);
		docActionBL.setDefaultProcessInterceptor(PlainDocumentBL.PROCESSINTERCEPTOR_CompleteDirectly);

		//
		// Setup context
		ctx = Env.getCtx();
		ctx.clear();
		Env.setContext(ctx, "#AD_Client_ID", 1);
		Env.setContext(ctx, "#AD_Org_ID", 1);
		Env.setContext(ctx, "#AD_Role_ID", 1);
		Env.setContext(ctx, "#AD_User_ID", 1);

		init();
	}

	protected void init()
	{
		// nothing
	}

	protected Properties getCtx()
	{
		return ctx;
	}

	/**
	 * If there is no payment, autoAllocateAvailablePayments() should return null.
	 */
	@Test
	public void test_AllocateForLine_NoPayment()
	{

		final I_C_DocType type = db.newInstance(I_C_DocType.class);
		type.setDocBaseType(X_C_DocType.DOCBASETYPE_APInvoice);
		db.save(type);

		final I_C_BPartner partner = db.newInstance(I_C_BPartner.class);
		partner.setValue("partner1");
		db.save(partner);

		final I_C_Currency currencyEUR = db.newInstance(I_C_Currency.class);
		currencyEUR.setISO_Code("EUR");
		currencyEUR.setStdPrecision(2);
		currencyEUR.setIsEuro(true);
		db.save(currencyEUR);
		POJOWrapper.enableStrictValues(currencyEUR);

		final I_C_Invoice invoice = db.newInstance(I_C_Invoice.class);
		invoice.setGrandTotal(new BigDecimal("100"));
		invoice.setC_DocType_ID(type.getC_DocType_ID());
		invoice.setIsSOTrx(true);
		invoice.setC_BPartner_ID(partner.getC_BPartner_ID());
		invoice.setC_Currency_ID(currencyEUR.getC_Currency_ID());
		db.save(invoice);
		
		final I_C_AllocationHdr alloc = Services.get(IAllocationBL.class).autoAllocateAvailablePayments(invoice);
		assertThat(alloc, nullValue());
	}
	
	@Test
	public void test_AllocateForLine_PartiallyPaid()
	{

		final Timestamp firstDate = new Timestamp(1000000);

		final I_C_DocType type = db.newInstance(I_C_DocType.class);
		type.setDocBaseType(X_C_DocType.DOCBASETYPE_APInvoice);
		InterfaceWrapperHelper.save(type);

		final I_C_BPartner partner = db.newInstance(I_C_BPartner.class);
		partner.setValue("partner1");
		InterfaceWrapperHelper.save(partner);

		final I_C_Currency currencyEUR = db.newInstance(I_C_Currency.class);
		currencyEUR.setISO_Code("EUR");
		currencyEUR.setStdPrecision(2);
		currencyEUR.setIsEuro(true);
		InterfaceWrapperHelper.save(currencyEUR);
		POJOWrapper.enableStrictValues(currencyEUR);

		final I_C_Invoice invoice = db.newInstance(I_C_Invoice.class);
		invoice.setGrandTotal(new BigDecimal("100"));
		invoice.setC_DocType_ID(type.getC_DocType_ID());
		invoice.setIsSOTrx(true);
		invoice.setC_BPartner_ID(partner.getC_BPartner_ID());
		invoice.setC_Currency_ID(currencyEUR.getC_Currency_ID());
		InterfaceWrapperHelper.setTrxName(invoice, Services.get(ITrxManager.class).createTrxName("trxName_test_AllocateForLine_PartiallyPaid", true));
		InterfaceWrapperHelper.save(invoice);

		final I_C_Payment payment1 = db.newInstance(I_C_Payment.class);
		payment1.setPayAmt(new BigDecimal("10"));
		payment1.setDateTrx(firstDate);
		payment1.setProcessed(true);
		payment1.setIsAllocated(false);
		payment1.setIsAutoAllocateAvailableAmt(true);
		payment1.setDocStatus(X_C_Payment.DOCSTATUS_Completed);
		payment1.setIsActive(true);
		payment1.setIsReceipt(true);
		payment1.setC_BPartner_ID(partner.getC_BPartner_ID());
		payment1.setC_Currency_ID(currencyEUR.getC_Currency_ID());
		InterfaceWrapperHelper.save(payment1);

		Assert.assertTrue(invoice.getGrandTotal().compareTo(Services.get(IInvoiceDAO.class).retrieveOpenAmt(invoice)) == 0);

		final I_C_AllocationHdr alloc = Services.get(IAllocationBL.class).autoAllocateAvailablePayments(invoice);
		assertThat(alloc, notNullValue());

		final List<I_C_AllocationLine> lines = Services.get(IAllocationDAO.class).retrieveLines(alloc);
		assertThat(lines.size(), is(1));
		
		assertThat(lines.get(0).getC_Invoice_ID(), is(invoice.getC_Invoice_ID()));
		assertThat(lines.get(0).getC_Payment_ID(), is(payment1.getC_Payment_ID()));
		assertThat(lines.get(0).getAmount(), comparesEqualTo(new BigDecimal("10"))); // payAmt of payment1
		
		assertThat(
				Services.get(IInvoiceDAO.class).retrieveOpenAmt(invoice),
				comparesEqualTo(invoice.getGrandTotal().subtract(payment1.getPayAmt())));
	}

	@Test
	public void test_AllocateForLine_FullyPaid()
	{

		final Timestamp firstDate = new Timestamp(1000000);

		final I_C_DocType type = db.newInstance(I_C_DocType.class);
		type.setDocBaseType(X_C_DocType.DOCBASETYPE_APInvoice);
		InterfaceWrapperHelper.save(type);

		final I_C_BPartner partner = db.newInstance(I_C_BPartner.class);
		partner.setValue("partner1");
		InterfaceWrapperHelper.save(partner);

		final I_C_Currency currencyEUR = db.newInstance(I_C_Currency.class);
		currencyEUR.setISO_Code("EUR");
		currencyEUR.setStdPrecision(2);
		currencyEUR.setIsEuro(true);
		InterfaceWrapperHelper.save(currencyEUR);
		POJOWrapper.enableStrictValues(currencyEUR);

		final I_C_Invoice invoice = db.newInstance(I_C_Invoice.class);
		invoice.setGrandTotal(new BigDecimal("100"));
		invoice.setC_DocType_ID(type.getC_DocType_ID());
		invoice.setIsSOTrx(true);
		invoice.setC_BPartner_ID(partner.getC_BPartner_ID());
		invoice.setC_Currency_ID(currencyEUR.getC_Currency_ID());
		InterfaceWrapperHelper.setTrxName(invoice, Services.get(ITrxManager.class).createTrxName("trxName_test_AllocateForLine_FullyPaid", true));
		InterfaceWrapperHelper.save(invoice);

		final I_C_Payment payment1 = db.newInstance(I_C_Payment.class);
		payment1.setPayAmt(new BigDecimal("10"));
		payment1.setDateTrx(firstDate);
		payment1.setProcessed(true);
		payment1.setIsAllocated(false);
		payment1.setIsAutoAllocateAvailableAmt(true);
		payment1.setDocStatus(X_C_Payment.DOCSTATUS_Completed);
		payment1.setIsActive(true);
		payment1.setIsReceipt(true);
		payment1.setC_BPartner_ID(partner.getC_BPartner_ID());
		payment1.setC_Currency_ID(currencyEUR.getC_Currency_ID());
		InterfaceWrapperHelper.save(payment1);

		final I_C_Payment payment2 = db.newInstance(I_C_Payment.class);
		payment2.setPayAmt(new BigDecimal("40"));
		payment2.setDateTrx(firstDate);
		payment2.setProcessed(true);
		payment2.setIsAllocated(false);
		payment2.setIsAutoAllocateAvailableAmt(true);
		payment2.setDocStatus(X_C_Payment.DOCSTATUS_Completed);
		payment2.setIsActive(true);
		payment2.setIsReceipt(true);
		payment2.setC_BPartner_ID(partner.getC_BPartner_ID());
		payment2.setC_Currency_ID(currencyEUR.getC_Currency_ID());
		InterfaceWrapperHelper.save(payment2);

		final I_C_Payment payment3 = db.newInstance(I_C_Payment.class);
		payment3.setPayAmt(new BigDecimal("50"));
		payment3.setDateTrx(firstDate);
		payment3.setProcessed(true);
		payment3.setIsAllocated(false);
		payment3.setIsAutoAllocateAvailableAmt(true);
		payment3.setDocStatus(X_C_Payment.DOCSTATUS_Completed);
		payment3.setIsActive(true);
		payment3.setIsReceipt(true);
		payment3.setC_BPartner_ID(partner.getC_BPartner_ID());
		payment3.setC_Currency_ID(currencyEUR.getC_Currency_ID());
		InterfaceWrapperHelper.save(payment3);

		Assert.assertTrue(invoice.getGrandTotal().compareTo(Services.get(IInvoiceDAO.class).retrieveOpenAmt(invoice)) == 0);

		final I_C_AllocationHdr alloc = Services.get(IAllocationBL.class).autoAllocateAvailablePayments(invoice);
		assertThat(alloc, notNullValue());

		final List<I_C_AllocationLine> lines = Services.get(IAllocationDAO.class).retrieveLines(alloc);
		assertThat(lines.size(), is(3));

		assertThat(lines.get(0).getC_Invoice_ID(), is(invoice.getC_Invoice_ID()));
		assertThat(lines.get(0).getC_Payment_ID(), is(payment1.getC_Payment_ID()));
		assertThat(lines.get(0).getAmount(), comparesEqualTo(new BigDecimal("10"))); // payAmt of payment1

		assertThat(lines.get(1).getC_Invoice_ID(), is(invoice.getC_Invoice_ID()));
		assertThat(lines.get(1).getC_Payment_ID(), is(payment2.getC_Payment_ID()));
		assertThat(lines.get(1).getAmount(), comparesEqualTo(new BigDecimal("40"))); // payAmt of payment2

		assertThat(lines.get(2).getC_Invoice_ID(), is(invoice.getC_Invoice_ID()));
		assertThat(lines.get(2).getC_Payment_ID(), is(payment3.getC_Payment_ID()));
		assertThat(lines.get(2).getAmount(), comparesEqualTo(new BigDecimal("50"))); // payAmt of payment2

		Assert.assertTrue(BigDecimal.ZERO.compareTo(Services.get(IInvoiceDAO.class).retrieveOpenAmt(invoice)) == 0);
	}

	@Test
	public void test_AllocateForLine_FullyPaid_ExtraPayment()
	{

		final Timestamp firstDate = new Timestamp(1000000);

		final I_C_DocType type = db.newInstance(I_C_DocType.class);
		type.setDocBaseType(X_C_DocType.DOCBASETYPE_APInvoice);
		db.save(type);

		final I_C_BPartner partner = db.newInstance(I_C_BPartner.class);
		partner.setValue("partner1");
		db.save(partner);

		final I_C_Currency currencyEUR = db.newInstance(I_C_Currency.class);
		currencyEUR.setISO_Code("EUR");
		currencyEUR.setStdPrecision(2);
		currencyEUR.setIsEuro(true);
		db.save(currencyEUR);
		POJOWrapper.enableStrictValues(currencyEUR);

		final I_C_Invoice invoice = db.newInstance(I_C_Invoice.class);
		invoice.setGrandTotal(new BigDecimal("100"));
		invoice.setC_DocType_ID(type.getC_DocType_ID());
		invoice.setIsSOTrx(true);
		invoice.setC_BPartner_ID(partner.getC_BPartner_ID());
		invoice.setC_Currency_ID(currencyEUR.getC_Currency_ID());
		InterfaceWrapperHelper.setTrxName(invoice, Services.get(ITrxManager.class).createTrxName("trxName_test_AllocateForLine_FullyPaid_ExtraPayment", true));
		InterfaceWrapperHelper.save(invoice);

		final I_C_Payment payment1 = db.newInstance(I_C_Payment.class);
		payment1.setPayAmt(new BigDecimal("10"));
		payment1.setDateTrx(firstDate);
		payment1.setProcessed(true);
		payment1.setIsAllocated(false);
		payment1.setIsAutoAllocateAvailableAmt(true);
		payment1.setDocStatus(X_C_Payment.DOCSTATUS_Completed);
		payment1.setIsActive(true);
		payment1.setIsReceipt(true);
		payment1.setC_BPartner_ID(partner.getC_BPartner_ID());
		payment1.setC_Currency_ID(currencyEUR.getC_Currency_ID());
		InterfaceWrapperHelper.save(payment1);

		final I_C_Payment payment2 = db.newInstance(I_C_Payment.class);
		payment2.setPayAmt(new BigDecimal("40"));
		payment2.setDateTrx(firstDate);
		payment2.setProcessed(true);
		payment2.setIsAllocated(false);
		payment2.setIsAutoAllocateAvailableAmt(true);
		payment2.setDocStatus(X_C_Payment.DOCSTATUS_Completed);
		payment2.setIsActive(true);
		payment2.setIsReceipt(true);
		payment2.setC_BPartner_ID(partner.getC_BPartner_ID());
		payment2.setC_Currency_ID(currencyEUR.getC_Currency_ID());
		InterfaceWrapperHelper.save(payment2);

		final I_C_Payment payment3 = db.newInstance(I_C_Payment.class);
		payment3.setPayAmt(new BigDecimal("150"));
		payment3.setDateTrx(firstDate);
		payment3.setProcessed(true);
		payment3.setIsAllocated(false);
		payment3.setIsAutoAllocateAvailableAmt(true);
		payment3.setDocStatus(X_C_Payment.DOCSTATUS_Completed);
		payment3.setIsActive(true);
		payment3.setIsReceipt(true);
		payment3.setC_BPartner_ID(partner.getC_BPartner_ID());
		payment3.setC_Currency_ID(currencyEUR.getC_Currency_ID());
		InterfaceWrapperHelper.save(payment3);

		assertThat(Services.get(IInvoiceDAO.class).retrieveOpenAmt(invoice), comparesEqualTo(invoice.getGrandTotal())); // guard

		final I_C_AllocationHdr alloc = Services.get(IAllocationBL.class).autoAllocateAvailablePayments(invoice);
		assertThat(alloc, notNullValue());

		final List<I_C_AllocationLine> lines = Services.get(IAllocationDAO.class).retrieveLines(alloc);
		assertThat(lines.size(), is(3));

		assertThat(lines.get(0).getC_Invoice_ID(), is(invoice.getC_Invoice_ID()));
		assertThat(lines.get(0).getC_Payment_ID(), is(payment1.getC_Payment_ID()));
		assertThat(lines.get(0).getAmount(), comparesEqualTo(new BigDecimal("10"))); // payAmt of payment1

		assertThat(lines.get(1).getC_Invoice_ID(), is(invoice.getC_Invoice_ID()));
		assertThat(lines.get(1).getC_Payment_ID(), is(payment2.getC_Payment_ID()));
		assertThat(lines.get(1).getAmount(), comparesEqualTo(new BigDecimal("40"))); // payAmt of payment2

		assertThat(lines.get(2).getC_Invoice_ID(), is(invoice.getC_Invoice_ID()));
		assertThat(lines.get(2).getC_Payment_ID(), is(payment3.getC_Payment_ID()));
		assertThat(lines.get(2).getAmount(), comparesEqualTo(new BigDecimal("50"))); // partial payAmt of payment2

		assertThat(Services.get(IInvoiceDAO.class).retrieveOpenAmt(invoice), comparesEqualTo(BigDecimal.ZERO));
	}
}
