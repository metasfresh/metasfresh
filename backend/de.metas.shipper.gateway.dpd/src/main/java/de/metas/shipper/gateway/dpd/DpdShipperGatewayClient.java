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

import ch.qos.logback.classic.Level;
import com.dpd.common.service.types.shipmentservice._3.Address;
import com.dpd.common.service.types.shipmentservice._3.FaultCodeType;
import com.dpd.common.service.types.shipmentservice._3.GeneralShipmentData;
import com.dpd.common.service.types.shipmentservice._3.Notification;
import com.dpd.common.service.types.shipmentservice._3.Parcel;
import com.dpd.common.service.types.shipmentservice._3.Pickup;
import com.dpd.common.service.types.shipmentservice._3.PrintOptions;
import com.dpd.common.service.types.shipmentservice._3.ProductAndServiceData;
import com.dpd.common.service.types.shipmentservice._3.ShipmentServiceData;
import com.dpd.common.service.types.shipmentservice._3.StoreOrders;
import com.dpd.common.service.types.shipmentservice._3.StoreOrdersResponse;
import com.dpd.common.service.types.shipmentservice._3.StoreOrdersResponseType;
import com.dpd.common.ws.loginservice.v2_0.types.GetAuth;
import com.dpd.common.ws.loginservice.v2_0.types.GetAuthResponse;
import com.dpd.common.ws.loginservice.v2_0.types.Login;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import de.metas.cache.CCache;
import de.metas.shipper.gateway.api.ShipperGatewayId;
import de.metas.shipper.gateway.dpd.logger.DpdClientLogEvent;
import de.metas.shipper.gateway.dpd.logger.DpdDatabaseClientLogger;
import de.metas.shipper.gateway.dpd.model.DpdClientConfig;
import de.metas.shipper.gateway.dpd.model.DpdOrderCustomDeliveryData;
import de.metas.shipper.gateway.dpd.util.DpdClientUtil;
import de.metas.shipper.gateway.dpd.util.DpdConversionUtil;
import de.metas.shipper.gateway.dpd.util.DpdSoapHeaderWithAuth;
import de.metas.shipper.gateway.spi.DeliveryOrderId;
import de.metas.shipper.gateway.spi.ShipperGatewayClient;
import de.metas.shipper.gateway.spi.exceptions.ShipperGatewayException;
import de.metas.shipper.gateway.spi.model.ContactPerson;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryOrderLine;
import de.metas.shipper.gateway.spi.model.OrderId;
import de.metas.shipper.gateway.spi.model.PackageLabel;
import de.metas.shipper.gateway.spi.model.PackageLabels;
import de.metas.shipper.gateway.spi.model.PickupDate;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.WebServiceTemplate;

