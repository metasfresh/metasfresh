/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.cucumber.stepdefs.remittanceAdvice;

import de.metas.banking.service.RemittanceAdviceBankingService;
import de.metas.bpartner.BPartnerBankAccountId;
import de.metas.bpartner.BPartnerId;
import de.metas.cucumber.stepdefs.C_BP_BankAccount_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.cucumber.stepdefs.payment.C_Payment_StepDefData;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentDAO;
import de.metas.remittanceadvice.CreateRemittanceAdviceLineRequest;
import de.metas.remittanceadvice.CreateRemittanceAdviceRequest;
import de.metas.remittanceadvice.RemittanceAdvice;
import de.metas.remittanceadvice.RemittanceAdviceId;
import de.metas.remittanceadvice.RemittanceAdviceRepository;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.assertj.core.api.SoftAssertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.compiere.model.I_C_RemittanceAdvice;
import org.compiere.model.I_C_RemittanceAdvice_Line;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.compiere.model.I_C_RemittanceAdvice.COLUMNNAME_DateDoc;
import static org.compiere.model.I_C_RemittanceAdvice.COLUMNNAME_Destination_BP_BankAccount_ID;
import static org.compiere.model.I_C_RemittanceAdvice.COLUMNNAME_DocumentNo;
import static org.compiere.model.I_C_RemittanceAdvice.COLUMNNAME_SendAt;
import static org.compiere.model.I_C_RemittanceAdvice.COLUMNNAME_ServiceFeeAmount_Currency_ID;
import static org.compiere.model.I_C_RemittanceAdvice.COLUMNNAME_Source_BP_BankAccount_ID;

@RequiredArgsConstructor
public class C_RemittanceAdvice_StepDef
{
	private final C_RemittanceAdvice_StepDefData remittanceAdviceTable;
	private final C_RemittanceAdvice_Line_StepDefData remittanceAdviceLineTable;
	private final C_BP_BankAccount_StepDefData bpBankAccountTable;
	private final C_Invoice_StepDefData invoiceTable;
	private final C_Payment_StepDefData paymentTable;

	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	private final IPaymentDAO paymentDAO = Services.get(IPaymentDAO.class);

	private final CurrencyRepository currencyRepository = SpringContextHolder.instance.getBean(CurrencyRepository.class);
	private final RemittanceAdviceRepository remittanceAdviceRepository = SpringContextHolder.instance.getBean(RemittanceAdviceRepository.class);
	private final RemittanceAdviceBankingService remittanceAdviceBankingService = SpringContextHolder.instance.getBean(RemittanceAdviceBankingService.class);

