package org.eevolution.mrp.api.impl;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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


import java.util.Collections;
import java.util.List;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.mrp.api.IMRPDAO;
import org.eevolution.mrp.api.IMRPExecutor;
import org.eevolution.mrp.api.IMRPExecutorService;
import org.eevolution.mrp.api.IMRPResult;
import org.eevolution.mrp.api.IMRPSegmentBL;
import org.slf4j.Logger;

import de.metas.material.planning.ErrorCodes;
import de.metas.material.planning.IMRPContextFactory;
import de.metas.material.planning.IMRPSegment;
import de.metas.material.planning.IMaterialPlanningContext;
import de.metas.material.planning.pporder.LiberoException;

public class MRPExecutorService implements IMRPExecutorService
{
	// Services
	// private final transient Logger logger = CLogMgt.getLogger(getClass());

	public static final String MRP_ERROR_MRPExecutorMaxIterationsExceeded = ErrorCodes.MRP_ERROR_999_Unknown; // TODO: add particular MRP code for this

	private final ThreadLocal<IMRPExecutor> _currentMRPExecutor = new ThreadLocal<>();

	/**
	 * Creates and returns a new executor (but doesn't reference it).
	 *
	 * @return {@link IMRPExecutor} instance
	 */
//	protected IMRPExecutor createMRPExecutor()
//	{
//		final MRPExecutor mrpExecutor = new MRPExecutor();
//		Adempiere.autowire(mrpExecutor);
//
//		return mrpExecutor;
//	}

	private final void setCurrentMRPExecutor(final IMRPExecutor mrpExecutor)
	{
		_currentMRPExecutor.set(mrpExecutor);
	}

	/**
	 * @return current {@link IMRPExecutor} or <code>null</code>
	 */
	private final IMRPExecutor getCurrentMRPExecutor()
	{
		return _currentMRPExecutor.get();
	}

	@Override
	public boolean isRunning()
	{
		return _currentMRPExecutor.get() != null;
	}

	@Override
	public IMRPResult run(final IMaterialPlanningContext mrpContext)
	{
		Check.assumeNotNull(mrpContext, "mrpContext not null");
		final List<IMaterialPlanningContext> mrpContexts = Collections.singletonList(mrpContext);
		return run(mrpContexts);
	}

	@Override
	public IMRPResult run(final List<IMaterialPlanningContext> mrpContexts)
	{
		final IMRPExecutor mrpExecutor = Adempiere.getBean(IMRPExecutor.class);

		//
		// Cleanup all MRP segments before starting to plan on them
		// NOTE: we do this once at the start because of some MRP Demands which are not bound to a particular Plant (e.g. Sales Orders)
		// and which will be balanced by first who can do this and then it will be marked as not available for others.
		// If we are not doing this, we risk to reset the IsAvailable flag and that demand could be balanced more then one time.
		cleanup(mrpContexts, mrpExecutor);

		//
		// Execute MRP on given contexts
		run(mrpContexts, mrpExecutor, new IMRPRunnable()
		{

			@Override
			public void run(final IMaterialPlanningContext mrpContextLocal)
			{
				mrpExecutor.runMRP(mrpContextLocal);
			}

			@Override
			public boolean isCleanup()
			{
				return false;
			}
		});

		//
		// Execute jobs that need to be executed after all segments were evaluated
		mrpExecutor.getAfterAllSegmentsRunJobs().executeAllJobs();

		return mrpExecutor.getMRPResult();
	}

	@Override
	public IMRPResult cleanup(final IMaterialPlanningContext mrpContext)
	{
		Check.assumeNotNull(mrpContext, "mrpContext not null");
		final List<IMaterialPlanningContext> mrpContexts = Collections.singletonList(mrpContext);

		final IMRPExecutor mrpExecutor = Adempiere
				.getSpringApplicationContext()
				.getBean(IMRPExecutor.class);

		cleanup(mrpContexts, mrpExecutor);
		return mrpExecutor.getMRPResult();
	}

