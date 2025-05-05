/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2021 metas GmbH
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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListVersionId;
import de.metas.project.Project;
import de.metas.project.ProjectCategory;
import de.metas.project.ProjectData;
import de.metas.project.ProjectId;
import de.metas.project.ProjectTypeId;
import de.metas.project.status.RStatusId;
import de.metas.project.RequestStatusCategoryId;
import de.metas.user.UserId;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_Project;
import org.compiere.model.X_C_Project;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class ProjectRepository
{

	@NonNull
	public Optional<Project> getOptionalById(@NonNull final ProjectId id)
	{
		return Optional.ofNullable(getRecordById(id))
				.map(ProjectRepository::ofProjectRecord);
	}

	@Nullable
	public I_C_Project getRecordById(@NonNull final ProjectId id)
	{
		return InterfaceWrapperHelper.load(id, I_C_Project.class);
	}

	@NonNull
	public Project update(@NonNull final Project project)
	{
		final I_C_Project projectRecord = getRecordById(project.getId());

		if (projectRecord == null)
		{
			throw new AdempiereException("No C_Project record found for id: " + project.getId().getRepoId());
		}

		return save(projectRecord, project.getProjectData());
	}

	@NonNull
	public Project create(@NonNull final ProjectData projectData)
	{
		final I_C_Project record = InterfaceWrapperHelper.newInstance(I_C_Project.class);

		return save(record, projectData);
	}

	public ImmutableList<ProjectId> getProjectIdsUpStream(@NonNull final ProjectId projectId)
	{
		return DB.retrieveRows(
				"SELECT C_Project_ID FROM getC_Project_IDs_UpStream(?)",
				Collections.singletonList(projectId),
				rs -> ProjectId.ofRepoId(rs.getInt(1)));
	}

	public ImmutableSet<ProjectId> getProjectIdsUpStream(@NonNull final Collection<ProjectId> projectIds)
	{
		if (projectIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		final ArrayList<Object> sqlParams = new ArrayList<>();
		return DB.retrieveUniqueRows(
				"SELECT DISTINCT C_Project_ID FROM getC_Project_IDs_UpStream(p_C_Project_IDs:=" + DB.TO_ARRAY(projectIds, sqlParams) + ")",
				sqlParams,
				rs -> ProjectId.ofRepoId(rs.getInt(1)));
	}

	public ImmutableSet<ProjectId> getProjectIdsDownStream(@NonNull final Collection<ProjectId> projectIds)
	{
		if (projectIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		final ArrayList<Object> sqlParams = new ArrayList<>();
		return DB.retrieveUniqueRows(
				"SELECT DISTINCT C_Project_ID FROM getC_Project_IDs_DownStream(p_C_Project_IDs:=" + DB.TO_ARRAY(projectIds, sqlParams) + ")",
				sqlParams,
				rs -> ProjectId.ofRepoId(rs.getInt(1)));
	}

	@NonNull
	private Project save(
			@NonNull final I_C_Project projectRecord,
			@NonNull final ProjectData projectData)
	{
		setProjectDefaultValues(projectRecord);

		if (Check.isNotBlank(projectData.getName()))
		{
			projectRecord.setName(projectData.getName());
		}
		projectRecord.setValue(projectData.getValue());

		projectRecord.setAD_Org_ID(OrgId.toRepoId(projectData.getOrgId()));
		projectRecord.setDescription(projectData.getDescription());
		projectRecord.setC_Project_Parent_ID(ProjectId.toRepoId(projectData.getProjectParentId()));
		projectRecord.setR_Project_Status_ID(RStatusId.toRepoId(projectData.getProjectStatusId()));
		projectRecord.setC_BPartner_ID(BPartnerId.toRepoId(projectData.getBPartnerId()));
		projectRecord.setC_BPartner_Location_ID(BPartnerLocationId.toRepoId(projectData.getBPartnerLocationId()));
		projectRecord.setAD_User_ID(BPartnerContactId.toRepoId(projectData.getContactId()));
		projectRecord.setC_Currency_ID(CurrencyId.toRepoId(projectData.getCurrencyId()));
		projectRecord.setM_PriceList_Version_ID(PriceListVersionId.toRepoId(projectData.getPriceListVersionId()));
		projectRecord.setM_Warehouse_ID(WarehouseId.toRepoId(projectData.getWarehouseId()));
		projectRecord.setSalesRep_ID(UserId.toRepoId(projectData.getSalesRepId()));
		projectRecord.setDateContract(TimeUtil.asTimestamp(projectData.getDateContract()));
		projectRecord.setDateFinish(TimeUtil.asTimestamp(projectData.getDateFinish()));
		projectRecord.setIsActive(projectData.isActive());
		projectRecord.setProjectCategory(projectData.getProjectCategory() != null ? projectData.getProjectCategory().getCode() : null);
		projectRecord.setR_StatusCategory_ID(RequestStatusCategoryId.toRepoId(projectData.getRequestStatusCategoryId()));

		projectRecord.setC_ProjectType_ID(ProjectTypeId.toRepoId(projectData.getProjectTypeId()));

		saveRecord(projectRecord);

		return ofProjectRecord(projectRecord);
	}

	@NonNull
	private static Project ofProjectRecord(@NonNull final I_C_Project projectRecord)
	{
		return Project.builder()
				.id(ProjectId.ofRepoId(projectRecord.getC_Project_ID()))
				.projectData(buildProjectData(projectRecord))
				.build();
	}

	@NonNull
	private static ProjectData buildProjectData(@NonNull final I_C_Project projectRecord)
	{
		final OrgId projectOrgId = OrgId.ofRepoId(projectRecord.getAD_Org_ID());

		return ProjectData.builder()
				.orgId(projectOrgId)
				.name(projectRecord.getName())
				.currencyId(CurrencyId.ofRepoId(projectRecord.getC_Currency_ID()))
				.value(projectRecord.getValue())
				.active(projectRecord.isActive())
				.bPartnerLocationId(BPartnerLocationId.ofRepoIdOrNull(projectRecord.getC_BPartner_ID(), projectRecord.getC_BPartner_Location_ID()))
				.priceListVersionId(PriceListVersionId.ofRepoIdOrNull(projectRecord.getM_PriceList_Version_ID()))
				.warehouseId(WarehouseId.ofRepoIdOrNull(projectRecord.getM_Warehouse_ID()))
				.contactId(BPartnerContactId.ofRepoIdOrNull(projectRecord.getC_BPartner_ID(), projectRecord.getAD_User_ID()))
				.description(projectRecord.getDescription())
				.projectParentId(ProjectId.ofRepoIdOrNull(projectRecord.getC_Project_Parent_ID()))
				.projectTypeId(ProjectTypeId.ofRepoIdOrNull((projectRecord.getC_ProjectType_ID())))
				.projectCategory(ProjectCategory.ofNullableCode(projectRecord.getProjectCategory()))
				.requestStatusCategoryId(RequestStatusCategoryId.ofRepoId(projectRecord.getR_StatusCategory_ID()))
				.projectStatusId(RStatusId.ofRepoIdOrNull(projectRecord.getR_Project_Status_ID()))
				.bPartnerId(BPartnerId.ofRepoIdOrNull(projectRecord.getC_BPartner_ID()))
				.salesRepId(UserId.ofIntegerOrNull(projectRecord.getSalesRep_ID()))
				.dateContract(TimeUtil.asLocalDate(projectRecord.getDateContract(), projectOrgId))
				.dateFinish(TimeUtil.asLocalDate(projectRecord.getDateFinish(), projectOrgId))
				.build();
	}

	private static void setProjectDefaultValues(@NonNull final I_C_Project project)
	{
		project.setCommittedAmt(BigDecimal.ZERO);
		project.setCommittedQty(BigDecimal.ZERO);
		project.setInvoicedAmt(BigDecimal.ZERO);
		project.setInvoicedQty(BigDecimal.ZERO);
		project.setIsSummary(false);
		project.setPlannedAmt(BigDecimal.ZERO);
		project.setPlannedMarginAmt(BigDecimal.ZERO);
		project.setPlannedQty(BigDecimal.ZERO);
		project.setProcessed(false);
		project.setProjectBalanceAmt(BigDecimal.ZERO);
		project.setProjectLineLevel(X_C_Project.PROJECTLINELEVEL_Project);
		project.setProjInvoiceRule(X_C_Project.PROJINVOICERULE_ProductQuantity);
	}
}
