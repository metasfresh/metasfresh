/*
 * #%L
 * de.metas.util.web
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

package de.metas.common.rest_api.v2;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Value;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Value
@Builder
@JsonDeserialize(builder = JsonApiResponse.JsonApiResponseBuilder.class)
public class JsonApiResponse
{
	@ApiModelProperty("The `API_Request_Audit_ID` of the request created by the invoker. Might be `null` if metasfresh was configured to not create an audit log at all.")
	@JsonProperty("requestId")
	@JsonInclude(NON_NULL)
	JsonMetasfreshId requestId;

	@ApiModelProperty("The response body as returned by the actual endpoint (e.g. BPartnerRestController).\n" +
			"This property is set only if the matched `API_Audit_Config` record has `IsInvokerWaitsForResult = 'Y'`")
	@JsonProperty("endpointResponse")
	@JsonInclude(NON_NULL)
	Object endpointResponse;
}
