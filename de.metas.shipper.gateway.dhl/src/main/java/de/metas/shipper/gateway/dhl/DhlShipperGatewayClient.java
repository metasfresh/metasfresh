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

import com.google.common.collect.ImmutableList;
import de.dhl.webservice.cisbase.AuthentificationType;
import de.dhl.webservice.cisbase.CommunicationType;
import de.dhl.webservice.cisbase.CountryType;
import de.dhl.webservice.cisbase.NameType;
import de.dhl.webservice.cisbase.NativeAddressType;
import de.dhl.webservice.cisbase.ReceiverNativeAddressType;
import de.dhl.webservices.businesscustomershipping._3.CreateShipmentOrderRequest;
import de.dhl.webservices.businesscustomershipping._3.CreateShipmentOrderResponse;
import de.dhl.webservices.businesscustomershipping._3.ReceiverType;
import de.dhl.webservices.businesscustomershipping._3.ShipmentDetailsTypeType;
import de.dhl.webservices.businesscustomershipping._3.ShipmentItemType;
import de.dhl.webservices.businesscustomershipping._3.ShipmentNotificationType;
import de.dhl.webservices.businesscustomershipping._3.ShipmentOrderType;
import de.dhl.webservices.businesscustomershipping._3.ShipperType;
import de.dhl.webservices.businesscustomershipping._3.Version;
import de.metas.shipper.gateway.dhl.model.DhlClientConfig;
import de.metas.shipper.gateway.dhl.model.DhlCustomDeliveryData;
import de.metas.shipper.gateway.spi.ShipperGatewayClient;
import de.metas.shipper.gateway.spi.exceptions.ShipperGatewayException;
import de.metas.shipper.gateway.spi.model.Address;
import de.metas.shipper.gateway.spi.model.ContactPerson;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryPosition;
import de.metas.shipper.gateway.spi.model.PackageDimensions;
import de.metas.shipper.gateway.spi.model.PackageLabels;
import de.metas.shipper.gateway.spi.model.PickupDate;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.List;

public class DhlShipperGatewayClient implements ShipperGatewayClient
{
	private static final Logger logger = LoggerFactory.getLogger(DhlShipperGatewayClient.class);

	private final Version API_VERSION;

	private final DhlClientConfig config;

	private final WebServiceTemplate webServiceTemplate;
	private final SoapHeaderWithAuth soapHeaderWithAuth;

	private final de.dhl.webservice.cisbase.ObjectFactory objectFactoryCis;
	private final de.dhl.webservices.businesscustomershipping._3.ObjectFactory objectFactory;

	@Builder
	public DhlShipperGatewayClient(@NonNull final DhlClientConfig config)
	{
		this.config = config;

		webServiceTemplate = createWebServiceTemplate();
		soapHeaderWithAuth = new SoapHeaderWithAuth();

		objectFactoryCis = new de.dhl.webservice.cisbase.ObjectFactory();
		objectFactory = new de.dhl.webservices.businesscustomershipping._3.ObjectFactory();

		// we're using DHL SOAP API V3
		API_VERSION = objectFactory.createVersion();
		API_VERSION.setMajorRelease("3");
		API_VERSION.setMinorRelease("0");
	}

	@Override
	public String getShipperGatewayId()
	{
		return DhlConstants.SHIPPER_GATEWAY_ID;
	}

	@Override
	public DeliveryOrder createDeliveryOrder(final DeliveryOrder draftDeliveryOrder) throws ShipperGatewayException
	{
		throw new ShipperGatewayException("DRAFT Delivery Orders shall never be created.");
	}

	@Override
	public DeliveryOrder completeDeliveryOrder(final DeliveryOrder deliveryOrder) throws ShipperGatewayException
	{
		logger.trace("Creating delivery order for {}", deliveryOrder);
		final CreateShipmentOrderRequest dhlRequest = createDHLShipmentOrderRequest(deliveryOrder);

		return null;
	}

	@Override
	public DeliveryOrder voidDeliveryOrder(final DeliveryOrder deliveryOrder) throws ShipperGatewayException
	{
		return null;
	}

	@Override
	public List<PackageLabels> getPackageLabelsList(final DeliveryOrder deliveryOrder) throws ShipperGatewayException
	{
		return null;
	}

	/////////////////////////////////////////////////
	/////////////////////////////////////////////////
	/////////////////////////////////////////////////
	/////////////////////////////////////////////////
	/////////////////////////////////////////////////

