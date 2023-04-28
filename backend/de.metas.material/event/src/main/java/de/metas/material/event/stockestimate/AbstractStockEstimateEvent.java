package de.metas.material.event.stockestimate;

import de.metas.material.event.MaterialEvent;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;

/*
 * #%L
 * metasfresh-material-event
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@EqualsAndHashCode(callSuper = false)
@Getter
@ToString
public abstract class AbstractStockEstimateEvent implements MaterialEvent
{
	private final EventDescriptor eventDescriptor;

	private final MaterialDescriptor materialDescriptor;

	private final Instant date;

	private final int plantId;

	private final int freshQtyOnHandId;

	private final int freshQtyOnHandLineId;

	private final Integer qtyStockEstimateSeqNo;
	
	private final Instant eventDate;

	public AbstractStockEstimateEvent(
			@NonNull final EventDescriptor eventDescriptor,
			@NonNull final MaterialDescriptor materialDescriptor,
			@NonNull final Instant date,
			final int plantId,
			final int freshQtyOnHandId,
			final int freshQtyOnHandLineId,
			@Nullable final Integer qtyStockEstimateSeqNo, 
			@NonNull final Instant eventDate
	)
	{
		this.eventDescriptor = eventDescriptor;
		this.materialDescriptor = materialDescriptor;
		this.date = date;
		this.plantId = plantId;
		this.freshQtyOnHandId = freshQtyOnHandId;
		this.freshQtyOnHandLineId = freshQtyOnHandLineId;
		this.qtyStockEstimateSeqNo = qtyStockEstimateSeqNo;
		this.eventDate = eventDate;
	}

	public abstract BigDecimal getQuantityDelta();
}
