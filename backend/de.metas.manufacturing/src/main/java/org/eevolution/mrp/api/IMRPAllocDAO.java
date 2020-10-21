package org.eevolution.mrp.api;

import de.metas.util.ISingletonService;

public interface IMRPAllocDAO extends ISingletonService
{
	IMRPAllocBuilder createMRPAllocs();

}
