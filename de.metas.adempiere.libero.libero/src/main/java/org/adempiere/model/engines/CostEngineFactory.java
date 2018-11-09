package org.adempiere.model.engines;

/**
 * 
 * @author teo.sarca@gmail.com
 */
public class CostEngineFactory
{
	public static CostEngine newCostEngine()
	{
		return new CostEngineImpl();
	}
}
