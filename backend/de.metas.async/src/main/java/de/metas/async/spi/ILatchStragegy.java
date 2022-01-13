package de.metas.async.spi;

/*
 * #%L
 * de.metas.async
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


import java.util.Collection;

import org.adempiere.ad.dao.IQueryBuilder;

import de.metas.async.api.IQueueDAO;
import de.metas.async.exceptions.WorkpackageSkipRequestException;
import de.metas.async.model.I_C_Queue_WorkPackage;

/**
 * Returned by {@link IWorkpackageProcessor2} implementations and invoked by the framework.<br>
 * See {@link #postponeIfNeeded(I_C_Queue_WorkPackage, IQueryBuilder)} .
 * 
 * task http://dewiki908/mediawiki/index.php/09216_Async_-_Need_SPI_to_decide_if_packets_can_be_processed_in_parallel_of_not_%28106397206117%29
 */
public interface ILatchStragegy
{
	/**
	 * 
	 * @param currentWorkPackage the workpackage that shall be checked and whose processing might be postoned.
	 * @param currentlyLockedWorkpackages query builder which the implementation can (but doesn't have to!) extend and use in order to get the currently locked workpackages.<br>
     *            Notes:
	 *            <ul>
	 *            <li>Inactive WPs are already excluded.<br>
	 *            <li>The builder's context is set from <code>currentWorkPackage</code>.<br>
	 *            <li><code>currentWorkPackage</code> itself is <b>also included</b> in the list, so the list is never empty. We are including <code>currentWorkPackage</code>, because that way this
	 *            method's implementors can retrieve an ordered list of all WPs and evaluate <code>currentWorkPackage</code>'s position in that list. In that respect, also see
	 *            {@link IQueueDAO#getQueueOrderBy()}.
	 *            </ul>
	 * @throws WorkpackageSkipRequestException if processing the given <code>currentWorkPackage</code> needs to be postponed until any of the given <code>currentlyLockedWorpackages</code> is
	 *             processed.
	 */
	void postponeIfNeeded(I_C_Queue_WorkPackage currentWorkPackage,
			IQueryBuilder<I_C_Queue_WorkPackage> currentlyLockedWorkpackages);
}
