package de.metas.payment.esr.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.ByteArrayInputStream;

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

import java.math.BigDecimal;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.currency.CurrencyCode;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.document.refid.model.I_C_ReferenceNo;
import de.metas.document.refid.model.I_C_ReferenceNo_Doc;
import de.metas.document.refid.model.I_C_ReferenceNo_Type;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.interfaces.I_C_BPartner;
import de.metas.interfaces.I_C_DocType;
import de.metas.money.CurrencyId;
import de.metas.payment.esr.ESRTestBase;
import de.metas.payment.esr.ESRTestUtil;
import de.metas.payment.esr.dataimporter.impl.v11.ESRTransactionLineMatcherUtil;
import de.metas.payment.esr.model.I_C_BP_BankAccount;
import de.metas.payment.esr.model.I_ESR_Import;
import de.metas.payment.esr.model.I_ESR_ImportLine;
import de.metas.util.Services;

public class ESRRegularLineMatcherTest extends ESRTestBase
{
	private I_C_ReferenceNo_Type refNoType;

	@Override
	public void init()
	{
		POJOWrapper.setDefaultStrictValues(false);

		refNoType = newInstance(I_C_ReferenceNo_Type.class);
		refNoType.setName("InvoiceReference");
		save(refNoType);
	}

	/**
	 * TODO: finish the infrastructure for testing regular line (create a plan class for bank account DAO and write the appropriate methods). When finished, this test will cover a lot of the
	 * business
	 * logic from ESR:)
	 */
	@Test
	public void test_regularLine()
	{
		final String esrImportLineText = "00201059931000000001050153641700120686900000040000012  190013011813011813012100015000400000000000000";

		final I_ESR_Import esrImport = createImport();

		final CurrencyId currencyEUR = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		final I_C_BP_BankAccount account = createBankAccount(true,
				Env.getAD_Org_ID(getCtx()),
				Env.getAD_User_ID(getCtx()),
				"01-059931-0",
				currencyEUR);

		esrImport.setC_BP_BankAccount_ID(account.getC_BP_BankAccount_ID());
		save(esrImport);

		esrImportBL.loadAndEvaluateESRImportStream(esrImport, new ByteArrayInputStream(esrImportLineText.getBytes()));

		assertThat(esrImport.isValid()).isFalse();
	}

	// task 09861
	@Test
	public void test_regularLineBPValueGreaterThan1000_fallback_from_invoice()
	{
		final I_C_BPartner bp = newInstance(I_C_BPartner.class);
		bp.setValue("G1386");
		bp.setC_BPartner_ID(15);
		save(bp);

		final String esrImportLineText = "0020105993102345370001000013860016436390000000100000000000016050116050116050100000000000000000000000";

		final I_ESR_Import esrImport = createImport();

		final CurrencyId currencyEUR = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		final I_C_BP_BankAccount account = createBankAccount(true,
				Env.getAD_Org_ID(getCtx()),
				Env.getAD_User_ID(getCtx()),
				"01-059931-0",
				currencyEUR);

		account.setC_BPartner_ID(bp.getC_BPartner_ID());
		save(account);

		esrImport.setC_BP_BankAccount_ID(account.getC_BP_BankAccount_ID());
		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setC_BPartner_ID(bp.getC_BPartner_ID());
		invoice.setDocumentNo("164363");
		save(invoice);

		esrImportBL.loadAndEvaluateESRImportStream(esrImport, new ByteArrayInputStream(esrImportLineText.getBytes()));

		final I_ESR_ImportLine esrImportLine = ESRTestUtil.retrieveSingleLine(esrImport);
		assertThat(esrImportLine.getC_BPartner_ID()).isLessThanOrEqualTo(0); // guard. we assume that the matcher did not know how to find 'bp'

		esrImportLine.setC_Invoice(invoice);
		save(esrImportLine);

		esrImportBL.evaluateLine(esrImport, esrImportLine);

		assertThat(esrImportLine.getC_BPartner_ID())
				.as("BPartner not the same in ESR Line and Invoice")
				.isEqualTo(bp.getC_BPartner_ID());
	}

