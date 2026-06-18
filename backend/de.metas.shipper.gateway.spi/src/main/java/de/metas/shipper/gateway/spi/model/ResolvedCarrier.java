/*
 * #%L
 * de.metas.shipper.gateway.spi
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.shipper.gateway.spi.model;

import com.google.common.collect.ImmutableSet;
import de.metas.inoutcandidate.CarrierGoodsTypeId;
import de.metas.inoutcandidate.CarrierServiceId;
import de.metas.shipping.CarrierProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Set;

/**
 * The carrier (product + goods-type + services) resolved for a single shipment schedule, ready to be packed
 * into a {@code DeliveryOrderKey}.
 * <p>
 * This is a plain data carrier so the carrier can be RESOLVED in {@code de.metas.handlingunits.base} (which can
 * see the picking-job line) and passed into {@code de.metas.shipper.gateway.commons} on the
 * {@link DeliveryOrderCreateRequest} — the commons module must not depend on the handlingunits module
 * (that would create a dependency cycle).
 */
@Value
public class ResolvedCarrier
{
	@Nullable CarrierProductId carrierProductId;
	@Nullable CarrierGoodsTypeId carrierGoodsTypeId;
	@NonNull ImmutableSet<CarrierServiceId> carrierServices;

	@Builder
	private ResolvedCarrier(
			@Nullable final CarrierProductId carrierProductId,
			@Nullable final CarrierGoodsTypeId carrierGoodsTypeId,
			@Nullable final Set<CarrierServiceId> carrierServices)
	{
		this.carrierProductId = carrierProductId;
		this.carrierGoodsTypeId = carrierGoodsTypeId;
		this.carrierServices = carrierServices != null ? ImmutableSet.copyOf(carrierServices) : ImmutableSet.of();
	}
}
