/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.inoutcandidate.shippertransportation;

import com.google.common.collect.ImmutableSet;
import de.metas.shipper.gateway.commons.ShipperGatewayFacade;
import de.metas.shipper.gateway.spi.model.DeliveryOrderCreateRequest;
import de.metas.shipping.IShipperDAO;
import de.metas.shipping.ShipperId;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.common.util.CoalesceUtil;
import lombok.NonNull;
import org.compiere.model.I_M_Package;
import org.compiere.model.I_M_Shipper;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.load;

@Service
public class ShipperDeliveryService
{
	private final ShipperGatewayFacade shipperGatewayFacade;

	public ShipperDeliveryService(final ShipperGatewayFacade shipperGatewayFacade)
	{
		this.shipperGatewayFacade = shipperGatewayFacade;
	}

	/**
	 * Call the remote Shipper Gateway API and request that the Shipper comes to retrieve the packages.
	 * The Shipper delivery papers are created as a consequence.
	 * <p>
	 * All the packages in the list should not already have delivery papers.
	 * <p>
	 * If the Shipper does not have a ShipperGateway, this method does nothing (hence the "ifPossible" in its name).
	 */
	public void generateShipperDeliveryOrderIfPossible(
			@NonNull final ShipperId shipperId,
			@NonNull final ShipperTransportationId shipperTransportationId,
			@NonNull final Collection<I_M_Package> packages)
	{
		final I_M_Shipper shipper = Services.get(IShipperDAO.class).getById(shipperId);
		final String shipperGatewayId = shipper.getShipperGateway();
		// no ShipperGateway, so no API to call/no courier to request
		if (Check.isEmpty(shipperGatewayId, true))
		{
			return;
		}

		if (!shipperGatewayFacade.hasServiceSupport(shipperGatewayId))
		{
			return;
		}

		final Set<Integer> mPackageIds = packages.stream()
				.map(I_M_Package::getM_Package_ID)
				.collect(ImmutableSet.toImmutableSet());

		final I_M_ShipperTransportation shipperTransportation = load(shipperTransportationId, I_M_ShipperTransportation.class);

		final DeliveryOrderCreateRequest request = DeliveryOrderCreateRequest.builder()
				.pickupDate(getPickupDate(shipperTransportation))
				.timeFrom(TimeUtil.asLocalTime(shipperTransportation.getPickupTimeFrom()))
				.timeTo(TimeUtil.asLocalTime(shipperTransportation.getPickupTimeTo()))
				.packageIds(mPackageIds)
				.shipperTransportationId(shipperTransportationId)
				.shipperGatewayId(shipperGatewayId)
				.build();
		shipperGatewayFacade.createAndSendDeliveryOrdersForPackages(request);
	}

	private LocalDate getPickupDate(@NonNull final I_M_ShipperTransportation shipperTransportation)
	{
		return CoalesceUtil.coalesce(TimeUtil.asLocalDate(shipperTransportation.getDateToBeFetched()), TimeUtil.asLocalDate(shipperTransportation.getDateDoc()));
	}
}
