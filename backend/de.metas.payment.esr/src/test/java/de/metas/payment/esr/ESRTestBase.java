package de.metas.payment.esr;

import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_Sequence;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Bank;
import org.compiere.model.I_C_Payment;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.allocation.api.C_AllocationHdr_ProcessInterceptor;
import de.metas.attachments.AttachmentEntryService;
import de.metas.currency.CurrencyCode;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.document.engine.impl.PlainDocumentBL;
import de.metas.document.refid.model.I_C_ReferenceNo;
import de.metas.document.refid.model.I_C_ReferenceNo_Doc;
import de.metas.document.refid.model.I_C_ReferenceNo_Type;
import de.metas.document.sequence.IDocumentNoBuilder;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.document.sequence.impl.DocumentNoBuilderFactory;
import de.metas.interfaces.I_C_DocType;
import de.metas.money.CurrencyId;
import de.metas.payment.api.C_Payment_ProcessInterceptor;
import de.metas.payment.api.IPaymentDAO;
import de.metas.payment.esr.api.IESRImportBL;
import de.metas.payment.esr.api.IESRImportDAO;
import de.metas.payment.esr.api.impl.ESRImportBL;
import de.metas.payment.esr.model.I_C_BP_BankAccount;
import de.metas.payment.esr.model.I_ESR_Import;
import de.metas.payment.esr.model.I_ESR_ImportLine;
import de.metas.payment.esr.model.I_ESR_PostFinanceUserNumber;
import de.metas.payment.esr.model.X_ESR_Import;
import de.metas.payment.esr.model.validator.ESR_Main_Validator;
import de.metas.util.Services;
import lombok.NonNull;

@ExtendWith(AdempiereTestWatcher.class)
public class ESRTestBase
{
	@BeforeAll
	public static void staticInit()
	{
		AdempiereTestHelper.get().staticInit();
	}

	private Properties ctx;
	protected IESRImportDAO dao;
	private I_AD_Client client;
	private I_AD_Org org;
	protected ITrxManager trxManager;
	protected IContextAware contextProvider;
	private I_C_Invoice invoice;
	private I_C_BPartner partner;

	protected IPaymentDAO paymentDAO;
	protected ESRImportBL esrImportBL;

	@BeforeEach
	public final void beforeTest()
	{
		AdempiereTestHelper.get().init();

		dao = Services.get(IESRImportDAO.class);

		final AttachmentEntryService attachmentEntryService = AttachmentEntryService.createInstanceForUnitTesting();
		esrImportBL = new ESRImportBL(attachmentEntryService);

		paymentDAO = Services.get(IPaymentDAO.class);

		final IDocumentNoBuilderFactory documentNoBuilderFactory = new DocumentNoBuilderFactory(Optional.empty());
		Services.registerService(IDocumentNoBuilderFactory.class, documentNoBuilderFactory);

		// register processors
		final PlainDocumentBL docActionBL = (PlainDocumentBL)Services.get(IDocumentBL.class);
		docActionBL.setDefaultProcessInterceptor(PlainDocumentBL.PROCESSINTERCEPTOR_CompleteDirectly);
		docActionBL.registerProcessInterceptor(I_C_Payment.Table_Name, IDocument.ACTION_Complete, new C_Payment_ProcessInterceptor());
		docActionBL.registerProcessInterceptor(I_C_AllocationHdr.Table_Name, IDocument.ACTION_Complete, new C_AllocationHdr_ProcessInterceptor());

		// Client
		client = newInstance(I_AD_Client.class);
		save(client);

		// Org
		org = newInstance(I_AD_Org.class);
		save(org);

		//
		// Setup context
		ctx = Env.getCtx();
		ctx.clear();
		Env.setContext(ctx, "#AD_Client_ID", client.getAD_Client_ID());
		Env.setContext(ctx, "#AD_Org_ID", org.getAD_Org_ID());
		Env.setContext(ctx, "#AD_Role_ID", 1);
		Env.setContext(ctx, "#AD_User_ID", 1);

		trxManager = Services.get(ITrxManager.class);
		contextProvider = PlainContextAware.newOutOfTrx(getCtx());

		// Make sure esr validator interceptor is registered
		Services.registerService(IESRImportBL.class, esrImportBL);
		final ESR_Main_Validator esrValidator = new ESR_Main_Validator();
		Services.get(IModelInterceptorRegistry.class).addModelInterceptor(esrValidator);

		//
		// Prepare needed sequences
		createDocumentSequence(I_C_BPartner.Table_Name);

		init();
	}