	// task 09861
	@Test
	public void test_regularLineBPValueGreaterThan1000_0Included_fallback_from_invoice()
	{
		final I_C_BPartner bp = newInstance(I_C_BPartner.class);
		bp.setValue("G01386");
		bp.setC_BPartner_ID(15);
		save(bp);

		final String esrImportLineText = "0020105993102345370001000013860016436390000000100000000000016050116050116050100000000000000000000000";

		final I_ESR_Import esrImport = createImport();

		final CurrencyId currencyEUR = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		final I_C_BP_BankAccount account = createBankAccount(true,
				Env.getAD_Org_ID(getCtx()),
				Env.getAD_User_ID(getCtx()),
				"01-059931-0",
				currencyEUR);

		account.setC_BPartner_ID(bp.getC_BPartner_ID());
		save(account);

		esrImport.setC_BP_BankAccount_ID(account.getC_BP_BankAccount_ID());

		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setC_BPartner_ID(bp.getC_BPartner_ID());
		invoice.setDocumentNo("164363");

		save(invoice);

		esrImportBL.loadAndEvaluateESRImportStream(esrImport, new ByteArrayInputStream(esrImportLineText.getBytes()));

		final I_ESR_ImportLine esrImportLine = ESRTestUtil.retrieveSingleLine(esrImport);
		assertThat(esrImportLine.getC_BPartner_ID()).isLessThanOrEqualTo(0); // guard. we assume that the matcher did not know how to find 'bp'

		esrImportLine.setC_Invoice(invoice);
		save(esrImportLine);

		esrImportBL.evaluateLine(esrImport, esrImportLine);

		assertThat(esrImportLine.getC_BPartner_ID())
				.as("BPartner not the same in ESR Line and Invoice")
				.isEqualTo(bp.getC_BPartner_ID());
	}

	// task 09861
	@Test
	public void test_regularLineBPValueGreaterThan10000_fallback_from_invoice()
	{
		final I_C_BPartner bp = newInstance(I_C_BPartner.class);
		bp.setValue("G11386");
		bp.setC_BPartner_ID(15);
		save(bp);

		final String esrImportLineText = "0020105993102345370001000113860016436390000000100000000000016050116050116050100000000000000000000000";

		final I_ESR_Import esrImport = createImport();

		final CurrencyId currencyEUR = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		final I_C_BP_BankAccount account = createBankAccount(true,
				Env.getAD_Org_ID(getCtx()),
				Env.getAD_User_ID(getCtx()),
				"01-059931-0",
				currencyEUR);

		account.setC_BPartner_ID(bp.getC_BPartner_ID());
		save(account);

		esrImport.setC_BP_BankAccount_ID(account.getC_BP_BankAccount_ID());

		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setC_BPartner_ID(bp.getC_BPartner_ID());
		invoice.setDocumentNo("164363");
		save(invoice);

		esrImportBL.loadAndEvaluateESRImportStream(esrImport, new ByteArrayInputStream(esrImportLineText.getBytes()));

		final I_ESR_ImportLine esrImportLine = ESRTestUtil.retrieveSingleLine(esrImport);
		assertThat(esrImportLine.getC_BPartner_ID()).isLessThanOrEqualTo(0); // guard. we assume that the matcher did not know how to find 'bp'

		esrImportLine.setC_Invoice(invoice);
		save(esrImportLine);

		esrImportBL.evaluateLine(esrImport, esrImportLine);

		assertThat(esrImportLine.getC_BPartner_ID())
				.as("BPartner not the same in ESR Line and Invoice")
				.isEqualTo(bp.getC_BPartner_ID());
	}

