/*
 * #%L
 * metasfresh-material-event
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

package de.metas.material.event.pporder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PPOrderCandidateRequestedEvent extends AbstractPPOrderCandidateEvent
{
	public static final String TYPE = "PPOrderCandidateRequestedEvent";

	@Getter
	boolean directlyCreatePPOrder;

	@JsonCreator
	@Builder
	private PPOrderCandidateRequestedEvent(
			@JsonProperty("eventDescriptor") @NonNull final EventDescriptor eventDescriptor,
			@JsonProperty("ppOrderCandidate") @NonNull final PPOrderCandidate ppOrderCandidate,
			@JsonProperty("supplyRequiredDescriptor") @Nullable final SupplyRequiredDescriptor supplyRequiredDescriptor,
			@JsonProperty("directlyCreatePPOrder") final boolean directlyCreatePPOrder)
	{
		super(eventDescriptor, ppOrderCandidate, supplyRequiredDescriptor);

		this.directlyCreatePPOrder = directlyCreatePPOrder;
	}
}
