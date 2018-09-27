/**
 *
 */
package de.metas.fresh.picking.form.swing;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.ui.api.IWindowBL;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.apps.AEnv;
import org.compiere.apps.form.FormFrame;
import org.compiere.minigrid.IDColumn;
import org.compiere.minigrid.IMiniTable;
import org.compiere.minigrid.MiniTable;
import org.compiere.model.I_C_BPartner;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.TrxRunnable;
import org.slf4j.Logger;

import de.metas.adempiere.form.IClientUI;
import de.metas.adempiere.form.terminal.IComponent;
import de.metas.adempiere.form.terminal.IConfirmPanel;
import de.metas.adempiere.form.terminal.IDisposable;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.ITerminalTable;
import de.metas.adempiere.form.terminal.TerminalException;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.context.ITerminalContextReferences;
import de.metas.bpartner.BPartnerId;
import de.metas.fresh.picking.PackingDetailsModel;
import de.metas.fresh.picking.form.FreshPackingMd;
import de.metas.fresh.picking.form.FreshPackingMdLinesComparator;
import de.metas.fresh.picking.form.SwingPackingTerminal;
import de.metas.handlingunits.client.terminal.ddorder.form.DDOrderHUSelectForm;
import de.metas.i18n.IMsgBL;
import de.metas.inoutcandidate.api.IShipmentScheduleUpdater;
import de.metas.inoutcandidate.api.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.logging.LogManager;
import de.metas.picking.legacy.form.ITableRowSearchSelectionMatcher;
import de.metas.picking.legacy.form.PackingMd;
import de.metas.picking.legacy.form.RowIndexes;
import de.metas.picking.legacy.form.TableRow;
import de.metas.picking.legacy.form.TableRowKey;
import de.metas.picking.terminal.IPickingTerminalPanel;
import de.metas.picking.terminal.IPickingTerminalPanel.ResetFilters;
import de.metas.picking.terminal.Utils;
import de.metas.picking.terminal.form.swing.IPackingTerminal;
import de.metas.process.IADPInstanceDAO;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * Picking First Window Panel, where basically the shipment schedules that shall be picked can be selected for the next window.
 *
 * @author cg
 *
 */
