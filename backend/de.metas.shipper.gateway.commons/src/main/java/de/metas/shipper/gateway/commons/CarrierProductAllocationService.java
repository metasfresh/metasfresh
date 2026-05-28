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

import de.metas.inoutcandidate.CarrierGoodsTypeId;
import de.metas.inoutcandidate.CarrierServiceId;
import de.metas.shipping.CarrierProductId;
import de.metas.shipper.gateway.commons.model.CarrierProductGoodsTypeAllocRepository;
import de.metas.shipper.gateway.commons.model.CarrierProductServiceAllocRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarrierProductAllocationService
{
	private final CarrierProductGoodsTypeAllocRepository goodsTypeAllocRepo;
	private final CarrierProductServiceAllocRepository serviceAllocRepo;

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
}
