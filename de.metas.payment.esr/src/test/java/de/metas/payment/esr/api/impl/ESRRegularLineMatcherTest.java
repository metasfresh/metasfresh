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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
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
import de.metas.payment.esr.exception.ESRParserException;
import de.metas.payment.esr.model.I_C_BP_BankAccount;
import de.metas.payment.esr.model.I_ESR_ImportLine;

public class ESRRegularLineMatcherTest extends ESRTestBase
{
	private ESRLineMatcher matcher;

	@Override
	public void init()
	{
		matcher = new ESRLineMatcher();
		POJOWrapper.setDefaultStrictValues(false);
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

		final I_ESR_ImportLine esrImportLine = createImportLine(esrImportLineText);

		final I_C_BP_BankAccount account = db.newInstance(I_C_BP_BankAccount.class);

		account.setIsEsrAccount(true);
		account.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
		account.setAD_User_ID(Env.getAD_User_ID(getCtx()));
		account.setESR_RenderedAccountNo("01-059931-0");

		db.save(account);

		esrImportLine.setC_BP_BankAccount(account);
		db.save(esrImportLine);

		final I_C_ReferenceNo_Type refNoType = db.newInstance(I_C_ReferenceNo_Type.class);
		refNoType.setName("InvoiceReference");
		db.save(refNoType);

		matcher.match(esrImportLine);

		Assert.assertEquals("Invalid IsValid", false, esrImportLine.isValid());
	}

	// task 09861
	@Test
	public void test_regularLineBPValueGreaterThan1000()
	{
		final I_C_BPartner bp = db.newInstance(I_C_BPartner.class);
		bp.setValue("G1386");
		bp.setC_BPartner_ID(15);
		db.save(bp);

		final String esrImportLineText = "0020105993102345370001000013860016436390000000100000000000016050116050116050100000000000000000000000";

		final I_ESR_ImportLine esrImportLine = createImportLine(esrImportLineText);

		final I_C_BP_BankAccount account = db.newInstance(I_C_BP_BankAccount.class);

		account.setIsEsrAccount(true);
		account.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
		account.setAD_User_ID(Env.getAD_User_ID(getCtx()));
		account.setESR_RenderedAccountNo("01-059931-0");

		account.setC_BPartner(bp);
		db.save(account);

		final I_C_Invoice invoice = db.newInstance(I_C_Invoice.class);
		invoice.setC_BPartner(bp);
		invoice.setDocumentNo("164363");

		db.save(invoice);

		esrImportLine.setC_Invoice(invoice);

		esrImportLine.setC_BP_BankAccount(account);
		db.save(esrImportLine);

		final I_C_ReferenceNo_Type refNoType = db.newInstance(I_C_ReferenceNo_Type.class);
		refNoType.setName("InvoiceReference");
		db.save(refNoType);

		matcher.match(esrImportLine);

		Assert.assertSame("BPartner not the same in ESR Line and Invoice", bp, esrImportLine.getC_BPartner());

	}

	// task 09861
	@Test
	public void test_regularLineBPValueGreaterThan1000_0Included()
	{
		final I_C_BPartner bp = db.newInstance(I_C_BPartner.class);
		bp.setValue("G01386");
		bp.setC_BPartner_ID(15);
		db.save(bp);

		final String esrImportLineText = "0020105993102345370001000013860016436390000000100000000000016050116050116050100000000000000000000000";

		final I_ESR_ImportLine esrImportLine = createImportLine(esrImportLineText);

		final I_C_BP_BankAccount account = db.newInstance(I_C_BP_BankAccount.class);

		account.setIsEsrAccount(true);
		account.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
		account.setAD_User_ID(Env.getAD_User_ID(getCtx()));
		account.setESR_RenderedAccountNo("01-059931-0");

		account.setC_BPartner(bp);
		db.save(account);

		final I_C_Invoice invoice = db.newInstance(I_C_Invoice.class);
		invoice.setC_BPartner(bp);
		invoice.setDocumentNo("164363");

		db.save(invoice);

		esrImportLine.setC_Invoice(invoice);

		esrImportLine.setC_BP_BankAccount(account);
		db.save(esrImportLine);

		final I_C_ReferenceNo_Type refNoType = db.newInstance(I_C_ReferenceNo_Type.class);
		refNoType.setName("InvoiceReference");
		db.save(refNoType);

		matcher.match(esrImportLine);

		Assert.assertSame("BPartner not the same in ESR Line and Invoice", bp, esrImportLine.getC_BPartner());

	}

