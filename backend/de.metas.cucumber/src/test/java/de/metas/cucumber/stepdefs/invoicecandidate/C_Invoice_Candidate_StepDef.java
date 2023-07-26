/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.cucumber.stepdefs.invoicecandidate;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.common.util.Check;
import de.metas.cucumber.stepdefs.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.ItemProvider;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.doctype.C_DocType_StepDefData;
import de.metas.cucumber.stepdefs.shipment.M_InOut_StepDefData;
import de.metas.edi.model.I_M_InOut;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.logging.LogManager;
import de.metas.rest_api.v2.invoice.impl.InvoiceService;
import de.metas.util.Loggables;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.logging.LogbackLoggable;
import org.assertj.core.api.SoftAssertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_QtyDelivered;
import static org.compiere.model.I_C_Invoice_Detail.COLUMNNAME_C_Invoice_Candidate_ID;

public class C_Invoice_Candidate_StepDef
{
	private final static Logger logger = LogManager.getLogger(C_Invoice_Candidate_StepDef.class);

	private final InvoiceService invoiceService = SpringContextHolder.instance.getBean(InvoiceService.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final C_Invoice_Candidate_StepDefData invoiceCandTable;
	private final C_Order_StepDefData orderTable;
	private final C_OrderLine_StepDefData orderLineTable;
	private final M_InOut_StepDefData shipmentTable;
	private final C_DocType_StepDefData docTypeTable;

	public C_Invoice_Candidate_StepDef(
			@NonNull final C_Invoice_Candidate_StepDefData invoiceCandTable,
			@NonNull final C_Order_StepDefData orderTable,
			@NonNull final C_OrderLine_StepDefData orderLineTable,
			@NonNull final M_InOut_StepDefData shipmentTable,
			@NonNull final C_DocType_StepDefData docTypeTable)
	{
		this.invoiceCandTable = invoiceCandTable;
		this.orderTable = orderTable;
		this.orderLineTable = orderLineTable;
		this.shipmentTable = shipmentTable;
		this.docTypeTable = docTypeTable;
	}

	@And("^after not more than (.*)s, C_Invoice_Candidate are found:$")
	public void find_C_Invoice_Candidate(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			StepDefUtil.tryAndWait(timeoutSec, 500, () -> load_C_Invoice_Candidate(row));
		}
	}

