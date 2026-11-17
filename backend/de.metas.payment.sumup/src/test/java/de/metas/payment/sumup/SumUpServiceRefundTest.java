package de.metas.payment.sumup;

import com.google.common.collect.ImmutableList;
import de.metas.JsonObjectMapperHolder;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.error.IErrorManager;
import de.metas.money.MoneyService;
import de.metas.organization.ClientAndOrgId;
import de.metas.payment.sumup.client.SumUpClient;
import de.metas.payment.sumup.repository.SumUpConfigRepository;
import de.metas.payment.sumup.repository.SumUpLogRepository;
import de.metas.payment.sumup.repository.SumUpTransactionQuery;
import de.metas.payment.sumup.repository.SumUpTransactionRepository;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.function.UnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * {@link SumUpService#refundTransaction(SumUpClientTransactionId)} refunds the named transaction, also when another approved
 * transaction carries the same POS reference, and both the refund and the lookup of a stored transaction call SumUp for the
 * merchant the transaction was made for. The SumUp API is served by a stubbed {@link RestTemplate}, as in
 * {@link SumUpServiceImportTest}; nothing reaches the live SumUp API.
 */
class SumUpServiceRefundTest
{
	private static final ClientAndOrgId CLIENT_AND_ORG_ID = ClientAndOrgId.ofClientAndOrg(7, 8);
	private static final String BASE_URL = "http://stub/v0.1";
	private static final SumUpConfigId CONFIG_ID = SumUpConfigId.ofRepoId(1);
	private static final SumUpPOSRef POS_REF = SumUpPOSRef.builder().posOrderId(11).posPaymentId(12).build();

	private RestTemplate restTemplate;
	private MockRestServiceServer server;
	private SumUpConfigRepository configRepository;
	private SumUpTransactionRepository trxRepository;
	private SumUpService service;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		restTemplate = new RestTemplate();
		server = MockRestServiceServer.bindTo(restTemplate).build();

		// like the real factory: the client calls SumUp for the config's merchant, unless a merchant is given
		final SumUpClientFactory clientFactory = Mockito.mock(SumUpClientFactory.class);
		Mockito.when(clientFactory.newClient(ArgumentMatchers.any(SumUpConfig.class)))
				.thenAnswer(invocation -> newClient(invocation.<SumUpConfig>getArgument(0).getMerchantCode()));
		Mockito.when(clientFactory.newClient(ArgumentMatchers.any(SumUpConfig.class), ArgumentMatchers.any(SumUpMerchantCode.class)))
				.thenAnswer(invocation -> newClient(invocation.getArgument(1)));
		configRepository = Mockito.mock(SumUpConfigRepository.class);
		givenConfigMerchantCode("M1");
		trxRepository = Mockito.mock(SumUpTransactionRepository.class);

		service = new SumUpService(Mockito.mock(MoneyService.class), clientFactory, configRepository, trxRepository);
	}

	private SumUpClient newClient(final SumUpMerchantCode merchantCode)
	{
		return SumUpClient.builder()
				.errorManager(Mockito.mock(IErrorManager.class))
				.logRepository(Mockito.mock(SumUpLogRepository.class))
				.jsonObjectMapper(JsonObjectMapperHolder.sharedJsonObjectMapper())
				.configId(CONFIG_ID)
				.apiKey("key")
				.merchantCode(merchantCode)
				.baseUrl(BASE_URL)
				.restTemplate(restTemplate)
				.build();
	}

	private void givenConfigMerchantCode(final String merchantCode)
	{
		final SumUpConfig config = SumUpConfig.builder()
				.id(CONFIG_ID)
				.isActive(true)
				.apiKey("key")
				.merchantCode(SumUpMerchantCode.ofString(merchantCode))
				.cardReaders(ImmutableList.of())
				.build();
		Mockito.when(configRepository.getById(CONFIG_ID)).thenReturn(config);
	}

	private static SumUpTransaction successfulTrx(final String externalId, final String clientTransactionId)
	{
		return SumUpTransaction.builder()
				.configId(CONFIG_ID)
				.externalId(SumUpTransactionExternalId.ofString(externalId))
				.clientTransactionId(SumUpClientTransactionId.ofString(clientTransactionId))
				.merchantCode(SumUpMerchantCode.ofString("M1"))
				.timestamp(Instant.parse("2026-11-10T09:00:00Z"))
				.status(SumUpTransactionStatus.SUCCESSFUL)
				.amount(Amount.of("12.50", CurrencyCode.EUR))
				.clientAndOrgId(CLIENT_AND_ORG_ID)
				.posRef(POS_REF)
				.build();
	}

	private static String refundedRemoteTrxJson(final String externalId, final String clientTransactionId)
	{
		return "{\"id\":\"" + externalId + "\",\"client_transaction_id\":\"" + clientTransactionId + "\",\"merchant_code\":\"M1\","
				+ "\"timestamp\":\"2026-11-10T09:00:00.000Z\",\"status\":\"REFUNDED\",\"amount\":12.50,\"currency\":\"" + CurrencyCode.EUR.toThreeLetterCode() + "\"}";
	}

	@Test
	void refundByClientTransactionId_twoApprovedTransactionsOnOnePOSRef_onlyTheNamedOneIsRefunded()
	{
		// an earlier checkout's approval that was not booked, and the approval of the checkout the card payment names
		final SumUpTransaction notBooked = successfulTrx("ext-earlier", "ctid-earlier");
		final SumUpTransaction named = successfulTrx("ext-named", "ctid-named");
		Mockito.when(trxRepository.stream(ArgumentMatchers.any())).thenAnswer(invocation -> ImmutableList.of(notBooked, named).stream());
		Mockito.when(trxRepository.list(SumUpTransactionQuery.ofClientTransactionId(named.getClientTransactionId()))).thenReturn(ImmutableList.of(named));
		Mockito.when(trxRepository.updateById(ArgumentMatchers.eq(named.getExternalId()), ArgumentMatchers.<UnaryOperator<SumUpTransaction>>any()))
				.thenAnswer(invocation -> invocation.<UnaryOperator<SumUpTransaction>>getArgument(1).apply(named));
		// only the named transaction is refunded at SumUp (an unexpected request fails the test)
		server.expect(requestTo("http://stub/v1.0/merchants/M1/payments/ext-named/refunds")).andExpect(method(HttpMethod.POST))
				.andRespond(withStatus(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body("{}"));
		server.expect(requestTo("http://stub/v2.1/merchants/M1/transactions?id=ext-named")).andExpect(method(HttpMethod.GET))
				.andRespond(withSuccess(refundedRemoteTrxJson("ext-named", "ctid-named"), MediaType.APPLICATION_JSON));

		final SumUpTransaction refunded = service.refundTransaction(named.getClientTransactionId());

		server.verify();
		assertThat(refunded.getClientTransactionId()).isEqualTo(named.getClientTransactionId());
		assertThat(refunded.getStatus()).isEqualTo(SumUpTransactionStatus.REFUNDED);
		Mockito.verify(trxRepository, Mockito.never()).updateById(ArgumentMatchers.eq(notBooked.getExternalId()), ArgumentMatchers.<UnaryOperator<SumUpTransaction>>any());
	}

	/**
	 * The config's merchant code was changed after the payment: the refund and the follow-up lookup still go to the
	 * merchant the payment was made for (SumUp answers 404 for a transaction of another merchant).
	 */
	@Test
	void refund_configMerchantCodeChangedAfterPayment_usesTheTransactionsMerchantCode()
	{
		givenConfigMerchantCode("M-NEW");
		final SumUpTransaction trx = successfulTrx("ext-1", "ctid-1"); // made for merchant M1
		Mockito.when(trxRepository.list(SumUpTransactionQuery.ofClientTransactionId(trx.getClientTransactionId()))).thenReturn(ImmutableList.of(trx));
		Mockito.when(trxRepository.updateById(ArgumentMatchers.eq(trx.getExternalId()), ArgumentMatchers.<UnaryOperator<SumUpTransaction>>any()))
				.thenAnswer(invocation -> invocation.<UnaryOperator<SumUpTransaction>>getArgument(1).apply(trx));
		server.expect(requestTo("http://stub/v1.0/merchants/M1/payments/ext-1/refunds")).andExpect(method(HttpMethod.POST))
				.andRespond(withStatus(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body("{}"));
		server.expect(requestTo("http://stub/v2.1/merchants/M1/transactions?id=ext-1")).andExpect(method(HttpMethod.GET))
				.andRespond(withSuccess(refundedRemoteTrxJson("ext-1", "ctid-1"), MediaType.APPLICATION_JSON));

		final SumUpTransaction refunded = service.refundTransaction(trx.getClientTransactionId());

		server.verify();
		assertThat(refunded.getStatus()).isEqualTo(SumUpTransactionStatus.REFUNDED);
	}

	/**
	 * The config's merchant code was changed after the payment: refreshing the stored transaction (e.g. by the pending
	 * transactions poller) still looks it up at the merchant the payment was made for.
	 */
	@Test
	void updateFromRemote_configMerchantCodeChangedAfterPayment_looksUpAtTheTransactionsMerchant()
	{
		givenConfigMerchantCode("M-NEW");
		final SumUpTransaction trx = successfulTrx("ext-1", "ctid-1"); // made for merchant M1
		final SumUpTransaction[] updated = new SumUpTransaction[1];
		Mockito.doAnswer(invocation -> {
			updated[0] = invocation.<UnaryOperator<SumUpTransaction>>getArgument(1).apply(trx);
			return null;
		}).when(trxRepository).updateById(ArgumentMatchers.eq(trx.getClientTransactionId()), ArgumentMatchers.<UnaryOperator<SumUpTransaction>>any());
		server.expect(requestTo("http://stub/v2.1/merchants/M1/transactions?client_transaction_id=ctid-1")).andExpect(method(HttpMethod.GET))
				.andRespond(withSuccess(refundedRemoteTrxJson("ext-1", "ctid-1"), MediaType.APPLICATION_JSON));

		service.updateTransactionFromRemote(trx.getClientTransactionId());

		server.verify();
		assertThat(updated[0].getStatus()).isEqualTo(SumUpTransactionStatus.REFUNDED);
	}
}
