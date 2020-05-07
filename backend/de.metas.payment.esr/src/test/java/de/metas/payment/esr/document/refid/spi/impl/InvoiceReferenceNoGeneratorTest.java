package de.metas.payment.esr.document.refid.spi.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocType;
import org.compiere.model.X_C_DocType;
import org.junit.Before;
import org.junit.Test;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.banking.model.I_C_Bank;
import de.metas.payment.esr.api.impl.ESRImportBL;
import de.metas.payment.esr.model.I_C_BP_BankAccount;
import lombok.NonNull;

/*
 * #%L
 * de.metas.payment.esr
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class InvoiceReferenceNoGeneratorTest
{
	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void invoiceReferenceNoGenerator_6DigitsAcctNoTest()
	{
		final I_AD_Org org = createOrg("Org", "001");
		final I_C_BPartner orgBP = createPartner("OrgParner", "0");
		linkOrgWithOrgBP(org, orgBP);
		createESRBankAccountForPartner(orgBP, "123456");

		final I_C_BPartner invoicePartner = createPartner("InvoicePartner", "1234");

		final I_C_DocType invoiceDocType = createInvoiceDocType(X_C_DocType.DOCBASETYPE_APInvoice);
		final String documentNo = "1234";

		final I_C_Invoice invoice = createInvoice(org, invoicePartner, documentNo, invoiceDocType);

		final InvoiceReferenceNoGenerator generatorInstance = new InvoiceReferenceNoGenerator();

		final int checkdigit = new ESRImportBL().calculateESRCheckDigit("12345600010000123400001234");

		final String expectedReferenceNo = "1234560" // first 7 digits are the accountNo, rpad 0
				+ "001" // next 3 digits are org value, lpad0
				+ "00001234" // the next 8 digits are the partner value, lpad 0
				+ "00001234" // the next 8 digits are the document number, lpad 0
				+ checkdigit;

		final String generatedReferenceNo = generatorInstance.generateReferenceNo(invoice);

		assertThat(expectedReferenceNo).isEqualTo(generatedReferenceNo);

	}
	
	
	@Test
	public void invoiceReferenceNoGenerator_7DigitsAcctNoTest()
	{
		final I_AD_Org org = createOrg("Org", "001");
		final I_C_BPartner orgBP = createPartner("OrgParner", "0");
		linkOrgWithOrgBP(org, orgBP);
		createESRBankAccountForPartner(orgBP, "1234567");

		final I_C_BPartner invoicePartner = createPartner("InvoicePartner", "1234");

		final I_C_DocType invoiceDocType = createInvoiceDocType(X_C_DocType.DOCBASETYPE_APInvoice);
		final String documentNo = "1234";

		final I_C_Invoice invoice = createInvoice(org, invoicePartner, documentNo, invoiceDocType);

		final InvoiceReferenceNoGenerator generatorInstance = new InvoiceReferenceNoGenerator();

		final int checkdigit = new ESRImportBL().calculateESRCheckDigit("12345670010000123400001234");

		final String expectedReferenceNo = "1234567" // first 7 digits are the accountNo, rpad 0
				+ "001" // next 3 digits are org value, lpad0
				+ "00001234" // the next 8 digits are the partner value, lpad 0
				+ "00001234" // the next 8 digits are the document number, lpad 0
				+ checkdigit;

		final String generatedReferenceNo = generatorInstance.generateReferenceNo(invoice);

		assertThat(expectedReferenceNo).isEqualTo(generatedReferenceNo);

	}

	private I_C_DocType createInvoiceDocType(final String docBaseType)
	{
		final I_C_DocType docType = InterfaceWrapperHelper.newInstance(I_C_DocType.class);
		docType.setDocBaseType(docBaseType);
		InterfaceWrapperHelper.save(docType);

		return docType;
	}

	private I_C_Invoice createInvoice(final I_AD_Org org, final I_C_BPartner invoicePartner, final String documentNo, final I_C_DocType docType)
	{
		final I_C_Invoice invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);

		invoice.setC_DocType(docType);
		invoice.setIsSOTrx(true);
		invoice.setAD_Org(org);
		invoice.setC_BPartner(invoicePartner);
		invoice.setDocumentNo(documentNo);

		InterfaceWrapperHelper.save(invoice);
		return invoice;
	}

	private void linkOrgWithOrgBP(@NonNull final I_AD_Org org, @NonNull final I_C_BPartner orgBP)
	{
		orgBP.setAD_Org(org);
		orgBP.setAD_OrgBP_ID(org.getAD_Org_ID());
		InterfaceWrapperHelper.save(orgBP);

	}

	private I_C_BPartner createPartner(final String name, final String value)
	{
		final I_C_BPartner partner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
		partner.setName(name);
		partner.setValue(value);

		InterfaceWrapperHelper.save(partner);
		return partner;
	}

	private I_AD_Org createOrg(final String name, final String value)
	{
		final I_AD_Org org = InterfaceWrapperHelper.newInstance(I_AD_Org.class);
		org.setName(name);
		org.setValue(value);
		InterfaceWrapperHelper.save(org);

		return org;

	}

	private I_C_BP_BankAccount createESRBankAccountForPartner(@NonNull final I_C_BPartner partner, @NonNull final String accountNo)
	{
		
		final I_C_Bank bank  = InterfaceWrapperHelper.newInstance(I_C_Bank.class);
		InterfaceWrapperHelper.save(bank);
		
		final I_C_BP_BankAccount bpBankAccount = InterfaceWrapperHelper.newInstance(I_C_BP_BankAccount.class);
		
		
		bpBankAccount.setC_Bank(bank);
		bpBankAccount.setAD_Org(partner.getAD_Org());
		bpBankAccount.setIsEsrAccount(true);

		bpBankAccount.setC_BPartner(partner);
		bpBankAccount.setAccountNo(accountNo);

		InterfaceWrapperHelper.save(bpBankAccount);

		return bpBankAccount;
	}

}
