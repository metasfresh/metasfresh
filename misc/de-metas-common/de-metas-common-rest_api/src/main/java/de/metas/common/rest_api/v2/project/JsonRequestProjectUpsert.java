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

package de.metas.common.rest_api.v2.project;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import de.metas.common.rest_api.v2.SyncAdvise;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;

import static de.metas.common.rest_api.v2.SwaggerDocConstants.READ_ONLY_SYNC_ADVISE_DOC;
import static de.metas.common.util.CoalesceUtil.coalesce;

@Value
@ApiModel
public class JsonRequestProjectUpsert
{
	@ApiModelProperty(position = 10, required = true)
	List<JsonRequestProjectUpsertItem> requestItems;

	@ApiModelProperty(position = 20, value = "Default sync-advise\n" + READ_ONLY_SYNC_ADVISE_DOC)
	SyncAdvise syncAdvise;

	@JsonCreator
	@Builder
	public JsonRequestProjectUpsert(
			@Singular @JsonProperty("requestItems") final List<JsonRequestProjectUpsertItem> requestItems,
			@Nullable @JsonProperty("syncAdvise") final SyncAdvise syncAdvise)
	{
		this.requestItems = coalesce(requestItems, ImmutableList.of());
		this.syncAdvise = coalesce(syncAdvise, SyncAdvise.READ_ONLY);
	}
}
