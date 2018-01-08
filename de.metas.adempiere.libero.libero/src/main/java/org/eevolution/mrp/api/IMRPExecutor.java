package org.eevolution.mrp.api;

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


import java.util.List;

import org.eevolution.model.I_PP_MRP;
import org.eevolution.mrp.spi.IMRPSupplyProducer;

import de.metas.material.planning.IMRPNoteBuilder;
import de.metas.material.planning.IMRPNotesCollector;
import de.metas.material.planning.IMRPSegment;
import de.metas.material.planning.IMaterialPlanningContext;

public interface IMRPExecutor
{
	/**
	 * Run the {@link IMRPExecutor} for given context.
	 *
	 * @param mrpContext MRP running context
	 */
	void runMRP(IMaterialPlanningContext mrpContext);

	/**
	 * @return MRP Run execution result
	 */
	IMRPResult getMRPResult();

	/**
	 * Cleanup (remove) all MRP generated data (documents, MRP records, notes)
	 *
	 * @param mrpContext0
	 */
	void cleanup(IMaterialPlanningContext mrpContext0);

	/**
	 * Adds given document to generated supply documents of the {@link IMRPResult}.
	 *
	 * To be used by {@link IMRPSupplyProducer} implementations.
	 *
	 * @param document
	 */
	void addGeneratedSupplyDocument(Object document);

	/**
	 * Adds given documents deleteted statistics to the {@link IMRPResult}.
	 *
	 * To be used by {@link IMRPSupplyProducer} implementations.
	 *
	 * @param tableName document's table
	 * @param count how many documents were deleted
	 */
	void addDeletedDocuments(String tableName, int count);

	/**
	 * Adds given segment to the list of additional segments that were changed while this executor was running.
	 *
	 * NOTE: don't call it directly, it's called by API.
	 *
	 * @param mrpSegment
	 */
	void addChangedMRPSegment(IMRPSegment mrpSegment);

	/**
	 * Gets and clears changed MRP segments.
	 *
	 * @return changed MRP segments
	 * @see #addChangedMRPSegment(IMRPSegment)
	 */
	List<IMRPSegment> getAndClearChangedMRPSegments();

	/**
	 * Called to notify that the MRP record is about to be created.
	 *
	 * The executor can decide what to do about it (e.g. mark it available or not)
	 *
	 * @param mrp
	 */
	void onMRPRecordBeforeCreate(I_PP_MRP mrp);

	/**
	 * Helper method to create an {@link IMRPQueryBuilder} starting from given {@link IMaterialPlanningContext}.
	 *
	 * To be used by {@link IMRPSupplyProducer} implementations.
	 *
	 * @param mrpContext
	 * @return mrp query builder
	 */
	IMRPQueryBuilder createMRPQueryBuilder(IMaterialPlanningContext mrpContext);

	/**
	 * Returns <code>true</code> if this is will a subsequent MRP executor call (i.e. MRP executor called again from inside MRP module).
	 *
	 * This information is mainly used to decide if we shall do the cleanup, MRP records locking in transaction or out of transaction.
	 *
	 * In case it's <code>true</code> the cleanup will be performed in transaction because we assume that the MRP executor caller already did some cleanups out of transaction so we need to avoid
	 * database deadlocks.
	 *
	 * @return
	 */
	boolean isSubsequentMRPExecutor();

	IMRPNoteBuilder newMRPNote(IMaterialPlanningContext mrpContext, String mrpErrorCode);

	IMRPNotesCollector getMRPNotesCollector();

	/**
	 * @return Jobs to be executed after MRP has run on a segment
	 */
	IMRPExecutorJobs getAfterSegmentRunJobs();

	/**
	 * @return Jobs to be executed after MRP has run on ALL segment
	 */
	IMRPExecutorJobs getAfterAllSegmentsRunJobs();
}
