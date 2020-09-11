package de.metas.handlingunits.pporder.api;

import java.time.LocalDate;
import java.time.ZonedDateTime;

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

import org.adempiere.warehouse.LocatorId;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.handlingunits.picking.PickingCandidateId;
import de.metas.quantity.Quantity;

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
	/**
	 * Creates planning HUs to be received.
	 * It also creates draft manufacturing receipt candidates ({@link I_PP_Order_Qty}).
	 */
	void createDraftReceiptCandidatesAndPlanningHUs();

	/**
	 * Creates planning HUs to be received.
	 * NO manufacturing receipt candidates will be created.
	 * 
	 * @deprecated To be removed. Needed only for the legacy Swing UI.
	 */
	@Deprecated
	List<I_M_HU> createPlanningHUs(Quantity qtyToReceive);

	I_M_HU receiveVHU(Quantity qtyToReceive);

	/**
	 * Creates & processes manufacturing receipt candidate(s) for an already existing planning HU.
	 *
	 * @deprecated To be removed. Needed only for the legacy Swing UI.
	 */
	@Deprecated
	void createReceiptCandidatesFromPlanningHU(I_M_HU planningHU);

	/**
	 * NOTE: by default current system time is considered.
	 */
	IPPOrderReceiptHUProducer movementDate(final ZonedDateTime movementDate);

	IPPOrderReceiptHUProducer locatorId(LocatorId locatorId);

	/**
	 * Sets LU/TU configuration to be used.
	 * If not set, the PP_Order/BOM line's current configuration will be used.
	 */
	IPPOrderReceiptHUProducer packUsingLUTUConfiguration(I_M_HU_LUTU_Configuration lutuConfiguration);

	IPPOrderReceiptHUProducer pickingCandidateId(PickingCandidateId pickingCandidateId);

	IPPOrderReceiptHUProducer lotNumber(String lotNumber);

	IPPOrderReceiptHUProducer bestBeforeDate(LocalDate bestBeforeDate);
}
