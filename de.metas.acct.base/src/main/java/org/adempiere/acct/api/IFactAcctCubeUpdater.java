package org.adempiere.acct.api;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.model.IContextAware;
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
