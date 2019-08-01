package de.metas.quantity;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.math.BigDecimal;

import org.compiere.model.I_C_UOM;

import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.business
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

@UtilityClass
public class StockQtyAndUOMQtys
{
	public static StockQtyAndUOMQty createZero(
			@NonNull final ProductId productId,
			@NonNull final UomId uomId)
	{
		final IProductBL productBL = Services.get(IProductBL.class);
		final IUOMDAO uomDao = Services.get(IUOMDAO.class);

		final I_C_UOM uomRecord = uomDao.getById(uomId);
		final I_C_UOM stockUOMRecord = productBL.getStockingUOM(productId);

		return StockQtyAndUOMQty.builder()
				.productId(productId)
				.stockQty(Quantity.zero(stockUOMRecord))
				.uomQty(Quantity.zero(uomRecord))
				.build();
	}

	public static StockQtyAndUOMQty create(
			@NonNull final ProductId productId,
			@NonNull final BigDecimal qtyInStockUOM,
			@NonNull final UomId uomId,
			@NonNull final BigDecimal qtyInUOM)
	{
		final IProductBL productBL = Services.get(IProductBL.class);

		final I_C_UOM stockUOMRecord = productBL.getStockingUOM(productId);
		final I_C_UOM uomRecord = loadOutOfTrx(uomId, I_C_UOM.class);

		final Quantity stockQty = Quantity.of(qtyInStockUOM, stockUOMRecord);
		final Quantity uomQty = Quantity.of(qtyInUOM, uomRecord);

		return StockQtyAndUOMQty.builder()
				.productId(productId)
				.stockQty(stockQty)
				.uomQty(uomQty)
				.build();
	}

	/**
	 * @return instance with {@link StockQtyAndUOMQty#getUOMQty()} being present.
	 */
	public StockQtyAndUOMQty createUsingUOMConversion(
			@NonNull final ProductId productId,
			@NonNull final BigDecimal qtyInStockUOM,
			@NonNull final UomId otherUomId)
	{
		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
		final IProductBL productBL = Services.get(IProductBL.class);

		final I_C_UOM stockUOMRecord = productBL.getStockingUOM(productId);

		final Quantity stockQty = Quantity.of(qtyInStockUOM, stockUOMRecord);
		final Quantity uomQty = uomConversionBL.convertQuantityTo(stockQty, UOMConversionContext.of(productId), otherUomId);

		return StockQtyAndUOMQty.builder()
				.productId(productId)
				.stockQty(stockQty)
				.uomQty(uomQty)
				.build();
	}

	public static StockQtyAndUOMQty add(
			@NonNull final StockQtyAndUOMQty firstAugent,
			@NonNull final StockQtyAndUOMQty secondAugent)
	{
		final ProductId productId = extractCommonProductId(firstAugent, secondAugent);

		final Quantity stockQty1 = firstAugent.getStockQty();
		final Quantity stockQty2 = secondAugent.getStockQty();

		final Quantity stockQtySum = Quantitys.add(
				UOMConversionContext.of(firstAugent.getProductId()),
				stockQty1,
				stockQty2);

		final Quantity uomQtySum = Quantitys.add(
				UOMConversionContext.of(productId),
				firstAugent.getUOMQty().orElse(stockQty1),
				secondAugent.getUOMQty().orElse(stockQty2));

		return StockQtyAndUOMQty.builder()
				.productId(productId)
				.stockQty(stockQtySum)
				.uomQty(uomQtySum)
				.build();
	}

	private static ProductId extractCommonProductId(
			@NonNull final StockQtyAndUOMQty firstAugent,
			@NonNull final StockQtyAndUOMQty secondAugent)
	{
		Check.assumeEquals(
				firstAugent.getProductId(),
				secondAugent.getProductId(),
				"The two augents need to have an equal productId; firstAugent={}; secondAugent={}", firstAugent, secondAugent);

		return firstAugent.getProductId();
	}

	public static StockQtyAndUOMQty subtract(
			@NonNull final StockQtyAndUOMQty firstAugent,
			@NonNull final StockQtyAndUOMQty subtrahent)
	{
		final ProductId productId = extractCommonProductId(firstAugent, subtrahent);

		final Quantity stockQty1 = firstAugent.getStockQty();
		final Quantity stockQty2 = subtrahent.getStockQty();

		final Quantity stockQtySum = Quantitys.subtract(
				UOMConversionContext.of(firstAugent.getProductId()),
				stockQty1,
				stockQty2);

		final Quantity uomQtySum = Quantitys.subtract(
				UOMConversionContext.of(productId),
				firstAugent.getUOMQty().orElse(stockQty1),
				subtrahent.getUOMQty().orElse(stockQty2));

		return StockQtyAndUOMQty.builder()
				.productId(productId)
				.stockQty(stockQtySum)
				.uomQty(uomQtySum)
				.build();
	}
}