	/**
	 * Set up a BPartner value="G0007", but the incoming esr line shall contain the bpartner substring "0007". Matching shall still work, as soon as the invoice is known
	 */
	// task 09861
	@Test
	public void test_regularLineBPValue_0007_fallback_from_invoice()
	{
		final I_C_BPartner bp = newInstance(I_C_BPartner.class);
		bp.setValue("G0007");
		bp.setC_BPartner_ID(15);
		save(bp);

		final I_ESR_Import esrImport = createImport();

		final CurrencyId currencyEUR = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		final I_C_BP_BankAccount account = createBankAccount(true,
				Env.getAD_Org_ID(getCtx()),
				Env.getAD_User_ID(getCtx()),
				"01-059931-0",
				currencyEUR);

		account.setC_BPartner_ID(bp.getC_BPartner_ID());
		save(account);

		esrImport.setC_BP_BankAccount_ID(account.getC_BP_BankAccount_ID());
		save(esrImport);

		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setC_BPartner_ID(bp.getC_BPartner_ID());
		invoice.setDocumentNo("164363");
		save(invoice);

		final String esrImportLineText = "0020105993102345370001000000070016436390000000100000000000016050116050116050100000000000000000000000";
		esrImportBL.loadAndEvaluateESRImportStream(esrImport, new ByteArrayInputStream(esrImportLineText.getBytes()));
		final I_ESR_ImportLine esrImportLine = ESRTestUtil.retrieveSingleLine(esrImport);
		assertThat(esrImportLine.getC_BPartner_ID()).isLessThanOrEqualTo(0); // guard. we assume that the matcher did not know how to find 'bp'

		esrImportLine.setC_Invoice(invoice);
		save(esrImportLine);
		esrImportBL.evaluateLine(esrImport, esrImportLine);

		assertThat(esrImportLine.getC_BPartner_ID())
				.as("BPartner not the same in ESR Line and Invoice")
				.isEqualTo(bp.getC_BPartner_ID());
	}

	@Test
	@Disabled
	/* This case never happened in real world. To be fixed when needed */
	public void test_invalidLength()
	{
		final String esrImportLineText = "00201059931000000001050153641700120686900000040000012  190013011813011813012100015000400000000000000142";

		final I_ESR_Import esrImport = createImport();

		final CurrencyId currencyEUR = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		final I_C_BP_BankAccount account = createBankAccount(true,
				Env.getAD_Org_ID(getCtx()),
				Env.getAD_User_ID(getCtx()),
				"01-059931-0",
				currencyEUR);

		esrImport.setC_BP_BankAccount_ID(account.getC_BP_BankAccount_ID());
		save(esrImport);

		esrImportBL.loadAndEvaluateESRImportStream(esrImport, new ByteArrayInputStream(esrImportLineText.getBytes()));
		final I_ESR_ImportLine esrImportLine = ESRTestUtil.retrieveSingleLine(esrImport);

		assertThat(esrImportLine.isValid()).isFalse();
		assertThat(esrImportLine.getMatchErrorMsg()).isNull();
		assertThat(esrImportLine.getImportErrorMsg()).isEqualTo("ESR_Wrong_Regular_Line_Length_[103]");
	}

	@Test
	public void test_regularLine_RenderedAccountNo()
	{
		final String esrImportLineText = "00201059931000000001050153641700120686900000040000012  190013011813011813012100015000400000000000000";

		final I_ESR_Import esrImport = createImport();

		final CurrencyId currencyEUR = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		final I_C_BP_BankAccount account = createBankAccount(true,
				Env.getAD_Org_ID(getCtx()),
				Env.getAD_User_ID(getCtx()),
				"01-059931-0",
				currencyEUR);

		esrImport.setC_BP_BankAccount_ID(account.getC_BP_BankAccount_ID());
		save(esrImport);

		esrImportBL.loadAndEvaluateESRImportStream(esrImport, new ByteArrayInputStream(esrImportLineText.getBytes()));
		final I_ESR_ImportLine esrImportLine = ESRTestUtil.retrieveSingleLine(esrImport);

		System.out.println(esrImportLine.getESRPostParticipantNumber() + "    ->    " + account.getESR_RenderedAccountNo().split("-"));

		final String[] unrenderedAccountNoParts = account.getESR_RenderedAccountNo().split("-");
		final String unrenderedPostAccountNo = unrenderedAccountNoParts[0] + unrenderedAccountNoParts[1] + unrenderedAccountNoParts[2];

		assertThat(esrImportLine.getESRPostParticipantNumber()).isEqualTo(unrenderedPostAccountNo);
	}

