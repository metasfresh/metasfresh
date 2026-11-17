package de.metas.cucumber.stepdefs.pos;

import de.metas.JsonObjectMapperHolder;
import de.metas.error.IErrorManager;
import de.metas.payment.sumup.SumUpCallException;
import de.metas.payment.sumup.SumUpClientTransactionId;
import de.metas.payment.sumup.SumUpConfigId;
import de.metas.payment.sumup.SumUpMerchantCode;
import de.metas.payment.sumup.client.SumUpClient;
import de.metas.payment.sumup.client.json.JsonGetTransactionResponse;
import de.metas.payment.sumup.repository.SumUpLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.client.HttpServerErrorException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * {@link SumUpApiStub} serves only {@link SumUpApiStub#MERCHANT_CODE}, so a scenario fails when the client sends another
 * merchant code. Drives the real {@link SumUpClient} against the stub; no database needed.
 */
class SumUpApiStubTest
{
	private String baseUrl;

	@BeforeEach
	void setUp()
	{
		baseUrl = SumUpApiStub.start();
		SumUpApiStub.reset();
	}

	private SumUpClient newClient(final String merchantCode)
	{
		final SumUpClient client = SumUpClient.builder()
				.errorManager(Mockito.mock(IErrorManager.class))
				.logRepository(Mockito.mock(SumUpLogRepository.class))
				.jsonObjectMapper(JsonObjectMapperHolder.sharedJsonObjectMapper())
				.configId(SumUpConfigId.ofRepoId(1))
				.apiKey("stub-api-key")
				.merchantCode(SumUpMerchantCode.ofString(merchantCode))
				.baseUrl(baseUrl)
				.build();
		client.setCreateIssueOnError(false);
		return client;
	}

	@Test
	void transactionLookup_configuredMerchant_isServed()
	{
		final JsonGetTransactionResponse trx = newClient(SumUpApiStub.MERCHANT_CODE).getTransactionById(SumUpClientTransactionId.ofString("ctid-1"));

		assertThat(trx.getClient_transaction_id().getAsString()).isEqualTo("ctid-1");
		assertThat(trx.getMerchant_code().getAsString()).isEqualTo(SumUpApiStub.MERCHANT_CODE);
	}

	@Test
	void transactionLookup_otherMerchant_isAnswered501_notAsNotFound()
	{
		assertThatThrownBy(() -> newClient("OTHERMERCHANT").getTransactionById(SumUpClientTransactionId.ofString("ctid-1")))
				.isInstanceOf(SumUpCallException.class)
				.hasCauseInstanceOf(HttpServerErrorException.class)
				.hasMessageContaining("501");
	}
}
