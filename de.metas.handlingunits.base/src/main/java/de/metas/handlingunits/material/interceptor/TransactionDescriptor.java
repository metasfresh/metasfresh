package de.metas.handlingunits.material.interceptor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import org.compiere.model.I_M_Transaction;

import de.metas.inout.InOutLineId;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.HUDescriptor;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * de.metas.handlingunits.base
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

/**
 * This pojo contains properties needed to create events for {@link I_M_Transaction}s.
 * We need it because we need to construct the events *after* commit, in order to also catch assigned HUs.
 */
@Value
public class TransactionDescriptor
{
	EventDescriptor eventDescriptor;

	int productId;
	int transactionId;
	int warehouseId;
	int costCollectorId;
	InOutLineId inoutLineId;
	int movementLineId;
	int inventoryLineId;
	Instant transactionDate;
	BigDecimal movementQty;
	String movementType;

	List<HUDescriptor> huDescriptors;

	@Builder
	private TransactionDescriptor(
			EventDescriptor eventDescriptor,
			int productId,
			int transactionId,
			int warehouseId,
			Instant transactionDate,
			BigDecimal movementQty,
			int costCollectorId,
			InOutLineId inoutLineId,
			int movementLineId,
			int inventoryLineId,
			String movementType,
			@Singular List<HUDescriptor> huDescriptors)
	{
		this.eventDescriptor = eventDescriptor;
		this.productId = productId;
		this.transactionId = transactionId;
		this.warehouseId = warehouseId;
		this.transactionDate = transactionDate;
		this.movementQty = movementQty;
		this.costCollectorId = costCollectorId;
		this.inoutLineId = inoutLineId;
		this.movementLineId = movementLineId;
		this.inventoryLineId = inventoryLineId;
		this.movementType = movementType;
		this.huDescriptors = huDescriptors;
	}
}
