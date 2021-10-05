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

package de.metas.material.event.ddorder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.material.event.commons.EventDescriptor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.warehouse.WarehouseId;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DDOrderDeletedEvent extends AbstractDDOrderEvent
{
	public static final String TYPE = "DDOrderDeletedEvent";

	@JsonCreator
	@Builder
	public DDOrderDeletedEvent(
			@JsonProperty("eventDescriptor") @NonNull final EventDescriptor eventDescriptor,
			@JsonProperty("ddOrder") @NonNull final DDOrder ddOrder,
			@JsonProperty("fromWarehouseId") @NonNull final WarehouseId fromWarehouseId,
			@JsonProperty("toWarehouseId") @NonNull final WarehouseId toWarehouseId)
	{
		super(
				eventDescriptor,
				ddOrder,
				fromWarehouseId,
				toWarehouseId,
				null);
	}
}
