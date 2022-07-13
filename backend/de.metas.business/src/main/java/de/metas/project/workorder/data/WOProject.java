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
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderProjectRequest;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListVersionId;
import de.metas.project.ProjectId;
import de.metas.project.ProjectTypeId;
import de.metas.user.UserId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Project;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.time.LocalDate;

@Value
@Builder
public class WOProject
{
	@Nullable
	@Getter(AccessLevel.NONE)
	ProjectId projectId;

	@NonNull
	OrgId orgId;

	@Nullable
	CurrencyId currencyId;

	@Nullable
	String name;

	@Nullable
	String value;

	@Nullable
	Boolean isActive;

	@Nullable
	PriceListVersionId priceListVersionId;

	@Nullable
	String description;

	@Nullable
	ProjectId projectParentId;

	@Nullable
	ProjectTypeId projectTypeId;

	@Nullable
	String projectReferenceExt;

	@Nullable
	BPartnerId bPartnerId;

	@Nullable
	UserId salesRepId;

	@Nullable
	LocalDate dateContract;

	@Nullable
	LocalDate dateFinish;

	@NonNull
	public ProjectId getProjectIdNonNull()
	{
		if (projectId == null)
		{
			throw new AdempiereException("WOProjectStepId cannot be null at this stage!");
		}
		return projectId;
	}

	@NonNull
	public String getNameNonNull()
	{
		if (name == null)
		{
			throw new AdempiereException("WOProject Name property cannot be null at this stage!");
		}
		return name;
	}

	@NonNull
	public String getValueNonNull()
	{
		if (value == null)
		{
			throw new AdempiereException("WOProject Value property cannot be null at this stage!");
		}
		return value;
	}

	@NonNull
	public static WOProject fromJson(@NonNull final JsonWorkOrderProjectRequest request, final OrgId orgId)
	{

		return WOProject.builder()
				.projectId(ProjectId.ofRepoIdOrNull(JsonMetasfreshId.toValue(request.getProjectId())))
				.name(request.getName())
				.projectReferenceExt(request.getProjectReferenceExt())
				.value(request.getValue())
				.projectTypeId(ProjectTypeId.ofRepoId(request.getProjectTypeId().getValue()))
				.projectParentId(ProjectId.ofRepoIdOrNull(JsonMetasfreshId.toValue(request.getProjectParentId())))
				.priceListVersionId(PriceListVersionId.ofRepoIdOrNull(JsonMetasfreshId.toValueInt(request.getPriceListVersionId())))
				.bPartnerId(BPartnerId.ofRepoIdOrNull(JsonMetasfreshId.toValueInt(request.getBusinessPartnerId())))
				.currencyId(CurrencyId.ofRepoIdOrNull(JsonMetasfreshId.toValueInt(request.getCurrencyId())))
				.orgId(orgId)
				.salesRepId(UserId.ofRepoId(request.getSalesRepId().getValue()))
				.description(request.getDescription())
				.dateContract(request.getDateContract())
				.dateFinish(request.getDateFinish())
				.isActive(request.getIsActive())
				.build();
	}

	@NonNull
	public static WOProject ofRecord(@NonNull final I_C_Project projectRecord)
	{
		final OrgId projectOrgId = OrgId.ofRepoId(projectRecord.getAD_Org_ID());

		return WOProject.builder()
				.projectId(ProjectId.ofRepoIdOrNull(projectRecord.getC_Project_ID()))
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
				.dateFinish(TimeUtil.asLocalDate(projectRecord.getDateFinish(), projectOrgId))
				.build();
	}
}
