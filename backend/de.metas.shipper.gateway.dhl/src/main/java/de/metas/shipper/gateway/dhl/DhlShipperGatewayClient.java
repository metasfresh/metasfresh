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

import ch.qos.logback.classic.Level;
import com.google.common.base.Joiner;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import de.dhl.webservice.cisbase.AuthentificationType;
import de.dhl.webservice.cisbase.CommunicationType;
import de.dhl.webservice.cisbase.CountryType;
import de.dhl.webservice.cisbase.NameType;
import de.dhl.webservice.cisbase.NativeAddressType;
import de.dhl.webservice.cisbase.ObjectFactory;
import de.dhl.webservice.cisbase.ReceiverNativeAddressType;
import de.dhl.webservices.businesscustomershipping._3.CreateShipmentOrderRequest;
import de.dhl.webservices.businesscustomershipping._3.CreateShipmentOrderResponse;
import de.dhl.webservices.businesscustomershipping._3.CreationState;
import de.dhl.webservices.businesscustomershipping._3.LabelData;
import de.dhl.webservices.businesscustomershipping._3.ReceiverType;
import de.dhl.webservices.businesscustomershipping._3.ShipmentDetailsTypeType;
import de.dhl.webservices.businesscustomershipping._3.ShipmentItemType;
import de.dhl.webservices.businesscustomershipping._3.ShipmentNotificationType;
import de.dhl.webservices.businesscustomershipping._3.ShipmentOrderType;
import de.dhl.webservices.businesscustomershipping._3.ShipperType;
import de.dhl.webservices.businesscustomershipping._3.Version;
import de.metas.mpackage.PackageId;
import de.metas.shipper.gateway.dhl.logger.DhlClientLogEvent;
import de.metas.shipper.gateway.dhl.logger.DhlDatabaseClientLogger;
import de.metas.shipper.gateway.dhl.model.DhlClientConfig;
import de.metas.shipper.gateway.dhl.model.DhlCustomDeliveryData;
import de.metas.shipper.gateway.dhl.model.DhlCustomDeliveryDataDetail;
import de.metas.shipper.gateway.dhl.model.DhlPackageLabelType;
import de.metas.shipper.gateway.dhl.model.DhlSequenceNumber;
import de.metas.shipper.gateway.spi.DeliveryOrderId;
import de.metas.shipper.gateway.spi.ShipperGatewayClient;
import de.metas.shipper.gateway.spi.exceptions.ShipperGatewayException;
import de.metas.shipper.gateway.spi.model.Address;
import de.metas.shipper.gateway.spi.model.ContactPerson;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryPosition;
import de.metas.shipper.gateway.spi.model.OrderId;
import de.metas.shipper.gateway.spi.model.PackageDimensions;
import de.metas.shipper.gateway.spi.model.PackageLabel;
import de.metas.shipper.gateway.spi.model.PackageLabels;
import de.metas.shipper.gateway.spi.model.PickupDate;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
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
import java.util.Base64;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class DhlShipperGatewayClient implements ShipperGatewayClient
{
	private static final Logger logger = LoggerFactory.getLogger(DhlShipperGatewayClient.class);
	private final DhlDatabaseClientLogger databaseLogger;

	private final Version API_VERSION;

	private final DhlClientConfig config;

	private final WebServiceTemplate webServiceTemplate;

	private final de.dhl.webservice.cisbase.ObjectFactory objectFactoryCis;
	private final de.dhl.webservices.businesscustomershipping._3.ObjectFactory objectFactory;

	@Builder
	public DhlShipperGatewayClient(@NonNull final DhlClientConfig config, @NonNull final DhlDatabaseClientLogger databaseLogger)
	{
		this.config = config;
		this.databaseLogger = databaseLogger;

		objectFactoryCis = new de.dhl.webservice.cisbase.ObjectFactory();
		objectFactory = new de.dhl.webservices.businesscustomershipping._3.ObjectFactory();

		webServiceTemplate = createWebServiceTemplate();

		// we're using DHL SOAP API V3
		API_VERSION = objectFactory.createVersion();
		API_VERSION.setMajorRelease("3");
		API_VERSION.setMinorRelease("0");
	}

	@NonNull
	@Override
	public String getShipperGatewayId()
	{
		return DhlConstants.SHIPPER_GATEWAY_ID;
	}

	@Override
	public DeliveryOrder createDeliveryOrder(final DeliveryOrder draftDeliveryOrder) throws ShipperGatewayException
	{
		throw new ShipperGatewayException("(DRAFT) Delivery Orders shall never be created.");
	}

	@NonNull
	@Override
	public DeliveryOrder completeDeliveryOrder(@NonNull final DeliveryOrder deliveryOrder) throws ShipperGatewayException
	{
		final ILoggable epicLogger = getEpicLogger();

		epicLogger.addLog("Creating shipment order request for {}", deliveryOrder);
		final CreateShipmentOrderRequest dhlRequest = createDHLShipmentOrderRequest(deliveryOrder);

		final CreateShipmentOrderResponse response = (CreateShipmentOrderResponse)doActualRequest(dhlRequest, deliveryOrder.getId(), SupportedSoapAction.CREATE_SHIPMENT_ORDER);
		if (!BigInteger.ZERO.equals(response.getStatus().getStatusCode()))
		{
			final String exceptionMessage = response.getCreationState()
					.stream()
					.map(it -> it.getLabelData().getStatus().getStatusText() + " " +
							Joiner.on("; ")
									.skipNulls()
									.join(it.getLabelData().getStatus().getStatusMessage())
					)
					.collect(Collectors.joining("; "));
			throw new ShipperGatewayException(exceptionMessage);
		}
		final DeliveryOrder completedDeliveryOrder = updateDeliveryOrderFromResponse(deliveryOrder, response);

		epicLogger.addLog("Completed deliveryOrder is {}", completedDeliveryOrder);

		return completedDeliveryOrder;
	}

	/**
	 * no idea what this does, but tobias sais it's useful to have this special log, so here it is!
	 */
	@NonNull
	private ILoggable getEpicLogger()
	{
		return Loggables.withLogger(logger, Level.TRACE);
	}

	@NonNull
	@Override
	public List<PackageLabels> getPackageLabelsList(@NonNull final DeliveryOrder deliveryOrder) throws ShipperGatewayException
	{
		final ILoggable epicLogger = getEpicLogger();
		epicLogger.addLog("getPackageLabelsList for {}", deliveryOrder);

		final DhlCustomDeliveryData customDeliveryData = DhlCustomDeliveryData.cast(deliveryOrder.getCustomDeliveryData());

		//noinspection ConstantConditions
		final ImmutableList<PackageLabels> packageLabels = customDeliveryData.getDetails().stream()
				.map(detail -> createPackageLabel(detail.getPdfLabelData(), detail.getAwb(), String.valueOf(deliveryOrder.getId().getRepoId())))
				.collect(ImmutableList.toImmutableList());

		epicLogger.addLog("getPackageLabelsList: labels are {}", packageLabels);

		return packageLabels;
	}

	@NonNull
	private static PackageLabels createPackageLabel(final byte[] labelData, @NonNull final String awb, final String deliveryOrderIdAsString)
	{
		return PackageLabels.builder()
				.orderId(OrderId.of(DhlConstants.SHIPPER_GATEWAY_ID, deliveryOrderIdAsString))
				.defaultLabelType(DhlPackageLabelType.GUI)
				.label(PackageLabel.builder()
						.type(DhlPackageLabelType.GUI)
						.labelData(labelData)
						.contentType(PackageLabel.CONTENTTYPE_PDF)
						.fileName(awb)
						.build())
				.build();
	}

	@NonNull
	private DeliveryOrder updateDeliveryOrderFromResponse(@NonNull final DeliveryOrder deliveryOrder, @NonNull final CreateShipmentOrderResponse response)
	{
		final DhlCustomDeliveryData initialCustomDeliveryData = DhlCustomDeliveryData.cast(deliveryOrder.getCustomDeliveryData());

		final ImmutableList.Builder<DhlCustomDeliveryDataDetail> updatedCustomDeliveryData = ImmutableList.builder();

		for (final CreationState creationState : response.getCreationState())
		{
			final LabelData labelData = creationState.getLabelData();
			final byte[] pdfData = Base64.getDecoder().decode(labelData.getLabelData());

			final DhlCustomDeliveryDataDetail detail = initialCustomDeliveryData
					.getDetailBySequenceNumber(DhlSequenceNumber.of(creationState.getSequenceNumber()))
					.toBuilder()
					.pdfLabelData(pdfData)
					.awb(creationState.getShipmentNumber()) // i hope shipmentNumber is the AWB
					.trackingUrl(config.getTrackingUrlBase() + creationState.getShipmentNumber())
					.build();

			updatedCustomDeliveryData.add(detail);
		}

		// imo this implementation is super ugly, but i have no idea how to make it better, and i've wasted enough time as it is.
		return deliveryOrder
				.toBuilder()
				.customDeliveryData(
						initialCustomDeliveryData
								.toBuilder()
								.clearDetails()
								.details(updatedCustomDeliveryData.build())
								.build())
				.build();
	}

	private Object doActualRequest(final Object request, @NonNull final DeliveryOrderId deliveryOrderRepoIdForLogging, @NonNull final SupportedSoapAction soapAction)
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();
		final DhlClientLogEvent.DhlClientLogEventBuilder logEventBuilder = DhlClientLogEvent.builder()
				.marshaller(webServiceTemplate.getMarshaller())
				.requestElement(request)
				.deliveryOrderRepoId(deliveryOrderRepoIdForLogging.getRepoId())
				.config(config);
		try
		{
			final Object response = webServiceTemplate.marshalSendAndReceive(request, new SoapHeaderWithAuth(objectFactoryCis, config, soapAction));
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
	private CreateShipmentOrderRequest createDHLShipmentOrderRequest(
			@NonNull final DeliveryOrder deliveryOrder)
	{
		final CreateShipmentOrderRequest createShipmentOrderRequest = objectFactory.createCreateShipmentOrderRequest();
		createShipmentOrderRequest.setVersion(API_VERSION);
		// (3.) base64 encoding for labels in response
		createShipmentOrderRequest.setLabelResponseType("B64");
		// (5.) GUI = sort of an "auto" label format
		createShipmentOrderRequest.setLabelFormat(DhlPackageLabelType.GUI.name());

		for (final DeliveryPosition deliveryPosition : deliveryOrder.getDeliveryPositions()) // only a single delivery position should exist
		{
			final ImmutableList<PackageId> packageIdsAsList = deliveryPosition.getPackageIds().asList();
			for (int i = 0; i < deliveryPosition.getNumberOfPackages(); i++)
			{
				// (2.2)  the shipment
				final ShipmentOrderType.Shipment shipmentOrderTypeShipment = objectFactory.createShipmentOrderTypeShipment();

				final ContactPerson deliveryContact = deliveryOrder.getDeliveryContact();

				{
					// (2.2.1) Shipment Details
					final PickupDate pickupDate = deliveryOrder.getPickupDate();

					final ShipmentDetailsTypeType shipmentDetailsTypeType = objectFactory.createShipmentDetailsTypeType();
					shipmentDetailsTypeType.setProduct(deliveryOrder.getShipperProduct().getCode());
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

				//				{
				//					// (2.2.6) Export Document - only for international shipments
				//
				//					//noinspection ConstantConditions
				//					final DhlCustomDeliveryData dhlCustomDeliveryData = DhlCustomDeliveryData.cast(deliveryOrder.getCustomDeliveryData());
				//					final DhlCustomDeliveryDataDetail deliveryDetail = dhlCustomDeliveryData.getDetailByPackageId(packageIdsAsList.get(i));
				//
				//					if (deliveryDetail.isInternationalDelivery())
				//					{
				//						final DhlCustomsDocument customsDocument = deliveryDetail.getCustomsDocument();
				//						final ExportDocumentType exportDocumentType = objectFactory.createExportDocumentType();
				//						//			exportDocumentType.setInvoiceNumber("2212011"); // optional
				//						exportDocumentType.setExportType(customsDocument.getExportType());
				//						exportDocumentType.setExportTypeDescription(customsDocument.getExportTypeDescription());
				//						//			exportDocumentType.setTermsOfTrade("DDP"); // optional
				//						exportDocumentType.setPlaceOfCommital(deliveryOrder.getDeliveryAddress().getCity());
				//						exportDocumentType.setAdditionalFee(customsDocument.getAdditionalFee());
				//						//			exportDocumentType.setPermitNumber("12345"); //  optional
				//						//			exportDocumentType.setAttestationNumber("65421"); //  optional
				//
				//						// (2.2.6.9)
				//						final Serviceconfiguration serviceconfiguration = objectFactory.createServiceconfiguration();
				//						serviceconfiguration.setActive(customsDocument.getElectronicExportNotification());
				//						exportDocumentType.setWithElectronicExportNtfctn(serviceconfiguration);
				//						// (2.2.6.10)
				//						final ExportDocumentType.ExportDocPosition docPosition = objectFactory.createExportDocumentTypeExportDocPosition();
				//						docPosition.setDescription(customsDocument.getPackageDescription());
				//						docPosition.setCountryCodeOrigin(deliveryOrder.getPickupAddress().getCountry().getAlpha2());
				//						docPosition.setCustomsTariffNumber(customsDocument.getCustomsTariffNumber());
				//						docPosition.setAmount(customsDocument.getCustomsAmount());
				//						docPosition.setNetWeightInKG(customsDocument.getNetWeightInKg()); // must be less than the weight!!
				//						docPosition.setCustomsValue(customsDocument.getCustomsValue());
				//						exportDocumentType.getExportDocPosition().add(docPosition);
				//
				//						shipmentOrderTypeShipment.setExportDocument(exportDocumentType);
				//					}
				//				}

				// (2) create the needed shipment order type
				final ShipmentOrderType shipmentOrderType = objectFactory.createShipmentOrderType();
				final DhlCustomDeliveryData dhlCustomDeliveryData = DhlCustomDeliveryData.cast(deliveryOrder.getCustomDeliveryData());
				final DhlCustomDeliveryDataDetail deliveryDetail = dhlCustomDeliveryData.getDetailByPackageId(packageIdsAsList.get(i).getRepoId());
				shipmentOrderType.setSequenceNumber(deliveryDetail.getSequenceNumber().getSequenceNumber());
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
		webServiceTemplate.setDefaultUri(config.getBaseUrl());
		webServiceTemplate.setMessageSender(messageSender);
		webServiceTemplate.setMarshaller(marshaller);
		webServiceTemplate.setUnmarshaller(marshaller);
		return webServiceTemplate;
	}

	private static class SoapHeaderWithAuth implements WebServiceMessageCallback
	{

		private final ObjectFactory objectFactoryCis;
		private final DhlClientConfig config;
		private final SupportedSoapAction soapAction;

		private SoapHeaderWithAuth(final ObjectFactory objectFactoryCis, final DhlClientConfig config, final SupportedSoapAction soapAction)
		{
			this.objectFactoryCis = objectFactoryCis;
			this.config = config;
			this.soapAction = soapAction;
		}

		// thx to https://www.devglan.com/spring-mvc/custom-header-in-spring-soap-request
		// 		for the SoapHeader reference
		@Override
		public void doWithMessage(final WebServiceMessage message)
		{
			try
			{
				// service authentication
				final AuthentificationType authentificationType = objectFactoryCis.createAuthentificationType();
				authentificationType.setUser(config.getUsername());
				authentificationType.setSignature(config.getSignature());
				final JAXBElement<AuthentificationType> authentification = objectFactoryCis.createAuthentification(authentificationType);

				// add the authentication to header
				final SoapMessage soapMessage = (SoapMessage)message;
				final SoapHeader header = soapMessage.getSoapHeader();

				soapMessage.setSoapAction(soapAction.getValue());

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

	public enum SupportedSoapAction
	{
		CREATE_SHIPMENT_ORDER("urn:createShipmentOrder");

		private final String value;

		SupportedSoapAction(final String value)
		{
			this.value = value;
		}

		public String getValue()
		{
			return value;
		}
	}

}
