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

package de.metas.async.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.metas.async.QueueWorkPackageId;
import de.metas.event.impl.IQueueWorkPackageIdProvider;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

/**
 * Works in conjunction with {@link WorkpackagesProcessedWaiter}.
 */
@Value
@Builder
@JsonDeserialize(builder = WorkpackageProcessedEvent.WorkpackageProcessedEventBuilder.class)
public class WorkpackageProcessedEvent implements IQueueWorkPackageIdProvider
{
	public enum Status
	{
		DONE, SKIPPED, ERROR
	}

	@JsonProperty("status")
	Status status;

	@JsonProperty("workPackageId")
	QueueWorkPackageId workPackageId;

	@JsonProperty("correlationId")
	UUID correlationId;

	@JsonIgnore
	public QueueWorkPackageId getQueueWorkPackageId()
	{
		return workPackageId;
	}
}
