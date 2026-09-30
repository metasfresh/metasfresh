package de.metas.handlingunits.grai;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings({ "AssertThatIsZeroOne" })
class GRAISetTest
{
	@Nested
	class OfStrings
	{
		@Test
		void canonical_values()
		{
			final GRAISet set = GRAISet.ofStrings(ImmutableList.of("7613204.00307.1000000001", "7613204.00307.1000000002"));
			assertThat(set.toStringList()).containsExactly("7613204.00307.1000000001", "7613204.00307.1000000002");
		}

		@Test
		void skips_blanks()
		{
			final GRAISet set = GRAISet.ofStrings(Arrays.asList("7613204.00307.1000000001", "", "  ", null));
			assertThat(set.toStringList()).containsExactly("7613204.00307.1000000001");
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
			final GRAISet set = GRAISet.ofStrings(ImmutableList.of("7613204.00307.1000000003", "7613204.00307.1000000001", "7613204.00307.1000000002"));
			assertThat(set.toStringList()).containsExactly("7613204.00307.1000000003", "7613204.00307.1000000001", "7613204.00307.1000000002");
		}

		@Test
		void preserves_order_and_deduplicates()
		{
			final GRAISet set = GRAISet.ofStrings(ImmutableList.of(
					"7613204.00307.1000000003",
					"7613204.00307.1000000001",
					"7613204.00307.1000000003",
					"7613204.00307.1000000002",
					"7613204.00307.1000000001"));
			assertThat(set.toStringList()).containsExactly(
					"7613204.00307.1000000003",
					"7613204.00307.1000000001",
					"7613204.00307.1000000002");
		}
	}

	@Nested
	class ParseStrings
	{
		@Test
		void canonical_values()
		{
			final GRAISet set = GRAISet.parseStrings(ImmutableList.of("7613204.00307.1000000001"));
			assertThat(set.toStringList()).containsExactly("7613204.00307.1000000001");
		}

		@Test
		void gs1_values()
		{
			final GRAISet set = GRAISet.parseStrings(ImmutableList.of("800307613204003095100691412000"));
			assertThat(set.toStringList()).containsExactly("7613204.00309.100691412000");
		}

		@Test
		void mixed_canonical_and_gs1()
		{
			final GRAISet set = GRAISet.parseStrings(ImmutableList.of(
					"7613204.00307.1000000001",
					"800307613204003095100691412000"));
			assertThat(set.toStringList()).containsExactly(
					"7613204.00307.1000000001",
					"7613204.00309.100691412000");
		}

		@Test
		void skips_unparseable()
		{
			final GRAISet set = GRAISet.parseStrings(ImmutableList.of("7613204.00307.1000000001", "garbage", ""));
			assertThat(set.toStringList()).containsExactly("7613204.00307.1000000001");
		}

		@Test
		void deduplicates()
		{
			final GRAISet set = GRAISet.parseStrings(ImmutableList.of(
					"7613204.00307.1000000001",
					"7613204.00307.1000000001"));
			assertThat(set.size()).isEqualTo(1);
		}

		@Test
		void preserves_insertion_order()
		{
			final GRAISet set = GRAISet.parseStrings(ImmutableList.of(
					"7613204.00307.1000000003",
					"7613204.00307.1000000001",
					"7613204.00307.1000000002"));
			assertThat(set.toStringList()).containsExactly(
					"7613204.00307.1000000003",
					"7613204.00307.1000000001",
					"7613204.00307.1000000002");
		}

		@Test
		void preserves_order_and_deduplicates()
		{
			final GRAISet set = GRAISet.parseStrings(ImmutableList.of(
					"7613204.00307.1000000002",
					"7613204.00307.1000000001",
					"7613204.00307.1000000002",
					"7613204.00307.1000000003"));
			assertThat(set.toStringList()).containsExactly(
					"7613204.00307.1000000002",
					"7613204.00307.1000000001",
					"7613204.00307.1000000003");
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
			assertThat(set.toStringList()).containsExactly("7613204.00307.1000000001", "7613204.00307.1000000002");
		}

		@Test
		void handles_whitespace()
		{
			final GRAISet set = GRAISet.ofNullableCommaSeparated(" 7613204.00307.1000000001 , 7613204.00307.1000000002 ");
			assertThat(set.toStringList()).containsExactly("7613204.00307.1000000001", "7613204.00307.1000000002");
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
			assertThat(set.toStringList()).containsExactly("7613204.00307.1000000001");
		}
	}

	@Nested
	class ToCommaSeparatedString
	{
		@Test
		void multiple()
		{
			final GRAISet set = GRAISet.ofCollection(ImmutableList.of(
					GRAI.ofCanonicalString("7613204.00307.1000000001"),
					GRAI.ofCanonicalString("7613204.00307.1000000002")
			));
			assertThat(set.toCommaSeparatedString()).isEqualTo("7613204.00307.1000000001,7613204.00307.1000000002");
		}

		@Test
		void single()
		{
			final GRAISet set = GRAISet.of(GRAI.ofCanonicalString("7613204.00307.1000000001"));
			assertThat(set.toCommaSeparatedString()).isEqualTo("7613204.00307.1000000001");
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
			assertThat(GRAISet.toCommaSeparatedStringOrNull(
					GRAISet.of(GRAI.ofCanonicalString("7613204.00307.1000000001"))))
					.isEqualTo("7613204.00307.1000000001");
		}
	}

	@Nested
	class ToStringList
	{
		@Test
		void returnsCanonicalStrings()
		{
			final GRAISet set = GRAISet.ofCollection(ImmutableList.of(
					GRAI.ofCanonicalString("7613204.00307.1000000001"),
					GRAI.ofCanonicalString("7613204.00307.1000000002")));
			assertThat(set.toStringList()).containsExactly("7613204.00307.1000000001", "7613204.00307.1000000002");
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
			final GRAI grai = GRAI.ofCanonicalString("7613204.00307.1000000001");
			assertThat(GRAISet.of(grai).singleElement()).isEqualTo(grai);
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
			final GRAI grai = GRAI.ofCanonicalString("7613204.00307.1000000001");
			assertThat(GRAISet.of(grai).noneOrSingleElement()).isEqualTo(grai);
		}
	}

	@Nested
	class Roundtrip
	{
		@Test
		void commaSeparated_roundtrip()
		{
			final GRAISet original = GRAISet.ofNullableCommaSeparated("7613204.00307.1000000001,7613204.00307.1000000002,7613204.00307.1000000003");
			assertThat(original.toCommaSeparatedString()).isEqualTo("7613204.00307.1000000001,7613204.00307.1000000002,7613204.00307.1000000003");
		}
	}
}
