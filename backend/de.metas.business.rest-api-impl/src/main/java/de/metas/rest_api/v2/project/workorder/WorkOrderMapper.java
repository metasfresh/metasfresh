/*
 * #%L
 * de.metas.business.rest-api-impl
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

package de.metas.rest_api.v2.project.workorder;

import de.metas.bpartner.BPartnerId;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderProjectUpsertRequest;
import de.metas.currency.Currency;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyBL;
import de.metas.money.CurrencyId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.project.ProjectId;
import de.metas.project.ProjectTypeId;
import de.metas.project.service.ProjectService;
import de.metas.project.workorder.data.CreateWOProjectRequest;
import de.metas.project.workorder.data.WOProject;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.web.exception.MissingPropertyException;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_PriceList;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.ZoneId;

@Service
public class WorkOrderMapper
{
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);
	private final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);

	private final ProjectService projectService;

	public WorkOrderMapper(@NonNull final ProjectService projectService)
	{
		this.projectService = projectService;
	}

	@NonNull
	public WOProject syncWOProjectWithJson(
			@NonNull final JsonWorkOrderProjectUpsertRequest request,
			@NonNull final WOProject existingWOProject,
			@NonNull final OrgId orgId)
	{
		final CurrencyId currencyId = getCurrencyId(request, existingWOProject);
		final PriceListVersionId priceListVersionId = getPriceListVersionId(request, existingWOProject);

		assertCurrencyIdsMatch(currencyId, priceListVersionId);

		final ZoneId zoneId = orgDAO.getTimeZone(orgId);

		final ProjectTypeId projectTypeId = ProjectTypeId.ofRepoId(request.getProjectTypeId().getValue());

		final String projectValue = getProjectValue(request, projectTypeId);
		final String projectName = getName(request, projectValue);

		final WOProject.WOProjectBuilder woProjectBuilder = existingWOProject.toBuilder()
				.orgId(orgId)
				.projectTypeId(projectTypeId)
				.currencyId(currencyId)
				.priceListVersionId(priceListVersionId)
				.value(projectValue)
				.name(projectName);

		if (request.isSalesRepIdSet())
		{
			woProjectBuilder.salesRepId(UserId.ofRepoIdOrNull(request.getSalesRepId().getValue()));
		}

		if (request.isBusinessPartnerIdSet())
		{
			woProjectBuilder.bPartnerId(BPartnerId.ofRepoIdOrNull(request.getBusinessPartnerId().getValue()));
		}

		if (request.isProjectParentIdSet())
		{
			woProjectBuilder.projectParentId(ProjectId.ofRepoIdOrNull(request.getProjectTypeId().getValue()));
		}

		if (request.isProjectReferenceExtSet())
		{
			woProjectBuilder.projectReferenceExt(request.getProjectReferenceExt());
		}

		if (request.isDateContractSet())
		{
			woProjectBuilder.dateContract(TimeUtil.asInstant(request.getDateContract(), zoneId));
		}

		if (request.isDateFinishSet())
		{
			woProjectBuilder.dateFinish(TimeUtil.asInstant(request.getDateFinish(), zoneId));
		}

		if (request.isDescriptionSet())
		{
			woProjectBuilder.description(request.getDescription());
		}

		if (request.isActiveSet())
		{
			woProjectBuilder.isActive(request.getIsActive());
		}

		if (request.isSpecialistConsultantIdSet())
		{
			woProjectBuilder.specialistConsultantId(request.getSpecialistConsultantId());
		}

		if (request.isDateOfProvisionByBPartnerSet())
		{
			woProjectBuilder.dateOfProvisionByBPartner(TimeUtil.asInstant(request.getDateOfProvisionByBPartner(), orgId));
		}

		if (request.isBpartnerDepartmentSet())
		{
			woProjectBuilder.bpartnerDepartment(request.getBpartnerDepartment());
		}

		if (request.isWoOwnerSet())
		{
			woProjectBuilder.woOwner(request.getWoOwner());
		}

		if (request.isPoReferenceSet())
		{
			woProjectBuilder.poReference(request.getPoReference());
		}

		if (request.isBpartnerTargetDateSet())
		{
			woProjectBuilder.bpartnerTargetDate(TimeUtil.asInstant(request.getBpartnerTargetDate(), zoneId));
		}

		if (request.isWoProjectCreatedDateSet())
		{
			woProjectBuilder.woProjectCreatedDate(TimeUtil.asInstant(request.getWoProjectCreatedDate(), zoneId));
		}

		return woProjectBuilder.build();
	}

	@NonNull
	public CreateWOProjectRequest buildCreateWOProjectRequest(
			@NonNull final JsonWorkOrderProjectUpsertRequest request,
			@NonNull final OrgId orgId)
	{
		if (request.getCurrencyId() == null)
		{
			throw new MissingPropertyException("currencyId", request);
		}

		final CurrencyId currencyId = getCurrencyId(request);
		final PriceListVersionId priceListVersionId = PriceListVersionId.ofRepoIdOrNull(JsonMetasfreshId.toValueInt(request.getPriceListVersionId()));

		assertCurrencyIdsMatch(currencyId, priceListVersionId);

		final ProjectTypeId projectTypeId = ProjectTypeId.ofRepoId(JsonMetasfreshId.toValueInt(request.getProjectTypeId()));
		final String projectValue = getProjectValue(request, projectTypeId);
		final String projectName = getName(request, projectValue);

		final ZoneId zoneId = orgDAO.getTimeZone(orgId);

		return CreateWOProjectRequest.builder()
				.orgId(orgId)
				.currencyId(currencyId)
				.name(projectName)
				.value(projectValue)
				.projectTypeId(projectTypeId)
				.isActive(request.getIsActive())
				.priceListVersionId(priceListVersionId)
				.description(request.getDescription())
				.projectReferenceExt(request.getProjectReferenceExt())
				.dateContract(TimeUtil.asInstant(request.getDateContract(), zoneId))
				.dateFinish(TimeUtil.asInstant(request.getDateFinish(), zoneId))
				.specialistConsultantId(request.getSpecialistConsultantId())
				.dateOfProvisionByBPartner(TimeUtil.asInstant(request.getDateOfProvisionByBPartner(), zoneId))
				.bpartnerDepartment(request.getBpartnerDepartment())
				.woOwner(request.getWoOwner())
				.poReference(request.getPoReference())
				.bpartnerTargetDate(TimeUtil.asInstant(request.getBpartnerTargetDate(), zoneId))
				.woProjectCreatedDate(TimeUtil.asInstant(request.getWoProjectCreatedDate(), zoneId))
				.salesRepId(UserId.ofRepoIdOrNull(request.getSalesRepId().getValue()))
				.bPartnerId(BPartnerId.ofRepoIdOrNull(request.getBusinessPartnerId().getValue()))
				.projectParentId(ProjectId.ofRepoIdOrNull(request.getProjectParentId().getValue()))
				.build();
	}

	@NonNull
	private CurrencyId getCurrencyId(
			@NonNull final JsonWorkOrderProjectUpsertRequest request,
			@NonNull final WOProject existingWOProject)
	{
		if (request.isCurrencyIdSet())
		{
			return getCurrencyId(request);
		}
		else
		{
			return existingWOProject.getCurrencyId();
		}
	}

	@NonNull
	private CurrencyId getCurrencyId(@NonNull final JsonWorkOrderProjectUpsertRequest request)
	{
		final CurrencyCode currencyCode = currencyBL.getCurrencyCodeById(CurrencyId.ofRepoId(request.getCurrencyId().getValue()));
		final Currency currency = currencyBL.getByCurrencyCode(CurrencyCode.ofThreeLetterCode(currencyCode.toThreeLetterCode()));

		return currency.getId();
	}

	private void assertCurrencyIdsMatch(
			@NonNull final CurrencyId currencyId,
			@Nullable final PriceListVersionId priceListVersionId) //todo fp move to some common service
	{
		if (priceListVersionId == null)
		{
			return;
		}

		final I_M_PriceList priceListRecord = priceListDAO.getPriceListByPriceListVersionId(priceListVersionId);

		if (priceListRecord == null)
		{
			return;
		}

		final CurrencyId priceListCurrencyId = CurrencyId.ofRepoId(priceListRecord.getC_Currency_ID());

		if (currencyId.getRepoId() != priceListCurrencyId.getRepoId())
		{
			throw new AdempiereException("Currency of the budget project does not match the currency of the price list!")
					.appendParametersToMessage()
					.setParameter("Project.CurrencyId", currencyId)
					.setParameter("PriceList.CurrencyId", priceListCurrencyId);
		}
	}

	@Nullable
	private static PriceListVersionId getPriceListVersionId(
			@NonNull final JsonWorkOrderProjectUpsertRequest request,
			@NonNull final WOProject existingWOProject)
	{
		if (request.isPriceListVersionIdSet())
		{
			return PriceListVersionId.ofRepoIdOrNull(JsonMetasfreshId.toValueInt(request.getPriceListVersionId()));
		}
		else
		{
			return existingWOProject.getPriceListVersionId();
		}
	}

	@NonNull
	private String getProjectValue(
			@NonNull final JsonWorkOrderProjectUpsertRequest request,
			@NonNull final ProjectTypeId projectTypeId)
	{
		final String projectValue;

		if (request.isValueSet())
		{
			projectValue = request.getValue();
		}
		else
		{
			projectValue = projectService.getNextProjectValue(projectTypeId);
		}

		if (Check.isBlank(projectValue))
		{
			throw new RuntimeException("Couldn't determine project value for projectIdentifier:" + request.getIdentifier());
		}

		return projectValue;
	}

	@NonNull
	private static String getName(
			@NonNull final JsonWorkOrderProjectUpsertRequest request,
			@NonNull final String updatedProjectValue)
	{
		if (request.isNameSet())
		{
			return request.getName();
		}
		else
		{
			return updatedProjectValue;
		}
	}
}
