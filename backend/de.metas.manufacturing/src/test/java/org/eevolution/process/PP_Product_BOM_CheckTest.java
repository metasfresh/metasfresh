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
 * <p>
 * Why JUnit and not only Cucumber: the Cucumber scenarios (bomCheck_categoryReport.feature, bomCheck_invalidUpdatedBy.feature)
 * cover the end-to-end category run with one failing product out of two (names the failed product) and an all-OK run.
 * No scenario covers the capped-list edge cases (exactly 100 characters, first value over 100 characters, the ", …" marker)
 * or the case that every product fails; reaching them in Cucumber would need many products with long values.
 * These tests pin that pure string / count logic without the Cucumber stack.
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

	/** Every product fails (3 of 3): no Cucumber scenario covers it, the category-report scenario has 1 of 2 failing. */
	@Test
	void failedMessageParams_everyProductFails()
	{
		final List<String> failed = new ArrayList<>(Arrays.asList("P1", "P2", "P3"));
		assertThat(PP_Product_BOM_Check.failedMessageParams(failed, 3)).containsExactly(3, 3, "P1, P2, P3");
	}
}