	@Test
	public void test_regularLine_WrongRenderedAccountNo()
	{
		final String esrImportLineText = "00288859931000000001050153641700120686900000040000012  190013011813011813012100015000400000000000000";

		final I_ESR_Import esrImport = createImport();

		final CurrencyId currencyEUR = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		final I_C_BP_BankAccount account = createBankAccount(true,
				Env.getAD_Org_ID(getCtx()),
				Env.getAD_User_ID(getCtx()),
				"01-059931-0",
				currencyEUR);

		esrImport.setC_BP_BankAccount_ID(account.getC_BP_BankAccount_ID());
		save(esrImport);

		esrImportBL.loadAndEvaluateESRImportStream(esrImport, new ByteArrayInputStream(esrImportLineText.getBytes()));
		final I_ESR_ImportLine esrImportLine = ESRTestUtil.retrieveSingleLine(esrImport);

		final String[] unrenderedAccountNoParts = account.getESR_RenderedAccountNo().split("-");
		final String unrenderedPostAccountNo = unrenderedAccountNoParts[0] + unrenderedAccountNoParts[1] + unrenderedAccountNoParts[2];

		assertThat(esrImportLine.getESRPostParticipantNumber()).isNotEqualTo(unrenderedPostAccountNo);
	}

	@Test
	public void test_regularLine_RenderedAccountNo_From_ESRPostFinanceUserNumber()
	{
		final String esrImportLineText = "00201059931000000001050153641700120686900000040000012  190013011813011813012100015000400000000000000";

		final I_ESR_Import esrImport = createImport();

		final CurrencyId currencyEUR = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		final I_C_BP_BankAccount account = createBankAccount(true,
				Env.getAD_Org_ID(getCtx()),
				Env.getAD_User_ID(getCtx()),
				"01-888888-0",
				currencyEUR);

		final String esrNoForPostFinanceUser = "010599310";

		createPostFinanceUserNumber(account, esrNoForPostFinanceUser);

		esrImport.setC_BP_BankAccount_ID(account.getC_BP_BankAccount_ID());
		save(esrImport);

		esrImportBL.loadAndEvaluateESRImportStream(esrImport, new ByteArrayInputStream(esrImportLineText.getBytes()));
		final I_ESR_ImportLine esrImportLine = ESRTestUtil.retrieveSingleLine(esrImport);

		assertThat(esrImportLine.getESRPostParticipantNumber()).isEqualTo(esrNoForPostFinanceUser);
	}

	@Test
	public void test_regularLine_RenderedAccountNo_From_ESRPostFinanceUserNumber_Rendered()
	{
		final String esrImportLineText = "00201059931000000001050153641700120686900000040000012  190013011813011813012100015000400000000000000";

		final I_ESR_Import esrImport = createImport();

		final CurrencyId currencyEUR = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		final I_C_BP_BankAccount account = createBankAccount(true,
				Env.getAD_Org_ID(getCtx()),
				Env.getAD_User_ID(getCtx()),
				"01-888888-0",
				currencyEUR);

		final String renderedEsrNoForPostFinanceUser = "01-059931-0";
		final String unRenderedEsrNoForPostFinanceUser = "010599310";

		createPostFinanceUserNumber(account, renderedEsrNoForPostFinanceUser);

		esrImport.setC_BP_BankAccount_ID(account.getC_BP_BankAccount_ID());
		save(esrImport);

		esrImportBL.loadAndEvaluateESRImportStream(esrImport, new ByteArrayInputStream(esrImportLineText.getBytes()));
		final I_ESR_ImportLine esrImportLine = ESRTestUtil.retrieveSingleLine(esrImport);

		assertThat(esrImportLine.getESRPostParticipantNumber()).isEqualTo(unRenderedEsrNoForPostFinanceUser);
	}

	@Test
	public void test_regularLine_RenderedAccountNo_From_ESRPostFinanceUserNumber_RenderedWrong()
	{
		final String esrImportLineText = "00201059931000000001050153641700120686900000040000012  190013011813011813012100015000400000000000000";

		final I_ESR_Import esrImport = createImport();

		final CurrencyId currencyEUR = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		final I_C_BP_BankAccount account = createBankAccount(true,
				Env.getAD_Org_ID(getCtx()),
				Env.getAD_User_ID(getCtx()),
				"01-888888-0",
				currencyEUR);

		final String esrNoForPostFinanceUser = "01-0599310";

		createPostFinanceUserNumber(account, esrNoForPostFinanceUser);

		esrImport.setC_BP_BankAccount_ID(account.getC_BP_BankAccount_ID());
		save(esrImport);

		assertThatThrownBy(() -> esrImportBL.loadAndEvaluateESRImportStream(esrImport, new ByteArrayInputStream(esrImportLineText.getBytes())))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("01-0599310 contains three '-' separated parts");
	}

