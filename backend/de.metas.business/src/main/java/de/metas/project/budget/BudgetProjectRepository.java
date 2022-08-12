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
import de.metas.project.ProjectTypeId;
import de.metas.project.workorder.data.ProjectQuery;
import de.metas.user.UserId;
import de.metas.util.InSetPredicate;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
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
	public static Optional<BudgetProject> fromRecord(final I_C_Project record)
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

		final List<I_C_Project> projectRecords = queryBuilder.create().list();
		if (projectRecords.isEmpty())
		{
			return Optional.empty();
		}

		return fromRecord(CollectionUtils.singleElement(projectRecords));
	}

	@NonNull
	public BudgetProject update(@NonNull final BudgetProject budgetProject)
	{
		final I_C_Project projectRecord = InterfaceWrapperHelper.load(budgetProject.getProjectId(), I_C_Project.class);

		if (projectRecord == null)
		{
			throw new AdempiereException("No C_Project record found for id: " + budgetProject.getProjectId().getRepoId());
		}

		projectRecord.setName(budgetProject.getName());
		projectRecord.setValue(budgetProject.getValue());
		projectRecord.setC_ProjectType_ID(ProjectTypeId.toRepoId(budgetProject.getProjectTypeId()));
		projectRecord.setIsActive(budgetProject.isActive());
		projectRecord.setC_Currency_ID(CurrencyId.toRepoId(budgetProject.getCurrencyId()));
		projectRecord.setAD_Org_ID(OrgId.toRepoId(budgetProject.getOrgId()));
		projectRecord.setDescription(budgetProject.getDescription());
		projectRecord.setC_Project_Parent_ID(ProjectId.toRepoId(budgetProject.getProjectParentId()));
		projectRecord.setC_BPartner_ID(BPartnerId.toRepoId(budgetProject.getBPartnerId()));
		projectRecord.setM_PriceList_Version_ID(PriceListVersionId.toRepoId(budgetProject.getPriceListVersionId()));
		projectRecord.setSalesRep_ID(UserId.toRepoId(budgetProject.getSalesRepId()));
		projectRecord.setDateContract(TimeUtil.asTimestamp(budgetProject.getDateContract()));
		projectRecord.setDateFinish(TimeUtil.asTimestamp(budgetProject.getDateFinish()));
		projectRecord.setC_Project_Reference_Ext(budgetProject.getProjectReferenceExt());

		saveRecord(projectRecord);

		return fromRecord(projectRecord)
				.orElseThrow(() -> new AdempiereException("BudgetProject has not been successfully saved!"));
	}

	@NonNull
	public BudgetProject create(@NonNull final CreateBudgetProjectRequest request)
	{
		final I_C_Project projectRecord = InterfaceWrapperHelper.newInstance(I_C_Project.class);

		projectRecord.setName(request.getName());
		projectRecord.setValue(request.getValue());
		projectRecord.setC_ProjectType_ID(ProjectTypeId.toRepoId(request.getProjectTypeId()));
		projectRecord.setIsActive(request.isActive());
		projectRecord.setC_Currency_ID(CurrencyId.toRepoId(request.getCurrencyId()));
		projectRecord.setAD_Org_ID(OrgId.toRepoId(request.getOrgId()));
		projectRecord.setDescription(request.getDescription());
		projectRecord.setC_Project_Parent_ID(ProjectId.toRepoId(request.getProjectParentId()));
		projectRecord.setC_BPartner_ID(BPartnerId.toRepoId(request.getBPartnerId()));
		projectRecord.setM_PriceList_Version_ID(PriceListVersionId.toRepoId(request.getPriceListVersionId()));
		projectRecord.setSalesRep_ID(UserId.toRepoId(request.getSalesRepId()));
		projectRecord.setDateContract(TimeUtil.asTimestamp(request.getDateContract()));
		projectRecord.setDateFinish(TimeUtil.asTimestamp(request.getDateFinish()));
		projectRecord.setC_Project_Reference_Ext(request.getProjectReferenceExt());

		saveRecord(projectRecord);

		return fromRecord(projectRecord)
				.orElseThrow(() -> new AdempiereException("BudgetProject has not been successfully saved!"));
	}
}
