package de.metas.handlingunits.serialno;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings({ "AssertThatIsZeroOne" })
class SerialNoSetTest
{
	@Nested
	class ParseStrings
	{
		@Test
		void values()
		{
			final SerialNoSet set = SerialNoSet.parseStrings(ImmutableList.of("SN-001", "SN-002"));
			assertThat(set.toStringList()).containsExactly("SN-001", "SN-002");
		}

		@Test
		void trims_and_skips_blanks()
		{
			final SerialNoSet set = SerialNoSet.parseStrings(Arrays.asList(" SN-001 ", "", "  ", null));
			assertThat(set.toStringList()).containsExactly("SN-001");
		}

		@Test
		void deduplicates()
		{
			final SerialNoSet set = SerialNoSet.parseStrings(ImmutableList.of("SN-001", "SN-001"));
			assertThat(set.size()).isEqualTo(1);
		}

		@Test
		void preserves_insertion_order_and_deduplicates()
		{
			final SerialNoSet set = SerialNoSet.parseStrings(ImmutableList.of("SN-003", "SN-001", "SN-003", "SN-002", "SN-001"));
			assertThat(set.toStringList()).containsExactly("SN-003", "SN-001", "SN-002");
		}

		@Test
		void empty_collection()
		{
			assertThat(SerialNoSet.parseStrings(Collections.emptyList())).isSameAs(SerialNoSet.EMPTY);
		}
	}

	@Nested
	class OfNullableCommaSeparated
	{
		@Test
		void parses_commaSeparated()
		{
			final SerialNoSet set = SerialNoSet.ofNullableCommaSeparated("SN-001,SN-002");
			assertThat(set.toStringList()).containsExactly("SN-001", "SN-002");
		}

		@Test
		void handles_whitespace()
		{
			final SerialNoSet set = SerialNoSet.ofNullableCommaSeparated(" SN-001 , SN-002 ");
			assertThat(set.toStringList()).containsExactly("SN-001", "SN-002");
		}

		@Test
		void deduplicates()
		{
			final SerialNoSet set = SerialNoSet.ofNullableCommaSeparated("SN-001,SN-001,SN-002");
			assertThat(set.size()).isEqualTo(2);
			assertThat(set.toStringList()).containsExactly("SN-001", "SN-002");
		}

		@Test
		void returnsEmpty_forNull()
		{
			assertThat(SerialNoSet.ofNullableCommaSeparated(null)).isSameAs(SerialNoSet.EMPTY);
		}

		@Test
		void returnsEmpty_forBlank()
		{
			assertThat(SerialNoSet.ofNullableCommaSeparated("  ")).isSameAs(SerialNoSet.EMPTY);
		}

		@Test
		void single_value()
		{
			final SerialNoSet set = SerialNoSet.ofNullableCommaSeparated("SN-001");
			assertThat(set.toStringList()).containsExactly("SN-001");
			assertThat(set.size()).isEqualTo(1);
		}
	}

	@Nested
	class ToCommaSeparatedString
	{
		@Test
		void multiple()
		{
			final SerialNoSet set = SerialNoSet.parseStrings(ImmutableList.of("SN-001", "SN-002"));
			assertThat(set.toCommaSeparatedString()).isEqualTo("SN-001,SN-002");
		}

		@Test
		void single()
		{
			final SerialNoSet set = SerialNoSet.of(SerialNo.ofString("SN-001"));
			assertThat(set.toCommaSeparatedString()).isEqualTo("SN-001");
		}

		@Test
		void empty()
		{
			assertThat(SerialNoSet.EMPTY.toCommaSeparatedString()).isEmpty();
		}
	}

	@Nested
	class ToCommaSeparatedStringOrNull
	{
		@Test
		void returnsNull_forNull()
		{
			assertThat(SerialNoSet.toCommaSeparatedStringOrNull(null)).isNull();
		}

		@Test
		void returnsNull_forEmpty()
		{
			assertThat(SerialNoSet.toCommaSeparatedStringOrNull(SerialNoSet.EMPTY)).isNull();
		}

		@Test
		void returnsString_forNonEmpty()
		{
			assertThat(SerialNoSet.toCommaSeparatedStringOrNull(SerialNoSet.of(SerialNo.ofString("SN-001"))))
					.isEqualTo("SN-001");
		}
	}

	@Nested
	class Size
	{
		@Test
		void empty_isZero()
		{
			assertThat(SerialNoSet.EMPTY.size()).isZero();
			assertThat(SerialNoSet.EMPTY.isEmpty()).isTrue();
		}

		@Test
		void counts_distinct()
		{
			final SerialNoSet set = SerialNoSet.parseStrings(ImmutableList.of("SN-001", "SN-002", "SN-003"));
			assertThat(set.size()).isEqualTo(3);
			assertThat(set.isEmpty()).isFalse();
		}
	}

	@Nested
	class Roundtrip
	{
		@Test
		void commaSeparated_roundtrip()
		{
			final SerialNoSet original = SerialNoSet.ofNullableCommaSeparated("SN-001,SN-002,SN-003");
			assertThat(original.toCommaSeparatedString()).isEqualTo("SN-001,SN-002,SN-003");
		}
	}
}
