package de.metas.frontend_testing.masterdata.hu_label_config;

import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.handlingunits.HuUnitType;
import de.metas.handlingunits.report.labels.HULabelSourceDocType;
import de.metas.process.AdProcessId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

/**
 * Creates a partner-scoped {@code M_HU_Label_Config} row, e.g.
 * {@code huLabelConfigs: {lc1: {bpartner: "BP1", huUnitType: "LU", sourceDocType: "PI", process: 540412, autoPrint: true}}}.
 */
@Value
@Builder
@Jacksonized
public class JsonHULabelConfigRequest
{
	/** The business partner the config is scoped to ({@code M_HU_Label_Config.C_BPartner_ID}); never a global config. */
	@NonNull Identifier bpartner;

	/** HU unit type the config applies to: JSON codes {@code LU}, {@code TU} or {@code V} (virtual, the {@code M_HU_PI_Version.HU_UnitType} code - not the enum name {@code VHU}). */
	@NonNull HuUnitType huUnitType;

	/** {@code M_HU_Label_Config.HU_SourceDocType} code (e.g. {@code PI}=Picking). If null, the config matches any source document type. */
	@Nullable HULabelSourceDocType sourceDocType;

	/** {@code M_HU_Label_Config.LabelReport_Process_ID} (an AD_Process_ID). */
	@NonNull AdProcessId process;

	/** {@code M_HU_Label_Config.IsAutoPrint} */
	boolean autoPrint;
}
