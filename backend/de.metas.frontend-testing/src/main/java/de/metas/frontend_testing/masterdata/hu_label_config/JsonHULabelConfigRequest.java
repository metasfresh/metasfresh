package de.metas.frontend_testing.masterdata.hu_label_config;

import de.metas.frontend_testing.masterdata.Identifier;
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

	/** HU unit type the config applies to: {@code LU}, {@code TU} or {@code VHU}. */
	@NonNull String huUnitType;

	/** {@code M_HU_Label_Config.HU_SourceDocType} code (e.g. {@code PI}=Picking). If null, the config matches any source document type. */
	@Nullable String sourceDocType;

	/** {@code M_HU_Label_Config.LabelReport_Process_ID} (an AD_Process_ID). */
	int process;

	/** {@code M_HU_Label_Config.IsAutoPrint} */
	boolean autoPrint;
}
