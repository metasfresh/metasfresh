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


import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;

import de.metas.commission.model.I_C_AdvCommissionCondition;
import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.model.I_C_Sponsor_SalesRep;
import de.metas.commission.model.MCAdvComSystem;
import de.metas.commission.service.ICommissionConditionDAO;
import de.metas.commission.service.ISponsorBL;
import de.metas.commission.util.CommissionConstants;

public class SponsorSalesRep extends CalloutEngine
{

	public String sponsorId(
			final Properties ctx, final int WindowNo, final GridTab mTab, final GridField mField, final Object value)
	{
		if (isCalloutActive())
		{
			return "";
		}

		final I_C_Sponsor_SalesRep ssr = InterfaceWrapperHelper.create(mTab, I_C_Sponsor_SalesRep.class);
		if (ssr.getC_Sponsor_ID() <= 0)
		{
			// without the sponsor, we can't do anything
			return "";
		}

		// get the commission system for the sponsor and add it
		final ISponsorBL sponsorBL = Services.get(ISponsorBL.class);
		final I_C_Sponsor sponsorRoot = sponsorBL.retrieveRoot(ctx, ssr.getC_Sponsor(), SystemTime.asTimestamp(), null);

		final MCAdvComSystem comSystem = MCAdvComSystem.retrieveForRootSponsor(ctx, sponsorRoot, null);
		if (comSystem == null)
		{
			return "";
		}
		
		// make sure that the commission system is ok for the current date
		ssr.setC_AdvComSystem_ID(comSystem.get_ID());
		
		if (ssr.getC_AdvCommissionCondition().getC_AdvComSystem_ID() != comSystem.get_ID() || ssr.getC_BPartner_ID() == 0)
		{
			// reset the commission conditions if they don't match the commission system
			ssr.setC_AdvCommissionCondition_ID(0);
		}
		
		if (ssr.getC_BPartner_ID() > 0)
		{
			// ssr is a sales rep link to a bPartner
			// try to set its default contract
			final I_C_AdvCommissionCondition defaultCondition = Services.get(ICommissionConditionDAO.class).retrieveDefault(ctx, comSystem, null);
			if (defaultCondition != null)
			{
				ssr.setC_AdvCommissionCondition_ID(defaultCondition.getC_AdvCommissionCondition_ID());
			}
		}

		return "";
	}

	/**
	 * Inserts {@link CommissionConstants#VALID_RANGE_MAX} into {@link I_C_Sponsor_SalesRep#COLUMNNAME_ValidTo}, if no
	 * value is currently set.
	 * 
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String validTo(
			final Properties ctx, final int WindowNo, final GridTab mTab, final GridField mField, final Object value)
	{
		if (isCalloutActive())
		{
			return "";
		}

		final I_C_Sponsor_SalesRep ssr = InterfaceWrapperHelper.create(mTab, I_C_Sponsor_SalesRep.class);
		if (ssr.getValidTo() == null)
		{
			ssr.setValidTo(CommissionConstants.VALID_RANGE_MAX);
		}
		return "";
	}
}
