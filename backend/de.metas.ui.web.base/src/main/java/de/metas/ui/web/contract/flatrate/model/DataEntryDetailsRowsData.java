/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.ui.web.contract.flatrate.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.contracts.flatrate.dataEntry.FlatrateDataEntry;
import de.metas.contracts.flatrate.dataEntry.FlatrateDataEntryDetail;
import de.metas.contracts.flatrate.dataEntry.FlatrateDataEntryDetailId;
import de.metas.contracts.flatrate.dataEntry.FlatrateDataEntryId;
import de.metas.contracts.flatrate.dataEntry.FlatrateDataEntryRepo;
import de.metas.contracts.model.I_C_Flatrate_DataEntry_Detail;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.ui.web.view.IEditableView;
import de.metas.ui.web.view.template.IEditableRowsData;
import de.metas.ui.web.view.template.IRowsData;
import de.metas.ui.web.view.template.ImmutableRowsIndex;
import de.metas.ui.web.view.template.SynchronizedRowsIndexHolder;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class DataEntryDetailsRowsData implements IEditableRowsData<DataEntryDetailsRow>
{
	private final FlatrateDataEntryId flatrateDataEntryId;
	private final SynchronizedRowsIndexHolder<DataEntryDetailsRow> rowsHolder;
	private final FlatrateDataEntryRepo flatrateDataEntryRepo;
	private final DataEntryDetailsRowsLoader loader;

	public DataEntryDetailsRowsData(
			@NonNull final FlatrateDataEntryId flatrateDataEntryId,
			@NonNull final ImmutableList<DataEntryDetailsRow> rows,
			@NonNull final FlatrateDataEntryRepo flatrateDataEntryRepo,
			@NonNull final DataEntryDetailsRowsLoader loader)
	{
		this.flatrateDataEntryId = flatrateDataEntryId;
		this.rowsHolder = SynchronizedRowsIndexHolder.of(rows);
		this.flatrateDataEntryRepo = flatrateDataEntryRepo;
		this.loader = loader;
	}

	public static DataEntryDetailsRowsData cast(@NonNull final IRowsData<DataEntryDetailsRow> rowsData)
	{
		return (DataEntryDetailsRowsData)rowsData;
	}

	@Override
	public void patchRow(final IEditableView.RowEditingContext ctx, final List<JSONDocumentChangedEvent> fieldChangeRequests)
	{
		final UnaryOperator<DataEntryDetailsRow> rowMapper = row -> {

			final DataEntryDetailsRow.DataEntryDetailsRowBuilder builder = row.toBuilder();

			for (final JSONDocumentChangedEvent fieldChangeRequest : fieldChangeRequests)
			{
				if (fieldChangeRequest.getPath().equals(DataEntryDetailsRow.FIELD_Qty))
				{
					builder.qty(fieldChangeRequest.getValueAsBigDecimal());
				}
				// note that qty is currently the only editable field
			}

			return builder.build();
		};

		final DocumentId rowIdToChange = ctx.getRowId();
		final UnaryOperator<ImmutableRowsIndex<DataEntryDetailsRow>> remappingFunction = rows -> rows.changingRow(rowIdToChange, rowMapper);

		rowsHolder.compute(remappingFunction);
		
		saveAll();
	}

	@Override
	public Map<DocumentId, DataEntryDetailsRow> getDocumentId2TopLevelRows()
	{
		return rowsHolder.getDocumentId2TopLevelRows();
	}

	@Override
	public DocumentIdsSelection getDocumentIdsToInvalidate(@NonNull final TableRecordReferenceSet recordRefs)
	{
		return recordRefs.streamIds(
						I_C_Flatrate_DataEntry_Detail.Table_Name,
						repoId -> FlatrateDataEntryDetailId.ofRepoId(flatrateDataEntryId, repoId))
				.map(DataEntryDetailsRowUtil::createDocumentId)
				.filter(rowsHolder.isRelevantForRefreshingByDocumentId())
				.collect(DocumentIdsSelection.toDocumentIdsSelection());
	}

	@Override
	public void invalidateAll()
	{
		invalidate(DocumentIdsSelection.ALL);
	}

	@Override
	public void invalidate(@NonNull final DocumentIdsSelection rowIds)
	{
		final Function<DocumentId, FlatrateDataEntryDetailId> idMapper = documentId -> FlatrateDataEntryDetailId.ofRepoId(flatrateDataEntryId, documentId.toInt());
		
		final ImmutableSet<FlatrateDataEntryDetailId> dataEntryIds = rowsHolder.getRecordIdsToRefresh(rowIds, idMapper);
		
		final Predicate<FlatrateDataEntryDetail> loadFilter = dataEntry -> dataEntryIds.contains(dataEntry.getId());
		
		final List<DataEntryDetailsRow> newRows = loader.loadMatchingRows(loadFilter)
				.stream()
				// .map(newRow -> newRow) // we are just replacing the stale rows
				.collect(ImmutableList.toImmutableList());

		rowsHolder.compute(rows -> rows.replacingRows(rowIds, newRows));
	}

	private void saveAll()
	{
		final Map<DocumentId, DataEntryDetailsRow> documentId2TopLevelRows = getDocumentId2TopLevelRows();

		final FlatrateDataEntry entry = flatrateDataEntryRepo.getById(flatrateDataEntryId);
		for (final FlatrateDataEntryDetail detail : entry.getDetails())
		{
			final DocumentId documentId = DataEntryDetailsRowUtil.createDocumentId(detail);
			final DataEntryDetailsRow row = documentId2TopLevelRows.get(documentId);
			if(row.getQty() != null)
			{
				final Quantity quantity = Quantitys.of(row.getQty(), entry.getUomId());
				detail.setQuantity(quantity);
			}
			else
			{
				detail.setQuantity(null);
			}
		}

		flatrateDataEntryRepo.save(entry);
	}
}
