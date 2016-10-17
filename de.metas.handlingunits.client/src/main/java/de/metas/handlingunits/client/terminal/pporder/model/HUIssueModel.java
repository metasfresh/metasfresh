/**
 *
 */
package de.metas.handlingunits.client.terminal.pporder.model;

/*
 * #%L
 * de.metas.handlingunits.client
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

import java.beans.PropertyChangeListener;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.beans.WeakPropertyChangeSupport;
import org.adempiere.util.time.SystemTime;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.compiere.process.DocAction;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;

import de.metas.adempiere.form.terminal.IDisposable;
import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.TerminalKeyListenerAdapter;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.context.ITerminalContextReferences;
import de.metas.adempiere.service.IPOSAccessBL;
import de.metas.document.engine.IDocActionBL;
import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.client.terminal.editor.model.IHUKey;
import de.metas.handlingunits.client.terminal.editor.model.IHUKeyFactory;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUEditorModel;
import de.metas.handlingunits.client.terminal.lutuconfig.model.CUKey;
import de.metas.handlingunits.client.terminal.pporder.api.IHUIssueFiltering;
import de.metas.handlingunits.client.terminal.pporder.receipt.model.HUPPOrderReceiptHUEditorModel;
import de.metas.handlingunits.client.terminal.pporder.receipt.model.HUPPOrderReceiptModel;
import de.metas.handlingunits.client.terminal.select.model.IHUEditorCallback;
import de.metas.handlingunits.client.terminal.select.model.WarehouseKey;
import de.metas.handlingunits.client.terminal.select.model.WarehouseKeyLayout;
import de.metas.handlingunits.document.impl.NullHUDocumentLineFinder;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.handlingunits.pporder.api.IHUPPOrderIssueProducer;

/**
 * Manufacturing Order Issue ViewModel
 *
 * @author cg
 *
 */
public class HUIssueModel implements IDisposable
{
	// Services
	private final transient IPOSAccessBL posAccessBL = Services.get(IPOSAccessBL.class);
	private final transient IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final transient IDocActionBL docActionBL = Services.get(IDocActionBL.class);
	private final transient IHUPPOrderBL huPPOrderBL = Services.get(IHUPPOrderBL.class);

	private final ITerminalContext _terminalContext;
	private final IHUIssueFiltering service;

	private final WarehouseKeyLayout warehouseKeyLayout;
	private final ManufacturingOrderKeyLayout manufacturingOrderKeyLayout;
	private final OrderBOMLineKeyLayout orderBOMLineKeyLayout;

	private I_M_Warehouse _selectedWarehouse = null;

	/**
	 * selected MO
	 */
	private I_PP_Order _selectedOrder = null;

	public static final String PROPERTY_ActionButtonsEnabled = "ActionButtonsEnabled";
	private boolean _actionButtonsEnabled = false;

	private final WeakPropertyChangeSupport pcs;

	private boolean disposed = false;

	public HUIssueModel(final ITerminalContext terminalContext)
	{
		super();

		Check.assumeNotNull(terminalContext, "terminalContext not null");
		_terminalContext = terminalContext;
		pcs = terminalContext.createPropertyChangeSupport(this);

		service = Services.get(IHUIssueFiltering.class);

		//
		//
		warehouseKeyLayout = new WarehouseKeyLayout(terminalContext);
		warehouseKeyLayout.getKeyLayoutSelectionModel().setAllowKeySelection(true);
		warehouseKeyLayout.addTerminalKeyListener(new TerminalKeyListenerAdapter()
		{
			@Override
			public void keyReturned(final ITerminalKey key)
			{
				final WarehouseKey warehouseKey = (WarehouseKey)key;
				onWarehouseKeyPressed(warehouseKey);
			}
		});

		//
		//
		manufacturingOrderKeyLayout = new ManufacturingOrderKeyLayout(terminalContext);
		manufacturingOrderKeyLayout.getKeyLayoutSelectionModel().setAllowKeySelection(true);
		manufacturingOrderKeyLayout.addTerminalKeyListener(new TerminalKeyListenerAdapter()
		{
			@Override
			public void keyReturned(final ITerminalKey key)
			{
				final ManufacturingOrderKey moKey = (ManufacturingOrderKey)key;
				onManufacturingOrderKeyPressed(moKey);
			}
		});

		//
		//
		orderBOMLineKeyLayout = new OrderBOMLineKeyLayout(terminalContext);
		orderBOMLineKeyLayout.getKeyLayoutSelectionModel().setAllowKeySelection(false); // we do not to select; are just for info

		//
		load();

		terminalContext.addToDisposableComponents(this);
	}

	/**
	 * Does nothing, only sets the disposed flag.
	 */
	@Override
	@OverridingMethodsMustInvokeSuper
	public void dispose()
	{
		disposed = true;
	}

	@Override
	public boolean isDisposed()
	{
		return disposed;
	}

