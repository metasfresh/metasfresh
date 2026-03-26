package de.metas.handlingunits.grai;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("AssertThatIsZeroOne")
class GRAISetTest
{
	private static final GRAI GRAI_A = GRAI.ofCanonicalString("7613204.00307.1000000001");
	private static final GRAI GRAI_B = GRAI.ofCanonicalString("7613204.00307.1000000002");
	private static final GRAI GRAI_C = GRAI.ofCanonicalString("7613204.00307.1000000003");

	@Nested
	class OfStrings
	{
		@Test
		void canonical_values()
		{
			final GRAISet set = GRAISet.ofStrings(ImmutableList.of("7613204.00307.1000000001", "7613204.00307.1000000002"));
			assertThat(set.toSet()).containsExactlyInAnyOrder(GRAI_A, GRAI_B);
		}

		@Test
		void skips_blanks()
		{
			final GRAISet set = GRAISet.ofStrings(ImmutableList.of("7613204.00307.1000000001", "", "  "));
			assertThat(set.toSet()).containsExactly(GRAI_A);
		}

		@Test
		void empty_collection()
		{
			assertThat(GRAISet.ofStrings(Collections.emptyList())).isSameAs(GRAISet.EMPTY);
		}

		@Test
		void deduplicates()
		{
			final GRAISet set = GRAISet.ofStrings(ImmutableList.of("7613204.00307.1000000001", "7613204.00307.1000000001"));
			assertThat(set.size()).isEqualTo(1);
		}

		@Test
		void preserves_insertion_order()
		{
			final GRAISet set = GRAISet.ofStrings(ImmutableList.of(
					GRAI_C.toCanonicalString(),
					GRAI_A.toCanonicalString(),
					GRAI_B.toCanonicalString()));
			assertThat(set.toStringList()).containsExactly(
					GRAI_C.toCanonicalString(),
					GRAI_A.toCanonicalString(),
					GRAI_B.toCanonicalString());
		}

		@Test
		void preserves_order_and_deduplicates()
		{
			final GRAISet set = GRAISet.ofStrings(ImmutableList.of(
					GRAI_C.toCanonicalString(),
					GRAI_A.toCanonicalString(),
					GRAI_C.toCanonicalString(),
					GRAI_B.toCanonicalString(),
					GRAI_A.toCanonicalString()));
			assertThat(set.toStringList()).containsExactly(
					GRAI_C.toCanonicalString(),
					GRAI_A.toCanonicalString(),
					GRAI_B.toCanonicalString());
		}
	}

	@Nested
	class ParseStrings
	{
		@Test
		void canonical_values()
		{
			final GRAISet set = GRAISet.parseStrings(ImmutableList.of("7613204.00307.1000000001"));
			assertThat(set.toSet()).containsExactly(GRAI_A);
		}

		@Test
		void gs1_values()
		{
			// GS1 AI 8003: indicator(0) + 7613204(cp) + 003095(at) + checkDigit(1) + serial(00691412000)
			final GRAISet set = GRAISet.parseStrings(ImmutableList.of("800307613204003095100691412000"));
			assertThat(set.size()).isEqualTo(1);
			assertThat(set.stream().findFirst().get().toCanonicalString()).isEqualTo("7613204.003095.00691412000");
		}

		@Test
		void mixed_canonical_and_gs1()
		{
			final GRAISet set = GRAISet.parseStrings(ImmutableList.of(
					"7613204.00307.1000000001",
					"800307613204003095100691412000"));
			assertThat(set.size()).isEqualTo(2);
		}

		@Test
		void skips_unparseable()
		{
			final GRAISet set = GRAISet.parseStrings(ImmutableList.of("7613204.00307.1000000001", "garbage", ""));
			assertThat(set.toSet()).containsExactly(GRAI_A);
		}

		@Test
		void deduplicates()
		{
			final GRAISet set = GRAISet.parseStrings(ImmutableList.of(
					GRAI_A.toCanonicalString(),
					GRAI_A.toCanonicalString()));
			assertThat(set.size()).isEqualTo(1);
		}

		@Test
		void preserves_insertion_order()
		{
			final GRAISet set = GRAISet.parseStrings(ImmutableList.of(
					GRAI_C.toCanonicalString(),
					GRAI_A.toCanonicalString(),
					GRAI_B.toCanonicalString()));
			assertThat(set.toStringList()).containsExactly(
					GRAI_C.toCanonicalString(),
					GRAI_A.toCanonicalString(),
					GRAI_B.toCanonicalString());
		}

