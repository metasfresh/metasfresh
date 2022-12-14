package de.metas.handlingunits.report.labels;

import de.metas.process.AdProcessId;
import de.metas.report.PrintCopies;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

@Value
@Builder
public class HULabelConfig
{
	@NonNull AdProcessId printFormatProcessId;
	boolean autoPrint;
	@With @NonNull PrintCopies autoPrintCopies;
}
