/*
 * #%L
 * metasfresh-material-event
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.material.event.pporder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.material.event.commons.EventDescriptor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PPOrderCandidateDeletedEvent extends AbstractPPOrderCandidateEvent
{
	public static final String TYPE = "PPOrderCandidateDeletedEvent";

	@JsonCreator
	@Builder
	public PPOrderCandidateDeletedEvent(
			@JsonProperty("eventDescriptor") @NonNull final EventDescriptor eventDescriptor,
			@JsonProperty("ppOrderCandidate") @NonNull final PPOrderCandidate ppOrderCandidate)
	{
		super(eventDescriptor, ppOrderCandidate, null);
	}

	public static PPOrderCandidateDeletedEvent cast(@NonNull final AbstractPPOrderCandidateEvent ppOrderCandidateEvent)
	{
		return (PPOrderCandidateDeletedEvent)ppOrderCandidateEvent;
	}
}
