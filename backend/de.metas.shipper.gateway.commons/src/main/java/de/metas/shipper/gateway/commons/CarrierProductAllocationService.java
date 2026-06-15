/*
 * #%L
 * de.metas.shipper.gateway.commons
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

package de.metas.shipper.gateway.commons;

import de.metas.common.delivery.v1.json.request.JsonCarrierService;
import de.metas.common.delivery.v1.json.request.JsonGoodsType;
import de.metas.common.delivery.v1.json.request.JsonShipperProduct;
import de.metas.inoutcandidate.CarrierGoodsType;
import de.metas.inoutcandidate.CarrierGoodsTypeId;
import de.metas.inoutcandidate.CarrierService;
import de.metas.inoutcandidate.CarrierServiceId;
import de.metas.shipping.CarrierProductId;
import de.metas.shipper.gateway.commons.model.CarrierGoodsTypeRepository;
import de.metas.shipper.gateway.commons.model.CarrierProduct;
import de.metas.shipper.gateway.commons.model.CarrierProductGoodsTypeAllocRepository;
import de.metas.shipper.gateway.commons.model.CarrierProductRepository;
import de.metas.shipper.gateway.commons.model.CarrierProductServiceAllocRepository;
import de.metas.shipper.gateway.commons.model.CarrierShipmentOrderServiceRepository;
import de.metas.shipping.ShipperId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CarrierProductAllocationService
{
	private final CarrierProductGoodsTypeAllocRepository goodsTypeAllocRepo;
	private final CarrierProductServiceAllocRepository serviceAllocRepo;
	private final CarrierProductRepository carrierProductRepository;
	private final CarrierGoodsTypeRepository carrierGoodsTypeRepository;
	private final CarrierShipmentOrderServiceRepository carrierServiceRepository;

	public void addGoodsTypeIfMissing(@NonNull final CarrierProductId carrierProductId, @NonNull final CarrierGoodsTypeId goodsTypeId)
	{
		if (!goodsTypeAllocRepo.exists(carrierProductId, goodsTypeId))
		{
			goodsTypeAllocRepo.save(carrierProductId, goodsTypeId);
		}
	}

	public void addServiceIfMissing(@NonNull final CarrierProductId carrierProductId, @NonNull final CarrierServiceId serviceId)
	{
		if (!serviceAllocRepo.exists(carrierProductId, serviceId))
		{
			serviceAllocRepo.save(carrierProductId, serviceId);
		}
	}

	/**
	 * Persists the carrier that was actually resolved at ship time into the carrier-product allocation tables
	 * (get-or-create product / goods types / services, then add the allocations only if missing) so what was
	 * shipped becomes selectable in manual advise. No-op when no product was resolved.
	 */
	public void persistResolvedAllocations(
			@NonNull final ShipperId shipperId,
			@Nullable final JsonShipperProduct product,
			@NonNull final Set<JsonGoodsType> goodsTypes,
			@NonNull final Set<JsonCarrierService> services)
	{
		if (product == null)
		{
			return;
		}

		final String productName = product.getName() != null ? product.getName() : product.getCode();
		final CarrierProduct carrierProduct = carrierProductRepository.getOrCreateCarrierProduct(shipperId, product.getCode(), productName);
		final CarrierProductId carrierProductId = carrierProduct.getId();

		for (final JsonGoodsType goodsType : goodsTypes)
		{
			final CarrierGoodsType carrierGoodsType = carrierGoodsTypeRepository.getOrCreateGoodsType(shipperId, goodsType.getId(), goodsType.getName());
			addGoodsTypeIfMissing(carrierProductId, carrierGoodsType.getId());
		}

		for (final JsonCarrierService service : services)
		{
			final CarrierService carrierService = carrierServiceRepository.getOrCreateService(shipperId, service.getId(), service.getName());
			addServiceIfMissing(carrierProductId, carrierService.getId());
		}
	}
}
