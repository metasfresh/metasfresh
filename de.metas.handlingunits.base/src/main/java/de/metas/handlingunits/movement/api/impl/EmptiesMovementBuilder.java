package de.metas.handlingunits.movement.api.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.movement.api.IEmptiesMovementBuilder;
import de.metas.handlingunits.movement.api.IHUMovementBL;
import de.metas.inoutcandidate.spi.impl.HUPackingMaterialDocumentLineCandidate;
import de.metas.interfaces.I_M_Movement;

public class EmptiesMovementBuilder implements IEmptiesMovementBuilder
{
	private static class WarehouseDirection
	{
		public WarehouseDirection(final int warehouseID, final boolean direction)
		{
			this.warehouseID = warehouseID;
			this.direction = direction;

		};

		private final int warehouseID;

		private final boolean direction;

		public int getWarehouseID()
		{
			return warehouseID;
		}

		public boolean getDirection()
		{
			return direction;
		}
	}

	private static Set<WarehouseDirection> warehouseDirections = new HashSet<EmptiesMovementBuilder.WarehouseDirection>();

	@Override
	public List<I_M_Movement> createMovements(final IHUContext huContext)
	{
		final List<HUPackingMaterialDocumentLineCandidate> candidates = huContext.getHUPackingMaterialsCollector().getAndClearCandidates();

		//
		// Iterate all receipt lines and group them by target warehouse
		final Map<WarehouseDirection, List<HUPackingMaterialDocumentLineCandidate>> directionToCandidates = new HashMap<WarehouseDirection, List<HUPackingMaterialDocumentLineCandidate>>();

		for (final HUPackingMaterialDocumentLineCandidate candidate : candidates)
		{
			final I_M_Locator locatorCandidate = candidate.getM_Locator();

			final I_M_Warehouse warehouseCandidate = locatorCandidate.getM_Warehouse();

			final BigDecimal qty = candidate.getQty();
			if (qty.signum() == 0)
			{
				// We don't need a movement in the case of a qty = 0
				// do nothing
				continue;
			}

			// The direction is on true (Y) when the movement will be done TO the Gebindelager
			// and false (N) when it is done FROM the Gebindelager

			WarehouseDirection warehouseDirection = getFromSet(warehouseCandidate.getM_Warehouse_ID(), qty.signum() > 0);
			if (warehouseDirection == null)
			{
				warehouseDirection = new WarehouseDirection(warehouseCandidate.getM_Warehouse_ID(), qty.signum() > 0);
				warehouseDirections.add(warehouseDirection);
			}

			List<HUPackingMaterialDocumentLineCandidate> linesForWarehouse = directionToCandidates.get(warehouseDirection);
			if (linesForWarehouse == null)
			{
				linesForWarehouse = new ArrayList<HUPackingMaterialDocumentLineCandidate>();
				directionToCandidates.put(warehouseDirection, linesForWarehouse);
			}

			linesForWarehouse.add(candidate);
		}

		//
		// Generate movements for each "warehouseDestId -> inout lines" pair
		final List<I_M_Movement> movements = new ArrayList<I_M_Movement>();
		for (final Map.Entry<WarehouseDirection, List<HUPackingMaterialDocumentLineCandidate>> movementCandidate : directionToCandidates.entrySet())
		{
			final WarehouseDirection warehouseDirection = movementCandidate.getKey();
			final int warehouseID = warehouseDirection.getWarehouseID();
			Check.assume(warehouseID >= 0, "Warehouse does not exist");

			final I_M_Warehouse warehouse = InterfaceWrapperHelper.create(huContext.getCtx(), warehouseID, I_M_Warehouse.class, ITrx.TRXNAME_None);

			final boolean direction = warehouseDirection.getDirection();

			final List<HUPackingMaterialDocumentLineCandidate> linesForWarehouse = movementCandidate.getValue();
			Check.assumeNotEmpty(linesForWarehouse, "linesForWarehouse not empty");

			final I_M_Movement movement = Services.get(IHUMovementBL.class).generateMovementFromPackingMaterialCandidates(warehouse, direction, linesForWarehouse);
			if (movement != null)
			{
				movements.add(movement);
			}
		}

		return movements;
	}

	private WarehouseDirection getFromSet(final int warehouseID, final boolean direction)
	{
		for (final WarehouseDirection warehouseDirection : warehouseDirections)
		{
			if (warehouseDirection.getWarehouseID() == warehouseID
					&& warehouseDirection.getDirection() == direction)
			{
				return warehouseDirection;
			}
		}
		return null;
	}
}
