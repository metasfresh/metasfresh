package de.metas.handlingunits.report.labels;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.report.HUToReport;
import de.metas.report.PrintCopies;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class HULabelPrintRequest
{
	@NonNull HULabelSourceDocType sourceDocType;
	@NonNull @Singular("hu") ImmutableList<HUToReport> hus;
	boolean onlyIfAutoPrint;
	@Nullable PrintCopies printCopiesOverride;
	boolean failOnMissingLabelConfig;
}
