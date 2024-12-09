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

<<<<<<< HEAD

import java.util.Iterator;
import java.util.Properties;

=======
import de.metas.util.ILoggable;
import de.metas.util.ISingletonService;
import de.metas.util.Loggables;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import org.adempiere.server.rpl.IImportProcessor;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.model.AdempiereProcessor;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_EXP_FormatLine;
import org.compiere.model.I_IMP_Processor;
import org.compiere.model.I_IMP_ProcessorLog;
import org.compiere.model.I_IMP_ProcessorParameter;

<<<<<<< HEAD
import de.metas.util.ILoggable;
import de.metas.util.ISingletonService;
import de.metas.util.Loggables;
=======
import java.util.Iterator;
import java.util.Properties;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

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
<<<<<<< HEAD
	 * @param pLog
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * @return xml message or null
	 */
	String getXmlMessage(I_IMP_ProcessorLog pLog);

	org.w3c.dom.Document getXmlDocument(I_IMP_ProcessorLog plog);

	/**
	 * Mark error log as resolved (e.g. to be called after it was resubmitted successfully).
<<<<<<< HEAD
	 *
	 * @param plog
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
	 * @param impProcessor
	 * @param key parameter key
	 * @param name parameter name (human readable)
	 * @param desc parameter description
	 * @param help parameter help
	 * @param value parameter value
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * @return created/updated parameter
	 */
	I_IMP_ProcessorParameter createParameter(I_IMP_Processor impProcessor, String key, String name, String desc, String help, String value);

<<<<<<< HEAD
	I_IMP_ProcessorParameter createParameter(I_IMP_Processor impProcessor, String key, String value);

=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	AdempiereProcessor asAdempiereProcessor(I_IMP_Processor impProcessor);

	I_IMP_Processor getIMP_Processor(AdempiereProcessor adempiereProcessor);

	IImportHelper createImportHelper(Properties initialCtx);

<<<<<<< HEAD
	void setImportHelperClass(Class<? extends IImportHelper> importHelperClass);

	/**
	 * Returns the reference of the given column or (if an overriding reference is set there) from the given line.
	 *
	 * @param column
	 * @param formatLine
=======
	/**
	 * Returns the reference of the given column or (if an overriding reference is set there) from the given line.
	 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * @return AD_Reference of column and formatLine
	 */
	int getAD_Reference_ID(I_AD_Column column, I_EXP_FormatLine formatLine);

	/**
	 * Returns the table and column that the given embedded or referencing format line points to. Throws an exception if the given line's type is neither <code>ReferencedEXPFormat</code> nor
	 * <code>EmbeddedEXPFormat</code>.
<<<<<<< HEAD
	 *
	 * @param formatLine
	 * @return
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 */
	ITableAndColumn getTargetTableAndColumn(I_EXP_FormatLine formatLine);

	/**
	 * Simple interface to return the result of {@link IIMPProcessorBL#getTargetTableAndColumn(I_EXP_FormatLine)}
	 *
<<<<<<< HEAD
	 *
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 */
	interface ITableAndColumn
	{
		String getTableName();

		String getColumnName();
	}
}
