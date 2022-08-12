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

package de.metas.rest_api.v2.project.workorder.responsemapper;

import com.google.common.collect.ImmutableMap;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.JsonResponseUpsertItem;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderResourceUpsertResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderStepUpsertResponse;
import de.metas.common.util.CoalesceUtil;
import de.metas.project.workorder.WOProjectStepId;
import de.metas.project.workorder.data.WOProjectStep;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.rest_api.v2.project.workorder.WorkOrderMapperUtil;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

@Value
public class WOProjectStepResponseMapper
{
	@NonNull
	String identifier;

	@NonNull
	JsonResponseUpsertItem.SyncOutcome syncOutcome;

	@NonNull
	Map<String, WOProjectResourceResponseMapper> resourceIdentifier2ResourceMapper;

	@Builder
	public WOProjectStepResponseMapper(
			@NonNull final String identifier,
			@NonNull final JsonResponseUpsertItem.SyncOutcome syncOutcome,
			@Nullable final Map<String, WOProjectResourceResponseMapper> resourceIdentifier2ResourceMapper)
	{
		this.identifier = identifier;
		this.syncOutcome = syncOutcome;
		this.resourceIdentifier2ResourceMapper = CoalesceUtil.coalesce(resourceIdentifier2ResourceMapper, ImmutableMap.of());
	}

	@NonNull
	public JsonWorkOrderStepUpsertResponse map(
			@NonNull final List<WOProjectStep> steps,
			@NonNull final Map<WOProjectStepId, List<JsonWorkOrderResourceUpsertResponse>> stepId2ResourcesResponse)
	{
		final IdentifierString identifierString = IdentifierString.of(this.identifier);

		return WorkOrderMapperUtil.resolveStepForExternalIdentifier(identifierString, steps)
				.map(matchedStep -> toResponse(matchedStep, stepId2ResourcesResponse))
				.orElseThrow(() -> new AdempiereException("No WOProjectStep found for identifier.")
						.appendParametersToMessage()
						.setParameter("IdentifierString", identifierString));
	}

	@NonNull
	private JsonWorkOrderStepUpsertResponse toResponse(
			@NonNull final WOProjectStep step,
			@NonNull final Map<WOProjectStepId, List<JsonWorkOrderResourceUpsertResponse>> stepId2ResourcesResponse)
	{
		return JsonWorkOrderStepUpsertResponse.builder()
				.identifier(this.identifier)
				.metasfreshId(JsonMetasfreshId.of(step.getWoProjectStepId().getRepoId()))
				.syncOutcome(this.syncOutcome)
				.resources(stepId2ResourcesResponse.get(step.getWoProjectStepId()))
				.build();
	}
}
