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

package de.metas.project.service;

import de.metas.bpartner.BPartnerId;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListVersionId;
import de.metas.project.ProjectId;
import de.metas.project.ProjectType;
import de.metas.project.ProjectTypeId;
import de.metas.project.ProjectTypeRepository;
import de.metas.project.workorder.data.WOProject;
import de.metas.user.UserId;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Project;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class WorkOrderProjectRepository
{
	private final IDocumentNoBuilderFactory documentNoBuilderFactory;
	private final ProjectTypeRepository projectTypeRepository;

	public WorkOrderProjectRepository(
			@NonNull final IDocumentNoBuilderFactory documentNoBuilderFactory,
			@NonNull final ProjectTypeRepository projectTypeRepository)
	{
		this.documentNoBuilderFactory = documentNoBuilderFactory;
		this.projectTypeRepository = projectTypeRepository;
	}

	@NonNull
	public Optional<WOProject> getOptionalById(@NonNull final ProjectId id)
	{
		return Optional.ofNullable(getRecordById(id))
				.map(WOProject::ofRecord);
	}

	@Nullable
	public I_C_Project getRecordById(@NonNull final ProjectId id)
	{
		return InterfaceWrapperHelper.load(id, I_C_Project.class);
	}

	@NonNull
	public WOProject update(@NonNull final WOProject project)
	{
		final I_C_Project projectRecord = getRecordById(project.getProjectIdNonNull());

		if (projectRecord == null)
		{
			throw new AdempiereException("No C_Project record found for id.")
					.appendParametersToMessage()
					.setParameter("ProjectId", project.getProjectIdNonNull());
		}

		return saveProject(projectRecord, project);
	}

	@NonNull
	public WOProject create(@NonNull final WOProject projectData)
	{
		final I_C_Project record = InterfaceWrapperHelper.newInstance(I_C_Project.class);

		return saveProject(record, projectData);
	}

	@NonNull
	private WOProject saveProject(
			@NonNull final I_C_Project projectRecord,
			@NonNull final WOProject woProject)
	{
		if (Check.isNotBlank(woProject.getName()))
		{
			projectRecord.setName(woProject.getName());
		}
		if (woProject.getActive() != null)
		{
			projectRecord.setIsActive(woProject.getActive());
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

		return WOProject.ofRecord(projectRecord);
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
}
