package de.metas.ordercandidate.api;

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


import java.util.Iterator;
import java.util.Properties;

import org.adempiere.util.ISingletonService;
import org.adempiere.util.api.IParams;

import de.metas.ordercandidate.model.I_C_OLCand;

/**
 * Updates order line candidates.
 * 
 * @author ts
 * 
 */
public interface IOLCandUpdateBL extends ISingletonService
{
	/**
	 * Will update the following C_OLCand columns from the given parameters:
	 * <ul>
	 * <li>I_C_OLCand.COLUMNNAME_C_BPartner_Override_ID
	 * <li>I_C_OLCand.COLUMNNAME_C_BP_Location_Override_ID
	 * <li>I_C_OLCand.COLUMNNAME_DatePromised_Override
	 * </ul>
	 * 
	 * The given <code>params</code> don't have a value for any of these 3 columns, it will be set to <code>null</code>.
	 * 
	 * @param ctx
	 * @param candsToUpdate
	 * @param params
	 * @return
	 */
	OLCandUpdateResult updateOLCands(Properties ctx, Iterator<I_C_OLCand> candsToUpdate, IParams params);

}
