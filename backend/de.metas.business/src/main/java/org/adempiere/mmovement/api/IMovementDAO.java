package org.adempiere.mmovement.api;

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

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.mmovement.MovementLineId;
import org.compiere.model.I_M_Movement;
import org.compiere.model.I_M_MovementLine;

import de.metas.inventory.InventoryId;
import de.metas.util.ISingletonService;

public interface IMovementDAO extends ISingletonService
{
	I_M_MovementLine getLineById(MovementLineId movementLineId);

	/**
	 * Retrieves all {@link I_M_MovementLine}s (including inactive ones), ordered by "Line" column.
	 * 
	 * @param movement
	 * @return movement lines
	 * @see #retrieveLines(I_M_Movement, Class)
	 */
	List<I_M_MovementLine> retrieveLines(I_M_Movement movement);

	/**
	 * Retrieves all {@link I_M_MovementLine}s (including inactive ones), ordered by "Line" column.
	 * 
	 * @param movement
	 * @param movementLineClass
	 * @return movement lines
	 */
	<MovementLineType extends I_M_MovementLine> List<MovementLineType> retrieveLines(I_M_Movement movement, final Class<MovementLineType> movementLineClass);

	IQueryBuilder<I_M_Movement> retrieveMovementsForInventoryQuery(InventoryId inventoryId);

	void save(final I_M_Movement movement);

	void save(final I_M_MovementLine movementLine);

	List<I_M_Movement> retrieveMovementsForDDOrder(int ddOrderId);
}
