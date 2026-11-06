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
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Schema(name = "Outcome of a single conversion-rate upsert request item. "
		+ "Extends the usual CREATED/UPDATED/NOTHING_DONE outcomes with ERROR, "
		+ "because per-record failures are a first-class outcome of the batch upsert.")
@Value
public class JsonResponseConversionRateUpsertItem
{
	@Schema(description =
			"UPDATED: " + "The rate was updated; note that it's possible that nothing really changed due to the update." + "\n"
			+ "NOTHING_DONE: " + "The rate already existed and the sync-advise indicated to do nothing." + "\n"
			+ "ERROR: " + "This record failed; see the error field for a user-friendly message.")
	public enum SyncOutcome
	{
		CREATED,

		UPDATED,

		NOTHING_DONE,

		ERROR
	}

	@Schema(description = "The ISO source currency code of the respective request item.")
	@NonNull
	String fromCurrencyCode;

	@Schema(description = "The ISO target currency code of the respective request item.")
	@NonNull
	String toCurrencyCode;

	@Schema(description = "The outcome of the upsert for this item.")
	@NonNull
	SyncOutcome syncOutcome;

	@Schema(description = "User-friendly error information; present when syncOutcome is ERROR.")
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
