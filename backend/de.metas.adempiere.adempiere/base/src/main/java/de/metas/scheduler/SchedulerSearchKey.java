/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.scheduler;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.metas.process.AdProcessId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@Value
@JsonDeserialize(builder = SchedulerSearchKey.SchedulerSearchKeyBuilder.class)
public class SchedulerSearchKey
{
	@Nullable
	AdProcessId adProcessId;
	@Nullable
	AdSchedulerId adSchedulerId;

	@NonNull
	public static SchedulerSearchKey of(@NonNull final AdProcessId adProcessId)
	{
		return SchedulerSearchKey.builder()
				.adProcessId(adProcessId)
				.build();
	}

	@NonNull
	public static SchedulerSearchKey of(@NonNull final AdSchedulerId adSchedulerId)
	{
		return SchedulerSearchKey.builder()
				.adSchedulerId(adSchedulerId)
				.build();
	}

	@JsonCreator
	@Builder
	private SchedulerSearchKey(
			@JsonProperty("adProcessId") @Nullable final AdProcessId adProcessId,
			@JsonProperty("adSchedulerId") @Nullable final AdSchedulerId adSchedulerId)
	{
		if (adProcessId == null && adSchedulerId == null)
		{
			throw new AdempiereException("One of adProcessId or adSchedulerId must be provided!");
		}
		else if (adProcessId != null && adSchedulerId != null)
		{
			throw new AdempiereException("Only one of adProcessId or adSchedulerId must be provided!")
					.appendParametersToMessage()
					.setParameter("adSchedulerId", adSchedulerId)
					.setParameter("adProcessId", adProcessId);
		}

		this.adProcessId = adProcessId;
		this.adSchedulerId = adSchedulerId;
	}
}