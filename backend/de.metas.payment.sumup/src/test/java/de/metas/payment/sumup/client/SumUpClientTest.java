package de.metas.payment.sumup.client;

import de.metas.JsonObjectMapperHolder;
import de.metas.error.IErrorManager;
import de.metas.payment.sumup.SumUpCallException;
import de.metas.payment.sumup.SumUpCardReaderExternalId;
import de.metas.payment.sumup.SumUpClientFactory;
import de.metas.payment.sumup.SumUpConfigId;
import de.metas.payment.sumup.SumUpMerchantCode;
import de.metas.payment.sumup.client.json.JsonReaderCheckoutRequest;
import de.metas.payment.sumup.repository.SumUpLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.SocketTimeoutException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.http.HttpMethod.POST;
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
