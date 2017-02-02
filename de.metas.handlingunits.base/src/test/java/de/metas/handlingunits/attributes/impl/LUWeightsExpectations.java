package de.metas.handlingunits.attributes.impl;

import java.util.ArrayList;

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

import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.test.ErrorMessage;

import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.expectations.AbstractHUExpectation;
import de.metas.handlingunits.expectations.HUAttributeExpectation;
import de.metas.handlingunits.expectations.HUWeightsExpectation;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU_Item;

public class LUWeightsExpectations<ParentExpectationType> extends AbstractHUExpectation<ParentExpectationType>
{
	private IAttributeStorageFactory attributeStorageFactory;

	// Expectations
	private I_M_HU_PI_Item luPIItem;
	private int tuCount;
	private HUWeightsExpectation<?> luWeightsExpectation;
	private TUWeightsExpectations<LUWeightsExpectations<ParentExpectationType>> tuWeightsExpectations;

	public LUWeightsExpectations()
	{
		super();
	}

	public final void assertExpected(final I_M_HU lu)
	{
		final ErrorMessage message = newErrorMessage("")
				.addContextInfo("LU", lu);

		//
		// LU expectation
		if (luWeightsExpectation != null)
		{
			final IAttributeStorage luAttributeStorage = attributeStorageFactory.getAttributeStorage(lu);
			luWeightsExpectation.assertExpected(message.expect("LU"), luAttributeStorage);
		}

		//
		// Iterate through the loadingUnit's items
		int tuCountActual = 0;
		final List<I_M_HU_Item> luItems = Services.get(IHandlingUnitsDAO.class).retrieveItems(lu);
		final List<I_M_HU> tus = new ArrayList<>();
		for (final I_M_HU_Item luItem : luItems)
		{
			// We only have one item with handling unit Item Type
			final String itemType = luItem.getItemType();
			if (!itemType.equals(X_M_HU_Item.ITEMTYPE_HUAggregate) && !itemType.equals(X_M_HU_Item.ITEMTYPE_HandlingUnit))
			{
				continue;
			}

			InterfaceWrapperHelper.refresh(luItem, true);

			final List<I_M_HU> luItemTUs = Services.get(IHandlingUnitsDAO.class).retrieveIncludedHUs(luItem);
			if (itemType.equals(X_M_HU_Item.ITEMTYPE_HandlingUnit))
			{
				tuCountActual += luItemTUs.size();
			}
			else
			{ // luItem is an HU aggregate item
				tuCountActual += luItem.getQty().intValue();
			}

			tus.addAll(luItemTUs);
		}

		tuWeightsExpectations.setAttributeStorageFactory(attributeStorageFactory);
		tuWeightsExpectations.assertExpected(message, tus);
		assertEquals(message.expect("Invalid number of TUs on LU"), tuCount, tuCountActual);
	}

	public LUWeightsExpectations<ParentExpectationType> setAttributeStorageFactory(final IAttributeStorageFactory attributeStorageFactory)
	{
		this.attributeStorageFactory = attributeStorageFactory;
		return this;
	}

	public LUWeightsExpectations<ParentExpectationType> luPIItem(final I_M_HU_PI_Item luPIItem)
	{
		this.luPIItem = luPIItem;
		return this;
	}

	public LUWeightsExpectations<ParentExpectationType> tuCount(final int tuCount)
	{
		this.tuCount = tuCount;
		return this;
	}

	public LUWeightsExpectations<ParentExpectationType> setLUWeightsExpectation(final HUWeightsExpectation<?> luWeightsExpectation)
	{
		this.luWeightsExpectation = luWeightsExpectation;
		return this;
	}

	public HUWeightsExpectation<LUWeightsExpectations<ParentExpectationType>> luWeightsExpectation()
	{
		if (luWeightsExpectation == null)
		{
			luWeightsExpectation = new HUWeightsExpectation<>(this);
		}

		@SuppressWarnings("unchecked")
		final HUWeightsExpectation<LUWeightsExpectations<ParentExpectationType>> luWeightsExpectationCast = (HUWeightsExpectation<LUWeightsExpectations<ParentExpectationType>>)luWeightsExpectation;
		return luWeightsExpectationCast;
	}

	public TUWeightsExpectations<LUWeightsExpectations<ParentExpectationType>> tuExpectations()
	{
		if (tuWeightsExpectations == null)
		{
			tuWeightsExpectations = new TUWeightsExpectations<>(this);

			// just create an dummy/null default expectation
			tuWeightsExpectations.defaultTUExpectation();
		}
		return tuWeightsExpectations;
	}

	public LUWeightsExpectations<ParentExpectationType> setVHUCostPriceExpectation(final HUAttributeExpectation<?> vhuCostPriceExpectation)
	{
		tuExpectations()
				.setVHUCostPriceExpectation(vhuCostPriceExpectation);

		return this;
	}

}