	@And("metasfresh contains C_RemittanceAdvice")
	public void createRemittanceAdvice(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createRemittanceAdvice);
	}

	private void createRemittanceAdvice(@NonNull final DataTableRow dataTableRow)
	{
		final ClientId clientId = Env.getClientId();
		final OrgId orgId = Env.getOrgId();

		final CreateRemittanceAdviceRequest.CreateRemittanceAdviceRequestBuilder reqBuilder = CreateRemittanceAdviceRequest.builder()
				.clientId(clientId)
				.orgId(orgId);

		final BPartnerBankAccountId sourceBankAccountId = dataTableRow
				.getAsIdentifier(COLUMNNAME_Source_BP_BankAccount_ID)
				.lookupNotNullIdIn(bpBankAccountTable);
		reqBuilder
				.sourceBPartnerId(sourceBankAccountId.getBpartnerId())
				.sourceBPartnerBankAccountId(sourceBankAccountId);

		final BPartnerBankAccountId destinationBankAccountId = dataTableRow
				.getAsIdentifier(COLUMNNAME_Destination_BP_BankAccount_ID)
				.lookupNotNullIdIn(bpBankAccountTable);
		reqBuilder
				.destinationBPartnerId(destinationBankAccountId.getBpartnerId())
				.destinationBPartnerBankAccountId(destinationBankAccountId);

		final String documentNumber = dataTableRow.getAsString(COLUMNNAME_DocumentNo);
		reqBuilder
				.documentNumber(documentNumber)
				.externalDocumentNumber(documentNumber);

		reqBuilder.documentDate(dataTableRow.getAsInstant(COLUMNNAME_DateDoc));

		final DocTypeId docTypeId = docTypeDAO.getDocTypeId(DocTypeQuery.builder()
				.clientAndOrgId(clientId, orgId)
				.docBaseType(X_C_DocType.DOCBASETYPE_RemittanceAdvice).build());
		reqBuilder.docTypeId(docTypeId);

		final Money remittanceAmt = dataTableRow.getAsMoney("RemittanceAmt", currencyRepository::getCurrencyIdByCurrencyCode);
		reqBuilder.remittedAmountSum(remittanceAmt.toBigDecimal())
				.remittedAmountCurrencyId(remittanceAmt.getCurrencyId());

		final CurrencyCode serviceFeeCurrencyCode = CurrencyCode.ofThreeLetterCode(dataTableRow.getAsString(COLUMNNAME_ServiceFeeAmount_Currency_ID));
		final CurrencyId serviceFeeCurrencyId = currencyRepository.getCurrencyIdByCurrencyCode(serviceFeeCurrencyCode);
		reqBuilder.serviceFeeCurrencyId(serviceFeeCurrencyId);

		final DocTypeId targetPaymentDocTypeId = docTypeDAO.getDocTypeId(DocTypeQuery.builder()
				.clientAndOrgId(clientId, orgId)
				.docBaseType(X_C_DocType.DOCBASETYPE_ARReceipt).build());
		reqBuilder.targetPaymentDocTypeId(targetPaymentDocTypeId);

		reqBuilder.sendDate(dataTableRow.getAsInstant(COLUMNNAME_SendAt));

		final RemittanceAdvice remittanceAdvice = remittanceAdviceRepository.createRemittanceAdviceHeader(reqBuilder.build());

		final I_C_RemittanceAdvice remittanceAdviceRecord = InterfaceWrapperHelper.load(remittanceAdvice.getRemittanceAdviceId(), I_C_RemittanceAdvice.class);
		remittanceAdviceTable.put(dataTableRow.getAsIdentifier(), remittanceAdviceRecord);
	}

	@And("metasfresh contains C_RemittanceAdvice_Lines")
	public void createLines(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createRemittanceAdviceLine);
	}

	private void createRemittanceAdviceLine(@NonNull final DataTableRow dataTableRow)
	{
		final CreateRemittanceAdviceLineRequest.CreateRemittanceAdviceLineRequestBuilder reqBuilder = CreateRemittanceAdviceLineRequest.builder();

		final RemittanceAdviceId remittanceAdviceId = dataTableRow
				.getAsIdentifier(I_C_RemittanceAdvice_Line.COLUMNNAME_C_RemittanceAdvice_ID)
				.lookupNotNullIdIn(remittanceAdviceTable);

		reqBuilder.remittanceAdviceId(remittanceAdviceId)
				.orgId(Env.getOrgId())
				.lineIdentifier(dataTableRow.getAsString(I_C_RemittanceAdvice_Line.COLUMNNAME_LineIdentifier))
				.remittedAmount(dataTableRow.getAsBigDecimal(I_C_RemittanceAdvice_Line.COLUMNNAME_RemittanceAmt))
				.serviceFeeAmount(dataTableRow.getAsBigDecimal(I_C_RemittanceAdvice_Line.COLUMNNAME_ServiceFeeAmount))
				.paymentDiscountAmount(dataTableRow.getAsBigDecimal(I_C_RemittanceAdvice_Line.COLUMNNAME_PaymentDiscountAmt));

		// note that we don't set the actual invoice - we just set the identifier etc, such that the BL can later match the invoice
		dataTableRow.getAsOptionalIdentifier(I_C_RemittanceAdvice_Line.COLUMNNAME_InvoiceIdentifier + "_for")
				.map(identifier -> identifier.lookupNotNullIn(invoiceTable))
				.ifPresent(invoiceRecord ->
						{
							final DocBaseType docBaseType = docTypeDAO.getDocBaseTypeById(DocTypeId.ofRepoId(invoiceRecord.getC_DocType_ID()));
							reqBuilder.externalInvoiceDocBaseType(docBaseType.getCode());
							reqBuilder.bpartnerIdentifier(BPartnerId.ofRepoId(invoiceRecord.getC_BPartner_ID()));
							reqBuilder.invoiceIdentifier(IdentifierString.PREFIX_DOC + invoiceRecord.getDocumentNo());
							reqBuilder.dateInvoiced(TimeUtil.asInstant(invoiceRecord.getDateInvoiced()));
							reqBuilder.invoiceGrossAmount(invoiceRecord.getGrandTotal());
						}
				);

		final I_C_RemittanceAdvice_Line remittanceAdviceLineRecord = remittanceAdviceRepository.createRemittanceAdviceLine(reqBuilder.build());
		remittanceAdviceLineTable.put(dataTableRow.getAsIdentifier(), remittanceAdviceLineRecord);
	}

	@And("^the C_RemittanceAdvice identified by (.*) is completed$")
	public void invoice_action(@NonNull final String identifier)
	{
		final I_C_RemittanceAdvice invoice = remittanceAdviceTable.get(identifier);

		invoice.setDocAction(IDocument.ACTION_Complete);
		documentBL.processEx(invoice, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
	}

	@And("^the C_RemittanceAdvice identified by (.*) is processed$")
	public void processRemittanceAdvice(@NonNull final String remittanceAdviceIdentifier)
	{
		final RemittanceAdviceId remittanceAdviceId = remittanceAdviceTable.getId(remittanceAdviceIdentifier);
		final RemittanceAdvice remittanceAdvice = remittanceAdviceRepository.getRemittanceAdvice(remittanceAdviceId);
		remittanceAdviceBankingService.createPaymentAndAllocations(remittanceAdvice);
	}

	@And("load ServiceFeeInvoices from C_RemittanceAdvice_Lines")
	public void loadInvoicesFromRemittanceAdviceLines(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::loadInvoiceFromLine);
	}

	private void loadInvoiceFromLine(@NonNull final DataTableRow dataTableRow)
	{
		final StepDefDataIdentifier identifier = dataTableRow.getAsIdentifier();

		final I_C_RemittanceAdvice_Line remittanceAdviceLineRecord = remittanceAdviceLineTable.get(identifier);
		final InvoiceId invoiceId = InvoiceId.ofRepoIdOrNull(remittanceAdviceLineRecord.getService_Fee_Invoice_ID());

		assertThat(invoiceId).as("C_RemittanceAdvice_Line with Identifier %s has no Service_Fee_Invoice_ID", identifier.getAsString()).isNotNull();

		final I_C_Invoice invoiceRecord = invoiceDAO.getByIdInTrx(invoiceId);
		invoiceTable.put(
				dataTableRow.getAsIdentifier(I_C_RemittanceAdvice_Line.COLUMNNAME_Service_Fee_Invoice_ID),
				invoiceRecord);
	}

	@And("load Payments from C_RemittanceAdvices")
	public void loadPaymentsFromRemittanceAdvices(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::loadPaymentFromAdvice);
	}

	private void loadPaymentFromAdvice(@NonNull final DataTableRow dataTableRow)
	{
		final StepDefDataIdentifier identifier = dataTableRow.getAsIdentifier();

		final I_C_RemittanceAdvice remittanceAdviceRecord = remittanceAdviceTable.get(identifier);
		final PaymentId paymentId = PaymentId.ofRepoIdOrNull(remittanceAdviceRecord.getC_Payment_ID());

		assertThat(paymentId).as("C_RemittanceAdvice with Identifier %s has no C_Payment_ID", identifier.getAsString()).isNotNull();

		final I_C_Payment paymentRecord = paymentDAO.getById(paymentId);
		paymentTable.put(
				dataTableRow.getAsIdentifier(I_C_RemittanceAdvice.COLUMNNAME_C_Payment_ID),
				paymentRecord);
	}

	@And("validate C_RemittanceAdvice")
	public void validateC_RemittanceAdvice(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::validateRemittanceAdvice);
	}

	private void validateRemittanceAdvice(@NonNull final DataTableRow dataTableRow)
	{
		final StepDefDataIdentifier identifier = dataTableRow.getAsIdentifier();
		final I_C_RemittanceAdvice remittanceAdvice = identifier.lookupNotNullIn(remittanceAdviceTable);

		final SoftAssertions softly = new SoftAssertions();

		final BigDecimal remittanceAmt = dataTableRow.getAsMoney(I_C_RemittanceAdvice.COLUMNNAME_RemittanceAmt, currencyRepository::getCurrencyIdByCurrencyCode).toBigDecimal();
		softly.assertThat(remittanceAdvice.getRemittanceAmt()).as("RemittanceAmt for Identifier %s", identifier.getAsString()).isEqualByComparingTo(remittanceAmt);

		softly.assertAll();
	}
}
