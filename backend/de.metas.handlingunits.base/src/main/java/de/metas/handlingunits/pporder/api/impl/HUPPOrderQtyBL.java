package de.metas.handlingunits.pporder.api.impl;

import org.adempiere.util.Services;
import org.eevolution.model.I_PP_Order_BOMLine;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.handlingunits.pporder.api.IHUPPOrderQtyBL;
import de.metas.handlingunits.pporder.api.IHUPPOrderQtyDAO;
import de.metas.material.planning.pporder.PPOrderUtil;

/*
 * #%L
 * de.metas.handlingunits.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class HUPPOrderQtyBL implements IHUPPOrderQtyBL
{
	@Override
	public void reverseDraftCandidate(final I_PP_Order_Qty candidate)
	{
		final I_PP_Order_BOMLine orderBOMLine = candidate.getPP_Order_BOMLine();
		if (orderBOMLine == null)
		{
			// Main product receipt
			reverseDraftReceipt(candidate);
		}
		else if (PPOrderUtil.isReceipt(orderBOMLine))
		{
			// Co/By Product receipt
			reverseDraftReceipt(candidate);
		}
		else
		{
			// Issue line
			reverseDraftIssue(candidate);
		}
	}

	private void reverseDraftIssue(final I_PP_Order_Qty candidate)
	{
		final IHUPPOrderBL huPPOrderBL = Services.get(IHUPPOrderBL.class);
		huPPOrderBL.createIssueProducer()
				.reverseDraftIssue(candidate);
	}

	private void reverseDraftReceipt(final I_PP_Order_Qty candidate)
	{
		//
		// Get the HU planned to be received and validate it (i.e. make sure it's still planning)
		final I_M_HU huToReceive = candidate.getM_HU();
		if (!X_M_HU.HUSTATUS_Planning.equals(huToReceive.getHUStatus()))
		{
			throw new HUException("Invalid HU Status")
					.setParameter("HUStatus", huToReceive.getHUStatus());
		}

		//
		// Destroy the planned HU
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);
		huTrxBL.createHUContextProcessorExecutor()
				.run(huContext -> {
					handlingUnitsBL.markDestroyed(huContext, huToReceive);
				});

		//
		// Delete the candidate
		final IHUPPOrderQtyDAO huPPOrderQtyDAO = Services.get(IHUPPOrderQtyDAO.class);
		huPPOrderQtyDAO.delete(candidate);
	}

}
