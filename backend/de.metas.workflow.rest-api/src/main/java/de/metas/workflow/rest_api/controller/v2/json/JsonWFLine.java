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
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonWFLine
{
	@JsonProperty("steps")
	@Nullable List<JsonWFStep> steps;

	@JsonProperty("id")
	@Nullable String id;

	@JsonProperty("qtyToReceive")
	@Nullable BigDecimal qtyToReceive;

	@JsonProperty("availableReceivingTargets")
	@Nullable JsonWFManufacturingAvailableReceivingTargets availableReceivingTargets;

	@Builder
	@JsonCreator
	public JsonWFLine(
			@JsonProperty("steps") @Nullable final List<JsonWFStep> steps,
			@JsonProperty("id") @Nullable final String id,
			@JsonProperty("qtyToReceive") final @Nullable BigDecimal qtyToReceive,
			@JsonProperty("availableReceivingTargets") @Nullable final JsonWFManufacturingAvailableReceivingTargets availableReceivingTargets
	)
	{
		this.steps = steps;
		this.id = id;
		this.qtyToReceive = qtyToReceive;
		this.availableReceivingTargets = availableReceivingTargets;
	}
}
