/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.cucumber.stepdefs.matchinv;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.ItemProvider;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.invoice.C_InvoiceLine_StepDefData;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.cucumber.stepdefs.shipment.M_InOutLine_StepDefData;
import de.metas.cucumber.stepdefs.shipment.M_InOut_Line_StepDef;
import de.metas.cucumber.stepdefs.shipment.M_InOut_StepDef;
import de.metas.cucumber.stepdefs.shipment.M_InOut_StepDefData;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_MatchInv;
import org.compiere.model.I_M_Product;

import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;

public class M_MatchInv_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final M_Product_StepDefData productTable;
	private final C_Invoice_StepDefData invoiceTable;
	private final C_InvoiceLine_StepDefData invoiceLineTable;
	private final M_MatchInv_StepDefData matchInvTable;
	private final M_InOut_StepDefData inoutTable;
	private final M_InOutLine_StepDefData inoutLineTable;

	public M_MatchInv_StepDef(
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final C_Invoice_StepDefData invoiceTable,
			@NonNull final C_InvoiceLine_StepDefData invoiceLineTable,
			@NonNull final M_MatchInv_StepDefData matchInvTable,
			@NonNull final M_InOut_StepDefData inoutTable,
			@NonNull final M_InOutLine_StepDefData inoutLineTable)
	{
		this.productTable = productTable;
		this.invoiceTable = invoiceTable;
		this.invoiceLineTable = invoiceLineTable;
		this.matchInvTable = matchInvTable;
		this.inoutTable = inoutTable;
		this.inoutLineTable = inoutLineTable;
	}

	@And("^after not more than (.*)s, M_MatchInv are found:$")
	public void wait_until_there_are_matchInvoices(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		for (final Map<String, String> tableRow : dataTable.asMaps())
		{
			final I_M_MatchInv matchInvRecord = StepDefUtil.tryAndWaitForItem(timeoutSec, 500, () -> loadMatchInv(tableRow));

			final String identifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_MatchInv.COLUMNNAME_M_MatchInv_ID + "." + TABLECOLUMN_IDENTIFIER);
			matchInvTable.putOrReplace(identifier, matchInvRecord);
		}
	}

	@NonNull
	private ItemProvider.ProviderResult<I_M_MatchInv> loadMatchInv(@NonNull final Map<String, String> row)
	{
		final I_M_MatchInv matchInvRecord = buildQuery(row)
				.firstOnlyOrNull(I_M_MatchInv.class);

		if (matchInvRecord == null)
		{
			return ItemProvider.ProviderResult.resultWasNotFound("No I_M_MatchInv found for row=" + row);
		}

		return ItemProvider.ProviderResult.resultWasFound(matchInvRecord);
	}

	@NonNull
	private IQuery<I_M_MatchInv> buildQuery(@NonNull final Map<String, String> row)
	{
		final String productIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_MatchInv.COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_M_Product productRecord = productTable.get(productIdentifier);

		final String invoiceIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_MatchInv.COLUMNNAME_C_Invoice_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_Invoice invoiceRecord = invoiceTable.get(invoiceIdentifier);

		final String invoiceLineIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_MatchInv.COLUMNNAME_C_InvoiceLine_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_InvoiceLine invoiceLineRecord = invoiceLineTable.get(invoiceLineIdentifier);

		final String inoutIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_M_MatchInv.COLUMNNAME_M_InOut_ID + "." + TABLECOLUMN_IDENTIFIER);
		final String inoutLineIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_M_MatchInv.COLUMNNAME_M_InOutLine_ID + "." + TABLECOLUMN_IDENTIFIER);

		final IQueryBuilder<I_M_MatchInv> queryBuilder = queryBL.createQueryBuilder(I_M_MatchInv.class)
				.addEqualsFilter(I_M_MatchInv.COLUMNNAME_M_Product_ID, productRecord.getM_Product_ID())
				.addEqualsFilter(I_M_MatchInv.COLUMNNAME_C_Invoice_ID, invoiceRecord.getC_Invoice_ID())
				.addEqualsFilter(I_M_MatchInv.COLUMNNAME_C_InvoiceLine_ID, invoiceLineRecord.getC_InvoiceLine_ID());

		if (Check.isNotBlank(inoutIdentifier))
		{
			final I_M_InOut inout = inoutTable.get(inoutIdentifier);
			queryBuilder.addEqualsFilter(I_M_MatchInv.COLUMNNAME_M_InOut_ID, inout.getM_InOut_ID());
		}
		if (Check.isNotBlank(inoutLineIdentifier))
		{
			final I_M_InOutLine inoutLine = inoutLineTable.get(inoutLineIdentifier);
			queryBuilder.addEqualsFilter(I_M_MatchInv.COLUMNNAME_M_InOutLine_ID, inoutLine.getM_InOutLine_ID());
		}

		return queryBuilder.create();
	}
}
