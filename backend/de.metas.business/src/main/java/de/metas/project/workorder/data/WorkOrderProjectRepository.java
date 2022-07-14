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

package de.metas.project.workorder.data;

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
public class WorkOrderProjectRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final IDocumentNoBuilderFactory documentNoBuilderFactory;
	private final ProjectTypeRepository projectTypeRepository;
	private final WorkOrderProjectStepRepository workOrderProjectStepRepository;

	public WorkOrderProjectRepository(
			@NonNull final IDocumentNoBuilderFactory documentNoBuilderFactory,
			@NonNull final ProjectTypeRepository projectTypeRepository,
			@NonNull final WorkOrderProjectStepRepository workOrderProjectStepRepository)
	{
		this.documentNoBuilderFactory = documentNoBuilderFactory;
		this.projectTypeRepository = projectTypeRepository;
		this.workOrderProjectStepRepository = workOrderProjectStepRepository;
	}

	@NonNull
	public Optional<WOProject> getOptionalById(@NonNull final ProjectId id)
	{
		return Optional.ofNullable(getRecordById(id))
				.map(this::ofRecord);
	}

	@NonNull
	public Optional<WOProject> getOptionalBy(@NonNull final ProjectQuery query)
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
	public WOProject save(@NonNull final WOProject woProject)
	{
		final I_C_Project projectRecord = InterfaceWrapperHelper.loadOrNew(woProject.getProjectId(), I_C_Project.class);
		
		if (Check.isNotBlank(woProject.getName()))
		{
			projectRecord.setName(woProject.getName());
		}
		if (woProject.getIsActive() != null)
		{
			projectRecord.setIsActive(woProject.getIsActive());
		}
		else
		{
			projectRecord.setIsActive(true);
		}

		projectRecord.setAD_Org_ID(OrgId.toRepoId(woProject.getOrgId()));
		projectRecord.setDescription(woProject.getDescription());
		projectRecord.setC_Project_Parent_ID(ProjectId.toRepoId(woProject.getProjectParentId()));
		projectRecord.setC_BPartner_ID(BPartnerId.toRepoId(woProject.getBPartnerId()));
		projectRecord.setM_PriceList_Version_ID(PriceListVersionId.toRepoId(woProject.getPriceListVersionId()));
		projectRecord.setSalesRep_ID(UserId.toRepoId(woProject.getSalesRepId()));
		projectRecord.setDateContract(TimeUtil.asTimestamp(woProject.getDateContract()));
		projectRecord.setDateFinish(TimeUtil.asTimestamp(woProject.getDateFinish()));
		projectRecord.setC_Project_Reference_Ext(woProject.getProjectReferenceExt());

		updateFromProjectType(projectRecord, woProject);

		saveRecord(projectRecord);

		for (final WOProjectStep woProjectStep : woProject.getProjectSteps())
		{
			final WOProjectStep stepDataToSave = woProjectStep.withProjectId(ProjectId.ofRepoId(projectRecord.getC_Project_ID()));
			workOrderProjectStepRepository.save(stepDataToSave);
		}

		return ofRecord(projectRecord);
	}

	private void updateFromProjectType(@NonNull final I_C_Project projectRecord, @NonNull final WOProject projectData)
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
		if (projectType.getProjectCategory().isWorkOrder())
		{
			projectRecord.setProjectCategory(projectType.getProjectCategory().getCode());
		}
		else
		{
			throw new AdempiereException("The project " + projectRecord.getName() + " has the following category : " + projectType.getProjectCategory().getCode() + " which is not fitting for a Work Order Project!");
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
	private WOProject ofRecord(@NonNull final I_C_Project projectRecord)
	{
		final OrgId projectOrgId = OrgId.ofRepoId(projectRecord.getAD_Org_ID());

		final ProjectId projectId = ProjectId.ofRepoIdOrNull(projectRecord.getC_Project_ID());

		final WOProject.WOProjectBuilder woProjectBuilder = WOProject.builder()
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
				.dateContract(TimeUtil.asLocalDate(projectRecord.getDateContract(), projectOrgId))
				.dateFinish(TimeUtil.asLocalDate(projectRecord.getDateFinish(), projectOrgId));

		final List<WOProjectStep> steps = workOrderProjectStepRepository.getByProjectId(projectId);
		woProjectBuilder.projectSteps(steps);

		return woProjectBuilder.build();
	}
}
