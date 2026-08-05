/*
 * #%L
 * de-metas-common-rest_api
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.common.rest_api.v2.currencyconversion;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.util.List;

@ApiModel("Outcome of a conversion-rate batch upsert: the top-level aggregate syncOutcome plus the per-item outcomes.")
@Value
@Builder
@JsonDeserialize(builder = JsonResponseConversionRateUpsert.JsonResponseConversionRateUpsertBuilder.class)
public class JsonResponseConversionRateUpsert
{
	@ApiModelProperty(position = 10, value = "Top-level aggregate outcome over the response items: SUCCESS (none failed), "
			+ "PARTIAL_SUCCESS (some failed, not all), or ERROR (every record failed). Maps to HTTP 200 (SUCCESS/PARTIAL_SUCCESS) or 422 (ERROR).")
	@NonNull
	@JsonProperty("syncOutcome")
	BatchSyncOutcome syncOutcome;

	@Singular
	@ApiModelProperty(position = 20, value = "The per-item outcome for each request item.")
	@JsonProperty("responseItems")
	List<JsonResponseConversionRateUpsertItem> responseItems;
}
