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

import com.google.common.collect.ImmutableSet;
import de.metas.async.api.IQueueDAO;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.api.impl.QueueProcessorDAO;
import de.metas.async.api.impl.WorkPackageQueue;
import de.metas.async.model.I_C_Queue_PackageProcessor;
import de.metas.async.model.I_C_Queue_Processor;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.processor.QueuePackageProcessorId;
import de.metas.async.processor.QueueProcessorId;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;

import java.util.List;
import java.util.Properties;

public class WorkPackageQueueFactory implements IWorkPackageQueueFactory
{
	private final QueueProcessorDAO queueProcessorDAO = QueueProcessorDAO.getInstance();

	@Override
	public IWorkPackageQueue getQueueForPackageProcessing(final I_C_Queue_Processor processor)
	{
		final List<I_C_Queue_PackageProcessor> packageProcessors = Services.get(IQueueDAO.class)
				.retrieveWorkpackageProcessors(processor);

		final ImmutableSet<QueuePackageProcessorId> packageProcessorIds = packageProcessors
				.stream()
				.map(I_C_Queue_PackageProcessor::getC_Queue_PackageProcessor_ID)
				.map(QueuePackageProcessorId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());

		final Properties ctx = InterfaceWrapperHelper.getCtx(processor);
		final String priorityFrom = processor.getPriority();
		final QueueProcessorId queueProcessorId =  QueueProcessorId.ofRepoId(processor.getC_Queue_Processor_ID());

		return WorkPackageQueue.createForQueueProcessing(ctx, packageProcessorIds, queueProcessorId, priorityFrom);
	}

	@Override
	public IWorkPackageQueue getQueueForEnqueuing(final Properties ctx, final Class<? extends IWorkpackageProcessor> packageProcessorClass)
	{
		final I_C_Queue_PackageProcessor packageProcessor = Services.get(IQueueDAO.class).retrievePackageProcessorDefByClass(ctx, packageProcessorClass);
		return getQueueForEnqueuing(ctx, packageProcessor);
	}

	@Override
	public IWorkPackageQueue getQueueForEnqueuing(final Properties ctx, final String packageProcessorClassname)
	{
		final I_C_Queue_PackageProcessor packageProcessor = Services.get(IQueueDAO.class).retrievePackageProcessorDefByClassname(ctx, packageProcessorClassname);
		return getQueueForEnqueuing(ctx, packageProcessor);
	}

	private IWorkPackageQueue getQueueForEnqueuing(final Properties ctx, final I_C_Queue_PackageProcessor packageProcessor)
	{
		final String internalNameToUse;
		if (Check.isEmpty(packageProcessor.getInternalName()))
		{
			internalNameToUse = packageProcessor.getClassname();
		}
		else
		{
			internalNameToUse = packageProcessor.getInternalName();
		}

		final QueueProcessorId queueProcessorId = queueProcessorDAO.getProcessorForPackage(packageProcessor)
				.orElseThrow(() -> new AdempiereException("C_Queue_PackageProcessor is not assign to any C_Queue_Processor!")
						.appendParametersToMessage()
						.setParameter("C_Queue_PackageProcessor_ID", packageProcessor.getC_Queue_PackageProcessor_ID()));

		return WorkPackageQueue.createForEnqueuing(
				ctx,
				QueuePackageProcessorId.ofRepoId(packageProcessor.getC_Queue_PackageProcessor_ID()),
				queueProcessorId,
				internalNameToUse);
	}
}
