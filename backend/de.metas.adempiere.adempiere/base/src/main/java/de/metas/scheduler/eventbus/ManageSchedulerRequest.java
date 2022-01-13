/*
 * #%L
 * de.metas.externalsystem
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

package de.metas.scheduler.eventbus;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.metas.scheduler.SchedulerSearchKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.service.ClientId;

import javax.annotation.Nullable;

@Value
@JsonDeserialize(builder = ManageSchedulerRequest.ManageSchedulerRequestBuilder.class)
public class ManageSchedulerRequest
{
	@NonNull SchedulerSearchKey schedulerSearchKey;
	@NonNull Advice schedulerAdvice;
	@NonNull ClientId clientId;
	@Nullable Advice supervisorAdvice;

	@JsonCreator
	@Builder
	public ManageSchedulerRequest(
			@JsonProperty("schedulerSearchKey") @NonNull final SchedulerSearchKey schedulerSearchKey,
			@JsonProperty("schedulerAdvice") @NonNull final Advice schedulerAdvice,
			@JsonProperty("clientId") @NonNull final ClientId clientId,
			@JsonProperty("supervisorAdvice") @Nullable final Advice supervisorAdvice)
	{
		this.schedulerSearchKey = schedulerSearchKey;
		this.schedulerAdvice = schedulerAdvice;
		this.clientId = clientId;
		this.supervisorAdvice = supervisorAdvice;
	}

	@AllArgsConstructor
	@Getter
	public enum Advice
	{
		ENABLE,
		DISABLE,
		RESTART
	}
}
