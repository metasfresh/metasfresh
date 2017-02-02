package de.metas.handlingunits.allocation.transfer.impl;

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


import java.math.BigDecimal;

import org.adempiere.util.Check;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.allocation.transfer.IHUSplitDefinition;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;

public class HUSplitDefinition implements IHUSplitDefinition
{
	private final I_M_HU_PI_Item luPIItem;
	private final I_M_HU_PI_Item tuPIItem;

	private final I_M_Product cuProduct;
	private final I_C_UOM cuUOM;

	private final BigDecimal cuPerTU;
	private final BigDecimal tuPerLU;

	private final BigDecimal maxLUToAllocate;

	public HUSplitDefinition(final I_M_HU_PI_Item luPIItem,
			final I_M_HU_PI_Item tuPIItem,
			final I_M_Product cuProduct, final I_C_UOM cuUOM,
			final BigDecimal cuPerTU,
			final BigDecimal tuPerLU,
			final BigDecimal maxLUToAllocate)
	{
		super();

		Check.assumeNotNull(luPIItem, "luPIItem not null");
		this.luPIItem = luPIItem;

		Check.assumeNotNull(tuPIItem, "tuPIItem not null");
		this.tuPIItem = tuPIItem;

		Check.assumeNotNull(cuProduct, "cuProduct not null");
		this.cuProduct = cuProduct;

		Check.assumeNotNull(cuUOM, "cuUOM not null");
		this.cuUOM = cuUOM;

		Check.assumeNotNull(cuPerTU, "cuPerTU not null");
		Check.assumeNotNull(cuPerTU.signum() > 0, "cuPerTU > 0 but it was {}", cuPerTU);
		this.cuPerTU = cuPerTU;

		Check.assumeNotNull(tuPerLU, "tuPerLU not null");
		Check.assumeNotNull(tuPerLU.signum() > 0, "tuPerLU > 0 but it was {}", tuPerLU);
		this.tuPerLU = tuPerLU;

		Check.assumeNotNull(maxLUToAllocate, "maxLUToAllocate not null");
		// MaxLUToAllocate can be 0, if, and only if, we only want products allocated to TUs
		Check.assume(maxLUToAllocate.signum() >= 0, "maxLUToAllocate >= 0 but it was {}", maxLUToAllocate);
		this.maxLUToAllocate = maxLUToAllocate;
	}

	@Override
	public I_M_HU_PI getTuPI()
	{
		return tuPIItem.getM_HU_PI_Version().getM_HU_PI();
	}

	@Override
	public I_M_HU_PI_Item getLuPIItem()
	{
		return luPIItem;
	}

	@Override
	public I_M_HU_PI getLuPI()
	{
		return luPIItem.getM_HU_PI_Version().getM_HU_PI();
	}

	@Override
	public I_M_Product getCuProduct()
	{
		return cuProduct;
	}

	@Override
	public I_C_UOM getCuUOM()
	{
		return cuUOM;
	}

	@Override
	public BigDecimal getCuPerTU()
	{
		return cuPerTU;
	}

	@Override
	public BigDecimal getTuPerLU()
	{
		return tuPerLU;
	}

	@Override
	public BigDecimal getMaxLUToAllocate()
	{
		return maxLUToAllocate;
	}
}
