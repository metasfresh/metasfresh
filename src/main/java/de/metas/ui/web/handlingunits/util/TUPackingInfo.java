package de.metas.ui.web.handlingunits.util;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;

import com.google.common.base.MoreObjects;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.storage.IHUProductStorage;
import lombok.NonNull;

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

class TUPackingInfo implements IHUPackingInfo
{
	private final I_M_HU tuHU;
	private final Supplier<IHUProductStorage> huProductStorageSupplier;

	TUPackingInfo(@NonNull final I_M_HU tuHU)
	{
		this.tuHU = tuHU;

		huProductStorageSupplier = Suppliers.memoize(() -> {
			final List<IHUProductStorage> productStorages = Services.get(IHandlingUnitsBL.class)
					.getStorageFactory()
					.getStorage(tuHU)
					.getProductStorages();
			if (productStorages.size() == 1)
			{
				return productStorages.get(0);
			}
			else
			{
				return null;
			}
		});
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this).addValue(tuHU).toString();
	}

	private IHUProductStorage getHUProductStorage()
	{
		return huProductStorageSupplier.get();
	}

	@Override
	public I_M_HU_PI getM_LU_HU_PI()
	{
		return null;
	}

	@Override
	public I_M_HU_PI getM_TU_HU_PI()
	{
		return tuHU.getM_HU_PI_Version().getM_HU_PI();
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
		return false;
	}

	@Override
	public BigDecimal getQtyCUsPerTU()
	{
		final IHUProductStorage huProductStorage = getHUProductStorage();
		return huProductStorage == null ? null : huProductStorage.getQty();
	}

	@Override
	public I_C_UOM getQtyCUsPerTU_UOM()
	{
		final IHUProductStorage huProductStorage = getHUProductStorage();
		return huProductStorage == null ? null : huProductStorage.getC_UOM();
	}

}
