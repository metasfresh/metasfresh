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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.service.ISequenceDAO;
import org.adempiere.ad.service.ISystemBL;
import org.adempiere.ad.service.ITableSequenceChecker;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.ILoggable;
import org.adempiere.util.LoggerLoggable;
import org.adempiere.util.Services;
import org.compiere.db.CConnection;
import org.compiere.model.I_AD_Sequence;
import org.compiere.model.I_AD_System;
import org.compiere.model.I_AD_Table;
import org.compiere.model.MSequence;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TrxRunnable2;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import ch.qos.logback.classic.Level;

public class TableSequenceChecker implements ITableSequenceChecker
{
	private final transient Logger log = LogManager.getLogger(getClass());
	private final ISequenceDAO sequenceDAO = Services.get(ISequenceDAO.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private ILoggable logger = null;
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

	@Override
	public ITableSequenceChecker setLogger(final ILoggable logger)
	{
		this.logger = logger;

		return this;
	}

	public ILoggable getLogger()
	{
		if (logger == null)
		{
			logger = LoggerLoggable.of(this.log, Level.INFO);
		}

		return logger;
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

			createUpdateTableSequence(table);
			countChecked++;
		}

		getLogger().addLog("Checked " + countChecked + " of " + countAll + " tables");
	}

	private void createUpdateTableSequence(final I_AD_Table table)
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
				createUpdateTableSequence(ctx, tableName, localTrxName);
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
	 * Create/Update Table ID Sequence
	 * 
	 * @param ctx context
	 * @param TableName table name
	 * @param trxName transaction
	 * @return created/updated sequence; never return null
	 */
	private final I_AD_Sequence createUpdateTableSequence(final Properties ctx, final String tableName, final String trxName)
	{
		I_AD_Sequence sequence = sequenceDAO.retrieveTableSequenceOrNull(ctx, tableName, trxName);
		if (sequence == null)
		{
			final Properties ctxSystem = Env.deriveCtx(ctx);
			Env.setContext(ctxSystem, Env.CTXNAME_AD_Client_ID, 0);
			Env.setContext(ctxSystem, Env.CTXNAME_AD_Org_ID, 0);
			final IContextAware contextProvider = new PlainContextAware(ctxSystem, trxName);

			I_AD_Sequence sequenceNew = InterfaceWrapperHelper.newInstance(I_AD_Sequence.class, contextProvider, true); // useCLientOrgFromProvider=true
			sequenceNew.setAD_Org_ID(0);
			sequenceNew.setName(tableName);
			sequenceNew.setDescription("Table " + tableName);
			sequenceNew.setIsTableID(true);
			sequenceNew.setIsActive(true);
			sequence = sequenceNew;
		}

		final SequenceChangeLog changeLog = new SequenceChangeLog(sequence);

		//
		// Correct Sequence Name (if needed)
		if (!tableName.equals(sequence.getName()))
		{
			sequence.setName(tableName);
		}

		//
		// Check ID range (if needed)
		if (isSequenceRangeCheck()
				// task 08607: if using native sequences, we will recreate the native sequence from the current AD_Sequence.CurrentNext value
				// so, as a workaround, we also make sure that AD_Sequence.CurrentNext is OK.
				|| DB.isUseNativeSequences())
		{
			updateTableCurrentNext(sequence);
		}

		//
		// Create native sequence (if needed)
		// task 08607 we still need this invocation; we already called updateTableCurrentNext(), but it might *not* already have called createTableNativeSequence(sequence).
		if (DB.isUseNativeSequences())
		{
			createTableNativeSequence(sequence);
		}

		//
		// Save sequence
		InterfaceWrapperHelper.save(sequence);

		//
		// Log
		changeLog.setNewValues(sequence);
		log(changeLog);

		return sequence;
	}	// createTableSequence

	private final void createTableNativeSequence(final I_AD_Sequence sequence)
	{
		final String trxName = InterfaceWrapperHelper.getTrxName(sequence);
		final String tableName = sequence.getName();

		//
		// metas: 03863: Skip inactive sequences
		if (!sequence.isActive())
		{
			// we already logged in updateTableCurrentNext() that we skip the sequence, so no need to log to the loggable again
			log.debug("Skip checking native sequence " + sequence + " because is not active.");
			return;
		}

		final String dbSequenceName = DB.getTableSequenceName(tableName);
		final int dbSeqIncrement = sequence.getIncrementNo();

		// NOTE: we use CurrentNext and not CurrentNextSys because CurrentNextSys is used only when changing system data, and then we are not using native sequences anyway
		final int dbSeqStart = sequence.getCurrentNext();

		final int dbSeqMin = 0;
		final int dbSeqMax = Integer.MAX_VALUE; // even though postgresql supports 2^63-1, we use MAX java integer (because we are fetching the IDs as integers atm), which is 2^31-1

		final boolean created = CConnection.get().getDatabase().createSequence(
				dbSequenceName,
				dbSeqIncrement, // increment
				dbSeqMin,  // min value
				dbSeqMax, // max value
				dbSeqStart, // start
				trxName);
		if (!created)
		{
			throw new DBException("Cannot create native sequence: " + dbSequenceName);
		}
	}

	/**
	 * Checks sequence's CurrentNext and CurrentNextSys and updates them if necessary
	 * 
	 * NOTE: this method is not saving the modified sequence
	 * 
	 * @return true if updated
	 */
	private final boolean updateTableCurrentNext(final I_AD_Sequence seq)
	{
		if (!seq.isTableID())
		{
			return false;
		}

		final String tableName = seq.getName();

		//
		// metas: 03863: Skip inactive sequences
		if (!seq.isActive())
		{
			final String msg = "Skip checking sequence " + tableName + " because it is not active.";
			getLogger().addLog(msg);

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
			log.debug("Skip checking sequence " + tableName + " because there is no key column name (" + keyColumnName + ")");
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
		// metas: tsa: 01534: create/update native sequences
		// [ 3307419 ] Sequence check shall update native sequences
		if (changed && DB.isUseNativeSequences())
		{
			createTableNativeSequence(seq);
			changed = true;
		}

		//
		// Log
		if (info != null && log.isDebugEnabled())
		{
			log.debug(seq.getName() + " - " + info);
		}

		return changed;
	}	// validate

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
			super();
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

			if (!Check.equals(this.sequenceName, this.sequenceNameNew))
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

			if (newValuesSet && !Check.equals(sequenceName, sequenceNameNew))
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
