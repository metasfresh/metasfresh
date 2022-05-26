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

import de.metas.cucumber.stepdefs.C_BPartner_Location_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.impl.PlainInvoicingParams;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.logging.LogManager;
import de.metas.order.OrderId;
import de.metas.payment.paymentterm.IPaymentTermRepository;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.impl.PaymentTermQuery;
import de.metas.process.PInstanceId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.testcontainers.shaded.com.google.common.collect.ImmutableList;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static de.metas.contracts.commission.model.I_C_Commission_Instance.COLUMNNAME_C_Invoice_Candidate_ID;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice_Override;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_C_Order.COLUMNNAME_C_Order_ID;

public class C_Invoice_StepDef
{
	private final static Logger logger = LogManager.getLogger(C_Invoice_StepDef.class);

	private final IPaymentTermRepository paymentTermRepo = Services.get(IPaymentTermRepository.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);

	private final C_Invoice_StepDefData invoiceTable;
	private final C_Order_StepDefData orderTable;
	private final C_BPartner_StepDefData bPartnerTable;
	private final C_BPartner_Location_StepDefData bPartnerLocationTable;

	public C_Invoice_StepDef(
			@NonNull final C_Invoice_StepDefData invoiceTable,
			@NonNull final C_Order_StepDefData orderTable,
			@NonNull final C_BPartner_StepDefData bPartnerTable,
			@NonNull final C_BPartner_Location_StepDefData bPartnerLocationTable)
	{
		this.invoiceTable = invoiceTable;
		this.orderTable = orderTable;
		this.bPartnerTable = bPartnerTable;
		this.bPartnerLocationTable = bPartnerLocationTable;
	}

	@And("validate created invoices")
	public void validate_created_invoices(@NonNull final DataTable table)
	{
		final List<Map<String, String>> dataTable = table.asMaps();
		for (final Map<String, String> row : dataTable)
		{
			final String identifier = DataTableUtil.extractStringForColumnName(row, I_C_Invoice.COLUMNNAME_C_Invoice_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

			final I_C_Invoice invoice = invoiceTable.get(identifier);

			validateInvoice(invoice, row);
		}
	}

	@Then("^enqueue candidate for invoicing and after not more than (.*)s, the invoice is found$")
	public void generateInvoice(final int timeoutSec, @NonNull final DataTable table) throws InterruptedException
	{
		final Map<String, String> row = table.asMaps().get(0);

		final String orderIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Order.COLUMNNAME_C_Order_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_C_Order orderRecord = orderTable.get(orderIdentifier);
		final OrderId targetOrderId = OrderId.ofRepoId(orderRecord.getC_Order_ID());

		//make sure the given invoice candidate is ready for processing
		StepDefUtil.tryAndWait(timeoutSec, 500, () -> isInvoiceCandidateReadyToBeProcessed(targetOrderId), () -> logCurrentContext(targetOrderId));

		//enqueue invoice candidate
		final I_C_Invoice_Candidate invoiceCandidateRecord = getFirstInvoiceCandidateByOrderId(targetOrderId);

		assertThat(invoiceCandidateRecord).isNotNull();

		final PInstanceId invoiceCandidatesSelectionId = DB.createT_Selection(ImmutableList.of(invoiceCandidateRecord.getC_Invoice_Candidate_ID()), null);

		final PlainInvoicingParams invoicingParams = new PlainInvoicingParams();
		invoicingParams.setIgnoreInvoiceSchedule(false);
		invoicingParams.setSupplementMissingPaymentTermIds(true);

		invoiceCandBL.enqueueForInvoicing()
				.setContext(Env.getCtx())
				.setFailIfNothingEnqueued(true)
				.setInvoicingParams(invoicingParams)
				.enqueueSelection(invoiceCandidatesSelectionId);

		//wait for the invoice to be created
		final Supplier<Boolean> invoiceCreated = () ->
		{
			final List<de.metas.adempiere.model.I_C_Invoice> invoices = invoiceDAO.getInvoicesForOrderIds(com.google.common.collect.ImmutableList.of(targetOrderId));
			if (invoices.isEmpty())
			{
				return false;
			}
			assertThat(invoices.size())
					.as("There may be just 1 invoice for C_Order_ID.Identifier %s", orderIdentifier)
					.isEqualTo(1);
			final String invoiceIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Invoice.COLUMNNAME_C_Invoice_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			invoiceTable.put(invoiceIdentifier, invoices.get(0));
			return true;
		};
		StepDefUtil.tryAndWait(timeoutSec, 500, invoiceCreated);
	}

	private void validateInvoice(@NonNull final I_C_Invoice invoice, @NonNull final Map<String, String> row)
	{
		final String bpartnerIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_BPartner.COLUMNNAME_C_BPartner_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final String bpartnerLocationIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final String poReference = DataTableUtil.extractStringForColumnName(row, "poReference");
		final String paymentTerm = DataTableUtil.extractStringForColumnName(row, "paymentTerm");
		final boolean processed = DataTableUtil.extractBooleanForColumnName(row, "processed");
		final String docStatus = DataTableUtil.extractStringForColumnName(row, "docStatus");

		final Integer expectedBPartnerId = bPartnerTable.getOptional(bpartnerIdentifier)
				.map(I_C_BPartner::getC_BPartner_ID)
				.orElseGet(() -> Integer.parseInt(bpartnerIdentifier));

		final Integer expectedBPartnerLocationId = bPartnerLocationTable.getOptional(bpartnerLocationIdentifier)
				.map(I_C_BPartner_Location::getC_BPartner_Location_ID)
				.orElseGet(() -> Integer.parseInt(bpartnerLocationIdentifier));

		assertThat(invoice.getC_BPartner_ID()).isEqualTo(expectedBPartnerId);
		assertThat(invoice.getC_BPartner_Location_ID()).isEqualTo(expectedBPartnerLocationId);
		assertThat(invoice.getPOReference()).isEqualTo(poReference);
		assertThat(invoice.isProcessed()).isEqualTo(processed);
		assertThat(invoice.getDocStatus()).isEqualTo(docStatus);

		final PaymentTermQuery query = PaymentTermQuery.builder()
				.orgId(StepDefConstants.ORG_ID)
				.value(paymentTerm)
				.build();
		final PaymentTermId paymentTermId = paymentTermRepo.retrievePaymentTermId(query)
				.orElse(null);

		assertThat(paymentTermId).isNotNull();
		assertThat(invoice.getC_PaymentTerm_ID()).isEqualTo(paymentTermId.getRepoId());
	}

	@Nullable
	private I_C_Invoice_Candidate getFirstInvoiceCandidateByOrderId(@NonNull final OrderId targetOrderId)
	{
		return queryBL.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_C_Order_ID, targetOrderId)
				.orderBy(I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID)
				.create()
				.first(I_C_Invoice_Candidate.class);
	}

