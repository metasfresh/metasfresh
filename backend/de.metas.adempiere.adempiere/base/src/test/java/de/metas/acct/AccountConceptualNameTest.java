package de.metas.acct;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class AccountConceptualNameTest
{
	@Test
	void interner()
	{
		final String randomName = "RandomName_" + UUID.randomUUID();
		assertThat(AccountConceptualName.ofString(randomName))
				.isSameAs(AccountConceptualName.ofString(randomName));
	}

	@Test
	void comparator()
	{
		final ArrayList<AccountConceptualName> list = new ArrayList<>();
		list.add(AccountConceptualName.ofString("C3"));
		list.add(AccountConceptualName.ofString("C2"));
		list.add(AccountConceptualName.ofString("C1"));
		list.sort(Comparator.naturalOrder());

		assertThat(list)
				.containsExactly(
						AccountConceptualName.ofString("C1"),
						AccountConceptualName.ofString("C2"),
						AccountConceptualName.ofString("C3"));
	}
}