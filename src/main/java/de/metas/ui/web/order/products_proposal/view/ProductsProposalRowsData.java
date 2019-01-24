package de.metas.ui.web.order.products_proposal.view;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import org.adempiere.util.lang.impl.TableRecordReferenceSet;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.view.AbstractCustomView.IEditableRowsData;
import de.metas.ui.web.view.IEditableView.RowEditingContext;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class ProductsProposalRowsData implements IEditableRowsData<ProductsProposalRow>
{
	private final ImmutableList<DocumentId> rowIds; // used to preserve the order
	private final ConcurrentMap<DocumentId, ProductsProposalRow> rowsById;

	@Builder
	private ProductsProposalRowsData(@NonNull final List<ProductsProposalRow> rows)
	{
		rowIds = rows.stream()
				.map(ProductsProposalRow::getId)
				.collect(ImmutableList.toImmutableList());

		rowsById = rows.stream()
				.collect(Collectors.toConcurrentMap(ProductsProposalRow::getId, Function.identity()));
	}

	@Override
	public int size()
	{
		return rowIds.size();
	}

	@Override
	public ImmutableMap<DocumentId, ProductsProposalRow> getDocumentId2TopLevelRows()
	{
		return ImmutableMap.copyOf(rowsById);
	}

	@Override
	public Map<DocumentId, ProductsProposalRow> getDocumentId2AllRows()
	{
		return getDocumentId2TopLevelRows();
	}

	@Override
	public ImmutableList<ProductsProposalRow> getTopLevelRows()
	{
		return rowIds.stream()
				.map(rowsById::get)
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public ImmutableList<ProductsProposalRow> getAllRows()
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

	@Override
	public void patchRow(final RowEditingContext ctx, final List<JSONDocumentChangedEvent> fieldChangeRequests)
	{
		final ProductsProposalRowChangeRequest request = ProductsProposalRowActions.toChangeRequest(fieldChangeRequests);
		changeRow(ctx.getRowId(), row -> ProductsProposalRowReducers.copyAndChange(request, row));
	}

	private void changeRow(@NonNull final DocumentId rowId, @NonNull final UnaryOperator<ProductsProposalRow> mapper)
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

}
