/*
 * #%L
 * de.metas.shipper.gateway.dhl
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.shipper.gateway.dhl.model.impl;

import de.metas.shipper.gateway.dhl.model.DhlCustomDeliveryDataDetail;
import de.metas.shipper.gateway.dhl.model.DhlSequenceNumber;
import de.metas.shipper.gateway.dhl.model.DhlShipmentOrderId;
import de.metas.shipper.gateway.dhl.model.IDhlShipmentOrderDAO;
import de.metas.shipper.gateway.dhl.model.I_DHL_ShipmentOrder;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;

import javax.annotation.Nullable;

public class DhlShipmentOrderDAO implements IDhlShipmentOrderDAO
{
	@Override
	@Nullable
	public DhlCustomDeliveryDataDetail getByIdOrNull(@NonNull final DhlShipmentOrderId id)
	{
		final I_DHL_ShipmentOrder shipmentOrder = InterfaceWrapperHelper.load(id, I_DHL_ShipmentOrder.class);
		if (shipmentOrder == null)
		{
			return null;
		}

		return DhlCustomDeliveryDataDetail.builder()
				.awb(shipmentOrder.getawb())
				.packageId(shipmentOrder.getPackageId())
				.trackingUrl(shipmentOrder.getTrackingURL())
				.pdfLabelData(shipmentOrder.getPdfLabelData())
				.internationalDelivery(shipmentOrder.isInternationalDelivery())
				.sequenceNumber(DhlSequenceNumber.of(shipmentOrder.getDHL_ShipmentOrder_ID()))
				.build();
	}
}
