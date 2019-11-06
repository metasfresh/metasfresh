/*
 * #%L
 * de.metas.shipper.gateway.dpd
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

package de.metas.shipper.gateway.dpd;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.mpackage.PackageId;
import de.metas.organization.OrgId;
import de.metas.shipper.gateway.commons.DeliveryOrderUtil;
import de.metas.shipper.gateway.dpd.model.DpdNotificationChannel;
import de.metas.shipper.gateway.dpd.model.DpdOrderCustomDeliveryData;
import de.metas.shipper.gateway.dpd.model.DpdOrderType;
import de.metas.shipper.gateway.dpd.model.DpdPaperFormat;
import de.metas.shipper.gateway.dpd.model.DpdServiceType;
import de.metas.shipper.gateway.spi.DraftDeliveryOrderCreator;
import de.metas.shipper.gateway.spi.model.ContactPerson;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryOrderLine;
import de.metas.shipper.gateway.spi.model.PackageDimensions;
import de.metas.shipper.gateway.spi.model.PickupDate;
import de.metas.shipper.gateway.spi.model.ServiceType;
import de.metas.shipping.ShipperId;
import de.metas.shipping.api.ShipperTransportationId;
import de.metas.util.Services;
import de.metas.util.lang.CoalesceUtil;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_M_Package;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Service
public class DpdDraftDeliveryOrderCreator implements DraftDeliveryOrderCreator
{
	// private static final Logger logger = LoggerFactory.getLogger(DpdDraftDeliveryOrderCreator.class);

	@Override
	public String getShipperGatewayId()
	{
		return DpdConstants.SHIPPER_GATEWAY_ID;
	}

	/**
	 * Create the initial DTO.
	 * <p>
	 * todo: keep in sync with:
	 */
	@NonNull
	@Override
	public DeliveryOrder createDraftDeliveryOrder(@NonNull final CreateDraftDeliveryOrderRequest request)
	{
		final DeliveryOrderKey deliveryOrderKey = request.getDeliveryOrderKey();
		final Set<PackageId> mpackageIds = request.getMpackageIds();

		final String customerReference = ""; // todo what is the customer reference ?

		final IBPartnerOrgBL bpartnerOrgBL = Services.get(IBPartnerOrgBL.class);
		final I_C_BPartner pickupFromBPartner = bpartnerOrgBL.retrieveLinkedBPartner(deliveryOrderKey.getFromOrgId());
		final I_C_Location pickupFromLocation = bpartnerOrgBL.retrieveOrgLocation(OrgId.ofRepoId(deliveryOrderKey.getFromOrgId()));
		final LocalDate pickupDate = deliveryOrderKey.getPickupDate();
		final LocalTime timeFrom = deliveryOrderKey.getTimeFrom();
		final LocalTime timeTo = deliveryOrderKey.getTimeTo();

		final int deliverToBPartnerId = deliveryOrderKey.getDeliverToBPartnerId();
		final I_C_BPartner deliverToBPartner = InterfaceWrapperHelper.load(deliverToBPartnerId, I_C_BPartner.class);

		final int deliverToBPartnerLocationId = deliveryOrderKey.getDeliverToBPartnerLocationId();
		final I_C_BPartner_Location deliverToBPLocation = InterfaceWrapperHelper.load(deliverToBPartnerLocationId, I_C_BPartner_Location.class);
		final I_C_Location deliverToLocation = deliverToBPLocation.getC_Location();
		final String deliverToPhoneNumber = CoalesceUtil.firstNotEmptyTrimmed(deliverToBPLocation.getPhone(), deliverToBPLocation.getPhone2(), deliverToBPartner.getPhone2());

		final int allPackagesGrossWeightInKg = Math.max(request.getAllPackagesGrossWeightInKg(), 1);
		final ShipperId shipperId = deliveryOrderKey.getShipperId();
		final ShipperTransportationId shipperTransportationId = deliveryOrderKey.getShipperTransportationId();

		final DpdServiceType serviceType = DpdServiceType.DPD_CLASSIC;

		final DpdOrderCustomDeliveryData customDeliveryData = DpdOrderCustomDeliveryData.builder()
				.orderType(DpdOrderType.CONSIGNMENT)
				// .sendingDepot()// this is null and only set in the client, after login is done
				.paperFormat(DpdPaperFormat.PAPER_FORMAT_A6) // todo should be read from shipper config
				.printerLanguage(DpdConstants.DEFAULT_PRINTER_LANGUAGE)
				.notificationChannel(DpdNotificationChannel.EMAIL)
				.build();

		// Order lines
		final ImmutableList.Builder<DeliveryOrderLine> deliveryOrderLinesBuilder = ImmutableList.builder();
		for (final PackageId packageId : mpackageIds)
		{
			final I_M_Package mPackage = InterfaceWrapperHelper.load(packageId, I_M_Package.class);

			final DeliveryOrderLine deliveryOrderLine = DeliveryOrderLine.builder()
					// .repoId()
					.content(mPackage.getDescription())
					.grossWeightKg(mPackage.getPackageWeight().intValue()) // todo same as in de.metas.shipper.gateway.commons.ShipperGatewayFacade.computeGrossWeightInKg: we assume it's in Kg
					.packageDimensions(getPackageDimensions(packageId, shipperId))
					// .customDeliveryData()
					.packageId(packageId)
					.build();

			deliveryOrderLinesBuilder.add(deliveryOrderLine);
		}

		return createDeliveryOrderFromParams(
				pickupFromBPartner,
				pickupFromLocation,
				pickupDate,
				timeFrom,
				timeTo,
				deliverToBPartner,
				deliverToBPartnerLocationId,
				deliverToLocation,
				deliverToPhoneNumber,
				serviceType,
				allPackagesGrossWeightInKg,
				shipperId,
				shipperTransportationId,
				customerReference,
				customDeliveryData,
				deliveryOrderLinesBuilder.build());
	}

	@VisibleForTesting
	DeliveryOrder createDeliveryOrderFromParams(
			@NonNull final I_C_BPartner pickupFromBPartner,
			@NonNull final I_C_Location pickupFromLocation,
			@NonNull final LocalDate pickupDate,
			@NonNull final LocalTime timeFrom,
			@NonNull final LocalTime timeTo,
			@NonNull final I_C_BPartner deliverToBPartner,
			final int deliverToBPartnerLocationId,
			@NonNull final I_C_Location deliverToLocation,
			@Nullable final String deliverToPhoneNumber,
			@NonNull final ServiceType serviceType,
			final int allPackagesGrossWeightKg,
			final ShipperId shipperId,
			final ShipperTransportationId shipperTransportationId, final String customerReference,
			@NonNull final DpdOrderCustomDeliveryData customDeliveryData,
			@NonNull final List<DeliveryOrderLine> deliveryOrderLines)
	{

		return DeliveryOrder.builder()
				.shipperId(shipperId)
				.shipperTransportationId(shipperTransportationId)
				//
				//
				.serviceType(serviceType)
				.customerReference(customerReference)
				.customDeliveryData(customDeliveryData)

				//
				// Pickup aka Sender
				.pickupAddress(DeliveryOrderUtil.prepareAddressFromLocation(pickupFromLocation)
						.companyName1(pickupFromBPartner.getName())
						.companyName2(pickupFromBPartner.getName2())
						.build())
				.pickupDate(PickupDate.builder()
						.date(pickupDate)
						.timeFrom(timeFrom)
						.timeTo(timeTo)
						.build())
				//
				// Delivery aka Recipient
				.deliveryAddress(DeliveryOrderUtil.prepareAddressFromLocation(deliverToLocation)
						.companyName1(deliverToBPartner.getName())
						.companyName2(deliverToBPartner.getName2())
						.bpartnerId(deliverToBPartner.getC_BPartner_ID()) // afaics used only for logging
						.bpartnerLocationId(deliverToBPartnerLocationId) // afaics used only for logging
						.build())
				.deliveryContact(ContactPerson.builder()
						.emailAddress(deliverToBPartner.getEMail())
						.simplePhoneNumber(deliverToPhoneNumber)
						.build())
				//
				// Delivery content
				.allPackagesGrossWeightInKg(allPackagesGrossWeightKg)
				.deliveryOrderLines(deliveryOrderLines)
				.build();
	}

	@NonNull
	private PackageDimensions getPackageDimensions(@NonNull final PackageId packageId, final ShipperId shipperId)
	{
		//		final DpdClientConfig clientConfig = clientConfigRepository.getByShipperId(ShipperId.ofRepoId(shipperId));
		//		return getPackageDimensions(firstPackageId, clientConfig.getLengthUomId());

		// todo don't hardcode
		// todo ask teo/tobi where to refactor the method which gets the uom from a packageId (copied both here and dhl)
		return PackageDimensions.builder()
				.lengthInCM(10)
				.widthInCM(20)
				.heightInCM(30)
				.build();
	}

	//	/**
	//	 * sql:
	//	 *
	//	 * <pre>{@code
	//	 * SELECT pack.width
	//	 * FROM m_package_hu phu
	//	 * 		INNER JOIN m_hu_item huitem ON phu.m_hu_id = huitem.m_hu_id
	//	 * 		INNER JOIN m_hu_packingmaterial pack ON huitem.m_hu_packingmaterial_id = pack.m_hu_packingmaterial_id
	//	 * WHERE phu.m_package_id = 1000023
	//	 * }</pre>
	//	 * <p>
	//	 * thx to ruxi for transforming this query into "metasfresh"
	//	 */
	//	@NonNull
	//	private PackageDimensions getPackageDimensions(final int packageId, @NonNull final UomId toUomId)
	//	{
	//		// assuming packing material is never null
	//		final I_M_HU_PackingMaterial packingMaterial = Services.get(IQueryBL.class)
	//				.createQueryBuilder(I_M_Package_HU.class)
	//				.addEqualsFilter(I_M_Package_HU.COLUMNNAME_M_Package_ID, packageId)
	//				//
	//
	//				.andCollect(I_M_HU.COLUMN_M_HU_ID, I_M_HU.class)
	//				.andCollectChildren(I_M_HU_Item.COLUMN_M_HU_ID)
	//				.andCollect(I_M_HU_PackingMaterial.COLUMN_M_HU_PackingMaterial_ID, I_M_HU_PackingMaterial.class)
	//				.create()
	//				.firstOnly(I_M_HU_PackingMaterial.class);
	//
	//		final UomId uomId = UomId.ofRepoIdOrNull(packingMaterial.getC_UOM_Dimension_ID());
	//
	//		if (uomId == null)
	//		{
	//			throw new AdempiereException("Package UOM must be set");
	//		}
	//
	//		final I_C_UOM fromUom = InterfaceWrapperHelper.load(uomId, I_C_UOM.class);
	//		final I_C_UOM toUom = InterfaceWrapperHelper.load(toUomId, I_C_UOM.class);
	//
	//		final IUOMConversionBL iuomConversionBL = Services.get(IUOMConversionBL.class);
	//		return PackageDimensions.builder()
	//				.heightInCM(iuomConversionBL.convert(fromUom, toUom, packingMaterial.getHeight()).get().intValue())
	//				.lengthInCM(iuomConversionBL.convert(fromUom, toUom, packingMaterial.getLength()).get().intValue())
	//				.widthInCM(iuomConversionBL.convert(fromUom, toUom, packingMaterial.getWidth()).get().intValue())
	//				.build();
	//	}
}
