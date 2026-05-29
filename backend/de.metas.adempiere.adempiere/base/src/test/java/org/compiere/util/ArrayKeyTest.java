package org.compiere.util;

import org.compiere.util.Util.ArrayKey;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ArrayKeyTest
{
	@Test
	public void test_toString()
	{
		final Timestamp date = TimeUtil.getDay(2013, 02, 05);
		final ArrayKey key = new ArrayKey(
				5,
				"s1",
				null,
				date
		);

		final String keyStrExpected = "5#s1#NULL#2013-02-05 00:00:00.0";
		final String keyStrActual = key.toString();
		Assertions.assertEquals(keyStrExpected, keyStrActual);
	}

	@Test
	public void test_with_optionals()
	{
		final ArrayKey key1 = ArrayKey.of("s1", 5, Optional.of(true));
		final ArrayKey key2 = ArrayKey.of("s1", 5, Optional.of(true));

		assertThat(key1).isEqualTo(key2);

		// Optional<true> is not equal to Optional<false> or an empty optional
		final ArrayKey key3 = ArrayKey.of("s1", 5, Optional.of(false));
		assertThat(key1).isNotEqualTo(key3);

		final ArrayKey key4 = ArrayKey.of("s1", 5, Optional.empty());
		assertThat(key1).isNotEqualTo(key4);

		// two arrayKeys with empty optionals can be equal to each other
		final ArrayKey key5 = ArrayKey.of("s1", 5, Optional.empty());
		assertThat(key4).isEqualTo(key5);
	}

	@Test
	public void test_compareTo()
	{
		// tests for NULLs last
		test_compareTo(
				+1, // expected
				null, // key1
				new ArrayKey("1", null, "2") // key2
		);
		test_compareTo(
				-1, // expected
				new ArrayKey("1", null, "2"), // key1
				null // key2
		);
		test_compareTo(
				0, // expected
				null, // key1
				null // key2
		);

		//
		test_compareTo(
				0, // expected
				new ArrayKey("1", null, "2"), // key1
				new ArrayKey("1", null, "2") // key2
		);
		test_compareTo(
				-1, // expected
				new ArrayKey("1", null, "2"), // key1
				new ArrayKey("1", null, "3") // key2
		);
		test_compareTo(
				+1, // expected
				new ArrayKey("1", null, "3"), // key1
				new ArrayKey("1", null, "2") // key2
		);

	}

	private final void test_compareTo(final int expected, final ArrayKey key1, final ArrayKey key2)
	{
		final String message = "Invalid compareTo result"
				+ "\n key1=" + key1
				+ "\n key2=" + key2;
		final int actual = ArrayKey.compare(key1, key2);
		Assertions.assertEquals(expected, actual, message);
	}

}
