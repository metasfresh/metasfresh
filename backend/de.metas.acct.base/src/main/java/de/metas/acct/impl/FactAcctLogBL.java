package de.metas.acct.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.ILoggable;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.adempiere.util.agg.key.IAggregationKeyBuilder;
import org.adempiere.util.collections.MapReduceAggregator;
import org.compiere.util.TimeUtil;
import org.compiere.util.TrxRunnableAdapter;

import de.metas.acct.IFactAcctLogBL;
import de.metas.acct.IFactAcctLogDAO;
import de.metas.acct.IFactAcctLogIterable;
import de.metas.acct.IFactAcctSummaryKey;
import de.metas.acct.model.I_Fact_Acct_Log;
import de.metas.acct.model.I_Fact_Acct_Summary;
import de.metas.acct.model.X_Fact_Acct_Log;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class FactAcctLogBL implements IFactAcctLogBL
{
	@Override
	public void processAll(final Properties ctx, final int limit)
	{
		final IFactAcctLogDAO factAcctLogDAO = Services.get(IFactAcctLogDAO.class);
		final ITrxManager trxManager = Services.get(ITrxManager.class);

		trxManager.run(new TrxRunnableAdapter()
		{

			@Override
			public void run(final String localTrxName) throws Exception
			{
				try (final IFactAcctLogIterable logs = factAcctLogDAO.tagAndRetrieve(ctx, limit))
				{
					process(logs);
				}
			}
		});
	}

	public void process(final IFactAcctLogIterable logs)
	{
		final ILoggable loggable = Loggables.get();

		//
		// Update Fact_Acct_Summary
		final FactAcctSummaryUpdater factAcctSummaryUpdater = new FactAcctSummaryUpdater();
		for (final I_Fact_Acct_Log log : logs)
		{
			factAcctSummaryUpdater.add(log);
		}
		factAcctSummaryUpdater.closeAllGroups();

		//
		// Update Fact_Acct_EndingBalance
		Services.get(IFactAcctLogDAO.class).updateFactAcctEndingBalanceForTag(logs.getProcessingTag());

		//
		// Delete all processed logs
		logs.deleteAll();

		loggable.addLog("Processed {0} {1} records", factAcctSummaryUpdater.getItemsCount(), I_Fact_Acct_Log.Table_Name);
		loggable.addLog("Created/Updated {0} {1} records", factAcctSummaryUpdater.getGroupsCount(), I_Fact_Acct_Summary.Table_Name);
	}

	private static class FactAcctSummaryUpdater extends MapReduceAggregator<FactAcctGroup, I_Fact_Acct_Log>
	{
		public FactAcctSummaryUpdater()
		{
			super();
			setGroupsBufferSize(1); // IMPORTANT: keep only one group in memory because we are also updating next groups when a current group is updated
			setItemAggregationKeyBuilder(FactAcctSummaryKeyBuilder.instance);
		}

		@Override
		protected FactAcctGroup createGroup(final Object itemHashKey, final I_Fact_Acct_Log log)
		{
			return FactAcctGroup.getCreatedForLog(log);
		}

		@Override
		protected void closeGroup(final FactAcctGroup group)
		{
			group.close();
		}

		@Override
		protected void addItemToGroup(final FactAcctGroup group, final I_Fact_Acct_Log log)
		{
			group.add(log);
		}
	}

	private static final class FactAcctGroup
	{
		private final transient IFactAcctLogDAO factAcctLogDAO = Services.get(IFactAcctLogDAO.class);

		public static final FactAcctGroup getCreatedForLog(final I_Fact_Acct_Log log)
		{
			return new FactAcctGroup(log);
		}

		private final Object contextProvider;
		private final Properties ctx;
		private final IFactAcctSummaryKey key;
		private BigDecimal amtAcctDr_ToAdd;
		private BigDecimal amtAcctCr_ToAdd;
		private BigDecimal qty_ToAdd;

		private FactAcctGroup(final I_Fact_Acct_Log log)
		{
			super();

			contextProvider = log;
			ctx = InterfaceWrapperHelper.getCtx(log);
			key = FactAcctSummaryKey.of(log);

			resetAmounts();
		}

		private final I_Fact_Acct_Summary getCreateFactAcctSummary()
		{
			final Date dateAcct = key.getDateAcct();

			//
			// Retrieve existing summary record
			final I_Fact_Acct_Summary factAcctSummaryExisting = factAcctLogDAO.retrieveLastMatchingFactAcctSummary(ctx, key);

			//
			// If the retrieved summary is precisely for our DateAcct, we can use it right away
			if (factAcctSummaryExisting != null && factAcctSummaryExisting.getDateAcct().getTime() == dateAcct.getTime())
			{
				return factAcctSummaryExisting;
			}
			//
			// Create new
			else
			{
				final I_Fact_Acct_Summary factAcctSummary = InterfaceWrapperHelper.newInstance(I_Fact_Acct_Summary.class, contextProvider);

				// Copy the values from last matching summary
				if (factAcctSummaryExisting != null)
				{
					InterfaceWrapperHelper.copyValues(factAcctSummaryExisting, factAcctSummary);
				}
				else
				{
					factAcctSummary.setAmtAcctDr(BigDecimal.ZERO);
					factAcctSummary.setAmtAcctCr(BigDecimal.ZERO);
					factAcctSummary.setAmtAcctDr_YTD(BigDecimal.ZERO);
					factAcctSummary.setAmtAcctCr_YTD(BigDecimal.ZERO);
					factAcctSummary.setQty(BigDecimal.ZERO);
				}

				// Set all dimensions & return it
				Check.assume(factAcctSummary.getAD_Client_ID() == key.getAD_Client_ID(), "Fact_Acct_Summary shall have the same AD_Client_ID as the log");
				factAcctSummary.setAD_Org_ID(key.getAD_Org_ID());
				factAcctSummary.setAccount_ID(key.getC_ElementValue_ID());
				factAcctSummary.setC_AcctSchema_ID(key.getC_AcctSchema_ID());
				factAcctSummary.setPostingType(key.getPostingType());
				factAcctSummary.setC_Period_ID(key.getC_Period_ID());
				factAcctSummary.setC_Year_ID(factAcctSummary.getC_Period().getC_Year_ID());
				factAcctSummary.setDateAcct(TimeUtil.asTimestamp(dateAcct));
				factAcctSummary.setPA_ReportCube_ID(key.getPA_ReportCube_ID());

				// If we copied the current summary from an existing summary which is from last year, we need to reset the YearToDate amounts
				if (factAcctSummaryExisting != null && factAcctSummaryExisting.getC_Year_ID() != factAcctSummary.getC_Year_ID())
				{
					factAcctSummary.setAmtAcctDr_YTD(BigDecimal.ZERO);
					factAcctSummary.setAmtAcctCr_YTD(BigDecimal.ZERO);
				}

				InterfaceWrapperHelper.save(factAcctSummary);

				return factAcctSummary;
			}
		}

		private final void resetAmounts()
		{
			amtAcctDr_ToAdd = BigDecimal.ZERO;
			amtAcctCr_ToAdd = BigDecimal.ZERO;
			qty_ToAdd = BigDecimal.ZERO;
		}

		public void add(final I_Fact_Acct_Log log)
		{
			final String logAction = log.getAction();
			final BigDecimal amtAcctDr_Diff;
			final BigDecimal amtAcctCr_Diff;
			final BigDecimal qty_Diff;
			if (X_Fact_Acct_Log.ACTION_Insert.equals(logAction))
			{
				amtAcctDr_Diff = log.getAmtAcctDr();
				amtAcctCr_Diff = log.getAmtAcctCr();
				qty_Diff = log.getQty();
			}
			else if (X_Fact_Acct_Log.ACTION_Delete.equals(logAction))
			{
				amtAcctDr_Diff = log.getAmtAcctDr().negate();
				amtAcctCr_Diff = log.getAmtAcctCr().negate();
				qty_Diff = log.getQty().negate();
			}
			else
			{
				throw new IllegalStateException("Log action not supported: " + logAction + " (" + log + ")");
			}

			//
			// Update the summary record
			amtAcctDr_ToAdd = amtAcctDr_ToAdd.add(amtAcctDr_Diff);
			amtAcctCr_ToAdd = amtAcctCr_ToAdd.add(amtAcctCr_Diff);
			qty_ToAdd = qty_ToAdd.add(qty_Diff);
		}

		private final boolean hasChanges()
		{
			return amtAcctDr_ToAdd.signum() != 0 || amtAcctCr_ToAdd.signum() != 0 || qty_ToAdd.signum() != 0;
		}

		public void close()
		{
			if (!hasChanges())
			{
				return;
			}

			//
			// Create the summary record for our particular DateAcct
			final I_Fact_Acct_Summary factAcctSummary = getCreateFactAcctSummary();
			final IQueryFilter<I_Fact_Acct_Summary> currentYearFilter = new EqualsQueryFilter<>(I_Fact_Acct_Summary.COLUMN_C_Year_ID, factAcctSummary.getC_Year_ID());

			//
			// Update all summary records which are >= particular DateAcct
			factAcctLogDAO.retrieveCurrentAndNextMatchingFactAcctSummaryQuery(ctx, key)
					.create()
					.updateDirectly()
					//
					// Amounts: from beginning to Date
					.addAddValueToColumn(I_Fact_Acct_Summary.COLUMNNAME_AmtAcctDr, amtAcctDr_ToAdd)
					.addAddValueToColumn(I_Fact_Acct_Summary.COLUMNNAME_AmtAcctCr, amtAcctCr_ToAdd)
					.addAddValueToColumn(I_Fact_Acct_Summary.COLUMNNAME_Qty, qty_ToAdd)
					//
					// Amounts: Year to Date
					.addAddValueToColumn(I_Fact_Acct_Summary.COLUMNNAME_AmtAcctDr_YTD, amtAcctDr_ToAdd, currentYearFilter)
					.addAddValueToColumn(I_Fact_Acct_Summary.COLUMNNAME_AmtAcctCr_YTD, amtAcctCr_ToAdd, currentYearFilter)
					//
					.execute();

			//
			// Reset the amounts because we processed them
			resetAmounts();
		}

	}

	private static final class FactAcctSummaryKeyBuilder implements IAggregationKeyBuilder<I_Fact_Acct_Log>
	{
		public static final transient FactAcctSummaryKeyBuilder instance = new FactAcctSummaryKeyBuilder();

		private FactAcctSummaryKeyBuilder()
		{
			super();
		}

		@Override
		public String buildKey(final I_Fact_Acct_Log item)
		{
			return FactAcctSummaryKey.of(item).asString();
		}

		@Override
		public List<String> getDependsOnColumnNames()
		{
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean isSame(final I_Fact_Acct_Log item1, final I_Fact_Acct_Log item2)
		{
			throw new UnsupportedOperationException();
		}
	}
}
