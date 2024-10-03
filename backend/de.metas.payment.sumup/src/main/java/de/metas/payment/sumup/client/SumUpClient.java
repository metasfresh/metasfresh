package de.metas.payment.sumup.client;

import com.google.common.collect.ImmutableList;
import de.metas.payment.sumup.SumUpCardReaderExternalId;
import de.metas.payment.sumup.client.json.GetReadersResponse;
import de.metas.payment.sumup.client.json.PairReaderRequest;
import de.metas.payment.sumup.client.json.PairReaderResponse;
import lombok.Builder;
import lombok.NonNull;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Duration;

public class SumUpClient
{
	private static final String BASE_URL = "https://api.sumup.com/v0.1";

	@NonNull String apiKey;
	@NonNull String merchantCode;

	@NonNull private final RestTemplate restTemplate;

	@Builder
	private SumUpClient(
			@NonNull final String apiKey,
			@NonNull final String merchantCode)
	{
		this.apiKey = apiKey;
		this.merchantCode = merchantCode;

		this.restTemplate = new RestTemplateBuilder()
				.defaultHeader("Authorization", "Bearer " + apiKey)
				.setConnectTimeout(Duration.ofSeconds(60))
				.setReadTimeout(Duration.ofSeconds(60))
				.build();
	}

	/**
	 * @implSpec <a href="https://developer.sumup.com/api/readers/list-readers">spec</a>
	 */
	public GetReadersResponse getCardReaders()
	{
		final String uri = UriComponentsBuilder.fromHttpUrl(BASE_URL)
				.pathSegment("merchants", merchantCode, "readers")
				.toUriString();

		final HttpEntity<?> request = new HttpEntity<>(newHttpHeaders());

		final ResponseEntity<GetReadersResponse> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, request, GetReadersResponse.class);
		return responseEntity.getBody();
	}

	/**
	 * @implSpec <a href="https://developer.sumup.com/api/readers/create-reader">spec</a>
	 */
	public PairReaderResponse pairCardReader(@NonNull PairReaderRequest request)
	{
		final String uri = UriComponentsBuilder.fromHttpUrl(BASE_URL)
				.pathSegment("merchants", merchantCode, "readers")
				.toUriString();

		final HttpEntity<?> httpRequest = new HttpEntity<>(request, newHttpHeaders());

		final ResponseEntity<PairReaderResponse> responseEntity = restTemplate.exchange(uri, HttpMethod.POST, httpRequest, PairReaderResponse.class);
		return responseEntity.getBody();
	}

	/**
	 * @implSpec <a href="https://developer.sumup.com/api/readers/delete-reader">spec</a>
	 */
	public void deleteCardReader(@NonNull SumUpCardReaderExternalId id)
	{
		final String uri = UriComponentsBuilder.fromHttpUrl(BASE_URL)
				.pathSegment("merchants", merchantCode, "readers", id.getAsString())
				.toUriString();

		final HttpEntity<?> httpRequest = new HttpEntity<>(newHttpHeaders());

		final ResponseEntity<PairReaderResponse> responseEntity = restTemplate.exchange(uri, HttpMethod.DELETE, httpRequest, PairReaderResponse.class);
	}

	private HttpHeaders newHttpHeaders()
	{
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(ImmutableList.of(MediaType.APPLICATION_JSON));
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey);
		return httpHeaders;
	}
}
