/**
 *
 */
package de.metas.payment.esr;

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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.ITrxRunConfig;
import org.adempiere.ad.trx.api.ITrxRunConfig.OnRunnableFail;
import org.adempiere.ad.trx.api.ITrxRunConfig.OnRunnableSuccess;
import org.adempiere.ad.trx.api.ITrxRunConfig.TrxPropagation;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.invoice.service.IInvoiceDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.IAttachmentBL;
import org.adempiere.service.IOrgDAO;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.service.ISysConfigDAO;
import org.adempiere.util.Services;
import org.adempiere.util.test.RepeatRule;
import org.adempiere.util.test.RepeatRule.Repeat;
import org.adempiere.util.trxConstraints.api.IOpenTrxBL;
import org.adempiere.util.trxConstraints.api.ITrxConstraintsBL;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_Payment;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import de.metas.adempiere.model.I_C_Currency;
import de.metas.adempiere.model.I_C_Invoice;
import de.metas.adempiere.service.IPeriodBL;
import de.metas.allocation.api.IAllocationDAO;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocActionBL;
import de.metas.document.refid.api.IReferenceNoDAO;
import de.metas.document.refid.model.I_C_ReferenceNo;
import de.metas.document.refid.model.I_C_ReferenceNo_Doc;
import de.metas.document.refid.model.I_C_ReferenceNo_Type;
import de.metas.interfaces.I_C_BPartner;
import de.metas.interfaces.I_C_DocType;
import de.metas.lock.api.ILockManager;
import de.metas.payment.api.IPaymentBL;
import de.metas.payment.api.IPaymentDAO;
import de.metas.payment.esr.api.IESRImportBL;
import de.metas.payment.esr.api.IESRImportDAO;
import de.metas.payment.esr.api.impl.ESRImportBL;
import de.metas.payment.esr.model.I_C_BP_BankAccount;
import de.metas.payment.esr.model.I_ESR_Import;
import de.metas.payment.esr.model.I_ESR_ImportLine;
import de.metas.payment.esr.model.X_ESR_ImportLine;
import de.metas.payment.esr.spi.impl.MoneyTransferedBackESRActionHandler;
import de.metas.payment.esr.spi.impl.UnableToAssignESRActionHandler;
import de.metas.payment.esr.spi.impl.WithCurrenttInvoiceESRActionHandler;
import de.metas.payment.esr.spi.impl.WithNextInvoiceESRActionHandler;
import de.metas.payment.esr.spi.impl.WriteoffESRActionHandler;

/**
 * This class tests the entire module of importing ESR
 *
 * @author cg
 *
 */
public class ESRImportTest extends ESRTestBase
{

		/**
	 * This test emulates a real-world case of a perfect match
	 * <ul>
	 * <li>invoice 50
	 * <li>one ESR line with amount 50
	 * <li>perfect match: partner, invoice, amount
	 * <li>payment created with value 50 and allocated with 50;
	 * <li>esr line processed
	 * </ul>
	 */
	@Test
	public void testStandardCase_T01()
	{

		final String grandTotal = "50";
		final String esrLineText = "01201067789300000001060012345600654321400000050009072  030014040914041014041100001006800000000000090                          ";
		final String refNo = "300000001060012345600654321";
		final String partnerValue = "123456";
		final String invDocNo = "654321";
		final String ESR_Rendered_AccountNo = "01-067789-3";

		final I_ESR_ImportLine esrImportLine = setupESR_ImportLine(grandTotal, esrLineText, refNo, ESR_Rendered_AccountNo, partnerValue, invDocNo, false, false);
		final I_ESR_Import esrImport = esrImportLine.getESR_Import();

		final ITrxRunConfig trxRunConfig = Services.get(ITrxManager.class).createTrxRunConfig(TrxPropagation.REQUIRES_NEW, OnRunnableSuccess.COMMIT, OnRunnableFail.ASK_RUNNABLE);
		final ESRImportBL esrBL = new ESRImportBL();

		esrBL.process(esrImport, trxRunConfig);

		// check import line
		InterfaceWrapperHelper.refresh(esrImportLine, true);
		assertThat(esrImportLine.isValid(), is(true));
		assertThat(esrImportLine.isProcessed(), is(true));
		assertThat(esrImportLine.getESR_Payment_Action(), is(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Fit_Amounts));
		assertThat(esrImportLine.getESR_Document_Status(), is(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_TotallyMatched));
		assertThat(esrImportLine.getErrorMsg(), nullValue());

		// check invoice
		InterfaceWrapperHelper.refresh(getC_Invoice(), true);
		assertThat(getC_Invoice().isPaid(), is(true));

		// check the created payments
		final I_C_Payment  esrLine1Payment = esrImportLine.getC_Payment();
		InterfaceWrapperHelper.refresh(esrLine1Payment, true);
		assertThat(esrLine1Payment.getPayAmt(), comparesEqualTo(new BigDecimal(50)));
		assertThat(esrLine1Payment.getC_Invoice_ID(), is(esrImportLine.getC_Invoice_ID()));
		assertThat(esrLine1Payment.isAllocated(), is(true));

		// check allocations
		List<I_C_AllocationLine> allocLines = Services.get(IAllocationDAO.class).retrieveAllocationLines(esrImportLine.getC_Invoice());
		assertThat(allocLines.size(), is(1));
		assertThat(allocLines.get(0).getAmount(), comparesEqualTo(new BigDecimal(50)));

	}

	/**
	 * This test emulates a real-world case of double payment of same invoice in one file
	 * <ul>
	 * <li>invoice 25
	 * <li>one ESR line with amount 25
	 * <li>other ESR line with amount 25
	 * <li>first line will be perfect match: partner, invoice, amount
	 * <li>payment created with value 25 and allocated with 25;
	 * <li>esr line processed
	 * <li>second line, will be an overpayment
	 * <li>payment will be created but not allocated
	 * </ul>
	 */
	@Test
	public void testDoublePayment_T02()
	{
		// org
		final I_AD_Org org = getAD_Org();
		org.setValue("106");
		InterfaceWrapperHelper.save(org);

		// partner
		final I_C_BPartner partner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class, contextProvider);
		partner.setValue("123456");
		partner.setAD_Org_ID(org.getAD_Org_ID());
		InterfaceWrapperHelper.save(partner);

		final I_C_ReferenceNo_Type refNoType = InterfaceWrapperHelper.newInstance(I_C_ReferenceNo_Type.class, contextProvider);
		refNoType.setName("InvoiceReference");
		InterfaceWrapperHelper.save(refNoType);

		// bank account
		final I_C_BP_BankAccount account = InterfaceWrapperHelper.newInstance(I_C_BP_BankAccount.class, contextProvider);
		account.setIsEsrAccount(true);
		account.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
		account.setAD_User_ID(Env.getAD_User_ID(getCtx()));
		account.setESR_RenderedAccountNo("01-067789-3");
		InterfaceWrapperHelper.save(account);

		// currency
		final I_C_Currency currencyEUR = InterfaceWrapperHelper.newInstance(I_C_Currency.class, contextProvider);
		currencyEUR.setISO_Code("EUR");
		currencyEUR.setStdPrecision(2);
		currencyEUR.setIsEuro(true);
		InterfaceWrapperHelper.save(currencyEUR);
		POJOWrapper.enableStrictValues(currencyEUR);

		// doc type
		final I_C_DocType type = InterfaceWrapperHelper.newInstance(I_C_DocType.class, contextProvider);
		type.setDocBaseType(X_C_DocType.DOCBASETYPE_ARInvoice);
		InterfaceWrapperHelper.save(type);

		// invoice
		final BigDecimal invoiceGrandTotal = new BigDecimal("25");
		final I_C_Invoice inv = InterfaceWrapperHelper.newInstance(I_C_Invoice.class, contextProvider);
		inv.setAD_Org_ID(org.getAD_Org_ID());
		inv.setGrandTotal(invoiceGrandTotal);
		inv.setC_BPartner_ID(partner.getC_BPartner_ID());
		inv.setDocumentNo("654321");
		inv.setAD_Org_ID(org.getAD_Org_ID());
		inv.setC_DocType_ID(type.getC_DocType_ID());
		inv.setC_Currency_ID(currencyEUR.getC_Currency_ID());
		inv.setProcessed(true);
		inv.setIsSOTrx(true);
		InterfaceWrapperHelper.save(inv);

		// reference no
		final I_C_ReferenceNo referenceNo = InterfaceWrapperHelper.newInstance(I_C_ReferenceNo.class, contextProvider);
		referenceNo.setReferenceNo("300000001060012345600654321");
		referenceNo.setC_ReferenceNo_Type(refNoType);
		referenceNo.setIsManual(true);
		InterfaceWrapperHelper.save(referenceNo);

		// reference nodoc
		final I_C_ReferenceNo_Doc esrReferenceNumberDocument = InterfaceWrapperHelper.newInstance(I_C_ReferenceNo_Doc.class, contextProvider);
		esrReferenceNumberDocument.setAD_Table_ID(Services.get(IADTableDAO.class).retrieveTableId(I_C_Invoice.Table_Name));
		esrReferenceNumberDocument.setRecord_ID(inv.getC_Invoice_ID());
		esrReferenceNumberDocument.setC_ReferenceNo(referenceNo);
		InterfaceWrapperHelper.save(esrReferenceNumberDocument);

		// esr line
		final List<I_ESR_ImportLine> lines = new ArrayList<I_ESR_ImportLine>();
		// first line
		final String esrLineText = "01201067789300000001060012345600654321400000025009072  030014040914041014041100001006800000000000090                          ";
		final I_ESR_ImportLine esrImportLine1 = createImportLine(esrLineText);
		esrImportLine1.setC_BP_BankAccount(account);
		esrImportLine1.setAD_Org_ID(org.getAD_Org_ID());
		InterfaceWrapperHelper.save(esrImportLine1);

		final I_ESR_Import esrImport = esrImportLine1.getESR_Import();

		// second line
		final I_ESR_ImportLine esrImportLine2 = createImportLine(esrLineText);
		esrImportLine2.setC_BP_BankAccount(account);
		esrImportLine2.setAD_Org_ID(org.getAD_Org_ID());
		esrImportLine2.setESR_Import(esrImport);
		InterfaceWrapperHelper.save(esrImportLine2);
		lines.add(esrImportLine2);

