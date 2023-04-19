package de.metas.async.processor.impl;

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

import de.metas.async.exceptions.ConfigurationException;
import de.metas.async.processor.IMutableQueueProcessorStatistics;
import de.metas.async.processor.IWorkpackageProcessorFactory;
import de.metas.async.processor.QueuePackageProcessorId;
import de.metas.async.processor.descriptor.QueueProcessorDescriptorRepository;
import de.metas.async.processor.descriptor.model.QueuePackageProcessor;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.IMBeanAwareService;
import lombok.NonNull;
import org.compiere.util.Util;
import org.slf4j.Logger;

import java.util.Properties;

class WorkpackageProcessorFactory implements IWorkpackageProcessorFactory, IMBeanAwareService
{
	protected final transient Logger logger = LogManager.getLogger(getClass());

	private final WorkpackageProcessorBlackList blacklist = new WorkpackageProcessorBlackList();

	public WorkpackageProcessorFactory()
	{
		super();
	}

	@Override
	public IWorkpackageProcessor getWorkpackageProcessor(final Properties ctx, final int packageProcessorId)
	{
		Check.assume(packageProcessorId > 0, "packageProcessorId > 0");
		blacklist.assertNotBlacklisted(packageProcessorId);

		//
		// Load Package Processor Definition
		final QueuePackageProcessor queuePackageProcessor;
		try
		{
			queuePackageProcessor = QueueProcessorDescriptorRepository.getInstance().getPackageProcessor(QueuePackageProcessorId.ofRepoId(packageProcessorId));
		}
		catch (final ConfigurationException e)
		{
			blacklist.addToBlacklist(packageProcessorId, null, e);
			throw e;
		}

		return getWorkpackageProcessor(queuePackageProcessor);
	}

	@Override
	public IWorkpackageProcessor getWorkpackageProcessor(@NonNull final QueuePackageProcessor packageProcessorDef)
	{
		final boolean checkBlacklist = true;
		return getWorkpackageProcessor(packageProcessorDef, checkBlacklist);
	}

	protected IWorkpackageProcessor getWorkpackageProcessor(@NonNull final QueuePackageProcessor packageProcessorDef, final boolean checkBlacklist)
	{
		Check.assumeNotNull(packageProcessorDef, "packageProcessorDef not null");

		final int packageProcessorId = packageProcessorDef.getQueuePackageProcessorId().getRepoId();
		if (checkBlacklist)
		{
			blacklist.assertNotBlacklisted(packageProcessorId);
		}

		//
		// Try to instantiate the IWorkpackageProcessor class
		final IWorkpackageProcessor packageProcessor;
		final String packageProcessorClassname = packageProcessorDef.getClassname();
		try
		{
			packageProcessor = getWorkpackageProcessorInstance(packageProcessorClassname);
		}
		catch (final Exception e)
		{
			final ConfigurationException exception = ConfigurationException.wrapIfNeeded(e);
			if (checkBlacklist)
			{
				blacklist.addToBlacklist(packageProcessorId, packageProcessorClassname, exception);
			}
			throw exception;
		}

		return packageProcessor;
	}

	@Override
	public void validateWorkpackageProcessor(@NonNull final QueuePackageProcessor packageProcessorDef)
	{
		final IWorkpackageProcessor packageProcessor = getWorkpackageProcessor(packageProcessorDef, false); // checkBlacklist=false
		Check.assumeNotNull(packageProcessor, "No " + IWorkpackageProcessor.class + " could be loaded for {}", packageProcessorDef);

		// If everything is ok, make sure to remove it from list
		blacklist.removeFromBlacklist(packageProcessorDef.getQueuePackageProcessorId().getRepoId());
	}

	protected IWorkpackageProcessor getWorkpackageProcessorInstance(final String classname)
	{
		final IWorkpackageProcessor packageProcessor = Util.getInstance(IWorkpackageProcessor.class, classname);
		return packageProcessor;
	}

	@Override
	public boolean isWorkpackageProcessorBlacklisted(final int workpackageProcessorId)
	{
		return blacklist.isBlacklisted(workpackageProcessorId);
	}

	public WorkpackageProcessorBlackList getBlackList()
	{
		return blacklist;
	}

	private volatile WorkpackageProcessorFactoryJMX mbean = null;

	@Override
	public synchronized Object getMBean()
	{
		if (mbean == null)
		{
			mbean = new WorkpackageProcessorFactoryJMX(this);
		}
		return mbean;
	}

	@Override
	public IMutableQueueProcessorStatistics getWorkpackageProcessorStatistics(@NonNull final IWorkpackageProcessor workpackageProcessor)
	{
		// NOTE: keep in sync with getWorkpackageProcessorStatistics(I_C_Queue_PackageProcessor workpackage). Both methods shall build the name in the same way
		final String workpackageProcessorName = workpackageProcessor.getClass().getName();
		
		return new MonitorableQueueProcessorStatistics(workpackageProcessorName);
	}

	@Override
	public IMutableQueueProcessorStatistics getWorkpackageProcessorStatistics(@NonNull final QueuePackageProcessor workpackageProcessorDef)
	{
		// NOTE: keep in sync with getWorkpackageProcessorStatistics(IWorkpackageProcessor workpackageProcessor). Both methods shall build the name in the same way
		final String workpackageProcessorName = workpackageProcessorDef.getClassname();
		
		return new MonitorableQueueProcessorStatistics(workpackageProcessorName);
	}
}