	@Test
	public void test_regularLine_RenderedAccountNo_WrongESRPostFinanceUserNumber()
	{
		final String esrImportLineText = "00201059931000000001050153641700120686900000040000012  190013011813011813012100015000400000000000000";

		final I_ESR_Import esrImport = createImport();

		final CurrencyId currencyEUR = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		final I_C_BP_BankAccount account = createBankAccount(true,
				Env.getAD_Org_ID(getCtx()),
				Env.getAD_User_ID(getCtx()),
				"01-888888-0",
				currencyEUR);

		final String esrNoForPostFinanceUser = "088888880";

		createPostFinanceUserNumber(account, esrNoForPostFinanceUser);

		esrImport.setC_BP_BankAccount_ID(account.getC_BP_BankAccount_ID());
		save(esrImport);

		esrImportBL.loadAndEvaluateESRImportStream(esrImport, new ByteArrayInputStream(esrImportLineText.getBytes()));
		final I_ESR_ImportLine esrImportLine = ESRTestUtil.retrieveSingleLine(esrImport);

		final String[] unrenderedAccountNoParts = account.getESR_RenderedAccountNo().split("-");
		final String unrenderedPostAccountNo = unrenderedAccountNoParts[0] + unrenderedAccountNoParts[1] + unrenderedAccountNoParts[2];

		assertThat(esrImportLine.getESRPostParticipantNumber()).isNotEqualTo(esrNoForPostFinanceUser);
		assertThat(esrImportLine.getESRPostParticipantNumber()).isNotEqualTo(unrenderedPostAccountNo);
	}

	@Test
	public void test_regularLine_ReferenceNumber()
	{
		Services.get(IDocumentNoBuilderFactory.class);

		final String esrImportLineText = "00201059931000000001050153641700120686900000040000012  190013011813011813012100015000400000000000000";
		final String referenceNumberStr = ESRTransactionLineMatcherUtil.extractReferenceNumberStr(esrImportLineText);

		final I_ESR_Import esrImport = createImport();

		final CurrencyId currencyEUR = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		final I_C_BP_BankAccount account = createBankAccount(true,
				Env.getAD_Org_ID(getCtx()),
				Env.getAD_User_ID(getCtx()),
				"01-059931-0",
				currencyEUR);

		esrImport.setC_BP_BankAccount_ID(account.getC_BP_BankAccount_ID());
		save(esrImport);

		final I_C_ReferenceNo referenceNo = newInstance(I_C_ReferenceNo.class);
		referenceNo.setReferenceNo(referenceNumberStr);
		referenceNo.setC_ReferenceNo_Type(refNoType);
		referenceNo.setIsManual(false);
		save(referenceNo);

		final I_C_BPartner partner = newInstance(I_C_BPartner.class);
		partner.setValue("partner1");
		save(partner);

		final I_C_DocType type = newInstance(I_C_DocType.class);
		type.setDocBaseType(X_C_DocType.DOCBASETYPE_APCreditMemo);
		save(type);

		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setC_BPartner_ID(partner.getC_BPartner_ID());
		invoice.setDocumentNo("000120686");
		invoice.setC_DocType_ID(type.getC_DocType_ID());
		save(invoice);

		final I_C_ReferenceNo_Doc esrReferenceNumberDocument = newInstance(I_C_ReferenceNo_Doc.class);
		esrReferenceNumberDocument.setAD_Table_ID(Services.get(IADTableDAO.class).retrieveTableId(I_C_Invoice.Table_Name));
		esrReferenceNumberDocument.setRecord_ID(invoice.getC_Invoice_ID());
		esrReferenceNumberDocument.setC_ReferenceNo(referenceNo);

		save(esrReferenceNumberDocument);

		I_C_AllocationLine allocAmt = newInstance(I_C_AllocationLine.class);
		allocAmt.setWriteOffAmt(new BigDecimal(10.0));
		allocAmt.setC_Invoice_ID(invoice.getC_Invoice_ID());
		save(allocAmt);

		esrImportBL.loadAndEvaluateESRImportStream(esrImport, new ByteArrayInputStream(esrImportLineText.getBytes()));
		final I_ESR_ImportLine esrImportLine = ESRTestUtil.retrieveSingleLine(esrImport);

		// I_ESR_Import esrImport = esrImportLine.getESR_Import();
		// Services.get(IESRImportBL.class).process(esrImport);
		//
		// assertNoErrors(Arrays.asList(esrImportLine));
		assertThat(esrImportLine.getC_ReferenceNo_ID()).as("Reference number not found.").isEqualTo(referenceNo.getC_ReferenceNo_ID());
	}

