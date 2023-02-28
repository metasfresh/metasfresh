package de.metas.material.event.purchase;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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

@Value
public class PurchaseCandidateRequestedEvent implements MaterialEvent
{
	public static final String TYPE = "PurchaseCandidateRequestedEvent";

	EventDescriptor eventDescriptor;

	/**
	 * If the purchase candidate is created according to this event, the respective {@link PurchaseCandidateCreatedEvent} will have the same suplyCandidateRepoId.
	 */
	int supplyCandidateRepoId;

	MaterialDescriptor purchaseMaterialDescriptor;

	/**
	 * can specify the sales order line (if there is any) that triggered the material-dispo to request this purchase candidate.
	 */
	int salesOrderLineRepoId;

	/**
	 * analog to {@link #salesOrderLineRepoId}.
	 */
	int salesOrderRepoId;

	int forecastId;
	int forecastLineId;

	// Dimensions
	int activityId;
	int campaignId;
	int projectId;
	int userElementId1;
	int userElementId2;
	String userElementString1;
	String userElementString2;
	String userElementString3;
	String userElementString4;
	String userElementString5;
	String userElementString6;
	String userElementString7;

	boolean simulated;

	@Builder
	@JsonCreator
	public PurchaseCandidateRequestedEvent(
			@JsonProperty("supplyCandidateRepoId") final int supplyCandidateRepoId,
			@JsonProperty("purchaseMaterialDescriptor") @NonNull final MaterialDescriptor purchaseMaterialDescriptor,
			@JsonProperty("salesOrderLineRepoId") final int salesOrderLineRepoId,
			@JsonProperty("salesOrderRepoId") final int salesOrderRepoId,
			@JsonProperty("forecastId") final int forecastId,
			@JsonProperty("forecastLineId") final int forecastLineId,
			@JsonProperty("eventDescriptor") @NonNull final EventDescriptor eventDescriptor,
			@JsonProperty("activityId") final int activityId,
			@JsonProperty("campaignId") final int campaignId,
			@JsonProperty("projectId") final int projectId,
			@JsonProperty("userElementId1") final int userElementId1,
			@JsonProperty("userElementId2") final int userElementId2,
			@JsonProperty("userElementString1") @Nullable final String userElementString1,
			@JsonProperty("userElementString2") @Nullable final String userElementString2,
			@JsonProperty("userElementString3") @Nullable final String userElementString3,
			@JsonProperty("userElementString4") @Nullable final String userElementString4,
			@JsonProperty("userElementString5") @Nullable final String userElementString5,
			@JsonProperty("userElementString6") @Nullable final String userElementString6,
			@JsonProperty("userElementString7") @Nullable final String userElementString7,
			@JsonProperty("simulated") final boolean simulated)
	{
		this.supplyCandidateRepoId = Check.assumeGreaterThanZero(supplyCandidateRepoId, "supplyCandidateRepoId");
		this.purchaseMaterialDescriptor = purchaseMaterialDescriptor;
		this.salesOrderLineRepoId = salesOrderLineRepoId;
		this.salesOrderRepoId = salesOrderRepoId;
		this.eventDescriptor = eventDescriptor;

		this.forecastId = forecastId;
		this.forecastLineId = forecastLineId;

		this.activityId = activityId;
		this.campaignId = campaignId;
		this.projectId = projectId;
		this.userElementId1 = userElementId1;
		this.userElementId2 = userElementId2;
		this.userElementString1 = userElementString1;
		this.userElementString2 = userElementString2;
		this.userElementString3 = userElementString3;
		this.userElementString4 = userElementString4;
		this.userElementString5 = userElementString5;
		this.userElementString6 = userElementString6;
		this.userElementString7 = userElementString7;
		this.simulated = simulated;
	}
}
