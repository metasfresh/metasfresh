package de.metas.handlingunits.hutransaction.impl;

import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.hutransaction.IHUTransactionCandidate;
import de.metas.handlingunits.hutransaction.IHUTransactionBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.util.Services;

public class HUTransactionBL implements IHUTransactionBL
{

	@Override
	public IHUTransactionCandidate createLUTransactionForAttributeTransfer(final I_M_HU luHU, final I_M_HU_PI_Item luItemPI, final IAllocationRequest request)
	{
		final I_M_HU_Item luItem = Services.get(IHandlingUnitsDAO.class).retrieveItem(luHU, luItemPI);

		final HUTransactionCandidate luTrx = new HUTransactionCandidate(AllocationUtils.getReferencedModel(request), // receipt schedule
				luItem, // huItem,
				null, // vhuItem (note: at this level we're not talking about VHUs but actual LU-HUs)
				request.getProduct(),
				request.getQuantity().toZero(),
				request.getDate()
				);

		return luTrx;
	}

}
