package de.metas.fresh.picking.form;

/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.apps.ADialog;
import org.compiere.minigrid.IDColumn;
import org.compiere.util.KeyNamePair;
import org.compiere.util.TimeUtil;

import java.util.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;

import de.metas.adempiere.form.terminal.ITerminalTable;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.fresh.picking.form.TableRowKey.TableRowKeyBuilder;
import de.metas.inoutcandidate.api.IPackagingDAO;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.api.Packageable;
import de.metas.inoutcandidate.api.PackageableQuery;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.order.DeliveryViaRule;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.time.SystemTime;
import lombok.NonNull;

public class FreshPackingMd
{
	private static final String SYSCONFIG_GroupByShipmentSchedule = "de.metas.fresh.picking.form.FreshPackingMd.groupByShipmentSchedule";

	// FIXME: hardcoded - ID is in column 0
	private static final int COLUMNINDEX_ID = 0;

	private final IPackagingDAO packagingDAO = Services.get(IPackagingDAO.class);

	private final Map<Integer, TableRowKey> uniqueId2Key = new HashMap<>();
	private final ListMultimap<TableRowKey, TableRow> rowsByKey = MultimapBuilder.hashKeys().arrayListValues().build();
	private Comparator<TableRowKey> tableRowKeysComparator = null;
	private final List<TableRowKey> keys = new ArrayList<>();

	private SpecialTerminalTable lines;

	//
	// Search filters
	private WarehouseId warehouseId;
	private ImmutableSet<BPartnerId> bpartnerIds = ImmutableSet.of();
	private LocalDate deliveryDate = null;
	private ITableRowSearchSelectionMatcher tableRowSearchSelectionMatcher = NullTableRowSearchSelectionMatcher.instance;

	/**
	 * Display only today's entries (i.e. DeliveryDate=TODAY)
	 */
	private boolean displayTodayEntriesOnly = false;

	/**
	 * Shall we group lines by destination Warehouse ?
	 *
	 * Default: true
	 */
	private boolean groupByWarehouseDest = false;

	/**
	 * Requery {@link #_packageableItemsAll} items from database
	 */
	private boolean _requeryNeeded = false;

	public SpecialTerminalTable getLines()
	{
		return lines;
	}

	public void setLines(@NonNull final SpecialTerminalTable lines)
	{
		this.lines = lines;
		lines.setColorProvider(new SwingPickingOKPanel_TableColorProvider(this));
	}

	public boolean isGroupByWarehouseDest()
	{
		return groupByWarehouseDest;
	}

	public void toggleGroupByWarehouseDest()
	{
		this.groupByWarehouseDest = !groupByWarehouseDest;
	}

	/**
	 * Tells that the next {@link #reload()} will need to ask the database no matter what, instead of just using cached data.
	 */
	public void setRequeryNeeded()
	{
		_requeryNeeded = true;
	}

	public WarehouseId getWarehouseId()
	{
		return warehouseId;
	}

	public void setWarehouseId(final WarehouseId warehouseId)
	{
		this.warehouseId = warehouseId;
		setRequeryNeeded();
	}

	private ImmutableSet<BPartnerId> getBPartnerIds()
	{
		return bpartnerIds;
	}

	/**
	 * Set BPartner Ids to filter
	 *
	 * @param bpartnerIds
	 */
	public void setBPartnerIds(final Set<BPartnerId> bpartnerIds)
	{
		this.bpartnerIds = bpartnerIds != null ? ImmutableSet.copyOf(bpartnerIds) : ImmutableSet.of();
	}

	public boolean isDisplayTodayEntriesOnly()
	{
		return displayTodayEntriesOnly;
	}

	public void toggleDisplayTodayEntriesOnly()
	{
		setDisplayTodayEntriesOnly(!isDisplayTodayEntriesOnly());
	}

	private void setDisplayTodayEntriesOnly(final boolean displayTodayEntriesOnly)
	{
		if (this.displayTodayEntriesOnly == displayTodayEntriesOnly)
		{
			return;
		}
		this.displayTodayEntriesOnly = displayTodayEntriesOnly;
		setRequeryNeeded();
	}

