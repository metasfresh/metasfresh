/*
 * #%L
 * de-metas-common-rest_api
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

package de.metas.common.rest_api.pricing.pricelist;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.rest_api.SyncAdvise;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

import static de.metas.common.rest_api.SwaggerDocConstants.READ_ONLY_SYNC_ADVISE_DOC;
import static de.metas.common.util.CoalesceUtil.coalesce;

@Value
@ApiModel
public class JsonRequestPriceListVersionUpsert
{
	@ApiModelProperty(position = 10, dataType = "java.lang.String")
	@JsonProperty("priceListVersionIdentifier")
	String priceListVersionIdentifier;

	@ApiModelProperty(position = 20, value = "The price list version json request object")
	@JsonProperty("jsonUpsertPriceListVersionRequest")
	JsonRequestPriceListVersion jsonRequestPriceListVersion;

	@ApiModelProperty(position = 30, value = "Default sync-advise that can be overridden by individual items\n" + READ_ONLY_SYNC_ADVISE_DOC)
	@JsonProperty("syncAdvise")
	SyncAdvise syncAdvise;

	@JsonCreator
	@Builder
	public JsonRequestPriceListVersionUpsert(
			@NonNull @JsonProperty("priceListVersionIdentifier") final String priceListVersionIdentifier,
			@NonNull @JsonProperty("jsonUpsertPriceListVersionRequest") final JsonRequestPriceListVersion jsonRequestPriceListVersion,
			@Nullable @JsonProperty("syncAdvise") final SyncAdvise syncAdvise)
	{
		this.priceListVersionIdentifier = priceListVersionIdentifier;
		this.jsonRequestPriceListVersion = jsonRequestPriceListVersion;
		this.syncAdvise = coalesce(syncAdvise, SyncAdvise.READ_ONLY);
	}
}
