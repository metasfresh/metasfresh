package de.metas.inoutcandidate.api;

import de.metas.util.ISingletonService;



/**
 * Statefull singleton that contains inout candidate configuration
 * @author ts
 *
 */
public interface IInOutCandidateConfig extends ISingletonService
{
	/**
	 * This method allows it to override the default setting for the value returned by
	 * {@link #isSupportForSchedsWithoutOrderLine()}.
	 * <p>
	 * Background: for some projects, we need de.metas.contracts to act as if scheds without order lines were supported. At the
	 * same time, the project registers an {@link IInOutCandHandlerListener} that makes sure no actual
	 * {@link I_M_ShipmentSchedule}s are created at all.
	 * <p>
	 * This method assumes that it is called only once (during startup).
	 * 
	 * @param support
	 */
	void setSupportForSchedsWithoutOrderLines(boolean support);

	/**
	 * Tells every called if the module supports {@link I_M_ShipmentSchedule}s that don't reference a
	 * <code>C_OrderLine_ID</code>.
	 * <p>
	 * Background: currently this is not the case, and de.metas.contracts needs to take account of this.
	 * 
	 * @return
	 */
	boolean isSupportForSchedsWithoutOrderLine();
}
