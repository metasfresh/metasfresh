package org.eevolution.process;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Collections;
import java.util.List;

import org.adempiere.ad.model.util.ModelByIdComparator;
import org.adempiere.ad.process.ISvrProcessPrecondition;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.SvrProcess;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.api.IPPCostCollectorDAO;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.IPPOrderBOMBL;
import org.eevolution.api.IPPOrderBOMDAO;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.X_PP_Order;

import de.metas.document.engine.IDocActionBL;

/**
 * Unclose a manufacturing order.
 *
 * @author tsa
 * @task 08731
 */
public class PP_Order_UnClose extends SvrProcess implements ISvrProcessPrecondition
{
	// services
	private final transient IDocActionBL docActionBL = Services.get(IDocActionBL.class);
	private final transient IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);
	private final transient IPPOrderBOMBL ppOrderBOMBL = Services.get(IPPOrderBOMBL.class);
	private final transient IPPCostCollectorDAO ppCostCollectorDAO = Services.get(IPPCostCollectorDAO.class);
	private final transient IPPCostCollectorBL ppCostCollectorBL = Services.get(IPPCostCollectorBL.class);

	@Override
	public boolean isPreconditionApplicable(final PreconditionsContext context)
	{
		final I_PP_Order ppOrder = context.getModel(I_PP_Order.class);
		return isEligible(ppOrder);
	}

	private boolean isEligible(I_PP_Order ppOrder)
	{
		if (!X_PP_Order.DOCSTATUS_Closed.equals(ppOrder.getDocStatus()))
		{
			return false;
		}

		return true;
	}

	@Override
	protected void prepare()
	{
		// nothing
	}

	@Override
	protected String doIt() throws Exception
	{
		final I_PP_Order ppOrder = getRecord(I_PP_Order.class);
		if (!isEligible(ppOrder))
		{
			throw new AdempiereException("@NotValid@ " + ppOrder);
		}

		unclose(ppOrder);

		return MSG_OK;
	}

	private void unclose(final I_PP_Order ppOrder)
	{
		ModelValidationEngine.get().fireDocValidate(ppOrder, ModelValidator.TIMING_BEFORE_UNCLOSE);

		//
		// Unclose PP_Order's Qty
		ppOrderBL.uncloseQtyOrdered(ppOrder);
		InterfaceWrapperHelper.save(ppOrder);

		//
		// Unclose PP_Order BOM Line's quantities
		final List<I_PP_Order_BOMLine> lines = Services.get(IPPOrderBOMDAO.class).retrieveOrderBOMLines(ppOrder);
		for (final I_PP_Order_BOMLine line : lines)
		{
			ppOrderBOMBL.unclose(line);
		}
		ppOrderBOMBL.reserveStock(lines);

		// firing this before having updated the docstatus. This is how the *real* DocActions like MInvoice do it too.
		ModelValidationEngine.get().fireDocValidate(ppOrder, ModelValidator.TIMING_AFTER_UNCLOSE);

		//
		// Update DocStatus
		ppOrder.setDocStatus(DocAction.STATUS_Completed);
		ppOrder.setDocAction(DocAction.ACTION_Close);
		InterfaceWrapperHelper.save(ppOrder);

		//
		// Reverse ALL cost collectors
		reverseAllCostCollectors(ppOrder);
	}

	private final void reverseAllCostCollectors(final I_PP_Order ppOrder)
	{
		final List<I_PP_Cost_Collector> costCollectors = ppCostCollectorDAO.retrieveForOrder(ppOrder);

		// Sort the cost collectors in reverse order of their creation,
		// just to make sure we are reversing the effect from last one to first one.
		Collections.sort(costCollectors, ModelByIdComparator.getInstance().reversed());

		for (final I_PP_Cost_Collector cc : costCollectors)
		{
			if (docActionBL.isStatusReversedOrVoided(cc))
			{
				continue;
			}

			// Reversing activity controls is not supported atm, so we are skipping them.
			if (ppCostCollectorBL.isActivityControl(cc))
			{
				continue;
			}

			if (docActionBL.isStatusOneOf(cc, DocAction.STATUS_Closed))
			{
				cc.setDocStatus(DocAction.STATUS_Completed);
				InterfaceWrapperHelper.save(cc);
			}

			docActionBL.processEx(cc, DocAction.ACTION_Reverse_Correct, DocAction.STATUS_Reversed);
		}
	}
}