	@NonNull
	private CreateShipmentOrderRequest createDHLShipmentOrderRequest(@NonNull final DeliveryOrder deliveryOrder)
	{
		final CreateShipmentOrderRequest createShipmentOrderRequest = objectFactory.createCreateShipmentOrderRequest();
		createShipmentOrderRequest.setVersion(API_VERSION);

		for (final DeliveryPosition deliveryPosition : deliveryOrder.getDeliveryPositions())
		{
			final ImmutableList<Integer> packageIdsAsList = deliveryPosition.getPackageIds().asList();
			for (int i = 0; i < deliveryPosition.getNumberOfPackages(); i++)
			{
				// (2.2)  the shipment
				final ShipmentOrderType.Shipment shipmentOrderTypeShipment = objectFactory.createShipmentOrderTypeShipment();

				final ContactPerson deliveryContact = deliveryOrder.getDeliveryContact();

				{
					// (2.2.1) Shipment Details
					final PickupDate pickupDate = deliveryOrder.getPickupDate();

					final ShipmentDetailsTypeType shipmentDetailsTypeType = objectFactory.createShipmentDetailsTypeType();
					shipmentDetailsTypeType.setProduct(deliveryOrder.getServiceType().getCode());
					shipmentDetailsTypeType.setAccountNumber(config.getAccountNumber());
					//noinspection ConstantConditions
					shipmentDetailsTypeType.setCustomerReference(deliveryOrder.getCustomerReference());
					shipmentDetailsTypeType.setShipmentDate(pickupDate.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
					// (2.2.1.8)
					final PackageDimensions packageDimensions = deliveryPosition.getPackageDimensions();
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
					receiverType.setName1(deliveryAddress.getCompanyName1());
					// (2.2.4.2)
					final ReceiverNativeAddressType receiverNativeAddressType = objectFactoryCis.createReceiverNativeAddressType();
					receiverNativeAddressType.setName2(deliveryAddress.getCompanyName2());
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

					final ShipperType shipperType = objectFactory.createShipperType();
					// (2.2.2.1)
					final NameType nameType = objectFactoryCis.createNameType();
					nameType.setName1(pickupAddress.getCompanyName1());
					nameType.setName2(pickupAddress.getCompanyName2());
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

				// (2) create the needed shipment order type
				final ShipmentOrderType shipmentOrderType = objectFactory.createShipmentOrderType();
				final DhlCustomDeliveryData dhlCustomDeliveryData = (DhlCustomDeliveryData)deliveryPosition.getCustomDeliveryData();
				final String packageSequenceNumber = dhlCustomDeliveryData.getPackageIdsToSequenceNumber().get(packageIdsAsList.get(i));
				if (StringUtils.isBlank(packageSequenceNumber))
				{
					throw new AdempiereException("PackageSequenceNumber must not be empty. There is a bug somewhere.");
				}
				shipmentOrderType.setSequenceNumber(packageSequenceNumber);
				shipmentOrderType.setShipment(shipmentOrderTypeShipment);
				createShipmentOrderRequest.getShipmentOrder().add(shipmentOrderType);
			}
		}

		return createShipmentOrderRequest;
	}

	@NonNull
	private WebServiceTemplate createWebServiceTemplate()
	{
		// cig authentication (basic http authentication) required @ each request: https://entwickler.dhl.de/en/group/ep/authentifizierung
		final UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(config.getApplicationID(), config.getApplicationToken());

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

	@NonNull
	private Object doActualRequest(final Object requestBody)
	{
		// todo make this beautiful

		// execute the actual request
		final CreateShipmentOrderResponse createShipmentOrderResponse = (CreateShipmentOrderResponse)webServiceTemplate.marshalSendAndReceive(requestBody, soapHeaderWithAuth);

		return createShipmentOrderResponse;

	}

	private class SoapHeaderWithAuth implements WebServiceMessageCallback
	{
		// thx to https://www.devglan.com/spring-mvc/custom-header-in-spring-soap-request
		// 		for the SoapHeader reference
		@Override
		public void doWithMessage(final WebServiceMessage message)
		{
			try
			{
				// service authentication
				final AuthentificationType authentificationType = objectFactoryCis.createAuthentificationType();
				authentificationType.setUser(config.getUser());
				authentificationType.setSignature(config.getSignature());
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
