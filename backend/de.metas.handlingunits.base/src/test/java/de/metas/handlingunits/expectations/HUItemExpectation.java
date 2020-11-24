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

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.Env;
import org.junit.Assert;

import de.metas.handlingunits.HUItemType;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.impl.HUAndItemsDAO;
import de.metas.handlingunits.inout.IHUPackingMaterialDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_Item_Storage;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.quantity.QuantityTU;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

public class HUItemExpectation<ParentExpectationType> extends AbstractHUExpectation<ParentExpectationType>
{
	public static HUItemExpectation<Object> newExpectation()
	{
		return new HUItemExpectation<>(null);
	}

	private HUItemType itemType;
	private I_M_HU_PI_Item _piItem;
	private List<HUExpectation<?>> includedHUExpectations = null;
	private List<HUItemStorageExpectation<HUItemExpectation<ParentExpectationType>>> itemStorageExpectations = null;

	private QuantityTU qtyTUs = null;
	private I_M_HU_PackingMaterial _packingMaterial = null;

	HUItemExpectation(final ParentExpectationType parentExpectation)
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

		if (itemType != null)
		{
			final HUItemType actual_ItemType = HUItemType.ofNullableCode(huItem.getItemType());
			Assert.assertEquals(prefix + "ItemType", itemType, actual_ItemType);
		}

		if (qtyTUs != null)
		{
			assertThat(prefix + "Qty", huItem.getQty(), comparesEqualTo(qtyTUs.toBigDecimal()));
		}

		if (_packingMaterial != null)
		{
			assertThat(prefix + "PackingMaterial", _packingMaterial, is(Services.get(IHUPackingMaterialDAO.class).retrieveHUPackingMaterialOrNull(huItem)));
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
			for (final HUExpectation<?> includedHUExpectation : includedHUExpectations)
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

	public HUItemExpectation<ParentExpectationType> itemType(@Nullable final String itemType)
	{
		return itemType(HUItemType.ofNullableCode(itemType));
	}

	public HUItemExpectation<ParentExpectationType> itemType(@Nullable final HUItemType itemType)
	{
		this.itemType = itemType;
		return this;
	}

	public HUItemExpectation<ParentExpectationType> huPIItem(final I_M_HU_PI_Item piItem)
	{
		this._piItem = piItem;
		return this;
	}

	public HUItemExpectation<ParentExpectationType> qty(@NonNull final String qtyTUs)
	{
		return qtyTUs(QuantityTU.ofInt(Integer.parseInt(qtyTUs)));
	}

	public HUItemExpectation<ParentExpectationType> qtyTUs(final int qtyTUs)
	{
		return qtyTUs(QuantityTU.ofInt(qtyTUs));
	}

	public HUItemExpectation<ParentExpectationType> qtyTUs(QuantityTU qtyTUs)
	{
		this.qtyTUs = qtyTUs;
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

	public HUExpectation<HUItemExpectation<ParentExpectationType>> includedHU()
	{
		final HUExpectation<HUItemExpectation<ParentExpectationType>> expectation = new HUExpectation<>(this);
		includedHU(expectation);
		return expectation;
	}

	public HUItemExpectation<ParentExpectationType> includedHU(@NonNull final HUExpectation<?> includedHU)
	{
		if (includedHUExpectations == null)
		{
			includedHUExpectations = new ArrayList<>();
		}
		includedHUExpectations.add(includedHU);

		return this;
	}

	/**
	 * Convenience method that does
	 * 
	 * <pre>
	 * newIncludedHUExpectation().huPI(virtualPI);
	 * </pre>
	 * 
	 * i.e. creates an new included-HU-expectation and directly expects that HU to have the "virtual" packing instruction.
	 * 
	 * @return
	 */
	public HUExpectation<HUItemExpectation<ParentExpectationType>> includedVirtualHU()
	{
		final I_M_HU_PI virtualPI = handlingUnitsDAO.retrieveVirtualPI(Env.getCtx());
		return includedHU()
				.huPI(virtualPI);
	}

	public HUItemExpectation<ParentExpectationType> noIncludedHUs()
	{
		includedHUExpectations = new ArrayList<>();
		return this;
	}

	public HUItemStorageExpectation<HUItemExpectation<ParentExpectationType>> storage()
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
