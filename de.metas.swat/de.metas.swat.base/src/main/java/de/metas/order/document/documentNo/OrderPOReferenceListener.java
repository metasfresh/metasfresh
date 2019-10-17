package de.metas.order.document.documentNo;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;

import de.metas.document.sequence.spi.IDocumentNoAware;
import de.metas.document.sequence.spi.IDocumentNoListener;
import de.metas.order.IOrderBL;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class OrderPOReferenceListener implements IDocumentNoListener
{
	public static OrderPOReferenceListener INSTANCE = new OrderPOReferenceListener();

	private OrderPOReferenceListener()
	{
	}

	@Override
	public String getTableName()
	{
		return I_C_Order.Table_Name;
	}

	/**
	 * If the given order has a <code>DocumentNo</code> and no <code>POReference</code> and if the order's <code>C_BPartner</code> is configured accordingly, then this method sets the given order's
	 * <code>POReference</code>.<br>
	 * Notes:
	 * <ul>
	 * <li>the order is not saved
	 * <li>calling this method from a model validator generally does not work well, because the (definite) documentNo itself is set when the order is changed, and the order might still have its old
	 * documentNo value.
	 * </ul>
	 *
	 * @param order
	 * @task http://dewiki908/mediawiki/index.php/09776_EDI_-_Customer_with_INVOIC_but_without_%28ORDERS%2C_DESADV%29_%28100584995833%29
	 */
	@Override
	public void onDocumentNoChange(IDocumentNoAware model, String newDocumentNo)
	{
		final I_C_Order order = InterfaceWrapperHelper.create(model, I_C_Order.class);

		if (!Check.isEmpty(order.getPOReference(), true))
		{
			return; // the POReference is already set.
		}

		final String documentNo = order.getDocumentNo();
		if(Check.isEmpty(documentNo, true))
		{
			return; // there is no documentNo to work with
		}

		final I_C_BPartner bpartner = Services.get(IOrderBL.class).getBPartner(order);
		if (!bpartner.isCreateDefaultPOReference())
		{
			return; // the BPartner doesn't want us to set it
		}

		final String poReferencePattern = bpartner.getPOReferencePattern();

		final String poReference = StringUtils.overlayAtEnd(poReferencePattern, documentNo);
		order.setPOReference(poReference);
	}

}
