package org.adempiere.mm.attributes.spi.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.weightable.IWeightable;
import de.metas.handlingunits.attribute.weightable.Weightables;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.hutransaction.IHUTrxListener;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_Trx_Line;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.Services;
import lombok.Value;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/**
 * Aim: in case we are transferring quantity between a document and a HU and we are dealing with a weightable or non weightable product we need to use it's standard weight.
 *
 * @author tsa
 * @implSpec <a href="http://dewiki908/mediawiki/index.php/06936_Packtischdialog_weights_%28103796014800%29">task</a>
 */
public class WeightGenerateHUTrxListener implements IHUTrxListener
{
	public static final WeightGenerateHUTrxListener instance = new WeightGenerateHUTrxListener();

	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

	@Override
	public void trxLineProcessed(final IHUContext huContext, final I_M_HU_Trx_Line trxLine)
	{
		// In case we are adjusting the HU Storage based on Weight attribute,
		// then we shall not update the WeightNet attribute here again because we will double it. (08728)
		final boolean storageAdjustment = huContext.isPropertyTrue(IHUContext.PROPERTY_IsStorageAdjustmentFromWeightAttribute);
		if (storageAdjustment)
		{
			return;
		}

		final TrxWeightDeltas deltas = calculateTrxWeightDeltasIfApplies(trxLine);
		if (deltas == null || deltas.getNetDelta().isZero())
		{
			return;
		}

		final IAttributeStorage attributeStorage = getAttributeStorageIfApplies(huContext, trxLine);
		if (attributeStorage == null)
		{
			return;
		}
		final IWeightable weightable = Weightables.wrap(attributeStorage);
		if (!weightable.hasWeightNet())
		{
			return;
		}

		final ProductId productId = ProductId.ofRepoIdOrNull(trxLine.getM_Product_ID());
		final I_C_UOM weightNetUOM = weightable.getWeightNetUOM();

		// Update WeightNet by adding the converted netDelta.
		final Quantity netDeltaConv = uomConversionBL.convertQuantityTo(deltas.getNetDelta(), productId, weightNetUOM);
		final BigDecimal weightNetOld = weightable.getWeightNet();
		final BigDecimal weightNetNew = weightNetOld.add(netDeltaConv.toBigDecimal());
		weightable.setWeightNet(weightNetNew);

		// Update WeightTare by adding the converted tareDelta. Skipped when the storage does not
		// carry the WeightTare attribute or when the per-CU packaging contribution is zero
		// (i.e. product master gross == net, or only one of them is set).
		// IWeightable does not expose setWeightTare by design (Tare is structural / seeded);
		// follow AggregateHUTrxListener and write via the attribute storage directly.
		if (!deltas.getTareDelta().isZero() && weightable.hasWeightTare())
		{
			final Quantity tareDeltaConv = uomConversionBL.convertQuantityTo(deltas.getTareDelta(), productId, weightNetUOM);
			final BigDecimal weightTareOld = weightable.getWeightTare();
			final BigDecimal weightTareNew = weightTareOld.add(tareDeltaConv.toBigDecimal());
			attributeStorage.setValue(weightable.getWeightTareAttribute(), weightTareNew);
			attributeStorage.pushUp();
		}
	}

	/**
	 * Net + Tare deltas to apply on a VHU after a trx line was processed.
	 * Both are expressed in the trx UOM; the caller converts to the weight UOM.
	 */
	@Value
	private static class TrxWeightDeltas
	{
		Quantity netDelta;
		Quantity tareDelta;
	}

