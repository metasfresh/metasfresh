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

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.lang.IMutable;
import org.adempiere.util.test.ErrorMessage;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Storage;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.storage.spi.hu.IHUStorageBL;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

public class HUStorageExpectation<ParentExpectationType> extends AbstractHUExpectation<ParentExpectationType>
{
	public static HUStorageExpectation<Object> newExpectation()
	{
		return new HUStorageExpectation<>(null);
	}

	private ProductId _productId;
	private BigDecimal _qty;
	private I_C_UOM _uom;
	private Integer _tuIndex;
	private IMutable<I_M_HU_Storage> _huStorageToSetRef;

	private HUStorageExpectation(final ParentExpectationType parentExpectation)
	{
		super(parentExpectation);
	}

	public HUStorageExpectation<ParentExpectationType> assertExpected(final I_M_HU hu)
	{
		final ErrorMessage message = null;
		final HuId huId = HuId.ofRepoId(hu.getM_HU_ID());
		return assertExpected(message, huId);
	}

	public HUStorageExpectation<ParentExpectationType> assertExpected(final HuId huId)
	{
		final ErrorMessage message = null;
		return assertExpected(message, huId);
	}

	public HUStorageExpectation<ParentExpectationType> assertExpected(
			@Nullable final ErrorMessage message,
			@NonNull final HuId huId)
	{
		//
		// Retrieve the HU Storage
		Check.assumeNotNull(_productId, "_productId not null");
		final I_M_HU_Storage huStorage = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_HU_Storage.class)
				.addEqualsFilter(I_M_HU_Storage.COLUMNNAME_M_HU_ID, huId)
				.addEqualsFilter(I_M_HU_Storage.COLUMNNAME_M_Product_ID, _productId)
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
			return assertExpected(messageToUse, HuId.ofRepoId(tuHU.getM_HU_ID()));
		}

		if (_productId != null)
		{
			final ProductId storageProductId = ProductId.ofRepoId(storage.getM_Product_ID());
			assertEquals(messageToUse.expect("Product"), _productId, storageProductId);
		}
		if (_qty != null)
		{
			assertEquals(messageToUse.expect("Qty"), _qty, storage.getQty());
		}
		if (_uom != null)
		{
			assertModelEquals(messageToUse.expect("UOM"), _uom, IHUStorageBL.extractUOM(storage));
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
		return product(ProductId.ofRepoId(product.getM_Product_ID()));
	}

	public HUStorageExpectation<ParentExpectationType> product(final ProductId productId)
	{
		this._productId = productId;
		return this;
	}

	public HUStorageExpectation<ParentExpectationType> qty(final Quantity qty)
	{
		qty(qty.toBigDecimal());
		uom(qty.getUOM());
		return this;
	}

	public HUStorageExpectation<ParentExpectationType> qty(final BigDecimal qty)
	{
		this._qty = qty;
		return this;
	}

	public HUStorageExpectation<ParentExpectationType> qty(final String qty)
	{
		return qty(new BigDecimal(qty));
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
