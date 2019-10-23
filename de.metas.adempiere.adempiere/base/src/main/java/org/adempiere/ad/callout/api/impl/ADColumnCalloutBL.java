package org.adempiere.ad.callout.api.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.util.List;
import java.util.Properties;

import org.adempiere.ad.callout.api.IADColumnCalloutBL;
import org.adempiere.ad.callout.api.IADColumnCalloutDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_ColumnCallout;

import de.metas.util.Services;

public class ADColumnCalloutBL implements IADColumnCalloutBL
{
	@Override
	public void copyAllFrom(I_AD_Column targetColumn, int fromColumn_ID)
	{
		if (targetColumn.getAD_Column_ID() <= 0)
		{
			throw new IllegalArgumentException("Target column is not saved - " + targetColumn);
		}
		
		final Properties ctx = InterfaceWrapperHelper.getCtx(targetColumn);
		final List<I_AD_ColumnCallout> fromCallouts = Services.get(IADColumnCalloutDAO.class).retrieveAllColumnCallouts(ctx, fromColumn_ID);
		
		for (final I_AD_ColumnCallout fromCallout : fromCallouts)
		{
			final I_AD_ColumnCallout toCallout = InterfaceWrapperHelper.newInstance(I_AD_ColumnCallout.class, targetColumn, true);
			
			InterfaceWrapperHelper.copyValues(fromCallout, toCallout);
			toCallout.setAD_Column_ID(targetColumn.getAD_Column_ID());
			
			InterfaceWrapperHelper.save(toCallout);
		}
	}

}
