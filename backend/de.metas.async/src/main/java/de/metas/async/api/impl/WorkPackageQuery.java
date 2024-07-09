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

import de.metas.async.api.IWorkPackageQuery;
import de.metas.async.processor.QueuePackageProcessorId;
import de.metas.util.Check;
import lombok.Getter;
import lombok.Setter;
import org.adempiere.ad.dao.QueryLimit;

import javax.annotation.Nullable;
import java.util.Set;

public class WorkPackageQuery implements IWorkPackageQuery
{
	private Boolean processed;
	private Boolean readyForProcessing;
	private Boolean error;
	private long skippedTimeoutMillis = 0;
	@Nullable
	private Set<QueuePackageProcessorId> packageProcessorIds;
	private String priorityFrom;

	@Getter
	@Setter
	private QueryLimit limit;

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
	public WorkPackageQuery setProcessed(final Boolean processed)
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
	public WorkPackageQuery setReadyForProcessing(final boolean readyForProcessing)
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
	public WorkPackageQuery setError(final Boolean error)
	{
		this.error = error;
		return this;
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
	public void setSkippedTimeoutMillis(final long skippedTimeoutMillis)
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
	@Nullable
	public Set<QueuePackageProcessorId> getPackageProcessorIds()
	{
		return packageProcessorIds;
	}


	public WorkPackageQuery setPackageProcessorIds(@Nullable final Set<QueuePackageProcessorId> packageProcessorIds)
	{
		if (packageProcessorIds != null)
		{
			Check.assumeNotEmpty(packageProcessorIds, "packageProcessorIds cannot be empty!");
		}

		this.packageProcessorIds = packageProcessorIds;
		return this;
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

	public WorkPackageQuery setPriorityFrom(final String priorityFrom)
	{
		this.priorityFrom = priorityFrom;
		return this;
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
