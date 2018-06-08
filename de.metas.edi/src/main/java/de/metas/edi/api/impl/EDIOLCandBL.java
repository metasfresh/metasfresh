package de.metas.edi.api.impl;

/*
 * #%L
 * de.metas.edi
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


import org.adempiere.util.Services;

import de.metas.adempiere.gui.search.impl.OLCandHUPackingAware;
import de.metas.edi.api.IEDIInputDataSourceBL;
import de.metas.edi.api.IEDIOLCandBL;
import de.metas.handlingunits.model.I_C_OLCand;

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
		if (olCandHUPackingAware.getM_HU_PI_Item_Product_ID() > 0
				&& olCandHUPackingAware.getM_HU_PI_Item_Product().isInfiniteCapacity())
		{
			return true;
		}

		return false;
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
