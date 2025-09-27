/*
 * #%L
 * de.metas.shipper.gateway.carrier
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

package de.metas.shipper.gateway.carrier.service;

import de.metas.shipper.gateway.carrier.CarrierConstants;
import de.metas.shipper.gateway.carrier.model.ShipmentOrderParcel;
import de.metas.shipper.gateway.spi.ShipperGatewayClient;
import de.metas.shipper.gateway.spi.exceptions.ShipperGatewayException;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.OrderId;
import de.metas.shipper.gateway.spi.model.PackageLabel;
import de.metas.shipper.gateway.spi.model.PackageLabelType;
import de.metas.shipper.gateway.spi.model.PackageLabels;
import lombok.NonNull;

import java.util.List;
import java.util.stream.Collectors;

public class CarrierGatewayClient implements ShipperGatewayClient
{
	//TODO implement as provided by carrier
	private final static PackageLabelType DEFAULT_LABEL_TYPE = new PackageLabelType() {};

	@Override
	public @NonNull String getShipperGatewayId()
	{
		return CarrierConstants.SHIPPER_GATEWAY_ID;
	}

	@Override
	public @NonNull DeliveryOrder completeDeliveryOrder(@NonNull final DeliveryOrder deliveryOrder) throws ShipperGatewayException
	{
		//TODO invoke carrier, then call de.metas.shipper.gateway.carrier.model.ShipmentOrderRepository.save to update awb/labelPdf in DB
		return null;
	}

	@NonNull
	@Override
	public List<PackageLabels> getPackageLabelsList(@NonNull final DeliveryOrder deliveryOrder) throws ShipperGatewayException
	{
		final String orderIdAsString = String.valueOf(deliveryOrder.getId());
		return deliveryOrder.getDeliveryOrderLines()
				.stream()
				.map(ShipmentOrderParcel::ofDeliveryOrderLineOrNull)
				.map(parcel -> createPackageLabel(parcel.getLabelPdfBase64(), parcel.getAwb(), orderIdAsString))
				.collect(Collectors.toList());
	}

	@NonNull
	private static PackageLabels createPackageLabel(final byte[] labelData, @NonNull final String awb, @NonNull final String deliveryOrderIdAsString)
	{
		return PackageLabels.builder()
				.orderId(OrderId.of(CarrierConstants.SHIPPER_GATEWAY_ID, deliveryOrderIdAsString))
				.defaultLabelType(DEFAULT_LABEL_TYPE)
				.label(PackageLabel.builder()
						.type(DEFAULT_LABEL_TYPE)
						.labelData(labelData)
						.contentType(PackageLabel.CONTENTTYPE_PDF)
						.fileName(awb)
						.build())
				.build();
	}
}
