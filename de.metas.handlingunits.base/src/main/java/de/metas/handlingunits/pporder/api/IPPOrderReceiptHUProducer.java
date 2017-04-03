package de.metas.handlingunits.pporder.api;

import java.math.BigDecimal;

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

import java.util.List;

import org.compiere.model.I_C_UOM;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.handlingunits.pporder.api.impl.CostCollectorCandidateCoProductHUProducer;
import de.metas.handlingunits.pporder.api.impl.CostCollectorCandidateFinishedGoodsHUProducer;

/**
 * Generates planning HUs which are received for a given manufacturing order.
 * Also generates {@link I_PP_Order_Qty} to link those planning HUs.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IPPOrderReceiptHUProducer
{
	public static IPPOrderReceiptHUProducer receiveMainProduct(final I_PP_Order ppOrder)
	{
		return new CostCollectorCandidateFinishedGoodsHUProducer(ppOrder);
	}

	public static IPPOrderReceiptHUProducer receiveByOrCoProduct(final I_PP_Order_BOMLine ppOrderBOMLine)
	{
		return new CostCollectorCandidateCoProductHUProducer(ppOrderBOMLine);
	}

	/**
	 * Creates planning HUs to be received.
	 */
	List<I_M_HU> receiveHUs(BigDecimal qtyToReceive, I_C_UOM uom);

	IPPOrderReceiptHUProducer setMovementDate(final java.util.Date movementDate);

	/**
	 * Sets LU/TU configuration to be used.
	 * If not set, the PP_Order/BOM line's current configuration will be used.
	 * 
	 * @param lutuConfiguration
	 */
	IPPOrderReceiptHUProducer setM_HU_LUTU_Configuration(I_M_HU_LUTU_Configuration lutuConfiguration);
}
