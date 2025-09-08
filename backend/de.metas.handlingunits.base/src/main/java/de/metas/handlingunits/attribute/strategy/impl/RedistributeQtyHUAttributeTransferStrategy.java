package de.metas.handlingunits.attribute.strategy.impl;

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

import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.strategy.IHUAttributeTransferRequest;
import de.metas.handlingunits.attribute.strategy.IHUAttributeTransferStrategy;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.product.ProductId;
import de.metas.util.Services;
import org.adempiere.mm.attributes.api.CurrentAttributeValueContextProvider;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.api.IAttributesBL;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Attribute;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class RedistributeQtyHUAttributeTransferStrategy implements IHUAttributeTransferStrategy
{
	public static final RedistributeQtyHUAttributeTransferStrategy instance = new RedistributeQtyHUAttributeTransferStrategy();

	private RedistributeQtyHUAttributeTransferStrategy()
	{
		super();
	}

	@Override
	public void transferAttribute(final IHUAttributeTransferRequest request, final I_M_Attribute attribute)
	{
		CurrentAttributeValueContextProvider.assertNoCurrentContext();

		final IAttributeSet attributesFrom = request.getAttributesFrom();

		//
		// Transfer ratio is used to proportionally distribute qty
		final BigDecimal transferRatio = getTransferRatio(request, attribute);

		//
		// Calculate valueToTransfer and valueFromNew depending on ratio and current attribute
		final BigDecimal valueFromOld = attributesFrom.getValueAsBigDecimal(attribute);

		final MathContext mc = Services.get(IAttributesBL.class).getMathContext(attribute);
		final BigDecimal valueToTransfer = valueFromOld.multiply(transferRatio).setScale(mc.getPrecision(), mc.getRoundingMode()); // preserve precision

		final BigDecimal valueFromNew = valueFromOld.subtract(valueToTransfer);

		//
		// Propagate valueFromNew on the From attribute storage
		attributesFrom.setValue(attribute, valueFromNew);

		//
		// Calculate valueToNew and depending on valueToTransfer and current attribute
		final IAttributeStorage attributesTo = request.getAttributesTo();
		final BigDecimal valueToOld = attributesTo.getValueAsBigDecimal(attribute);
		final BigDecimal valueToNew = valueToOld.add(valueToTransfer);

		//
		// Propagate valueToNew on the To attribute storage
		attributesTo.setValue(attribute, valueToNew);
	}

	/**
	 * Values are considered not transferable within this implementation if we're not transferring anything or if we're transferring the full qty.
	 */
	@Override
	public boolean isTransferable(final IHUAttributeTransferRequest request, final I_M_Attribute attribute)
	{
		// This strategy does only apply on VHU-to-VHU attributes transfer because it involves calculations
		if (!request.isVHUTransfer())
		{
			return false;
		}

		final BigDecimal transferRatio = getTransferRatio(request, attribute);

		final boolean isTransferrable = BigDecimal.ZERO.compareTo(transferRatio) < 0 // transferRatio > 0
				&& BigDecimal.ONE.compareTo(transferRatio) >= 0; // transferRatio <= 1

		return isTransferrable;
	}

	/**
	 * @return a value between 0 and 1, resulted by the division of the requestQty (qty) and storageQty (source) for given attribute
	 */
	private BigDecimal getTransferRatio(final IHUAttributeTransferRequest request, final I_M_Attribute attribute)
	{
		//
		// The qty which is to be subtracted divided by the full qty is our division ratio
		// (will return a value which is numerically less than 1, and greater or equal to 0)
		final ProductId requestProductId = request.getProductId();

		final IHUStorage huStorageFrom = request.getHUStorageFrom();

		I_C_UOM uomToUse = request.getC_UOM();
		if (uomToUse == null)
		{
			uomToUse = huStorageFrom.getProductStorage(requestProductId).getC_UOM(); // fallback and consider using the request UOM
		}

		final BigDecimal requestQty = request.getQty();

		final BigDecimal existingStorageQtyOnSource;
		if (huStorageFrom != null)
		{
			final BigDecimal storageQty = huStorageFrom.getQty(requestProductId, uomToUse);
			final BigDecimal qtyUnloaded = request.getQtyUnloaded();

			//
			// Treat the case where the transfer does nothing
			if (storageQty == null || qtyUnloaded == null)
			{
				existingStorageQtyOnSource = BigDecimal.ONE;
			}
			else
			{
				existingStorageQtyOnSource = storageQty
						//
						// Subtract qty already unloaded (at the moment of the attribute transfer, the trxLines are not processed, so the ProductStorage changes are not persisted yet)
						//
						.subtract(qtyUnloaded);
			}
		}
		else
		{
			existingStorageQtyOnSource = requestQty; // no HU storage => transfer all (TODO check if this adds inconsistencies to overall calculation)
		}

		if (existingStorageQtyOnSource == null || existingStorageQtyOnSource.signum() <= 0)
		{
			return BigDecimal.ZERO;
		}

		//
		// Do not use math context; instead, force a high scale and rounding mode to get the lowest possible error for the ratio
		final int scale = 12;
		final RoundingMode roundingMode = RoundingMode.HALF_UP;

		final BigDecimal transferRatio = requestQty.divide(existingStorageQtyOnSource, scale, roundingMode);
		return transferRatio;
	}
}
