package de.metas.async.api;

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

import de.metas.async.processor.QueuePackageProcessorId;
import org.adempiere.ad.dao.QueryLimit;

import java.util.Set;

public interface IWorkPackageQuery
{

	/**
	 * @return the processed
	 */
	Boolean getProcessed();

	/**
	 * @return the readyForProcessing
	 */
	Boolean getReadyForProcessing();

	/**
	 * @return the error
	 */
	Boolean getError();

	/**
	 * @return the skippedTimeoutMillis
	 */
	long getSkippedTimeoutMillis();

	/**
	 * @return the packageProcessorIds
	 */
	Set<QueuePackageProcessorId> getPackageProcessorIds();

	/**
	 * @return the priorityFrom
	 */
	String getPriorityFrom();

	QueryLimit getLimit();

	void setLimit(QueryLimit limit);
}
