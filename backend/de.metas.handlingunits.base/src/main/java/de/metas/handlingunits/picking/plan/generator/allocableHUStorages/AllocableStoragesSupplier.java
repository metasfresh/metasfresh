/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.handlingunits.picking.plan.generator.allocableHUStorages;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.plan.generator.pickFromHUs.HUsLoadingCache;
import de.metas.handlingunits.reservation.HUReservationDocRef;
import de.metas.handlingunits.reservation.HUReservationEntry;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Value;
import org.compiere.model.I_C_UOM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class AllocableStoragesSupplier
{
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final HUReservationService huReservationService;

	private final HUsLoadingCache husCache;
	private final Map<ReservableStorageKey, AllocableStorage> storages = new HashMap<>();

	public AllocableStoragesSupplier(
			@NonNull final HUReservationService huReservationService,
			@NonNull final HUsLoadingCache husCache)
	{
		this.huReservationService = huReservationService;
		this.husCache = husCache;
	}

	public AllocableStorage getStorage(final HuId huId, final ProductId productId)
	{
		final ReservableStorageKey key = ReservableStorageKey.of(huId, productId);
		return storages.computeIfAbsent(key, this::retrieveStorage);
	}

	private AllocableStorage retrieveStorage(final ReservableStorageKey key)
	{
		final ProductId productId = key.getProductId();

		final HuId topLeveHUId = key.getHuId();
		final ImmutableSet<HuId> vhuIds = husCache.getVHUIds(topLeveHUId);

		final ImmutableMap<HuId, HUReservationEntry> reservationsByVHUId = Maps.uniqueIndex(huReservationService.getEntriesByVHUIds(vhuIds), HUReservationEntry::getVhuId);

		final ArrayList<VHUAllocableStorage> vhuAllocableStorages = new ArrayList<>();

		for (final HuId vhuId : husCache.getVHUIds(topLeveHUId))
		{
			final I_M_HU vhu = husCache.getHUById(vhuId);
			final IHUProductStorage huProductStorage = handlingUnitsBL
					.getStorageFactory()
					.getStorage(vhu)
					.getProductStorageOrNull(productId);

			if (huProductStorage != null)
			{
				final int seqNo = vhuId.getRepoId();
				final Quantity qtyFreeToAllocate = huProductStorage.getQty();
				final HUReservationEntry reservation = reservationsByVHUId.get(vhuId);
				final HUReservationDocRef reservationDocRef = reservation != null ? reservation.getDocumentRef() : null;
				final VHUAllocableStorage vhuAllocableStorage = new VHUAllocableStorage(seqNo, productId, qtyFreeToAllocate, reservationDocRef);
				vhuAllocableStorages.add(vhuAllocableStorage);
			}
		}

		if (vhuAllocableStorages.isEmpty())
		{
			final I_C_UOM stockingUOM = productBL.getStockUOM(productId);
			final VHUAllocableStorage vhuAllocableStorage = new VHUAllocableStorage(0, productId, Quantity.zero(stockingUOM), null);
			vhuAllocableStorages.add(vhuAllocableStorage);
		}

		return new AllocableStorage(vhuAllocableStorages);
	}

	@Value(staticConstructor = "of")
	private static class ReservableStorageKey
	{
		@NonNull HuId huId;
		@NonNull ProductId productId;
	}
}
