/*
 * #%L
 * de.metas.shipper.gateway.dhl
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

package de.metas.shipper.gateway.dhl;

import com.google.common.annotations.VisibleForTesting;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.customs.CustomsInvoiceRepository;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.handlingunits.model.I_M_Package_HU;
import de.metas.mpackage.PackageId;
import de.metas.organization.OrgId;
import de.metas.shipper.gateway.commons.DeliveryOrderUtil;
import de.metas.shipper.gateway.dhl.model.DhlClientConfig;
import de.metas.shipper.gateway.dhl.model.DhlClientConfigRepository;
import de.metas.shipper.gateway.dhl.model.DhlCustomDeliveryData;
import de.metas.shipper.gateway.dhl.model.DhlCustomDeliveryDataDetail;
import de.metas.shipper.gateway.dhl.model.DhlServiceType;
import de.metas.shipper.gateway.spi.DraftDeliveryOrderCreator;
import de.metas.shipper.gateway.spi.model.ContactPerson;
import de.metas.shipper.gateway.spi.model.CustomDeliveryData;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryPosition;
import de.metas.shipper.gateway.spi.model.PackageDimensions;
import de.metas.shipper.gateway.spi.model.PickupDate;
import de.metas.shipping.ShipperId;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.util.lang.CoalesceUtil;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_UOM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Set;

@Service
public class DhlDraftDeliveryOrderCreator implements DraftDeliveryOrderCreator
{
	private static final Logger logger = LoggerFactory.getLogger(DhlDraftDeliveryOrderCreator.class);

	private final DhlClientConfigRepository clientConfigRepository;
	private final CustomsInvoiceRepository customsInvoiceRepository;

	public DhlDraftDeliveryOrderCreator(@NonNull final DhlClientConfigRepository clientConfigRepository, @NonNull final CustomsInvoiceRepository customsInvoiceRepository)
	{
		this.clientConfigRepository = clientConfigRepository;
		this.customsInvoiceRepository = customsInvoiceRepository;
	}

	@Override
	public String getShipperGatewayId()
	{
		return DhlConstants.SHIPPER_GATEWAY_ID;
	}

	/**
	 * Create the initial DTO.
	 * <p>
	 * keep in sync with {@link DhlDeliveryOrderRepository#toDeliveryOrderFromPO(de.metas.shipper.gateway.dhl.model.I_DHL_ShipmentOrderRequest)}
	 * and {@link DhlDeliveryOrderRepository#createShipmentOrderRequest(DeliveryOrder)}
	 */
	@SuppressWarnings("JavadocReference")
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

		final int deliverToBPartnerId = deliveryOrderKey.getDeliverToBPartnerId();
		final I_C_BPartner deliverToBPartner = InterfaceWrapperHelper.load(deliverToBPartnerId, I_C_BPartner.class);

		final int deliverToBPartnerLocationId = deliveryOrderKey.getDeliverToBPartnerLocationId();
		final I_C_BPartner_Location deliverToBPLocation = InterfaceWrapperHelper.load(deliverToBPartnerLocationId, I_C_BPartner_Location.class);
		final I_C_Location deliverToLocation = deliverToBPLocation.getC_Location();
		final String deliverToPhoneNumber = CoalesceUtil.firstNotEmptyTrimmed(deliverToBPLocation.getPhone(), deliverToBPLocation.getPhone2(), deliverToBPartner.getPhone2());

		final int grossWeightInKg = Math.max(request.getAllPackagesGrossWeightInKg(), 1);
		final int shipperId = deliveryOrderKey.getShipperId();
		final int shipperTransportationId = deliveryOrderKey.getShipperTransportationId();

		DhlServiceType detectedServiceType = DhlServiceType.Dhl_Paket;
		final DhlCustomDeliveryData.DhlCustomDeliveryDataBuilder dataBuilder = DhlCustomDeliveryData.builder();

		// create the customDeliveryDataDetails
		for (final PackageId packageId : mpackageIds)
		{
			final DhlCustomDeliveryDataDetail.DhlCustomDeliveryDataDetailBuilder dataDetailBuilder = DhlCustomDeliveryDataDetail.builder();
			dataDetailBuilder.packageId(packageId.getRepoId());

			// implement handling for DE -> DE and DE -> International packages
			// currently we only support inside-EU international shipping. For everything else dhl api will error out until the DhlCustomsDocument is properly filled!
			if (deliverToLocation.getC_Country_ID() != pickupFromLocation.getC_Country_ID())
			{
				detectedServiceType = DhlServiceType.Dhl_PaketInternational;

				// "{ }" for easier method extraction later on
				//				{
				//					final I_M_Package mPackage = InterfaceWrapperHelper.load(packageId, I_M_Package.class);
				//					final I_M_InOut inOut = InterfaceWrapperHelper.load(mPackage.getM_InOut_ID(), I_M_InOut.class);
				//					if (!inOut.isExportedToCustomsInvoice())
				//					{
				//						throw new AdempiereException("International Delivery Order must have a Customs Invoice!");
				//					}
				//
				//					final List<CustomsInvoiceLine> customsInvoiceLines = customsInvoiceRepository.retrieveLines(CustomsInvoiceId.ofRepoId(inOut.getC_Customs_Invoice_ID()));
				//
				//					detectedServiceType = DhlServiceType.Dhl_PaketInternational;
				//					dataDetailBuilder.customsDocument(
				//							DhlCustomsDocument.builder()
				//									.exportType("OTHER")
				//									//							.exportTypeDescription()
				//									//							.additionalFee()
				//									//							.electronicExportNotification()
				//									//							.packageDescription()
				//									//							.customsTariffNumber()
				//									//							.customsAmount()
				//									//							.netWeightInKg()
				//									//							.customsValue()
				//									//							.invoiceId()
				//									//							.invoiceLineId()
				//									.build())
				//							.internationalDelivery(true)
				//							.build();
				//				}
			}
			else
			{
				dataDetailBuilder.internationalDelivery(false);
			}
			dataBuilder.detail(dataDetailBuilder.build());
		}

		return createDeliveryOrderFromParams(
				mpackageIds,
				pickupFromBPartner,
				pickupFromLocation,
				pickupDate,
				deliverToBPartner,
				deliverToBPartnerLocationId,
				deliverToLocation,
				deliverToPhoneNumber,
				detectedServiceType,
				grossWeightInKg,
				shipperId,
				customerReference,
				shipperTransportationId,
				getPackageDimensions(mpackageIds, shipperId),
				dataBuilder.build());

	}

	@VisibleForTesting
	DeliveryOrder createDeliveryOrderFromParams(
			@NonNull final Set<PackageId> mpackageIds,
			@NonNull final I_C_BPartner pickupFromBPartner,
			@NonNull final I_C_Location pickupFromLocation,
			@NonNull final LocalDate pickupDate,
			@NonNull final I_C_BPartner deliverToBPartner,
			final int deliverToBPartnerLocationId,
			@NonNull final I_C_Location deliverToLocation,
			@Nullable final String deliverToPhoneNumber,
			@NonNull final DhlServiceType serviceType,
			final int grossWeightKg,
			final int shipperId,
			final String customerReference, final int shipperTransportationId,
			@NonNull final PackageDimensions packageDimensions,
			final CustomDeliveryData customDeliveryData)
	{
		return DeliveryOrder.builder()
				.shipperId(shipperId)
				.shipperTransportationId(shipperTransportationId)
				//

				.serviceType(serviceType) // todo this should be made user-selectable. Ref: https://github.com/metasfresh/me03/issues/3128
				.customerReference(customerReference)
				.customDeliveryData(customDeliveryData)
				//
				// Pickup aka Shipper
				.pickupAddress(DeliveryOrderUtil.prepareAddressFromLocation(pickupFromLocation)
						.companyName1(pickupFromBPartner.getName())
						.companyName2(pickupFromBPartner.getName2())
						.build())
				.pickupDate(PickupDate.builder()
						.date(pickupDate)
						.build())
				//
				// Delivery aka Receiver
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
				.deliveryPosition(DeliveryPosition.builder()
						.numberOfPackages(mpackageIds.size())
						.packageIds(mpackageIds)
						.grossWeightKg(grossWeightKg)
						.packageDimensions(packageDimensions)
						.build())
				//
				.build();
	}

	/**
	 * Assume that all the packages inside a delivery position are of the same type and therefore have the same size.
	 */
	@NonNull
	private PackageDimensions getPackageDimensions(@NonNull final Set<PackageId> mpackageIds, final int shipperId)
	{
		final PackageId firstPackageId = mpackageIds.iterator().next();
		final DhlClientConfig clientConfig = clientConfigRepository.getByShipperId(ShipperId.ofRepoId(shipperId));
		return getPackageDimensions(firstPackageId, clientConfig.getLengthUomId());
	}

	/**
	 * sql:
	 *
	 * <pre>{@code
	 * SELECT pack.width
	 * FROM m_package_hu phu
	 * 		INNER JOIN m_hu_item huitem ON phu.m_hu_id = huitem.m_hu_id
	 * 		INNER JOIN m_hu_packingmaterial pack ON huitem.m_hu_packingmaterial_id = pack.m_hu_packingmaterial_id
	 * WHERE phu.m_package_id = 1000023
	 * }</pre>
	 * <p>
	 * thx to ruxi for transforming this query into "metasfresh"
	 */
	@NonNull
	private PackageDimensions getPackageDimensions(final PackageId packageId, @NonNull final UomId toUomId)
	{
		// assuming packing material is never null
		final I_M_HU_PackingMaterial packingMaterial = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Package_HU.class)
				.addEqualsFilter(I_M_Package_HU.COLUMNNAME_M_Package_ID, packageId)
				//

				.andCollect(I_M_HU.COLUMN_M_HU_ID, I_M_HU.class)
				.andCollectChildren(I_M_HU_Item.COLUMN_M_HU_ID)
				.andCollect(I_M_HU_PackingMaterial.COLUMN_M_HU_PackingMaterial_ID, I_M_HU_PackingMaterial.class)
				.create()
				.first(I_M_HU_PackingMaterial.class);

		// if there's no packing material, don't die with NPE, but return a dummy package dimensions and call it a day
		if (packingMaterial == null)
		{
			return PackageDimensions.builder()
					.heightInCM(10)
					.lengthInCM(10)
					.widthInCM(10)
					.build();
		}

		final UomId uomId = UomId.ofRepoIdOrNull(packingMaterial.getC_UOM_Dimension_ID());

		if (uomId == null)
		{
			throw new AdempiereException("Package UOM must be set");
		}

		final I_C_UOM fromUom = InterfaceWrapperHelper.load(uomId, I_C_UOM.class);
		final I_C_UOM toUom = InterfaceWrapperHelper.load(toUomId, I_C_UOM.class);

		final IUOMConversionBL iuomConversionBL = Services.get(IUOMConversionBL.class);
		return PackageDimensions.builder()
				.heightInCM(iuomConversionBL.convert(fromUom, toUom, packingMaterial.getHeight()).get().intValue())
				.lengthInCM(iuomConversionBL.convert(fromUom, toUom, packingMaterial.getLength()).get().intValue())
				.widthInCM(iuomConversionBL.convert(fromUom, toUom, packingMaterial.getWidth()).get().intValue())
				.build();
	}
}
