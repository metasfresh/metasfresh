package de.metas.async.api;

import org.compiere.model.I_AD_User;

import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.util.ILoggable;
import de.metas.util.ISingletonService;

public interface IWorkPackageBL extends ISingletonService
{
	ILoggable createLoggable(I_C_Queue_WorkPackage workPackage);

	/**
	 *
	 * @param workPackage doesn't have to be saved
	 * @return
	 */
	I_AD_User getUserInChargeOrNull (I_C_Queue_WorkPackage workPackage);
}
