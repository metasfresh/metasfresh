package de.metas.material.event.purchase;

import de.metas.material.event.MaterialEvent;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.MinMaxDescriptor;
import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;

/*
 * #%L
 * metasfresh-material-event
 * %%
 * Copyright (C) 2018 metas GmbH
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

@EqualsAndHashCode(callSuper = false)
@Getter
@ToString
public abstract class PurchaseCandidateEvent implements MaterialEvent
{
	private final EventDescriptor eventDescriptor;
	private final int purchaseCandidateRepoId;
	private final MaterialDescriptor purchaseMaterialDescriptor;
	@Nullable
	private final MinMaxDescriptor minMaxDescriptor;
	private final int vendorId;

	protected PurchaseCandidateEvent(
			@NonNull final MaterialDescriptor purchaseMaterialDescriptor,
			@Nullable final MinMaxDescriptor minMaxDescriptor,
			@NonNull final EventDescriptor eventDescriptor,
			final int purchaseCandidateRepoId,
			final int vendorId)
	{
		this.purchaseMaterialDescriptor = purchaseMaterialDescriptor;
		this.minMaxDescriptor = minMaxDescriptor;
		this.eventDescriptor = eventDescriptor;
		this.purchaseCandidateRepoId = Check.assumeGreaterThanZero(purchaseCandidateRepoId, "purchaseCandidateRepoId");
		this.vendorId = Check.assumeGreaterThanZero(vendorId, "vendorId");
	}
}
