package de.metas.ui.web.split_shipment;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.document.dimension.Dimension;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.split.ShipmentScheduleSplit;
import de.metas.inoutcandidate.split.ShipmentScheduleSplitId;
import de.metas.inoutcandidate.split.ShipmentScheduleSplitService;
import de.metas.quantity.Quantity;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.view.IEditableView;
import de.metas.ui.web.view.event.ViewChangesCollector;
import de.metas.ui.web.view.template.IEditableRowsData;
import de.metas.ui.web.view.template.IRowsData;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.util.GuavaCollectors;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.compiere.model.I_C_UOM;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.UnaryOperator;

public class SplitShipmentRows implements IEditableRowsData<SplitShipmentRow>
{
	public static SplitShipmentRows cast(final IRowsData<SplitShipmentRow> rowsData) {return (SplitShipmentRows)rowsData;}

	@NonNull private final ShipmentScheduleSplitService service;

	@NonNull @Getter private final ShipmentScheduleId shipmentScheduleId;
	@NonNull private final I_C_UOM uom;

	@NonNull private final LinkedHashMap<DocumentId, SplitShipmentRow> rowsById = new LinkedHashMap<>();

	@Builder
	private SplitShipmentRows(
			@NonNull ShipmentScheduleSplitService service,
			@NonNull final ShipmentScheduleId shipmentScheduleId,
			@NonNull final I_C_UOM uom)
	{
		this.service = service;
		this.shipmentScheduleId = shipmentScheduleId;
		this.uom = uom;

		refresh();
		addEmptyLineIfNeeded();
	}

	@Override
	public synchronized Map<DocumentId, SplitShipmentRow> getDocumentId2TopLevelRows() {return ImmutableMap.copyOf(rowsById);}

	@Override
	public DocumentIdsSelection getDocumentIdsToInvalidate(final TableRecordReferenceSet recordRefs) {return DocumentIdsSelection.EMPTY;}

	@Override
	public void invalidateAll() {}

	@Override
	public void patchRow(final IEditableView.RowEditingContext ctx, final List<JSONDocumentChangedEvent> fieldChangeRequests)
	{
		changeRow(ctx.getRowId(), row -> row.withChanges(fieldChangeRequests));
	}

	private DocumentId newRowId() {return DocumentId.of(UUID.randomUUID().toString());}

	private synchronized void refresh()
	{
		final LinkedHashMap<ShipmentScheduleSplitId, ShipmentScheduleSplit> existingSplitsById = service.getByShipmentScheduleId(shipmentScheduleId)
				.stream()
				.collect(GuavaCollectors.toLinkedHashMapByKey(ShipmentScheduleSplit::getId));

		//
		// Update existing rows
		// Remove rows which are no longer present in database
		for (final SplitShipmentRow row : ImmutableList.copyOf(rowsById.values()))
		{
			if (row.getShipmentScheduleSplitId() == null)
			{
				continue;
			}

			final ShipmentScheduleSplit existingSplit = existingSplitsById.remove(row.getShipmentScheduleSplitId());
			if (existingSplit == null)
			{
				// split was removed from database => remove it from rows too
				removeRowById(row.getId());
			}
			else
			{
				final SplitShipmentRow rowUpdated = updatingRow(row, existingSplit);
				addRow(rowUpdated);
			}
		}

		//
		// Add new rows
		for (final ShipmentScheduleSplit split : existingSplitsById.values())
		{
			final SplitShipmentRow newRow = updatingRow(newEmptyRow(), split);
			addRow(newRow);
		}
	}

	private synchronized void changeRow(
			@NonNull final DocumentId rowId,
			@NonNull final UnaryOperator<SplitShipmentRow> mapper)
	{
		rowsById.compute(rowId, (key, oldRow) -> {
			if (oldRow == null)
			{
				throw new EntityNotFoundException(rowId.toJson());
			}

			SplitShipmentRow row = mapper.apply(oldRow);
			return saveIfPossible(row);
		});

		addEmptyLineIfNeeded();
	}

