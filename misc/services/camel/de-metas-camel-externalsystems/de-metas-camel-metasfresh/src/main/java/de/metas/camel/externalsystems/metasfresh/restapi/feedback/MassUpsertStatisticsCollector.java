/*
 * #%L
 * de-metas-camel-metasfresh
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

package de.metas.camel.externalsystems.metasfresh.restapi.feedback;

import com.google.common.collect.ImmutableList;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicInteger;

@Value
@Builder
public class MassUpsertStatisticsCollector
{
	@NonNull
	String batchId;

	@NonNull
	@Getter(AccessLevel.NONE)
	@Builder.Default
	ImmutableList.Builder<String> errorsCollector = ImmutableList.builder();

	@NonNull
	@Builder.Default
	@Getter(AccessLevel.NONE)
	AtomicInteger processedItemsCount = new AtomicInteger(0);

	@Nullable
	FeedbackConfig feedbackConfig;

	public void increaseProcessedItemsCount(final int readItemsCount)
	{
		processedItemsCount.addAndGet(readItemsCount);
	}

	public void collectError(@NonNull final String errorMessage)
	{
		errorsCollector.add(errorMessage);
	}

	public int getProcessedItemsCount()
	{
		return processedItemsCount.get();
	}

	@NonNull
	public ImmutableList<String> getCollectedErrors()
	{
		return errorsCollector.build();
	}
}
