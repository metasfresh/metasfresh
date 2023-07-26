/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.ui.web.order.attachmenteditor;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import de.metas.attachments.AttachmentLinksRequest;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.view.IEditableView;
import de.metas.ui.web.view.template.IEditableRowsData;
import de.metas.ui.web.view.template.IRowsData;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.UnaryOperator;

import static de.metas.ui.web.order.attachmenteditor.OrderAttachmentRow.FIELD_IsAttachToPurchaseOrder;

final class OrderAttachmentRows implements IEditableRowsData<OrderAttachmentRow>
{
	public static OrderAttachmentRows cast(final IRowsData<OrderAttachmentRow> rowsData)
	{
		return (OrderAttachmentRows)rowsData;
	}

	private final ImmutableList<DocumentId> rowIds;
	private final ConcurrentHashMap<DocumentId, OrderAttachmentRow> rowsById;

	@Builder
	private OrderAttachmentRows(
			@NonNull final List<OrderAttachmentRow> rows)
	{
		rowIds = rows.stream()
				.map(OrderAttachmentRow::getId)
				.collect(ImmutableList.toImmutableList());

		rowsById = new ConcurrentHashMap<>(Maps.uniqueIndex(rows, OrderAttachmentRow::getId));
	}

	@Override
	public void patchRow(final IEditableView.RowEditingContext ctx, final List<JSONDocumentChangedEvent> fieldChangeRequests)
	{
		Check.assumeNotEmpty(fieldChangeRequests, "fieldChangeRequests is not empty");

		// we can have only one request, as only one field/column is editable
		final JSONDocumentChangedEvent fieldChangeRequest = CollectionUtils.singleElement(fieldChangeRequests);

		Check.assume(fieldChangeRequest.getPath().equals(FIELD_IsAttachToPurchaseOrder), "path should be: " + FIELD_IsAttachToPurchaseOrder);

		final Boolean userChange = fieldChangeRequest.getValueAsBoolean(false);

		changeRow(ctx.getRowId(), row -> row.withChanges(userChange));
	}

	@Override
	public Map<DocumentId, OrderAttachmentRow> getDocumentId2TopLevelRows()
	{
		return rowIds.stream()
				.map(rowsById::get)
				.collect(GuavaCollectors.toImmutableMapByKey(OrderAttachmentRow::getId));
	}

	@Override
	public DocumentIdsSelection getDocumentIdsToInvalidate(final TableRecordReferenceSet recordRefs)
	{
		return DocumentIdsSelection.EMPTY;
	}

	@Override
	public void invalidateAll() {}

	@NonNull
	Optional<ImmutableList<AttachmentLinksRequest>> createAttachmentLinksRequestList()
	{
		final ImmutableList<AttachmentLinksRequest> userChanges = rowsById.values()
				.stream()
				.map(OrderAttachmentRow::toAttachmentLinksRequest)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(ImmutableList.toImmutableList());

		return !userChanges.isEmpty()
				? Optional.of(userChanges)
				: Optional.empty();
	}

	private void changeRow(
			@NonNull final DocumentId rowId,
			@NonNull final UnaryOperator<OrderAttachmentRow> mapper)
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