	// task 09861
	@Test
	public void test_regularLineBPValueGreaterThan10000()
	{
		final I_C_BPartner bp = db.newInstance(I_C_BPartner.class);
		bp.setValue("G11386");
		bp.setC_BPartner_ID(15);
		db.save(bp);

		final String esrImportLineText = "0020105993102345370001000113860016436390000000100000000000016050116050116050100000000000000000000000";

		final I_ESR_ImportLine esrImportLine = createImportLine(esrImportLineText);

		final I_C_BP_BankAccount account = db.newInstance(I_C_BP_BankAccount.class);

		account.setIsEsrAccount(true);
		account.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
		account.setAD_User_ID(Env.getAD_User_ID(getCtx()));
		account.setESR_RenderedAccountNo("01-059931-0");

		account.setC_BPartner(bp);
		db.save(account);

		final I_C_Invoice invoice = db.newInstance(I_C_Invoice.class);
		invoice.setC_BPartner(bp);
		invoice.setDocumentNo("164363");

		db.save(invoice);

		esrImportLine.setC_Invoice(invoice);

		esrImportLine.setC_BP_BankAccount(account);
		db.save(esrImportLine);

		final I_C_ReferenceNo_Type refNoType = db.newInstance(I_C_ReferenceNo_Type.class);
		refNoType.setName("InvoiceReference");
		db.save(refNoType);

		matcher.match(esrImportLine);

		Assert.assertSame("BPartner not the same in ESR Line and Invoice", bp, esrImportLine.getC_BPartner());

	}

	// task 09861
	@Test
	public void test_regularLineBPValue_0007()
	{
		final I_C_BPartner bp = db.newInstance(I_C_BPartner.class);
		bp.setValue("G0007");
		bp.setC_BPartner_ID(15);
		db.save(bp);

		final String esrImportLineText = "0020105993102345370001000000070016436390000000100000000000016050116050116050100000000000000000000000";

		final I_ESR_ImportLine esrImportLine = createImportLine(esrImportLineText);

		final I_C_BP_BankAccount account = db.newInstance(I_C_BP_BankAccount.class);

		account.setIsEsrAccount(true);
		account.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
		account.setAD_User_ID(Env.getAD_User_ID(getCtx()));
		account.setESR_RenderedAccountNo("01-059931-0");

		account.setC_BPartner(bp);
		db.save(account);

		final I_C_Invoice invoice = db.newInstance(I_C_Invoice.class);
		invoice.setC_BPartner(bp);
		invoice.setDocumentNo("164363");

		db.save(invoice);

		esrImportLine.setC_Invoice(invoice);

		esrImportLine.setC_BP_BankAccount(account);
		db.save(esrImportLine);

		final I_C_ReferenceNo_Type refNoType = db.newInstance(I_C_ReferenceNo_Type.class);
		refNoType.setName("InvoiceReference");
		db.save(refNoType);

		matcher.match(esrImportLine);
		
		Assert.assertSame("BPartner not the same in ESR Line and Invoice", bp, esrImportLine.getC_BPartner());

	}

	@Test(expected = ESRParserException.class)
	public void test_invalidLength()
	{
		final String esrImportLineText = "00201059931000000001050153641700120686900000040000012  190013011813011813012100015000400000000000000142";

		final I_ESR_ImportLine esrImportLine = createImportLine(esrImportLineText);

		final I_C_BP_BankAccount account = db.newInstance(I_C_BP_BankAccount.class);

		account.setIsEsrAccount(true);
		account.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
		account.setAD_User_ID(Env.getAD_User_ID(getCtx()));
		account.setESR_RenderedAccountNo("01-059931-0");

		db.save(account);

		esrImportLine.setC_BP_BankAccount(account);
		db.save(esrImportLine);

		final I_C_ReferenceNo_Type refNoType = db.newInstance(I_C_ReferenceNo_Type.class);
		refNoType.setName("InvoiceReference");
		db.save(refNoType);

		matcher.match(esrImportLine);

		Assert.assertEquals("Invalid IsValid", false, esrImportLine.isValid());
	}

	@Test
	public void test_regularLine_RenderedAccountNo()
	{
		final String esrImportLineText = "00201059931000000001050153641700120686900000040000012  190013011813011813012100015000400000000000000";

		final I_ESR_ImportLine esrImportLine = createImportLine(esrImportLineText);

		final I_C_BP_BankAccount account = db.newInstance(I_C_BP_BankAccount.class);

		account.setIsEsrAccount(true);
		account.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
		account.setAD_User_ID(Env.getAD_User_ID(getCtx()));
		account.setESR_RenderedAccountNo("01-059931-0");

		db.save(account);

		esrImportLine.setC_BP_BankAccount(account);
		db.save(esrImportLine);

		final I_C_ReferenceNo_Type refNoType = db.newInstance(I_C_ReferenceNo_Type.class);
		refNoType.setName("InvoiceReference");
		db.save(refNoType);

		matcher.match(esrImportLine);
		System.out.println(esrImportLine.getESRPostParticipantNumber() + "    ->    " + account.getESR_RenderedAccountNo().split("-"));

		final String[] unrenderedAccountNoParts = account.getESR_RenderedAccountNo().split("-");
		final String unrenderedPostAccountNo = unrenderedAccountNoParts[0] + unrenderedAccountNoParts[1] + unrenderedAccountNoParts[2];

		Assert.assertTrue("Wrong rendered account number", esrImportLine.getESRPostParticipantNumber().equals(unrenderedPostAccountNo));
	}

