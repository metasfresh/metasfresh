package de.metas.material.event.purchase;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.util.lang.impl.TableRecordReference;

import static de.metas.material.event.MaterialEventConstants.MD_CANDIDATE_TABLE_NAME;

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
	public static final String TYPE = "PurchaseCandidateAdvisedEvent";

	EventDescriptor eventDescriptor;

	SupplyRequiredDescriptor supplyRequiredDescriptor;

	int productPlanningId;

	boolean directlyCreatePurchaseCandidate;

	int vendorId;

	@Builder
	@JsonCreator
	private PurchaseCandidateAdvisedEvent(
			@JsonProperty("eventDescriptor") @NonNull final EventDescriptor eventDescriptor,
			@JsonProperty("supplyRequiredDescriptor") @NonNull final SupplyRequiredDescriptor supplyRequiredDescriptor,
			@JsonProperty("productPlanningId") final int productPlanningId,
			@JsonProperty("directlyCreatePurchaseCandidate") final boolean directlyCreatePurchaseCandidate,
			@JsonProperty("vendorId") final int vendorId)
	{
		this.eventDescriptor = eventDescriptor;
		this.supplyRequiredDescriptor = supplyRequiredDescriptor;
		this.productPlanningId = Check.assumeGreaterThanZero(productPlanningId, "productPlanningId");
		this.vendorId = vendorId;
		this.directlyCreatePurchaseCandidate = directlyCreatePurchaseCandidate;
	}

	@Override
	public TableRecordReference getSourceTableReference()
	{
		return TableRecordReference.of(MD_CANDIDATE_TABLE_NAME, supplyRequiredDescriptor.getDemandCandidateId());
	}

	@Override
	public String getEventName() {return TYPE;}
}
