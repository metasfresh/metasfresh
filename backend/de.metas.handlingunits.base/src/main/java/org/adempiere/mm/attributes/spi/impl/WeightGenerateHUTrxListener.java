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

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.weightable.IWeightable;
import de.metas.handlingunits.attribute.weightable.Weightables;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.hutransaction.IHUTrxDAO;
import de.metas.handlingunits.hutransaction.IHUTrxListener;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_Trx_Line;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Aim: in case we are transferring quantity between a document and a HU and we are dealing with a weightable or non weightable product we need to use it's standard weight.
 *
 * @author tsa
 * @implSpec <a href="http://dewiki908/mediawiki/index.php/06936_Packtischdialog_weights_%28103796014800%29">task</a>
 */
public class WeightGenerateHUTrxListener implements IHUTrxListener
{
	public static final WeightGenerateHUTrxListener instance = new WeightGenerateHUTrxListener();

	@Override
	public void trxLineProcessed(final IHUContext huContext, final I_M_HU_Trx_Line trxLine)
	{
		//
		// In case we are adjusting the HU Storage based on Weight attribute,
		// then we shall not update the WeightNet attribute here again because we will double it. (08728)
		final Boolean storageAdjustment = huContext.getProperty(IHUContext.PROPERTY_IsStorageAdjustmentFromWeightAttribute);
		if (storageAdjustment != null && storageAdjustment)
		{
			return;
		}

		final Quantity trxWeightNet = calculateTrxWeightIfApplies(trxLine).orElse(null);
		if (trxWeightNet == null || trxWeightNet.signum() == 0)
		{
			return;
		}

		final IWeightable weightable = getWeightableIfApplies(huContext, trxLine);
		if (weightable == null)
		{
			return;
		}

		//
		// Update weight by adding the transaction weight
		{
			final Quantity trxWeightNetConv = convertQuantityTo(trxWeightNet, weightable.getWeightNetUOM(), trxLine);

			final BigDecimal weightNetOld = weightable.getWeightNet();
			final BigDecimal weightNetNew = weightNetOld.add(trxWeightNetConv.toBigDecimal());
			weightable.setWeightNet(weightNetNew);
		}
		// nothing
	}

	private static Quantity convertQuantityTo(final Quantity qty, final I_C_UOM targetUOM, final I_M_HU_Trx_Line trxLine)
	{
		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
		final ProductId productId = IHUTrxBL.extractProductId(trxLine);
		return uomConversionBL.convertQuantityTo(qty, productId, targetUOM);
	}

	/**
	 * Calculates the Weight of transaction line quantity by using the standard product weight.
	 */
	private Optional<Quantity> calculateTrxWeightIfApplies(final I_M_HU_Trx_Line trxLine)
	{
		//
		// Get transaction Qty.
		final BigDecimal qty = trxLine.getQty();
		if (qty.signum() == 0)
		{
			return Optional.empty();
		}

		//
		// Get transaction Product.
		final I_M_Product product = IHUTrxBL.extractProductOrNull(trxLine);
		if (product == null)
		{
			return Optional.empty();
		}

		//
		// Get transaction UOM (NOT the weight UOM!)
		final I_C_UOM qtyUOM = IHUTrxBL.extractUOMOrNull(trxLine);
		if (qtyUOM == null)
		{
			return Optional.empty();
		}

		//
		// Get Product's weight
		final Quantity productWeight = Services.get(IProductBL.class).getNetWeight(product, qtyUOM).orElse(null);
		if (productWeight == null || productWeight.signum() <= 0)
		{
			return Optional.empty();
		}

		//
		// Calculate transaction's weight
		final Quantity trxWeightNet = productWeight.multiply(qty);
		return Optional.of(trxWeightNet);
	}

	private IWeightable getWeightableIfApplies(final IHUContext huContext, final I_M_HU_Trx_Line trxLine)
	{
		//
		// If there is no VHU involved on this transaction, then it does not apply
		final I_M_HU_Item vhuItem = trxLine.getVHU_Item();
		if (vhuItem == null || vhuItem.getM_HU_Item_ID() <= 0)
		{
			return null;
		}

		//
		// Make sure we are NOT transfering between HUs.
		// In that case we relly on de.metas.handlingunits.attribute.strategy.impl.RedistributeQtyHUAttributeTransferStrategy.
		// Also we assume the weight attributes have UseInASI=false
		final I_M_HU_Trx_Line trxLineCounterpart = Services.get(IHUTrxDAO.class).retrieveCounterpartTrxLine(trxLine);
		if (trxLineCounterpart.getVHU_Item_ID() > 0)
		{
			return null;
		}

		//
		// Get the IWeightable from VHU
		final I_M_HU vhu = vhuItem.getM_HU();
		final IAttributeStorage attributeStoarge = huContext.getHUAttributeStorageFactory().getAttributeStorage(vhu);
		final IWeightable weightable = Weightables.wrap(attributeStoarge);

		//
		// If there is no WeightNet attribute, there is no point to update it
		if (!weightable.hasWeightNet())
		{
			return null;
		}

		return weightable;
	}
}
