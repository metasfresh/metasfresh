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
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Stopwatch;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.mpackage.PackageId;
import de.metas.shipper.gateway.dhl.json.JSONDhlCreateOrderRequest;
import de.metas.shipper.gateway.dhl.json.JSONDhlCreateOrderResponse;
import de.metas.shipper.gateway.dhl.json.JsonDHLItem;
import de.metas.shipper.gateway.dhl.json.JsonDhlAddress;
import de.metas.shipper.gateway.dhl.json.JsonDhlAmount;
import de.metas.shipper.gateway.dhl.json.JsonDhlCustomsDeclaration;
import de.metas.shipper.gateway.dhl.json.JsonDhlDimension;
import de.metas.shipper.gateway.dhl.json.JsonDhlItemResponse;
import de.metas.shipper.gateway.dhl.json.JsonDhlPackageDetails;
import de.metas.shipper.gateway.dhl.json.JsonDhlShipment;
import de.metas.shipper.gateway.dhl.json.JsonDhlWeight;
import de.metas.shipper.gateway.dhl.logger.DhlClientLogEvent;
import de.metas.shipper.gateway.dhl.logger.DhlDatabaseClientLogger;
import de.metas.shipper.gateway.dhl.model.DhlClientConfig;
import de.metas.shipper.gateway.dhl.model.DhlCustomDeliveryData;
import de.metas.shipper.gateway.dhl.model.DhlCustomDeliveryDataDetail;
import de.metas.shipper.gateway.dhl.model.DhlCustomsDocument;
import de.metas.shipper.gateway.dhl.model.DhlCustomsItem;
import de.metas.shipper.gateway.dhl.model.DhlPackageLabelType;
import de.metas.shipper.gateway.spi.DeliveryOrderId;
import de.metas.shipper.gateway.spi.ShipperGatewayClient;
import de.metas.shipper.gateway.spi.exceptions.ShipperGatewayException;
import de.metas.shipper.gateway.spi.model.Address;
import de.metas.shipper.gateway.spi.model.ContactPerson;
import de.metas.shipper.gateway.spi.model.CustomDeliveryData;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryOrderLine;
import de.metas.shipper.gateway.spi.model.OrderId;
import de.metas.shipper.gateway.spi.model.PackageDimensions;
import de.metas.shipper.gateway.spi.model.PackageLabel;
import de.metas.shipper.gateway.spi.model.PackageLabels;
import de.metas.util.Check;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static de.metas.shipper.gateway.dhl.DhlConstants.PARAM_RESPONSE_BODY_AS_STRING;

public class DhlShipperGatewayClient implements ShipperGatewayClient
{
	private static final Logger logger = LoggerFactory.getLogger(DhlShipperGatewayClient.class);

	private final DhlDatabaseClientLogger databaseLogger;

	private final DhlClientConfig config;

	@VisibleForTesting
	@Getter(value = AccessLevel.PACKAGE)
	private final RestTemplate restTemplate;

	@Builder
	public DhlShipperGatewayClient(@NonNull final DhlClientConfig config, @NonNull final DhlDatabaseClientLogger databaseLogger, @NonNull final RestTemplate restTemplate)
	{
		this.config = config;
		this.databaseLogger = databaseLogger;
		this.restTemplate = restTemplate;

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
		final JSONDhlCreateOrderRequest dhlOrderRequest = createNewDHLShipmentOrderRequest(deliveryOrder);
		final JSONDhlCreateOrderResponse dhlResponse = callDhl(dhlOrderRequest, deliveryOrder.getId());

		final DeliveryOrder completedDeliveryOrder = updateDeliveryOrderFromResponse(deliveryOrder, dhlResponse);

		epicLogger.addLog("Completed deliveryOrder is {}", completedDeliveryOrder);

		return completedDeliveryOrder;
	}

