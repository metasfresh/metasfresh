package de.metas.flatrate.modelvalidator;

/*
 * #%L
 * de.metas.contracts
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


import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.Env;
import org.compiere.util.Msg;

import de.metas.contracts.subscription.model.I_C_OrderLine;

/**
 *
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Abonnement_Auftragsverwaltung_(2009_0015_G36)'>(2009_0015_G36)</a>"
 */
// code used to be located in /sw01_swat_it/src/java/org/adempiere/order/subscription/modelvalidator/OrderValidator.java
public class C_OrderLine implements ModelValidator
{
	public static final String MSG_SUBSCRIPTION_ORDERLINE_2P = "subscription.olHasSubscription_2P";

	private int ad_Client_ID = -1;

	@Override
	public int getAD_Client_ID()
	{
		return ad_Client_ID;
	}

	@Override
	public final void initialize(final ModelValidationEngine engine,
			final MClient client)
	{
		if (client != null)
		{
			ad_Client_ID = client.getAD_Client_ID();
		}

		engine.addModelChange(I_C_OrderLine.Table_Name, this);
	}

	@Override
	public String login(final int AD_Org_ID, final int AD_Role_ID,
			final int AD_User_ID)
	{
		// nothing to do
		return null;
	}

	@Override
	public String docValidate(final PO po, final int timing)
	{
		return null; // nothing to do
	}

	@Override
	public String modelChange(final PO po, final int type) throws Exception
	{
		if (type == TYPE_BEFORE_DELETE)
		{
			final I_C_OrderLine ol = InterfaceWrapperHelper.create(po, I_C_OrderLine.class);

			if (ol.isProcessed())
			{
				final I_C_Order order = ol.getC_Order();

				final Integer subscriptionId = ol.getC_Flatrate_Conditions_ID();

				if (subscriptionId == null || subscriptionId <= 0)
				{
					// TODO figure out wtf this check and this error message mean
					throw new AdempiereException("OrderLine " + ol.getLine()
							+ " of order " + order.getDocumentNo()
							+ " is processed, but I don't know why");
				}

				return Msg.getMsg(Env.getCtx(), MSG_SUBSCRIPTION_ORDERLINE_2P,
						new Object[] { ol.getLine(), order.getDocumentNo() });
			}
		}
		return null;

	}
}