	private boolean isInvoiceCandidateReadyToBeProcessed(@NonNull final OrderId targetOrderId)
	{
		final I_C_Invoice_Candidate invoiceableInvoiceCand = getFirstInvoiceCandidateByOrderId(targetOrderId);

		if (invoiceableInvoiceCand == null)
		{
			return false;
		}

		return invoiceableInvoiceCand.getQtyToInvoice().signum() > 0 || invoiceableInvoiceCand.getQtyToInvoice_Override().signum() > 0;
	}

	private void logCurrentContext(@NonNull final OrderId targetOrderId)
	{
		final StringBuilder message = new StringBuilder();

		message.append("Looking for invoice candidate with:").append("\n")
				.append(COLUMNNAME_C_Order_ID).append(" : ").append(targetOrderId).append("\n")
				.append(COLUMNNAME_QtyToInvoice).append(" > 0").append("\n")
				.append("OR ").append(COLUMNNAME_QtyToInvoice_Override).append(" > 0");

		message.append("C_Invoice_Candidate record:").append("\n");

		final I_C_Invoice_Candidate invoiceCandidateRecord = getFirstInvoiceCandidateByOrderId(targetOrderId);

		message.append(COLUMNNAME_C_Invoice_Candidate_ID).append(" : ").append(invoiceCandidateRecord.getC_Invoice_Candidate_ID()).append(" ; ")
				.append(COLUMNNAME_C_Order_ID).append(" : ").append(invoiceCandidateRecord.getC_Order_ID()).append(" ; ")
				.append(COLUMNNAME_QtyToInvoice).append(" : ").append(invoiceCandidateRecord.getQtyToInvoice()).append(" ; ")
				.append(COLUMNNAME_QtyToInvoice_Override).append(" : ").append(invoiceCandidateRecord.getQtyToInvoice_Override()).append(" ; ")
				.append("\n");

		logger.error("*** Error while looking for first invoice-able invoice candidate record, see current context: \n" + message);
	}
}
