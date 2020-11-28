package de.metas.allocation.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

// import static org.hamcrest.Matchers.comparesEqualTo;
// import static org.hamcrest.Matchers.is;
// import static org.junit.Assert.assertThat;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_Payment;
import org.compiere.model.X_C_DocType;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.allocation.api.IAllocationBL;
import de.metas.allocation.api.IAllocationDAO;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocumentBL;
import de.metas.document.engine.impl.PlainDocumentBL;
import de.metas.interfaces.I_C_BPartner;
import de.metas.interfaces.I_C_DocType;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.money.CurrencyId;
import de.metas.util.Services;

@ExtendWith(AdempiereTestWatcher.class)
public class AllocationBLTest
{
	private ITrxManager trxManager;
	private IInvoiceDAO invoiceDAO;
	private IAllocationDAO allocationDAO;
	
	private IAllocationBL allocationBL;

	@BeforeEach
	public final void beforeTest()
	{
		AdempiereTestHelper.get().init();

		SpringContextHolder.registerJUnitBean(new CurrencyRepository());
		
		trxManager = Services.get(ITrxManager.class);
		invoiceDAO = Services.get(IInvoiceDAO.class);
		allocationDAO = Services.get(IAllocationDAO.class);

		allocationBL = Services.get(IAllocationBL.class);

		// Config PlainDocActionBL
		final PlainDocumentBL docActionBL = (PlainDocumentBL)Services.get(IDocumentBL.class);
		docActionBL.setDefaultProcessInterceptor(PlainDocumentBL.PROCESSINTERCEPTOR_CompleteDirectly);

		setThreadInheritedTrx();
	}

	private void setThreadInheritedTrx()
	{
		final String trxName = trxManager.createTrxName("dummy", true);
		trxManager.setThreadInheritedTrxName(trxName);
	}

	/**
	 * If there is no payment, autoAllocateAvailablePayments() should return null.
	 */
	@Test
	public void test_AllocateForLine_NoPayment()
	{
		final I_C_DocType type = newInstance(I_C_DocType.class);
		type.setDocBaseType(X_C_DocType.DOCBASETYPE_APInvoice);
		saveRecord(type);

		final I_C_BPartner partner = newInstance(I_C_BPartner.class);
		partner.setValue("partner1");
		saveRecord(partner);

		final CurrencyId currencyEUR = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setGrandTotal(new BigDecimal("100"));
		invoice.setC_DocType_ID(type.getC_DocType_ID());
		invoice.setIsSOTrx(true);
		invoice.setC_BPartner_ID(partner.getC_BPartner_ID());
		invoice.setC_Currency_ID(currencyEUR.getRepoId());
		invoiceDAO.save(invoice);

		final I_C_AllocationHdr alloc = allocationBL.autoAllocateAvailablePayments(invoice);
		assertThat(alloc).isNull();
	}

	@Test
	public void test_AllocateForLine_PartiallyPaid()
	{

		final Timestamp firstDate = new Timestamp(1000000);

		final I_C_DocType type = newInstance(I_C_DocType.class);
		type.setDocBaseType(X_C_DocType.DOCBASETYPE_APInvoice);
		InterfaceWrapperHelper.save(type);

		final I_C_BPartner partner = newInstance(I_C_BPartner.class);
		partner.setValue("partner1");
		InterfaceWrapperHelper.save(partner);

		final CurrencyId currencyEUR = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setGrandTotal(new BigDecimal("100"));
		invoice.setC_DocType_ID(type.getC_DocType_ID());
		invoice.setIsSOTrx(true);
		invoice.setC_BPartner_ID(partner.getC_BPartner_ID());
		invoice.setC_Currency_ID(currencyEUR.getRepoId());
		InterfaceWrapperHelper.save(invoice);

		final I_C_Payment payment1 = newInstance(I_C_Payment.class);
		payment1.setPayAmt(new BigDecimal("10"));
		payment1.setDateTrx(firstDate);
		payment1.setProcessed(true);
		payment1.setIsAllocated(false);
		payment1.setIsAutoAllocateAvailableAmt(true);
		payment1.setDocStatus(DocStatus.Completed.getCode());
		payment1.setIsActive(true);
		payment1.setIsReceipt(true);
		payment1.setC_BPartner_ID(partner.getC_BPartner_ID());
		payment1.setC_Currency_ID(currencyEUR.getRepoId());
		InterfaceWrapperHelper.save(payment1);

		Assert.assertTrue(invoice.getGrandTotal().compareTo(invoiceDAO.retrieveOpenAmt(invoice)) == 0);

		final I_C_AllocationHdr alloc = allocationBL.autoAllocateAvailablePayments(invoice);
		assertThat(alloc).isNotNull();

		final List<I_C_AllocationLine> lines = allocationDAO.retrieveLines(alloc);
		assertThat(lines).hasSize(1);

		assertThat(lines.get(0).getC_Invoice_ID()).isEqualTo(invoice.getC_Invoice_ID());
		assertThat(lines.get(0).getC_Payment_ID()).isEqualTo(payment1.getC_Payment_ID());
		assertThat(lines.get(0).getAmount()).isEqualByComparingTo("10"); // payAmt of payment1

		assertThat(invoiceDAO.retrieveOpenAmt(invoice))
				.isEqualByComparingTo(invoice.getGrandTotal().subtract(payment1.getPayAmt()));
	}