import javax.annotation.Nullable;
import javax.xml.bind.JAXBElement;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class DpdShipperGatewayClient implements ShipperGatewayClient
{
	private static final Logger logger = LoggerFactory.getLogger(DpdShipperGatewayClient.class);
	private final DpdDatabaseClientLogger databaseLogger;

	private final DpdClientConfig config;

	// login cache
	// DPD login token changes every 24 hours and they kindly ask us to login at most 2 times a day, hence this login cache with expiration
	private final static int LOGIN_CACHE_23_HOURS_EXPIRATION_TIME = (int)Duration.ofHours(23).toMinutes();
	private static final CCache<Integer, Login> loginCache = CCache.newCache("DpdShipperGatewayClientAuth", 1, LOGIN_CACHE_23_HOURS_EXPIRATION_TIME);

	// dpd webservice data
	private final WebServiceTemplate webServiceTemplate;
	private final com.dpd.common.ws.loginservice.v2_0.types.ObjectFactory loginServiceOF;
	private final com.dpd.common.service.types.shipmentservice._3.ObjectFactory shipmentServiceOF;

	@Builder
	public DpdShipperGatewayClient(final DpdDatabaseClientLogger databaseLogger, @NonNull final DpdClientConfig config)
	{
		this.config = config;
		this.databaseLogger = databaseLogger;

		webServiceTemplate = DpdClientUtil.createWebServiceTemplate();
		loginServiceOF = new com.dpd.common.ws.loginservice.v2_0.types.ObjectFactory();
		shipmentServiceOF = new com.dpd.common.service.types.shipmentservice._3.ObjectFactory();
	}

	@NonNull
	@Override
	public ShipperGatewayId getShipperGatewayId()
	{
		return DpdConstants.SHIPPER_GATEWAY_ID;
	}

	@Override
	@Deprecated
	public DeliveryOrder createDeliveryOrder(final DeliveryOrder draftDeliveryOrder) throws ShipperGatewayException
	{
		throw new ShipperGatewayException("(DRAFT) Delivery Orders shall never be created.");
	}

	@NonNull
	@Override
	public DeliveryOrder completeDeliveryOrder(@NonNull final DeliveryOrder deliveryOrder) throws ShipperGatewayException
	{
		final Login login = loginCache.getOrLoad(0, this::authenticateRequest);

		final ILoggable epicLogger = getEpicLogger();
		epicLogger.addLog("Creating shipment order request for {}", deliveryOrder);

		final StoreOrders storeOrders = createStoreOrdersFromDeliveryOrder(deliveryOrder, login.getDepot());

		final JAXBElement<StoreOrders> storeOrdersElement = shipmentServiceOF.createStoreOrders(storeOrders);
		@SuppressWarnings("unchecked") final JAXBElement<StoreOrdersResponse> storeOrdersResponseElement = (JAXBElement<StoreOrdersResponse>)doActualRequest(config.getShipmentServiceApiUrl(), storeOrdersElement, login, deliveryOrder.getId());

		final StoreOrdersResponseType storeOrdersResponse = storeOrdersResponseElement.getValue().getOrderResult();

		final List<FaultCodeType> faults = storeOrdersResponse.getShipmentResponses().get(0).getFaults();
		if (!faults.isEmpty())
		{
			final String exceptionMessage = faults.stream()
					.map(it -> it.getFaultCode() + ": " + it.getMessage())
					.collect(Collectors.joining("; "));
			throw new ShipperGatewayException(exceptionMessage);
		}

		final DeliveryOrder completedDeliveryOrder = updateDeliveryOrderFromResponse(deliveryOrder, storeOrdersResponse);
		epicLogger.addLog("Completed deliveryOrder is {}", completedDeliveryOrder);

		return completedDeliveryOrder;
	}

	@NonNull
	@Override
	public List<PackageLabels> getPackageLabelsList(@NonNull final DeliveryOrder deliveryOrder) throws ShipperGatewayException
	{
		final ILoggable epicLogger = getEpicLogger();
		epicLogger.addLog("getPackageLabelsList for {}", deliveryOrder);

		final DpdOrderCustomDeliveryData customDeliveryData = DpdOrderCustomDeliveryData.cast(deliveryOrder.getCustomDeliveryData());

		final ImmutableList<PackageLabels> packageLabels = ImmutableList.of(
				PackageLabels.builder()
						.orderId(OrderId.of(getShipperGatewayId(), String.valueOf(deliveryOrder.getId().getRepoId())))
						.defaultLabelType(customDeliveryData.getPaperFormat())
						.label(PackageLabel.builder()
								.type(customDeliveryData.getPaperFormat())
								.labelData(customDeliveryData.getPdfData())
								.contentType(customDeliveryData.getPrinterLanguage())
								.fileName(deliveryOrder.getTrackingNumber())
								.build())
						.build());

		epicLogger.addLog("getPackageLabelsList: labels are {}", packageLabels);

		return packageLabels;
	}

	@NonNull
	private DeliveryOrder updateDeliveryOrderFromResponse(@NonNull final DeliveryOrder deliveryOrder, @NonNull final StoreOrdersResponseType storeOrdersResponse)
	{
		final DpdOrderCustomDeliveryData customDeliveryData = DpdOrderCustomDeliveryData.cast(deliveryOrder.getCustomDeliveryData())
				.toBuilder()
				.pdfData(storeOrdersResponse.getParcellabelsPDF())
				.build();

		final String mpsId = storeOrdersResponse.getShipmentResponses().get(0).getMpsId();
		return deliveryOrder.toBuilder()
				.trackingNumber(mpsId)
				.trackingUrl(config.getTrackingUrlBase() + mpsId)
				.customDeliveryData(customDeliveryData)
				.build();
	}

	private Object doActualRequest(
			@NonNull final String apiUrl,
			@NonNull final Object request,
			@Nullable final Login login,
			@Nullable final DeliveryOrderId deliveryOrderRepoIdForLogging)
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();
		final DpdClientLogEvent.DpdClientLogEventBuilder logEventBuilder = DpdClientLogEvent.builder()
				.marshaller(webServiceTemplate.getMarshaller())
				.requestElement(request)
				.deliveryOrderRepoId(deliveryOrderRepoIdForLogging == null ? 0 : deliveryOrderRepoIdForLogging.getRepoId())
				.config(config);
		try
		{
			final Object response;
			if (login == null)
			{
				// this is a login request
				response = webServiceTemplate.marshalSendAndReceive(apiUrl, request);
			}
			else
			{
				// this is a normal request
				response = webServiceTemplate.marshalSendAndReceive(apiUrl, request, new DpdSoapHeaderWithAuth(login));
			}
			databaseLogger.log(logEventBuilder
					.responseElement(response)
					.durationMillis(stopwatch.elapsed(TimeUnit.MILLISECONDS))
					.build());
			return response;
		}
		catch (final Throwable throwable)
		{
			final AdempiereException exception = AdempiereException.wrapIfNeeded(throwable);
			databaseLogger.log(logEventBuilder
					.responseException(exception)
					.durationMillis(stopwatch.elapsed(TimeUnit.MILLISECONDS))
					.build());

			throw exception;
		}
	}

	@NonNull
	private StoreOrders createStoreOrdersFromDeliveryOrder(@NonNull final DeliveryOrder deliveryOrder, @NonNull final String depot)
	{
		final StoreOrders storeOrders = shipmentServiceOF.createStoreOrders();
		final PrintOptions printOptions = createPrintOptions(DpdOrderCustomDeliveryData.cast(deliveryOrder.getCustomDeliveryData()));
		storeOrders.setPrintOptions(printOptions);

		final ShipmentServiceData shipmentServiceData = createShipmentServiceData(deliveryOrder);
		storeOrders.getOrder().add(shipmentServiceData);

		// set the depot from the login data
		storeOrders.getOrder().forEach(it -> it.getGeneralShipmentData().setSendingDepot(depot));

		return storeOrders;
	}

	@NonNull
	private ShipmentServiceData createShipmentServiceData(
			@NonNull final DeliveryOrder deliveryOrder)
	{
		// Shipment Data 1
		final ShipmentServiceData shipmentServiceData = shipmentServiceOF.createShipmentServiceData();
		{
			// General Shipment Data
			final GeneralShipmentData generalShipmentData = shipmentServiceOF.createGeneralShipmentData();
			shipmentServiceData.setGeneralShipmentData(generalShipmentData);
			//noinspection ConstantConditions
			generalShipmentData.setMpsCustomerReferenceNumber1(deliveryOrder.getCustomerReference()); // what is this? optional?
			generalShipmentData.setIdentificationNumber(String.valueOf(deliveryOrder.getId().getRepoId())); // unique metasfresh number for this shipment
			//			generalShipmentData.setSendingDepot(); // not set here, as it's taken from login info
			generalShipmentData.setProduct(deliveryOrder.getShipperProduct().getCode()); // this is the DPD product

			{
				// Sender aka Pickup
				// todo [sender address vs login address]
				// 		i'm not sure if the following is true, as dpd test doesn't complain if the sender address is different from the one in the login.
				// 		the sender _seems_ hard linked with the location provided to dpd when the account was created.
				// 		it is not connected, though, with the pickup address! as in the pickup section there's the mandatory field "CollectionRequestAddress".
				final Address sender = createAddress(deliveryOrder.getPickupAddress());
				generalShipmentData.setSender(sender);
			}
			{
				// Recipient aka Delivery
				//noinspection ConstantConditions
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
				parcel.setWeight(DpdConversionUtil.convertWeightKgToDag(deliveryOrderLine.getGrossWeightKg().intValue()));
				//				parcel.setInternational(); // todo non-UE orders will be implemented in a followup task
			}
		}
		{
			// ProductAndService Data
			final ProductAndServiceData productAndServiceData = shipmentServiceOF.createProductAndServiceData();
			shipmentServiceData.setProductAndServiceData(productAndServiceData);
			{
				// only works with E12 product
				// Saturday delivery is disabled for now. Maybe in the future :)
				// productAndServiceData.setSaturdayDelivery(DpdShipperProduct.DPD_E12.equals(deliveryOrder.getShipperProduct()));
			}
			{
				// Shipper Product
				productAndServiceData.setOrderType(DpdOrderCustomDeliveryData.cast(deliveryOrder.getCustomDeliveryData()).getOrderType()); // this is somehow related to product: CL; and i think it should always be "consignment"
			}
			{
				// Predict aka Notification
				//
				// For some reason, notifications of any kind only work with DpdServiceType classic. All the rest will throw error.
				// There i no explanation of what's going on, and what combinations are functional.
				// Because of this all notifications are disabled for now.

				// final Notification notification = createNotification(deliveryOrder);
				// productAndServiceData.setPredict(notification);
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
	private Address createAddress(
			@NonNull final de.metas.shipper.gateway.spi.model.Address addressFrom)
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
	private Address createRecipientAddress(
			@NonNull final de.metas.shipper.gateway.spi.model.Address deliveryAddress,
			@NonNull final ContactPerson deliveryContact)
	{
		final Address recipient = createAddress(deliveryAddress);
		//noinspection ConstantConditions
		recipient.setPhone(deliveryContact.getPhoneAsStringOrNull());
		recipient.setEmail(deliveryContact.getEmailAddress());
		return recipient;
	}

	@Nullable
	private Notification createNotification(
			@NonNull final DeliveryOrder deliveryOrder)
	{
		if (deliveryOrder.getDeliveryContact() != null && deliveryOrder.getDeliveryContact().getEmailAddress() == null)
		{
			return null;
		}

		final Notification notification = shipmentServiceOF.createNotification();
		notification.setChannel(DpdOrderCustomDeliveryData.cast(deliveryOrder.getCustomDeliveryData()).getNotificationChannel().toDpdDataFormat());
		//noinspection ConstantConditions
		notification.setValue(deliveryOrder.getDeliveryContact().getEmailAddress());
		notification.setLanguage(deliveryOrder.getDeliveryAddress().getCountry().getAlpha2());
		return notification;
	}

	@NonNull
	private Pickup createPickupDateAndTime(
			@NonNull final PickupDate pickupDate, final int numberOfPackages,
			@NonNull final de.metas.shipper.gateway.spi.model.Address sender)
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
	private PrintOptions createPrintOptions(
			@NonNull final DpdOrderCustomDeliveryData customDeliveryData)
	{
		final PrintOptions printOptions = shipmentServiceOF.createPrintOptions();
		printOptions.setPaperFormat(customDeliveryData.getPaperFormat().getCode());
		printOptions.setPrinterLanguage(customDeliveryData.getPrinterLanguage());
		return printOptions;
	}

	private Login authenticateRequest()
	{
		// Login
		final GetAuth getAuthValue = loginServiceOF.createGetAuth();
		getAuthValue.setDelisId(config.getDelisID());
		getAuthValue.setPassword(config.getDelisPassword());
		getAuthValue.setMessageLanguage(DpdConstants.DEFAULT_MESSAGE_LANGUAGE);

		final ILoggable epicLogger = getEpicLogger();
		epicLogger.addLog("Creating login request");

		final JAXBElement<GetAuth> getAuthElement = loginServiceOF.createGetAuth(getAuthValue);
		@SuppressWarnings("unchecked") final JAXBElement<GetAuthResponse> authenticationElement = (JAXBElement<GetAuthResponse>)doActualRequest(config.getLoginApiUrl(), getAuthElement, null, null);

		final Login login = authenticationElement.getValue().getReturn();

		epicLogger.addLog("Finished login Request");

		return login;
	}

	/**
	 * no idea what this does, but tobias sais it's useful to have this special log, so here it is!
	 */
	@NonNull
	private ILoggable getEpicLogger()
	{
		return Loggables.withLogger(logger, Level.TRACE);
	}

}
