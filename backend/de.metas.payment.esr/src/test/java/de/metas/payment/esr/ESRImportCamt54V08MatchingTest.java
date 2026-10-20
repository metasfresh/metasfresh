package de.metas.payment.esr;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.allocation.api.IAllocationDAO;
import de.metas.currency.CurrencyCode;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.document.engine.IDocument;
import de.metas.document.refid.model.I_C_ReferenceNo;
import de.metas.document.refid.model.I_C_ReferenceNo_Doc;
import de.metas.document.refid.model.I_C_ReferenceNo_Type;
import de.metas.interfaces.I_C_DocType;
import de.metas.invoice.InvoiceDocBaseType;
import de.metas.money.CurrencyId;
import de.metas.payment.PaymentId;
import de.metas.payment.esr.api.IESRImportDAO;
import de.metas.payment.esr.api.IESRLineHandlersService;
import de.metas.payment.esr.model.I_C_BP_BankAccount;
import de.metas.payment.esr.model.I_ESR_Import;
import de.metas.payment.esr.model.I_ESR_ImportFile;
import de.metas.payment.esr.model.I_ESR_ImportLine;
import de.metas.payment.esr.model.X_ESR_Import;
import de.metas.payment.esr.model.X_ESR_ImportLine;
import de.metas.payment.esr.spi.impl.DefaultESRLineHandler;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.table.api.IADTableDAO;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Payment;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.refresh;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.payment.esr
 * %%
 * Copyright (C) 2026 metas GmbH
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

/**
 * Imports a camt.054.001.08 file that credits one QR-reference payment (the synthetic sample {@code postfinance/camt054_v08_qrr_credit_sample.xml},
 * the same file the sample class imports) through the ESR import, the way {@link ESRImportTest} does for v11 text:
 * a completed CHF sales invoice whose ESR reference is the one in the file, import, process.
 * <p>
 * Expected values come from the file: one {@code TxDtls}, QRR reference {@code 000000000000000000000000218}, credit of CHF 6045.85.
 * The file's entry reference ({@code NtryRef}) is a placeholder; the importer compares it with the QR-IBAN of the organization's bank account,
 * so the test gives the account exactly that value as its QR-IBAN.
 * "Posted" (accounting) is not reachable in a unit test; it is checked on a running stack.
 */
public class ESRImportCamt54V08MatchingTest extends ESRTestBase
{
	private static final String FIXTURE = "/postfinance/camt054_v08_qrr_credit_sample.xml";
	private static final String ENTRY_REFERENCE_IN_FILE = "SAMPLE-NTRY-1";
	private static final String QRR_REFERENCE_IN_FILE = "000000000000000000000000218";
	private static final String AMOUNT_IN_FILE = "6045.85";

	@Test
	public void qrrCreditSample_matchesInvoiceWithSameQrr_openAmountZero_paymentCreated()
	{
		final ImportResult result = importFixtureAgainstInvoiceWithReference(QRR_REFERENCE_IN_FILE);
		final I_C_Invoice invoice = result.invoice;

		final List<I_ESR_ImportLine> lines = Services.get(IESRImportDAO.class).retrieveLines(result.esrImport);
		assertThat(lines).as("lines imported from the file").hasSize(1);

		final I_ESR_ImportLine line = lines.get(0);
		assertThat(line.getImportErrorMsg()).as("import error").isNull();
		assertThat(line.getMatchErrorMsg()).as("match error").isNull();
		assertThat(line.getC_Invoice_ID()).as("matched invoice").isEqualTo(invoice.getC_Invoice_ID());
		assertThat(line.getESRFullReferenceNumber()).as("reference taken from the file").isEqualTo(QRR_REFERENCE_IN_FILE);
		assertThat(line.isValid()).isTrue();
		assertThat(line.isProcessed()).isTrue();
		assertThat(line.getESR_Payment_Action()).isEqualTo(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Fit_Amounts);
		assertThat(line.getESR_Document_Status()).isEqualTo(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_TotallyMatched);
		assertThat(line.getESR_Invoice_Openamt()).as("open amount of the line's invoice").isEqualByComparingTo("0.00");

		refresh(invoice, true);
		assertThat(invoice.isPaid()).as("invoice paid").isTrue();

		final PaymentId paymentId = PaymentId.ofRepoIdOrNull(line.getC_Payment_ID());
		assertThat(paymentId).as("payment created for the line").isNotNull();
		final I_C_Payment payment = paymentDAO.getById(paymentId);
		assertThat(payment.getPayAmt()).isEqualByComparingTo(AMOUNT_IN_FILE);
		assertThat(payment.getC_Invoice_ID()).isEqualTo(invoice.getC_Invoice_ID());
		assertThat(payment.isAllocated()).isTrue();

		final List<I_C_AllocationLine> allocationLines = Services.get(IAllocationDAO.class).retrieveAllocationLines(invoice);
		assertThat(allocationLines).hasSize(1);
		assertThat(allocationLines.get(0).getAmount()).isEqualByComparingTo(AMOUNT_IN_FILE);
	}

