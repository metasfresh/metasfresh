package de.metas.payment.sumup.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.payment.sumup.SumUpCallException;
import de.metas.payment.sumup.SumUpCardReaderExternalId;
import de.metas.payment.sumup.SumUpClientTransactionId;
import de.metas.payment.sumup.SumUpConfigId;
import de.metas.payment.sumup.SumUpLogRequest;
import de.metas.payment.sumup.SumUpMerchantCode;
import de.metas.payment.sumup.SumUpPOSRef;
import de.metas.payment.sumup.SumUpTransactionExternalId;
import de.metas.payment.sumup.client.json.JsonGetReadersResponse;
import de.metas.payment.sumup.client.json.JsonGetTransactionResponse;
import de.metas.payment.sumup.client.json.JsonPairReaderRequest;
import de.metas.payment.sumup.client.json.JsonPairReaderResponse;
import de.metas.payment.sumup.client.json.JsonReaderCheckoutRequest;
import de.metas.payment.sumup.client.json.JsonReaderCheckoutResponse;
import de.metas.payment.sumup.repository.SumUpLogRepository;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Setter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.Optional;

public class SumUpClient
{
	private static final String API_V01_SEGMENT = "/v0.1";
	private static final String API_V10_SEGMENT = "/v1.0";
	private static final String API_V21_SEGMENT = "/v2.1";

	@NonNull private final IErrorManager errorManager;
	@NonNull private final SumUpLogRepository logRepository;
	@NonNull private final ObjectMapper jsonObjectMapper;

	@NonNull private final SumUpConfigId configId;
	@NonNull private final String apiKey;
	@NonNull private final SumUpMerchantCode merchantCode;
	/**
	 * Base of SumUp API v0.1 (e.g. {@code https://api.sumup.com/v0.1}), from {@code sumup.baseUrl}: the reader calls.
	 * The transaction lookup uses API v2.1, see {@link #toTransactionsApiBaseUrl(String)}, the refund API v1.0, see
	 * {@link #toRefundsApiBaseUrl(String)}.
	 */
	@NonNull private final String baseUrl;

	@NonNull private final RestTemplate restTemplate;

	@Nullable @Setter private SumUpPOSRef posRef;
	/**
	 * Whether a failed call is recorded as AD_Issue (default); if not, the caller reports the failure itself. The API
	 * log is written either way.
	 */
	@Setter private boolean createIssueOnError = true;

	@Builder
	private SumUpClient(
			@NonNull final IErrorManager errorManager,
			@NonNull final SumUpLogRepository logRepository,
			@NonNull final ObjectMapper jsonObjectMapper,
			//
			@NonNull final SumUpConfigId configId,
			@NonNull final String apiKey,
			@NonNull final SumUpMerchantCode merchantCode,
			@NonNull final String baseUrl,
			@Nullable final RestTemplate restTemplate)
	{
		this.errorManager = errorManager;
		this.logRepository = logRepository;
		this.jsonObjectMapper = jsonObjectMapper;

		this.configId = configId;
		this.apiKey = apiKey;
		this.merchantCode = merchantCode;
		this.baseUrl = baseUrl;

		this.restTemplate = restTemplate != null
				? restTemplate
				: new RestTemplateBuilder()
				.defaultHeader("Authorization", "Bearer " + apiKey)
				.setConnectTimeout(Duration.ofSeconds(60))
				.setReadTimeout(Duration.ofSeconds(60))
				.build();
	}

	/**
	 * @return the base of SumUp API v2.1 (transaction lookup): the given v0.1 base with its trailing {@code /v0.1} replaced by {@code /v2.1}
	 * @throws AdempiereException if the base URL does not end with {@code /v0.1}
	 */
	@VisibleForTesting
	static String toTransactionsApiBaseUrl(@NonNull final String baseUrl)
	{
		return replaceApiVersionSegment(baseUrl, API_V21_SEGMENT);
	}

	/**
	 * @return the base of SumUp API v1.0 (refund): the given v0.1 base with its trailing {@code /v0.1} replaced by {@code /v1.0}
	 * @throws AdempiereException if the base URL does not end with {@code /v0.1}
	 */
	@VisibleForTesting
	static String toRefundsApiBaseUrl(@NonNull final String baseUrl)
	{
		return replaceApiVersionSegment(baseUrl, API_V10_SEGMENT);
	}

