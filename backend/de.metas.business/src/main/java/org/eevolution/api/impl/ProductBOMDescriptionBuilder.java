package org.eevolution.api.impl;

import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.uom.IUOMDAO;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.stream.Collectors;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

final class ProductBOMDescriptionBuilder
{
	public static ProductBOMDescriptionBuilder newInstance()
	{
		return new ProductBOMDescriptionBuilder();
	}

	private final IProductDAO productsRepo = Services.get(IProductDAO.class);
	private final IProductBOMDAO bomsRepo = Services.get(IProductBOMDAO.class);
	private final IUOMDAO uomsRepo = Services.get(IUOMDAO.class);

	private ProductBOMDescriptionBuilder()
	{
	}

	@Nullable
	public String build(@NonNull final ProductId productId)
	{
		final I_PP_Product_BOM bom = bomsRepo.getDefaultBOMByProductId(productId).orElse(null);
		if (bom == null || !bom.isActive())
		{
			return null;
		}

		return bomsRepo.retrieveLines(bom)
				.stream()
				.map(this::toBOMLineString)
				.filter(Objects::nonNull)
				.collect(Collectors.joining("\r\n"))
				.trim();
	}

	@Nullable
	private String toBOMLineString(final I_PP_Product_BOMLine bomLine)
	{
		if (!bomLine.isActive())
		{
			return null;
		}

		final I_M_Product product = productsRepo.getById(bomLine.getM_Product_ID());
		final String qtyStr = toBOMLineQtyAndUOMString(bomLine);

		return (product.getName() + " " + qtyStr).trim();
	}

	private String toBOMLineQtyAndUOMString(final I_PP_Product_BOMLine bomLine)
	{
		if (bomLine.isQtyPercentage())
		{
			return NumberUtils.stripTrailingDecimalZeros(bomLine.getQtyBatch()) + "%";
		}
		else if (BigDecimal.ONE.equals(bomLine.getQtyBOM()))
		{
			return "";
		}
		else
		{
			final StringBuilder qtyStr = new StringBuilder();
			qtyStr.append(NumberUtils.stripTrailingDecimalZeros(bomLine.getQtyBOM()));

			final int uomId = bomLine.getC_UOM_ID();
			if (uomId > 0)
			{
				final I_C_UOM uom = uomsRepo.getById(uomId);
				qtyStr.append(" ").append(uom.getUOMSymbol());
			}

			return qtyStr.toString();
		}
	}
}
