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

import com.dpd.common.service.types.shipmentservice._3.Address;
import com.dpd.common.service.types.shipmentservice._3.GeneralShipmentData;
import com.dpd.common.service.types.shipmentservice._3.Notification;
import com.dpd.common.service.types.shipmentservice._3.Parcel;
import com.dpd.common.service.types.shipmentservice._3.Pickup;
import com.dpd.common.service.types.shipmentservice._3.PrintOptions;
import com.dpd.common.service.types.shipmentservice._3.ProductAndServiceData;
import com.dpd.common.service.types.shipmentservice._3.ShipmentResponse;
import com.dpd.common.service.types.shipmentservice._3.ShipmentServiceData;
import com.dpd.common.service.types.shipmentservice._3.StoreOrders;
import com.dpd.common.service.types.shipmentservice._3.StoreOrdersResponse;
import com.dpd.common.service.types.shipmentservice._3.StoreOrdersResponseType;
import com.dpd.common.ws.loginservice.v2_0.types.GetAuth;
import com.dpd.common.ws.loginservice.v2_0.types.GetAuthResponse;
import com.dpd.common.ws.loginservice.v2_0.types.Login;
import de.metas.shipper.gateway.dpd.model.DpdOrderCustomDeliveryData;
import de.metas.shipper.gateway.dpd.util.DpdClientUtil;
import de.metas.shipper.gateway.dpd.util.DpdConversionUtil;
import de.metas.shipper.gateway.dpd.util.DpdSoapHeaderWithAuth;
import de.metas.shipper.gateway.spi.DeliveryOrderId;
import de.metas.shipper.gateway.spi.model.ContactPerson;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryOrderLine;
import de.metas.shipper.gateway.spi.model.PickupDate;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ws.client.core.WebServiceTemplate;

