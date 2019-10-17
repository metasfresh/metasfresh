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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_UOM;

import de.metas.adempiere.gui.search.IHUPackingAware;
import de.metas.handlingunits.model.I_M_ForecastLine;
import de.metas.product.ProductId;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMDAO;
import de.metas.util.Check;
import de.metas.util.Services;

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
	public int getC_UOM_ID()
	{
		return forecastLine.getC_UOM_ID();
	}

	@Override
	public void setC_UOM_ID(final int uomId)
	{
		forecastLine.setC_UOM_ID(uomId);
	}

	@Override
	public void setQty(final BigDecimal qty)
	{
		forecastLine.setQty(qty);

		final ProductId productId = ProductId.ofRepoIdOrNull(getM_Product_ID());
		final I_C_UOM uom = Services.get(IUOMDAO.class).getById(getC_UOM_ID());
		final BigDecimal qtyCalculated = Services.get(IUOMConversionBL.class).convertToProductUOM(productId, uom, qty);
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
	public void setM_HU_PI_Item_Product_ID(final int huPiItemProductId)
	{
		forecastLine.setM_HU_PI_Item_Product_ID(huPiItemProductId);
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
	public void setC_BPartner_ID(final int partnerId)
	{
		values.setC_BPartner_ID(partnerId);
	}

	@Override
	public int getC_BPartner_ID()
	{
		return values.getC_BPartner_ID();
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
