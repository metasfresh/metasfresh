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

package de.metas.async.processor.descriptor.model;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.async.processor.QueuePackageProcessorId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.List;
import java.util.Map;

/**
 * To be used as the cached items within {@link de.metas.async.processor.descriptor.QueueProcessorDescriptorRepository},
 */
@Value
@Builder
public class QueueProcessorDescriptorIndex
{
	@NonNull
	QueueProcessorDescriptor queueProcessor;

	@NonNull
	Map<QueuePackageProcessorId, QueuePackageProcessor> packageProcessors;

	@NonNull
	public static QueueProcessorDescriptorIndex of(@NonNull final QueueProcessorDescriptor queueProcessor, @NonNull final List<QueuePackageProcessor> packageProcessorList)
	{
		final Map<QueuePackageProcessorId, QueuePackageProcessor> packageProcessors = Maps.uniqueIndex(packageProcessorList, QueuePackageProcessor::getQueuePackageProcessorId);

		return QueueProcessorDescriptorIndex.builder()
				.queueProcessor(queueProcessor)
				.packageProcessors(packageProcessors)
				.build();
	}

	@NonNull
	public ImmutableSet<QueuePackageProcessorId> getQueuePackageProcessorIds()
	{
		return ImmutableSet.copyOf(packageProcessors.keySet());
	}

	@NonNull
	public QueuePackageProcessor getPackageProcessor(@NonNull final QueuePackageProcessorId packageProcessorId)
	{
		return packageProcessors.get(packageProcessorId);
	}
}