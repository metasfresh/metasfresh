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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.IMutable;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.junit.Assert;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_Item_Storage;
import de.metas.handlingunits.storage.IHUItemStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;

public class HUItemStorageExpectation<ParentExpectationType> extends AbstractHUExpectation<ParentExpectationType>
{
	private I_M_Product _product;
	private BigDecimal _qty;
	private I_C_UOM _uom;
	private IMutable<I_M_HU_Item_Storage> _huItemStorageRef;

	public HUItemStorageExpectation(final ParentExpectationType parentExpectation)
	{
		super(parentExpectation);
	}

	public HUItemStorageExpectation<ParentExpectationType> assertExpected(final String message, final I_M_HU_Item_Storage storage)
	{
		final String prefix = (message == null ? "" : message)
				+ "\n\nInvalid: ";

		Assert.assertNotNull(prefix + "HU Item Storage not null", storage);

		if (_product != null)
		{
			assertModelEquals(prefix + "Product", _product, storage.getM_Product());
		}
		if (_qty != null)
		{
			assertEquals(prefix + "Qty", _qty, storage.getQty());
		}
		if (_uom != null)
		{
			assertModelEquals(prefix + "UOM", _uom, storage.getC_UOM());
		}

		//
		// Capture if asked
		if (_huItemStorageRef != null)
		{
			_huItemStorageRef.setValue(storage);
		}
		
		return this;
	}

	public HUItemStorageExpectation<ParentExpectationType> product(final I_M_Product product)
	{
		this._product = product;
		return this;
	}

	public I_M_Product getProduct()
	{
		return _product;
	}

	public HUItemStorageExpectation<ParentExpectationType> qty(final BigDecimal qty)
	{
		this._qty = qty;
		return this;
	}

	public HUItemStorageExpectation<ParentExpectationType> qty(final String qtyStr)
	{
		return qty(new BigDecimal(qtyStr));
	}

	public BigDecimal getQty()
	{
		return _qty;
	}

	public HUItemStorageExpectation<ParentExpectationType> uom(final I_C_UOM uom)
	{
		this._uom = uom;
		return this;
	}

	public I_C_UOM getC_UOM()
	{
		return _uom;
	}

	public void createHUItemStorage(final I_M_HU_Item huItem)
	{
		// NOTE: we are creating the HU_Item_Storage by using IHUItemStorage because we want to trigger the rollup too

		// final I_M_HU_Item_Storage huItemStorage = InterfaceWrapperHelper.newInstance(I_M_HU_Item_Storage.class, huItem);
		// huItemStorage.setM_HU_Item(huItem);
		// huItemStorage.setM_Product(getProduct());
		// huItemStorage.setQty(getQty());
		// huItemStorage.setC_UOM(getC_UOM());
		// InterfaceWrapperHelper.save(huItemStorage);

		final IContextAware contextProvider = InterfaceWrapperHelper.getContextAware(huItem);
		final IHUContext huContext = Services.get(IHUContextFactory.class).createMutableHUContext(contextProvider);
		final IHUStorageFactory huStorageFactory = huContext.getHUStorageFactory();
		final IHUItemStorage storage = huStorageFactory.getStorage(huItem);

		Assert.assertTrue("Storage shall be empty: " + storage, storage.isEmpty(getProduct()));

		storage.addQty(getProduct(), getQty(), getC_UOM());
		
		//
		// Capture if asked
		if (_huItemStorageRef != null)
		{
			final I_M_HU_Item_Storage huItemStorage = huStorageFactory
					.getHUStorageDAO()
					.retrieveItemStorage(huItem, getProduct());
			Check.assumeNotNull(huItemStorage, "huItemStorage not null");
			_huItemStorageRef.setValue(huItemStorage);
		}
	}

	public HUItemStorageExpectation<ParentExpectationType> capture(final IMutable<I_M_HU_Item_Storage> huItemStorageRef)
	{
		this._huItemStorageRef = huItemStorageRef;
		return this;
	}
}
