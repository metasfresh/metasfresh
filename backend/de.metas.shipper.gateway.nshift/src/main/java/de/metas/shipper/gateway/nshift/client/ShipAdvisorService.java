/*
 * #%L
 * de.metas.shipper.gateway.nshift
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

package de.metas.shipper.gateway.nshift.client;

import com.google.common.collect.ImmutableList;
import de.metas.common.delivery.v1.json.request.JsonCarrierAdvice;
import de.metas.common.delivery.v1.json.request.JsonDeliveryAdvisorRequest;
import de.metas.common.delivery.v1.json.request.JsonDeliveryAdvisorRequestItem;
import de.metas.common.delivery.v1.json.request.JsonDeliveryOrderParcel;
import de.metas.common.delivery.v1.json.request.JsonDeliveryRequest;
import de.metas.common.delivery.v1.json.response.JsonDeliveryAdvisorResponse;
import de.metas.shipper.client.nshift.NShiftConstants;
import de.metas.shipper.client.nshift.NShiftShipAdvisorService;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ShipAdvisorService
{
	private final NShiftShipAdvisorService shipAdvisorService;

	public JsonDeliveryRequest advise(@NonNull final JsonDeliveryRequest deliveryRequest)
	{
		// For now, I ignore a set Shipper Product

		final ImmutableList<JsonDeliveryOrderParcel> deliveryOrderParcels = deliveryRequest.getDeliveryOrderParcels();
		Check.assumeNotEmpty(deliveryOrderParcels, "deliveryOrderParcels is not empty");
		final JsonDeliveryAdvisorResponse response = shipAdvisorService.getShipAdvises(JsonDeliveryAdvisorRequest.builder()
						.pickupAddress(deliveryRequest.getPickupAddress())
						.pickupDate(deliveryRequest.getPickupDate())
						.pickupNote(deliveryRequest.getPickupNote())
						.deliveryAddress(deliveryRequest.getDeliveryAddress())
						.deliveryContact(deliveryRequest.getDeliveryContact())
						.deliveryDate(deliveryRequest.getDeliveryDate())
						.deliveryNote(deliveryRequest.getDeliveryNote())
						.shipperConfig(deliveryRequest.getShipperConfig().toBuilder()
								.additionalProperty(NShiftConstants.SERVICE_LEVEL, "Test") //FIXME hardcoded NShift.ShippingRule.ServiceLevel should be come from config repo, so the config isn't mutated
								.build())
						.item(JsonDeliveryAdvisorRequestItem.builder() // When moved to shipment schedule we only have "1 line", so I only use one here as well
								.grossWeightKg(computeTotalGrossWeightKg(deliveryOrderParcels))
								.packageDimensions(deliveryOrderParcels.get(0).getPackageDimensions())
								.numberOfItems(deliveryOrderParcels.size())
										.build())
								.build()
		);

		if(response.isError())
		{
			//noinspection DataFlowIssue
			throw new AdempiereException(response.getErrorMessage());
		}

		return deliveryRequest.toBuilder()
				.shipperProduct(response.getShipperProduct())
				.carrierAdvice(JsonCarrierAdvice.builder()
						.goodsType(response.getGoodsType())
						.services(response.getShipperProductServices())
						.build())
				.carrierAdvice(JsonCarrierAdvice.builder()
						.services(response.getShipperProductServices())
						.goodsType(response.getGoodsType())
						.build())
				.build();
	}
	
	private static BigDecimal computeTotalGrossWeightKg(@NonNull final ImmutableList<JsonDeliveryOrderParcel> parcels)
	{
		return parcels.stream()
				.map(JsonDeliveryOrderParcel::getGrossWeightKg)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}
