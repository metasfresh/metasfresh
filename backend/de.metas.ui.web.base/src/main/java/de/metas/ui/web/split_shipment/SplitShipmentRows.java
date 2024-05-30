package de.metas.ui.web.split_shipment;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_Split;
import de.metas.inoutcandidate.split.ShipmentScheduleSplit;
import de.metas.inoutcandidate.split.ShipmentScheduleSplitId;
import de.metas.inoutcandidate.split.ShipmentScheduleSplitService;
import de.metas.quantity.Quantity;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.view.IEditableView;
import de.metas.ui.web.view.ViewHeaderProperties;
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
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;

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

	@NonNull private final ShipmentScheduleInfoLoader shipmentScheduleInfoLoader;
	@NonNull private final ShipmentScheduleSplitService shipmentScheduleSplitService;

	@NonNull @Getter private final ShipmentScheduleId shipmentScheduleId;
	private ShipmentScheduleInfo _shipmentScheduleInfo; // lazy

	@NonNull private final LinkedHashMap<DocumentId, SplitShipmentRow> _rowsById = new LinkedHashMap<>();
	private boolean needsRefresh;

	@Builder
	private SplitShipmentRows(
			@NonNull final ShipmentScheduleInfoLoader shipmentScheduleInfoLoader,
			@NonNull ShipmentScheduleSplitService shipmentScheduleSplitService,
			@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		this.shipmentScheduleInfoLoader = shipmentScheduleInfoLoader;
		this.shipmentScheduleSplitService = shipmentScheduleSplitService;

		this.shipmentScheduleId = shipmentScheduleId;
		this.needsRefresh = true;
	}

	@Override
	public synchronized Map<DocumentId, SplitShipmentRow> getDocumentId2TopLevelRows()
	{
		refreshIfNeeded();
		return ImmutableMap.copyOf(getMap());
	}

	@Override
	public DocumentIdsSelection getDocumentIdsToInvalidate(@NonNull final TableRecordReferenceSet recordRefs)
	{
		if (recordRefs.isEmpty())
		{
			return DocumentIdsSelection.EMPTY;
		}

		boolean isShipmentScheduleChanged = recordRefs.streamIds(I_M_ShipmentSchedule.Table_Name, ShipmentScheduleId::ofRepoId)
				.anyMatch(shipmentScheduleId -> ShipmentScheduleId.equals(shipmentScheduleId, this.shipmentScheduleId));
		if (isShipmentScheduleChanged)
		{
			// all rows shall be invalidated
			return DocumentIdsSelection.ALL;
		}
		else
		{
			final ImmutableMap<ShipmentScheduleSplitId, DocumentId> rowIdsBySplitId = getMap().values()
					.stream()
					.filter(row -> row.getShipmentScheduleSplitId() != null)
					.collect(ImmutableMap.toImmutableMap(SplitShipmentRow::getShipmentScheduleSplitId, SplitShipmentRow::getId));
			if (rowIdsBySplitId.isEmpty())
			{
				return DocumentIdsSelection.EMPTY;
			}

			return recordRefs.streamIds(I_M_ShipmentSchedule_Split.Table_Name, ShipmentScheduleSplitId::ofRepoId)
					.map(rowIdsBySplitId::get)
					.filter(Objects::nonNull)
					.collect(DocumentIdsSelection.toDocumentIdsSelection());
		}
	}

	@Override
	public synchronized void invalidateAll()
	{
		this._shipmentScheduleInfo = null;
		this.needsRefresh = true;
	}

	@Override
	public void patchRow(final IEditableView.RowEditingContext ctx, final List<JSONDocumentChangedEvent> fieldChangeRequests)
	{
		changeRow(ctx.getRowId(), row -> row.withChanges(fieldChangeRequests));
	}

	private DocumentId newRowId() {return DocumentId.of(UUID.randomUUID().toString());}

	private LinkedHashMap<DocumentId, SplitShipmentRow> getMap()
	{
		return this._rowsById;
	}

	private void refreshIfNeeded()
	{
		if (this.needsRefresh)
		{
			refresh();
		}
	}

	private synchronized void refresh()
	{
		refreshRows();
		addOrRemoveEmptyLineIfNeeded();

		this.needsRefresh = false;
	}

	private synchronized ShipmentScheduleInfo getShipmentScheduleInfo()
	{
		ShipmentScheduleInfo shipmentScheduleInfo = this._shipmentScheduleInfo;
		if (shipmentScheduleInfo == null)
		{
			shipmentScheduleInfo = this._shipmentScheduleInfo = shipmentScheduleInfoLoader.getById(shipmentScheduleId);
		}
		return shipmentScheduleInfo;
	}

	private void refreshRows()
	{
		final LinkedHashMap<ShipmentScheduleSplitId, ShipmentScheduleSplit> existingSplitsById = shipmentScheduleSplitService.getByShipmentScheduleId(shipmentScheduleId)
				.stream()
				.collect(GuavaCollectors.toLinkedHashMapByKey(ShipmentScheduleSplit::getId));

		//
		// Update existing rows
		// Remove rows which are no longer present in database
		final ShipmentScheduleInfo shipmentScheduleInfo = getShipmentScheduleInfo();
		for (final SplitShipmentRow row : ImmutableList.copyOf(getMap().values()))
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
				final SplitShipmentRow rowUpdated = updatingRow(row, existingSplit, shipmentScheduleInfo);
				addRow(rowUpdated);
			}
		}

		//
		// Add new rows
		for (final ShipmentScheduleSplit split : existingSplitsById.values())
		{
			final SplitShipmentRow newRow = updatingRow(newEmptyRow(), split, shipmentScheduleInfo);
			addRow(newRow);
		}
	}

	private synchronized void changeRow(
			@NonNull final DocumentId rowId,
			@NonNull final UnaryOperator<SplitShipmentRow> mapper)
	{
		refreshIfNeeded();
		getMap().compute(rowId, (key, oldRow) -> {
			if (oldRow == null)
			{
				throw new EntityNotFoundException(rowId.toJson());
			}

			SplitShipmentRow row = mapper.apply(oldRow);
			return saveIfPossible(row);
		});

		addOrRemoveEmptyLineIfNeeded();
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
			splitUpdated = shipmentScheduleSplitService.changeById(row.getShipmentScheduleSplitId(), split -> updateFromRow(split, row));
		}
		else
		{
			final ShipmentScheduleSplit split = ShipmentScheduleSplit.builder()
					.shipmentScheduleId(shipmentScheduleId)
					.deliveryDate(row.getDeliveryDateNotNull())
					.qtyToDeliver(row.getQtyToDeliver())
					.build();
			updateFromRow(split, row);
			shipmentScheduleSplitService.save(split);
			splitUpdated = split;
		}

		return updatingRow(row, splitUpdated, getShipmentScheduleInfo());
	}

	private static void updateFromRow(final ShipmentScheduleSplit split, final SplitShipmentRow from)
	{
		split.assertNotProcessed();
		split.setDeliveryDate(from.getDeliveryDateNotNull());
		split.setQtyToDeliver(from.getQtyToDeliver());
		split.setDimension(split.getDimension().toBuilder()
				.userElementNumber1(from.getUserElementNumber1())
				.userElementNumber2(from.getUserElementNumber2())
				.build());
	}

	private static SplitShipmentRow updatingRow(final SplitShipmentRow row, final ShipmentScheduleSplit from, final ShipmentScheduleInfo shipmentScheduleInfo)
	{
		final SplitShipmentRow rowUpdated = row.toBuilder()
				.shipmentScheduleSplitId(from.getId())
				.readonly(from.isProcessed() || shipmentScheduleInfo.isReadonly())
				//
				.deliveryDate(from.getDeliveryDate())
				.qtyToDeliver(from.getQtyToDeliver())
				.userElementNumber1(from.getDimension().getUserElementNumber1())
				.userElementNumber2(from.getDimension().getUserElementNumber2())
				//
				.build();

		return !Objects.equals(row, rowUpdated) ? rowUpdated : row;
	}

	private synchronized void addOrRemoveEmptyLineIfNeeded()
	{
		final boolean readonly = getShipmentScheduleInfo().isReadonly();
		final LinkedHashMap<DocumentId, SplitShipmentRow> map = getMap();
		final DocumentIdsSelection newLineIds = map.values().stream().filter(SplitShipmentRow::isNewLine).map(SplitShipmentRow::getId).collect(DocumentIdsSelection.toDocumentIdsSelection());

		if (readonly)
		{
			if (!newLineIds.isEmpty())
			{
				newLineIds.forEach(this::removeRowById);
				notifyFullyChanged();
			}
		}
		else
		{
			if (newLineIds.isEmpty())
			{
				addRow(newEmptyRow());
				notifyFullyChanged();
			}
		}
	}

	private void notifyFullyChanged()
	{
		ViewChangesCollector.getCurrentOrAutoflush().collectFullyChanged(); // force reloading to also add the new row to frontend rows list
	}

	private SplitShipmentRow newEmptyRow()
	{
		final ShipmentScheduleInfo shipmentScheduleInfo = getShipmentScheduleInfo();
		return SplitShipmentRow.builder()
				.rowId(newRowId())
				.qtyToDeliver(Quantity.zero(shipmentScheduleInfo.getUom()))
				.build();
	}

	private void addRow(final SplitShipmentRow row) {getMap().put(row.getId(), row);}

	private void removeRowById(final DocumentId rowId) {getMap().remove(rowId);}

	public synchronized void deleteByIds(@NonNull final DocumentIdsSelection rowIds)
	{
		if (rowIds.isEmpty())
		{
			return;
		}

		refreshIfNeeded();

		final HashSet<DocumentId> rowIdsToRemove = new HashSet<>();
		final HashSet<ShipmentScheduleSplitId> splitIdsToRemove = new HashSet<>();
		final LinkedHashMap<DocumentId, SplitShipmentRow> rowsById = getMap();
		for (final SplitShipmentRow row : rowsById.values())
		{
			if (!rowIds.contains(row.getId()))
			{
				continue;
			}

			if (!row.isDeletable())
			{
				throw new AdempiereException("row is not deletable: " + row);
			}

			rowIdsToRemove.add(row.getId());
			if (row.getShipmentScheduleSplitId() != null)
			{
				splitIdsToRemove.add(row.getShipmentScheduleSplitId());
			}
		}

		shipmentScheduleSplitService.deleteByIds(splitIdsToRemove);
		rowIdsToRemove.forEach(rowsById::remove);

		addOrRemoveEmptyLineIfNeeded();

		notifyFullyChanged();
	}

	public synchronized boolean hasRowsToProcess()
	{
		refreshIfNeeded();
		return getMap().values().stream().anyMatch(SplitShipmentRow::isEligibleForProcessing);
	}

	public ViewHeaderProperties getHeaderProperties()
	{
		return getShipmentScheduleInfo().toViewHeaderProperties();
	}
}
