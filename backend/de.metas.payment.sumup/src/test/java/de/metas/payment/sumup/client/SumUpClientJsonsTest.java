package de.metas.payment.sumup.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import de.metas.JsonObjectMapperHolder;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.CurrencyCode;
import de.metas.payment.sumup.SumUpCardReaderExternalId;
import de.metas.payment.sumup.SumUpClientTransactionId;
import de.metas.payment.sumup.SumUpMerchantCode;
import de.metas.payment.sumup.client.json.JsonGetReadersResponse;
import de.metas.payment.sumup.client.json.JsonGetTransactionResponse;
import de.metas.payment.sumup.client.json.JsonPairReaderRequest;
import de.metas.payment.sumup.client.json.JsonPairReaderResponse;
import de.metas.payment.sumup.client.json.JsonReaderCheckoutRequest;
import de.metas.payment.sumup.client.json.JsonReaderCheckoutResponse;
import lombok.NonNull;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class SumUpClientJsonsTest
{
	<T> void testSerializeDeserialize(@NonNull final T obj) throws JsonProcessingException
	{
		//noinspection unchecked
		final Class<T> type = (Class<T>)obj.getClass();

		System.out.println("  obj: " + obj);

		final ObjectMapper jsonObjectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
		final String json = jsonObjectMapper.writeValueAsString(obj);
		System.out.println(" json: " + json);
		final T obj2 = jsonObjectMapper.readValue(json, type);
		System.out.println(" obj2: " + obj2);

		assertThat(obj2).usingRecursiveComparison().isEqualTo(obj);
		assertThat(obj2).isEqualTo(obj);
	}

	@Test
	void test_JsonGetReadersResponse() throws JsonProcessingException
	{
		testSerializeDeserialize(
				JsonGetReadersResponse.builder()
						.items(ImmutableList.of(
								JsonGetReadersResponse.Item.builder()
										.id(SumUpCardReaderExternalId.ofString(UUID.randomUUID().toString()))
										.name("card reader 1")
										.build()
						))
						.build()
		);
	}

	@Test
	void test_JsonGetTransactionResponse() throws JsonProcessingException
	{
		testSerializeDeserialize(
				JsonGetTransactionResponse.builder()
						.id(UUID.randomUUID().toString())
						.client_transaction_id(SumUpClientTransactionId.ofString(UUID.randomUUID().toString()))
						.merchant_code(SumUpMerchantCode.ofString("merchant_code"))
						.timestamp(SystemTime.asInstant().toString())
						.status("my PENDING status")
						.amount(new BigDecimal("12.34"))
						.currency(CurrencyCode.EUR)
						.json(null) // this is not serialized
						.build()
		);
	}

	@Test
	void test_JsonPairReaderRequest() throws JsonProcessingException
	{
		testSerializeDeserialize(
				JsonPairReaderRequest.builder()
						.name("my card reader")
						.pairing_code("my pairing code")
						.build()
		);
	}

	@Test
	void test_JsonPairReaderResponse() throws JsonProcessingException
	{
		testSerializeDeserialize(
				JsonPairReaderResponse.builder()
						.created_at(SystemTime.asInstant().toString())
						.device(JsonPairReaderResponse.Device.builder()
								.identifier(UUID.randomUUID().toString())
								.model("card reader model")
								.build())
						.build()
		);
	}

	@Test
	void test_JsonReaderCheckoutRequest() throws JsonProcessingException
	{
		testSerializeDeserialize(
				JsonReaderCheckoutRequest.builder()
						.description("my description")
						.return_url("https://my_return_url")
						.total_amount(JsonReaderCheckoutRequest.JsonAmount.builder()
								.currency("EUR")
								.value(123)
								.minor_unit(2)
								.build())
						.build()
		);
	}

	@Test
	void test_JsonReaderCheckoutResponse() throws JsonProcessingException
	{
		testSerializeDeserialize(
				JsonReaderCheckoutResponse.builder()
						.data(JsonReaderCheckoutResponse.Data.builder()
								.client_transaction_id(SumUpClientTransactionId.ofString(UUID.randomUUID().toString()))
								.build())
						.build()
		);
	}
}