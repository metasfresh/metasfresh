package de.metas.inoutcandidate.spi.impl;

import de.metas.handlingunits.model.I_M_InOutLine;

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

public class InOutLineHUPackingMaterialCollectorSource implements IHUPackingMaterialCollectorSource
{
	private I_M_InOutLine inOutLine;
	private boolean isCollectHUPipToSource = true;

	public InOutLineHUPackingMaterialCollectorSource(I_M_InOutLine inOutLine)
	{
		this.inOutLine = inOutLine;
	}

	@Override
	public int getM_Product_ID()
	{
		return inOutLine.getM_Product_ID();
	}

	@Override
	public int getRecord_ID()
	{
		return inOutLine.getM_InOutLine_ID();
	}

	@Override
	public boolean isCollectHUPipToSource()
	{
		// // Only applied to customer returns that are build from HUs (non-manual)
		// if (!inOutLine.getM_InOut().isSOTrx())
		// {
		// return false;
		// }
		//
		// if (Services.get(IHUInOutBL.class).isCustomerReturn(inOutLine.getM_InOut()))
		// {
		// return false;
		// }
		//
		// return true;

		return isCollectHUPipToSource;
	}

	public I_M_InOutLine getM_InOutLine()
	{
		return inOutLine;
	}


	public void setIsCollectHUPipToSource(boolean isCollectHUPipToSource)
	{
		this.isCollectHUPipToSource = isCollectHUPipToSource;
	}

	

}
