/*
 * #%L
 * de.metas.business
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

package de.metas.project.budget;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.EmptyUtil;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListVersionId;
import de.metas.project.ProjectCategory;
import de.metas.project.ProjectId;
import de.metas.util.InSetPredicate;
import de.metas.project.ProjectTypeId;
import de.metas.project.workorder.data.ProjectQuery;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Project;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class BudgetProjectRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final BudgetProjectResourceRepository budgetProjectResourceRepository;

	public BudgetProjectRepository(@NonNull final BudgetProjectResourceRepository budgetProjectResourceRepository)
	{
		this.budgetProjectResourceRepository = budgetProjectResourceRepository;
	}

	public Optional<BudgetProject> getOptionalById(@NonNull final ProjectId projectId)
	{
		final I_C_Project record = InterfaceWrapperHelper.load(projectId, I_C_Project.class);
		return fromRecord(record);
	}

	public List<BudgetProject> queryAllActiveProjects(@NonNull final InSetPredicate<ProjectId> projectIds)
	{
		if (projectIds.isNone())
		{
			return ImmutableList.of();
		}

		return queryBL
				.createQueryBuilder(I_C_Project.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Project.COLUMNNAME_ProjectCategory, ProjectCategory.Budget)
				.addInArrayFilter(I_C_Project.COLUMNNAME_C_Project_ID, projectIds)
				.orderBy(I_C_Project.COLUMNNAME_C_Project_ID)
				.stream()
				.map(record -> fromRecord(record).orElse(null))
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public Optional<BudgetProject> fromRecord(final I_C_Project record)
	{
		final ProjectCategory projectCategory = ProjectCategory.ofNullableCodeOrGeneral(record.getProjectCategory());
		if (!projectCategory.isBudget())
		{
			//throw new AdempiereException("Project " + record + " is not budget project");
			return Optional.empty();
		}

		final ProjectTypeId projectTypeId = ProjectTypeId.ofRepoIdOrNull(record.getC_ProjectType_ID());
		if (projectTypeId == null)
		{
			return Optional.empty();
		}

		final OrgId projectOrgId = OrgId.ofRepoId(record.getAD_Org_ID());
		final ProjectId projectId = ProjectId.ofRepoId(record.getC_Project_ID());

		return Optional.of(
				BudgetProject.builder()
						.projectId(projectId)
						.budgetProjectData(BudgetProjectData.builder()
												   .name(record.getName())
												   .orgId(projectOrgId)
												   .currencyId(CurrencyId.ofRepoId(record.getC_Currency_ID()))
												   .value(record.getValue())
												   .isActive(record.isActive())
												   .priceListVersionId(PriceListVersionId.ofRepoIdOrNull(record.getM_PriceList_Version_ID()))
												   .description(record.getDescription())
												   .projectParentId(ProjectId.ofRepoIdOrNull(record.getC_Project_Parent_ID()))
												   .projectTypeId(projectTypeId)
												   .projectReferenceExt(record.getC_Project_Reference_Ext())
												   .bPartnerId(BPartnerId.ofRepoIdOrNull(record.getC_BPartner_ID()))
												   .salesRepId(UserId.ofIntegerOrNull(record.getSalesRep_ID()))
												   .dateContract(TimeUtil.asLocalDate(record.getDateContract(), projectOrgId))
												   .dateFinish(TimeUtil.asLocalDate(record.getDateFinish(), projectOrgId))
												   .build())
						.projectResources(BudgetProjectResources.builder()
												  .projectId(projectId)
												  .budgets(budgetProjectResourceRepository.getResourcesAsListByProjectId(projectId))
												  .build())
						.build());
	}

	@NonNull
	public Optional<BudgetProject> getOptionalBy(@NonNull final ProjectQuery query)
	{
		final IQueryBuilder<I_C_Project> queryBuilder = queryBL.createQueryBuilder(I_C_Project.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_Project.COLUMNNAME_AD_Org_ID, query.getOrgId(), OrgId.ANY)
				.orderByDescending(I_C_Project.COLUMNNAME_AD_Org_ID);

		if (EmptyUtil.isNotBlank(query.getValue()))
		{
			queryBuilder.addEqualsFilter(I_C_Project.COLUMNNAME_Value, query.getValue().trim());
		}
		if (query.getExternalProjectReference() != null)
		{
			queryBuilder.addEqualsFilter(I_C_Project.COLUMNNAME_C_Project_Reference_Ext, query.getExternalProjectReference().getValue());
		}

		final I_C_Project projectRecord = queryBuilder.create().first();
		if (projectRecord == null)
		{
			return Optional.empty();
		}

		return fromRecord(projectRecord);
	}

	@NonNull
	public BudgetProject save(@NonNull final UpsertBudgetProjectRequest upsertBudgetProjectRequest)
	{
		final I_C_Project projectRecord = InterfaceWrapperHelper.loadOrNew(upsertBudgetProjectRequest.getProjectId(), I_C_Project.class);

		final BudgetProjectData budgetProjectData = upsertBudgetProjectRequest.getBudgetProjectData();

		projectRecord.setName(budgetProjectData.getName());
		projectRecord.setValue(budgetProjectData.getValue());
		projectRecord.setC_ProjectType_ID(ProjectTypeId.toRepoId(budgetProjectData.getProjectTypeId()));
		projectRecord.setIsActive(budgetProjectData.isActive());
		projectRecord.setC_Currency_ID(CurrencyId.toRepoId(budgetProjectData.getCurrencyId()));
		projectRecord.setAD_Org_ID(OrgId.toRepoId(budgetProjectData.getOrgId()));
		projectRecord.setDescription(budgetProjectData.getDescription());
		projectRecord.setC_Project_Parent_ID(ProjectId.toRepoId(budgetProjectData.getProjectParentId()));
		projectRecord.setC_BPartner_ID(BPartnerId.toRepoId(budgetProjectData.getBPartnerId()));
		projectRecord.setM_PriceList_Version_ID(PriceListVersionId.toRepoId(budgetProjectData.getPriceListVersionId()));
		projectRecord.setSalesRep_ID(UserId.toRepoId(budgetProjectData.getSalesRepId()));
		projectRecord.setDateContract(TimeUtil.asTimestamp(budgetProjectData.getDateContract()));
		projectRecord.setDateFinish(TimeUtil.asTimestamp(budgetProjectData.getDateFinish()));
		projectRecord.setC_Project_Reference_Ext(budgetProjectData.getProjectReferenceExt());

		saveRecord(projectRecord);

		final ProjectId projectId = ProjectId.ofRepoId(projectRecord.getC_Project_ID());

		upsertBudgetProjectRequest.getProjectResourceRequests()
				.forEach(upsertBudgetProjectResourceRequest -> budgetProjectResourceRepository.save(upsertBudgetProjectResourceRequest, projectId));

		return fromRecord(projectRecord)
				.orElseThrow(() -> new AdempiereException("BudgetProject has not been successfully saved!"));
	}
}
