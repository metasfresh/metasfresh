package de.metas.async.api.impl;

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


import java.util.ArrayList;
import java.util.List;

import de.metas.async.api.IWorkPackageQuery;
import de.metas.util.Check;

public class WorkPackageQuery implements IWorkPackageQuery
{
	private Boolean processed;
	private Boolean readyForProcessing;
	private Boolean error;
	private long skippedTimeoutMillis = 0;
	private List<Integer> packageProcessorIds;
	private String priorityFrom;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.metas.async.api.IWorkPackageQuery#getProcessed()
	 */
	@Override
	public Boolean getProcessed()
	{
		return processed;
	}

	/**
	 * @param processed the processed to set
	 */
	public WorkPackageQuery setProcessed(Boolean processed)
	{
		this.processed = processed;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.metas.async.api.IWorkPackageQuery#getReadyForProcessing()
	 */
	@Override
	public Boolean getReadyForProcessing()
	{
		return readyForProcessing;
	}

	/**
	 * @param readyForProcessing the readyForProcessing to set
	 */
	public WorkPackageQuery setReadyForProcessing(Boolean readyForProcessing)
	{
		this.readyForProcessing = readyForProcessing;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.metas.async.api.IWorkPackageQuery#getError()
	 */
	@Override
	public Boolean getError()
	{
		return error;
	}

	/**
	 * @param error the error to set
	 */
	public void setError(Boolean error)
	{
		this.error = error;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.metas.async.api.IWorkPackageQuery#getSkippedTimeoutMillis()
	 */
	@Override
	public long getSkippedTimeoutMillis()
	{
		return skippedTimeoutMillis;
	}

	/**
	 * @param skippedTimeoutMillis the skippedTimeoutMillis to set
	 */
	public void setSkippedTimeoutMillis(long skippedTimeoutMillis)
	{
		Check.assume(skippedTimeoutMillis >= 0, "skippedTimeoutMillis >= 0");
		this.skippedTimeoutMillis = skippedTimeoutMillis;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.metas.async.api.IWorkPackageQuery#getPackageProcessorIds()
	 */
	@Override
	public List<Integer> getPackageProcessorIds()
	{
		return packageProcessorIds;
	}

	/**
	 * @param packageProcessorIds the packageProcessorIds to set
	 */
	public void setPackageProcessorIds(List<Integer> packageProcessorIds)
	{
		if (packageProcessorIds == null)
		{
			this.packageProcessorIds = null;
		}
		else
		{
			this.packageProcessorIds = new ArrayList<Integer>(packageProcessorIds);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.metas.async.api.IWorkPackageQuery#getPriorityFrom()
	 */
	@Override
	public String getPriorityFrom()
	{
		return priorityFrom;
	}

	/**
	 * @param priorityFrom the priorityFrom to set
	 */
	public void setPriorityFrom(String priorityFrom)
	{
		this.priorityFrom = priorityFrom;
	}

	@Override
	public String toString()
	{
		return "WorkPackageQuery ["
				+ "processed=" + processed
				+ ", readyForProcessing=" + readyForProcessing
				+ ", error=" + error
				+ ", skippedTimeoutMillis=" + skippedTimeoutMillis
				+ ", packageProcessorIds=" + packageProcessorIds
				+ ", priorityFrom=" + priorityFrom
				+ "]";
	}

}
