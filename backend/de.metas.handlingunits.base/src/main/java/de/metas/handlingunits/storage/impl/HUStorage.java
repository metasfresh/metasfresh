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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import com.google.common.collect.ImmutableList;
import de.metas.common.util.CoalesceUtil;
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
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.storage.spi.hu.IHUStorageBL;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/* package */class HUStorage implements IHUStorage
{
	// Services
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	private final IHUStorageFactory storageFactory;
	private final IHUStorageDAO dao;

	private final I_M_HU hu;
	private final boolean virtualHU;

	public HUStorage(
			@NonNull final IHUStorageFactory storageFactory,
			@NonNull final I_M_HU hu)
	{
		this.storageFactory = storageFactory;

		dao = storageFactory.getHUStorageDAO();
		Check.assumeNotNull(dao, "HUStorageDAO needs to be not-null; storageFactory={}", storageFactory);

		Check.errorUnless(hu.getM_HU_ID() > 0, "Given HU has to be saved; hu={}", hu);
		this.hu = hu;
		virtualHU = Services.get(IHandlingUnitsBL.class).isVirtual(hu);
	}

	private I_M_HU_Storage retrieveOrCreateStorageLine(
			final ProductId productId,
			final I_C_UOM uomIfNew)
	{
		I_M_HU_Storage storage = dao.retrieveStorage(hu, productId);
		if (storage == null)
		{
			storage = dao.newInstance(I_M_HU_Storage.class, hu);
			storage.setM_HU(hu);
			storage.setM_Product_ID(productId.getRepoId());
			storage.setC_UOM_ID(uomIfNew.getC_UOM_ID());
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
	public BigDecimal getQty(final ProductId productId, final I_C_UOM uom)
	{
		final I_M_HU_Storage storageLine = retrieveOrCreateStorageLine(productId, uom);
		final BigDecimal qty = storageLine.getQty();
		final I_C_UOM qtyUOM = IHUStorageBL.extractUOM(storageLine);
		final BigDecimal qtyConv = uomConversionBL.convertQty(productId, qty, qtyUOM, uom);
		return qtyConv;
	}

	@Override
	public Optional<Quantity> getQuantity(ProductId productId)
	{
		final I_M_HU_Storage storage = dao.retrieveStorage(hu, productId);
		return storage != null
				? Optional.of(Quantitys.of(storage.getQty(), UomId.ofRepoId(storage.getC_UOM_ID())))
				: Optional.empty();
	}

	@Override
	public void addQty(final ProductId productId, final BigDecimal qty, final I_C_UOM uom)
	{
		if (qty.signum() == 0)
		{
			return;
		}

		final I_M_HU_Storage storageLine = retrieveOrCreateStorageLine(productId, uom);

		final I_C_UOM uomStorage = IHUStorageBL.extractUOM(storageLine);
		final BigDecimal qtyConv = uomConversionBL.convertQty(productId, qty, uom, uomStorage);

		//
		// Update storage line
		final BigDecimal qtyOld = storageLine.getQty(); // UOM=uomStorage
		final BigDecimal qtyNew = qtyOld.add(qtyConv);
		storageLine.setQty(qtyNew);

		dao.save(storageLine);

		//
		// Roll-up
		rollupIncremental(productId, Quantity.of(qtyConv, uomStorage));
	}

	private void rollupIncremental(final ProductId productId, final Quantity qtyDelta)
	{
		final IGenericHUStorage parentStorage = getParentStorage();
		if (parentStorage != null)
		{
			parentStorage.addQty(productId, qtyDelta.toBigDecimal(), qtyDelta.getUOM());
		}
	}

	@Override
	public void rollup()
	{
		for (final IHUProductStorage productStorage : getProductStorages())
		{
			final ProductId productId = productStorage.getProductId();
			final Quantity qtyDelta = productStorage.getQty();
			rollupIncremental(productId, qtyDelta);
		}
	}

	@Override
	public void rollupRevert()
	{
		for (final IHUProductStorage productStorage : getProductStorages())
		{
			final ProductId productId = productStorage.getProductId();
			final Quantity qtyDelta = productStorage.getQty().negate();
			rollupIncremental(productId, qtyDelta);
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
	public boolean isEmpty(@NonNull final ProductId productId)
	{
		final I_M_HU_Storage storage = dao.retrieveStorage(hu, productId);
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

		final Set<Integer> productIds = new HashSet<>();
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
	public ProductId getSingleProductIdOrNull()
	{
		final List<I_M_HU_Storage> storages = dao.retrieveStorages(hu);

		final int productRepoId = CollectionUtils.extractSingleElementOrDefault(
				storages,
				I_M_HU_Storage::getM_Product_ID,
				-1);

		return ProductId.ofRepoIdOrNull(productRepoId);
	}

	@Override
	public final I_M_Product getSingleProductOrNull()
	{
		ProductId productId = getSingleProductIdOrNull();
		if (productId == null)
		{
			return null;
		}

		return Services.get(IProductDAO.class).getById(productId);
	}

	@Override
	@NonNull
	public IHUProductStorage getSingleHUProductStorage()
	{
		final List<IHUProductStorage> productStorages = streamProductStorages()
				.filter(huProductStorage -> !huProductStorage.isEmpty())
				.collect(ImmutableList.toImmutableList());

		return CollectionUtils.singleElement(productStorages);
	}

	@Override
	public List<IHUProductStorage> getProductStorages()
	{
		final List<I_M_HU_Storage> storages = dao.retrieveStorages(hu);
		final List<IHUProductStorage> productStorages = new ArrayList<>(storages.size());
		for (final I_M_HU_Storage storage : storages)
		{
			final IHUProductStorage productStorage = createProductStorage(storage);
			productStorages.add(productStorage);
		}

		return productStorages;
	}

	@Override
	public IHUProductStorage getProductStorage(final ProductId productId)
	{
		final IHUProductStorage productStorage = getProductStorageOrNull(productId);
		if (productStorage == null)
		{
			throw new AdempiereException("@NotFound@ @M_HU_Storage_ID@ (@M_Product_ID@:" + productId + "; @M_HU_ID@:" + getM_HU().getM_HU_ID() + ")");
		}

		return productStorage;
	}

	@Override
	public IHUProductStorage getProductStorageOrNull(@NonNull final ProductId productId)
	{
		final I_M_HU_Storage storage = dao.retrieveStorage(hu, productId);
		if (storage == null)
		{
			return null;
		}
		final IHUProductStorage productStorage = createProductStorage(storage);
		return productStorage;
	}

	@Override
	public final Quantity getQtyForProductStorages(@NonNull final I_C_UOM uom)
	{
		return getProductStorages()
				.stream()
				.map(productStorage -> productStorage.getQty(uom))
				.reduce(Quantity.zero(uom), Quantity::add);
	}

	@Override
	public Quantity getQtyForProductStorages()
	{
		final List<IHUProductStorage> productStorages = getProductStorages();
		if (Check.isEmpty(productStorages))
		{
			final I_C_UOM uomWEach = Services.get(IUOMDAO.class).getEachUOM();

			return Quantity.zero(CoalesceUtil.coalesce(getC_UOMOrNull(), uomWEach));
		}
		return getQtyForProductStorages(productStorages.get(0).getC_UOM());
	}

	private final IHUProductStorage createProductStorage(final I_M_HU_Storage storage)
	{
		final ProductId productId = ProductId.ofRepoId(storage.getM_Product_ID());
		final I_C_UOM uom = IHUStorageBL.extractUOM(storage);
		final HUProductStorage productStorage = new HUProductStorage(this, productId, uom);
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

	@Override
	public boolean isSingleProductWithQtyEqualsTo(@NonNull final ProductId productId, @NonNull final Quantity qty)
	{
		final List<IHUProductStorage> productStorages = getProductStorages();
		return productStorages.size() == 1
				&& ProductId.equals(productStorages.get(0).getProductId(), productId)
				&& productStorages.get(0).getQty(qty.getUOM()).compareTo(qty) == 0;
	}

	@Override
	public boolean isSingleProductStorageMatching(@NonNull final ProductId productId)
	{
		final List<IHUProductStorage> productStorages = getProductStorages();
		return productStorages.size() == 1
				&& ProductId.equals(productStorages.get(0).getProductId(), productId);
	}
}