	public List<TableRowKey> getKeys()
	{
		return keys;
	}

	private final IDColumn getIDColumn(final int row)
	{
		final ITerminalTable lines = getLines();
		final IDColumn idColumn = (IDColumn)lines.getValueAt(row, COLUMNINDEX_ID);
		return idColumn;
	}

	private final int getLinesCount()
	{
		final ITerminalTable lines = getLines();
		return lines.getRowCount();
	}

	public final RowIndexes getSelectedRowIndexes()
	{
		final ITerminalTable lines = getLines();
		if (lines == null)
		{
			return RowIndexes.EMPTY;
		}
		return RowIndexes.ofArray(lines.getSelectedRows());
	}

	public final ImmutableSet<TableRowKey> getSelectedTableRowKeys()
	{
		final RowIndexes rows = getSelectedRowIndexes();
		return rows.toSet(this::getTableRowKeyForRow);
	}

	public final ImmutableSet<Integer> getSelectedRecordIds()
	{
		final RowIndexes rows = getSelectedRowIndexes();
		return rows.toSet(this::getRecordIdForRow);
	}

	public final void setSelectedTableRowKeys(final Collection<TableRowKey> tableRowKeys)
	{
		final ITerminalTable lines = getLines();

		if (tableRowKeys == null)
		{
			// force fire event even if the selection is empty
			lines.setSelectedRows(null);
			return;
		}
		else if (tableRowKeys.isEmpty())
		{
			lines.setSelectedRows(Collections.<Integer> emptyList());
			return;
		}
		final List<Integer> rowsToSelect = new ArrayList<>(tableRowKeys.size());

		final int rowCount = lines.getRowCount();
		for (int row = 0; row < rowCount; row++)
		{
			final IDColumn id = getIDColumn(row);
			if (id == null)
			{
				// shall not happen
				continue;
			}

			final TableRowKey tableRowKey = uniqueId2Key.get(id.getRecord_ID());
			if (tableRowKey == null)
			{
				continue;
			}

			if (tableRowKeys.contains(tableRowKey))
			{
				rowsToSelect.add(row);
			}
		}

		lines.setSelectedRows(rowsToSelect);
	}

	private List<TableRow> getTableRows(final RowIndexes rows, final Predicate<TableRow> filter)
	{
		if (rows.isEmpty())
		{
			return ImmutableList.of();
		}

		final int linesCount = getLinesCount();

		final List<TableRow> result = new ArrayList<>();
		for (final int row : rows.toIntSet())
		{
			if (row < 0)
			{
				// invalid row; skip it
				continue;
			}
			if (row >= linesCount)
			{
				// invalid row, skip it
				continue;
			}

			final List<TableRow> selectedRows = getTableRowsForRow(row);
			if (selectedRows.isEmpty())
			{
				continue;
			}

			for (final TableRow currentRow : selectedRows)
			{
				if (filter != null && !filter.test(currentRow))
				{
					continue;
				}

				result.add(currentRow);
			}
		}

		return result;
	}

	private List<TableRow> getTableRows(final Predicate<TableRow> filter)
	{
		if (rowsByKey.isEmpty())
		{
			return ImmutableList.of();
		}

		return rowsByKey.values()
				.stream()
				.sequential()
				.filter(row -> filter == null || filter.test(row))
				.collect(ImmutableList.toImmutableList());
	}

	public final Set<ShipmentScheduleId> getScheduleIdsForRow(final RowIndexes rows)
	{
		if (rows.isEmpty())
		{
			return ImmutableSet.of();
		}

		final Collection<TableRow> selectedRows = new ArrayList<>();
		for (final int row : rows.toIntSet())
		{
			final Collection<TableRow> currentSelectedRows = getTableRowsForRow(row);
			if (currentSelectedRows.isEmpty())
			{
				throw new AdempiereException("There is no row selected (row=" + row + ")");
			}
			selectedRows.addAll(currentSelectedRows);
		}

		return selectedRows.stream()
				.map(TableRow::getShipmentScheduleId)
				.filter(Objects::nonNull) // shall not be needed
				.collect(ImmutableSet.toImmutableSet());
	}

