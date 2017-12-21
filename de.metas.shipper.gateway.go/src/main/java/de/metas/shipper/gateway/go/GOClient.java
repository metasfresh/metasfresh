package de.metas.shipper.gateway.go;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.shipper.gateway.api.ShipperGatewayClient;
import de.metas.shipper.gateway.api.model.Address;
import de.metas.shipper.gateway.api.model.ContactPerson;
import de.metas.shipper.gateway.api.model.CreatePickupOrderRequest;
import de.metas.shipper.gateway.api.model.DeliveryPosition;
import de.metas.shipper.gateway.api.model.PackageDimensions;
import de.metas.shipper.gateway.api.model.PhoneNumber;
import de.metas.shipper.gateway.api.model.PickupDate;
import de.metas.shipper.gateway.api.model.PickupOrderResponse;
import de.metas.shipper.gateway.api.model.PickupOrderResponse.ResponseDeliveryPosition;
import de.metas.shipper.gateway.go.schema.GOOrderStatus;
import de.metas.shipper.gateway.go.schema.ObjectFactory;
import de.metas.shipper.gateway.go.schema.Sendung;
import de.metas.shipper.gateway.go.schema.Sendung.Abholadresse;
import de.metas.shipper.gateway.go.schema.Sendung.Abholdatum;
import de.metas.shipper.gateway.go.schema.Sendung.Empfaenger;
import de.metas.shipper.gateway.go.schema.Sendung.Empfaenger.Ansprechpartner;
import de.metas.shipper.gateway.go.schema.Sendung.Empfaenger.Ansprechpartner.Telefon;
import de.metas.shipper.gateway.go.schema.Sendung.SendungsPosition;
import de.metas.shipper.gateway.go.schema.Sendung.SendungsPosition.Abmessungen;
import de.metas.shipper.gateway.go.schema.SendungsRueckmeldung;

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

public class GOClient extends WebServiceGatewaySupport implements ShipperGatewayClient
{
	private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

