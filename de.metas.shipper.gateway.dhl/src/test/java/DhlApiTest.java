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

import de.dhl.webservice.cisbase.AuthentificationType;
import de.dhl.webservice.cisbase.CommunicationType;
import de.dhl.webservice.cisbase.CountryType;
import de.dhl.webservice.cisbase.NameType;
import de.dhl.webservice.cisbase.NativeAddressType;
import de.dhl.webservice.cisbase.ReceiverNativeAddressType;
import de.dhl.webservices.businesscustomershipping._3.CreateShipmentOrderRequest;
import de.dhl.webservices.businesscustomershipping._3.CreateShipmentOrderResponse;
import de.dhl.webservices.businesscustomershipping._3.GetVersionResponse;
import de.dhl.webservices.businesscustomershipping._3.ObjectFactory;
import de.dhl.webservices.businesscustomershipping._3.ReceiverType;
import de.dhl.webservices.businesscustomershipping._3.ShipmentDetailsTypeType;
import de.dhl.webservices.businesscustomershipping._3.ShipmentItemType;
import de.dhl.webservices.businesscustomershipping._3.ShipmentNotificationType;
import de.dhl.webservices.businesscustomershipping._3.ShipmentOrderType;
import de.dhl.webservices.businesscustomershipping._3.ShipperType;
import de.dhl.webservices.businesscustomershipping._3.Version;
import de.metas.shipper.gateway.spi.model.CountryCode;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled("makes ACTUAL calls to dhl api and needs auth")
class DhlApiTest
{

	private static final CountryCode COUNTRY_CODE_DE = CountryCode.builder().alpha2("DE").alpha3("DEU").build();
	private static final String USER_NAME = "a";
	private static final String PASSWORD = "b";

	private WebServiceTemplate webServiceTemplate;
	private de.dhl.webservice.cisbase.ObjectFactory objectFactoryCis;
	private de.dhl.webservices.businesscustomershipping._3.ObjectFactory objectFactory;

	@BeforeEach
	private void beforeEach()
	{
		webServiceTemplate = createWebServiceTemplate();
		objectFactoryCis = new de.dhl.webservice.cisbase.ObjectFactory();
		objectFactory = new de.dhl.webservices.businesscustomershipping._3.ObjectFactory();

	}

	@Test
	void callGetVersion()
	{
		final Version versionRequest = objectFactory.createVersion();

		// execute the actual request
		final GetVersionResponse versionResponse = (GetVersionResponse)webServiceTemplate.marshalSendAndReceive(versionRequest);

		final Version version = versionResponse.getVersion();
		assertEquals("3", version.getMajorRelease());
		assertEquals("0", version.getMinorRelease());
		assertEquals("0", version.getBuild());
	}

	@Test
	void createShipmentOrderWithHardcodedData()
	{
		final CreateShipmentOrderRequest createShipmentOrderRequest = createShipmentOrderRequestWithHardcodedData();

		// execute the actual request
		final CreateShipmentOrderResponse createShipmentOrderResponse = (CreateShipmentOrderResponse)webServiceTemplate.marshalSendAndReceive(createShipmentOrderRequest, new AddSoapHeader());

		assertEquals(BigInteger.ZERO, createShipmentOrderResponse.getStatus().getStatusCode());
	}

