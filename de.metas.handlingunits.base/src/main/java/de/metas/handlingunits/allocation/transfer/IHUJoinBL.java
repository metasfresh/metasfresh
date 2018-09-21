package de.metas.handlingunits.allocation.transfer;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.exceptions.NoCompatibleHUItemParentFoundException;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.util.ISingletonService;

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
