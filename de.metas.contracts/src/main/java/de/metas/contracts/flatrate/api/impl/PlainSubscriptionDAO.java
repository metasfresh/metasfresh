package de.metas.contracts.flatrate.api.impl;

import java.util.List;

import org.adempiere.ad.wrapper.POJOLookupMap;

import de.metas.contracts.flatrate.interfaces.I_C_OLCand;
import de.metas.contracts.model.I_C_Flatrate_Term;

public class PlainSubscriptionDAO extends AbstractSubscriptionDAO
{
	private final POJOLookupMap db = POJOLookupMap.get();

	public POJOLookupMap getDB()
	{
		return db;
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
