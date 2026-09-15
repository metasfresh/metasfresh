package de.metas.shipper.gateway.commons.servicelevel;

import com.google.common.collect.ImmutableList;
import de.metas.externalsystem.ExternalSystemId;
import de.metas.shipping.ShipperId;
import de.metas.shipper.gateway.spi.ShipperConfigRequest;
import de.metas.util.lang.SeqNo;
import lombok.NonNull;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ShipperServiceLevelConfigListTest
{
	private static final ShipperId SHIPPER_ID = ShipperId.ofRepoId(1);
	private static final ExternalSystemId EXTERNAL_SYSTEM_1 = ExternalSystemId.ofRepoId(100);
	private static final ExternalSystemId EXTERNAL_SYSTEM_2 = ExternalSystemId.ofRepoId(200);

	private static ShipperServiceLevelConfig config(final int seqNo, @Nullable final ExternalSystemId externalSystemId, @NonNull final String serviceLevel)
	{
		return ShipperServiceLevelConfig.builder()
				.id(ShipperServiceLevelConfigId.ofRepoId(seqNo))
				.shipperId(SHIPPER_ID)
				.seqNo(SeqNo.ofInt(seqNo * 10))
				.externalSystemId(externalSystemId)
				.serviceLevel(serviceLevel)
				.build();
	}

	@Test
	void emptyList_returnsEmpty()
	{
		final ShipperServiceLevelConfigList list = ShipperServiceLevelConfigList.EMPTY;
		final Optional<String> result = list.getEffectiveServiceLevel(ShipperConfigRequest.builder().externalSystemId(EXTERNAL_SYSTEM_1).build());
		assertThat(result).isEmpty();
	}

	@Test
	void matchingExternalSystem_returnsSpecificServiceLevel()
	{
		final ShipperServiceLevelConfigList list = ShipperServiceLevelConfigList.ofCollection(ImmutableList.of(
				config(1, EXTERNAL_SYSTEM_1, "DHL_EXPRESS"),
				config(2, null, "DEFAULT_LEVEL")
		));

		final Optional<String> result = list.getEffectiveServiceLevel(ShipperConfigRequest.builder().externalSystemId(EXTERNAL_SYSTEM_1).build());
		assertThat(result).contains("DHL_EXPRESS");
	}

	@Test
	void noMatchingExternalSystem_fallsBackToNullEntry()
	{
		final ShipperServiceLevelConfigList list = ShipperServiceLevelConfigList.ofCollection(ImmutableList.of(
				config(1, EXTERNAL_SYSTEM_1, "DHL_EXPRESS"),
				config(2, null, "DEFAULT_LEVEL")
		));

		final Optional<String> result = list.getEffectiveServiceLevel(ShipperConfigRequest.builder().externalSystemId(EXTERNAL_SYSTEM_2).build());
		assertThat(result).contains("DEFAULT_LEVEL");
	}

	@Test
	void nullExternalSystemInRequest_onlyMatchesNullEntries()
	{
		final ShipperServiceLevelConfigList list = ShipperServiceLevelConfigList.ofCollection(ImmutableList.of(
				config(1, EXTERNAL_SYSTEM_1, "DHL_EXPRESS"),
				config(2, null, "DEFAULT_LEVEL")
		));

		final Optional<String> result = list.getEffectiveServiceLevel(ShipperConfigRequest.builder().externalSystemId(null).build());
		assertThat(result).contains("DEFAULT_LEVEL");
	}

	@Test
	void noFallbackAndNoMatch_returnsEmpty()
	{
		final ShipperServiceLevelConfigList list = ShipperServiceLevelConfigList.ofCollection(ImmutableList.of(
				config(1, EXTERNAL_SYSTEM_1, "DHL_EXPRESS")
		));

		final Optional<String> result = list.getEffectiveServiceLevel(ShipperConfigRequest.builder().externalSystemId(EXTERNAL_SYSTEM_2).build());
		assertThat(result).isEmpty();
	}
}
