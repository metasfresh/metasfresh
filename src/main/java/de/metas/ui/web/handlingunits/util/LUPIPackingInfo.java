package de.metas.ui.web.handlingunits.util;

import java.math.BigDecimal;

import org.compiere.model.I_C_UOM;

import com.google.common.base.MoreObjects;

import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.util.Check;

/*
 * #%L
 * metasfresh-webui-api
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

class LUPIPackingInfo implements IHUPackingInfo
{
	private final I_M_HU_PI luPI;

	public LUPIPackingInfo(final I_M_HU_PI luPI)
	{
		Check.assumeNotNull(luPI, "Parameter luPI is not null");
		this.luPI = luPI;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this).addValue(luPI).toString();
	}

	@Override
	public I_M_HU_PI getM_LU_HU_PI()
	{
		return luPI;
	}

	@Override
	public I_M_HU_PI getM_TU_HU_PI()
	{
		return null;
	}

	@Override
	public boolean isInfiniteQtyTUsPerLU()
	{
		return true;
	}

	@Override
	public BigDecimal getQtyTUsPerLU()
	{
		return null;
	}

	@Override
	public boolean isInfiniteQtyCUsPerTU()
	{
		return true;
	}

	@Override
	public BigDecimal getQtyCUsPerTU()
	{
		return null;
	}

	@Override
	public I_C_UOM getQtyCUsPerTU_UOM()
	{
		return null;
	}

}
