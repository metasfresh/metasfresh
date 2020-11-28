package de.metas.ui.web.handlingunits.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.compiere.model.I_C_UOM;
import org.compiere.util.DisplayType;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU_PI;
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

/**
 * {@link IHUPackingInfo} formatter.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class HUPackingInfoFormatter
{
	public static final HUPackingInfoFormatter newInstance()
	{
		return new HUPackingInfoFormatter();
	}

	private boolean _showLU = true;

	/**
	 * Format given packing info.
	 *
	 * If you want to quickly create some {@link IHUPackingInfo} instances from other objects, please check the {@link HUPackingInfos} facade.
	 *
	 * @param huPackingInfo
	 * @return formatted packing info string
	 */
	public String format(final IHUPackingInfo huPackingInfo)
	{
		final StringBuilder packingInfo = new StringBuilder();

		//
		// LU
		if (isShowLU())
		{
			final I_M_HU_PI luPI = huPackingInfo.getM_LU_HU_PI();
			if (luPI != null)
			{
				packingInfo.append(luPI.getName());
			}
		}

		//
		// TU
		final I_M_HU_PI tuPI = huPackingInfo.getM_TU_HU_PI();
		if (tuPI != null && !Services.get(IHandlingUnitsBL.class).isVirtual(tuPI))
		{
			if (packingInfo.length() > 0)
			{
				packingInfo.append(" x ");
			}

			final BigDecimal qtyTU = huPackingInfo.getQtyTUsPerLU();
			if (!huPackingInfo.isInfiniteQtyTUsPerLU() && qtyTU != null && qtyTU.signum() > 0)
			{
				packingInfo.append(qtyTU.intValue()).append(" ");
			}

			packingInfo.append(tuPI.getName());
		}

		//
		// CU
		final BigDecimal qtyCU = huPackingInfo.getQtyCUsPerTU();
		if (!huPackingInfo.isInfiniteQtyCUsPerTU() && qtyCU != null && qtyCU.signum() > 0)
		{
			if (packingInfo.length() > 0)
			{
				packingInfo.append(" x ");
			}

			final DecimalFormat qtyFormat = DisplayType.getNumberFormat(DisplayType.Quantity);
			packingInfo.append(qtyFormat.format(qtyCU));

			final I_C_UOM uom = huPackingInfo.getQtyCUsPerTU_UOM();
			final String uomSymbol = uom == null ? null : uom.getUOMSymbol();
			if (uomSymbol != null)
			{
				packingInfo.append(" ").append(uomSymbol);
			}
		}

		if (packingInfo.length() == 0)
		{
			return null; // no override
		}
		return packingInfo.toString();
	}

	public HUPackingInfoFormatter setShowLU(final boolean showLU)
	{
		_showLU = showLU;
		return this;
	}

	private boolean isShowLU()
	{
		return _showLU;
	}
}
