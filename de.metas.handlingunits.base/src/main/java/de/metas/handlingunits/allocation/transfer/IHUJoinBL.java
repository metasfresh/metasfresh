package de.metas.handlingunits.allocation.transfer;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.adempiere.util.ISingletonService;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.exceptions.NoCompatibleHUItemParentFoundException;
import de.metas.handlingunits.model.I_M_HU;

/**
 * Implementors assign a pre-existing TU to a pre-existing LU.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IHUJoinBL extends ISingletonService
{
	/**
	 * Assigns a trading unit to the selected loading unit
	 *
	 * @param huContext
	 * @param loadingUnit
	 * @param tradingUnit
	 *
	 * @throws NoCompatibleHUItemParentFoundException if the trading unit is incompatible with the all of the loading unit's item definitions
	 */
	void assignTradingUnitToLoadingUnit(IHUContext huContext, I_M_HU loadingUnit, I_M_HU tradingUnit) throws NoCompatibleHUItemParentFoundException;
}
