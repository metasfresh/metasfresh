package de.metas.handlingunits.storage.impl;

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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_Storage;
import de.metas.handlingunits.storage.IGenericHUStorage;
import de.metas.handlingunits.storage.IHUItemStorage;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.IHUStorageDAO;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.handlingunits.storage.IProductStorage;

/* package */class HUStorage implements IHUStorage
{
	// Services
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	private final IHUStorageFactory storageFactory;
	private final IHUStorageDAO dao;
	
	private final I_M_HU hu;
	private final boolean virtualHU;

	public HUStorage(final IHUStorageFactory storageFactory, final I_M_HU hu)
	{
		Check.assumeNotNull(storageFactory, "storageFactory not null");
		this.storageFactory = storageFactory;

		dao = storageFactory.getHUStorageDAO();
		Check.assumeNotNull(dao, "dao not null");

		Check.assumeNotNull(hu, "HU not null");
		Check.assumeNotNull(hu.getM_HU_ID() > 0, "HU is saved: {}", hu);
		this.hu = hu;
		virtualHU = Services.get(IHandlingUnitsBL.class).isVirtual(hu);
	}

	@Override
	public IHUStorageFactory getHUStorageFactory()
	{
		return storageFactory;
	}

	private I_M_HU_Storage getCreateStorageLine(final I_M_Product product, final I_C_UOM uomIfNew)
	{
		I_M_HU_Storage storage = dao.retrieveStorage(hu, product.getM_Product_ID());
		if (storage == null)
		{
			storage = dao.newInstance(I_M_HU_Storage.class, hu);
			storage.setM_HU(hu);
			storage.setM_Product(product);
			storage.setC_UOM(uomIfNew);
			storage.setQty(BigDecimal.ZERO);

			// don't save it; it will be saved after Qty update
			// dao.save(storage);
		}
		return storage;
	}

	@Override
	public IGenericHUStorage getParentStorage()
	{
		final I_M_HU_Item parentItem = handlingUnitsDAO.retrieveParentItem(hu);
		if (parentItem == null)
		{
			return null;
		}

		if (virtualHU)
		{
			final IHUItemStorage parentStorage = storageFactory.getStorage(parentItem);
			return parentStorage;
		}
		else
		{
			final I_M_HU huParent = parentItem.getM_HU();
			if (huParent == null)
			{
				return null;
			}

			final IHUStorage parentStorage = storageFactory.getStorage(huParent);
			return parentStorage;
		}
	}

	@Override
	public BigDecimal getQty(final I_M_Product product, final I_C_UOM uom)
	{
		final I_M_HU_Storage storageLine = getCreateStorageLine(product, uom);
		final BigDecimal qty = storageLine.getQty();
		final BigDecimal qtyConv = uomConversionBL.convertQty(product, qty, storageLine.getC_UOM(), uom);
		return qtyConv;
	}

	@Override
	public void addQty(final I_M_Product product, final BigDecimal qty, final I_C_UOM uom)
	{
		if (qty.signum() == 0)
		{
			return;
		}

		final I_M_HU_Storage storageLine = getCreateStorageLine(product, uom);

		final I_C_UOM uomStorage = storageLine.getC_UOM();
		final BigDecimal qtyConv = uomConversionBL.convertQty(product, qty, uom, uomStorage);

		//
		// Update storage line
		final BigDecimal qtyOld = storageLine.getQty(); // UOM=uomStorage
		final BigDecimal qtyNew = qtyOld.add(qtyConv);
		storageLine.setQty(qtyNew);

		dao.save(storageLine);

		//
		// Roll-up
		rollupIncremental(product, qtyConv, uomStorage);
	}

	private void rollupIncremental(final I_M_Product product, final BigDecimal qtyDelta, final I_C_UOM uom)
	{
		final IGenericHUStorage parentStorage = getParentStorage();
		if (parentStorage != null)
		{
			parentStorage.addQty(product, qtyDelta, uom);
		}
	}

	@Override
	public void rollup()
	{
		for (final IHUProductStorage productStorage : getProductStorages())
		{
			final I_M_Product product = productStorage.getM_Product();
			final BigDecimal qtyDelta = productStorage.getQty();
			final I_C_UOM uom = productStorage.getC_UOM();
			rollupIncremental(product, qtyDelta, uom);
		}
	}

	@Override
	public void rollupRevert()
	{
		for (final IHUProductStorage productStorage : getProductStorages())
		{
			final I_M_Product product = productStorage.getM_Product();
			final BigDecimal qtyDelta = productStorage.getQty().negate();
			final I_C_UOM uom = productStorage.getC_UOM();
			rollupIncremental(product, qtyDelta, uom);
		}
	}

	@Override
	public boolean isEmpty()
	{
		final List<I_M_HU_Storage> storages = dao.retrieveStorages(hu);
		for (final I_M_HU_Storage storage : storages)
		{
			if (!isEmpty(storage))
			{
				return false;
			}
		}

		return true;
	}

	private boolean isEmpty(final I_M_HU_Storage storage)
	{
		final BigDecimal qty = storage.getQty();
		if (qty.signum() != 0)
		{
			return false;
		}

		return true;
	}

	@Override
	public boolean isEmpty(final I_M_Product product)
	{
		Check.assumeNotNull(product, "product not null");
		final I_M_HU_Storage storage = dao.retrieveStorage(hu, product.getM_Product_ID());
		if (storage == null)
		{
			return true;
		}

		return isEmpty(storage);
	}

	@Override
	public boolean isSingleProductStorage()
	{
		final List<I_M_HU_Storage> storages = dao.retrieveStorages(hu);

		// Case: there is no product on storage
		// => this can be a single product storage
		if (storages.isEmpty())
		{
			return true;
		}

		final Set<Integer> productIds = new HashSet<Integer>();
		for (final I_M_HU_Storage storage : storages)
		{
			if (isEmpty(storage))
			{
				continue;
			}

			final int productId = storage.getM_Product_ID();
			productIds.add(productId);
			if (productIds.size() > 1)
			{
				// we found more then one product on storage
				// => not a single product storage
				return false;
			}
		}

		// If we reach this point it means there are no products on this storage (i.e. storage is empty)
		// => this can be a single product storage
		return true;
	}
	
	@Override
	public final I_M_Product getSingleProductOrNull()
	{
		final List<I_M_HU_Storage> storages = dao.retrieveStorages(hu);
		
		I_M_Product product = null;
		for (final I_M_HU_Storage storage : storages)
		{
			if (product == null)
			{
				product = storage.getM_Product();
			}
			else if (product.getM_Product_ID() != storage.getM_Product_ID())
			{
				// found more then one product stored in our HU
				return null;
			}
			else
			{
				// same product => go on
			}
		}
		
		return product;
	}

	@Override
	public List<IHUProductStorage> getProductStorages()
	{
		final List<I_M_HU_Storage> storages = dao.retrieveStorages(hu);
		final List<IHUProductStorage> productStorages = new ArrayList<IHUProductStorage>(storages.size());
		for (final I_M_HU_Storage storage : storages)
		{
			final IHUProductStorage productStorage = createProductStorage(storage);
			productStorages.add(productStorage);
		}

		return productStorages;
	}

	@Override
	public IHUProductStorage getProductStorage(final I_M_Product product)
	{
		final IHUProductStorage productStorage = getProductStorageOrNull(product);
		if (productStorage == null)
		{
			throw new AdempiereException("@NotFound@ M_HU_Storage_ID@ (@M_Product_ID@:" + product.getName() + ")");
		}

		return productStorage;
	}

	@Override
	public IHUProductStorage getProductStorageOrNull(final I_M_Product product)
	{
		Check.assumeNotNull(product, "product not null");
		final I_M_HU_Storage storage = dao.retrieveStorage(hu, product.getM_Product_ID());
		if (storage == null)
		{
			return null;
		}
		final IHUProductStorage productStorage = createProductStorage(storage);
		return productStorage;
	}

	@Override
	public final BigDecimal getQtyForProductStorages()
	{
		BigDecimal fullCUQty = BigDecimal.ZERO;
		for (final IProductStorage productStorage : getProductStorages())
		{
			fullCUQty = fullCUQty.add(productStorage.getQty());
		}
		return fullCUQty;
	}

	private final IHUProductStorage createProductStorage(final I_M_HU_Storage storage)
	{
		final I_M_Product product = storage.getM_Product();
		final I_C_UOM uom = storage.getC_UOM();
		final HUProductStorage productStorage = new HUProductStorage(this, product, uom);
		return productStorage;
	}

	@Override
	public boolean isVirtual()
	{
		return virtualHU;
	}

	@Override
	public I_M_HU getM_HU()
	{
		return hu;
	}

	@Override
	public String toString()
	{
		return "HUStorage [hu=" + hu + ", virtualHU=" + virtualHU + "]";
	}

	@Override
	public I_C_UOM getC_UOMOrNull()
	{
		return dao.getC_UOMOrNull(hu);
	}
}
