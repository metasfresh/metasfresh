package de.metas.async.api;

import org.adempiere.util.api.IParams;

import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.model.I_C_Queue_WorkPackage_Param;
import de.metas.util.ISingletonService;

public interface IWorkpackageParamDAO extends ISingletonService
{
	IParams retrieveWorkpackageParams(I_C_Queue_WorkPackage queueWorkPackageRecord);

	/**
	 * Sets given <code>parameterValue</code> to {@link I_C_Queue_WorkPackage_Param}.
	 *
	 * This method will figure out exactly where the value will be set, based on <code>parameterValue</code>'s class.
	 */
	void setParameterValue(I_C_Queue_WorkPackage_Param queueWorkpackageParamRecord, Object parameterValue);

	void setParameterValue(I_C_Queue_WorkPackage queueWorkPackageRecord, String parameterName, Object parameterValue);
}
