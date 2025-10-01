package de.metas.shipper.gateway.nshift.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.base.Stopwatch;
import de.metas.common.delivery.v1.json.request.JsonDeliveryRequest;
import de.metas.common.delivery.v1.json.response.JsonDeliveryResponse;
import de.metas.logging.LogManager;
import de.metas.shipper.gateway.nshift.json.JsonAddress;
import de.metas.shipper.gateway.nshift.json.JsonAddressKind;
import de.metas.shipper.gateway.nshift.json.JsonCustomsArticleDetail;
import de.metas.shipper.gateway.nshift.json.JsonCustomsArticleDetailKind;
import de.metas.shipper.gateway.nshift.json.JsonCustomsArticleInfo;
import de.metas.shipper.gateway.nshift.json.JsonDetailGroup;
import de.metas.shipper.gateway.nshift.json.JsonDetailRow;
import de.metas.shipper.gateway.nshift.json.JsonLine;
import de.metas.shipper.gateway.nshift.json.JsonPackage;
import de.metas.shipper.gateway.nshift.json.JsonShipmentData;
import de.metas.shipper.gateway.nshift.json.JsonShipmentOptions;
import de.metas.shipper.gateway.nshift.json.JsonShipmentReference;
import de.metas.shipper.gateway.nshift.json.JsonShipmentReferenceKind;
import de.metas.shipper.gateway.nshift.json.JsonShipmentRequest;
import de.metas.shipper.gateway.nshift.json.JsonShipmentResponse;
import de.metas.shipper.gateway.spi.exceptions.ShipperGatewayException;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import static de.metas.shipper.gateway.nshift.client.NShiftClientConfig.NSHIFT_OBJECT_MAPPER;
import static de.metas.shipper.gateway.nshift.client.NShiftClientConfig.NSHIFT_REST_TEMPLATE;


@Service
public class NShiftShipmentService
{

	private static final Logger logger = LogManager.getLogger(NShiftShipmentService.class);
	//For now only save as configuration is missing (to be replaced with /Shipments )
	private static final String CREATE_SHIPMENT_ENDPOINT = "/ShipServer/{ID}/SaveShipment";

	@NonNull private final RestTemplate restTemplate;
	@NonNull private final ObjectMapper objectMapper;

	public NShiftShipmentService(
			@Qualifier(NSHIFT_REST_TEMPLATE) @NonNull final RestTemplate restTemplate,
			@Qualifier(NSHIFT_OBJECT_MAPPER) @NonNull final ObjectMapper objectMapper)
	{
		this.restTemplate = restTemplate;
		this.objectMapper = objectMapper;
	}


	public JsonDeliveryResponse createShipment(@NonNull final JsonDeliveryRequest deliveryRequest)
	{
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		objectMapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		logger.debug("Creating shipment for request: {}", deliveryRequest);
		final JsonShipmentRequest requestBody = buildShipmentRequest(deliveryRequest);

		logRequestAsJson(requestBody);

		final JsonShipmentResponse response = callNShift(requestBody, deliveryRequest);

		logger.debug("Successfully received nShift response: {}", response);
		return JsonDeliveryResponse.builder()
                .build();
	}

