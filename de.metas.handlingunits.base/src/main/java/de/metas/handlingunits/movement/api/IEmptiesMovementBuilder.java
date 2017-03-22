package de.metas.handlingunits.movement.api;

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


import java.util.List;

import de.metas.handlingunits.IHUContext;
import de.metas.interfaces.I_M_Movement;

/**
 * Create movements based on the data collected within {@link IHUContext#getHUPackingMaterialsCollector()}.
 * <p>
 * Use {@link de.metas.handlingunits.IHandlingUnitsBL#createEmptiesMovementBuilder()} to get your instance.
 * <p>
 * * The Empties movements will be either FROM the Empties Warehouse to the current warehouse (in case of creating a new HU) or from the current warehouse TO the Empties Warehouse (in case of
 * destroying an HU).
 *
 */
public interface IEmptiesMovementBuilder
{
	/**
	 * See the {@link IEmptiesMovementBuilder interface} javadoc.
	 *
	 * @param huContext
	 * @return list of the created movements
	 */
	List<I_M_Movement> createMovements(IHUContext huContext);
}