	public I_AD_Org getAD_Org()
	{
		return org;
	}

	public I_C_Invoice getC_Invoice()
	{
		return invoice;
	}

	public I_C_BPartner getBPartner()
	{
		return partner;
	}

	protected void init()
	{
		// nothing
	}

	@AfterEach
	public final void afterTest()
	{
		POJOLookupMap.get().dumpStatus();
		Services.clear();
	}

	protected Properties getCtx()
	{
		return ctx;
	}

	protected I_ESR_Import createImport()
	{
		final I_ESR_Import esrImport = newInstance(I_ESR_Import.class, contextProvider);
		esrImport.setDataType(X_ESR_Import.DATATYPE_V11);
		save(esrImport);

		return esrImport;
	}

	protected I_C_BP_BankAccount createBankAccount(final boolean isEsrAccount,
			final int orgId,
			final int userId,
			final String esrRenderedAcctNo,
			@NonNull final CurrencyId currencyId)
	{
		final I_C_Bank bank = newInstance(I_C_Bank.class);
		bank.setName("Test Bank");
		save(bank);

		final I_C_BP_BankAccount account = newInstance(I_C_BP_BankAccount.class);

		account.setC_Bank_ID(bank.getC_Bank_ID());
		account.setIsEsrAccount(isEsrAccount);
		account.setAD_Org_ID(orgId);
		account.setAD_User_ID(userId);
		account.setESR_RenderedAccountNo(esrRenderedAcctNo);
		account.setC_Currency_ID(currencyId.getRepoId());

		save(account);

		return account;
	}

	public void assertNoErrors(final List<I_ESR_ImportLine> lines)
	{
		if (lines == null || lines.isEmpty())
		{
			return;
		}

		for (final I_ESR_ImportLine line : lines)
		{
			assertThat(line.getImportErrorMsg())
					.as("ImportError message found on line " + line)
					.isEmpty();
			assertThat(line.getMatchErrorMsg())
					.as("MatchError message found on line " + line)
					.isEmpty();
		}
	}

	private void createDocumentSequence(final String tableName)
	{
		final I_AD_Sequence adSequence = create(getCtx(), I_AD_Sequence.class, ITrx.TRXNAME_None);
		adSequence.setAD_Org_ID(0);
		adSequence.setName(IDocumentNoBuilder.PREFIX_DOCSEQ + tableName);
		adSequence.setDescription("DocumentNo/Value for Table " + tableName);
		// adSequence.setPrefix(prefix);
		adSequence.setIsTableID(false);
		adSequence.setIsAutoSequence(true);
		save(adSequence);
	}

	protected I_ESR_ImportLine setupESR_ImportLine(
			@NonNull final String invDocNo,
			@NonNull final String invAmount,
			final boolean invPaid,
			@NonNull final String fullRefNo,
			@NonNull final String ESR_RenderedAccountNo,
			@NonNull final String partnerValue,
			@NonNull final String payAmt,
			final boolean createAllocation)
	{
		return setupESR_ImportLine(invDocNo, invAmount, invPaid, fullRefNo, ESR_RenderedAccountNo, partnerValue, payAmt, createAllocation, false);
	}