	/**
	 * Calculates the net and tare deltas for the trx line, derived from the product's
	 * master-data weights. Returns null when no derivation is possible (no qty,
	 * no product, no UOM, or no gross weight on the product master).
	 */
	@Nullable
	private TrxWeightDeltas calculateTrxWeightDeltasIfApplies(final I_M_HU_Trx_Line trxLine)
	{
		// Get transaction UOM (NOT the weight UOM!)
		final I_C_UOM qtyUOM = IHUTrxBL.extractUOMOrNull(trxLine);
		if (qtyUOM == null)
		{
			return null;
		}

		// Get transaction Qty.
		final Quantity qty = Quantity.of(trxLine.getQty(), qtyUOM);
		if (qty.isZero())
		{
			return null;
		}

		// Get transaction Product.
		final I_M_Product product = IHUTrxBL.extractProductOrNull(trxLine);
		if (product == null)
		{
			return null;
		}

		// Get Product's gross weight (per-CU). Returns null when neither gross nor net is on the master
		// (IProductBL.getGrossWeight already falls back to net internally).
		final Quantity productGross = productBL.getGrossWeight(product, qtyUOM).orElse(null);
		if (productGross == null || !productGross.isPositive())
		{
			return null;
		}

		// Get Product's net weight (per-CU). Strict: returns null when M_Product.Weight is the default 0,
		// i.e. no real net weight has been maintained. In that case the listener does not derive
		// WeightNet — the same as pre-2025 behaviour, consistent with how the rest of the system reads
		// IProductBL.getNetWeight.
		final Quantity productNet = productBL.getNetWeight(product, qtyUOM).orElse(null);
		if (productNet == null || !productNet.isPositive())
		{
			return null;
		}

		// Net and gross may carry different UOMs: net is hardcoded to KG (see IProductBL),
		// gross uses M_Product.GrossWeight_UOM_ID and may be e.g. grams. Normalise gross
		// to the net's UOM before subtracting so the per-CU tare delta is correct.
		// IUOMConversionBL.convertQuantityTo no-ops when source and target UOM already match.
		final ProductId productId = ProductId.ofRepoId(product.getM_Product_ID());
		final Quantity productGrossInNetUOM = uomConversionBL.convertQuantityTo(productGross, productId, productNet.getUOM());

		// Calculate transaction's deltas (per-CU × qty).
		final Quantity netDelta = productNet.multiply(qty.toBigDecimal());

		// Per-CU packaging delta: only meaningful when gross > net. A misconfigured product
		// with gross <= net would produce a non-positive delta — same skip semantic as
		// WeightTareAttributeValueCallout.calculateProductPackagingDelta and
		// WeightTareDeltaTransferStrategy.computePerCUDelta, so the incremental and
		// recompute paths stay consistent.
		final Quantity productGrossNetDiff = productGrossInNetUOM.subtract(productNet);
		final Quantity tareDelta = productGrossNetDiff.isPositive()
				? productGrossNetDiff.multiply(qty.toBigDecimal())
				: productGrossNetDiff.toZero();

		return new TrxWeightDeltas(netDelta, tareDelta);
	}

	@Nullable
	private IAttributeStorage getAttributeStorageIfApplies(final IHUContext huContext, final I_M_HU_Trx_Line trxLine)
	{
		//
		// If there is no VHU involved in this transaction, then it does not apply
		final I_M_HU_Item vhuItem = trxLine.getVHU_Item();
		if (vhuItem == null || vhuItem.getM_HU_Item_ID() <= 0)
		{
			return null;
		}

		//
		// Make sure we are NOT transferring between HUs.
		// In that case we rely on the per-attribute transfer strategies registered on M_HU_PI_Attribute:
		//   - WeightNet  → RedistributeQtyHUAttributeTransferStrategy (proportional split by qty)
		//   - WeightTare → WeightTareDeltaTransferStrategy             (moves only the per-CU packaging delta;
		//                                                                packing material stays with the physical container)
		// Also we assume the weight attributes have UseInASI=false
		final I_M_HU_Trx_Line trxLineCounterpart = huTrxBL.retrieveCounterpartTrxLine(trxLine);
		if (trxLineCounterpart.getVHU_Item_ID() > 0)
		{
			return null;
		}

		//
		// Resolve the VHU's attribute storage from the trx line's VHU item id.
		final HuId vhuId = HuId.ofRepoId(vhuItem.getM_HU_ID());
		final I_M_HU vhu = handlingUnitsBL.getById(vhuId);
		return huContext.getHUAttributeStorageFactory().getAttributeStorage(vhu);
	}
}
