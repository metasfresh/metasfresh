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

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandidateEnqueueResult;
import de.metas.invoicecandidate.api.impl.PlainInvoicingParams;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
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
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Note;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.testcontainers.shaded.com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

public class C_Invoice_StepDef
{
	private final IPaymentTermRepository paymentTermRepo = Services.get(IPaymentTermRepository.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	private final IOrderDAO iOrderDAO = Services.get(IOrderDAO.class);

	private final StepDefData<I_C_Invoice> invoiceTable;
	private final StepDefData<I_C_Order> orderTable;
	private final StepDefData<I_C_BPartner> bPartnerTable;
	private final StepDefData<I_C_BPartner_Location> bPartnerLocationTable;

	public C_Invoice_StepDef(
			@NonNull final StepDefData<I_C_Invoice> invoiceTable,
			@NonNull final StepDefData<I_C_Order> orderTable,
			@NonNull final StepDefData<I_C_BPartner> bPartnerTable,
			@NonNull final StepDefData<I_C_BPartner_Location> bPartnerLocationTable)
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

	@And("validate invoice salesRep_ID")
	public void validate_invoice_salesRep(@NonNull final DataTable table)
	{
		final List<Map<String, String>> dataTable = table.asMaps();
		for (final Map<String, String> row : dataTable)
		{
			final String identifier = DataTableUtil.extractStringForColumnName(row, I_C_Invoice.COLUMNNAME_C_Invoice_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_C_Invoice invoice = invoiceTable.get(identifier);

			validateInvoiceSalesRep(invoice, row);
		}
	}

	@Then("^enqueue candidate for invoicing and after not more than (.*)s, the invoice is found$")
	public void generateInvoice0(final int timeoutSec, @NonNull final DataTable table) throws InterruptedException
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
		final I_C_Invoice_Candidate invoiceCandidateRecord = invoiceCandDAO.getById(invoiceCandidateId);

		final PInstanceId invoiceCandidatesSelectionId = DB.createT_Selection(ImmutableList.of(invoiceCandidateId.getRepoId()), ITrx.TRXNAME_None);

		final PlainInvoicingParams invoicingParams = new PlainInvoicingParams();
		invoicingParams.setIgnoreInvoiceSchedule(false);
		invoicingParams.setSupplementMissingPaymentTermIds(true);

		final IInvoiceCandidateEnqueueResult iInvoiceCandidateEnqueueResult = invoiceCandBL.enqueueForInvoicing()
				.setContext(Env.getCtx())
				.setFailIfNothingEnqueued(true)
				.setInvoicingParams(invoicingParams)
				.enqueueSelection(invoiceCandidatesSelectionId);


		//wait for the invoice to be created
		StepDefUtil.tryAndWait(timeoutSec, 500, invoiceCandidateRecord::isProcessed);

		final List<de.metas.adempiere.model.I_C_Invoice> invoices = invoiceDAO.getInvoicesForOrderIds(ImmutableList.of(targetOrderId));



		assertThat(invoices.size()).isEqualTo(1);

		final String invoiceIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Invoice.COLUMNNAME_C_Invoice_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);


		invoiceTable.put(invoiceIdentifier, invoices.get(0));
	}