		@Test
		void preserves_order_and_deduplicates()
		{
			final GRAISet set = GRAISet.parseStrings(ImmutableList.of(
					GRAI_B.toCanonicalString(),
					GRAI_A.toCanonicalString(),
					GRAI_B.toCanonicalString(),
					GRAI_C.toCanonicalString()));
			assertThat(set.toStringList()).containsExactly(
					GRAI_B.toCanonicalString(),
					GRAI_A.toCanonicalString(),
					GRAI_C.toCanonicalString());
		}

		@Test
		void empty_collection()
		{
			assertThat(GRAISet.parseStrings(Collections.emptyList())).isSameAs(GRAISet.EMPTY);
		}
	}

	@Nested
	class OfNullableCommaSeparated
	{
		@Test
		void parses_commaSeparated()
		{
			final GRAISet set = GRAISet.ofNullableCommaSeparated("7613204.00307.1000000001,7613204.00307.1000000002");
			assertThat(set.toSet()).containsExactlyInAnyOrder(GRAI_A, GRAI_B);
		}

		@Test
		void handles_whitespace()
		{
			final GRAISet set = GRAISet.ofNullableCommaSeparated(" 7613204.00307.1000000001 , 7613204.00307.1000000002 ");
			assertThat(set.toSet()).containsExactlyInAnyOrder(GRAI_A, GRAI_B);
		}

		@Test
		void returnsEmpty_forNull()
		{
			assertThat(GRAISet.ofNullableCommaSeparated(null)).isSameAs(GRAISet.EMPTY);
		}

		@Test
		void returnsEmpty_forBlank()
		{
			assertThat(GRAISet.ofNullableCommaSeparated("  ")).isSameAs(GRAISet.EMPTY);
		}

		@Test
		void single_value()
		{
			final GRAISet set = GRAISet.ofNullableCommaSeparated("7613204.00307.1000000001");
			assertThat(set.toSet()).containsExactly(GRAI_A);
		}
	}

	@Nested
	class ToCommaSeparatedString
	{
		@Test
		void multiple()
		{
			final GRAISet set = GRAISet.ofCollection(ImmutableList.of(GRAI_A, GRAI_B));
			final String csv = set.toCommaSeparatedString();
			// Order is not guaranteed by ImmutableSet, but both values must be present
			assertThat(csv).contains("7613204.00307.1000000001");
			assertThat(csv).contains("7613204.00307.1000000002");
			assertThat(csv.split(",")).hasSize(2);
		}

		@Test
		void single()
		{
			assertThat(GRAISet.of(GRAI_A).toCommaSeparatedString()).isEqualTo("7613204.00307.1000000001");
		}

		@Test
		void empty()
		{
			assertThat(GRAISet.EMPTY.toCommaSeparatedString()).isEmpty();
		}
	}

	@Nested
	class ToCommaSeparatedStringOrNull
	{
		@Test
		void returnsNull_forNull()
		{
			assertThat(GRAISet.toCommaSeparatedStringOrNull(null)).isNull();
		}

		@Test
		void returnsNull_forEmpty()
		{
			assertThat(GRAISet.toCommaSeparatedStringOrNull(GRAISet.EMPTY)).isNull();
		}

		@Test
		void returnsString_forNonEmpty()
		{
			assertThat(GRAISet.toCommaSeparatedStringOrNull(GRAISet.of(GRAI_A))).isEqualTo("7613204.00307.1000000001");
		}
	}

	@Nested
	class ToStringList
	{
		@Test
		void returnsCanonicalStrings()
		{
			final GRAISet set = GRAISet.ofCollection(ImmutableList.of(GRAI_A, GRAI_B));
			assertThat(set.toStringList()).containsExactlyInAnyOrder("7613204.00307.1000000001", "7613204.00307.1000000002");
		}

		@Test
		void empty()
		{
			assertThat(GRAISet.EMPTY.toStringList()).isEmpty();
		}
	}

	@Nested
	class SingleElement
	{
		@Test
		void returnsSingle()
		{
			assertThat(GRAISet.of(GRAI_A).singleElement()).isEqualTo(GRAI_A);
		}
	}

	@Nested
	class NoneOrSingleElement
	{
		@Test
		void returnsNull_forEmpty()
		{
			assertThat(GRAISet.EMPTY.noneOrSingleElement()).isNull();
		}

		@Test
		void returnsSingle()
		{
			assertThat(GRAISet.of(GRAI_A).noneOrSingleElement()).isEqualTo(GRAI_A);
		}
	}

	@Nested
	class Roundtrip
	{
		@Test
		void commaSeparated_roundtrip()
		{
			final GRAISet original = GRAISet.ofCollection(ImmutableList.of(GRAI_A, GRAI_B, GRAI_C));
			final String csv = original.toCommaSeparatedString();
			final GRAISet restored = GRAISet.ofNullableCommaSeparated(csv);
			assertThat(restored).isEqualTo(original);
		}
	}
}
