package de.metas.ui.web.window.datatypes.json;

import de.metas.ui.web.window.descriptor.IncludedTabNewRecordInputMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class JSONIncludedTabNewRecordInputModeTest
{
	@ParameterizedTest
	@EnumSource(IncludedTabNewRecordInputMode.class)
	void of_IncludedTabNewRecordInputMode(final IncludedTabNewRecordInputMode inputMode)
	{
		final JSONIncludedTabNewRecordInputMode json = JSONIncludedTabNewRecordInputMode.of(inputMode);
		assertNotNull(json);
	}
}