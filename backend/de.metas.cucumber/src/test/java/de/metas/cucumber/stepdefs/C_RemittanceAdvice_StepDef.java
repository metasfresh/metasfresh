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

package de.metas.cucumber.stepdefs;

import de.metas.banking.service.RemittanceAdviceBankingService;
import de.metas.bpartner.BPartnerBankAccountId;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.currency.CurrencyRepository;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.remittanceadvice.CreateRemittanceAdviceLineRequest;
import de.metas.remittanceadvice.CreateRemittanceAdviceRequest;
import de.metas.remittanceadvice.RemittanceAdvice;
import de.metas.remittanceadvice.RemittanceAdviceId;
import de.metas.remittanceadvice.RemittanceAdviceRepository;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_RemittanceAdvice;
import org.compiere.model.I_C_RemittanceAdvice_Line;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;

import java.math.BigDecimal;

import static org.compiere.model.I_C_RemittanceAdvice.COLUMNNAME_DateDoc;
import static org.compiere.model.I_C_RemittanceAdvice.COLUMNNAME_Destination_BP_BankAccount_ID;
import static org.compiere.model.I_C_RemittanceAdvice.COLUMNNAME_DocumentNo;
import static org.compiere.model.I_C_RemittanceAdvice.COLUMNNAME_Source_BP_BankAccount_ID;

public class C_RemittanceAdvice_StepDef
{
	private final C_RemittanceAdvice_StepDefData remittanceAdviceTable;
	private final C_RemittanceAdvice_Line_StepDefData remittanceAdviceLineTable;
	private final C_BP_BankAccount_StepDefData bpBankAccountTable;
	private final C_Invoice_StepDefData invoiceTable;

	private final CurrencyRepository currencyRepository = SpringContextHolder.instance.getBean(CurrencyRepository.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final RemittanceAdviceRepository remittanceAdviceRepository = SpringContextHolder.instance.getBean(RemittanceAdviceRepository.class);
	private final RemittanceAdviceBankingService remittanceAdviceBankingService = SpringContextHolder.instance.getBean(RemittanceAdviceBankingService.class);

	public C_RemittanceAdvice_StepDef(@NonNull final C_RemittanceAdvice_StepDefData remittanceAdviceTable,
									  @NonNull final C_RemittanceAdvice_Line_StepDefData remittanceAdviceLineTable,
									  @NonNull final C_BP_BankAccount_StepDefData bpBankAccountTable,
									  @NonNull final C_Invoice_StepDefData invoiceTable)
	{
		this.remittanceAdviceTable = remittanceAdviceTable;
		this.remittanceAdviceLineTable = remittanceAdviceLineTable;
		this.bpBankAccountTable = bpBankAccountTable;
		this.invoiceTable = invoiceTable;
	}

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

		final DocTypeId targetPaymentDocTypeId = docTypeDAO.getDocTypeId(DocTypeQuery.builder()
				.clientAndOrgId(clientId, orgId)
				.docBaseType(X_C_DocType.DOCBASETYPE_ARReceipt).build());
		reqBuilder.targetPaymentDocTypeId(targetPaymentDocTypeId);

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
				.remittedAmount(dataTableRow.getAsBigDecimal(I_C_RemittanceAdvice_Line.COLUMNNAME_RemittanceAmt));

		final I_C_RemittanceAdvice_Line remittanceAdviceLineRecord = remittanceAdviceRepository.createRemittanceAdviceLine(reqBuilder.build());

		dataTableRow.getAsOptionalIdentifier(I_C_RemittanceAdvice_Line.COLUMNNAME_C_Invoice_ID)
				.map(identifier -> identifier.lookupNotNullIdIn(invoiceTable))
				.ifPresent(invoiceId ->
				{
					remittanceAdviceLineRecord.setC_Invoice_ID(invoiceId.getRepoId());
					InterfaceWrapperHelper.saveRecord(remittanceAdviceLineRecord);
				});

		remittanceAdviceLineTable.put(dataTableRow.getAsIdentifier(), remittanceAdviceLineRecord);
	}

	@And("^the C_RemittanceAdvice identified by (.*) is processed$")
	public void processRemittanceAdvice(@NonNull final String remittanceAdviceIdentifier)
	{
		final RemittanceAdviceId remittanceAdviceId = remittanceAdviceTable.getId(remittanceAdviceIdentifier);
		final RemittanceAdvice remittanceAdvice = remittanceAdviceRepository.getRemittanceAdvice(remittanceAdviceId);
		remittanceAdviceBankingService.runForRemittanceAdvice(remittanceAdvice);
	}
}
