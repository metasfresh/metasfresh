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

import de.metas.material.event.MaterialEvent;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;

@EqualsAndHashCode(callSuper = false)
@Getter
@ToString
public abstract class AbstractPPOrderCandidateEvent implements MaterialEvent
{
	private final EventDescriptor eventDescriptor;
	private final PPOrderCandidate ppOrderCandidate;

	private final SupplyRequiredDescriptor supplyRequiredDescriptor;

	public AbstractPPOrderCandidateEvent(
			@NonNull final EventDescriptor eventDescriptor,
			@NonNull final PPOrderCandidate ppOrderCandidate,
			@Nullable final SupplyRequiredDescriptor supplyRequiredDescriptor)
	{
		this.eventDescriptor = eventDescriptor;
		this.ppOrderCandidate = ppOrderCandidate;
		this.supplyRequiredDescriptor = supplyRequiredDescriptor;
	}
}