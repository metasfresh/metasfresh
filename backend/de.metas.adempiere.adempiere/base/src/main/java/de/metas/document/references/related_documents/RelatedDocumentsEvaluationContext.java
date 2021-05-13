/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.document.references.related_documents;

import de.metas.util.lang.Priority;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.util.HashMap;

@ToString
public class RelatedDocumentsEvaluationContext
{
	@Getter
	private final RelatedDocumentsPermissions permissions;
	@Getter
	private final RelatedDocumentsId onlyRelatedDocumentsId;

	@Nullable
	private final HashMap<RelatedDocumentsTargetWindow, Priority> alreadySeenWindows;

	@Builder
	private RelatedDocumentsEvaluationContext(
			@NonNull final RelatedDocumentsPermissions permissions,
			@Nullable final RelatedDocumentsId onlyRelatedDocumentsId,
			final boolean doNotTrackSeenWindows)
	{
		this.permissions = permissions;
		this.onlyRelatedDocumentsId = onlyRelatedDocumentsId;
		this.alreadySeenWindows = doNotTrackSeenWindows ? null : new HashMap<>();
	}

	public Priority getPriorityOrNull(final RelatedDocumentsTargetWindow window)
	{
		return alreadySeenWindows != null ? alreadySeenWindows.get(window) : null;
	}

	public void putAlreadySeenWindow(@NonNull final RelatedDocumentsTargetWindow targetWindow, @NonNull final Priority priority)
	{
		if (alreadySeenWindows != null)
		{
			alreadySeenWindows.put(targetWindow, priority);
		}
	}
}
