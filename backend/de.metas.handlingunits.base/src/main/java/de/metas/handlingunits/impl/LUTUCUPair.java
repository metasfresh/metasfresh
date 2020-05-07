package de.metas.handlingunits.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.util.lang.ObjectUtils;

import de.metas.handlingunits.ILUTUCUPair;
import de.metas.handlingunits.model.I_M_HU;

public class LUTUCUPair implements ILUTUCUPair
{
	private I_M_HU luHU = null;
	private I_M_HU tuHU = null;
	private I_M_HU vhu = null;

	public LUTUCUPair(final I_M_HU luHU, final I_M_HU tuHU, final I_M_HU vhu)
	{
		super();
		this.luHU = luHU;
		this.tuHU = tuHU;
		this.vhu = vhu;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public I_M_HU getM_TU_HU()
	{
		return tuHU;
	}

	@Override
	public I_M_HU getM_LU_HU()
	{
		return luHU;
	}

	@Override
	public I_M_HU getVHU()
	{
		return vhu;
	}

}