import javax.xml.bind.JAXBElement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestApiRequestFromDeliveryOrderDEtoDE
{
	private WebServiceTemplate webServiceTemplate;
	private com.dpd.common.ws.loginservice.v2_0.types.ObjectFactory loginServiceOF;
	private com.dpd.common.service.types.shipmentservice._3.ObjectFactory shipmentServiceOF;

	@BeforeEach
	private void beforeEach()
	{
		webServiceTemplate = DpdClientUtil.createWebServiceTemplate();
		loginServiceOF = new com.dpd.common.ws.loginservice.v2_0.types.ObjectFactory();
		shipmentServiceOF = new com.dpd.common.service.types.shipmentservice._3.ObjectFactory();
	}

	@Test
	void DEtoDE()
	{
		final Login login = authenticateRequest();

		final StoreOrders storeOrders = createShipmentOrderRequestFromDeliveryOrder();

		// mandatory to have sending depot
		storeOrders.getOrder().forEach(it -> it.getGeneralShipmentData().setSendingDepot(login.getDepot()));

		{
			// Call the Shipment API
			final JAXBElement<StoreOrders> storeOrdersElement = shipmentServiceOF.createStoreOrders(storeOrders);
			//noinspection unchecked
			final JAXBElement<StoreOrdersResponse> storeOrdersResponseElement =
					(JAXBElement<StoreOrdersResponse>)webServiceTemplate.marshalSendAndReceive(DpdTestHelper.SHIPMENT_SERVICE_API_URL, storeOrdersElement, new DpdSoapHeaderWithAuth(login));

			final StoreOrdersResponseType orderResult = storeOrdersResponseElement.getValue().getOrderResult();
			assertTrue(orderResult.getShipmentResponses().get(0).getFaults().isEmpty());
			assertTrue(orderResult.getParcellabelsPDF().length > 2);
			assertEquals(1, orderResult.getShipmentResponses().size());
			final ShipmentResponse shipmentResponse = orderResult.getShipmentResponses().get(0);
			assertEquals("987654321", shipmentResponse.getIdentificationNumber());
			assertTrue(StringUtils.isNotBlank(shipmentResponse.getMpsId()));

			DpdTestHelper.dumpPdfToDisk(orderResult.getParcellabelsPDF());
		}
	}

	private StoreOrders createShipmentOrderRequestFromDeliveryOrder()
	{
		DeliveryOrder deliveryOrder = DpdTestHelper.createDummyDeliveryOrderDEtoDE();
		deliveryOrder = deliveryOrder.toBuilder()
				.repoId(DeliveryOrderId.ofRepoId(987654321))
				.build();

		return createStoreOrdersFromDeliveryOrder(deliveryOrder);
	}

	@NonNull
	private StoreOrders createStoreOrdersFromDeliveryOrder(@NonNull final DeliveryOrder deliveryOrder)
	{
		final StoreOrders storeOrders = shipmentServiceOF.createStoreOrders();
		final PrintOptions printOptions = createPrintOptions(DpdOrderCustomDeliveryData.cast(deliveryOrder.getCustomDeliveryData()));
		storeOrders.setPrintOptions(printOptions);

		final ShipmentServiceData shipmentServiceData = createShipmentServiceData(deliveryOrder);
		storeOrders.getOrder().add(shipmentServiceData);

		return storeOrders;
	}

	@NonNull
	private ShipmentServiceData createShipmentServiceData(@NonNull final DeliveryOrder deliveryOrder)
	{
		// Shipment Data 1
		final ShipmentServiceData shipmentServiceData = shipmentServiceOF.createShipmentServiceData();
		{
			// General Shipment Data
			final GeneralShipmentData generalShipmentData = shipmentServiceOF.createGeneralShipmentData();
			shipmentServiceData.setGeneralShipmentData(generalShipmentData);
			//noinspection ConstantConditions
			generalShipmentData.setMpsCustomerReferenceNumber1(deliveryOrder.getCustomerReference()); // what is this? optional?
			generalShipmentData.setIdentificationNumber(String.valueOf(deliveryOrder.getRepoId().getRepoId())); // unique metasfresh number for this shipment
			//			generalShipmentData.setSendingDepot(); // not set here, as it's taken from login info
			generalShipmentData.setProduct(deliveryOrder.getServiceType().getCode()); // this is the DPD product

			{
				// Sender aka Pickup
				// todo i'm not sure if the following is true, as dpd test doesn't complain if the sender address is different from the one in the login.
				// 		the sender _seems_ hard linked with the location provided to dpd when the account was created.
				// 		it is not connected, though, with the pickup address! as in the pickup section there's the mandatory field "CollectionRequestAddress".
				final Address sender = createAddress(deliveryOrder.getPickupAddress());
				generalShipmentData.setSender(sender);
			}
			{
				// Recipient aka Delivery
				final Address recipient = createRecipientAddress(deliveryOrder.getDeliveryAddress(), deliveryOrder.getDeliveryContact());
				generalShipmentData.setRecipient(recipient);
			}
		}
		{
			// Parcels aka Packages aka DeliveryOrderLines
			for (final DeliveryOrderLine deliveryOrderLine : deliveryOrder.getDeliveryOrderLines())
			{
				final Parcel parcel = shipmentServiceOF.createParcel();
				shipmentServiceData.getParcels().add(parcel);
				//noinspection ConstantConditions
				parcel.setContent(deliveryOrderLine.getContent());
				parcel.setVolume(DpdConversionUtil.formatVolume(deliveryOrderLine.getPackageDimensions()));
				parcel.setWeight(DpdConversionUtil.convertWeightKgToDag(deliveryOrderLine.getGrossWeightKg()));
				//				parcel.setInternational(); // todo god save us
			}
		}
		{
			// ProductAndService Data
			final ProductAndServiceData productAndServiceData = shipmentServiceOF.createProductAndServiceData();
			shipmentServiceData.setProductAndServiceData(productAndServiceData);
			{
				// Shipper Product

				productAndServiceData.setOrderType(DpdOrderCustomDeliveryData.cast(deliveryOrder.getCustomDeliveryData()).getOrderType()); // this is somehow related to product: CL; and i think it should always be "consignment"
			}
			{
				// Predict aka Notification
				final Notification notification = createNotification(deliveryOrder);
				productAndServiceData.setPredict(notification);
			}
			{
				// Pickup date and time
				final Pickup pickup = createPickupDateAndTime(deliveryOrder.getPickupDate(), deliveryOrder.getDeliveryOrderLines().size(), deliveryOrder.getPickupAddress());
				productAndServiceData.setPickup(pickup);
			}
		}
		return shipmentServiceData;
	}

	@NonNull
	private Pickup createPickupDateAndTime(@NonNull final PickupDate pickupDate, final int numberOfPackages, @NonNull final de.metas.shipper.gateway.spi.model.Address sender)
	{
		final Pickup pickup = shipmentServiceOF.createPickup();
		pickup.setQuantity(numberOfPackages);
		pickup.setDate(DpdConversionUtil.formatDate(pickupDate.getDate()));
		pickup.setDay(DpdConversionUtil.getPickupDayOfTheWeek(pickupDate));
		pickup.setFromTime1(DpdConversionUtil.formatTime(pickupDate.getTimeFrom()));
		pickup.setToTime1(DpdConversionUtil.formatTime(pickupDate.getTimeTo()));
		//					pickup.setExtraPickup(true); // optional
		pickup.setCollectionRequestAddress(createAddress(sender));
		return pickup;
	}

	@NonNull
	private Notification createNotification(@NonNull final DeliveryOrder deliveryOrder)
	{
		final Notification notification = shipmentServiceOF.createNotification();
		//noinspection ConstantConditions - custom delivery order data is never null for dpd
		notification.setChannel(DpdOrderCustomDeliveryData.cast(deliveryOrder.getCustomDeliveryData()).getNotificationChannel().toDpdDataFormat());
		//noinspection ConstantConditions
		notification.setValue(deliveryOrder.getDeliveryContact().getEmailAddress());
		notification.setLanguage(deliveryOrder.getDeliveryAddress().getCountry().getAlpha2());
		return notification;
	}

	@NonNull
	private Address createRecipientAddress(@NonNull final de.metas.shipper.gateway.spi.model.Address deliveryAddress, @NonNull final ContactPerson deliveryContact)
	{
		final Address recipient = createAddress(deliveryAddress);
		//noinspection ConstantConditions
		recipient.setPhone(deliveryContact.getPhoneAsStringOrNull());
		recipient.setEmail(deliveryContact.getEmailAddress());
		return recipient;
	}

	@NonNull
	private Address createAddress(@NonNull final de.metas.shipper.gateway.spi.model.Address addressFrom)
	{
		final Address address = shipmentServiceOF.createAddress();
		address.setName1(addressFrom.getCompanyName1());
		address.setName2(addressFrom.getCompanyName2());
		address.setStreet(addressFrom.getStreet1());
		address.setHouseNo(addressFrom.getHouseNo());
		address.setZipCode(addressFrom.getZipCode());
		address.setCity(addressFrom.getCity());
		address.setCountry(addressFrom.getCountry().getAlpha2());
		return address;
	}

	@NonNull
	private PrintOptions createPrintOptions(@NonNull final DpdOrderCustomDeliveryData customDeliveryData)
	{
		// Print Options
		final PrintOptions printOptions = shipmentServiceOF.createPrintOptions();
		printOptions.setPaperFormat(customDeliveryData.getPaperFormat().getCode());
		printOptions.setPrinterLanguage(customDeliveryData.getPrinterLanguage());
		return printOptions;
	}

	private Login authenticateRequest()
	{
		// Login
		final GetAuth getAuthValue = loginServiceOF.createGetAuth();
		getAuthValue.setDelisId(DpdTestHelper.DELIS_ID);
		getAuthValue.setPassword(DpdTestHelper.DELIS_PASSWORD);
		getAuthValue.setMessageLanguage(DpdConstants.DEFAULT_MESSAGE_LANGUAGE);

		final JAXBElement<GetAuth> getAuth = loginServiceOF.createGetAuth(getAuthValue);
		//noinspection unchecked
		final JAXBElement<GetAuthResponse> authenticationElement = (JAXBElement<GetAuthResponse>)webServiceTemplate.marshalSendAndReceive(DpdTestHelper.LOGIN_SERVICE_API_URL, getAuth);
		final Login login = authenticationElement.getValue().getReturn();

		assertTrue(StringUtils.isNotBlank(login.getAuthToken()));
		assertTrue(StringUtils.isNotBlank(login.getDepot()));
		assertEquals(login.getDelisId(), DpdTestHelper.DELIS_ID);
		assertEquals(login.getCustomerUid(), DpdTestHelper.DELIS_ID);

		return login;
	}
}
