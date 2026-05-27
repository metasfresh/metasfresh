package de.metas.shipper.gateway.nshift.client;

import de.metas.common.delivery.v1.json.request.JsonShipperConfig;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;

import static org.assertj.core.api.Assertions.assertThat;

class NShiftShipperGatewayClientTest
{
	private static JsonShipperConfig baseConfig(@Nullable final String existingServiceLevel)
	{
		final JsonShipperConfig.JsonShipperConfigBuilder b = JsonShipperConfig.builder()
				.url("https://api.nshift.example")
				.username("user")
				.password("pass")
				.clientId("cid")
				.clientSecret("csecret")
				.trackingUrlTemplate("https://track.example/{awb}")
				.additionalProperty("ActorId", "ACT123");
		if (existingServiceLevel != null)
		{
			b.additionalProperty("ServiceLevel", existingServiceLevel);
		}
		return b.build();
	}

	@Test
	void overridesExistingServiceLevel()
	{
		final JsonShipperConfig base = baseConfig("OLD_LEVEL");
		final JsonShipperConfig result = NShiftShipperGatewayClient.buildConfigWithOverriddenServiceLevel(base, "NEW_LEVEL");

		assertThat(result.getAdditionalProperty("ServiceLevel")).isEqualTo("NEW_LEVEL");
		assertThat(result.getAdditionalProperty("ActorId")).isEqualTo("ACT123");
	}

	@Test
	void addsServiceLevelWhenNotPresentInBase()
	{
		final JsonShipperConfig base = baseConfig(null);
		final JsonShipperConfig result = NShiftShipperGatewayClient.buildConfigWithOverriddenServiceLevel(base, "EXPRESS");

		assertThat(result.getAdditionalProperty("ServiceLevel")).isEqualTo("EXPRESS");
		assertThat(result.getAdditionalProperty("ActorId")).isEqualTo("ACT123");
	}

	@Test
	void preservesAllBaseFields()
	{
		final JsonShipperConfig base = baseConfig(null);
		final JsonShipperConfig result = NShiftShipperGatewayClient.buildConfigWithOverriddenServiceLevel(base, "STANDARD");

		assertThat(result.getUrl()).isEqualTo(base.getUrl());
		assertThat(result.getUsername()).isEqualTo(base.getUsername());
		assertThat(result.getPassword()).isEqualTo(base.getPassword());
		assertThat(result.getClientId()).isEqualTo(base.getClientId());
		assertThat(result.getClientSecret()).isEqualTo(base.getClientSecret());
		assertThat(result.getTrackingUrlTemplate()).isEqualTo(base.getTrackingUrlTemplate());
	}
}
