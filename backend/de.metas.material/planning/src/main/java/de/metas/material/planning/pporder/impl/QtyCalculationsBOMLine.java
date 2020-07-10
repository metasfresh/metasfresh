package de.metas.material.planning.pporder.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.annotation.Nullable;

import org.compiere.model.I_C_UOM;
import org.eevolution.api.BOMComponentType;
import org.eevolution.api.ProductBOMQtys;

import com.google.common.annotations.VisibleForTesting;

import de.metas.material.planning.pporder.PPOrderBOMLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UOMConversionRate;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * metasfresh-material-planning
 * %%
 * Copyright (C) 2019 metas GmbH
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
 * BOM Line used for various quantity calculations
 */
@EqualsAndHashCode
@ToString
public final class QtyCalculationsBOMLine
{
	private final IUOMConversionBL uomConversionService = Services.get(IUOMConversionBL.class);

	private final ProductId bomProductId;
	private final I_C_UOM bomProductUOM;

	private final BOMComponentType componentType;

	@Getter
	private final ProductId productId;
	private final I_C_UOM uom;
	private final boolean qtyPercentage;
	private final BigDecimal qtyForOneFinishedGood;
	private final Percent percentOfFinishedGood;
	private final Percent scrap;

	// References
	@Getter
	private final PPOrderBOMLineId orderBOMLineId;

	@Builder
	private QtyCalculationsBOMLine(
			@NonNull final ProductId bomProductId,
			@NonNull final I_C_UOM bomProductUOM,
			//
			@NonNull final BOMComponentType componentType,
			//
			@NonNull final ProductId productId,
			@NonNull final I_C_UOM uom,
			final boolean qtyPercentage,
			final BigDecimal qtyForOneFinishedGood,
			final Percent percentOfFinishedGood,
			//
			@Nullable final Percent scrap,
			//
			@Nullable PPOrderBOMLineId orderBOMLineId)
	{
		this.bomProductId = bomProductId;
		this.bomProductUOM = bomProductUOM;
		this.componentType = componentType;

		this.productId = productId;
		this.uom = uom;
		this.qtyPercentage = qtyPercentage;
		if (qtyPercentage)
		{
			Check.assumeNotNull(percentOfFinishedGood, "Parameter qtyBatch is not null");
			this.qtyForOneFinishedGood = null;
			this.percentOfFinishedGood = percentOfFinishedGood;
		}
		else
		{
			Check.assumeNotNull(qtyForOneFinishedGood, "Parameter qtyBOM is not null");
			this.qtyForOneFinishedGood = qtyForOneFinishedGood;
			this.percentOfFinishedGood = null;
		}

		this.scrap = scrap != null ? scrap : Percent.ZERO;

		this.orderBOMLineId = orderBOMLineId;
	}

	public Quantity computeQtyRequired(@NonNull final Quantity qtyFinishedGood)
	{
		Check.assumeEquals(qtyFinishedGood.getUomId().getRepoId(), bomProductUOM.getC_UOM_ID(), "{} shall have uom={}", qtyFinishedGood, bomProductUOM);

		return computeQtyRequired(qtyFinishedGood.toBigDecimal());
	}

	public Quantity computeQtyRequired(@NonNull final BigDecimal qtyFinishedGood)
	{
		final BigDecimal multiplier = getFinishedGoodQtyMultiplier();

		final BigDecimal qtyRequired;
		if (componentType.isTools())
		{
			qtyRequired = multiplier;
		}
		else
		{
			qtyRequired = qtyFinishedGood.multiply(multiplier).setScale(8, RoundingMode.UP);
		}

		//
		// Adjust the qtyRequired by adding the scrap percentage to it.
		final BigDecimal qtyRequiredPlusScrap = ProductBOMQtys.computeQtyWithScrap(qtyRequired, scrap);
		return Quantity.of(qtyRequiredPlusScrap, uom);
	}

	@VisibleForTesting
	BigDecimal getFinishedGoodQtyMultiplier()
	{
		if (qtyPercentage)
		{
			//
			// We also need to multiply by BOM UOM to BOM Line UOM multiplier
			// see http://dewiki908/mediawiki/index.php/06973_Fix_percentual_BOM_line_quantities_calculation_%28108941319640%29
			final UOMConversionRate bomToLineRate = uomConversionService.getRate(bomProductId,
					UomId.ofRepoId(bomProductUOM.getC_UOM_ID()),
					UomId.ofRepoId(uom.getC_UOM_ID()));
			final BigDecimal bomToLineUOMMultiplier = bomToLineRate.getFromToMultiplier();
			return percentOfFinishedGood.computePercentageOf(bomToLineUOMMultiplier, 12);
		}
		else
		{
			return qtyForOneFinishedGood;
		}
	}

	public Quantity computeQtyOfFinishedGoodsForComponentQty(@NonNull final BigDecimal componentsQty)
	{
		return computeQtyOfFinishedGoodsForComponentQty(Quantity.of(componentsQty, uom));
	}

	public Quantity computeQtyOfFinishedGoodsForComponentQty(@NonNull final Quantity componentsQty)
	{
		final BigDecimal qtyForOneFinishedGoodMultiplier = getFinishedGoodQtyMultiplier();

		final Quantity componentsQtyConverted = uomConversionService.convertQuantityTo(componentsQty,
				UOMConversionContext.of(productId),
				uom);

		final BigDecimal qtyOfFinishedGoodsBD = componentsQtyConverted
				.toBigDecimal()
				.divide(
						qtyForOneFinishedGoodMultiplier,
						bomProductUOM.getStdPrecision(),
						RoundingMode.DOWN); // IMPORTANT to round DOWN because we need complete products.

		return Quantity.of(qtyOfFinishedGoodsBD, bomProductUOM);
	}
}
