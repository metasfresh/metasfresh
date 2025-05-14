package de.metas.ui.web.window.datatypes;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static de.metas.ui.web.window.datatypes.DocumentIdsSelection.ALL;
import static de.metas.ui.web.window.datatypes.DocumentIdsSelection.EMPTY;
import static org.assertj.core.api.Assertions.assertThat;

class DocumentIdsSelectionTest
{
	@Nested
	class addAll
	{
		@Test
		void EMPTY_and_ALL()
		{
			assertThat(EMPTY.addAll(EMPTY)).isSameAs(EMPTY);
			assertThat(EMPTY.addAll(ALL)).isSameAs(ALL);
			assertThat(ALL.addAll(EMPTY)).isSameAs(ALL);
			assertThat(ALL.addAll(ALL)).isSameAs(ALL);
		}

		@Test
		void EMPTY_and_fixedSet()
		{
			final DocumentIdsSelection fixedSet = DocumentIdsSelection.ofCommaSeparatedString("1,2,3");
			assertThat(EMPTY.addAll(fixedSet)).isSameAs(fixedSet);
			assertThat(fixedSet.addAll(EMPTY)).isSameAs(fixedSet);
		}

		@Test
		void ALL_and_fixedSet()
		{
			final DocumentIdsSelection fixedSet = DocumentIdsSelection.ofCommaSeparatedString("1,2,3");
			assertThat(ALL.addAll(fixedSet)).isSameAs(ALL);
			assertThat(fixedSet.addAll(ALL)).isSameAs(ALL);
		}

		@Test
		void overlapping_fixedSets()
		{
			final DocumentIdsSelection fixedSet1 = DocumentIdsSelection.ofCommaSeparatedString("1,2,3");
			final DocumentIdsSelection fixedSet2 = DocumentIdsSelection.ofCommaSeparatedString("3,4,5");
			assertThat(fixedSet1.addAll(fixedSet2)).isEqualTo(DocumentIdsSelection.ofCommaSeparatedString("1,2,3,4,5"));
		}

		@Test
		void disjunct_fixedSets()
		{
			final DocumentIdsSelection fixedSet1 = DocumentIdsSelection.ofCommaSeparatedString("1,2,3");
			final DocumentIdsSelection fixedSet2 = DocumentIdsSelection.ofCommaSeparatedString("7,8,9");
			assertThat(fixedSet1.addAll(fixedSet2)).isEqualTo(DocumentIdsSelection.ofCommaSeparatedString("1,2,3,7,8,9"));
		}

		@Test
		void sameFixedSet()
		{
			final DocumentIdsSelection fixedSet = DocumentIdsSelection.ofCommaSeparatedString("1,2,3");
			assertThat(fixedSet.addAll(fixedSet)).isSameAs(fixedSet);
		}

		@Test
		void included_sets()
		{
			final DocumentIdsSelection fixedSet1 = DocumentIdsSelection.ofCommaSeparatedString("1,2,3");
			final DocumentIdsSelection fixedSet2 = DocumentIdsSelection.ofCommaSeparatedString("1,3");
			assertThat(fixedSet1.addAll(fixedSet2)).isSameAs(fixedSet1);
			assertThat(fixedSet2.addAll(fixedSet1)).isSameAs(fixedSet1);
		}
	}
}