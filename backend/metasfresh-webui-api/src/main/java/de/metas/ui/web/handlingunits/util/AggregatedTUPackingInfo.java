package de.metas.ui.web.handlingunits.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.compiere.model.I_C_UOM;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.logging.LogManager;
import de.metas.util.Services;

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

class AggregatedTUPackingInfo implements IHUPackingInfo
{
	private static final Logger logger = LogManager.getLogger(AggregatedTUPackingInfo.class);

	private final I_M_HU aggregatedTU;
	private final Supplier<IHUProductStorage> huProductStorageSupplier;

	public AggregatedTUPackingInfo(final I_M_HU aggregatedTU)
	{
		this.aggregatedTU = aggregatedTU;
		huProductStorageSupplier = Suppliers.memoize(() -> {
			final List<IHUProductStorage> productStorages = Services.get(IHandlingUnitsBL.class)
					.getStorageFactory()
					.getStorage(aggregatedTU)
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
		return MoreObjects.toStringHelper(this).addValue(aggregatedTU).toString();
	}

	private IHUProductStorage getHUProductStorage()
	{
		return huProductStorageSupplier.get();
	}

	@Override
	public I_M_HU_PI getM_LU_HU_PI()
	{
		return null; // no LU
	}

	@Override
	public I_M_HU_PI getM_TU_HU_PI()
	{
		final I_M_HU_PI tuPI = Services.get(IHandlingUnitsBL.class).getEffectivePI(aggregatedTU);
		if(tuPI == null)
		{
			new HUException("Invalid aggregated TU. Effective PI could not be fetched; aggregatedTU=" + aggregatedTU).throwIfDeveloperModeOrLogWarningElse(logger);
			return null;
		}
		return tuPI;
	}

	@Override
	public boolean isInfiniteQtyTUsPerLU()
	{
		return false;
	}

	@Override
	public BigDecimal getQtyTUsPerLU()
	{
		final I_M_HU_Item parentHUItem = aggregatedTU.getM_HU_Item_Parent();
		if (parentHUItem == null)
		{
			// note: shall not happen because we assume the aggregatedTU is really an aggregated TU.
			new HUException("Invalid aggregated TU. Parent item is null; aggregatedTU=" + aggregatedTU).throwIfDeveloperModeOrLogWarningElse(logger);
			return null;
		}

		return parentHUItem.getQty();
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
		if (huProductStorage == null)
		{
			return null;
		}

		final BigDecimal qtyTUsPerLU = getQtyTUsPerLU();
		if (qtyTUsPerLU == null || qtyTUsPerLU.signum() == 0)
		{
			return null;
		}

		final BigDecimal qtyCUTotal = huProductStorage.getQty().toBigDecimal();

		final BigDecimal qtyCUsPerTU = qtyCUTotal.divide(qtyTUsPerLU, 0, RoundingMode.HALF_UP);
		return qtyCUsPerTU;
	}

	@Override
	public I_C_UOM getQtyCUsPerTU_UOM()
	{
		final IHUProductStorage huProductStorage = getHUProductStorage();
		if (huProductStorage == null)
		{
			return null;
		}

		return huProductStorage.getC_UOM();
	}

}
