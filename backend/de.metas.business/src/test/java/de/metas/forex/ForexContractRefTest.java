package de.metas.forex;

import de.metas.JsonObjectMapperHolder;
import de.metas.money.CurrencyId;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

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
		assertThat(forexContractRefDeserialized).isEqualTo(forexContractRef);
	}

	@Nested
	class getForeignCurrencyId
	{
		CurrencyId CURRENCY1 = CurrencyId.ofRepoId(555);
		CurrencyId CURRENCY2 = CurrencyId.ofRepoId(444);

		private ForexContractRef.ForexContractRefBuilder forexContractRef()
		{
			return ForexContractRef.builder()
					.forexContractId(ForexContractId.ofRepoId(123))
					.currencyRate(new BigDecimal("987.6543"));
		}

		@Test
		void fromCurrency_is_foreignCurrency()
		{
			final CurrencyId CURRENCY1 = CurrencyId.ofRepoId(555);
			final ForexContractRef forexContractRef = forexContractRef()
					.orderCurrencyId(CURRENCY1)
					.fromCurrencyId(CURRENCY2)
					.toCurrencyId(CURRENCY1)
					.build();
			assertThat(forexContractRef.getForeignCurrencyId()).isEqualTo(CURRENCY2);
		}

		@Test
		void toCurrency_is_foreignCurrency()
		{
			final ForexContractRef forexContractRef = forexContractRef()
					.orderCurrencyId(CURRENCY2)
					.fromCurrencyId(CURRENCY2)
					.toCurrencyId(CURRENCY1)
					.build();
			assertThat(forexContractRef.getForeignCurrencyId()).isEqualTo(CURRENCY1);
		}
	}
}