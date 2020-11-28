package de.metas.workflow.api;

import de.metas.util.ISingletonService;

/**
 * Note about the interface name: Later on we will support different workflow engines and then this API will return
 * "WF Execution"/"WF Process" instances.
 * 
 */
public interface IWFExecutionFactory extends ISingletonService
{
	void registerListener(IWFExecutionListener listener);

	/**
	 * Nofifies the system that <code>toModel</code> has been created from <code>fromModel</code>.
	 * 
	 * @param fromModel
	 * @param toModel
	 */
	void notifyActivityPerformed(Object fromModel, Object toModel);
}
