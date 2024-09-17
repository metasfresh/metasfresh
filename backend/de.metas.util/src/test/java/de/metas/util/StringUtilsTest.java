package de.metas.util;

import com.google.common.collect.ImmutableList;
import de.metas.util.StringUtils.TruncateAt;
import org.adempiere.util.lang.IPair;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class StringUtilsTest
{
	@Test
	public void trunc()
	{
		testTrunc(null, 10, null);
		testTrunc("", 0, "");
		testTrunc("", 10, "");
		testTrunc("1234567890", 0, "");
		testTrunc("1234567890", 1, "1");
		testTrunc("1234567890", 2, "12");
		testTrunc("1234567890", 3, "123");
		testTrunc("1234567890", 4, "1234");
		testTrunc("1234567890", 5, "12345");
		testTrunc("1234567890", 6, "123456");
		testTrunc("1234567890", 7, "1234567");
		testTrunc("1234567890", 8, "12345678");
		testTrunc("1234567890", 9, "123456789");
		testTrunc("1234567890", 10, "1234567890");
		testTrunc("1234567890", 11, "1234567890");
		testTrunc("1234567890", 12, "1234567890");
		testTrunc("1234567890", 13, "1234567890");
	}

	@Test
	public void testTrunc_invalidLength()
	{
		assertThatThrownBy(() -> testTrunc("1234567890", -10, "1234567890"))
				.isInstanceOf(StringIndexOutOfBoundsException.class);
	}

	private void testTrunc(@Nullable final String string, final int length, @Nullable final String resultExpected)
	{
		assertThat(StringUtils.trunc(string, length))
				.as("Invalid trunc() result for string=%s, length=%s", string, length)
				.isEqualTo(resultExpected);
	}

	@Test
	public void truncLeftr()
	{
		testTruncLeft(null, 10, null);
		testTruncLeft("", 0, "");
		testTruncLeft("", 10, "");
		testTruncLeft("1234567890", 0, "");
		testTruncLeft("1234567890", 1, "0");
		testTruncLeft("1234567890", 2, "90");
		testTruncLeft("1234567890", 3, "890");
		testTruncLeft("1234567890", 4, "7890");
		testTruncLeft("1234567890", 5, "67890");
		testTruncLeft("1234567890", 6, "567890");
		testTruncLeft("1234567890", 7, "4567890");
		testTruncLeft("1234567890", 8, "34567890");
		testTruncLeft("1234567890", 9, "234567890");
		testTruncLeft("1234567890", 10, "1234567890");
		testTruncLeft("1234567890", 11, "1234567890");
		testTruncLeft("1234567890", 12, "1234567890");
		testTruncLeft("1234567890", 13, "1234567890");
	}

	private void testTruncLeft(@Nullable final String string, final int length, @Nullable final String resultExpected)
	{
		assertThat(StringUtils.trunc(string, length, TruncateAt.STRING_START))
				.as("Invalid truncLeft() result for string=%s, length=%s", string, length)
				.isEqualTo(resultExpected);
	}

	@Test
	public void test_isNumber_Null()
	{
		test_isNumber(null, false);
	}

	@Test
	public void test_isNumber_Empty()
	{
		test_isNumber("", false);
	}

	@Test
	public void test_isNumber_StringWithSpaces()
	{
		test_isNumber(" 123", false);
	}

	@Test
	public void test_isNumber_StandardCases()
	{
		test_isNumber("1", true);
		test_isNumber("01", true);
		test_isNumber("01234567890", true);
		test_isNumber("0000000000000", true);
	}

	@Test
	public void test_quote()
	{
		assertThat(StringUtils.formatMessage("{0} text with ' quote", "test")).isEqualTo("test text with ' quote");
		assertThat(StringUtils.formatMessage("text with ' quote {0}", "test")).isEqualTo("text with ' quote test");
		assertThat(StringUtils.formatMessage("{0} text with '' doublequote", "test")).isEqualTo("test text with '' doublequote");
		assertThat(StringUtils.formatMessage("text with '' doublequote {0}", "test")).isEqualTo("text with '' doublequote test");
	}

	@Test
	public void test_overlayAtEnd()
	{
		assertThat(StringUtils.overlayAtEnd("0000000000", "123456")).isEqualTo("0000123456");
		assertThat(StringUtils.overlayAtEnd("000a000000", "123456")).isEqualTo("000a123456");
		assertThat(StringUtils.overlayAtEnd("000", "123456")).isEqualTo("123456");
	}

	private void test_isNumber(@Nullable final String string, final boolean expected)
	{
		assertThat(StringUtils.isNumber(string))
				.withFailMessage("Invalid StringUtils.isNumber() return for string: " + string)
				.isEqualTo(expected);
	}

	@Test
	public void splitStreetAndHouseNumberOrNull()
	{
		final IPair<String, String> result = StringUtils.splitStreetAndHouseNumberOrNull("Carretera Nueva Jarilla");

		assertThat(result).isNotNull();
		assertThat(result.getLeft()).isEqualTo("Carretera Nueva Jarilla");
		assertThat(result.getRight()).isNullOrEmpty();
	}

	@Test
	public void splitStreetAndHouseNumberOrNull_2()
	{
		final IPair<String, String> result = StringUtils.splitStreetAndHouseNumberOrNull("Laternenstrasse 14");

		assertThat(result).isNotNull();
		assertThat(result.getLeft()).isEqualTo("Laternenstrasse");
		assertThat(result.getRight()).isEqualTo("14");
	}

	@Test
	public void splitStreetAndHouseNumberOrNull_3()
	{
		final IPair<String, String> result = StringUtils.splitStreetAndHouseNumberOrNull("Laternenstrasse 14-26c");

		assertThat(result).isNotNull();
		assertThat(result.getLeft()).isEqualTo("Laternenstrasse");
		assertThat(result.getRight()).isEqualTo("14-26c");
	}

	@Test
	public void test_trimBlankToNull()
	{
		assertThat(StringUtils.trimBlankToNull(null)).isNull();
		assertThat(StringUtils.trimBlankToNull("")).isNull();
		assertThat(StringUtils.trimBlankToNull(" ")).isNull();
		assertThat(StringUtils.trimBlankToNull(" \t\n ")).isNull();
		assertThat(StringUtils.trimBlankToNull(" \taaaa\n ")).isEqualTo("aaaa");

		{
			final String s = "test";
			assertThat(StringUtils.trimBlankToNull(s)).isSameAs(s);
		}
	}

	@Test
	public void test_cleanWhitespace()
	{
		assertThat(StringUtils.cleanWhitespace("")).isEmpty();
		assertThat(StringUtils.cleanWhitespace(" ")).isEmpty();
		assertThat(StringUtils.cleanWhitespace(" \t\n ")).isEmpty();
		assertThat(StringUtils.cleanWhitespace(" \taaaa\n ")).isEqualTo("aaaa");
		assertThat(StringUtils.cleanWhitespace("CH34 8914 4463 3729 49 43 8")).isEqualTo("CH3489144463372949438");

		{
			final String s = "test";
			assertThat(StringUtils.cleanWhitespace(s)).isSameAs(s);
		}
	}

	@Test
	public void tokenizeStringToIntegers()
	{
		final ImmutableList<Integer> result = StringUtils.tokenizeStringToIntegers(
				"2195601\n"
						+ "   \n"
						+ "2195602, \n"
						+ "2195603, \n"
						+ "2195604 ,\n"
						+ "2199111,2175651\n"
						+ "2195605,\t 2195606abc2195607");

		assertThat(result).containsExactly(2195601, 2195602, 2195603, 2195604, 2199111, 2175651, 2195605, 2195606, 2195607);
	}

	@Nested
	class trimBlankToOptional
	{
		@Test
		void nullValue() {assertThat(StringUtils.trimBlankToOptional(null)).isEmpty();}

		@Test
		void empty() {assertThat(StringUtils.trimBlankToOptional("")).isEmpty();}

		@Test
		void blank() {assertThat(StringUtils.trimBlankToOptional("   \t   ")).isEmpty();}

		@Test
		void nonBlank() {assertThat(StringUtils.trimBlankToOptional("   \taaa\r\n   ")).contains("aaa");}
	}

	@Nested
	class trimBlankToNullAndMap
	{
		String append2(String s) {return s + "2";}

		@Test
		void nullValue() {assertThat(StringUtils.trimBlankToNullAndMap(null, this::append2)).isNull();}

		@Test
		void empty() {assertThat(StringUtils.trimBlankToNullAndMap("", this::append2)).isNull();}

		@Test
		void blank() {assertThat(StringUtils.trimBlankToNullAndMap("   \t   ", this::append2)).isNull();}

		@Test
		void nonBlank() {assertThat(StringUtils.trimBlankToNullAndMap("   \taaa\r\n   ", this::append2)).contains("aaa2");}
	}
}
