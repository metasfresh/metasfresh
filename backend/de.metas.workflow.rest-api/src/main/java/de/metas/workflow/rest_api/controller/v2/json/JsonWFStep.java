/*
 * #%L
 * de.metas.workflow.rest-api
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

package de.metas.workflow.rest_api.controller.v2.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonWFStep
{
	@JsonProperty("pickingStepId")
	@Nullable String pickingStepId;
	@JsonProperty("id")
	@Nullable String id;
	@JsonProperty("mainPickFrom")
	@Nullable
	JsonWFPickFrom mainPickFrom;

	@JsonProperty("pickFromHU")
	@Nullable
	JsonWFPickFrom pickFromHU;

	@JsonProperty("huQRCode")
	@Nullable JsonWFHQRCode huQRCode;

	@JsonProperty("qtyToIssue")
	@Nullable BigDecimal qtyToIssue;

	@Builder
	@JsonCreator
	public JsonWFStep(
			@JsonProperty("pickingStepId") @Nullable final String pickingStepId,
			@JsonProperty("id") @Nullable final String id,
			@JsonProperty("mainPickFrom") @Nullable final JsonWFPickFrom mainPickFrom,
			@JsonProperty("pickFromHU") @Nullable final JsonWFPickFrom pickFromHU,
			@JsonProperty("huQRCode") @Nullable final JsonWFHQRCode huQRCode,
			@JsonProperty("qtyToIssue") @Nullable final BigDecimal qtyToIssue
	)
	{
		this.pickingStepId = pickingStepId;
		this.id = id;
		this.mainPickFrom = mainPickFrom;
		this.pickFromHU = pickFromHU;
		this.huQRCode = huQRCode;
		this.qtyToIssue = qtyToIssue;
	}
}
