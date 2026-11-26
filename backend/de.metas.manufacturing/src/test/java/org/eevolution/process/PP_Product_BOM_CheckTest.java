package org.eevolution.process;

import com.google.common.base.Strings;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Pins the capped list of failed product values and the counts of the category-run summary message.
 * Plain unit test: the 100-character boundaries are pure string logic that a Cucumber scenario would need many products to reach
 * (the Cucumber scenarios cover the end-to-end category run with one failing product).
 */
class PP_Product_BOM_CheckTest
{
	@Nested
	class CappedList
	{
		@Test
		void singleValue()
		{
			assertThat(PP_Product_BOM_Check.cappedList(Collections.singletonList("P1"))).isEqualTo("P1");
		}

		@Test
		void exactly100Chars_noEllipsis()
		{
			final String a = Strings.repeat("a", 49);
			final String b = Strings.repeat("b", 49); // 49 + 2 + 49 = 100
			assertThat(PP_Product_BOM_Check.cappedList(Arrays.asList(a, b))).isEqualTo(a + ", " + b).hasSize(100);
		}

		@Test
		void chars101_lastValueReplacedByEllipsis()
		{
			final String a = Strings.repeat("a", 49);
			final String b = Strings.repeat("b", 50); // 101
			assertThat(PP_Product_BOM_Check.cappedList(Arrays.asList(a, b))).isEqualTo(a + ", …");
		}

		@Test
		void moreFollowAfterFit()
		{
			final String a = Strings.repeat("a", 48); // 48 + 2 + 48 = 98, "c" would make 101
			final String b = Strings.repeat("b", 48);
			assertThat(PP_Product_BOM_Check.cappedList(Arrays.asList(a, b, "c", "d"))).isEqualTo(a + ", " + b + ", …");
		}

		@Test
		void firstValueOver100Chars_keptWhole()
		{
			final String a = Strings.repeat("a", 120);
			assertThat(PP_Product_BOM_Check.cappedList(Collections.singletonList(a))).isEqualTo(a);
			assertThat(PP_Product_BOM_Check.cappedList(Arrays.asList(a, "b"))).isEqualTo(a + ", …");
		}
	}

	@Test
	void failedMessageParams_everyProductFails()
	{
		final List<String> failed = new ArrayList<>(Arrays.asList("P1", "P2", "P3"));
		assertThat(PP_Product_BOM_Check.failedMessageParams(failed, 3)).containsExactly(3, 3, "P1, P2, P3");
	}
}