		esrImport.setC_BP_BankAccount(account);
		InterfaceWrapperHelper.save(esrImport);

		// start processing

		final ITrxRunConfig trxRunConfig = Services.get(ITrxManager.class).createTrxRunConfig(TrxPropagation.REQUIRES_NEW, OnRunnableSuccess.COMMIT, OnRunnableFail.ASK_RUNNABLE);
		final ESRImportBL esrBL = new ESRImportBL();
		esrBL.process(esrImport, trxRunConfig);

		// check first import line
		InterfaceWrapperHelper.refresh(esrImportLine1, true);
		assertThat(esrImportLine1.isValid(), is(true));
		assertThat(esrImportLine1.isProcessed(), is(true));
		assertThat(esrImportLine1.getESR_Payment_Action(), is(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Fit_Amounts));
		assertThat(esrImportLine1.getESR_Document_Status(), is(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_TotallyMatched));
		assertThat(esrImportLine1.getErrorMsg(), nullValue());
		assertThat(esrImportLine1.getESR_Invoice_Openamt(), comparesEqualTo(new BigDecimal(0)));

		// check second import line
		InterfaceWrapperHelper.refresh(esrImportLine2, true);
		assertThat(esrImportLine2.isValid(), is(true));
		assertThat(esrImportLine2.isProcessed(), is(false));
		assertThat(esrImportLine2.getESR_Payment_Action(), nullValue());
		assertThat(esrImportLine2.getESR_Document_Status(), is(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_PartiallyMatched));
		assertThat(esrImportLine2.getESR_Invoice_Openamt(), comparesEqualTo(new BigDecimal(-25)));

		// check invoice
		assertThat(esrImportLine1.getC_Invoice().isPaid(), is(true));

		// check the created payments - first payment
		final I_C_Payment esrLine1Payment1 = esrImportLine1.getC_Payment();
		InterfaceWrapperHelper.refresh(esrLine1Payment1, true);
		assertThat(esrLine1Payment1.getPayAmt(), comparesEqualTo(new BigDecimal(25)));
		assertThat(esrLine1Payment1.getC_Invoice_ID(), is(esrImportLine1.getC_Invoice_ID()));
		assertThat(esrLine1Payment1.isAllocated(), is(true));

		// check the created payments - second payment
		final I_C_Payment esrLine1Payment2 = esrImportLine2.getC_Payment();
		InterfaceWrapperHelper.refresh(esrLine1Payment2, true);
		assertThat(esrLine1Payment2.getPayAmt(), comparesEqualTo(new BigDecimal(25)));
		assertThat(esrLine1Payment2.getC_Invoice_ID(), is(-1));
		assertThat(esrLine1Payment2.isAllocated(), is(false));

		// check allocations - first payment
		List<I_C_AllocationLine> allocLines = Services.get(IAllocationDAO.class).retrieveAllocationLines(esrImportLine1.getC_Invoice());
		assertThat(allocLines.size(), is(1));
		assertThat(allocLines.get(0).getAmount(), comparesEqualTo(new BigDecimal(25)));

