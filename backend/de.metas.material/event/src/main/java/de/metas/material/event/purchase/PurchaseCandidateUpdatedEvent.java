package de.metas.material.event.purchase;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.MinMaxDescriptor;
import lombok.Builder;
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

@EqualsAndHashCode(callSuper = true)
@Getter
@ToString
public class PurchaseCandidateUpdatedEvent extends PurchaseCandidateEvent
{
	public static final String TYPE = "PurchaseCandidateUpdatedEvent";

	@JsonCreator
	@Builder
	private PurchaseCandidateUpdatedEvent(
			@JsonProperty("eventDescriptor") @NonNull final EventDescriptor eventDescriptor,
			@JsonProperty("purchaseCandidateRepoId") final int purchaseCandidateRepoId,
			@JsonProperty("purchaseMaterialDescriptor") @NonNull final MaterialDescriptor purchaseMaterialDescriptor,
			@JsonProperty("minMaxDescriptor") @Nullable final MinMaxDescriptor minMaxDescriptor,
			@JsonProperty("vendorId") final int vendorId)
	{
		super(purchaseMaterialDescriptor,
				minMaxDescriptor,
				eventDescriptor,
				purchaseCandidateRepoId,
				vendorId);
	}
}
