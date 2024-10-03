package de.metas.payment.sumup.client;

import com.google.common.collect.ImmutableList;
import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.payment.sumup.SumUpCardReaderExternalId;
import de.metas.payment.sumup.SumUpConfigId;
import de.metas.payment.sumup.SumUpLogRequest;
import de.metas.payment.sumup.client.json.GetReadersResponse;
import de.metas.payment.sumup.client.json.PairReaderRequest;
import de.metas.payment.sumup.client.json.PairReaderResponse;
import de.metas.payment.sumup.repository.SumUpLogRepository;
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

	@NonNull private final SumUpConfigId configId;
	@NonNull private final String apiKey;
	@NonNull private final String merchantCode;

	@NonNull private final RestTemplate restTemplate;

	@Builder
	private SumUpClient(
			@NonNull final IErrorManager errorManager,
			@NonNull final SumUpLogRepository logRepository,
			@NonNull final SumUpConfigId configId,
			@NonNull final String apiKey,
			@NonNull final String merchantCode)
	{
		this.errorManager = errorManager;
		this.logRepository = logRepository;
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
			@NonNull final Class<T> responseType)
	{
		final SumUpLogRequest.SumUpLogRequestBuilder log = newLogRequest()
				.method(method)
				.requestURI(uri)
				.requestBody(requestBody);

		try
		{
			final HttpEntity<?> request = new HttpEntity<>(requestBody, newHttpHeaders());

			final ResponseEntity<T> responseEntity = restTemplate.exchange(uri, method, request, responseType);
			final T body = responseEntity.getBody();

			log.responseCode(responseEntity.getStatusCode());
			log.responseBody(body);

			return body;
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
	public GetReadersResponse getCardReaders()
	{
		final String uri = UriComponentsBuilder.fromHttpUrl(BASE_URL)
				.pathSegment("merchants", merchantCode, "readers")
				.toUriString();

		return httpCall(uri, HttpMethod.GET, null, GetReadersResponse.class);
	}

	/**
	 * @implSpec <a href="https://developer.sumup.com/api/readers/create-reader">spec</a>
	 */
	public void pairCardReader(@NonNull PairReaderRequest request)
	{
		final String uri = UriComponentsBuilder.fromHttpUrl(BASE_URL)
				.pathSegment("merchants", merchantCode, "readers")
				.toUriString();

		httpCall(uri, HttpMethod.POST, request, PairReaderResponse.class);
	}

	/**
	 * @implSpec <a href="https://developer.sumup.com/api/readers/delete-reader">spec</a>
	 */
	public void deleteCardReader(@NonNull SumUpCardReaderExternalId id)
	{
		final String uri = UriComponentsBuilder.fromHttpUrl(BASE_URL)
				.pathSegment("merchants", merchantCode, "readers", id.getAsString())
				.toUriString();

		httpCall(uri, HttpMethod.DELETE, null, Object.class);
	}
}
