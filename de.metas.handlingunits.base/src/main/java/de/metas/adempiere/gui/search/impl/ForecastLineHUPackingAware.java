package de.metas.adempiere.gui.search.impl;

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
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.adempiere.gui.search.IHUPackingAware;
import de.metas.handlingunits.model.I_M_ForecastLine;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;

public class ForecastLineHUPackingAware implements IHUPackingAware
{
	public static final ForecastLineHUPackingAware of(final org.compiere.model.I_M_ForecastLine forecastLine)
	{
		return new ForecastLineHUPackingAware(InterfaceWrapperHelper.create(forecastLine, I_M_ForecastLine.class));
	}


	private final I_M_ForecastLine forecastLine;
	private final PlainHUPackingAware values = new PlainHUPackingAware();

	public ForecastLineHUPackingAware(final I_M_ForecastLine forecastLine)
	{
		super();

		Check.assumeNotNull(forecastLine, "orderLine not null");
		this.forecastLine = forecastLine;
	}

	@Override
	public int getM_Product_ID()
	{
		return forecastLine.getM_Product_ID();
	}

	@Override
	public I_M_Product getM_Product()
	{
		return forecastLine.getM_Product();
	}

	@Override
	public void setM_Product_ID(final int productId)
	{
		forecastLine.setM_Product_ID(productId);
	}

	@Override
	public int getM_AttributeSetInstance_ID()
	{
		return forecastLine.getM_AttributeSetInstance_ID();
	}

	@Override
	public void setM_AttributeSetInstance_ID(final int M_AttributeSetInstance_ID)
	{
		forecastLine.setM_AttributeSetInstance_ID(M_AttributeSetInstance_ID);
	}

	@Override
	public I_C_UOM getC_UOM()
	{
		return forecastLine.getC_UOM();
	}

	@Override
	public void setC_UOM(final I_C_UOM uom)
	{
		forecastLine.setC_UOM(uom);
	}

	@Override
	public void setQty(final BigDecimal qty)
	{
		forecastLine.setQty(qty);

		final Properties ctx = InterfaceWrapperHelper.getCtx(forecastLine);
		final BigDecimal qtyCalculated = Services.get(IUOMConversionBL.class).convertToProductUOM(ctx, getM_Product(), getC_UOM(), qty);
		forecastLine.setQtyCalculated(qtyCalculated);
	}

	@Override
	public BigDecimal getQty()
	{
		return forecastLine.getQty();
	}

	@Override
	public int getM_HU_PI_Item_Product_ID()
	{
		final int forecastLine_PIItemProductId = forecastLine.getM_HU_PI_Item_Product_ID();
		if (forecastLine_PIItemProductId > 0)
		{
			return forecastLine_PIItemProductId;
		}

		return -1;
	}

	@Override
	public I_M_HU_PI_Item_Product getM_HU_PI_Item_Product()
	{
		final I_M_HU_PI_Item_Product il_piip = forecastLine.getM_HU_PI_Item_Product();
		if(il_piip != null)
		{
			return il_piip;
		}

		return null;
	}

	@Override
	public void setM_HU_PI_Item_Product(final I_M_HU_PI_Item_Product huPiItemProduct)
	{
		forecastLine.setM_HU_PI_Item_Product(huPiItemProduct);
	}

	@Override
	public BigDecimal getQtyTU()
	{
		return forecastLine.getQtyEnteredTU();
	}

	@Override
	public void setQtyTU(final BigDecimal qtyPacks)
	{
		forecastLine.setQtyEnteredTU(qtyPacks);
	}

	@Override
	public void setC_BPartner(final I_C_BPartner partner)
	{
		values.setC_BPartner(partner);
	}

	@Override
	public I_C_BPartner getC_BPartner()
	{
		return values.getC_BPartner();
	}

	@Override
	public void setDateOrdered(final Timestamp dateOrdered)
	{
		values.setDateOrdered(dateOrdered);
	}

	@Override
	public Timestamp getDateOrdered()
	{
		return values.getDateOrdered();
	}

	@Override
	public boolean isInDispute()
	{
		return values.isInDispute();
	}

	@Override
	public void setInDispute(final boolean inDispute)
	{
		values.setInDispute(inDispute);
	}
}
