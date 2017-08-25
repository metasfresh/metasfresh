package de.metas.handlingunits.client.terminal.editor.model.impl;

import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Movement;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.IHUWarehouseDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Warehouse;
import de.metas.handlingunits.movement.api.IHUMovementBL;

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

public class QualityReturnsWarehouseModel extends AbstractMovementsWarehouseModel
{

	private static final String MSG_NoQualityWarehouse = "NoQualityWarehouse";
	private final transient IHUMovementBL huMovementBL = Services.get(IHUMovementBL.class);

	public QualityReturnsWarehouseModel(final ITerminalContext terminalContext, final I_M_Warehouse warehouseFrom, final List<I_M_HU> hus)
	{
		super(terminalContext, warehouseFrom, hus);

	}

	@Override
	protected void load()
	{
		final List<I_M_Warehouse> warehouses = Services.get(IHUWarehouseDAO.class).retrieveQualityReturnWarehouse(getCtx());

		final List<org.compiere.model.I_M_Warehouse> warehousesToLoad = InterfaceWrapperHelper.createList(warehouses, org.compiere.model.I_M_Warehouse.class);
		Check.assumeNotEmpty(warehouses, MSG_NoQualityWarehouse);
		warehouseKeyLayout.createAndSetKeysFromWarehouses(warehousesToLoad);

	}

	@Override
	protected void createMovements()
	{
		setMovements(doMoveToQualityWarehouse());

	}

	/**
	 * Move the selected HUs to quality warehouse
	 * task #1065
	 */
	private List<I_M_Movement> doMoveToQualityWarehouse()
	{
		final I_M_Warehouse qualityReturnsWarehouse = getM_WarehouseTo();
		final List<I_M_HU> hus = getHUs();
		final List<I_M_Movement> qualityMovements = huMovementBL.moveHUsToWarehouse(hus, qualityReturnsWarehouse)
				.getMovements();

		return qualityMovements;

	}

}
