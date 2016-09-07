package de.metas.ordercandidate.callout;

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


import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.I_AD_Column;
import org.compiere.util.DisplayType;
import org.compiere.util.Util;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.ordercandidate.model.I_C_OLCandAggAndOrder;
import de.metas.ordercandidate.model.X_C_OLCandAggAndOrder;

public class OLCandAggAndOrder extends CalloutEngine
{

	private static final Logger logger = LogManager.getLogger(OLCandAggAndOrder.class);

	public String adColumnOLCandId(
			final Properties ctx,
			final int WindowNo,
			final GridTab mTab,
			final GridField mField,
			final Object value)
	{
		if (isCalloutActive())
		{
			return "";
		}

		return evaluate(mTab);
	}

	public String isGroupBy(
			final Properties ctx,
			final int WindowNo,
			final GridTab mTab,
			final GridField mField,
			final Object value)
	{
		if (isCalloutActive())
		{
			return "";
		}

		return evaluate(mTab);
	}

	private String evaluate(final GridTab mTab)
	{
		final I_C_OLCandAggAndOrder aggAndOrder = InterfaceWrapperHelper.create(mTab, I_C_OLCandAggAndOrder.class);

		if (aggAndOrder.getAD_Column_OLCand_ID() <= 0)
		{
			logger.debug("AD_Column_OLCand=" + aggAndOrder.getAD_Column_OLCand_ID()
					+ " is not set");
			aggAndOrder.setAD_Reference_OLCand_ID(0);
			return "";
		}

		final I_AD_Column colOLCand = aggAndOrder.getAD_Column_OLCand();
		aggAndOrder.setAD_Reference_OLCand_ID(colOLCand.getAD_Reference_ID());

		if (!aggAndOrder.isGroupBy()
				|| (colOLCand.getAD_Reference_ID() != DisplayType.Date && colOLCand.getAD_Reference_ID() != DisplayType.DateTime))
		{
			logger.debug("AD_Column_OLCand=" + aggAndOrder.getAD_Column_OLCand_ID()
					+ " has AD_Reference_ID=" + colOLCand.getAD_Reference_ID()
					+ " and IsGroupBy=" + aggAndOrder.isGroupBy()
					+ "; Setting Granularity=null");
			aggAndOrder.setGranularity(null);
		}
		else if (Util.isEmpty(aggAndOrder.getGranularity()))
		{
			aggAndOrder.setGranularity(X_C_OLCandAggAndOrder.GRANULARITY_Tag);
			logger.debug("AD_Column_OLCand=" + aggAndOrder.getAD_Column_OLCand_ID()
					+ " has AD_Reference_ID=" + colOLCand.getAD_Reference_ID()
					+ " and IsGroupBy=" + aggAndOrder.isGroupBy()
					+ "; Setting Granularity=" + X_C_OLCandAggAndOrder.GRANULARITY_Tag);
		}

		return "";
	}

}