	private static final class ImportResult
	{
		private final I_C_Invoice invoice;
		private final I_ESR_Import esrImport;

		private ImportResult(@NonNull final I_C_Invoice invoice, @NonNull final I_ESR_Import esrImport)
		{
			this.invoice = invoice;
			this.esrImport = esrImport;
		}
	}

	/**
	 * Creates a completed CHF sales invoice of the amount in the file carrying the given ESR reference, imports the fixture and processes the import.
	 */
	private ImportResult importFixtureAgainstInvoiceWithReference(@NonNull final String invoiceEsrReference)
	{
		final I_AD_Org org = getAD_Org();
		org.setValue("106");
		save(org);

		final I_C_BPartner partner = newInstance(I_C_BPartner.class, contextProvider);
		partner.setValue("123456");
		partner.setAD_Org_ID(org.getAD_Org_ID());
		save(partner);

		final I_C_ReferenceNo_Type refNoType = newInstance(I_C_ReferenceNo_Type.class, contextProvider);
		refNoType.setName("InvoiceReference");
		save(refNoType);

		final CurrencyId chf = PlainCurrencyDAO.createCurrencyId(CurrencyCode.CHF);

		final I_C_BP_BankAccount account = createBankAccount(true, org.getAD_Org_ID(), Env.getAD_User_ID(getCtx()), "01-067789-3", chf);
		account.setQR_IBAN(ENTRY_REFERENCE_IN_FILE);
		save(account);

		final I_C_DocType type = newInstance(I_C_DocType.class, contextProvider);
		type.setDocBaseType(X_C_DocType.DOCBASETYPE_ARInvoice);
		save(type);

		final I_C_Invoice invoice = newInstance(I_C_Invoice.class, contextProvider);
		invoice.setAD_Org_ID(org.getAD_Org_ID());
		invoice.setGrandTotal(new BigDecimal(AMOUNT_IN_FILE));
		invoice.setC_BPartner_ID(partner.getC_BPartner_ID());
		invoice.setDocumentNo("INV-1");
		invoice.setC_DocType_ID(type.getC_DocType_ID());
		invoice.setC_Currency_ID(chf.getRepoId());
		invoice.setIsFinancial(InvoiceDocBaseType.ofCode(type.getDocBaseType()).isFinancial());
		invoice.setIsSOTrx(true);
		invoice.setDocStatus(IDocument.STATUS_Completed);
		invoice.setProcessed(true);
		save(invoice);

		final I_C_ReferenceNo referenceNo = newInstance(I_C_ReferenceNo.class, contextProvider);
		referenceNo.setReferenceNo(invoiceEsrReference);
		referenceNo.setC_ReferenceNo_Type(refNoType);
		referenceNo.setIsManual(true);
		save(referenceNo);

		final I_C_ReferenceNo_Doc referenceNoDoc = newInstance(I_C_ReferenceNo_Doc.class, contextProvider);
		referenceNoDoc.setAD_Table_ID(Services.get(IADTableDAO.class).retrieveTableId(I_C_Invoice.Table_Name));
		referenceNoDoc.setRecord_ID(invoice.getC_Invoice_ID());
		referenceNoDoc.setC_ReferenceNo(referenceNo);
		save(referenceNoDoc);

		final I_ESR_Import esrImport = createImport();
		esrImport.setDataType(X_ESR_Import.DATATYPE_Camt54);
		esrImport.setC_BP_BankAccount_ID(account.getC_BP_BankAccount_ID());
		save(esrImport);

		final I_ESR_ImportFile esrImportFile = createImportFile(esrImport);
		esrImportFile.setDataType(X_ESR_Import.DATATYPE_Camt54);
		save(esrImportFile);

		Services.get(IESRLineHandlersService.class).registerESRLineListener(new DefaultESRLineHandler());

		try (final InputStream in = getClass().getResourceAsStream(FIXTURE))
		{
			assertThat(in).as("fixture %s", FIXTURE).isNotNull();
			esrImportBL.loadAndEvaluateESRImportStream(esrImportFile, in);
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}

		esrImportBL.process(esrImport);

		return new ImportResult(invoice, esrImport);
	}
}
