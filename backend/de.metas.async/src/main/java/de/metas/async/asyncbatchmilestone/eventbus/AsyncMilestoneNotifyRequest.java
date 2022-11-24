/*
 * #%L
 * de.metas.async
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

package de.metas.async.asyncbatchmilestone.eventbus;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.service.ClientId;

@Value
@JsonDeserialize(builder = AsyncMilestoneNotifyRequest.AsyncMilestoneNotifyRequestBuilder.class)
public class AsyncMilestoneNotifyRequest
{
	@JsonProperty("clientId")
	@NonNull
	ClientId clientId;

	@JsonProperty("milestoneId")
	@NonNull
	Integer milestoneId;

	@JsonProperty("asyncBatchId")
	@NonNull
	Integer asyncBatchId;

	@JsonProperty("success")
	@NonNull
	Boolean success;

	@JsonCreator
	@Builder
	public AsyncMilestoneNotifyRequest(
			@JsonProperty("clientId") @NonNull final ClientId clientId,
			@JsonProperty("milestoneId") @NonNull final Integer milestoneId,
			@JsonProperty("asyncBatchId") @NonNull final Integer asyncBatchId,
			@JsonProperty("success") @NonNull final Boolean success)
	{
		this.clientId = clientId;
		this.milestoneId = milestoneId;
		this.asyncBatchId = asyncBatchId;
		this.success = success;
	}
}
