package de.metas.ui.web.order.pricingconditions.view;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.order.OrderLineId;
import de.metas.ui.web.document.filter.DocumentFiltersList;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.view.AbstractCustomView.IEditableRowsData;
import de.metas.ui.web.view.IEditableView.RowEditingContext;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
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

@ToString(doNotUseGetters = true)
class PricingConditionsRowData implements IEditableRowsData<PricingConditionsRow>
{
	@Getter
	private final OrderLineId orderLineId;
	private final DocumentFiltersList filters;
	private final PricingConditionsRowData allRowsData;

	private final ImmutableList<DocumentId> rowIds; // used to preserve the order
	private final ConcurrentMap<DocumentId, PricingConditionsRow> rowsById;
	private final DocumentId editableRowId;

	@Builder
	private PricingConditionsRowData(
			final OrderLineId orderLineId,
			@Nullable final PricingConditionsRow editableRow,
			@NonNull final List<PricingConditionsRow> rows)
	{
		// Check.assumeGreaterThanZero(salesOrderLineId, "salesOrderLineId"); // OK to not be set

		this.orderLineId = orderLineId;
		this.allRowsData = null;
		this.filters = DocumentFiltersList.EMPTY;

		rowIds = stream(editableRow, rows)
				.map(PricingConditionsRow::getId)
				.collect(ImmutableList.toImmutableList());
		rowsById = stream(editableRow, rows)
				.collect(Collectors.toConcurrentMap(PricingConditionsRow::getId, Function.identity()));

		this.editableRowId = editableRow != null ? editableRow.getId() : null;
	}

	private PricingConditionsRowData(final PricingConditionsRowData from, final DocumentFiltersList filters)
	{
		this.allRowsData = from.getAllRowsData();
		this.filters = filters;
		this.orderLineId = allRowsData.getOrderLineId();

		rowsById = allRowsData.rowsById;
		final ImmutableSet<DocumentId> rowIdsNotOrdered = allRowsData.rowsById.values()
				.stream()
				.filter(PricingConditionsViewFilters.isEditableRowOrMatching(filters))
				.map(PricingConditionsRow::getId)
				.collect(ImmutableSet.toImmutableSet());
		rowIds = allRowsData.rowIds.stream()
				.filter(rowIdsNotOrdered::contains)
				.collect(ImmutableList.toImmutableList());

		this.editableRowId = from.editableRowId;
	}

	private PricingConditionsRowData getAllRowsData()
	{
		return allRowsData != null ? allRowsData : this;
	}

	private static final Stream<PricingConditionsRow> stream(final PricingConditionsRow editableRow, final List<PricingConditionsRow> rows)
	{
		return editableRow != null
				? Stream.concat(Stream.of(editableRow), rows.stream())
				: rows.stream();
	}

	@Override
	public int size()
	{
		return rowIds.size();
	}

	@Override
	public Map<DocumentId, PricingConditionsRow> getDocumentId2TopLevelRows()
	{
		return ImmutableMap.copyOf(rowsById);
	}

	@Override
	public Map<DocumentId, PricingConditionsRow> getDocumentId2AllRows()
	{
		return getDocumentId2TopLevelRows();
	}

	@Override
	public Collection<PricingConditionsRow> getTopLevelRows()
	{
		return rowIds.stream()
				.map(rowsById::get)
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public Collection<PricingConditionsRow> getAllRows()
	{
		return getTopLevelRows();
	}

	@Override
	public DocumentIdsSelection getDocumentIdsToInvalidate(final TableRecordReferenceSet recordRefs)
	{
		return DocumentIdsSelection.EMPTY;
	}

	@Override
	public void invalidateAll()
	{
	}

	private void changeRow(final DocumentId rowId, final UnaryOperator<PricingConditionsRow> mapper)
	{
		if (!rowIds.contains(rowId))
		{
			throw new EntityNotFoundException(rowId.toJson());
		}

		rowsById.compute(rowId, (key, oldRow) -> {
			if (oldRow == null)
			{
				throw new EntityNotFoundException(rowId.toJson());
			}

			return mapper.apply(oldRow);
		});
	}

	@Override
	public void patchRow(final RowEditingContext ctx, final List<JSONDocumentChangedEvent> fieldChangeRequests)
	{
		final PricingConditionsRowChangeRequest request = PricingConditionsRowActions.toChangeRequest(fieldChangeRequests);
		changeRow(ctx.getRowId(), row -> PricingConditionsRowReducers.copyAndChange(request, row));
	}

	public void patchEditableRow(final PricingConditionsRowChangeRequest request)
	{
		changeRow(getEditableRowId(), editableRow -> PricingConditionsRowReducers.copyAndChange(request, editableRow));
	}

	public boolean hasEditableRow()
	{
		return editableRowId != null;
	}

	public DocumentId getEditableRowId()
	{
		if (editableRowId == null)
		{
			throw new AdempiereException("No editable row found");
		}
		return editableRowId;
	}

	public PricingConditionsRow getEditableRow()
	{
		return getById(getEditableRowId());
	}

	public DocumentFiltersList getFilters()
	{
		return filters;
	}

	public PricingConditionsRowData filter(@NonNull final DocumentFiltersList filters)
	{
		if (Objects.equals(this.filters, filters))
		{
			return this;
		}

		if (filters.isEmpty())
		{
			return getAllRowsData();
		}

		return new PricingConditionsRowData(this, filters);
	}
}
