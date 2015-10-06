/**
 * 
 */
package org.eevolution.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;
import org.compiere.util.CCache;
import org.compiere.util.Env;

/**
 * HR Concept Category
 * 
 * @author Cristina Ghita, www.arhipac.ro
 */
public class MHRConceptCategory extends X_HR_Concept_Category
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8470029939291479283L;

	private static CCache<Integer, MHRConceptCategory> s_cache = new CCache<Integer, MHRConceptCategory>(Table_Name, 20);
	private static CCache<String, MHRConceptCategory> s_cacheValue = new CCache<String, MHRConceptCategory>(Table_Name+"_Value", 20);
	
	public static MHRConceptCategory get(Properties ctx, int HR_Concept_Category_ID)
	{
		if (HR_Concept_Category_ID <= 0)
		{
			return null;
		}
		// Try cache
		MHRConceptCategory cc = s_cache.get(HR_Concept_Category_ID);
		if (cc != null)
		{
			return cc;
		}
		// Load from DB
		cc = new MHRConceptCategory(ctx, HR_Concept_Category_ID, null);
		if (cc.get_ID() != HR_Concept_Category_ID)
		{
			return null;
		}
		if (cc != null)
		{
			s_cache.put(HR_Concept_Category_ID, cc);
		}
		return cc;
	}
	
	public static MHRConceptCategory forValue(Properties ctx, String value)
	{
		if (value == null)
		{
			return null;
		}
		final int AD_Client_ID = Env.getAD_Client_ID(ctx);
		// Try cache
		final String key = AD_Client_ID+"#"+value;
		MHRConceptCategory cc = s_cacheValue.get(key);
		if (cc != null)
		{
			return cc;
		}
		// Try database
		final String whereClause = COLUMNNAME_Value+"=? AND AD_Client_ID IN (?,?)";
		cc = new Query(ctx, Table_Name, whereClause, null)
									.setParameters(new Object[]{value, 0, AD_Client_ID})
									.setOnlyActiveRecords(true)
									.setOrderBy("AD_Client_ID DESC")
									.first();
		if (cc != null)
		{
			s_cacheValue.put(key, cc);
			s_cache.put(cc.get_ID(), cc);
		}
		return cc;
	}

	public MHRConceptCategory(Properties ctx, int HR_Concept_Category_ID, String trxName)
	{
		super(ctx, HR_Concept_Category_ID, trxName);
	}
	public MHRConceptCategory(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}
}
