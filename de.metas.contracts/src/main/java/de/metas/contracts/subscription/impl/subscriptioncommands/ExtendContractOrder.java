package de.metas.contracts.subscription.impl.subscriptioncommands;

import org.adempiere.model.CopyRecordFactory;
import org.adempiere.model.CopyRecordSupport;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.PO;
import org.compiere.model.X_C_Order;

import de.metas.contracts.order.model.I_C_Order;
import lombok.NonNull;

/*
 * #%L
 * de.metas.contracts
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

public class ExtendContractOrder
{
	public static I_C_Order extend(@NonNull final I_C_Order existentOrder )
	{
		final I_C_Order newOrder = InterfaceWrapperHelper.newInstance(I_C_Order.class, existentOrder);
		
		final PO to = InterfaceWrapperHelper.getPO(newOrder);
		final PO from = InterfaceWrapperHelper.getPO(existentOrder);
				
		PO.copyValues(from, to, true);

		InterfaceWrapperHelper.save(newOrder);
		
		final CopyRecordSupport childCRS = CopyRecordFactory.getCopyRecordSupport(I_C_Order.Table_Name);
		childCRS.setParentPO(to);
		childCRS.setBase(true);
		childCRS.copyRecord(from, InterfaceWrapperHelper.getTrxName(existentOrder));

		newOrder.setDocStatus(X_C_Order.DOCSTATUS_Drafted);
		newOrder.setDocAction(X_C_Order.DOCACTION_Complete);
		
		InterfaceWrapperHelper.save(newOrder);

		// link the existent order to the new one
		existentOrder.setRef_FollowupOrder_ID(newOrder.getC_Order_ID());
		InterfaceWrapperHelper.save(existentOrder);
		
		return newOrder;
	}

}