	public final ITerminalContext getTerminalContext()
	{
		return _terminalContext;
	}

	private void load()
	{
		// 06472 : We take the warehouses from the filtering service and keep only the ones allowed in the POS profile.
		final List<I_M_Warehouse> warehouses = posAccessBL.filterWarehousesByProfile(getCtx(), service.retrieveWarehouse(getCtx()));
		warehouseKeyLayout.createAndSetKeysFromWarehouses(warehouses);
	}

	public void addPropertyChangeListener(final PropertyChangeListener listener)
	{
		pcs.removePropertyChangeListener(listener);
		pcs.addPropertyChangeListener(listener);
	}

	public void addPropertyChangeListener(final String propertyName, final PropertyChangeListener listener)
	{
		pcs.removePropertyChangeListener(propertyName, listener);
		pcs.addPropertyChangeListener(propertyName, listener);
	}

	public boolean isActionButtonsEnabled()
	{
		return _actionButtonsEnabled;
	}

	public void setActionButtonsEnabled(final boolean actionButtonsEnabled)
	{
		final boolean actionButtonsEnabledOld = _actionButtonsEnabled;
		_actionButtonsEnabled = actionButtonsEnabled;

		pcs.firePropertyChange(HUIssueModel.PROPERTY_ActionButtonsEnabled, actionButtonsEnabledOld, _actionButtonsEnabled);
	}

	private void onWarehouseKeyPressed(final WarehouseKey warehouseKey)
	{
		manufacturingOrderKeyLayout.getKeyLayoutSelectionModel().onKeySelected(null);
		orderBOMLineKeyLayout.getKeyLayoutSelectionModel().onKeySelected(null);

		clearSelected();

		_selectedWarehouse = warehouseKey.getM_Warehouse();

		// Update Key Layouts:
		// update orders
		loadManufacturingOrderKeyLayout();
		// update BOM Lines
		loadOrderBOMLineKeyLayout();
	}

	private void onManufacturingOrderKeyPressed(final ManufacturingOrderKey moKey)
	{
		final I_PP_Order order = moKey.getPP_Order();
		_selectedOrder = order;

		orderBOMLineKeyLayout.getKeyLayoutSelectionModel().onKeySelected(null);

		// update BOM Lines
		loadOrderBOMLineKeyLayout();

		setActionButtonsEnabled(moKey != null);
	}

	public I_M_Warehouse getSelectedWarehouse()
	{
		return _selectedWarehouse;
	}

	public I_M_Locator getSelectedLocator()
	{
		final I_M_Warehouse warehouse = getSelectedWarehouse();
		if (warehouse == null)
		{
			return null;
		}

		return warehouseBL.getDefaultLocator(warehouse);
	}

	public int getSelectedWarehouseId()
	{
		final I_M_Warehouse selectedWarehouse = getSelectedWarehouse();
		if (selectedWarehouse == null)
		{
			return -1;
		}
		return selectedWarehouse.getM_Warehouse_ID();
	}

	public final void loadManufacturingOrderKeyLayout()
	{
		final int warehouseId = getSelectedWarehouseId();
		final List<I_PP_Order> orders = service.getManufacturingOrders(getCtx(), warehouseId);
		manufacturingOrderKeyLayout.createAndSetKeysFromOrders(orders);
	}

	public final void loadOrderBOMLineKeyLayout()
	{
		final I_PP_Order order = getSelectedOrderOrNull();

		// Make sure the selected order is up2date
		// NOTE: in some cases we got here old values after a Receipt
		if (order != null)
		{
			InterfaceWrapperHelper.refresh(order);
		}

		final List<I_PP_Order_BOMLine> lines = service.getOrderBOMLines(order);
		orderBOMLineKeyLayout.createAndSetKeysFromBOMLines(lines);
	}

	private final void clearSelected()
	{
		// NOTE: we are not reseting the selected warehouse

		_selectedOrder = null;
	}

	public final Properties getCtx()
	{
		final ITerminalContext terminalContext = getTerminalContext();
		return terminalContext.getCtx();
	}

	public final WarehouseKeyLayout getWarehouseKeyLayout()
	{
		return warehouseKeyLayout;
	}

	public final ManufacturingOrderKeyLayout getManufacturingOrderKeyLayout()
	{
		return manufacturingOrderKeyLayout;
	}

	public final OrderBOMLineKeyLayout getOrderBOMLineKeyLayout()
	{
		return orderBOMLineKeyLayout;
	}

	public final I_PP_Order getSelectedOrder()
	{
		final I_PP_Order selectedOrder = getSelectedOrderOrNull();
		Check.assumeNotNull(selectedOrder, "selectedOrder not null");
		return selectedOrder;
	}

	public final I_PP_Order getSelectedOrderOrNull()
	{
		return _selectedOrder;
	}