	@Test
	public void test_AllocateForLine_FullyPaid()
	{

		final Timestamp firstDate = new Timestamp(1000000);

		final I_C_DocType type = newInstance(I_C_DocType.class);
		type.setDocBaseType(X_C_DocType.DOCBASETYPE_APInvoice);
		InterfaceWrapperHelper.save(type);

		final I_C_BPartner partner = newInstance(I_C_BPartner.class);
		partner.setValue("partner1");
		InterfaceWrapperHelper.save(partner);

		final CurrencyId currencyEUR = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setGrandTotal(new BigDecimal("100"));
		invoice.setC_DocType_ID(type.getC_DocType_ID());
		invoice.setIsSOTrx(true);
		invoice.setC_BPartner_ID(partner.getC_BPartner_ID());
		invoice.setC_Currency_ID(currencyEUR.getRepoId());
		InterfaceWrapperHelper.save(invoice);

		final I_C_Payment payment1 = newInstance(I_C_Payment.class);
		payment1.setPayAmt(new BigDecimal("10"));
		payment1.setDateTrx(firstDate);
		payment1.setProcessed(true);
		payment1.setIsAllocated(false);
		payment1.setIsAutoAllocateAvailableAmt(true);
		payment1.setDocStatus(DocStatus.Completed.getCode());
		payment1.setIsActive(true);
		payment1.setIsReceipt(true);
		payment1.setC_BPartner_ID(partner.getC_BPartner_ID());
		payment1.setC_Currency_ID(currencyEUR.getRepoId());
		InterfaceWrapperHelper.save(payment1);

		final I_C_Payment payment2 = newInstance(I_C_Payment.class);
		payment2.setPayAmt(new BigDecimal("40"));
		payment2.setDateTrx(firstDate);
		payment2.setProcessed(true);
		payment2.setIsAllocated(false);
		payment2.setIsAutoAllocateAvailableAmt(true);
		payment2.setDocStatus(DocStatus.Completed.getCode());
		payment2.setIsActive(true);
		payment2.setIsReceipt(true);
		payment2.setC_BPartner_ID(partner.getC_BPartner_ID());
		payment2.setC_Currency_ID(currencyEUR.getRepoId());
		InterfaceWrapperHelper.save(payment2);

		final I_C_Payment payment3 = newInstance(I_C_Payment.class);
		payment3.setPayAmt(new BigDecimal("50"));
		payment3.setDateTrx(firstDate);
		payment3.setProcessed(true);
		payment3.setIsAllocated(false);
		payment3.setIsAutoAllocateAvailableAmt(true);
		payment3.setDocStatus(DocStatus.Completed.getCode());
		payment3.setIsActive(true);
		payment3.setIsReceipt(true);
		payment3.setC_BPartner_ID(partner.getC_BPartner_ID());
		payment3.setC_Currency_ID(currencyEUR.getRepoId());
		InterfaceWrapperHelper.save(payment3);

		Assert.assertTrue(invoice.getGrandTotal().compareTo(invoiceDAO.retrieveOpenAmt(invoice)) == 0);

		final I_C_AllocationHdr alloc = allocationBL.autoAllocateAvailablePayments(invoice);
		assertThat(alloc).isNotNull();

		final List<I_C_AllocationLine> lines = allocationDAO.retrieveLines(alloc);
		assertThat(lines).hasSize(3);

		assertThat(lines.get(0).getC_Invoice_ID()).isEqualTo(invoice.getC_Invoice_ID());
		assertThat(lines.get(0).getC_Payment_ID()).isEqualTo(payment1.getC_Payment_ID());
		assertThat(lines.get(0).getAmount()).isEqualByComparingTo("10"); // payAmt of payment1

		assertThat(lines.get(1).getC_Invoice_ID()).isEqualTo(invoice.getC_Invoice_ID());
		assertThat(lines.get(1).getC_Payment_ID()).isEqualTo(payment2.getC_Payment_ID());
		assertThat(lines.get(1).getAmount()).isEqualByComparingTo("40"); // payAmt of payment2

		assertThat(lines.get(2).getC_Invoice_ID()).isEqualTo(invoice.getC_Invoice_ID());
		assertThat(lines.get(2).getC_Payment_ID()).isEqualTo(payment3.getC_Payment_ID());
		assertThat(lines.get(2).getAmount()).isEqualByComparingTo("50"); // payAmt of payment2

		Assert.assertTrue(BigDecimal.ZERO.compareTo(invoiceDAO.retrieveOpenAmt(invoice)) == 0);
	}

