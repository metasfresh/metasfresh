package de.metas.ui.web.quickinput;

import com.google.common.collect.ImmutableList;
import de.metas.quickinput.config.QuickInputConfigLayout;
import de.metas.quickinput.config.QuickInputConfigService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QuickInputConfigServiceTest
{
	@Nested
	class parseLayoutFromSysconfigValue
	{
		@Test
		void standardCase()
		{
			assertThat(QuickInputConfigService.parseLayoutFromSysconfigValue("Field1,Field2,Field3?,Field4"))
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