	protected I_ESR_ImportLine setupESR_ImportLine(
			@NonNull final String invDocNo,
			@NonNull final String invAmount,
			final boolean invPaid,
			@NonNull final String fullRefNo,
			@NonNull final String ESR_RenderedAccountNo,
			@NonNull final String partnerValue,
			@NonNull final String payAmt,
			final boolean createAllocation,
			final boolean differentInvoiceOrg)
	{
		// org
		final I_AD_Org org = getAD_Org();
		org.setValue("106");
		save(org);

		// second org
		final I_AD_Org org1 = newInstance(I_AD_Org.class);
		org1.setValue("105");
		save(org1);

		// partner
		partner = newInstance(I_C_BPartner.class, contextProvider);
		partner.setValue(partnerValue);
		partner.setAD_Org_ID(org.getAD_Org_ID());
		save(partner);

		final I_C_ReferenceNo_Type refNoType = newInstance(I_C_ReferenceNo_Type.class, contextProvider);
		refNoType.setName("InvoiceReference");
		save(refNoType);

		// currency
		final CurrencyId currencyEUR = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		// bank account
		final I_C_BP_BankAccount account = newInstance(I_C_BP_BankAccount.class, contextProvider);
		account.setC_Bank_ID(999);
		account.setIsEsrAccount(true);
		account.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
		account.setAD_User_ID(Env.getAD_User_ID(getCtx()));
		account.setESR_RenderedAccountNo(ESR_RenderedAccountNo);
		account.setC_Currency_ID(currencyEUR.getRepoId());
		save(account);

		// doc type
		final I_C_DocType type = newInstance(I_C_DocType.class, contextProvider);
		type.setDocBaseType(X_C_DocType.DOCBASETYPE_ARInvoice);
		save(type);

		// invoice
		final BigDecimal invoiceGrandTotal = new BigDecimal(invAmount);
		invoice = newInstance(I_C_Invoice.class, contextProvider);
		if (differentInvoiceOrg)
		{
			invoice.setAD_Org_ID(org1.getAD_Org_ID());
		}
		else
		{
			invoice.setAD_Org_ID(org.getAD_Org_ID());
		}
		invoice.setGrandTotal(invoiceGrandTotal);
		invoice.setC_BPartner_ID(partner.getC_BPartner_ID());
		invoice.setDocumentNo(invDocNo);
		invoice.setC_DocType_ID(type.getC_DocType_ID());
		invoice.setC_Currency_ID(currencyEUR.getRepoId());
		invoice.setIsPaid(invPaid);
		invoice.setIsSOTrx(true);
		invoice.setProcessed(true);
		save(invoice);

		// reference no
		final I_C_ReferenceNo referenceNo = newInstance(I_C_ReferenceNo.class, contextProvider);
		referenceNo.setReferenceNo(fullRefNo);
		referenceNo.setC_ReferenceNo_Type(refNoType);
		referenceNo.setIsManual(true);
		if (differentInvoiceOrg)
		{
			referenceNo.setAD_Org_ID(org1.getAD_Org_ID());
		}
		else
		{
			referenceNo.setAD_Org_ID(org.getAD_Org_ID());
		}
		save(referenceNo);

		// reference nodoc
		final I_C_ReferenceNo_Doc esrReferenceNumberDocument = newInstance(I_C_ReferenceNo_Doc.class, contextProvider);
		esrReferenceNumberDocument.setAD_Table_ID(Services.get(IADTableDAO.class).retrieveTableId(I_C_Invoice.Table_Name));
		esrReferenceNumberDocument.setRecord_ID(invoice.getC_Invoice_ID());
		esrReferenceNumberDocument.setC_ReferenceNo(referenceNo);
		save(esrReferenceNumberDocument);

		// esr line
		final List<I_ESR_ImportLine> lines = new ArrayList<>();
		final I_ESR_Import esrImport = createImport();
		esrImport.setC_BP_BankAccount_ID(account.getC_BP_BankAccount_ID());
		save(esrImport);

		final I_ESR_ImportLine esrImportLine = newInstance(I_ESR_ImportLine.class, contextProvider);
		esrImportLine.setESR_Import(esrImport);
		esrImportLine.setC_BP_BankAccount(account);
		esrImportLine.setAD_Org_ID(org.getAD_Org_ID());
		esrImportLine.setESRPostParticipantNumber(ESR_RenderedAccountNo.replaceAll("-", ""));
		esrImportLine.setESRFullReferenceNumber(fullRefNo);
		esrImportLine.setAmount(new BigDecimal(payAmt));
		save(esrImportLine);

		lines.add(esrImportLine);

		if (createAllocation)
		{
			final I_C_AllocationHdr allocHdr = newInstance(I_C_AllocationHdr.class, contextProvider);
			allocHdr.setC_Currency_ID(currencyEUR.getRepoId());
			save(allocHdr);

			final I_C_AllocationLine allocAmt = newInstance(I_C_AllocationLine.class, contextProvider);
			allocAmt.setAmount(new BigDecimal(50.0));
			allocAmt.setC_Invoice_ID(invoice.getC_Invoice_ID());
			save(allocAmt);
		}

		return esrImportLine;
	}

	protected I_ESR_PostFinanceUserNumber createPostFinanceUserNumber(final I_C_BP_BankAccount account, final String esrNoForPostFinanceUser)
	{
		final I_ESR_PostFinanceUserNumber postFinanceUserNumber = newInstance(I_ESR_PostFinanceUserNumber.class);
		postFinanceUserNumber.setC_BP_BankAccount(account);
		postFinanceUserNumber.setESR_RenderedAccountNo(esrNoForPostFinanceUser);
		save(postFinanceUserNumber);

		return postFinanceUserNumber;
	}
}
