package de.metas.handlingunits.report.labels;

import de.metas.i18n.ITranslatableString;
import de.metas.process.AdProcessId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class HULabelPrintProcess
{
	@NonNull AdProcessId processId;
	@NonNull ITranslatableString name;
}
