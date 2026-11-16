package de.metas.payment.sumup.client;

import de.metas.JsonObjectMapperHolder;
import de.metas.error.IErrorManager;
import de.metas.payment.sumup.SumUpCallException;
import de.metas.payment.sumup.SumUpCardReaderExternalId;
import de.metas.payment.sumup.SumUpClientFactory;
import de.metas.payment.sumup.SumUpClientTransactionId;
import de.metas.payment.sumup.SumUpConfigId;
import de.metas.payment.sumup.SumUpMerchantCode;
import de.metas.payment.sumup.SumUpTransactionExternalId;
import de.metas.payment.sumup.client.json.JsonGetTransactionResponse;
import de.metas.payment.sumup.client.json.JsonReaderCheckoutRequest;
import de.metas.payment.sumup.repository.SumUpLogRepository;
import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.SocketTimeoutException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withBadRequest;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withException;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * Every case is served by a stubbed {@link RestTemplate}; nothing reaches the live SumUp API.
 */
class SumUpClientTest
{
	private static final String BASE_URL = "http://stub/v0.1";
	private static final SumUpCardReaderExternalId READER_ID = SumUpCardReaderExternalId.ofString("R1");

	/**
	 * Body as answered by SumUp's "Retrieve a transaction": a single transaction object, not wrapped.
	 */
	private static final String TRANSACTION_JSON = "{"
			+ "\"id\":\"TX1\","
			+ "\"transaction_code\":\"TCODE1\","
			+ "\"client_transaction_id\":\"CTX1\","
			+ "\"merchant_code\":\"M1\","
			+ "\"timestamp\":\"2026-11-16T08:55:19Z\","
			+ "\"status\":\"SUCCESSFUL\","
			+ "\"amount\":10.0,"
			+ "\"currency\":\"EUR\""
			+ "}";

	private MockRestServiceServer server;
	private IErrorManager errorManager;
	private SumUpClient client;

	@BeforeEach
	void setUp()
	{
		final RestTemplate restTemplate = new RestTemplate();
		server = MockRestServiceServer.bindTo(restTemplate).build();
		errorManager = Mockito.mock(IErrorManager.class);
		client = SumUpClient.builder()
				.errorManager(errorManager)
				.logRepository(Mockito.mock(SumUpLogRepository.class))
				.jsonObjectMapper(JsonObjectMapperHolder.sharedJsonObjectMapper())
				.configId(SumUpConfigId.ofRepoId(1))
				.apiKey("key")
				.merchantCode(SumUpMerchantCode.ofString("M1"))
				.baseUrl(BASE_URL)
				.restTemplate(restTemplate)
				.build();
	}

	private void checkout()
	{
		client.cardReaderCheckout(READER_ID, JsonReaderCheckoutRequest.builder()
				.total_amount(JsonReaderCheckoutRequest.JsonAmount.builder().currency("EUR").minor_unit(2).value(100).build())
				.build());
	}

	@Test
	void checkout_4xx_isDefiniteRejection()
	{
		server.expect(requestTo(BASE_URL + "/merchants/M1/readers/R1/checkout")).andRespond(withBadRequest());

		assertThatThrownBy(this::checkout)
				.isInstanceOfSatisfying(SumUpCallException.class, ex -> assertThat(ex.isDefiniteRejection()).isTrue());
	}

	@Test
	void checkout_5xx_isNotDefiniteRejection()
	{
		server.expect(requestTo(BASE_URL + "/merchants/M1/readers/R1/checkout")).andRespond(withServerError());

		assertThatThrownBy(this::checkout)
				.isInstanceOfSatisfying(SumUpCallException.class, ex -> assertThat(ex.isDefiniteRejection()).isFalse());
	}

	@Test
	void checkout_socketTimeout_isNotDefiniteRejection()
	{
		server.expect(requestTo(BASE_URL + "/merchants/M1/readers/R1/checkout")).andRespond(withException(new SocketTimeoutException()));

		assertThatThrownBy(this::checkout)
				.isInstanceOfSatisfying(SumUpCallException.class, ex -> assertThat(ex.isDefiniteRejection()).isFalse());
	}

	@Test
	void factory_defaultBaseUrl_isSumUpProduction() throws Exception
	{
		final Value value = SumUpClientFactory.class.getDeclaredField("baseUrl").getAnnotation(Value.class);
		assertThat(value.value()).isEqualTo("${sumup.baseUrl:https://api.sumup.com/v0.1}");
	}

	@Test
	void client_usesConfiguredBaseUrl()
	{
		server.expect(requestTo("http://stub/v0.1/merchants/M1/readers/R1/checkout"))
				.andExpect(method(POST))
				.andRespond(withSuccess("{\"data\":{\"client_transaction_id\":\"abc\"}}", MediaType.APPLICATION_JSON));

		checkout();

		server.verify();
	}