	private final ObjectFactory objectFactory = new ObjectFactory();

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("url", getDefaultUri())
				.toString();
	}

	@Override
	public PickupOrderResponse createPickupOrder(final CreatePickupOrderRequest request)
	{
		final Sendung goRequest = createGOPickupRequest(request);
		final SendungsRueckmeldung goResponse = (SendungsRueckmeldung)getWebServiceTemplate().marshalSendAndReceive(objectFactory.createGOWebServiceSendungsErstellung(goRequest));
		final PickupOrderResponse response = createPickupOrderResponse(goResponse);
		return response;
	}

	private Sendung createGOPickupRequest(final CreatePickupOrderRequest request)
	{
		final Abholadresse pickupAddress = createGOPickupAddress(request.getPickupAddress());
		final Abholdatum pickupDate = createGOPickupDate(request.getPickupDate());

		final Empfaenger deliveryAddress = createGODeliveryAddress(request.getDeliveryAddress(), request.getDeliveryContact());
		final SendungsPosition deliveryPosition = createGODeliveryPosition(request.getDeliveryPosition());
		final Sendung goRequest = objectFactory.createSendung();

		goRequest.setSendungsnummerAX4(null); // AX4 shipment no. (n15, mandatory for update and cancellation)
		goRequest.setFrachtbriefnummer(null); // HWB no. (N18, not mandatory). If you provide a HWB no. in this field, AX4 will not generate a new HWB no.
		goRequest.setVersender(null); // AX4 ID shipper (n30, not mandatory). This is the AX4 ID to be booked for this order
		goRequest.setStatus(GOOrderStatus.NEW.getCode()); // Order status

		goRequest.setKundenreferenz(null); // Customer reference no (an40, i guess it's not mandatory)
		goRequest.setAbholadresse(pickupAddress); // Pickup address (mandatory)
		goRequest.setAbholdatum(pickupDate); // Pickup date (mandatory)

		goRequest.setEmpfaenger(deliveryAddress); // Delivery address (mandatory)
		goRequest.setZustelldatum(null); // Delivery date (not mandatory)

		goRequest.setService(request.getServiceType().getCode()); // Service type (mandatory)
		goRequest.setUnfrei(request.getPaidMode().getCode()); // Flag unpaid (mandatory)
		goRequest.setSelbstanlieferung(request.getSelfDelivery().getCode()); // Flag self delivery (mandatory)
		goRequest.setSelbstabholung(request.getSelfPickup().getCode()); // Flag self pickup (mandatory)
		goRequest.setWarenwert(null); // Value of goods (not mandatory)
		goRequest.setSonderversicherung(null); // Special insurance (not mandatory)
		goRequest.setNachnahme(null); // Cash on delivery (not mandatory)
		goRequest.setAbholhinweise(null); // Pickup note (an128, not mandatory)
		goRequest.setZustellhinweise(null); // Delivery note (an128, not mandatory)
		goRequest.setTelefonEmpfangsbestaetigung(request.getReceiptConfirmationPhoneNumber()); // Phone no. for confirmation of receipt (an25, mandatory)

		goRequest.setSendungsPosition(deliveryPosition); // Shipment position (mandatory, max. 1)
		return goRequest;
	}

	private SendungsPosition createGODeliveryPosition(final DeliveryPosition shipmentPosition)
	{
		final SendungsPosition goShipmentPosition = objectFactory.createSendungSendungsPosition();
		goShipmentPosition.setAnzahlPackstuecke(String.valueOf(shipmentPosition.getNumberOfPackages())); // Number of packages (n9, mandatory)
		goShipmentPosition.setGewicht(String.valueOf(shipmentPosition.getGrossWeightKg())); // Package gross weight (n5, mandatory), in Kg
		goShipmentPosition.setInhalt(shipmentPosition.getContent()); // Content (an40, mandatory)

		final PackageDimensions packageDimensions = shipmentPosition.getPackageDimensions();
		if (packageDimensions != null)
		{
			final Abmessungen goPackageDimensions = objectFactory.createSendungSendungsPositionAbmessungen();
			goPackageDimensions.setLaenge(String.valueOf(packageDimensions.getLengthInCM())); // Length (n6, mandatory), im cm
			goPackageDimensions.setBreite(String.valueOf(packageDimensions.getWidthInCM())); // Width (n6, mandatory), im cm
			goPackageDimensions.setHoehe(String.valueOf(packageDimensions.getHeightInCM())); // Height (n6, mandatory), im cm
			goShipmentPosition.setAbmessungen(goPackageDimensions); // Package dimensions (not mandatory)
		}

		return goShipmentPosition;
	}

	private Abholadresse createGOPickupAddress(final Address pickupAddress)
	{
		final Abholadresse goPickupAddress = objectFactory.createSendungAbholadresse();
		goPickupAddress.setFirmenname1(pickupAddress.getCompanyName1()); // Name 1 (an60, mandatory)
		goPickupAddress.setFirmenname2(pickupAddress.getCompanyName2()); // Name 2 (an60, not mandatory)
		goPickupAddress.setAbteilung(pickupAddress.getCompanyDepartment()); // Department (an40, not mandatory)
		goPickupAddress.setStrasse1(pickupAddress.getStreet1()); // Street 1 (an35, mandatory)
		goPickupAddress.setHausnummer(pickupAddress.getHouseNo()); // House no. (an10, mandatory in DE)
		goPickupAddress.setStrasse2(pickupAddress.getStreet2()); // Street 2 (an25, not mandatory)
		goPickupAddress.setLand(pickupAddress.getCountry().getAlpha3()); // Country (an3, mandatory)
		goPickupAddress.setPostleitzahl(pickupAddress.getZipCode()); // ZIP code (an9, mandatory)
		goPickupAddress.setStadt(pickupAddress.getCity()); // City (an30, mandatory)

		return goPickupAddress;
	}

	private Abholdatum createGOPickupDate(final PickupDate pickupDate)
	{
		final Abholdatum goPickupDate = objectFactory.createSendungAbholdatum();

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

	private Empfaenger createGODeliveryAddress(final Address deliveryAddress, final ContactPerson deliveryContact)
	{
		final Empfaenger goDeliveryAddress = objectFactory.createSendungEmpfaenger(); // Consginee address
		goDeliveryAddress.setFirmenname1(deliveryAddress.getCompanyName1()); // Name 1 (an60, mandatory)
		goDeliveryAddress.setFirmenname2(deliveryAddress.getCompanyName2()); // Name 2 (an60, not mandatory)
		goDeliveryAddress.setAbteilung(deliveryAddress.getCompanyDepartment()); // Department (an40, not mandatory)
		goDeliveryAddress.setStrasse1(deliveryAddress.getStreet1()); // Street 1 (an35, mandatory)
		goDeliveryAddress.setHausnummer(deliveryAddress.getHouseNo()); // House no. (an10, mandatory in DE)
		goDeliveryAddress.setStrasse2(deliveryAddress.getStreet2()); // Street 2 (an25, not mandatory)
		goDeliveryAddress.setLand(deliveryAddress.getCountry().getAlpha3()); // Country (an3, mandatory)
		goDeliveryAddress.setPostleitzahl(deliveryAddress.getZipCode()); // ZIP code (an9, mandatory)
		goDeliveryAddress.setStadt(deliveryAddress.getCity()); // City (an30, mandatory)

		if (deliveryContact != null)
		{
			final PhoneNumber phone = deliveryContact.getPhone();
			final Telefon goPhone = objectFactory.createSendungEmpfaengerAnsprechpartnerTelefon();
			goPhone.setLaenderPrefix(phone.getCountryCode()); // Country phone area code (n4, mandatory)
			goPhone.setOrtsvorwahl(phone.getAreaCode()); // Area code (n7, mandatory)
			goPhone.setTelefonnummer(phone.getPhoneNumber()); // Phone no. (n10, mandatory)

			final Ansprechpartner goContactPerson = objectFactory.createSendungEmpfaengerAnsprechpartner();
			goContactPerson.setTelefon(goPhone); // Phone (mandatory)
			goDeliveryAddress.setAnsprechpartner(goContactPerson); // Contact person (not mandatory)
		}

		return goDeliveryAddress;
	}

	private PickupOrderResponse createPickupOrderResponse(SendungsRueckmeldung goResponse)
	{
		final SendungsRueckmeldung.Sendung goResponseContent = goResponse.getSendung();

		final List<ResponseDeliveryPosition> deliveryPositions = goResponseContent.getPosition()
				.stream()
				.map(goDeliveryPosition -> PickupOrderResponse.ResponseDeliveryPosition.builder()
						.positionNo(goDeliveryPosition.getPositionsNr())
						.numberOfPackages(goDeliveryPosition.getAnzahlPackstuecke())
						.barcodes(goDeliveryPosition.getBarcodes().getBarcodeNr())
						.build())
				.collect(ImmutableList.toImmutableList());

		return PickupOrderResponse.builder()
				.orderId(goResponseContent.getSendungsnummerAX4())
				.hwbNumber(goResponseContent.getFrachtbriefnummer())
				.pickupDate(goResponseContent.getAbholdatum())
				.note(goResponseContent.getHinweis())
				.deliveryPositions(deliveryPositions)
				.build();
	}
}
