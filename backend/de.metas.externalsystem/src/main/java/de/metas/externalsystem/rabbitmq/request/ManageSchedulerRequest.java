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

package de.metas.externalsystem.rabbitmq.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.metas.process.AdProcessId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@JsonDeserialize(builder = ManageSchedulerRequest.ManageSchedulerRequestBuilder.class)
public class ManageSchedulerRequest
{
	@NonNull AdProcessId adProcessId;
	@NonNull Boolean enable;

	@JsonCreator
	@Builder
	private ManageSchedulerRequest(
			@JsonProperty("adProcessId") @NonNull final AdProcessId adProcessId,
			@JsonProperty("enable") @NonNull final Boolean enable)
	{
		this.adProcessId = adProcessId;
		this.enable = enable;
	}
}
