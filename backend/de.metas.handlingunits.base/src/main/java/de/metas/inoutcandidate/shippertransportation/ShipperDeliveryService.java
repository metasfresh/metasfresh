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

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.async.AsyncBatchId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.common.util.CoalesceUtil;
import de.metas.handlingunits.impl.CreateShipperTransportationRequest;
import de.metas.handlingunits.impl.ShipperTransportationRepository;
import de.metas.handlingunits.transportation.InOutToTransportationOrderService;
import de.metas.inout.IInOutBL;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.logging.LogManager;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.shipper.gateway.commons.ShipperGatewayFacade;
import de.metas.shipper.gateway.spi.model.DeliveryOrderCreateRequest;
import de.metas.shipping.IShipperDAO;
import de.metas.shipping.ShipperId;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Package;
import org.compiere.model.I_M_Shipper;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.load;

@Service
public class ShipperDeliveryService
{
	private final static transient Logger logger = LogManager.getLogger(ShipperDeliveryService.class);

	private final IInOutBL inOutBL = Services.get(IInOutBL.class);
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);

	private final InOutToTransportationOrderService inOutToTransportationOrderService;
	private final ShipperGatewayFacade shipperGatewayFacade;
	private final ShipperTransportationRepository shipperTransportationRepository;

	public ShipperDeliveryService(
			@NonNull final ShipperGatewayFacade shipperGatewayFacade,
			@NonNull final ShipperTransportationRepository shipperTransportationRepository,
			@NonNull final InOutToTransportationOrderService inOutToTransportationOrderService)
	{
		this.shipperGatewayFacade = shipperGatewayFacade;
		this.shipperTransportationRepository = shipperTransportationRepository;
		this.inOutToTransportationOrderService = inOutToTransportationOrderService;
	}

	public void createTransportationAndPackagesForShipment(@NonNull final InOutId inOutId)
	{
		final I_M_InOut shipment = inOutDAO.getById(inOutId);

		final ShipperId shipperId = ShipperId.ofRepoIdOrNull(shipment.getM_Shipper_ID());

		if (shipperId == null)
		{
			Loggables.withLogger(logger, Level.INFO).addLog("Returning! No shipper present on shipment! m_inout_id: ", inOutId);
			return;
		}

		final WarehouseId warehouseId = WarehouseId.ofRepoId(shipment.getM_Warehouse_ID());
		final BPartnerLocationAndCaptureId shipFromBPWarehouseLocation = warehouseDAO.getWarehouseLocationById(warehouseId);

		final LocalDate shipDate = inOutBL.retrieveMovementDate(shipment);

		final CreateShipperTransportationRequest createShipperTransportationRequest = CreateShipperTransportationRequest
				.builder()
				.shipperId(shipperId)
				.shipperBPartnerAndLocationId(shipFromBPWarehouseLocation.getBpartnerLocationId())
				.orgId(OrgId.ofRepoId(shipment.getAD_Org_ID()))
				.shipDate(shipDate)
				.assignAnonymouslyPickedHUs(true) // a metasfresh user is supposed to find and ship exactly those HUs
				.build();

		final ShipperTransportationId shipperTransportationId = shipperTransportationRepository.create(createShipperTransportationRequest);

		final List<I_M_Package> addedPackages = inOutToTransportationOrderService.addShipmentsToTransportationOrder(shipperTransportationId, ImmutableList.of(inOutId));
		if (addedPackages.isEmpty())
		{
			Loggables.withLogger(logger, Level.INFO).addLog("Returning! No packages added for shipperTransportationId: ", shipperTransportationId);
			return;
		}

		final AsyncBatchId shipmentAsyncBatchId = AsyncBatchId.ofRepoIdOrNull(shipment.getC_Async_Batch_ID());

		generateShipperDeliveryOrderIfPossible(shipperId, shipperTransportationId, addedPackages, shipmentAsyncBatchId);
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
			@NonNull final Collection<I_M_Package> packages,
			@Nullable final AsyncBatchId asyncBatchId)
	{
		final I_M_Shipper shipper = Services.get(IShipperDAO.class).getById(shipperId);
		final String shipperGatewayId = shipper.getShipperGateway();
		// no ShipperGateway, so no API to call/no courier to request
		if (Check.isBlank(shipperGatewayId))
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
				.asyncBatchId(asyncBatchId)
				.build();
		shipperGatewayFacade.createAndSendDeliveryOrdersForPackages(request);
	}

	private LocalDate getPickupDate(@NonNull final I_M_ShipperTransportation shipperTransportation)
	{
		final ZoneId timeZone = orgDAO.getTimeZone(OrgId.ofRepoId(shipperTransportation.getAD_Org_ID()));
		return CoalesceUtil.coalesce(
				TimeUtil.asLocalDate(shipperTransportation.getDateToBeFetched(), timeZone), 
				TimeUtil.asLocalDate(shipperTransportation.getDateDoc(), timeZone));
	}
}
