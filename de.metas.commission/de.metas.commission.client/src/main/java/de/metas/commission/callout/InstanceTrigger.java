package de.metas.commission.callout;

/*
 * #%L
 * de.metas.commission.client
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
import java.util.List;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;

import de.metas.commission.interfaces.I_C_BPartner;
import de.metas.commission.interfaces.I_C_Invoice;
import de.metas.commission.interfaces.I_C_Order;
import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.model.I_C_Sponsor_SalesRep;
import de.metas.commission.service.ISponsorDAO;

/**
 * 
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Sponsor_(2009_0027_G8)'>(2009 0027 G8)</a>"
 */
public class InstanceTrigger extends CalloutEngine
{

	private static final String TRX_NAME = null;

	public String billBPartnerID(
			final Properties ctx, final int WindowNo,
			final GridTab mTab, final GridField mField, final Object value)
	{
		if (isCalloutActive())
		{
			return "";
		}

		return lookup(ctx, mTab);
	}

	public String cBPartnerID(
			final Properties ctx, final int WindowNo,
			final GridTab mTab, final GridField mField, final Object value)
	{
		if (isCalloutActive())
		{
			return "";
		}
		return lookup(ctx, mTab);
	}

	private String lookup(
			final Properties ctx,
			final GridTab mTab)
	{
		final int bPartnerId;
		final Timestamp date;

		if (I_C_Order.Table_Name.equals(mTab.getTableName()))
		{
			final I_C_Order order = InterfaceWrapperHelper.create(mTab, I_C_Order.class);
			if (!order.isSOTrx())
			{
				return ""; // nothing to do
			}
			if (order.getBill_BPartner_ID() > 0)
			{
				bPartnerId = order.getBill_BPartner_ID();
			}
			else
			{
				bPartnerId = order.getC_BPartner_ID();
			}
			date = order.getDateOrdered();

			final int sponsorId = lookupForBPartner(ctx, bPartnerId, date);
			order.setC_Sponsor_ID(sponsorId);
		}
		else
		{
			Check.assume(I_C_Invoice.Table_Name.equals(mTab.getTableName()), mTab + " is either C_Order or M_InOut or C_Invoice");
			final I_C_Invoice invoice = InterfaceWrapperHelper.create(mTab, I_C_Invoice.class);
			if (!invoice.isSOTrx())
			{
				return ""; // nothing to do
			}

			bPartnerId = invoice.getC_BPartner_ID();
			date = invoice.getDateInvoiced();

			final int sponsorId = lookupForBPartner(ctx, bPartnerId, date);
			invoice.setC_Sponsor_ID(sponsorId);
		}

		return "";
	}

	private int lookupForBPartner(
			final Properties ctx,
			final int bPartnerId,
			final Timestamp date)
	{
		if (bPartnerId <= 0)
		{
			return 0;
		}

		final I_C_BPartner bPartner = InterfaceWrapperHelper.create(ctx, bPartnerId, I_C_BPartner.class, TRX_NAME);

		final I_C_Sponsor sponsor = Services.get(ISponsorDAO.class).retrieveForBPartner(bPartner, false);
		if (sponsor == null)
		{
			return -1;
		}
		
		final List<I_C_Sponsor_SalesRep> ssrParents = Services.get(ISponsorDAO.class).retrieveParentLinks(ctx, sponsor.getC_Sponsor_ID(), date, date, null);

		Check.assume(ssrParents.size() == 1,
				sponsor + " has " + ssrParents.size() + " parents at " + date);

		final int parentSponsorId = ssrParents.get(0).getC_Sponsor_Parent_ID();

		return parentSponsorId;
	}
}
