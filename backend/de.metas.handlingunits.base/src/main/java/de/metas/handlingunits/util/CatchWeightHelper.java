package de.metas.handlingunits.util;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.weightable.IWeightable;
import de.metas.handlingunits.attribute.weightable.Weightables;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQty.StockQtyAndUOMQtyBuilder;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UOMType;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.PlainContextAware;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;

/*
 * #%L
 * de.metas.handlingunits.base
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
public class CatchWeightHelper
{
	@Nullable
	public StockQtyAndUOMQty extractQtysOrNull(
			final ProductId productId,
			final HuId huId)
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final IMutableHUContext huContext = handlingUnitsBL.createMutableHUContext(PlainContextAware.newWithThreadInheritedTrx());

		return extractQtysOrNull(huContext, productId, handlingUnitsBL.getById(huId));
	}

	@Nullable
	public StockQtyAndUOMQty extractQtysOrNull(
			final IHUContext huContext,
			final ProductId productId,
			final I_M_HU huRecord)
	{
		final IHUProductStorage storage = huContext
				.getHUStorageFactory()
				.getStorage(huRecord)
				.getProductStorageOrNull(productId);

		if (storage == null)
		{
			return null;
		}

		return extractQtys(huContext, productId, storage.getQty(), huRecord);
	}

	public StockQtyAndUOMQty extractQtys(
			final ProductId productId,
			final I_M_HU huRecord)
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		final IMutableHUContext huContext = handlingUnitsBL.createMutableHUContext(PlainContextAware.newWithThreadInheritedTrx());

		return extractQtys(huContext, productId, huRecord);
	}

	/**
	 * Use if your calling code has a {@code huContext}, because there might be not-yet-flushed-to-DB stuff stuff that we would miss when not using that {@code huContext}.
	 */
	public StockQtyAndUOMQty extractQtys(
			final IHUContext huContext,
			final ProductId productId,
			final I_M_HU huRecord)
	{
		final Quantity qty = huContext
				.getHUStorageFactory()
				.getStorage(huRecord)
				.getProductStorage(productId)
				.getQty();

		return extractQtys(huContext, productId, qty, huRecord);
	}

	public StockQtyAndUOMQty extractQtys(
			final IHUContext huContext,
			final ProductId productId,
			final Quantity pickedQty,
			final I_M_HU huRecord)
	{
		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
		final IProductBL productBL = Services.get(IProductBL.class);

		final UomId stockUOMId = productBL.getStockUOMId(productId);
		final Quantity stockQty = uomConversionBL.convertQuantityTo(pickedQty, UOMConversionContext.of(productId), stockUOMId);

		final StockQtyAndUOMQtyBuilder qtyPicked = StockQtyAndUOMQty.builder()
				.productId(productId)
				.stockQty(stockQty);

		final UomId catchUomId = productBL.getCatchUOMId(productId).orElse(null);
		if (catchUomId != null)
		{
			assertUomWeightable(catchUomId);

			final IAttributeStorage attributeStorage = huContext.getHUAttributeStorageFactory().getAttributeStorage(huRecord);
			final IWeightable weightable = Weightables.wrap(attributeStorage);
			final Quantity huWeightNet = Quantity.of(weightable.getWeightNet(), weightable.getWeightNetUOM());

			final Quantity weight;
			if (stockQty.signum() >= 0)
			{
				weight = huWeightNet;
			}
			else
			{
				final Quantity huQty = huContext.getHUStorageFactory().getStorage(huRecord).getQuantity(productId)
						.map(qty -> uomConversionBL.convertQuantityTo(qty, UOMConversionContext.of(productId), stockUOMId))
						.orElse(null);
				if (huQty == null || huQty.signum() == 0)
				{
					throw new AdempiereException("Cannot determine catch weight because HU's qty is zero");
				}
				else
				{
					final BigDecimal factor = stockQty.toBigDecimal().divide(huQty.toBigDecimal(), 12, RoundingMode.HALF_UP);
					weight = huWeightNet.multiply(factor).roundToUOMPrecision();
				}
			}

			final Quantity catchQty = uomConversionBL.convertQuantityTo(weight, UOMConversionContext.of(productId), catchUomId);
			qtyPicked.uomQty(catchQty);
		}

		return qtyPicked.build();
	}

	private void assertUomWeightable(@NonNull final UomId catchUomId)
	{
		final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
		final UOMType catchUOMType = uomDAO.getUOMTypeById(catchUomId);
		if (!catchUOMType.isWeight())
		{
			throw new AdempiereException("CatchUomId=" + catchUomId + " needs to be weightable");
		}
	}
}
