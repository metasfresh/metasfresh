package org.adempiere.acct.api;

import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_PA_ReportCube;

import de.metas.acct.model.I_Fact_Acct_Summary;

public interface IFactAcctCubeUpdater
{
	/**
	 * Execute {@link I_Fact_Acct_Summary} update
	 */
	IFactAcctCubeUpdater update();

	IFactAcctCubeUpdater setContext(IContextAware context);

	IFactAcctCubeUpdater setPA_ReportCube(final I_PA_ReportCube reportCube);

	IFactAcctCubeUpdater setResetCube(final boolean resetCube);

	/**
	 * 
	 * @param forceUpdate if true, the {@link #update()} will be performed even if {@link I_PA_ReportCube#isProcessing()} is set
	 * @return
	 */
	IFactAcctCubeUpdater setForceUpdate(final boolean forceUpdate);

	/**
	 * @return result summary string or null
	 */
	String getResultSummary();

}
