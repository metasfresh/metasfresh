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

import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.handlingunits.model.I_M_Package_HU;
import de.metas.organization.OrgId;
import de.metas.shipper.gateway.commons.DeliveryOrderUtil;
import de.metas.shipper.gateway.dhl.model.DhlClientConfig;
import de.metas.shipper.gateway.dhl.model.DhlClientConfigRepository;
import de.metas.shipper.gateway.dhl.model.DhlServiceType;
import de.metas.shipper.gateway.spi.DraftDeliveryOrderCreator;
import de.metas.shipper.gateway.spi.model.ContactPerson;
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
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_UOM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

@Service
public class DhlDraftDeliveryOrderCreator implements DraftDeliveryOrderCreator
{
	private static final Logger logger = LoggerFactory.getLogger(DhlDraftDeliveryOrderCreator.class);

	@Override
	public String getShipperGatewayId()
	{
		return DhlConstants.SHIPPER_GATEWAY_ID;
	}

	/**
	 * Create the initial DTO.
	 * <p>
	 * keep in sync with {@link DhlDeliveryOrderRepository#toDeliveryOrderFromPO(de.metas.shipper.gateway.dhl.model.I_DHL_ShipmentOrderRequest)}
	 * and {@link DhlDeliveryOrderRepository#toCreateShipmentOrderRequestPO(de.metas.shipper.gateway.spi.model.DeliveryOrder)}
	 */
	@SuppressWarnings("JavadocReference")
	@NonNull
	@Override
	public DeliveryOrder createDraftDeliveryOrder(@NonNull final CreateDraftDeliveryOrderRequest request)
	{
		final DeliveryOrderKey deliveryOrderKey = request.getDeliveryOrderKey();
		final Set<Integer> mpackageIds = request.getMpackageIds();

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

		return DeliveryOrder.builder()
				.shipperId(deliveryOrderKey.getShipperId())
				.shipperTransportationId(deliveryOrderKey.getShipperTransportationId())
				//

				.serviceType(DhlServiceType.V01PAK) // todo how to change the service type?
				//				.customerReference() // todo this is not set in any place with any user-relevant value afaics!
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
						.bpartnerId(deliverToBPartnerId) // afaics used only for logging
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
						.grossWeightKg(Math.max(request.getGrossWeightInKg(), 1))
						.packageDimensions(getPackageDimensions(mpackageIds, deliveryOrderKey.getShipperId()))
						.build())
				//
				.build();

	}

	/**
	 * Assume that all the packages inside a delivery position are of the same type and therefore have the same size.
	 * <p>
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
	 * thx to ruxi for this query
	 */
	@NonNull
	private PackageDimensions getPackageDimensions(@NonNull final Set<Integer> mpackageIds, final int shipperId)
	{
		final Integer firstPackageId = mpackageIds.iterator().next();

		// packing material is never null
		final I_M_HU_PackingMaterial packingMaterial = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Package_HU.class)
				.addEqualsFilter(I_M_Package_HU.COLUMNNAME_M_Package_ID, firstPackageId)
				//

				.andCollect(I_M_HU.COLUMN_M_HU_ID, I_M_HU.class)
				.andCollectChildren(I_M_HU_Item.COLUMN_M_HU_ID)
				.andCollect(I_M_HU_PackingMaterial.COLUMN_M_HU_PackingMaterial_ID, I_M_HU_PackingMaterial.class)
				.create()
				.firstOnly(I_M_HU_PackingMaterial.class);

		final UomId uomId = UomId.ofRepoIdOrNull(packingMaterial.getC_UOM_Dimension_ID());

		if (uomId == null)
		{
			throw new AdempiereException("Package UOM must be set");
		}

		final DhlClientConfig clientConfig = SpringContextHolder.instance.getBean(DhlClientConfigRepository.class).getByShipperId(ShipperId.ofRepoId(shipperId));

		final I_C_UOM fromUom = InterfaceWrapperHelper.load(uomId, I_C_UOM.class);
		final I_C_UOM toUom = InterfaceWrapperHelper.load(clientConfig.getLengthUomId(), I_C_UOM.class);

		return PackageDimensions.builder()
				.heightInCM(Services.get(IUOMConversionBL.class).convert(fromUom, toUom, packingMaterial.getHeight()).get().intValue())
				.lengthInCM(Services.get(IUOMConversionBL.class).convert(fromUom, toUom, packingMaterial.getLength()).get().intValue())
				.widthInCM(Services.get(IUOMConversionBL.class).convert(fromUom, toUom, packingMaterial.getWidth()).get().intValue())
				.build();
	}
}
