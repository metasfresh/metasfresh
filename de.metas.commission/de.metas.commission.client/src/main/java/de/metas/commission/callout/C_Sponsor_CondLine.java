/**
 * 
 */
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
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.Trx;

import de.metas.commission.model.I_C_AdvComSystem;
import de.metas.commission.model.I_C_AdvCommissionCondition;
import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.model.I_C_Sponsor_CondLine;
import de.metas.commission.model.X_C_Sponsor_CondLine;
import de.metas.commission.service.ICommissionConditionDAO;

/**
 * @author cg
 * 
 */
public class C_Sponsor_CondLine extends CalloutEngine
{
	public String onSalesRepTypeChange(final Properties ctx, final int WindowNo,
			final GridTab mTab, final GridField mField, final Object value,
			final Object oldValue)
	{
		final I_C_Sponsor_CondLine condLine = InterfaceWrapperHelper.create(mTab, I_C_Sponsor_CondLine.class);
		if (X_C_Sponsor_CondLine.SPONSORSALESREPTYPE_VP.equals(condLine.getSponsorSalesRepType()))
		{
			if (condLine.getC_Sponsor_Cond_ID() > 0) // guard against NPE; if the head record wasn't saved, it should not be this callout to break the bad news in form of an NPE
			{
				// set partner based on sponsor
				final I_C_Sponsor sponsor = condLine.getC_Sponsor_Cond().getC_Sponsor();
				condLine.setC_BPartner_ID(sponsor.getC_BPartner_ID());
			}
			// set adv commission type based on adv comm system
			final I_C_AdvComSystem comSys = condLine.getC_AdvComSystem();
			if (comSys != null)
			{
				// try to set its default contract
				final I_C_AdvCommissionCondition defaultCondition = Services.get(ICommissionConditionDAO.class).retrieveDefault(ctx, comSys, Trx.TRXNAME_None);
				if (defaultCondition != null)
				{
					condLine.setC_AdvCommissionCondition_ID(defaultCondition.getC_AdvCommissionCondition_ID());
				}
			}
		}
		else
		{
			condLine.setC_BPartner_ID(0);
			condLine.setC_AdvCommissionCondition_ID(0);
		}

		return NO_ERROR;
	}
}