	@Test
	void terminate_callsDocumentedEndpoint()
	{
		server.expect(requestTo("http://stub/v0.1/merchants/M1/readers/R1/terminate"))
				.andExpect(method(POST))
				.andRespond(withSuccess());

		client.terminateCardReaderCheckout(READER_ID);

		server.verify();
	}

	@Test
	void terminate_202AcceptedWithoutBody_isSuccess()
	{
		// SumUp API reference (readers/terminate-checkout): 202 Accepted, no body
		server.expect(requestTo("http://stub/v0.1/merchants/M1/readers/R1/terminate"))
				.andExpect(method(POST))
				.andRespond(withStatus(HttpStatus.ACCEPTED));

		client.terminateCardReaderCheckout(READER_ID);

		server.verify();
	}

	@Test
	void terminate_4xx_isDefiniteRejection()
	{
		server.expect(requestTo("http://stub/v0.1/merchants/M1/readers/R1/terminate")).andRespond(withBadRequest());

		assertThatThrownBy(() -> client.terminateCardReaderCheckout(READER_ID))
				.isInstanceOfSatisfying(SumUpCallException.class, ex -> assertThat(ex.isDefiniteRejection()).isTrue());
	}

	/**
	 * SumUp answers 404 to every v0.1 {@code /me/transactions} lookup; "Retrieve a transaction" exists only as
	 * {@code GET /v2.1/merchants/{merchant_code}/transactions} (https://developer.sumup.com/api/transactions/get).
	 */
	@Test
	void getTransactionByClientTransactionId_callsV21MerchantTransactions()
	{
		server.expect(requestTo("http://stub/v2.1/merchants/M1/transactions?client_transaction_id=CTX1"))
				.andExpect(method(GET))
				.andRespond(withSuccess(TRANSACTION_JSON, MediaType.APPLICATION_JSON));

		final JsonGetTransactionResponse trx = client.getTransactionById(SumUpClientTransactionId.ofString("CTX1"));

		server.verify();
		assertThat(trx.getId()).isEqualTo(SumUpTransactionExternalId.ofString("TX1"));
		assertThat(trx.getStatus()).isEqualTo("SUCCESSFUL");
	}

	@Test
	void getTransactionByExternalId_callsV21MerchantTransactions()
	{
		server.expect(requestTo("http://stub/v2.1/merchants/M1/transactions?id=TX1"))
				.andExpect(method(GET))
				.andRespond(withSuccess(TRANSACTION_JSON, MediaType.APPLICATION_JSON));

		final JsonGetTransactionResponse trx = client.getTransactionById(SumUpTransactionExternalId.ofString("TX1"));

		server.verify();
		assertThat(trx.getClient_transaction_id()).isEqualTo(SumUpClientTransactionId.ofString("CTX1"));
	}

	/**
	 * SumUpService tells "SumUp does not know the transaction" from a failed call by the 404 cause.
	 */
	@Test
	void getTransaction_404_keepsTheHttpStatusAsCause()
	{
		server.expect(requestTo("http://stub/v2.1/merchants/M1/transactions?client_transaction_id=CTX1"))
				.andRespond(withStatus(HttpStatus.NOT_FOUND));

		assertThatThrownBy(() -> client.getTransactionById(SumUpClientTransactionId.ofString("CTX1")))
				.isInstanceOfSatisfying(SumUpCallException.class, ex -> {
					assertThat(ex.isDefiniteRejection()).isTrue();
					assertThat(ex.getCause()).isInstanceOfSatisfying(HttpClientErrorException.class,
							cause -> assertThat(cause.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND));
				});
	}