	private SplitShipmentRow saveIfPossible(final SplitShipmentRow row)
	{
		if (!row.isSaveable())
		{
			return row;
		}

		final ShipmentScheduleSplit splitUpdated;
		if (row.getShipmentScheduleSplitId() != null)
		{
			splitUpdated = service.changeById(row.getShipmentScheduleSplitId(), split -> updateFromRow(split, row));
		}
		else
		{
			final ShipmentScheduleSplit split = ShipmentScheduleSplit.builder()
					.shipmentScheduleId(shipmentScheduleId)
					.deliveryDate(row.getDeliveryDate())
					.qtyToDeliver(row.getQtyToDeliver())
					.dimension(Dimension.EMPTY)
					.build();
			updateFromRow(split, row);
			service.save(split);
			splitUpdated = split;
		}

		return updatingRow(row, splitUpdated);
	}

	private static void updateFromRow(final ShipmentScheduleSplit split, final SplitShipmentRow from)
	{
		split.assertNotProcessed();
		split.setDeliveryDate(from.getDeliveryDate());
		split.setQtyToDeliver(from.getQtyToDeliver());
		split.setDimension(split.getDimension().toBuilder()
				.userElementString1(from.getUserElementString1())
				.userElementString2(from.getUserElementString2())
				.build());
	}

	private static SplitShipmentRow updatingRow(final SplitShipmentRow row, final ShipmentScheduleSplit from)
	{
		final SplitShipmentRow rowUpdated = row.toBuilder()
				.shipmentScheduleSplitId(from.getId())
				.processed(from.isProcessed())
				//
				.deliveryDate(from.getDeliveryDate())
				.qtyToDeliver(from.getQtyToDeliver())
				.userElementString1(from.getDimension().getUserElementString1())
				.userElementString2(from.getDimension().getUserElementString2())
				//
				.build();

		return !Objects.equals(row, rowUpdated) ? rowUpdated : row;
	}

	private synchronized void addEmptyLineIfNeeded()
	{
		if (!hasNewLines())
		{
			final SplitShipmentRow emptyRow = newEmptyRow();
			addRow(emptyRow);

			notifyFullyChanged();
		}
	}

	private void notifyFullyChanged()
	{
		ViewChangesCollector.getCurrentOrAutoflush().collectFullyChanged(); // force reloading to also add the new row to frontend rows list
	}

	private boolean hasNewLines()
	{
		return rowsById.values().stream().anyMatch(SplitShipmentRow::isNewLine);
	}

	private SplitShipmentRow newEmptyRow()
	{
		return SplitShipmentRow.builder()
				.rowId(newRowId())
				.qtyToDeliver(Quantity.zero(uom))
				.build();
	}

	private void addRow(final SplitShipmentRow row) {rowsById.put(row.getId(), row);}

	private void removeRowById(final DocumentId rowId) {rowsById.remove(rowId);}

	public synchronized void deleteByIds(@NonNull final DocumentIdsSelection rowIds)
	{
		if (rowIds.isEmpty())
		{
			return;
		}

		final HashSet<DocumentId> rowIdsToRemove = new HashSet<>();
		final HashSet<ShipmentScheduleSplitId> splitIdsToRemove = new HashSet<>();
		for (final SplitShipmentRow row : rowsById.values())
		{
			if (!rowIds.contains(row.getId()))
			{
				continue;
			}

			rowIdsToRemove.add(row.getId());
			if (row.getShipmentScheduleSplitId() != null)
			{
				splitIdsToRemove.add(row.getShipmentScheduleSplitId());
			}
		}

		service.deleteByIds(splitIdsToRemove);
		rowIdsToRemove.forEach(rowsById::remove);

		addEmptyLineIfNeeded();

		notifyFullyChanged();
	}

	public synchronized boolean hasRowsToProcess()
	{
		return rowsById.values().stream().anyMatch(SplitShipmentRow::isEligibleForProcessing);
	}
}
