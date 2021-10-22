package de.metas.handlingunits.ddorder;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.ddorder.movement.DDOrderLinesAllocatorFactory;
import de.metas.handlingunits.ddorder.producer.HUToDistribute;
import de.metas.quantity.Quantity;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.eevolution.api.DDOrderId;
import org.eevolution.api.DDOrderLineId;
import org.eevolution.api.DDOrderQuery;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.I_DD_OrderLine_Alternative;
import org.eevolution.model.I_DD_OrderLine_Or_Alternative;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public interface IHUDDOrderBL extends ISingletonService
{
	I_DD_Order getById(DDOrderId ddOrderId);

	Stream<I_DD_Order> streamDDOrders(DDOrderQuery query);

	void save(I_DD_Order ddOrder);

	List<I_DD_OrderLine> retrieveLines(I_DD_Order order);

	I_DD_OrderLine getLineById(final DDOrderLineId ddOrderLineId);

	List<I_DD_OrderLine_Alternative> retrieveAllAlternatives(I_DD_OrderLine ddOrderLine);

	/**
	 * If the given <code>ddOrder</code> is in status draft or in progress, then the method attempts to complete it. Otherwise, it assumes that the ddOrder is already completed and throws an exception
	 * if that is not the case.
	 */
	void completeDDOrderIfNeeded(I_DD_Order ddOrder);

	/**
	 * Start allocating HUs to DD Order Lines and then you can generate movements
	 */
	DDOrderLinesAllocatorFactory prepareAllocateAndMove();

	/**
	 * Close given distribution order line.
	 */
	void closeLine(I_DD_OrderLine ddOrderLine);

	/**
	 * Unassigns given HUs from given DD order line.
	 */
	void unassignHUs(DDOrderLineId ddOrderLineId, Set<HuId> huIdsToUnassign);

	/**
	 * Create a ddOrder with the handling units assigned to the given receipt line to a warehouse flagged as IsQuarantineWarehouse
	 */
	List<I_DD_Order> createQuarantineDDOrderForReceiptLines(List<QuarantineInOutLine> receiptLines);

	/**
	 * Create a ddOrder with the handling units given as parameter to a warehouse flagged as IsQuarantineWarehouse
	 */
	List<I_DD_Order> createQuarantineDDOrderForHUs(List<HUToDistribute> hus);

	DDOrderLineId addDDOrderLine(DDOrderLineCreateRequest ddOrderLineCreateRequest);

	void generateMovements(@NonNull I_DD_Order ddOrder);

	boolean isCreateMovementOnComplete();

	/**
	 * Gets Qty that needs to be shipped so far from source locator.
	 * <p>
	 * The UOM is line's UOM.
	 */
	Quantity getQtyToShip(I_DD_OrderLine_Or_Alternative ddOrderLineOrAlt);
}
