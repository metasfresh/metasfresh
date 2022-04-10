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
import de.metas.cucumber.stepdefs.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.invoicecandidate.C_Invoice_Candidate_StepDefData;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.impl.PlainInvoicingParams;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.logging.LogManager;
import de.metas.order.OrderId;
import de.metas.payment.paymentterm.IPaymentTermRepository;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.impl.PaymentTermQuery;
import de.metas.process.PInstanceId;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_Order;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID;
import static org.compiere.model.I_C_Invoice.COLUMNNAME_C_DocType_ID;
import static org.compiere.model.I_C_Invoice.COLUMNNAME_C_Invoice_ID;
import static org.compiere.model.I_C_Invoice.COLUMNNAME_POReference;

public class C_Invoice_StepDef
{
	private final static transient Logger logger = LogManager.getLogger(C_Invoice_StepDef.class);

	private final IPaymentTermRepository paymentTermRepo = Services.get(IPaymentTermRepository.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);

	private final C_Invoice_StepDefData invoiceTable;
	private final C_Invoice_Candidate_StepDefData invoiceCandTable;
	private final C_Order_StepDefData orderTable;
	private final C_BPartner_StepDefData bpartnerTable;
	private final C_BPartner_Location_StepDefData bPartnerLocationTable;

	public C_Invoice_StepDef(
			@NonNull final C_Invoice_StepDefData invoiceTable,
			@NonNull final C_Invoice_Candidate_StepDefData invoiceCandTable,
			@NonNull final C_Order_StepDefData orderTable,
			@NonNull final C_OrderLine_StepDefData orderLineTable,
			@NonNull final C_BPartner_StepDefData bpartnerTable,
			@NonNull final C_BPartner_Location_StepDefData bPartnerLocationTable)
	{
		this.invoiceTable = invoiceTable;
		this.invoiceCandTable = invoiceCandTable;
		this.orderTable = orderTable;
		this.bpartnerTable = bpartnerTable;
		this.bPartnerLocationTable = bPartnerLocationTable;
	}

	@And("validate created invoices")
	public void validate_created_invoices(@NonNull final DataTable table)
	{
		final List<Map<String, String>> dataTable = table.asMaps();
		for (final Map<String, String> row : dataTable)
		{
			final String identifier = DataTableUtil.extractStringForColumnName(row, I_C_Invoice.COLUMNNAME_C_Invoice_ID + "." + TABLECOLUMN_IDENTIFIER);

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
		final Supplier<Boolean> noInvoiceCandidateRecompute = () ->
		{
			final IInvoiceCandDAO.InvoiceableInvoiceCandIdResult invoiceableInvoiceCandId = invoiceCandDAO.getFirstInvoiceableInvoiceCandId(targetOrderId);

			return invoiceableInvoiceCandId.getFirstInvoiceableInvoiceCandId() != null;
		};

		StepDefUtil.tryAndWait(timeoutSec, 500, noInvoiceCandidateRecompute);

		final IInvoiceCandDAO.InvoiceableInvoiceCandIdResult invoiceableInvoiceCandId = invoiceCandDAO.getFirstInvoiceableInvoiceCandId(targetOrderId);
		final InvoiceCandidateId invoiceCandidateId = invoiceableInvoiceCandId.getFirstInvoiceableInvoiceCandId();

		//enqueue invoice candidate
		final PInstanceId invoiceCandidatesSelectionId = DB.createT_Selection(ImmutableList.of(invoiceCandidateId.getRepoId()), ITrx.TRXNAME_None);

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
			final List<de.metas.adempiere.model.I_C_Invoice> invoices = invoiceDAO.getInvoicesForOrderIds(ImmutableList.of(targetOrderId));
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

	@And("^after not more than (.*)s, C_Invoice are found:$")
	public void wait_until_there_are_invoices(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		for (final Map<String, String> tableRow : dataTable.asMaps())
		{
			StepDefUtil.tryAndWait(timeoutSec, 500, () -> loadInvoice(tableRow));
		}
	}

	private void validateInvoice(@NonNull final I_C_Invoice invoice, @NonNull final Map<String, String> row)
	{
		InterfaceWrapperHelper.refresh(invoice);

		final String bpartnerIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_BPartner.COLUMNNAME_C_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_BPartner bPartner = bpartnerTable.get(bpartnerIdentifier);
		assertThat(bPartner).isNotNull();
		assertThat(invoice.getC_BPartner_ID()).isEqualTo(bPartner.getC_BPartner_ID());

		final String bpartnerLocationIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_BPartner_Location_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_BPartner_Location bPartnerLocation = bPartnerLocationTable.get(bpartnerLocationIdentifier);
		assertThat(bPartnerLocation).isNotNull();
		assertThat(invoice.getC_BPartner_Location_ID()).as("C_BPartner_Location_ID").isEqualTo(bPartnerLocation.getC_BPartner_Location_ID());

		final String poReference = DataTableUtil.extractStringOrNullForColumnName(row, COLUMNNAME_POReference);
		if (Check.isNotBlank(poReference))
		{
			assertThat(invoice.getPOReference()).isEqualTo(poReference);
		}

		final String paymentTerm = DataTableUtil.extractStringForColumnName(row, "paymentTerm");
		final boolean processed = DataTableUtil.extractBooleanForColumnName(row, "processed");
		final String docStatus = DataTableUtil.extractStringForColumnName(row, "docStatus");

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

		final String docSubType = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_DocType.COLUMNNAME_DocSubType);
		if (Check.isNotBlank(docSubType))
		{
			final int docTargetTypeId = invoice.getC_DocTypeTarget_ID();
			final I_C_DocType docType = queryBL.createQueryBuilder(I_C_DocType.class)
					.addEqualsFilter(I_C_DocType.COLUMN_C_DocType_ID, docTargetTypeId)
					.create()
					.firstOnlyNotNull(I_C_DocType.class);

			assertThat(docType.getDocSubType()).isEqualTo(docSubType);
		}

		final String bpartnerAddress = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Invoice.COLUMNNAME_BPartnerAddress);
		if (Check.isNotBlank(bpartnerAddress))
		{
			assertThat(invoice.getBPartnerAddress()).isEqualTo(bpartnerAddress);
		}

		final String expectedDocTypeName = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_C_DocType_ID + "." + I_C_DocType.COLUMNNAME_Name);

		if (Check.isNotBlank(expectedDocTypeName))
		{
			final I_C_DocType actualInvoiceDocType = InterfaceWrapperHelper.load(invoice.getC_DocType_ID(), I_C_DocType.class);

			assertThat(actualInvoiceDocType.getName()).isEqualTo(expectedDocTypeName);
		}

		final String paymentRule = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Invoice.COLUMNNAME_PaymentRule);
		if (Check.isNotBlank(paymentRule))
		{
			assertThat(invoice.getPaymentRule()).isEqualTo(paymentRule);
		}

		assertThat(paymentTermId).isNotNull();
		assertThat(invoice.getC_PaymentTerm_ID()).isEqualTo(paymentTermId.getRepoId());
	}

	public Boolean loadInvoice(@NonNull final Map<String, String> row)
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
}
