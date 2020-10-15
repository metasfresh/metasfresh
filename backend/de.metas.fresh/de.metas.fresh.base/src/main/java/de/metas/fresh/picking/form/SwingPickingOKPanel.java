/**
 *
 */
package de.metas.fresh.picking.form;

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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;

import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.ui.api.IWindowBL;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.apps.AEnv;
import org.compiere.apps.form.FormFrame;
import org.compiere.minigrid.IMiniTable;
import org.compiere.util.KeyNamePair;
import org.slf4j.Logger;

import de.metas.adempiere.form.IClientUI;
import de.metas.adempiere.form.terminal.IConfirmPanel;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.ITerminalScrollPane;
import de.metas.adempiere.form.terminal.ITerminalScrollPane.ScrollPolicy;
import de.metas.adempiere.form.terminal.TerminalException;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.context.ITerminalContextReferences;
import de.metas.adempiere.form.terminal.swing.TerminalSubPanel;
import de.metas.bpartner.BPartnerId;
import de.metas.fresh.picking.PackingDetailsModel;
import de.metas.fresh.picking.form.SwingPickingTerminalPanel.ResetFilters;
import de.metas.handlingunits.client.terminal.ddorder.form.DDOrderHUSelectForm;
import de.metas.inoutcandidate.api.IShipmentScheduleUpdater;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.inoutcandidate.api.ShipmentScheduleUpdateInvalidRequest;
import de.metas.logging.LogManager;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.PInstanceId;
import de.metas.util.Services;
import lombok.NonNull;
import net.miginfocom.swing.MigLayout;

/**
 * Picking First Window Panel, where basically the shipment schedules that shall be picked can be selected for the next window.
 *
 * @author cg
 *
 */
