/**
 * 
 */
package org.eevolution.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.CCache;

/**
 * HR Period
 * @author Teo Sarca, www.arhipac.ro
 */
public class MHRPeriod extends X_HR_Period
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7787966459848200539L;
	
	private static CCache<Integer, MHRPeriod> s_cache = new CCache<Integer, MHRPeriod>(Table_Name, 20);
	
	public static MHRPeriod get(Properties ctx, int HR_Period_ID)
	{
		if (HR_Period_ID <= 0)
		{
			return null;
		}
		//
		MHRPeriod period = s_cache.get(HR_Period_ID);
		if (period != null)
		{
			return period;
		}
		// Try Load
		period = new MHRPeriod(ctx, HR_Period_ID, null);
		if (period.get_ID() == HR_Period_ID)
		{
			s_cache.put(HR_Period_ID, period);
		}
		else
		{
			period = null;
		}
		return period;
	}

	public MHRPeriod(Properties ctx, int HR_Period_ID, String trxName)
	{
		super(ctx, HR_Period_ID, trxName);
	}
	public MHRPeriod(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

}
