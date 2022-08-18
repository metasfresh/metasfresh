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

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.EmptyUtil;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListVersionId;
import de.metas.project.ProjectCategory;
import de.metas.project.ProjectId;
import de.metas.project.ProjectTypeId;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_Project;
import org.compiere.model.X_C_Project;
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

	@NonNull
	public WOProject getById(@NonNull final ProjectId id)
	{
		return Optional.ofNullable(getRecordById(id))
				.map(WorkOrderProjectRepository::ofRecord)
				.orElseThrow(() -> new AdempiereException("No WOProject found for C_Project_ID=" + id.getRepoId()));
	}

	@NonNull
	public Optional<WOProject> getOptionalBy(@NonNull final WOProjectQuery query)
	{
		final I_C_Project projectRecord = computeQuery(query).firstOnly(I_C_Project.class);
		if (projectRecord == null)
		{
			return Optional.empty();
		}
		return Optional.of(ofRecord(projectRecord));
	}

	@NonNull
	public WOProject update(@NonNull final WOProject woProject)
	{
		final I_C_Project projectRecord = InterfaceWrapperHelper.load(woProject.getProjectId(), I_C_Project.class);

		if (projectRecord == null)
		{
			throw new AdempiereException("No C_Project record found for id: " + woProject.getProjectId().getRepoId());
		}

		projectRecord.setAD_Org_ID(OrgId.toRepoId(woProject.getOrgId()));
		projectRecord.setIsActive(woProject.getIsActive());
		projectRecord.setName(woProject.getName());
		projectRecord.setValue(woProject.getValue());
		projectRecord.setM_PriceList_Version_ID(PriceListVersionId.toRepoId(woProject.getPriceListVersionId()));
		projectRecord.setSalesRep_ID(UserId.toRepoId(woProject.getSalesRepId()));
		projectRecord.setDateContract(TimeUtil.asTimestamp(woProject.getDateContract()));
		projectRecord.setC_BPartner_ID(BPartnerId.toRepoId(woProject.getBPartnerId()));
		projectRecord.setDateFinish(TimeUtil.asTimestamp(woProject.getDateFinish()));
		projectRecord.setDateOfProvisionByBPartner(TimeUtil.asTimestamp(woProject.getDateOfProvisionByBPartner()));
		projectRecord.setC_Project_Reference_Ext(woProject.getProjectReferenceExt());
		projectRecord.setDescription(woProject.getDescription());
		projectRecord.setC_Currency_ID(CurrencyId.toRepoId(woProject.getCurrencyId()));
		projectRecord.setC_Project_Parent_ID(ProjectId.toRepoId(woProject.getProjectParentId()));
		projectRecord.setBPartnerDepartment(woProject.getBpartnerDepartment());
		projectRecord.setWOOwner(woProject.getWoOwner());
		projectRecord.setPOReference(woProject.getPoReference());
		projectRecord.setBPartnerTargetDate(TimeUtil.asTimestamp(woProject.getBpartnerTargetDate()));
		projectRecord.setWOProjectCreatedDate(TimeUtil.asTimestamp(woProject.getWoProjectCreatedDate()));
		projectRecord.setC_ProjectType_ID(woProject.getProjectTypeId().getRepoId());

		saveRecord(projectRecord);

		return ofRecord(projectRecord);
	}

	@NonNull
	public WOProject create(@NonNull final CreateWOProjectRequest createWOProjectRequest)
	{
		final I_C_Project projectRecord = InterfaceWrapperHelper.newInstance(I_C_Project.class);

		projectRecord.setAD_Org_ID(OrgId.toRepoId(createWOProjectRequest.getOrgId()));
		projectRecord.setC_Project_Reference_Ext(createWOProjectRequest.getProjectReferenceExt());

		projectRecord.setName(createWOProjectRequest.getName());
		projectRecord.setValue(createWOProjectRequest.getValue());
		projectRecord.setPOReference(createWOProjectRequest.getPoReference());
		projectRecord.setDescription(createWOProjectRequest.getDescription());

		projectRecord.setProjectCategory(X_C_Project.PROJECTCATEGORY_WorkOrderJob);
		projectRecord.setC_ProjectType_ID(createWOProjectRequest.getProjectTypeId().getRepoId());
		projectRecord.setC_Currency_ID(createWOProjectRequest.getCurrencyId().getRepoId());
		projectRecord.setC_Project_Parent_ID(ProjectId.toRepoId(createWOProjectRequest.getProjectParentId()));
		projectRecord.setM_PriceList_Version_ID(PriceListVersionId.toRepoId(createWOProjectRequest.getPriceListVersionId()));
		projectRecord.setSalesRep_ID(UserId.toRepoId(createWOProjectRequest.getSalesRepId()));
		projectRecord.setC_BPartner_ID(BPartnerId.toRepoId(createWOProjectRequest.getBPartnerId()));

		projectRecord.setWOProjectCreatedDate(TimeUtil.asTimestamp(createWOProjectRequest.getWoProjectCreatedDate()));
		projectRecord.setDateContract(TimeUtil.asTimestamp(createWOProjectRequest.getDateContract()));
		projectRecord.setDateFinish(TimeUtil.asTimestamp(createWOProjectRequest.getDateFinish()));
		projectRecord.setDateOfProvisionByBPartner(TimeUtil.asTimestamp(createWOProjectRequest.getDateOfProvisionByBPartner()));
		projectRecord.setBPartnerTargetDate(TimeUtil.asTimestamp(createWOProjectRequest.getBpartnerTargetDate()));

		projectRecord.setBPartnerDepartment(createWOProjectRequest.getBpartnerDepartment());
		projectRecord.setWOOwner(createWOProjectRequest.getWoOwner());

		projectRecord.setIsActive(createWOProjectRequest.isActive());

		saveRecord(projectRecord);

		return ofRecord(projectRecord);
	}

	@NonNull
	public List<WOProject> listBy(@NonNull final WOProjectQuery query)
	{
		return computeQuery(query)
				.stream()
				.map(WorkOrderProjectRepository::ofRecord)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private static WOProject ofRecord(@NonNull final I_C_Project projectRecord)
	{
		final ProjectCategory projectCategory = ProjectCategory.ofNullableCodeOrGeneral(projectRecord.getProjectCategory());
		if (!projectCategory.isWorkOrder())
		{
			throw new AdempiereException("Project " + projectRecord.getC_Project_ID() + " is not a work order project!");
		}

		final OrgId orgId = OrgId.ofRepoId(projectRecord.getAD_Org_ID());

		final ProjectId projectId = ProjectId.ofRepoId(projectRecord.getC_Project_ID());

		return WOProject.builder()
				.projectId(projectId)
				.orgId(orgId)
				.projectReferenceExt(projectRecord.getC_Project_Reference_Ext())

				.name(projectRecord.getName())
				.value(projectRecord.getValue())
				.poReference(projectRecord.getPOReference())
				.description(projectRecord.getDescription())

				.currencyId(CurrencyId.ofRepoId(projectRecord.getC_Currency_ID()))
				.projectTypeId(ProjectTypeId.ofRepoId(projectRecord.getC_ProjectType_ID()))
				.bPartnerId(BPartnerId.ofRepoIdOrNull(projectRecord.getC_BPartner_ID()))
				.projectParentId(ProjectId.ofRepoIdOrNull(projectRecord.getC_Project_Parent_ID()))
				.priceListVersionId(PriceListVersionId.ofRepoIdOrNull(projectRecord.getM_PriceList_Version_ID()))
				.salesRepId(UserId.ofRepoIdOrNull(projectRecord.getSalesRep_ID()))

				.dateContract(TimeUtil.asInstant(projectRecord.getDateContract()))
				.dateFinish(TimeUtil.asInstant(projectRecord.getDateFinish()))
				.dateOfProvisionByBPartner(TimeUtil.asInstant(projectRecord.getDateOfProvisionByBPartner()))
				.bpartnerTargetDate(TimeUtil.asInstant(projectRecord.getBPartnerTargetDate()))
				.woProjectCreatedDate(TimeUtil.asInstant(projectRecord.getWOProjectCreatedDate()))

				.bpartnerDepartment(projectRecord.getBPartnerDepartment())
				.woOwner(projectRecord.getWOOwner())

				.isActive(projectRecord.isActive())
				.build();
	}

	@Nullable
	private I_C_Project getRecordById(@NonNull final ProjectId id)
	{
		return InterfaceWrapperHelper.load(id, I_C_Project.class);
	}

	@NonNull
	private IQuery<I_C_Project> computeQuery(@NonNull final WOProjectQuery query)
	{
		final IQueryBuilder<I_C_Project> queryBuilder = queryBL.createQueryBuilder(I_C_Project.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Project.COLUMNNAME_ProjectCategory, ProjectCategory.WorkOrderJob.getCode())
				.addInArrayFilter(I_C_Project.COLUMNNAME_AD_Org_ID, query.getOrgId(), OrgId.ANY)
				.orderByDescending(I_C_Project.COLUMNNAME_AD_Org_ID);

		if (EmptyUtil.isNotBlank(query.getExternalProjectReferencePattern()))
		{
			queryBuilder.addStringLikeFilter(I_C_Project.COLUMNNAME_C_Project_Reference_Ext, "%" + query.getExternalProjectReferencePattern() + "%", true);
		}

		if (query.getExternalProjectReference() != null)
		{
			queryBuilder.addEqualsFilter(I_C_Project.COLUMNNAME_C_Project_Reference_Ext, query.getExternalProjectReference().getValue());
		}

		if (EmptyUtil.isNotBlank(query.getValue()))
		{
			queryBuilder.addEqualsFilter(I_C_Project.COLUMNNAME_Value, query.getValue().trim());
		}

		return queryBuilder.create();
	}
}
