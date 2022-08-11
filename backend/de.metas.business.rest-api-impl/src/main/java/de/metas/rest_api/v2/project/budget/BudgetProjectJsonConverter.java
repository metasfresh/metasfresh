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

package de.metas.rest_api.v2.project.budget;

import de.metas.bpartner.BPartnerId;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.project.budget.JsonBudgetProjectUpsertRequest;
import de.metas.currency.Currency;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyBL;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.project.ProjectId;
import de.metas.project.ProjectTypeId;
import de.metas.project.budget.BudgetProject;
import de.metas.project.budget.CreateBudgetProjectRequest;
import de.metas.project.service.ProjectService;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.web.exception.MissingPropertyException;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_PriceList;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

@Service
public class BudgetProjectJsonConverter
{
	private final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);
	private final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
	private final ProjectService projectService;

	public BudgetProjectJsonConverter(@NonNull final ProjectService projectService)
	{
		this.projectService = projectService;
	}

	@NonNull
	public BudgetProject syncBudgetProjectWithJson(
			@NonNull final JsonBudgetProjectUpsertRequest request,
			@NonNull final BudgetProject existingBudgetProject,
			@NonNull final OrgId orgId)
	{
		final ProjectTypeId projectTypeId = ProjectTypeId.ofRepoId(JsonMetasfreshId.toValueInt(request.getProjectTypeId()));

		final CurrencyId currencyId = getCurrencyId(request, existingBudgetProject);
		final PriceListVersionId priceListVersionId = getPriceListVersionId(request, existingBudgetProject);

		assertCurrencyIdsMatch(currencyId, priceListVersionId);

		final BudgetProject.BudgetProjectBuilder budgetProjectBuilder = existingBudgetProject.toBuilder()
				.projectTypeId(projectTypeId)
				.orgId(orgId)
				.currencyId(currencyId)
				.priceListVersionId(priceListVersionId);

		if (request.isValueSet())
		{
			budgetProjectBuilder.value(request.getValue());
		}

		if (request.isNameSet())
		{
			budgetProjectBuilder.name(request.getName());
		}

		if (request.isSalesRepIdSet())
		{
			budgetProjectBuilder.salesRepId(UserId.ofRepoIdOrNull(JsonMetasfreshId.toValueInt(request.getSalesRepId())));
		}

		if (request.isProjectReferenceExtSet())
		{
			budgetProjectBuilder.projectReferenceExt(request.getProjectReferenceExt());
		}

		if (request.isProjectParentIdSet())
		{
			budgetProjectBuilder.projectParentId(ProjectId.ofRepoIdOrNull(JsonMetasfreshId.toValueInt(request.getProjectParentId())));
		}

		if (request.isBpartnerIdSet())
		{
			budgetProjectBuilder.bPartnerId(BPartnerId.ofRepoIdOrNull(JsonMetasfreshId.toValueInt(request.getBpartnerId())));
		}

		if (request.isDateContractSet())
		{
			budgetProjectBuilder.dateContract(request.getDateContract());
		}

		if (request.isDateFinishSet())
		{
			budgetProjectBuilder.dateFinish(request.getDateFinish());
		}

		if (request.isDescriptionSet())
		{
			budgetProjectBuilder.description(request.getDescription());
		}

		if (request.isActiveSet())
		{
			budgetProjectBuilder.isActive(request.getActive());
		}

		return budgetProjectBuilder.build();
	}

	@NonNull
	public CreateBudgetProjectRequest buildCreateBudgetProjectRequest(
			@NonNull final JsonBudgetProjectUpsertRequest request,
			@NonNull final OrgId orgId)
	{
		if (!request.isCurrencyCodeSet())
		{
			throw new MissingPropertyException("currencyCode", request);
		}

		final ProjectTypeId projectTypeId = ProjectTypeId.ofRepoId(JsonMetasfreshId.toValueInt(request.getProjectTypeId()));
		final String projectValue = getProjectValue(request, projectTypeId);
		final String projectName = getName(request, projectValue);
		final CurrencyId currencyId = currencyBL.getByCurrencyCode(CurrencyCode.ofThreeLetterCode(request.getCurrencyCode())).getId();
		final PriceListVersionId priceListVersionId = PriceListVersionId.ofRepoIdOrNull(JsonMetasfreshId.toValueInt(request.getPriceListVersionId()));

		assertCurrencyIdsMatch(currencyId, priceListVersionId);

		return CreateBudgetProjectRequest.builder()
				.orgId(orgId)
				.projectTypeId(projectTypeId)
				.value(projectValue)
				.name(projectName)
				.currencyId(currencyId)
				.priceListVersionId(priceListVersionId)
				.isActive(request.getActive())
				.description(request.getDescription())
				.projectParentId(ProjectId.ofRepoIdOrNull(JsonMetasfreshId.toValueInt(request.getProjectParentId())))
				.projectReferenceExt(request.getProjectReferenceExt())
				.bPartnerId(BPartnerId.ofRepoIdOrNull(JsonMetasfreshId.toValueInt(request.getBpartnerId())))
				.salesRepId(UserId.ofRepoIdOrNull(JsonMetasfreshId.toValueInt(request.getSalesRepId())))
				.dateContract(request.getDateContract())
				.dateFinish(request.getDateFinish())
				.build();
	}

	@NonNull
	private String getName(
			@NonNull final JsonBudgetProjectUpsertRequest request,
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

	@NonNull
	private String getProjectValue(
			@NonNull final JsonBudgetProjectUpsertRequest request,
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
			throw new RuntimeException("Couldn't determine project value for projectIdentifier:" + request.getProjectIdentifier());
		}

		return projectValue;
	}

	@NonNull
	private CurrencyId getCurrencyId(
			@NonNull final JsonBudgetProjectUpsertRequest request,
			@NonNull final BudgetProject existingBudgetProject)
	{
		if (request.isCurrencyCodeSet())
		{
			final Currency currency = currencyBL.getByCurrencyCode(CurrencyCode.ofThreeLetterCode(request.getCurrencyCode()));

			return currency.getId();
		}
		else
		{
			return existingBudgetProject.getCurrencyId();
		}
	}

	@Nullable
	private PriceListVersionId getPriceListVersionId(
			@NonNull final JsonBudgetProjectUpsertRequest request,
			@NonNull final BudgetProject existingBudgetProject)
	{
		if (request.isPriceListVersionIdSet())
		{
			return PriceListVersionId.ofRepoIdOrNull(JsonMetasfreshId.toValueInt(request.getPriceListVersionId()));
		}
		else
		{
			return existingBudgetProject.getPriceListVersionId();
		}
	}

	private void assertCurrencyIdsMatch(
			@NonNull final CurrencyId currencyId,
			@Nullable final PriceListVersionId priceListVersionId)
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
}
