package de.metas.picking.legacy.form;

/*
 * #%L
 * de.metas.swat.base
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.apps.ADialog;
import org.compiere.minigrid.IDColumn;
import org.compiere.minigrid.IMiniTable;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.TimeUtil;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;

import de.metas.adempiere.service.IPackagingBL;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.inoutcandidate.api.IPackagingDAO;
import de.metas.inoutcandidate.api.Packageable;
import de.metas.inoutcandidate.api.PackageableQuery;
import de.metas.inoutcandidate.model.X_M_ShipmentSchedule;
import de.metas.picking.legacy.form.TableRowKey.TableRowKeyBuilder;
import de.metas.shipping.ShipperId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.time.SystemTime;

public class PackingMd extends MvcMdGenForm
{
	// FIXME: hardcoded - ID is in column 0
	private static final int COLUMNINDEX_ID = 0;

	private final IPackagingDAO packagingDAO = Services.get(IPackagingDAO.class);

	private final int packingUserId;

	// metas: c.ghita@metas.ro: use this for separating pos
	private boolean posMode = false;

	private final Map<Integer, TableRowKey> uniqueId2Key = new HashMap<>();

	private final ListMultimap<TableRowKey, TableRow> rowsByKey = MultimapBuilder.hashKeys().arrayListValues().build();

	private Comparator<TableRowKey> tableRowKeysComparator = null;
	private final List<TableRowKey> keys = new ArrayList<>();

	//
	// Search filters
	private WarehouseId warehouseId;
	private Set<BPartnerId> bpartnerIds;
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

	public PackingMd(final int windowNo, final int packingUserId)
	{
		super(windowNo);
		this.packingUserId = packingUserId;
	}

	public final boolean isPOSMode()
	{
		return posMode;
	}

	public final void setPOSMode(final boolean posMode)
	{
		this.posMode = posMode;
	}

	public int getPackingUserId()
	{
		return packingUserId;
	}

	public boolean isGroupByWarehouseDest()
	{
		return groupByWarehouseDest;
	}

	public void setGroupByWarehouseDest(final boolean groupByWarehouseDest)
	{
		this.groupByWarehouseDest = groupByWarehouseDest;
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

	public Set<BPartnerId> getBPartnerIds()
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
		this.bpartnerIds = bpartnerIds;
	}

	public boolean isDisplayTodayEntriesOnly()
	{
		return displayTodayEntriesOnly;
	}

	public void setDisplayTodayEntriesOnly(final boolean displayTodayEntriesOnly)
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
		final IMiniTable miniTable = getMiniTable();
		final IDColumn idColumn = (IDColumn)miniTable.getValueAt(row, COLUMNINDEX_ID);
		return idColumn;
	}

	public final int getTableRowsCount()
	{
		final IMiniTable miniTable = getMiniTable();
		final int rowCount = miniTable.getRowCount();
		return rowCount;
	}

	private final int[] getSelectedRowIndices()
	{
		final IMiniTable miniTable = getMiniTable();
		final int[] rows = miniTable.getSelectedRows();
		return rows;
	}

	public final Set<TableRowKey> getSelectedTableRowKeys()
	{
		final int[] rows = getSelectedRowIndices();
		if (rows == null || rows.length == 0)
		{
			// returing a new instance instead of Collection.emptyList() because maybe we want to modify
			return new HashSet<>();
		}

		final Set<TableRowKey> tableRowKeys = new HashSet<>(rows.length);
		for (final int row : rows)
		{
			final TableRowKey tableRowKey = getTableRowKeyForRow(row);
			if (tableRowKey == null)
			{
				continue;
			}

			tableRowKeys.add(tableRowKey);
		}

		return tableRowKeys;
	}

	public final void setSelectedTableRowKeys(final Collection<TableRowKey> tableRowKeys)
	{
		final IMiniTable miniTable = getMiniTable();

		if (tableRowKeys == null)
		{
			// force fire event even if the selection is empty
			miniTable.setSelectedRows(null);
			return;
		}
		else if (tableRowKeys.isEmpty())
		{
			miniTable.setSelectedRows(Collections.<Integer> emptyList());
			return;
		}
		final List<Integer> rowsToSelect = new ArrayList<>(tableRowKeys.size());

		final int rowCount = miniTable.getRowCount();
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

		miniTable.setSelectedRows(rowsToSelect);
	}

	private List<TableRow> getTableRows(final int[] rows, final Predicate<TableRow> filter)
	{
		if (rows == null || rows.length == 0)
		{
			return Collections.emptyList();
		}

		final int rowCount = getTableRowsCount();

		final List<TableRow> result = new ArrayList<>();
		for (final int row : rows)
		{
			if (row < 0)
			{
				// invalid row; skip it
				continue;
			}
			if (row >= rowCount)
			{
				// invalid row, skip it
				continue;
			}

			final Collection<TableRow> selectedRows = getTableRowsForRow(row);
			if (selectedRows == null || selectedRows.isEmpty())
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

	/**
	 *
	 * @param miniTable
	 * @return
	 */
	public final Set<Integer> getSelectedScheduleIds()
	{
		final int[] selectedIds = getSelectedRowIndices();
		return getScheduleIdsForRow(selectedIds);
	}

	public final Set<Integer> getScheduleIdsForRow(final int[] rows)
	{
		if (rows == null || rows.length == 0)
		{
			return Collections.emptySet();
		}

		final Collection<TableRow> selectedRows = new ArrayList<>();

		for (final int row : rows)
		{
			final Collection<TableRow> currentSelectedRows = getTableRowsForRow(row);
			if (currentSelectedRows.isEmpty())
			{
				throw new AdempiereException("There is no row selected (row=" + row + ")");
			}
			selectedRows.addAll(currentSelectedRows);
		}

		final Set<Integer> selectedShipmentScheduleIds = new HashSet<>();
		for (final TableRow currentRow : selectedRows)
		{
			selectedShipmentScheduleIds.add(currentRow.getShipmentScheduleId());
		}
		return selectedShipmentScheduleIds;
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

		final int[] selectedRows = getSelectedRowIndices();
		if (selectedRows == null || selectedRows.length == 0)
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

	public int getBPartnerIdForRow(final int row)
	{
		final TableRowBPartnersCollector bpartnersCollector = new TableRowBPartnersCollector();
		getTableRows(new int[] { row }, bpartnersCollector);

		final Set<KeyNamePair> bpartners = bpartnersCollector.getBPartners();
		if (bpartners.isEmpty())
		{
			// shall not happen
			// FIXME: throw exception?
			return -1;
		}
		else if (bpartners.size() > 1)
		{
			throw new AdempiereException("More then one BP found for row=" + row + ": " + bpartners);
		}

		//
		// Get the first (and only one) BPartner ID and return it
		final KeyNamePair bpartner = bpartners.iterator().next();
		final int bpartnerId = bpartner.getKey();
		return bpartnerId;
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
		final int[] selectedRows = getSelectedRowIndices();

		final TableRowDeliveryDatesCollector deliveryDatesCollector = new TableRowDeliveryDatesCollector();

		if (selectedRows == null || selectedRows.length == 0)
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

	private Collection<TableRow> getTableRowsForRow(final int row)
	{
		final TableRowKey key = getTableRowKeyForRow(row);
		return rowsByKey.get(key);
	}

	public int getBPartnerLocationIdForRow(final int row)
	{
		final Collection<TableRow> selectedRows = getTableRowsForRow(row);

		int selectedBPLocation = 0;
		for (final TableRow currentRow : selectedRows)
		{
			selectedBPLocation = currentRow.getBpartnerLocationId();
		}
		return selectedBPLocation;
	}

	public int getWarehouseDestIdForRow(final int row)
	{
		final Collection<TableRow> selectedRows = getTableRowsForRow(row);

		int warehouseDestId = 0;
		for (final TableRow currentRow : selectedRows)
		{
			warehouseDestId = currentRow.getWarehouseDestId();
		}
		return warehouseDestId;
	}

	public int getShipperIdForRow(final int row)
	{
		if (row < 0)
		{
			return -1;
		}
		final TableRowKey tableRowKey = getTableRowKeyForRow(row);
		return tableRowKey.getShipperId();
	}

	public final int getProductIdForRow(final int row)
	{
		final TableRowKey tableRowKey = getTableRowKeyForRow(row);
		if (tableRowKey == null)
		{
			return -1;
		}
		return tableRowKey.getProductId();
	}

	/**
	 * Returns false if the selected row's DeliveryViaRule equals {@link X_M_ShipmentSchedule#DELIVERYVIARULE_Abholung}. False otherwise.
	 *
	 * @return
	 * @throws IllegalStateException if no row is selected
	 */
	public boolean isRowUsesShipper(final int selectedIdx)
	{
		final IDColumn id = getIDColumn(selectedIdx);

		final String selectedDeliveryVia = uniqueId2Key.get(id.getRecord_ID()).getDeliveryVia();
		return !Objects.equals(selectedDeliveryVia, X_M_ShipmentSchedule.DELIVERYVIARULE_Pickup); // selectedDeliveryVia might be null
	}

	/**
	 * Clear model content
	 */
	public void clear()
	{
		rowsByKey.clear();
		uniqueId2Key.clear();
		keys.clear();
		if (getSelection() != null)
		{
			getSelection().clear();
		}
	}

	public void addTableRow(final TableRow row)
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

	protected TableRow createTableRow(final Packageable item)
	{
		final BPartnerId bpartnerId = item.getCustomerId();
		final WarehouseId warehouseDestId = null; // M_Warehouse_Dest is gone for a long time by now
		final BigDecimal qtyToDeliver = item.getQtyToDeliver().getAsBigDecimal();

		final TableRowKeyBuilder keyBuilder = TableRowKey.builder();
		keyBuilder.bpartnerId(BPartnerId.toRepoIdOr(bpartnerId, -1));

		final BPartnerLocationId bpartnerLocationId = item.getCustomerLocationId();
		final String bPartnerAddress = item.getCustomerAddress();
		keyBuilder.bpartnerAddress(bPartnerAddress);

		final WarehouseId warehouseId = item.getWarehouseId();
		keyBuilder.warehouseId(warehouseId.getRepoId());
		final String warehouseName = item.getWarehouseName();

		final String warehouseDestName;
		if (groupByWarehouseDest)
		{
			throw new UnsupportedOperationException("WarehouseDest is not supported");
		}
		else
		{
			// warehouseDestId = 0;
			warehouseDestName = null;
			keyBuilder.warehouseDestId(-1);
		}

		final int productId = item.getProductId().getRepoId();
		final String productName = item.getProductName();

		keyBuilder.productId(productId);
		keyBuilder.warehouseDestId(-1);
		keyBuilder.bpartnerId(-1);
		keyBuilder.bpartnerAddress(null);

		final String deliveryVia = item.getDeliveryVia();
		// final String deliveryViaName = item.getDeliveryViaName();
		// final int shipperId = rs.getInt(I_M_Shipper.COLUMNNAME_M_Shipper_ID);
		final LocalDateTime deliveryDate = item.getDeliveryDate(); // 01676
		final int shipmentScheduleId = item.getShipmentScheduleId().getRepoId();
		final String bpartnerValue = item.getCustomerBPValue();
		final String bpartnerName = item.getCustomerName();
		final String bPartnerLocationName = item.getCustomerBPLocationName();
		final String shipper = item.getShipperName();

		// metas-ts: we need the shipper-ID to be in PackingDetailsMd (see PAcking.createPackingDetailsModel() ), because it needs to be displayed in PackingDetailsV (see
		// VPackaging.validateSuggestion())
		keyBuilder.shipperId(ShipperId.toRepoId(item.getShipperId()));

		final boolean isDisplayed = item.isDisplayed();

		final TableRowKey key = getCreateTableRowKey(keyBuilder);
		final TableRow row = TableRow.builder()
				.shipmentScheduleId(shipmentScheduleId)
				.qtyToDeliver(qtyToDeliver)
				.bpartnerId(bpartnerId.getRepoId())
				.bpartnerValue(bpartnerValue)
				.bpartnerName(bpartnerName)
				.bpartnerLocationId(BPartnerLocationId.toRepoId(bpartnerLocationId))
				.bpartnerLocationName(bPartnerLocationName)
				.warehouseName(warehouseName)
				.deliveryVia(deliveryVia)
				.shipper(shipper)
				.displayed(isDisplayed)
				.key(key)
				//
				.warehouseDestId(WarehouseId.toRepoId(warehouseDestId))
				.warehouseDestName(warehouseDestName)
				.productId(productId)
				.productName(productName)
				.preparationDate(TimeUtil.asTimestamp(item.getPreparationDate()))
				.deliveryDate(TimeUtil.asTimestamp(deliveryDate))
				//
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
				_packageableItemsAll = packagingDAO.retrievePackableLines(query);
			}

			_requeryNeeded = false;
		}

		return _packageableItemsAll;
	}

	private List<Packageable> _packageableItemsAll = null;

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
		final Set<TableRowKey> selectionNew = getSelectedTableRowKeys();
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

		final boolean displayNonItems = Services.get(IPackagingBL.class).isDisplayNonItemsEnabled(Env.getCtx());

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
			if (displayNonItems || row.isDisplayed())
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
