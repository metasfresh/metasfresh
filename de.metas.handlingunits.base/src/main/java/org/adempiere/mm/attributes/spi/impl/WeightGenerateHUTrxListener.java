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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;

import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUTrxDAO;
import de.metas.handlingunits.IHUTrxListener;
import de.metas.handlingunits.attribute.IWeightable;
import de.metas.handlingunits.attribute.IWeightableFactory;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_Trx_Line;
import de.metas.product.IProductBL;

/**
 * Aim: in case we are transferring quantity between a document and a HU and we are dealing with a weightable or non weightable product we need to use it's standard weight.
 *
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/06936_Packtischdialog_weights_%28103796014800%29
 */
public class WeightGenerateHUTrxListener implements IHUTrxListener
{
	public static final transient WeightGenerateHUTrxListener instance = new WeightGenerateHUTrxListener();
	
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
		
		final BigDecimal trxWeightNet = calculateTrxWeightIfApplies(trxLine);
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
			final BigDecimal weightNetOld = weightable.getWeightNet();
			final BigDecimal weightNetNew = weightNetOld.add(trxWeightNet);
			weightable.setWeightNet(weightNetNew);
		}
		// nothing
	}

	/**
	 * Calculates the Weight of transaction line quantity by using the standard product weight.
	 *
	 * <strike>If the product has a weightable UOM then <code>null</code> will be returned because it does not apply for our case.</strike>
	 *
	 * Now our case changed. This logic is used for all UOMs, weightable or not Task:
	 * http://dewiki908/mediawiki/index.php/08175_Gewicht_aus_Gebindeconfig_nach_Nettogewicht_%C3%BCbernehmen_bei_kg_Artikel_%28104866551966%29
	 *
	 * @param trxLine
	 * @return weight or <code>null</code> if it does not apply.
	 */
	private BigDecimal calculateTrxWeightIfApplies(final I_M_HU_Trx_Line trxLine)
	{
		//
		// Get transaction Qty.
		final BigDecimal qty = trxLine.getQty();
		if (qty.signum() == 0)
		{
			return null;
		}

		//
		// Get transaction Product.
		// Make sure we are dealing with a non-weightable product
		final I_M_Product product = trxLine.getM_Product();
		if (product == null)
		{
			return null;
		}

		//
		// 08175: Apply TrxListener and generate NET weights for both weightable and non-weightable products
		// if (Services.get(IWeightableBL.class).isWeightable(product))
		// {
		// return null;
		// }

		//
		// Get transaction UOM.
		final I_C_UOM qtyUOM = trxLine.getC_UOM();
		if (qtyUOM == null)
		{
			return null;
		}

		//
		// Get Product's weight
		final BigDecimal productWeight = Services.get(IProductBL.class).getWeight(product, qtyUOM);
		if (productWeight.signum() <= 0)
		{
			return null;
		}

		//
		// Calculate transaction's weight
		final BigDecimal trxWeightNet = qty.multiply(productWeight);

		return trxWeightNet;
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
		final IWeightable weightable = Services.get(IWeightableFactory.class).createWeightableOrNull(attributeStoarge);
		if (weightable == null)
		{
			// shall not happen
			return null;
		}

		//
		// If there is no WeightNet attribute, there is no point to update it
		if (!weightable.hasWeightNet())
		{
			return null;
		}

		return weightable;
	}
}