	private JsonShipmentResponse callNShift(@NonNull final JsonShipmentRequest request, @NonNull final JsonDeliveryRequest deliveryRequest)
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();
		try
		{
			final String responseJson = callNShiftWith(request, deliveryRequest);
			logger.info("nShift API call successful. Duration: {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
			logger.trace("nShift response JSON: {}", responseJson);
			return objectMapper.readValue(responseJson, JsonShipmentResponse.class);
		}
		catch (final Throwable throwable)
		{
			logger.error("nShift API call failed. Duration: {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS), throwable);
			throw AdempiereException.wrapIfNeeded(throwable);
		}
	}

	private String callNShiftWith(@NonNull final JsonShipmentRequest request, @NonNull final JsonDeliveryRequest deliveryRequest)
	{
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		httpHeaders.set("Authorization", getAuthorizationHeader(deliveryRequest));

		try
		{
			final String url = deliveryRequest.getShipperConfig().getUrl() + CREATE_SHIPMENT_ENDPOINT;
			final HttpEntity<JsonShipmentRequest> entity = new HttpEntity<>(request, httpHeaders);
			final ResponseEntity<String> result = restTemplate.postForEntity(url, entity, String.class, deliveryRequest.getShipperConfig().getGatewayId());

			if (!result.getStatusCode().is2xxSuccessful() || result.getBody() == null)
			{
				throw createShipperException(request, result.getStatusCode(), String.valueOf(result.getBody()));
			}
			return result.getBody();
		}
		catch (final HttpClientErrorException e)
		{
			throw createShipperException(request, e.getStatusCode(), e.getResponseBodyAsString());
		}
	}

	private @NotNull String getAuthorizationHeader(@NonNull final JsonDeliveryRequest deliveryRequest)
	{
		final String auth = deliveryRequest.getShipperConfig().getUsername() + ":" + deliveryRequest.getShipperConfig().getPassword();
		return "Basic " + Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
	}

	private AdempiereException createShipperException(@NonNull final JsonShipmentRequest request, @NonNull final HttpStatus statusCode, @NonNull final String responseBody)
	{
		final ShipperGatewayException cause = new ShipperGatewayException("nShift API call failed with status code " + statusCode);

		try
		{
			final String requestAsJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(request);
			return AdempiereException.wrapIfNeeded(cause)
					.appendParametersToMessage()
					.setParameter("statusCode", statusCode.toString())
					.setParameter("responseBody", responseBody)
					.setParameter("nShiftRequest", requestAsJson);
		}
		catch (final JsonProcessingException e)
		{
			logger.warn("Failed to serialize nShift request for exception details", e);
			return AdempiereException.wrapIfNeeded(cause)
					.appendParametersToMessage()
					.setParameter("statusCode", statusCode.toString())
					.setParameter("responseBody", responseBody);
		}
	}

	private void logRequestAsJson(@NonNull final JsonShipmentRequest requestBody)
	{
		if (!logger.isTraceEnabled())
		{
			return;
		}
		try
		{
			final String jsonRequest = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestBody);
			logger.trace("---- Sending nShift JSON Request ----\n{}", jsonRequest);
		}
		catch (final JsonProcessingException e)
		{
			logger.trace("Could not serialize nShift request to JSON for logging", e);
		}
	}

	private JsonShipmentRequest buildShipmentRequest(@NonNull final JsonDeliveryRequest deliveryRequest)
	{
		final JsonShipmentOptions options = JsonShipmentOptions.builder()
				//.submit(true)
				//.labelType(JsonLabelType.PDF)
				//.requiredDeliveryDate(LocalDateTime.now().plusDays(3).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
				//.validatePostCode(true)
				.place("Inbox") //should be removed after switch of Endpoint
				.build();

		final String actorId = Check.assumeNotNull(deliveryRequest.getShipperConfig().getGatewayId(), "The GatewayId (ActorId) shouldn't be null");

		final JsonShipmentData.JsonShipmentDataBuilder dataBuilder = JsonShipmentData.builder()
				.actorCSID(Integer.valueOf(actorId))
				.orderNo(deliveryRequest.getId())
				.prodConceptID(2757)
				.pickupDt(LocalDate.parse(deliveryRequest.getPickupDate()));

		// Add Addresses
		dataBuilder.address(buildNShiftAddressBuilder(deliveryRequest.getPickupAddress(), JsonAddressKind.SENDER).build());
		final JsonAddress.JsonAddressBuilder receiverAddressBuilder = buildNShiftAddressBuilder(deliveryRequest.getDeliveryAddress(), JsonAddressKind.RECEIVER);
		final de.metas.common.delivery.v1.json.JsonContact deliveryContact = deliveryRequest.getDeliveryContact();
		if (deliveryContact != null)
		{
			if (Check.isNotBlank(deliveryContact.getPhone()))
			{
				receiverAddressBuilder.phone(deliveryContact.getPhone());
			}
			if (Check.isNotBlank(deliveryContact.getSimplePhoneNumber()))
			{
				receiverAddressBuilder.mobile(deliveryContact.getSimplePhoneNumber());
			}
			if (Check.isNotBlank(deliveryContact.getEmailAddress()))
			{
				receiverAddressBuilder.email(deliveryContact.getEmailAddress());
			}
		}
		dataBuilder.address(receiverAddressBuilder.build());

		// Add References
		if (deliveryRequest.getDeliveryDate() != null)
		{
			dataBuilder.reference(JsonShipmentReference.builder()
					.kind(JsonShipmentReferenceKind.WANTED_DELIVERY_TIME)
					.value(deliveryRequest.getDeliveryDate())
					.build());
		}
		if (Check.isNotBlank(deliveryRequest.getCustomerReference()))
		{
			dataBuilder.reference(JsonShipmentReference.builder()
					.kind(JsonShipmentReferenceKind.SENDER_REFERENCE)
					.value(deliveryRequest.getCustomerReference())
					.build());
		}


		// Process each delivery line as a separate package line in the nShift request
		int lineNoCounter = 1;
		for (final de.metas.common.delivery.v1.json.request.JsonDeliveryOrderLine deliveryLine : deliveryRequest.getDeliveryOrderLines())
		{
			dataBuilder.line(buildNShiftLine(deliveryLine, lineNoCounter));
			// Each line might also require a corresponding customs declaration group
			dataBuilder.detailGroup(buildCustomsArticleGroup(deliveryLine, lineNoCounter, deliveryRequest.getPickupAddress().getCountry()));
			lineNoCounter++;
		}

		return JsonShipmentRequest.builder()
				.options(options)
				.data(dataBuilder.build())
				.build();
	}

