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

import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.service.ISponsorBL;

/**
 * 
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Sponsor_(2009_0027_G8)'>(2009 0027 G8)</a>"
 */
public class Sponsor extends CalloutEngine
{

	/**
	 * Implemented for US1026:  aenderung Verguetungsplan (2011010610000028), R01A06
	 * 
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String isManualRank(
			final Properties ctx, final int WindowNo, final GridTab mTab, final GridField mField, final Object value)
	{
		if (isCalloutActive())
		{
			return "";
		}
		
		final I_C_Sponsor sponsor = InterfaceWrapperHelper.create(mTab, I_C_Sponsor.class);
		Services.get(ISponsorBL.class).onIsManualRankChange(sponsor, false, 0);
		
		return "";
	}
}
