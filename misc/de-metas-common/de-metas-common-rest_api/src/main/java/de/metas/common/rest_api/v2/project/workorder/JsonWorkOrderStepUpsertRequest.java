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

package de.metas.common.rest_api.v2.project.workorder;

import com.google.common.collect.ImmutableList;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.SyncAdvise;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.List;

import static de.metas.common.rest_api.v2.SwaggerDocConstants.READ_ONLY_SYNC_ADVISE_DOC;
import static de.metas.common.util.CoalesceUtil.coalesce;

@Value
@Schema
public class JsonWorkOrderStepUpsertRequest
{
	@Schema(description = "Corresponding to `C_Project_WO_Step.C_Project_ID`", required = true)
	JsonMetasfreshId projectId;

	@Schema(required = true)
	List<JsonWorkOrderStepUpsertItemRequest> requestItems;

	@Schema(description = "Default sync-advise\n" + READ_ONLY_SYNC_ADVISE_DOC)
	SyncAdvise syncAdvise;

	@Builder
	@Jacksonized
	public JsonWorkOrderStepUpsertRequest(
			@NonNull final JsonMetasfreshId projectId,
			@Nullable @Singular final List<JsonWorkOrderStepUpsertItemRequest> requestItems,
			@Nullable final SyncAdvise syncAdvise)
	{
		this.projectId = projectId;
		this.requestItems = coalesce(requestItems, ImmutableList.of());
		this.syncAdvise = coalesce(syncAdvise, SyncAdvise.READ_ONLY);
	}
}
