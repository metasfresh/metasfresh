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

import de.metas.common.util.Check;
import de.metas.cucumber.stepdefs.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.ItemProvider;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.compiere.model.I_C_Invoice_Detail.COLUMNNAME_C_Invoice_Candidate_ID;

public class C_Invoice_Candidate_StepDef
{
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final C_Invoice_Candidate_StepDefData invoiceCandTable;
	private final C_Order_StepDefData orderTable;
	private final C_OrderLine_StepDefData orderLineTable;

	public C_Invoice_Candidate_StepDef(
			@NonNull final C_Invoice_Candidate_StepDefData invoiceCandTable,
			@NonNull final C_Order_StepDefData orderTable,
			@NonNull final C_OrderLine_StepDefData orderLineTable)
	{
		this.invoiceCandTable = invoiceCandTable;
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
	public void validate_C_Invoice_Candidate(@NonNull final DataTable dataTable) throws InterruptedException
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final I_C_Invoice_Candidate invoiceCandidate = StepDefUtil.tryAndWaitForItem(30, 500, () -> isInvoiceCandidateUpdated(row));

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

			final boolean isDeliveryClosed = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_IsDeliveryClosed, false);
			assertThat(invoiceCandidate.isDeliveryClosed()).isEqualTo(isDeliveryClosed);
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

	@NonNull
	private ItemProvider.ProviderResult<I_C_Invoice_Candidate> isInvoiceCandidateUpdated(@NonNull final Map<String, String> row)
	{
		final String invoiceCandidateIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_Invoice_Candidate invoiceCandidateRecord = invoiceCandTable.get(invoiceCandidateIdentifier);

		InterfaceWrapperHelper.refresh(invoiceCandidateRecord);

		if (invoiceCandDAO.isToRecompute(invoiceCandidateRecord))
		{
			return ItemProvider.ProviderResult.resultWasNotFound("C_Invoice_Candidate_ID=" + invoiceCandidateRecord.getC_Invoice_Candidate_ID() + " is not updated yet");
		}
		return ItemProvider.ProviderResult.resultWasFound(invoiceCandidateRecord);
	}
}
