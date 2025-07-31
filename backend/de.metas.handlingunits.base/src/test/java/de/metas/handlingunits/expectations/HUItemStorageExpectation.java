/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.handlingunits.expectations;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_Item_Storage;
import de.metas.handlingunits.storage.IHUItemStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.product.ProductId;
import de.metas.uom.IUOMDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.IMutable;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class HUItemStorageExpectation<ParentExpectationType> extends AbstractHUExpectation<ParentExpectationType>
{
	private ProductId _productId;
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

		assertThat(storage).as(prefix + "HU Item Storage not null").isNotNull();

		if (_productId != null)
		{
			assertThat(ProductId.ofRepoId(storage.getM_Product_ID())).as(prefix + "Product").isEqualTo(_productId);
		}
		if (_qty != null)
		{
			assertThat(storage.getQty()).as(prefix + "Qty").isEqualByComparingTo(_qty);
		}
		if (_uom != null)
		{
			assertModelEquals(prefix + "UOM", _uom, Services.get(IUOMDAO.class).getById(storage.getC_UOM_ID()));
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
		return product(ProductId.ofRepoIdOrNull(product != null ? product.getM_Product_ID() : null));
	}

	public HUItemStorageExpectation<ParentExpectationType> product(final ProductId productId)
	{
		this._productId = productId;
		return this;
	}

	public ProductId getProductId()
	{
		return _productId;
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

		assertThat(storage.isEmpty(getProductId())).as("Storage shall be empty: " + storage).isTrue();

		storage.addQty(getProductId(), getQty(), getC_UOM());

		//
		// Capture if asked
		if (_huItemStorageRef != null)
		{
			final I_M_HU_Item_Storage huItemStorage = huStorageFactory
					.getHUStorageDAO()
					.retrieveItemStorage(huItem, getProductId());
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
