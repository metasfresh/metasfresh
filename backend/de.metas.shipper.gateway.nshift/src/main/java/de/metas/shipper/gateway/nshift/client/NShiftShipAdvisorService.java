package de.metas.shipper.gateway.nshift.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Stopwatch;
import de.metas.common.delivery.v1.json.request.JsonDeliveryAdvisorItem;
import de.metas.common.delivery.v1.json.request.JsonDeliveryAdvisorRequest;
import de.metas.common.delivery.v1.json.request.JsonShipperConfig;
import de.metas.common.delivery.v1.json.response.JsonDeliveryAdvisorResponse;
import de.metas.common.util.CoalesceUtil;
import de.metas.logging.LogManager;
import de.metas.shipper.gateway.nshift.json.JsonAddress;
import de.metas.shipper.gateway.nshift.json.JsonAddressKind;
import de.metas.shipper.gateway.nshift.json.JsonLine;
import de.metas.shipper.gateway.nshift.json.JsonShipmentData;
import de.metas.shipper.gateway.nshift.json.JsonShipmentOptions;
import de.metas.shipper.gateway.nshift.json.request.JsonShipAdvisorRequest;
import de.metas.shipper.gateway.nshift.json.response.JsonShipAdvisorResponse;
import de.metas.shipper.gateway.nshift.json.response.JsonShipAdvisorResponseGoodsType;
import de.metas.shipper.gateway.nshift.json.response.JsonShipAdvisorResponseProduct;
import de.metas.shipper.gateway.nshift.json.response.JsonShipAdvisorResponseService;
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
import java.util.Base64;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import static de.metas.shipper.gateway.nshift.client.NShiftClientConfig.NSHIFT_OBJECT_MAPPER;
import static de.metas.shipper.gateway.nshift.client.NShiftClientConfig.NSHIFT_REST_TEMPLATE;

@Service
public class NShiftShipAdvisorService
{

	private static final Logger logger = LogManager.getLogger(NShiftShipAdvisorService.class);
	private static final String CREATE_SHIPMENT_ENDPOINT = "/ShipServer/{ID}/shipAdvises";

	@NonNull private final RestTemplate restTemplate;
	@NonNull private final ObjectMapper objectMapper;

	public NShiftShipAdvisorService(
			@Qualifier(NSHIFT_REST_TEMPLATE) @NonNull final RestTemplate restTemplate,
			@Qualifier(NSHIFT_OBJECT_MAPPER) @NonNull final ObjectMapper objectMapper)
	{
		this.restTemplate = restTemplate;
		this.objectMapper = objectMapper;
	}

	public JsonDeliveryAdvisorResponse getShipAdvises(@NonNull final JsonDeliveryAdvisorRequest deliveryAdvisorRequest)
	{
		try
		{
			logger.debug("Getting Ship Advises for request: {}", deliveryAdvisorRequest);
			final JsonShipAdvisorRequest requestBody = buildRequest(deliveryAdvisorRequest);

			logRequestAsJson(requestBody);

			final JsonShipAdvisorResponse response = callNShift(requestBody, deliveryAdvisorRequest.getShipperConfig());

			logger.debug("Successfully received nShift response: {}", response);
			return buildJsonDeliveryResponse(response, deliveryAdvisorRequest.getId());
		}
		catch (final Throwable throwable)
		{
			return JsonDeliveryAdvisorResponse.builder()
					.requestId(deliveryAdvisorRequest.getId())
					.errorMessage(throwable.getMessage())
					.build();
		}
	}

