package de.metas.handlingunits.client.terminal.ddorder.model;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.collections.Predicate;
import org.compiere.model.I_M_Product;
import org.eevolution.api.IPPOrderBOMDAO;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;

import de.metas.adempiere.form.terminal.IKeyLayoutSelectionModel;
import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.TerminalKeyListenerAdapter;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.table.ITerminalTableModel;
import de.metas.adempiere.form.terminal.table.ITerminalTableModel.SelectionMode;
import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.client.terminal.ddorder.api.impl.DDOrderFiltering;
import de.metas.handlingunits.client.terminal.editor.model.IHUKey;
import de.metas.handlingunits.client.terminal.editor.model.IHUKeyFactory;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUEditorModel;
import de.metas.handlingunits.client.terminal.select.api.IPOSFiltering;
import de.metas.handlingunits.client.terminal.select.api.IPOSTableRow;
import de.metas.handlingunits.client.terminal.select.model.AbstractHUSelectModel;
import de.metas.handlingunits.client.terminal.select.model.IHUEditorCallback;
import de.metas.handlingunits.client.terminal.select.model.WarehouseKey;
import de.metas.handlingunits.document.impl.NullHUDocumentLineFinder;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.interfaces.I_M_Warehouse;

public class DDOrderHUSelectModel extends AbstractHUSelectModel
{
	// services
	private final transient IPPOrderBOMDAO ppOrderBOMDAO = Services.get(IPPOrderBOMDAO.class);

	private final DDOrderKeyLayout ddOrderKeyLayout;

	/**
	 * Used when starting this model from PP Order POS.
	 */
	private boolean isManualStart = false;
	private I_PP_Order pp_Order;

	public DDOrderHUSelectModel(final ITerminalContext terminalContext)
	{
		super(terminalContext);
		setDisplayPhotoShootButton(false); // not working with DD OrderLines anyway (because they are aggregated)
		setDisplayCloseLinesButton(true);

		//
		// Configure ddOrderKeyLayout selectionModel
		ddOrderKeyLayout = new DDOrderKeyLayout(terminalContext);
		{
			final IKeyLayoutSelectionModel ddOrderKeyLayoutModel = ddOrderKeyLayout.getKeyLayoutSelectionModel();
			ddOrderKeyLayoutModel.setAllowKeySelection(true);
			ddOrderKeyLayoutModel.setToggleableSelection(true);
		}
		ddOrderKeyLayout.addTerminalKeyListener(new TerminalKeyListenerAdapter()
		{
			@Override
			public void keyReturned(final ITerminalKey key)
			{
				final DDOrderKey purchaseOrderKey = (DDOrderKey)key;
				onDDOrderKeyPressed(purchaseOrderKey);
			}
		});

		//
		// Configure Rows Model
		// NOTE: configure rowsModel before doing ANYTHING else because if we change the selection mode after Rows Panel is initialized, this won't be propagated to Panel
		final ITerminalTableModel<IPOSTableRow> rowsModel = getRowsModel();
		rowsModel.setSelectionMode(SelectionMode.SINGLE);

		load();
	}

	@Override
	protected final DDOrderFiltering getService()
	{
		final IPOSFiltering service = super.getService();
		Check.assumeInstanceOf(service, DDOrderFiltering.class, "service");
		return (DDOrderFiltering)service;
	}

	private void onDDOrderKeyPressed(final DDOrderKey purchaseOrderKey)
	{
		final List<IPOSTableRow> lines = getRows();
		final List<I_DD_Order> ddOrders = getService().getDDOrders(lines);
		ddOrderKeyLayout.createAndSetKeysFromDDOrders(ddOrders);

		refreshLines(false);
	}

	@Override
	protected final void onWarehouseKeyPressed(final WarehouseKey key)
	{
		getVendorKeyLayout().getKeyLayoutSelectionModel().onKeySelected(null);
		ddOrderKeyLayout.getKeyLayoutSelectionModel().onKeySelected(null);
	}

