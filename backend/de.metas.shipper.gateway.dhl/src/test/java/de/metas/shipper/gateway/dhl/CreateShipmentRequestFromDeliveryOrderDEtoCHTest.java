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

import de.dhl.webservice.cisbase.AuthentificationType;
import de.dhl.webservice.cisbase.CommunicationType;
import de.dhl.webservice.cisbase.CountryType;
import de.dhl.webservice.cisbase.NameType;
import de.dhl.webservice.cisbase.NativeAddressType;
import de.dhl.webservice.cisbase.ReceiverNativeAddressType;
import de.dhl.webservices.businesscustomershipping._3.CreateShipmentOrderRequest;
import de.dhl.webservices.businesscustomershipping._3.CreateShipmentOrderResponse;
import de.dhl.webservices.businesscustomershipping._3.CreationState;
import de.dhl.webservices.businesscustomershipping._3.ExportDocumentType;
import de.dhl.webservices.businesscustomershipping._3.ObjectFactory;
import de.dhl.webservices.businesscustomershipping._3.ReceiverType;
import de.dhl.webservices.businesscustomershipping._3.Serviceconfiguration;
import de.dhl.webservices.businesscustomershipping._3.ShipmentDetailsTypeType;
import de.dhl.webservices.businesscustomershipping._3.ShipmentItemType;
import de.dhl.webservices.businesscustomershipping._3.ShipmentNotificationType;
import de.dhl.webservices.businesscustomershipping._3.ShipmentOrderType;
import de.dhl.webservices.businesscustomershipping._3.ShipperType;
import de.dhl.webservices.businesscustomershipping._3.Version;
import de.metas.shipper.gateway.spi.model.Address;
import de.metas.shipper.gateway.spi.model.ContactPerson;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryPosition;
import de.metas.shipper.gateway.spi.model.PackageDimensions;
import de.metas.shipper.gateway.spi.model.PickupDate;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled("makes ACTUAL calls to dhl api and needs auth")
class CreateShipmentRequestFromDeliveryOrderDEtoCHTest
{

	private static final String USER_NAME = "a";
	private static final String PASSWORD = "b";

	private WebServiceTemplate webServiceTemplate;
	private de.dhl.webservice.cisbase.ObjectFactory objectFactoryCis;
	private ObjectFactory objectFactory;

	@BeforeEach
	private void beforeEach()
	{
		webServiceTemplate = createWebServiceTemplate();
		objectFactoryCis = new de.dhl.webservice.cisbase.ObjectFactory();
		objectFactory = new ObjectFactory();

	}

	@Test
	void createShipmentOrderFromDeliveryOrder()
	{
		final CreateShipmentOrderRequest createShipmentOrderRequest = createShipmentOrderRequestFromDeliveryOrder();

		// execute the actual request
		final CreateShipmentOrderResponse createShipmentOrderResponse = (CreateShipmentOrderResponse)webServiceTemplate.marshalSendAndReceive(createShipmentOrderRequest, new AddSoapHeader());

		assertEquals(BigInteger.ZERO, createShipmentOrderResponse.getStatus().getStatusCode());
		assertEquals(1, createShipmentOrderResponse.getCreationState().size());

		final CreationState creationState = createShipmentOrderResponse.getCreationState().get(0);
		assertEquals("1", creationState.getSequenceNumber());
		assertTrue(creationState.getLabelData().getLabelUrl().startsWith("https://"));
		assertTrue(creationState.getLabelData().getExportLabelUrl().startsWith("https://"));
	}

	private CreateShipmentOrderRequest createShipmentOrderRequestFromDeliveryOrder()
	{
		final DeliveryOrder request = DhlTestHelper.createDummyDeliveryOrderDEtoCH();
		final ShipmentOrderType shipmentOrderType = createShipmentFromDeliveryOrder(request);

		final Version version = createHardcodedVersion(objectFactory);

		final CreateShipmentOrderRequest createShipmentOrderRequest = objectFactory.createCreateShipmentOrderRequest();
		createShipmentOrderRequest.setVersion(version);
		createShipmentOrderRequest.getShipmentOrder().add(shipmentOrderType);

		return createShipmentOrderRequest;
	}

