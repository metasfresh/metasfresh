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
import de.metas.common.rest_api.common.JsonExternalId;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderProjectUpsertRequest;
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
import de.metas.project.workorder.project.CreateWOProjectRequest;
import de.metas.project.workorder.project.WOProject;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.rest_api.v2.project.ValidateProjectHelper;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.ExternalId;
import de.metas.util.web.exception.MissingPropertyException;
import lombok.NonNull;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.ZoneId;

@Service
public class WorkOrderMapper
{
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
	private final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);

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

		ValidateProjectHelper.assertCurrencyIdsMatch(currencyId, priceListVersionId, priceListDAO::getPriceListByPriceListVersionId);

		final ZoneId zoneId = orgDAO.getTimeZone(orgId);

		final ProjectTypeId projectTypeId = request.getProjectTypeId().mapValue(ProjectTypeId::ofRepoId);

		final WOProject.WOProjectBuilder woProjectBuilder = existingWOProject.toBuilder()
				.orgId(orgId)
				.projectTypeId(projectTypeId)
				.currencyId(currencyId)
				.priceListVersionId(priceListVersionId);

		if (request.isValueSet())
		{
			woProjectBuilder.value(request.getValue());
		}

		if (request.isNameSet())
		{
			woProjectBuilder.name(request.getName());
		}

		if (request.isSalesRepIdSet())
		{
			woProjectBuilder.salesRepId(JsonMetasfreshId.mapToOrNull(request.getSalesRepId(), UserId::ofRepoId));
		}

		if (request.isBpartnerIdSet())
		{
			woProjectBuilder.bPartnerId(JsonMetasfreshId.mapToOrNull(request.getBpartnerId(), BPartnerId::ofRepoId));
		}

		if (request.isProjectParentIdSet())
		{
			woProjectBuilder.projectParentId(JsonMetasfreshId.mapToOrNull(request.getProjectParentId(), ProjectId::ofRepoId));
		}

		if (request.isProjectReferenceExtSet())
		{
			woProjectBuilder.projectReferenceExt(request.getProjectReferenceExt());
		}

		if(request.isExternalIdSet())
		{
			woProjectBuilder.externalId(ExternalId.ofOrNull(JsonExternalId.toValue(request.getExternalId())));
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

		if (request.isDateOfProvisionByBPartnerSet())
		{
			woProjectBuilder.dateOfProvisionByBPartner(TimeUtil.asInstant(request.getDateOfProvisionByBPartner(), zoneId));
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
		if (request.getCurrencyCode() == null)
		{
			throw new MissingPropertyException("currencyCode", request);
		}

		final CurrencyId currencyId = currencyBL.getByCurrencyCode(CurrencyCode.ofThreeLetterCode(request.getCurrencyCode())).getId();
		final PriceListVersionId priceListVersionId = JsonMetasfreshId.mapToOrNull(request.getPriceListVersionId(), PriceListVersionId::ofRepoId);

		ValidateProjectHelper.assertCurrencyIdsMatch(currencyId, priceListVersionId, priceListDAO::getPriceListByPriceListVersionId);

		final ProjectTypeId projectTypeId = request.getProjectTypeId().mapValue(ProjectTypeId::ofRepoId);
		final String projectValue = getProjectValue(request, projectTypeId);
		final String projectName = getName(request, projectValue);

		final ZoneId zoneId = orgDAO.getTimeZone(orgId);

		final CreateWOProjectRequest.CreateWOProjectRequestBuilder createWOProjectRequestBuilder = CreateWOProjectRequest.builder()
				.orgId(orgId)
				.currencyId(currencyId)
				.name(projectName)
				.value(projectValue)
				.projectType(projectService.getProjectTypeById(projectTypeId))
				.isActive(request.getIsActive())
				.priceListVersionId(priceListVersionId)
				.description(request.getDescription())
				.projectReferenceExt(request.getProjectReferenceExt())
				.externalId(ExternalId.ofOrNull(JsonExternalId.toValue(request.getExternalId())))
				.dateContract(TimeUtil.asInstant(request.getDateContract(), zoneId))
				.dateFinish(TimeUtil.asInstant(request.getDateFinish(), zoneId))
				.dateOfProvisionByBPartner(TimeUtil.asInstant(request.getDateOfProvisionByBPartner(), zoneId))
				.bpartnerDepartment(request.getBpartnerDepartment())
				.woOwner(request.getWoOwner())
				.poReference(request.getPoReference())
				.bpartnerTargetDate(TimeUtil.asInstant(request.getBpartnerTargetDate(), zoneId))
				.woProjectCreatedDate(TimeUtil.asInstant(request.getWoProjectCreatedDate(), zoneId))
				.salesRepId(JsonMetasfreshId.mapToOrNull(request.getSalesRepId(), UserId::ofRepoId))
				.bPartnerId(JsonMetasfreshId.mapToOrNull(request.getBpartnerId(), BPartnerId::ofRepoId))
				.projectParentId(JsonMetasfreshId.mapToOrNull(request.getProjectParentId(), ProjectId::ofRepoId));

		if (request.getExternalId() == null) // if no externalId was given for the new project, then see if it was implied by the identifier
		{
			final IdentifierString projectIdentifier = request.mapProjectIdentifier(IdentifierString::of);
			if(projectIdentifier.isExternalId())
			{
				createWOProjectRequestBuilder.externalId(projectIdentifier.asExternalId());
			}
		}
		if(Check.isBlank(request.getValue())) // if no value was given for the new project, then see if it was implied by the identifier
		{
			final IdentifierString projectIdentifier = request.mapProjectIdentifier(IdentifierString::of);
			if(projectIdentifier.isValue())
			{
				createWOProjectRequestBuilder.value(projectIdentifier.asValue());
			}
		}
		
		return createWOProjectRequestBuilder.build();
	}

	@NonNull
	private String getProjectValue(
			@NonNull final JsonWorkOrderProjectUpsertRequest request,
			@NonNull final ProjectTypeId projectTypeId)
	{
		final String projectValue = request.isValueSet()
				? request.getValue()
				: projectService.getNextProjectValue(projectTypeId);

		if (Check.isBlank(projectValue))
		{
			throw new RuntimeException("Couldn't determine project value for projectIdentifier:" + request.getIdentifier());
		}

		return projectValue;
	}

	@NonNull
	private static String getName(@NonNull final JsonWorkOrderProjectUpsertRequest request, @NonNull final String updatedProjectValue)
	{
		final String projectName = request.isNameSet()
				? request.getName()
				: updatedProjectValue;

		if (Check.isBlank(projectName))
		{
			throw new RuntimeException("Couldn't determine project name for projectIdentifier:" + request.getIdentifier());
		}

		return projectName;
	}

	@NonNull
	private CurrencyId getCurrencyId(@NonNull final JsonWorkOrderProjectUpsertRequest request, @NonNull final WOProject existingWOProject)
	{
		return request.isCurrencyCodeSet()
				? currencyBL.getByCurrencyCode(CurrencyCode.ofThreeLetterCode(request.getCurrencyCode())).getId()
				: existingWOProject.getCurrencyId();
	}

	@Nullable
	private static PriceListVersionId getPriceListVersionId(
			@NonNull final JsonWorkOrderProjectUpsertRequest request,
			@NonNull final WOProject existingWOProject)
	{
		return request.isPriceListVersionIdSet()
				? JsonMetasfreshId.mapToOrNull(request.getPriceListVersionId(), PriceListVersionId::ofRepoId)
				: existingWOProject.getPriceListVersionId();
	}
}