	/**
	 * "Refund a transaction" (https://developer.sumup.com/api/transactions/refund): {@code POST
	 * /v1.0/merchants/{merchant_code}/payments/{transaction_id}/refunds}; a body without {@code amount} refunds the full
	 * amount; success is 201 with body {@code {}}. The v0.1 {@code /me/refund/{id}} refunds against the API key's default
	 * merchant, which is not necessarily the configured one.
	 */
	@Test
	void refund_callsV10MerchantPaymentRefunds_forTheFullAmount()
	{
		server.expect(requestTo("http://stub/v1.0/merchants/M1/payments/TX1/refunds"))
				.andExpect(method(POST))
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(content().string("{}"))
				.andRespond(withStatus(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body("{}"));

		client.refundTransaction(SumUpTransactionExternalId.ofString("TX1"));

		server.verify();
	}

	@Test
	void refund_409NotRefundable_isDefiniteRejection()
	{
		// as answered by the sandbox for a transaction refunded already
		server.expect(requestTo("http://stub/v1.0/merchants/M1/payments/TX1/refunds"))
				.andRespond(withStatus(HttpStatus.CONFLICT)
						.contentType(MediaType.valueOf("application/problem+json"))
						.body("{\"type\":\"https://developer.sumup.com/problem/conflict\",\"title\":\"Conflict\",\"status\":409,"
								+ "\"detail\":\"The transaction is not refundable in its current state\"}"));

		assertThatThrownBy(() -> client.refundTransaction(SumUpTransactionExternalId.ofString("TX1")))
				.isInstanceOfSatisfying(SumUpCallException.class, ex -> assertThat(ex.isDefiniteRejection()).isTrue());
	}

	@Test
	void refund_5xx_isNotDefiniteRejection()
	{
		server.expect(requestTo("http://stub/v1.0/merchants/M1/payments/TX1/refunds")).andRespond(withServerError());

		assertThatThrownBy(() -> client.refundTransaction(SumUpTransactionExternalId.ofString("TX1")))
				.isInstanceOfSatisfying(SumUpCallException.class, ex -> assertThat(ex.isDefiniteRejection()).isFalse());
	}

	@Test
	void transactionsApiBaseUrl_replacesTheTrailingV01Segment()
	{
		assertThat(SumUpClient.toTransactionsApiBaseUrl("https://api.sumup.com/v0.1")).isEqualTo("https://api.sumup.com/v2.1");
		assertThat(SumUpClient.toTransactionsApiBaseUrl("http://wiremock:8080/sumup/v0.1/")).isEqualTo("http://wiremock:8080/sumup/v2.1");
	}

	@Test
	void refundsApiBaseUrl_replacesTheTrailingV01Segment()
	{
		assertThat(SumUpClient.toRefundsApiBaseUrl("https://api.sumup.com/v0.1")).isEqualTo("https://api.sumup.com/v1.0");
		assertThat(SumUpClient.toRefundsApiBaseUrl("http://wiremock:8080/sumup/v0.1/")).isEqualTo("http://wiremock:8080/sumup/v1.0");
	}

	/**
	 * A base URL the lookup and refund bases cannot be derived from fails the lookup (which the poller records as an issue) and
	 * the refund, but not the reader calls.
	 */
	@Test
	void baseUrlWithoutTrailingV01Segment_failsOnlyTheLookupAndTheRefund()
	{
		final RestTemplate restTemplate = new RestTemplate();
		final MockRestServiceServer otherServer = MockRestServiceServer.bindTo(restTemplate).build();
		final SumUpClient clientWithOtherBaseUrl = SumUpClient.builder()
				.errorManager(errorManager)
				.logRepository(Mockito.mock(SumUpLogRepository.class))
				.jsonObjectMapper(JsonObjectMapperHolder.sharedJsonObjectMapper())
				.configId(SumUpConfigId.ofRepoId(1))
				.apiKey("key")
				.merchantCode(SumUpMerchantCode.ofString("M1"))
				.baseUrl("http://proxy/sumup")
				.restTemplate(restTemplate)
				.build();
		otherServer.expect(requestTo("http://proxy/sumup/merchants/M1/readers/R1/terminate")).andExpect(method(POST)).andRespond(withSuccess());

		clientWithOtherBaseUrl.terminateCardReaderCheckout(READER_ID);
		otherServer.verify();

		assertThatThrownBy(() -> clientWithOtherBaseUrl.getTransactionById(SumUpClientTransactionId.ofString("CTX1")))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("sumup.baseUrl");
		assertThatThrownBy(() -> clientWithOtherBaseUrl.refundTransaction(SumUpTransactionExternalId.ofString("TX1")))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("sumup.baseUrl");
	}

	@Test
	void baseUrlWithoutTrailingV01Segment_isRejected()
	{
		assertThatThrownBy(() -> SumUpClient.toTransactionsApiBaseUrl("https://api.sumup.com"))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("sumup.baseUrl");
	}

	@Test
	void callException_keepsRawRemoteTextWithBracesAndAtSigns()
	{
		final String raw = "{\"error\":\"x\"} @Foo@ {0}";
		server.expect(requestTo(BASE_URL + "/merchants/M1/readers/R1/checkout")).andRespond(withException(new IOException(raw)));

		assertThatThrownBy(this::checkout)
				.isInstanceOfSatisfying(SumUpCallException.class, ex -> assertThat(ex.getMessage()).contains(raw));
	}

	@Test
	void failedCall_isRecordedAsIssue()
	{
		server.expect(requestTo(BASE_URL + "/merchants/M1/readers/R1/checkout")).andRespond(withServerError());

		assertThatThrownBy(this::checkout).isInstanceOf(SumUpCallException.class);
		Mockito.verify(errorManager).createIssue(Mockito.any(SumUpCallException.class));
	}

	@Test
	void failedCall_withoutIssueOnError_isNotRecordedAsIssue_butStillThrown()
	{
		client.setCreateIssueOnError(false);
		server.expect(requestTo(BASE_URL + "/merchants/M1/readers/R1/checkout")).andRespond(withServerError());

		assertThatThrownBy(this::checkout).isInstanceOf(SumUpCallException.class);
		Mockito.verifyNoInteractions(errorManager);
	}
}
