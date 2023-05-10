package de.metas.forex;

import de.metas.JsonObjectMapperHolder;
import de.metas.money.CurrencyId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class ForexContractRefTest
{
	@Test
	void testSerializeDeserialize()
	{
		final ForexContractRef forexContractRef = ForexContractRef.builder()
				.forexContractId(ForexContractId.ofRepoId(123))
				.orderCurrencyId(CurrencyId.ofRepoId(444))
				.fromCurrencyId(CurrencyId.ofRepoId(444))
				.toCurrencyId(CurrencyId.ofRepoId(555))
				.currencyRate(new BigDecimal("987.6543"))
				.build();
		System.out.println("forexContractRef=" + forexContractRef);

		final String json = JsonObjectMapperHolder.toJson(forexContractRef);
		System.out.println("json=" + json);

		final ForexContractRef forexContractRefDeserialized = JsonObjectMapperHolder.fromJson(json, ForexContractRef.class);
		Assertions.assertThat(forexContractRefDeserialized).isEqualTo(forexContractRef);
	}
}