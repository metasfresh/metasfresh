package org.eevolution.api.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
import java.util.Date;
import java.util.List;

import org.adempiere.util.Services;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.eevolution.api.IProductBOMBL;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.api.IProductLowLevelUpdater;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;
import org.eevolution.model.X_PP_Order_BOMLine;

public class ProductBOMBL implements IProductBOMBL
{
	@Override
	public boolean isValidFromTo(final I_PP_Product_BOM productBOM, final Date date)
	{
		final Date validFrom = productBOM.getValidFrom();
		if (validFrom != null && date.before(validFrom))
		{
			return false;
		}

		final Date validTo = productBOM.getValidTo();
		if (validTo != null && date.after(validTo))
		{
			return false;
		}
		return true;
	}

	@Override
	public boolean isValidFromTo(final I_PP_Product_BOMLine bomLine, final Date date)
	{
		final Date validFrom = bomLine.getValidFrom();
		if (validFrom != null && date.before(validFrom))
		{
			return false;
		}

		final Date validTo = bomLine.getValidTo();
		if (validTo != null && date.after(validTo))
		{
			return false;
		}
		return true;
	}

	@Override
	public void setIsBOM(final I_M_Product product)
	{
		final boolean hasBOMs = Services.get(IProductBOMDAO.class).hasBOMs(product);
		product.setIsBOM(hasBOMs);
	}

	@Override
	public int calculateProductLowestLevel(final int productId)
	{
		return ProductLowLevelCalculator.newInstance().getLowLevel(productId);
	}

	@Override
	public IProductLowLevelUpdater updateProductLowLevels()
	{
		return new ProductLowLevelUpdater();
	}

	@Override
	public BigDecimal calculateQtyWithScrap(final BigDecimal qty, final BigDecimal qtyScrap)
	{
		if (qty == null || qty.signum() == 0)
		{
			return BigDecimal.ZERO;
		}

		if (qtyScrap == null || qtyScrap.signum() == 0)
		{
			return qty;
		}

		final BigDecimal scrapPerc = qtyScrap.divide(Env.ONEHUNDRED, 8, BigDecimal.ROUND_UP);
		final BigDecimal qtyPlusScrapPerc = BigDecimal.ONE.add(scrapPerc);
		final BigDecimal qtyPlusScap = qty.multiply(qtyPlusScrapPerc);
		return qtyPlusScap;
	}


	@Override
	public boolean isValidVariantGroup(final I_PP_Product_BOMLine bomLine)
	{
		if (!X_PP_Order_BOMLine.COMPONENTTYPE_Variant.equals(bomLine.getComponentType()))
		{
			return true;
		}

		boolean valid = false;
		final IProductBOMDAO bomDAO = Services.get(IProductBOMDAO.class);
		final List<I_PP_Product_BOMLine> bomLines = bomDAO.retrieveLines(bomLine.getPP_Product_BOM());
		for (I_PP_Product_BOMLine bl : bomLines)
		{
			if ((X_PP_Order_BOMLine.COMPONENTTYPE_Component.equals(bl.getComponentType()) || X_PP_Order_BOMLine.COMPONENTTYPE_Packing.equals(bl.getComponentType()))
					&& bomLine.getVariantGroup().equals(bl.getVariantGroup()))
			{
				valid = true;
				continue;
			}
		}

		return valid;
	}
}