	/**
	 * Creates Manufacturing Issue Cost Collectors (i.e. transfer all qty from given HUs to selected manufacturing order)
	 *
	 * @param hus HUs to issue
	 */
	private void createIssues(final Set<I_M_HU> hus)
	{
		final IHUPPOrderIssueProducer issueProducer = huPPOrderBL.createIssueProducer(getCtx());
		issueProducer.setMovementDate(getMovementDate());
		issueProducer.setTargetOrderBOMLines(orderBOMLineKeyLayout.getOrderBOMLines());

		issueProducer.createIssues(hus);
	}

	/**
	 * Gets document date to be used when creating Issue Cost Collectors.
	 *
	 * @return
	 */
	private final Timestamp getMovementDate()
	{
		return SystemTime.asDayTimestamp();
	}

	/**
	 * Issue HUs to selected manufacturing order.
	 *
	 * @param editorCallback callback which will provide which HUs to issue (see {@link IHUEditorCallback#editHUs(Object)})
	 */
	public void doIssueHUs(final IHUEditorCallback<HUEditorModel> editorCallback)
	{
		Check.assumeNotNull(editorCallback, "editorCallback not null");

		try (final ITerminalContextReferences references = getTerminalContext().newReferences())
		{
			final HUEditorModel editorModel = createIssueHUEditorModel();

			final boolean edited = editorCallback.editHUs(editorModel);

			if (edited)
			{
				final Set<I_M_HU> selectedHUs = editorModel.getSelectedHUs();
				createIssues(selectedHUs);
			}
		}

		// Reload everything
		loadManufacturingOrderKeyLayout();
		loadOrderBOMLineKeyLayout();
	}

	/**
	 * Creates the {@link HUEditorModel} which will provide to user which HUs he/she is able to issue to selected manufacturing order
	 *
	 * @return
	 */
	private HUEditorModel createIssueHUEditorModel()
	{
		final ITerminalContext terminalContext = getTerminalContext();

		final I_PP_Order ppOrder = getSelectedOrder();
		final List<I_PP_Order_BOMLine> ppOrderBOMLines = getPP_Order_BOMLines();
		final int selectedWarehouseId = getSelectedWarehouseId();

		//
		// Create a Root HU Key from HUs assigned to our documents
		final IHUQueryBuilder husQuery = service.getHUsForIssueQuery(ppOrder, ppOrderBOMLines, selectedWarehouseId);
		final IHUKeyFactory keyFactory = terminalContext.getService(IHUKeyFactory.class);
		final IHUKey rootHUKey = keyFactory.createRootKey(husQuery, NullHUDocumentLineFinder.instance);

		//
		// Create HU Editor
		final HUEditorModel editorModel = new HUEditorModel(terminalContext);
		editorModel.setRootHUKey(rootHUKey);

		// Because there is no huDocumentLineFinder, there is no point to check and update HU Allocations
		editorModel.setUpdateHUAllocationsOnSave(false);

		return editorModel;
	}

	public HUPPOrderReceiptModel createReceiptModel()
	{
		final ITerminalContext terminalContext = getTerminalContext();

		final I_PP_Order ppOrder = getSelectedOrder();
		final List<I_PP_Order_BOMLine> ppOrderBOMLines = getPP_Order_BOMLines();

		final HUPPOrderReceiptModel model = new HUPPOrderReceiptModel(terminalContext);

		model.load(ppOrder, ppOrderBOMLines);
		return model;
	}

	private List<I_PP_Order_BOMLine> getPP_Order_BOMLines()
	{
		final List<I_PP_Order_BOMLine> ppOrderBOMLines = getOrderBOMLineKeyLayout().getOrderBOMLines();
		return ppOrderBOMLines;
	}

	/**
	 * @param loadHUs true if we need the already existing HUs to be loaded, flase otherwise
	 * @return
	 */
	public HUPPOrderReceiptHUEditorModel createReceiptHUEditorModel(final CUKey cuKey, final boolean loadHUs)
	{
		final ITerminalContext terminalContext = getTerminalContext();

		final I_PP_Order ppOrder = getSelectedOrder();
		final List<I_PP_Order_BOMLine> ppOrderBOMLines = getPP_Order_BOMLines();

		final HUPPOrderReceiptHUEditorModel editorModel = new HUPPOrderReceiptHUEditorModel(terminalContext, ppOrder, ppOrderBOMLines, cuKey, loadHUs);

		return editorModel;
	}

	public void closeSelectedOrder()
	{
		final I_PP_Order selectedOrder = getSelectedOrder();

		docActionBL.processEx(selectedOrder, DocAction.ACTION_Close, DocAction.STATUS_Closed);

		// Afterwards, refresh orders.
		clearSelected();
		loadManufacturingOrderKeyLayout();
		loadOrderBOMLineKeyLayout();

	}

	/**
	 * Refresh the manufacturing and the bom slots after receipt
	 */
	public void refreshAfterReceipt()
	{
		loadManufacturingOrderKeyLayout();
		loadOrderBOMLineKeyLayout();
	}
}
