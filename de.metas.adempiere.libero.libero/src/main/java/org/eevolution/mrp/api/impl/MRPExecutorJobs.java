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


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.adempiere.util.Check;
import org.adempiere.util.lang.IReference;
import org.eevolution.mrp.api.IMRPExecutorJobs;
import org.slf4j.Logger;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.material.planning.pporder.LiberoException;
import de.metas.logging.LogManager;

public class MRPExecutorJobs implements IMRPExecutorJobs
{
	// services
	private static final transient Logger loggerDefault = LogManager.getLogger(MRPExecutorJobs.class);
	private final transient Logger logger = loggerDefault;

	/** Jobs which were submited but not yet executed */
	private final LinkedHashMap<String, Runnable> jobs = new LinkedHashMap<>();

	@Override
	public void addJob(final String jobName, final Runnable job)
	{
		Check.assumeNotEmpty(jobName, "jobName not empty");
		Check.assumeNotNull(job, "job not null");

		if (jobs.containsKey(jobName))
		{
			throw new LiberoException("Cannot add job because we already have a job for job name: " + jobName
					+ "\n Job to add: " + job
					+ "\n Existing job: " + jobs.get(jobName));
		}

		jobs.put(jobName, job);
	}

	@Override
	public <T extends Runnable> T getJob(final String jobName)
	{
		@SuppressWarnings("unchecked")
		final T job = (T)jobs.get(jobName);
		return job;
	}

	@Override
	public <T extends Runnable> T getCreateJob(final String jobName, final IReference<T> jobCreator)
	{
		Check.assumeNotEmpty(jobName, "jobName not empty");

		if (jobs.containsKey(jobName))
		{
			@SuppressWarnings("unchecked")
			final T job = (T)jobs.get(jobName);
			return job;
		}
		else
		{
			Check.assumeNotNull(jobCreator, "jobCreator not null");
			final T job = jobCreator.getValue();
			jobs.put(jobName, job);
			return job;
		}
	}

	@Override
	public void executeAllJobs()
	{
		if (jobs.isEmpty())
		{
			return;
		}

		final List<String> jobNames = new ArrayList<>(jobs.keySet());
		for (final String jobName : jobNames)
		{
			final Runnable job = jobs.remove(jobName);
			Check.assumeNotNull(job, LiberoException.class, "job not null");

			executeJob(job);
		}

	}

	private final void executeJob(final Runnable job)
	{
		if (job == null)
		{
			return; // shall not happen
		}

		try
		{
			job.run();
		}
		catch (final Exception e)
		{
			logger.warn("Error while running the job: " + job + " [SKIPPED]", e);
		}
	}
}
