package de.metas.handlingunits.client.terminal.editor.model.impl;

import java.util.List;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Movement;
import org.compiere.util.TrxRunnable2;

import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.TerminalKeyListenerAdapter;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.IHUWarehouseDAO;
import de.metas.handlingunits.client.terminal.mmovement.exception.MaterialMovementException;
import de.metas.handlingunits.client.terminal.mmovement.model.impl.AbstractMaterialMovementModel;
import de.metas.handlingunits.client.terminal.select.model.WarehouseKey;
import de.metas.handlingunits.client.terminal.select.model.WarehouseKeyLayout;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Warehouse;
import de.metas.handlingunits.movement.api.IHUMovementBL;
import de.metas.movement.event.MovementProcessedEventBus;

/*
 * #%L
 * de.metas.handlingunits.client
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class ReturnsWarehouseModel extends AbstractMaterialMovementModel
{

	private static final String MSG_NoQualityWarehouse = "NoQualityWarehouse";
	
	private List<I_M_HU> hus;
	private List<I_M_Movement> movements;

	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);

	private final transient IHUMovementBL huMovementBL = Services.get(IHUMovementBL.class);

	private WarehouseKeyLayout warehouseKeyLayout;

	/**
	 * Selected key (used when {@link #allowKeySelection} is false)
	 */
	private ITerminalKey selectedKey = null;

	public ReturnsWarehouseModel(ITerminalContext terminalContext, final I_M_Warehouse warehouseFrom, final List<I_M_HU> hus)
	{
		super(terminalContext);

		this.hus = hus;
		//
		// Configure Warehouse Key Layout
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

		load();
	}

	public final WarehouseKeyLayout getWarehouseKeyLayout()
	{
		return warehouseKeyLayout;
	}

	private void load()
	{

		final List<I_M_Warehouse> warehouses = Services.get(IHUWarehouseDAO.class).retrieveQualityReturnWarehouse(getCtx());

		final List<org.compiere.model.I_M_Warehouse> warehousesToLoad = InterfaceWrapperHelper.createList(warehouses, org.compiere.model.I_M_Warehouse.class);
		Check.assumeNotEmpty(warehouses, MSG_NoQualityWarehouse);
		warehouseKeyLayout.createAndSetKeysFromWarehouses(warehousesToLoad);

	}

	public ITerminalKey getSelectedKeyOrNull()
	{
		return selectedKey;
	}

	private boolean _disposed = false;

	@Override
	public final void dispose()
	{
		if (_disposed)
		{
			return;
		}
		_disposed = true;
	}

	protected void onWarehouseKeyPressed(WarehouseKey warehouseKey)
	{
		setSelectedKey(warehouseKey, true);

	}

	private void setSelectedKey(final ITerminalKey selectedKey, final boolean selectDirectly)
	{
		if (_disposed)
		{
			return;
		}

		if (selectedKey == null)
		{
			return;
		}

		this.selectedKey = selectedKey;

	}

	/**
	 * Move the selected HUs to the quality returns warehouse
	 * task #1065
	 */
	private List<I_M_Movement> doMoveToQualityWarehouse()
	{
		final I_M_Warehouse qualityReturnsWarehouse = getM_WarehouseTo();
		final List<I_M_HU> hus = getHUs();
		movements = huMovementBL.moveHUsToWarehouse(hus, qualityReturnsWarehouse)
				.getMovements();

		return movements;

	}

	public List<I_M_Movement> getMovements()
	{
		return movements;
	}

	public void setMovements(List<I_M_Movement> movements)
	{
		this.movements = movements;
	}

	@Override
	public void execute() throws MaterialMovementException
	{
		trxManager.run(new TrxRunnable2()
		{

			@Override
			public void run(final String localTrxName) throws Exception
			{

				// final List<de.metas.handlingunits.model.I_M_Warehouse> qualityWarehouses = Services.get(IHUWarehouseDAO.class).retrieveQualityReturnWarehouse(ctx);
				final List<I_M_Movement> movements = doMoveToQualityWarehouse();

				MovementProcessedEventBus.newInstance()
						.queueEventsUntilTrxCommit(trxManager.getThreadInheritedTrxName())
						.notify(movements);
			}

			@Override
			public boolean doCatch(final Throwable e) throws Throwable
			{
				throw new MaterialMovementException(e.getLocalizedMessage(), e);
			}

			@Override
			public void doFinally()
			{
				// nothing
			}
		});
	}

	/**
	 * Warehouse that was selected to move the quality HUs to
	 * 
	 * @return
	 */
	@OverridingMethodsMustInvokeSuper
	public I_M_Warehouse getM_WarehouseTo()
	{
		final WarehouseKey warehouseKey = warehouseKeyLayout.getKeyLayoutSelectionModel().getSelectedKeyOrNull(WarehouseKey.class);
		if (warehouseKey == null)
		{
			return null;
		}
		final int warehouseID = warehouseKey.getM_Warehouse_ID();

		final I_M_Warehouse warehouseTo = InterfaceWrapperHelper.create(getCtx(), warehouseID, I_M_Warehouse.class, ITrx.TRXNAME_None);

		return warehouseTo;
	}

	public List<I_M_HU> getHUs()
	{
		return hus;
	}

}
