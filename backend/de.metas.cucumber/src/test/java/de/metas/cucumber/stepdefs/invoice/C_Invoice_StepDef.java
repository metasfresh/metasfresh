/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.cucumber.stepdefs.invoice;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.cucumber.stepdefs.C_BPartner_Location_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.doctype.C_DocType_StepDefData;
import de.metas.cucumber.stepdefs.invoicecandidate.C_Invoice_Candidate_StepDefData;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.payment.paymentterm.IPaymentTermRepository;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.impl.PaymentTermQuery;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.X_C_Invoice;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.invoicecandidate.model.I_C_InvoiceCandidate_InOutLine.COLUMNNAME_C_Invoice_Candidate_ID;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID;
import static org.compiere.model.I_C_Invoice.COLUMNNAME_C_Invoice_ID;

public class C_Invoice_StepDef
{
	private final IPaymentTermRepository paymentTermRepo = Services.get(IPaymentTermRepository.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);

	private final C_Invoice_StepDefData invoiceTable;
	private final C_BPartner_StepDefData bpartnerTable;
	private final C_BPartner_Location_StepDefData bPartnerLocationTable;
	private final C_Invoice_Candidate_StepDefData invoiceCandTable;
	private final C_DocType_StepDefData docTypeTable;

	public C_Invoice_StepDef(
			@NonNull final C_Invoice_StepDefData invoiceTable,
			@NonNull final C_BPartner_StepDefData bpartnerTable,
			@NonNull final C_BPartner_Location_StepDefData bPartnerLocationTable,
			@NonNull final C_Invoice_Candidate_StepDefData invoiceCandTable,
			@NonNull final C_DocType_StepDefData docTypeTable)
	{
		this.invoiceTable = invoiceTable;
		this.bpartnerTable = bpartnerTable;
		this.bPartnerLocationTable = bPartnerLocationTable;
		this.invoiceCandTable = invoiceCandTable;
		this.docTypeTable = docTypeTable;
	}

	@And("validate created invoices")
	public void validate_created_invoices(@NonNull final DataTable table)
	{
		final List<Map<String, String>> dataTable = table.asMaps();
		for (final Map<String, String> row : dataTable)
		{
			final String identifier = DataTableUtil.extractStringForColumnName(row, "Invoice.Identifier");

			final I_C_Invoice invoice = invoiceTable.get(identifier);

			validateInvoice(invoice, row);
		}
	}

	@And("^after not more than (.*)s, C_Invoice are found:$")
	public void wait_until_there_are_invoices(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		for (final Map<String, String> tableRow : dataTable.asMaps())
		{
			StepDefUtil.tryAndWait(timeoutSec, 500, () -> loadInvoice(tableRow));
		}
	}

	public Boolean loadInvoice(@NonNull final Map<String, String> row)
	{
		final String invoiceIdentifierCandidate = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_ID + "." + TABLECOLUMN_IDENTIFIER);
		final ImmutableList<String> invoiceIdentifiers = StepDefUtil.extractIdentifiers(invoiceIdentifierCandidate);

		if (invoiceIdentifiers.isEmpty())
		{
			throw new RuntimeException("No invoice identifier present for column: " + COLUMNNAME_C_Invoice_ID + "." + TABLECOLUMN_IDENTIFIER);
		}

		final boolean lookingForMultipleInvoices = invoiceIdentifiers.size() > 1;

