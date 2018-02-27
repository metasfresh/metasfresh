package de.metas.payment.esr.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

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
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Test;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.document.documentNo.IDocumentNoBuilderFactory;
import de.metas.document.refid.model.I_C_ReferenceNo;
import de.metas.document.refid.model.I_C_ReferenceNo_Doc;
import de.metas.document.refid.model.I_C_ReferenceNo_Type;
import de.metas.interfaces.I_C_BPartner;
import de.metas.interfaces.I_C_DocType;
import de.metas.payment.esr.ESRTestBase;
import de.metas.payment.esr.ESRTestUtil;
import de.metas.payment.esr.model.I_C_BP_BankAccount;
import de.metas.payment.esr.model.I_ESR_Import;
import de.metas.payment.esr.model.I_ESR_ImportLine;

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

		final I_C_BP_BankAccount account = newInstance(I_C_BP_BankAccount.class);

		account.setIsEsrAccount(true);
		account.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
		account.setAD_User_ID(Env.getAD_User_ID(getCtx()));
		account.setESR_RenderedAccountNo("01-059931-0");

		save(account);

		esrImport.setC_BP_BankAccount(account);
		save(esrImport);

		esrImportBL.loadAndEvaluateESRImportStream(esrImport, new ByteArrayInputStream(esrImportLineText.getBytes()));

		Assert.assertEquals("Invalid IsValid", false, esrImport.isValid());
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

		final I_C_BP_BankAccount account = newInstance(I_C_BP_BankAccount.class);

		account.setIsEsrAccount(true);
		account.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
		account.setAD_User_ID(Env.getAD_User_ID(getCtx()));
		account.setESR_RenderedAccountNo("01-059931-0");

		account.setC_BPartner(bp);
		save(account);

		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setC_BPartner(bp);
		invoice.setDocumentNo("164363");
		save(invoice);

		esrImportBL.loadAndEvaluateESRImportStream(esrImport, new ByteArrayInputStream(esrImportLineText.getBytes()));

		final I_ESR_ImportLine esrImportLine = ESRTestUtil.retrieveSingleLine(esrImport);
		assertThat(esrImportLine.getC_BPartner(), nullValue()); // guard. we assume that the matcher did not know how to find 'bp'

		esrImportLine.setC_Invoice(invoice);
		save(esrImportLine);

		esrImportBL.evaluateLine(esrImport, esrImportLine);

		assertThat("BPartner not the same in ESR Line and Invoice", esrImportLine.getC_BPartner(), is(bp));
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

		final I_C_BP_BankAccount account = newInstance(I_C_BP_BankAccount.class);

		account.setIsEsrAccount(true);
		account.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
		account.setAD_User_ID(Env.getAD_User_ID(getCtx()));
		account.setESR_RenderedAccountNo("01-059931-0");

		account.setC_BPartner(bp);
		save(account);

		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setC_BPartner(bp);
		invoice.setDocumentNo("164363");

		save(invoice);

		esrImportBL.loadAndEvaluateESRImportStream(esrImport, new ByteArrayInputStream(esrImportLineText.getBytes()));

		final I_ESR_ImportLine esrImportLine = ESRTestUtil.retrieveSingleLine(esrImport);
		assertThat(esrImportLine.getC_BPartner(), nullValue()); // guard. we assume that the matcher did not know how to find 'bp'

		esrImportLine.setC_Invoice(invoice);
		save(esrImportLine);

		esrImportBL.evaluateLine(esrImport, esrImportLine);

		assertThat("BPartner not the same in ESR Line and Invoice", esrImportLine.getC_BPartner(), is(bp));
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

		final I_C_BP_BankAccount account = newInstance(I_C_BP_BankAccount.class);

		account.setIsEsrAccount(true);
		account.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
		account.setAD_User_ID(Env.getAD_User_ID(getCtx()));
		account.setESR_RenderedAccountNo("01-059931-0");

		account.setC_BPartner(bp);
		save(account);

		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setC_BPartner(bp);
		invoice.setDocumentNo("164363");
		save(invoice);

		esrImportBL.loadAndEvaluateESRImportStream(esrImport, new ByteArrayInputStream(esrImportLineText.getBytes()));

		final I_ESR_ImportLine esrImportLine = ESRTestUtil.retrieveSingleLine(esrImport);
		assertThat(esrImportLine.getC_BPartner(), nullValue()); // guard. we assume that the matcher did not know how to find 'bp'

		esrImportLine.setC_Invoice(invoice);
		save(esrImportLine);

		esrImportBL.evaluateLine(esrImport, esrImportLine);

		assertThat("BPartner not the same in ESR Line and Invoice", esrImportLine.getC_BPartner(), is(bp));
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

		final I_C_BP_BankAccount account = newInstance(I_C_BP_BankAccount.class);
		account.setIsEsrAccount(true);
		account.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
		account.setAD_User_ID(Env.getAD_User_ID(getCtx()));
		account.setESR_RenderedAccountNo("01-059931-0");

		account.setC_BPartner(bp);
		save(account);

		esrImport.setC_BP_BankAccount(account);
		save(esrImport);

		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setC_BPartner(bp);
		invoice.setDocumentNo("164363");
		save(invoice);

		final String esrImportLineText = "0020105993102345370001000000070016436390000000100000000000016050116050116050100000000000000000000000";
		esrImportBL.loadAndEvaluateESRImportStream(esrImport, new ByteArrayInputStream(esrImportLineText.getBytes()));
		final I_ESR_ImportLine esrImportLine = ESRTestUtil.retrieveSingleLine(esrImport);
		assertThat(esrImportLine.getC_BPartner(), nullValue()); // guard. we assume that the matcher did not know how to find 'bp'

		esrImportLine.setC_Invoice(invoice);
		save(esrImportLine);
		esrImportBL.evaluateLine(esrImport, esrImportLine);

		assertThat("BPartner not the same in ESR Line and Invoice", esrImportLine.getC_BPartner(), is(bp));
	}

	@Test
	public void test_invalidLength()
	{
		final String esrImportLineText = "00201059931000000001050153641700120686900000040000012  190013011813011813012100015000400000000000000142";

		final I_ESR_Import esrImport = createImport();

		final I_C_BP_BankAccount account = newInstance(I_C_BP_BankAccount.class);

		account.setIsEsrAccount(true);
		account.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
		account.setAD_User_ID(Env.getAD_User_ID(getCtx()));
		account.setESR_RenderedAccountNo("01-059931-0");

		save(account);

		esrImportBL.loadAndEvaluateESRImportStream(esrImport, new ByteArrayInputStream(esrImportLineText.getBytes()));
		final I_ESR_ImportLine esrImportLine = ESRTestUtil.retrieveSingleLine(esrImport);

		assertThat("Invalid IsValid", esrImportLine.isValid(), is(false));
		assertThat(esrImportLine.getMatchErrorMsg(), nullValue());
		assertThat(esrImportLine.getImportErrorMsg(), is("ESR_Wrong_Regular_Line_Length_[103]"));
	}

	@Test
	public void test_regularLine_RenderedAccountNo()
	{
		final String esrImportLineText = "00201059931000000001050153641700120686900000040000012  190013011813011813012100015000400000000000000";

		final I_ESR_Import esrImport = createImport();

		final I_C_BP_BankAccount account = newInstance(I_C_BP_BankAccount.class);

		account.setIsEsrAccount(true);
		account.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
		account.setAD_User_ID(Env.getAD_User_ID(getCtx()));
		account.setESR_RenderedAccountNo("01-059931-0");

		save(account);

		esrImport.setC_BP_BankAccount(account);
		save(esrImport);

		esrImportBL.loadAndEvaluateESRImportStream(esrImport, new ByteArrayInputStream(esrImportLineText.getBytes()));
		final I_ESR_ImportLine esrImportLine = ESRTestUtil.retrieveSingleLine(esrImport);

		System.out.println(esrImportLine.getESRPostParticipantNumber() + "    ->    " + account.getESR_RenderedAccountNo().split("-"));

		final String[] unrenderedAccountNoParts = account.getESR_RenderedAccountNo().split("-");
		final String unrenderedPostAccountNo = unrenderedAccountNoParts[0] + unrenderedAccountNoParts[1] + unrenderedAccountNoParts[2];

		Assert.assertTrue("Wrong rendered account number", esrImportLine.getESRPostParticipantNumber().equals(unrenderedPostAccountNo));
	}

	@Test
	public void test_regularLine_WrongRenderedAccountNo()
	{
		final String esrImportLineText = "00288859931000000001050153641700120686900000040000012  190013011813011813012100015000400000000000000";

		final I_ESR_Import esrImport = createImport();

		final I_C_BP_BankAccount account = newInstance(I_C_BP_BankAccount.class);

		account.setIsEsrAccount(true);
		account.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
		account.setAD_User_ID(Env.getAD_User_ID(getCtx()));
		account.setESR_RenderedAccountNo("01-059931-0");

		save(account);

		esrImport.setC_BP_BankAccount(account);
		save(esrImport);

		esrImportBL.loadAndEvaluateESRImportStream(esrImport, new ByteArrayInputStream(esrImportLineText.getBytes()));
		final I_ESR_ImportLine esrImportLine = ESRTestUtil.retrieveSingleLine(esrImport);

		final String[] unrenderedAccountNoParts = account.getESR_RenderedAccountNo().split("-");
		final String unrenderedPostAccountNo = unrenderedAccountNoParts[0] + unrenderedAccountNoParts[1] + unrenderedAccountNoParts[2];

		Assert.assertTrue("Rendered account numbers are equal.", !esrImportLine.getESRPostParticipantNumber().equals(unrenderedPostAccountNo));
	}

	@Test
	public void test_regularLine_RenderedAccountNo_From_ESRPostFinanceUserNumber()
	{
		final String esrImportLineText = "00201059931000000001050153641700120686900000040000012  190013011813011813012100015000400000000000000";

		final I_ESR_Import esrImport = createImport();

		final I_C_BP_BankAccount account = newInstance(I_C_BP_BankAccount.class);

		account.setIsEsrAccount(true);
		account.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
		account.setAD_User_ID(Env.getAD_User_ID(getCtx()));
		account.setESR_RenderedAccountNo("01-888888-0");

		save(account);
		final String esrNoForPostFinanceUser = "010599310";

		createPostFinanceUserNumber(account, esrNoForPostFinanceUser);

		esrImport.setC_BP_BankAccount(account);
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

		final I_C_BP_BankAccount account = newInstance(I_C_BP_BankAccount.class);

		account.setIsEsrAccount(true);
		account.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
		account.setAD_User_ID(Env.getAD_User_ID(getCtx()));
		account.setESR_RenderedAccountNo("01-888888-0");

		save(account);
		final String renderedEsrNoForPostFinanceUser = "01-059931-0";
		final String unRenderedEsrNoForPostFinanceUser = "010599310";

		createPostFinanceUserNumber(account, renderedEsrNoForPostFinanceUser);

		esrImport.setC_BP_BankAccount(account);
		save(esrImport);

		esrImportBL.loadAndEvaluateESRImportStream(esrImport, new ByteArrayInputStream(esrImportLineText.getBytes()));
		final I_ESR_ImportLine esrImportLine = ESRTestUtil.retrieveSingleLine(esrImport);

		assertThat(esrImportLine.getESRPostParticipantNumber()).isEqualTo(unRenderedEsrNoForPostFinanceUser);
	}

	@Test(expected = AdempiereException.class)
	public void test_regularLine_RenderedAccountNo_From_ESRPostFinanceUserNumber_RenderedWrong()
	{
		final String esrImportLineText = "00201059931000000001050153641700120686900000040000012  190013011813011813012100015000400000000000000";

		final I_ESR_Import esrImport = createImport();

		final I_C_BP_BankAccount account = newInstance(I_C_BP_BankAccount.class);

		account.setIsEsrAccount(true);
		account.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
		account.setAD_User_ID(Env.getAD_User_ID(getCtx()));
		account.setESR_RenderedAccountNo("01-888888-0");

		save(account);
		final String esrNoForPostFinanceUser = "01-0599310";

		createPostFinanceUserNumber(account, esrNoForPostFinanceUser);

		esrImport.setC_BP_BankAccount(account);
		save(esrImport);

		esrImportBL.loadAndEvaluateESRImportStream(esrImport, new ByteArrayInputStream(esrImportLineText.getBytes()));
	}

	@Test
	public void test_regularLine_RenderedAccountNo_WrongESRPostFinanceUserNumber()
	{
		final String esrImportLineText = "00201059931000000001050153641700120686900000040000012  190013011813011813012100015000400000000000000";

		final I_ESR_Import esrImport = createImport();

		final I_C_BP_BankAccount account = newInstance(I_C_BP_BankAccount.class);

		account.setIsEsrAccount(true);
		account.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
		account.setAD_User_ID(Env.getAD_User_ID(getCtx()));
		account.setESR_RenderedAccountNo("01-888888-0");

		save(account);
		final String esrNoForPostFinanceUser = "088888880";

		createPostFinanceUserNumber(account, esrNoForPostFinanceUser);

		esrImport.setC_BP_BankAccount(account);
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

		final I_ESR_Import esrImport = createImport();

		final I_C_BP_BankAccount account = newInstance(I_C_BP_BankAccount.class);

		account.setIsEsrAccount(true);
		account.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
		account.setAD_User_ID(Env.getAD_User_ID(getCtx()));
		account.setESR_RenderedAccountNo("01-059931-0");

		save(account);

		esrImport.setC_BP_BankAccount(account);
		save(esrImport);

		final I_C_ReferenceNo referenceNo = newInstance(I_C_ReferenceNo.class);
		referenceNo.setReferenceNo("536417000120686");
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

		Assert.assertTrue("Reference number not found.", referenceNo.equals(esrImportLine.getC_ReferenceNo()));
	}

	@Test
	public void test_regularLine_No_Invoice_In_RefNo()
	{
		final String esrImportLineText = "00201059931000000001050153641700120686900000040000012  190013011813011813012100015000400000000000000";

		final I_ESR_Import esrImport = createImport();

		final I_C_BP_BankAccount account = newInstance(I_C_BP_BankAccount.class);

		account.setIsEsrAccount(true);
		account.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
		account.setAD_User_ID(Env.getAD_User_ID(getCtx()));
		account.setESR_RenderedAccountNo("01-059931-0");

		save(account);

		esrImport.setC_BP_BankAccount(account);
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

		Assert.assertTrue("Org not found.", org.equals(esrImportLine.getOrg()));
	}

	@Test
	public void test_regularLine_manual_refNo()
	{
		final String esrImportLineText = "00201059931000000001050153641700120686900000040000012  190013011813011813012100015000400000000000000";

		final I_AD_Org org = getAD_Org();

		final I_ESR_Import esrImport = createImport();

		final I_C_BP_BankAccount account = newInstance(I_C_BP_BankAccount.class);

		account.setIsEsrAccount(true);
		account.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
		account.setAD_User_ID(Env.getAD_User_ID(getCtx()));
		account.setESR_RenderedAccountNo("01-059931-0");

		save(account);

		esrImport.setC_BP_BankAccount(account);
		save(esrImport);

		final I_C_BPartner partner = newInstance(I_C_BPartner.class);
		partner.setValue("partner1");
		partner.setAD_Org_ID(org.getAD_Org_ID());
		save(partner);

		esrImport.setAD_Org(org);
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

		esrImportBL.loadAndEvaluateESRImportStream(esrImport, new ByteArrayInputStream(esrImportLineText.getBytes()));
		final I_ESR_ImportLine esrImportLine = ESRTestUtil.retrieveSingleLine(esrImport);

		Assert.assertTrue("Is not manual referenceNO", esrImportLine.isESR_IsManual_ReferenceNo());
		Assert.assertTrue("Org not found.", org.equals(esrImportLine.getAD_Org()));
		Assert.assertTrue("Partner not found.", partner.equals(esrImportLine.getC_BPartner()));
		Assert.assertTrue("Invoice not found.", invoice.equals(esrImportLine.getC_Invoice()));
	}
}
