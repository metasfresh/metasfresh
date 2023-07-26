/*
 * #%L
 * de-metas-common-ordercandidates
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

package de.metas.common.ordercandidates.v2.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@JsonDeserialize(builder = JsonOLCandProcessRequest.JsonOLCandProcessRequestBuilder.class)
public class JsonOLCandProcessRequest
{
	@ApiModelProperty(required = true, value = "This translates to 'C_OLCand.externalLineId'.")
	String externalHeaderId;

	@ApiModelProperty(required = true, value = "This translates to 'AD_InputDataSource.internalName' of the data source the candidates in question were added with.")
	String inputDataSourceName;

	Boolean ship;

	Boolean invoice;

	Boolean closeOrder;

	@Builder
	JsonOLCandProcessRequest(
			@JsonProperty("externalHeaderId") @NonNull final String externalHeaderId,
			@JsonProperty("inputDataSourceName") @NonNull final String inputDataSourceName,
			@JsonProperty("ship") @NonNull final Boolean ship,
			@JsonProperty("invoice") @NonNull final Boolean invoice,
			@JsonProperty("closeOrder") @Nullable final Boolean closeOrder)
	{
		this.externalHeaderId = externalHeaderId;
		this.inputDataSourceName = inputDataSourceName;
		this.ship = ship;
		this.invoice = invoice;
		this.closeOrder = closeOrder;
	}
}
