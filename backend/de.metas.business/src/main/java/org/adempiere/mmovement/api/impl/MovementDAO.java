package org.adempiere.mmovement.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.mmovement.MovementLineId;
import org.adempiere.mmovement.api.IMovementDAO;
import org.compiere.model.I_M_Movement;
import org.compiere.model.I_M_MovementLine;

import de.metas.inventory.InventoryId;
import de.metas.util.Services;
import lombok.NonNull;

public class MovementDAO implements IMovementDAO
{
	final private IQueryBL queryBL = Services.get(IQueryBL.class);
	
	@Override
	public I_M_MovementLine getLineById(@NonNull final MovementLineId movementLineId)
	{
		return load(movementLineId, I_M_MovementLine.class);
	}

	@Override
	public List<I_M_MovementLine> retrieveLines(final I_M_Movement movement)
	{
		return retrieveLines(movement, I_M_MovementLine.class);
	}

	@Override
	public <MovementLineType extends I_M_MovementLine> List<MovementLineType> retrieveLines(@NonNull final I_M_Movement movement, @NonNull final Class<MovementLineType> movementLineClass)
	{
		final IQueryBuilder<MovementLineType> queryBuilder = queryBL.createQueryBuilder(movementLineClass, movement);

		queryBuilder.getCompositeFilter()
				.addEqualsFilter(I_M_MovementLine.COLUMNNAME_M_Movement_ID, movement.getM_Movement_ID());

		queryBuilder.orderBy()
				.addColumn(I_M_MovementLine.COLUMNNAME_Line, Direction.Ascending, Nulls.Last);

		return queryBuilder
				.create()
				.list(movementLineClass);
	}

	@Override
	public IQueryBuilder<I_M_Movement> retrieveMovementsForInventoryQuery(@NonNull final InventoryId inventoryId)
	{
		return queryBL.createQueryBuilder(I_M_Movement.class)
				.addEqualsFilter(I_M_Movement.COLUMN_M_Inventory_ID, inventoryId);
	}
	
	@Override
	public List<I_M_Movement> retrieveMovementsForDDOrder(final int ddOrderId)
	{
		return queryBL.createQueryBuilder(I_M_Movement.class)
				.addEqualsFilter(I_M_Movement.COLUMN_DD_Order_ID, ddOrderId)
				.create()
				.list();
	}

	@Override
	public void save(@NonNull final I_M_Movement movement)
	{
		saveRecord(movement);
	}

	@Override
	public void save(@NonNull final I_M_MovementLine movementLine)
	{
		saveRecord(movementLine);
	}
}
