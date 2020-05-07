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
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;

/**
 * Implementors assign a pre-existing TU to a pre-existing LU.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IHUJoinBL extends ISingletonService
{
	/**
	 * Assign a "real" trading unit to the selected loading unit. Use {@link IHUTrxBL} to do the actual work, but first find the best {@link I_M_HU_Item} of the given {@code loadingUnit}.
	 * 
	 *
	 * @param huContext
	 * @param loadingUnit
	 * @param tradingUnit should <b>not</b> be an aggregate HU (unless you know what you do).
	 *
	 * @throws NoCompatibleHUItemParentFoundException if the trading unit is incompatible with the all of the loading unit's item definitions
	 */
	void assignTradingUnitToLoadingUnit(IHUContext huContext, I_M_HU loadingUnit, I_M_HU tradingUnit) throws NoCompatibleHUItemParentFoundException;
}
