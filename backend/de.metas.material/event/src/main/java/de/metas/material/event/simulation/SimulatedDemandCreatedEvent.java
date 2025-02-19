/*
 * #%L
 * metasfresh-material-event
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.material.event.simulation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.adempiere.model.I_C_Order;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.commons.DocumentLineDescriptor;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.order.OrderId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;

@Value
public class SimulatedDemandCreatedEvent implements MaterialEvent
{
	public static final String TYPE = "SimulatedDemandCreatedEvent";

	@NonNull
	EventDescriptor eventDescriptor;

	@NonNull MaterialDescriptor materialDescriptor;

	@NonNull
	DocumentLineDescriptor documentLineDescriptor;

	@NonNull
	OrderId orderId;

	@JsonCreator
	@Builder
	public SimulatedDemandCreatedEvent(
			@JsonProperty("eventDescriptor") @NonNull final EventDescriptor eventDescriptor,
			@JsonProperty("materialDescriptor") @NonNull final MaterialDescriptor materialDescriptor,
			@JsonProperty("documentLineDescriptor") @NonNull final DocumentLineDescriptor documentLineDescriptor,
			@JsonProperty("orderId") @NonNull final OrderId orderId)
	{
		this.eventDescriptor = eventDescriptor;
		this.materialDescriptor = materialDescriptor;
		this.documentLineDescriptor = documentLineDescriptor;
		this.orderId = orderId;
	}

	@Nullable
	@Override
	public TableRecordReference getSourceTableReference()
	{
		return TableRecordReference.ofNullable(I_C_Order.Table_Name, orderId.getRepoId());
	}

	@Override
	public String getEventName() {return TYPE;}
}