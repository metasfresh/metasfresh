package org.adempiere.server.rpl.api;

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

import de.metas.util.ILoggable;
import de.metas.util.ISingletonService;
import de.metas.util.Loggables;
import org.adempiere.server.rpl.IImportProcessor;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.model.AdempiereProcessor;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_EXP_FormatLine;
import org.compiere.model.I_IMP_Processor;
import org.compiere.model.I_IMP_ProcessorLog;
import org.compiere.model.I_IMP_ProcessorParameter;

import java.util.Iterator;
import java.util.Properties;

public interface IIMPProcessorBL extends ISingletonService
{
	I_IMP_ProcessorLog createLog(I_IMP_Processor impProcessor, String summary, String text, String reference, Throwable error);

	/**
	 * Set up a thread-local loggable that can be used within import and export business logic.
	 * <p>
	 * Hints:
	 * <li>Use {@link Loggables#get()} to retrieve this loggable.
	 * <li>All {@link ILoggable#addLog(String, Object...)} invocations
	 * will call {@link #createLog(I_IMP_Processor, String, String, String, Throwable)} with the given {@code impProcessor} and {@code reference}.
	 */
	IAutoCloseable setupTemporaryLoggable(I_IMP_Processor impProcessor, String reference);

	/**
	 * Gets XML message as String
	 *
	 * @return xml message or null
	 */
	String getXmlMessage(I_IMP_ProcessorLog pLog);

	org.w3c.dom.Document getXmlDocument(I_IMP_ProcessorLog plog);

	/**
	 * Mark error log as resolved (e.g. to be called after it was resubmitted successfully).
	 */
	void markResolved(I_IMP_ProcessorLog plog);

	/**
	 * Invoke the import processor and attempt to (re-)import the given logs.
	 */
	void resubmit(Iterator<I_IMP_ProcessorLog> logs, boolean failfast, ILoggable loggable);

	IImportProcessor getIImportProcessor(I_IMP_Processor impProcessor);

	/**
	 * Create/Update Parameter
	 *
	 * @return created/updated parameter
	 */
	I_IMP_ProcessorParameter createParameter(I_IMP_Processor impProcessor, String key, String name, String desc, String help, String value);

	AdempiereProcessor asAdempiereProcessor(I_IMP_Processor impProcessor);

	I_IMP_Processor getIMP_Processor(AdempiereProcessor adempiereProcessor);

	IImportHelper createImportHelper(Properties initialCtx);

	/**
	 * Returns the reference of the given column or (if an overriding reference is set there) from the given line.
	 *
	 * @return AD_Reference of column and formatLine
	 */
	int getAD_Reference_ID(I_AD_Column column, I_EXP_FormatLine formatLine);

	/**
	 * Returns the table and column that the given embedded or referencing format line points to. Throws an exception if the given line's type is neither <code>ReferencedEXPFormat</code> nor
	 * <code>EmbeddedEXPFormat</code>.
	 */
	ITableAndColumn getTargetTableAndColumn(I_EXP_FormatLine formatLine);

	/**
	 * Simple interface to return the result of {@link IIMPProcessorBL#getTargetTableAndColumn(I_EXP_FormatLine)}
	 *
	 */
	interface ITableAndColumn
	{
		String getTableName();

		String getColumnName();
	}
}
