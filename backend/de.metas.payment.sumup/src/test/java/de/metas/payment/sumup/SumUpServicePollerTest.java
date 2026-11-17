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
import de.metas.payment.sumup.repository.SumUpTransactionRepository;
import de.metas.util.Services;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.Env;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.ResponseCreator;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Nullable;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * The SumUp poller's cycle ({@link SumUpService#bulkUpdatePendingTransactionsToPoll()}) over several cycles, on the
 * in-memory database: it stops asking SumUp for a PENDING transaction only once a lookup made at least 24 h after metasfresh
 * created it was answered with SumUp's "not found" (a reader checkout whose card was never presented). A lookup that failed
 * (or none at all, e.g. while the server was down) keeps it polled, so a charge SumUp approved meanwhile is still booked.
 * <p>
 * The SumUp API is served by a stubbed {@link RestTemplate}; every lookup the test does not expect fails the test.
 */
class SumUpServicePollerTest
{
	private static final String BASE_URL = "http://stub/v0.1";
	private static final SumUpConfigId CONFIG_ID = SumUpConfigId.ofRepoId(1);
	private static final SumUpClientTransactionId CLIENT_TRX_ID = SumUpClientTransactionId.ofString("ctid-1");
	private static final String GET_TRX_URL = "http://stub/v2.1/merchants/M1/transactions?client_transaction_id=ctid-1";
	private static final String SUMUP_NOT_FOUND_BODY = "{\"message\":\"Resource not found\",\"error_code\":\"NOT_FOUND\"}";
	private static final String SUMUP_SUCCESSFUL_BODY = "{\"id\":\"ext-1\",\"client_transaction_id\":\"ctid-1\",\"merchant_code\":\"M1\","
			+ "\"timestamp\":\"2026-11-16T10:00:30.000Z\",\"status\":\"SUCCESSFUL\",\"amount\":10.00,\"currency\":\"EUR\"}";

	/**
	 * When metasfresh created the transaction; the 24 h mark is {@code 2026-11-17T10:00:00Z}
	 */
	private static final String CREATED_AT = "2026-11-16T10:00:00Z";

	private MockRestServiceServer server;
	private SumUpTransactionRepository trxRepository;
	private SumUpService service;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		final IErrorManager errorManager = Mockito.mock(IErrorManager.class);
		Mockito.when(errorManager.createIssue(ArgumentMatchers.<Throwable>any())).thenReturn(AdIssueId.ofRepoId(4711));
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
		trxRepository = new SumUpTransactionRepository(Mockito.mock(SumUpEventsDispatcher.class));

		service = new SumUpService(Mockito.mock(MoneyService.class), clientFactory, configRepository, trxRepository);

		// the PENDING transaction of a reader checkout, as metasfresh stores it when SumUp accepted the checkout
		SystemTime.setFixedTimeSource(CREATED_AT);
		trxRepository.saveNew(SumUpTransaction.builder()
				.configId(CONFIG_ID)
				.externalId(SumUpTransactionExternalId.ofString("UNKNOWN-ctid-1"))
				.clientTransactionId(CLIENT_TRX_ID)
				.merchantCode(SumUpMerchantCode.ofString("M1"))
				.timestamp(Instant.parse(CREATED_AT))
				.status(SumUpTransactionStatus.PENDING)
				.amount(Amount.of("10", CurrencyCode.EUR))
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(Env.getClientId(), Env.getOrgId()))
				.posRef(SumUpPOSRef.builder().posOrderId(11).posPaymentId(12).build())
				.build());
	}

	@AfterEach
	void tearDown()
	{
		SystemTime.resetTimeSource();
	}

	/**
	 * Runs one poller cycle at the given time, in which SumUp is asked exactly once and gives the given answer (or, if null,
	 * is not asked at all: any lookup fails the test).
	 */
	private void pollAt(final String time, @Nullable final ResponseCreator sumUpAnswer)
	{
		server.reset();
		if (sumUpAnswer != null)
		{
			server.expect(ExpectedCount.once(), requestTo(GET_TRX_URL)).andRespond(sumUpAnswer);
		}

		SystemTime.setFixedTimeSource(time);
		service.bulkUpdatePendingTransactionsToPoll();

		server.verify();
	}

	private static ResponseCreator notFound()
	{
		return withStatus(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(SUMUP_NOT_FOUND_BODY);
	}

	private static ResponseCreator successful()
	{
		return withSuccess(SUMUP_SUCCESSFUL_BODY, MediaType.APPLICATION_JSON);
	}

	private SumUpTransaction getTrx()
	{
		return service.findTransactionByClientTransactionId(CLIENT_TRX_ID).orElseThrow(IllegalStateException::new);
	}

	@Test
	void notFoundAfter24h_stopsPolling()
	{
		pollAt("2026-11-17T11:00:00Z", notFound());

		pollAt("2026-11-17T11:00:30Z", null);

		assertThat(getTrx().getStatus()).isEqualTo(SumUpTransactionStatus.PENDING);
		assertThat(getTrx().getLastSync().getTimestamp()).isEqualTo(Instant.parse("2026-11-17T11:00:00Z"));
	}

	@Test
	void lookupErrorAfter24h_keepsPolling()
	{
		pollAt("2026-11-17T11:00:00Z", withServerError());
		assertThat(getTrx().getLastSync().getStatus()).isEqualTo(LastSyncStatus.Error);

		pollAt("2026-11-17T11:00:30Z", notFound());
	}

	@Test
	void notFoundOnlyBefore24h_keepsPollingUntilALookupAfter24hAnswers()
	{
		pollAt("2026-11-16T11:00:00Z", notFound());
		pollAt("2026-11-17T09:59:59Z", notFound());
		pollAt("2026-11-17T10:00:30Z", notFound());

		pollAt("2026-11-17T10:01:00Z", null);
	}

	/**
	 * Every lookup failed until after the 24 h mark (e.g. SumUp unreachable over a weekend), while SumUp approved the charge:
	 * the first lookup that gets through stores it.
	 */
	@Test
	void successfulAfterAnOutageLongerThan24h_isStored()
	{
		pollAt("2026-11-16T10:01:00Z", withServerError());
		pollAt("2026-11-17T12:00:00Z", withServerError());
		pollAt("2026-11-18T08:00:00Z", successful());

		assertThat(getTrx().getStatus()).isEqualTo(SumUpTransactionStatus.SUCCESSFUL);
		assertThat(getTrx().getLastSync().getStatus()).isEqualTo(LastSyncStatus.OK);
	}

	/**
	 * The server was down for more than 24 h, so the transaction was never looked up after its creation: the first cycle
	 * after the restart still asks SumUp.
	 */
	@Test
	void neverLookedUp_olderThan24h_isStillPolled()
	{
		pollAt("2026-11-18T08:00:00Z", successful());

		assertThat(getTrx().getStatus()).isEqualTo(SumUpTransactionStatus.SUCCESSFUL);
	}
}
