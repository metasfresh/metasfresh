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


import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.eevolution.api.IDDOrderBL;
import org.eevolution.model.I_DD_Order;

import de.metas.process.JavaProcess;

/**
 * Process used to manually trigger the completion of forward and backward {@link I_DD_Order}s
 * 
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/08059_Trigger_Fertigstellen_for_DD_Orders_%28107323649094%29
 */
public class DD_Order_CompleteForwardBackward extends JavaProcess
{

	private I_DD_Order p_ddOrder;

	@Override
	protected void prepare()
	{
		// nothing
	}

	@Override
	protected String doIt() throws Exception
	{
		final I_DD_Order ddOrder = getDD_Order();
		Services.get(IDDOrderBL.class).completeForwardAndBackwardDDOrders(ddOrder);

		return MSG_OK;
	}

	private I_DD_Order getDD_Order()
	{
		if (p_ddOrder != null)
		{
			return p_ddOrder;
		}

		if (I_DD_Order.Table_Name.equals(getTableName()) && getRecord_ID() > 0)
		{
			p_ddOrder = InterfaceWrapperHelper.create(getCtx(), getRecord_ID(), I_DD_Order.class, get_TrxName());
		}

		if (p_ddOrder == null || p_ddOrder.getDD_Order_ID() <= 0)
		{
			throw new FillMandatoryException(I_DD_Order.COLUMNNAME_DD_Order_ID);
		}

		return p_ddOrder;
	}
}