	@Test
	public void test_regularLine_No_Invoice_In_RefNo()
	{
		final String esrImportLineText = "00201059931000000001050153641700120686900000040000012  190013011813011813012100015000400000000000000";

		final I_ESR_Import esrImport = createImport();

		final CurrencyId currencyEUR = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		final I_C_BP_BankAccount account = createBankAccount(true,
				Env.getAD_Org_ID(getCtx()),
				Env.getAD_User_ID(getCtx()),
				"01-059931-0",
				currencyEUR);

		esrImport.setC_BP_BankAccount_ID(account.getC_BP_BankAccount_ID());
		save(esrImport);

		final I_C_BPartner partner = newInstance(I_C_BPartner.class);
		partner.setValue("partner1");
		save(partner);

		final I_AD_Org org = newInstance(I_AD_Org.class);
		org.setValue("105");
		save(org);

		I_C_AllocationLine allocAmt = newInstance(I_C_AllocationLine.class);
		allocAmt.setWriteOffAmt(new BigDecimal(10.0));
		// allocAmt.setC_Invoice_ID(invoice.getC_Invoice_ID());
		save(allocAmt);

		esrImportBL.loadAndEvaluateESRImportStream(esrImport, new ByteArrayInputStream(esrImportLineText.getBytes()));
		final I_ESR_ImportLine esrImportLine = ESRTestUtil.retrieveSingleLine(esrImport);

		// System.out.println(org.getAD_Org_ID() + " -------> " + esrImportLine.getOrg());

		assertThat(esrImportLine.getOrg_ID()).isEqualTo(org.getAD_Org_ID());
	}

	@Test
	public void test_regularLine_manual_refNo()
	{
		final String esrImportLineText = "00201059931000000001050153641700120686900000040000012  190013011813011813012100015000400000000000000";
		final String referenceNumberStr = ESRTransactionLineMatcherUtil.extractReferenceNumberStr(esrImportLineText);
		final I_AD_Org org = getAD_Org();

		final I_ESR_Import esrImport = createImport();

		final CurrencyId currencyEUR = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		final I_C_BP_BankAccount account = createBankAccount(true,
				Env.getAD_Org_ID(getCtx()),
				Env.getAD_User_ID(getCtx()),
				"01-059931-0",
				currencyEUR);

		esrImport.setC_BP_BankAccount_ID(account.getC_BP_BankAccount_ID());
		save(esrImport);

		final I_C_BPartner partner = newInstance(I_C_BPartner.class);
		partner.setValue("partner1");
		partner.setAD_Org_ID(org.getAD_Org_ID());
		save(partner);

		esrImport.setAD_Org_ID(org.getAD_Org_ID());
		save(esrImport);

		final I_C_DocType type = newInstance(I_C_DocType.class);
		type.setDocBaseType(X_C_DocType.DOCBASETYPE_APCreditMemo);
		save(type);

		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setAD_Org_ID(org.getAD_Org_ID());
		invoice.setC_BPartner_ID(partner.getC_BPartner_ID());
		invoice.setDocumentNo("000120686");
		invoice.setAD_Org_ID(org.getAD_Org_ID());
		invoice.setC_DocType_ID(type.getC_DocType_ID());
		save(invoice);

		final I_C_ReferenceNo referenceNo = newInstance(I_C_ReferenceNo.class);
		referenceNo.setReferenceNo(referenceNumberStr);
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

		esrImportBL.loadAndEvaluateESRImportStream(esrImport, new ByteArrayInputStream(esrImportLineText.getBytes()));
		final I_ESR_ImportLine esrImportLine = ESRTestUtil.retrieveSingleLine(esrImport);

		assertThat(esrImportLine.isESR_IsManual_ReferenceNo()).isTrue();
		assertThat(esrImportLine.getAD_Org_ID()).isEqualTo(org.getAD_Org_ID());
		assertThat(esrImportLine.getC_BPartner_ID()).isEqualTo(partner.getC_BPartner_ID());
		assertThat(esrImportLine.getC_Invoice_ID()).isEqualTo(invoice.getC_Invoice_ID());
	}
}
