package de.metas.handlingunits.report.labels;

import de.metas.process.AdProcessId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class HULabelConfig
{
	@NonNull AdProcessId printFormatProcessId;
	boolean autoPrint;
	int autoPrintCopies;
}
