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
import de.metas.cucumber.stepdefs.AD_User_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_Location_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.R_Status_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.cucumber.stepdefs.projectType.C_ProjectType_StepDefData;
import de.metas.currency.Currency;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyDAO;
import de.metas.organization.OrgId;
import de.metas.project.ProjectId;
import de.metas.project.status.RStatusId;
import de.metas.project.service.ProjectRepository;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Project;
import org.compiere.model.I_C_ProjectType;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_R_Status;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

public class C_Project_StepDef
{
	private static final RStatusId OPPORTUNITY_STATUS_CATEGORY_ID = RStatusId.ofRepoId(540004);

	private final ProjectRepository projectRepository = SpringContextHolder.instance.getBean(ProjectRepository.class);
	private final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);
	private final ObjectMapper mapper = JsonObjectMapperHolder.sharedJsonObjectMapper();
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final TestContext testContext;
	private final C_Project_StepDefData projectTable;
	private final C_BPartner_StepDefData bpartnerTable;
	private final C_ProjectType_StepDefData projectTypeTable;
	private final R_Status_StepDefData rStatusTable;
	private final C_BPartner_Location_StepDefData bpartnerLocationTable;
	private final AD_User_StepDefData userTable;
	private final M_Product_StepDefData productTable;

	public C_Project_StepDef(
			@NonNull final TestContext testContext,
			@NonNull final C_Project_StepDefData projectTable,
			@NonNull final C_BPartner_StepDefData bpartnerTable,
			@NonNull final C_ProjectType_StepDefData projectTypeTable,
			@NonNull final R_Status_StepDefData rStatusTable,
			@NonNull final C_BPartner_Location_StepDefData bpartnerLocationTable,
			@NonNull final AD_User_StepDefData userTable,
			@NonNull final M_Product_StepDefData productTable)
	{
		this.testContext = testContext;
		this.projectTable = projectTable;
		this.bpartnerTable = bpartnerTable;
		this.projectTypeTable = projectTypeTable;
		this.rStatusTable = rStatusTable;
		this.bpartnerLocationTable = bpartnerLocationTable;
		this.userTable = userTable;
		this.productTable = productTable;
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

	@Given("create or update C_Project:")
	public void create_update_C_Project(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String name = DataTableUtil.extractStringForColumnName(tableRow, I_C_Project.COLUMNNAME_Name);
			final String value = DataTableUtil.extractStringForColumnName(tableRow, I_C_Project.COLUMNNAME_Value);
			final String currencyIsoCode = DataTableUtil.extractStringForColumnName(tableRow, I_C_Project.COLUMNNAME_C_Currency_ID + ".ISO_Code");

			final Currency currency = currencyDAO.getByCurrencyCode(CurrencyCode.ofThreeLetterCode(currencyIsoCode));

			final I_C_Project project = CoalesceUtil.coalesceSuppliersNotNull(
					() -> queryBL.createQueryBuilder(I_C_Project.class)
							.addEqualsFilter(I_C_Project.COLUMNNAME_Value, value)
							.create()
							.firstOnly(I_C_Project.class),
					() -> newInstance(I_C_Project.class));

			project.setName(name);
			project.setValue(value);
			project.setR_StatusCategory_ID(OPPORTUNITY_STATUS_CATEGORY_ID.getRepoId());
			project.setAD_Org_ID(OrgId.MAIN.getRepoId());
			project.setC_Currency_ID(currency.getId().getRepoId());

			final String bPartnerIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Project.COLUMNNAME_C_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(bPartnerIdentifier))
			{
				final I_C_BPartner bPartner = bpartnerTable.get(bPartnerIdentifier);
				project.setC_BPartner_ID(bPartner.getC_BPartner_ID());
			}

			final String bPartnerLocationIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Project.COLUMNNAME_C_BPartner_Location_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(bPartnerLocationIdentifier))
			{
				final I_C_BPartner_Location bPartnerLocation = bpartnerLocationTable.get(bPartnerLocationIdentifier);
				project.setC_BPartner_Location_ID(bPartnerLocation.getC_BPartner_Location_ID());
			}

			final String userIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Project.COLUMNNAME_AD_User_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(userIdentifier))
			{
				final I_AD_User user = userTable.get(userIdentifier);
				project.setAD_User_ID(user.getAD_User_ID());
			}

			final String productIdentifier = DataTableUtil.extractNullableStringForColumnName(tableRow, "OPT." + I_C_Project.COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(productIdentifier))
			{
				final String nullableIdentifier = DataTableUtil.nullToken2Null(productIdentifier);
				if (nullableIdentifier == null)
				{
					project.setM_Product_ID(-1);
				}
				else
				{
					final I_M_Product product = productTable.get(productIdentifier);
					project.setM_Product_ID(product.getM_Product_ID());
				}
			}

			InterfaceWrapperHelper.saveRecord(project);

			final String projectIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_Project.COLUMNNAME_C_Project_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			projectTable.putOrReplace(projectIdentifier, project);
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
		final Integer projectId = DataTableUtil.extractIntegerOrNullForColumnName(tableRow, I_C_Project.COLUMNNAME_C_Project_ID);
		final String name = DataTableUtil.extractStringForColumnName(tableRow, I_C_Project.COLUMNNAME_Name);
		final String currencyIsoCode = DataTableUtil.extractStringForColumnName(tableRow, I_C_Project.COLUMNNAME_C_Currency_ID + ".ISO_Code");

		final Currency currency = currencyDAO.getByCurrencyCode(CurrencyCode.ofThreeLetterCode(currencyIsoCode));

		final I_C_Project projectRecord = InterfaceWrapperHelper.newInstance(I_C_Project.class);
		projectRecord.setR_StatusCategory_ID(OPPORTUNITY_STATUS_CATEGORY_ID.getRepoId());
		projectRecord.setAD_Org_ID(OrgId.MAIN.getRepoId());
		projectRecord.setName(name);
		projectRecord.setC_Currency_ID(currency.getId().getRepoId());

		if (projectId != null)
		{
			projectRecord.setC_Project_ID(projectId);
		}

		final String projectCategory = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Project.COLUMNNAME_ProjectCategory);
		if (Check.isNotBlank(projectCategory))
		{
			projectRecord.setProjectCategory(projectCategory);
		}

		final String value = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Project.COLUMNNAME_Value);
		if (Check.isNotBlank(value))
		{
			projectRecord.setValue(value);
		}

		final String projectTypeIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Project.COLUMNNAME_C_ProjectType_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(projectTypeIdentifier))
		{
			final I_C_ProjectType projectType = projectTypeTable.get(projectTypeIdentifier);
			assertThat(projectType).isNotNull();

			projectRecord.setC_ProjectType_ID(projectType.getC_ProjectType_ID());
		}

		final String projectRefExt = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Project.COLUMNNAME_C_Project_Reference_Ext);
		if (Check.isNotBlank(projectRefExt))
		{
			projectRecord.setC_Project_Reference_Ext(projectRefExt);
		}

		final String bpartnerDepartment = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Project.COLUMNNAME_BPartnerDepartment);
		if (Check.isNotBlank(bpartnerDepartment))
		{
			projectRecord.setBPartnerDepartment(bpartnerDepartment);
		}

		final String specialistConsultantIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Project.COLUMNNAME_Specialist_Consultant_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(specialistConsultantIdentifier))
		{
			final I_AD_User specialistConsultant = userTable.get(specialistConsultantIdentifier);
			assertThat(specialistConsultant).isNotNull();

			projectRecord.setSpecialist_Consultant_ID(specialistConsultant.getAD_User_ID());
		}

		final String salesRepIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Project.COLUMNNAME_SalesRep_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(salesRepIdentifier))
		{
			final I_AD_User salesRep = userTable.get(salesRepIdentifier);
			assertThat(salesRep).isNotNull();

			projectRecord.setSalesRep_ID(salesRep.getAD_User_ID());
		}

		final String internalPriority = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Project.COLUMNNAME_InternalPriority);
		if (Check.isNotBlank(internalPriority))
		{
			projectRecord.setInternalPriority(internalPriority);
		}

		saveRecord(projectRecord);

		final String projectIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Project.COLUMNNAME_C_Project_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(projectIdentifier))
		{
			projectTable.putOrReplace(projectIdentifier, projectRecord);
		}
	}

	private void validateProject(@NonNull final Map<String, String> tableRow)
	{
		final String projectIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_Project.COLUMNNAME_C_Project_ID + "." + TABLECOLUMN_IDENTIFIER);

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

		final String bpartnerIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Project.COLUMNNAME_C_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);

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

		final String projectTypeIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Project.COLUMNNAME_C_ProjectType_ID + "." + TABLECOLUMN_IDENTIFIER);

		if (Check.isNotBlank(projectTypeIdentifier))
		{
			final Integer projectTypeId = projectTypeTable.getOptional(projectTypeIdentifier)
					.map(I_C_ProjectType::getC_ProjectType_ID)
					.orElseGet(() -> Integer.parseInt(projectTypeIdentifier));

			assertThat(projectRecord.getC_ProjectType_ID()).isEqualTo(projectTypeId);
		}

		final String rStatusIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Project.COLUMNNAME_R_Project_Status_ID + "." + TABLECOLUMN_IDENTIFIER);

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
		final String projectIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_Project.COLUMNNAME_C_Project_ID + "." + TABLECOLUMN_IDENTIFIER);

		final I_C_Project projectRecord = projectRepository.getRecordById(ProjectId.ofRepoId(jsonResponseProjectUpsertItem.getMetasfreshId().getValue()));

		projectTable.putOrReplace(projectIdentifier, projectRecord);
	}
}
