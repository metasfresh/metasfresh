package de.metas.inoutcandidate.spi.impl;

import de.metas.handlingunits.model.I_M_MovementLine;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class MovementLineHUPackingMaterialCollectorSource implements IHUPackingMaterialCollectorSource
{
	private boolean isCollectHUPipToSource = false;
	public MovementLineHUPackingMaterialCollectorSource(I_M_MovementLine movementLine)
	{
	
		this.movementLine = movementLine;
		setIsCollectHUPipToSource(true);
	}

	private I_M_MovementLine movementLine;

	@Override
	public int getM_Product_ID()
	{
		return movementLine.getM_Product_ID();
	}

	@Override
	public int getRecord_ID()
	{
		return movementLine.getM_MovementLine_ID();
	}

	@Override
	public boolean isCollectHUPipToSource()
	{
		return isCollectHUPipToSource;
	}

	public I_M_MovementLine getMovementLine()
	{
		return movementLine;
	}

	@Override
	public void setIsCollectHUPipToSource(boolean isCollectHUPipToSource)
	{
		this.isCollectHUPipToSource = isCollectHUPipToSource;
		
	}

}
