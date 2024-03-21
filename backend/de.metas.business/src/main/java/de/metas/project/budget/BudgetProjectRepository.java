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
import de.metas.project.InternalPriority;
import de.metas.project.ProjectCategory;
import de.metas.project.ProjectId;
import de.metas.project.ProjectTypeId;
import de.metas.user.UserId;
import de.metas.util.InSetPredicate;
import de.metas.util.Services;
import de.metas.util.lang.ExternalId;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_Project;
import org.compiere.model.X_C_Project;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@Repository
public class BudgetProjectRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public Optional<BudgetProject> getOptionalById(@NonNull final ProjectId projectId)
	{
		return Optional.ofNullable(fromRecord(getRecordByIdNotNull(projectId)));
	}

	@NonNull
	public List<BudgetProject> queryAllActiveProjects(@NonNull final InSetPredicate<ProjectId> projectIds)
	{
		if (projectIds.isNone())
		{
			return ImmutableList.of();
		}

		return queryBL
				.createQueryBuilder(I_C_Project.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Project.COLUMNNAME_AD_Client_ID, ClientId.METASFRESH)
				.addEqualsFilter(I_C_Project.COLUMNNAME_ProjectCategory, ProjectCategory.Budget)
				.addInArrayFilter(I_C_Project.COLUMNNAME_C_Project_ID, projectIds)
				.orderBy(I_C_Project.COLUMNNAME_C_Project_ID)
				.stream()
				.map(record -> fromRecord(record))
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());
	}

	@Nullable
	public static BudgetProject fromRecord(@NonNull final I_C_Project record)
	{
		final ProjectCategory projectCategory = ProjectCategory.ofNullableCodeOrGeneral(record.getProjectCategory());
		if (!projectCategory.isBudget())
		{
			//throw new AdempiereException("Project " + record + " is not budget project");
			return null;
		}

		final ProjectTypeId projectTypeId = ProjectTypeId.ofRepoIdOrNull(record.getC_ProjectType_ID());
		if (projectTypeId == null)
		{
			return null;
		}

		final OrgId projectOrgId = OrgId.ofRepoId(record.getAD_Org_ID());
		final ProjectId projectId = ProjectId.ofRepoId(record.getC_Project_ID());

		return BudgetProject.builder()
				.projectId(projectId)
				.name(record.getName())
				.orgId(projectOrgId)
				.currencyId(CurrencyId.ofRepoId(record.getC_Currency_ID()))
				.value(record.getValue())
				.externalId(ExternalId.ofOrNull(record.getExternalId()))
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
				.bpartnerDepartment(record.getBPartnerDepartment())
				.specialistConsultantID(UserId.ofRepoIdOrNullIfSystem(record.getSpecialist_Consultant_ID()))
				.internalPriority(InternalPriority.ofNullableCode(record.getInternalPriority()))
				.build();
	}

	@NonNull
	public ImmutableList<BudgetProject> getBy(@NonNull final BudgetProjectQuery query)
	{
		final IQueryBuilder<I_C_Project> queryBuilder = queryBL.createQueryBuilder(I_C_Project.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Project.COLUMNNAME_AD_Client_ID, ClientId.METASFRESH)
				.addEqualsFilter(I_C_Project.COLUMNNAME_ProjectCategory, ProjectCategory.Budget.getCode())
				.addInArrayFilter(I_C_Project.COLUMNNAME_AD_Org_ID, query.getOrgId(), OrgId.ANY)
				.orderByDescending(I_C_Project.COLUMNNAME_AD_Org_ID);

		if (EmptyUtil.isNotBlank(query.getValue()))
		{
			queryBuilder.addEqualsFilter(I_C_Project.COLUMNNAME_Value, query.getValue().trim());
		}
		if (EmptyUtil.isNotBlank(query.getExternalProjectReference()))
		{
			queryBuilder.addEqualsFilter(I_C_Project.COLUMNNAME_C_Project_Reference_Ext, query.getExternalProjectReference());
		}
		if (query.getExternalId() != null)
		{
			queryBuilder.addEqualsFilter(I_C_Project.COLUMNNAME_ExternalId, ExternalId.toValue(query.getExternalId()));
		}
		if (query.getExternalIdPattern() != null)
		{
			queryBuilder.addStringLikeFilter(I_C_Project.COLUMNNAME_ExternalId, "%" + query.getExternalIdPattern() + "%", true);
		}

		return queryBuilder
				.create()
				.list()
				.stream()
				.map(BudgetProjectRepository::fromRecord)
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public BudgetProject update(@NonNull final BudgetProject budgetProject)
	{
		final I_C_Project projectRecord = getRecordByIdNotNull(budgetProject.getProjectId());

		projectRecord.setName(budgetProject.getName());
		projectRecord.setValue(budgetProject.getValue());
		projectRecord.setExternalId(ExternalId.toValue(budgetProject.getExternalId()));
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
		projectRecord.setBPartnerDepartment(budgetProject.getBpartnerDepartment());
		projectRecord.setSpecialist_Consultant_ID(UserId.toRepoId(budgetProject.getSpecialistConsultantID()));
		projectRecord.setInternalPriority(InternalPriority.toCode(budgetProject.getInternalPriority()));

		InterfaceWrapperHelper.saveRecord(projectRecord);

		return Optional.ofNullable(fromRecord(projectRecord))
				.orElseThrow(() -> new AdempiereException("BudgetProject has not been successfully saved!"));
	}

	@NonNull
	public BudgetProject create(@NonNull final CreateBudgetProjectRequest request)
	{
		final I_C_Project projectRecord = InterfaceWrapperHelper.newInstance(I_C_Project.class);

		projectRecord.setName(request.getName());
		projectRecord.setValue(request.getValue());
		projectRecord.setExternalId(ExternalId.toValue(request.getExternalId()));
		projectRecord.setProjectCategory(X_C_Project.PROJECTCATEGORY_Budget);
		projectRecord.setC_ProjectType_ID(request.getProjectType().getId().getRepoId());
		projectRecord.setR_StatusCategory_ID(request.getProjectType().getRequestStatusCategoryId().getRepoId());
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

		InterfaceWrapperHelper.saveRecord(projectRecord);

		return Optional.ofNullable(fromRecord(projectRecord))
				.orElseThrow(() -> new AdempiereException("BudgetProject has not been successfully saved!"));
	}

	public void applyAndSave(@NonNull final ProjectId projectId, @NonNull final Consumer<I_C_Project> updateProject)
	{
		final I_C_Project projectRecord = getRecordByIdNotNull(projectId);

		updateProject.accept(projectRecord);

		InterfaceWrapperHelper.save(projectRecord);
	}

	@NonNull
	public <T> T mapProject(@NonNull final ProjectId projectId, @NonNull final Function<I_C_Project, T> mapProject)
	{
		final I_C_Project projectRecord = getRecordByIdNotNull(projectId);

		return mapProject.apply(projectRecord);
	}

	@NonNull
	public Iterator<I_C_Project> iterateAllActive()
	{
		return queryBL.createQueryBuilder(I_C_Project.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Project.COLUMNNAME_ProjectCategory, ProjectCategory.Budget.getCode())
				.create()
				.iterate(I_C_Project.class);
	}

	@NonNull
	private I_C_Project getRecordByIdNotNull(final @NonNull ProjectId projectId)
	{
		final I_C_Project projectRecord = InterfaceWrapperHelper.load(projectId, I_C_Project.class);

		if (projectRecord == null)
		{
			throw new AdempiereException("No C_Project record found for id: " + projectId);
		}

		return projectRecord;
	}
}
