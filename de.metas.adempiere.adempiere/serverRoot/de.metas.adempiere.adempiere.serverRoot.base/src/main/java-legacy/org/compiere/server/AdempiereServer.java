/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.server;

import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.sql.Timestamp;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.service.IClientDAO;
import org.compiere.model.AdempiereProcessor;
import org.compiere.model.AdempiereProcessor2;
import org.compiere.model.AdempiereProcessorLog;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_IMP_Processor;
import org.compiere.model.MAlertProcessor;
import org.compiere.model.MRequestProcessor;
import org.compiere.model.MScheduler;
import org.compiere.model.X_R_RequestProcessor;
import org.compiere.server.exception.ServerThreadException;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.wf.MWorkflowProcessor;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 * Adempiere Server Base
 *
 * @author Jorg Janke
 * @version $Id: AdempiereServer.java,v 1.3 2006/10/09 00:23:26 jjanke Exp $
 */
public abstract class AdempiereServer extends Thread
{
	/**
	 * Create New Server Thread
	 *
	 * @param model model
	 * @return server tread or null
	 */
	public static AdempiereServer create(AdempiereProcessor model)
	{
		Check.assumeNotNull(model, "model not null");

		if (model instanceof MRequestProcessor)
			return new RequestProcessor((MRequestProcessor)model);
		if (model instanceof MWorkflowProcessor)
			return new WorkflowProcessor((MWorkflowProcessor)model);
		if (model instanceof MAlertProcessor)
			return new AlertProcessor((MAlertProcessor)model);
		if (model instanceof MScheduler)
			return new Scheduler((MScheduler)model);
		if (I_IMP_Processor.Table_Name.equals(model.get_TableName())) // @Trifon
			return new ReplicationProcessor(model);
		//
		throw new IllegalArgumentException("Unknown Processor");
	}	// create

	/**************************************************************************
	 * Server Base Class
	 *
	 * @param model model
	 * @param initialNapSecs delay time running in sec
	 */
	public AdempiereServer(final AdempiereProcessor model, final int initialNapSecs)
	{
		super(
				AdempiereServerGroup.get(), // ThreadGroup
				null, // Runnable
				model.getClass().getSimpleName() + "-" + model.getName(), // Thread Name
				0 // stackSize (0=this parameter is to be ignored)
		);
		p_model = model;
		m_ctx = Env.deriveCtx(model.getCtx());

		final I_AD_Client adClient = Services.get(IClientDAO.class).retriveClient(m_ctx);
		Env.setContext(m_ctx, Env.CTXNAME_AD_Client_ID, adClient.getAD_Client_ID());

		m_initialNapSecs = initialNapSecs;
	}	// ServerBase

	/** The Processor Model */
	private final AdempiereProcessor p_model;
	/** Initial nap is seconds */
	private int m_initialNapSecs = 0;

	/** Miliseconds to sleep - 10 Min default */
	private long m_sleepMS = 600000;
	/** Sleeping */
	private final AtomicBoolean sleeping = new AtomicBoolean(false);
	/** Server start time */
	private long serverStartTimeMillis = 0;
	/** Number of Work executions */
	private int p_runCount = 0;
	/** Time start of work */
	private long workStartTimeMillis = 0;
	/** Number MS of last Run */
	private long m_runLastMS = 0;
	/** Number of MS total */
	private long m_runTotalMS = 0;
	/** When to run next */
	private long m_nextWork = 0;

	/** Logger */
	protected final transient Logger log = LogManager.getLogger(getClass());
	/** Context */
	private final Properties m_ctx;

	/**
	 * Get Server Context
	 *
	 * @return context
	 */
	protected final Properties getCtx()
	{
		return m_ctx;
	}	// getCtx

	/**
	 * @return Returns the sleepMS.
	 */
	private final long getSleepMS()
	{
		return m_sleepMS;
	}	// getSleepMS

	/**
	 * Sleep for set time
	 *
	 * @return true if not interrupted
	 */
	protected final boolean sleep()
	{
		final String threadName = getName();

		if (isInterrupted())
		{
			log.info("{}: interrupted", threadName);
			return false;
		}

		final long sleepMillis = getSleepMS();
		log.debug("{}: sleeping {}", threadName, TimeUtil.formatElapsed(sleepMillis));

		sleeping.getAndSet(true);
		try
		{
			Thread.sleep(sleepMillis);
			return true;
		}
		catch (final InterruptedException e)
		{
			log.info("{}: interrupted", threadName);
			return false;
		}
		finally
		{
			sleeping.set(false);
		}
	}	// sleep

