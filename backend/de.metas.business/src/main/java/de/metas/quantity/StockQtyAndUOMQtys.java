package de.metas.quantity;

import com.google.common.collect.ImmutableList;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.StockQtyAndUOMQty.StockQtyAndUOMQtyBuilder;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_UOM;
import org.compiere.util.Util.ArrayKey;

import javax.annotation.Nullable;
import java.math.BigDecimal;

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
		return StockQtyAndUOMQty.builder()
				.productId(productId)
				.stockQty(Quantitys.zero(productId))
				.uomQty(uomId != null ? Quantitys.zero(uomId) : null)
				.build();

		// NOTE: no need to validate(result) because we created valid (using product's stocking UOM)
	}

	public StockQtyAndUOMQty ofQtyInStockUOM(
			@NonNull final BigDecimal qtyInStockUOM,
			@NonNull final ProductId productId)
	{
		final BigDecimal qtyInUOM = null;
		final UomId uomId = null;
		return create(qtyInStockUOM, productId, qtyInUOM, uomId);
	}

	/**
	 * @param qtyInUOM may be {@code null} only if {@code uomId} is {@code null}.
	 * @param uomId    may be {@code null} in which case the result will contain no {@code uomQty}.
	 */
	public StockQtyAndUOMQty create(
			@NonNull final BigDecimal qtyInStockUOM,
			@NonNull final ProductId productId,
			@Nullable final BigDecimal qtyInUOM,
			@Nullable final UomId uomId)
	{
		final Quantity stockQty = Quantitys.of(qtyInStockUOM, productId);

		final StockQtyAndUOMQtyBuilder result = StockQtyAndUOMQty.builder()
				.productId(productId)
				.stockQty(stockQty);

		if (uomId != null)
		{
			final IUOMDAO uomDao = Services.get(IUOMDAO.class);
			final I_C_UOM uomRecord = uomDao.getById(uomId);
			Check.assumeNotNull(qtyInUOM, "If parameter 'uomId' is not null, then qtyInUOM needs to be not-null too; uomId={}", uomId);

			final Quantity uomQty = Quantity.of(qtyInUOM, uomRecord);
			result.uomQty(uomQty);
		}
		else
		{
			Check.assume(qtyInUOM == null || qtyInUOM.signum() == 0, "qtyInUOM={} shall be ZERO when uomId is null", qtyInUOM);
		}

		return validate(result.build());
	}

	/**
	 * @return instance with {@link StockQtyAndUOMQty#getUOMQtyOpt()} being present.
	 */
	public StockQtyAndUOMQty createWithUomQtyUsingConversion(
			@NonNull final BigDecimal qtyInStockUOM,
			@NonNull final ProductId productId,
			@NonNull final UomId otherUomId)
	{
		final Quantity stockQty = Quantitys.of(qtyInStockUOM, productId);

		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
		final Quantity uomQty = uomConversionBL.convertQuantityTo(stockQty, UOMConversionContext.of(productId), otherUomId);

		return validate(
				StockQtyAndUOMQty.builder()
						.productId(productId)
						.stockQty(stockQty)
						.uomQty(uomQty)
						.build());
	}

	public StockQtyAndUOMQty createConvert(
			@NonNull final BigDecimal qtyInStockUom,
			@NonNull final ProductId productId,
			@NonNull final UomId uomId)
	{
		final Quantity stockQty = Quantitys.of(qtyInStockUom, productId);

		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
		final Quantity uomQty = uomConversionBL.convertQuantityTo(stockQty, UOMConversionContext.of(productId), uomId);

		return validate(
				StockQtyAndUOMQty.builder()
						.productId(productId)
						.stockQty(stockQty)
						.uomQty(uomQty)
						.build());
	}

	/**
	 * Converts the given {@code qtyInAnyUom} to both the stockQty and uomQty.
	 */
	public StockQtyAndUOMQty createConvert(
			@NonNull final Quantity qtyInAnyUom,
			@NonNull final ProductId productId,
			@NonNull final UomId uomId)
	{
		final Quantity stockQty = Quantitys.of(qtyInAnyUom.toBigDecimal(), qtyInAnyUom.getUomId(), productId);

		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
		final Quantity uomQty = uomConversionBL.convertQuantityTo(qtyInAnyUom, UOMConversionContext.of(productId), uomId);

		return validate(
				StockQtyAndUOMQty.builder()
						.productId(productId)
						.stockQty(stockQty)
						.uomQty(uomQty)
						.build());
	}

	/**
	 * @param stockQtyInAnyUom converted to the product's stock UOM is needed
	 * @param uomQty           added to the new {@link StockQtyAndUOMQty} as-is. May be {@code null}.
	 */
	public StockQtyAndUOMQty createConvert(
			@NonNull final Quantity stockQtyInAnyUom,
			@NonNull final ProductId productId,
			@Nullable final Quantity uomQty)
	{
		final Quantity stockQty = Quantitys.of(stockQtyInAnyUom.toBigDecimal(), stockQtyInAnyUom.getUomId(), productId);

		return validate(
				StockQtyAndUOMQty.builder()
						.productId(productId)
						.stockQty(stockQty)
						.uomQty(uomQty)
						.build());
	}

	/**
	 * @return the sum of the given quantities; See {@link Quantitys#add(UOMConversionContext, Quantity, Quantity)} for the result's uomQty's UOM.
	 */
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
				firstAugent.getUOMQtyOpt().orElse(stockQty1),
				secondAugent.getUOMQtyOpt().orElse(stockQty2));

		return validate(
				StockQtyAndUOMQty.builder()
						.productId(productId)
						.stockQty(stockQtySum)
						.uomQty(uomQtySum)
						.build());
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
				minuend.getUOMQtyOpt().orElse(stockQty1),
				subtrahend.getUOMQtyOpt().orElse(stockQty2));

		return validate(
				StockQtyAndUOMQty.builder()
						.productId(productId)
						.stockQty(stockQtySum)
						.uomQty(uomQtySum)
						.build());
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

	public static StockQtyAndUOMQty validate(@NonNull final StockQtyAndUOMQty qtys)
	{
		final IProductBL productBL = Services.get(IProductBL.class);

		final UomId productStockUomId = productBL.getStockUOMId(qtys.getProductId());
		if (!UomId.equals(productStockUomId, qtys.getStockQty().getUomId()))
		{
			throw new AdempiereException("Product's stock UOM does not match stockQty's UOM")
					.appendParametersToMessage()
					.setParameter("qtys", qtys);
		}

		// Note: stockQty and UOM might have the same UOM but different amounts!
		// That can happen if e.g. stock UOM and price UOM are PCE, but the catch UOM is KGM!

		return qtys;
	}

	public static void assumeCommonProductAndUom(@NonNull final StockQtyAndUOMQty... qtys)
	{
		// extract an arrayKey from qtys' productId and uomId and assume that there is just one distinct
		final ImmutableList<ArrayKey> differentValues = CollectionUtils.extractDistinctElements(
				CollectionUtils.asSet(qtys),
				item -> ArrayKey.of(item.getProductId(), item.getUOMQtyOpt().map(Quantity::getUomId).orElse(null)));
		if (differentValues.size() > 1)
		{
			throw new AdempiereException("The given StockQtyAndUOMQtys have different productIds and/or uomIds")
					.appendParametersToMessage()
					.setParameter("differentValues", differentValues);
		}
	}

	public StockQtyAndUOMQty minUomQty(
			@NonNull final StockQtyAndUOMQty qtysToCompare1,
			@NonNull final StockQtyAndUOMQty qtysToCompare2)
	{
		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

		final ProductId productId = extractCommonProductId(qtysToCompare1, qtysToCompare2);

		final Quantity uomQty1 = qtysToCompare1.getUOMQtyNotNull();
		final Quantity uomQty2 = uomConversionBL.convertQuantityTo(
				qtysToCompare2.getUOMQtyNotNull(),
				UOMConversionContext.of(productId),
				qtysToCompare1.getUOMQtyNotNull().getUomId());

		return uomQty1.compareTo(uomQty2) <= 0 ? qtysToCompare1 : qtysToCompare2;
	}

	public StockQtyAndUOMQty maxUomQty(
			@NonNull final StockQtyAndUOMQty qtysToCompare1,
			@NonNull final StockQtyAndUOMQty qtysToCompare2)
	{
		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

		final ProductId productId = extractCommonProductId(qtysToCompare1, qtysToCompare2);

		final Quantity uomQty1 = qtysToCompare1.getUOMQtyNotNull();
		final Quantity uomQty2 = uomConversionBL.convertQuantityTo(
				qtysToCompare2.getUOMQtyNotNull(),
				UOMConversionContext.of(productId),
				qtysToCompare1.getUOMQtyNotNull().getUomId());

		return uomQty1.compareTo(uomQty2) >= 0 ? qtysToCompare1 : qtysToCompare2;
	}
}