		// check allocations - second payment
		allocLines = Services.get(IPaymentDAO.class).retrieveAllocationLines(esrLine1Payment2);
		assertThat(allocLines.size(), is(0));

	}

	/**
	 * This test emulates a real-world case of a already paid invoice
	 * <ul>
	 * <li>invoice already paid with amount of 50
	 * <li>one ESR line with amount 50
	 * <li>payment created with value 50 and not allocated
	 * <li>change the invoice to an unpaid invoice
	 * <li>complete esr -> allocation created and line with amount 50 allocated against the newly set invoice
	 * </ul>
	 */
	@Test
	public void testAlreadyPaidInvoice_T03()
	{
		Services.get(ILockManager.class);

		final String grandTotal = "50";
		final String esrLineText = "01201067789300000001060012345600654321400000050009072  030014040914041014041100001006800000000000090                          ";
		final String refNo = "300000001060012345600654321";
		final String partnerValue = "123456";
		final String invDocNo = "654321";
		final String ESR_Rendered_AccountNo = "01-067789-3";

		final I_ESR_ImportLine esrImportLine = setupESR_ImportLine(grandTotal, esrLineText, refNo, ESR_Rendered_AccountNo, partnerValue, invDocNo, true, true);
		final I_ESR_Import esrImport = esrImportLine.getESR_Import();


		// start processing
		final ITrxRunConfig trxRunConfig = trxManager.createTrxRunConfig(TrxPropagation.REQUIRES_NEW, OnRunnableSuccess.COMMIT, OnRunnableFail.ASK_RUNNABLE);
		final ESRImportBL esrBL = new ESRImportBL();
		esrBL.process(esrImport, trxRunConfig);

		// check import line
		InterfaceWrapperHelper.refresh(esrImportLine, true);
		assertThat(esrImportLine.isValid(), is(false));
		assertThat(esrImportLine.isProcessed(), is(false));
		assertThat(esrImportLine.getESR_Payment_Action(), nullValue());
		assertThat(esrImportLine.getESR_Document_Status(), is(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_PartiallyMatched));
		assertThat(esrImportLine.getESR_Invoice_Openamt(), comparesEqualTo(new BigDecimal(-50)));
		assertThat(esrImportLine.getErrorMsg(), Matchers.notNullValue());

		// check the created payments
		I_C_Payment esrLine1Payment = esrImportLine.getC_Payment();
		assertThat(esrLine1Payment.getPayAmt(), comparesEqualTo(new BigDecimal(50)));
		assertThat(esrLine1Payment.getC_Invoice_ID(), is(-1));
		assertThat(esrLine1Payment.isAllocated(), is(false));

		// shall be a previous allocation
		List<I_C_AllocationLine> allocLines = Services.get(IAllocationDAO.class).retrieveAllocationLines(esrImportLine.getC_Invoice());
		assertThat(allocLines.size(), is(1));

		// create new invoice
		final I_C_Invoice inv1 = InterfaceWrapperHelper.newInstance(I_C_Invoice.class, contextProvider);
		inv1.setGrandTotal(new BigDecimal(50));
		inv1.setC_BPartner_ID(esrImportLine.getC_BPartner_ID());
		inv1.setDocumentNo("654322");
		inv1.setAD_Org_ID(esrImportLine.getAD_Org_ID());
		inv1.setC_DocType_ID(esrImportLine.getC_Invoice().getC_DocType_ID());
		inv1.setC_Currency_ID(esrImportLine.getC_Invoice().getC_Currency_ID());
		inv1.setIsSOTrx(true);
		inv1.setProcessed(true);
		InterfaceWrapperHelper.save(inv1);

		// Registrate payment action handlers.
		esrBL.registerActionHandler(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Allocate_Payment_With_Current_Invoice, new WithCurrenttInvoiceESRActionHandler());

		// assign the new invoice to the esrline
		InterfaceWrapperHelper.refresh(esrLine1Payment, true);
		esrImportLine.setC_Invoice_ID(inv1.getC_Invoice_ID());
		InterfaceWrapperHelper.save(esrImportLine);
		Services.get(IESRImportBL.class).setInvoice(esrImportLine, inv1);
		InterfaceWrapperHelper.save(esrImportLine);

		esrBL.complete(esrImport, "Complete", trxRunConfig);


		// check import line
		InterfaceWrapperHelper.refresh(esrImportLine, true);
		assertThat(esrImportLine.isValid(), is(false));
		assertThat(esrImportLine.isProcessed(), is(true));
		assertThat(esrImportLine.getESR_Payment_Action(), is(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Allocate_Payment_With_Current_Invoice));
		assertThat(esrImportLine.getESR_Document_Status(), is(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_PartiallyMatched));

		// check if invoice is paid
		InterfaceWrapperHelper.refresh(inv1, true);
		assertThat(inv1.isPaid(), is(true));

		// check the created payments
		// reload payment
		esrLine1Payment = esrImportLine.getC_Payment();
		InterfaceWrapperHelper.refresh(esrLine1Payment, true);
		assertThat(esrLine1Payment.getPayAmt(), comparesEqualTo(new BigDecimal(50)));
		assertThat(esrLine1Payment.isAllocated(), is(true));
		// shall be one allocation
		allocLines = Services.get(IAllocationDAO.class).retrieveAllocationLines(esrImportLine.getC_Invoice());
		assertThat(allocLines.size(), is(1));
		assertThat(allocLines.get(0).getAmount(), comparesEqualTo(new BigDecimal(50)));

		// esr processed
		assertThat(esrImport.isProcessed(), is(true));

	}

	/**
	 * This test emulates a real-world case of a payment lower then the invoice
	 * <ul>
	 * <li>One ESR Payment lower than invoice amount
	 * <li>invoice with amount of 50
	 * <li>one ESR line with amount 25
	 * <li>esr line has payment with amount of 25; the payment is not allocated
	 * <li>choose action W_Betrag abschreiben and complete esr
	 * <li>Payment is marked as IsOverUnderPayment; OverUnderAmt is -25;
	 * <li>An allocation of 25 for the invoice was created
	 * <li>An allocation with WriteOff amount of 25 was created
	 * <li>ESR processed
	 * </ul>
	 */
	@Test
	public void testPaymentLowerThenInvoice_T04()
	{

		final String grandTotal = "50";
		final String esrLineText = "01201067789300000001060012345600654321400000025009072  030014040914041014041100001006800000000000090                          ";
		final String refNo = "300000001060012345600654321";
		final String partnerValue = "123456";
		final String invDocNo = "654321";
		final String ESR_Rendered_AccountNo = "01-067789-3";

		final I_ESR_ImportLine esrImportLine = setupESR_ImportLine(grandTotal, esrLineText, refNo, ESR_Rendered_AccountNo, partnerValue, invDocNo, false, false);
		final I_ESR_Import esrImport = esrImportLine.getESR_Import();

		// start processing
		final ITrxRunConfig trxRunConfig = Services.get(ITrxManager.class).createTrxRunConfig(TrxPropagation.REQUIRES_NEW, OnRunnableSuccess.COMMIT, OnRunnableFail.ASK_RUNNABLE);
		final ESRImportBL esrBL = new ESRImportBL();
		esrBL.process(esrImport, trxRunConfig);

		// check import line
		InterfaceWrapperHelper.refresh(esrImportLine, true);
		assertThat(esrImportLine.isValid(), is(true));
		assertThat(esrImportLine.isProcessed(), is(false));
		assertThat(esrImportLine.getESR_Payment_Action(), nullValue());
		assertThat(esrImportLine.getESR_Document_Status(), is(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_TotallyMatched));

		// check the created payments
		I_C_Payment esrLine1Payment = esrImportLine.getC_Payment();
		assertThat(esrLine1Payment.getPayAmt(), comparesEqualTo(new BigDecimal(25)));
		assertThat(esrLine1Payment.getC_Invoice_ID(), is(-1));
		assertThat(esrLine1Payment.isAllocated(), is(false));

		// Registrate payment action handlers.
		esrBL.registerActionHandler(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Write_Off_Amount, new WriteoffESRActionHandler());
		esrImportLine.setESR_Payment_Action(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Write_Off_Amount);
		InterfaceWrapperHelper.save(esrImportLine);

		esrBL.complete(esrImport, "Complete", trxRunConfig);

		InterfaceWrapperHelper.refresh(getC_Invoice(), true);
		assertThat(getC_Invoice().isPaid(), is(true));

		// check import line
		InterfaceWrapperHelper.refresh(esrImportLine, true);
		assertThat(esrImportLine.isProcessed(), is(true));
		assertThat(esrImportLine.getC_Invoice_ID(), is(getC_Invoice().getC_Invoice_ID()));
		assertThat(esrImportLine.getESR_Payment_Action(), is(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Write_Off_Amount));
		assertThat(esrImportLine.getESR_Document_Status(), is(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_TotallyMatched));
		assertThat(esrImportLine.getESR_Invoice_Openamt(), comparesEqualTo(new BigDecimal(25)));

		// check the created payments
		esrLine1Payment = esrImportLine.getC_Payment();
		assertThat(esrLine1Payment.getPayAmt(), comparesEqualTo(new BigDecimal(25)));
		assertThat(esrLine1Payment.getC_Invoice_ID(), is(getC_Invoice().getC_Invoice_ID()));
		assertThat(esrLine1Payment.isAllocated(), is(true));
		assertThat(esrLine1Payment.getOverUnderAmt(), comparesEqualTo(new BigDecimal(-25)));

		final List<I_C_AllocationLine> allocLines = Services.get(IAllocationDAO.class).retrieveAllocationLines(esrImportLine.getC_Invoice());
		assertThat(allocLines.size(), is(2));
		assertThat(allocLines.get(0).getAmount(), comparesEqualTo(new BigDecimal(25)));
		assertThat(allocLines.get(0).getC_Invoice_ID(), is(getC_Invoice().getC_Invoice_ID()));
		assertThat(allocLines.get(1).getWriteOffAmt(), comparesEqualTo(new BigDecimal(25)));
		assertThat(allocLines.get(1).getC_Invoice_ID(), Matchers.notNullValue());

		// esr processed
		InterfaceWrapperHelper.refresh(esrImport, true);
		assertThat(esrImport.isProcessed(), is(true));
	}

	/**
	 * This test emulates a real-world case of a payment higher then the invoice
	 * <ul>
	 * <li>One ESR Payment lower than invoice amount
	 * <li>invoice with amount of 50
	 * <li>one ESR line with amount 70
	 * <li>esr line has payment with amount of 70; the payment is not allocated
	 * <li>choose action <code>X_ESR_ImportLine.ESR_PAYMENT_ACTION_Allocate_Payment_With_Next_Invoice</code> and complete esr
	 * <li>Payment is marked as IsOverUnderPayment; OverUnderAmt is 20;
	 * <li>An allocation of 50 for the invoice was created
	 * <li>The flag isAutoAllocateAvailableAmt was checked
	 * <li>ESR processed
	 * </ul>
	 */
	@Test
	public void testPaymentHigherThenInvoice_Action_N_T05()
	{
		final String grandTotal = "50";
		final String esrLineText = "01201067789300000001060012345600654321400000070009072  030014040914041014041100001006800000000000090                          ";
		final String refNo = "300000001060012345600654321";
		final String partnerValue = "123456";
		final String invDocNo = "654321";
		final String ESR_Rendered_AccountNo = "01-067789-3";

		final I_ESR_ImportLine esrImportLine = setupESR_ImportLine(grandTotal, esrLineText, refNo, ESR_Rendered_AccountNo, partnerValue, invDocNo, false, false);
		final I_ESR_Import esrImport = esrImportLine.getESR_Import();

		// start processing

		final ITrxRunConfig trxRunConfig = Services.get(ITrxManager.class).createTrxRunConfig(TrxPropagation.REQUIRES_NEW, OnRunnableSuccess.COMMIT, OnRunnableFail.ASK_RUNNABLE);
		final ESRImportBL esrBL = new ESRImportBL();
		esrBL.process(esrImport, trxRunConfig);

		// check import line
		InterfaceWrapperHelper.refresh(esrImportLine, true);
		assertThat(esrImportLine.isValid(), is(true));
		assertThat(esrImportLine.isProcessed(), is(false));
		assertThat(esrImportLine.getESR_Payment_Action(), nullValue());
		assertThat(esrImportLine.getESR_Document_Status(), is(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_TotallyMatched));

		// check the created payments
		final I_C_Payment esrLine1Payment = esrImportLine.getC_Payment();
		assertThat(esrLine1Payment.getPayAmt(), comparesEqualTo(new BigDecimal(70)));
		assertThat(esrLine1Payment.getC_Invoice_ID(), is(-1));
		assertThat(esrLine1Payment.isAllocated(), is(false));

		// Registrate payment action handlers.
		esrBL.registerActionHandler(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Allocate_Payment_With_Next_Invoice, new WithNextInvoiceESRActionHandler());
		esrImportLine.setESR_Payment_Action(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Allocate_Payment_With_Next_Invoice);
		InterfaceWrapperHelper.save(esrImportLine);

		esrBL.complete(esrImport, "Complete", trxRunConfig);

		// check the invoice
		InterfaceWrapperHelper.refresh(getC_Invoice(), true);
		assertThat(getC_Invoice().isPaid(), is(true));

		// check import line
		InterfaceWrapperHelper.refresh(esrImportLine, true);
		assertThat(esrImportLine.isProcessed(), is(true));
		assertThat(esrImportLine.getESR_Payment_Action(), is(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Allocate_Payment_With_Next_Invoice));
		assertThat(esrImportLine.getESR_Document_Status(), is(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_TotallyMatched));

		// check the created payments
		InterfaceWrapperHelper.refresh(esrLine1Payment, true);
		assertThat(esrLine1Payment.getC_Invoice_ID(), is(getC_Invoice().getC_Invoice_ID()));
		assertThat(esrLine1Payment.getPayAmt(), comparesEqualTo(new BigDecimal(70)));
		assertThat(esrLine1Payment.getOverUnderAmt(), comparesEqualTo(new BigDecimal(20)));
		assertThat(esrLine1Payment.isAutoAllocateAvailableAmt(), is(true));
		assertThat(esrLine1Payment.isAllocated(), is(false));

		// alocations
		List<I_C_AllocationLine> allocLines = Services.get(IAllocationDAO.class).retrieveAllocationLines(esrImportLine.getC_Invoice());
		assertThat(allocLines.size(), is(1));
		assertThat(allocLines.get(0).getAmount(), comparesEqualTo(new BigDecimal(50)));

		// esr processed
		InterfaceWrapperHelper.refresh(esrImport, true);
		assertThat(esrImport.isProcessed(), is(true));
	}

	/**
	 * This test emulates a real-world case of a payment higher then the invoice
	 * <ul>
	 * <li>One ESR Payment lower than invoice amount
	 * <li>invoice with amount of 50
	 * <li>one ESR line with amount 70
	 * <li>esr line has payment with amount of 70; the payment is not allocated
	 * <li>choose action <code>X_ESR_ImportLine.ESR_PAYMENT_ACTION_Money_Was_Transfered_Back_to_Partner</code> and complete esr
	 * <li>Payment is fully allocated
	 * <li>An allocation of 70 for the invoice was created
	 * <li>ESR processed
	 * </ul>
	 */
	@Test
	public void testPaymentHigherThenInvoice_Action_B_T05()
	{
		final String grandTotal = "50";
		final String esrLineText = "01201067789300000001060012345600654321400000070009072  030014040914041014041100001006800000000000090                          ";
		final String refNo = "300000001060012345600654321";
		final String partnerValue = "123456";
		final String invDocNo = "654321";
		final String ESR_Rendered_AccountNo = "01-067789-3";

		final I_ESR_ImportLine esrImportLine = setupESR_ImportLine(grandTotal, esrLineText, refNo, ESR_Rendered_AccountNo, partnerValue, invDocNo, false, false);
		final I_ESR_Import esrImport = esrImportLine.getESR_Import();

		// start processing

		final ITrxRunConfig trxRunConfig = trxManager.createTrxRunConfig(TrxPropagation.REQUIRES_NEW, OnRunnableSuccess.COMMIT, OnRunnableFail.ASK_RUNNABLE);
		final ESRImportBL esrBL = new ESRImportBL();
		esrBL.process(esrImport, trxRunConfig);

		// check import line
		InterfaceWrapperHelper.refresh(esrImportLine, true);
		assertThat(esrImportLine.isValid(), is(true));
		assertThat(esrImportLine.isProcessed(), is(false));
		assertThat(esrImportLine.getESR_Payment_Action(), nullValue());
		assertThat(esrImportLine.getESR_Document_Status(), is(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_TotallyMatched));
		assertThat(esrImportLine.getC_Payment(), notNullValue());

		// check the created payments
		final I_C_Payment esrLine1Payment = esrImportLine.getC_Payment();
		assertThat(esrLine1Payment.getPayAmt(), comparesEqualTo(new BigDecimal(70)));
		assertThat(esrLine1Payment.getC_Invoice_ID(), is(-1));
		assertThat(esrLine1Payment.isAllocated(), is(false));

		// allocations
		List<I_C_AllocationLine> allocLines = Services.get(IAllocationDAO.class).retrieveAllocationLines(esrImportLine.getC_Invoice());
		assertThat(allocLines.size(), is(0));

		// Registrate payment action handlers.
		esrBL.registerActionHandler(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Money_Was_Transfered_Back_to_Partner, new MoneyTransferedBackESRActionHandler());
		esrImportLine.setESR_Payment_Action(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Money_Was_Transfered_Back_to_Partner);
		InterfaceWrapperHelper.save(esrImportLine);

		// create doc type credit memo - need for B action
		final I_C_DocType type = InterfaceWrapperHelper.newInstance(I_C_DocType.class, contextProvider);
		type.setDocBaseType(X_C_DocType.DOCBASETYPE_ARCreditMemo);
		InterfaceWrapperHelper.save(type);

		esrBL.complete(esrImport, "Complete", trxRunConfig);

		// check the invoice
		InterfaceWrapperHelper.refresh(getC_Invoice(), true);
		assertThat(getC_Invoice().isPaid(), is(true));

		// check import line
		InterfaceWrapperHelper.refresh(esrImportLine, true);
		assertThat(esrImportLine.isProcessed(), is(true));
		assertThat(esrImportLine.getESR_Payment_Action(), is(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Money_Was_Transfered_Back_to_Partner));
		assertThat(esrImportLine.getESR_Document_Status(), is(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_TotallyMatched));

		// check the created payments
		InterfaceWrapperHelper.refresh(esrLine1Payment, true);
		assertThat(esrLine1Payment.getPayAmt(), comparesEqualTo(new BigDecimal(70)));
		assertThat(esrLine1Payment.getOverUnderAmt(), comparesEqualTo(new BigDecimal(20)));
		assertThat(esrLine1Payment.isAllocated(), is(true));

		// allocations
		allocLines = Services.get(IAllocationDAO.class).retrieveAllocationLines(esrImportLine.getC_Invoice());
		assertThat(allocLines.size(), is(1));
		assertThat(allocLines.get(0).getAmount(), comparesEqualTo(new BigDecimal(50)));

		allocLines = Services.get(IPaymentDAO.class).retrieveAllocationLines(esrLine1Payment);
		assertThat(allocLines.size(), is(2));
		assertThat(allocLines.get(0).getAmount(), comparesEqualTo(new BigDecimal(50)));
		assertThat(allocLines.get(1).getAmount(), comparesEqualTo(new BigDecimal(20)));

		// esr processed
		InterfaceWrapperHelper.refresh(esrImport, true);
		assertThat(esrImport.isProcessed(), is(true));
	}

	/**
	 * This test emulates a real-world case of a completely not found reference
	 * <ul>
	 * <li>One ESR Payment with amount of 50
	 * <li>invoice not matched, partner not matched
	 * <li>no payment is created
	 * <li>choose and complete the esr
	 * <li>A payment is created with amount of 50
	 * <li>ESR processed
	 * </ul>
	 */
	@Test
	public void testReferenceCompletelyNotFound_T06()
	{
		// org
		final I_AD_Org org = InterfaceWrapperHelper.newInstance(I_AD_Org.class, contextProvider);
		org.setValue("106");
		InterfaceWrapperHelper.save(org);

		final I_C_ReferenceNo_Type refNoType = InterfaceWrapperHelper.newInstance(I_C_ReferenceNo_Type.class, contextProvider);
		refNoType.setName("InvoiceReference");
		InterfaceWrapperHelper.save(refNoType);

		// currency
		final I_C_Currency currencyEUR = InterfaceWrapperHelper.newInstance(I_C_Currency.class, contextProvider);
		currencyEUR.setISO_Code("EUR");
		currencyEUR.setStdPrecision(2);
		currencyEUR.setIsEuro(true);
		InterfaceWrapperHelper.save(currencyEUR);
		POJOWrapper.enableStrictValues(currencyEUR);

		// bank account
		final I_C_BP_BankAccount account = InterfaceWrapperHelper.newInstance(I_C_BP_BankAccount.class, contextProvider);
		account.setIsEsrAccount(true);
		account.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
		account.setAD_User_ID(Env.getAD_User_ID(getCtx()));
		account.setESR_RenderedAccountNo("01-067789-3");
		account.setC_Currency_ID(currencyEUR.getC_Currency_ID());
		InterfaceWrapperHelper.save(account);

		// esr line
		final List<I_ESR_ImportLine> lines = new ArrayList<I_ESR_ImportLine>();
		final String esrLineText = "01201067789300000001060000000000000000400000050009072  030014040914041014041100001006800000000000090                          ";
		final I_ESR_ImportLine esrImportLine = createImportLine(esrLineText);
		esrImportLine.setAD_Org_ID(org.getAD_Org_ID());
		esrImportLine.setC_BP_BankAccount(account);
		esrImportLine.setAccountNo(account.getAccountNo());
		InterfaceWrapperHelper.save(esrImportLine);

		I_ESR_Import esrImport = esrImportLine.getESR_Import();
		esrImport.setC_BP_BankAccount_ID(account.getC_BP_BankAccount_ID());
		InterfaceWrapperHelper.save(esrImportLine);

		// start processing
		final ITrxRunConfig trxRunConfig = Services.get(ITrxManager.class).createTrxRunConfig(TrxPropagation.REQUIRES_NEW, OnRunnableSuccess.COMMIT, OnRunnableFail.ASK_RUNNABLE);
		final ESRImportBL esrBL = new ESRImportBL();

		esrBL.process(esrImport, trxRunConfig);

		// this needs to be here because happens when saving, while importing the line
		// process emulates the importing of the file and at the end the line is saved when the default values are set
		InterfaceWrapperHelper.refresh(esrImportLine, true);
		esrImportLine.setESR_IsManual_ReferenceNo(true); // is by default on 'Y' in db
		InterfaceWrapperHelper.save(esrImportLine);

		// check import line
		assertThat(esrImportLine.isValid(), is(false));
		assertThat(esrImportLine.isProcessed(), is(false));
		assertThat(esrImportLine.getESR_Payment_Action(), nullValue());
		assertThat(esrImportLine.getESR_Document_Status(), is(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_PartiallyMatched));
		assertThat(esrImportLine.getErrorMsg(), Matchers.notNullValue());

		// check the created payments
		I_C_Payment esrLine1Payment = esrImportLine.getC_Payment();
		assertThat(esrLine1Payment, nullValue());

		// partner
		final I_C_BPartner partner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class, contextProvider);
		partner.setValue("123456");
		InterfaceWrapperHelper.save(partner);

		// doc type
		final I_C_DocType type = InterfaceWrapperHelper.newInstance(I_C_DocType.class, contextProvider);
		type.setDocBaseType(X_C_DocType.DOCBASETYPE_ARInvoice);
		InterfaceWrapperHelper.save(type);

		// invoice
		final BigDecimal invoiceGrandTotal = new BigDecimal("50");
		final I_C_Invoice inv = InterfaceWrapperHelper.newInstance(I_C_Invoice.class, contextProvider);
		inv.setGrandTotal(invoiceGrandTotal);
		inv.setC_BPartner_ID(partner.getC_BPartner_ID());
		inv.setDocumentNo("654321");
		inv.setAD_Org_ID(org.getAD_Org_ID());
		inv.setC_DocType_ID(type.getC_DocType_ID());
		inv.setC_Currency_ID(currencyEUR.getC_Currency_ID());
		inv.setIsSOTrx(true);
		inv.setProcessed(true);
		InterfaceWrapperHelper.save(inv);

		// Registrate payment action handlers.
		esrBL.registerActionHandler(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Allocate_Payment_With_Current_Invoice, new WithCurrenttInvoiceESRActionHandler());
		esrImportLine.setESR_Payment_Action(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Allocate_Payment_With_Current_Invoice);

		esrBL.setInvoice(esrImportLine, inv);
		InterfaceWrapperHelper.save(esrImportLine);
		lines.add(esrImportLine);

		esrBL.complete(esrImport, "Complete", trxRunConfig);

		// check invoice
		InterfaceWrapperHelper.refresh(inv, true);
		assertThat(inv.isPaid(), is(true));

		// check import line
		InterfaceWrapperHelper.refresh(esrImportLine, true);
		assertThat(esrImportLine.isProcessed(), is(true));
		assertThat(esrImportLine.getESR_Payment_Action(), is(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Allocate_Payment_With_Current_Invoice));
		assertThat(esrImportLine.getESR_Document_Status(), is(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_PartiallyMatched));

		// check the created payments
		esrLine1Payment = esrImportLine.getC_Payment();
		assertThat(esrLine1Payment.getPayAmt(), comparesEqualTo(new BigDecimal(50)));
		assertThat(esrLine1Payment.getOverUnderAmt(), comparesEqualTo(new BigDecimal(0)));
		assertThat(esrLine1Payment.isAllocated(), is(true));

		// allocations
		List<I_C_AllocationLine> allocLines = Services.get(IAllocationDAO.class).retrieveAllocationLines(esrImportLine.getC_Invoice());
		assertThat(allocLines.size(), is(1));
		assertThat(allocLines.get(0).getAmount(), comparesEqualTo(new BigDecimal(50)));

		// esr processed
		InterfaceWrapperHelper.refresh(esrImport, true);
		assertThat(esrImport.isProcessed(), is(true));
	}

	/**
	 * This test emulates a real-world case of a case when invoice reference not found
	 * <ul>
	 * <li>One ESR Payment with amount of 50
	 * <li>invoice not matched, partner matched
	 * <li>A payment is created, but not allocated
	 * <li>choose an unpaid invoice and complete the esr
	 * <li>Allocation against invoice
	 * <li>ESR processed
	 * </ul>
	 */
	@Test
	public void testInvoiceReferenceNotFound_T07()
	{

		// org
		final I_AD_Org org = InterfaceWrapperHelper.newInstance(I_AD_Org.class, contextProvider);
		org.setValue("106");
		InterfaceWrapperHelper.save(org);

		// partner
		final I_C_BPartner partner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class, contextProvider);
		partner.setValue("123456");
		partner.setAD_Org_ID(org.getAD_Org_ID());
		InterfaceWrapperHelper.save(partner);

		final I_C_ReferenceNo_Type refNoType = InterfaceWrapperHelper.newInstance(I_C_ReferenceNo_Type.class, contextProvider);
		refNoType.setName("InvoiceReference");
		InterfaceWrapperHelper.save(refNoType);

		// esr line
		final List<I_ESR_ImportLine> lines = new ArrayList<I_ESR_ImportLine>();
		final String esrLineText = "01201067789300000001060012345600000000400000050009072  030014040914041014041100001006800000000000090                          ";
		final I_ESR_ImportLine esrImportLine = createImportLine(esrLineText);
		esrImportLine.setAD_Org_ID(org.getAD_Org_ID());
		esrImportLine.setC_BPartner(partner);
		InterfaceWrapperHelper.save(esrImportLine);
		lines.add(esrImportLine);

		// currency
		final I_C_Currency currencyEUR = InterfaceWrapperHelper.newInstance(I_C_Currency.class, contextProvider);
		currencyEUR.setISO_Code("EUR");
		currencyEUR.setStdPrecision(2);
		currencyEUR.setIsEuro(true);
		InterfaceWrapperHelper.save(currencyEUR);
		POJOWrapper.enableStrictValues(currencyEUR);

		// bank account
		final I_C_BP_BankAccount account = InterfaceWrapperHelper.newInstance(I_C_BP_BankAccount.class, contextProvider);
		account.setIsEsrAccount(true);
		account.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
		account.setAD_User_ID(Env.getAD_User_ID(getCtx()));
		account.setESR_RenderedAccountNo("01-067789-3");
		account.setC_Currency_ID(currencyEUR.getC_Currency_ID());
		InterfaceWrapperHelper.save(account);

		final I_ESR_Import esrImport = esrImportLine.getESR_Import();
		esrImport.setC_BP_BankAccount(account);
		InterfaceWrapperHelper.save(esrImport);

		// start processing
		final ITrxRunConfig trxRunConfig = Services.get(ITrxManager.class).createTrxRunConfig(TrxPropagation.REQUIRES_NEW, OnRunnableSuccess.COMMIT, OnRunnableFail.ASK_RUNNABLE);
		final ESRImportBL esrBL = new ESRImportBL();
		esrBL.process(esrImport, trxRunConfig);

		// this needs to be here because happens when saving, while importing the line
		// process emulates the importing of the file and at the end the line is saved when the default values are set
		InterfaceWrapperHelper.refresh(esrImportLine, true);
		esrImportLine.setESR_IsManual_ReferenceNo(true); // is by default on 'Y' in db
		InterfaceWrapperHelper.save(esrImportLine);

		// check import line
		assertThat(esrImportLine.isValid(), is(false));
		assertThat(esrImportLine.isProcessed(), is(false));
		assertThat(esrImportLine.getC_BPartner(), Matchers.notNullValue());
		assertThat(esrImportLine.getESR_Payment_Action(), nullValue());
		assertThat(esrImportLine.getESR_Document_Status(), is(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_PartiallyMatched));
		assertThat(esrImportLine.getErrorMsg(), Matchers.notNullValue());

		// check the created payments
		I_C_Payment esrLine1Payment = esrImportLine.getC_Payment();
		assertThat(esrLine1Payment.getPayAmt(), is(esrImportLine.getAmount()));
		assertThat(esrLine1Payment.isAllocated(), is(false));

		// doc type
		final I_C_DocType type = InterfaceWrapperHelper.newInstance(I_C_DocType.class, contextProvider);
		type.setDocBaseType(X_C_DocType.DOCBASETYPE_ARInvoice);
		InterfaceWrapperHelper.save(type);

		// invoice
		final BigDecimal invoiceGrandTotal = new BigDecimal("50");
		final I_C_Invoice inv = InterfaceWrapperHelper.newInstance(I_C_Invoice.class, contextProvider);
		inv.setGrandTotal(invoiceGrandTotal);
		inv.setC_BPartner_ID(partner.getC_BPartner_ID());
		inv.setDocumentNo("654321");
		inv.setAD_Org_ID(org.getAD_Org_ID());
		inv.setC_DocType_ID(type.getC_DocType_ID());
		inv.setC_Currency_ID(currencyEUR.getC_Currency_ID());
		inv.setIsSOTrx(true);
		inv.setProcessed(true);
		InterfaceWrapperHelper.save(inv);

		// Registrate payment action handlers.
		esrBL.registerActionHandler(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Allocate_Payment_With_Current_Invoice, new WithCurrenttInvoiceESRActionHandler());
		esrImportLine.setESR_Payment_Action(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Allocate_Payment_With_Current_Invoice);
		esrBL.setInvoice(esrImportLine, inv);
		InterfaceWrapperHelper.save(esrImportLine);

		esrBL.complete(esrImport, "Complete", trxRunConfig);

		// check invoice
		InterfaceWrapperHelper.refresh(inv, true);
		assertThat(inv.isPaid(), is(true));

		// check import line
		InterfaceWrapperHelper.refresh(esrImportLine, true);
		assertThat(esrImportLine.isProcessed(), is(true));
		assertThat(esrImportLine.getESR_Payment_Action(), is(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Allocate_Payment_With_Current_Invoice));
		assertThat(esrImportLine.getESR_Document_Status(), is(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_PartiallyMatched));

		// check the created payments
		esrLine1Payment = esrImportLine.getC_Payment();
		assertThat(esrLine1Payment.getPayAmt(), comparesEqualTo(new BigDecimal(50)));
		assertThat(esrLine1Payment.getOverUnderAmt(), comparesEqualTo(new BigDecimal(0)));
		assertThat(esrLine1Payment.isAllocated(), is(true));

		final List<I_C_AllocationLine> allocLines = Services.get(IAllocationDAO.class).retrieveAllocationLines(inv);
		assertThat(allocLines.size(), is(1));
		assertThat(allocLines.get(0).getAmount(), comparesEqualTo(new BigDecimal(50)));

		// esr processed
		InterfaceWrapperHelper.refresh(esrImport, true);
		assertThat(esrImport.isProcessed(), is(true));
	}

	/**
	 * This test emulates a real-world case of a case when invoice reference not found
	 * <ul>
	 * <li>One ESR Payment with amount of 50
	 * <li>invoice not matched, partner matched
	 * <li>A payment is created, but not allocated
	 * <li>leave invoice field empty, choose action 'E' and complete the esr
	 * <li>ESR processed
	 * </ul>
	 */
	@Test
	public void testInvoiceReferenceNotFound_T09()
	{

		// org
		final I_AD_Org org = InterfaceWrapperHelper.newInstance(I_AD_Org.class, contextProvider);
		org.setValue("106");
		InterfaceWrapperHelper.save(org);

		// partner
		final I_C_BPartner partner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class, contextProvider);
		partner.setValue("123456");
		partner.setAD_Org_ID(org.getAD_Org_ID());
		InterfaceWrapperHelper.save(partner);

		final I_C_ReferenceNo_Type refNoType = InterfaceWrapperHelper.newInstance(I_C_ReferenceNo_Type.class, contextProvider);
		refNoType.setName("InvoiceReference");
		InterfaceWrapperHelper.save(refNoType);

		// esr line
		final List<I_ESR_ImportLine> lines = new ArrayList<I_ESR_ImportLine>();
		final String esrLineText = "01201067789300000001060012345600000000400000050009072  030014040914041014041100001006800000000000090                          ";
		final I_ESR_ImportLine esrImportLine = createImportLine(esrLineText);
		esrImportLine.setAD_Org_ID(org.getAD_Org_ID());
		esrImportLine.setC_BPartner(partner);
		InterfaceWrapperHelper.save(esrImportLine);
		lines.add(esrImportLine);

		// currency
		final I_C_Currency currencyEUR = InterfaceWrapperHelper.newInstance(I_C_Currency.class, contextProvider);
		currencyEUR.setISO_Code("EUR");
		currencyEUR.setStdPrecision(2);
		currencyEUR.setIsEuro(true);
		InterfaceWrapperHelper.save(currencyEUR);
		POJOWrapper.enableStrictValues(currencyEUR);

		// bank account
		final I_C_BP_BankAccount account = InterfaceWrapperHelper.newInstance(I_C_BP_BankAccount.class, contextProvider);
		account.setIsEsrAccount(true);
		account.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
		account.setAD_User_ID(Env.getAD_User_ID(getCtx()));
		account.setESR_RenderedAccountNo("01-067789-3");
		account.setC_Currency_ID(currencyEUR.getC_Currency_ID());
		InterfaceWrapperHelper.save(account);

		final I_ESR_Import esrImport = esrImportLine.getESR_Import();
		esrImport.setC_BP_BankAccount(account);
		InterfaceWrapperHelper.save(esrImport);

		// start processing
		final ITrxRunConfig trxRunConfig = Services.get(ITrxManager.class).createTrxRunConfig(TrxPropagation.REQUIRES_NEW, OnRunnableSuccess.COMMIT, OnRunnableFail.ASK_RUNNABLE);
		final ESRImportBL esrBL = new ESRImportBL();
		esrBL.process(esrImport, trxRunConfig);

		// this needs to be here because happens when saving, while importing the line
		// process emulates the importing of the file and at the end the line is saved when the default values are set
		InterfaceWrapperHelper.refresh(esrImportLine, true);
		esrImportLine.setESR_IsManual_ReferenceNo(true); // is by default on 'Y' in db
		InterfaceWrapperHelper.save(esrImportLine);

		// check import line
		assertThat(esrImportLine.isValid(), is(false));
		assertThat(esrImportLine.isProcessed(), is(false));
		assertThat(esrImportLine.getESR_Payment_Action(), nullValue());
		assertThat(esrImportLine.getESR_Document_Status(), is(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_PartiallyMatched));
		assertThat(esrImportLine.getErrorMsg(), Matchers.notNullValue());

		// check the created payments
		I_C_Payment esrLine1Payment = esrImportLine.getC_Payment();
		assertThat(esrLine1Payment.getPayAmt(), is(esrImportLine.getAmount()));

		// Registrate payment action handlers.
		esrBL.registerActionHandler(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Unable_To_Assign_Income, new UnableToAssignESRActionHandler());
		esrImportLine.setESR_Payment_Action(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Unable_To_Assign_Income);
		InterfaceWrapperHelper.save(esrImportLine);

		esrBL.complete(esrImport, "Complete", trxRunConfig);

		// check import line
		InterfaceWrapperHelper.refresh(esrImportLine, true);
		assertThat(esrImportLine.isProcessed(), is(true));
		assertThat(esrImportLine.getESR_Payment_Action(), is(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Unable_To_Assign_Income));
		assertThat(esrImportLine.getESR_Document_Status(), is(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_PartiallyMatched));

		// check the created payments
		esrLine1Payment = esrImportLine.getC_Payment();
		assertThat(esrLine1Payment.getPayAmt(), comparesEqualTo(new BigDecimal(50)));
		assertThat(esrLine1Payment.getOverUnderAmt(), comparesEqualTo(new BigDecimal(0)));
		assertThat(esrLine1Payment.isAutoAllocateAvailableAmt(), is(false));
		assertThat(esrLine1Payment.isAllocated(), is(false));

		// esr processed
		InterfaceWrapperHelper.refresh(esrImport, true);
		assertThat(esrImport.isProcessed(), is(true));
	}

	@Rule
	public RepeatRule repeatRule = new RepeatRule();

	@Test
	@Repeat(times = 50)
	public void testStandardCase_T01_diffThreads()
	{
		Services.get(IESRImportDAO.class);
		Services.get(IAttachmentBL.class);
		Services.get(IESRImportBL.class);
		Services.get(IDocActionBL.class);
		Services.get(ITrxManager.class);
		Services.get(ITrxConstraintsBL.class);
		Services.get(ISysConfigBL.class);
		Services.get(IQueryBL.class);
		Services.get(ISysConfigDAO.class);
		Services.get(IOpenTrxBL.class);
		Services.get(IReferenceNoDAO.class);
		Services.get(IAllocationDAO.class);
		Services.get(IPeriodBL.class);
		Services.get(IPaymentBL.class);
		Services.get(IDocTypeDAO.class);
		Services.get(IInvoiceDAO.class);
		Services.get(IInvoiceBL.class);
		Services.get(IOrgDAO.class);
		Services.get(ILockManager.class);

		final String grandTotal = "50";
		final String esrLineText = "01201067789300000001060012345600654321400000050009072  030014040914041014041100001006800000000000090                          ";
		final String refNo = "300000001060012345600654321";
		final String partnerValue = "123456";
		final String invDocNo = "654321";
		final String ESR_Rendered_AccountNo = "01-067789-3";

		final I_ESR_ImportLine esrImportLine = setupESR_ImportLine(grandTotal, esrLineText, refNo, ESR_Rendered_AccountNo, partnerValue, invDocNo, false, false);
		final I_ESR_Import esrImport = esrImportLine.getESR_Import();

		final ITrxRunConfig trxRunConfig = Services.get(ITrxManager.class).createTrxRunConfig(TrxPropagation.REQUIRES_NEW, OnRunnableSuccess.COMMIT, OnRunnableFail.ASK_RUNNABLE);
		final ESRImportBL esrBL = new ESRImportBL();

		final Runnable runnable = new Runnable()
		{

			@Override
			public void run()
			{
				esrBL.process(esrImport, trxRunConfig);
			}
		};

		final List<Thread> threadsRunning = new ArrayList<Thread>();
		for (int threadNo = 1; threadNo <= 5; threadNo++)
		{
			final Thread thread = new Thread(runnable, Thread.currentThread().getName() + "_PrintJobsProducer" + threadNo);
			thread.start();
			threadsRunning.add(thread);
		}

		//
		// Wait until all threads finished
		while (!threadsRunning.isEmpty())
		{
			for (Iterator<Thread> it = threadsRunning.iterator(); it.hasNext();)
			{
				final Thread thread = it.next();
				try
				{
					thread.join();
				}
				catch (InterruptedException e)
				{
					throw new RuntimeException(e);
				}

				it.remove();
			}
		}

		final List<I_C_Payment> payments = db.getRecords(I_C_Payment.class);
//		System.out.println("***************************TEST PAYMENTS**********************");
//
//		for (final I_C_Payment payment : payments)
//		{
//			System.out.println("Payment " + payment.getC_Payment_ID() + " Allocated " + payment.isAllocated());
//		}

		Assert.assertEquals("Shall be only 1 payment", 1, payments.size());

	}

	/**
	 * This test emulates a real-world case of a different org for the invoice partner
	 * <ul>
	 * <li>invoice 50
	 * <li>one ESR line with amount 50
	 * <li>perfect match: partner, invoice, amount
	 * <li>but the partner of the invoice belong to different org
	 * <li>esr line should not be processed
	 * <li>esr line should not be valid
	 * <li>esr line should have an error message
	 * <li>esr line should have fields invoice, partner and payment empty
	 * </ul>
	 */
	@Test
	public void testStandardCase_DiffOrgForInvoicePartner()
	{

		final String grandTotal = "50";
		final String esrLineText = "01201067789300000001060012345600654321400000050009072  030014040914041014041100001006800000000000090                          ";
		final String refNo = "300000001060012345600654321";
		final String partnerValue = "123456";
		final String invDocNo = "654321";
		final String ESR_Rendered_AccountNo = "01-067789-3";

		// org
		final I_AD_Org org = InterfaceWrapperHelper.newInstance(I_AD_Org.class, contextProvider);
		org.setValue("106");
		InterfaceWrapperHelper.save(org);

		// second org
		final I_AD_Org org1 = InterfaceWrapperHelper.newInstance(I_AD_Org.class, contextProvider);
		org1.setValue("105");
		InterfaceWrapperHelper.save(org1);

		// partner
		final I_C_BPartner partner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class, contextProvider);
		partner.setValue(partnerValue);
		partner.setAD_Org_ID(org1.getAD_Org_ID());
		InterfaceWrapperHelper.save(partner);

		final I_C_ReferenceNo_Type refNoType = InterfaceWrapperHelper.newInstance(I_C_ReferenceNo_Type.class, contextProvider);
		refNoType.setName("InvoiceReference");
		InterfaceWrapperHelper.save(refNoType);

		// bank account
		final I_C_BP_BankAccount account = InterfaceWrapperHelper.newInstance(I_C_BP_BankAccount.class, contextProvider);
		account.setIsEsrAccount(true);
		account.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
		account.setAD_User_ID(Env.getAD_User_ID(getCtx()));
		account.setESR_RenderedAccountNo(ESR_Rendered_AccountNo);
		InterfaceWrapperHelper.save(account);

		// currency
		final I_C_Currency currencyEUR = InterfaceWrapperHelper.newInstance(I_C_Currency.class, contextProvider);
		currencyEUR.setISO_Code("EUR");
		currencyEUR.setStdPrecision(2);
		currencyEUR.setIsEuro(true);
		InterfaceWrapperHelper.save(currencyEUR);
		POJOWrapper.enableStrictValues(currencyEUR);

		// doc type
		final I_C_DocType type = InterfaceWrapperHelper.newInstance(I_C_DocType.class, contextProvider);
		type.setDocBaseType(X_C_DocType.DOCBASETYPE_ARInvoice);
		InterfaceWrapperHelper.save(type);

		// invoice
		final BigDecimal invoiceGrandTotal = new BigDecimal(grandTotal);
		final I_C_Invoice inv = InterfaceWrapperHelper.newInstance(I_C_Invoice.class, contextProvider);
		inv.setAD_Org_ID(org.getAD_Org_ID());
		inv.setGrandTotal(invoiceGrandTotal);
		inv.setC_BPartner_ID(partner.getC_BPartner_ID());
		inv.setDocumentNo(invDocNo);
		inv.setAD_Org_ID(org.getAD_Org_ID());
		inv.setC_DocType_ID(type.getC_DocType_ID());
		inv.setC_Currency_ID(currencyEUR.getC_Currency_ID());
		inv.setIsPaid(false);
		inv.setIsSOTrx(true);
		InterfaceWrapperHelper.save(inv);

		// reference no
		final I_C_ReferenceNo referenceNo = InterfaceWrapperHelper.newInstance(I_C_ReferenceNo.class, contextProvider);
		referenceNo.setReferenceNo(refNo);
		referenceNo.setC_ReferenceNo_Type(refNoType);
		referenceNo.setIsManual(true);
		InterfaceWrapperHelper.save(referenceNo);

		// reference nodoc
		final I_C_ReferenceNo_Doc esrReferenceNumberDocument = InterfaceWrapperHelper.newInstance(I_C_ReferenceNo_Doc.class, contextProvider);
		esrReferenceNumberDocument.setAD_Table_ID(Services.get(IADTableDAO.class).retrieveTableId(I_C_Invoice.Table_Name));
		esrReferenceNumberDocument.setRecord_ID(inv.getC_Invoice_ID());
		esrReferenceNumberDocument.setC_ReferenceNo(referenceNo);
		InterfaceWrapperHelper.save(esrReferenceNumberDocument);

		// esr line
		final List<I_ESR_ImportLine> lines = new ArrayList<I_ESR_ImportLine>();
		final I_ESR_ImportLine esrImportLine = createImportLine(esrLineText);
		esrImportLine.setC_BP_BankAccount(account);
		esrImportLine.setAD_Org_ID(org.getAD_Org_ID());
		InterfaceWrapperHelper.save(esrImportLine);
		lines.add(esrImportLine);

		final I_ESR_Import esrImport = esrImportLine.getESR_Import();
		esrImport.setC_BP_BankAccount(account);
		InterfaceWrapperHelper.save(esrImport);

		final ITrxRunConfig trxRunConfig = Services.get(ITrxManager.class).createTrxRunConfig(TrxPropagation.REQUIRES_NEW, OnRunnableSuccess.COMMIT, OnRunnableFail.ASK_RUNNABLE);
		final ESRImportBL esrBL = new ESRImportBL();
		esrBL.process(esrImport, trxRunConfig);

		// check import line
		InterfaceWrapperHelper.refresh(esrImportLine, true);
		assertThat(esrImportLine.isValid(), is(false));
		assertThat(esrImportLine.isProcessed(), is(false));
		assertThat(esrImportLine.getC_Invoice(), nullValue());
		assertThat(esrImportLine.getC_BPartner(), nullValue());
		assertThat(esrImportLine.getC_Payment(), nullValue());
		assertThat(esrImportLine.getErrorMsg(), Matchers.notNullValue());

	}

	/**
	 * This test emulates a real-world case of different org for invoice and partner
	 * <ul>
	 * <li>invoice 50
	 * <li>one ESR line with amount 50
	 * <li>perfect match: partner, invoice, amount
	 * <li>but the partner and invoice belong to different org than the import line
	 * <li>esr line should not be processed
	 * <li>esr line should not be valid
	 * <li>esr line should have an error message
	 * <li>esr line should have fields invoice, partner and payment empty
	 * </ul>
	 */
	@Test
	public void testStandardCase_DiffOrgForInvoiceAndPartner()
	{

		final String grandTotal = "50";
		final String esrLineText = "01201067789300000001060012345600654321400000050009072  030014040914041014041100001006800000000000090                          ";
		final String refNo = "300000001060012345600654321";
		final String partnerValue = "123456";
		final String invDocNo = "654321";
		final String ESR_Rendered_AccountNo = "01-067789-3";

		// org
		final I_AD_Org org = InterfaceWrapperHelper.newInstance(I_AD_Org.class, contextProvider);
		org.setValue("106");
		InterfaceWrapperHelper.save(org);

		// second org
		final I_AD_Org org1 = InterfaceWrapperHelper.newInstance(I_AD_Org.class, contextProvider);
		org1.setValue("105");
		InterfaceWrapperHelper.save(org1);

		// partner
		final I_C_BPartner partner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class, contextProvider);
		partner.setValue(partnerValue);
		partner.setAD_Org_ID(org1.getAD_Org_ID());
		InterfaceWrapperHelper.save(partner);

		final I_C_ReferenceNo_Type refNoType = InterfaceWrapperHelper.newInstance(I_C_ReferenceNo_Type.class, contextProvider);
		refNoType.setName("InvoiceReference");
		InterfaceWrapperHelper.save(refNoType);

		// bank account
		final I_C_BP_BankAccount account = InterfaceWrapperHelper.newInstance(I_C_BP_BankAccount.class, contextProvider);
		account.setIsEsrAccount(true);
		account.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
		account.setAD_User_ID(Env.getAD_User_ID(getCtx()));
		account.setESR_RenderedAccountNo(ESR_Rendered_AccountNo);
		InterfaceWrapperHelper.save(account);

		// currency
		final I_C_Currency currencyEUR = InterfaceWrapperHelper.newInstance(I_C_Currency.class, contextProvider);
		currencyEUR.setISO_Code("EUR");
		currencyEUR.setStdPrecision(2);
		currencyEUR.setIsEuro(true);
		InterfaceWrapperHelper.save(currencyEUR);
		POJOWrapper.enableStrictValues(currencyEUR);

		// doc type
		final I_C_DocType type = InterfaceWrapperHelper.newInstance(I_C_DocType.class, contextProvider);
		type.setDocBaseType(X_C_DocType.DOCBASETYPE_ARInvoice);
		InterfaceWrapperHelper.save(type);

		// invoice
		final BigDecimal invoiceGrandTotal = new BigDecimal(grandTotal);
		final I_C_Invoice inv = InterfaceWrapperHelper.newInstance(I_C_Invoice.class, contextProvider);
		inv.setAD_Org_ID(org1.getAD_Org_ID());
		inv.setGrandTotal(invoiceGrandTotal);
		inv.setC_BPartner_ID(partner.getC_BPartner_ID());
		inv.setDocumentNo(invDocNo);
		inv.setAD_Org_ID(org.getAD_Org_ID());
		inv.setC_DocType_ID(type.getC_DocType_ID());
		inv.setC_Currency_ID(currencyEUR.getC_Currency_ID());
		inv.setIsPaid(false);
		inv.setIsSOTrx(true);
		InterfaceWrapperHelper.save(inv);

		// reference no
		final I_C_ReferenceNo referenceNo = InterfaceWrapperHelper.newInstance(I_C_ReferenceNo.class, contextProvider);
		referenceNo.setReferenceNo(refNo);
		referenceNo.setC_ReferenceNo_Type(refNoType);
		referenceNo.setAD_Org_ID(org1.getAD_Org_ID());
		referenceNo.setIsManual(true);
		InterfaceWrapperHelper.save(referenceNo);

		// reference nodoc
		final I_C_ReferenceNo_Doc esrReferenceNumberDocument = InterfaceWrapperHelper.newInstance(I_C_ReferenceNo_Doc.class, contextProvider);
		esrReferenceNumberDocument.setAD_Table_ID(Services.get(IADTableDAO.class).retrieveTableId(I_C_Invoice.Table_Name));
		esrReferenceNumberDocument.setRecord_ID(inv.getC_Invoice_ID());
		esrReferenceNumberDocument.setC_ReferenceNo(referenceNo);
		esrReferenceNumberDocument.setAD_Org_ID(org1.getAD_Org_ID());
		InterfaceWrapperHelper.save(esrReferenceNumberDocument);

		// esr line
		final List<I_ESR_ImportLine> lines = new ArrayList<I_ESR_ImportLine>();
		final I_ESR_ImportLine esrImportLine = createImportLine(esrLineText);
		esrImportLine.setC_BP_BankAccount(account);
		esrImportLine.setAD_Org_ID(org.getAD_Org_ID());
		InterfaceWrapperHelper.save(esrImportLine);
		lines.add(esrImportLine);

		final I_ESR_Import esrImport = esrImportLine.getESR_Import();
		esrImport.setC_BP_BankAccount(account);
		InterfaceWrapperHelper.save(esrImport);


		final ITrxRunConfig trxRunConfig = Services.get(ITrxManager.class).createTrxRunConfig(TrxPropagation.REQUIRES_NEW, OnRunnableSuccess.COMMIT, OnRunnableFail.ASK_RUNNABLE);
		final ESRImportBL esrBL = new ESRImportBL();
		esrBL.process(esrImport, trxRunConfig);

		// check import line
		InterfaceWrapperHelper.refresh(esrImportLine, true);
		assertThat(esrImportLine.isValid(), is(false));
		assertThat(esrImportLine.isProcessed(), is(false));
		assertThat(esrImportLine.getC_Invoice(), nullValue());
		assertThat(esrImportLine.getC_BPartner(), nullValue());
		assertThat(esrImportLine.getC_Payment(), nullValue());
		assertThat(esrImportLine.getErrorMsg(), Matchers.notNullValue());

	}

	/**
	 * This test emulates a real-world of Invoice from a different organization
	 * <ul>
	 * <li>invoice 50
	 * <li>one ESR line with amount 50
	 * <li>perfect match: partner, invoice, amount
	 * <li>but the partner and invoice belong to different org then the import line
	 * <li>esr line should not be processed
	 * <li>esr line should not be valid
	 * <li>esr line should have an error message
	 * <li>esr line should have field invoiceempty
	 * </ul>
	 */
	@Test
	public void testStandardCase_DiffOrgForOnlyForInvoice()
	{

		final String grandTotal = "50";
		final String esrLineText = "01201067789300000001060012345600654321400000050009072  030014040914041014041100001006800000000000090                          ";
		final String refNo = "300000001060012345600654321";
		final String partnerValue = "123456";
		final String invDocNo = "654321";
		final String ESR_Rendered_AccountNo = "01-067789-3";

		// org
		final I_AD_Org org = InterfaceWrapperHelper.newInstance(I_AD_Org.class, contextProvider);
		org.setValue("106");
		InterfaceWrapperHelper.save(org);

		// second org
		final I_AD_Org org1 = InterfaceWrapperHelper.newInstance(I_AD_Org.class, contextProvider);
		org1.setValue("105");
		InterfaceWrapperHelper.save(org1);

		// partner
		final I_C_BPartner partner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class, contextProvider);
		partner.setValue(partnerValue);
		partner.setAD_Org_ID(org.getAD_Org_ID());
		InterfaceWrapperHelper.save(partner);

		final I_C_ReferenceNo_Type refNoType = InterfaceWrapperHelper.newInstance(I_C_ReferenceNo_Type.class, contextProvider);
		refNoType.setName("InvoiceReference");
		InterfaceWrapperHelper.save(refNoType);

		// bank account
		final I_C_BP_BankAccount account = InterfaceWrapperHelper.newInstance(I_C_BP_BankAccount.class, contextProvider);
		account.setIsEsrAccount(true);
		account.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
		account.setAD_User_ID(Env.getAD_User_ID(getCtx()));
		account.setESR_RenderedAccountNo(ESR_Rendered_AccountNo);
		InterfaceWrapperHelper.save(account);

		// currency
		final I_C_Currency currencyEUR = InterfaceWrapperHelper.newInstance(I_C_Currency.class, contextProvider);
		currencyEUR.setISO_Code("EUR");
		currencyEUR.setStdPrecision(2);
		currencyEUR.setIsEuro(true);
		InterfaceWrapperHelper.save(currencyEUR);
		POJOWrapper.enableStrictValues(currencyEUR);

		// doc type
		final I_C_DocType type = InterfaceWrapperHelper.newInstance(I_C_DocType.class, contextProvider);
		type.setDocBaseType(X_C_DocType.DOCBASETYPE_ARInvoice);
		InterfaceWrapperHelper.save(type);

		// invoice
		final BigDecimal invoiceGrandTotal = new BigDecimal(grandTotal);
		final I_C_Invoice inv = InterfaceWrapperHelper.newInstance(I_C_Invoice.class, contextProvider);
		inv.setAD_Org_ID(org1.getAD_Org_ID());
		inv.setGrandTotal(invoiceGrandTotal);
		inv.setC_BPartner_ID(partner.getC_BPartner_ID());
		inv.setDocumentNo(invDocNo);
		inv.setC_DocType_ID(type.getC_DocType_ID());
		inv.setC_Currency_ID(currencyEUR.getC_Currency_ID());
		inv.setIsPaid(false);
		inv.setIsSOTrx(true);
		InterfaceWrapperHelper.save(inv);

		// reference no
		final I_C_ReferenceNo referenceNo = InterfaceWrapperHelper.newInstance(I_C_ReferenceNo.class, contextProvider);
		referenceNo.setReferenceNo(refNo);
		referenceNo.setAD_Org_ID(org1.getAD_Org_ID());
		referenceNo.setC_ReferenceNo_Type(refNoType);
		referenceNo.setIsManual(true);
		InterfaceWrapperHelper.save(referenceNo);

		// reference nodoc
		final I_C_ReferenceNo_Doc esrReferenceNumberDocument = InterfaceWrapperHelper.newInstance(I_C_ReferenceNo_Doc.class, contextProvider);
		esrReferenceNumberDocument.setAD_Table_ID(Services.get(IADTableDAO.class).retrieveTableId(I_C_Invoice.Table_Name));
		esrReferenceNumberDocument.setRecord_ID(inv.getC_Invoice_ID());
		esrReferenceNumberDocument.setC_ReferenceNo(referenceNo);
		esrReferenceNumberDocument.setAD_Org_ID(org1.getAD_Org_ID());
		InterfaceWrapperHelper.save(esrReferenceNumberDocument);

		// esr line
		final List<I_ESR_ImportLine> lines = new ArrayList<I_ESR_ImportLine>();
		final I_ESR_ImportLine esrImportLine = createImportLine(esrLineText);
		esrImportLine.setC_BP_BankAccount(account);
		esrImportLine.setAD_Org_ID(org.getAD_Org_ID());
		InterfaceWrapperHelper.save(esrImportLine);
		lines.add(esrImportLine);

		final I_ESR_Import esrImport = esrImportLine.getESR_Import();
		esrImport.setC_BP_BankAccount(account);
		esrImport.setAD_Org_ID(org.getAD_Org_ID());
		InterfaceWrapperHelper.save(esrImport);


		final ITrxRunConfig trxRunConfig = Services.get(ITrxManager.class).createTrxRunConfig(TrxPropagation.REQUIRES_NEW, OnRunnableSuccess.COMMIT, OnRunnableFail.ASK_RUNNABLE);
		final ESRImportBL esrBL = new ESRImportBL();
		esrBL.process(esrImport, trxRunConfig);

		// check import line
		InterfaceWrapperHelper.refresh(esrImportLine, true);
		final String msg = "Invalid (errmsg=" + esrImportLine.getErrorMsg() + ")";
		assertThat(msg, esrImportLine.isValid(), is(false));
		assertThat(msg, esrImportLine.isProcessed(), is(false));
		assertThat(msg, esrImportLine.getC_Invoice(), nullValue());
		assertThat(msg, esrImportLine.getC_BPartner_ID(), is(partner.getC_BPartner_ID()));
		assertThat(msg, esrImportLine.getC_Payment(), Matchers.notNullValue());
		assertThat(msg, esrImportLine.getErrorMsg(), Matchers.notNullValue());

	}

	/**
	 * This test emulates a real-world case of multiple lines and no action
	 * <ul>
	 * <li>invoice 25
	 * <li>one ESR line with amount 25
	 * <li>other ESR line with amount 25
	 * <li>other ESR line with amount 25
	 * <li>noone of the line has action set
	 * <li>we set for the third one and check that was processed
	 * </ul>
	 */
	@Test
	public void testMultipleLines_NoAction()
	{
		// org
		final I_AD_Org org = InterfaceWrapperHelper.newInstance(I_AD_Org.class, contextProvider);
		org.setValue("106");
		InterfaceWrapperHelper.save(org);

		// partner
		final I_C_BPartner partner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class, contextProvider);
		partner.setValue("123456");
		partner.setAD_Org_ID(org.getAD_Org_ID());
		InterfaceWrapperHelper.save(partner);

		final I_C_ReferenceNo_Type refNoType = InterfaceWrapperHelper.newInstance(I_C_ReferenceNo_Type.class, contextProvider);
		refNoType.setName("InvoiceReference");
		InterfaceWrapperHelper.save(refNoType);

		// bank account
		final I_C_BP_BankAccount account = InterfaceWrapperHelper.newInstance(I_C_BP_BankAccount.class, contextProvider);
		account.setIsEsrAccount(true);
		account.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
		account.setAD_User_ID(Env.getAD_User_ID(getCtx()));
		account.setESR_RenderedAccountNo("01-067789-3");
		InterfaceWrapperHelper.save(account);

		// currency
		final I_C_Currency currencyEUR = InterfaceWrapperHelper.newInstance(I_C_Currency.class, contextProvider);
		currencyEUR.setISO_Code("EUR");
		currencyEUR.setStdPrecision(2);
		currencyEUR.setIsEuro(true);
		InterfaceWrapperHelper.save(currencyEUR);
		POJOWrapper.enableStrictValues(currencyEUR);

		// doc type
		final I_C_DocType type = InterfaceWrapperHelper.newInstance(I_C_DocType.class, contextProvider);
		type.setDocBaseType(X_C_DocType.DOCBASETYPE_ARInvoice);
		InterfaceWrapperHelper.save(type);

		// invoice
		final BigDecimal invoiceGrandTotal = new BigDecimal("25");
		final I_C_Invoice inv = InterfaceWrapperHelper.newInstance(I_C_Invoice.class, contextProvider);
		inv.setAD_Org_ID(org.getAD_Org_ID());
		inv.setGrandTotal(invoiceGrandTotal);
		inv.setC_BPartner_ID(partner.getC_BPartner_ID());
		inv.setDocumentNo("654321");
		inv.setAD_Org_ID(org.getAD_Org_ID());
		inv.setC_DocType_ID(type.getC_DocType_ID());
		inv.setC_Currency_ID(currencyEUR.getC_Currency_ID());
		inv.setProcessed(true);
		inv.setIsSOTrx(true);
		inv.setIsPaid(true);
		InterfaceWrapperHelper.save(inv);

		// allocation for invoice
		final I_C_AllocationLine allocAmt = InterfaceWrapperHelper.newInstance(I_C_AllocationLine.class, contextProvider);
		allocAmt.setAmount(new BigDecimal(25));
		allocAmt.setC_Invoice_ID(inv.getC_Invoice_ID());
		InterfaceWrapperHelper.save(allocAmt);

		// reference no
		final I_C_ReferenceNo referenceNo = InterfaceWrapperHelper.newInstance(I_C_ReferenceNo.class, contextProvider);
		referenceNo.setReferenceNo("300000001060012345600654321");
		referenceNo.setC_ReferenceNo_Type(refNoType);
		referenceNo.setIsManual(true);
		InterfaceWrapperHelper.save(referenceNo);

		// reference nodoc
		final I_C_ReferenceNo_Doc esrReferenceNumberDocument = InterfaceWrapperHelper.newInstance(I_C_ReferenceNo_Doc.class, contextProvider);
		esrReferenceNumberDocument.setAD_Table_ID(Services.get(IADTableDAO.class).retrieveTableId(I_C_Invoice.Table_Name));
		esrReferenceNumberDocument.setRecord_ID(inv.getC_Invoice_ID());
		esrReferenceNumberDocument.setC_ReferenceNo(referenceNo);
		InterfaceWrapperHelper.save(esrReferenceNumberDocument);

		// esr line
		final List<I_ESR_ImportLine> lines = new ArrayList<I_ESR_ImportLine>();
		// first line
		final String esrLineText = "01201067789300000001060012345600654321400000025009072  030014040914041014041100001006800000000000090                          ";
		final I_ESR_ImportLine esrImportLine1 = createImportLine(esrLineText);
		esrImportLine1.setC_BP_BankAccount(account);
		esrImportLine1.setAD_Org_ID(org.getAD_Org_ID());
		InterfaceWrapperHelper.save(esrImportLine1);

		final I_ESR_Import esrImport = esrImportLine1.getESR_Import();

		// second line
		final I_ESR_ImportLine esrImportLine2 = createImportLine(esrLineText);
		esrImportLine2.setC_BP_BankAccount(account);
		esrImportLine2.setAD_Org_ID(org.getAD_Org_ID());
		esrImportLine2.setESR_Import(esrImport);
		InterfaceWrapperHelper.save(esrImportLine2);
		lines.add(esrImportLine2);

		// third line
		final I_ESR_ImportLine esrImportLine3 = createImportLine(esrLineText);
		esrImportLine3.setC_BP_BankAccount(account);
		esrImportLine3.setAD_Org_ID(org.getAD_Org_ID());
		esrImportLine3.setESR_Import(esrImport);
		InterfaceWrapperHelper.save(esrImportLine3);
		lines.add(esrImportLine3);

		esrImport.setC_BP_BankAccount(account);
		InterfaceWrapperHelper.save(esrImport);

		// start processing

		final ITrxRunConfig trxRunConfig = Services.get(ITrxManager.class).createTrxRunConfig(TrxPropagation.REQUIRES_NEW, OnRunnableSuccess.COMMIT, OnRunnableFail.ASK_RUNNABLE);
		final ESRImportBL esrBL = new ESRImportBL();
		esrBL.process(esrImport, trxRunConfig);

		// check first import line
		InterfaceWrapperHelper.refresh(esrImportLine1, true);
		assertThat(esrImportLine1.isProcessed(), is(false));
		assertThat(esrImportLine1.getESR_Payment_Action(), nullValue());

		// check second import line
		InterfaceWrapperHelper.refresh(esrImportLine2, true);
		assertThat(esrImportLine2.isProcessed(), is(false));
		assertThat(esrImportLine2.getESR_Payment_Action(), nullValue());

		// check third import line
		InterfaceWrapperHelper.refresh(esrImportLine3, true);
		assertThat(esrImportLine3.isProcessed(), is(false));
		assertThat(esrImportLine3.getESR_Payment_Action(), nullValue());


		// Registrate payment action handlers.
		esrBL.registerActionHandler(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Unable_To_Assign_Income, new UnableToAssignESRActionHandler());
		esrImportLine3.setESR_Payment_Action(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Unable_To_Assign_Income);
		InterfaceWrapperHelper.save(esrImportLine3);
		// esrBL.process(esrImport, trxRunConfig);
		esrBL.complete(esrImport, "test", trxRunConfig);

		InterfaceWrapperHelper.refresh(esrImportLine3, true);
		assertThat(esrImportLine3.isProcessed(), is(true));
	}

}
