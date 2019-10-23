package org.adempiere.util.trxConstraints.api;

import org.compiere.util.DB;

import de.metas.util.ISingletonService;

/**
 * Service maintains {@link ITrxConstraints} instances for threads. A new instance is created for a thread at the first
 * time, 'getConstraints' is called. The instance is destroyed when the thread finishes.
 * 
 * @author ts
 * @see DB#getConstraints()
 */
public interface ITrxConstraintsBL extends ISingletonService
{
	/**
	 * Returns the constraints instance for the calling thread.
	 * 
	 * @return
	 */
	public ITrxConstraints getConstraints();

	/**
	 * Returns the constraints instance for the given thread.
	 * 
	 * @param thread
	 * @return
	 */
	public ITrxConstraints getConstraints(Thread thread);

	public void saveConstraints();
	
	public void restoreConstraints();

	/**
	 * 
	 * @param constraints
	 * @return true if the constraints are disabled and shall not be used/enforced
	 */
	boolean isDisabled(ITrxConstraints constraints);
}
