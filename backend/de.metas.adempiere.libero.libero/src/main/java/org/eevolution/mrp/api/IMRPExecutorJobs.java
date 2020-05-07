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


import org.adempiere.util.lang.IReference;

public interface IMRPExecutorJobs
{

	void addJob(String jobName, Runnable job);

	<T extends Runnable> T getJob(String jobName);

	<T extends Runnable> T getCreateJob(String jobName, IReference<T> jobCreator);

	/**
	 * Execute now all submited jobs.
	 * 
	 * NOTE:
	 * <ul>
	 * <li>this method will not throw exceptions in case on of the job failed but it will just log warning/notes.
	 * <li>jobs which were executed are removed from this collection, so you can execute this method how many times you want, a job will be executed only once.
	 * </ul>
	 */
	void executeAllJobs();
}
