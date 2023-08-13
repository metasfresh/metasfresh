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
import lombok.Value;

/**
 * @deprecated please consider using {@link JsonOLCandProcessRequest} instead.
 */
@Deprecated
@Value
@Builder
@JsonDeserialize(builder = JsonOLCandClearRequest.JsonOLCandClearRequestBuilder.class)
public class JsonOLCandClearRequest
{
	@ApiModelProperty(required = true, value = "This translates to 'C_OLCand.externalLineId'.")
	@JsonProperty("externalHeaderId")
	String externalHeaderId;

	@ApiModelProperty(required = true, value = "This translates to 'AD_InputDataSource.internalName' of the data source the candidates in question were added with.")
	@JsonProperty("inputDataSourceName")
	String inputDataSourceName;
}
