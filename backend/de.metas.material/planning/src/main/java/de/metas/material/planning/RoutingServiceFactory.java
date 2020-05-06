package de.metas.material.planning;

import de.metas.material.planning.impl.DefaultRoutingServiceImpl;

/**
 * Routing Service Factory
 * 
 * @author Teo Sarca
 */
public class RoutingServiceFactory
{
	public static final transient RoutingServiceFactory instance = new RoutingServiceFactory();
	
	private final RoutingService routingService = new DefaultRoutingServiceImpl();

	public static RoutingServiceFactory get()
	{
		return instance;
	}

	private RoutingServiceFactory()
	{
	}

	public RoutingService getRoutingService()
	{
		return routingService;
	}
}
