/**
 * 
 */
package de.metas.adempiere.callout;

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


import java.sql.Timestamp;

import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.CalloutEngine;
import org.compiere.util.TimeUtil;

import de.metas.adempiere.model.I_C_Order;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeBL;
import de.metas.util.Services;

/**
 * @author tsa
 *
 */
public class OrderOffer extends CalloutEngine
{
	public String setOfferValidDays (final ICalloutField calloutField)
	{
		I_C_Order order = calloutField.getModel(I_C_Order.class);
		setOfferValidDate(order);
		return "";
	}
	
	private static void setOfferValidDate(I_C_Order order)
	{
		if (order.isProcessed())
			return;
		
		final Timestamp dateOrdered = order.getDateOrdered();
		
		if (dateOrdered != null && isOffer(order))
		{
			final int days = order.getOfferValidDays();
			if (days < 0)
				throw new AdempiereException("@"+I_C_Order.COLUMNNAME_OfferValidDays+"@ < 0");
			final Timestamp offerValidDate = TimeUtil.addDays(dateOrdered, days);
			order.setOfferValidDate(offerValidDate);
		}
		else
		{
			order.setOfferValidDays(0);
			order.setOfferValidDate(null);
		}
	}
	
	private static boolean isOffer(final I_C_Order order)
	{
		DocTypeId docTypeId = DocTypeId.ofRepoIdOrNull(order.getC_DocType_ID());
		if (docTypeId == null)
		{
			docTypeId = DocTypeId.ofRepoIdOrNull(order.getC_DocTypeTarget_ID());
		}
		if (docTypeId == null)
		{
			return false;
		}

		return Services.get(IDocTypeBL.class).isSalesProposalOrQuotation(docTypeId);
	}

}