	/**
	 *
	 * @return DD_Order_ID of currently pressed {@link DDOrderKey}
	 */
	public final int getDD_Order_ID()
	{
		final DDOrderKey ddOrderKey = ddOrderKeyLayout.getKeyLayoutSelectionModel().getSelectedKeyOrNull(DDOrderKey.class);
		if (ddOrderKey == null)
		{
			return -1;
		}
		return ddOrderKey.getDD_Order_ID();
	}

	public DDOrderKeyLayout getDDOrderKeyLayout()
	{
		return ddOrderKeyLayout;
	}

	@Override
	protected void loadKeysFromLines(final List<IPOSTableRow> lines)
	{
		final List<I_DD_Order> ddOrders = getService().getDDOrders(lines);
		ddOrderKeyLayout.createAndSetKeysFromDDOrders(ddOrders);
	}

	/**
	 * Predicate used to filter retrieved rows ( {@link IPOSTableRow} ) based on current pressed Keys
	 */
	private final Predicate<IPOSTableRow> rowsFilter = new Predicate<IPOSTableRow>()
	{
		@Override
		public boolean evaluate(final IPOSTableRow row)
		{
			if (row == null)
			{
				return false;
			}

			final int currentBPartnerId = getC_BPartner_ID();
			if (currentBPartnerId > 0 && row.getC_BPartner().getC_BPartner_ID() != currentBPartnerId)
			{
				return false;
			}

			final DDOrderFiltering service = getService();

			final int currentDD_Order_ID = getDD_Order_ID();
			if (currentDD_Order_ID > 0)
			{
				final IDDOrderTableRow ddOrderRow = service.getDDOrderTableRow(row);
				if (!ddOrderRow.getDD_Order_IDs().contains(currentDD_Order_ID))
				{
					return false;
				}
			}

			return true;
		}
	};

	/**
	 * Predicate used to filter retrieved rows ( {@link IPOSTableRow} ) based on current pressed Keys
	 */
	private final Predicate<IPOSTableRow> rowsFilterPPOrder = new Predicate<IPOSTableRow>()
	{
		@Override
		public boolean evaluate(final IPOSTableRow row)
		{
			Check.assumeNotNull(pp_Order, "PP Order is not null");

			final Set<Integer> rowProductIds = row.getM_Product_IDs();
			if (rowProductIds == null || rowProductIds.isEmpty())
			{
				return false;
			}

			final List<I_PP_Order_BOMLine> bomList = ppOrderBOMDAO.retrieveOrderBOMLines(pp_Order);
			for (final I_PP_Order_BOMLine bom : bomList)
			{
				final int bomProductId = bom.getM_Product_ID();
				if (rowProductIds.contains(bomProductId))
				{
					return true;
				}
			}
			return false;
		}
	};

	@Override
	protected Predicate<IPOSTableRow> getRowsFilter()
	{
		if (isManualStart && null != pp_Order)
		{
			// 06597 : We filter order lines by PP order's BOM if we have a manual start.
			return rowsFilterPPOrder;
		}
		else
		{
			return rowsFilter;
		}
	}

	@Override
	protected HUEditorModel createHUEditorModel(final Collection<IPOSTableRow> rows, final IHUEditorCallback<HUEditorModel> editorCallback_NOTUSED)
	{
		Check.assume(rows != null && rows.size() == 1, "Only one row is allowed to be selected");

		final ITerminalContext terminalContext = getTerminalContext();
		final IHUKeyFactory keyFactory = terminalContext.getService(IHUKeyFactory.class);

		//
		// Create a Root HU Key from HUs
		// NOTE: we are using null documentLine because our HUs are not assigned to this DD Order Line yet
		final IHUQueryBuilder husQuery = createHUQueryBuilder(rows);
		final IHUKey rootKey = keyFactory.createRootKey(husQuery, NullHUDocumentLineFinder.instance);

		//
		// Create HU Editor Model
		final HUEditorModel huEditorModel = new HUEditorModel(terminalContext);
		huEditorModel.setRootHUKey(rootKey);

		// Because there is no huDocumentLineFinder, there is no point to check and update HU Allocations
		huEditorModel.setUpdateHUAllocationsOnSave(false);

		return huEditorModel;
	}

