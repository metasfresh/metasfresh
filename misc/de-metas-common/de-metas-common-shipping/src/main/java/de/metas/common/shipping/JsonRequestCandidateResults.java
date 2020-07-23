/*
 * #%L
 * de-metas-common-shipping
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.common.shipping;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.rest_api.JsonError;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;

@Value
public class JsonRequestCandidateResults
{
	String transactionKey;

	List<JsonRequestCandidateResult> items;

	/**
	 * If not null, then this error applies to all included items
	 */
	JsonError error;

	@JsonCreator
	@Builder
	private JsonRequestCandidateResults(
			@JsonProperty("transactionKey") @NonNull final String transactionKey,
			@JsonProperty("error") @Nullable final JsonError error,
			@JsonProperty("items") @NonNull @Singular final List<JsonRequestCandidateResult> items)
	{
		this.transactionKey = transactionKey;
		this.error = error;
		this.items = items;
	}

	public JsonRequestCandidateResults withError(@NonNull final JsonError error)
	{
		final JsonRequestCandidateResultsBuilder result = JsonRequestCandidateResults
				.builder()
				.transactionKey(transactionKey)
				.error(error);
		for (final JsonRequestCandidateResult item : items)
		{
			result.item(item.withError());
		}
		return result.build();

	}
}
