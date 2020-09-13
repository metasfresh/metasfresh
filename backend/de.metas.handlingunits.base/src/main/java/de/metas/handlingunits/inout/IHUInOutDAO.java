package de.metas.handlingunits.inout;

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

import de.metas.handlingunits.HUConstants;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.inout.InOutLineId;
import de.metas.util.ISingletonService;
import org.compiere.model.I_M_InOut;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IHUInOutDAO extends ISingletonService
{
	/**
	 * Retrieve the <b>top-level</b> HUs associated with the given {@code inout}'s lines.
	 */
	List<I_M_HU> retrieveHandlingUnits(I_M_InOut inOut);

	List<I_M_InOutLine> retrievePackingMaterialLines(I_M_InOut inOut);

	boolean hasPackingMaterialLines(I_M_InOut inOut);

	/**
	 * Returns the inoutline that is referenced by the given <code>hu</code>'s {@link HUConstants#ATTRIBUTE_VALUE_HU_ReceiptInOutLine_ID} value,<br>
	 * or <code>null</code> if there is no such (active!) inout line, <b>or</b> if the inOutline dies not belong to an <code>MInOut</code> that is completed or closed.
	 */
	I_M_InOutLine retrieveCompletedReceiptLineOrNull(I_M_HU hu);
	
	List<I_M_InOutLine> retrieveInOutLinesForHU(I_M_HU topLevelHU);

	List<I_M_HU> retrieveHandlingUnitsByInOutLineId(InOutLineId inOutLineId);

	/**
	 * Retrieve the handling units assigned to the lines of a given inout if and only if they have status shipped.
	 */
	List<I_M_HU> retrieveShippedHandlingUnits(I_M_InOut inOut);

	Map<InOutLineId, List<I_M_HU>> retrieveShippedHUsByShipmentLineId(Set<InOutLineId> shipmentLineIds);

	List<I_M_HU> retrieveHUsForReceiptLineId(int receiptLineId);
}
