package org.adempiere.ad.migration.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.spi.TrxOnCommitCollectorFactory;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_MigrationScript;
import org.compiere.model.MSequence;
import org.compiere.util.DisplayType;
import org.compiere.util.Util;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;

import de.metas.logging.LogManager;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * SQL migration script file logger.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class MigrationScriptFileLogger
{
	public static final MigrationScriptFileLogger of(final String dbType)
	{
		return new MigrationScriptFileLogger(dbType);
	}

	private static final Logger logger = LogManager.getLogger(MigrationScriptFileLogger.class);

	private final String dbType;
	private File _file;
	private FileOutputStream _out;
	private BufferedWriter _writer;

	private static final String COLLECTOR_TRXPROPERTYNAME = MigrationScriptFileLogger.class.getName() + ".collectorFactory";
	private final TrxOnCommitCollectorFactory<StringBuilder, String> collectorFactory = new TrxOnCommitCollectorFactory<StringBuilder, String>()
	{

		@Override
		protected String getTrxProperyName()
		{
			return COLLECTOR_TRXPROPERTYNAME;
		}

		@Override
		protected String extractTrxNameFromItem(final String sqlStatement)
		{
			return ITrx.TRXNAME_ThreadInherited;
		}

		@Override
		protected StringBuilder newCollector(final String sqlStatement)
		{
			return new StringBuilder();
		}

		@Override
		protected void collectItem(final StringBuilder collector, final String sqlStatement)
		{
			if (Check.isEmpty(sqlStatement))
			{
				return;
			}

			final String prm_COMMENT = Services.get(ISysConfigBL.class).getValue("DICTIONARY_ID_COMMENTS");
			final SimpleDateFormat format = DisplayType.getDateFormat(DisplayType.DateTime);
			final String dateTimeText = format.format(new Timestamp(System.currentTimeMillis()));

			// log time and date
			collector.append("-- ").append(dateTimeText).append("\n");
			// log sysconfig comment
			collector.append("-- ").append(prm_COMMENT).append("\n");
			// log statement
			collector.append(sqlStatement);
			// close statement
			collector.append("\n;\n\n");
		}

		@Override
		protected void processCollector(final StringBuilder collector)
		{
			final String sqlStatements = collector.toString();
			appendNow(sqlStatements);
		}

		@Override
		protected void discardCollector(final StringBuilder collector)
		{
			final String sqlStatements = collector.toString();
			if (sqlStatements.isEmpty())
			{
				return;
			}

			System.out.println("\n");
			System.out.println("---------------------------------------------------------------------------");
			System.out.println("Discarding following SQL statements, due to transaction rollback:\n");
			System.out.println(sqlStatements);
			System.out.println("---------------------------------------------------------------------------");
			System.out.println("\n");
			System.out.flush();
		};
	};

	private MigrationScriptFileLogger(final String dbType)
	{
		super();
		this.dbType = dbType;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("dbType", dbType)
				.add("file", _file)
				.toString();
	}

	@Override
	protected void finalize() throws Throwable
	{
		close();
	}

	private synchronized Writer getWriter() throws IOException
	{
		if (_writer == null)
		{
			_file = createMigrationScriptFile(dbType);
			_out = new FileOutputStream(_file, true);
			_writer = new BufferedWriter(new OutputStreamWriter(_out, "UTF8"));

			System.out.println("Created migration script: " + _file);
		}
		return _writer;
	}

	private static final File createMigrationScriptFile(final String dbType) throws IOException
	{
		final int adClientId = 0;
		final int scriptId = MSequence.getNextID(adClientId, I_AD_MigrationScript.Table_Name) * 10;
		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");

		final String prefix = scriptId + "_migration_script_"
				+ dateFormat.format(new Date())
				+ "_";
		final String sufix = "_"
				+ dbType
				+ ".sql";
		final File file = File.createTempFile(prefix, sufix);
		return file;
	}

	/**
	 * Appends given SQL statement.
	 * 
	 * If this method is called within a database transaction then the SQL statement will not be written to file directly,
	 * but it will be collected and written when the transaction is committed.
	 * 
	 * @param sqlStatement
	 */
	public synchronized void appendSqlStatement(final String sqlStatement)
	{
		collectorFactory.collect(sqlStatement);
	}

	private synchronized void appendNow(final String sqlStatements)
	{
		if(sqlStatements == null || sqlStatements.isEmpty())
		{
			return;
		}
		
		try
		{
			final Writer w = getWriter();
			w.append(sqlStatements);
			w.flush();
		}
		catch (final IOException ex)
		{
			logger.error("Failed writting to {}:\n {}", this, sqlStatements, ex);
			close(); // better to close the file ... so, next time a new one will be created
		}
	}

	/**
	 * Close underling file.
	 * 
	 * Next time, {@link #appendSqlStatement(String)} will be called, a new file will be created, so it's safe to call this method as many times as needed.
	 */
	public synchronized void close()
	{
		if (_file == null)
		{
			// already closed or never opened
			return;
		}

		Util.close(_writer);
		Util.close(_out);
		_writer = null;
		_out = null;

		System.out.println("Closed migration script: " + _file);

		_file = null;
	}
}