	private static String replaceApiVersionSegment(@NonNull final String baseUrl, @NonNull final String apiVersionSegment)
	{
		final String baseUrlNorm = baseUrl.replaceFirst("/+$", "");
		if (!baseUrlNorm.endsWith(API_V01_SEGMENT))
		{
			throw new AdempiereException("sumup.baseUrl must end with " + API_V01_SEGMENT)
					.appendParametersToMessage()
					.setParameter("sumup.baseUrl", baseUrl);
		}
		return baseUrlNorm.substring(0, baseUrlNorm.length() - API_V01_SEGMENT.length()) + apiVersionSegment;
	}

	private SumUpLogRequest.SumUpLogRequestBuilder newLogRequest()
	{
		return SumUpLogRequest.builder()
				.configId(configId)
				.merchantCode(merchantCode)
				.posRef(posRef);
	}

	private HttpHeaders newHttpHeaders()
	{
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(ImmutableList.of(MediaType.APPLICATION_JSON));
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey);
		return httpHeaders;
	}

	private <T> T httpCall(
			@NonNull final String uri,
			@NonNull final HttpMethod method,
			@Nullable final Object requestBody,
			@Nullable final Class<T> responseType)
	{
		return httpCall(uri, method, requestBody, responseType, false);
	}

	/**
	 * @param isNotFoundAnAnswer whether SumUp answering that the resource does not exist ({@link #isResourceNotFound}) is an
	 *                           answer, not a failed call: then null is returned, no AD_Issue is recorded, and the call is
	 *                           logged with its 404
	 */
	@Nullable
	private <T> T httpCall(
			@NonNull final String uri,
			@NonNull final HttpMethod method,
			@Nullable final Object requestBody,
			@Nullable final Class<T> responseType,
			final boolean isNotFoundAnAnswer)
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

			if (responseType == null || Void.class.equals(responseType))
			{
				return null;
			}
			if (String.class.equals(responseType))
			{
				// never null, so that null tells only "not found" (see isNotFoundAnAnswer)
				//noinspection unchecked
				return (T)(bodyJson != null ? bodyJson : "");
			}
			else
			{
				return jsonObjectMapper.readValue(bodyJson, responseType);
			}
		}
		catch (Exception ex)
		{
			if (ex instanceof HttpStatusCodeException)
			{
				final HttpStatusCodeException httpEx = (HttpStatusCodeException)ex;
				log.responseCode(httpEx.getStatusCode());
				log.responseBody(StringUtils.trimBlankToNull(httpEx.getResponseBodyAsString()));
				if (isNotFoundAnAnswer && isResourceNotFound(httpEx))
				{
					return null;
				}
			}

			// 4xx: SumUp definitely rejected the request. Anything else (5xx, timeout/IO, parse errors)
			// leaves it open whether the request took effect.
			final SumUpCallException metasfreshException = new SumUpCallException(ex, ex instanceof HttpClientErrorException);
			final TableRecordReference settlementRef = posRef != null ? posRef.toPOSInvoiceCardSettlementRecordRefOrNull() : null;
			if (settlementRef != null)
			{
				metasfreshException.setRecord(settlementRef);
			}
			if (createIssueOnError)
			{
				final AdIssueId adIssueId = errorManager.createIssue(metasfreshException);
				log.adIssueId(adIssueId);
			}

			throw metasfreshException;
		}
		finally
		{
			logRepository.createLog(log.build());
		}
	}

	/**
	 * @return whether SumUp answered that the requested resource does not exist: HTTP 404 with {@code error_code}
	 * {@code NOT_FOUND}. Any other 404 (e.g. a wrong base URL or merchant code, answered by something else than the SumUp
	 * API) is a failed call, so that it is not taken for "the card was not presented yet" and goes unnoticed.
	 */
	private boolean isResourceNotFound(@NonNull final HttpStatusCodeException ex)
	{
		if (ex.getStatusCode() != HttpStatus.NOT_FOUND)
		{
			return false;
		}

		try
		{
			return "NOT_FOUND".equals(jsonObjectMapper.readTree(ex.getResponseBodyAsString()).path("error_code").asText(null));
		}
		catch (final Exception parseEx)
		{
			return false;
		}
	}

	/**
	 * @implSpec <a href="https://developer.sumup.com/api/readers/list-readers">spec</a>
	 */
	public JsonGetReadersResponse getCardReaders()
	{
		final String uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
				.pathSegment("merchants", merchantCode.getAsString(), "readers")
				.toUriString();

		return httpCall(uri, HttpMethod.GET, null, JsonGetReadersResponse.class);
	}

	/**
	 * @implSpec <a href="https://developer.sumup.com/api/readers/create-reader">spec</a>
	 */
	public void pairCardReader(@NonNull JsonPairReaderRequest request)
	{
		final String uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
				.pathSegment("merchants", merchantCode.getAsString(), "readers")
				.toUriString();

		httpCall(uri, HttpMethod.POST, request, JsonPairReaderResponse.class);
	}

	/**
	 * @implSpec <a href="https://developer.sumup.com/api/readers/delete-reader">spec</a>
	 */
	public void deleteCardReader(@NonNull SumUpCardReaderExternalId id)
	{
		final String uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
				.pathSegment("merchants", merchantCode.getAsString(), "readers", id.getAsString())
				.toUriString();

		httpCall(uri, HttpMethod.DELETE, null, null);
	}

	/**
	 * @implSpec <a href="https://developer.sumup.com/api/readers/create-reader-checkout">spec</a>
	 */
	public JsonReaderCheckoutResponse cardReaderCheckout(@NonNull final SumUpCardReaderExternalId id, @NonNull final JsonReaderCheckoutRequest request)
	{
		final String uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
				.pathSegment("merchants", merchantCode.getAsString(), "readers", id.getAsString(), "checkout")
				.toUriString();

		return httpCall(uri, HttpMethod.POST, request, JsonReaderCheckoutResponse.class);
	}

	/**
	 * Asks the reader to abort its current checkout. SumUp processes this asynchronously and does not confirm it.
	 *
	 * @implSpec <a href="https://developer.sumup.com/api/readers">spec ("Terminate a Reader Checkout")</a>
	 */
	public void terminateCardReaderCheckout(@NonNull final SumUpCardReaderExternalId id)
	{
		final String uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
				.pathSegment("merchants", merchantCode.getAsString(), "readers", id.getAsString(), "terminate")
				.toUriString();

		httpCall(uri, HttpMethod.POST, null, null);
	}

	/**
	 * Looks up the transaction of a checkout by the client transaction id SumUp gave it. SumUp creates the transaction only
	 * once the card is presented on the reader; until then (and forever, if the reader timed out) it answers HTTP 404.
	 *
	 * @return the transaction, or empty if SumUp answered that it does not know it (HTTP 404 with {@code error_code}
	 * {@code NOT_FOUND}: no AD_Issue, the call is logged); any other 404 is a failed call
	 * @throws SumUpCallException if the call failed otherwise
	 * @implSpec <a href="https://developer.sumup.com/api/transactions/get">spec</a>
	 */
	public Optional<JsonGetTransactionResponse> findTransactionById(@NonNull final SumUpClientTransactionId id)
	{
		return Optional.ofNullable(getTransaction("client_transaction_id", id.getAsString(), true));
	}

	/**
	 * @implSpec <a href="https://developer.sumup.com/api/transactions/get">spec</a>
	 */
	public JsonGetTransactionResponse getTransactionById(@NonNull final SumUpTransactionExternalId id)
	{
		return getTransaction("id", id.getAsString(), false);
	}

	/**
	 * @return the transaction; null only if {@code isNotFoundAnAnswer} and SumUp answered HTTP 404
	 */
	@Nullable
	private JsonGetTransactionResponse getTransaction(
			@NonNull final String queryParamName,
			@NonNull final String queryParamValue,
			final boolean isNotFoundAnAnswer)
	{
		final String uri = UriComponentsBuilder.fromHttpUrl(toTransactionsApiBaseUrl(baseUrl))
				.pathSegment("merchants", merchantCode.getAsString(), "transactions")
				.queryParam(queryParamName, queryParamValue)
				.toUriString();

		final String json = httpCall(uri, HttpMethod.GET, null, String.class, isNotFoundAnAnswer);
		if (json == null)
		{
			return null;
		}

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

	/**
	 * Refunds the transaction's full amount: the request carries no {@code amount}. SumUp answers 201 with an empty object.
	 *
	 * @implSpec <a href="https://developer.sumup.com/api/transactions/refund">spec</a>
	 */
	public void refundTransaction(@NonNull final SumUpTransactionExternalId id)
	{
		final String uri = UriComponentsBuilder.fromHttpUrl(toRefundsApiBaseUrl(baseUrl))
				.pathSegment("merchants", merchantCode.getAsString(), "payments", id.getAsString(), "refunds")
				.toUriString();

		httpCall(uri, HttpMethod.POST, ImmutableMap.of(), null);
	}

}
