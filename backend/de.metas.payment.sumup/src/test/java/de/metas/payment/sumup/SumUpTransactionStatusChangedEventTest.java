package de.metas.payment.sumup;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
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
						.clientTransactionId(SumUpClientTransactionId.ofString(UUID.randomUUID().toString()))
						.configId(SumUpConfigId.ofRepoId(1))
						.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(21, 22))
						.posRef(SumUpPOSRef.builder()
								.posOrderId(31)
								.posPaymentId(32)
								.build())
						.statusNew(SumUpTransactionStatus.SUCCESSFUL)
						.statusOld(SumUpTransactionStatus.PENDING)
						.refundedNew(true)
						.refundedOld(false)
						.build()
		);
	}

	void testSerializeDeserialize(SumUpTransactionStatusChangedEvent event) throws JsonProcessingException
	{
		System.out.println(" event: " + event);

		final ObjectMapper jsonObjectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
		final String json = jsonObjectMapper.writeValueAsString(event);
		System.out.println("  json: " + json);
		final SumUpTransactionStatusChangedEvent event2 = jsonObjectMapper.readValue(json, SumUpTransactionStatusChangedEvent.class);
		System.out.println("event2: " + event2);

		assertThat(event2).usingRecursiveComparison().isEqualTo(event);
		assertThat(event2).isEqualTo(event);
	}

}