package de.metas.material.planning;

import de.metas.util.ISingletonService;

public interface IMRPContextFactory extends ISingletonService
{

	IMutableMRPContext createInitialMRPContext();

}
