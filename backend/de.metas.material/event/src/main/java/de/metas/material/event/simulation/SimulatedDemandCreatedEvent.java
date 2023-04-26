/*
 * #%L
 * metasfresh-material-event
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

package de.metas.material.event.simulation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.commons.DocumentLineDescriptor;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
public class SimulatedDemandCreatedEvent implements MaterialEvent
{
	public static final String TYPE = "SimulatedDemandCreatedEvent";

	@NonNull
	EventDescriptor eventDescriptor;

	@NonNull MaterialDescriptor materialDescriptor;

	@NonNull
	DocumentLineDescriptor documentLineDescriptor;

	@JsonCreator
	@Builder
	public SimulatedDemandCreatedEvent(
			@JsonProperty("eventDescriptor") @NonNull final EventDescriptor eventDescriptor,
			@JsonProperty("materialDescriptor") @NonNull final MaterialDescriptor materialDescriptor,
			@JsonProperty("documentLineDescriptor") @NonNull final DocumentLineDescriptor documentLineDescriptor)
	{
		this.eventDescriptor = eventDescriptor;
		this.materialDescriptor = materialDescriptor;
		this.documentLineDescriptor = documentLineDescriptor;
	}
}