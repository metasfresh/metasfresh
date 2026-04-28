package de.metas.handlingunits.picking.job.service.commands.pick;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class PickAttributesTest
{
	private static final PickAttributes EMPTY = PickAttributes.builder().build();

	@Nested
	class fallbackTo
	{
		private void test(final PickAttributes pickAttributes1, final PickAttributes pickAttributes2)
		{
			assertThat(pickAttributes1.fallbackTo(EMPTY)).isSameAs(pickAttributes1);
			assertThat(EMPTY.fallbackTo(pickAttributes1)).isEqualTo(pickAttributes1);

			assertThat(pickAttributes1.fallbackTo(pickAttributes1)).isSameAs(pickAttributes1);
			assertThat(pickAttributes2.fallbackTo(pickAttributes2)).isSameAs(pickAttributes2);

			assertThat(pickAttributes1.fallbackTo(pickAttributes2)).isEqualTo(pickAttributes1);
			assertThat(pickAttributes2.fallbackTo(pickAttributes1)).isEqualTo(pickAttributes2);
		}

		@Test
		void empty()
		{
			assertThat(EMPTY.fallbackTo(EMPTY)).isSameAs(EMPTY);
		}

		@Test
		void bestBeforeDate()
		{
			test(
					PickAttributes.builder().isSetBestBeforeDate(true).bestBeforeDate(LocalDate.parse("2025-04-05")).build(),
					PickAttributes.builder().isSetBestBeforeDate(true).bestBeforeDate(LocalDate.parse("2026-07-08")).build()
			);
			test(
					PickAttributes.builder().isSetBestBeforeDate(true).bestBeforeDate(LocalDate.parse("2025-04-05")).build(),
					PickAttributes.builder().isSetBestBeforeDate(true).bestBeforeDate(null).build()
			);
		}

		@Test
		void productionDate()
		{
			test(
					PickAttributes.builder().isSetProductionDate(true).productionDate(LocalDate.parse("2025-04-05")).build(),
					PickAttributes.builder().isSetProductionDate(true).productionDate(LocalDate.parse("2026-07-08")).build()
			);
			test(
					PickAttributes.builder().isSetProductionDate(true).productionDate(LocalDate.parse("2025-04-05")).build(),
					PickAttributes.builder().isSetProductionDate(true).productionDate(null).build()
			);
		}

		@Test
		void lotNo()
		{
			test(
					PickAttributes.builder().isSetLotNo(true).lotNo("lot1").build(),
					PickAttributes.builder().isSetLotNo(true).lotNo("lot2").build()
			);
			test(
					PickAttributes.builder().isSetLotNo(true).lotNo("lot1").build(),
					PickAttributes.builder().isSetLotNo(true).lotNo(null).build()
			);
		}

	}
}