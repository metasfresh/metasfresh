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
import de.pentabyte.springfox.ApiEnum;
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
	/** An item counts as failed iff its per-item outcome is {@code ERROR} ({@code NOTHING_DONE} counts as applied). */
	public enum BatchSyncOutcome
	{
		@ApiEnum("No record failed; the batch fully applied (also the degenerate empty batch).")
		SUCCESS,

		@ApiEnum("Some records applied and at least one failed; the failed records carry a per-item ERROR outcome.")
		PARTIAL_SUCCESS,

		@ApiEnum("No record was applied; every record failed (the response reports the per-record outcomes).")
		ERROR
	}

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
