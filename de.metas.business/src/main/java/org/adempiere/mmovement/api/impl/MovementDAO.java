package org.adempiere.mmovement.api.impl;

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
import org.adempiere.mmovement.api.IMovementDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Movement;
import org.compiere.model.I_M_MovementLine;

public class MovementDAO implements IMovementDAO
{
	@Override
	public List<I_M_MovementLine> retrieveLines(final I_M_Movement movement)
	{
		return retrieveLines(movement, I_M_MovementLine.class);
	}

	@Override
	public <MovementLineType extends I_M_MovementLine> List<MovementLineType> retrieveLines(final I_M_Movement movement, final Class<MovementLineType> movementLineClass)
	{
		Check.assumeNotNull(movement, "movement not null");
		Check.assumeNotNull(movementLineClass, "movementLineClass not null");

		final IQueryBuilder<MovementLineType> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(movementLineClass, movement);

		queryBuilder.getFilters()
				.addEqualsFilter(I_M_MovementLine.COLUMNNAME_M_Movement_ID, movement.getM_Movement_ID());

		queryBuilder.orderBy()
				.addColumn(I_M_MovementLine.COLUMNNAME_Line, Direction.Ascending, Nulls.Last);

		return queryBuilder
				.create()
				.list(movementLineClass);
	}

	@Override
	public IQueryBuilder<I_M_Movement> retrieveMovementsForInventoryQuery(final int inventoryId)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_Movement.class)
				.addEqualsFilter(I_M_Movement.COLUMN_M_Inventory_ID, inventoryId);
	}
}
