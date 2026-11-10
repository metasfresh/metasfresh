package de.metas.payment.sumup;

import de.metas.JsonObjectMapperHolder;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.organization.ClientAndOrgId;
import org.compiere.util.Env;
import de.metas.error.IErrorManager;
import de.metas.money.MoneyService;
import de.metas.payment.sumup.client.SumUpClient;
import de.metas.payment.sumup.repository.SumUpConfigRepository;
import de.metas.payment.sumup.repository.SumUpLogRepository;
import de.metas.payment.sumup.repository.SumUpTransactionQuery;
import de.metas.payment.sumup.repository.SumUpTransactionRepository;
import com.google.common.collect.ImmutableList;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import java.time.Instant;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * The SumUp API is served by a stubbed {@link RestTemplate}, as in {@link de.metas.payment.sumup.client.SumUpClientTest}; nothing reaches the live SumUp API.
 */
class SumUpServiceImportTest
{
	private static final String BASE_URL = "http://stub/v0.1";
	private static final SumUpConfigId CONFIG_ID = SumUpConfigId.ofRepoId(1);
	private static final SumUpCardReaderExternalId READER_ID = SumUpCardReaderExternalId.ofString("R1");
	private static final SumUpClientTransactionId CLIENT_TRX_ID = SumUpClientTransactionId.ofString("ctid-1");
	private static final SumUpPOSRef POS_REF = SumUpPOSRef.builder().posOrderId(11).posPaymentId(12).build();
	private static final String GET_TRX_URL = BASE_URL + "/me/transactions?client_transaction_id=ctid-1";

	private MockRestServiceServer server;
	private SumUpTransactionRepository trxRepository;
	private SumUpService service;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		final RestTemplate restTemplate = new RestTemplate();
		server = MockRestServiceServer.bindTo(restTemplate).build();
		final SumUpConfig config = SumUpConfig.builder()
				.id(CONFIG_ID)
				.isActive(true)
				.apiKey("key")
				.merchantCode(SumUpMerchantCode.ofString("M1"))
				.cardReaders(ImmutableList.of(SumUpCardReader.builder().name("Reader 1").externalId(READER_ID).isActive(true).build()))
				.build();

		final SumUpClient client = SumUpClient.builder()
				.errorManager(Mockito.mock(IErrorManager.class))
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

	private static String remoteTrxJson(final String status)
	{
		return "{\"id\":\"ext-1\",\"client_transaction_id\":\"ctid-1\",\"merchant_code\":\"M1\","
				+ "\"timestamp\":\"2026-11-10T10:00:00.000Z\",\"status\":\"" + status + "\",\"amount\":12.50,\"currency\":\"" + CurrencyCode.EUR.toThreeLetterCode() + "\","
				+ "\"card\":{\"type\":\"VISA\",\"last_4_digits\":\"1234\"}}";
	}

	@Test
	void import_savesRowWithPOSRefAndRemoteStatus()
	{
		Mockito.when(trxRepository.list(SumUpTransactionQuery.ofClientTransactionId(CLIENT_TRX_ID))).thenReturn(ImmutableList.of());
		server.expect(requestTo(GET_TRX_URL)).andRespond(withSuccess(remoteTrxJson("SUCCESSFUL"), MediaType.APPLICATION_JSON));

		final SumUpImportResult result = service.importAcceptedCheckout(CONFIG_ID, CLIENT_TRX_ID, POS_REF, READER_ID);

		final ArgumentCaptor<SumUpTransaction> saved = ArgumentCaptor.forClass(SumUpTransaction.class);
		Mockito.verify(trxRepository).saveNew(saved.capture());
		assertThat(result.getLookupError()).isNull();
		assertThat(result.getTransaction()).isSameAs(saved.getValue());
		assertThat(saved.getValue().getPosRef()).isEqualTo(POS_REF);
		assertThat(saved.getValue().getClientTransactionId()).isEqualTo(CLIENT_TRX_ID);
		assertThat(saved.getValue().getExternalId()).isEqualTo(SumUpTransactionExternalId.ofString("ext-1"));
		assertThat(saved.getValue().getStatus()).isEqualTo(SumUpTransactionStatus.SUCCESSFUL);
		assertThat(saved.getValue().getCardReader().getExternalId()).isEqualTo(READER_ID);
		server.verify();
	}

	@Test
	void import_noOpWhenRowExists()
	{
		final SumUpTransaction existing = SumUpTransaction.builder()
				.configId(CONFIG_ID)
				.externalId(SumUpTransactionExternalId.ofString("ext-existing"))
				.clientTransactionId(CLIENT_TRX_ID)
				.merchantCode(SumUpMerchantCode.ofString("M1"))
				.timestamp(Instant.parse("2026-11-10T09:00:00Z"))
				.status(SumUpTransactionStatus.PENDING)
				.amount(Amount.of("12.50", CurrencyCode.EUR))
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(Env.getClientId(), Env.getOrgId()))
				.posRef(POS_REF)
				.build();
		Mockito.when(trxRepository.list(SumUpTransactionQuery.ofClientTransactionId(CLIENT_TRX_ID))).thenReturn(ImmutableList.of(existing));

		final SumUpImportResult result = service.importAcceptedCheckout(CONFIG_ID, CLIENT_TRX_ID, POS_REF, READER_ID);

		assertThat(result.getTransaction()).isSameAs(existing);
		assertThat(result.getLookupError()).isNull();
		Mockito.verify(trxRepository, Mockito.never()).saveNew(Mockito.any());
		server.verify(); // no call to SumUp
	}

	@Test
	void import_lookupFails_savesNothing_returnsLookupError()
	{
		Mockito.when(trxRepository.list(SumUpTransactionQuery.ofClientTransactionId(CLIENT_TRX_ID))).thenReturn(ImmutableList.of());
		server.expect(requestTo(GET_TRX_URL)).andRespond(withServerError());

		final SumUpImportResult result = service.importAcceptedCheckout(CONFIG_ID, CLIENT_TRX_ID, POS_REF, READER_ID);

		assertThat(result.getTransaction()).isNull();
		assertThat(result.getLookupError()).isInstanceOf(SumUpCallException.class);
		Mockito.verify(trxRepository, Mockito.never()).saveNew(Mockito.any());
	}
}
