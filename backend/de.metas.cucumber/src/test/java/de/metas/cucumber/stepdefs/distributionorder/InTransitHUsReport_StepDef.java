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

package de.metas.cucumber.stepdefs.distributionorder;

import de.metas.cucumber.stepdefs.M_Locator_StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.report.AD_Process_StepDef;
import de.metas.i18n.Language;
import de.metas.process.AdProcessId;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.IADProcessDAO;
import de.metas.process.ProcessCalledFrom;
import de.metas.process.ProcessInfo;
import de.metas.report.server.OutputType;
import de.metas.util.Services;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_Locator;
import org.compiere.util.Env;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Prints the In-Transit HUs report ("Materialbegleitschein") through the process path (same as {@code AD_Process_StepDef}),
 * not through the REST print, because the latter enqueues a direct print that needs a printer configuration.
 * The PDF is archived for record (M_Locator, locator id); assert it with the generic {@code AD_Archive_StepDef} steps.
 */
@RequiredArgsConstructor
public class InTransitHUsReport_StepDef
{
	private static final String PROCESS_Value = "DD_Order_MaterialInTransitReport";
	private static final String PARAM_M_Locator_ID = "M_Locator_ID";
	private static final String REPORT_LANGUAGE = "de_DE";

	@NonNull private final M_Locator_StepDefData locatorTable;
	@NonNull private final AD_Process_StepDef adProcessStepDef;

	@NonNull private final IADProcessDAO processDAO = Services.get(IADProcessDAO.class);
	@NonNull private final IADPInstanceDAO pInstanceDAO = Services.get(IADPInstanceDAO.class);

	/**
	 * Runs the In-Transit HUs report for the given locator and archives the resulting PDF (German report language).
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: M_Locator_StepDefData
	 * @cucumber.example
	 * <pre>
	 * When the Materialbegleitschein is printed for locator trolleyLoc
	 * </pre>
	 */
	@When("^the Materialbegleitschein is printed for locator (.*)$")
	public void printMaterialbegleitschein(@NonNull final String locatorIdentifierStr)
	{
		final I_M_Locator locator = locatorTable.get(StepDefDataIdentifier.ofString(locatorIdentifierStr));
		final TableRecordReference locatorRef = TableRecordReference.of(I_M_Locator.Table_Name, locator.getM_Locator_ID());

		final AdProcessId processId = processDAO.retrieveProcessIdByValue(PROCESS_Value);
		assertThat(processId).as("AD_Process " + PROCESS_Value).isNotNull();

		final Language reportLanguage = Language.getLanguage(REPORT_LANGUAGE);

		final ProcessInfo processInfo = ProcessInfo.builder()
				.setCtx(Env.getCtx())
				.setProcessCalledFrom(ProcessCalledFrom.Scheduler)
				.setAD_Process_ID(processId)
				.setAD_PInstance(pInstanceDAO.createAD_PInstance(processId))
				.setReportLanguage(reportLanguage)
				.setJRDesiredOutputType(OutputType.PDF)
				.setArchiveReportData(true)
				.setRecord(locatorRef)
				.addParameter(PARAM_M_Locator_ID, locator.getM_Locator_ID())
				.build();

		adProcessStepDef.runAndArchive(processInfo, reportLanguage);
	}
}
