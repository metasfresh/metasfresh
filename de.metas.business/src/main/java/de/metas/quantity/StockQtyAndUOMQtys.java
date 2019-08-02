package de.metas.quantity;

import java.math.BigDecimal;

import javax.annotation.Nullable;

import org.compiere.model.I_C_UOM;

import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.StockQtyAndUOMQty.StockQtyAndUOMQtyBuilder;
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
	/**
	 * @param uomId if not {@code null} then the result also has a zero {@code uomQty}.
	 */
	public static StockQtyAndUOMQty createZero(@NonNull final ProductId productId, @Nullable final UomId uomId)
	{
		final IProductBL productBL = Services.get(IProductBL.class);
		final I_C_UOM stockUOMRecord = productBL.getStockingUOM(productId);

		final StockQtyAndUOMQtyBuilder result = StockQtyAndUOMQty.builder()
				.productId(productId)
				.stockQty(Quantity.zero(stockUOMRecord));

		if (uomId != null)
		{
			final IUOMDAO uomDao = Services.get(IUOMDAO.class);
			final I_C_UOM uomRecord = uomDao.getById(uomId);
			result.uomQty(Quantity.zero(uomRecord));
		}

		return result.build();
	}

	/**
	 * @param uomId may be {@code null} in which case the result will contain no {@code uomQty}.
	 * @param qtyInUOM may be {@code null} only if {@code uomId} is {@code null}.
	 */
	public StockQtyAndUOMQty create(
			@NonNull final ProductId productId,
			@NonNull final BigDecimal qtyInStockUOM,
			@Nullable final UomId uomId,
			@Nullable final BigDecimal qtyInUOM)
	{
		final Quantity stockQty = createStockQty(productId, qtyInStockUOM);

		final IUOMDAO uomDao = Services.get(IUOMDAO.class);
		final I_C_UOM uomRecord = uomDao.getById(uomId);

		final StockQtyAndUOMQtyBuilder result = StockQtyAndUOMQty.builder()
				.productId(productId)
				.stockQty(stockQty);

		if (uomId != null)
		{
			Check.assumeNotNull(qtyInUOM, "If parameter 'uomId' is not null, then qtyInUOM needs to be not-null too; uomId={}", uomId);

			final Quantity uomQty = Quantity.of(qtyInUOM, uomRecord);
			result.uomQty(uomQty);
		}
		return result.build();
	}

	public StockQtyAndUOMQty create(@NonNull final ProductId productId, @NonNull final BigDecimal qtyInStockUOM)
	{
		final Quantity stockQty = createStockQty(productId, qtyInStockUOM);

		return StockQtyAndUOMQty.builder()
				.productId(productId)
				.stockQty(stockQty)
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
		final Quantity stockQty = createStockQty(productId, qtyInStockUOM);

		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
		final Quantity uomQty = uomConversionBL.convertQuantityTo(stockQty, UOMConversionContext.of(productId), otherUomId);

		return StockQtyAndUOMQty.builder()
				.productId(productId)
				.stockQty(stockQty)
				.uomQty(uomQty)
				.build();
	}

	public StockQtyAndUOMQty createConvertToStockUom(@NonNull final ProductId productId, @NonNull final Quantity stockQtyInAnyUom)
	{
		final IProductBL productBL = Services.get(IProductBL.class);
		final UomId stockUomId = productBL.getStockingUOMId(productId);

		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
		final Quantity stockQty = uomConversionBL.convertQuantityTo(stockQtyInAnyUom, UOMConversionContext.of(productId), stockUomId);

		return StockQtyAndUOMQty.builder()
				.productId(productId)
				.stockQty(stockQty)
				.build();
	}

	public StockQtyAndUOMQty createConvertToStockUom(
			@NonNull final ProductId productId,
			@NonNull final Quantity stockQtyInAnyUom,
			@Nullable final Quantity uomQty)
	{
		final IProductBL productBL = Services.get(IProductBL.class);
		final UomId stockUomId = productBL.getStockingUOMId(productId);

		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
		final Quantity stockQty = uomConversionBL.convertQuantityTo(stockQtyInAnyUom, UOMConversionContext.of(productId), stockUomId);

		return StockQtyAndUOMQty.builder()
				.productId(productId)
				.stockQty(stockQty)
				.uomQty(uomQty)
				.build();
	}

	private Quantity createStockQty(@NonNull final ProductId productId, @NonNull final BigDecimal qtyInStockUOM)
	{
		return createStockQty(productId, qtyInStockUOM, null/* nonStockUomId */);
	}

	/**
	 * @param anyUomId may be {@code null} if given {@code qtyInUOM} is in stock UOM
	 */
	public StockQtyAndUOMQty createConvertToStockUom(
			@NonNull final ProductId productId,
			@NonNull final BigDecimal stockQtyInAnyUOM,
			@Nullable final UomId anyUomId)
	{
		return StockQtyAndUOMQty
				.builder()
				.productId(productId)
				.stockQty(createStockQty(productId, stockQtyInAnyUOM, anyUomId))
				.build();
	}

	private Quantity createStockQty(
			@NonNull final ProductId productId,
			@NonNull final BigDecimal qtyInUOM,
			@Nullable final UomId nonStockUomId)
	{
		final IProductBL productBL = Services.get(IProductBL.class);

		if (nonStockUomId == null)
		{
			final I_C_UOM stockUOMRecord = productBL.getStockingUOM(productId);
			final Quantity stockQty = Quantity.of(qtyInUOM, stockUOMRecord);
			return stockQty;
		}

		final UomId stockUomId = productBL.getStockingUOMId(productId);
		final IUOMDAO uomDao = Services.get(IUOMDAO.class);
		final I_C_UOM nonStockUomRecord = uomDao.getById(nonStockUomId);

		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
		final Quantity stockQty = uomConversionBL.convertQuantityTo(Quantity.of(qtyInUOM, nonStockUomRecord), UOMConversionContext.of(productId), stockUomId);
		return stockQty;
	}

	public StockQtyAndUOMQty add(
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

	public StockQtyAndUOMQty subtract(
			@NonNull final StockQtyAndUOMQty minuend,
			@NonNull final StockQtyAndUOMQty subtrahend)
	{
		final ProductId productId = extractCommonProductId(minuend, subtrahend);

		final Quantity stockQty1 = minuend.getStockQty();
		final Quantity stockQty2 = subtrahend.getStockQty();

		final Quantity stockQtySum = Quantitys.subtract(
				UOMConversionContext.of(minuend.getProductId()),
				stockQty1,
				stockQty2);

		final Quantity uomQtySum = Quantitys.subtract(
				UOMConversionContext.of(productId),
				minuend.getUOMQty().orElse(stockQty1),
				subtrahend.getUOMQty().orElse(stockQty2));

		return StockQtyAndUOMQty.builder()
				.productId(productId)
				.stockQty(stockQtySum)
				.uomQty(uomQtySum)
				.build();
	}

	private ProductId extractCommonProductId(
			@NonNull final StockQtyAndUOMQty firstAugent,
			@NonNull final StockQtyAndUOMQty secondAugent)
	{
		Check.assumeEquals(
				firstAugent.getProductId(),
				secondAugent.getProductId(),
				"The two augents need to have an equal productId; firstAugent={}; secondAugent={}", firstAugent, secondAugent);

		return firstAugent.getProductId();
	}

}
