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

import com.google.common.collect.ImmutableList;
import de.metas.attachments.AttachmentEntryService;
import de.metas.mpackage.PackageId;
import de.metas.shipper.gateway.dpd.model.DpdOrderCustomDeliveryData;
import de.metas.shipper.gateway.dpd.model.I_DPD_StoreOrder;
import de.metas.shipper.gateway.dpd.util.DpdConversionUtil;
import de.metas.shipper.gateway.spi.DeliveryOrderId;
import de.metas.shipper.gateway.spi.DeliveryOrderRepository;
import de.metas.shipper.gateway.spi.model.Address;
import de.metas.shipper.gateway.spi.model.ContactPerson;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryPosition;
import de.metas.shipper.gateway.spi.model.PickupDate;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class DpdDeliveryOrderRepository implements DeliveryOrderRepository
{
	private static final Logger logger = LoggerFactory.getLogger(DpdDeliveryOrderRepository.class);
	private final AttachmentEntryService attachmentEntryService;

	public DpdDeliveryOrderRepository(final AttachmentEntryService attachmentEntryService)
	{
		this.attachmentEntryService = attachmentEntryService;
	}

	@Override
	public String getShipperGatewayId()
	{
		return DpdConstants.SHIPPER_GATEWAY_ID;
	}

	@NonNull
	@Override
	public ITableRecordReference toTableRecordReference(@NonNull final DeliveryOrder deliveryOrder)
	{
		final int deliveryOrderRepoId = deliveryOrder.getRepoId();
		Check.assume(deliveryOrderRepoId > 0, "deliveryOrderRepoId > 0 for {}", deliveryOrder);
		return TableRecordReference.of(I_DPD_StoreOrder.Table_Name, deliveryOrderRepoId);
	}

	@Override
	public DeliveryOrder getByRepoId(final DeliveryOrderId deliveryOrderRepoId)
	{
		return null;
	}

	/**
	 * Explanation of the different data structures:
	 * <p>
	 * - DeliveryOrder is the DTO
	 * - I_Dpd_StoreOrder is the persisted object for that DTO (which has lines), with data relevant for DPD.
	 * Each different shipper has its own "shipper-po" with its own relevant data.
	 */
	@Override
	@NonNull
	public DeliveryOrder save(@NonNull final DeliveryOrder deliveryOrder)
	{
		if (deliveryOrder.getRepoId() >= 1)
		{

			return null;
		}
		else
		{
			final I_DPD_StoreOrder storeOrderPO = createStoreOrderPO(deliveryOrder);
			return updateDeliveryOrderRepoId(deliveryOrder, storeOrderPO);
		}
	}

	@NonNull
	private DeliveryOrder updateDeliveryOrderRepoId(@NonNull final DeliveryOrder deliveryOrder, @NonNull final I_DPD_StoreOrder storeOrderPO)
	{
		return deliveryOrder.toBuilder()
				.repoId(storeOrderPO.getDPD_StoreOrder_ID())
				.build();
	}

	/**
	 * Persists the shipper-dependant DeliveryOrder details.
	 * <p>
	 * Keep in sync with
	 */
	@NonNull
	private I_DPD_StoreOrder createStoreOrderPO(@NonNull final DeliveryOrder deliveryOrder)
	{
		final I_DPD_StoreOrder dpdStoreOrder = InterfaceWrapperHelper.newInstance(I_DPD_StoreOrder.class);
		InterfaceWrapperHelper.save(dpdStoreOrder);

		// maybe this will be removed in the future, but for now it simplifies the PO deserialization implementation and other implementation details dramatically, ref: constructDeliveryPositions()
		// therefore please ignore the for loops over `deliveryOrder.getDeliveryPositions()` as they don't help at all
		Check.errorIf(deliveryOrder.getDeliveryPositions().size() != 1,
				"The DPD implementation needs to always create DeliveryOrders with exactly 1 DeliveryPosition; deliveryOrder={}",
				deliveryOrder);

		final DeliveryPosition deliveryPosition = deliveryOrder.getDeliveryPositions().get(0);
		final ImmutableList<PackageId> packageIdsAsList = deliveryPosition.getPackageIds().asList();
		for (final PackageId packageID : packageIdsAsList)
		{
			{
				// Pickup aka Sender
				final Address pickupAddress = deliveryOrder.getPickupAddress();
				dpdStoreOrder.setSenderName1(pickupAddress.getCompanyName1());
				dpdStoreOrder.setSenderName2(pickupAddress.getCompanyName2());
				dpdStoreOrder.setSenderStreet(pickupAddress.getStreet1());
				dpdStoreOrder.setSenderHouseNo(pickupAddress.getHouseNo());
				dpdStoreOrder.setSenderZipCode(pickupAddress.getZipCode());
				dpdStoreOrder.setSenderCity(pickupAddress.getCity());
				dpdStoreOrder.setSenderCountry(pickupAddress.getCountry().getAlpha2());
			}
			{
				// Pickup Date and Time
				final PickupDate pickupDate = deliveryOrder.getPickupDate();
				dpdStoreOrder.setPickupDate(DpdConversionUtil.formatDate(pickupDate.getDate()));
				dpdStoreOrder.setPickupDay(DpdConversionUtil.getPickupDayOfTheWeek(pickupDate));
				dpdStoreOrder.setPickupTimeFrom(DpdConversionUtil.formatTime(pickupDate.getTimeFrom()));
				dpdStoreOrder.setPickupTimeTo(DpdConversionUtil.formatTime(pickupDate.getTimeTo()));
			}
			{
				// Delivery aka Recipient
				final Address deliveryAddress = deliveryOrder.getDeliveryAddress();
				dpdStoreOrder.setRecipientName1(deliveryAddress.getCompanyName1());
				dpdStoreOrder.setRecipientName2(deliveryAddress.getCompanyName2());
				dpdStoreOrder.setRecipientStreet(deliveryAddress.getStreet1());
				dpdStoreOrder.setRecipientHouseNo(deliveryAddress.getHouseNo());
				dpdStoreOrder.setRecipientZipCode(deliveryAddress.getZipCode());
				dpdStoreOrder.setRecipientCity(deliveryAddress.getCity());
				dpdStoreOrder.setRecipientCountry(deliveryAddress.getCountry().getAlpha2());
				final ContactPerson deliveryContact = deliveryOrder.getDeliveryContact();
				dpdStoreOrder.setRecipientEmailAddress(deliveryContact.getEmailAddress());
				dpdStoreOrder.setRecipientPhone(deliveryContact.getPhoneAsStringOrNull());
			}
			final DpdOrderCustomDeliveryData customDeliveryData = DpdOrderCustomDeliveryData.cast(deliveryOrder.getCustomDeliveryData());
			{
				// Predict aka Notification
				dpdStoreOrder.setNotificationChannel(customDeliveryData.getNotificationChannel().getCode());
			}
			{
				// General Shipment Data
				// dpdStoreOrder.identification number // there's no identification number saved. it needs to be created during PO retrieval!
				dpdStoreOrder.setDpdProduct(deliveryOrder.getServiceType().getCode());
				dpdStoreOrder.setDpdOrderType(customDeliveryData.getOrderType());
				dpdStoreOrder.setSendingDepot(customDeliveryData.getSendingDepot());

			}
			{
				// Parcels aka Packages aka DeliveryPositions
				dpdStoreOrder.setPackageContent(deliveryPosition.getContent());
				dpdStoreOrder.setVolume(DpdConversionUtil.formatVolume(deliveryPosition.getPackageDimensions()));
				dpdStoreOrder.setWeightInKg(deliveryPosition.getGrossWeightKg());
				// dpdStoreOrder.setpackageId(packageID); todo persist this
			}
		}

		return dpdStoreOrder;
	}
}