public class SwingPickingOKPanel implements IDisposable
{
	private static final Logger logger = LogManager.getLogger(SwingPickingOKPanel.class);
	private final IShipmentScheduleUpdater shipmentScheduleUpdater = Services.get(IShipmentScheduleUpdater.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	/**
	 * Existing AD_Message (created in 2003) with EntityType='D'. German message text is "Fehler".
	 */
	// task 05582
	private static final String MSG_ERRORS = "Errors";

	// task 05582
	private static final String ACTION_QuickInv = "FreshSwingPickingOKPanel_QuickInv";

	// task fresh_05763
	private static final String ACTION_ShipperTransportation = "FreshSwingPickingOKPanel_ShipperTransportation";

	// task fresh_06982
	private static final String ACTION_DDOrder = "FreshSwingPickingOKPanel_DDOrder";

	static final String ACTION_Switch = "Switch";
	static final String ACTION_Today = "Today";

	public static final String COLUMNNAME_ROWID = "ROWID";
	public static final String COLUMNNAME_M_Product_ID = I_M_ShipmentSchedule.COLUMNNAME_M_Product_ID;
	public static final String COLUMNNAME_Qty = "Qty";
	public static final String COLUMNNAME_DeliveryDate = I_M_ShipmentSchedule.COLUMNNAME_DeliveryDate;
	public static final String COLUMNNAME_PreparationDate = de.metas.tourplanning.model.I_M_ShipmentSchedule.COLUMNNAME_PreparationDate;
	public static final String COLUMNNAME_BPValue = I_C_BPartner.COLUMNNAME_Value;
	public static final String COLUMNNAME_C_BPartner_Location_ID = I_M_ShipmentSchedule.COLUMNNAME_C_BPartner_Location_ID;
	public static final String COLUMNNAME_M_Warehouse_Dest_ID = I_M_ShipmentSchedule.COLUMNNAME_M_Warehouse_Dest_ID;
	/**
	 * Barcode matching type column
	 */
	public static final String COLUMNNAME_MatchingType = "B";

	private final Map<String, Integer> columnName2index = new HashMap<>();

	private final Properties ctx;

	/**
	 * AD_PInstance_ID to be used in shipment schedules locking/unlocking
	 */
	private final int adPInstanceId;

	private boolean disposing = false;
	private boolean disposed = false;

	/**
	 * Used to be able to dispose components that are created while the "packing" terminal window is open.
	 *
	 * @task https://github.com/metasfresh/metasfresh/issues/1911
	 */
	private ITerminalContextReferences packageTerminalRefs;

	/**
	 * parent panel
	 */
	private final SwingPickingTerminalPanel pickingTerminalPanel;
	private final SwingPickingOKPanel_LinesPanel pickingPanel;
	private final IConfirmPanel confirmPanel;
	private IPackingTerminal packageTerminal = null; // instantiated by validateSuggestion()
	private final WindowListener packageTerminalWindowListener = new WindowAdapter()
	{
		@Override
		public void windowClosed(final WindowEvent e)
		{
			// NOTE: in some environments we had complains that the lines are not refreshed.
			// Even though i could not reproduce in my environment,
			// i've decided to postone the refreshing right after other evens are processed.
			SwingUtilities.invokeLater(() -> onPackageTerminalClosed());
		}
	};
	private PackingMd model;

	public SwingPickingOKPanel(final SwingPickingTerminalPanel pickingTerminalPanel)
	{
		ctx = Env.getCtx();
		adPInstanceId = Services.get(IADPInstanceDAO.class).createAD_PInstance_ID(ctx);
		this.pickingTerminalPanel = pickingTerminalPanel;
		this.pickingPanel = new SwingPickingOKPanel_LinesPanel(this, pickingTerminalPanel);

		this.pickingTerminalPanel.getTerminalContext().addToDisposableComponents(this);

		final ITerminalFactory terminalFactory = pickingTerminalPanel.getTerminalFactory();
		confirmPanel = terminalFactory.createConfirmPanel(true, Utils.getButtonSize());
		confirmPanel.addButton(ACTION_QuickInv); // task 05582: add button to open window "Eigenverbrauch (metas)"
		confirmPanel.addButton(ACTION_ShipperTransportation); // task fresh_05763: add button to open window "Speditionsauftrag"
		confirmPanel.addButton(ACTION_DDOrder); // task fresh_06982: add button to open window "Bereitstellung (POS)"
		confirmPanel.addListener(new FreshPropertyChangeListener());
	}

	public final ITerminalContext getTerminalContext()
	{
		return getPickingTerminalPanel().getTerminalContext();
	}

	IComponent getComponent()
	{
		return pickingPanel;
	}

	IConfirmPanel getConfirmPanel()
	{
		return confirmPanel;
	}

	SwingPickingTerminalPanel getPickingTerminalPanel()
	{
		return pickingTerminalPanel;
	}

	public FormFrame getPickingFrame()
	{
		if (pickingTerminalPanel != null)
		{
			return pickingTerminalPanel.getComponent();
		}
		return null;
	}

	FormFrame getPackageFrame()
	{
		if (packageTerminal != null)
		{
			return packageTerminal.getFrame();
		}
		return null;
	}

	void setModel(final PackingMd model)
	{
		this.model = model;
	}

	public PackingMd getModel()
	{
		return model;
	}

	void configureMiniTable(final IMiniTable miniTable)
	{
		columnName2index.clear();
		miniTable.setMultiSelection(false);
		setSelectionModel(miniTable);
		//
		// Create Columns
		addColumn(miniTable, COLUMNNAME_ROWID);

		addColumn(miniTable, COLUMNNAME_M_Product_ID);
		addColumn(miniTable, COLUMNNAME_Qty);
		addColumn(miniTable, COLUMNNAME_DeliveryDate); // 01676
		addColumn(miniTable, COLUMNNAME_PreparationDate); // 01676

		addColumn(miniTable, COLUMNNAME_MatchingType);

		//
		// Setup columns
		// NOTE: we need to do this in two phases (addColumns and then setup)
		// because there is a fucked up thing regarding org.compiere.minigrid.MiniTable.addColumn(String)
		// ... see the comment from there
		setupColumnIfExists(miniTable, COLUMNNAME_ROWID, DisplayType.ID, IDColumn.class);
		// ((MiniTable)miniTable).setColorColumn(idColumnIndex); // not needed
		setupColumnIfExists(miniTable, COLUMNNAME_M_Product_ID, DisplayType.String, String.class);
		setupColumnIfExists(miniTable, COLUMNNAME_Qty, DisplayType.Quantity, BigDecimal.class);
		setupColumnIfExists(miniTable, COLUMNNAME_DeliveryDate, DisplayType.Date, Timestamp.class); // 01676
		setupColumnIfExists(miniTable, COLUMNNAME_PreparationDate, DisplayType.Time, Timestamp.class);
		setupColumnIfExists(miniTable, COLUMNNAME_BPValue, DisplayType.String, String.class);
		setupColumnIfExists(miniTable, COLUMNNAME_C_BPartner_Location_ID, DisplayType.String, String.class);
		setupColumnIfExists(miniTable, COLUMNNAME_M_Warehouse_Dest_ID, DisplayType.String, String.class);
		setupColumnIfExists(miniTable, COLUMNNAME_MatchingType, DisplayType.String, String.class);

		miniTable.autoSize();
		miniTable.setColorProvider(new SwingPickingOKPanel_TableColorProvider(this));
	}

	private void updateMiniTable()
	{
		final IMiniTable miniTable = getMiniTable();

		final PackingMd model = getModel();
		final Set<TableRowKey> selectedTableRowKeys = model.getSelectedTableRowKeys();

		int rowIdx = 0;
		final boolean loadingOld = miniTable.setLoading(true);
		try
		{
			final List<TableRow> tableRows = model.getAggregatedTableRows();

			miniTable.setRowCount(tableRows.size());
			for (final TableRow currentRow : tableRows)
			{
				updateMiniTableRow(miniTable, rowIdx, currentRow);

				// prepare next
				rowIdx++;
			}
			miniTable.setRowCount(rowIdx);
			model.setSelectedTableRowKeys(selectedTableRowKeys);
			miniTable.autoSize();
		}
		finally
		{
			miniTable.setLoading(loadingOld);
		}
	}

	final IMiniTable getMiniTable()
	{
		final PackingMd model = getModel();
		final IMiniTable miniTable = model.getMiniTable();
		return miniTable;
	}

	protected final void updateMiniTableRow(final IMiniTable miniTable, final int rowIdx, final TableRow currentRow)
	{
		final TableRowKey key = currentRow.getKey();

		// set values
		setValueAt(miniTable, COLUMNNAME_ROWID, rowIdx, new IDColumn(key.hashCode())); // ID
		setValueAt(miniTable, COLUMNNAME_M_Product_ID, rowIdx, currentRow.getProductName());
		setValueAt(miniTable, COLUMNNAME_Qty, rowIdx, currentRow.getQtyToDeliver());
		setValueAt(miniTable, COLUMNNAME_DeliveryDate, rowIdx, currentRow.getDeliveryDate());
		setValueAt(miniTable, COLUMNNAME_PreparationDate, rowIdx, currentRow.getPreparationDate());
		setValueAt(miniTable, COLUMNNAME_BPValue, rowIdx, currentRow.getBpartnerValue()); // BP Value
		setValueAt(miniTable, COLUMNNAME_C_BPartner_Location_ID, rowIdx, currentRow.getBpartnerLocationName()); // BP Location Name
		setValueAt(miniTable, COLUMNNAME_M_Warehouse_Dest_ID, rowIdx, currentRow.getWarehouseDestName());

		final String matchingType;
		final PackingMd model = getModel();
		final ITableRowSearchSelectionMatcher matcher = model.getTableRowSearchSelectionMatcher();
		if (matcher != null && matcher.match(key))
		{
			matchingType = matcher.getName();
		}
		else
		{
			matchingType = null;
		}
		setValueAt(miniTable, COLUMNNAME_MatchingType, rowIdx, matchingType);
	}

	private void setValueAt(final IMiniTable miniTable, final String columnName, final int rowIndex, final Object value)
	{
		final Integer columnIndex = columnName2index.get(columnName);
		if (columnIndex == null)
		{
			// If column is missing, then we just skip setting the value
			return;
		}

		miniTable.setValueAt(value, rowIndex, columnIndex);
	}

	private int addColumn(final IMiniTable miniTable, final String columnName)
	{
		miniTable.addColumn(columnName);

		final int columnIndex = miniTable.getColumnCount() - 1;
		columnName2index.put(columnName, columnIndex);

		return columnIndex;
	}

	private void setupColumnIfExists(final IMiniTable miniTable,
			final String columnName,
			final int displayType,
			final Class<?> columnClass)
	{
		final Integer columnIndex = columnName2index.get(columnName);
		if (columnIndex == null)
		{
			// column does not exist, skip it
			return;
		}

		final String columnNameTrl;
		if (IDColumn.class.equals(columnClass))
		{
			columnNameTrl = " ";
		}
		else if (Services.get(IDeveloperModeBL.class).isEnabled())
		{
			columnNameTrl = columnName;
		}
		else if (columnName.length() <= 2)
		{
			// don't translate short column names
			columnNameTrl = columnName;
		}
		else
		{
			columnNameTrl = Services.get(IMsgBL.class).translate(Env.getCtx(), columnName);
		}

		final boolean readonly = true;
		miniTable.setColumnClass(columnIndex, displayType, columnClass, readonly, columnNameTrl);
	}

	FreshPackingMd createPackingModel(final ITerminalTable lines)
	{
		final ITerminalContext terminalContext = getTerminalContext();
		final FreshPackingMd model = new FreshPackingMd(terminalContext);
		model.setMiniTable(lines);

		model.setTableRowKeysComparator(new FreshPackingMdLinesComparator(model));

		return model;
	}

	final void createPackingDetails()
	{
		final RowIndexes rows = getSelectedRows();
		if (rows.isEmpty())
		{
			logger.warn("createPackingDetails: No rows");
			return;
		}

		if (getWarehouseId() == null)
		{
			throw new AdempiereException("@NotFound@ @M_Warehouse_ID@");
		}

		final Set<ShipmentScheduleId> shipmentScheduleIds = getShipmentScheduleIds(rows);
		if (shipmentScheduleIds.isEmpty())
		{
			throw new AdempiereException("@NotFound@ @M_ShipmentSchedule_ID@");
		}

		try
		{
			executePacking(shipmentScheduleIds);
		}
		catch (final Throwable ex)
		{
			unlockShipmentSchedules();
			throw AdempiereException.wrapIfNeeded(ex);
		}

		// related to https://github.com/metasfresh/metasfresh/issues/456
		// invokeProcess(detailsModel);
	}

	private Set<ShipmentScheduleId> getShipmentScheduleIds(@NonNull final RowIndexes rows)
	{
		return getModel().getScheduleIdsForRow(rows);
	}

	private void setSelectionModel(final IMiniTable miniTable)
	{
		if (miniTable instanceof MiniTable)
		{
			final MiniTable mTable = (MiniTable)miniTable;
			mTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		}
	}

	/**
	 * @return true if window was disposed or it's disposing
	 */
	@Override
	public boolean isDisposed()
	{
		return disposed || disposing;
	}

	@Override
	public void dispose()
	{
		if (disposing || disposed)
		{
			return;
		}
		disposing = true;
		try
		{
			if (packageTerminal != null)
			{
				packageTerminal.dispose(); // this is not an IDisposable, so we need to deal with it here
				onPackageTerminalClosed();
				packageTerminal = null;
			}
		}
		finally
		{
			disposing = false;
			disposed = true;
		}
		// pickingPanel.dispose(); // this is called above
		// pickingTerminalPanel.dispose(); // this is the callee
	}

	/**
	 * Called when Package Terminal was closed
	 */
	private final void onPackageTerminalClosed()
	{
		//
		// Cleanup current package terminal
		if (packageTerminal != null)
		{
			packageTerminal = null;
		}

		unlockShipmentSchedules(); // task 08153: make sure we unlock *and* update the scheds we did picking on

		getTerminalContext().deleteReferences(packageTerminalRefs); // gh #1911

		//
		// If this window was already disposed (i.e. user closed all windows all together, e.g. on logout) then do nothing
		if (isDisposed())
		{
			return;
		}

		//
		// Put Picking Window (first window) on front and refresh the current lines

		//
		// Ask parent to refresh lines
		{
			final PackingMd model = getModel();
			model.setRequeryNeeded();

			final IPickingTerminalPanel parent = getPickingTerminalPanel();
			parent.refreshLines(ResetFilters.IfNoResult);
		}

		setEnabled(true);
	}

	/**
	 * Unlock all shipment schedules
	 */
	private final void unlockShipmentSchedules()
	{
		final int adUserId = Env.getAD_User_ID(ctx);

		trxManager.run((TrxRunnable)localTrxName -> {
			final boolean updateOnlyLocked = true;

			shipmentScheduleUpdater.updateShipmentSchedule(
					ctx,
					adUserId,
					adPInstanceId,
					updateOnlyLocked,
					localTrxName);
		});
	}

	private void setEnabled(final boolean enabled)
	{
		if (isDisposed())
		{
			return;
		}

		final FormFrame pickingFrame = getPickingFrame();
		if (pickingFrame == null)
		{
			return;
		}

		pickingFrame.setEnabled(enabled);
		if (enabled)
		{
			pickingFrame.toFront();

			// Ask parent to give focus to right editing component
			final IPickingTerminalPanel parent = getPickingTerminalPanel();
			parent.requestFocus();
		}
		else
		{
			pickingFrame.toBack();
		}
	}

	WarehouseId getWarehouseId()
	{
		return getModel().getWarehouseId();
	}

	public void setWarehouseId(final WarehouseId warehouseId)
	{
		getModel().setWarehouseId(warehouseId);
	}

	Set<KeyNamePair> getSelectedBPartners()
	{
		return getModel().getSelectedBPartners();
	}

	public void setBPartnerIds(final Set<BPartnerId> bpartnerIds)
	{
		getModel().setBPartnerIds(bpartnerIds);
	}

	Set<LocalDate> getSelectedDeliveryDates()
	{
		return getModel().getSelectedDeliveryDates();
	}

	public void setDeliveryDate(LocalDate deliveryDate)
	{
		getModel().setDeliveryDate(deliveryDate);
	}

	public String getLabelData()
	{
		final StringBuilder data = new StringBuilder();
		data.append("<html><font align='left'>");
		final List<IDColumn> ids = new ArrayList<>();
		final RowIndexes rows = getSelectedRows();
		for (final int row : rows.toIntSet())
		{
			final IDColumn id = (IDColumn)getMiniTable().getValueAt(row, 0);
			ids.add(id);
		}

		final PackingMd model = getModel();
		final Collection<TableRow> selectedRows = new ArrayList<>();
		for (final IDColumn id : ids)
		{
			final int uniqueId = id.getRecord_ID();
			final Collection<TableRow> tableRowsForUniqueId = model.getTableRowsForUniqueId(uniqueId);
			selectedRows.addAll(tableRowsForUniqueId);
		}

		final HashMap<String, BigDecimal> products = new HashMap<>();
		for (final TableRow currentRow : selectedRows)
		{
			final String productName = currentRow.getProductName().trim();
			BigDecimal qty = products.get(productName);
			if (qty == null)
			{
				products.put(productName, currentRow.getQtyToDeliver());
			}
			else
			{
				qty = qty.add(currentRow.getQtyToDeliver());
				products.remove(productName);
				products.put(productName, qty);
			}
		}

		for (final Map.Entry<String, BigDecimal> entry : products.entrySet())
		{
			final String name = entry.getKey();
			BigDecimal totalQty = entry.getValue();
			//
			if (totalQty.scale() != 0)
			{
				totalQty = totalQty.setScale(2, BigDecimal.ROUND_HALF_UP);
			}

			data.append(totalQty)
					.append(" x ")
					.append(name);

			if (products.size() > 1)
			{
				data.append("<br>");
			}
		}

		data.append("</font></html>");
		return data.toString();
	}

	private RowIndexes getSelectedRows()
	{
		final IMiniTable miniTable = getModel().getMiniTable();
		return RowIndexes.ofArray(miniTable.getSelectedRows());
	}

	public void logout()
	{
		final IPickingTerminalPanel pickingTerminalPanel = getPickingTerminalPanel();
		pickingTerminalPanel.logout();
	}

	void refresh()
	{
		executeQuery();
	}

	private final void executeQuery()
	{
		final PackingMd model = getModel();
		try
		{
			model.reload();
		}
		catch (final Exception ex)
		{
			displayError(ex);
		}

		updateMiniTable();
	}

	private void executePacking(final Set<ShipmentScheduleId> shipmentScheduleIds)
	{
		//
		// Cleanup old Package Terminal (if any)
		{
			final IPackingTerminal packageTerminalOld = packageTerminal;
			if (packageTerminalOld != null && packageTerminalOld.getFrame() != null)
			{
				packageTerminalOld.getFrame().removeWindowListener(packageTerminalWindowListener);
				getTerminalContext().deleteReferences(packageTerminalRefs); // gh #1911
			}
			packageTerminal = null;

			packageTerminalRefs = getTerminalContext().newReferences(); // gh #1911
		}

		//
		// Create and setup new Package Terminal
		{
			final PackingDetailsModel packingDetailsModel = new PackingDetailsModel(getTerminalContext(), shipmentScheduleIds);
			final SwingPackingTerminal packageTerminalNew = new SwingPackingTerminal(this, packingDetailsModel);
			final ITerminalContext terminalContext = getTerminalContext();
			final FormFrame packageTerminalNewFrame = new FormFrame();
			packageTerminalNew.init(terminalContext.getWindowNo(), packageTerminalNewFrame);
			packageTerminalNewFrame.addWindowListener(packageTerminalWindowListener);
			packageTerminal = packageTerminalNew;

			AEnv.showMaximized(packageTerminalNewFrame);
		}

		//
		// Disable this window
		// NOTE: it will be enabled again onPackageTerminalClosed()
		setEnabled(false);
	}

	private void displayError(final Throwable e)
	{
		final ITerminalFactory factory = pickingPanel.getTerminalFactory();
		factory.showWarning(pickingPanel, "Error", new TerminalException(e));
	}

	//
	//
	//
	//
	//

	private class FreshPropertyChangeListener implements PropertyChangeListener
	{
		@Override
		public void propertyChange(final PropertyChangeEvent evt)
		{
			if (!IConfirmPanel.PROP_Action.equals(evt.getPropertyName()))
			{
				return;
			}

			final String action = String.valueOf(evt.getNewValue());
			if (ACTION_QuickInv.equals(action))
			{
				// FIXME find a better solution..maybe by introducing column AD_Window.InternalName? 540205 is the AD_Window_ID of the "Eigenverbrauch (metas)" window
				openDynamicWindow(540205);
			}
			else if (ACTION_ShipperTransportation.equals(action))
			{
				// FIXME find a better solution..maybe by introducing column AD_Window.InternalName?
				openDynamicWindow(540020);
			}
			else if (ACTION_DDOrder.equals(action))
			{
				//
				// Simulate DDOrder HU editor callback
				final ITerminalContext terminalContext = getTerminalContext();

				final DDOrderHUSelectForm form = new DDOrderHUSelectForm();
				form.init(terminalContext.getWindowNo(), new FormFrame());

				//
				// Force Target WarehouseId the one which is selected in Kommissionier Terminal
				{
					final SwingPickingTerminalPanel basePanel = getPickingTerminalPanel();
					final WarehouseId selectedWarehouseId = basePanel.getSelectedWarehouseId();
					form.setWarehouseOverrideId(selectedWarehouseId);
				}

				form.show();
			}
		}

		/**
		 * FIXME find a better solution..maybe by introducing column AD_Window.InternalName?<br>
		 * <br>
		 * Open dynamic window
		 *
		 * @param AD_Window_ID
		 */
		private void openDynamicWindow(final int AD_Window_ID)
		{
			final boolean success = Services.get(IWindowBL.class).openWindow(AD_Window_ID);
			if (!success)
			{
				final int windowNo = getPickingTerminalPanel().getTerminalContext().getWindowNo();
				Services.get(IClientUI.class).error(windowNo, MSG_ERRORS);
			}
		}
	}
}
