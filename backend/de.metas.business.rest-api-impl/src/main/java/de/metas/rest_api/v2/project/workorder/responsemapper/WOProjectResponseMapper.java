/*
 * #%L
 * de-metas-common-rest_api
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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.JsonResponseUpsertItem;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderObjectUnderTestUpsertResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderProjectUpsertResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderStepUpsertResponse;
import de.metas.common.util.CoalesceUtil;
import de.metas.project.workorder.data.WOProject;
import de.metas.project.workorder.data.WOProjectObjectUnderTest;
import de.metas.project.workorder.data.WOProjectStep;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.NonFinal;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

@Value
public class WOProjectResponseMapper
{
	@NonNull
	String identifier;

	@NonFinal
	JsonMetasfreshId metasfreshId;

	@NonNull
	JsonResponseUpsertItem.SyncOutcome syncOutcome;

	@NonNull
	Map<String, WOProjectStepResponseMapper> identifier2StepMapper;

	@NonNull
	Map<String, WOProjectObjectUnderTestResponseMapper> identifier2ObjectUnderTestMapper;

	@Builder
	public WOProjectResponseMapper(
			@NonNull final String identifier,
			@NonFinal final JsonMetasfreshId metasfreshId,
			@NonNull final JsonResponseUpsertItem.SyncOutcome syncOutcome,
			@Nullable final Map<String, WOProjectStepResponseMapper> identifier2StepMapper,
			@Nullable final Map<String, WOProjectObjectUnderTestResponseMapper> identifier2ObjectUnderTestMapper)
	{
		this.syncOutcome = syncOutcome;
		this.metasfreshId = metasfreshId;
		this.identifier = identifier;
		this.identifier2StepMapper = CoalesceUtil.coalesce(identifier2StepMapper, ImmutableMap.of());
		this.identifier2ObjectUnderTestMapper = CoalesceUtil.coalesce(identifier2ObjectUnderTestMapper, ImmutableMap.of());
	}

	@NonNull
	public JsonWorkOrderProjectUpsertResponse map(@NonNull final WOProject woProject) //todo fp: drop
	{
		final JsonMetasfreshId projectMetasfreshId = JsonMetasfreshId.of(woProject.getProjectId().getRepoId());

		if (!projectMetasfreshId.equals(this.metasfreshId))
		{
			throw new AdempiereException("No WOProject found for identifier.")
					.appendParametersToMessage()
					.setParameter("IdentifierString", this.identifier);
		}

		return JsonWorkOrderProjectUpsertResponse.builder()
				.identifier(this.identifier)
				.metasfreshId(this.metasfreshId)
				.syncOutcome(this.syncOutcome)
				// .steps(mapSteps(woProject.getProjectSteps()))
				// .objectsUnderTest(mapObjectUnderTest(woProject.getProjectObjectsUnderTest()))
				.build();
	}
}
