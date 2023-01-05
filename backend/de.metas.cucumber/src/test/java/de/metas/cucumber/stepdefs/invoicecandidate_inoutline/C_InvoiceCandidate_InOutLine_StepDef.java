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
import de.metas.cucumber.stepdefs.invoicecandidate.C_Invoice_Candidate_StepDefData;
import de.metas.cucumber.stepdefs.shipment.M_InOutLine_StepDefData;
import de.metas.invoicecandidate.model.I_C_InvoiceCandidate_InOutLine;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.compiere.model.I_M_InOutLine;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;

public class C_InvoiceCandidate_InOutLine_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final C_InvoiceCandidate_InOutLine_StepDefData invoiceCandInOuLineTable;
	private final C_Invoice_Candidate_StepDefData invoiceCandTable;
	private final M_InOutLine_StepDefData shipmentLineTable;

	public C_InvoiceCandidate_InOutLine_StepDef(
			@NonNull final C_InvoiceCandidate_InOutLine_StepDefData invoiceCandInOuLineTable,
			@NonNull final C_Invoice_Candidate_StepDefData invoiceCandTable,
			@NonNull final M_InOutLine_StepDefData shipmentLineTable)
	{
		this.invoiceCandInOuLineTable = invoiceCandInOuLineTable;
		this.invoiceCandTable = invoiceCandTable;
		this.shipmentLineTable = shipmentLineTable;
	}

	@And("validate created C_InvoiceCandidate_InOutLine")
	public void validate_C_InvoiceCandidate_InOutLine(@NonNull final DataTable dataTable) throws InterruptedException
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final I_C_InvoiceCandidate_InOutLine invoiceCandidateInOutLineRecord = StepDefUtil.tryAndWaitForItem(120, 500, () -> getInvoiceCandidateInOutLine(row), this::logCurrentContext);

			final String invoiceCandidateInOutLineIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_InvoiceCandidate_InOutLine.COLUMNNAME_C_InvoiceCandidate_InOutLine_ID + "." + TABLECOLUMN_IDENTIFIER);
			invoiceCandInOuLineTable.putOrReplace(invoiceCandidateInOutLineIdentifier, invoiceCandidateInOutLineRecord);
		}
	}

	@NonNull
	private ItemProvider.ProviderResult<I_C_InvoiceCandidate_InOutLine> getInvoiceCandidateInOutLine(@NonNull final Map<String, String> row)
	{
		final List<String> queryCollector = new ArrayList<>();

		final IQueryBuilder<I_C_InvoiceCandidate_InOutLine> queryBuilder = queryBL.createQueryBuilder(I_C_InvoiceCandidate_InOutLine.class).addOnlyActiveRecordsFilter();

		final String invoiceCandidateIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_InvoiceCandidate_InOutLine.COLUMNNAME_C_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(invoiceCandidateIdentifier))
		{
			final I_C_Invoice_Candidate invoiceCandidate = invoiceCandTable.get(invoiceCandidateIdentifier);
			queryBuilder.addEqualsFilter(I_C_InvoiceCandidate_InOutLine.COLUMNNAME_C_Invoice_Candidate_ID, invoiceCandidate.getC_Invoice_Candidate_ID());
			queryCollector.add(MessageFormat.format("C_Invoice_Candidate_ID={0}", invoiceCandidate.getC_Invoice_Candidate_ID()));
		}

		final String shipmentLineIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_InvoiceCandidate_InOutLine.COLUMNNAME_M_InOutLine_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(shipmentLineIdentifier))
		{
			final I_M_InOutLine shipmentLine = shipmentLineTable.get(shipmentLineIdentifier);
			queryBuilder.addEqualsFilter(I_C_InvoiceCandidate_InOutLine.COLUMNNAME_M_InOutLine_ID, shipmentLine.getM_InOutLine_ID());
			queryCollector.add(MessageFormat.format("M_InOutLine_ID={0}", shipmentLine.getM_InOutLine_ID()));
		}

		final BigDecimal qtyDelivered = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_InvoiceCandidate_InOutLine.COLUMNNAME_QtyDelivered);
		if (qtyDelivered != null)
		{
			queryBuilder.addEqualsFilter(I_C_InvoiceCandidate_InOutLine.COLUMNNAME_QtyDelivered, qtyDelivered);
			queryCollector.add(MessageFormat.format("C_InvoiceCandidate_InOutLine.QtyDelivered={0}", qtyDelivered));
		}

		return queryBuilder.create()
				.firstOnlyOptional(I_C_InvoiceCandidate_InOutLine.class)
				.map(ItemProvider.ProviderResult::resultWasFound)
				.orElseGet(() -> ItemProvider.ProviderResult.resultWasNotFound("Couldn't find any C_InvoiceCandidate_InOutLine querying by"
																					   + String.join(" && ", queryCollector)));
	}

	@NonNull
	private String logCurrentContext()
	{
		final StringBuilder message = new StringBuilder("C_InvoiceCandidate_InOutLine records:").append("\n");

		queryBL.createQueryBuilder(I_C_InvoiceCandidate_InOutLine.class)
				.create()
				.stream(I_C_InvoiceCandidate_InOutLine.class)
				.forEach(invoiceCandidateInOutLineRecord -> message
						.append(I_C_InvoiceCandidate_InOutLine.COLUMNNAME_C_InvoiceCandidate_InOutLine_ID).append(" : ").append(invoiceCandidateInOutLineRecord.getC_InvoiceCandidate_InOutLine_ID()).append(" ; ")
						.append(I_C_InvoiceCandidate_InOutLine.COLUMNNAME_C_Invoice_Candidate_ID).append(" : ").append(invoiceCandidateInOutLineRecord.getC_Invoice_Candidate_ID()).append(" ; ")
						.append(I_C_InvoiceCandidate_InOutLine.COLUMNNAME_M_InOutLine_ID).append(" : ").append(invoiceCandidateInOutLineRecord.getM_InOutLine_ID()).append(" ; ")
						.append(I_C_InvoiceCandidate_InOutLine.COLUMNNAME_QtyDelivered).append(" : ").append(invoiceCandidateInOutLineRecord.getQtyDelivered()).append(" ; ")
						.append("\n"));

		return "see current context: \n" + message;
	}
}
