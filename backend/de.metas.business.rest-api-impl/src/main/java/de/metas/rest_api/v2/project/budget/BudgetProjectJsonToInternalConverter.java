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
import de.metas.common.rest_api.common.JsonExternalId;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.project.budget.JsonBudgetProjectUpsertRequest;
import de.metas.common.rest_api.v2.project.budget.JsonBudgetResourceUpsertRequest;
import de.metas.common.util.CoalesceUtil;
import de.metas.costing.CostAmount;
import de.metas.costing.CostPrice;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyBL;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListVersionId;
import de.metas.product.ResourceId;
import de.metas.project.ProjectId;
import de.metas.project.ProjectTypeId;
import de.metas.project.budget.data.BudgetProject;
import de.metas.project.budget.data.BudgetProjectResource;
import de.metas.resource.Resource;
import de.metas.resource.ResourceGroupId;
import de.metas.resource.ResourceService;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.util.web.exception.InvalidIdentifierException;
import de.metas.util.web.exception.MissingResourceException;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static de.metas.RestUtils.retrieveOrgIdOrDefault;

@Service
public class BudgetProjectJsonToInternalConverter
{
	private final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);

	private final ResourceService resourceService;

	public BudgetProjectJsonToInternalConverter(@NonNull final ResourceService resourceService)
	{
		this.resourceService = resourceService;
	}

	@NonNull
	public BudgetProject updateWOProjectFromJson(
			@NonNull final JsonBudgetProjectUpsertRequest request,
			@Nullable final BudgetProject existingBudgetProject)
	{
		final OrgId orgId = retrieveOrgIdOrDefault(request.getOrgCode());

		final Instant dateContract = TimeUtil.asInstant(request.getDateContract(), orgId);
		final Instant dateFinish = TimeUtil.asInstant(request.getDateFinish(), orgId);

		final BudgetProject.BudgetProjectBuilder updatedBudgetProjectBuilder = BudgetProject.builder()
				.projectTypeId(ProjectTypeId.ofRepoId(request.getProjectTypeId().getValue()))
				.orgId(orgId);
		if (existingBudgetProject != null)
		{
			updatedBudgetProjectBuilder.projectId(existingBudgetProject.getProjectIdNonNull());
		}

		if (request.isPriceListVersionIdSet() || existingBudgetProject == null)
		{
			updatedBudgetProjectBuilder.priceListVersionId(PriceListVersionId.ofRepoIdOrNull(JsonMetasfreshId.toValueInt(request.getPriceListVersionId())));
		}
		else
		{
			updatedBudgetProjectBuilder.priceListVersionId(existingBudgetProject.getPriceListVersionId());
		}

		if (request.isCurrencyIdSet() || existingBudgetProject == null)
		{
			updatedBudgetProjectBuilder.currencyId(CurrencyId.ofRepoIdOrNull(JsonMetasfreshId.toValueInt(request.getCurrencyId())));
		}
		else
		{
			updatedBudgetProjectBuilder.currencyId(existingBudgetProject.getCurrencyId());
		}

		if (request.isSalesRepIdSet() || existingBudgetProject == null)
		{
			updatedBudgetProjectBuilder.salesRepId(UserId.ofRepoIdOrNull(JsonMetasfreshId.toValueInt(request.getSalesRepId())));
		}
		else
		{
			updatedBudgetProjectBuilder.salesRepId(existingBudgetProject.getSalesRepId());
		}

		if (request.isProjectReferenceExtSet() || existingBudgetProject == null)
		{
			updatedBudgetProjectBuilder.projectReferenceExt(request.getProjectReferenceExt());
		}
		else
		{
			updatedBudgetProjectBuilder.projectReferenceExt(existingBudgetProject.getProjectReferenceExt());
		}

		if (request.isProjectParentIdSet() || existingBudgetProject == null)
		{
			updatedBudgetProjectBuilder.projectParentId(ProjectId.ofRepoIdOrNull(JsonMetasfreshId.toValueInt(request.getProjectParentId())));
		}
		else
		{
			updatedBudgetProjectBuilder.projectParentId(existingBudgetProject.getProjectParentId());
		}

		if (request.isBusinessPartnerIdSet() || existingBudgetProject == null)
		{
			updatedBudgetProjectBuilder.bPartnerId(BPartnerId.ofRepoIdOrNull(JsonMetasfreshId.toValueInt(request.getBusinessPartnerId())));
		}
		else
		{
			updatedBudgetProjectBuilder.bPartnerId(existingBudgetProject.getBPartnerId());
		}

		if (request.isCurrencyIdSet() || existingBudgetProject == null)
		{
			updatedBudgetProjectBuilder.currencyId(CurrencyId.ofRepoIdOrNull(JsonMetasfreshId.toValueInt(request.getCurrencyId())));
		}
		else
		{
			updatedBudgetProjectBuilder.currencyId(existingBudgetProject.getCurrencyId());
		}

		if (request.isNameSet() || existingBudgetProject == null)
		{
			updatedBudgetProjectBuilder.name(request.getName());
		}
		else
		{
			updatedBudgetProjectBuilder.name(existingBudgetProject.getName());
		}

		if (request.isValueSet() || existingBudgetProject == null)
		{
			updatedBudgetProjectBuilder.value(request.getValue());
		}
		else
		{
			updatedBudgetProjectBuilder.value(existingBudgetProject.getValue());
		}

		if (request.isDateContractSet() || existingBudgetProject == null)
		{
			updatedBudgetProjectBuilder.dateContract(dateContract);
		}
		else
		{
			updatedBudgetProjectBuilder.dateContract(existingBudgetProject.getDateContract());
		}

		if (request.isDateFinishSet() || existingBudgetProject == null)
		{
			updatedBudgetProjectBuilder.dateFinish(dateFinish);
		}
		else
		{
			updatedBudgetProjectBuilder.dateFinish(existingBudgetProject.getDateFinish());
		}

		if (request.isDescriptionSet() || existingBudgetProject == null)
		{
			updatedBudgetProjectBuilder.description(request.getDescription());
		}
		else
		{
			updatedBudgetProjectBuilder.description(existingBudgetProject.getDescription());
		}

		if (request.isActiveSet() || existingBudgetProject == null)
		{
			updatedBudgetProjectBuilder.isActive(CoalesceUtil.coalesceNotNull(request.getIsActive(), true));
		}
		else
		{
			updatedBudgetProjectBuilder.isActive(existingBudgetProject.getIsActive());
		}

		final Map<JsonExternalId, JsonBudgetResourceUpsertRequest> jsonProjectResources = request.getResources().stream()
				.collect(Collectors.toMap(JsonBudgetResourceUpsertRequest::getExternalId, Function.identity()));

		if (existingBudgetProject != null)
		{
			for (final BudgetProjectResource existingProjectResource : existingBudgetProject.getProjectResources())
			{
				if (existingProjectResource.getExternalId() == null)
				{
					continue; // can't match a step that has no external ID
				}
				final JsonExternalId existingJsonExtenalId = JsonExternalId.of(existingProjectResource.getExternalId().getValue());
				final JsonBudgetResourceUpsertRequest matchedJsonProjectStep = jsonProjectResources.remove(existingJsonExtenalId);
				if (matchedJsonProjectStep == null)
				{
					continue;
				}
				final BudgetProjectResource updatedBudgetProjectResource = updateBudgetProjectResourceFromJson(
						orgId,
						existingBudgetProject.getProjectIdNonNull(),
						existingBudgetProject.getCurrencyId(),
						matchedJsonProjectStep,
						existingProjectResource);
				updatedBudgetProjectBuilder.projectResource(updatedBudgetProjectResource);
			}
		}
		for (final JsonBudgetResourceUpsertRequest remainingJsonProjectResource : jsonProjectResources.values())
		{
			final ResourceId resourceId = extractResourceId(orgId, remainingJsonProjectResource);

			final Instant dateFinishPlan = TimeUtil.asInstant(remainingJsonProjectResource.getDateFinishPlan(), orgId);
			final Instant dateStartPlan = TimeUtil.asInstant(remainingJsonProjectResource.getDateStartPlan(), orgId);
			if (dateStartPlan == null || dateFinishPlan == null)
			{
				throw new AdempiereException("I_C_Project_Resource_Budget.dateFinishPlan and I_C_Project_Resource_Budget.dateStartPlan should be set on the record at this point!");
			}

			final CurrencyId requestCurrencyId = CurrencyId.ofRepoId(request.getCurrencyId().getValue());

			final CurrencyCode currencyCode = currencyBL.getCurrencyCodeById(requestCurrencyId);

			final Amount plannedAmt = Amount.of(remainingJsonProjectResource.getPlannedAmt().getAmount(), currencyCode);

			final CostPrice pricePerTimeUOM = CostPrice.ownCostPrice(CostAmount.of(remainingJsonProjectResource.getPricePerTimeUOM(), requestCurrencyId),
																	 UomId.ofRepoId(remainingJsonProjectResource.getUomTimeId().getValue()));

			final BudgetProjectResource updatedBudgetProjectResource = BudgetProjectResource.builder()
					.resourceId(resourceId)
					.resourceGroupId(ResourceGroupId.ofRepoIdOrNull(JsonMetasfreshId.toValueInt(remainingJsonProjectResource.getResourceGroupId())))
					.dateFinishPlan(dateFinishPlan)
					.dateStartPlan(dateStartPlan)
					.description(remainingJsonProjectResource.getDescription())
					.isActive(remainingJsonProjectResource.getIsActive())
					.plannedAmt(plannedAmt)
					.plannedDuration(remainingJsonProjectResource.getPlannedDuration())
					.pricePerTimeUOM(pricePerTimeUOM)
					.currencyId(requestCurrencyId)
					.uomTimeId(UomId.ofRepoId(JsonMetasfreshId.toValueInt(remainingJsonProjectResource.getUomTimeId())))
					.build();

			updatedBudgetProjectBuilder.projectResource(updatedBudgetProjectResource);
		}

		return updatedBudgetProjectBuilder.build();
	}

	@NonNull
	private BudgetProjectResource updateBudgetProjectResourceFromJson(
			@NonNull final OrgId orgId,
			@NonNull final ProjectId projectId,
			@NonNull final CurrencyId currencyId,
			@NonNull final JsonBudgetResourceUpsertRequest request,
			@Nullable final BudgetProjectResource budgetProjectResourceToUpdate)
	{
		final Instant dateFinishPlan = TimeUtil.asInstant(request.getDateFinishPlan(), orgId);
		final Instant dateStartPlan = TimeUtil.asInstant(request.getDateStartPlan(), orgId);
		if (dateStartPlan == null || dateFinishPlan == null)
		{
			throw new AdempiereException("I_C_Project_Resource_Budget.dateFinishPlan and I_C_Project_Resource_Budget.dateStartPlan should be set on the record at this point!");
		}

		final CurrencyCode currencyCode = currencyBL.getCurrencyCodeById(currencyId);

		if (currencyCode.toThreeLetterCode().equals(request.getPlannedAmt().getCurrencyCode()))
		{
			throw new AdempiereException("Currency code for the amount does not match the currency of the Budget Project!");
		}

		final ResourceId resourceId = extractResourceId(orgId, request);

		final BudgetProjectResource.BudgetProjectResourceBuilder updatedBudgetProjectResourceBuilder = BudgetProjectResource.builder()
				.resourceId(resourceId);
		if (request.isPlannedAmtSet() || budgetProjectResourceToUpdate == null)
		{
			final Amount plannedAmt = Amount.of(request.getPlannedAmt().getAmount(), currencyCode);
			updatedBudgetProjectResourceBuilder.plannedAmt(plannedAmt);
		}
		else
		{
			updatedBudgetProjectResourceBuilder.plannedAmt(budgetProjectResourceToUpdate.getPlannedAmt());
		}

		if (request.isPricePerTimeUOMSet() || budgetProjectResourceToUpdate == null)
		{
			final CostPrice pricePerTimeUOM = CostPrice.ownCostPrice(CostAmount.of(request.getPricePerTimeUOM(), currencyId),
																	 UomId.ofRepoId(request.getUomTimeId().getValue()));

			updatedBudgetProjectResourceBuilder.pricePerTimeUOM(pricePerTimeUOM);
		}
		else
		{
			updatedBudgetProjectResourceBuilder.pricePerTimeUOM(budgetProjectResourceToUpdate.getPricePerTimeUOM());
		}

		if (request.isPlannedDurationSet() || budgetProjectResourceToUpdate == null)
		{
			updatedBudgetProjectResourceBuilder.plannedDuration(request.getPlannedDuration());
		}
		else
		{
			updatedBudgetProjectResourceBuilder.plannedDuration(budgetProjectResourceToUpdate.getPlannedDuration());
		}

		if (request.isUomTimeIdSet() || budgetProjectResourceToUpdate == null)
		{
			updatedBudgetProjectResourceBuilder.uomTimeId(UomId.ofRepoId(request.getUomTimeId().getValue()));
		}
		else
		{
			updatedBudgetProjectResourceBuilder.uomTimeId(budgetProjectResourceToUpdate.getUomTimeId());
		}

		if (request.isDateStartPlanSet() || budgetProjectResourceToUpdate == null)
		{
			updatedBudgetProjectResourceBuilder.dateStartPlan(dateStartPlan);
		}
		else
		{
			updatedBudgetProjectResourceBuilder.dateStartPlan(budgetProjectResourceToUpdate.getDateStartPlan());
		}

		if (request.isDateFinishPlanSet() || budgetProjectResourceToUpdate == null)
		{
			updatedBudgetProjectResourceBuilder.dateFinishPlan(dateFinishPlan);
		}
		else
		{
			updatedBudgetProjectResourceBuilder.dateFinishPlan(budgetProjectResourceToUpdate.getDateFinishPlan());
		}

		if (request.isDescriptionSet() || budgetProjectResourceToUpdate == null)
		{
			updatedBudgetProjectResourceBuilder.description(request.getDescription());
		}
		else
		{
			updatedBudgetProjectResourceBuilder.description(budgetProjectResourceToUpdate.getDescription());
		}

		if (request.isActiveSet() || budgetProjectResourceToUpdate == null)
		{
			updatedBudgetProjectResourceBuilder.isActive(request.getIsActive());
		}
		else
		{
			updatedBudgetProjectResourceBuilder.isActive(budgetProjectResourceToUpdate.getIsActive());
		}

		if (request.isResourceGroupIdSet() || budgetProjectResourceToUpdate == null)
		{
			updatedBudgetProjectResourceBuilder.resourceGroupId(ResourceGroupId.ofRepoIdOrNull(request.getResourceGroupId().getValue()));
		}
		else
		{
			updatedBudgetProjectResourceBuilder.resourceGroupId(budgetProjectResourceToUpdate.getResourceGroupId());
		}

		// if (request())
		// {
		// 	final ResourceId resourceId = extractResourceId(orgId, request);
		// 	updatedBudgetProjectResourceBuilder.resourceId(resourceId);
		// }
		// else
		// {
		// 	updatedBudgetProjectResourceBuilder.resourceId(budgetProjectResourceToUpdate.getResourceId());
		// }

		return updatedBudgetProjectResourceBuilder.build();
	}

	private ResourceId extractResourceId(
			@NonNull final OrgId orgId,
			@NonNull final JsonBudgetResourceUpsertRequest request
	)
	{
		final IdentifierString resourceIdentifier = IdentifierString.of(request.getResourceIdentifier());

		final Predicate<Resource> resourcePredicate;
		switch (resourceIdentifier.getType())
		{
			case METASFRESH_ID:
				resourcePredicate = r -> ResourceId.toRepoId(r.getResourceId()) == resourceIdentifier.asMetasfreshId().getValue();
				break;
			case VALUE:
				resourcePredicate = r -> // make sure that org and value match
						(r.getOrgId().isAny() || orgId.isAny() || Objects.equals(r.getOrgId(), orgId))
								&& Objects.equals(r.getValue(), resourceIdentifier.asValue());
				break;
			case INTERNALNAME:
				resourcePredicate = r -> // make sure that org and value match
						(r.getOrgId().isAny() || orgId.isAny() || Objects.equals(r.getOrgId(), orgId))
								&& Objects.equals(r.getInternalName(), resourceIdentifier.asInternalName());
				break;
			default:
				throw new InvalidIdentifierException(resourceIdentifier.getRawIdentifierString(), request);
		}

		return resourceService.getAllActiveResources()
				.stream()
				.filter(resourcePredicate)
				.findAny()
				.map(Resource::getResourceId)
				.orElseThrow(() -> MissingResourceException.builder()
						.resourceName("resourceIdentifier")
						.resourceIdentifier(request.getResourceIdentifier())
						.parentResource(request)
						.build());
	}
}
