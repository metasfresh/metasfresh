package de.metas.handlingunits.hutransaction;

import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.util.ISingletonService;

/**
 * Declare business logic to create different {@link IHUTransactionCandidate}s for different purposes.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IHUTransactionBL extends ISingletonService
{
	/**
	 * Current use: creates a IHUTransaction for a LU, which is created from a receipt schedule.<br>
	 * Just so that HULoader.load0() will later on transfer the source's attributes also to the LU and not just to the TUs (task 06748).
	 *
	 * @param luHU
	 * @param luItemPI
	 * @param request
	 * @return
	 */
	IHUTransactionCandidate createLUTransactionForAttributeTransfer(I_M_HU luHU, I_M_HU_PI_Item luItemPI, IAllocationRequest request);

}