	/**
	 * Create the (2) ShipmentOrderType
	 */
	@NonNull
	private ShipmentOrderType createShipmentFromDeliveryOrder(final DeliveryOrder deliveryOrder)
	{
		// (2.2)  the shipment
		final ShipmentOrderType.Shipment shipmentOrderTypeShipment = objectFactory.createShipmentOrderTypeShipment();

		final ContactPerson deliveryContact = deliveryOrder.getDeliveryContact();

		{
			// (2.2.1) Shipment Details
			final PickupDate pickupDate = deliveryOrder.getPickupDate();
			final DeliveryPosition deliveryPosition = deliveryOrder.getDeliveryPositions().get(0);

			final ShipmentDetailsTypeType shipmentDetailsTypeType = objectFactory.createShipmentDetailsTypeType();
			shipmentDetailsTypeType.setProduct(deliveryOrder.getShipperProduct().getCode());
			shipmentDetailsTypeType.setAccountNumber("22222222225301"); // special account number, depending on target country. why dhl why?????
			//noinspection ConstantConditions
			shipmentDetailsTypeType.setCustomerReference(deliveryOrder.getCustomerReference());
			shipmentDetailsTypeType.setShipmentDate(pickupDate.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
			// (2.2.1.8)
			final PackageDimensions packageDimensions = deliveryPosition.getPackageDimensions();
			assertNotNull(packageDimensions);
			final ShipmentItemType shipmentItemType = objectFactory.createShipmentItemType();
			shipmentItemType.setHeightInCM(BigInteger.valueOf(packageDimensions.getHeightInCM()));
			shipmentItemType.setLengthInCM(BigInteger.valueOf(packageDimensions.getLengthInCM()));
			shipmentItemType.setWidthInCM(BigInteger.valueOf(packageDimensions.getWidthInCM()));
			shipmentItemType.setWeightInKG(BigDecimal.valueOf(deliveryPosition.getGrossWeightKg()));
			shipmentDetailsTypeType.setShipmentItem(shipmentItemType);
			// (2.2.1.10)
			final ShipmentNotificationType shipmentNotificationType = objectFactory.createShipmentNotificationType();
			//noinspection ConstantConditions
			shipmentNotificationType.setRecipientEmailAddress(deliveryContact != null ? deliveryContact.getEmailAddress() : null);
			shipmentDetailsTypeType.setNotification(shipmentNotificationType);
			shipmentOrderTypeShipment.setShipmentDetails(shipmentDetailsTypeType);
		}

		{
			// (2.2.4) Receiver aka Delivery
			final Address deliveryAddress = deliveryOrder.getDeliveryAddress();

			final ReceiverType receiverType = objectFactory.createReceiverType();
			receiverType.setName1(deliveryAddress.getCompanyName1()); // where do i find this name in deliveryOrder?
			// (2.2.4.2)
			final ReceiverNativeAddressType receiverNativeAddressType = objectFactoryCis.createReceiverNativeAddressType();
			receiverNativeAddressType.setStreetName(deliveryAddress.getStreet1());
			receiverNativeAddressType.getAddressAddition().add(deliveryAddress.getStreet2());
			receiverNativeAddressType.setStreetNumber(deliveryAddress.getHouseNo());
			receiverNativeAddressType.setZip(deliveryAddress.getZipCode());
			receiverNativeAddressType.setCity(deliveryAddress.getCity());
			// (2.2.4.2.10)
			final CountryType countryType = objectFactoryCis.createCountryType();
			countryType.setCountryISOCode(deliveryAddress.getCountry().getAlpha2());
			receiverNativeAddressType.setOrigin(countryType);
			receiverType.setAddress(receiverNativeAddressType);
			// (2.2.4.2)
			final CommunicationType communicationType = objectFactoryCis.createCommunicationType();
			//noinspection ConstantConditions
			communicationType.setEmail(deliveryContact != null ? deliveryContact.getEmailAddress() : null);
			//noinspection ConstantConditions
			communicationType.setPhone(deliveryContact != null ? deliveryContact.getPhoneAsStringOrNull() : null);
			receiverType.setCommunication(communicationType);

			shipmentOrderTypeShipment.setReceiver(receiverType);
		}

		{
			// (2.2.2) Shipper aka Pickup
			final Address pickupAddress = deliveryOrder.getPickupAddress();
			//			deliveryOrder.getPickupNote() // todo what is a pickup note?

			final ShipperType shipperType = objectFactory.createShipperType();
			// (2.2.2.1)
			final NameType nameType = objectFactoryCis.createNameType();
			nameType.setName1("TheBestPessimist");
			shipperType.setName(nameType);
			// (2.2.2.2)
			final NativeAddressType nativeAddressType = objectFactoryCis.createNativeAddressType();
			nativeAddressType.setStreetName(pickupAddress.getStreet1());
			nativeAddressType.getAddressAddition().add(pickupAddress.getStreet2());
			nativeAddressType.setStreetNumber(pickupAddress.getHouseNo());
			nativeAddressType.setZip(pickupAddress.getZipCode());
			nativeAddressType.setCity(pickupAddress.getCity());
			// (2.2.2.2.8)
			final CountryType countryType = objectFactoryCis.createCountryType();
			countryType.setCountryISOCode(pickupAddress.getCountry().getAlpha2());
			nativeAddressType.setOrigin(countryType);
			shipperType.setAddress(nativeAddressType);

			shipmentOrderTypeShipment.setShipper(shipperType);
		}

		{
			// (2.2.6) Export Document - only for international shipments
			final ExportDocumentType exportDocumentType = objectFactory.createExportDocumentType();
			exportDocumentType.setExportType("OTHER");
			exportDocumentType.setExportTypeDescription("Sales of Goods");
			exportDocumentType.setPlaceOfCommital("Bonn");
			exportDocumentType.setAdditionalFee(BigDecimal.valueOf(0));

			// (2.2.6.9)
			final Serviceconfiguration serviceconfiguration = objectFactory.createServiceconfiguration();
			serviceconfiguration.setActive("0");
			exportDocumentType.setWithElectronicExportNtfctn(serviceconfiguration);
			// (2.2.6.10)
			final ExportDocumentType.ExportDocPosition docPosition = objectFactory.createExportDocumentTypeExportDocPosition();
			docPosition.setDescription("doc_customs_description");
			docPosition.setCountryCodeOrigin("DE");
			docPosition.setCustomsTariffNumber("960340");
			docPosition.setAmount(BigInteger.valueOf(1));
			docPosition.setNetWeightInKG(BigDecimal.valueOf(0.5)); // must be less than the weight!!
			docPosition.setCustomsValue(BigDecimal.valueOf(1));
			exportDocumentType.getExportDocPosition().add(docPosition);

			shipmentOrderTypeShipment.setExportDocument(exportDocumentType);
		}

		// (2) create the need shipment order type
		final ShipmentOrderType shipmentOrderType = objectFactory.createShipmentOrderType();
		shipmentOrderType.setSequenceNumber("1");
		shipmentOrderType.setShipment(shipmentOrderTypeShipment);
		return shipmentOrderType;

	}

	@NonNull
	private static Version createHardcodedVersion(final ObjectFactory objectFactory)
	{
		final Version version = objectFactory.createVersion();
		version.setMajorRelease("3");
		version.setMinorRelease("0");
		return version;
	}

	@NonNull
	private WebServiceTemplate createWebServiceTemplate()
	{
		final HttpComponentsMessageSender messageSender = createMessageSenderWithCIGAuthentication(USER_NAME, PASSWORD);

		final Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setPackagesToScan(
				de.dhl.webservice.cisbase.ObjectFactory.class.getPackage().getName(),
				ObjectFactory.class.getPackage().getName()
		);

		final WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
		webServiceTemplate.setDefaultUri("https://cig.dhl.de/services/sandbox/soap");
		webServiceTemplate.setMessageSender(messageSender);
		webServiceTemplate.setMarshaller(marshaller);
		webServiceTemplate.setUnmarshaller(marshaller);
		return webServiceTemplate;
	}

	@SuppressWarnings("SameParameterValue")
	private static HttpComponentsMessageSender createMessageSenderWithCIGAuthentication(final String authUsername, final String authPassword)
	{
		// cig authentication (basic http authentication) required @ each request: https://entwickler.dhl.de/en/group/ep/authentifizierung
		final UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(authUsername, authPassword);

		final HttpComponentsMessageSender messageSender = new HttpComponentsMessageSender();
		messageSender.setCredentials(credentials);
		try
		{
			messageSender.afterPropertiesSet(); // to make sure credentials are set to HttpClient
		}
		catch (final Exception ex)
		{
			throw AdempiereException.wrapIfNeeded(ex);
		}
		return messageSender;
	}

	private class AddSoapHeader implements WebServiceMessageCallback
	{
		// thx to https://www.devglan.com/spring-mvc/custom-header-in-spring-soap-request
		// for the SoapHeader reference
		@Override
		public void doWithMessage(final WebServiceMessage message)
		{
			try
			{
				// service authentication
				final AuthentificationType authentificationType = objectFactoryCis.createAuthentificationType();
				authentificationType.setUser("2222222222_01");
				authentificationType.setSignature("pass");
				final JAXBElement<AuthentificationType> authentification = objectFactoryCis.createAuthentification(authentificationType);

				// add the authentication to header
				final SoapMessage soapMessage = (SoapMessage)message;
				final SoapHeader header = soapMessage.getSoapHeader();

				soapMessage.setSoapAction("urn:createShipmentOrder");
				final JAXBContext context = JAXBContext.newInstance(AuthentificationType.class);
				final Marshaller marshaller = context.createMarshaller();
				marshaller.marshal(authentification, header.getResult());
			}
			catch (final JAXBException e)
			{
				throw new AdempiereException("Error while setting soap header");
			}
		}
	}
}