	/**
	 * Run Now
	 */
	public final void runNow()
	{
		DB.saveConstraints();
		try
		{
			DB.getConstraints().addAllowedTrxNamePrefix(ITrx.TRXNAME_PREFIX_LOCAL);

			runNow0();
		}
		finally
		{
			DB.restoreConstraints();
		}
	}

	private final void runNow0()
	{
		log.info(getName());
		workStartTimeMillis = System.currentTimeMillis();
		doWork();
		long now = System.currentTimeMillis();
		// ---------------

		p_runCount++;
		m_runLastMS = now - workStartTimeMillis;
		m_runTotalMS += m_runLastMS;
		//
		p_model.setDateLastRun(new Timestamp(now));
		save(p_model, ITrx.TRXNAME_None);
		//
		log.debug(getName() + ": " + getStatistics());
	}	// runNow

	/**************************************************************************
	 * Run async
	 */
	@Override
	public void run()
	{
		final String threadName = getName();
		try
		{
			final int initialNapSecs = m_initialNapSecs;
			log.debug("{}: pre-nap - {} seconds", threadName, initialNapSecs);
			sleep(m_initialNapSecs * 1000);
		}
		catch (InterruptedException ex)
		{
			log.error("{}: pre-nap interrupted", threadName, ex);
			return;
		}

		serverStartTimeMillis = System.currentTimeMillis();
		try
		{
			while (true)
			{
				// 03034: do the actual work in a try-catch block to make sure that the server thread doesn't just end
				// if an unexpected (and maybe temporary) problem arises in the thread
				DB.saveConstraints();
				try
				{
					DB.getConstraints().addAllowedTrxNamePrefix(ITrx.TRXNAME_PREFIX_LOCAL);

					final boolean doBreak = run0();
					if (doBreak)
					{
						break;
					}
				}
				catch (final Exception ex)
				{
					final ServerThreadException serverThreadEx = new ServerThreadException(p_model.getName(), ex);
					log.error("{}: run failed", threadName, serverThreadEx);
				}
				finally
				{
					DB.restoreConstraints();
				}
				// me_03034 end

				if (!sleep())
				{
					break;
				}
			}
		}
		finally
		{
			serverStartTimeMillis = 0;
		}
	}	// run

	/**
	 * This method does the actual work of the {@link #run()} method.
	 *
	 * @return <code>true</code> if the calling {@link #run()} should break out of its <code>while(true)</code> block.
	 *
	 * @see http://dewiki908/mediawiki/index.php/03034:_ADempiere_ServerProcesses_can_die_%282012072510000033%29
	 */
	private final boolean run0() throws Exception
	{
		// note that this is basically the original code that has formerly been part of the run() method.
		if (m_nextWork <= 0)
		{
			Timestamp dateNextRun = getDateNextRun(true);
			if (dateNextRun != null)
				m_nextWork = dateNextRun.getTime();
		}
		long now = System.currentTimeMillis();
		if (m_nextWork > now)
		{
			m_sleepMS = m_nextWork - now;
			if (!sleep())
				return true;
		}
		if (isInterrupted())
		{
			log.info("{}: interrupted", getName());
			return true;
		}

		// ---------------
		workStartTimeMillis = System.currentTimeMillis();
		doWork();
		now = System.currentTimeMillis();
		// ---------------

		p_runCount++;
		m_runLastMS = now - workStartTimeMillis;
		m_runTotalMS += m_runLastMS;
		//
		m_sleepMS = calculateSleep();
		Timestamp lastRun = new Timestamp(now);
		if (p_model instanceof AdempiereProcessor2)
		{
			AdempiereProcessor2 ap = (AdempiereProcessor2)p_model;
			if (ap.isIgnoreProcessingTime())
			{
				lastRun = new Timestamp(workStartTimeMillis);
				if (m_nextWork <= 0)
					m_nextWork = workStartTimeMillis;
				m_nextWork = m_nextWork + m_sleepMS;
				while (m_nextWork < now)
				{
					m_nextWork = m_nextWork + m_sleepMS;
				}
			}
			else
			{
				m_nextWork = now + m_sleepMS;
			}
		}
		else
		{
			m_nextWork = now + m_sleepMS;
		}
		//
		p_model.setDateLastRun(lastRun);
		p_model.setDateNextRun(new Timestamp(m_nextWork));
		p_model.saveOutOfTrx();

		log.debug(getName() + ": " + getStatistics());

		return false;
	}

