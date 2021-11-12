package de.metas.payment.esr.api.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.allocation.api.IAllocationDAO;
import de.metas.allocation.api.impl.PlainAllocationDAO;
import de.metas.currency.CurrencyCode;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.document.refid.model.I_C_ReferenceNo;
import de.metas.document.refid.model.I_C_ReferenceNo_Doc;
import de.metas.document.refid.model.I_C_ReferenceNo_Type;
import de.metas.interfaces.I_C_BPartner;
import de.metas.interfaces.I_C_DocType;
import de.metas.money.CurrencyId;
import de.metas.payment.PaymentId;
import de.metas.payment.esr.ESRTestBase;
import de.metas.payment.esr.ESRTestUtil;
import de.metas.payment.esr.ESRValidationRuleTools;
import de.metas.payment.esr.model.I_C_BP_BankAccount;
import de.metas.payment.esr.model.I_ESR_Import;
import de.metas.payment.esr.model.I_ESR_ImportFile;
import de.metas.payment.esr.model.I_ESR_ImportLine;
import de.metas.payment.esr.model.X_ESR_ImportLine;
import de.metas.util.Services;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_Payment;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.refresh;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class ESRImportBLTest extends ESRTestBase
{
	private static final BigDecimal FOURTY = new BigDecimal("40");
	private static final BigDecimal TWENTY = new BigDecimal("20");
	private static final BigDecimal SIXTY = new BigDecimal("60");
	private static final BigDecimal HUNDRET = new BigDecimal("100");
	private static final BigDecimal ESR_LINE_1_AMOUNT = new BigDecimal("31");
	private static final BigDecimal INVOICE_GRANDTOTAL = new BigDecimal("62.50");
	private I_C_Invoice invoice;

	private AllocationDAOMock allocationDAOMock;

	@Override
	public void init()
	{
		allocationDAOMock = new AllocationDAOMock();
	}

	// 04220

	/**
	 * Verifies {@link ESRImportBLTest#testEvaluateTrxQty()}.
	 *
	 * @task https://github.com/metasfresh/metasfresh/issues/2106
	 */
	@Test
	public void testEvaluateTrxQty()
	{
		final I_ESR_Import esrImport = newInstance(I_ESR_Import.class);

		save(esrImport);
		final I_ESR_ImportFile esrImportFile = createImportFile(esrImport);

		// https://github.com/metasfresh/metasfresh/issues/2106
		assertThat(esrImportBL.evaluateTrxQty(esrImportFile, 23))
				.as("esrImport has no information, so assume it's OK and return true")
				.isTrue();

		esrImportFile.setESR_Control_Trx_Qty(BigDecimal.ZERO);
		save(esrImportFile);
		assertThat(esrImportBL.evaluateTrxQty(esrImportFile, 23))
				.as("zero is not 23, so return false")
				.isFalse();

		esrImportFile.setESR_Control_Trx_Qty(new BigDecimal("23.000"));
		save(esrImportFile);
		assertThat(esrImportBL.evaluateTrxQty(esrImportFile, 23))
				.as("if numbers match, it shall return true")
				.isTrue();
	}

	/**
	 * <ul>
	 * <li>ESR line with a pay amount of 40 and invoice docNo 000120686?</li>
	 * <li>However existing C_Invoice docno 000120686 has a grand total of 100, 10 already written off
	 * <li>ESR line is matched => now referencing invoice
	 * <li>*TODO ruxi: if there should be a payment of allocation at this point, pls verify/assert it in this test case
	 * <li>other existing invoice with docNo 000120688 and grand total of 123.56:
	 * <li>from 000120688's 123.56, 120.00 are allocated (100 "payed", 20 written off)
	 * <li>000120688 is set as the "real" invoice of the ESR line
	 * <li>*TODO ruxi: i think now the "old" invoice 000120686 needs to be deallocation from the 40 bucks we allocated earlier (if that was actually ok).
	 */
	@Test
	public void test_setInvoice()
	{
		final String esrImportLineText = "00201059931000000010501536417000120686900000040000012  190013011813011813012100015000400000000000000";

		final I_AD_Org org = getAD_Org();
		// org.setValue("105");
		// save(org);

		final I_ESR_Import esrImport = createImport();

		final CurrencyId currencyEUR = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		final I_C_BP_BankAccount account = createBankAccount(true,
															 org.getAD_Org_ID(),
															 Env.getAD_User_ID(getCtx()),
															 "01-059931-0",
															 currencyEUR);

		esrImport.setC_BP_BankAccount_ID(account.getC_BP_BankAccount_ID());

		final I_C_ReferenceNo_Type refNoType = newInstance(I_C_ReferenceNo_Type.class);
		refNoType.setName("InvoiceReference");
		save(refNoType);

		final I_C_BPartner partner = newInstance(I_C_BPartner.class);
		partner.setValue("partner1");
		partner.setAD_Org_ID(org.getAD_Org_ID());
		save(partner);

		esrImport.setAD_Org_ID(org.getAD_Org_ID());

		save(esrImport);

		final I_ESR_ImportFile esrImportFile = createImportFile(esrImport);

		final I_C_DocType type = newInstance(I_C_DocType.class);
		type.setDocBaseType(X_C_DocType.DOCBASETYPE_ARInvoice);
		save(type);

		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setC_BPartner_ID(partner.getC_BPartner_ID());
		invoice.setAD_Org_ID(org.getAD_Org_ID());
		invoice.setDocumentNo("000120686");
		invoice.setAD_Org_ID(org.getAD_Org_ID());
		invoice.setGrandTotal(HUNDRET);
		invoice.setC_DocType_ID(type.getC_DocType_ID());
		invoice.setC_Currency_ID(currencyEUR.getRepoId());
		save(invoice);

		final I_C_ReferenceNo referenceNo = newInstance(I_C_ReferenceNo.class);
		referenceNo.setReferenceNo("0000000105015364170001206869"); // note that we include the check-digit 9 in here
		referenceNo.setC_ReferenceNo_Type(refNoType);
		referenceNo.setIsManual(true);
		referenceNo.setAD_Org_ID(org.getAD_Org_ID());
		save(referenceNo);

		final I_C_ReferenceNo_Doc esrReferenceNumberDocument = newInstance(I_C_ReferenceNo_Doc.class);
		esrReferenceNumberDocument.setAD_Table_ID(Services.get(IADTableDAO.class).retrieveTableId(I_C_Invoice.Table_Name));
		esrReferenceNumberDocument.setRecord_ID(invoice.getC_Invoice_ID());
		esrReferenceNumberDocument.setC_ReferenceNo(referenceNo);
		save(esrReferenceNumberDocument);

		// invoke the code under test
		esrImportBL.loadAndEvaluateESRImportStream(esrImportFile, new ByteArrayInputStream(esrImportLineText.getBytes()));
		refresh(esrImport, true);

		final I_ESR_ImportLine esrImportLine = ESRTestUtil.retrieveSingleLine(esrImport);

		assertThat(esrImportLine.getAmount()).isEqualByComparingTo(FOURTY); // guard
		// guards
		assertThat(esrImportLine.getC_Invoice_ID()).as("Invoice not set correctly").isEqualTo(invoice.getC_Invoice_ID());
		assertThat(esrImportLine.getESR_Invoice_Grandtotal()).as("Incorrect grandtotal").isEqualByComparingTo(HUNDRET);
		assertThat(invoice.getGrandTotal()).as("Incorrect grandtotal").isEqualByComparingTo(HUNDRET);

		// guard: invoice has grandtotal=100; 10 already written off => 90 open; payment of 40 already allocated as of task 06677 => 50 open
		// TODO: write unit tests to further dig into the "matching" and "updateOpenAmount" topics
		assertThat(esrImportLine.getESR_Invoice_Openamt()).as("Incorrect invoice open amount").isEqualByComparingTo(SIXTY);

		final BigDecimal invoice2GrandTotal = new BigDecimal("123.56");

		final I_C_Invoice invoice2 = newInstance(I_C_Invoice.class);
		invoice2.setGrandTotal(invoice2GrandTotal);
		invoice2.setC_BPartner_ID(partner.getC_BPartner_ID());
		invoice2.setDocumentNo("000120688");
		invoice2.setAD_Org_ID(org.getAD_Org_ID());
		invoice2.setC_DocType_ID(type.getC_DocType_ID());
		save(invoice2);

		// create allocation over 100 (plus 20 writeoff)
		// note that PlainInvoiceDAO.retrieveAllocatedAmt() currently only checks for allocation lines, ignoring any hdr info.
		final I_C_AllocationLine allocAmt2 = newInstance(I_C_AllocationLine.class);
		allocAmt2.setWriteOffAmt(TWENTY);
		allocAmt2.setAmount(HUNDRET);
		allocAmt2.setC_Invoice_ID(invoice2.getC_Invoice_ID());
		save(allocAmt2);

		esrImportBL.setInvoice(esrImportLine, invoice2);

		assertThat(esrImportLine.getC_Invoice_ID()).as("Invoice not set correctly").isEqualTo(invoice2.getC_Invoice_ID());
		assertThat(invoice2GrandTotal).as("Incorrect grandtotal").isEqualByComparingTo(esrImportLine.getESR_Invoice_Grandtotal());

		// ts: note that we always subtract the line's (or lines' !) amount from the invoice's open amount
		assertThat(esrImportLine.getESR_Invoice_Openamt()).isEqualByComparingTo(new BigDecimal("3.56").subtract(esrImportLine.getAmount())); // this should be correct when we have a non-credit-memo
	}

	@Test
	public void test_setInvoice_wrongOrg()
	{
		final String ESR_NO_HAS_WRONG_ORG_2P = "de.metas.payment.esr.EsrNoHasWrongOrg";

		final String esrImportLineText = "00201059931000000010501536417000120686900000040000012  190013011813011813012100015000400000000000000";

		final I_ESR_Import esrImport = createImport();

		final CurrencyId currencyEUR = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		final I_AD_Org org = newInstance(I_AD_Org.class);
		org.setValue("105");
		save(org);

		final I_C_BP_BankAccount account = createBankAccount(true,
															 org.getAD_Org_ID(),
															 Env.getAD_User_ID(getCtx()),
															 "01-059931-0",
															 currencyEUR);

		esrImport.setC_BP_BankAccount_ID(account.getC_BP_BankAccount_ID());
		save(esrImport);

		final I_C_ReferenceNo_Type refNoType = newInstance(I_C_ReferenceNo_Type.class);
		refNoType.setName("InvoiceReference");
		save(refNoType);

		final I_C_BPartner partner = newInstance(I_C_BPartner.class);
		partner.setValue("partner1");
		save(partner);



		esrImport.setAD_Org_ID(org.getAD_Org_ID());
		save(esrImport);

		final I_ESR_ImportFile esrImportFile = createImportFile(esrImport);

		final I_C_DocType type = newInstance(I_C_DocType.class);
		type.setDocBaseType(X_C_DocType.DOCBASETYPE_APCreditMemo);
		save(type);

		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setC_BPartner_ID(partner.getC_BPartner_ID());
		invoice.setDocumentNo("000120686");
		invoice.setAD_Org_ID(org.getAD_Org_ID());
		invoice.setC_DocType_ID(type.getC_DocType_ID());
		save(invoice);

		final I_C_ReferenceNo referenceNo = newInstance(I_C_ReferenceNo.class);
		referenceNo.setReferenceNo("000000010501536417000120686");
		referenceNo.setC_ReferenceNo_Type(refNoType);
		referenceNo.setIsManual(true);
		save(referenceNo);

		final I_C_ReferenceNo_Doc esrReferenceNumberDocument = newInstance(I_C_ReferenceNo_Doc.class);
		esrReferenceNumberDocument.setAD_Table_ID(Services.get(IADTableDAO.class).retrieveTableId(I_C_Invoice.Table_Name));
		esrReferenceNumberDocument.setRecord_ID(invoice.getC_Invoice_ID());
		esrReferenceNumberDocument.setC_ReferenceNo(referenceNo);
		save(esrReferenceNumberDocument);

		I_C_AllocationLine allocAmt = newInstance(I_C_AllocationLine.class);
		allocAmt.setWriteOffAmt(new BigDecimal(10.0));
		allocAmt.setC_Invoice_ID(invoice.getC_Invoice_ID());
		save(allocAmt);

		esrImportBL.loadAndEvaluateESRImportStream(esrImportFile, new ByteArrayInputStream(esrImportLineText.getBytes()));

		final BigDecimal grandTotal = new BigDecimal("123.56");

		final I_AD_Org org2 = newInstance(I_AD_Org.class);
		org2.setValue("org2");
		save(org2);

		final I_C_Invoice invoice2 = newInstance(I_C_Invoice.class);
		invoice2.setGrandTotal(grandTotal);
		invoice2.setC_BPartner_ID(partner.getC_BPartner_ID());
		invoice2.setDocumentNo("000120688");
		invoice2.setAD_Org_ID(org2.getAD_Org_ID());
		invoice2.setC_DocType_ID(type.getC_DocType_ID());
		save(invoice2);

		final I_C_AllocationLine allocAmt2 = newInstance(I_C_AllocationLine.class);
		allocAmt2.setWriteOffAmt(TWENTY);
		allocAmt2.setAmount(HUNDRET);
		allocAmt2.setC_Invoice_ID(invoice2.getC_Invoice_ID());
		save(allocAmt2);

		// for testing purposes
		final I_ESR_ImportLine esrImportLine = ESRTestUtil.retrieveSingleLine(esrImport);
		esrImportLine.setImportErrorMsg(null);
		esrImportLine.setMatchErrorMsg(null);
		save(esrImportLine);

		// invoke the code under test
		esrImportBL.setInvoice(esrImportLine, invoice2);

		assertThat(esrImportLine.getImportErrorMsg()).isNull();
		assertThat(esrImportLine.getMatchErrorMsg()).isEqualTo("de.metas.payment.esr.EsrNoHasWrongOrg_[org2, 105]");
	}

	/**
	 * This test emulates a real-world case:
	 * <ul>
	 * <li>invoice over 62.50
	 * <li>three ESR lines with amounts 31, 31.5 and 62.5, with same invoice, same date etc
	 * <li>we will have 3 payments
	 * </ul>
	 */
	@Test
	public void testProcessLinesWithInvoice_3Lines_1Payment()
	{
		final List<I_ESR_ImportLine> lines = testProcessLinesWithInvoice_common_setup(10, 20, 30);

		allocationDAOMock.addResult(BigDecimal.ZERO); // 1st invocation: ZERO, as nothing was allocated agains the invoice
		allocationDAOMock.addResult(BigDecimal.ZERO); // 2nd invocation: still ZERO, as only the 3 lines' OWN payment was allocated
		allocationDAOMock.addResult(BigDecimal.ZERO);
		allocationDAOMock.addResult(BigDecimal.ZERO);
		allocationDAOMock.addResult(BigDecimal.ZERO);
		allocationDAOMock.addResult(BigDecimal.ZERO);
		allocationDAOMock.addResult(BigDecimal.ZERO);
		allocationDAOMock.addResult(BigDecimal.ZERO);
		allocationDAOMock.addResult(BigDecimal.ZERO);

		Services.registerService(IAllocationDAO.class, allocationDAOMock);

		final I_ESR_ImportLine esrImportLine1 = lines.get(0);
		final I_ESR_ImportLine esrImportLine2 = lines.get(1);
		final I_ESR_ImportLine esrImportLine3 = lines.get(2);

		for (final I_ESR_ImportLine line : lines)
		{
			refresh(line, true);
		}
		esrImportBL.processLinesWithInvoice(lines);

		// check the created payments
		refresh(esrImportLine1, true);
		refresh(esrImportLine2, true);
		refresh(esrImportLine3, true);
		assert3Lines_DifferentPayment_Correct(esrImportLine1, esrImportLine2, esrImportLine3);

		// check the line's status and open amounts
		assert3Lines_123Order_Correct(esrImportLine1, esrImportLine2, esrImportLine3);
	}

	/**
	 * Same lines as in {@link #testProcessLinesWithInvoice_3Lines_1Payment()}, All should have different payments at all.
	 */
	@Test
	public void testProcessLinesWithInvoice_3Lines_2Payments_12_3()
	{
		final List<I_ESR_ImportLine> lines = testProcessLinesWithInvoice_common_setup(10, 20, 30);

		allocationDAOMock.addResult(BigDecimal.ZERO); // 1st invocation: ZERO, as nothing was allocated agains the invoice
		allocationDAOMock.addResult(BigDecimal.ZERO); // further invocations: still ZERO, as only the 3 lines' OWN payment was allocated
		allocationDAOMock.addResult(BigDecimal.ZERO);
		allocationDAOMock.addResult(BigDecimal.ZERO);
		allocationDAOMock.addResult(BigDecimal.ZERO);
		allocationDAOMock.addResult(BigDecimal.ZERO);
		allocationDAOMock.addResult(BigDecimal.ZERO);
		allocationDAOMock.addResult(BigDecimal.ZERO);
		allocationDAOMock.addResult(BigDecimal.ZERO);

		Services.registerService(IAllocationDAO.class, allocationDAOMock);

		final I_ESR_ImportLine esrImportLine1 = lines.get(0);
		final I_ESR_ImportLine esrImportLine2 = lines.get(1);
		final I_ESR_ImportLine esrImportLine3 = lines.get(2);

		for (final I_ESR_ImportLine line : lines)
		{
			refresh(line, true);
		}

		esrImportBL.processLinesWithInvoice(Arrays.asList(esrImportLine1, esrImportLine2));

		esrImportBL.processLinesWithInvoice(Arrays.asList(esrImportLine3));

		// check the created payments
		// there is no perfect match (the amount is not matching perfect), so shall be no allocation and no payment match at this moment
		refresh(esrImportLine1, true);
		refresh(esrImportLine2, true);
		refresh(esrImportLine3, true);

		final PaymentId esrImportLine1PaymentId = PaymentId.ofRepoIdOrNull(esrImportLine1.getC_Payment_ID());
		final I_C_Payment esrLine1Payment = esrImportLine1PaymentId == null ? null
				: paymentDAO.getById(esrImportLine1PaymentId);

		assertThat(esrLine1Payment.getPayAmt(), comparesEqualTo(new BigDecimal(31.0)));
		assertThat(esrLine1Payment.getC_Invoice_ID(), is(0));

		final PaymentId esrImportLine2PaymentId = PaymentId.ofRepoIdOrNull(esrImportLine2.getC_Payment_ID());
		final I_C_Payment esrLine2Payment = esrImportLine2PaymentId == null ? null
				: paymentDAO.getById(esrImportLine2PaymentId);

		assertThat(esrLine2Payment, not(esrLine1Payment));
		assertThat(esrLine2Payment.getPayAmt(), comparesEqualTo(new BigDecimal(31.5)));
		assertThat(esrLine2Payment.getC_Invoice_ID(), is(0));

		final PaymentId esrImportLine3PaymentId = PaymentId.ofRepoIdOrNull(esrImportLine3.getC_Payment_ID());
		final I_C_Payment esrLine3Payment = esrImportLine3PaymentId == null ? null
				: paymentDAO.getById(esrImportLine3PaymentId);

		assertThat(esrLine3Payment, not(esrLine1Payment));
		assertThat(esrLine3Payment, not(esrLine2Payment));
		assertThat(esrLine3Payment.getPayAmt(), comparesEqualTo(new BigDecimal(62.50)));
		assertThat(esrLine3Payment.getC_Invoice_ID(), is(0));

		// check the line's status and open amounts
		assert3Lines_123Order_Correct(esrImportLine1, esrImportLine2, esrImportLine3);
	}

	/**
	 * Same lines as in {@link #testProcessLinesWithInvoice_3Lines_1Payment()}, but all line shoudl have different payments. Despite none of the two payments' PayAmounts matches the invoice's grant
	 * total, the first two lines shall be processed, so that the user doen't need to deal with them.
	 */
	@Test
	public void testProcessLinesWithInvoice_3Lines_2Payments_1_23()
	{
		final List<I_ESR_ImportLine> lines = testProcessLinesWithInvoice_common_setup(10, 20, 30);

		final I_ESR_ImportLine esrImportLine1 = lines.get(0);
		final I_ESR_ImportLine esrImportLine2 = lines.get(1);
		final I_ESR_ImportLine esrImportLine3 = lines.get(2);

		allocationDAOMock.addResult(BigDecimal.ZERO); // 1st invocation: ZERO, as nothing was allocated agains the invoice
		allocationDAOMock.addResult(BigDecimal.ZERO); // further invocations: still ZERO, as only the 3 lines' OWN payment was allocated
		allocationDAOMock.addResult(BigDecimal.ZERO);
		allocationDAOMock.addResult(BigDecimal.ZERO);
		allocationDAOMock.addResult(BigDecimal.ZERO);
		allocationDAOMock.addResult(BigDecimal.ZERO);
		allocationDAOMock.addResult(BigDecimal.ZERO);
		allocationDAOMock.addResult(BigDecimal.ZERO);
		allocationDAOMock.addResult(BigDecimal.ZERO);

		Services.registerService(IAllocationDAO.class, allocationDAOMock);

		for (final I_ESR_ImportLine line : lines)
		{
			refresh(line, true);
		}

		esrImportBL.processLinesWithInvoice(Arrays.asList(esrImportLine1));
		esrImportBL.processLinesWithInvoice(Arrays.asList(esrImportLine2, esrImportLine3));

		// check the created payments
		// there is no perfect match (the amount is not matching perfect), so shall be no allocation and no payment match at this moment
		refresh(esrImportLine1, true);
		refresh(esrImportLine2, true);
		refresh(esrImportLine3, true);

		final PaymentId esrImportLine1PaymentId = PaymentId.ofRepoIdOrNull(esrImportLine1.getC_Payment_ID());
		final I_C_Payment esrLine1Payment = esrImportLine1PaymentId == null ? null
				: paymentDAO.getById(esrImportLine1PaymentId);

		assertThat(esrLine1Payment.getPayAmt(), comparesEqualTo(new BigDecimal(31.0)));
		assertThat(esrLine1Payment.getC_Invoice_ID(), is(0));

		final PaymentId esrImportLine2PaymentId = PaymentId.ofRepoIdOrNull(esrImportLine2.getC_Payment_ID());
		final I_C_Payment esrLine2Payment = esrImportLine2PaymentId == null ? null
				: paymentDAO.getById(esrImportLine2PaymentId);

		assertThat(esrLine2Payment, not(esrLine1Payment));
		assertThat(esrLine2Payment.getPayAmt(), comparesEqualTo(new BigDecimal(31.5)));
		assertThat(esrLine2Payment.getC_Invoice_ID(), is(0));

		final PaymentId esrImportLine3PaymentId = PaymentId.ofRepoIdOrNull(esrImportLine3.getC_Payment_ID());
		final I_C_Payment esrLine3Payment = esrImportLine3PaymentId == null ? null
				: paymentDAO.getById(esrImportLine3PaymentId);

		assertThat(esrLine3Payment, not(esrLine2Payment));
		assertThat(esrLine3Payment.getPayAmt(), comparesEqualTo(new BigDecimal(62.50)));
		assertThat(esrLine3Payment.getC_Invoice_ID(), is(0));

		// check the line's status and open amounts
		assert3Lines_123Order_Correct(esrImportLine1, esrImportLine2, esrImportLine3);
	}

	/**
	 * Same lines as in {@link #testProcessLinesWithInvoice_3Lines_1Payment()}, but we assume a different order. Because of that order, neither the first nor the first two lines (nor all three, as
	 * always) will match the invoice's grand total with their amount. Still we expect the first line to be processed.
	 */
	@Test
	public void testProcessLinesWithInvoice_3Lines_1Payment_132()
	{
		final List<I_ESR_ImportLine> lines = testProcessLinesWithInvoice_common_setup(10, 30, 20);

		final I_ESR_ImportLine esrImportLine1 = lines.get(0);
		final I_ESR_ImportLine esrImportLine2 = lines.get(2);
		final I_ESR_ImportLine esrImportLine3 = lines.get(1);

		allocationDAOMock.addResult(BigDecimal.ZERO); // 1st invocation: ZERO, as nothing was allocated agains the invoice
		allocationDAOMock.addResult(BigDecimal.ZERO); // further invocations: still ZERO, as only the 3 lines' OWN payment was allocated
		allocationDAOMock.addResult(BigDecimal.ZERO);
		allocationDAOMock.addResult(BigDecimal.ZERO);
		allocationDAOMock.addResult(BigDecimal.ZERO);
		allocationDAOMock.addResult(BigDecimal.ZERO);
		allocationDAOMock.addResult(BigDecimal.ZERO);
		allocationDAOMock.addResult(BigDecimal.ZERO);
		allocationDAOMock.addResult(BigDecimal.ZERO);
		Services.registerService(IAllocationDAO.class, allocationDAOMock);

		for (final I_ESR_ImportLine line : lines)
		{
			refresh(line, true);
		}

		esrImportBL.processLinesWithInvoice(Arrays.asList(esrImportLine1, esrImportLine2, esrImportLine3));

		refresh(esrImportLine1, true);
		refresh(esrImportLine2, true);
		refresh(esrImportLine3, true);

		// check the created payments
		assert3Lines_DifferentPayment_Correct(esrImportLine1, esrImportLine3, esrImportLine2);

		// check the line's status and open amounts
		assertThat(esrImportLine1.getESR_Invoice_Openamt(), comparesEqualTo(new BigDecimal(62.5 - 31.0)));
		assertThat(esrImportLine1.getESR_Payment_Action(), nullValue());
		assertThat(esrImportLine1.isProcessed(), is(false));

		assertThat(esrImportLine2.getESR_Invoice_Openamt(), comparesEqualTo(new BigDecimal(62.5 - 31.0 - 62.5)));
		assertThat(esrImportLine2.getESR_Payment_Action(), comparesEqualTo(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Allocate_Payment_With_Current_Invoice));
		assertThat(esrImportLine2.isProcessed(), is(false));

		// make sure that the correct actions are available for the user
		assertOverPaymentActionsAvailable(esrImportLine2);

		assertThat(esrImportLine3.getESR_Invoice_Openamt(), comparesEqualTo(new BigDecimal(-62.5)));
		assertThat(esrImportLine3.getESR_Payment_Action(), nullValue());
		assertThat(esrImportLine3.isProcessed(), is(false));

		// make sure that the correct actions are available for the user
		assertOverPaymentActionsAvailable(esrImportLine3);
	}

	/**
	 * checks if all lines have different payments
	 *
	 * @param esrImportLine1
	 * @param esrImportLine2
	 * @param esrImportLine3
	 */
	private void assert3Lines_DifferentPayment_Correct(final I_ESR_ImportLine esrImportLine1, final I_ESR_ImportLine esrImportLine2, final I_ESR_ImportLine esrImportLine3)
	{
		// there is no perfect match (the amount is not matching perfect), so shall be no allocation and no payment match at this moment

		final PaymentId esrImportLine1PaymentId = PaymentId.ofRepoIdOrNull(esrImportLine1.getC_Payment_ID());
		final I_C_Payment esrLine1Payment = esrImportLine1PaymentId == null ? null
				: paymentDAO.getById(esrImportLine1PaymentId);

		assertThat(esrLine1Payment.getPayAmt(), comparesEqualTo(new BigDecimal(31.0)));
		assertThat(esrLine1Payment.getC_Invoice_ID(), is(0));

		final PaymentId esrImportLine2PaymentId = PaymentId.ofRepoIdOrNull(esrImportLine2.getC_Payment_ID());
		final I_C_Payment esrLine2Payment = esrImportLine2PaymentId == null ? null
				: paymentDAO.getById(esrImportLine2PaymentId);

		assertThat(esrLine2Payment, not(esrLine1Payment));
		assertThat(esrLine2Payment.getPayAmt(), comparesEqualTo(new BigDecimal(31.5)));
		assertThat(esrLine2Payment.getC_Invoice_ID(), is(0));

		final PaymentId esrImportLine3PaymentId = PaymentId.ofRepoIdOrNull(esrImportLine3.getC_Payment_ID());
		final I_C_Payment esrLine3Payment = esrImportLine3PaymentId == null ? null
				: paymentDAO.getById(esrImportLine3PaymentId);

		assertThat(esrLine3Payment, not(esrLine1Payment));
		assertThat(esrLine3Payment.getPayAmt(), comparesEqualTo(new BigDecimal(62.50)));
		assertThat(esrLine3Payment.getC_Invoice_ID(), is(0));
	}

	private void assert3Lines_123Order_Correct(final I_ESR_ImportLine esrImportLine1, final I_ESR_ImportLine esrImportLine2, final I_ESR_ImportLine esrImportLine3)
	{
		assertThat(esrImportLine1.getESR_Invoice_Openamt(), comparesEqualTo(new BigDecimal(62.5 - 31.0)));
		assertThat(esrImportLine1.getESR_Payment_Action(), nullValue());
		assertThat(esrImportLine1.isProcessed(), is(false));

		assertThat(esrImportLine2.getESR_Invoice_Openamt(), comparesEqualTo(new BigDecimal(62.5 - 31.0 - 31.5)));
		assertThat(esrImportLine2.getESR_Payment_Action(), nullValue());
		assertThat(esrImportLine2.isProcessed(), is(false));

		assertThat(esrImportLine3.getESR_Invoice_Openamt(), comparesEqualTo(new BigDecimal(62.5 - 31.0 - 31.5 - 62.5)));
		assertThat(esrImportLine3.getESR_Payment_Action(), comparesEqualTo(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Allocate_Payment_With_Current_Invoice));
		assertThat(esrImportLine3.isProcessed(), is(false));

		// make sure that the correct actions are available for the user
		assertOverPaymentActionsAvailable(esrImportLine3);
	}

	private void assertOverPaymentActionsAvailable(final I_ESR_ImportLine... esrImportLines)
	{
		for (final I_ESR_ImportLine esrImportLine : esrImportLines)
		{
			// make sure that the correct actions are available for the user
			assertThat(ESRValidationRuleTools.evalPaymentActionOK(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Allocate_Payment_With_Next_Invoice, esrImportLine), is(true));
			assertThat(ESRValidationRuleTools.evalPaymentActionOK(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Keep_For_Dunning, esrImportLine), is(false));
			assertThat(ESRValidationRuleTools.evalPaymentActionOK(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Money_Was_Transfered_Back_to_Partner, esrImportLine), is(true));
			if (esrImportLine.getC_Invoice_ID() <= 0)
			{
				assertThat(ESRValidationRuleTools.evalPaymentActionOK(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Unable_To_Assign_Income, esrImportLine), is(true));
			}
			assertThat(ESRValidationRuleTools.evalPaymentActionOK(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Write_Off_Amount, esrImportLine), is(false));
		}
	}

	/**
	 * Creates 3 ESR import lines that all reference the same invoice. The first two line's amounts sum up to the invoice GrandTotal. The third line's amount is equal to the invoice's GrandTotal.
	 *
	 * @param lineNo1 {@code LineNo} value for the 1st created line; use a value >0 to avoid creating the line
	 * @param lineNo2 {@code LineNo} value for the 2nd created line; use a value >0 to avoid creating the line
	 * @param lineNo3 {@code LineNo} value for the 3rd created line; use a value >0 to avoid creating the line
	 * @return
	 */
	private List<I_ESR_ImportLine> testProcessLinesWithInvoice_common_setup(final int lineNo1, final int lineNo2, final int lineNo3)
	{
		final I_AD_Org org = newInstance(I_AD_Org.class);
		org.setValue("153");
		save(org);

		final I_C_BPartner partner = newInstance(I_C_BPartner.class);
		partner.setValue("449369");
		save(partner);

		final I_C_DocType type = newInstance(I_C_DocType.class);
		type.setDocBaseType(X_C_DocType.DOCBASETYPE_ARInvoice);
		save(type);

		final CurrencyId currencyEUR = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		// bank account
		final I_C_BP_BankAccount account = newInstance(I_C_BP_BankAccount.class, contextProvider);
		account.setC_Bank_ID(999);
		account.setIsEsrAccount(true);
		account.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
		account.setAD_User_ID(Env.getAD_User_ID(getCtx()));
		account.setC_Currency_ID(currencyEUR.getRepoId());
		save(account);

		final BigDecimal invoiceGrandTotal = INVOICE_GRANDTOTAL;
		invoice = newInstance(I_C_Invoice.class);
		invoice.setAD_Org_ID(org.getAD_Org_ID());
		invoice.setGrandTotal(invoiceGrandTotal);
		invoice.setC_BPartner_ID(partner.getC_BPartner_ID());
		invoice.setDocumentNo("452432");
		invoice.setAD_Org_ID(org.getAD_Org_ID());
		invoice.setC_DocType_ID(type.getC_DocType_ID());
		save(invoice);

		final I_ESR_Import esrImport = newInstance(I_ESR_Import.class);
		esrImport.setC_BP_BankAccount_ID(account.getC_BP_BankAccount_ID());
		save(esrImport);

		final I_ESR_ImportFile esrImportFile = createImportFile(esrImport);

		final List<I_ESR_ImportLine> lines = new ArrayList<>();
		if (lineNo1 > 0)
		{
			final I_ESR_ImportLine esrImportLine1 = newInstance(I_ESR_ImportLine.class);
			esrImportLine1.setESR_Import_ID(esrImport.getESR_Import_ID());
			esrImportLine1.setESR_ImportFile_ID(esrImportFile.getESR_ImportFile_ID());
			esrImportLine1.setC_BPartner_ID(partner.getC_BPartner_ID());
			esrImportLine1.setC_Invoice_ID(invoice.getC_Invoice_ID());
			esrImportLine1.setAD_Org_ID(org.getAD_Org_ID());
			esrImportLine1.setAmount(ESR_LINE_1_AMOUNT);
			esrImportLine1.setESR_Invoice_Openamt(invoiceGrandTotal);
			esrImportLine1.setLineNo(lineNo1);
			save(esrImportLine1);
			lines.add(esrImportLine1);
		}
		if (lineNo2 > 0)
		{
			final I_ESR_ImportLine esrImportLine2 = newInstance(I_ESR_ImportLine.class);
			esrImportLine2.setESR_Import_ID(esrImport.getESR_Import_ID());
			esrImportLine2.setESR_ImportFile_ID(esrImportFile.getESR_ImportFile_ID());
			esrImportLine2.setC_BPartner_ID(partner.getC_BPartner_ID());
			esrImportLine2.setC_Invoice_ID(invoice.getC_Invoice_ID());
			esrImportLine2.setAD_Org_ID(org.getAD_Org_ID());
			esrImportLine2.setAmount(new BigDecimal("31.5"));
			esrImportLine2.setESR_Invoice_Openamt(invoiceGrandTotal);
			esrImportLine2.setLineNo(lineNo2);
			save(esrImportLine2);
			lines.add(esrImportLine2);
		}
		if (lineNo3 > 0)
		{
			final I_ESR_ImportLine esrImportLine3 = newInstance(I_ESR_ImportLine.class);
			esrImportLine3.setESR_Import_ID(esrImport.getESR_Import_ID());
			esrImportLine3.setESR_ImportFile_ID(esrImportFile.getESR_ImportFile_ID());
			esrImportLine3.setC_BPartner_ID(partner.getC_BPartner_ID());
			esrImportLine3.setC_Invoice_ID(invoice.getC_Invoice_ID());
			esrImportLine3.setAD_Org_ID(org.getAD_Org_ID());
			esrImportLine3.setAmount(invoiceGrandTotal);
			esrImportLine3.setESR_Invoice_Openamt(invoiceGrandTotal);
			esrImportLine3.setLineNo(lineNo3);
			save(esrImportLine3);
			lines.add(esrImportLine3);
		}

		return lines;
	}

	private static class AllocationDAOMock extends PlainAllocationDAO
	{
		private int invocationCount = 0;
		private final List<BigDecimal> returnValues = new ArrayList<>();

		void addResult(final BigDecimal returnValue)
		{
			returnValues.add(returnValue);
		}

		@Override
		public BigDecimal retrieveAllocatedAmtIgnoreGivenPaymentIDs(org.compiere.model.I_C_Invoice invoice, Set<Integer> ignored)
		{
			if (returnValues.size() < invocationCount + 1)
			{
				throw new AdempiereException("unexpected invocation");
			}

			final BigDecimal returnValue = returnValues.get(invocationCount);
			invocationCount++;
			return returnValue;
		}
	}

	@Test
	public void testUpdateOpenAmtAndStatusDontSave1()
	{
		final BigDecimal externallyAllocatedAmt = new BigDecimal("70");
		assertThat(externallyAllocatedAmt, greaterThan(INVOICE_GRANDTOTAL)); // guard
		allocationDAOMock.addResult(externallyAllocatedAmt);
		Services.registerService(IAllocationDAO.class, allocationDAOMock);

		final List<I_ESR_ImportLine> lines = testProcessLinesWithInvoice_common_setup(10, -1, -1);

		I_C_Payment payment = newInstance(I_C_Payment.class);
		payment.setPayAmt(ESR_LINE_1_AMOUNT);
		payment.setC_BPartner_ID(lines.get(0).getC_BPartner_ID());
		payment.setDocumentNo("452432");
		payment.setAD_Org_ID(lines.get(0).getAD_Org_ID());
		save(payment);

		lines.get(0).setC_Payment_ID(payment.getC_Payment_ID());

		esrImportBL.updateOpenAmtAndStatusDontSave(invoice, lines);

		for (final I_ESR_ImportLine line : lines)
		{
			assertThat(line.getESR_Payment_Action(), is(nullValue()));
		}

		assertThat(lines.get(0).getESR_Invoice_Openamt(), comparesEqualTo(INVOICE_GRANDTOTAL.subtract(ESR_LINE_1_AMOUNT).subtract(externallyAllocatedAmt)));

	}

	@Test
	public void testUpdateOpenAmtAndStatusDontSave2a()
	{
		final BigDecimal alreadyAllocatedAmt = BigDecimal.ZERO;
		allocationDAOMock.addResult(alreadyAllocatedAmt);
		Services.registerService(IAllocationDAO.class, allocationDAOMock);

		final List<I_ESR_ImportLine> lines = testProcessLinesWithInvoice_common_setup(10, -1, -1);

		final I_ESR_ImportLine line1 = lines.get(0);
		line1.setC_Payment_ID(0); // if the line has no payment assigned, then we assume that ESR_LINE_1_AMOUNT has not yet been allocated against the invoice

		esrImportBL.updateOpenAmtAndStatusDontSave(invoice, lines);

		// assertThat(lines.get(0).getESR_Payment_Action(), is(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Fit_Amounts));
		assertThat("the invoice is completely unpaid, and this line's amount was not yet allocated either => the line's open amount is the invoice's grand-total minus line's pay amount",
				   line1.getESR_Invoice_Openamt(), comparesEqualTo(INVOICE_GRANDTOTAL.subtract(ESR_LINE_1_AMOUNT)));
	}

	@Test
	public void testUpdateOpenAmtAndStatusDontSave2b()
	{
		final BigDecimal externallAllocatedAmt = BigDecimal.ZERO;
		allocationDAOMock.addResult(externallAllocatedAmt);
		Services.registerService(IAllocationDAO.class, allocationDAOMock);

		final List<I_ESR_ImportLine> lines = testProcessLinesWithInvoice_common_setup(10, -1, -1);

		final I_ESR_ImportLine line1 = lines.get(0);

		I_C_Payment payment = newInstance(I_C_Payment.class);
		payment.setPayAmt(ESR_LINE_1_AMOUNT);
		payment.setC_BPartner_ID(lines.get(0).getC_BPartner_ID());
		payment.setDocumentNo("452432");
		payment.setAD_Org_ID(lines.get(0).getAD_Org_ID());
		save(payment);

		// if the line has a C_Payment_ID>0 payment assigned, then we assume that ESR_LINE_1_AMOUNT was allocated against the invoice
		line1.setC_Payment_ID(payment.getC_Payment_ID());

		esrImportBL.updateOpenAmtAndStatusDontSave(invoice, lines);

		assertThat("the invoice's allocated sum is ZERO, but this line's amount is ALREADY a part of that sum, so it shall not count to reduce the overall open amount",
				   line1.getESR_Invoice_Openamt(), comparesEqualTo(INVOICE_GRANDTOTAL.subtract(externallAllocatedAmt).subtract(ESR_LINE_1_AMOUNT)));
	}

	@Test
	public void testUpdateOpenAmtAndStatusDontSave3a()
	{
		final BigDecimal alreadyAllocatedAmt = new BigDecimal("20");
		allocationDAOMock.addResult(alreadyAllocatedAmt);
		Services.registerService(IAllocationDAO.class, allocationDAOMock);

		final List<I_ESR_ImportLine> lines = testProcessLinesWithInvoice_common_setup(10, -1, -1);

		final I_ESR_ImportLine line1 = lines.get(0);
		line1.setC_Payment_ID(0); // if the line has no payment assigned, then we assume that ESR_LINE_1_AMOUNT has not yet been allocated against the invoice

		esrImportBL.updateOpenAmtAndStatusDontSave(invoice, lines);

		// assertThat(lines.get(0).getESR_Payment_Action(), is(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Fit_Amounts));
		assertThat("'this line's amount has *not* been allocated against the invoice, so it counts in addition to the alreadyAllocatedAmt",
				   line1.getESR_Invoice_Openamt(), comparesEqualTo(INVOICE_GRANDTOTAL.subtract(alreadyAllocatedAmt).subtract(ESR_LINE_1_AMOUNT)));
	}

	@Test
	public void testUpdateOpenAmtAndStatusDontSave3b()
	{
		final BigDecimal alreadyAllocatedAmt = new BigDecimal("20");
		allocationDAOMock.addResult(alreadyAllocatedAmt);
		Services.registerService(IAllocationDAO.class, allocationDAOMock);

		final List<I_ESR_ImportLine> lines = testProcessLinesWithInvoice_common_setup(10, -1, -1);

		I_C_Payment payment = newInstance(I_C_Payment.class);
		payment.setPayAmt(ESR_LINE_1_AMOUNT);
		payment.setC_BPartner_ID(lines.get(0).getC_BPartner_ID());
		payment.setDocumentNo("452432");
		payment.setAD_Org_ID(lines.get(0).getAD_Org_ID());
		save(payment);

		final I_ESR_ImportLine line1 = lines.get(0);
		line1.setC_Payment_ID(payment.getC_Payment_ID()); // if the line has a C_Payment_ID>0 payment assigned, then we assume that ESR_LINE_1_AMOUNT is *already* allocated against the invoice and is therefore part of the
		// sum that makes up alreadyAllocatedAmt

		esrImportBL.updateOpenAmtAndStatusDontSave(invoice, lines);

		// assertThat(lines.get(0).getESR_Payment_Action(), is(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Fit_Amounts));
		assertThat("'this line has been allocated, still the invoice's allocation SUM is 20 => the line's open amount is the invoice's open amount",
				   line1.getESR_Invoice_Openamt(), comparesEqualTo(INVOICE_GRANDTOTAL.subtract(alreadyAllocatedAmt).subtract(ESR_LINE_1_AMOUNT)));
	}

}
