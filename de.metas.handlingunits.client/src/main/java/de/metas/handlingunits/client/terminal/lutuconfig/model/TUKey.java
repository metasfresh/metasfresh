package de.metas.handlingunits.client.terminal.lutuconfig.model;

/*
 * #%L
 * de.metas.handlingunits.client
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
import java.text.DecimalFormat;

import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.DisplayType;
import org.compiere.util.Util;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.client.terminal.helper.HUTerminalHelper;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.util.Check;
import de.metas.util.NumberUtils;

public class TUKey extends AbstractLUTUKey
{
	private final I_M_HU_PI_Item_Product tuPIItemProduct;
	private final I_M_Product cuProduct;
	private final I_C_UOM cuUOM;
	private final BigDecimal qtyCUsPerTU;
	private final boolean qtyCUsPerTUInfinite;

	public TUKey(final ITerminalContext terminalContext,
			final I_M_HU_PI_Item_Product tuPIItemProduct,
			final I_M_HU_PI huPI,
			final I_M_Product cuProduct,
			final I_C_UOM cuUOM,
			final boolean qtyCUPerTUInfinite,
			final BigDecimal qtyCUPerTU)
	{
		super(terminalContext, huPI);

		//
		this.tuPIItemProduct = tuPIItemProduct;

		Check.assumeNotNull(cuProduct, "cuProduct not null");
		this.cuProduct = cuProduct;

		// Check.assumeNotNull(cuUOM, "cuUOM not null");
		this.cuUOM = cuUOM;

		qtyCUsPerTUInfinite = qtyCUPerTUInfinite;
		if (!qtyCUPerTUInfinite)
		{
			Check.assumeNotNull(qtyCUPerTU, "qtyCUPerTU not null");
			qtyCUsPerTU = qtyCUPerTU;
		}
		else
		{
			qtyCUsPerTU = null;
		}
	}

	@Override
	protected String buildId()
	{
		final int huPIId = getM_HU_PI().getM_HU_PI_ID();
		final int cuProductId = getCuProduct().getM_Product_ID();
		final I_C_UOM cuUOM = getCuUOM();
		final int cuUOMId = cuUOM == null ? -1 : cuUOM.getC_UOM_ID();
		final BigDecimal qtyCUsPerTU = NumberUtils.stripTrailingDecimalZeros(this.qtyCUsPerTU);
		final String id = getClass().getName()
				+ "#M_HU_PI_ID=" + huPIId
				+ "#M_Product_ID=" + cuProductId
				+ "#C_UOM_ID=" + cuUOMId
				+ "#isQtyCUsPerTUInfinite=" + isQtyCUsPerTUInfinite()
				+ "#QtyCUPerTU=" + qtyCUsPerTU;
		return id;
	}

	@Override
	protected final String createName()
	{
		final DecimalFormat qtyFormat = DisplayType.getNumberFormat(DisplayType.Quantity);

		final I_M_HU_PI tuPI = getM_HU_PI();
		final String piName = HUTerminalHelper.truncateName(tuPI.getName());

		final StringBuilder qtyLineStr = new StringBuilder();

		final I_M_Product cuProduct = getCuProduct();
		final I_C_UOM cuUOM = getCuUOM();
		final BigDecimal qtyCUsPerTU = getQtyCUsPerTU();
		final boolean qtyCUsPerTUInfinite = isQtyCUsPerTUInfinite();

		qtyLineStr.append(cuProduct == null ? "?" : HUTerminalHelper.truncateName(cuProduct.getName()));
		if (!qtyCUsPerTUInfinite)
		{
			final String cuCapacityStr = qtyCUsPerTU == null ? "?" : qtyFormat.format(qtyCUsPerTU);
			qtyLineStr.append(" x ").append(cuCapacityStr);
			if (cuUOM != null)
			{
				qtyLineStr.append(" ").append(cuUOM.getUOMSymbol());
			}
		}

		//
		// Build final name
		final StringBuilder name = new StringBuilder();
		name.append("<center>");
		name.append(Util.maskHTML(piName));

		if (qtyLineStr.length() > 0)
		{
			name.append("<br>");
			name.append("(");
			name.append(Util.maskHTML(qtyLineStr.toString()));
			name.append(")");
		}

		name.append("</center>");

		return name.toString();
	}

	public I_M_Product getCuProduct()
	{
		return cuProduct;
	}

	public BigDecimal getQtyCUsPerTU()
	{
		return qtyCUsPerTU;
	}

	public I_C_UOM getCuUOM()
	{
		return cuUOM;
	}

	public boolean isQtyCUsPerTUInfinite()
	{
		return qtyCUsPerTUInfinite;
	}

	public I_M_HU_PI_Item_Product getM_HU_PI_Item_Product()
	{
		return tuPIItemProduct;
	}
}
