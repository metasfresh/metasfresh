package de.metas.shipper.nshift;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Stopwatch;
import de.metas.common.delivery.v1.json.request.JsonShipperConfig;
import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import static de.metas.shipper.nshift.NShiftClientConfig.NSHIFT_OBJECT_MAPPER;
import static de.metas.shipper.nshift.NShiftClientConfig.NSHIFT_REST_TEMPLATE;

@Component
class NShiftRestClient
{
	private static final Logger logger = LogManager.getLogger(NShiftRestClient.class);

	@NonNull private final RestTemplate restTemplate;
	@NonNull private final ObjectMapper objectMapper;

	public NShiftRestClient(
			@Qualifier(NSHIFT_REST_TEMPLATE) @NonNull final RestTemplate restTemplate,
			@Qualifier(NSHIFT_OBJECT_MAPPER) @NonNull final ObjectMapper objectMapper)
	{
		this.restTemplate = restTemplate;
		this.objectMapper = objectMapper;
	}

	public <T_Req, T_Resp> T_Resp post(
			@NonNull final String endpoint,
			@NonNull final T_Req request,
			@NonNull final JsonShipperConfig config,
			@NonNull final Class<T_Resp> responseClass)
	{

		final Stopwatch stopwatch = Stopwatch.createStarted();
		final HttpHeaders httpHeaders = createHttpHeaders(config);
		final String url = config.getUrl() + endpoint;
		final HttpEntity<T_Req> entity = new HttpEntity<>(request, httpHeaders);
		final String actorId = config.getAdditionalPropertyNotNull(NShiftConstants.ACTOR_ID);

		logRequestAsJson(request);

		try
		{
			final ResponseEntity<String> result = restTemplate.postForEntity(url, entity, String.class, actorId);

			if (!result.getStatusCode().is2xxSuccessful() || result.getBody() == null)
			{
				throw createApiException(request, result.getStatusCode(), String.valueOf(result.getBody()));
			}

			final String responseJson = result.getBody();
			logger.info("nShift API call successful. Duration: {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
			logger.trace("nShift response JSON: {}", responseJson);
			return objectMapper.readValue(responseJson, responseClass);
		}
		catch (final HttpClientErrorException e)
		{
			throw createApiException(request, e.getStatusCode(), e.getResponseBodyAsString());
		}
		catch (final Throwable throwable)
		{
			logger.error("nShift API call failed. Duration: {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS), throwable);
			throw new RuntimeException(throwable);
		}
	}

	private HttpHeaders createHttpHeaders(@NonNull final JsonShipperConfig config)
	{
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		httpHeaders.set("Authorization", getAuthorizationHeader(config));
		return httpHeaders;
	}

	private String getAuthorizationHeader(@NonNull final JsonShipperConfig config)
	{
		final String auth = config.getUsername() + ":" + config.getPassword();
		return "Basic " + Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
	}

	private <T_Req> void logRequestAsJson(@NonNull final T_Req requestBody)
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
		catch (final JsonProcessingException ex)
		{
			logger.trace("Could not serialize nShift request to JSON for logging", ex);
		}
	}

	private <T_Req> RuntimeException createApiException(
			@NonNull final T_Req request, @NonNull final HttpStatus statusCode, @NonNull final String responseBody)
	{
		final StringBuilder sb = new StringBuilder();
		sb.append("nShift API call failed with status code ").append(statusCode).append("\n");
		sb.append("Additional information's:\n");
		sb.append("Response body: ").append(responseBody).append("\n");

		try
		{
			final String requestAsJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(request);
			sb.append("nShiftRequest: ").append(requestAsJson).append("\n");
			return new RuntimeException(sb.toString());
		}
		catch (final JsonProcessingException ex)
		{
			logger.warn("Failed to serialize nShift request for exception details", ex);
			return new RuntimeException(sb.toString(), ex);
		}
	}
}
