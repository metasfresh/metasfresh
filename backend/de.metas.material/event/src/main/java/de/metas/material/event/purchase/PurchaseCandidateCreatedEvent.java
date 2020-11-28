package de.metas.material.event.purchase;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

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

@EqualsAndHashCode(callSuper = true)
@Getter
@ToString
public class PurchaseCandidateCreatedEvent extends PurchaseCandidateEvent
{
	public static PurchaseCandidateCreatedEvent cast(@NonNull final PurchaseCandidateEvent event)
	{
		return (PurchaseCandidateCreatedEvent)event;
	}

	public static final String TYPE = "PurchaseCandidateCreatedEvent";

	/**
	 * If the purchase candidate is created according a {@link PurchaseCandidateRequestedEvent}, then this is the ID of the supply candidate the new purchase candidate belongs to;
	 * Otherwise its value is <= 1.
	 */
	private final int supplyCandidateRepoId;

	private final SupplyRequiredDescriptor supplyRequiredDescriptor;

	@JsonCreator
	@Builder
	public PurchaseCandidateCreatedEvent(
			@JsonProperty("eventDescriptor") @NonNull final EventDescriptor eventDescriptor,
			@JsonProperty("purchaseCandidateRepoId") final int purchaseCandidateRepoId,
			@JsonProperty("purchaseMaterialDescriptor") @NonNull final MaterialDescriptor purchaseMaterialDescriptor,
			@JsonProperty("supplyRequiredDescriptor") @Nullable final SupplyRequiredDescriptor supplyRequiredDescriptor,
			@JsonProperty("supplyCandidateRepoId") final int supplyCandidateRepoId,
			@JsonProperty("vendorId") final int vendorId)
	{
		super(purchaseMaterialDescriptor,
				null,
				eventDescriptor,
				purchaseCandidateRepoId,
				vendorId);
		this.supplyCandidateRepoId = supplyCandidateRepoId;
		this.supplyRequiredDescriptor = supplyRequiredDescriptor;
	}
}
