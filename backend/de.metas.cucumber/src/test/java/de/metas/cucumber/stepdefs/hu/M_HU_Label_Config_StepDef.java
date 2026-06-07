/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.cucumber.stepdefs.hu;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.handlingunits.model.I_M_HU_Label_Config;
import de.metas.process.IADProcessDAO;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.model.InterfaceWrapperHelper;

/**
 * Step definitions for creating {@link I_M_HU_Label_Config} records in cucumber scenarios.
 * Used to configure HU label printing for tests that verify the mass-printing label flow.
 */
@RequiredArgsConstructor
public class M_HU_Label_Config_StepDef
{
	@NonNull private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);

	/**
	 * Creates {@link I_M_HU_Label_Config} records for use in HU label printing scenarios.
	 *
	 * <h3>Required columns:</h3>
	 * <ul>
	 *   <li>{@code HU_SourceDocType} — source document type code (e.g. {@code PI} for Picking)</li>
	 *   <li>{@code LabelReport_Process_ID.Value} — {@code Value} of the {@code AD_Process} record
	 *       to use as the label print format (e.g. {@code M_HU_Report_Print_Labels})</li>
	 *   <li>{@code SeqNo} — sequence number; lower values take priority</li>
	 * </ul>
	 *
	 * <h3>Optional columns:</h3>
	 * <ul>
	 *   <li>{@code OPT.IsApplyToLUs} — apply to LUs (default: {@code N})</li>
	 *   <li>{@code OPT.IsApplyToTUs} — apply to TUs (default: {@code N})</li>
	 *   <li>{@code OPT.IsApplyToCUs} — apply to CUs/VHUs (default: {@code N})</li>
	 *   <li>{@code OPT.IsAutoPrint} — print immediately when HU becomes Active (default: {@code N})</li>
	 *   <li>{@code OPT.AutoPrintCopies} — number of copies for auto-print (default: {@code 1})</li>
	 * </ul>
	 *
	 * <h3>Example:</h3>
	 * <pre>
	 * And metasfresh contains M_HU_Label_Config:
	 *   | HU_SourceDocType | LabelReport_Process_ID.Value    | SeqNo | OPT.IsApplyToTUs |
	 *   | PI               | M_HU_Report_Print_Labels        | 10    | Y                |
	 * </pre>
	 */
	@And("metasfresh contains M_HU_Label_Config:")
	public void metasfresh_contains_m_hu_label_config(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createHULabelConfig);
	}

	private void createHULabelConfig(@NonNull final DataTableRow row)
	{
		final String sourceDocType = row.getAsString(I_M_HU_Label_Config.COLUMNNAME_HU_SourceDocType);
		final int seqNo = row.getAsInt(I_M_HU_Label_Config.COLUMNNAME_SeqNo);

		final String processValue = row.getAsString(I_M_HU_Label_Config.COLUMNNAME_LabelReport_Process_ID + ".Value");
		final int labelReportProcessId = adProcessDAO.retrieveProcessIdByValue(processValue).getRepoId();

		final boolean isApplyToLUs = row.getAsOptionalBoolean(I_M_HU_Label_Config.COLUMNNAME_IsApplyToLUs).orElse(false);
		final boolean isApplyToTUs = row.getAsOptionalBoolean(I_M_HU_Label_Config.COLUMNNAME_IsApplyToTUs).orElse(false);
		final boolean isApplyToCUs = row.getAsOptionalBoolean(I_M_HU_Label_Config.COLUMNNAME_IsApplyToCUs).orElse(false);
		final boolean isAutoPrint = row.getAsOptionalBoolean(I_M_HU_Label_Config.COLUMNNAME_IsAutoPrint).orElse(false);
		final int autoPrintCopies = row.getAsOptionalInt(I_M_HU_Label_Config.COLUMNNAME_AutoPrintCopies).orElse(1);

		final I_M_HU_Label_Config record = InterfaceWrapperHelper.newInstance(I_M_HU_Label_Config.class);
		record.setHU_SourceDocType(sourceDocType);
		record.setSeqNo(seqNo);
		record.setLabelReport_Process_ID(labelReportProcessId);
		record.setIsApplyToLUs(isApplyToLUs);
		record.setIsApplyToTUs(isApplyToTUs);
		record.setIsApplyToCUs(isApplyToCUs);
		record.setIsAutoPrint(isAutoPrint);
		record.setAutoPrintCopies(autoPrintCopies);

		InterfaceWrapperHelper.saveRecord(record);
	}
}
