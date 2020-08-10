/*
 * #%L
 * metasfresh-webui-api
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

package de.metas.ui.web.comments;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import de.metas.ui.web.window.datatypes.DocumentId;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public final class ViewRowCommentsSummary
{
	private final ImmutableMap<DocumentId, Boolean> hasCommentsByDocumentId;

	public static final ViewRowCommentsSummary EMPTY = new ViewRowCommentsSummary(ImmutableMap.of());

	public static ViewRowCommentsSummary ofMap(@NonNull final Map<DocumentId, Boolean> hasCommentsByDocumentId)
	{
		return !hasCommentsByDocumentId.isEmpty()
				? new ViewRowCommentsSummary(ImmutableMap.copyOf(hasCommentsByDocumentId))
				: EMPTY;
	}

	private ViewRowCommentsSummary(@NonNull final ImmutableMap<DocumentId, Boolean> documentsWithComments)
	{
		this.hasCommentsByDocumentId = documentsWithComments;
	}

	public boolean hasComments(@NonNull final DocumentId documentId)
	{
		return hasCommentsByDocumentId.getOrDefault(documentId, false);
	}
}
