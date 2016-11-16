package de.metas.purchasing.process;

/*
 * #%L
 * de.metas.swat.base
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


import java.util.Collection;
import java.util.List;

import org.adempiere.util.Services;
import org.compiere.model.I_C_Order;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.util.TimeUtil;

import de.metas.order.IOrderPA;
import de.metas.process.SvrProcess;
import de.metas.purchasing.model.I_M_PurchaseSchedule;
import de.metas.purchasing.model.MMPurchaseSchedule;
import de.metas.purchasing.service.IPurchaseScheduleBL;

/**
 * 
 * @author ts
 * 
 * @see "<a href='http://dewiki908/mediawiki/index.php/Bestelldisposition_durchf%C3%BChren_%282009_0019_G69%29'>(2009 0019 G69)</a>"
 */
public class UpdatePurchaseSchedule extends SvrProcess
{

	@Override
	protected String doIt() throws Exception
	{
		final long startTime = System.currentTimeMillis();

		// note: delete the scheds via API to make sure that the appropriate relations are also removed
		final List<MMPurchaseSchedule> schedsToDelete = new Query(getCtx(), I_M_PurchaseSchedule.Table_Name, "", get_TrxName()).setClient_ID().list();
		for (final MMPurchaseSchedule sched : schedsToDelete)
		{
			// addLog("Deleting " + sched);
			sched.deleteEx(false);
		}

		final long afterDeleteTime = System.currentTimeMillis();
		addLog("Deleted " + schedsToDelete.size() + " @M_PurchaseSchedule_ID@ records in " + TimeUtil.formatElapsed(afterDeleteTime - startTime));

		final IOrderPA orderPA = Services.get(IOrderPA.class);
		final IPurchaseScheduleBL purchaseScheduleBL = Services.get(IPurchaseScheduleBL.class);

		int createCounter = 0;
		for (final I_C_Order order : orderPA.retrieveOpenOrders(DocAction.STATUS_Completed, get_TrxName()))
		{
			if (!order.isSOTrx())
			{
				continue;
			}

			final Collection<MMPurchaseSchedule> purchaseScheds = purchaseScheduleBL.retrieveOrCreateForSO(getCtx(), order, get_TrxName());
			for (final MMPurchaseSchedule ps : purchaseScheds)
			{
				purchaseScheduleBL.updateStorageData(getCtx(), ps, get_TrxName());
				purchaseScheduleBL.updateQtyToOrder(ps);
				ps.saveEx();
			}

			addLog("Created " + purchaseScheds.size() + " @M_PurchaseSchedule_ID@ records for @C_Order_ID@ " + order.getDocumentNo());
			createCounter += purchaseScheds.size();
		}
		
		addLog("Created " + createCounter + " purchase schedule records in " + TimeUtil.formatElapsed(System.currentTimeMillis() - afterDeleteTime));
		return "@Success@";
	}

	@Override
	protected void prepare()
	{
		// nothing to do
	}

}
