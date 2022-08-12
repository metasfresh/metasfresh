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
import de.metas.money.CurrencyId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListVersionId;
import de.metas.project.ProjectId;
import de.metas.project.ProjectTypeId;
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

import javax.annotation.Nullable;
import java.time.ZoneId;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class WorkOrderProjectRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

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
		projectRecord.setSpecialist_Consultant_ID(woProject.getSpecialistConsultantId());
		projectRecord.setC_ProjectType_ID(woProject.getProjectTypeId().getRepoId());

		saveRecord(projectRecord);

		return ofRecord(projectRecord);
	}

	@NonNull
	public WOProject save(@NonNull final CreateWOProjectRequest createWOProjectRequest)
	{
		final I_C_Project projectRecord = InterfaceWrapperHelper.newInstance(I_C_Project.class);

		projectRecord.setAD_Org_ID(OrgId.toRepoId(createWOProjectRequest.getOrgId()));
		projectRecord.setIsActive(createWOProjectRequest.isActive());
		projectRecord.setName(createWOProjectRequest.getName());
		projectRecord.setValue(createWOProjectRequest.getValue());
		projectRecord.setM_PriceList_Version_ID(PriceListVersionId.toRepoId(createWOProjectRequest.getPriceListVersionId()));
		projectRecord.setSalesRep_ID(UserId.toRepoId(createWOProjectRequest.getSalesRepId()));
		projectRecord.setDateContract(TimeUtil.asTimestamp(createWOProjectRequest.getDateContract()));
		projectRecord.setC_BPartner_ID(BPartnerId.toRepoId(createWOProjectRequest.getBPartnerId()));
		projectRecord.setDateFinish(TimeUtil.asTimestamp(createWOProjectRequest.getDateFinish()));
		projectRecord.setDateOfProvisionByBPartner(TimeUtil.asTimestamp(createWOProjectRequest.getDateOfProvisionByBPartner()));
		projectRecord.setC_Project_Reference_Ext(createWOProjectRequest.getProjectReferenceExt());
		projectRecord.setDescription(createWOProjectRequest.getDescription());
		projectRecord.setC_Currency_ID(CurrencyId.toRepoId(createWOProjectRequest.getCurrencyId()));
		projectRecord.setC_Project_Parent_ID(ProjectId.toRepoId(createWOProjectRequest.getProjectParentId()));
		projectRecord.setBPartnerDepartment(createWOProjectRequest.getBpartnerDepartment());
		projectRecord.setWOOwner(createWOProjectRequest.getWoOwner());
		projectRecord.setPOReference(createWOProjectRequest.getPoReference());
		projectRecord.setBPartnerTargetDate(TimeUtil.asTimestamp(createWOProjectRequest.getBpartnerTargetDate()));
		projectRecord.setWOProjectCreatedDate(TimeUtil.asTimestamp(createWOProjectRequest.getWoProjectCreatedDate()));
		projectRecord.setSpecialist_Consultant_ID(createWOProjectRequest.getSpecialistConsultantId());
		projectRecord.setC_ProjectType_ID(createWOProjectRequest.getProjectTypeId().getRepoId());
		projectRecord.setName(createWOProjectRequest.getName());
		projectRecord.setValue(createWOProjectRequest.getValue());

		saveRecord(projectRecord);

		return ofRecord(projectRecord);
	}

	@NonNull
	private WOProject ofRecord(@NonNull final I_C_Project projectRecord)
	{
		final OrgId orgId = OrgId.ofRepoId(projectRecord.getAD_Org_ID());

		final ZoneId timeZone = orgDAO.getTimeZone(orgId);

		final ProjectId projectId = ProjectId.ofRepoId(projectRecord.getC_Project_ID());

		return WOProject.builder()
				.projectId(projectId)
				.orgId(orgId)
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
				.dateContract(TimeUtil.asInstant(projectRecord.getDateContract(), timeZone))
				.dateFinish(TimeUtil.asInstant(projectRecord.getDateFinish(), timeZone))
				.specialistConsultantId(projectRecord.getSpecialist_Consultant_ID())
				.bpartnerDepartment(projectRecord.getBPartnerDepartment())
				.dateOfProvisionByBPartner(TimeUtil.asInstant(projectRecord.getDateOfProvisionByBPartner(), timeZone))
				.woOwner(projectRecord.getWOOwner())
				.poReference(projectRecord.getPOReference())
				.bpartnerTargetDate(TimeUtil.asInstant(projectRecord.getBPartnerTargetDate(), timeZone))
				.woProjectCreatedDate(TimeUtil.asInstant(projectRecord.getWOProjectCreatedDate(), timeZone))
				.build();
	}
}
