package de.metas.handlingunits.empties;

import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Warehouse;

import de.metas.handlingunits.inout.returns.IReturnsInOutProducer;
import de.metas.handlingunits.model.I_M_Locator;
import de.metas.util.ISingletonService;

/*
 * #%L
 * de.metas.handlingunits.base
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

public interface IHUEmptiesService extends ISingletonService
{
	/**
	 * Gets the warehouse to be used for empties (Gebinde).
	 * 
	 * The empties warehouse is taken from a special distribution network that has isHUDestroyed = true, from the (first) line that has the warehouse source the one given as parameter
	 *
	 * @param warehouse counter part warehouse, i.e. on which warehouse the empties are currently on
	 *
	 * @return the empties warehouse which shall be used for given counterpart warehouse
	 * @throws AdempiereException if empties warehouse was not found
	 */
	I_M_Warehouse getEmptiesWarehouse(I_M_Warehouse warehouse);

	/**
	 * Gets the warehouse locator to be used for empties.
	 * 
	 * @param warehouse
	 * @return empties locator
	 * @see #getEmptiesWarehouse(I_M_Warehouse)
	 */
	I_M_Locator getEmptiesLocator(I_M_Warehouse warehouse);

	/**
	 * Generate movements for the empties (Leergut) inOut. If the given <code>inout</code> is a receipt, the movement will be from inOut's warehouse to the empties-warehouse (Gebindelager). If the
	 * inOut is a shipment, the movement will be in the opposite direction.
	 *
	 * @param emptiesInOut
	 * @task 08070
	 */
	void generateMovementFromEmptiesInout(I_M_InOut emptiesInOut);

	/**
	 * Calls {@link EmptiesMovementProducer#newInstance()}.
	 */
	EmptiesMovementProducer newEmptiesMovementProducer();

	boolean isEmptiesInOut(I_M_InOut inout);

	IReturnsInOutProducer newReturnsInOutProducer(Properties ctx);

}
