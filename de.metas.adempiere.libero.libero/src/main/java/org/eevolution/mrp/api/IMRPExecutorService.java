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

import org.adempiere.util.ISingletonService;
import org.eevolution.model.I_PP_MRP;

import de.metas.material.planning.IMRPSegment;
import de.metas.material.planning.IMaterialPlanningContext;

/**
 * This service's implementor can hold one or more {@link IMRPExecutor}s, each one being referenced as {@link ThreadLocal} variable.
 *
 *
 */
public interface IMRPExecutorService extends ISingletonService
{
	/**
	 * Checks if there is any {@link IMRPExecutor}s running at the moment.
	 *
	 * @return true if is running.
	 */
	boolean isRunning();

	/**
	 * Execute MRP on given context
	 *
	 * @param mrpContext
	 */
	IMRPResult run(IMaterialPlanningContext mrpContext);

	/**
	 * Execute MRP on given contexts
	 *
	 * @param mrpContexts
	 */
	IMRPResult run(List<IMaterialPlanningContext> mrpContexts);

	/**
	 * Removed all generated MRP documents and notices.
	 *
	 * @param mrpContext
	 */
	IMRPResult cleanup(IMaterialPlanningContext mrpContext);

	/**
	 * Called to notify that the MRP segment changed.
	 *
	 * The executor can decide if it shall evaluate the given segment again.
	 *
	 * @param mrpSegment
	 */
	void notifyMRPSegmentChanged(IMRPSegment mrpSegment);

	/**
	 * Called to notify that the MRP record is about to be created.
	 *
	 * The executor can decide what to do about it (e.g. mark it available or not)
	 *
	 * @param mrp
	 */
	void onMRPRecordBeforeCreate(I_PP_MRP mrp);
}
