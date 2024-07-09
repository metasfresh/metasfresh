package de.metas.quickinput.config;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QuickInputConfigLayoutTest
{
	@Nested
	class parse
	{
		@Test
		void standardCase()
		{
			assertThat(QuickInputConfigLayout.parse("Field1,Field2,Field3?,Field4"))
					.get()
					.usingRecursiveComparison()
					.isEqualTo(QuickInputConfigLayout.builder()
							.fields(ImmutableList.of(
									QuickInputConfigLayout.Field.builder().fieldName("Field1").mandatory(true).build(),
									QuickInputConfigLayout.Field.builder().fieldName("Field2").mandatory(true).build(),
									QuickInputConfigLayout.Field.builder().fieldName("Field3").mandatory(false).build(),
									QuickInputConfigLayout.Field.builder().fieldName("Field4").mandatory(true).build()
							))
							.build());
		}
	}

}