	/**
	 * Get Run Statistics
	 *
	 * @return Statistic info
	 */
	public final String getStatistics()
	{
		return "Run #" + p_runCount
				+ " - Last=" + TimeUtil.formatElapsed(m_runLastMS)
				+ " - Total=" + TimeUtil.formatElapsed(m_runTotalMS)
				+ " - Next " + TimeUtil.formatElapsed(m_nextWork - System.currentTimeMillis());
	}	// getStatistics

	/**
	 * Do the actual Work
	 */
	protected abstract void doWork();

	/**
	 * Get Server Info
	 *
	 * @return info
	 */
	public abstract String getServerInfo();

	/**
	 * Get Unique ID
	 *
	 * @return Unique ID
	 */
	public final String getServerID()
	{
		return p_model.getServerID();
	}	// getServerID

	/**
	 * Get the date Next run
	 *
	 * @param requery requery database
	 * @return date next run
	 */
	public final Timestamp getDateNextRun(boolean requery)
	{
		return p_model.getDateNextRun(requery);
	}	// getDateNextRun

	/**
	 * Sets DateNextRun and save.
	 *
	 * @param dateNextRun
	 */
	public final void setDateNextRun(final Timestamp dateNextRun)
	{
		p_model.setDateNextRun(dateNextRun);

		// NOTE: we need to save it because some BL is relying on this (e.g. Scheduler)
		save(p_model, ITrx.TRXNAME_None);
	}

	/**
	 * Get the date Last run
	 *
	 * @return date last run
	 */
	public final Timestamp getDateLastRun()
	{
		return p_model.getDateLastRun();
	}	// getDateLastRun

	/**
	 * Get Description
	 *
	 * @return Description
	 */
	public final String getDescription()
	{
		return p_model.getDescription();
	}	// getDescription

	/**
	 * Get Model
	 *
	 * @return Model
	 */
	public final AdempiereProcessor getModel()
	{
		return p_model;
	}	// getModel

	/**
	 * Calculate Sleep ms
	 *
	 * @return miliseconds
	 */
	protected final long calculateSleep()
	{
		String frequencyType = p_model.getFrequencyType();
		int frequency = p_model.getFrequency();
		if (frequency < 1)
			frequency = 1;
		//
		final long typeSec;
		if (frequencyType == null)
			typeSec = 300;			// 5 minutes (default)
		else if (X_R_RequestProcessor.FREQUENCYTYPE_Minute.equals(frequencyType))
			typeSec = 60;
		else if (X_R_RequestProcessor.FREQUENCYTYPE_Hour.equals(frequencyType))
			typeSec = 3600;
		else if (X_R_RequestProcessor.FREQUENCYTYPE_Day.equals(frequencyType))
			typeSec = 86400;
		else // Unknown Frequency
		{
			typeSec = 600; // 10 min
			log.warn("Unknown FrequencyType=" + frequencyType + ". Using Frequency=" + typeSec + "seconds.");
		}
		//
		return typeSec * 1000 * frequency;		// ms
	}	// calculateSleep

	/**
	 * Is Sleeping
	 *
	 * @return sleeping
	 */
	public final boolean isSleeping()
	{
		return sleeping.get();
	}	// isSleeping

	@Override
	public final String toString()
	{
		final boolean sleeping = isSleeping();

		final StringBuilder sb = new StringBuilder(getName())
				.append(",Prio=").append(getPriority())
				.append(",").append(getThreadGroup())
				.append(",Alive=").append(isAlive())
				.append(",Sleeping=").append(sleeping)
				.append(",Last=").append(getDateLastRun());
		if (sleeping)
		{
			sb.append(",Next=").append(getDateNextRun(false));
		}

		return sb.toString();
	}

	public final Timestamp getStartTime()
	{
		final long startTimeMillis = this.serverStartTimeMillis;
		return startTimeMillis > 0 ? new Timestamp(startTimeMillis) : null;
	}

	public final AdempiereProcessorLog[] getLogs()
	{
		return p_model.getLogs();
	}

	/**
	 * Set the initial nap/sleep when server starts.
	 *
	 * Mainly this method is used by tests.
	 *
	 * @param initialNapSeconds
	 */
	public final void setInitialNapSeconds(final int initialNapSeconds)
	{
		Check.assume(initialNapSeconds >= 0, "initialNapSeconds >= 0");
		this.m_initialNapSecs = initialNapSeconds;
	}

	protected final int getRunCount()
	{
		return p_runCount;
	}

	protected final Timestamp getStartWork()
	{
		return new Timestamp(workStartTimeMillis);
	}
}
