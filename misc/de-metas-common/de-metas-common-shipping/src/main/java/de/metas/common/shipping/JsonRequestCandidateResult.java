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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.rest_api.JsonError;
import de.metas.common.rest_api.JsonMetasfreshId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

import static de.metas.common.shipping.Outcome.ERROR;

@Value
@Builder
public class JsonRequestCandidateResult
{
	JsonMetasfreshId scheduleId;

	Outcome outcome;

	@JsonInclude(Include.NON_NULL)
	JsonError error;

	@JsonCreator
	private JsonRequestCandidateResult(
			@JsonProperty("scheduleId") @NonNull final JsonMetasfreshId scheduleId,
			@JsonProperty("outcome") @NonNull final Outcome outcome,
			@JsonProperty("error") @Nullable final JsonError error)
	{
		this.scheduleId = scheduleId;
		this.outcome = outcome;
		this.error = error;
	}

	public JsonRequestCandidateResult withError()
	{
		return JsonRequestCandidateResult.builder()
				.scheduleId(scheduleId)
				.outcome(ERROR)
				.build();
	}
}
