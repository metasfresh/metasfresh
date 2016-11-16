/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/

package org.compiere.process;

import java.util.List;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.SvrProcess;

import org.adempiere.acct.api.IFactAcctCubeBL;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.IContextAware;
import org.adempiere.util.Services;
import org.compiere.model.I_PA_ReportCube;

/*
 * Populate Fact_Acct_Summary table with pre-calculated totals of
 * accounting facts, grouped by the dimensions selected in active report cubes.
 * @author Paul Bowden
 */
public class FactAcctSummary extends SvrProcess
{
	// Services
	private final transient IFactAcctCubeBL factAcctCubeBL = Services.get(IFactAcctCubeBL.class);
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);

	private int p_PA_ReportCube_ID = -1;
	private boolean p_Reset = false;
	private boolean p_Force = false;

	@Override
	protected void prepare()
	{
		final ProcessInfoParameter[] params = getParametersAsArray();
		for (final ProcessInfoParameter p : params)
		{
			final String parameterName = p.getParameterName();

			if ("Reset".equals(parameterName))
			{
				p_Reset = p.getParameterAsBoolean();
			}
			else if ("PA_ReportCube_ID".equals(parameterName))
			{
				p_PA_ReportCube_ID = p.getParameterAsInt();
			}
			else if ("Force".equals(parameterName))
			{
				p_Force = p.getParameterAsBoolean();
			}
			else
			{
				log.error("Unknown Parameter: " + parameterName);
			}
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		final List<I_PA_ReportCube> reportCubes = retriveReportCubes();
		for (final I_PA_ReportCube reportCube : reportCubes)
		{
			final String updateResultSummary = factAcctCubeBL.createFactAcctCubeUpdater()
					.setContext(this)
					.setPA_ReportCube(reportCube)
					.setResetCube(p_Reset)
					.setForceUpdate(p_Force)
					.update()
					.getResultSummary();
			addLog(updateResultSummary);
		}

		return MSG_OK;
	}

	private List<I_PA_ReportCube> retriveReportCubes()
	{
		final IContextAware context = this;
		final IQueryBuilder<I_PA_ReportCube> queryBuilder = queryBL
				.createQueryBuilder(I_PA_ReportCube.class, context)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient();

		if (p_PA_ReportCube_ID > 0)
		{
			queryBuilder.addEqualsFilter(I_PA_ReportCube.COLUMNNAME_PA_ReportCube_ID, p_PA_ReportCube_ID);
		}

		queryBuilder.orderBy()
				.addColumn(I_PA_ReportCube.COLUMNNAME_PA_ReportCube_ID) // just to have a predictible order
		;

		return queryBuilder.create()
				.list();
	}

}
