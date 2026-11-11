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

package de.metas.cucumber.stepdefs.report;

import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.calendar.C_Year_StepDefData;
import de.metas.cucumber.stepdefs.util.IdentifiersResolver;
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
import org.compiere.util.Env;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Runs the Jasper report "Customer/Product Statistics (Long)" with parameters through the process path
 * (same as {@code AD_Process_StepDef}, not the REST print) and archives the PDF against a given record.
 * Assert the PDF with the generic {@code AD_Archive_StepDef} steps.
 */
@RequiredArgsConstructor
public class CustomerProductStatistics_StepDef
{
	private static final String PROCESS_Value = "Customer_Product_Statistics";
	private static final String PARAM_AD_Org_ID = "AD_Org_ID";
	private static final String PARAM_C_Year_ID = "C_Year_ID";
	private static final String PARAM_C_BPartner_ID = "C_BPartner_ID";
	private static final String PARAM_M_Product_ID = "M_Product_ID";
	private static final String NOT_SET = "-";

	@NonNull private final C_Year_StepDefData yearTable;
	@NonNull private final C_BPartner_StepDefData bpartnerTable;
	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final IdentifiersResolver identifiersResolver;
	@NonNull private final AD_Process_StepDef adProcessStepDef;

	@NonNull private final IADProcessDAO processDAO = Services.get(IADProcessDAO.class);
	@NonNull private final IADPInstanceDAO pInstanceDAO = Services.get(IADPInstanceDAO.class);

	/**
	 * Runs the report for the given year and archives the PDF for the given record (a C_BPartner identifier).
	 *
	 * @param yearIdentifier       {@code C_Year} identifier
	 * @param customerIdentifier   {@code C_BPartner} identifier, or {@code -} for not set
	 * @param productIdentifier    {@code M_Product} identifier, or {@code -} for not set
	 * @param language             report language, e.g. {@code de_DE}
	 * @param recordIdentifier     identifier of the record (e.g. a C_BPartner) the PDF is archived for
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: C_Year_StepDefData, C_BPartner_StepDefData, M_Product_StepDefData
	 * @cucumber.example
	 * <pre>
	 * When the customer product statistics report is run for year "yearN", customer "-", product "-" in language "de_DE", archived for the record identified by "bp1"
	 * </pre>
	 */
	@When("the customer product statistics report is run for year {string}, customer {string}, product {string} in language {string}, archived for the record identified by {string}")
	public void runReport(
			@NonNull final String yearIdentifier,
			@NonNull final String customerIdentifier,
			@NonNull final String productIdentifier,
			@NonNull final String language,
			@NonNull final String recordIdentifier)
	{
		final int yearId = yearTable.get(StepDefDataIdentifier.ofString(yearIdentifier)).getC_Year_ID();
		final TableRecordReference recordRef = identifiersResolver.getTableRecordReference(StepDefDataIdentifier.ofString(recordIdentifier));

		final AdProcessId processId = processDAO.retrieveProcessIdByValue(PROCESS_Value);
		assertThat(processId).as("AD_Process " + PROCESS_Value).isNotNull();

		final Language reportLanguage = Language.getLanguage(language);

		final ProcessInfo.ProcessInfoBuilder builder = ProcessInfo.builder()
				.setCtx(Env.getCtx())
				.setProcessCalledFrom(ProcessCalledFrom.Scheduler)
				.setAD_Process_ID(processId)
				.setAD_PInstance(pInstanceDAO.createAD_PInstance(processId))
				.setReportLanguage(reportLanguage)
				.setJRDesiredOutputType(OutputType.PDF)
				.setArchiveReportData(true)
				.setRecord(recordRef)
				.addParameter(PARAM_AD_Org_ID, StepDefConstants.ORG_ID.getRepoId())
				.addParameter(PARAM_C_Year_ID, yearId);

		if (!NOT_SET.equals(customerIdentifier))
		{
			builder.addParameter(PARAM_C_BPartner_ID, bpartnerTable.get(StepDefDataIdentifier.ofString(customerIdentifier)).getC_BPartner_ID());
		}
		if (!NOT_SET.equals(productIdentifier))
		{
			builder.addParameter(PARAM_M_Product_ID, productTable.get(StepDefDataIdentifier.ofString(productIdentifier)).getM_Product_ID());
		}

		adProcessStepDef.runAndArchive(builder.build(), reportLanguage);
	}
}
