/**********************************************************************
 * This file is part of Adempiere ERP Bazaar                          * 
 * http://www.adempiere.org                                           * 
 *                                                                    * 
 * Copyright (C) Trifon Trifonov.                                     * 
 * Copyright (C) Contributors                                         * 
 *                                                                    * 
 * This program is free software; you can redistribute it and/or      * 
 * modify it under the terms of the GNU General Public License        * 
 * as published by the Free Software Foundation; either version 2     * 
 * of the License, or (at your option) any later version.             * 
 *                                                                    * 
 * This program is distributed in the hope that it will be useful,    * 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of     * 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the       * 
 * GNU General Public License for more details.                       * 
 *                                                                    * 
 * You should have received a copy of the GNU General Public License  * 
 * along with this program; if not, write to the Free Software        * 
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,         * 
 * MA 02110-1301, USA.                                                * 
 *                                                                    * 
 * Contributors:                                                      * 
 *  - Trifon Trifonov (trifonnt@users.sourceforge.net)                *
 *                                                                    *
 * Sponsors:                                                          *
 *  - E-evolution (http://www.e-evolution.com/)                       *
 **********************************************************************/
package org.compiere.server;

import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.server.rpl.IImportProcessor;
import org.adempiere.server.rpl.IReplicationProcessor;
import org.adempiere.server.rpl.api.IIMPProcessorBL;
import org.adempiere.server.rpl.api.IIMPProcessorDAO;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.model.AdempiereProcessor;
import org.compiere.model.I_IMP_Processor;
import org.compiere.util.TimeUtil;

import ch.qos.logback.classic.Level;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;

/**
 * @author Trifon N. Trifonov
 */
public class ReplicationProcessor extends AdempiereServer
		implements IReplicationProcessor
{

	/**
	 * Last Summary
	 */
	private StringBuffer m_summary = new StringBuffer();

	/**
	 * Contains the config data, including a reference to the import processor type.
	 */
	private final I_IMP_Processor mImportProcessor;

	/**
	 * The actual import processor
	 */
	private IImportProcessor importProcessor;

	/**
	 * flag showing if process is working!
	 */
	private boolean importProcessorRunning = false;

	public ReplicationProcessor(AdempiereProcessor model, int initialNapSecs)
	{
		super(model, initialNapSecs);
		mImportProcessor = Services.get(IIMPProcessorBL.class).getIMP_Processor(model);
	}

	protected ReplicationProcessor(AdempiereProcessor model)
	{
		this(model, 10);
	}

	@Override
	protected void doWork()
	{
		//
		InterfaceWrapperHelper.refresh(mImportProcessor); // daysToKeepLog might have changed
		final int no = Services.get(IIMPProcessorDAO.class).deleteLogs(mImportProcessor);
		if (no > 0)
		{
			m_summary.append("Logs Records deleted=").append(no).append("; ");
		}
		if (isProcessRunning())
		{
			// process is already started successfully!
			return;
		}

		// process is not started!
		m_summary = new StringBuffer();
		final Properties ctx = InterfaceWrapperHelper.getCtx(mImportProcessor);
		final String trxName = InterfaceWrapperHelper.getTrxName(mImportProcessor);
		log.debug("trxName = " + trxName);
		log.debug("ImportProcessor = " + mImportProcessor);

		final String reference = "#" + getRunCount() + " - " + TimeUtil.formatElapsed(getStartWork());
		final IIMPProcessorBL impProcessorBL = Services.get(IIMPProcessorBL.class);
		try (final IAutoCloseable closable = impProcessorBL.setupTemporaryLoggable(mImportProcessor, reference))
		{
			importProcessor = impProcessorBL.getIImportProcessor(mImportProcessor);
			importProcessor.start(ctx, this, trxName);
			Check.assume(isProcessRunning(), importProcessor + " has called setProcessRunning(true)");
			Loggables.withLogger(log, Level.INFO).addLog(m_summary.toString());
		}
		catch (Exception e)
		{
			setProcessRunning(false);
			log(null, e);

			try
			{
				importProcessor.stop();
			}
			catch (Exception e1)
			{
				log(null, e1);
			}
		}

	}

	private void log(String summary, Throwable t)
	{
		if (summary == null && t != null)
		{
			summary = t.getMessage();
		}
		if (t != null)
		{
			log.error(summary, t);
		}

		final String reference = "#" + getRunCount() + " - " + TimeUtil.formatElapsed(getStartWork());
		String text = null;
		Services.get(IIMPProcessorBL.class).createLog(mImportProcessor, summary, text, reference, t);
	}

	@Override
	public String getServerInfo()
	{
		return "#" + getRunCount() + " - Last=" + m_summary.toString();
	}

	/**
	 * @return the isProcessRunning
	 */
	@Override
	public boolean isProcessRunning()
	{
		return importProcessorRunning;
	}

	/**
	 * @param isProcessRunning the isProcessRunning to set
	 */
	@Override
	public void setProcessRunning(boolean isProcessRunning)
	{
		this.importProcessorRunning = isProcessRunning;
	}

	/**
	 * @return the mImportProcessor
	 */
	@Override
	public I_IMP_Processor getMImportProcessor()
	{
		return mImportProcessor;
	}
}
