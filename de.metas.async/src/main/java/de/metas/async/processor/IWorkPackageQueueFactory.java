package de.metas.async.processor;

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


import java.util.Properties;

import org.adempiere.util.ISingletonService;

import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.model.I_C_Queue_Processor;
import de.metas.async.spi.IWorkpackageProcessor;

public interface IWorkPackageQueueFactory extends ISingletonService
{
	/**
	 * Return a queue for a <b>queue processor</b>, in order to poll and lock and process work-packages that were enqueued earlier.
	 * 
	 * @param processor
	 * @return
	 */
	IWorkPackageQueue getQueueForPackageProcessing(I_C_Queue_Processor processor);

	/**
	 * Return a queue instance for a particular <b>work package processor</b>, in order to create new work-packages for it.
	 * 
	 * @param ctx
	 * @param packageProcessorClass
	 * @return a queue for the given work package processor (as specified by its class)
	 */
	IWorkPackageQueue getQueueForEnqueuing(Properties ctx, Class<? extends IWorkpackageProcessor> packageProcessorClass);
	

	/**
	 * Return a queue instance for a particular <b>work package processor</b>, in order to create new work-packages for it.
	 * 
	 * @param ctx
	 * @param packageProcessorClassname
	 * @return a queue for the given work package processor (as specified by its class)
	 */
	IWorkPackageQueue getQueueForEnqueuing(Properties ctx, String packageProcessorClassname);

}
