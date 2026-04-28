/**
 * 
 */
package de.metas.location.impl;

import de.metas.location.CountryCustomInfo;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.compiere.model.I_C_Country;
import org.junit.jupiter.api.Disabled;

import java.util.Comparator;
import java.util.List;
import java.util.Properties;

/**
 * @author cg
 * 
 */
@Disabled
public class PlainCountryDAO extends CountryDAO
{
	@Override
	public CountryCustomInfo retriveCountryCustomInfo(Properties ctx, String trxName)
	{
		return null;
	}

	@Override
	public List<I_C_Country> getCountries(Properties ctx)
	{
		return POJOLookupMap.get().getRecords(I_C_Country.class, country -> true);
	}

	@Override
	public I_C_Country getDefault(final Properties ctx)
	{
		// NOTE: we need to override the default implementation because that one is assuming that we have AD_Client records... and in some tests we don't have it.
		// So for now, we are just returning first country which was created.
		final Comparator<I_C_Country> orderByComparator = Comparator.comparing(I_C_Country::getC_Country_ID);
		return POJOLookupMap.get().getFirst(I_C_Country.class, country -> true, orderByComparator);
	}
}
