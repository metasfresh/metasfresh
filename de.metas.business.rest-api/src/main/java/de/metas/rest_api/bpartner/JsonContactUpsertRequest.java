package de.metas.rest_api.bpartner;

import static de.metas.util.lang.CoalesceUtil.coalesce;

import java.util.List;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

import de.metas.rest_api.SyncAdvise;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * de.metas.business.rest-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Value
@ApiModel
public class JsonContactUpsertRequest
{
	List<JsonContactUpsertRequestItem> requestItems;

	SyncAdvise.IfExists ifExists;

	@JsonCreator
	@Builder
	public JsonContactUpsertRequest(
			@Singular @JsonProperty("requestItems") final List<JsonContactUpsertRequestItem> requestItems,
			@Nullable @JsonProperty("ifExists") final SyncAdvise.IfExists ifExists)
	{
		this.requestItems = coalesce(requestItems, ImmutableList.of());
		this.ifExists = coalesce(ifExists, SyncAdvise.IfExists.UPDATE_MERGE);
	}
}
