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

package de.metas.project.budget.data;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.EmptyUtil;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListVersionId;
import de.metas.project.ProjectId;
import de.metas.project.ProjectType;
import de.metas.project.ProjectTypeId;
import de.metas.project.ProjectTypeRepository;
import de.metas.project.budget.BudgetProjectResourceId;
import de.metas.project.workorder.data.ProjectQuery;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Project;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class BudgetProjectRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final IDocumentNoBuilderFactory documentNoBuilderFactory;
	private final ProjectTypeRepository projectTypeRepository;
	private final BudgetProjectResourceRepository budgetProjectResourceRepository;

	public BudgetProjectRepository(
			@NonNull final IDocumentNoBuilderFactory documentNoBuilderFactory,
			@NonNull final ProjectTypeRepository projectTypeRepository,
			@NonNull final BudgetProjectResourceRepository budgetProjectResourceRepository)
	{
		this.documentNoBuilderFactory = documentNoBuilderFactory;
		this.projectTypeRepository = projectTypeRepository;
		this.budgetProjectResourceRepository = budgetProjectResourceRepository;
	}

	@NonNull
	public Optional<BudgetProject> getOptionalById(@NonNull final ProjectId id)
	{
		return Optional.ofNullable(getRecordById(id))
				.map(this::ofRecord);
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
		return Optional.of(ofRecord(projectRecord));
	}

	@Nullable
	private I_C_Project getRecordById(@NonNull final ProjectId id)
	{
		return InterfaceWrapperHelper.load(id, I_C_Project.class);
	}

	@NonNull
	public BudgetProject save(@NonNull final BudgetProject budgetProject,
			@NonNull final ImmutableList.Builder<BudgetProjectResourceId> resourceResponseListBuilder
	)
	{
		final I_C_Project projectRecord = InterfaceWrapperHelper.loadOrNew(budgetProject.getProjectId(), I_C_Project.class);

		if (Check.isNotBlank(budgetProject.getName()))
		{
			projectRecord.setName(budgetProject.getName());
		}
		if (budgetProject.getIsActive() != null)
		{
			projectRecord.setIsActive(budgetProject.getIsActive());
		}
		else
		{
			projectRecord.setIsActive(true);
		}
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

		updateFromProjectType(projectRecord, budgetProject);

		saveRecord(projectRecord);

		for (final BudgetProjectResource budgetProjectResource : budgetProject.getProjectResources())
		{
			final BudgetProjectResource resourceToSave = budgetProjectResource.withProjectId(ProjectId.ofRepoId(projectRecord.getC_Project_ID()));
			final BudgetProjectResource savedResource = budgetProjectResourceRepository.save(resourceToSave);

			resourceResponseListBuilder.add(savedResource.getBudgetProjectResourceIdNonNull());
		}

		return ofRecord(projectRecord);
	}

	private void updateFromProjectType(@NonNull final I_C_Project projectRecord, @NonNull final BudgetProject projectData)
	{
		final ProjectTypeId projectTypeId = projectData.getProjectTypeId();
		if (projectTypeId == null)
		{
			return;
		}

		projectRecord.setC_ProjectType_ID(projectTypeId.getRepoId());

		final String projectValue = computeNextProjectValue(projectRecord);
		if (projectValue != null)
		{
			projectRecord.setValue(projectValue);
		}
		if (Check.isEmpty(projectRecord.getName()))
		{
			projectRecord.setName(projectValue != null ? projectValue : ".");
		}

		final ProjectType projectType = projectTypeRepository.getById(projectTypeId);
		if (projectType.getProjectCategory().isBudget())
		{
			projectRecord.setProjectCategory(projectType.getProjectCategory().getCode());
		}
		else
		{
			throw new AdempiereException("The project " + projectRecord.getName() + " has the following category : " + projectType.getProjectCategory().getCode() + " which is not fitting for a Budget Project!");
		}
	}

	@Nullable
	private String computeNextProjectValue(final I_C_Project projectRecord)
	{
		return documentNoBuilderFactory
				.createValueBuilderFor(projectRecord)
				.setFailOnError(false)
				.build();
	}

	@NonNull
	private BudgetProject ofRecord(@NonNull final I_C_Project projectRecord)
	{
		final OrgId projectOrgId = OrgId.ofRepoId(projectRecord.getAD_Org_ID());

		final ProjectId projectId = ProjectId.ofRepoIdOrNull(projectRecord.getC_Project_ID());

		final BudgetProject.BudgetProjectBuilder budgetProjectBuilder = BudgetProject.builder()
				.projectId(projectId)
				.orgId(projectOrgId)
				.name(projectRecord.getName())
				.value(projectRecord.getValue())
				.bPartnerId(BPartnerId.ofRepoIdOrNull(projectRecord.getC_BPartner_ID()))
				.currencyId(CurrencyId.ofRepoIdOrNull(projectRecord.getC_Currency_ID()))
				.projectParentId(ProjectId.ofRepoIdOrNull(projectRecord.getC_Project_Parent_ID()))
				.isActive(projectRecord.isActive())
				.description(projectRecord.getDescription())
				.projectTypeId(ProjectTypeId.ofRepoIdOrNull(projectRecord.getC_ProjectType_ID()))
				.priceListVersionId(PriceListVersionId.ofRepoIdOrNull(projectRecord.getM_PriceList_Version_ID()))
				.salesRepId(UserId.ofRepoIdOrNull(projectRecord.getSalesRep_ID()))
				.projectReferenceExt(projectRecord.getC_Project_Reference_Ext())
				.dateContract(TimeUtil.asInstant(projectRecord.getDateContract(), projectOrgId))
				.dateFinish(TimeUtil.asInstant(projectRecord.getDateFinish(), projectOrgId));

		final List<BudgetProjectResource> resources = budgetProjectResourceRepository.getByProjectId(projectId);
		budgetProjectBuilder.projectResources(resources);

		return budgetProjectBuilder.build();
	}
}
