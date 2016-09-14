package de.metas.flatrate.modelvalidator;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.DB;
import org.compiere.util.Env;

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

import org.slf4j.Logger;

import de.metas.adempiere.model.I_C_Order;
import de.metas.contracts.subscription.ISubscriptionBL;
import de.metas.contracts.subscription.ISubscriptionDAO;
import de.metas.contracts.subscription.model.I_C_OrderLine;
import de.metas.flatrate.model.I_C_Flatrate_Term;
import de.metas.flatrate.model.X_C_Flatrate_Term;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.logging.LogManager;
import de.metas.order.IOrderPA;

public class C_Order implements ModelValidator
{
	private static final Logger logger = LogManager.getLogger(C_OrderLine.class);

	private static final String MSG_ORDER_DATE_ORDERED_CHANGE_FORBIDDEN_1P = "Order_DateOrdered_Change_Forbidden";

	private int m_AD_Client_ID = -1;

	@Override
	public int getAD_Client_ID()
	{
		return m_AD_Client_ID;
	}

	@Override
	public void initialize(final ModelValidationEngine engine, final MClient client)
	{
		if (client != null)
			m_AD_Client_ID = client.getAD_Client_ID();

		engine.addModelChange(I_C_Order.Table_Name, this);
		engine.addDocValidate(I_C_Order.Table_Name, this);
	}

	@Override
	public String login(final int AD_Org_ID, final int AD_Role_ID, final int AD_User_ID)
	{
		return null;
	}

	@Override
	public String modelChange(final PO po, final int type) throws Exception
	{
		if (type == TYPE_BEFORE_CHANGE)
		{
			final I_C_Order order = InterfaceWrapperHelper.create(po, I_C_Order.class);

			if (po.is_ValueChanged(I_C_Invoice_Candidate.COLUMNNAME_DateOrdered))
			{
				final IOrderPA orderPA = Services.get(IOrderPA.class);
				final IInvoiceCandDAO invoiceCandDB = Services.get(IInvoiceCandDAO.class);

				for (final I_C_OrderLine ol : orderPA.retrieveOrderLines(order, I_C_OrderLine.class))
				{
					for (final I_C_Invoice_Candidate icOfOl : invoiceCandDB.retrieveReferencing(ol))
					{
						if (icOfOl.isToClear())
						{
							// If the column was updatable, we would have to
							// *check if new and old term are the same
							// *check if ICAs need update, creation or deletion and do it;
							// *check which dataEntries' ActualQty needs update and make sure that they are not yet
							// completed
							// *check is isToClear needs update;
							throw new AdempiereException(Env.getAD_Language(po.getCtx()), MSG_ORDER_DATE_ORDERED_CHANGE_FORBIDDEN_1P, new Object[] { ol.getLine() });
						}
					}
				}
			}
		}
		return null;
	}

	// Note: this code used to be located in
	// /sw01_swat_it/src/java/org/adempiere/order/subscription/modelvalidator/OrderValidator.java
	@Override
	public String docValidate(final PO po, final int timing)
	{
		if (timing != TIMING_AFTER_COMPLETE
				&& timing != TIMING_AFTER_REACTIVATE)
		{
			return null;
		}

		final String trxName = po.get_TrxName();
		final I_C_Order order = InterfaceWrapperHelper.create(po, I_C_Order.class);

		final IOrderPA orderPA = Services.get(IOrderPA.class);

		for (final I_C_OrderLine ol : orderPA.retrieveOrderLines(order, I_C_OrderLine.class))
		{
			if (ol.getC_Flatrate_Conditions_ID() <= 0)
			{
				logger.debug("Order line " + ol + " has no subscription");
				continue;
			}
			if (timing == TIMING_AFTER_COMPLETE)
			{
				handleOrderLineComplete(order, ol, trxName);

			}
			else if (timing == TIMING_AFTER_REACTIVATE)
			{
				handleOrderLineReactivate(ol, trxName);

			}
		}
		return null;
	}

	private void handleOrderLineComplete(
			final I_C_Order order,
			final I_C_OrderLine ol,
			final String trxName)
	{
		final ISubscriptionDAO subscriptionDAO = Services.get(ISubscriptionDAO.class);

		final I_C_Flatrate_Term existingSc = subscriptionDAO.retrieveTermForOl(ol);
		if (existingSc != null)
		{
			logger.debug("{} has already {}", ol, existingSc);
			return;
		}

		logger.info("Creating new {} entry", I_C_Flatrate_Term.Table_Name);

		// Note that order.getDocStatus() might still return 'IP' at this point
		final I_C_Flatrate_Term newSc = Services.get(ISubscriptionBL.class).createSubscriptionTerm(ol, true, order);

		Check.assume(
				X_C_Flatrate_Term.DOCSTATUS_Completed.equals(newSc.getDocStatus()),
				"{} has DocStatus={}", newSc, newSc.getDocStatus());
		logger.info("Created and completed {}", newSc);
	}

	/**
	 * Make sure the orderLine still has processed='Y', even if the order is reactivated. <br>
	 * This was apparently added in task 03152.<br>
	 * I can guess that if an order line already has a C_Flatrate_Term, then we don't want that order line to be editable, because it could create inconsistencies with the term.
	 *
	 * @param ol
	 * @param trxName
	 */
	private void handleOrderLineReactivate(
			final I_C_OrderLine ol,
			final String trxName)
	{
		logger.info("Setting processed status of subscription order line " + ol + " back to Processed='Y'");

		final String sql = "UPDATE C_OrderLine SET Processed='Y' WHERE C_OrderLine_ID=?";
		final int no = DB.executeUpdateEx(sql, new Object[] { ol.getC_OrderLine_ID() }, trxName);
		logger.trace("Update result: " + no);
	}
}