	private CreateShipmentOrderRequest createShipmentOrderRequestWithHardcodedData()
	{
		// (2.2)  the shipment
		final ShipmentOrderType.Shipment shipmentOrderTypeShipment = objectFactory.createShipmentOrderTypeShipment();

		{
			// (2.2.1) shipment details
			final ShipmentDetailsTypeType shipmentDetailsTypeType = objectFactory.createShipmentDetailsTypeType();
			// todo make enum from this
			shipmentDetailsTypeType.setProduct("V01PAK");
			// todo how to get DHL account numbers??
			shipmentDetailsTypeType.setAccountNumber("22222222220104");
			// todo this is PO reference
			shipmentDetailsTypeType.setCustomerReference("the helpful customer reference");
			shipmentDetailsTypeType.setShipmentDate(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
			shipmentDetailsTypeType.setCostCentre("what is a cost center?");
			// (2.2.1.8)
			final ShipmentItemType shipmentItemType = objectFactory.createShipmentItemType();
			shipmentItemType.setHeightInCM(BigInteger.TEN);
			shipmentItemType.setLengthInCM(BigInteger.TEN);
			shipmentItemType.setWidthInCM(BigInteger.TEN);
			shipmentItemType.setWeightInKG(BigDecimal.TEN);
			shipmentDetailsTypeType.setShipmentItem(shipmentItemType);
			// (2.2.1.10)
			final ShipmentNotificationType shipmentNotificationType = objectFactory.createShipmentNotificationType();
			shipmentNotificationType.setRecipientEmailAddress("cristian.pasat@metasfresh.com");
			shipmentDetailsTypeType.setNotification(shipmentNotificationType);
			shipmentOrderTypeShipment.setShipmentDetails(shipmentDetailsTypeType);
		}

		{
			// (2.2.4) receiver
			final ReceiverType receiverType = objectFactory.createReceiverType();
			receiverType.setName1("DHL Packet gmbh"); // todo where do i find this name in deliveryOrder?
			// (2.2.4.2)
			final ReceiverNativeAddressType receiverNativeAddressType = objectFactoryCis.createReceiverNativeAddressType();
			receiverNativeAddressType.setStreetName("Charles-de-Gaulle-Str.");
			receiverNativeAddressType.setStreetNumber("20");
			receiverNativeAddressType.setZip("53113");
			receiverNativeAddressType.setCity("Bonn");
			// (2.2.4.2.10)
			final CountryType countryType = objectFactoryCis.createCountryType();
			countryType.setCountryISOCode(COUNTRY_CODE_DE.getAlpha2());
			receiverNativeAddressType.setOrigin(countryType);
			receiverType.setAddress(receiverNativeAddressType);

			shipmentOrderTypeShipment.setReceiver(receiverType);
		}

		{
			// (2.2.2) Shipper
			final ShipperType shipperType = objectFactory.createShipperType();
			// (2.2.2.1)
			final NameType nameType = objectFactoryCis.createNameType();
			nameType.setName1("TheBestPessimist Inc.");
			shipperType.setName(nameType);
			// (2.2.2.2)
			final NativeAddressType nativeAddressType = objectFactoryCis.createNativeAddressType();
			nativeAddressType.setStreetName("Eduard-Otto-Straße");
			nativeAddressType.setStreetNumber("10");
			nativeAddressType.setZip("53129");
			nativeAddressType.setCity("Bonn");
			// (2.2.2.2.8)
			final CountryType countryType = objectFactoryCis.createCountryType();
			countryType.setCountryISOCode(COUNTRY_CODE_DE.getAlpha2());
			nativeAddressType.setOrigin(countryType);
			shipperType.setAddress(nativeAddressType);
			// (2.2.2.3)
			final CommunicationType communicationType = objectFactoryCis.createCommunicationType();
			communicationType.setPhone("012345689");
			communicationType.setContactPerson("gigi"); // optional
			communicationType.setEmail("tbp@tbp.com");
			shipperType.setCommunication(communicationType);
			shipmentOrderTypeShipment.setShipper(shipperType);
		}

		// (2) create the need shipment order type
		final ShipmentOrderType shipmentOrderType = objectFactory.createShipmentOrderType();
		shipmentOrderType.setSequenceNumber("1");
		shipmentOrderType.setShipment(shipmentOrderTypeShipment);

		final Version version = createHardcodedVersion(objectFactory);

		final CreateShipmentOrderRequest createShipmentOrderRequest = objectFactory.createCreateShipmentOrderRequest();
		createShipmentOrderRequest.setVersion(version);
		createShipmentOrderRequest.getShipmentOrder().add(shipmentOrderType);

		return createShipmentOrderRequest;
	}

	/**
	 * todo how do i find the version?
	 * todo Should this be a hardcoded value (3.0 in this case)?
	 * todo or should i always query the dhl api and get their version response?
	 * todo i don't understand this :(
	 * todo Ref: https://entwickler.dhl.de/group/ep/wsapis/geschaeftskundenversand-3.0/operationen/createshipmentorder/ioreference section 1. Version
	 */
	@NonNull
	private static Version createHardcodedVersion(final ObjectFactory objectFactory)
	{
		final Version version = objectFactory.createVersion();
		version.setMajorRelease("3");
		version.setMinorRelease("0");
		return version;
	}

	@NonNull private WebServiceTemplate createWebServiceTemplate()
	{
		final HttpComponentsMessageSender messageSender = createMessageSenderWithCIGAuthentication(USER_NAME, PASSWORD);

		final Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setPackagesToScan(
				de.dhl.webservice.cisbase.ObjectFactory.class.getPackage().getName(),
				de.dhl.webservices.businesscustomershipping._3.ObjectFactory.class.getPackage().getName()
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
		@Override public void doWithMessage(final WebServiceMessage message)
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

