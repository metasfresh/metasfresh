package de.metas.handlingunits.ddorder.api;

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

import de.metas.handlingunits.ddorder.api.impl.DDOrderLinesAllocator;
import de.metas.handlingunits.ddorder.api.impl.HUs2DDOrderProducer.HUToDistribute;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.util.ISingletonService;
import lombok.NonNull;

import org.adempiere.ad.dao.IQueryOrderBy;
import org.eevolution.api.DDOrderLineId;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;

import java.util.Collection;
import java.util.List;

public interface IHUDDOrderBL extends ISingletonService
{
	DDOrderLinesAllocator createMovements();

	/**
	 * Close given distribution order line.
	 *
	 * @param ddOrderLine
	 */
	void closeLine(I_DD_OrderLine ddOrderLine);

	/**
	 * Unassigns given HUs from given DD order line.
	 *
	 * @param ddOrderLine
	 * @param hus HUs to unassign
	 */
	void unassignHUs(I_DD_OrderLine ddOrderLine, Collection<I_M_HU> hus);

	/**
	 * Create a ddOrder with the handling units assigned to the given receipt line to a warehouse flagged as IsQuarantineWarehouse
	 */
	List<I_DD_Order> createQuarantineDDOrderForReceiptLines(List<QuarantineInOutLine> receiptLines);

	/**
	 * Create a ddOrder with the handling units given as parameter to a warehouse flagged as IsQuarantineWarehouse
	 */
	List<I_DD_Order> createQuarantineDDOrderForHUs(List<HUToDistribute> hus);

	DDOrderLineId addDDOrderLine(DDOrderLineCreateRequest ddOrderLineCreateRequest);

	void processDDOrderLines(@NonNull I_DD_Order ddOrder);

	/**
	 * Retrieves available Hus for given locator and product and using provided order
	 * 
	 * @param ddOrderLine
	 * @return
	 */
	List<I_M_HU> retrieveAvailableHusToMove(@NonNull I_DD_OrderLine ddOrderLine, @NonNull IQueryOrderBy queryOrderBy);

	boolean isCreateMovementOnComplete();
}
