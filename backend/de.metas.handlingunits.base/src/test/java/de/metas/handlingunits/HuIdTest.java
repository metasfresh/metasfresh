package de.metas.handlingunits;

import org.adempiere.exceptions.AdempiereException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class HuIdTest
{
	@Nested
	class ofHUValue
	{
		@Test
		void from_toHUValue()
		{
			final HuId huId = HuId.ofRepoId(12345);
			Assertions.assertThat(HuId.ofHUValue(huId.toHUValue())).isEqualTo(huId);
		}

		@Test
		void invalidHUValue()
		{
			final HuId huId = HuId.ofRepoId(12345);
			Assertions.assertThatThrownBy(() -> HuId.ofHUValue("12345invalid"))
					.isInstanceOf(AdempiereException.class)
					.hasMessageStartingWith("Invalid HUValue `12345invalid`");
		}

	}
}