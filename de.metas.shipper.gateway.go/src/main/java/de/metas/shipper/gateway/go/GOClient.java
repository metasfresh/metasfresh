package de.metas.shipper.gateway.go;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.xml.bind.JAXBElement;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

import com.google.common.base.MoreObjects;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;

import de.metas.shipper.gateway.go.GOClientLogEvent.GOClientLogEventBuilder;
import de.metas.shipper.gateway.go.schema.Fehlerbehandlung;
import de.metas.shipper.gateway.go.schema.GOOrderStatus;
import de.metas.shipper.gateway.go.schema.GOPackageLabelType;
import de.metas.shipper.gateway.go.schema.Label;
import de.metas.shipper.gateway.go.schema.ObjectFactory;
import de.metas.shipper.gateway.go.schema.Sendung;
import de.metas.shipper.gateway.go.schema.SendungsRueckmeldung;
import de.metas.shipper.gateway.go.schema.Sendungsnummern;
import de.metas.shipper.gateway.spi.ShipperGatewayClient;
import de.metas.shipper.gateway.spi.exceptions.ShipperErrorMessage;
import de.metas.shipper.gateway.spi.exceptions.ShipperGatewayException;
import de.metas.shipper.gateway.spi.model.Address;
import de.metas.shipper.gateway.spi.model.ContactPerson;
import de.metas.shipper.gateway.spi.model.DeliveryDate;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryPosition;
import de.metas.shipper.gateway.spi.model.HWBNumber;
import de.metas.shipper.gateway.spi.model.OrderId;
import de.metas.shipper.gateway.spi.model.PackageDimensions;
import de.metas.shipper.gateway.spi.model.PackageLabel;
import de.metas.shipper.gateway.spi.model.PackageLabels;
import de.metas.shipper.gateway.spi.model.PhoneNumber;
import de.metas.shipper.gateway.spi.model.PickupDate;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * de.metas.shipper.go
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class GOClient implements ShipperGatewayClient
{
	private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

	private static final Logger logger = LoggerFactory.getLogger(GOClient.class);
	private final GOClientLogger goClientLogger;

	private final ObjectFactory objectFactory = new ObjectFactory();
	private final WebServiceTemplate webServiceTemplate;

	private final GOClientConfig config;

	@Builder
	private GOClient(
			@NonNull final GOClientConfig config,
			final GOClientLogger goClientLogger)
	{
		this.config = config;
		this.goClientLogger = goClientLogger != null ? goClientLogger : SLF4JGOClientLogger.instance;

		final HttpComponentsMessageSender messageSender = createMessageSender(config.getAuthUsername(), config.getAuthPassword());

		final Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setPackagesToScan(de.metas.shipper.gateway.go.schema.ObjectFactory.class.getPackage().getName());

		webServiceTemplate = new WebServiceTemplate();
		webServiceTemplate.setDefaultUri(config.getUrl());
		webServiceTemplate.setMessageSender(messageSender);
		webServiceTemplate.setMarshaller(marshaller);
		webServiceTemplate.setUnmarshaller(marshaller);
	}

	private static HttpComponentsMessageSender createMessageSender(final String authUsername, final String authPassword)
	{
		final UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(authUsername, authPassword);

		final HttpComponentsMessageSender messageSender = new HttpComponentsMessageSender();
		messageSender.setCredentials(credentials);
		try
		{
			messageSender.afterPropertiesSet(); // to make sure credentials are set to HttpClient
		}
		catch (Exception ex)
		{
			throw AdempiereException.wrapIfNeeded(ex);
		}
		return messageSender;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("url", webServiceTemplate.getDefaultUri())
				.toString();
	}

	@Override
	public String getShipperGatewayId()
	{
		return GOConstants.SHIPPER_GATEWAY_ID;
	}

	@Override
	public DeliveryOrder createDeliveryOrder(@NonNull final DeliveryOrder draftDeliveryOrder)
	{
		logger.trace("Creating delivery order for {}", draftDeliveryOrder);
		final Sendung goRequest = createGODeliveryOrder(draftDeliveryOrder, GOOrderStatus.NEW);

		final Object goResponseObj = sendAndReceive(
				objectFactory.createGOWebServiceSendungsErstellung(goRequest),
				"createDeliveryOrder",
				draftDeliveryOrder.getRepoId());
		final SendungsRueckmeldung goResponse = (SendungsRueckmeldung)goResponseObj;
		final DeliveryOrder deliveryOrderResponse = createDeliveryOrderFromResponse(goResponse, draftDeliveryOrder, GOOrderStatus.NEW);
		logger.trace("Delivery order created: {}", deliveryOrderResponse);

		return deliveryOrderResponse;
	}

	@Override
	public DeliveryOrder completeDeliveryOrder(@NonNull final DeliveryOrder deliveryOrderRequest)
	{
		logger.trace("Creating delivery order for {}", deliveryOrderRequest);
		final Sendung goRequest = createGODeliveryOrder(deliveryOrderRequest, GOOrderStatus.APPROVED);

		final Object goResponseObj = sendAndReceive(
				objectFactory.createGOWebServiceSendungsErstellung(goRequest),
				"completeDeliveryOrder",
				deliveryOrderRequest.getRepoId());
		final SendungsRueckmeldung goResponse = (SendungsRueckmeldung)goResponseObj;
		final DeliveryOrder deliveryOrderResponse = createDeliveryOrderFromResponse(goResponse, deliveryOrderRequest, GOOrderStatus.APPROVED);
		logger.trace("Delivery order completed: {}", deliveryOrderResponse);

		return deliveryOrderResponse;
	}

	@Override
	public DeliveryOrder voidDeliveryOrder(@NonNull final DeliveryOrder deliveryOrderRequest)
	{
		logger.trace("Creating delivery order for {}", deliveryOrderRequest);
		final Sendung goRequest = createGODeliveryOrder(deliveryOrderRequest, GOOrderStatus.CANCELLATION);

		final Object goResponseObj = sendAndReceive(
				objectFactory.createGOWebServiceSendungsErstellung(goRequest),
				"voidDeliveryOrder",
				deliveryOrderRequest.getRepoId());
		final SendungsRueckmeldung goResponse = (SendungsRueckmeldung)goResponseObj;
		final DeliveryOrder deliveryOrderResponse = createDeliveryOrderFromResponse(goResponse, deliveryOrderRequest, GOOrderStatus.CANCELLATION);
		logger.trace("Delivery order completed: {}", deliveryOrderResponse);

		return deliveryOrderResponse;
	}

	@Override
	public List<PackageLabels> getPackageLabelsList(@NonNull final DeliveryOrder deliveryOrder) throws ShipperGatewayException
	{
		logger.trace("getPackageLabelsList for {}", deliveryOrder);
		final Sendungsnummern goRequest = objectFactory.createSendungsnummern();
		goRequest.getSendungsnummerAX4().add(deliveryOrder.getOrderId().getOrderIdAsInt());

		final Object goResponseObj = sendAndReceive(
				objectFactory.createGOWebServiceSendungsnummern(goRequest),
				"getPackageLabelsList",
				deliveryOrder.getRepoId());
		if (goResponseObj instanceof Label)
		{
			final Label goLabels = (Label)goResponseObj;
			final List<PackageLabels> packageLabels = createDeliveryPackageLabels(goLabels);
			logger.trace("getPackageLabelsList: got packageLabels={}", packageLabels);
			return packageLabels;
		}
		else if (goResponseObj instanceof Fehlerbehandlung)
		{
			final Fehlerbehandlung errorResponse = (Fehlerbehandlung)goResponseObj;
			throw extractException(errorResponse);
		}
		else
		{
			throw new IllegalArgumentException("Unknown reponse type: " + goResponseObj.getClass());
		}
	}

	private Object sendAndReceive(final JAXBElement<?> goRequestElement,
			final String action, // for logging
			final int deliveryOrderRepoId // for logging
	)
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();
		final GOClientLogEventBuilder logEventBuilder = GOClientLogEvent.builder()
				.jaxbMarshaller(webServiceTemplate.getMarshaller())
				.requestElement(goRequestElement)
				.deliveryOrderRepoId(deliveryOrderRepoId)
				.action(action)
				.config(config);

		try
		{
			final JAXBElement<?> goResponseElement = (JAXBElement<?>)webServiceTemplate.marshalSendAndReceive(goRequestElement);
			goClientLogger.log(logEventBuilder
					.responseElement(goResponseElement)
					.durationMillis(stopwatch.elapsed(TimeUnit.MILLISECONDS))
					.build());

			final Object goResponseObj = goResponseElement.getValue();
			return goResponseObj;
		}
		catch (final Throwable throwable)
		{
			final AdempiereException exception = AdempiereException.wrapIfNeeded(throwable);
			goClientLogger.log(logEventBuilder
					.responseException(exception)
					.durationMillis(stopwatch.elapsed(TimeUnit.MILLISECONDS))
					.build());

			throw exception;

		}
	}

	private ShipperGatewayException extractException(final Fehlerbehandlung errorResponse)
	{
		final Fehlerbehandlung.Fehlermeldungen goErrorMessages = errorResponse.getFehlermeldungen();
		final List<ShipperErrorMessage> shipperErrorMessages = goErrorMessages.getFehler()
				.stream()
				.map(this::createShipperErrorMessage)
				.collect(ImmutableList.toImmutableList());

		return new ShipperGatewayException(shipperErrorMessages);
	}

	private ShipperErrorMessage createShipperErrorMessage(final Fehlerbehandlung.Fehlermeldungen.Fehler goError)
	{
		return ShipperErrorMessage.builder()
				.code(goError.getFehlerNr())
				.message(goError.getBeschreibung())
				.fieldName(goError.getFeld())
				.stackTrace(goError.getStackTrace())
				.build();
	}

	private Sendung createGODeliveryOrder(final DeliveryOrder request, final GOOrderStatus status)
	{
		final OrderId orderId = request.getOrderId();
		final HWBNumber hwbNumber = request.getHwbNumber();

		final Sendung.Abholadresse pickupAddress = createGOPickupAddress(request.getPickupAddress());
		final Sendung.Abholdatum pickupDate = createGOPickupDate(request.getPickupDate());

		final Sendung.Empfaenger deliveryAddress = createGODeliveryAddress(request.getDeliveryAddress(), request.getDeliveryContact());
		final Sendung.SendungsPosition deliveryPosition = createGODeliveryPosition(request.getDeliveryPosition());

		final Sendung goRequest = newGODeliveryRequest();
		goRequest.setStatus(status.getCode()); // Order status
		goRequest.setSendungsnummerAX4(orderId != null ? orderId.getOrderIdAsString() : null); // AX4 shipment no. (n15, mandatory for update and cancellation)
		goRequest.setFrachtbriefnummer(hwbNumber != null ? hwbNumber.getAsString() : null); // HWB no. (N18, not mandatory). If you provide a HWB no. in this field, AX4 will not generate a new HWB no.

		goRequest.setKundenreferenz(request.getCustomerReference()); // Customer reference no (an40, i guess it's not mandatory)
		goRequest.setAbholadresse(pickupAddress); // Pickup address (mandatory)
		goRequest.setAbholdatum(pickupDate); // Pickup date (mandatory)
		goRequest.setAbholhinweise(request.getPickupNote()); // Pickup note (an128, not mandatory)

		goRequest.setEmpfaenger(deliveryAddress); // Delivery address (mandatory)
		goRequest.setZustelldatum(createGODeliveryDateOrNull(request.getDeliveryDate())); // Delivery date (not mandatory)
		goRequest.setZustellhinweise(request.getDeliveryNote()); // Delivery note (an128, not mandatory)

		goRequest.setService(request.getServiceType().getCode()); // Service type (mandatory)
		goRequest.setUnfrei(request.getPaidMode().getCode()); // Flag unpaid (mandatory)
		goRequest.setSelbstanlieferung(request.getSelfDelivery().getCode()); // Flag self delivery (mandatory)
		goRequest.setSelbstabholung(request.getSelfPickup().getCode()); // Flag self pickup (mandatory)
		goRequest.setWarenwert(null); // Value of goods (not mandatory)
		goRequest.setSonderversicherung(null); // Special insurance (not mandatory)
		goRequest.setNachnahme(null); // Cash on delivery (not mandatory)
		goRequest.setTelefonEmpfangsbestaetigung(request.getReceiptConfirmationPhoneNumber()); // Phone no. for confirmation of receipt (an25, mandatory)

		goRequest.setSendungsPosition(deliveryPosition); // Shipment position (mandatory, max. 1)

		return goRequest;
	}

	private Sendung newGODeliveryRequest()
	{
		final Sendung goRequest = objectFactory.createSendung();
		goRequest.setVersender(config.getRequestSenderId());
		goRequest.setBenutzername(config.getRequestUsername());
		return goRequest;
	}

	private Sendung.SendungsPosition createGODeliveryPosition(final DeliveryPosition shipmentPosition)
	{
		final Sendung.SendungsPosition goShipmentPosition = objectFactory.createSendungSendungsPosition();
		goShipmentPosition.setAnzahlPackstuecke(String.valueOf(shipmentPosition.getNumberOfPackages())); // Number of packages (n9, mandatory)
		goShipmentPosition.setGewicht(String.valueOf(shipmentPosition.getGrossWeightKg())); // Package gross weight (n5, mandatory), in Kg
		goShipmentPosition.setInhalt(shipmentPosition.getContent()); // Content (an40, mandatory)

		final PackageDimensions packageDimensions = shipmentPosition.getPackageDimensions();
		if (packageDimensions != null)
		{
			final Sendung.SendungsPosition.Abmessungen goPackageDimensions = objectFactory.createSendungSendungsPositionAbmessungen();
			goPackageDimensions.setLaenge(String.valueOf(packageDimensions.getLengthInCM())); // Length (n6, mandatory), im cm
			goPackageDimensions.setBreite(String.valueOf(packageDimensions.getWidthInCM())); // Width (n6, mandatory), im cm
			goPackageDimensions.setHoehe(String.valueOf(packageDimensions.getHeightInCM())); // Height (n6, mandatory), im cm
			goShipmentPosition.setAbmessungen(goPackageDimensions); // Package dimensions (not mandatory)
		}

		return goShipmentPosition;
	}

	private Sendung.Abholadresse createGOPickupAddress(final Address pickupAddress)
	{
		final Sendung.Abholadresse goPickupAddress = objectFactory.createSendungAbholadresse();
		goPickupAddress.setFirmenname1(pickupAddress.getCompanyName1()); // Name 1 (an60, mandatory)
		goPickupAddress.setFirmenname2(pickupAddress.getCompanyName2()); // Name 2 (an60, not mandatory)
		goPickupAddress.setAbteilung(pickupAddress.getCompanyDepartment()); // Department (an40, not mandatory)
		goPickupAddress.setStrasse1(pickupAddress.getStreet1()); // Street 1 (an35, mandatory)
		goPickupAddress.setHausnummer(pickupAddress.getHouseNo()); // House no. (an10, mandatory in DE)
		goPickupAddress.setStrasse2(pickupAddress.getStreet2()); // Street 2 (an25, not mandatory)
		goPickupAddress.setLand(pickupAddress.getCountry().getAlpha2()); // Country (an3, mandatory) // IMPORTANT: it's alpha2
		goPickupAddress.setPostleitzahl(pickupAddress.getZipCode()); // ZIP code (an9, mandatory)
		goPickupAddress.setStadt(pickupAddress.getCity()); // City (an30, mandatory)

		return goPickupAddress;
	}

	private Sendung.Abholdatum createGOPickupDate(final PickupDate pickupDate)
	{
		final Sendung.Abholdatum goPickupDate = objectFactory.createSendungAbholdatum();

		final String dateStr = pickupDate.getDate().format(dateFormatter);
		goPickupDate.setDatum(dateStr); // Pickup date (TT.MM.JJJJ, mandatory), shall be >= actual date, < delivery date

		final LocalTime timeFrom = pickupDate.getTimeFrom();
		final String timeFromStr = timeFrom != null ? timeFrom.format(timeFormatter) : null;
		goPickupDate.setUhrzeitVon(timeFromStr); // Pickup time - from (hh:mm, not mandatory)

		final LocalTime timeTo = pickupDate.getTimeFrom();
		final String timeToStr = timeTo != null ? timeTo.format(timeFormatter) : null;
		goPickupDate.setUhrzeitBis(timeToStr); // Pickup time - to (hh:mm, not mandatory)

		return goPickupDate;
	}

	private Sendung.Empfaenger createGODeliveryAddress(final Address deliveryAddress, final ContactPerson deliveryContact)
	{
		final Sendung.Empfaenger goDeliveryAddress = objectFactory.createSendungEmpfaenger(); // Consginee address
		goDeliveryAddress.setFirmenname1(deliveryAddress.getCompanyName1()); // Name 1 (an60, mandatory)
		goDeliveryAddress.setFirmenname2(deliveryAddress.getCompanyName2()); // Name 2 (an60, not mandatory)
		goDeliveryAddress.setAbteilung(deliveryAddress.getCompanyDepartment()); // Department (an40, mandatory)
		goDeliveryAddress.setStrasse1(deliveryAddress.getStreet1()); // Street 1 (an35, mandatory)
		goDeliveryAddress.setHausnummer(deliveryAddress.getHouseNo()); // House no. (an10, mandatory in DE)
		goDeliveryAddress.setStrasse2(deliveryAddress.getStreet2()); // Street 2 (an25, not mandatory)
		goDeliveryAddress.setLand(deliveryAddress.getCountry().getAlpha2()); // Country (an3, mandatory) // IMPORTANT: it's alpha2
		goDeliveryAddress.setPostleitzahl(deliveryAddress.getZipCode()); // ZIP code (an9, mandatory)
		goDeliveryAddress.setStadt(deliveryAddress.getCity()); // City (an30, mandatory)

		if (deliveryContact != null)
		{
			final PhoneNumber phone = deliveryContact.getPhone();
			final Sendung.Empfaenger.Ansprechpartner.Telefon goPhone = objectFactory.createSendungEmpfaengerAnsprechpartnerTelefon();
			goPhone.setLaenderPrefix(phone.getCountryCode()); // Country phone area code (n4, mandatory)
			goPhone.setOrtsvorwahl(phone.getAreaCode()); // Area code (n7, mandatory)
			goPhone.setTelefonnummer(phone.getPhoneNumber()); // Phone no. (n10, mandatory)

			final Sendung.Empfaenger.Ansprechpartner goContactPerson = objectFactory.createSendungEmpfaengerAnsprechpartner();
			goContactPerson.setTelefon(goPhone); // Phone (mandatory)
			goDeliveryAddress.setAnsprechpartner(goContactPerson); // Contact person (not mandatory)
		}

		return goDeliveryAddress;
	}

	private Sendung.Zustelldatum createGODeliveryDateOrNull(final DeliveryDate deliveryDate)
	{
		if (deliveryDate == null)
		{
			return null;
		}

		final Sendung.Zustelldatum goDeliveryDate = objectFactory.createSendungZustelldatum();

		final String dateStr = deliveryDate.getDate().format(dateFormatter);
		goDeliveryDate.setDatum(dateStr); // Delivery date (TT.MM.JJJJ, mandatory)

		final LocalTime timeFrom = deliveryDate.getTimeFrom();
		final String timeFromStr = timeFrom != null ? timeFrom.format(timeFormatter) : null;
		goDeliveryDate.setUhrzeitVon(timeFromStr); // Delivery time - from (hh:mm, not mandatory)

		final LocalTime timeTo = deliveryDate.getTimeFrom();
		final String timeToStr = timeTo != null ? timeTo.format(timeFormatter) : null;
		goDeliveryDate.setUhrzeitBis(timeToStr); // Delivery time - to (hh:mm, not mandatory)

		return goDeliveryDate;
	}

	private DeliveryOrder createDeliveryOrderFromResponse(
			final SendungsRueckmeldung goResponse,
			final DeliveryOrder deliveryOrderRequest,
			final GOOrderStatus newStatus)
	{
		final SendungsRueckmeldung.Sendung goResponseContent = goResponse.getSendung();

		// NOTE: based on protocol v1.3 i understand there will be only one position, always
		final List<SendungsRueckmeldung.Sendung.Position> goResponseDeliveryPositions = goResponseContent.getPosition();
		if (goResponseDeliveryPositions.size() != 1)
		{
			throw new ShipperGatewayException("Only one delivery position was expected but got " + goResponseDeliveryPositions);
		}
		final SendungsRueckmeldung.Sendung.Position goResponseDeliveryPosition = goResponseDeliveryPositions.get(0);

		return deliveryOrderRequest.toBuilder()
				.orderId(GOUtils.createOrderId(goResponseContent.getSendungsnummerAX4()))
				.hwbNumber(HWBNumber.of(goResponseContent.getFrachtbriefnummer()))
				.orderStatus(newStatus)
				// .serviceType(deliveryOrderRequest.getServiceType())
				// .paidMode(deliveryOrderRequest.getPaidMode())

				//
				// Pickup
				// .pickupAddress(deliveryOrderRequest.getPickupAddress())
				.pickupDate(PickupDate.builder()
						.date(parseLocalDate(goResponseContent.getAbholdatum()))
						.build())
				// .pickupNote(deliveryOrderRequest.getPickupNote())
				// .selfPickup(deliveryOrderRequest.getSelfPickup())

				//
				// Delivery
				// .deliveryAddress(deliveryOrderRequest.getDeliveryAddress())
				// .deliveryContact(deliveryOrderRequest.getDeliveryContact())
				.deliveryDate(DeliveryDate.builder()
						.date(parseLocalDate(goResponseContent.getZustelldatum()))
						.timeFrom(parseLocalTime(goResponseContent.getZustellUhrzeitVon()))
						.timeTo(parseLocalTime(goResponseContent.getZustellUhrzeitBis()))
						.build())
				// .deliveryNote(deliveryOrderRequest.getDeliveryNote())
				// .selfDelivery(deliveryOrderRequest.getSelfDelivery())
				// .customerReference(deliveryOrderRequest.getCustomerReference())

				//
				// Delivery content
				.deliveryPosition(deliveryOrderRequest.getDeliveryPosition().toBuilder()
						// .positionNo(goDeliveryPosition.getPositionsNr()) // assume it's always 1
						.numberOfPackages(Integer.parseInt(goResponseDeliveryPosition.getAnzahlPackstuecke()))
						// .barcodes(goDeliveryPosition.getBarcodes().getBarcodeNr())
						.build())
				//
				// .receiptConfirmationPhoneNumber(deliveryOrderRequest.getReceiptConfirmationPhoneNumber())
				.build();
	}

	private static LocalDate parseLocalDate(final String dateStr)
	{
		return !Check.isEmpty(dateStr, true) ? LocalDate.parse(dateStr, dateFormatter) : null;
	}

	private static LocalTime parseLocalTime(final String timeStr)
	{
		return !Check.isEmpty(timeStr, true) ? LocalTime.parse(timeStr, timeFormatter) : null;
	}

	private List<PackageLabels> createDeliveryPackageLabels(final Label goLabels)
	{
		return goLabels.getSendung()
				.stream()
				.map(goPackageLabels -> createPackageLabels(goPackageLabels))
				.collect(ImmutableList.toImmutableList());
	}

	private PackageLabels createPackageLabels(final Label.Sendung goPackageLabels)
	{
		final Label.Sendung.PDFs pdfs = goPackageLabels.getPDFs();

		return PackageLabels.builder()
				.orderId(GOUtils.createOrderId(goPackageLabels.getSendungsnummerAX4()))
				.hwbNumber(HWBNumber.of(goPackageLabels.getFrachtbriefnummer()))
				.defaultLabelType(GOPackageLabelType.DIN_A6_ROUTER_LABEL)
				.label(PackageLabel.builder()
						.type(GOPackageLabelType.DIN_A4_HWB)
						.contentType(PackageLabel.CONTENTTYPE_PDF)
						.labelData(pdfs.getFrachtbrief())
						.build())
				.label(PackageLabel.builder()
						.type(GOPackageLabelType.DIN_A6_ROUTER_LABEL)
						.contentType(PackageLabel.CONTENTTYPE_PDF)
						.labelData(pdfs.getRouterlabel())
						.build())
				.label(PackageLabel.builder()
						.type(GOPackageLabelType.DIN_A6_ROUTER_LABEL_ZEBRA)
						.contentType(PackageLabel.CONTENTTYPE_PDF)
						.labelData(pdfs.getRouterlabelZebra())
						.build())
				.build();
	}
}