	@And("^after not more than (.*)s, credit memo candidates are found:$")
	public void find_credit_memo_candidates(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			StepDefUtil.tryAndWait(timeoutSec, 500, () -> loadCreditMemoCandidate(row));
		}
	}

	@And("validate C_Invoice_Candidate:")
	public void validate_C_Invoice_Candidate(@NonNull final DataTable dataTable) throws InterruptedException
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final I_C_Invoice_Candidate invoiceCandidate = StepDefUtil.tryAndWaitForItem(30, 500, () -> isInvoiceCandidateUpdated(row));

			final SoftAssertions softly = new SoftAssertions();
			
			final BigDecimal qtyToInvoice = DataTableUtil.extractBigDecimalOrNullForColumnName(row, I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice);
			softly.assertThat(invoiceCandidate.getQtyToInvoice()).isEqualTo(qtyToInvoice);

			final BigDecimal qtyOrdered = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_QtyOrdered);
			if (qtyOrdered != null)
			{
				softly.assertThat(invoiceCandidate.getQtyOrdered()).isEqualTo(qtyOrdered);
			}

			final BigDecimal qtyDelivered = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_QtyDelivered);
			if (qtyDelivered != null)
			{
				softly.assertThat(invoiceCandidate.getQtyDelivered()).isEqualTo(qtyDelivered);
			}

			final BigDecimal qtyToInvoiceOverride = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice_Override);
			if (qtyToInvoiceOverride != null)
			{
				softly.assertThat(invoiceCandidate.getQtyToInvoice_Override()).isEqualTo(qtyToInvoiceOverride);
			}

			final String orderIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_C_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(orderIdentifier))
			{
				final I_C_Order order = orderTable.get(orderIdentifier);
				softly.assertThat(invoiceCandidate.getC_Order_ID()).isEqualTo(order.getC_Order_ID());
			}

			final String orderLineIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_C_OrderLine_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(orderLineIdentifier))
			{
				final I_C_OrderLine orderLine = orderLineTable.get(orderLineIdentifier);
				softly.assertThat(invoiceCandidate.getC_OrderLine_ID()).isEqualTo(orderLine.getC_OrderLine_ID());
			}

			final String docTypeIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + org.compiere.model.I_M_InOut.COLUMNNAME_C_DocType_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(docTypeIdentifier))
			{
				final I_C_DocType docTypeRecord = docTypeTable.get(docTypeIdentifier);
				softly.assertThat(docTypeRecord).isNotNull();
				softly.assertThat(invoiceCandidate.getC_DocTypeInvoice_ID()).isEqualTo(docTypeRecord.getC_DocType_ID());
			}

			final boolean isDeliveryClosed = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_IsDeliveryClosed, false);
			softly.assertThat(invoiceCandidate.isDeliveryClosed()).isEqualTo(isDeliveryClosed);

			softly.assertAll();
		}
	}

	@And("process invoice candidates")
	public void process_invoice_cand(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);

		for (final Map<String, String> row : tableRows)
		{
			try (final IAutoCloseable ignore = Loggables.temporarySetLoggable(new LogbackLoggable(logger, Level.INFO)))
			{
				final String invoiceCandIdentifiers = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);

				final Set<InvoiceCandidateId> invoiceCandidateIds = StepDefUtil.splitIdentifiers(invoiceCandIdentifiers)
						.stream()
						.map(invoiceCandTable::get)
						.map(I_C_Invoice_Candidate::getC_Invoice_Candidate_ID)
						.map(InvoiceCandidateId::ofRepoId)
						.collect(ImmutableSet.toImmutableSet());

				invoiceService.processInvoiceCandidates(invoiceCandidateIds);
			}
		}
	}

	@And("^after not more than (.*)s, C_Invoice_Candidates are not marked as 'to recompute'$")
	public void check_not_marked_as_to_recompute(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		for (final Map<String, String> tableRow : dataTable.asMaps())
		{
			StepDefUtil.tryAndWaitForItem(timeoutSec, 500,
										  () -> isInvoiceCandidateUpdated(tableRow));
		}
	}

	private boolean load_C_Invoice_Candidate(@NonNull final Map<String, String> row)
	{
		final String orderLineIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Invoice_Candidate.COLUMNNAME_C_OrderLine_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_OrderLine orderLine = orderLineTable.get(orderLineIdentifier);

		final BigDecimal qtyToInvoice = DataTableUtil.extractBigDecimalOrNullForColumnName(row, I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice);

		final Optional<I_C_Invoice_Candidate> invoiceCandidate = queryBL.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_C_OrderLine_ID, orderLine.getC_OrderLine_ID())
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice, qtyToInvoice)
				.create()
				.firstOnlyOptional(I_C_Invoice_Candidate.class);

		if (!invoiceCandidate.isPresent())
		{
			return false;
		}

		final String invoiceCandIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);
		invoiceCandTable.putOrReplace(invoiceCandIdentifier, invoiceCandidate.get());

		return true;
	}

	private boolean loadCreditMemoCandidate(@NonNull final Map<String, String> row)
	{
		final String customerReturnIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_InOut.COLUMNNAME_M_InOut_ID + "." + TABLECOLUMN_IDENTIFIER);
		final int customerReturnId = shipmentTable.get(customerReturnIdentifier).getM_InOut_ID();

		final Optional<I_C_Invoice_Candidate> invoiceCandidate = queryBL.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_M_InOut_ID, customerReturnId)
				.create()
				.firstOnlyOptional(I_C_Invoice_Candidate.class);

		if (!invoiceCandidate.isPresent())
		{
			return false;
		}

		final String invoiceCandIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);
		invoiceCandTable.putOrReplace(invoiceCandIdentifier, invoiceCandidate.get());

		return true;
	}

	@NonNull
	private ItemProvider.ProviderResult<I_C_Invoice_Candidate> isInvoiceCandidateUpdated(@NonNull final Map<String, String> row)
	{
		final String invoiceCandidateIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_Invoice_Candidate invoiceCandidateRecord = invoiceCandTable.get(invoiceCandidateIdentifier);

		InterfaceWrapperHelper.refresh(invoiceCandidateRecord);

		final ImmutableList.Builder<String> errorCollectors = ImmutableList.builder();

		final BigDecimal qtyDelivered = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_QtyDelivered);

		if (qtyDelivered != null)
		{
			if (invoiceCandidateRecord.getQtyDelivered().compareTo(qtyDelivered) != 0)
			{
				errorCollectors.add(MessageFormat.format("C_Invoice_Candidate_ID={0}; Expecting QtyDelivered={1} but actual is {2}",
														 invoiceCandidateRecord.getC_Invoice_Candidate_ID(), qtyDelivered, invoiceCandidateRecord.getQtyDelivered()));
			}
		}

		if (invoiceCandDAO.isToRecompute(invoiceCandidateRecord))
		{
			errorCollectors.add("C_Invoice_Candidate_ID=" + invoiceCandidateRecord.getC_Invoice_Candidate_ID() + " is not updated yet");
		}

		final List<String> errors = errorCollectors.build();

		if (errors.size() > 0)
		{
			final String errorMessages = String.join(" && \n", errors);
			return ItemProvider.ProviderResult.resultWasNotFound(errorMessages);
		}

		return ItemProvider.ProviderResult.resultWasFound(invoiceCandidateRecord);
	}
}
