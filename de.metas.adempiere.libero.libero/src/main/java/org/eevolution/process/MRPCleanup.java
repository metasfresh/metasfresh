package org.eevolution.process;

import org.eevolution.mrp.api.IMRPResult;

import de.metas.material.planning.IMaterialPlanningContext;

/**
 * Cleanup (remove) MRP generated data
 * 
 * @author tsa
 * 
 */
public class MRPCleanup extends AbstractMRPProcess
{

	@Override
	protected IMRPResult run(final IMaterialPlanningContext mrpContext)
	{
		return mrpExecutorService.cleanup(mrpContext);
	}
}
