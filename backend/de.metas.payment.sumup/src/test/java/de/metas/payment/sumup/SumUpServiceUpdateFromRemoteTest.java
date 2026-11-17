package de.metas.payment.sumup;

import com.google.common.collect.ImmutableList;
import de.metas.JsonObjectMapperHolder;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.money.MoneyService;
import de.metas.organization.ClientAndOrgId;
import de.metas.payment.sumup.SumUpTransaction.LastSyncStatus;
import de.metas.payment.sumup.client.SumUpClient;
import de.metas.payment.sumup.repository.SumUpConfigRepository;
import de.metas.payment.sumup.repository.SumUpLogRepository;
import de.metas.payment.sumup.repository.SumUpTransactionQuery;
import de.metas.payment.sumup.repository.SumUpTransactionRepository;
import de.metas.util.Services;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.function.UnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * Refreshing a transaction metasfresh already stores ({@link SumUpService#updateTransactionFromRemote}, also run by the SumUp poller
 * for every PENDING transaction metasfresh created in the last 24 h). SumUp answers the lookup of a reader checkout with HTTP 404 until the card is
 * presented on the reader (and forever, if the reader times out): for a PENDING transaction that is SumUp's answer, not a
 * failed lookup.
 * <p>
 * The SumUp API is served by a stubbed {@link RestTemplate}, as in {@link de.metas.payment.sumup.client.SumUpClientTest};
 * nothing reaches the live SumUp API.
 */
class SumUpServiceUpdateFromRemoteTest
{
	private static final ClientAndOrgId CLIENT_AND_ORG_ID = ClientAndOrgId.ofClientAndOrg(7, 8);
	private static final String BASE_URL = "http://stub/v0.1";
	private static final SumUpConfigId CONFIG_ID = SumUpConfigId.ofRepoId(1);
	private static final SumUpClientTransactionId CLIENT_TRX_ID = SumUpClientTransactionId.ofString("ctid-1");
	private static final SumUpPOSRef POS_REF = SumUpPOSRef.builder().posOrderId(11).posPaymentId(12).build();
	private static final String GET_TRX_URL = "http://stub/v2.1/merchants/M1/transactions?client_transaction_id=ctid-1";
	private static final AdIssueId ISSUE_ID = AdIssueId.ofRepoId(4711);
	/**
	 * SumUp's answer for a transaction it does not know, as answered by the SumUp sandbox for a reader checkout whose card
	 * was not presented
	 */
	private static final String SUMUP_NOT_FOUND_BODY = "{\"message\":\"Resource not found\",\"error_code\":\"NOT_FOUND\"}";

	private MockRestServiceServer server;
	private IErrorManager errorManager;
	private SumUpTransactionRepository trxRepository;
	private SumUpService service;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		errorManager = Mockito.mock(IErrorManager.class);
		Mockito.when(errorManager.createIssue(ArgumentMatchers.<Throwable>any())).thenReturn(ISSUE_ID);
		Services.registerService(IErrorManager.class, errorManager);

		final RestTemplate restTemplate = new RestTemplate();
		server = MockRestServiceServer.bindTo(restTemplate).build();
		final SumUpConfig config = SumUpConfig.builder()
				.id(CONFIG_ID)
				.isActive(true)
				.apiKey("key")
				.merchantCode(SumUpMerchantCode.ofString("M1"))
				.cardReaders(ImmutableList.of())
				.build();

		final SumUpClient client = SumUpClient.builder()
				.errorManager(errorManager)
				.logRepository(Mockito.mock(SumUpLogRepository.class))
				.jsonObjectMapper(JsonObjectMapperHolder.sharedJsonObjectMapper())
				.configId(CONFIG_ID)
				.apiKey("key")
				.merchantCode(config.getMerchantCode())
				.baseUrl(BASE_URL)
				.restTemplate(restTemplate)
				.build();

		final SumUpClientFactory clientFactory = Mockito.mock(SumUpClientFactory.class);
		Mockito.when(clientFactory.newClient(config)).thenReturn(client);
		final SumUpConfigRepository configRepository = Mockito.mock(SumUpConfigRepository.class);
		Mockito.when(configRepository.getById(CONFIG_ID)).thenReturn(config);
		trxRepository = Mockito.mock(SumUpTransactionRepository.class);

		service = new SumUpService(Mockito.mock(MoneyService.class), clientFactory, configRepository, trxRepository);
	}

	private static SumUpTransaction storedTrx(final SumUpTransactionStatus status)
	{
		return SumUpTransaction.builder()
				.configId(CONFIG_ID)
				.externalId(SumUpTransactionExternalId.ofString("ext-1"))
				.clientTransactionId(CLIENT_TRX_ID)
				.merchantCode(SumUpMerchantCode.ofString("M1"))
				.timestamp(Instant.parse("2026-11-16T18:33:56Z"))
				.status(status)
				.amount(Amount.of("10", CurrencyCode.EUR))
				.clientAndOrgId(CLIENT_AND_ORG_ID)
				.posRef(POS_REF)
				.build();
	}

	/**
	 * Runs {@link SumUpService#updateTransactionFromRemote(SumUpClientTransactionId, boolean)} on the given stored transaction.
	 *
	 * @return the transaction as the service stores it
	 */
	private SumUpTransaction updateFromRemote(final SumUpTransaction stored, final boolean isCreateIssueOnError)
	{
		service.updateTransactionFromRemote(CLIENT_TRX_ID, isCreateIssueOnError);

		@SuppressWarnings("unchecked") final ArgumentCaptor<UnaryOperator<SumUpTransaction>> updater = ArgumentCaptor.forClass(UnaryOperator.class);
		Mockito.verify(trxRepository).updateById(ArgumentMatchers.eq(CLIENT_TRX_ID), updater.capture());
		return updater.getValue().apply(stored);
	}

	/**
	 * The SumUp poller's cycle asks SumUp only for the PENDING transactions metasfresh created in the last 24 h: a reader
	 * checkout whose card was never presented is not looked up for ever.
	 */
	@Test
	void poller_refreshesOnlyPendingTransactionsCreatedInTheLast24h()
	{
		try
		{
			SystemTime.setFixedTimeSource("2026-11-17T12:00:00Z");

			service.bulkUpdateRecentPendingTransactions();

			Mockito.verify(trxRepository).bulkUpdateByQuery(
					ArgumentMatchers.eq(SumUpTransactionQuery.builder()
							.status(SumUpTransactionStatus.PENDING)
							.createdAfter(Instant.parse("2026-11-16T12:00:00Z"))
							.build()),
					ArgumentMatchers.eq(false),
					ArgumentMatchers.any());
		}
		finally
		{
			SystemTime.resetTimeSource();
		}
	}

	@Test
	void pending_notFound404_staysPending_lastSyncOk_noIssue()
	{
		server.expect(requestTo(GET_TRX_URL)).andRespond(withStatus(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(SUMUP_NOT_FOUND_BODY));

		final SumUpTransaction updated = updateFromRemote(storedTrx(SumUpTransactionStatus.PENDING), true);

		assertThat(updated.getStatus()).isEqualTo(SumUpTransactionStatus.PENDING);
		assertThat(updated.getLastSync()).isNotNull();
		assertThat(updated.getLastSync().getStatus()).isEqualTo(LastSyncStatus.OK);
		assertThat(updated.getLastSync().getErrorId()).isNull();
		Mockito.verify(errorManager, Mockito.never()).createIssue(ArgumentMatchers.<Throwable>any());
		server.verify();
	}

	@Test
	void pending_notFound404_withoutIssueOnError_lastSyncOk()
	{
		server.expect(requestTo(GET_TRX_URL)).andRespond(withStatus(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(SUMUP_NOT_FOUND_BODY));

		final SumUpTransaction updated = updateFromRemote(storedTrx(SumUpTransactionStatus.PENDING), false);

		assertThat(updated.getStatus()).isEqualTo(SumUpTransactionStatus.PENDING);
		assertThat(updated.getLastSync().getStatus()).isEqualTo(LastSyncStatus.OK);
		Mockito.verify(errorManager, Mockito.never()).createIssue(ArgumentMatchers.<Throwable>any());
	}

	/**
	 * Back office's "update all pending" path: every PENDING transaction is refreshed, whatever its age.
	 */
	@Test
	void updateAllPending_pending_notFound404_lastSyncOk_noIssue()
	{
		server.expect(requestTo(GET_TRX_URL)).andRespond(withStatus(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(SUMUP_NOT_FOUND_BODY));

		service.bulkUpdatePendingTransactions(false);

		@SuppressWarnings("unchecked") final ArgumentCaptor<UnaryOperator<SumUpTransaction>> updater = ArgumentCaptor.forClass(UnaryOperator.class);
		Mockito.verify(trxRepository).bulkUpdateByQuery(ArgumentMatchers.eq(SumUpTransactionQuery.ofStatus(SumUpTransactionStatus.PENDING)), ArgumentMatchers.eq(false), updater.capture());
		final SumUpTransaction updated = updater.getValue().apply(storedTrx(SumUpTransactionStatus.PENDING));

		assertThat(updated.getStatus()).isEqualTo(SumUpTransactionStatus.PENDING);
		assertThat(updated.getLastSync().getStatus()).isEqualTo(LastSyncStatus.OK);
		Mockito.verify(errorManager, Mockito.never()).createIssue(ArgumentMatchers.<Throwable>any());
		server.verify();
	}

	@Test
	void pending_foundSuccessful_isUpdated_lastSyncOk()
	{
		server.expect(requestTo(GET_TRX_URL)).andRespond(withSuccess("{\"id\":\"ext-1\",\"client_transaction_id\":\"ctid-1\",\"merchant_code\":\"M1\","
				+ "\"timestamp\":\"2026-11-16T18:40:00.000Z\",\"status\":\"SUCCESSFUL\",\"amount\":10.00,\"currency\":\"EUR\"}", MediaType.APPLICATION_JSON));

		final SumUpTransaction updated = updateFromRemote(storedTrx(SumUpTransactionStatus.PENDING), true);

		assertThat(updated.getStatus()).isEqualTo(SumUpTransactionStatus.SUCCESSFUL);
		assertThat(updated.getLastSync().getStatus()).isEqualTo(LastSyncStatus.OK);
		Mockito.verify(errorManager, Mockito.never()).createIssue(ArgumentMatchers.<Throwable>any());
	}

	/**
	 * An outage stays a failed lookup.
	 */
	@Test
	void pending_serverError_isFailedLookup_withIssue()
	{
		server.expect(requestTo(GET_TRX_URL)).andRespond(withServerError());

		final SumUpTransaction updated = updateFromRemote(storedTrx(SumUpTransactionStatus.PENDING), true);

		assertThat(updated.getStatus()).isEqualTo(SumUpTransactionStatus.PENDING);
		assertThat(updated.getLastSync().getStatus()).isEqualTo(LastSyncStatus.Error);
		assertThat(updated.getLastSync().getErrorId()).isEqualTo(ISSUE_ID);
	}

	/**
	 * SumUp reported this transaction already: if it no longer knows it, that is not "no card presented yet", so it stays a
	 * failed lookup (e.g. the booking's refund check must not take it as confirmed).
	 */
	@Test
	void successful_notFound404_isFailedLookup_withIssue()
	{
		server.expect(requestTo(GET_TRX_URL)).andRespond(withStatus(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(SUMUP_NOT_FOUND_BODY));

		final SumUpTransaction updated = updateFromRemote(storedTrx(SumUpTransactionStatus.SUCCESSFUL), true);

		assertThat(updated.getStatus()).isEqualTo(SumUpTransactionStatus.SUCCESSFUL);
		assertThat(updated.getLastSync().getStatus()).isEqualTo(LastSyncStatus.Error);
		assertThat(updated.getLastSync().getErrorId()).isEqualTo(ISSUE_ID);
	}
}