	/**
	 * Gets BPartner IDs from selected rows.
	 *
	 * If there are no selected rows, all BPartners will be returned
	 *
	 * @return
	 */
	public Set<KeyNamePair> getSelectedBPartners()
	{
		final TableRowBPartnersCollector bpartnersCollector = new TableRowBPartnersCollector();

		final RowIndexes selectedRows = getSelectedRowIndexes();
		if (selectedRows.isEmpty())
		{
			getTableRows(bpartnersCollector);
		}
		else
		{
			getTableRows(selectedRows, bpartnersCollector);
		}

		final Set<KeyNamePair> bpartners = bpartnersCollector.getBPartners();
		return bpartners;
	}

	/**
	 * Gets DeliveryDates from selected rows.
	 *
	 * If there are no selected rows than DeliveryDates from all lines will be returned.
	 *
	 * @return DeliveryDates from selected rows; never return null
	 */
	public Set<LocalDate> getSelectedDeliveryDates()
	{
		final RowIndexes selectedRows = getSelectedRowIndexes();

		final TableRowDeliveryDatesCollector deliveryDatesCollector = new TableRowDeliveryDatesCollector();

		if (selectedRows.isEmpty())
		{
			// If there is no selection
			// Collect Delivery Dates from all lines, even if they are selected or not
			getTableRows(deliveryDatesCollector);
		}
		else
		{
			getTableRows(selectedRows, deliveryDatesCollector);
		}

		final Set<LocalDate> deliveryDates = deliveryDatesCollector.getDeliveryDates();
		return deliveryDates;
	}

	private int getRecordIdForRow(final int row)
	{
		if (row < 0)
		{
			return -1;
		}

		final IDColumn id = getIDColumn(row);
		if (id == null)
		{
			return -1;
		}

		return id.getRecord_ID();
	}

	public final TableRowKey getTableRowKeyForRow(final int row)
	{
		final int recordId = getRecordIdForRow(row);
		return uniqueId2Key.get(recordId);
	}

	private List<TableRow> getTableRowsForRow(final int row)
	{
		final TableRowKey key = getTableRowKeyForRow(row);
		return rowsByKey.get(key);
	}

	/**
	 * Clear model content
	 */
	public void clear()
	{
		rowsByKey.clear();
		uniqueId2Key.clear();
		keys.clear();
	}

	private void addTableRow(final TableRow row)
	{
		final TableRowKey key = row.getKey();

		final List<TableRow> rows = rowsByKey.get(key);
		// System.out.println("key=" + key + " -- rows: " + rows.size() + " -- " + rows);
		if (rows.isEmpty())
		{
			uniqueId2Key.put(key.hashCode(), key);

			if (tableRowKeysComparator != null)
			{
				final int insertionPoint = Collections.binarySearch(keys, key, tableRowKeysComparator);
				final int index = insertionPoint > -1 ? insertionPoint : (-insertionPoint) - 1;
				keys.add(index, key);
			}
			else
			{
				keys.add(key);
			}
		}
		rows.add(row);
	}

	private PackageableQuery createPackageableQuery()
	{
		final WarehouseId warehouseId = getWarehouseId();
		if (warehouseId == null)
		{
			return null;
		}

		return PackageableQuery.builder()
				.warehouseId(warehouseId)
				.deliveryDate(isDisplayTodayEntriesOnly() ? SystemTime.asLocalDate() : null)
				.build();
	}

