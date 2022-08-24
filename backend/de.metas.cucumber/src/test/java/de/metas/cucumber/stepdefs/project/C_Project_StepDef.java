/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.cucumber.stepdefs.project;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import de.metas.common.rest_api.v2.project.JsonResponseProjectUpsert;
import de.metas.common.rest_api.v2.project.JsonResponseProjectUpsertItem;
import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.R_Status_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.cucumber.stepdefs.projectType.C_ProjectType_StepDefData;
import de.metas.currency.Currency;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyDAO;
import de.metas.organization.OrgId;
import de.metas.project.ProjectId;
import de.metas.project.service.ProjectRepository;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Project;
import org.compiere.model.I_C_ProjectType;
import org.compiere.model.I_R_Status;

import java.util.List;
import java.util.Map;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

public class C_Project_StepDef
{
	private final ProjectRepository projectRepository = SpringContextHolder.instance.getBean(ProjectRepository.class);
	private final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);
	private final ObjectMapper mapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

	private final TestContext testContext;
	private final C_Project_StepDefData projectTable;
	private final C_BPartner_StepDefData bpartnerTable;
	private final C_ProjectType_StepDefData projectTypeTable;
	private final R_Status_StepDefData rStatusTable;

	public C_Project_StepDef(
			@NonNull final TestContext testContext,
			@NonNull final C_Project_StepDefData projectTable,
			@NonNull final C_BPartner_StepDefData bpartnerTable,
			@NonNull final C_ProjectType_StepDefData projectTypeTable,
			@NonNull final R_Status_StepDefData rStatusTable)
	{
		this.testContext = testContext;
		this.projectTable = projectTable;
		this.bpartnerTable = bpartnerTable;
		this.projectTypeTable = projectTypeTable;
		this.rStatusTable = rStatusTable;
	}

	@Given("metasfresh contains C_Project")
	public void metasfresh_contains_C_Project(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			metasfreshContainsProject(tableRow);
		}
	}

	@When("process metasfresh project upsert response")
	public void process_project_upsert_response(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);

		final JsonResponseProjectUpsert jsonResponseProjectUpsert = mapper.readValue(testContext.getApiResponse().getContent(), JsonResponseProjectUpsert.class);
		assertThat(jsonResponseProjectUpsert).isNotNull();
		assertThat(jsonResponseProjectUpsert.getResponseItems().size()).isEqualTo(tableRows.size());

		int index = 0;
		for (final Map<String, String> tableRow : tableRows)
		{
			processProjectUpsertResponse(tableRow, jsonResponseProjectUpsert.getResponseItems().get(index));
			index++;
		}
	}

	@Then("validate the created projects")
	public void validate_project(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			validateProject(tableRow);
		}
	}

	private void metasfreshContainsProject(@NonNull final Map<String, String> tableRow)
	{
		final int projectId = DataTableUtil.extractIntForColumnName(tableRow, I_C_Project.COLUMNNAME_C_Project_ID);
		final String name = DataTableUtil.extractStringForColumnName(tableRow, I_C_Project.COLUMNNAME_Name);
		final String currencyIsoCode = DataTableUtil.extractStringForColumnName(tableRow, I_C_Project.COLUMNNAME_C_Currency_ID + ".ISO_Code");

		final Currency currency = currencyDAO.getByCurrencyCode(CurrencyCode.ofThreeLetterCode(currencyIsoCode));

		final I_C_Project projectRecord = CoalesceUtil.coalesceSuppliersNotNull(
				() -> InterfaceWrapperHelper.load(projectId, I_C_Project.class),
				() -> InterfaceWrapperHelper.newInstance(I_C_Project.class)
		);

		projectRecord.setAD_Org_ID(OrgId.MAIN.getRepoId());
		projectRecord.setC_Project_ID(projectId);
		projectRecord.setName(name);
		projectRecord.setC_Currency_ID(currency.getId().getRepoId());

		saveRecord(projectRecord);
	}

	private void validateProject(@NonNull final Map<String, String> tableRow)
	{
		final String projectIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_Project.COLUMNNAME_C_Project_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		final I_C_Project projectRecord = projectTable.get(projectIdentifier);

		final Integer projectId = DataTableUtil.extractIntegerOrNullForColumnName(tableRow, "OPT." + I_C_Project.COLUMNNAME_C_Project_ID);

		if (projectId != null)
		{
			assertThat(projectRecord.getC_Project_ID()).isEqualTo(projectId);
		}

		final String name = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Project.COLUMNNAME_Name);

		if (Check.isNotBlank(name))
		{
			assertThat(projectRecord.getName()).isEqualTo(name);
		}

		final String description = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Project.COLUMNNAME_Description);

		if (Check.isNotBlank(description))
		{
			assertThat(projectRecord.getDescription()).isEqualTo(description);
		}

		final String bpartnerIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Project.COLUMNNAME_C_BPartner_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		if (Check.isNotBlank(bpartnerIdentifier))
		{
			final Integer bpartnerId = bpartnerTable.getOptional(bpartnerIdentifier)
					.map(I_C_BPartner::getC_BPartner_ID)
					.orElseGet(() -> Integer.parseInt(bpartnerIdentifier));

			assertThat(projectRecord.getC_BPartner_ID()).isEqualTo(bpartnerId);
		}

		final String currencyIsoCode = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Project.COLUMNNAME_C_Currency_ID + ".ISO_Code");

		if (Check.isNotBlank(currencyIsoCode))
		{
			final Currency currency = currencyDAO.getByCurrencyCode(CurrencyCode.ofThreeLetterCode(currencyIsoCode));
			assertThat(projectRecord.getC_Currency_ID()).isEqualTo(currency.getId().getRepoId());
		}

		final String projectTypeIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Project.COLUMNNAME_C_ProjectType_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		if (Check.isNotBlank(projectTypeIdentifier))
		{
			final Integer projectTypeId = projectTypeTable.getOptional(projectTypeIdentifier)
					.map(I_C_ProjectType::getC_ProjectType_ID)
					.orElseGet(() -> Integer.parseInt(projectTypeIdentifier));

			assertThat(projectRecord.getC_ProjectType_ID()).isEqualTo(projectTypeId);
		}

		final String rStatusIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Project.COLUMNNAME_R_Project_Status_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		if (Check.isNotBlank(rStatusIdentifier))
		{
			final Integer rStatusId = rStatusTable.getOptional(rStatusIdentifier)
					.map(I_R_Status::getR_Status_ID)
					.orElseGet(() -> Integer.parseInt(rStatusIdentifier));

			assertThat(projectRecord.getR_Project_Status_ID()).isEqualTo(rStatusId);
		}

		final boolean isActive = DataTableUtil.extractBooleanForColumnNameOr(tableRow, "OPT." + I_C_Project.COLUMNNAME_IsActive, true);

		assertThat(projectRecord.isActive()).isEqualTo(isActive);
	}

	private void processProjectUpsertResponse(
			@NonNull final Map<String, String> tableRow,
			@NonNull final JsonResponseProjectUpsertItem jsonResponseProjectUpsertItem)
	{
		final String projectIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_Project.COLUMNNAME_C_Project_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		final I_C_Project projectRecord = projectRepository.getRecordById(ProjectId.ofRepoId(jsonResponseProjectUpsertItem.getMetasfreshId().getValue()));

		projectTable.putOrReplace(projectIdentifier, projectRecord);
	}
}