	private JsonAddress.JsonAddressBuilder buildNShiftAddressBuilder(@NonNull final de.metas.common.delivery.v1.json.JsonAddress commonAddress, @NonNull final JsonAddressKind kind)
	{
		String street1 = commonAddress.getStreet1();
		if (Check.isNotBlank(commonAddress.getHouseNo()))
		{
			street1 = street1 + " " + commonAddress.getHouseNo();
		}

		return JsonAddress.builder()
				.kind(kind)
				.name1(commonAddress.getCompanyName1())
				.name2(commonAddress.getCompanyName2())
				.street1(street1)
				.street2(commonAddress.getStreet2())
				.postCode(commonAddress.getZipCode())
				.city(commonAddress.getCity())
				.countryCode(commonAddress.getCountry());
	}

	private JsonLine buildNShiftLine(@NonNull final de.metas.common.delivery.v1.json.request.JsonDeliveryOrderLine deliveryLine, final int lineNo)
	{
		// nShift expects weight in grams and dimensions in millimeters.
		final int weightGrams = deliveryLine.getGrossWeightKg().multiply(BigDecimal.valueOf(1000)).intValue();
		final int lengthMM = deliveryLine.getPackageDimensions().getLengthInCM() * 10;
		final int widthMM = deliveryLine.getPackageDimensions().getWidthInCM() * 10;
		final int heightMM = deliveryLine.getPackageDimensions().getHeightInCM() * 10;

		return JsonLine.builder()
				.number(lineNo)
				.pkgWeight(weightGrams)
				.lineWeight(weightGrams)
				.length(lengthMM)
				.width(widthMM)
				.height(heightMM)
				.goodsTypeID(21) // Goods Types currently seem not to be configured, but as this is mandatory we only save the shipment
				.pkg(JsonPackage.builder()
						.pkgNo(deliveryLine.getPackageId())
						.build())
				.build();
	}

	private JsonDetailGroup buildCustomsArticleGroup(@NonNull final de.metas.common.delivery.v1.json.request.JsonDeliveryOrderLine deliveryLine, final int lineNo, @NonNull final String countryOfOrigin)
	{
		final JsonCustomsArticleInfo.JsonCustomsArticleInfoBuilder<?, ?> customsArticleInfoBuilder = JsonCustomsArticleInfo.builder();

		for (final de.metas.common.delivery.v1.json.request.JsonDeliveryOrderLineContents content : deliveryLine.getContents())
		{
			final JsonDetailRow.JsonDetailRowBuilder<JsonCustomsArticleDetail> detailRowBuilder = JsonDetailRow.<JsonCustomsArticleDetail>builder()
					.lineNo(lineNo);

			detailRowBuilder
					.detail(JsonCustomsArticleDetail.builder().kindId(JsonCustomsArticleDetailKind.QUANTITY).value(content.getShippedQuantity().getValue().toPlainString()).build())
					.detail(JsonCustomsArticleDetail.builder().kindId(JsonCustomsArticleDetailKind.UNIT_OF_MEASURE).value(content.getShippedQuantity().getUomCode()).build())
					.detail(JsonCustomsArticleDetail.builder().kindId(JsonCustomsArticleDetailKind.DESCRIPTION_OF_GOODS).value(content.getProductName()).build())
					.detail(JsonCustomsArticleDetail.builder().kindId(JsonCustomsArticleDetailKind.ARTICLE_NO).value(content.getShipmentOrderItemId()).build())
					.detail(JsonCustomsArticleDetail.builder().kindId(JsonCustomsArticleDetailKind.UNIT_VALUE).value(content.getUnitPrice().getAmount().toPlainString()).build())
					.detail(JsonCustomsArticleDetail.builder().kindId(JsonCustomsArticleDetailKind.TOTAL_VALUE).value(content.getTotalValue().getAmount().toPlainString()).build())
					.detail(JsonCustomsArticleDetail.builder().kindId(JsonCustomsArticleDetailKind.CUSTOMS_ARTICLE_CURRENCY).value(content.getTotalValue().getCurrencyCode()).build())
					.detail(JsonCustomsArticleDetail.builder().kindId(JsonCustomsArticleDetailKind.COUNTRY_OF_ORIGIN).value(countryOfOrigin).build());

			customsArticleInfoBuilder.row(detailRowBuilder.build());
		}

		return customsArticleInfoBuilder.build();
	}
}