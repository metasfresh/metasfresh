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
import de.metas.common.util.CoalesceUtil;
import de.metas.util.lang.ExternalId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.NonFinal;

import javax.annotation.Nullable;
import java.util.Map;

@Value
public class WOProjectStepResponseMapper
{
	@NonNull
	ExternalId stepExternalId;

	@NonNull
	JsonResponseUpsertItem.SyncOutcome syncOutcome;

	@NonFinal
	JsonMetasfreshId stepMetasfreshId;

	@NonNull
	Map<ExternalId, WOProjectResourceResponseMapper> resourceToExternalIdMap;

	@Builder
	public WOProjectStepResponseMapper(
			@NonNull final ExternalId stepExternalId,
			@NonNull final JsonResponseUpsertItem.SyncOutcome syncOutcome,
			@NonFinal final JsonMetasfreshId stepMetasfreshId,
			@Nullable final Map<ExternalId, WOProjectResourceResponseMapper> resourceToExternalIdMap)
	{
		this.stepExternalId = stepExternalId;
		this.syncOutcome = syncOutcome;
		this.stepMetasfreshId = stepMetasfreshId;
		this.resourceToExternalIdMap = CoalesceUtil.coalesce(resourceToExternalIdMap, ImmutableMap.of());
	}
}
