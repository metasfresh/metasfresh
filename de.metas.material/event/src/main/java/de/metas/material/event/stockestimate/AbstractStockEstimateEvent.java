package de.metas.material.event.stockestimate;

import java.math.BigDecimal;
import java.util.Date;

import de.metas.material.event.MaterialEvent;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

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

	private final ProductDescriptor productDescriptor;

	private final Date date;

	private final int plantId;


	private final BigDecimal quantity;

	public AbstractStockEstimateEvent(
			@NonNull final EventDescriptor eventDescriptor,
			@NonNull final ProductDescriptor productDescriptor,
			@NonNull final Date date,
			final int plantId,
			@NonNull final BigDecimal quantity)
	{
		this.eventDescriptor = eventDescriptor;
		this.productDescriptor = productDescriptor;
		this.date = date;
		this.plantId = plantId;
		this.quantity = quantity;
	}

	public abstract BigDecimal getQuantityDelta();
}
