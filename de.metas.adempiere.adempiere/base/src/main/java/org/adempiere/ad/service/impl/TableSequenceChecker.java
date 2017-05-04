package org.adempiere.ad.service.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import org.adempiere.ad.service.ISequenceDAO;
import org.adempiere.ad.service.ISystemBL;
import org.adempiere.ad.service.ITableSequenceChecker;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.ILoggable;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Sequence;
import org.compiere.model.I_AD_System;
import org.compiere.model.I_AD_Table;
import org.compiere.model.MSequence;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TrxRunnable2;
import org.slf4j.Logger;

import ch.qos.logback.classic.Level;
import de.metas.logging.LogManager;

public class TableSequenceChecker implements ITableSequenceChecker
{
	private final transient Logger log = LogManager.getLogger(getClass());
	private final ISequenceDAO sequenceDAO = Services.get(ISequenceDAO.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private final Properties ctx;
	private String trxName = ITrx.TRXNAME_None;
	private boolean sequenceRangeCheck = false;
	private boolean failOnFirstError = true;
	private List<I_AD_Table> tables = null;

	public TableSequenceChecker(final Properties ctx)
	{
		super();
		this.ctx = ctx;
	}

	@Override
	public ITableSequenceChecker setTrxName(final String trxName)
	{
		this.trxName = trxName;

		return this;
	}

	private ILoggable getLogger()
	{
		return Loggables.get().withLogger(log, Level.INFO);
	}

	@Override
	public ITableSequenceChecker setSequenceRangeCheck(boolean sequenceRangeCheck)
	{
		this.sequenceRangeCheck = sequenceRangeCheck;
		return this;
	}

	private boolean isSequenceRangeCheck()
	{
		return this.sequenceRangeCheck;
	}

	@Override
	public ITableSequenceChecker setFailOnFirstError(boolean failOnFirstError)
	{
		this.failOnFirstError = failOnFirstError;
		return this;
	}

	private boolean isFailOnFirstError()
	{
		return this.failOnFirstError;
	}

	@Override
	public ITableSequenceChecker setTables(final List<I_AD_Table> tables)
	{
		Check.assumeNotEmpty(tables, "tables not empty");
		this.tables = new ArrayList<I_AD_Table>(tables);

		return this;
	}

	@Override
	public ITableSequenceChecker setTable(final I_AD_Table table)
	{
		Check.assumeNotNull(table, "table not null");
		this.tables = Collections.singletonList(table);

		return this;
	}

	private Iterator<I_AD_Table> retrieveTables()
	{
		if (tables != null)
		{
			return tables.iterator();
		}

		final List<I_AD_Table> tables = Services.get(IADTableDAO.class).retrieveAllTables(ctx, ITrx.TRXNAME_None);
		return tables.iterator();
	}

	@Override
	public void run()
	{
		final Iterator<I_AD_Table> tables = retrieveTables();

		int countAll = 0;
		int countChecked = 0;
		while (tables.hasNext())
		{
			final I_AD_Table table = tables.next();
			countAll++;
			if (!table.isActive())
			{
				continue;
			}

			if (table.isView())
			{
				continue;
			}

			createOrUpdateSequence(table);
			countChecked++;
		}

		getLogger().addLog("Checked " + countChecked + " of " + countAll + " tables");
	}

	private void createOrUpdateSequence(final I_AD_Table table)
	{
		final String tableName = table.getTableName();

		final String trxNameToUse;
		if (trxManager.isNull(trxName))
		{
			trxNameToUse = ITrx.TRXNAME_None;
		}
		else
		{
			trxNameToUse = trxName;
		}

		trxManager.run(trxNameToUse, new TrxRunnable2()
		{

			@Override
			public void run(final String localTrxName) throws Exception
			{
				createOrUpdateTableSequence(ctx, tableName, localTrxName);

				// gh #941 *always* deal with native sequences, even if there is no AD_Sequence or no AD_Column
				createOrUpdateNativeSequence(tableName, localTrxName);
			}

			@Override
			public boolean doCatch(Throwable e) throws Throwable
			{
				if (isFailOnFirstError())
				{
					throw e;
				}
				else
				{
					logError(tableName, e);
				}
				return true; // do ROLLBACK
			}

			@Override
			public void doFinally()
			{
				// nothing
			}

		});
	}

	/**
	 * Create/Update {@link I_AD_Sequence} for the given {@code tableName}.
	 * 
	 * @param ctx context
	 * @param TableName table name
	 * @param trxName transaction
	 * @return created/updated sequence; never return null
	 */
	private final I_AD_Sequence createOrUpdateTableSequence(final Properties ctx, final String tableName, final String trxName)
	{
		I_AD_Sequence sequence = sequenceDAO.retrieveTableSequenceOrNull(ctx, tableName, trxName);
		if (sequence == null)
		{
			final Properties ctxSystem = Env.deriveCtx(ctx);
			Env.setContext(ctxSystem, Env.CTXNAME_AD_Client_ID, 0);
			Env.setContext(ctxSystem, Env.CTXNAME_AD_Org_ID, 0);
			final IContextAware contextProvider = PlainContextAware.newWithTrxName(ctxSystem, trxName);

			final I_AD_Sequence sequenceNew = InterfaceWrapperHelper.newInstance(I_AD_Sequence.class, contextProvider, true); // useCLientOrgFromProvider=true
			sequenceNew.setAD_Org_ID(0);
			sequenceNew.setName(tableName);
			sequenceNew.setDescription("Table " + tableName);
			sequenceNew.setIsTableID(true);
			sequenceNew.setIsActive(true);
			sequence = sequenceNew;
		}

		//
		// Correct Sequence Name (if needed)
		if (!tableName.equals(sequence.getName()))
		{
			sequence.setName(tableName);
		}

		//
		// Check ID range (if needed)
		if (isSequenceRangeCheck())
		{
			updateTableCurrentNext(sequence);
		}

		//
		// Save sequence
		InterfaceWrapperHelper.save(sequence);

		//
		// Log
		final SequenceChangeLog changeLog = new SequenceChangeLog(sequence);
		changeLog.setNewValues(sequence);
		log(changeLog);

		return sequence;
	}	// createTableSequence

	/**
	 * Checks the given {@code seq}'s {@code CurrentNext} and {@code CurrentNextSys} and updates them if necessary.
	 * 
	 * NOTE: this method is not saving the modified sequence
	 * 
	 * @return true if updated
	 */
	private final boolean updateTableCurrentNext(final I_AD_Sequence seq)
	{
		if (!seq.isTableID())
		{
			getLogger().addLog("Skip checking sequence {} because it has isTableID=false.", seq);
			return false;
		}

		final String tableName = seq.getName();

		//
		// metas: 03863: Skip inactive sequences
		if (!seq.isActive())
		{
			getLogger().addLog("Skip checking sequence {} because it has isActive=false.", seq);
			return false;
		}

		//
		// Make sure we have one single primary key
		final String keyColumnName = tableName + "_ID";
		final int keyColumnId = DB.getSQLValue(null, "SELECT MAX(c.AD_Column_ID) "
				+ "FROM AD_Table t"
				+ " INNER JOIN AD_Column c ON (t.AD_Table_ID=c.AD_Table_ID) "
				+ "WHERE t.TableName='" + tableName + "'"
				+ " AND c.ColumnName='" + keyColumnName + "'");
		if (keyColumnId <= 0)
		{
			getLogger().addLog("Skip checking sequence {} because there is no key column name {}", seq, keyColumnName);
			return false;
		}

		//
		// Retrieve from AD_System: IDRangeEnd
		final Properties ctx = InterfaceWrapperHelper.getCtx(seq);
		final I_AD_System system = Services.get(ISystemBL.class).get(ctx);
		int IDRangeEnd = 0;
		if (system != null && system.getIDRangeEnd() != null)
		{
			IDRangeEnd = system.getIDRangeEnd().intValue();
		}

		boolean changed = false;
		String info = null;

		//
		// Check/Update CurrentNext
		{
			String sql = "SELECT MAX(" + keyColumnName + ") FROM " + tableName;
			if (IDRangeEnd > 0)
				sql += " WHERE " + tableName + "_ID < " + IDRangeEnd;
			int maxTableID = DB.getSQLValue(ITrx.TRXNAME_None, sql);
			if (maxTableID < MSequence.INIT_NO)
				maxTableID = MSequence.INIT_NO - 1;
			maxTableID++;		// Next
			if (seq.getCurrentNext() < maxTableID)
			{
				seq.setCurrentNext(maxTableID);
				info = "CurrentNext=" + maxTableID;
				changed = true;
			}
		}

		//
		// Check/Update CurrentNextSys
		{
			final String sql = "SELECT MAX(" + keyColumnName + ") FROM " + tableName + " WHERE " + keyColumnName + " < " + MSequence.INIT_NO;
			int maxTableSysID = DB.getSQLValue(ITrx.TRXNAME_None, sql);
			if (maxTableSysID <= 0)
				maxTableSysID = MSequence.INIT_SYS_NO - 1;
			maxTableSysID++;	// Next
			if (seq.getCurrentNextSys() < maxTableSysID)
			{
				seq.setCurrentNextSys(maxTableSysID);
				if (info == null)
					info = "CurrentNextSys=" + maxTableSysID;
				else
					info += " - CurrentNextSys=" + maxTableSysID;
				changed = true;
			}
		}

		//
		// Log
		if (info != null && log.isDebugEnabled())
		{
			log.debug(seq.getName() + " - " + info);
		}

		return changed;
	}	// validate

	/**
	 * Invokes the DB function {@code dba_seq_check_native()} with the given {@code tableName}.
	 *  
	 * @task https://github.com/metasfresh/metasfresh/issues/941
	 */
	private void createOrUpdateNativeSequence(final String tableName, final String trxName)
	{
		DB.executeFunctionCallEx(trxName, "select dba_seq_check_native(?)", new Object[] { tableName });
	}
	
	private void log(final SequenceChangeLog changeLog)
	{
		if (!changeLog.isChange())
		{
			return;
		}

		getLogger().addLog(changeLog.toString());
	}

	private final void logError(final String tableName, final Throwable e)
	{
		getLogger().addLog("Sequence " + tableName + ": " + e.getLocalizedMessage());
		log.warn(e.getLocalizedMessage(), e);
	}

	private static class SequenceChangeLog
	{
		private boolean newSequence = false;
		private String sequenceName;
		private String sequenceNameNew;
		private int currentNext;
		private int currentNextNew;
		private int currentNextSys;
		private int currentNextSysNew;
		private boolean newValuesSet = false;

		public SequenceChangeLog(final I_AD_Sequence sequence)
		{
			Check.assumeNotNull(sequence, "sequence not null");
			this.newSequence = sequence.getAD_Sequence_ID() <= 0;
			this.sequenceName = sequence.getName();
			this.currentNext = sequence.getCurrentNext();
			this.currentNextSys = sequence.getCurrentNextSys();
		}

		public void setNewValues(final I_AD_Sequence sequence)
		{
			Check.assumeNotNull(sequence, "sequence not null");
			this.sequenceNameNew = sequence.getName();
			this.currentNextNew = sequence.getCurrentNext();
			this.currentNextSysNew = sequence.getCurrentNextSys();
			this.newValuesSet = true;
		}

		public boolean isChange()
		{
			if (!newValuesSet)
			{
				return false;
			}

			if (newSequence)
			{
				return true;
			}

			if (!Objects.equals(this.sequenceName, this.sequenceNameNew))
			{
				return true;
			}
			if (currentNext != currentNextNew)
			{
				return true;
			}
			if (currentNextSys != currentNextSysNew)
			{
				return true;
			}

			return false;
		}

		@Override
		public String toString()
		{
			final StringBuilder changes = new StringBuilder();

			if (newValuesSet && !Objects.equals(sequenceName, sequenceNameNew))
			{
				if (changes.length() > 0)
				{
					changes.append(", ");
				}
				changes.append("Name(new)=").append(sequenceNameNew);
			}

			if (newValuesSet && currentNext != currentNextNew)
			{
				if (changes.length() > 0)
				{
					changes.append(", ");
				}
				changes.append("CurrentNext=").append(currentNext).append("->").append(currentNextNew);
			}

			if (newValuesSet && currentNextSys != currentNextSysNew)
			{
				if (changes.length() > 0)
				{
					changes.append(", ");
				}
				changes.append("CurrentNextSys=").append(currentNextSys).append("->").append(currentNextSysNew);
			}

			final StringBuilder sb = new StringBuilder();
			sb.append("Sequence ").append(sequenceName);
			if (newSequence)
			{
				sb.append(" (new)");
			}
			sb.append(": ");
			if (changes.length() > 0)
			{
				sb.append(changes);
			}
			else if (newSequence)
			{
				sb.append("just created");
			}
			else
			{
				sb.append("no changes");
			}

			return sb.toString();
		}
	}
}
