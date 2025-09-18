package de.metas.ui.web.window.datatypes.json;

import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.WindowId;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JSONProcessNewRecordRequest
{
	@Nullable WindowId triggeringWindowId;
	@Nullable DocumentId triggeringDocumentId;
	@Nullable String triggeringField;
}
