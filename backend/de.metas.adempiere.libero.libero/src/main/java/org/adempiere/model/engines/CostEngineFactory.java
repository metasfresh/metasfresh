package org.adempiere.model.engines;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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


import java.util.HashMap;

/**
 * 
 * @author teo.sarca@gmail.com
 */
public class CostEngineFactory
{
	private static final HashMap<Integer, CostEngine> s_engines = new HashMap<Integer, CostEngine>();
	
	public static CostEngine getCostEngine(int AD_Client_ID)
	{
		CostEngine engine = s_engines.get(AD_Client_ID);
		// Fallback to global engine
		if (engine == null && AD_Client_ID > 0)
		{
			engine = s_engines.get(0);
		}
		// Create Default Engine
		if (engine == null)
		{
			engine = new CostEngine();
			s_engines.put(AD_Client_ID, engine);
		}
		return engine;
	}
	
	public static void registerCostEngine(int AD_Client_ID, CostEngine engine)
	{
		s_engines.put(AD_Client_ID, engine);
	}
}
