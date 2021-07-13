package de.metas.impexp;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

@ToString
public class MockedImportRecordsAsyncExecutor implements ImportRecordsAsyncExecutor
{
	private final List<ImportRecordsRequest> scheduledRequests = new ArrayList<>();
	private int nextWorkpackageId = 1;

	@Override
	public AsyncImportRecordsResponse schedule(@NonNull final ImportRecordsRequest request)
	{
		scheduledRequests.add(request);
		
		return AsyncImportRecordsResponse.builder()
				.workpackageId(nextWorkpackageId++)
				.build();
	}

	public void assertNoCalls()
	{
		assertThat(scheduledRequests).isEmpty();
	}

	public void assertOneCall()
	{
		assertThat(scheduledRequests).hasSize(1);
	}

	public ImportRecordsRequest getSingleScheduledRequest()
	{
		return CollectionUtils.singleElement(scheduledRequests);
	}
}
