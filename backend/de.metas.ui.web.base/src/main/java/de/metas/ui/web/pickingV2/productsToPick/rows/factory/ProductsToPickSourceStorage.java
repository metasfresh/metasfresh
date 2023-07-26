package de.metas.ui.web.pickingV2.productsToPick.rows.factory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.compiere.model.I_C_UOM;

import com.google.common.collect.Maps;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

final class ProductsToPickSourceStorage
{
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);

	private final Map<HuId, I_M_HU> husCache = new HashMap<>();
	private final Map<ReservableStorageKey, ReservableStorage> storages = new HashMap<>();

	public I_M_HU getHU(final HuId huId)
	{
		return husCache.computeIfAbsent(huId, handlingUnitsBL::getById);
	}

	public void warmUpCacheForHuIds(final Collection<HuId> huIds)
	{
		CollectionUtils.getAllOrLoad(husCache, huIds, this::retrieveHUs);
	}

	private Map<HuId, I_M_HU> retrieveHUs(final Collection<HuId> huIds)
	{
		return Maps.uniqueIndex(handlingUnitsBL.getByIds(huIds), hu -> HuId.ofRepoId(hu.getM_HU_ID()));
	}

	public ReservableStorage getStorage(final HuId huId, final ProductId productId)
	{
		final ReservableStorageKey key = ReservableStorageKey.of(huId, productId);
		return storages.computeIfAbsent(key, this::retrieveStorage);
	}

	private ReservableStorage retrieveStorage(final ReservableStorageKey key)
	{
		final ProductId productId = key.getProductId();
		final I_M_HU hu = getHU(key.getHuId());

		final IHUProductStorage huProductStorage = handlingUnitsBL
				.getStorageFactory()
				.getStorage(hu)
				.getProductStorageOrNull(productId);

		if (huProductStorage == null)
		{
			final I_C_UOM uom = productBL.getStockUOM(productId);
			return new ReservableStorage(productId, Quantity.zero(uom));
		}
		else
		{
			final Quantity qtyFreeToReserve = huProductStorage.getQty();
			return new ReservableStorage(productId, qtyFreeToReserve);
		}
	}

	@Value(staticConstructor = "of")
	private static class ReservableStorageKey
	{
		@NonNull
		HuId huId;
		@NonNull
		ProductId productId;
	}
}
