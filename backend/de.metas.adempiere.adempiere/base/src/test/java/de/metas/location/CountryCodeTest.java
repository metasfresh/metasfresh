package de.metas.location;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
			"AT,AUT",
			"US,USA",
			"IE,IRL",
			"RO,ROU",
	})
	void ofAlpha2_ofAlpha3(String alpha2, String alpha3) throws JsonProcessingException
	{
		final CountryCode countryCodeOfAlpha2 = CountryCode.ofAlpha2(alpha2);
		final CountryCode countryCodeOfAlpha3 = CountryCode.ofAlpha3(alpha3);
		assertThat(countryCodeOfAlpha2)
				.isEqualTo(countryCodeOfAlpha3)
				.isSameAs(countryCodeOfAlpha3);

		assertThat(countryCodeOfAlpha2.getAlpha2()).as("alpha2").isEqualTo(alpha2);
		assertThat(countryCodeOfAlpha2.getAlpha3()).as("alpha3").isEqualTo(alpha3);

		final ObjectMapper objectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();
		final String json = objectMapper.writeValueAsString(countryCodeOfAlpha2);
		assertThat(json).as("json").isEqualTo("\"" + alpha2 + "\"");
		final CountryCode countryCodeDeserialized = objectMapper.readValue(json, CountryCode.class);
		assertThat(countryCodeDeserialized).as("deserialized").isSameAs(countryCodeOfAlpha2);
	}

	@Test
	void invalidAlpha2()
	{
		assertThatThrownBy(() -> CountryCode.ofAlpha2("DEU")).hasMessageContaining("Invalid alpha-2 country code");
		assertThatThrownBy(() -> CountryCode.ofAlpha2("D")).hasMessageContaining("Invalid alpha-2 country code");
	}

	@Test
	void invalidAlpha3()
	{
		assertThatThrownBy(() -> CountryCode.ofAlpha3("DE")).hasMessageContaining("Invalid alpha-3 country code");
		assertThatThrownBy(() -> CountryCode.ofAlpha3("D")).hasMessageContaining("Invalid alpha-3 country code");
	}
}