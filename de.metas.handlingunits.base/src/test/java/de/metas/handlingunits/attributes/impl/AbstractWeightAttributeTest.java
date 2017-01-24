package de.metas.handlingunits.attributes.impl;

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
import java.util.Collections;
import java.util.List;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.adempiere.util.test.ErrorMessage;

import de.metas.handlingunits.AbstractHUTestWithSampling;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.expectations.HUWeightsExpectation;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Item;

/**
 * Abstract class used for setting up data for weight propagation tests
 *
 * Note: Weight attributes are created in the helper on NoPI level.
 *
 * @author al
 */
public abstract class AbstractWeightAttributeTest extends AbstractHUTestWithSampling
{
	private BigDecimal _acceptableWeightErrorMargin;

	protected final void setAcceptableWeightErrorMargin(final BigDecimal acceptableWeightErrorMargin)
	{
		_acceptableWeightErrorMargin = acceptableWeightErrorMargin;
	}

	private final BigDecimal getAcceptableWeightErrorMarginOrDefault()
	{
		if (_acceptableWeightErrorMargin == null)
		{
			return HUWeightsExpectation.DEFAULT_AcceptableWeightErrorMargin;
		}
		return _acceptableWeightErrorMargin;
	}

	/**
	 * Sets Tare Adjust weight
	 *
	 * @param handlingUnit
	 * @param weightTareAdjust
	 */
	protected final void setWeightTareAdjust(final I_M_HU handlingUnit, final BigDecimal weightTareAdjust)
	{
//		Services.get(ITrxManager.class).run(new TrxRunnable()
//		{
//			@Override
//			public void run(String localTrxName) throws Exception
//			{
				final IAttributeStorage attributeStorage = attributeStorageFactory.getAttributeStorage(handlingUnit);
				attributeStorage.setValue(attr_WeightTareAdjust, weightTareAdjust);
//				attributeStorage.saveChangesIfNeeded();
//			}
//		});

	}

	@OverridingMethodsMustInvokeSuper
	protected LUWeightsExpectations<Object> newLUWeightsExpectations()
	{
		return new LUWeightsExpectations<Object>()
				.setAttributeStorageFactory(attributeStorageFactory);
	}

	@OverridingMethodsMustInvokeSuper
	protected TUWeightsExpectations<Object> newTUWeightsExpectations()
	{
		return new TUWeightsExpectations<Object>()
				.setAttributeStorageFactory(attributeStorageFactory);
	}

	@OverridingMethodsMustInvokeSuper
	protected HUWeightsExpectation<Object> newHUWeightsExpectation(final String weightGross,
			final String weightNet,
			final String weightTare,
			final String weightTareAdjust)
	{
		final HUWeightsExpectation<Object> expectation = new HUWeightsExpectation<>(weightGross, weightNet, weightTare, weightTareAdjust);
		expectation.setErrorMargin(getAcceptableWeightErrorMarginOrDefault());
		return expectation;
	}

	/**
	 * Asserts that:
	 * <ul>
	 * <li>the given loading unit has the correct <code>tuAmountOnLU</code> of TUs on it</li>
	 * <li>given loading unit weight attributes meet the <code>luExpectation</code></li>
	 * <li>given loading unit's first N (according to amount of weight expectations) trading units weight attributes meet the <code>tuExpectations</code></li>
	 * </ul>
	 *
	 * @param loadingUnit
	 * @param tuAmountOnLU
	 * @param luExpectation
	 * @param tuExpectations
	 */
	protected final void assertLoadingUnitStorageWeights(final I_M_HU loadingUnit,
			final I_M_HU_PI_Item loadingUnitPIItem,
			final int tuAmountOnLU,
			final HUWeightsExpectation<?> luExpectation,
			final HUWeightsExpectation<?>... tuExpectations)
	{
		//@formatter:off
		newLUWeightsExpectations()
			.luPIItem(loadingUnitPIItem)
			.tuCount(tuAmountOnLU)
			.setLUWeightsExpectation(luExpectation)
			.tuExpectations()
				.addTUExpectations(tuExpectations)
				.endExpectation()
			.assertExpected(loadingUnit);
		//@formatter:on
	}

	/**
	 * Assert that weights of the {@link IAttributeStorage} meet the requirements of the given {@link HUWeightsExpectation}
	 *
	 * @param attributeStorage
	 * @param expectation
	 */
	protected final void assertSingleHandlingUnitWeights(final IAttributeStorage attributeStorage,
			final HUWeightsExpectation<?> expectation)
	{
		newTUWeightsExpectations()
				.addTUExpectation(expectation)
				.assertExpectedTUAttributeStorages(ErrorMessage.NULL,
						Collections.singletonList(attributeStorage),
						attributeStorageFactory);
	}

	/**
	 * Assert weights on all Trading Units
	 *
	 * @param tradingUnits
	 * @param weightExpectation if index of weight expectation does not exist within the tradingUnits, use {@link #standardWeightExpectation}
	 */
	protected final void assertTradingUnitsWeightExpectations(final List<I_M_HU> tradingUnits,
			final HUWeightsExpectation<?> defaultWeightExpectation,
			final HUWeightsExpectation<?>... weightExpectations)
	{
		newTUWeightsExpectations()
				.setDefaultTUExpectation(defaultWeightExpectation)
				.addTUExpectations(weightExpectations)
				.assertExpected(tradingUnits);
	}

	@OverridingMethodsMustInvokeSuper
	protected void assertTradingUnitInLoadingUnitWeights(final IAttributeStorage tuAttributeStorage,
			final HUWeightsExpectation<?> expectation,
			final int index)
	{
		final ErrorMessage message = newErrorMessage()
				.addContextInfo("TU Index", index)
				.expect("Valid TU weights");

		expectation.assertExpected(message, tuAttributeStorage);
	}
}
