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

package de.metas.camel.externalsystems.metasfresh.bpartner;

import com.google.common.collect.ImmutableList;
import de.metas.camel.externalsystems.metasfresh.restapi.feedback.FeedbackConfig;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicInteger;

@Value
@Builder(toBuilder = true)
public class UpsertBPartnersRouteContext
{
	@NonNull
	String batchId;

	@NonNull
	ImmutableList.Builder<String> errorsCollector;

	@NonNull
	String externalSystemConfigValue;

	@NonNull
	String orgCode;

	@Nullable
	FeedbackConfig feedbackConfig;

	@NonNull
	@Builder.Default
	AtomicInteger totalItemsToUpsert = new AtomicInteger(0);

	public void increaseReadItemsCount(final int readItemsCount)
	{
		totalItemsToUpsert.addAndGet(readItemsCount);
	}

	public void collectError(@NonNull final String errorMessage)
	{
		errorsCollector.add(errorMessage);
	}
}
