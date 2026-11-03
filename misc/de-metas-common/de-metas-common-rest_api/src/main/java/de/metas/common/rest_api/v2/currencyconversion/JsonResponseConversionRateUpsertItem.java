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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.rest_api.v2.JsonErrorItem;
import de.pentabyte.springfox.ApiEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@ApiModel("Outcome of a single conversion-rate upsert request item. "
		+ "Extends the usual CREATED/UPDATED/NOTHING_DONE outcomes with ERROR, "
		+ "because per-record failures are a first-class outcome of the batch upsert.")
@Value
public class JsonResponseConversionRateUpsertItem
{
	public enum SyncOutcome
	{
		CREATED,

		@ApiEnum("The rate was updated; note that it's possible that nothing really changed due to the update.")
		UPDATED,

		@ApiEnum("The rate already existed and the sync-advise indicated to do nothing.")
		NOTHING_DONE,

		@ApiEnum("This record failed; see the error field for a user-friendly message.")
		ERROR
	}

	@ApiModelProperty(position = 10, value = "The ISO source currency code of the respective request item.")
	@NonNull
	String fromCurrencyCode;

	@ApiModelProperty(position = 20, value = "The ISO target currency code of the respective request item.")
	@NonNull
	String toCurrencyCode;

	@ApiModelProperty(position = 30, value = "The outcome of the upsert for this item.")
	@NonNull
	SyncOutcome syncOutcome;

	@ApiModelProperty(position = 40, value = "User-friendly error information; present when syncOutcome is ERROR.")
	@Nullable
	JsonErrorItem error;

	@Builder
	@JsonCreator
	public JsonResponseConversionRateUpsertItem(
			@NonNull @JsonProperty("fromCurrencyCode") final String fromCurrencyCode,
			@NonNull @JsonProperty("toCurrencyCode") final String toCurrencyCode,
			@NonNull @JsonProperty("syncOutcome") final SyncOutcome syncOutcome,
			@Nullable @JsonProperty("error") final JsonErrorItem error)
	{
		this.fromCurrencyCode = fromCurrencyCode;
		this.toCurrencyCode = toCurrencyCode;
		this.syncOutcome = syncOutcome;
		this.error = error;
	}
}
