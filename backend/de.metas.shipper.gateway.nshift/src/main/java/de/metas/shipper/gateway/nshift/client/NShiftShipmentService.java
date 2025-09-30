package de.metas.shipper.gateway.nshift.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Stopwatch;
import de.metas.common.delivery.v1.json.request.JsonDeliveryRequest;
import de.metas.common.delivery.v1.json.response.JsonDeliveryResponse;
import de.metas.logging.LogManager;
import de.metas.shipper.gateway.nshift.config.NShiftConfig;
import de.metas.shipper.gateway.nshift.json.JsonAddress;
import de.metas.shipper.gateway.nshift.json.JsonAddressKind;
import de.metas.shipper.gateway.nshift.json.JsonCustomsArticleDetail;
import de.metas.shipper.gateway.nshift.json.JsonCustomsArticleDetailKind;
import de.metas.shipper.gateway.nshift.json.JsonCustomsArticleInfo;
import de.metas.shipper.gateway.nshift.json.JsonDetailRow;
import de.metas.shipper.gateway.nshift.json.JsonLabelType;
import de.metas.shipper.gateway.nshift.json.JsonLine;
import de.metas.shipper.gateway.nshift.json.JsonLineReference;
import de.metas.shipper.gateway.nshift.json.JsonLineReferenceKind;
import de.metas.shipper.gateway.nshift.json.JsonShipmentData;
import de.metas.shipper.gateway.nshift.json.JsonShipmentOptions;
import de.metas.shipper.gateway.nshift.json.JsonShipmentRequest;
import de.metas.shipper.gateway.nshift.json.JsonShipmentResponse;
import de.metas.shipper.gateway.spi.exceptions.ShipperGatewayException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class NShiftShipmentService
{

	private static final Logger logger = LogManager.getLogger(NShiftShipmentService.class);
	private static final String CREATE_SHIPMENT_ENDPOINT = "/ShipServer/{ID}/Shipments";

	@NonNull
	private final NShiftConfig config;
	@NonNull
	private final RestTemplate restTemplate;
	@NonNull
	private final ObjectMapper objectMapper;

	public JsonDeliveryResponse createShipment(@NonNull final JsonDeliveryRequest deliveryRequest)
	{
		logger.debug("Creating shipment for request: {}", deliveryRequest);
		final JsonShipmentRequest requestBody = buildShipmentRequest(deliveryRequest);

		logRequestAsJson(requestBody);

		final JsonShipmentResponse response = callNShift(requestBody);

		logger.debug("Successfully received nShift response: {}", response);
		return JsonDeliveryResponse.builder()
                .build();
	}

	private JsonShipmentResponse callNShift(@NonNull final JsonShipmentRequest request)
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();
		try
		{
			final String responseJson = callNShiftWith(request);
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

	private String callNShiftWith(@NonNull final JsonShipmentRequest request)
	{
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		httpHeaders.set("Authorization", getAuthorizationHeader());

		try
		{
			final String url = config.getBaseUrl() + CREATE_SHIPMENT_ENDPOINT;
			final HttpEntity<JsonShipmentRequest> entity = new HttpEntity<>(request, httpHeaders);
			final ResponseEntity<String> result = restTemplate.postForEntity(url, entity, String.class, config.getActorId());

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

	private @NotNull String getAuthorizationHeader()
	{
		final String auth = config.getUsername() + ":" + config.getPassword();
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
		// TODO: Map the DeliveryOrder to the JsonShipmentRequest instead of using hardcoded data.
		final JsonShipmentOptions options = JsonShipmentOptions.builder()
				.submit(true)
				.labelType(JsonLabelType.PDF)
				.workstationID(UUID.randomUUID())
				.requiredDeliveryDate(LocalDateTime.now().plusDays(3).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
				.validatePostCode(true)
				.build();

		final JsonCustomsArticleInfo customsArticleGroup = JsonCustomsArticleInfo.builder()
				.row(JsonDetailRow.<JsonCustomsArticleDetail>builder()
						.lineNo(1)
						.detail(JsonCustomsArticleDetail.builder().kindId(JsonCustomsArticleDetailKind.QUANTITY).value("1").build())
						.detail(JsonCustomsArticleDetail.builder().kindId(JsonCustomsArticleDetailKind.UNIT_OF_MEASURE).value("KG").build())
						.detail(JsonCustomsArticleDetail.builder().kindId(JsonCustomsArticleDetailKind.DESCRIPTION_OF_GOODS).value("Some other Item").build())
						.detail(JsonCustomsArticleDetail.builder().kindId(JsonCustomsArticleDetailKind.COUNTRY_OF_ORIGIN).value("DK").build())
						.detail(JsonCustomsArticleDetail.builder().kindId(JsonCustomsArticleDetailKind.UNIT_VALUE).value("100,00").build())
						.detail(JsonCustomsArticleDetail.builder().kindId(JsonCustomsArticleDetailKind.ARTICLE_NO).value("123456").build())
						.detail(JsonCustomsArticleDetail.builder().kindId(JsonCustomsArticleDetailKind.CUSTOMS_ARTICLE_COMMODITY_CODE).value("123456").build())
						.detail(JsonCustomsArticleDetail.builder().kindId(JsonCustomsArticleDetailKind.CUSTOMS_ARTICLE_CURRENCY).value("DKK").build())
						.detail(JsonCustomsArticleDetail.builder().kindId(JsonCustomsArticleDetailKind.TOTAL_VALUE).value("100,00").build())
						.build())
				.build();

		final JsonShipmentData data = JsonShipmentData.builder()
				.prodConceptID(1782)
				.orderNo("Test Shipment 1")
				.address(JsonAddress.builder()
						.kind(JsonAddressKind.RECEIVER)
						.name1("Test Receiver Name1")
						.street1("Test Road 1")
						.postCode("7400")
						.city("Herning")
						.countryCode("DK")
						.phone("12341234")
						.mobile("12341234")
						.email("noreply@nshift.com")
						.attention("Test Attention")
						.build())
				.address(JsonAddress.builder()
						.kind(JsonAddressKind.SENDER)
						.name1("Test Sender Name1")
						.street1("Test Road 1")
						.postCode("6400")
						.city("Sønderborg")
						.countryCode("DK")
						.phone("12341234")
						.mobile("12341234")
						.email("noreply@nshift.com")
						.attention("Test Attention")
						.build())
				.line(JsonLine.builder()
						.number(1)
						.pkgWeight(10000)
						.height(100)
						.length(100)
						.width(100)
						.goodsTypeID(13)
						.reference(JsonLineReference.builder()
								.kind(JsonLineReferenceKind.ESRK_CONTENTS)
								.value("Test Shipment - Do not process !")
								.build())
						.build())
				.detailGroup(customsArticleGroup)
				.build();

		return JsonShipmentRequest.builder()
				.options(options)
				.data(data)
				.build();
	}
}