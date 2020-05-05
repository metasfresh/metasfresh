package de.metas.ui.web.process;

import java.util.List;
import java.util.Objects;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.google.common.collect.ImmutableList;

import de.metas.ui.web.view.ViewRowIdsSelection;
import de.metas.ui.web.window.datatypes.DocumentPath;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * Request for creating a new process instance.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Value
@Immutable
public final class CreateProcessInstanceRequest
{
	private final ProcessId processId;
	private final DocumentPath singleDocumentPath;
	private final List<DocumentPath> selectedIncludedDocumentPaths;
	private final ViewRowIdsSelection viewRowIdsSelection;
	private final ViewRowIdsSelection parentViewRowIdsSelection;
	private final ViewRowIdsSelection childViewRowIdsSelection;

	@Builder
	private CreateProcessInstanceRequest(
			@NonNull final ProcessId processId,
			@Nullable final DocumentPath singleDocumentPath,
			@Nullable final List<DocumentPath> selectedIncludedDocumentPaths,
			@Nullable final ViewRowIdsSelection viewRowIdsSelection,
			@Nullable final ViewRowIdsSelection parentViewRowIdsSelection,
			@Nullable final ViewRowIdsSelection childViewRowIdsSelection)
	{
		this.processId = processId;

		this.singleDocumentPath = singleDocumentPath;
		this.selectedIncludedDocumentPaths = selectedIncludedDocumentPaths != null ? ImmutableList.copyOf(selectedIncludedDocumentPaths) : ImmutableList.of();

		this.viewRowIdsSelection = viewRowIdsSelection;
		this.parentViewRowIdsSelection = parentViewRowIdsSelection;
		this.childViewRowIdsSelection = childViewRowIdsSelection;
	}

	public void assertProcessIdEquals(final ProcessId expectedProcessId)
	{
		if (!Objects.equals(processId, expectedProcessId))
		{
			throw new IllegalArgumentException("Request's processId is not valid. It shall be " + expectedProcessId + " but it was " + processId);
		}
	}

	public int getProcessIdAsInt()
	{
		return processId.getProcessIdAsInt();
	}

}