	@Then("^enqueue invoice candidates for invoicing and after not more than (.*)s, the invoice is found$")
	public void generateInvoice(final int timeoutSec, @NonNull final DataTable table) throws InterruptedException
	{
		final List<I_C_Invoice_Candidate> invoiceCandidateIds = new ArrayList<>();
		final List<I_C_Order> orders = new ArrayList<>();

		for (final Map<String, String> row : table.asMaps())
		{
			final String orderIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Order.COLUMNNAME_C_Order_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_C_Order orderRecord = orderTable.get(orderIdentifier);
			final OrderId targetOrderId = OrderId.ofRepoId(orderRecord.getC_Order_ID());

			orders.add(orderRecord);

			//make sure the given invoice candidate is ready for processing
			final Supplier<Boolean> noInvoiceCandidateRecompute = () ->
			{
				final IInvoiceCandDAO.InvoiceableInvoiceCandIdResult invoiceableInvoiceCandId = invoiceCandDAO.getFirstInvoiceableInvoiceCandId(targetOrderId);
				return invoiceableInvoiceCandId.getFirstInvoiceableInvoiceCandId() != null;
			};

			StepDefUtil.tryAndWait(timeoutSec, 500, noInvoiceCandidateRecompute);

			final IInvoiceCandDAO.InvoiceableInvoiceCandIdResult invoiceableInvoiceCandId = invoiceCandDAO.getFirstInvoiceableInvoiceCandId(targetOrderId);
			final InvoiceCandidateId invoiceCandidateId = invoiceableInvoiceCandId.getFirstInvoiceableInvoiceCandId();

			final I_C_Invoice_Candidate invoiceCandidateRecord = invoiceCandDAO.getById(invoiceCandidateId);
			invoiceCandidateIds.add(invoiceCandidateRecord);
		}

		//enqueue invoice candidate
		final List<Integer> t_Selection = invoiceCandidateIds
				.stream()
				.map(ic -> ic.getC_Invoice_Candidate_ID())
				.collect(Collectors.toList());

		final PInstanceId invoiceCandidatesSelectionId = DB.createT_Selection(t_Selection, ITrx.TRXNAME_None);

		final PlainInvoicingParams invoicingParams = new PlainInvoicingParams();
		invoicingParams.setIgnoreInvoiceSchedule(false);
		invoicingParams.setSupplementMissingPaymentTermIds(false);
		invoicingParams.setAssumeOneInvoice(true);

		final IInvoiceCandidateEnqueueResult iInvoiceCandidateEnqueueResult = invoiceCandBL.enqueueForInvoicing()
																							.setContext(Env.getCtx())
																							.setFailIfNothingEnqueued(false)
																							.setInvoicingParams(invoicingParams)
																							.enqueueSelection(invoiceCandidatesSelectionId);

		final IInvoiceCandBL.IInvoiceGenerateResult result = invoiceCandBL.generateInvoicesFromQueue(Env.getCtx());

		//wait for the invoice to be created
		for (I_C_Invoice_Candidate candidate : invoiceCandidateIds)
		{
			StepDefUtil.tryAndWait(timeoutSec, 500, candidate::isProcessed);
		}

		final List<OrderLineId> orderLines = new ArrayList<>();
		for (final I_C_Order order : orders)
		{
			final List<I_C_OrderLine> lines = iOrderDAO.retrieveOrderLines(OrderId.ofRepoId(order.getC_Order_ID()));
			for (I_C_OrderLine ol: lines)
			{
				orderLines.add(OrderLineId.ofRepoId(ol.getC_OrderLine_ID()));
			}
		}

		final List<InvoiceId> invoices = invoiceDAO.retrieveInvoicesForOrderLineIds(orderLines);
		assertThat(invoices.size()).isEqualTo(1);

		final I_C_Invoice invoice = InterfaceWrapperHelper.load(invoices.get(0), I_C_Invoice.class);

		final String invoiceIdentifier = DataTableUtil.extractStringForColumnName(table.asMaps().get(0), I_C_Invoice.COLUMNNAME_C_Invoice_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		invoiceTable.put(invoiceIdentifier,invoice);
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

	private void validateInvoiceSalesRep(@NonNull final I_C_Invoice invoice, @NonNull final Map<String, String> row)
	{
		final int expectedSalesRep_ID = DataTableUtil.extractIntOrMinusOneForColumnName(row, "OPT." + I_C_Order.COLUMNNAME_SalesRep_ID);

		assertThat(invoice.getSalesRep_ID()).isEqualTo(expectedSalesRep_ID);
	}
}
