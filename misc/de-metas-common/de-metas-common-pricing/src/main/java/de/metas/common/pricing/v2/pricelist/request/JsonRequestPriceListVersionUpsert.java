/*
 * #%L
 * de-metas-common-pricing
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

package de.metas.common.pricing.v2.pricelist.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import de.metas.common.rest_api.v2.SwaggerDocConstants;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.util.CoalesceUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;

@Value
@ApiModel
public class JsonRequestPriceListVersionUpsert
{
	@ApiModelProperty(position = 10, required = true)
	@JsonProperty("requestItems")
	List<JsonRequestPriceListVersionUpsertItem> requestItems;

	@ApiModelProperty(position = 20, value = "Default sync-advise that can be overridden by individual items\n" + SwaggerDocConstants.READ_ONLY_SYNC_ADVISE_DOC)
	@JsonProperty("syncAdvise")
	SyncAdvise syncAdvise;

	@JsonCreator
	@Builder
	public JsonRequestPriceListVersionUpsert(
			@Singular @JsonProperty("requestItems") final List<JsonRequestPriceListVersionUpsertItem> requestItems,
			@Nullable @JsonProperty("syncAdvise") final SyncAdvise syncAdvise)
	{
		this.requestItems = CoalesceUtil.coalesce(requestItems, ImmutableList.of());
		this.syncAdvise = CoalesceUtil.coalesce(syncAdvise, SyncAdvise.READ_ONLY);
	}
}
