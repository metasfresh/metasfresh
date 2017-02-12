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


import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import org.adempiere.mm.attributes.api.IAttributesBL;
import org.adempiere.mm.attributes.model.I_M_Attribute;
import org.adempiere.util.Services;

import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.strategy.IAttributeSplitRequest;
import de.metas.handlingunits.attribute.strategy.IAttributeSplitResult;
import de.metas.handlingunits.attribute.strategy.IAttributeSplitterStrategy;
import de.metas.handlingunits.conversion.ConversionHelper;

/**
 * Linearly distribute a given numeric value to it's children
 *
 * @author tsa
 *
 */
public class LinearDistributionAttributeSplitterStrategy implements IAttributeSplitterStrategy
{
	@Override
	public IAttributeSplitResult split(final IAttributeSplitRequest request)
	{
		final Object valueToSplitObj = request.getValueToSplit();
		final BigDecimal valueToSplit = ConversionHelper.toBigDecimal(valueToSplitObj);

		final IAttributeStorage parentAttributeStorage = request.getParentAttributeStorage();
		final BigDecimal parentStorageQty = parentAttributeStorage.getStorageQtyOrZERO();

		final IAttributeStorage currentAttributeStorage = request.getAttributeStorageCurrent();
		final BigDecimal currentStorageQty = currentAttributeStorage.getStorageQtyOrZERO();

		final int currentIndex = request.getAttributeStorageCurrentIndex();
		final int childrenCount = request.getAttributeStorages().size();
		final int childrenRemainingCount = childrenCount - currentIndex;

		//
		// Ratio shall have a high precision and be rounded up at all times
		final int distributionRatioPrecision = 12;
		final RoundingMode distributionRatioRoundingMode = RoundingMode.HALF_UP;

		final boolean useRemainingValue;
		final BigDecimal distributionRatio;
		if (currentStorageQty.signum() != 0 && parentStorageQty.signum() != 0)
		{
			//
			// We're using initial value for calculation in this case, but keep using remaining value to split for the last allocation to preserve decimal integrity
			useRemainingValue = false;
			distributionRatio = currentStorageQty.divide(parentStorageQty, distributionRatioPrecision, distributionRatioRoundingMode);
		}
		else
		{
			//
			// If parent storage is ZERO, then rely on the current storage only and use remaining value in calculation
			useRemainingValue = true;
			distributionRatio = BigDecimal.ONE.divide(BigDecimal.valueOf(childrenRemainingCount), distributionRatioPrecision, distributionRatioRoundingMode);
		}

		final I_M_Attribute attribute = request.getM_Attribute();
		final MathContext mc = Services.get(IAttributesBL.class).getMathContext(attribute);

		final BigDecimal splitValue;
		final BigDecimal remainingValue;
		if (childrenRemainingCount > 1)
		{
			// Calculate the splitValue as valueToSplit OR initial value - see useRemainigValue - (remaining value) * distributionRatio
			final BigDecimal valueToDistribute;
			if (useRemainingValue)
			{
				valueToDistribute = valueToSplit;
			}
			else
			{
				valueToDistribute = (BigDecimal)request.getValueInitial();
			}
			splitValue = valueToDistribute.multiply(distributionRatio).setScale(mc.getPrecision(), mc.getRoundingMode()); // preserve precision
			remainingValue = valueToSplit.subtract(splitValue);
		}
		else
		{
			// In case this is the last item on which we need to distribute, we consider the whole value to avoid rounding errors
			splitValue = valueToSplit;
			remainingValue = BigDecimal.ZERO;
		}

		return new AttributeSplitResult(splitValue, remainingValue);
	}

	@Override
	public Object recalculateRemainingValue(final IAttributeSplitResult result, final Object valueSet)
	{
		final BigDecimal valueToSplit = (BigDecimal)result.getSplitValue();
		final BigDecimal valueToSplitActual = valueSet == null ? BigDecimal.ZERO : (BigDecimal)valueSet;

		final BigDecimal valueToSplitDiff = valueToSplit.subtract(valueToSplitActual);

		final BigDecimal remainingValue = (BigDecimal)result.getRemainingValue();
		final BigDecimal remainingValueActual = remainingValue.add(valueToSplitDiff);

		return remainingValueActual;
	}

	@Override
	public String toString()
	{
		return "LinearDistributionAttributeSplitterStrategy []";
	}
}
