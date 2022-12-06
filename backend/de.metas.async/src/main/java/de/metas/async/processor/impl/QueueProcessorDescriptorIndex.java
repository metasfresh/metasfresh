/*
 * #%L
 * de.metas.async
 * %%
 * Copyright (C) 2022 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.async.processor.impl;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.async.api.IQueueDAO;
import de.metas.async.api.impl.QueueProcessorDAO;
import de.metas.async.model.I_C_Queue_PackageProcessor;
import de.metas.async.model.I_C_Queue_Processor;
import de.metas.async.model.I_C_Queue_Processor_Assign;
import de.metas.async.processor.QueuePackageProcessorId;
import de.metas.async.processor.QueueProcessorId;
import de.metas.cache.CCache;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
public class QueueProcessorDescriptorIndex
{
	private final static String QUEUE_PROCESSOR_DESCRIPTOR_CCACHE_KEY = "ANY";

	@NonNull
	public static QueueProcessorDescriptorIndex getInstance()
	{
		if (Adempiere.isUnitTestMode())
		{
			return new QueueProcessorDescriptorIndex(new QueueProcessorDAO());
		}
		else
		{
			return SpringContextHolder.instance.getBean(QueueProcessorDescriptorIndex.class);
		}
	}

	private final CCache<String, Map<QueueProcessorId, QueueProcessorDescriptor>> queueProcessorDescriptorIndexCCache = CCache.<String, Map<QueueProcessorId, QueueProcessorDescriptor>>builder()
			.cacheName("QueueProcessorDescriptor")
			.additionalTableNameToResetFor(I_C_Queue_Processor.Table_Name)
			.additionalTableNameToResetFor(I_C_Queue_PackageProcessor.Table_Name)
			.additionalTableNameToResetFor(I_C_Queue_Processor_Assign.Table_Name)
			.build();

	private final IQueueDAO queueDAO = Services.get(IQueueDAO.class);
	private final QueueProcessorDAO queueProcessorDAO;

	public QueueProcessorDescriptorIndex(
			@NonNull final QueueProcessorDAO queueProcessorDAO)
	{
		this.queueProcessorDAO = queueProcessorDAO;
	}

	@NonNull
	public ImmutableSet<QueuePackageProcessorId> getPackageProcessorIdsForProcessor(@NonNull final QueueProcessorId processorId)
	{
		final Map<QueueProcessorId, QueueProcessorDescriptor> queueProcessorDescriptorSet = getQueueProcessorIndex();

		return Optional.ofNullable(queueProcessorDescriptorSet.get(processorId))
				.map(QueueProcessorDescriptor::getQueuePackageProcessorIds)
				.orElseThrow(() -> new AdempiereException("There are no C_Queue_PackageProcessor records assigned to C_Queue_Processor!")
						.appendParametersToMessage()
						.setParameter("C_Queue_Processor_ID", processorId.getRepoId()));
	}

	@NonNull
	public QueueProcessorId getQueueProcessorForPackageProcessor(@NonNull final QueuePackageProcessorId packageProcessorId)
	{
		final I_C_Queue_Processor queueProcessor = getQueueProcessor(packageProcessorId);

		return QueueProcessorId.ofRepoId(queueProcessor.getC_Queue_Processor_ID());
	}

	@NonNull
	public I_C_Queue_Processor getQueueProcessor(@NonNull final QueuePackageProcessorId packageProcessorId)
	{
		final Map<QueueProcessorId, QueueProcessorDescriptor> queueProcessorDescriptors = getQueueProcessorIndex();

		return queueProcessorDescriptors
				.values()
				.stream()
				.filter(descriptor -> descriptor.hasPackageProcessor(packageProcessorId))
				.map(QueueProcessorDescriptor::getQueueProcessor)
				.findFirst()
				.orElseThrow(() -> new AdempiereException("There is no C_Queue_Processor assigned to C_Queue_PackageProcessor!")
						.appendParametersToMessage()
						.setParameter("C_Queue_PackageProcessor_ID", packageProcessorId.getRepoId()));
	}

	@NonNull
	public I_C_Queue_PackageProcessor getPackageProcessor(@NonNull final QueuePackageProcessorId packageProcessorId)
	{
		final Map<QueueProcessorId, QueueProcessorDescriptor> queueProcessorDescriptors = getQueueProcessorIndex();

		return queueProcessorDescriptors
				.values()
				.stream()
				.map(descriptor -> descriptor.getPackageProcessor(packageProcessorId))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.findFirst()
				.orElseThrow(() -> new AdempiereException("There is no C_Queue_PackageProcessor found!")
						.appendParametersToMessage()
						.setParameter("C_Queue_PackageProcessor_ID", packageProcessorId.getRepoId()));
	}

	@NonNull
	private Map<QueueProcessorId, QueueProcessorDescriptor> getQueueProcessorIndex()
	{
		return queueProcessorDescriptorIndexCCache.getOrLoad(QUEUE_PROCESSOR_DESCRIPTOR_CCACHE_KEY, this::loadQueueProcessorIndex);
	}

	@NonNull
	private Map<QueueProcessorId, QueueProcessorDescriptor> loadQueueProcessorIndex(@NonNull final String ignoredCacheKey)
	{
		return queueProcessorDAO.getAllQueueProcessors()
				.stream()
				.map(queueProcessor -> {

					final List<I_C_Queue_PackageProcessor> packageProcessors = queueDAO.retrieveWorkpackageProcessors(queueProcessor);

					return new QueueProcessorDescriptor(queueProcessor, packageProcessors);
				})
				.collect(ImmutableMap.toImmutableMap(QueueProcessorDescriptor::getQueueProcessorId, Function.identity()));
	}
}
