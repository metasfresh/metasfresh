package de.metas.ui.web.pickingV2.productsToPick.rows;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.UnaryOperator;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.model.I_M_Picking_Candidate;
import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.handlingunits.picking.PickingCandidateId;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.ui.web.view.AbstractCustomView.IEditableRowsData;
import de.metas.ui.web.view.IEditableView.RowEditingContext;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import de.metas.ui.web.window.model.DocumentQueryOrderBys;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;

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

@ToString(of = { "_rowsById", "orderBys" })
public class ProductsToPickRowsData implements IEditableRowsData<ProductsToPickRow>
{
	private final PickingCandidateService pickingCandidateService;

	@Getter
	private final ImmutableList<DocumentQueryOrderBy> orderBys;
	private final ImmutableList<DocumentId> rowIdsOrdered;
	private final ConcurrentHashMap<DocumentId, ProductsToPickRow> _rowsById;
	private volatile boolean rowIdsInvalid;

	@Builder
	private ProductsToPickRowsData(
			@NonNull final PickingCandidateService pickingCandidateService,
			@NonNull final List<ProductsToPickRow> rows,
			@NonNull @Singular final ImmutableList<DocumentQueryOrderBy> orderBys)
	{
		this.pickingCandidateService = pickingCandidateService;

		this.orderBys = orderBys;
		rowIdsOrdered = rows.stream()
				.sorted(createComparator(orderBys))
				.map(ProductsToPickRow::getId)
				.distinct()
				.collect(ImmutableList.toImmutableList());

		_rowsById = rows.stream()
				.map(row -> GuavaCollectors.entry(row.getId(), row))
				.collect(GuavaCollectors.toMap(ConcurrentHashMap::new));
		rowIdsInvalid = false;
	}

	private static Comparator<ProductsToPickRow> createComparator(final List<DocumentQueryOrderBy> orderBys)
	{
		return DocumentQueryOrderBys.<ProductsToPickRow> asComparator(orderBys, JSONOptions.newInstance())
				.thenComparing(ProductsToPickRow::getShipmentScheduleId);
	}

	private synchronized Map<DocumentId, ProductsToPickRow> getRowsById()
	{
		if (rowIdsInvalid)
		{
			final Map<PickingCandidateId, DocumentId> rowIdsByPickingCandidateId = _rowsById.values()
					.stream()
					.filter(row -> row.getPickingCandidateId() != null)
					.collect(ImmutableMap.toImmutableMap(ProductsToPickRow::getPickingCandidateId, ProductsToPickRow::getId));

			final List<PickingCandidate> pickingCandidates = pickingCandidateService.getByIds(rowIdsByPickingCandidateId.keySet());

			pickingCandidates
					.forEach(pickingCandidate -> _rowsById.compute(
							rowIdsByPickingCandidateId.get(pickingCandidate.getId()),
							(rowId, row) -> row.withUpdatesFromPickingCandidate(pickingCandidate)));

			rowIdsInvalid = false;
		}
		return _rowsById;
	}

	public synchronized void changeRow(@NonNull final DocumentId rowId, @NonNull final UnaryOperator<ProductsToPickRow> mapper)
	{
		final Map<DocumentId, ProductsToPickRow> rowsById = getRowsById();
		rowsById.compute(rowId, (k, row) -> {
			if (row == null)
			{
				throw new AdempiereException("No row found for id: " + k);
			}
			else
			{
				final ProductsToPickRow newRow = mapper.apply(row);
				Check.assumeNotNull(newRow, "newRow shall not be null");
				return newRow;
			}
		});
	}

	@Override
	public Map<DocumentId, ProductsToPickRow> getDocumentId2TopLevelRows()
	{
		return getRowsById();
	}

	@Override
	public Collection<ProductsToPickRow> getTopLevelRows()
	{
		final Map<DocumentId, ProductsToPickRow> rowsById = getRowsById();
		return rowIdsOrdered.stream()
				.map(rowsById::get)
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public void patchRow(final RowEditingContext ctx, final List<JSONDocumentChangedEvent> fieldChangeRequests)
	{
		changeRow(ctx.getRowId(), row -> applyFieldChangeRequests(row, fieldChangeRequests));
	}

	private ProductsToPickRow applyFieldChangeRequests(@NonNull final ProductsToPickRow row, final List<JSONDocumentChangedEvent> fieldChangeRequests)
	{
		Check.assumeNotEmpty(fieldChangeRequests, "fieldChangeRequests is not empty");
		fieldChangeRequests.forEach(JSONDocumentChangedEvent::assertReplaceOperation);

		ProductsToPickRow changedRow = row;
		for (final JSONDocumentChangedEvent fieldChangeRequest : fieldChangeRequests)
		{
			final String fieldName = fieldChangeRequest.getPath();
			if (ProductsToPickRow.FIELD_QtyOverride.equals(fieldName))
			{
				final BigDecimal qtyOverride = fieldChangeRequest.getValueAsBigDecimal();
				changedRow = changedRow.withQtyOverride(qtyOverride);
			}
			else if (ProductsToPickRow.FIELD_QtyReview.equals(fieldName))
			{
				final BigDecimal qtyReviewed = fieldChangeRequest.getValueAsBigDecimal();
				final PickingCandidate pickingCandidate = pickingCandidateService.setQtyReviewed(row.getPickingCandidateId(), qtyReviewed);
				changedRow = changedRow.withUpdatesFromPickingCandidate(pickingCandidate);
			}
			else
			{
				throw new AdempiereException("Field " + fieldName + " is not editable");
			}
		}

		return changedRow;
	}

	@Override
	public DocumentIdsSelection getDocumentIdsToInvalidate(final TableRecordReferenceSet recordRefs)
	{
		final Set<PickingCandidateId> pickingCandidateIds = recordRefs
				.streamIds(I_M_Picking_Candidate.Table_Name, PickingCandidateId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
		if (pickingCandidateIds.isEmpty())
		{
			return DocumentIdsSelection.EMPTY;
		}

		return getRowsById()
				.values()
				.stream()
				.filter(row -> pickingCandidateIds.contains(row.getPickingCandidateId()))
				.map(ProductsToPickRow::getId)
				.collect(DocumentIdsSelection.toDocumentIdsSelection());
	}

	@Override
	public void invalidateAll()
	{
		rowIdsInvalid = true;
	}
}
