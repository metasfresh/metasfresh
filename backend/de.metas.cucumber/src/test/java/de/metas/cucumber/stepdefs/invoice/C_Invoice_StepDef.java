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
import de.metas.banking.payment.paymentallocation.InvoiceToAllocate;
import de.metas.banking.payment.paymentallocation.InvoiceToAllocateQuery;
import de.metas.banking.payment.paymentallocation.PaymentAllocationRepository;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.invoicecandidate.C_Invoice_Candidate_StepDefData;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.impl.PlainInvoicingParams;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
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
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_Order;
import org.compiere.model.X_C_Invoice;
import org.compiere.util.DB;
import org.compiere.util.Env;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_C_Invoice.COLUMNNAME_AD_User_ID;
import static org.compiere.model.I_C_Invoice.COLUMNNAME_C_BPartner_Location_ID;
import static org.compiere.model.I_C_Invoice.COLUMNNAME_C_DocType_ID;
import static org.compiere.model.I_C_Invoice.COLUMNNAME_C_Invoice_ID;
import static org.compiere.model.I_C_Invoice.COLUMNNAME_IsPaid;
import static org.compiere.model.I_C_Invoice.COLUMNNAME_POReference;

public class C_Invoice_StepDef
{
	private final IPaymentTermRepository paymentTermRepo = Services.get(IPaymentTermRepository.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	private final PaymentAllocationRepository paymentAllocationRepository = SpringContextHolder.instance.getBean(PaymentAllocationRepository.class);

	private final StepDefData<I_C_Invoice> invoiceTable;
	private final C_Invoice_Candidate_StepDefData invoiceCandTable;
	private final StepDefData<I_C_Order> orderTable;
	private final StepDefData<I_C_BPartner> bpartnerTable;
	private final StepDefData<I_C_BPartner_Location> bPartnerLocationTable;
	private final StepDefData<I_AD_User> userTable;

	public C_Invoice_StepDef(
			@NonNull final StepDefData<I_C_Invoice> invoiceTable,
			@NonNull final C_Invoice_Candidate_StepDefData invoiceCandTable,
			@NonNull final StepDefData<I_C_Order> orderTable,
			@NonNull final StepDefData<I_C_BPartner> bpartnerTable,
			@NonNull final StepDefData<I_C_BPartner_Location> bPartnerLocationTable,
			@NonNull final StepDefData<I_AD_User> userTable)
	{
		this.invoiceTable = invoiceTable;
		this.invoiceCandTable = invoiceCandTable;
		this.orderTable = orderTable;
		this.bpartnerTable = bpartnerTable;
		this.bPartnerLocationTable = bPartnerLocationTable;
		this.userTable = userTable;
	}

	@And("validate created invoices")
	public void validate_created_invoices(@NonNull final DataTable table)
	{
		final List<Map<String, String>> dataTable = table.asMaps();
		for (final Map<String, String> row : dataTable)
		{
			final String identifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_ID + "." + TABLECOLUMN_IDENTIFIER);

			final I_C_Invoice invoice = invoiceTable.get(identifier);

			validateInvoice(invoice, row);
		}
	}

	@Then("^enqueue candidate for invoicing and after not more than (.*)s, the invoice is found$")
	public void generateInvoice(final int timeoutSec, @NonNull final DataTable table) throws InterruptedException
	{
		final Map<String, String> row = table.asMaps().get(0);

		final String orderIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Order.COLUMNNAME_C_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
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

		invoiceCandBL.enqueueForInvoicing()
				.setContext(Env.getCtx())
				.setFailIfNothingEnqueued(true)
				.setInvoicingParams(invoicingParams)
				.enqueueSelection(invoiceCandidatesSelectionId);

		//wait for the invoice to be created
		StepDefUtil.tryAndWait(timeoutSec, 500, invoiceCandidateRecord::isProcessed);

		final List<de.metas.adempiere.model.I_C_Invoice> invoices = invoiceDAO.getInvoicesForOrderIds(ImmutableList.of(targetOrderId));
		assertThat(invoices.size()).isEqualTo(1);

		final String invoiceIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_ID + "." + TABLECOLUMN_IDENTIFIER);
		invoiceTable.put(invoiceIdentifier, invoices.get(0));
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
		final Integer expectedBPartnerId = bpartnerTable.getOptional(bpartnerIdentifier)
				.map(I_C_BPartner::getC_BPartner_ID)
				.orElseGet(() -> Integer.parseInt(bpartnerIdentifier));
		assertThat(invoice.getC_BPartner_ID()).isEqualTo(expectedBPartnerId);

		final String bpartnerLocationIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_BPartner_Location_ID + "." + TABLECOLUMN_IDENTIFIER);
		final Integer expectedBPartnerLocationId = bPartnerLocationTable.getOptional(bpartnerLocationIdentifier)
				.map(I_C_BPartner_Location::getC_BPartner_Location_ID)
				.orElseGet(() -> Integer.parseInt(bpartnerLocationIdentifier));
		assertThat(invoice.getC_BPartner_Location_ID()).as("C_BPartner_Location_ID").isEqualTo(expectedBPartnerLocationId);

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

		// final String internalName = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Invoice.COLUMNNAME_AD_InputDataSource_ID + "." + I_AD_InputDataSource.COLUMNNAME_InternalName);
		// if (Check.isNotBlank(internalName))
		// {
		// 	final I_AD_InputDataSource dataSource = inputDataSourceDAO.retrieveInputDataSource(Env.getCtx(), internalName, true, Trx.TRXNAME_None);
		// 	assertThat(invoice.getAD_InputDataSource_ID()).isEqualTo(dataSource.getAD_InputDataSource_ID());
		// }

		final String adUserIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_AD_User_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(adUserIdentifier))
		{
			final I_AD_User contact = userTable.get(adUserIdentifier);
			assertThat(invoice.getAD_User_ID()).isEqualTo(contact.getAD_User_ID());
		}

		{// payment related
			final Boolean invoiceIsPaid = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + COLUMNNAME_IsPaid, null);

			final BigDecimal invoiceOpenAmt = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT.OpenAmt");

			if (invoiceIsPaid != null)
			{
				assertThat(invoice.isPaid()).isEqualTo(invoiceIsPaid);
			}

			if (invoiceOpenAmt != null)
			{
				final InvoiceToAllocate invoiceToAllocate = paymentAllocationRepository
						.retrieveInvoicesToAllocate(InvoiceToAllocateQuery.builder()
															.evaluationDate(ZonedDateTime.now())
															.onlyInvoiceId(InvoiceId.ofRepoId(invoice.getC_Invoice_ID()))
															.build()).get(0);
				assertThat(invoiceToAllocate.getOpenAmountConverted().getAsBigDecimal()).isEqualTo(invoiceOpenAmt);
			}
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
}