	private JsonShipAdvisorResponse callNShift(@NonNull final JsonShipAdvisorRequest request, @NonNull final JsonShipperConfig config)
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();
		try
		{
			final String responseJson = callNShiftWith(request, config);
			logger.info("nShift API call successful. Duration: {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
			logger.trace("nShift response JSON: {}", responseJson);
			return objectMapper.readValue(responseJson, JsonShipAdvisorResponse.class);
		}
		catch (final Throwable throwable)
		{
			logger.error("nShift API call failed. Duration: {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS), throwable);
			throw AdempiereException.wrapIfNeeded(throwable);
		}
	}

	private String callNShiftWith(@NonNull final JsonShipAdvisorRequest request, @NonNull final JsonShipperConfig config)
	{
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		httpHeaders.set("Authorization", getAuthorizationHeader(config));

		try
		{
			final String url = config.getUrl() + CREATE_SHIPMENT_ENDPOINT;
			final HttpEntity<JsonShipAdvisorRequest> entity = new HttpEntity<>(request, httpHeaders);
			final String actorId = getActorId(config);
			final ResponseEntity<String> result = restTemplate.postForEntity(url, entity, String.class, actorId);

			if (!result.getStatusCode().is2xxSuccessful() || result.getBody() == null)
			{
				throw createException(request, result.getStatusCode(), String.valueOf(result.getBody()));
			}
			return result.getBody();
		}
		catch (final HttpClientErrorException e)
		{
			throw createException(request, e.getStatusCode(), e.getResponseBodyAsString());
		}
	}

	@NonNull
	private String getActorId(@NonNull final JsonShipperConfig config)
	{
		return Check.assumeNotNull(config.getAdditionalProperties().get(NShiftConstants.ACTOR_ID),
				"No actorId found in (ShipperConfig.additionalProperties.actorId): {} "
				, config.getAdditionalProperties()
		);
	}

	private @NotNull String getAuthorizationHeader(@NonNull final JsonShipperConfig config)
	{
		final String auth = config.getUsername() + ":" + config.getPassword();
		return "Basic " + Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
	}

	private AdempiereException createException(@NonNull final JsonShipAdvisorRequest request, @NonNull final HttpStatus statusCode, @NonNull final String responseBody)
	{
		final AdempiereException cause = new AdempiereException("nShift API call failed with status code " + statusCode);

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

	private void logRequestAsJson(@NonNull final JsonShipAdvisorRequest requestBody)
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

	private JsonShipAdvisorRequest buildRequest(@NonNull final JsonDeliveryAdvisorRequest deliveryAdvisorRequest)
	{
		final JsonShipmentOptions options = JsonShipmentOptions.builder()
				.serviceLevel(getServiceLevel(deliveryAdvisorRequest.getShipperConfig()))
				.build();

		final JsonShipmentData.JsonShipmentDataBuilder dataBuilder = JsonShipmentData.builder();

		// Add Addresses
		dataBuilder.address(buildNShiftAddressBuilder(deliveryAdvisorRequest.getPickupAddress(), JsonAddressKind.SENDER)
				.attention(deliveryAdvisorRequest.getPickupAddress().getCompanyName1())
				.build());
		final JsonAddress.JsonAddressBuilder receiverAddressBuilder = buildNShiftAddressBuilder(deliveryAdvisorRequest.getDeliveryAddress(), JsonAddressKind.RECEIVER);
		final de.metas.common.delivery.v1.json.JsonContact deliveryContact = deliveryAdvisorRequest.getDeliveryContact();
		if (deliveryContact != null)
		{
			if (Check.isNotBlank(deliveryContact.getPhone()))
			{
				receiverAddressBuilder.phone(deliveryContact.getPhone());
			}
			if (Check.isNotBlank(deliveryContact.getEmailAddress()))
			{
				receiverAddressBuilder.email(deliveryContact.getEmailAddress());
			}
			receiverAddressBuilder.attention(deliveryContact.getName());
		}
		else
		{
			final String attention = CoalesceUtil.coalesceNotNull(
					deliveryAdvisorRequest.getDeliveryAddress().getAdditionalAddressInfo(),
					deliveryAdvisorRequest.getDeliveryAddress().getCompanyName2(),
					deliveryAdvisorRequest.getDeliveryAddress().getCompanyName1());
			receiverAddressBuilder.attention(attention);
		}
		dataBuilder.address(receiverAddressBuilder.build());

		dataBuilder.line(buildNShiftLine(deliveryAdvisorRequest.getItem()));


		return JsonShipAdvisorRequest.builder()
				.options(options)
				.data(dataBuilder.build())
				.build();
	}

	private JsonAddress.JsonAddressBuilder buildNShiftAddressBuilder(@NonNull final de.metas.common.delivery.v1.json.JsonAddress commonAddress, @NonNull final JsonAddressKind kind)
	{
		String street1 = commonAddress.getStreet();
		if (Check.isNotBlank(commonAddress.getHouseNo()))
		{
			street1 = street1 + " " + commonAddress.getHouseNo();
		}

		return JsonAddress.builder()
				.kind(kind)
				.name1(commonAddress.getCompanyName1())
				.name2(commonAddress.getCompanyName2())
				.street1(street1)
				.street2(commonAddress.getAdditionalAddressInfo())
				.postCode(commonAddress.getZipCode())
				.city(commonAddress.getCity())
				.countryCode(commonAddress.getCountry());
	}

	private JsonLine buildNShiftLine(@NonNull final JsonDeliveryAdvisorItem item)
	{
		// nShift expects weight in grams and dimensions in millimeters.
		final int weightGrams = item.getGrossWeightKg().multiply(BigDecimal.valueOf(1000)).intValue();
		final int lengthMM = item.getPackageDimensions().getLengthInCM() * 10;
		final int widthMM = item.getPackageDimensions().getWidthInCM() * 10;
		final int heightMM = item.getPackageDimensions().getHeightInCM() * 10;

		return JsonLine.builder()
				.number(1)
				.pkgWeight(weightGrams)
				.lineWeight(weightGrams)
				.length(lengthMM)
				.width(widthMM)
				.height(heightMM)
				.build();
	}

	private JsonDeliveryAdvisorResponse buildJsonDeliveryResponse(@NonNull final JsonShipAdvisorResponse response, @NonNull final String requestId)
	{
		Check.assumeEquals(response.getProducts().size(), 1, "response should only contain 1 product");
		final JsonShipAdvisorResponseProduct product = response.getProducts().get(0);
		final JsonDeliveryAdvisorResponse.JsonDeliveryAdvisorResponseBuilder responseBuilder = JsonDeliveryAdvisorResponse.builder()
				.requestId(requestId)
				.responseItem(NShiftConstants.SHIPPER_PRODUCT, String.valueOf(product.getProdConceptID()));

		final JsonShipAdvisorResponseGoodsType productGoodsType = product.getProductGoodsType();
		if (productGoodsType != null)
		{
			responseBuilder.responseItem(NShiftConstants.GOODS_TYPE_ID, String.valueOf(productGoodsType.getGoodsTypeId()));
			responseBuilder.responseItem(NShiftConstants.GOODS_TYPE_NAME, productGoodsType.getGoodsTypeName());
		}

		Check.assume(product.getServices().size() <= 1, "response should contain max 1 service");
		if (!product.getServices().isEmpty())
		{
			final JsonShipAdvisorResponseService service = product.getServices().get(0);
			responseBuilder.responseItem(NShiftConstants.SERVICE_ID, String.valueOf(service.getServiceId()));
			responseBuilder.responseItem(NShiftConstants.SERVICE_NAME, String.valueOf(service.getServiceId()));
		}

		return responseBuilder.build();
	}

	@NonNull
	private String getServiceLevel(@NonNull final JsonShipperConfig config)
	{
		return Check.assumeNotNull(config.getAdditionalProperties().get("ServiceLevel"),
				"No Service Level found in (ShipperConfig.additionalProperties.ServiceLevel): {} "
				, config.getAdditionalProperties()
		);
	}
}