	private final void cleanup(final List<IMaterialPlanningContext> mrpContexts, final IMRPExecutor mrpExecutor)
	{
		run(mrpContexts, mrpExecutor, new IMRPRunnable()
		{
			@Override
			public void run(final IMaterialPlanningContext mrpContextLocal)
			{
				mrpExecutor.cleanup(mrpContextLocal);
			}

			@Override
			public boolean isCleanup()
			{
				return true;
			}
		});

	}

	private final void run(final List<IMaterialPlanningContext> mrpContexts, final IMRPExecutor mrpExecutor, final IMRPRunnable mrpRunnable)
	{
		Check.assumeNotEmpty(mrpContexts, "mrpContexts not empty");
		for (final IMaterialPlanningContext mrpContext : mrpContexts)
		{
			run(mrpContext, mrpExecutor, mrpRunnable);
		}
	}

	private final void run(final IMaterialPlanningContext mrpContext0, final IMRPExecutor mrpExecutor, final IMRPRunnable mrpRunnable)
	{
		// trxManager.assertTrxNull(mrpContext0); // it can be not null // TODO: narrow down this (i.e. when is allowed?)
		Check.assumeNotNull(mrpContext0, LiberoException.class, "mrpContext0 not null");
		Check.assumeNotNull(mrpExecutor, LiberoException.class, "mrpExecutor not null");
		Check.assumeNotNull(mrpRunnable, LiberoException.class, "mrpRunnable not null");

		final Logger logger = mrpContext0.getLogger();

		// Services
		final IMRPSegmentBL mrpSegmentBL = Services.get(IMRPSegmentBL.class);
		final IMRPContextFactory mrpContextFactory = Services.get(IMRPContextFactory.class);
		final IMRPDAO mrpDAO = Services.get(IMRPDAO.class);

		//
		// Create initial fully defined MRP segments from initial context
		// Create MRP boundaries, in which we enforce our MRP executor to run.
		// If we will be notified about a segment change which is not in our boundaries, we will skip it.
		final IMRPSegment mrpSegment0 = mrpSegmentBL.createMRPSegment(mrpContext0);
		final MRPSegmentsCollector mrpSegments = new MRPSegmentsCollector()
				.addFullyDefinedSegments(mrpContext0.getCtx(), mrpSegment0);
		final MRPSegmentsCollector mrpBoundaries;
		final boolean mrpBoundariesEnforced = mrpSegment0.getM_Warehouse_ID() > 0 || mrpSegment0.getPlant_ID() > 0 || mrpSegment0.getM_Product_ID() > 0;
		if (mrpBoundariesEnforced)
		{
			mrpBoundaries = new MRPSegmentsCollector();
			mrpBoundaries.addSegments(mrpSegments);
		}
		else
		{
			logger.info("NOTE: not enforcing MRP boundaries because our initial segment is not defined at all: {}", mrpSegment0);
			mrpBoundaries = null;
		}

		//
		// Execute MRP on our planning segments
		// We exect that after maximum "Initial MRP segments" x 10 to finish
		final int maxIterations = mrpSegments.size() * 10;
		for (int iterationNo = 0; iterationNo < maxIterations && mrpSegments.hasSegments(); iterationNo++)
		{
			final MRPSegmentAndTrace mrpSegmentAndTrace = mrpSegments.removeFirst();
			final IMRPSegment mrpSegment = mrpSegmentAndTrace.getMRPSegment();
			logger.info("\n\n\n------------------------------------------------------------------------------------------------------------------");
			logger.info("Iteration: {}/{}", new Object[] { iterationNo, maxIterations });
			logger.info("Evaluating segment: {}", mrpSegment);
			logger.info("Trace: {}", mrpSegmentAndTrace.getTrace());

			final IMaterialPlanningContext currentMRPContext = mrpContextFactory.createMRPContext(mrpContext0, mrpSegment);

			//
			// Make sure there is no other executor which is running at the moment
			final IMRPExecutor currentMRPExecutorOld = getCurrentMRPExecutor();
			Check.assumeNull(currentMRPExecutorOld, LiberoException.class, "No IMRPExecutor shall be running at this moment but it is: {}", currentMRPExecutorOld);

			//
			// Run MRP
			setCurrentMRPExecutor(mrpExecutor);
			try
			{
				//
				// Actually execute MRP
				mrpRunnable.run(currentMRPContext);

				//
				// Get segments changed after run and collect them if they are in our boundaries
				final List<IMRPSegment> mrpSegmentsChangedAfterRun = mrpExecutor.getAndClearChangedMRPSegments();
				for (final IMRPSegment mrpSegmentChangedAfterRun : mrpSegmentsChangedAfterRun)
				{
					// Skip segments which are not in our boundaries
					if (mrpBoundaries != null && !mrpBoundaries.includes(mrpSegmentChangedAfterRun))
					{
						continue;
					}

					final MRPSegmentAndTrace mrpSegmentAndTraceChangedAfterRun = new MRPSegmentAndTrace(mrpSegmentChangedAfterRun, mrpSegmentAndTrace);

					// Skip segments which are introducing cycles
					// i.e. on of the segment from it's trace contains or it's equal with the segment that we are enqueing now.
					// NOTE: in most of the cases this could happen when there are cyclic distribution networks
					if (mrpSegmentAndTraceChangedAfterRun.getMRPSegmentCyclesCount() >= 1)
					{
						final String comment = "Detected a cycle in triggered MRP segment changed. Skipped segment."
								+ "\n MRP Segment: " + mrpSegmentAndTraceChangedAfterRun.getMRPSegment()
								+ "\n MRP Segment trace: " + mrpSegmentAndTraceChangedAfterRun.getTrace();
						mrpExecutor.newMRPNote(mrpContext0, MRP_ERROR_MRPExecutorMaxIterationsExceeded)
								.setAD_Org(mrpSegmentChangedAfterRun.getAD_Org())
								.setPlant(mrpSegmentChangedAfterRun.getPlant())
								.setM_Warehouse(mrpSegmentChangedAfterRun.getM_Warehouse())
								.setM_Product(mrpSegmentChangedAfterRun.getM_Product())
								.setComment(comment)
								.collect();

						continue;
					}
					mrpSegments.addSegment(mrpSegmentAndTraceChangedAfterRun);
				}
			}
			finally
			{
				setCurrentMRPExecutor(currentMRPExecutorOld);
			}

			//
			// If there are no more segments and MRP boundaries are not enforced
			// Then check available MRP demands (which is ideal case shall be none),
			// create the segments from them
			// and enqueue them to be processed
			if (mrpSegments.isEmpty() && mrpBoundaries == null && !mrpRunnable.isCleanup())
			{
				final List<IMRPSegment> mrpSegmentsOfAvailableDemands = mrpDAO.retrieveMRPSegmentsForAvailableDemands(mrpContext0.getCtx());
				mrpSegments.addSegments(mrpSegmentsOfAvailableDemands);
			}

		} // end iteration

		//
		// Make sure we don't have any more segments to balance
		if (!mrpSegments.isEmpty())
		{
			final String comment = "Cannot balance all MRP demands after " + maxIterations + " iterators."
					+ "\n Remaining segments are:"
					+ "\n" + mrpSegments;
			mrpExecutor.newMRPNote(mrpContext0, MRP_ERROR_MRPExecutorMaxIterationsExceeded)
					.setComment(comment)
					.collect();
		}
	}

	@Override
	public void notifyMRPSegmentChanged(final IMRPSegment mrpSegment)
	{
		final IMRPExecutor mrpExecutor = getCurrentMRPExecutor();
		if (mrpExecutor == null)
		{
			return;
		}

		mrpExecutor.addChangedMRPSegment(mrpSegment);
	}

	@Override
	public void onMRPRecordBeforeCreate(final I_PP_MRP mrp)
	{
		final IMRPExecutor mrpExecutor = getCurrentMRPExecutor();
		if (mrpExecutor == null)
		{
			return;
		}

		mrpExecutor.onMRPRecordBeforeCreate(mrp);
	}

}

interface IMRPRunnable
{
	void run(IMaterialPlanningContext mrpContext);

	/** @return <code>true</code> if this runnable is about cleaning up data and not about actual MRP work */
	boolean isCleanup();
}
