package de.metas.location;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class CountryCodeTest
{
	@Test
	void constants()
	{
		assertThat(CountryCode.ofAlpha2(CountryCode.DE.getAlpha2())).isSameAs(CountryCode.DE);
		assertThat(CountryCode.ofAlpha2(CountryCode.CH.getAlpha2())).isSameAs(CountryCode.CH);
	}

	@ParameterizedTest
	@CsvSource({
			"DE,DEU",
			"CH,CHE",
			"RO,ROU",
	})
	void ofAlpha2_ofAlpha3(String alpha2, String alpha3)
	{
		final CountryCode expected = CountryCode.builder().alpha2(alpha2).alpha3(alpha3).build();
		assertThat(CountryCode.ofAlpha2(alpha2)).isEqualTo(expected);
		assertThat(CountryCode.ofAlpha3(alpha3)).isEqualTo(expected);

	}
}