	@NonNull
	private JSONDhlCreateOrderResponse callDhl(@NonNull final JSONDhlCreateOrderRequest dhlOrderRequest, @Nullable final DeliveryOrderId id)
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();
		final DhlClientLogEvent.DhlClientLogEventBuilder logEventBuilder = DhlClientLogEvent.builder()
				.requestElement(dhlOrderRequest)
				.deliveryOrderId(id)
				.config(config);
		try
		{
			final JSONDhlCreateOrderResponse response = callDhlWith(dhlOrderRequest);
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
	private JSONDhlCreateOrderResponse callDhlWith(@NonNull final JSONDhlCreateOrderRequest dhlOrderRequest)
	{
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(ImmutableList.of(MediaType.APPLICATION_JSON));
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		final HttpEntity<JSONDhlCreateOrderRequest> entity = new HttpEntity<>(dhlOrderRequest, httpHeaders);
		try
		{
			final ResponseEntity<JSONDhlCreateOrderResponse> result = restTemplate.postForEntity(config.getBaseUrl() + "/parcel/de/shipping/v2/orders", entity, JSONDhlCreateOrderResponse.class);
			if (!result.getStatusCode().is2xxSuccessful() || result.getBody() == null)
			{
				throw createShipperException(dhlOrderRequest, result.getStatusCode(), String.valueOf(result.getBody()));
			}
			return result.getBody();
		}
		catch (final HttpClientErrorException e)
		{
			final HttpStatus statusCode = e.getStatusCode();
			final String responseBodyAsString = e.getResponseBodyAsString();
			throw createShipperException(dhlOrderRequest, statusCode, responseBodyAsString);
		}
	}

	private AdempiereException createShipperException(final @NonNull JSONDhlCreateOrderRequest dhlOrderRequest, @NonNull final HttpStatus statusCode, @NonNull final String responseBodyAsString)
	{
		final ShipperGatewayException shipperGatewayException = //
				new ShipperGatewayException("HttpClientErrorException with statusCode=" + statusCode);

		return AdempiereException.wrapIfNeeded(shipperGatewayException)
				.appendParametersToMessage()
				.setParameter(PARAM_RESPONSE_BODY_AS_STRING, responseBodyAsString)
				.setParameter("routingRequest", dhlOrderRequest);
	}

	private JSONDhlCreateOrderRequest createNewDHLShipmentOrderRequest(@NonNull final DeliveryOrder deliveryOrder)
	{
		final JSONDhlCreateOrderRequest.JSONDhlCreateOrderRequestBuilder orderRequestBuilder = JSONDhlCreateOrderRequest.builder()
				//I can't tell if this is variable in any way, but on the sandbox environment this is the only profile I was able to use
				.profile(DhlConstants.STANDARD_GRUPPENPROFIL);
		final ImmutableList.Builder<JsonDhlShipment> shipments = ImmutableList.builder();
		final ContactPerson deliveryContact = deliveryOrder.getDeliveryContact();
		for (final DeliveryOrderLine deliveryOrderLine : deliveryOrder.getDeliveryOrderLines())
		{
			final String customerReference = getCustomerReference(deliveryOrder);
			final JsonDhlShipment.JsonDhlShipmentBuilder shipmentBuilder = JsonDhlShipment.builder()
					.billingNumber(config.getAccountNumber())
					.product(deliveryOrder.getShipperProduct().getCode())
					.shipDate(deliveryOrder.getPickupDate().getDate().format(DateTimeFormatter.ISO_LOCAL_DATE))
					.shipper(getJsonDhlAddress(deliveryOrder.getPickupAddress(), null))
					.consignee(getJsonDhlAddress(deliveryOrder.getDeliveryAddress(), deliveryContact))
					.details(getJsonDhlDetails(deliveryOrderLine))
					.customs(getJsonCustomsDeclaration(deliveryOrder.getCustomDeliveryData(), deliveryOrderLine.getPackageId()));
			if (customerReference != null)
			{
				shipmentBuilder.refNo(customerReference);
			}

			shipments.add(shipmentBuilder.build());

		}
		orderRequestBuilder.shipments(shipments.build());

		return orderRequestBuilder.build();
	}

	/**
	 * DHL Expects a length of between 8 and 30 characters, or nothing at all
	 */
	@Nullable
	private static String getCustomerReference(final @NonNull DeliveryOrder deliveryOrder)
	{
		final String customerReference = deliveryOrder.getCustomerReference();
		if (Check.isBlank(customerReference))
		{
			return null;
		}
		if (customerReference.length() < 8)
		{
			return Strings.padEnd(customerReference, 8, ' ');
		}
		if (customerReference.length() > 30)
		{
			return customerReference.substring(0, 30);
		}
		return customerReference;
	}

	@Nullable
	private JsonDhlCustomsDeclaration getJsonCustomsDeclaration(@Nullable final CustomDeliveryData customDeliveryData, final @NonNull PackageId packageId)
	{
		if (customDeliveryData == null)
		{
			return null;
		}
		final DhlCustomsDocument customsDocument = getDhlCustomsDocument(customDeliveryData, packageId);
		if (customsDocument == null)
		{
			return null;
		}
		return JsonDhlCustomsDeclaration.builder()
				.shipperCustomsRef(customsDocument.getShipperEORI())
				.consigneeCustomsRef(customsDocument.getConsigneeEORI())
				.postalCharges(JsonDhlAmount.builder()
						.amount(Amount.of(0, CurrencyCode.EUR))
						.build())
				.items(customsDocument.getItems()
						.stream()
						.map(this::toJsonDHLItem)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	@Nullable
	private static DhlCustomsDocument getDhlCustomsDocument(final @NonNull CustomDeliveryData customDeliveryData, final @NonNull PackageId packageId)
	{
		final DhlCustomDeliveryData dhlCustomDeliveryData = DhlCustomDeliveryData.cast(customDeliveryData);
		final DhlCustomDeliveryDataDetail dhlCustomDeliveryDataDetail = dhlCustomDeliveryData.getDetailByPackageId(packageId);
		final DhlCustomsDocument customsDocument = dhlCustomDeliveryDataDetail.getCustomsDocument();
		if (dhlCustomDeliveryDataDetail.isInternationalDelivery() && customsDocument == null)
		{
			throw new AdempiereException("Customs document needs to be provided for international shipments!");
		}
		return customsDocument;
	}

	private JsonDHLItem toJsonDHLItem(@NonNull final DhlCustomsItem dhlCustomsItem)
	{
		return JsonDHLItem.builder()
				.itemDescription(dhlCustomsItem.getItemDescription())
				.packagedQuantity(dhlCustomsItem.getPackagedQuantity())
				.itemWeight(JsonDhlWeight._inKg()
						.qtyInKg(dhlCustomsItem.getWeightInKg())
						.weightInKg())
				.itemValue(JsonDhlAmount.builder()
						.amount(dhlCustomsItem.getItemValue())
						.build())
				.build();
	}

	private JsonDhlPackageDetails getJsonDhlDetails(@NonNull final DeliveryOrderLine deliveryOrderLine)
	{
		final PackageDimensions packageDimensions = deliveryOrderLine.getPackageDimensions();
		return JsonDhlPackageDetails.builder()
				.weight(JsonDhlWeight._inKg()
						.qtyInKg(deliveryOrderLine.getGrossWeightKg())
						.weightInKg())
				.dim(JsonDhlDimension._inCM()
						.heightInCM(BigDecimal.valueOf(packageDimensions.getHeightInCM()))
						.widthInCM(BigDecimal.valueOf(packageDimensions.getWidthInCM()))
						.lengthInCM(BigDecimal.valueOf(packageDimensions.getLengthInCM()))
						.weightInCM())
				.build();
	}

	private JsonDhlAddress getJsonDhlAddress(final @NonNull Address address, @Nullable final ContactPerson deliveryContact)
	{
		final JsonDhlAddress.JsonDhlAddressBuilder addressBuilder = JsonDhlAddress.builder()
				.name1(address.getCompanyName1())
				.name2(address.getCompanyName2())
				.addressStreet(address.getStreet1())
				.addressHouse(address.getHouseNo())
				.additionalAddressInformation1(address.getStreet2())
				.postalCode(address.getZipCode())
				.city(address.getCity())
				.country(address.getCountry().getAlpha3());
		if (deliveryContact != null)
		{
			addressBuilder.email(deliveryContact.getEmailAddress())
					.phone(deliveryContact.getPhoneAsStringOrNull())
			;
		}
		return addressBuilder
				.build();
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

		final DhlCustomDeliveryData customDeliveryData = DhlCustomDeliveryData.cast(Objects.requireNonNull(deliveryOrder.getCustomDeliveryData()));

		//noinspection ConstantConditions
		final ImmutableList<PackageLabels> packageLabels = customDeliveryData.getDetails().stream()
				.map(detail -> createPackageLabel(detail.getPdfLabelData(), detail.getAwb(), String.valueOf(deliveryOrder.getId().getRepoId())))
				.collect(ImmutableList.toImmutableList());

		epicLogger.addLog("getPackageLabelsList: labels are {}", packageLabels);

		return packageLabels;
	}

	@NonNull
	private static PackageLabels createPackageLabel(final byte[] labelData, @NonNull final String awb, @NonNull final String deliveryOrderIdAsString)
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
	private DeliveryOrder updateDeliveryOrderFromResponse(@NonNull final DeliveryOrder deliveryOrder, @NonNull final JSONDhlCreateOrderResponse response)
	{
		final DhlCustomDeliveryData initialCustomDeliveryData = DhlCustomDeliveryData.cast(deliveryOrder.getCustomDeliveryData());

		final ImmutableList.Builder<DhlCustomDeliveryDataDetail> updatedCustomDeliveryData = ImmutableList.builder();
		final int deliveryOrderLineCount = deliveryOrder.getDeliveryOrderLines().size();
		final int responseItemsCount = response.getItems().size();
		if (deliveryOrderLineCount != responseItemsCount)
		{
			//the new API no longer allows for reliable mapping between order lines and generated shipments.
			//maybe match through order references?
			final String awbs = response.getItems().stream()
					.map(JsonDhlItemResponse::getShipmentNo)
					.collect(Collectors.joining(", "));
			getEpicLogger().addLog("Sent {} orders to DHL, but got {} labels for them. Unclear how to perform matching of following AWBs: {}", deliveryOrderLineCount, responseItemsCount, awbs);
			throw new ShipperGatewayException(String.format("Sent %d orders to DHL, but got %d labels for them. Unclear how to perform matching of following AWBs: %s", deliveryOrderLineCount, responseItemsCount, awbs));
		}

		for (int i = 0; i < deliveryOrderLineCount; i++)
		{
			final JsonDhlItemResponse creationState = response.getItems().get(i);
			if (creationState.getLabel() == null)
			{
				throw new AdempiereException("DHL Label is unexpectedly null for: " + creationState);
			}
			final byte[] pdfData = Base64.getDecoder().decode(creationState.getLabel().getB64());

			final DhlCustomDeliveryDataDetail detail = initialCustomDeliveryData
					.getDetails()
					.get(i)
					.toBuilder()
					.pdfLabelData(pdfData)
					.awb(creationState.getShipmentNo())
					.trackingUrl(config.getTrackingUrlBase() + creationState.getShipmentNo())
					.build();

			updatedCustomDeliveryData.add(detail);
		}

		return deliveryOrder.withCustomDeliveryData(initialCustomDeliveryData
				.withDhlCustomDeliveryDataDetails(updatedCustomDeliveryData.build()));
	}
}
