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

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.async.model.I_C_Queue_PackageProcessor;
import de.metas.async.model.I_C_Queue_Processor;
import de.metas.async.processor.QueuePackageProcessorId;
import de.metas.async.processor.QueueProcessorId;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * To be used as the cached items within {@link QueueProcessorDescriptorIndex}, 
 */
class QueueProcessorDescriptor
{
	@NonNull
	@Getter
	private final I_C_Queue_Processor queueProcessor;

	@NonNull
	private final Map<QueuePackageProcessorId, I_C_Queue_PackageProcessor> packageProcessors;

	public QueueProcessorDescriptor(
			@NonNull final I_C_Queue_Processor queueProcessor,
			@NonNull final List<I_C_Queue_PackageProcessor> packageProcessorList)
	{
		this.queueProcessor = queueProcessor;
		this.packageProcessors = Maps.uniqueIndex(packageProcessorList, (packageProcessor) -> QueuePackageProcessorId.ofRepoId(packageProcessor.getC_Queue_PackageProcessor_ID()));
	}

	@NonNull
	public QueueProcessorId getQueueProcessorId()
	{
		return QueueProcessorId.ofRepoId(queueProcessor.getC_Queue_Processor_ID());
	}

	@NonNull
	public ImmutableSet<QueuePackageProcessorId> getQueuePackageProcessorIds()
	{
		return ImmutableSet.copyOf(packageProcessors.keySet());
	}

	public boolean hasPackageProcessor(@NonNull final QueuePackageProcessorId packageProcessorId)
	{
		return packageProcessors.get(packageProcessorId) != null;
	}

	public Optional<I_C_Queue_PackageProcessor> getPackageProcessor(@NonNull final QueuePackageProcessorId packageProcessorId)
	{
		return Optional.ofNullable(packageProcessors.get(packageProcessorId));
	}
}