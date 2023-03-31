/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.ui.web.project.step;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.project.workorder.resource.WOProjectResourceId;
import de.metas.project.workorder.stepresource.WOProjectStepResourceService;
import de.metas.ui.web.window.datatypes.DocumentId;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class WOProjectStepRowInvalidateService
{
	@NonNull
	private final WOProjectStepResourceService woProjectStepResourceService;

	public WOProjectStepRowInvalidateService(final @NonNull WOProjectStepResourceService woProjectStepResourceService)
	{
		this.woProjectStepResourceService = woProjectStepResourceService;
	}

	@NonNull
	public ImmutableList<WOProjectStepResourceRow> getInvalidatedRows(@NonNull final ConcurrentHashMap<DocumentId, WOProjectStepResourceRow> rowsById)
	{
		final ImmutableSet<WOProjectResourceId> woProjectResourceIds = rowsById.values()
				.stream()
				.map(WOProjectStepResourceRow::getResourceId)
				.collect(ImmutableSet.toImmutableSet());

		return documentId2Row(woProjectResourceIds);
	}

	@NonNull
	private ImmutableList<WOProjectStepResourceRow> documentId2Row(@NonNull final ImmutableSet<WOProjectResourceId> woProjectResourceIds)
	{
		return woProjectStepResourceService.streamUnresolvedByResourceIds(woProjectResourceIds)
				.map(WOProjectStepResourceRowsLoader::buildFromWOStepResource)
				.collect(ImmutableList.toImmutableList());
	}
}
