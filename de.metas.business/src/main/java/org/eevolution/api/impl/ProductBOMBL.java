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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import org.compiere.model.I_C_UOM;
import org.compiere.util.Env;
import org.eevolution.api.BOMComponentType;
import org.eevolution.api.IProductBOMBL;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.api.IProductLowLevelUpdater;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;

import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.product.UpdateProductRequest;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;

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
	public void updateIsBOMFlag(@NonNull final ProductId productId)
	{
		final IProductDAO productsRepo = Services.get(IProductDAO.class);
		final IProductBOMDAO bomsRepo = Services.get(IProductBOMDAO.class);

		final boolean hasBOMs = bomsRepo.hasBOMs(productId);

		productsRepo.updateProduct(UpdateProductRequest.builder()
				.productId(productId)
				.isBOM(hasBOMs)
				.build());
	}

	@Override
	public int calculateProductLowestLevel(final ProductId productId)
	{
		return ProductLowLevelCalculator.newInstance().getLowLevel(productId);
	}

	@Override
	public IProductLowLevelUpdater updateProductLowLevels()
	{
		return new ProductLowLevelUpdater();
	}

	public BigDecimal computeQtyWithScrap(final BigDecimal qty, @NonNull final Percent scrapPercent)
	{
		if (qty == null || qty.signum() == 0)
		{
			return BigDecimal.ZERO;
		}

		if (scrapPercent.isZero())
		{
			return qty;
		}

		final int precision = 8;
		return scrapPercent.addToBase(qty, precision);
	}

	@Override
	public boolean isValidVariantGroup(final I_PP_Product_BOMLine bomLine)
	{
		final BOMComponentType currentComponentType = BOMComponentType.ofCode(bomLine.getComponentType());
		if (!currentComponentType.isVariant())
		{
			return true;
		}

		boolean isComponentOrPacking = false;
		final IProductBOMDAO bomDAO = Services.get(IProductBOMDAO.class);
		final List<I_PP_Product_BOMLine> bomLines = bomDAO.retrieveLines(bomLine.getPP_Product_BOM());
		for (I_PP_Product_BOMLine bl : bomLines)
		{
			final BOMComponentType componentType = BOMComponentType.ofCode(bl.getComponentType());
			if (componentType.isComponentOrPacking() && bomLine.getVariantGroup().equals(bl.getVariantGroup()))
			{
				isComponentOrPacking = true;
				continue;
			}
		}

		return isComponentOrPacking;
	}

	@Override
	public BigDecimal computeQtyRequired(
			@NonNull I_PP_Product_BOMLine bomLine,
			@NonNull ProductId finishedGoodProductId,
			@NonNull BigDecimal finishedGoodQty)
	{
		final BigDecimal multiplier = computeQtyMultiplier(bomLine, finishedGoodProductId);

		final BigDecimal qtyRequired;
		final BOMComponentType componentType = BOMComponentType.ofCode(bomLine.getComponentType());
		if (componentType.isTools())
		{
			qtyRequired = multiplier;
		}
		else
		{
			qtyRequired = finishedGoodQty.multiply(multiplier).setScale(8, RoundingMode.UP);
		}

		//
		// Adjust the qtyRequired by adding the scrap percentage to it.
		final Percent qtyScrap = Percent.of(bomLine.getScrap());
		final BigDecimal qtyRequiredPlusScrap = computeQtyWithScrap(qtyRequired, qtyScrap);
		return qtyRequiredPlusScrap;
	}

	@Override
	public BigDecimal computeQtyMultiplier(
			@NonNull final I_PP_Product_BOMLine bomLine,
			@NonNull final ProductId finishedGoodProductId)
	{
		if (!bomLine.isQtyPercentage())
		{
			return bomLine.getQtyBOM();
		}

		// We also need to multiply by BOM UOM to BOM Line UOM multiplier
		// see http://dewiki908/mediawiki/index.php/06973_Fix_percentual_BOM_line_quantities_calculation_%28108941319640%29
		final IProductBL productBL = Services.get(IProductBL.class);
		final UomId endUOMId = productBL.getStockUOMId(finishedGoodProductId);

		final UomId bomLineUOMId = UomId.ofRepoId(bomLine.getC_UOM_ID());

		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
		final UOMConversionContext uomConversionCtx = UOMConversionContext.of(finishedGoodProductId);
		final BigDecimal bomToLineUOMMultiplier = uomConversionBL.convertQty(uomConversionCtx, BigDecimal.ONE, endUOMId, bomLineUOMId);

		final Percent qtyBatchPercent = Percent.of(bomLine.getQtyBatch());
		return qtyBatchPercent.computePercentageOf(bomToLineUOMMultiplier, 8);
	}

	@Override
	public Quantity getQtyIncludingScrap(@NonNull final I_PP_Product_BOMLine bomLine)
	{
		final boolean includeScrapQty = true;
		return getQty(bomLine, includeScrapQty);
	}

	@Override
	public Quantity getQtyExcludingScrap(@NonNull final I_PP_Product_BOMLine bomLine)
	{
		final boolean includeScrapQty = false;
		return getQty(bomLine, includeScrapQty);
	}

	@Override
	public Percent getCoProductCostDistributionPercent(final I_PP_Product_BOMLine bomLine)
	{
		final BOMComponentType bomComponentType = BOMComponentType.ofCode(bomLine.getComponentType());
		Check.assume(bomComponentType.isCoProduct(), "Only co-products are allowing cost distribution percent but not {}, {}", bomComponentType, bomLine);

		final BigDecimal qty = getQtyExcludingScrap(bomLine).toBigDecimal().negate();
		return Percent.of(BigDecimal.ONE, qty, 4);
	}

	/**
	 * Return absolute (unified) quantity value. If IsQtyPercentage then QtyBatch / 100 will be returned. Else QtyBOM will be returned.
	 *
	 * @param includeScrapQty if true, scrap qty will be used for calculating qty
	 * @return qty
	 */
	private Quantity getQty(@NonNull final I_PP_Product_BOMLine bomLine, final boolean includeScrapQty)
	{
		final IUOMDAO uomsRepo = Services.get(IUOMDAO.class);

		final I_C_UOM uom = uomsRepo.getById(bomLine.getC_UOM_ID());
		int precision = uom.getStdPrecision();
		BigDecimal qty;
		if (bomLine.isQtyPercentage())
		{
			precision += 2;
			qty = bomLine.getQtyBatch().divide(Env.ONEHUNDRED, precision, RoundingMode.HALF_UP);
		}
		else
		{
			qty = bomLine.getQtyBOM();
		}
		//
		if (includeScrapQty)
		{
			final Percent scrap = Percent.of(bomLine.getScrap());
			qty = computeQtyWithScrap(qty, scrap);
		}
		//
		if (qty.scale() > precision)
		{
			qty = qty.setScale(precision, RoundingMode.HALF_UP);
		}
		//
		return Quantity.of(qty, uom);
	}

	@Override
	public String getBOMDescriptionForProductId(@NonNull final ProductId productId)
	{
		return ProductBOMDescriptionBuilder.newInstance()
				.build(productId);
	}
}
