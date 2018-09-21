package de.metas.handlingunits.allocation;

import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.util.ISingletonService;

public interface IAllocationStrategyFactory extends ISingletonService
{
	IHandlingUnitsDAO getHandlingUnitsDAO();

	IAllocationStrategy getAllocationStrategy(I_M_HU hu);

	IAllocationStrategy getDeallocationStrategy(I_M_HU hu);
}
