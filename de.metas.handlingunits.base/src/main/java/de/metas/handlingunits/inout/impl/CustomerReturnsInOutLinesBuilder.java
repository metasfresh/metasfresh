package de.metas.handlingunits.inout.impl;

import org.adempiere.util.Services;
import org.adempiere.util.lang.IReference;
import org.compiere.model.I_M_InOut;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;

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

public class CustomerReturnsInOutLinesBuilder extends AbstractQualityReturnsInOutLinesBuilder
{
	// services
	private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	public CustomerReturnsInOutLinesBuilder(IReference<I_M_InOut> inoutRef)
	{
		super(inoutRef);

	}

	public static CustomerReturnsInOutLinesBuilder newBuilder(IReference<I_M_InOut> inoutRef)
	{

		return new CustomerReturnsInOutLinesBuilder(inoutRef);
	}

	@Override
	protected void setHUStatus(IHUContext huContext, I_M_HU hu)
	{
		handlingUnitsBL.setHUStatus(huContext, hu, X_M_HU.HUSTATUS_Active);
	}
	
	

}