	private TableRow createTableRow(final Packageable item)
	{
		final TableRowKeyBuilder keyBuilder = TableRowKey.builder();

		final BPartnerId bpartnerId = item.getCustomerId();
		keyBuilder.bpartnerId(bpartnerId.getRepoId());

		final BigDecimal qtyToDeliver = item.getQtyToDeliver().toBigDecimal();

		final BPartnerLocationId bpartnerLocationId = item.getCustomerLocationId();
		final String bPartnerAddress = item.getCustomerAddress();
		keyBuilder.bpartnerAddress(Check.isEmpty(bPartnerAddress, true) ? null : bPartnerAddress.trim());

		final WarehouseId warehouseId = item.getWarehouseId();
		keyBuilder.warehouseId(WarehouseId.toRepoId(warehouseId));
		final String warehouseName = item.getWarehouseName();

		final int warehouseDestId;
		final String warehouseDestName;
		if (isGroupByWarehouseDest())
		{
			throw new UnsupportedOperationException("WarehouseDest is not supported");
			// keyBuilder.warehouseDestId(0);
		}
		else
		{
			warehouseDestId = 0;
			warehouseDestName = null;
			keyBuilder.warehouseDestId(-1);
		}

		final ProductId productId = item.getProductId();
		final String productName = item.getProductName();

		keyBuilder.productId(productId);
		keyBuilder.warehouseDestId(-1);
		keyBuilder.bpartnerAddress(null);

		final DeliveryViaRule deliveryVia = item.getDeliveryViaRule();

		final ZonedDateTime deliveryDate = item.getDeliveryDate(); // customer01676
		final ShipmentScheduleId shipmentScheduleId = item.getShipmentScheduleId();
		final String bpartnerValue = item.getCustomerBPValue();
		final String bpartnerName = item.getCustomerName();
		final String bPartnerLocationName = item.getCustomerBPLocationName();
		final String shipper = item.getShipperName();

		final boolean isDisplayed = item.isDisplayed();

		final boolean groupByShipmentSchedule;

		final I_M_ShipmentSchedule sched = Services.get(IShipmentSchedulePA.class).getById(shipmentScheduleId);
		groupByShipmentSchedule = Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_GroupByShipmentSchedule, false, sched.getAD_Client_ID(), sched.getAD_Org_ID());

		final TableRowKey key;
		if (groupByShipmentSchedule)
		{
			key = keyBuilder
					.shipmentScheduleId(shipmentScheduleId.getRepoId())
					.build();
		}
		else
		{
			key = getCreateTableRowKey(keyBuilder);
		}
		final TableRow row = TableRow.builder()
				.shipmentScheduleId(shipmentScheduleId)
				.qtyToDeliver(qtyToDeliver)
				.bpartnerId(bpartnerId.getRepoId())
				.bpartnerValue(bpartnerValue)
				.bpartnerName(bpartnerName)
				.bpartnerLocationId(bpartnerLocationId.getRepoId())
				.bpartnerLocationName(bPartnerLocationName)
				.warehouseName(warehouseName)
				.deliveryVia(DeliveryViaRule.toCodeOrNull(deliveryVia))
				.shipper(shipper)
				.displayed(isDisplayed)
				.key(key)
				//
				.warehouseDestId(warehouseDestId)
				.warehouseDestName(warehouseDestName)
				.productId(productId)
				.productName(productName)
				.deliveryDate(TimeUtil.asTimestamp(deliveryDate))
				.preparationDate(TimeUtil.asTimestamp(item.getPreparationDate()))
				.build();

