package de.metas.payment.sumup.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.payment.sumup.SumUpCardReaderExternalId;
import de.metas.payment.sumup.SumUpClientTransactionId;
import de.metas.payment.sumup.SumUpConfigId;
import de.metas.payment.sumup.SumUpLogRequest;
import de.metas.payment.sumup.SumUpMerchantCode;
import de.metas.payment.sumup.client.json.JsonReaderCheckoutRequest;
import de.metas.payment.sumup.client.json.JsonReaderCheckoutResponse;
import de.metas.payment.sumup.client.json.JsonGetReadersResponse;
import de.metas.payment.sumup.client.json.JsonGetTransactionResponse;
import de.metas.payment.sumup.client.json.JsonPairReaderRequest;
import de.metas.payment.sumup.client.json.JsonPairReaderResponse;
import de.metas.payment.sumup.repository.SumUpLogRepository;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Nullable;
import java.time.Duration;

public class SumUpClient
{
	private static final String BASE_URL = "https://api.sumup.com/v0.1";

	@NonNull private final IErrorManager errorManager;
	@NonNull private final SumUpLogRepository logRepository;
	@NonNull private final ObjectMapper jsonObjectMapper;

	@NonNull private final SumUpConfigId configId;
	@NonNull private final String apiKey;
	@NonNull private final SumUpMerchantCode merchantCode;

	@NonNull private final RestTemplate restTemplate;

	@Builder
	private SumUpClient(
			@NonNull final IErrorManager errorManager,
			@NonNull final SumUpLogRepository logRepository,
			@NonNull final ObjectMapper jsonObjectMapper,
			//
			@NonNull final SumUpConfigId configId,
			@NonNull final String apiKey,
			@NonNull final SumUpMerchantCode merchantCode)
	{
		this.errorManager = errorManager;
		this.logRepository = logRepository;
		this.jsonObjectMapper = jsonObjectMapper;

		this.configId = configId;
		this.apiKey = apiKey;
		this.merchantCode = merchantCode;

		this.restTemplate = new RestTemplateBuilder()
				.defaultHeader("Authorization", "Bearer " + apiKey)
				.setConnectTimeout(Duration.ofSeconds(60))
				.setReadTimeout(Duration.ofSeconds(60))
				.build();
	}

	private SumUpLogRequest.SumUpLogRequestBuilder newLogRequest()
	{
		return SumUpLogRequest.builder()
				.configId(configId)
				.merchantCode(merchantCode);
	}

	private HttpHeaders newHttpHeaders()
	{
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(ImmutableList.of(MediaType.APPLICATION_JSON));
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey);
		return httpHeaders;
	}

	@SuppressWarnings("SameParameterValue")
	private <T> T httpCall(
			@NonNull final String uri,
			@NonNull final HttpMethod method,
			@Nullable final Object requestBody,
			@Nullable final Class<T> responseType)
	{
		final SumUpLogRequest.SumUpLogRequestBuilder log = newLogRequest()
				.method(method)
				.requestURI(uri)
				.requestBody(requestBody);

		try
		{
			final HttpEntity<?> request = new HttpEntity<>(requestBody, newHttpHeaders());

			final ResponseEntity<String> responseEntity = restTemplate.exchange(uri, method, request, String.class);
			final String bodyJson = responseEntity.getBody();

			log.responseCode(responseEntity.getStatusCode());
			log.responseBody(StringUtils.trimBlankToNull(bodyJson));

			if (responseType != null && !responseType.equals(Void.class))
			{
				return jsonObjectMapper.readValue(bodyJson, responseType);
			}
			else
			{
				return null;
			}
		}
		catch (Exception ex)
		{
			final AdempiereException metasfreshException = AdempiereException.wrapIfNeeded(ex);
			final AdIssueId adIssueId = errorManager.createIssue(metasfreshException);
			log.adIssueId(adIssueId);

			throw metasfreshException;
		}
		finally
		{
			logRepository.createLog(log.build());
		}
	}

	/**
	 * @implSpec <a href="https://developer.sumup.com/api/readers/list-readers">spec</a>
	 */
	public JsonGetReadersResponse getCardReaders()
	{
		final String uri = UriComponentsBuilder.fromHttpUrl(BASE_URL)
				.pathSegment("merchants", merchantCode.getAsString(), "readers")
				.toUriString();

		return httpCall(uri, HttpMethod.GET, null, JsonGetReadersResponse.class);
	}

	/**
	 * @implSpec <a href="https://developer.sumup.com/api/readers/create-reader">spec</a>
	 */
	public void pairCardReader(@NonNull JsonPairReaderRequest request)
	{
		final String uri = UriComponentsBuilder.fromHttpUrl(BASE_URL)
				.pathSegment("merchants", merchantCode.getAsString(), "readers")
				.toUriString();

		httpCall(uri, HttpMethod.POST, request, JsonPairReaderResponse.class);
	}

	/**
	 * @implSpec <a href="https://developer.sumup.com/api/readers/delete-reader">spec</a>
	 */
	public void deleteCardReader(@NonNull SumUpCardReaderExternalId id)
	{
		final String uri = UriComponentsBuilder.fromHttpUrl(BASE_URL)
				.pathSegment("merchants", merchantCode.getAsString(), "readers", id.getAsString())
				.toUriString();

		httpCall(uri, HttpMethod.DELETE, null, null);
	}

	/**
	 * @implSpec <a href="https://developer.sumup.com/api/readers/create-reader-checkout">spec</a>
	 */
	public JsonReaderCheckoutResponse cardReaderCheckout(@NonNull final SumUpCardReaderExternalId id, @NonNull final JsonReaderCheckoutRequest request)
	{
		final String uri = UriComponentsBuilder.fromHttpUrl(BASE_URL)
				.pathSegment("merchants", merchantCode.getAsString(), "readers", id.getAsString(), "checkout")
				.toUriString();

		return httpCall(uri, HttpMethod.POST, request, JsonReaderCheckoutResponse.class);
	}

	/**
	 * @implSpec <a href="https://developer.sumup.com/api/transactions/get">spec</a>
	 */
	public JsonGetTransactionResponse getTransactionById(@NonNull final SumUpClientTransactionId id)
	{
		final String uri = UriComponentsBuilder.fromHttpUrl(BASE_URL)
				.pathSegment("me", "transactions")
				.queryParam("client_transaction_id", id.getAsString())
				.toUriString();

		final String json = httpCall(uri, HttpMethod.GET, null, String.class);

		try
		{
			return jsonObjectMapper.readValue(json, JsonGetTransactionResponse.class)
					.withJson(json);
		}
		catch (JsonProcessingException ex)
		{
			throw AdempiereException.wrapIfNeeded(ex);
		}
	}
}