	@Test
	public void test_regularLine_WrongRenderedAccountNo()
	{
		final String esrImportLineText = "00288859931000000001050153641700120686900000040000012  190013011813011813012100015000400000000000000";

		final I_ESR_ImportLine esrImportLine = createImportLine(esrImportLineText);

		final I_C_BP_BankAccount account = db.newInstance(I_C_BP_BankAccount.class);

		account.setIsEsrAccount(true);
		account.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
		account.setAD_User_ID(Env.getAD_User_ID(getCtx()));
		account.setESR_RenderedAccountNo("01-059931-0");

		db.save(account);

		esrImportLine.setC_BP_BankAccount(account);
		db.save(esrImportLine);

		final I_C_ReferenceNo_Type refNoType = db.newInstance(I_C_ReferenceNo_Type.class);
		refNoType.setName("InvoiceReference");
		db.save(refNoType);

		matcher.match(esrImportLine);

		final String[] unrenderedAccountNoParts = account.getESR_RenderedAccountNo().split("-");
		final String unrenderedPostAccountNo = unrenderedAccountNoParts[0] + unrenderedAccountNoParts[1] + unrenderedAccountNoParts[2];

		Assert.assertTrue("Rendered account numbers are equal.", !esrImportLine.getESRPostParticipantNumber().equals(unrenderedPostAccountNo));
	}

	@Test
	public void test_regularLine_ReferenceNumber()
	{
		Services.get(IDocumentNoBuilderFactory.class);

		final String esrImportLineText = "00201059931000000001050153641700120686900000040000012  190013011813011813012100015000400000000000000";

		final I_ESR_ImportLine esrImportLine = createImportLine(esrImportLineText);

		final I_C_BP_BankAccount account = db.newInstance(I_C_BP_BankAccount.class);

		account.setIsEsrAccount(true);
		account.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
		account.setAD_User_ID(Env.getAD_User_ID(getCtx()));
		account.setESR_RenderedAccountNo("01-059931-0");

		InterfaceWrapperHelper.save(account);

		esrImportLine.setC_BP_BankAccount(account);
		InterfaceWrapperHelper.save(esrImportLine);

		final I_C_ReferenceNo_Type refNoType = db.newInstance(I_C_ReferenceNo_Type.class);
		refNoType.setName("InvoiceReference");
		InterfaceWrapperHelper.save(refNoType);

		final I_C_ReferenceNo referenceNo = db.newInstance(I_C_ReferenceNo.class);
		referenceNo.setReferenceNo("536417000120686");
		referenceNo.setC_ReferenceNo_Type(refNoType);
		referenceNo.setIsManual(false);
		InterfaceWrapperHelper.save(referenceNo);

		final I_C_BPartner partner = db.newInstance(I_C_BPartner.class);
		partner.setValue("partner1");
		InterfaceWrapperHelper.save(partner);

		final I_C_DocType type = db.newInstance(I_C_DocType.class);
		type.setDocBaseType(X_C_DocType.DOCBASETYPE_APCreditMemo);
		InterfaceWrapperHelper.save(type);

		final I_C_Invoice invoice = db.newInstance(I_C_Invoice.class);
		invoice.setC_BPartner_ID(partner.getC_BPartner_ID());
		invoice.setDocumentNo("000120686");
		invoice.setC_DocType_ID(type.getC_DocType_ID());
		InterfaceWrapperHelper.save(invoice);

		final I_C_ReferenceNo_Doc esrReferenceNumberDocument = db.newInstance(I_C_ReferenceNo_Doc.class);
		esrReferenceNumberDocument.setAD_Table_ID(Services.get(IADTableDAO.class).retrieveTableId(I_C_Invoice.Table_Name));
		esrReferenceNumberDocument.setRecord_ID(invoice.getC_Invoice_ID());
		esrReferenceNumberDocument.setC_ReferenceNo(referenceNo);

		InterfaceWrapperHelper.save(esrReferenceNumberDocument);

		I_C_AllocationLine allocAmt = db.newInstance(I_C_AllocationLine.class);
		allocAmt.setWriteOffAmt(new BigDecimal(10.0));
		allocAmt.setC_Invoice_ID(invoice.getC_Invoice_ID());
		InterfaceWrapperHelper.save(allocAmt);

		matcher.match(esrImportLine);

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

		final I_ESR_ImportLine esrImportLine = createImportLine(esrImportLineText);

		final I_C_BP_BankAccount account = db.newInstance(I_C_BP_BankAccount.class);

		account.setIsEsrAccount(true);
		account.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
		account.setAD_User_ID(Env.getAD_User_ID(getCtx()));
		account.setESR_RenderedAccountNo("01-059931-0");

		db.save(account);

		esrImportLine.setC_BP_BankAccount(account);
		db.save(esrImportLine);

		final I_C_ReferenceNo_Type refNoType = db.newInstance(I_C_ReferenceNo_Type.class);
		refNoType.setName("InvoiceReference");
		db.save(refNoType);

		final I_C_BPartner partner = db.newInstance(I_C_BPartner.class);
		partner.setValue("partner1");
		db.save(partner);

		final I_AD_Org org = db.newInstance(I_AD_Org.class);
		org.setValue("105");
		db.save(org);

		I_C_AllocationLine allocAmt = db.newInstance(I_C_AllocationLine.class);
		allocAmt.setWriteOffAmt(new BigDecimal(10.0));
		// allocAmt.setC_Invoice_ID(invoice.getC_Invoice_ID());
		db.save(allocAmt);

		matcher.match(esrImportLine);

		System.out.println(org.getAD_Org_ID() + " -------> " + esrImportLine.getOrg());

		Assert.assertTrue("Org not found.", org.equals(esrImportLine.getOrg()));
	}

