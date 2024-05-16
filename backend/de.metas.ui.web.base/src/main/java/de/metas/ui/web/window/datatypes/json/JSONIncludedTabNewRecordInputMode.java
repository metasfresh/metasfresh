package de.metas.ui.web.window.datatypes.json;

import de.metas.ui.web.window.descriptor.IncludedTabNewRecordInputMode;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

public enum JSONIncludedTabNewRecordInputMode
{
	ALL_METHODS,
	QUICK_INPUT_ONLY;

	public static JSONIncludedTabNewRecordInputMode of(@NonNull final IncludedTabNewRecordInputMode inputMode)
	{
		switch (inputMode)
		{
			case ALL_AVAILABLE_METHODS:
				return ALL_METHODS;
			case QUICK_INPUT_ONLY:
				return QUICK_INPUT_ONLY;
			default:
				throw new AdempiereException("Unknown input mode: " + inputMode);
		}
	}
}
