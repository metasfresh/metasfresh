package de.metas.ui.web.order.sales.purchasePlanning.process;

import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ImmutablePair;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.Multimaps;

import de.metas.process.ProcessPreconditionsResolution;
import de.metas.purchasecandidate.availability.AvailabilityResult.Type;
import de.metas.quantity.Quantity;
import de.metas.ui.web.order.sales.purchasePlanning.view.PurchaseRow;
import de.metas.ui.web.order.sales.purchasePlanning.view.PurchaseRowChangeRequest;
import de.metas.ui.web.order.sales.purchasePlanning.view.PurchaseRowChangeRequest.PurchaseRowChangeRequestBuilder;
import de.metas.util.Check;
import de.metas.ui.web.order.sales.purchasePlanning.view.PurchaseRowId;
import de.metas.ui.web.order.sales.purchasePlanning.view.PurchaseView;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class WEBUI_SalesOrder_Apply_Availability_Row extends PurchaseViewBasedProcess
{
	@Override
	public final ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final Multimap<PurchaseRow, PurchaseRow> lineRows2availabilityRows = //
				extractLineRow2availabilityRows();
		if (lineRows2availabilityRows.isEmpty())
		{
			return ProcessPreconditionsResolution.reject();
		}

		if (hasMultipleAvailabilityRowsPerLineRow(lineRows2availabilityRows))
		{
			return ProcessPreconditionsResolution.reject();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final Multimap<PurchaseRow, PurchaseRow> lineRows2availabilityRows = extractLineRow2availabilityRows();

		Check.errorIf(hasMultipleAvailabilityRowsPerLineRow(lineRows2availabilityRows),
				"The selected rows contain > 1 availability row for one line row; lineRows2availabilityRows={}",
				lineRows2availabilityRows);

		lineRows2availabilityRows.forEach(this::updateLineAndGroupRow);

		return MSG_OK;
	}

	private static boolean hasMultipleAvailabilityRowsPerLineRow(
			@NonNull final Multimap<PurchaseRow, PurchaseRow> lineRows2availabilityRows)
	{
		return lineRows2availabilityRows
				.asMap()
				.entrySet()
				.stream()
				.anyMatch(lineRow2availabilityRows -> lineRow2availabilityRows.getValue().size() > 1);
	}

	public void updateLineAndGroupRow(@NonNull final PurchaseRow lineRow, @NonNull final PurchaseRow availabilityRow)
	{
		final PurchaseRowId lineRowId = lineRow.getRowId();
		final PurchaseRowChangeRequest lineChangeRequest = createPurchaseRowChangeRequest(availabilityRow);

		patchViewRow(lineRowId, lineChangeRequest);
	}

	private Multimap<PurchaseRow, PurchaseRow> extractLineRow2availabilityRows()
	{
		final PurchaseView view = getView();

		final ListMultimap<PurchaseRow, PurchaseRow> lineRow2AvailabilityRows = getSelectedRowIds().stream()

				.map(PurchaseRowId::fromDocumentId) // map to PurchaseRowIds
				.filter(PurchaseRowId::isAvailabilityRowId)
				.filter(availabilityRowId -> availabilityRowId.getAvailabilityType().equals(Type.AVAILABLE))

				.map(availabilityRowId -> ImmutablePair.of( // map to pair (availabilityRowId, availabilityRow)
						availabilityRowId,
						view.getById(availabilityRowId.toDocumentId())))
				.filter(availabilityRowId2row -> isPositive(availabilityRowId2row.getRight().getQtyToPurchase()))

				.map(availabilityRowId2row -> ImmutablePair.of( // map to pair (lineRow, availabilityRow)
						view.getById(availabilityRowId2row.getLeft().toLineRowId().toDocumentId()),
						availabilityRowId2row.getRight()))
				.filter(lineRow2availabilityRow -> !lineRow2availabilityRow.getLeft().isProcessed())

				.collect(Multimaps.toMultimap(
						IPair::getLeft,
						IPair::getRight,
						MultimapBuilder.hashKeys().arrayListValues()::build));

		return ImmutableMultimap.copyOf(lineRow2AvailabilityRows);
	}

	private static final boolean isPositive(final Quantity qty)
	{
		return qty != null && qty.signum() > 0;
	}

	private static PurchaseRowChangeRequest createPurchaseRowChangeRequest(final PurchaseRow availabilityRow)
	{
		final PurchaseRowChangeRequestBuilder requestBuilder = PurchaseRowChangeRequest.builder();

		if (availabilityRow.getDatePromised() != null)
		{
			requestBuilder.purchaseDatePromised(availabilityRow.getDatePromised());
		}
		requestBuilder.qtyToPurchase(availabilityRow.getQtyToPurchase());

		return requestBuilder.build();
	}
}
