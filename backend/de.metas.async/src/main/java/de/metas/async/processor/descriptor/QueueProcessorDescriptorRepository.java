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

package de.metas.async.processor.descriptor;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.async.api.IQueueDAO;
import de.metas.async.api.impl.QueueProcessorDAO;
import de.metas.async.exceptions.ConfigurationException;
import de.metas.async.model.I_C_Queue_PackageProcessor;
import de.metas.async.model.I_C_Queue_Processor;
import de.metas.async.model.I_C_Queue_Processor_Assign;
import de.metas.async.processor.QueuePackageProcessorId;
import de.metas.async.processor.QueueProcessorId;
import de.metas.async.processor.descriptor.model.QueuePackageProcessor;
import de.metas.async.processor.descriptor.model.QueueProcessorDescriptor;
import de.metas.async.processor.descriptor.model.QueueProcessorDescriptorIndex;
import de.metas.cache.CCache;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class QueueProcessorDescriptorRepository
{
	@NonNull
	public static QueueProcessorDescriptorRepository getInstance()
	{
		if (Adempiere.isUnitTestMode())
		{
			return new QueueProcessorDescriptorRepository(new QueueProcessorDAO());
		}
		else
		{
			return SpringContextHolder.instance.getBean(QueueProcessorDescriptorRepository.class);
		}
	}

	private final IQueueDAO queueDAO = Services.get(IQueueDAO.class);

	private final QueueProcessorDAO queueProcessorDAO;

	public QueueProcessorDescriptorRepository(@NonNull final QueueProcessorDAO queueProcessorDAO)
	{
		this.queueProcessorDAO = queueProcessorDAO;
	}

	private final CCache<QueueProcessorId, QueueProcessorDescriptorIndex> queueProcessorId2QueueProcessorIndexCCache = CCache.<QueueProcessorId, QueueProcessorDescriptorIndex>builder()
			.cacheName("QueueProcessorDescriptorIndexByQueueProcessorId")
			.additionalTableNameToResetFor(I_C_Queue_Processor.Table_Name)
			.additionalTableNameToResetFor(I_C_Queue_PackageProcessor.Table_Name)
			.additionalTableNameToResetFor(I_C_Queue_Processor_Assign.Table_Name)
			.build();

	private final CCache<QueuePackageProcessorId, QueueProcessorId> packageProcessorId2QueueProcessorCCache = CCache.<QueuePackageProcessorId, QueueProcessorId>builder()
			.cacheName("QueueProcessorIdByPackageProcessorId")
			.additionalTableNameToResetFor(I_C_Queue_Processor.Table_Name)
			.additionalTableNameToResetFor(I_C_Queue_PackageProcessor.Table_Name)
			.additionalTableNameToResetFor(I_C_Queue_Processor_Assign.Table_Name)
			.build();

	@NonNull
	public static QueueProcessorDescriptor mapToQueueProcessor(@NonNull final I_C_Queue_Processor record)
	{
		return QueueProcessorDescriptor.builder()
				.queueProcessorId(QueueProcessorId.ofRepoId(record.getC_Queue_Processor_ID()))
				.keepAliveTimeMillis(record.getKeepAliveTimeMillis())
				.name(record.getName())
				.poolSize(record.getPoolSize())
				.priority(record.getPriority())
				.build();
	}

	@NonNull
	public static QueuePackageProcessor mapToPackageProcessor(@NonNull final I_C_Queue_PackageProcessor record)
	{
		return QueuePackageProcessor.builder()
				.queuePackageProcessorId(QueuePackageProcessorId.ofRepoId(record.getC_Queue_PackageProcessor_ID()))
				.classname(record.getClassname())
				.internalName(record.getInternalName())
				.build();
	}

	@NonNull
	public QueueProcessorId getQueueProcessorForPackageProcessor(@NonNull final QueuePackageProcessorId packageProcessorId)
	{
		final QueueProcessorDescriptor queueProcessor = getQueueProcessor(packageProcessorId);

		return queueProcessor.getQueueProcessorId();
	}

	@NonNull
	public QueueProcessorDescriptor getQueueProcessor(@NonNull final QueuePackageProcessorId packageProcessorId)
	{
		return getQueueProcessorIndex(packageProcessorId).getQueueProcessor();
	}

	@NonNull
	public QueuePackageProcessor getPackageProcessor(@NonNull final QueuePackageProcessorId packageProcessorId)
	{
		final QueueProcessorDescriptorIndex queueProcessorDescriptorIndex = getQueueProcessorIndex(packageProcessorId);

		return queueProcessorDescriptorIndex.getPackageProcessor(packageProcessorId);
	}

	@NonNull
	public ImmutableSet<QueuePackageProcessorId> getPackageProcessorIdsForProcessor(@NonNull final QueueProcessorId processorId)
	{
		return getQueueProcessorIndex(processorId).getQueuePackageProcessorIds();
	}

	@NonNull
	public ImmutableSet<QueueProcessorDescriptor> getAllQueueProcessors()
	{
		return queueDAO.retrieveAllProcessors()
				.stream()
				.map(I_C_Queue_Processor::getC_Queue_Processor_ID)
				.map(QueueProcessorId::ofRepoId)
				.map(this::getQueueProcessorIndex)
				.map(QueueProcessorDescriptorIndex::getQueueProcessor)
				.collect(ImmutableSet.toImmutableSet());
	}
	@NonNull
	private QueueProcessorDescriptorIndex getQueueProcessorIndex(@NonNull final QueueProcessorId key)
	{
		return queueProcessorId2QueueProcessorIndexCCache.getOrLoad(key, this::loadQueueProcessorIndex);
	}

	@NonNull
	private QueueProcessorDescriptorIndex getQueueProcessorIndex(@NonNull final QueuePackageProcessorId key)
	{
		final QueueProcessorId queueProcessorId = packageProcessorId2QueueProcessorCCache.getOrLoad(key, this::loadQueueProcessorId);

		return getQueueProcessorIndex(queueProcessorId);
	}

	@NonNull
	private QueueProcessorDescriptorIndex loadQueueProcessorIndex(@NonNull final QueueProcessorId processorId)
	{
		final Optional<I_C_Queue_Processor> queueProcessor = queueProcessorDAO.getById(processorId);

		return queueProcessor.map(this::buildQueueProcessorDescriptorIndex)
				.orElseThrow(() -> new ConfigurationException("There is no C_QueueProcessor found!")
						.appendParametersToMessage()
						.setParameter("C_Queue_Processor_ID", processorId.getRepoId()));
	}

	@NonNull
	private QueueProcessorId loadQueueProcessorId(@NonNull final QueuePackageProcessorId packageProcessorId)
	{
		final Optional<QueueProcessorId> queueProcessor = queueProcessorDAO.getByPackageProcessorId(packageProcessorId);

		return queueProcessor
				.orElseThrow(() -> new ConfigurationException("There is no C_QueueProcessor found for QueuePackageProcessorId!")
						.appendParametersToMessage()
						.setParameter("C_Queue_PackageProcessor_ID", packageProcessorId.getRepoId()));
	}

	@NonNull
	private QueueProcessorDescriptorIndex buildQueueProcessorDescriptorIndex(@NonNull final I_C_Queue_Processor queueProcessor)
	{
		final ImmutableList<QueuePackageProcessor> packageProcessorsList = queueDAO.retrieveWorkpackageProcessors(queueProcessor)
				.stream()
				.map(QueueProcessorDescriptorRepository::mapToPackageProcessor)
				.collect(ImmutableList.toImmutableList());

		if (packageProcessorsList.isEmpty())
		{
			throw new ConfigurationException("There are no C_Queue_PackageProcessor records assigned to C_Queue_Processor!")
					.appendParametersToMessage()
					.setParameter("C_Queue_Processor_ID", queueProcessor.getC_Queue_Processor_ID());
		}

		return QueueProcessorDescriptorIndex.of(mapToQueueProcessor(queueProcessor), packageProcessorsList);
	}
}
