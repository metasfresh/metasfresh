package de.metas.ui.web.document.references;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.adempiere.ad.element.api.AdWindowId;

import com.google.common.collect.ImmutableList;

import de.metas.document.references.ZoomInfo;
import de.metas.document.references.ZoomInfoCandidate;
import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.document.filter.provider.userQuery.MQueryDocumentFilterHelper;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.util.lang.Priority;
import lombok.NonNull;

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

public class DocumentReferenceCandidate
{
	public static ImmutableList<DocumentReference> evaluateAll(final Collection<DocumentReferenceCandidate> candidates)
	{
		if (candidates.isEmpty())
		{
			return ImmutableList.of();
		}

		final HashMap<AdWindowId, Priority> alreadySeenWindowIds = new HashMap<>();

		return candidates.stream()
				.map(candidate -> candidate.evaluate(alreadySeenWindowIds).orElse(null))
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());
	}

	private final ZoomInfoCandidate zoomInfoCandidate;
	private final ITranslatableString filterCaption;

	public DocumentReferenceCandidate(
			@NonNull final ZoomInfoCandidate zoomInfoCandidate,
			@NonNull final ITranslatableString filterCaption)
	{
		this.zoomInfoCandidate = zoomInfoCandidate;
		this.filterCaption = filterCaption;
	}

	public Optional<DocumentReference> evaluate()
	{
		return zoomInfoCandidate.evaluate()
				.map(zoomInfo -> toDocumentReference(zoomInfo, filterCaption));
	}

	private Optional<DocumentReference> evaluate(@NonNull final Map<AdWindowId, Priority> alreadySeenWindowIds)
	{
		return zoomInfoCandidate.evaluate(alreadySeenWindowIds)
				.map(zoomInfo -> toDocumentReference(zoomInfo, filterCaption));
	}

	public static DocumentReference toDocumentReference(
			@NonNull final ZoomInfo zoomInfo,
			@NonNull final ITranslatableString filterCaption)
	{
		final WindowId windowId = WindowId.of(zoomInfo.getAdWindowId());

		// NOTE: we use the windowId as the ID because we want to have only one document reference per window.
		// In case of multiple references, the one with highest priority shall be picked.\\
		final String id = windowId.toJson();

		return DocumentReference.builder()
				.id(id)
				.internalName(zoomInfo.getInternalName())
				.caption(zoomInfo.getCaption())
				.windowId(windowId)
				.priority(zoomInfo.getPriority())
				.documentsCount(zoomInfo.getRecordCount())
				.filter(MQueryDocumentFilterHelper.createDocumentFilterFromMQuery(zoomInfo.getQuery(), filterCaption))
				.loadDuration(zoomInfo.getRecordCountDuration())
				.build();
	}

}