		return lookingForMultipleInvoices ? loadMultipleInvoices(row) : loadSingleInvoiceByDocStatus(row);
	}

	private Boolean loadMultipleInvoices(@NonNull final Map<String, String> row)
	{
		final String invoiceCandIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_Invoice_Candidate invoiceCandidate = invoiceCandTable.get(invoiceCandIdentifier);

		final InvoiceCandidateId invoiceCandidateId = InvoiceCandidateId.ofRepoId(invoiceCandidate.getC_Invoice_Candidate_ID());

		final Set<InvoiceId> invoiceIds = invoiceCandDAO.retrieveIlForIc(invoiceCandidateId)
				.stream()
				.map(I_C_InvoiceLine::getC_Invoice_ID)
				.map(InvoiceId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());

		final String invoiceIdCandidate = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		final ImmutableList<String> invoiceIdentifiers = StepDefUtil.extractIdentifiers(invoiceIdCandidate);

		if (invoiceIds.size() != invoiceIdentifiers.size())
		{
			return false;
		}

		final List<I_C_Invoice> invoices = invoiceDAO.getByIdsOutOfTrx(invoiceIds)
				.stream()
				.sorted(Comparator.comparingInt(I_C_Invoice::getC_Invoice_ID))
				.collect(ImmutableList.toImmutableList());

		assertThat(invoices).isNotEmpty();
		assertThat(invoices.size()).isEqualTo(invoiceIdentifiers.size());

		for (int invoiceIndex = 0; invoiceIndex < invoices.size(); invoiceIndex++)
		{
			invoiceTable.putOrReplace(invoiceIdentifiers.get(invoiceIndex), invoices.get(invoiceIndex));
		}

		return true;
	}

	private Boolean loadSingleInvoiceByDocStatus(@NonNull final Map<String, String> row)
	{
		final String invoiceCandIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_Invoice_Candidate invoiceCandidate = invoiceCandTable.get(invoiceCandIdentifier);

		final InvoiceCandidateId invoiceCandidateId = InvoiceCandidateId.ofRepoId(invoiceCandidate.getC_Invoice_Candidate_ID());

		final Set<InvoiceId> invoiceIds = invoiceCandDAO.retrieveIlForIc(invoiceCandidateId)
				.stream()
				.map(I_C_InvoiceLine::getC_Invoice_ID)
				.map(InvoiceId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());

		if (invoiceIds.isEmpty())
		{
			return false;
		}

		final List<I_C_Invoice> invoices = invoiceDAO.getByIdsOutOfTrx(invoiceIds);

		final String invoiceStatus = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Invoice.COLUMNNAME_DocStatus);
		final String docStatus = Optional.ofNullable(invoiceStatus)
				.orElse(X_C_Invoice.DOCACTION_Complete);

		final Optional<I_C_Invoice> invoice = invoices.stream()
				.filter(i -> i.getDocStatus().equals(docStatus))
				.findFirst();

		if (!invoice.isPresent())
		{
			return false;
		}

		final String invoiceIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_ID + "." + TABLECOLUMN_IDENTIFIER);
		invoiceTable.putOrReplace(invoiceIdentifier, invoice.get());

		return true;
	}
	
	private void validateInvoice(@NonNull final I_C_Invoice invoice, @NonNull final Map<String, String> row)
	{
		InterfaceWrapperHelper.refresh(invoice);

		final String bPartnerIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_BPartner.COLUMNNAME_C_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
		final Integer expectedBPartnerId = bpartnerTable.getOptional(bPartnerIdentifier)
				.map(I_C_BPartner::getC_BPartner_ID)
				.orElseGet(() -> Integer.parseInt(bPartnerIdentifier));

		final String bpartnerLocationIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_BPartner_Location_ID + "." + TABLECOLUMN_IDENTIFIER);
		final Integer expectedBPartnerLocationId = bPartnerLocationTable.getOptional(bpartnerLocationIdentifier)
				.map(I_C_BPartner_Location::getC_BPartner_Location_ID)
				.orElseGet(() -> Integer.parseInt(bpartnerLocationIdentifier));

		final String poReference = DataTableUtil.extractStringForColumnName(row, "poReference");
		final String paymentTerm = DataTableUtil.extractStringForColumnName(row, "paymentTerm");
		final boolean processed = DataTableUtil.extractBooleanForColumnName(row, "processed");
		final String docStatus = DataTableUtil.extractStringForColumnName(row, "docStatus");

		final SoftAssertions softly = new SoftAssertions();

		softly.assertThat(invoice.getC_BPartner_ID()).isEqualTo(expectedBPartnerId);
		softly.assertThat(invoice.getC_BPartner_Location_ID()).isEqualTo(expectedBPartnerLocationId);
		softly.assertThat(invoice.getPOReference()).isEqualTo(poReference);
		softly.assertThat(invoice.isProcessed()).isEqualTo(processed);
		softly.assertThat(invoice.getDocStatus()).isEqualTo(docStatus);

		final PaymentTermQuery query = PaymentTermQuery.builder()
				.orgId(StepDefConstants.ORG_ID)
				.value(paymentTerm)
				.build();
		final PaymentTermId paymentTermId = paymentTermRepo.retrievePaymentTermId(query)
				.orElse(null);

		softly.assertThat(paymentTermId).isNotNull();
		softly.assertThat(invoice.getC_PaymentTerm_ID()).isEqualTo(paymentTermId.getRepoId());
		
		final BigDecimal grandTotal = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_Invoice.COLUMNNAME_GrandTotal);
		if (grandTotal != null)
		{
			softly.assertThat(invoice.getGrandTotal()).isEqualByComparingTo(grandTotal);
		}

		final String docTypeIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Invoice.COLUMNNAME_C_DocType_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(docTypeIdentifier))
		{
			final I_C_DocType docTypeRecord = docTypeTable.get(docTypeIdentifier);
			softly.assertThat(docTypeRecord).isNotNull();

			softly.assertThat(invoice.getC_DocType_ID()).isEqualTo(docTypeRecord.getC_DocType_ID());
		}

		softly.assertAll();
	}
}
