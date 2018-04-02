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
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IMutable;
import org.adempiere.util.test.ErrorMessage;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Storage;
import de.metas.handlingunits.model.X_M_HU_PI_Version;

public class HUStorageExpectation<ParentExpectationType> extends AbstractHUExpectation<ParentExpectationType>
{
	public static HUStorageExpectation<Object> newExpectation()
	{
		return new HUStorageExpectation<>(null);
	}

	private I_M_Product _product;
	private BigDecimal _qty;
	private I_C_UOM _uom;
	private Integer _tuIndex;
	private IMutable<I_M_HU_Storage> _huStorageToSetRef;

	public HUStorageExpectation(final ParentExpectationType parentExpectation)
	{
		super(parentExpectation);
	}

	public HUStorageExpectation<ParentExpectationType> assertExpected(final I_M_HU hu)
	{
		final ErrorMessage message = null;
		return assertExpected(message, hu);
	}

	public HUStorageExpectation<ParentExpectationType> assertExpected(final ErrorMessage message, final I_M_HU hu)
	{
		Check.assumeNotNull(hu, "hu not null");

		//
		// Retrieve the HU Storage
		Check.assumeNotNull(_product, "_product not null");
		final I_M_HU_Storage huStorage = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_HU_Storage.class, hu)
				.addEqualsFilter(I_M_HU_Storage.COLUMN_M_HU_ID, hu.getM_HU_ID())
				.addEqualsFilter(I_M_HU_Storage.COLUMN_M_Product_ID, _product.getM_Product_ID())
				.create()
				.firstOnly(I_M_HU_Storage.class);

		return assertExpected(message, huStorage);
	}

	public HUStorageExpectation<ParentExpectationType> assertExpected(final String message, final I_M_HU_Storage storage)
	{
		return assertExpected(newErrorMessage(message), storage);
	}

	public HUStorageExpectation<ParentExpectationType> assertExpected(final ErrorMessage message, final I_M_HU_Storage storage)
	{
		ErrorMessage messageToUse = derive(message)
				.addContextInfo("HU Storage", storage);

		assertNotNull(messageToUse.expect("HU Storage not null"), storage);

		if (_tuIndex != null)
		{
			final I_M_HU luHU = storage.getM_HU();
			messageToUse = messageToUse.addContextInfo("LU", luHU);
			assertEquals(messageToUse.expect("HU is Loading Unit"), X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit, Services.get(IHandlingUnitsBL.class).getHU_UnitType(luHU));

			final List<I_M_HU> tuHUs = handlingUnitsDAO.retrieveIncludedHUs(luHU);
			final I_M_HU tuHU = tuHUs.get(_tuIndex);
			messageToUse = messageToUse.addContextInfo("TU", tuHU);
			_tuIndex = null;
			return assertExpected(messageToUse, tuHU);
		}

		if (_product != null)
		{
			assertModelEquals(messageToUse.expect("Product"), _product, storage.getM_Product());
		}
		if (_qty != null)
		{
			assertEquals(messageToUse.expect("Qty"), _qty, storage.getQty());
		}
		if (_uom != null)
		{
			assertModelEquals(messageToUse.expect("UOM"), _uom, storage.getC_UOM());
		}

		//
		// Capture if asked
		if (_huStorageToSetRef != null)
		{
			_huStorageToSetRef.setValue(storage);
		}

		return this;
	}

	public HUStorageExpectation<ParentExpectationType> product(final I_M_Product product)
	{
		this._product = product;
		return this;
	}

	public I_M_Product getProduct()
	{
		return _product;
	}

	public HUStorageExpectation<ParentExpectationType> qty(final BigDecimal qty)
	{
		this._qty = qty;
		return this;
	}

	public HUStorageExpectation<ParentExpectationType> qty(final int qty)
	{
		return qty(new BigDecimal(qty));
	}

	public BigDecimal getQty()
	{
		return _qty;
	}

	public HUStorageExpectation<ParentExpectationType> uom(final I_C_UOM uom)
	{
		this._uom = uom;
		return this;
	}

	public I_C_UOM getC_UOM()
	{
		return _uom;
	}

	public HUStorageExpectation<ParentExpectationType> tuIndex(final int tuIndex)
	{
		this._tuIndex = tuIndex;
		return this;
	}

	/**
	 * Capture the {@link I_M_HU_Storage} while we are testing.
	 *
	 * @param huStorageToSetRef
	 * @return this
	 */
	public HUStorageExpectation<ParentExpectationType> capture(final IMutable<I_M_HU_Storage> huStorageToSetRef)
	{
		this._huStorageToSetRef = huStorageToSetRef;
		return this;
	}

}
