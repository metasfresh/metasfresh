package de.metas.ui.web.window.descriptor.decorator;

import de.metas.i18n.BooleanWithReason;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ReadOnlyInfoTest
{
	@Test
	void of_StandardCases()
	{
		assertThat(ReadOnlyInfo.of(BooleanWithReason.TRUE)).isSameAs(ReadOnlyInfo.TRUE);
		assertThat(ReadOnlyInfo.of(BooleanWithReason.FALSE)).isSameAs(ReadOnlyInfo.FALSE);

		assertThat(ReadOnlyInfo.of(BooleanWithReason.falseBecause("bla")))
				.isNotSameAs(ReadOnlyInfo.FALSE)
				.isEqualTo(ReadOnlyInfo.builder()
						.isReadOnly(BooleanWithReason.falseBecause("bla"))
						.forceReadOnlySubDocuments(false)
						.build());
	}
}