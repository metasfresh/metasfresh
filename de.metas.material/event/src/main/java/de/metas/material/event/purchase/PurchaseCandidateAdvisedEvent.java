package de.metas.material.event.purchase;

import org.adempiere.util.Check;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.material.event.MaterialEvent;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
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
public class PurchaseCandidateAdvisedEvent implements MaterialEvent
{
	public static final String TYPE = "PurchaseDemandAdvisedEvent";

	private EventDescriptor eventDescriptor;

	private SupplyRequiredDescriptor supplyRequiredDescriptor;

	int productPlanningId;

	private boolean directlyCreatePurchaseCandidate;

	private MaterialDescriptor purchaseMaterialDescriptor;

	@Builder
	@JsonCreator
	private PurchaseCandidateAdvisedEvent(
			@JsonProperty("eventDescriptor") @NonNull final EventDescriptor eventDescriptor,
			@JsonProperty("supplyRequiredDescriptor") @NonNull final SupplyRequiredDescriptor supplyRequiredDescriptor,
			@JsonProperty("productPlanningId") final int productPlanningId,
			@JsonProperty("purchaseMaterialDescriptor") @NonNull final MaterialDescriptor purchaseMaterialDescriptor,
			@JsonProperty("directlyCreatePurchaseCandidate") final boolean directlyCreatePurchaseCandidate)
	{
		this.eventDescriptor = eventDescriptor;
		this.supplyRequiredDescriptor = supplyRequiredDescriptor;
		this.productPlanningId = Check.assumeGreaterThanZero(productPlanningId, "productPlanningId");
		this.purchaseMaterialDescriptor = purchaseMaterialDescriptor;
		this.directlyCreatePurchaseCandidate = directlyCreatePurchaseCandidate;
	}
}
