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

	/**
	 * Autoprint can be switched on/off via sysconfig {@link HULabelConfigFromSysConfigProvider#SYSCONFIG_PICKING_LABEL_AUTO_PRINT_ENABLED}.
	 */
	boolean onlyIfAutoPrint;
		
	@Nullable PrintCopies printCopiesOverride;
	
	boolean failOnMissingLabelConfig;
}
