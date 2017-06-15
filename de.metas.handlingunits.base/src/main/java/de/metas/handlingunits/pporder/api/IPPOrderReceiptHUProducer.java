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
 * Generates manufacturing receipt candidates ({@link I_PP_Order_Qty}) together with the planning HUs.
 *
 * The generated receipt candidates are not processed.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IPPOrderReceiptHUProducer
{
	/** @return new producer for receiving a main product */
	public static IPPOrderReceiptHUProducer receiveMainProduct(final I_PP_Order ppOrder)
	{
		return new CostCollectorCandidateFinishedGoodsHUProducer(ppOrder);
	}

	/** @return new producer for receiving a by/co product */
	public static IPPOrderReceiptHUProducer receiveByOrCoProduct(final I_PP_Order_BOMLine ppOrderBOMLine)
	{
		return new CostCollectorCandidateCoProductHUProducer(ppOrderBOMLine);
	}

	/**
	 * Creates planning HUs to be received.
	 *
	 * It also creates manufacturing receipt candidates ({@link I_PP_Order_Qty}).
	 */
	List<I_M_HU> createReceiptCandidatesAndPlanningHUs();

	/**
	 * Creates planning HUs to be received.
	 *
	 * It also creates manufacturing receipt candidates ({@link I_PP_Order_Qty}).
	 * 
	 * @param qtyToReceive precise quantity to receive
	 * @param uom
	 * @deprecated To be removed. Needed only for the legacy Swing UI.
	 */
	@Deprecated
	List<I_M_HU> createReceiptCandidatesAndPlanningHUs(BigDecimal qtyToReceive, I_C_UOM uom);

	/**
	 * Create manufacturing receipt candidate(s) for an already existing planning HU.
	 *
	 * @param planningHU
	 * @deprecated To be removed. Needed only for the legacy Swing UI.
	 */
	@Deprecated
	void createReceiptCandidatesFromPlanningHU(I_M_HU planningHU);

	/**
	 * @deprecated To be removed. Needed only for the legacy Swing UI.
	 */
	@Deprecated
	void setSkipCreateCandidates();

	/**
	 * @return created manufacturing receipt candidate
	 */
	List<I_PP_Order_Qty> getCreatedCandidates();

	/**
	 * NOTE: by default current system time is considered.
	 *
	 * @param movementDate
	 */
	IPPOrderReceiptHUProducer setMovementDate(final java.util.Date movementDate);

	/**
	 * Sets LU/TU configuration to be used.
	 * If not set, the PP_Order/BOM line's current configuration will be used.
	 *
	 * @param lutuConfiguration
	 */
	IPPOrderReceiptHUProducer setM_HU_LUTU_Configuration(I_M_HU_LUTU_Configuration lutuConfiguration);
}
