/*
 * #%L
 * de-metas-common-bpartner
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.common.bpartner.request;

import static de.metas.common.rest_api.SwaggerDocConstants.PARENT_SYNC_ADVISE_DOC;
import static de.metas.common.util.CoalesceUtil.coalesce;

import java.util.List;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

import de.metas.common.rest_api.SyncAdvise;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

@Value
@ApiModel
public class JsonRequestLocationUpsert
{
	@ApiModelProperty(position = 10)
	List<JsonRequestLocationUpsertItem> requestItems;

	@ApiModelProperty(position = 20, value = "Sync-advise for individual items.\n" + PARENT_SYNC_ADVISE_DOC)
	SyncAdvise syncAdvise;

	@JsonCreator
	@Builder(toBuilder = true)
	public JsonRequestLocationUpsert(
			@Singular @JsonProperty("requestItems") final List<JsonRequestLocationUpsertItem> requestItems,
			@Nullable @JsonProperty("syncAdvise") final SyncAdvise syncAdvise)
	{
		this.requestItems = coalesce(requestItems, ImmutableList.of());
		this.syncAdvise = syncAdvise;
	}
}