	@Test
	public void test_regularLine_manual_refNo()
	{
		final String esrImportLineText = "00201059931000000001050153641700120686900000040000012  190013011813011813012100015000400000000000000";

		final I_AD_Org org = getAD_Org();

		final I_ESR_ImportLine esrImportLine = createImportLine(esrImportLineText);

		final I_C_BP_BankAccount account = db.newInstance(I_C_BP_BankAccount.class);

		account.setIsEsrAccount(true);
		account.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
		account.setAD_User_ID(Env.getAD_User_ID(getCtx()));
		account.setESR_RenderedAccountNo("01-059931-0");

		db.save(account);

		esrImportLine.setC_BP_BankAccount(account);
		db.save(esrImportLine);

		final I_C_ReferenceNo_Type refNoType = db.newInstance(I_C_ReferenceNo_Type.class);
		refNoType.setName("InvoiceReference");
		db.save(refNoType);

		final I_C_BPartner partner = db.newInstance(I_C_BPartner.class);
		partner.setValue("partner1");
		partner.setAD_Org_ID(org.getAD_Org_ID());
		db.save(partner);

		esrImportLine.setAD_Org(org);
		db.save(esrImportLine);

		final I_C_DocType type = db.newInstance(I_C_DocType.class);
		type.setDocBaseType(X_C_DocType.DOCBASETYPE_APCreditMemo);
		db.save(type);

		final I_C_Invoice invoice = db.newInstance(I_C_Invoice.class);
		invoice.setAD_Org_ID(org.getAD_Org_ID());
		invoice.setC_BPartner_ID(partner.getC_BPartner_ID());
		invoice.setDocumentNo("000120686");
		invoice.setAD_Org_ID(org.getAD_Org_ID());
		invoice.setC_DocType_ID(type.getC_DocType_ID());
		db.save(invoice);

		final I_C_ReferenceNo referenceNo = db.newInstance(I_C_ReferenceNo.class);
		referenceNo.setReferenceNo("000000010501536417000120686");
		referenceNo.setC_ReferenceNo_Type(refNoType);
		referenceNo.setIsManual(true);
		db.save(referenceNo);

		final I_C_ReferenceNo_Doc esrReferenceNumberDocument = db.newInstance(I_C_ReferenceNo_Doc.class);
		esrReferenceNumberDocument.setAD_Table_ID(Services.get(IADTableDAO.class).retrieveTableId(I_C_Invoice.Table_Name));
		esrReferenceNumberDocument.setRecord_ID(invoice.getC_Invoice_ID());
		esrReferenceNumberDocument.setC_ReferenceNo(referenceNo);
		db.save(esrReferenceNumberDocument);

		I_C_AllocationLine allocAmt = db.newInstance(I_C_AllocationLine.class);
		allocAmt.setWriteOffAmt(new BigDecimal(10.0));
		allocAmt.setC_Invoice_ID(invoice.getC_Invoice_ID());
		db.save(allocAmt);

		matcher.match(esrImportLine);

		Assert.assertTrue("Is not manual referenceNO", esrImportLine.isESR_IsManual_ReferenceNo());
		Assert.assertTrue("Org not found.", org.equals(esrImportLine.getAD_Org()));
		Assert.assertTrue("Partner not found.", partner.equals(esrImportLine.getC_BPartner()));
		Assert.assertTrue("Invoice not found.", invoice.equals(esrImportLine.getC_Invoice()));

	}
}