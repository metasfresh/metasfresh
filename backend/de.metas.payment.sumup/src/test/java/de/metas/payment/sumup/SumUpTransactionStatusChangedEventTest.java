package de.metas.payment.sumup;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.organization.ClientAndOrgId;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class SumUpTransactionStatusChangedEventTest
{
	@Test
	void testSerializeDeserialize() throws JsonProcessingException
	{
		testSerializeDeserialize(
				SumUpTransactionStatusChangedEvent.builder()
						.trx(SumUpTransaction.builder()
								.configId(SumUpConfigId.ofRepoId(1))
								.externalId(SumUpTransactionExternalId.ofString(UUID.randomUUID().toString()))
								.clientTransactionId(SumUpClientTransactionId.ofString(UUID.randomUUID().toString()))
								.merchantCode(SumUpMerchantCode.ofString("merchant code"))
								.timestamp(SystemTime.asInstant())
								.status(SumUpTransactionStatus.SUCCESSFUL)
								.amount(Amount.of("123.45", CurrencyCode.EUR))
								.card(SumUpTransaction.Card.builder().type("VISA").last4Digits("4321").build())
								.json("json raw code")
								.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(21, 22))
								.posRef(SumUpPOSRef.builder()
										.posOrderId(31)
										.posPaymentId(32)
										.build())
								.build())
						.trxPrev(SumUpTransaction.builder()
								.configId(SumUpConfigId.ofRepoId(1))
								.externalId(SumUpTransactionExternalId.ofString(UUID.randomUUID().toString()))
								.clientTransactionId(SumUpClientTransactionId.ofString(UUID.randomUUID().toString()))
								.merchantCode(SumUpMerchantCode.ofString("merchant code"))
								.timestamp(SystemTime.asInstant())
								.status(SumUpTransactionStatus.PENDING)
								.amount(Amount.of("123.45", CurrencyCode.EUR))
								.json("json raw code")
								.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(21, 22))
								.posRef(SumUpPOSRef.builder()
										.posOrderId(31)
										.posPaymentId(32)
										.build())
								.build())
						.build()
		);
	}

	void testSerializeDeserialize(SumUpTransactionStatusChangedEvent event) throws JsonProcessingException
	{
		final ObjectMapper jsonObjectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
		
		System.out.println(" event: " + event);

		final String json = jsonObjectMapper.writeValueAsString(event);
		System.out.println("  json: " + json);
		System.out.println("  \t\tjson size: " + json.getBytes().length + " bytes");
		final SumUpTransactionStatusChangedEvent event2 = jsonObjectMapper.readValue(json, SumUpTransactionStatusChangedEvent.class);
		System.out.println("event2: " + event2);

		assertThat(event2).usingRecursiveComparison().isEqualTo(event);
		assertThat(event2).isEqualTo(event);
	}

}