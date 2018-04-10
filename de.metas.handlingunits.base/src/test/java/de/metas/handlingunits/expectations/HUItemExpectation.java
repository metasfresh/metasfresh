package de.metas.handlingunits.expectations;

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
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.Env;
import org.junit.Assert;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.impl.HUAndItemsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_Item_Storage;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;

public class HUItemExpectation<ParentExpectationType> extends AbstractHUExpectation<ParentExpectationType>
{

	private String _itemType;
	private I_M_HU_PI_Item _piItem;
	private List<HUExpectation<HUItemExpectation<ParentExpectationType>>> includedHUExpectations = null;
	private List<HUItemStorageExpectation<HUItemExpectation<ParentExpectationType>>> itemStorageExpectations = null;

	private BigDecimal _qty = null;
	private I_M_HU_PackingMaterial _packingMaterial = null;

	public HUItemExpectation(final ParentExpectationType parentExpectation)
	{
		super(parentExpectation);
	}

	public HUItemExpectation<ParentExpectationType> assertExpected(final String message, final I_M_HU_Item huItem)
	{
		final String prefix = (message == null ? "" : message)
				+ "\n HU Item: " + huItem
				+ "\n\nInvalid: ";
		Assert.assertNotNull(prefix + "HU Item not null", huItem);

		if (_piItem != null)
		{
			assertModelEquals(prefix + "PI Item", _piItem, Services.get(IHandlingUnitsBL.class).getPIItem(huItem));
		}

		if (_itemType != null)
		{
			final String actual_ItemType = huItem.getItemType();
			Assert.assertEquals(prefix + "ItemType", _itemType, actual_ItemType);
		}

		if (_qty != null)
		{
			assertThat(prefix + "Qty", huItem.getQty(), comparesEqualTo(_qty));
		}

		if (_packingMaterial != null)
		{
			assertThat(prefix + "PackingMaterial", _packingMaterial, is(Services.get(IHandlingUnitsBL.class).getHUPackingMaterial(huItem)));
		}

		if (includedHUExpectations != null)
		{
			final List<I_M_HU> includedHUs = handlingUnitsDAO.retrieveIncludedHUs(huItem);
			assertExpectedIncludedHUs(message + " Included HUs", includedHUs);
		}

		if (itemStorageExpectations != null)
		{
			final List<I_M_HU_Item_Storage> storages = huStorageDAO.retrieveItemStorages(huItem);
			assertExpectedHUItemStorages(message + "HU Item Storages", storages);
		}

		return this;
	}

	private void assertExpectedIncludedHUs(final String message, final List<I_M_HU> includedHUs)
	{
		final int count = includedHUs.size();
		final int expectedCount = includedHUExpectations.size();

		Assert.assertEquals(message + " included HUs count", expectedCount, count);

		for (int i = 0; i < count; i++)
		{
			final I_M_HU includedHU = includedHUs.get(i);

			final String prefix = (message == null ? "" : message)
					+ "\n Included HU Index: " + (i + 1) + "/" + count;

			includedHUExpectations.get(i).assertExpected(prefix, includedHU);
		}
	}

	private void assertExpectedHUItemStorages(final String message, final List<I_M_HU_Item_Storage> storages)
	{
		final int count = storages.size();
		final int expectedCount = itemStorageExpectations.size();

		Assert.assertEquals(message + " included M_HU_Item_Storages count", expectedCount, count);

		for (int i = 0; i < count; i++)
		{
			final I_M_HU_Item_Storage huItemStorage = storages.get(i);

			final String prefix = (message == null ? "" : message)
					+ "\n HU Item Storage Index: " + (i + 1) + "/" + count;

			itemStorageExpectations.get(i).assertExpected(prefix, huItemStorage);
		}
	}

	public I_M_HU_Item createHUItem(final I_M_HU hu)
	{
		Check.assumeNotNull(hu, "hu not null");

		final I_M_HU_PI_Item piItem = getM_HU_PI_Item();
		final I_M_HU_Item huItem = HUAndItemsDAO.createHUItemNoSave(hu, piItem);
		InterfaceWrapperHelper.save(huItem);

		if (includedHUExpectations != null)
		{
			for (final HUExpectation<HUItemExpectation<ParentExpectationType>> includedHUExpectation : includedHUExpectations)
			{
				includedHUExpectation.createHU(huItem);
			}
		}

		if (itemStorageExpectations != null)
		{
			for (final HUItemStorageExpectation<HUItemExpectation<ParentExpectationType>> itemStorageExpectation : itemStorageExpectations)
			{
				itemStorageExpectation.createHUItemStorage(huItem);
			}
		}

		return huItem;
	}

	public HUItemExpectation<ParentExpectationType> itemType(final String itemType)
	{
		this._itemType = itemType;
		return this;
	}

	public String getItemType()
	{
		return this._itemType;
	}

	public HUItemExpectation<ParentExpectationType> huPIItem(final I_M_HU_PI_Item piItem)
	{
		this._piItem = piItem;
		return this;
	}

	/**
	 * Assert that this item has the given qty set.
	 * 
	 * @param string
	 * @return
	 */
	public HUItemExpectation<ParentExpectationType> qty(String qtyStr)
	{
		this._qty = new BigDecimal(qtyStr);
		return this;
	}

	public HUItemExpectation<ParentExpectationType> packingMaterial(I_M_HU_PackingMaterial packingMaterial)
	{
		this._packingMaterial = packingMaterial;
		return this;
	}

	public I_M_HU_PI_Item getM_HU_PI_Item()
	{
		return this._piItem;
	}

	public HUExpectation<HUItemExpectation<ParentExpectationType>> newIncludedHUExpectation()
	{
		final HUExpectation<HUItemExpectation<ParentExpectationType>> expectation = new HUExpectation<>(this);

		if (includedHUExpectations == null)
		{
			includedHUExpectations = new ArrayList<>();
		}
		includedHUExpectations.add(expectation);
		return expectation;
	}

	/**
	 * Convenience method that does
	 * <pre>
	 * newIncludedHUExpectation().huPI(virtualPI);
	 * </pre>
	 * i.e. creates an new included-HU-expectation and directly expects that HU to have the "virtual" packing instruction.
	 * 
	 * @return
	 */
	public HUExpectation<HUItemExpectation<ParentExpectationType>> newIncludedVirtualHU()
	{
		final I_M_HU_PI virtualPI = handlingUnitsDAO.retrieveVirtualPI(Env.getCtx());
		return newIncludedHUExpectation()
				.huPI(virtualPI);
	}

	public HUItemExpectation<ParentExpectationType> noIncludedHUs()
	{
		includedHUExpectations = new ArrayList<>();
		return this;
	}

	public HUItemStorageExpectation<HUItemExpectation<ParentExpectationType>> newItemStorageExpectation()
	{
		final HUItemStorageExpectation<HUItemExpectation<ParentExpectationType>> expectation = new HUItemStorageExpectation<>(this);
		if (itemStorageExpectations == null)
		{
			itemStorageExpectations = new ArrayList<>();
		}
		itemStorageExpectations.add(expectation);

		return expectation;
	}

	public HUItemExpectation<ParentExpectationType> noItemStorages()
	{
		itemStorageExpectations = new ArrayList<>();
		return this;
	}
}
