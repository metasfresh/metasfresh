package de.metas.contracts.subscription.impl.subscriptioncommands;

import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.order.model.I_C_Order;
import de.metas.contracts.subscription.ISubscriptionBL;
import de.metas.copy_with_details.CopyRecordFactory;
import de.metas.document.engine.DocStatus;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.PO;
import org.compiere.model.X_C_Order;
import org.compiere.util.TimeUtil;

import java.sql.Timestamp;

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
	private static final AdMessageKey MSG_EXTEND_CONTRACT_ALREADY_PROLONGED = AdMessageKey.of("de.metas.contracts.subscription.impl.subscriptioncommands.ExtendContractOrder");

	public static I_C_Order extend(@NonNull final I_C_Order existentOrder)
	{
		if (I_C_Order.CONTRACTSTATUS_Extended.equals(existentOrder.getContractStatus()))
		{
			throw new AdempiereException(Services.get(IMsgBL.class).getTranslatableMsgText(MSG_EXTEND_CONTRACT_ALREADY_PROLONGED));
		}
		else
		{
			final I_C_Order newOrder = InterfaceWrapperHelper.newInstance(I_C_Order.class, existentOrder);

			final PO newOrderPO = InterfaceWrapperHelper.getPO(newOrder);
			final PO existentOrderPO = InterfaceWrapperHelper.getPO(existentOrder);

			PO.copyValues(existentOrderPO, newOrderPO, true);
			InterfaceWrapperHelper.save(newOrder);

			CopyRecordFactory.getCopyRecordSupport(I_C_Order.Table_Name)
					.copyChildren(newOrderPO, existentOrderPO);

			newOrder.setDocStatus(DocStatus.Drafted.getCode());
			newOrder.setDocAction(X_C_Order.DOCACTION_Complete);

			final I_C_Flatrate_Term lastTerm = Services.get(ISubscriptionBL.class).retrieveLastFlatrateTermFromOrder(existentOrder);
			if (lastTerm != null)
			{
				final Timestamp addDays = TimeUtil.addDays(lastTerm.getEndDate(), 1);
				newOrder.setDatePromised(addDays);
				newOrder.setPreparationDate(addDays);
			}

			InterfaceWrapperHelper.save(newOrder);

			// link the existent order to the new one
			existentOrder.setRef_FollowupOrder_ID(newOrder.getC_Order_ID());

			InterfaceWrapperHelper.save(existentOrder);

			return newOrder;
		}
	}

}
