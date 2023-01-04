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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.product.IssuingToleranceSpec;
import de.metas.product.ProductId;
import de.metas.product.UpdateProductRequest;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Optionals;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.eevolution.api.BOMComponentType;
import org.eevolution.api.BOMType;
import org.eevolution.api.IProductBOMBL;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.api.IProductLowLevelUpdater;
import org.eevolution.api.ProductBOMId;
import org.eevolution.api.ProductBOMQtys;
import org.eevolution.api.QtyCalculationsBOM;
import org.eevolution.api.QtyCalculationsBOMLine;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ProductBOMBL implements IProductBOMBL
{
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IProductBOMDAO bomDAO = Services.get(IProductBOMDAO.class);

	@Override
	public boolean isValidFromTo(final I_PP_Product_BOM productBOM, final Date date)
	{
		final Date validFrom = productBOM.getValidFrom();
		if (validFrom != null && date.before(validFrom))
		{
			return false;
		}

		final Date validTo = productBOM.getValidTo();
		return validTo == null || !date.after(validTo);
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
		return validTo == null || !date.after(validTo);
	}

	@Override
	public void updateIsBOMFlag(@NonNull final ProductId productId)
	{
		final boolean hasBOMs = bomDAO.hasBOMs(productId);

		productDAO.updateProduct(UpdateProductRequest.builder()
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

	@Override
	public boolean isValidVariantGroup(final I_PP_Product_BOMLine bomLine)
	{
		final BOMComponentType currentComponentType = BOMComponentType.ofCode(bomLine.getComponentType());
		if (!currentComponentType.isVariant())
		{
			return true;
		}

		boolean isComponentOrPacking = false;
		final List<I_PP_Product_BOMLine> bomLines = bomDAO.retrieveLines(bomLine.getPP_Product_BOM());
		for (final I_PP_Product_BOMLine bl : bomLines)
		{
			final BOMComponentType componentType = BOMComponentType.ofCode(bl.getComponentType());
			if (componentType.isComponentOrPacking() && bomLine.getVariantGroup().equals(bl.getVariantGroup()))
			{
				isComponentOrPacking = true;
			}
		}

		return isComponentOrPacking;
	}

	@Override
	public BigDecimal computeQtyRequired(
			@NonNull final I_PP_Product_BOMLine bomLine,
			@NonNull final ProductId finishedGoodProductId,
			@NonNull final BigDecimal finishedGoodQty)
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
		return ProductBOMQtys.computeQtyWithScrap(qtyRequired, qtyScrap);
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
		final UomId endUOMId = productBL.getStockUOMId(finishedGoodProductId);

		final UomId bomLineUOMId = UomId.ofRepoId(bomLine.getC_UOM_ID());

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
		final I_C_UOM uom = uomDAO.getById(bomLine.getC_UOM_ID());
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
			qty = ProductBOMQtys.computeQtyWithScrap(qty, scrap);
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

	@Override
	public List<QtyCalculationsBOM> getQtyCalculationBOMs(
			final @NonNull Set<ProductId> finishGoodIds,
			final @NonNull BOMType bomType)
	{
		if (finishGoodIds.isEmpty())
		{
			return ImmutableList.of();
		}

		final HashMap<ProductBOMId, I_PP_Product_BOM> boms = new HashMap<>();

		final List<I_M_Product> finishGoods = productDAO.getByIds(finishGoodIds);

		for (final I_M_Product finishGood : finishGoods)
		{
			final ProductId finishedGoodProductId = ProductId.ofRepoId(finishGood.getM_Product_ID());

			bomDAO.getDefaultBOM(finishedGoodProductId, bomType)
					.ifPresent(bom -> boms.put(ProductBOMId.ofRepoId(bom.getPP_Product_BOM_ID()), bom));
		}

		if (boms.isEmpty())
		{
			return ImmutableList.of();
		}

		final ImmutableListMultimap<ProductBOMId, I_PP_Product_BOMLine> bomLines = Multimaps.index(
				bomDAO.retrieveLinesByBOMIds(boms.keySet()),
				bomLine -> ProductBOMId.ofRepoId(bomLine.getPP_Product_BOM_ID()));

		final ArrayList<QtyCalculationsBOM> qtyCalculationsBOMs = new ArrayList<>(boms.size());
		for (final ProductBOMId bomId : boms.keySet())
		{
			final QtyCalculationsBOM qtyCalculationsBOM = toQtyCalculationsBOM(boms.get(bomId), bomLines.get(bomId));
			qtyCalculationsBOMs.add(qtyCalculationsBOM);
		}

		return ImmutableList.copyOf(qtyCalculationsBOMs);
	}

	private QtyCalculationsBOM toQtyCalculationsBOM(final I_PP_Product_BOM bom, final List<I_PP_Product_BOMLine> bomLines)
	{
		return QtyCalculationsBOM.builder()
				.lines(bomLines.stream()
						.map(bomLine -> toQtyCalculationsBOMLine(bomLine, bom))
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	@Override
	public QtyCalculationsBOMLine toQtyCalculationsBOMLine(
			@NonNull final I_PP_Product_BOMLine productBOMLine,
			@NonNull final I_PP_Product_BOM bom)
	{
		return QtyCalculationsBOMLine.builder()
				.bomProductId(ProductId.ofRepoId(bom.getM_Product_ID()))
				.bomProductUOM(uomDAO.getById(bom.getC_UOM_ID()))
				.componentType(BOMComponentType.ofCode(productBOMLine.getComponentType()))
				//
				.productId(ProductId.ofRepoId(productBOMLine.getM_Product_ID()))
				.qtyPercentage(productBOMLine.isQtyPercentage())
				.qtyForOneFinishedGood(productBOMLine.getQtyBOM())
				.percentOfFinishedGood(Percent.of(productBOMLine.getQtyBatch()))
				.scrap(Percent.of(productBOMLine.getScrap()))
				//
				.uom(uomDAO.getById(productBOMLine.getC_UOM_ID()))
				//
				.build();
	}

	@Override
	public Optional<IssuingToleranceSpec> getEffectiveIssuingToleranceSpec(@NonNull final I_PP_Product_BOMLine bomLine)
	{
		return Optionals.firstPresentOfSuppliers(
				() -> ProductBOMDAO.extractIssuingToleranceSpec(bomLine),
				() -> productBL.getIssuingToleranceSpec(ProductId.ofRepoId(bomLine.getM_Product_ID()))
		);
	}
}