		return row;
	}

	protected TableRowKey getCreateTableRowKey(final TableRowKey.TableRowKeyBuilder keyBuilder)
	{
		// Set SeqNo: it actually represent the order that we retrieved from database
		final int seqNo = keys.size() + 1;
		keyBuilder.seqNo(seqNo);

		final TableRowKey key = keyBuilder.build();

		//
		// Check if we already have that key in our keys list
		// NOTE: TableRowKey.equals() is not considering SeqNo deliberately
		final int idx = keys.indexOf(key);
		if (idx >= 0)
		{
			// return existing key
			return keys.get(idx);
		}

		// Return the new created key
		return key;
	}

	public void reload() throws Exception
	{
		//
		// Get all packageable items
		final List<Packageable> packageableItemsAll = getAllPackageableItems();

		//
		// Clear Model
		clear();

		//
		// Filter packageable items and add them to table
		packageableItemsAll.stream()
				.filter(this::isPackageableItemAccepted)
				.map(this::createTableRow)
				.filter(row -> row != null)
				.forEach(this::addTableRow);
	}

	private boolean isPackageableItemAccepted(final Packageable packageableItem)
	{
		if (packageableItem == null)
		{
			return false;
		}

		//
		// Filter by BPartner
		final ImmutableSet<BPartnerId> bpartnerIds = getBPartnerIds();
		if (bpartnerIds != null && !bpartnerIds.isEmpty())
		{
			final BPartnerId bpartnerId = packageableItem.getCustomerId();
			if (!bpartnerIds.contains(bpartnerId))
			{
				return false;
			}
		}

		//
		// Filter by DeliveryDate
		final LocalDate deliveryDate = getDeliveryDate();
		if (deliveryDate != null)
		{
			final LocalDate packageableDeliveryDateDay = packageableItem.getDeliveryDate().toLocalDate();
			if (!deliveryDate.equals(packageableDeliveryDateDay))
			{
				return false;
			}
		}

		return true;
	}

	private List<Packageable> getAllPackageableItems()
	{
		if (_packageableItemsAll == null || _requeryNeeded)
		{
			final PackageableQuery query = createPackageableQuery();
			if (query == null)
			{
				_packageableItemsAll = ImmutableList.of();
			}
			else
			{
				_packageableItemsAll = packagingDAO.stream(query)
						.collect(ImmutableList.toImmutableList());
			}

			_requeryNeeded = false;
		}

		return _packageableItemsAll;
	}

	private ImmutableList<Packageable> _packageableItemsAll = null;

	/**
	 *
	 * @return how many rows (i.e. product picking lines) we have
	 */
	public int getRowsCount()
	{
		return rowsByKey.size();
	}

	public final void setTableRowSearchSelectionMatcher(final ITableRowSearchSelectionMatcher tableRowSearchSelectionMatcher)
	{
		Check.assumeNotNull(tableRowSearchSelectionMatcher, "tableRowSearchSelectionMatcher not null");
		this.tableRowSearchSelectionMatcher = tableRowSearchSelectionMatcher;

		//
		// Our new matcher is NULL => do nothing
		if (tableRowSearchSelectionMatcher == null)
		{
			return;
		}

		//
		// Our new matcher will always return false => do nothing
		if (tableRowSearchSelectionMatcher.isNull())
		{
			return;
		}

		//
		// Get matched rows
		final List<TableRowKey> tableRowKeysFound = new ArrayList<>();
		for (final TableRowKey key : getKeys())
		{
			if (tableRowSearchSelectionMatcher.match(key))
			{
				tableRowKeysFound.add(key);
			}
		}

		//
		// No matched rows found => beep the user, but do nothing
		if (tableRowKeysFound.isEmpty())
		{
			ADialog.beep();
			return;
		}

		//
		// We found more then one row and this is not allowed by our matcher
		if (!tableRowSearchSelectionMatcher.isAllowMultipleResults() && tableRowKeysFound.size() > 1)
		{
			// do nothing
			return;
		}

		//
		// Add our matched rows to selection
		final Set<TableRowKey> selectionNew = new HashSet<>(getSelectedTableRowKeys());
		selectionNew.addAll(tableRowKeysFound);
		setSelectedTableRowKeys(selectionNew);
	}

	/**
	 * Gets current {@link ITableRowSearchSelectionMatcher}.
	 *
	 * @return current {@link ITableRowSearchSelectionMatcher}; never null
	 */
	public final ITableRowSearchSelectionMatcher getTableRowSearchSelectionMatcher()
	{
		return tableRowSearchSelectionMatcher;
	}

	public void setDeliveryDate(final LocalDate deliveryDate)
	{
		this.deliveryDate = deliveryDate;
	}

	public LocalDate getDeliveryDate()
	{
		return deliveryDate;
	}

	public void setTableRowKeysComparator(final Comparator<TableRowKey> tableRowKeysComparator)
	{
		if (this.tableRowKeysComparator == tableRowKeysComparator)
		{
			return;
		}

		this.tableRowKeysComparator = tableRowKeysComparator;
		if (this.tableRowKeysComparator != null)
		{
			Collections.sort(keys, this.tableRowKeysComparator);
		}
	}

	public List<TableRowKey> getTableRowKeys()
	{
		return keys;
	}

	public TableRow getAggregatedTableRowOrNull(final TableRowKey key)
	{
		final List<TableRow> rowsForKey = rowsByKey.get(key);
		if (rowsForKey.isEmpty())
		{
			return null;
		}

		//
		// Iterate source rows for current Key and compute
		// * DeliveryDate
		// * PreparationTime
		// * QtyToDeliver
		final TableRow rowAggregated = rowsForKey.get(0).copy();
		rowAggregated.setDeliveryDate(null);
		rowAggregated.setPreparationDate(null);
		rowAggregated.setQtyToDeliver(BigDecimal.ZERO);

		for (final TableRow row : rowsForKey)
		{
			// DeliveryDate: earliest
			// PreparationTime: the one which is corresponding to earliest DeliveryDate
			final Date rowDeliveryDate = row.getDeliveryDate();
			final Date rowPreparationDate = row.getPreparationDate();
			if (row.isDisplayed() && rowDeliveryDate != null)
			{
				final Date aggDeliveryDate = rowAggregated.getDeliveryDate();

				// Case: DeliveryDate was not set until now
				// => use current row's Delivery Date
				// => set PreparationDate from current row
				if (aggDeliveryDate == null)
				{
					rowAggregated.setDeliveryDate(rowDeliveryDate);
					rowAggregated.setPreparationDate(rowPreparationDate);
				}
				// Case: aggregated DeliveryDate equals with our current minimum DeliveryDate
				// => use the minimum PreparationDate
				else if (aggDeliveryDate.compareTo(rowDeliveryDate) == 0)
				{
					final Date aggPreparationDateOld = rowAggregated.getPreparationDate();
					final Date aggPreparationDateNew = TimeUtil.min(aggPreparationDateOld, rowPreparationDate);
					rowAggregated.setPreparationDate(aggPreparationDateNew);
				}
				// Case: aggregated DeliveryDate is after current row's DeliveryDate
				// => use current row's Delivery Date
				// => set PreparationDate from current row
				else if (aggDeliveryDate.compareTo(rowDeliveryDate) > 0)
				{
					rowAggregated.setDeliveryDate(rowDeliveryDate);
					rowAggregated.setPreparationDate(rowPreparationDate);
				}
				// Case: aggregated DeliveryDate is before current row's DeliveryDate
				// => do nothing
				else
				{
					// nothing
				}
			}

			// QtyToDeliver: sum of all QtyToDeliver
			{
				final BigDecimal rowQtyToDeliver = row.getQtyToDeliver();

				// sum only if QtyToDeliver>0, other lines are only for user information (if isDisplayNonDeliverableItems is set)
				if (rowQtyToDeliver != null && rowQtyToDeliver.signum() > 0)
				{
					final BigDecimal qtyToDeliverOld = rowAggregated.getQtyToDeliver();
					final BigDecimal qtyToDeliverNew = qtyToDeliverOld.add(rowQtyToDeliver);
					rowAggregated.setQtyToDeliver(qtyToDeliverNew);
				}
			}
		}

		return rowAggregated;
	}

	public List<TableRow> getAggregatedTableRows()
	{
		final List<TableRow> tableRowsAggregated = new ArrayList<>();

		final List<TableRowKey> tableRowKeys = getTableRowKeys();
		for (final TableRowKey key : tableRowKeys)
		{
			final TableRow tableRowAggregated = getAggregatedTableRowOrNull(key);
			if (tableRowAggregated == null)
			{
				continue;
			}

			tableRowsAggregated.add(tableRowAggregated);
		}

		return tableRowsAggregated;
	}

	/**
	 *
	 * @return true if this model has no rows (i.e. there are no {@link TableRowKey}s)
	 */
	public boolean isEmpty()
	{
		return keys.isEmpty();
	}

	public Collection<TableRow> getTableRowsForKey(final TableRowKey key)
	{
		return rowsByKey.get(key);
	}

	public Collection<TableRow> getTableRowsForUniqueId(final int uniqueId)
	{
		final TableRowKey key = uniqueId2Key.get(uniqueId);
		return getTableRowsForKey(key);
	}
}