public class SwingPickingOKPanel extends TerminalSubPanel
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

	/**
	 * AD_PInstance_ID to be used in shipment schedules locking/unlocking
	 */
	private PInstanceId _pinstanceId;

	private boolean disposing = false;
	private boolean disposed = false;

	/**
	 * Used to be able to dispose components that are created while the "packing" terminal window is open.
	 *
	 * @task https://github.com/metasfresh/metasfresh/issues/1911
	 */
	private ITerminalContextReferences packageTerminalRefs;

	private ITerminalScrollPane linesContainer;
	private IConfirmPanel confirmPanel;
	private SwingPackingTerminal packageTerminal = null;
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

	private FreshPackingMd model;

	public SwingPickingOKPanel(final SwingPickingTerminalPanel pickingTerminalPanel)
	{
		super(pickingTerminalPanel);
	}

	private SwingPickingTerminalPanel getPickingTerminalPanel()
	{
		return SwingPickingTerminalPanel.cast(getTerminalBasePanel());
	}

	@Override
	protected void init()
	{
		final SpecialTerminalTable lines = createTerminalTable();

		model = new FreshPackingMd();
		model.setLines(lines);
		model.setTableRowKeysComparator(new PackingMdLinesComparator(model));

		initComponents();
		initLayout();
	}

	private void initComponents()
	{
		final ITerminalFactory terminalFactory = getTerminalFactory();
		confirmPanel = terminalFactory.createConfirmPanel(true, SwingPickingTerminalConstants.getButtonSize());
		confirmPanel.addButton(ACTION_QuickInv); // task 05582: add button to open window "Eigenverbrauch (metas)"
		confirmPanel.addButton(ACTION_ShipperTransportation); // task fresh_05763: add button to open window "Speditionsauftrag"
		confirmPanel.addButton(ACTION_DDOrder); // task fresh_06982: add button to open window "Bereitstellung (POS)"
		confirmPanel.addListener(this::onConfirmPanelAction);
	}

	private SpecialTerminalTable createTerminalTable()
	{
		final SpecialTerminalTable lines = new SpecialTerminalTable(getTerminalContext());
		return lines;
	}

	private void initLayout()
	{
		linesContainer = getTerminalFactory().createScrollPane(getModel().getLines());
		linesContainer.setHorizontalScrollBarPolicy(ScrollPolicy.WHEN_NEEDED);
		add(linesContainer, "dock north, spanx, growy, pushy, h 70%::");

		getUI().setLayout(new MigLayout("ins 0 0", "[fill][grow]", "[nogrid]unrel[||]"));
		//
		add(confirmPanel, "dock south");
	}

	public FormFrame getPickingFrame()
	{
		final SwingPickingTerminalPanel pickingTerminalPanel = getPickingTerminalPanel();
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

	private FreshPackingMd getModel()
	{
		return model;
	}

	private void toggleGroupByWarehouseDest()
	{
		getModel().toggleGroupByWarehouseDest();
		recreateLines();
	}

	private void toggleDisplayTodayEntriesOnly()
	{
		getModel().toggleDisplayTodayEntriesOnly();
		refreshLines();
	}

	private void updateLinesFromModel()
	{
		final FreshPackingMd model = getModel();
		final ITableRowSearchSelectionMatcher matcher = model.getTableRowSearchSelectionMatcher();

		final SpecialTerminalTable lines = model.getLines();

		final Set<TableRowKey> selectedTableRowKeys = model.getSelectedTableRowKeys();

		int rowIdx = 0;
		final boolean loadingOld = lines.setLoading(true);
		try
		{
			final List<TableRow> tableRows = model.getAggregatedTableRows();

			lines.setRowCount(tableRows.size());
			for (final TableRow currentRow : tableRows)
			{
				lines.updateRow(rowIdx, currentRow, matcher);

				// prepare next
				rowIdx++;
			}
			lines.setRowCount(rowIdx);
			model.setSelectedTableRowKeys(selectedTableRowKeys);
			lines.autoSize();
		}
		finally
		{
			lines.setLoading(loadingOld);
		}
	}

	public void addLinesSelectionChangedListener(@NonNull final Runnable listener)
	{
		getModel().getLines().addPropertyChangeListener(IMiniTable.PROPERTY_SelectionChanged, evt -> listener.run());
	}

	private final void recreateLines()
	{
		final SpecialTerminalTable lines = createTerminalTable();

		final FreshPackingMd model = getModel();
		model.setLines(lines);
		refreshLines();

		linesContainer.setViewport(lines);
	}

	private final void packSelectedLines()
	{
		final RowIndexes rows = getSelectedRowsIndexes();
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
			packLines(shipmentScheduleIds);
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
			final FreshPackingMd model = getModel();
			model.setRequeryNeeded();

			final SwingPickingTerminalPanel parent = getPickingTerminalPanel();
			parent.refreshLines(ResetFilters.IfNoResult);
		}

		setEnabled(true);
	}

	/**
	 * Unlock all shipment schedules
	 */
	private final void unlockShipmentSchedules()
	{
		trxManager.runInNewTrx(this::updateShipmentSchedulesInTrx);
	}

	private void updateShipmentSchedulesInTrx()
	{
		shipmentScheduleUpdater.updateShipmentSchedules(ShipmentScheduleUpdateInvalidRequest.builder()
				.ctx(getCtx())
				.selectionId(getADPInstanceId())
				.build());
	}

	private final synchronized PInstanceId getADPInstanceId()
	{
		PInstanceId pinstanceId = _pinstanceId;
		if (pinstanceId == null)
		{
			_pinstanceId = pinstanceId = Services.get(IADPInstanceDAO.class).createSelectionId();
		}
		return pinstanceId;
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
			final SwingPickingTerminalPanel parent = getPickingTerminalPanel();
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

	public void setDeliveryDate(final LocalDate deliveryDate)
	{
		getModel().setDeliveryDate(deliveryDate);
	}

	public String getLabelData()
	{
		final StringBuilder data = new StringBuilder();
		data.append("<html><font align='left'>");

		final FreshPackingMd model = getModel();
		final Collection<TableRow> selectedRows = new ArrayList<>();
		for (final int recordId : model.getSelectedRecordIds())
		{
			final int uniqueId = recordId;
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

	private RowIndexes getSelectedRowsIndexes()
	{
		return getModel().getSelectedRowIndexes();
	}

	public void logout()
	{
		getPickingTerminalPanel().logout();
	}

	void refreshLines()
	{
		final FreshPackingMd model = getModel();
		try
		{
			model.reload();
		}
		catch (final Exception ex)
		{
			displayError(ex);
		}

		updateLinesFromModel();
	}

	private void packLines(final Set<ShipmentScheduleId> shipmentScheduleIds)
	{
		//
		// Cleanup old Package Terminal (if any)
		{
			final SwingPackingTerminal packageTerminalOld = packageTerminal;
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
		final ITerminalFactory factory = getTerminalFactory();
		factory.showWarning(this, "Error", new TerminalException(e));
	}

	private void onConfirmPanelAction(final PropertyChangeEvent evt)
	{
		if (!IConfirmPanel.PROP_Action.equals(evt.getPropertyName()))
		{
			return;
		}

		final String action = String.valueOf(evt.getNewValue());

		if (IConfirmPanel.ACTION_OK.equals(action))
		{
			packSelectedLines();
		}
		else if (IConfirmPanel.ACTION_Cancel.equals(action))
		{
			getPickingFrame().dispose();
		}
		else if (ACTION_Switch.equals(action))
		{
			toggleGroupByWarehouseDest();
		}
		else if (ACTION_Today.equals(action))
		{
			toggleDisplayTodayEntriesOnly();
		}
		else if (ACTION_QuickInv.equals(action))
		{
			// FIXME find a better solution..maybe by introducing column AD_Window.InternalName? 540205 is the AD_Window_ID of the "Eigenverbrauch (metas)" window
			openDynamicWindow(AdWindowId.ofRepoId(540205));
		}
		else if (ACTION_ShipperTransportation.equals(action))
		{
			// FIXME find a better solution..maybe by introducing column AD_Window.InternalName?
			openDynamicWindow(AdWindowId.ofRepoId(540020));
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
	 * @param adWindowId
	 */
	private void openDynamicWindow(final AdWindowId adWindowId)
	{
		final boolean success = Services.get(IWindowBL.class).openWindow(adWindowId);
		if (!success)
		{
			final int windowNo = getPickingTerminalPanel().getTerminalContext().getWindowNo();
			Services.get(IClientUI.class).error(windowNo, MSG_ERRORS);
		}
	}

	public int getRowsCount()
	{
		return getModel().getRowsCount();
	}

	public ITableRowSearchSelectionMatcher getTableRowSearchSelectionMatcher()
	{
		return getModel().getTableRowSearchSelectionMatcher();
	}

	public void setTableRowSearchSelectionMatcher(final ITableRowSearchSelectionMatcher matcher)
	{
		getModel().setTableRowSearchSelectionMatcher(matcher);
	}

	public void setSelectedTableRowKeys(final Collection<TableRowKey> tableRowKeys)
	{
		getModel().setSelectedTableRowKeys(tableRowKeys);
	}
}
