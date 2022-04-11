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

package de.metas.cucumber.stepdefs;

import com.google.common.collect.ImmutableList;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.invoicecandidate.api.impl.PlainInvoicingParams;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.process.PInstanceId;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID;
import static org.assertj.core.api.Assertions.*;

public class C_Invoice_Candidate_StepDef
{
	private final StepDefData<I_C_Invoice_Candidate> invoiceCandidateTable;
	private final StepDefData<I_C_Order> orderTable;
	private final StepDefData<I_C_OrderLine> orderLineTable;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IInvoiceCandidateHandlerBL invoiceCandidateHandlerBL = Services.get(IInvoiceCandidateHandlerBL.class);
	private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);

	public C_Invoice_Candidate_StepDef(
			@NonNull final StepDefData<I_C_Invoice_Candidate> invoiceCandidateTable,
			@NonNull final StepDefData<I_C_Order> orderTable,
			@NonNull final StepDefData<I_C_OrderLine> orderLineTable)
	{
		this.invoiceCandidateTable = invoiceCandidateTable;
		this.orderTable = orderTable;
		this.orderLineTable = orderLineTable;
	}

	@And("^after not more than (.*)s, C_Invoice_Candidate are found:$")
	public void find_C_Invoice_Candidate(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			StepDefUtil.tryAndWait(timeoutSec, 500, () -> load_C_Invoice_Candidate(row));
		}
	}

	@And("validate C_Invoice_Candidate:")
	public void validate_C_Invoice_Candidate(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String invoiceCandIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_Invoice_Candidate invoiceCandidate = invoiceCandidateTable.get(invoiceCandIdentifier);

			final BigDecimal qtyToInvoice = DataTableUtil.extractBigDecimalOrNullForColumnName(row, I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice);
			assertThat(invoiceCandidate.getQtyToInvoice()).isEqualTo(qtyToInvoice);

			final BigDecimal qtyOrdered = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_QtyOrdered);
			if (qtyOrdered != null)
			{
				assertThat(invoiceCandidate.getQtyOrdered()).isEqualTo(qtyOrdered);
			}

			final BigDecimal qtyDelivered = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_QtyDelivered);
			if (qtyDelivered != null)
			{
				assertThat(invoiceCandidate.getQtyDelivered()).isEqualTo(qtyDelivered);
			}

			final BigDecimal qtyToInvoiceOverride = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice_Override);
			if (qtyToInvoiceOverride != null)
			{
				assertThat(invoiceCandidate.getQtyToInvoice_Override()).isEqualTo(qtyToInvoiceOverride);
			}

			final String orderIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_C_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(orderIdentifier))
			{
				final I_C_Order order = orderTable.get(orderIdentifier);
				assertThat(invoiceCandidate.getC_Order_ID()).isEqualTo(order.getC_Order_ID());
			}

			final String orderLineIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_C_OrderLine_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(orderLineIdentifier))
			{
				final I_C_OrderLine orderLine = orderLineTable.get(orderLineIdentifier);
				assertThat(invoiceCandidate.getC_OrderLine_ID()).isEqualTo(orderLine.getC_OrderLine_ID());
			}
		}
	}

	@And("update C_Invoice_Candidate:")
	public void update_C_Invoice_Candidate(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String invoiceCandIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_Invoice_Candidate invoiceCandidate = invoiceCandidateTable.get(invoiceCandIdentifier);

			final BigDecimal qtyToInvoiceOverride = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice_Override);
			if (qtyToInvoiceOverride != null)
			{
				invoiceCandidate.setQtyToInvoice_Override(qtyToInvoiceOverride);
			}

			InterfaceWrapperHelper.saveRecord(invoiceCandidate);
			invoiceCandidateTable.putOrReplace(invoiceCandIdentifier, invoiceCandidate);
		}
	}

	@And("^there is no C_Invoice_Candidate for C_Order (.*)$")
	public void validate_no_C_Invoice_Candidate_created(@NonNull final String orderIdentifier)
	{
		final I_C_Order order = orderTable.get(orderIdentifier);

		final I_C_Invoice_Candidate candidate = queryBL.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_C_Order_ID, order.getC_Order_ID())
				.create()
				.firstOnlyOrNull(I_C_Invoice_Candidate.class);
		assertThat(candidate).isNull();

		final List<I_C_Invoice_Candidate> invoiceCandidates = invoiceCandidateHandlerBL.createMissingCandidatesFor(order);
		assertThat(invoiceCandidates).isEmpty();
	}

	@And("^process invoice candidates and wait (.*)s for C_Invoice_Candidate to be processed$")
	public void process_invoice_cand(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);

		for (final Map<String, String> row : tableRows)
		{
			final String invoiceCandIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_Invoice_Candidate invoiceCandidate = invoiceCandidateTable.get(invoiceCandIdentifier);
			InterfaceWrapperHelper.refresh(invoiceCandidate);

			final InvoiceCandidateId invoiceCandidateId = InvoiceCandidateId.ofRepoId(invoiceCandidate.getC_Invoice_Candidate_ID());

			final PInstanceId invoiceCandidatesSelectionId = DB.createT_Selection(ImmutableList.of(invoiceCandidateId.getRepoId()), Trx.TRXNAME_None);

			final PlainInvoicingParams invoicingParams = new PlainInvoicingParams();
			invoicingParams.setIgnoreInvoiceSchedule(false);
			invoicingParams.setSupplementMissingPaymentTermIds(true);

			invoiceCandBL.enqueueForInvoicing()
					.setContext(Env.getCtx())
					.setFailIfNothingEnqueued(true)
					.setInvoicingParams(invoicingParams)
					.enqueueSelection(invoiceCandidatesSelectionId);

			//wait for the invoice to be created
			StepDefUtil.tryAndWait(timeoutSec, 500, () -> isInvoiceCandidateProcessed(invoiceCandidate));

			DB.deleteT_Selection(invoiceCandidatesSelectionId, Trx.TRXNAME_None);
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
		invoiceCandidateTable.putOrReplace(invoiceCandIdentifier, invoiceCandidate.get());

		return true;
	}

	private boolean isInvoiceCandidateProcessed(@NonNull final I_C_Invoice_Candidate invoiceCandidate)
	{
		return invoiceCandidate.isProcessed();
	}
}
