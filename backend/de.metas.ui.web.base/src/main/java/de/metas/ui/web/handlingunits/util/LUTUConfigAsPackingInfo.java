package de.metas.ui.web.handlingunits.util;

import java.math.BigDecimal;

import lombok.NonNull;
import org.compiere.model.I_C_UOM;

import com.google.common.base.MoreObjects;

import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
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

class LUTUConfigAsPackingInfo implements IHUPackingInfo
{
	private final I_M_HU_LUTU_Configuration lutuConfig;

	LUTUConfigAsPackingInfo(@NonNull final I_M_HU_LUTU_Configuration lutuConfig)
	{
		this.lutuConfig = lutuConfig;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this).addValue(lutuConfig).toString();
	}

	@Override
	public I_M_HU_PI getM_LU_HU_PI()
	{
		return lutuConfig.getM_LU_HU_PI();
	}

	@Override
	public I_M_HU_PI getM_TU_HU_PI()
	{
		return lutuConfig.getM_TU_HU_PI();
	}

	@Override
	public boolean isInfiniteQtyTUsPerLU()
	{
		return lutuConfig.isInfiniteQtyTU();
	}

	@Override
	public BigDecimal getQtyTUsPerLU()
	{
		return lutuConfig.getQtyTU();
	}

	@Override
	public boolean isInfiniteQtyCUsPerTU()
	{
		return lutuConfig.isInfiniteQtyCU();
	}

	@Override
	public BigDecimal getQtyCUsPerTU()
	{
		return lutuConfig.getQtyCUsPerTU();
	}

	@Override
	public I_C_UOM getQtyCUsPerTU_UOM()
	{
		return ILUTUConfigurationFactory.extractUOMOrNull(lutuConfig);
	}
}
