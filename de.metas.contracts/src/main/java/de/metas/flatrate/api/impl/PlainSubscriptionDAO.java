package de.metas.flatrate.api.impl;

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


import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.wrapper.POJOLookupMap;

import de.metas.flatrate.interfaces.I_C_OLCand;
import de.metas.flatrate.model.I_C_Flatrate_Term;
import de.metas.flatrate.model.I_C_SubscriptionProgress;

public class PlainSubscriptionDAO extends AbstractSubscriptionDAO
{
	private final POJOLookupMap db = POJOLookupMap.get();

	public POJOLookupMap getDB()
	{
		return db;
	}

	@Override
	public List<I_C_SubscriptionProgress> retrieveNextSPs(I_C_Flatrate_Term term, Timestamp date)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public List<I_C_SubscriptionProgress> retrievePlannedAndDelayedDeliveries(Properties ctx, Timestamp date, String trxName)
	{
		throw new UnsupportedOperationException();
	}


	@Override
	public List<I_C_Flatrate_Term> retrieveTermsForOLCand(I_C_OLCand olCand)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public <T extends I_C_OLCand> List<T> retrieveOLCands(I_C_Flatrate_Term term, Class<T> clazz)
	{
		throw new UnsupportedOperationException();
	}

}
