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

package de.metas.cucumber.stepdefs.invoicecandidate_inoutline;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.ItemProvider;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.invoicecandidate.C_Invoice_Candidate_StepDef;
import de.metas.cucumber.stepdefs.invoicecandidate.C_Invoice_Candidate_StepDefData;
import de.metas.cucumber.stepdefs.shipment.M_InOutLine_StepDefData;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.model.I_C_InvoiceCandidate_InOutLine;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.compiere.model.IQuery;
import org.slf4j.Logger;

import java.util.function.Supplier;
import java.util.stream.Collectors;

import static de.metas.invoicecandidate.model.I_C_InvoiceCandidate_InOutLine.COLUMNNAME_M_InOutLine_ID;

public class C_InvoiceCandidate_InOutLine_StepDef
{
	private final static Logger logger = LogManager.getLogger(C_InvoiceCandidate_InOutLine_StepDef.class);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final C_InvoiceCandidate_InOutLine_StepDefData invoiceCandInOuLineTable;
	private final C_Invoice_Candidate_StepDefData invoiceCandTable;
	private final M_InOutLine_StepDefData shipmentLineTable;

	private final C_Invoice_Candidate_StepDef invoiceCandidateStepDef;

	public C_InvoiceCandidate_InOutLine_StepDef(
			@NonNull final C_InvoiceCandidate_InOutLine_StepDefData invoiceCandInOuLineTable,
			@NonNull final C_Invoice_Candidate_StepDefData invoiceCandTable,
			@NonNull final M_InOutLine_StepDefData shipmentLineTable,
			@NonNull final C_Invoice_Candidate_StepDef invoiceCandidateStepDef)
	{
		this.invoiceCandInOuLineTable = invoiceCandInOuLineTable;
		this.invoiceCandTable = invoiceCandTable;
		this.shipmentLineTable = shipmentLineTable;
		this.invoiceCandidateStepDef = invoiceCandidateStepDef;
	}

	@And("validate created C_InvoiceCandidate_InOutLine")
	public void validate_C_InvoiceCandidate_InOutLine(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach((row) -> {
			Supplier<String> logContext = null;
			final InvoiceCandidateId invoiceCandidateId = row.getAsOptionalIdentifier(I_C_InvoiceCandidate_InOutLine.COLUMNNAME_C_Invoice_Candidate_ID)
					.map(invoiceCandTable::getId)
					.orElse(null);
			if (invoiceCandidateId != null)
			{
				logContext = () -> getContextInfo(invoiceCandidateId);
			}

			final int maxSecondsToWait = 120;
			try
			{
				StepDefUtil.tryAndWaitForItem(maxSecondsToWait, 1000, () -> isValid_C_InvoiceCandidate_InOutLine(row), logContext);
			}
			catch (final Throwable e)
			{
				invoiceCandidateStepDef.manuallyRecomputeInvoiceCandidate(e, row.asMap(), maxSecondsToWait);

				StepDefUtil.tryAndWaitForItem(5, 1000, () -> isValid_C_InvoiceCandidate_InOutLine(row), logContext);
			}
		});
	}

	@NonNull
	private ItemProvider.ProviderResult<I_C_InvoiceCandidate_InOutLine> isValid_C_InvoiceCandidate_InOutLine(@NonNull final DataTableRow row)
	{
		final IQueryBuilder<I_C_InvoiceCandidate_InOutLine> queryBuilder = queryBL.createQueryBuilder(I_C_InvoiceCandidate_InOutLine.class).addOnlyActiveRecordsFilter();

		row.getAsOptionalIdentifier(I_C_InvoiceCandidate_InOutLine.COLUMNNAME_C_Invoice_Candidate_ID)
				.map(invoiceCandTable::getId)
				.ifPresent(invoiceCandidateId -> queryBuilder.addEqualsFilter(I_C_InvoiceCandidate_InOutLine.COLUMNNAME_C_Invoice_Candidate_ID, invoiceCandidateId));
		row.getAsOptionalIdentifier(COLUMNNAME_M_InOutLine_ID)
				.map(shipmentLineTable::getId)
				.ifPresent(shipmentLineId -> queryBuilder.addEqualsFilter(COLUMNNAME_M_InOutLine_ID, shipmentLineId));
		row.getAsOptionalBigDecimal(I_C_InvoiceCandidate_InOutLine.COLUMNNAME_QtyDelivered)
				.ifPresent(qtyDelivered -> queryBuilder.addEqualsFilter(I_C_InvoiceCandidate_InOutLine.COLUMNNAME_QtyDelivered, qtyDelivered));

		final IQuery<I_C_InvoiceCandidate_InOutLine> query = queryBuilder.create();

		final I_C_InvoiceCandidate_InOutLine invoiceCandidateInOutLine = query.firstOnly(I_C_InvoiceCandidate_InOutLine.class);

		if (invoiceCandidateInOutLine == null)
		{
			return ItemProvider.ProviderResult.resultWasNotFound("No C_InvoiceCandidate_InOutLine found!"
					+ "\n\trow=" + row
					+ "\n\tquery=" + query);
		}

		row.getAsOptionalIdentifier(I_C_InvoiceCandidate_InOutLine.COLUMNNAME_C_InvoiceCandidate_InOutLine_ID)
				.ifPresent(invoiceCandidateInOutLineIdentifier -> invoiceCandInOuLineTable.putOrReplace(invoiceCandidateInOutLineIdentifier, invoiceCandidateInOutLine));

		return ItemProvider.ProviderResult.resultWasFound(invoiceCandidateInOutLine);
	}

	private String getContextInfo(@NonNull final InvoiceCandidateId invoiceCandidateId)
	{
		final String allocations = queryBL.createQueryBuilder(I_C_InvoiceCandidate_InOutLine.class)
				.addEqualsFilter(I_C_InvoiceCandidate_InOutLine.COLUMNNAME_C_Invoice_Candidate_ID, invoiceCandidateId)
				.create()
				.stream()
				.map(inoutLineAllocation -> "[C_InvoiceCandidate_InOutLine_ID - " + inoutLineAllocation.getC_InvoiceCandidate_InOutLine_ID()
						+ ": C_Invoice_Candidate_ID: " + inoutLineAllocation.getC_Invoice_Candidate_ID()
						+ " , M_InOut_Line_ID: " + inoutLineAllocation.getM_InOutLine_ID()
						+ " , QtyDelivered: " + inoutLineAllocation.getQtyDelivered() + "]")
				.collect(Collectors.joining(","));

		return "allocations: " + allocations;
	}
}
