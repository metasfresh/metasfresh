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

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.ItemProvider;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.invoicecandidate.C_Invoice_Candidate_StepDef;
import de.metas.cucumber.stepdefs.invoicecandidate.C_Invoice_Candidate_StepDefData;
import de.metas.cucumber.stepdefs.shipment.M_InOutLine_StepDefData;
import de.metas.invoicecandidate.model.I_C_InvoiceCandidate_InOutLine;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.compiere.model.I_M_InOutLine;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
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
	public void validate_C_InvoiceCandidate_InOutLine(@NonNull final DataTable dataTable) throws Throwable
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{

			Runnable logContext = () -> {};
			final String invoiceCandidateIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_InvoiceCandidate_InOutLine.COLUMNNAME_C_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(invoiceCandidateIdentifier))
			{
				final I_C_Invoice_Candidate invoiceCandidate = invoiceCandTable.get(invoiceCandidateIdentifier);
				logContext = () -> logCurrentContext(invoiceCandidate.getC_Invoice_Candidate_ID());
			}

			final int maxSecondsToWait = 120;
			try
			{
				StepDefUtil.tryAndWaitForItem(maxSecondsToWait, 1000, () -> isValid_C_InvoiceCandidate_InOutLine(row), logContext);
			}
			catch (final Throwable e)
			{
				invoiceCandidateStepDef.manuallyRecomputeInvoiceCandidate(e, row, maxSecondsToWait);

				StepDefUtil.tryAndWaitForItem(5, 1000, () -> isValid_C_InvoiceCandidate_InOutLine(row), logContext);
			}

		}
	}

	@NonNull
	private ItemProvider.ProviderResult<I_C_InvoiceCandidate_InOutLine> isValid_C_InvoiceCandidate_InOutLine(@NonNull final Map<String, String> row)
	{
		final IQueryBuilder<I_C_InvoiceCandidate_InOutLine> queryBuilder = queryBL.createQueryBuilder(I_C_InvoiceCandidate_InOutLine.class).addOnlyActiveRecordsFilter();

		final String invoiceCandidateIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_InvoiceCandidate_InOutLine.COLUMNNAME_C_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(invoiceCandidateIdentifier))
		{
			final I_C_Invoice_Candidate invoiceCandidate = invoiceCandTable.get(invoiceCandidateIdentifier);
			queryBuilder.addEqualsFilter(I_C_InvoiceCandidate_InOutLine.COLUMNNAME_C_Invoice_Candidate_ID, invoiceCandidate.getC_Invoice_Candidate_ID());
		}

		final String shipmentLineIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_M_InOutLine_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(shipmentLineIdentifier))
		{
			final I_M_InOutLine shipmentLine = shipmentLineTable.get(shipmentLineIdentifier);
			queryBuilder.addEqualsFilter(COLUMNNAME_M_InOutLine_ID, shipmentLine.getM_InOutLine_ID());
		}

		final BigDecimal qtyDelivered = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_InvoiceCandidate_InOutLine.COLUMNNAME_QtyDelivered);
		if (qtyDelivered != null)
		{
			queryBuilder.addEqualsFilter(I_C_InvoiceCandidate_InOutLine.COLUMNNAME_QtyDelivered, qtyDelivered);
		}

		final I_C_InvoiceCandidate_InOutLine invoiceCandidateInOutLine = queryBuilder.create()
				.firstOnly(I_C_InvoiceCandidate_InOutLine.class);

		if (invoiceCandidateInOutLine == null)
		{
			return ItemProvider.ProviderResult.resultWasNotFound("No C_InvoiceCandidate_InOutLine found!");
		}

		final String invoiceCandidateInOutLineIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_InvoiceCandidate_InOutLine.COLUMNNAME_C_InvoiceCandidate_InOutLine_ID + "." + TABLECOLUMN_IDENTIFIER);
		invoiceCandInOuLineTable.putOrReplace(invoiceCandidateInOutLineIdentifier, invoiceCandidateInOutLine);

		return ItemProvider.ProviderResult.resultWasFound(invoiceCandidateInOutLine);
	}

	private void logCurrentContext(@NonNull final Integer invoiceCandidateId)
	{
		final String currentContext = queryBL.createQueryBuilder(I_C_InvoiceCandidate_InOutLine.class)
				.addEqualsFilter(I_C_InvoiceCandidate_InOutLine.COLUMNNAME_C_Invoice_Candidate_ID, invoiceCandidateId)
				.create()
				.stream()
				.map(inoutLineAllocation -> "[C_InvoiceCandidate_InOutLine_ID - " + inoutLineAllocation.getC_InvoiceCandidate_InOutLine_ID()
						+ ": C_Invoice_Candidate_ID: " + inoutLineAllocation.getC_Invoice_Candidate_ID()
						+ " , M_InOut_Line_ID: " + inoutLineAllocation.getM_InOutLine_ID()
						+ " , QtyDelivered: " + inoutLineAllocation.getQtyDelivered() + "]")
				.collect(Collectors.joining(","));

		logger.error("*** Looking for C_InvoiceCandidate_InOutLine instance. See current context: \n" + currentContext);
	}
}
