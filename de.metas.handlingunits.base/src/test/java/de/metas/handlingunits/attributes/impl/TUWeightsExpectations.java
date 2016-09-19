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


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.adempiere.util.Check;
import org.adempiere.util.test.ErrorMessage;

import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.expectations.AbstractHUExpectation;
import de.metas.handlingunits.expectations.HUAttributeExpectation;
import de.metas.handlingunits.expectations.HUWeightsExpectation;
import de.metas.handlingunits.model.I_M_HU;

public class TUWeightsExpectations<ParentExpectationType> extends AbstractHUExpectation<ParentExpectationType>
{
	private final List<HUWeightsExpectation<TUWeightsExpectations<ParentExpectationType>>> expectations = new ArrayList<>();
	private HUWeightsExpectation<TUWeightsExpectations<ParentExpectationType>> defaultTUExpectation;
	private HUAttributeExpectation<?> vhuCostPriceExpectation;
	private IAttributeStorageFactory _attributeStorageFactory;
	private Integer countTUsExpected = null;

	public TUWeightsExpectations()
	{
		super();
	}

	public TUWeightsExpectations(final ParentExpectationType parent)
	{
		super(parent);
	}

	/**
	 * Assert weights on all Trading Units
	 */
	public TUWeightsExpectations<ParentExpectationType> assertExpected(final List<I_M_HU> tradingUnits)
	{
		return assertExpected(ErrorMessage.NULL, tradingUnits);
	}

	/**
	 * Assert weights on all Trading Units
	 */
	public TUWeightsExpectations<ParentExpectationType> assertExpected(
			final ErrorMessage message,
			final List<I_M_HU> tradingUnits)
	{
		final ErrorMessage messageToUse = derive(message);

		final IAttributeStorageFactory attributeStorageFactory = getAttributeStorageFactory();

		final int countTUs = tradingUnits.size();
		if (this.countTUsExpected != null)
		{
			assertEquals(messageToUse.expect("Count TUs"), this.countTUsExpected, countTUs);
		}

		final int weightExpectationCount = expectations.size();
		for (int i = 0; i < countTUs; i++)
		{
			final I_M_HU tu = tradingUnits.get(i);

			final ErrorMessage tuMessage = messageToUse
					.addContextInfo("TU", tu)
					.addContextInfo("TU Index", i + 1 + "/" + countTUs);

			final IAttributeStorage tuAttributeStorage = attributeStorageFactory.getAttributeStorage(tu);

			final HUWeightsExpectation<?> tuExpectationToUse = i < weightExpectationCount ? expectations.get(i) : defaultTUExpectation;

			tuExpectationToUse.assertExpectedForTU(tuMessage, tu, tuAttributeStorage);

			if (vhuCostPriceExpectation != null)
			{
				assertExpectedTUCostPrice(tuMessage, tuAttributeStorage);
			}
		}

		return this;
	}

	public TUWeightsExpectations<ParentExpectationType> assertExpectedTUAttributeStorages(final ErrorMessage message,
			final List<IAttributeStorage> tuAttributeStorages,
			final IAttributeStorageFactory attributeStorageFactory)
	{
		final ErrorMessage messageToUse = derive(message);

		final int weightExpectationCount = expectations.size();
		final int tusCount = tuAttributeStorages.size();
		for (int i = 0; i < tusCount; i++)
		{
			final ErrorMessage tuMessage = messageToUse
					.addContextInfo("TU Index", i + 1 + "/" + tusCount);

			final IAttributeStorage tuAttributeStorage = tuAttributeStorages.get(i);

			final HUWeightsExpectation<?> tuExpectationToUse = i < weightExpectationCount ? expectations.get(i) : defaultTUExpectation;

			tuExpectationToUse.assertExpected(tuMessage, tuAttributeStorage);

			if (vhuCostPriceExpectation != null)
			{
				assertExpectedTUCostPrice(tuMessage, tuAttributeStorage);
			}
		}

		return this;
	}

	private final void assertExpectedTUCostPrice(final ErrorMessage message, final IAttributeStorage tuAttributeStorage)
	{
		final ErrorMessage messageToUse = derive(message);

		final Collection<IAttributeStorage> vhuAttributeStorages = tuAttributeStorage.getChildAttributeStorages(true);
		assertNotEmpty(messageToUse.expect("No VHU storages found on TU"), vhuAttributeStorages);
		;

		for (final IAttributeStorage vhuAttributeStorage : vhuAttributeStorages)
		{
			final ErrorMessage vhuMessage = messageToUse
					.addContextInfo("VHU Storage", vhuAttributeStorage);
			vhuCostPriceExpectation.assertExpected(vhuMessage, vhuAttributeStorage);
		}
	}

	public HUWeightsExpectation<TUWeightsExpectations<ParentExpectationType>> newTUExpectation()
	{
		final HUWeightsExpectation<TUWeightsExpectations<ParentExpectationType>> expectation = new HUWeightsExpectation<>(this);
		expectations.add(expectation);

		return expectation;
	}

	public TUWeightsExpectations<ParentExpectationType> addTUExpectations(final HUWeightsExpectation<?>... expectations)
	{
		if (expectations == null || expectations.length == 0)
		{
			return this;
		}

		for (final HUWeightsExpectation<?> expectation : expectations)
		{
			addTUExpectation(expectation);
		}

		return this;
	}

	public TUWeightsExpectations<ParentExpectationType> addTUExpectation(final HUWeightsExpectation<?> expectation)
	{
		newTUExpectation()
				.copyFrom(expectation);
		return this;
	}

	public HUWeightsExpectation<?> defaultTUExpectation()
	{
		if (defaultTUExpectation == null)
		{
			defaultTUExpectation = new HUWeightsExpectation<>(this);
		}
		return defaultTUExpectation;
	}

	public TUWeightsExpectations<ParentExpectationType> setDefaultTUExpectation(final HUWeightsExpectation<?> defaultTUExpectation)
	{
		defaultTUExpectation()
				.copyFrom(defaultTUExpectation);

		return this;
	}

	public TUWeightsExpectations<ParentExpectationType> setVHUCostPriceExpectation(final HUAttributeExpectation<?> vhuCostPriceExpectation)
	{
		this.vhuCostPriceExpectation = vhuCostPriceExpectation;
		return this;
	}

	public TUWeightsExpectations<ParentExpectationType> setAttributeStorageFactory(final IAttributeStorageFactory attributeStorageFactory)
	{
		this._attributeStorageFactory = attributeStorageFactory;
		return this;
	}

	private final IAttributeStorageFactory getAttributeStorageFactory()
	{
		Check.assumeNotNull(_attributeStorageFactory, "_attributeStorageFactory set");
		return this._attributeStorageFactory;
	}

	public TUWeightsExpectations<ParentExpectationType> countTUs(final int countTUs)
	{
		this.countTUsExpected = countTUs;
		return this;
	}
}