	@Test
	public void test_AllocateForLine_FullyPaid_ExtraPayment()
	{

		final Timestamp firstDate = new Timestamp(1000000);

		final I_C_DocType type = newInstance(I_C_DocType.class);
		type.setDocBaseType(X_C_DocType.DOCBASETYPE_APInvoice);
		saveRecord(type);

		final I_C_BPartner partner = newInstance(I_C_BPartner.class);
		partner.setValue("partner1");
		saveRecord(partner);

		final CurrencyId currencyEUR = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setGrandTotal(new BigDecimal("100"));
		invoice.setC_DocType_ID(type.getC_DocType_ID());
		invoice.setIsSOTrx(true);
		invoice.setC_BPartner_ID(partner.getC_BPartner_ID());
		invoice.setC_Currency_ID(currencyEUR.getRepoId());
		InterfaceWrapperHelper.save(invoice);

		final I_C_Payment payment1 = newInstance(I_C_Payment.class);
		payment1.setPayAmt(new BigDecimal("10"));
		payment1.setDateTrx(firstDate);
		payment1.setProcessed(true);
		payment1.setIsAllocated(false);
		payment1.setIsAutoAllocateAvailableAmt(true);
		payment1.setDocStatus(DocStatus.Completed.getCode());
		payment1.setIsActive(true);
		payment1.setIsReceipt(true);
		payment1.setC_BPartner_ID(partner.getC_BPartner_ID());
		payment1.setC_Currency_ID(currencyEUR.getRepoId());
		InterfaceWrapperHelper.save(payment1);

		final I_C_Payment payment2 = newInstance(I_C_Payment.class);
		payment2.setPayAmt(new BigDecimal("40"));
		payment2.setDateTrx(firstDate);
		payment2.setProcessed(true);
		payment2.setIsAllocated(false);
		payment2.setIsAutoAllocateAvailableAmt(true);
		payment2.setDocStatus(DocStatus.Completed.getCode());
		payment2.setIsActive(true);
		payment2.setIsReceipt(true);
		payment2.setC_BPartner_ID(partner.getC_BPartner_ID());
		payment2.setC_Currency_ID(currencyEUR.getRepoId());
		InterfaceWrapperHelper.save(payment2);

		final I_C_Payment payment3 = newInstance(I_C_Payment.class);
		payment3.setPayAmt(new BigDecimal("150"));
		payment3.setDateTrx(firstDate);
		payment3.setProcessed(true);
		payment3.setIsAllocated(false);
		payment3.setIsAutoAllocateAvailableAmt(true);
		payment3.setDocStatus(DocStatus.Completed.getCode());
		payment3.setIsActive(true);
		payment3.setIsReceipt(true);
		payment3.setC_BPartner_ID(partner.getC_BPartner_ID());
		payment3.setC_Currency_ID(currencyEUR.getRepoId());
		InterfaceWrapperHelper.save(payment3);

		assertThat(invoiceDAO.retrieveOpenAmt(invoice)).isEqualByComparingTo(invoice.getGrandTotal()); // guard

		final I_C_AllocationHdr alloc = allocationBL.autoAllocateAvailablePayments(invoice);
		assertThat(alloc).isNotNull();

		final List<I_C_AllocationLine> lines = allocationDAO.retrieveLines(alloc);
		assertThat(lines).hasSize(3);

		assertThat(lines.get(0).getC_Invoice_ID()).isEqualTo(invoice.getC_Invoice_ID());
		assertThat(lines.get(0).getC_Payment_ID()).isEqualTo(payment1.getC_Payment_ID());
		assertThat(lines.get(0).getAmount()).isEqualByComparingTo("10"); // payAmt of payment1

		assertThat(lines.get(1).getC_Invoice_ID()).isEqualTo(invoice.getC_Invoice_ID());
		assertThat(lines.get(1).getC_Payment_ID()).isEqualTo(payment2.getC_Payment_ID());
		assertThat(lines.get(1).getAmount()).isEqualByComparingTo("40"); // payAmt of payment2

		assertThat(lines.get(2).getC_Invoice_ID()).isEqualTo(invoice.getC_Invoice_ID());
		assertThat(lines.get(2).getC_Payment_ID()).isEqualTo(payment3.getC_Payment_ID());
		assertThat(lines.get(2).getAmount()).isEqualByComparingTo("50"); // partial payAmt of payment2

		assertThat(invoiceDAO.retrieveOpenAmt(invoice)).isZero();
	}
}
