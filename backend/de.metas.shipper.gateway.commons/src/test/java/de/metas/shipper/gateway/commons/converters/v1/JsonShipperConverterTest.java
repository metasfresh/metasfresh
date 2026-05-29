package de.metas.shipper.gateway.commons.converters.v1;

import de.metas.common.delivery.v1.json.JsonAddress;
import de.metas.location.CountryCode;
import de.metas.shipper.gateway.spi.model.Address;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JsonShipperConverterTest
{
	@Test
	void toJsonAddress_mapsAttention()
	{
		// given
		final Address address = Address.builder()
				.companyName1("ACME GmbH")
				.street1("Hauptstraße 1")
				.country(CountryCode.ofAlpha2("DE"))
				.zipCode("10115")
				.city("Berlin")
				.bpartnerId(0)
				.attention("z. Hd. Test")
				.build();

		// when
		final JsonAddress result = JsonShipperConverter.toJsonAddress(address);

		// then
		assertThat(result.getAttention()).isEqualTo("z. Hd. Test");
	}
}
