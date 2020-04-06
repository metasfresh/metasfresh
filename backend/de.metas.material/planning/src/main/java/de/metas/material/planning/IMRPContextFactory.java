package de.metas.material.planning;

import de.metas.util.ISingletonService;

public interface IMRPContextFactory extends ISingletonService
{

	IMutableMRPContext createInitialMRPContext();

	IMutableMRPContext createMRPContext(IMaterialPlanningContext mrpContext);

	/**
	 * Creates {@link IMaterialPlanningContext} from a given initial context and applying the given {@link IMRPSegment} on it.
	 *
	 * @param mrpContext0
	 * @param mrpSegment
	 * @return MRP context
	 */
	IMaterialPlanningContext createMRPContext(IMaterialPlanningContext mrpContext0, IMRPSegment mrpSegment);
}
