package de.metas.handlingunits.attribute.strategy.impl;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

import de.metas.handlingunits.attribute.strategy.IHUAttributeTransferRequest;
import de.metas.handlingunits.attribute.strategy.IHUAttributeTransferStrategy;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_Product;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/**
 * Transfer strategy for the {@code WeightTare} attribute that moves only the per-CU
 * packaging delta {@code (Product.GrossWeight − Product.NetWeight) × qty} between source
 * and destination on a VHU-to-VHU transfer.
 * <p>
 * The packing-material portion of WeightTare (Karton, Pallet, …) is intentionally NOT
 * touched: packing material is indivisible per physical container and stays where the
 * container is. BOTU sum from the source HU's children re-aggregates packing-material
 * weight automatically once the qty has moved.
 * <p>
 * Returning a partial Karton (e.g. proportional split via RedistributeQty) is physically
 * wrong — a Karton can't be half on each side. This strategy avoids that by computing
 * the moved portion from the product master + request qty directly, without touching
 * the source's existing packing-material total.
 */
public final class WeightTareDeltaTransferStrategy implements IHUAttributeTransferStrategy
{
	public static WeightTareDeltaTransferStrategy newInstance()
	{
		return new WeightTareDeltaTransferStrategy();
	}

	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

	private WeightTareDeltaTransferStrategy()
	{
	}

	@Override
	public void transferAttribute(@NonNull final IHUAttributeTransferRequest request, @NonNull final I_M_Attribute attribute)
	{
		final Quantity perCUDeltaForMovedQty = computePerCUDelta(request);
		if (perCUDeltaForMovedQty == null || perCUDeltaForMovedQty.isZero())
		{
			return;
		}

		final BigDecimal perCUDeltaForMovedQtyBD = perCUDeltaForMovedQty.toBigDecimal();

		final BigDecimal sourceOldBD = request.getAttributesFrom().getValueAsBigDecimal(attribute);
		request.getAttributesFrom().setValue(attribute, sourceOldBD.subtract(perCUDeltaForMovedQtyBD));

		final BigDecimal destOldBD = request.getAttributesTo().getValueAsBigDecimal(attribute);
		request.getAttributesTo().setValue(attribute, destOldBD.add(perCUDeltaForMovedQtyBD));
	}

	@Override
	public boolean isTransferable(@NonNull final IHUAttributeTransferRequest request, @NonNull final I_M_Attribute attribute)
	{
		if (!request.isVHUTransfer())
		{
			return false;
		}
		// Only redistribute when the source actually carries tare. On an initial fill (non-HU source
		// → VHU) HULoader.transferAttributes(useVHU=true) still invokes us because the destination
		// is a VHU, but the source carries 0 tare and there is nothing to redistribute — the per-CU
		// packaging contribution is added once by WeightGenerateHUTrxListener.trxLineProcessed for
		// the same trx-line. Skipping here keeps that contribution single-counted.
		final java.math.BigDecimal sourceTare = request.getAttributesFrom().getValueAsBigDecimal(attribute);
		return sourceTare != null && sourceTare.signum() > 0;
	}

	@Nullable
	private Quantity computePerCUDelta(@NonNull final IHUAttributeTransferRequest request)
	{
		final ProductId productId = request.getProductId();
		final BigDecimal qtyBD = request.getQty();
		final I_C_UOM requestUOM = request.getC_UOM();
		if (productId == null || qtyBD == null || requestUOM == null)
		{
			return null;
		}

		// HU-to-HU transfers carry a positive qty (the amount being moved). A non-positive qty
		// has no per-CU delta to move — return null so the strategy short-circuits without writing.
		// This is not a directional reversal: the upstream caller never produces negative qty here.
		final Quantity qty = Quantity.of(qtyBD, requestUOM);
		if (!qty.isPositive())
		{
			return null;
		}

		final Quantity productGross = productBL.getGrossWeight(productId, requestUOM).orElse(null);
		if (productGross == null || !productGross.isPositive())
		{
			return null;
		}

		final I_M_Product product = productBL.getById(productId);
		final Quantity productNet = productBL.getNetWeight(product, requestUOM).orElse(null);
		if (productNet == null || !productNet.isPositive())
		{
			return null;
		}

		final Quantity productGrossInNetUOM = uomConversionBL.convertQuantityTo(productGross, productId, productNet.getUOM());

		final Quantity perUnitDelta = productGrossInNetUOM.subtract(productNet);
		if (!perUnitDelta.isPositive())
		{
			return null;
		}

		return perUnitDelta.multiply(qty.toBigDecimal());
	}
}
