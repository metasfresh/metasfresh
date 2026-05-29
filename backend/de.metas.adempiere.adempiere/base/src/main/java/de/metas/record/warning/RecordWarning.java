package de.metas.record.warning;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class RecordWarning
{
	@NonNull RecordWarningId id;
	@NonNull String msgText;
}
