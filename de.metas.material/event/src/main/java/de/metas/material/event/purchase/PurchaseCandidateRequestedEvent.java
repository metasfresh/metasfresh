package de.metas.material.event.purchase;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.material.event.MaterialEvent;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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

@Value
public class PurchaseCandidateRequestedEvent implements MaterialEvent
{
	public static final String TYPE = "PurchaseCandidateRequestedEvent";

	EventDescriptor eventDescriptor;

	/** If the purchase candidate is created according to this event, the respective {@link PurchaseCandidateCreatedEvent} will have the same suplyCandidateRepoId. */
	int supplyCandidateRepoId;

	MaterialDescriptor purchaseMaterialDescriptor;

	/** can specify the sales order line (if there is any) that triggered the material-dispo to request this purchase candidate. */
	int salesOrderLineRepoId;

	/** analog to {@link #salesOrderLineRepoId}. */
	int salesOrderRepoId;

	@Builder
	@JsonCreator
	public PurchaseCandidateRequestedEvent(
			@JsonProperty("supplyCandidateRepoId") final int supplyCandidateRepoId,
			@JsonProperty("purchaseMaterialDescriptor") @NonNull final MaterialDescriptor purchaseMaterialDescriptor,
			@JsonProperty("salesOrderLineRepoId") @Nullable final int salesOrderLineRepoId,
			@JsonProperty("salesOrderRepoId") @Nullable final int salesOrderRepoId,
			@JsonProperty("eventDescriptor") @NonNull final EventDescriptor eventDescriptor)
	{
		this.supplyCandidateRepoId = Check.assumeGreaterThanZero(supplyCandidateRepoId, "supplyCandidateRepoId");
		this.purchaseMaterialDescriptor = purchaseMaterialDescriptor;
		this.salesOrderLineRepoId = salesOrderLineRepoId;
		this.salesOrderRepoId = salesOrderRepoId;
		this.eventDescriptor = eventDescriptor;
	}
}
