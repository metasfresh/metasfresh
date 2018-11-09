package org.adempiere.model.engines;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.adempiere.service.ClientId;

import lombok.NonNull;

/**
 * 
 * @author teo.sarca@gmail.com
 */
public class CostEngineFactory
{
	private static final Map<ClientId, CostEngine> s_engines = new ConcurrentHashMap<ClientId, CostEngine>();

	public static CostEngine getCostEngine(@NonNull final ClientId clientId)
	{
		CostEngine engine = s_engines.get(clientId);
		// Fallback to global engine
		if (engine == null && !clientId.isSystem())
		{
			engine = s_engines.get(ClientId.SYSTEM);
		}
		// Create Default Engine
		if (engine == null)
		{
			engine = s_engines.computeIfAbsent(clientId, k -> new CostEngineImpl());
		}
		return engine;
	}
}
