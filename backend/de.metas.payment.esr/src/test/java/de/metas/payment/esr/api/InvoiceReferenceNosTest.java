package de.metas.payment.esr.api;

import static de.metas.util.StringUtils.lpadZero;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocType;
import org.compiere.model.X_C_DocType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.attachments.AttachmentEntryService;
import de.metas.banking.Bank;
import de.metas.banking.BankCreateRequest;
import de.metas.banking.api.BankRepository;
import de.metas.payment.esr.api.impl.ESRBPBankAccountBL;
import de.metas.payment.esr.api.impl.ESRImportBL;
import de.metas.payment.esr.model.I_C_BP_BankAccount;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.payment.esr
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class InvoiceReferenceNosTest
{
	private BankRepository bankRepo;
	private ESRImportBL esrImportBL;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		bankRepo = new BankRepository();

		final ESRBPBankAccountBL esrBankAccountBL = new ESRBPBankAccountBL(bankRepo);
		SpringContextHolder.registerJUnitBean(IESRBPBankAccountBL.class, esrBankAccountBL);

		final AttachmentEntryService attachmentEntryService = AttachmentEntryService.createInstanceForUnitTesting();
		esrImportBL = new ESRImportBL(attachmentEntryService);
		Services.registerService(IESRImportBL.class, esrImportBL);

	}

	@Test
	public void invoiceReferenceNoGenerator_6DigitsAcctNoTest()
	{
		final I_AD_Org org = createOrg("Org", "001");
		final I_C_BPartner orgBP = createPartner("OrgParner", "0");
		linkOrgWithOrgBP(org, orgBP);
		final I_C_BP_BankAccount esrBankAccountForPartner = createESRBankAccountForPartner(orgBP, "123456");

		final I_C_BPartner invoicePartner = createPartner("InvoicePartner", "1234");

		final I_C_DocType invoiceDocType = createInvoiceDocType(X_C_DocType.DOCBASETYPE_APInvoice);
		final I_C_Invoice invoice = createInvoice(org, invoicePartner, "1234"/* documentNo */, invoiceDocType);

		final String referenceNo = "1234560" // first 7 digits are the accountNo, rpad 0
				+ "001" // next 3 digits are org value, lpad0
				+ lpadZero(Integer.toString(invoicePartner.getC_BPartner_ID()), 8, "BPartnerHint") // the next 8 digits are the bpartner's ID, lpad 0
				+ lpadZero(Integer.toString(invoice.getC_Invoice_ID()), 8, "InvoiceHint") // the next 8 digits are the document's ID, lpad 0
		;

		final int checkdigit = esrImportBL.calculateESRCheckDigit(referenceNo);
		final String expectedReferenceNo = referenceNo + checkdigit;

		// invoke the method under test
		final String generatedReferenceNo = InvoiceReferenceNos
				.createFor(invoice, esrBankAccountForPartner)
				.asString();

		assertThat(expectedReferenceNo).isEqualTo(generatedReferenceNo);
	}

	@Test
	public void invoiceReferenceNoGenerator_7DigitsAcctNoTest()
	{
		final I_AD_Org org = createOrg("Org", "001");
		final I_C_BPartner orgBP = createPartner("OrgParner", "0");
		linkOrgWithOrgBP(org, orgBP);
		final I_C_BP_BankAccount esrBankAccountForPartner = createESRBankAccountForPartner(orgBP, "1234567");

		final I_C_BPartner invoicePartner = createPartner("InvoicePartner", "1234");

		final I_C_DocType invoiceDocType = createInvoiceDocType(X_C_DocType.DOCBASETYPE_APInvoice);
		final String documentNo = "1234";

		final I_C_Invoice invoice = createInvoice(org, invoicePartner, documentNo, invoiceDocType);

		final String referenceNo = "1234567" // first 7 digits are the accountNo, rpad 0
				+ "001" // next 3 digits are org value, lpad0
				+ lpadZero(Integer.toString(invoicePartner.getC_BPartner_ID()), 8, "BPartnerHint") // the next 8 digits are the bpartner's ID, lpad 0
				+ lpadZero(Integer.toString(invoice.getC_Invoice_ID()), 8, "Invoicehint"); // the next 8 digits are the invoice-ID, lpad 0

		final int checkdigit = esrImportBL.calculateESRCheckDigit(referenceNo);
		final String expectedReferenceNo = referenceNo + checkdigit;

		// invoke the method under test
		final String generatedReferenceNo = InvoiceReferenceNos
				.createFor(invoice, esrBankAccountForPartner)
				.asString();

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

		invoice.setC_DocType_ID(docType.getC_DocType_ID());
		invoice.setIsSOTrx(true);
		invoice.setAD_Org_ID(org.getAD_Org_ID());
		invoice.setC_BPartner_ID(invoicePartner.getC_BPartner_ID());
		invoice.setDocumentNo(documentNo);

		InterfaceWrapperHelper.save(invoice);
		return invoice;
	}

	private void linkOrgWithOrgBP(@NonNull final I_AD_Org org, @NonNull final I_C_BPartner orgBP)
	{
		orgBP.setAD_Org_ID(org.getAD_Org_ID());
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
		final Bank bank = bankRepo.createBank(BankCreateRequest.builder()
				.bankName("bankName")
				.routingNo("routingNo")
				.build());

		final I_C_BP_BankAccount bpBankAccount = InterfaceWrapperHelper.newInstance(I_C_BP_BankAccount.class);

		bpBankAccount.setC_Bank_ID(bank.getBankId().getRepoId());
		bpBankAccount.setAD_Org_ID(partner.getAD_Org_ID());
		bpBankAccount.setIsEsrAccount(true);

		bpBankAccount.setC_BPartner_ID(partner.getC_BPartner_ID());
		bpBankAccount.setAccountNo(accountNo);

		InterfaceWrapperHelper.save(bpBankAccount);

		return bpBankAccount;
	}
}
