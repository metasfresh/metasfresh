package org.adempiere.acct.api.impl;

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


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.acct.api.IFactAcctCubeUpdater;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.IContextAware;
import org.adempiere.util.Check;
import org.compiere.model.I_PA_ReportCube;
import org.compiere.util.DB;
import org.compiere.util.KeyNamePair;

import de.metas.acct.model.I_Fact_Acct_Summary;

/*package*/class FactAcctCubeUpdater implements IFactAcctCubeUpdater
{
	private final Logger log = LogManager.getLogger(getClass());

	// Parameters
	private IContextAware _context;
	private I_PA_ReportCube _reportCube;
	private boolean _resetCube;
	private boolean _forceUpdate;

	// Status
	private String _resultSummary = null;

	public FactAcctCubeUpdater()
	{
		super();
	}

	@Override
	public IFactAcctCubeUpdater setContext(final IContextAware context)
	{
		this._context = context;
		return this;
	}

	private IContextAware getContext()
	{
		Check.assumeNotNull(_context, "_context not null");
		return _context;
	}

	@Override
	public IFactAcctCubeUpdater setPA_ReportCube(final I_PA_ReportCube reportCube)
	{
		this._reportCube = reportCube;
		return this;
	}

	private I_PA_ReportCube getPA_ReportCube()
	{
		Check.assumeNotNull(_reportCube, "reportCube not null");
		return _reportCube;
	}

	@Override
	public IFactAcctCubeUpdater setResetCube(final boolean resetCube)
	{
		this._resetCube = resetCube;
		return this;
	}

	private boolean isResetCube()
	{
		return _resetCube;
	}

	@Override
	public IFactAcctCubeUpdater setForceUpdate(final boolean forceUpdate)
	{
		this._forceUpdate = forceUpdate;
		return this;
	}

	private boolean isForceUpdate()
	{
		return this._forceUpdate;
	}

	private int getPA_ReportCube_ID()
	{
		return getPA_ReportCube().getPA_ReportCube_ID();
	}

	@Override
	public String getResultSummary()
	{
		return _resultSummary;
	}

	private void setResultSummary(final String resultSummary)
	{
		this._resultSummary = resultSummary;

		//
		// Log (fine) the result of our updating
		log.debug("Result summary: {}", _resultSummary);
	}

	@Override
	public IFactAcctCubeUpdater update()
	{
		//
		// Extract parameters
		final I_PA_ReportCube paReportCube = getPA_ReportCube();
		final int paReportCubeId = getPA_ReportCube_ID();
		final Timestamp lastRecalculated = paReportCube.getLastRecalculated();
		final String paReportCubeName = paReportCube.getName();
		final boolean reset = isResetCube();
		final String trxName = getContext().getTrxName();

		final StringBuilder resultSummary = new StringBuilder(paReportCubeName + ": ");

		String where = " WHERE PA_ReportCube_ID = " + paReportCubeId;

		//
		// SQL: C_Period_IDs IN list
		final String sqlPeriodIn; // e.g. "(PeriodId1, PeriodId2, ...)"
		final boolean filterOnlyChangedPeriods = lastRecalculated != null && !reset;
		if (filterOnlyChangedPeriods)
		{
			final List<Integer> periodIds = getChangedPeriodIds();
			if (periodIds.isEmpty())
			{
				setResultSummary("Nothing to update in " + paReportCubeName);
				return this;
			}

			sqlPeriodIn = DB.buildSqlList(periodIds);
			where += (" AND C_Period_ID IN " + sqlPeriodIn);
		}
		else
		{
			sqlPeriodIn = null; // i.e. shall not be used
		}

		//
		// Lock the report cube
		lockReportCube();

		try
		{
			//
			// Delete from Fact_Acct_Summary
			{
				final long startMillis = System.currentTimeMillis();
				final int deleted = deleteFactAcctSummary(where);
				final long elapsedSec = (System.currentTimeMillis() - startMillis) / 1000;
				resultSummary.append("Deleted " + deleted + " in " + elapsedSec + " s; ");
				log.debug(resultSummary.toString());
			}

			//
			// Insert into Fact_Acct_Summary
			StringBuilder insert = new StringBuilder("INSERT " +
					"INTO FACT_ACCT_SUMMARY (PA_ReportCube_ID , AD_Client_ID, " +
					"AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, " +
					"C_AcctSchema_ID, Account_ID, PostingType, " +
					"GL_Budget_ID, C_Period_ID, DateAcct, AmtAcctDr, AmtAcctCr, Qty");

			final StringBuilder select = new StringBuilder(" ) SELECT " +
					"?, f.AD_CLIENT_ID, f.AD_ORG_ID, " +
					"max(f.Created), max(f.CreatedBy), max(f.Updated), max(f.UpdatedBy), 'Y', " +
					"f.C_ACCTSCHEMA_ID, f.ACCOUNT_ID, f.POSTINGTYPE, GL_Budget_ID, " +
					"p.c_period_id," +
					"p.StartDate, " + // DateAcct
					"COALESCE(SUM(AmtAcctDr),0), COALESCE(SUM(AmtAcctCr),0), " +
					"COALESCE(SUM(Qty),0)");
			String from = " FROM fact_acct f " +
					" INNER JOIN C_Period p ON ( f.DateAcct BETWEEN p.StartDate AND p.EndDate ) " +
					" INNER JOIN C_Year y ON ( p.C_Year_ID = y.C_Year_ID ) " +
					" WHERE p.PeriodType = 'S' " +
					" AND y.C_Calendar_ID = ? ";
			if (filterOnlyChangedPeriods)
			{
				from += "AND  p.C_Period_ID IN " + sqlPeriodIn;
			}

			final StringBuilder groups = new StringBuilder(" GROUP BY " +
					"f.AD_CLIENT_ID, f.AD_ORG_ID, f.C_ACCTSCHEMA_ID, f.ACCOUNT_ID, " +
					"f.POSTINGTYPE, GL_Budget_ID, p.c_period_id, p.StartDate ");

			final List<String> dimensionColumnNames = getDimensionColumnNames(paReportCube);
			for (final String dim : dimensionColumnNames)
			{
				insert.append(", " + dim);
				select.append(", f." + dim);
				groups.append(", f." + dim);
			}

			final String sql = insert.append(select.toString()).append(from).append(groups.toString()).toString();
			log.debug(sql);
			final Object[] sqlParams = new Object[] { paReportCubeId, paReportCube.getC_Calendar_ID() };

			final long startMillis = System.currentTimeMillis();
			final int rows = DB.executeUpdateEx(sql, sqlParams, trxName);
			final long seconds = (System.currentTimeMillis() - startMillis) / 1000;

			final String insertResult = "Inserted " + rows + " in " + seconds + " s.";
			log.debug(insertResult);
			resultSummary.append(insertResult);
		}
		catch (DBException e)
		{
			// failure results in null timestamp => rebuild on next run
			// nothing else to do
			log.debug(paReportCubeName + " update failed:" + e.getMessage());
		}
		finally
		{
			unlockReportCube();
		}

		setResultSummary(resultSummary.toString());

		return this;
	}

	/**
	 * 
	 * @return C_Period_ID/Name pairs
	 */
	private KeyNamePair[] retrieveChangedPeriods()
	{
		final String sql = "SELECT DISTINCT p.C_Period_ID, p.Name FROM C_Period p " +
				"INNER JOIN C_Year y ON (y.C_Year_ID=p.C_Year_ID) " +
				"INNER JOIN PA_ReportCube c ON (c.C_Calendar_ID = y.C_Calendar_ID) " +
				"INNER JOIN Fact_Acct fact ON (fact.dateacct between p.startdate and p.enddate " +
				"                      and fact.ad_client_id = c.ad_client_id) " +
				"WHERE c.PA_ReportCube_ID = ? " +
				"AND fact.updated > c.LastRecalculated " +
				"AND p.periodtype='S' " // standard period
		;
		log.debug(sql);

		final int paReportCubeId = getPA_ReportCube_ID();

		final long startMillis = System.currentTimeMillis();
		final KeyNamePair[] changedPeriods = DB.getKeyNamePairs(sql, false, paReportCubeId);

		final long elapsedSec = (System.currentTimeMillis() - startMillis) / 1000;
		log.debug("Selecting changed periods took:" + elapsedSec + "s");

		return changedPeriods;
	}

	private List<Integer> getChangedPeriodIds()
	{
		final List<Integer> periodIds = new ArrayList<Integer>();
		final StringBuilder periodNames = new StringBuilder();

		final KeyNamePair[] changedPeriods = retrieveChangedPeriods();
		if (!Check.isEmpty(changedPeriods))
		{
			for (final KeyNamePair p : changedPeriods)
			{
				final int periodId = p.getKey();
				if (periodId <= 0)
				{
					continue;
				}
				if (periodIds.contains(periodId))
				{
					continue;
				}
				periodIds.add(periodId);

				final String periodName = p.getName();
				if (periodNames.length() > 0)
				{
					periodNames.append(periodName);
				}
			}
		}

		if (periodIds.isEmpty())
		{
			log.debug("No periods to update found");
		}
		else
		{
			log.debug("Periods to update: {}", periodNames);
		}

		return periodIds;
	}

	/**
	 * @return max of {@link I_Fact_Acct_Summary#getUpdated()}
	 */
	private Timestamp retrieveLastUpdated()
	{
		final int paReportCubeId = getPA_ReportCube_ID();
		final String trxName = getContext().getTrxName();
		// set timestamp
		final String sql = "SELECT max(fas.Updated)" +
				" FROM Fact_Acct_Summary fas" +
				" WHERE fas.PA_ReportCube_ID = ?";
		final Object[] sqlParams = new Object[] { paReportCubeId };
		final Timestamp lastUpdated = DB.getSQLValueTSEx(trxName, sql, sqlParams);

		return lastUpdated;
	}

	private void lockReportCube()
	{
		final boolean force = isForceUpdate();
		if (!force)
		{
			return;
		}

		final int paReportCubeId = getPA_ReportCube_ID();
		final String trxName = getContext().getTrxName();

		final String sql = "UPDATE PA_ReportCube SET Processing = 'Y'"
				+ " WHERE Processing = 'N' AND PA_ReportCube_ID = ?";
		final Object[] sqlParams = new Object[] {
				paReportCubeId,
		};

		final int locked = DB.executeUpdateEx(sql, sqlParams, trxName);
		if (locked != 1)
		{
			throw new AdempiereException("Unable to lock cube for update:" + getPA_ReportCube().getName());
		}
	}

	private void unlockReportCube()
	{
		final Timestamp lastRecalculatedNew = retrieveLastUpdated();
		final int paReportCubeId = getPA_ReportCube_ID();
		final String trxName = getContext().getTrxName();

		final String sql = "UPDATE PA_ReportCube SET Processing=?, LastRecalculated=?"
				+ " WHERE PA_ReportCube_ID=?";
		final Object[] sqlParams = new Object[] {
				false // Processing
				, lastRecalculatedNew // LastRecalculated
				, paReportCubeId // PA_ReportCube_ID
		};

		DB.executeUpdateEx(sql, sqlParams, trxName);
	}

	private int deleteFactAcctSummary(final String where)
	{
		final String trxName = getContext().getTrxName();

		// delete
		final String sql = "DELETE FROM Fact_Acct_Summary fas " + where;
		log.debug("Delete sql: " + sql);

		final int deletedNo = DB.executeUpdateEx(sql, trxName);
		return deletedNo;
	}

	private List<String> getDimensionColumnNames(final I_PA_ReportCube paReportCube)
	{
		final List<String> values = new ArrayList<String>();
		if (paReportCube.isProductDim())
			values.add("M_Product_ID");
		if (paReportCube.isBPartnerDim())
			values.add("C_BPartner_ID");
		if (paReportCube.isProjectDim())
			values.add("C_Project_ID");
		if (paReportCube.isOrgTrxDim())
			values.add("AD_OrgTrx_ID");
		if (paReportCube.isSalesRegionDim())
			values.add("C_SalesRegion_ID");
		if (paReportCube.isActivityDim())
			values.add("C_Activity_ID");
		if (paReportCube.isCampaignDim())
			values.add("C_Campaign_ID");
		if (paReportCube.isLocToDim())
			values.add("C_LocTo_ID");
		if (paReportCube.isLocFromDim())
			values.add("C_LocFrom_ID");
		if (paReportCube.isUser1Dim())
			values.add("User1_ID");
		if (paReportCube.isUser2Dim())
			values.add("User2_ID");
		if (paReportCube.isUserElement1Dim())
			values.add("UserElement1_ID");
		if (paReportCube.isUserElement2Dim())
			values.add("UserElement2_ID");
		if (paReportCube.isSubAcctDim())
			values.add("C_SubAcct_ID");
		if (paReportCube.isProjectPhaseDim())
			values.add("C_ProjectPhase_ID");
		if (paReportCube.isProjectTaskDim())
			values.add("C_ProjectTask_ID");

		// --(CASE v.IsGL_Category_ID WHEN 'Y' THEN f."GL_Category_ID END) GL_Category_ID

		return values;
	}
}
