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

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.common.rest_api.common.JsonExternalId;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderProjectRequest;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderResourceRequest;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderStepRequest;
import de.metas.common.util.CoalesceUtil;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListVersionId;
import de.metas.product.ResourceId;
import de.metas.project.ProjectId;
import de.metas.project.ProjectTypeId;
import de.metas.project.budget.BudgetProjectResourceId;
import de.metas.project.workorder.data.WOProject;
import de.metas.project.workorder.data.WOProjectResource;
import de.metas.project.workorder.data.WOProjectStep;
import de.metas.user.UserId;
import de.metas.util.lang.ExternalId;
import de.metas.util.web.exception.MissingResourceException;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

import static de.metas.common.util.CoalesceUtil.coalesceNotNull;

@UtilityClass
public class FromJSONUtil
{
	@NonNull
	public WOProject fromJson(@NonNull final JsonWorkOrderProjectRequest request, @NonNull final OrgId orgId)
	{
		final JsonMetasfreshId projectTypeId = request.getProjectTypeId();
		if (projectTypeId == null)
		{
			throw MissingResourceException.builder()
					.resourceName("projectTypeId")
					.parentResource(request)
					.build()
					.setParameter("JsonWorkOrderProjectRequest", request);
		}

		final WOProject.WOProjectBuilder projectBuilder = WOProject.builder()
				.projectId(null)
				.name(request.getName())
				.projectReferenceExt(request.getProjectReferenceExt())
				.value(request.getValue())
				.projectTypeId(ProjectTypeId.ofRepoId(projectTypeId.getValue()))
				.projectParentId(ProjectId.ofRepoIdOrNull(JsonMetasfreshId.toValue(request.getProjectParentId())))
				.priceListVersionId(PriceListVersionId.ofRepoIdOrNull(JsonMetasfreshId.toValueInt(request.getPriceListVersionId())))
				.bPartnerId(BPartnerId.ofRepoIdOrNull(JsonMetasfreshId.toValueInt(request.getBusinessPartnerId())))
				.currencyId(CurrencyId.ofRepoIdOrNull(JsonMetasfreshId.toValueInt(request.getCurrencyId())))
				.orgId(orgId)
				.salesRepId(UserId.ofRepoIdOrNull(JsonMetasfreshId.toValueInt(request.getSalesRepId())))
				.description(request.getDescription())
				.dateContract(request.getDateContract())
				.dateFinish(request.getDateFinish())
				.isActive(request.getIsActive());

		final List<JsonWorkOrderStepRequest> stepRequests = coalesceNotNull(request.getSteps(), ImmutableList.of());
		for (final JsonWorkOrderStepRequest stepRequest : stepRequests)
		{
			projectBuilder.projectStep(fromJson(stepRequest, null));
		}

		return projectBuilder.build();
	}

	@NonNull
	public WOProjectStep fromJson(
			@NonNull final JsonWorkOrderStepRequest jsonStep,
			@Nullable final ProjectId projectId)
	{
		final WOProjectStep.WOProjectStepBuilder woProjectStepBuilder = WOProjectStep.builder()
				.name(jsonStep.getName())
				.description(jsonStep.getDescription())
				.dateEnd(jsonStep.getDateEnd())
				.dateStart(jsonStep.getDateStart())
				.seqNo(jsonStep.getSeqNo())
				.projectId(projectId)
				.externalId(ExternalId.ofOrNull(JsonExternalId.toValue(jsonStep.getExternalId())));

		final List<JsonWorkOrderResourceRequest> resourceRequests = jsonStep.getResourceRequests();
		for (final JsonWorkOrderResourceRequest resourceRequest : resourceRequests)
		{
			woProjectStepBuilder.projectResource(fromJson(
					resourceRequest,
					null,
					null,
					null,
					null));
		}
		return woProjectStepBuilder.build();
	}

	@NonNull
	public WOProjectResource fromJson(
			@NonNull final JsonWorkOrderResourceRequest jsonWorkOrderResourceRequest,
			@Nullable final BigDecimal durationOverride,
			@Nullable final String durationUnitOverride,
			@Nullable final ProjectId budgetProjectId,
			@Nullable final BudgetProjectResourceId budgetProjectResourceId)
	{
		return WOProjectResource.builder()
				.resourceId(ResourceId.ofRepoIdOrNull(JsonMetasfreshId.toValueInt(jsonWorkOrderResourceRequest.getResourceId())))
				.isActive(jsonWorkOrderResourceRequest.getIsActive())
				.isAllDay(jsonWorkOrderResourceRequest.getIsAllDay())
				.assignDateFrom(jsonWorkOrderResourceRequest.getAssignDateFrom())
				.assignDateTo(jsonWorkOrderResourceRequest.getAssignDateTo())
				.duration(CoalesceUtil.coalesce(durationOverride, jsonWorkOrderResourceRequest.getDuration()))
				.durationUnit(CoalesceUtil.coalesce(durationUnitOverride, jsonWorkOrderResourceRequest.getDurationUnit()))
				.budgetProjectId(budgetProjectId)
				.projectResourceBudgetId(budgetProjectResourceId)
				.externalId(ExternalId.ofOrNull(JsonExternalId.toValue(jsonWorkOrderResourceRequest.getExternalId())))
				.build();
	}
}
