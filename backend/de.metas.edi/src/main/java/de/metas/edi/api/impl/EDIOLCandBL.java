package de.metas.edi.api.impl;

import de.metas.adempiere.gui.search.IHUPackingAwareBL;
import de.metas.adempiere.gui.search.impl.OLCandHUPackingAware;
import de.metas.edi.api.IEDIInputDataSourceBL;
import de.metas.edi.api.IEDIOLCandBL;
import de.metas.handlingunits.model.I_C_OLCand;
import de.metas.util.Services;

public class EDIOLCandBL implements IEDIOLCandBL
{
	@Override
	public boolean isManualQtyItemCapacity(final I_C_OLCand olCand)
	{
		// @formatter:off
		// task 08147: we generally don't use the ORDERS file's QtyItemCapacity, because so far it was not reliable (it is mostly 1 and doesn't reflect the TU's actual capacity).
		// Instead, we go with our own master data to obtain the capacity.
//		if (isEDIInput(olCand))
//		{
//			return true;
//		}
		// @formatter:on

		// task 08147
		// were we just don't have the capacity, so we use the ORDERS file's value, but that's the only case where we do that

		final OLCandHUPackingAware olCandHUPackingAware = new OLCandHUPackingAware(olCand);
		
		return Services.get(IHUPackingAwareBL.class).isInfiniteCapacityTU(olCandHUPackingAware);
	}

	@Override
	public boolean isEDIInput(final de.metas.ordercandidate.model.I_C_OLCand olCand)
	{
		if (olCand.getAD_InputDataSource_ID() <= 0)
		{
			return false;
		}
		final int dataSourceId = olCand.getAD_InputDataSource_ID();
		return Services.get(IEDIInputDataSourceBL.class).isEDIInputDataSource(dataSourceId);
	}
}