	private final IHUQueryBuilder createHUQueryBuilder(final Collection<IPOSTableRow> rows)
	{
		final DDOrderFiltering service = getService();
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		//
		// Fetch source Warehouse IDs from selected rows
		final Set<Integer> sourceWarehouseIds = service.getSourceWarehouseIds(rows);
		if (sourceWarehouseIds.isEmpty())
		{
			// no lines selected => no warehouses => nothing to do
			return null;
		}

		//
		// Fetch product IDs
		final Set<Integer> productIds = service.getProductIdsFromRows(rows);

		//
		// Create the HU query builder
		final ITerminalContext terminalContext = getTerminalContext();
		final IHUQueryBuilder huQueryBuilder = handlingUnitsDAO.createHUQueryBuilder()
				.setContext(terminalContext)
				.setErrorIfNoHUs(MSG_ErrorNoHandlingUnitFoundForSelection)
				.setOnlyTopLevelHUs()
				.addOnlyInWarehouseIds(sourceWarehouseIds)
				.addOnlyWithProductIds(productIds);

		//
		// If our rows have some HUs assigned to be moved, then present to user ONLY those HUs (task 08639)
		final List<Integer> huIdsScheduledToMove = service.getHUIdsScheduledToMove(getCtx(), rows);
		if (!huIdsScheduledToMove.isEmpty())
		{
			huQueryBuilder.addOnlyHUIds(huIdsScheduledToMove);
		}

		return huQueryBuilder;
	}

	@Override
	protected void processRows(final Set<IPOSTableRow> rows, final HUEditorModel huEditorModel)
	{
		//
		// Get selected row from given rows
		// NOTE: we expect only get only one row
		if (rows.isEmpty())
		{
			// shall not happen, but fine
			return;
		}
		else if (rows.size() > 1)
		{
			throw new AdempiereException("Only one selected row expected but we got: " + rows);
		}

		final DDOrderFiltering service = getService();

		final IPOSTableRow row = rows.iterator().next();
		final IDDOrderTableRow ddOrderRow = service.getDDOrderTableRow(row);

		final List<I_DD_OrderLine> ddOrderLines = service.getDDOrderLines(ddOrderRow);
		final List<I_M_Product> products = ddOrderRow.getM_Products();

		final List<IHUProductStorage> huProductStorages = new ArrayList<IHUProductStorage>();
		for (final I_M_Product product : products)
		{
			final List<IHUProductStorage> subHUProductStorages = huEditorModel.getSelectedHUProductStorages(product);
			huProductStorages.addAll(subHUProductStorages);
		}

		service.processDDOrderLines(ddOrderLines, huProductStorages);
	}

	/**
	 * Used when manually opening the DD order panel for a particular order.
	 *
	 * @param pp_Order
	 */
	public void setContextManufacturingOrder(final I_PP_Order pp_Order)
	{
		isManualStart = true;
		this.pp_Order = pp_Order;

		ddOrderKeyLayout.setEnabledKeys(false);
		super.setLayoutsEnabled(false);

		super.setSingleWarehouse(pp_Order.getM_Warehouse());

		warehouseOverrideId = -1; // protection
	}

	private int warehouseOverrideId = -1;

	public void setWarehouseOverrideId(final int warehouseOverrideId)
	{
		this.warehouseOverrideId = warehouseOverrideId;

		final I_M_Warehouse warehouse = InterfaceWrapperHelper.create(getCtx(), warehouseOverrideId, I_M_Warehouse.class, ITrx.TRXNAME_None);
		setSingleWarehouse(warehouse);
	}

	/**
	 * @return warehouseId override, then try the PP_Order's warehouseId if available and manual, else get it from the selection
	 */
	@Override
	public int getM_Warehouse_ID()
	{
		if (warehouseOverrideId > 0)
		{
			return warehouseOverrideId;
		}
		else if (isManualStart && null != pp_Order)
		{
			return pp_Order.getM_Warehouse_ID();
		}
		else
		{
			return super.getM_Warehouse_ID();
		}
	}
}
