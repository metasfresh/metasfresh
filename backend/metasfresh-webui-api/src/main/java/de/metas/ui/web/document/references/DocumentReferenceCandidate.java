package de.metas.ui.web.document.references;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import com.google.common.collect.ImmutableList;

import de.metas.document.references.ZoomInfo;
import de.metas.document.references.ZoomInfoCandidate;
import de.metas.document.references.ZoomTargetWindow;
import de.metas.document.references.ZoomTargetWindowEvaluationContext;
import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.document.filter.provider.userQuery.MQueryDocumentFilterHelper;
import de.metas.ui.web.window.datatypes.WindowId;
import lombok.NonNull;
import lombok.ToString;

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

@ToString
public class DocumentReferenceCandidate
{
	public static ImmutableList<DocumentReference> evaluateAll(final Collection<DocumentReferenceCandidate> candidates)
	{
		if (candidates.isEmpty())
		{
			return ImmutableList.of();
		}

		final ZoomTargetWindowEvaluationContext alreadySeenWindowIds = new ZoomTargetWindowEvaluationContext();

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

	private Optional<DocumentReference> evaluate(@NonNull final ZoomTargetWindowEvaluationContext alreadySeenWindowIds)
	{
		return zoomInfoCandidate.evaluate(alreadySeenWindowIds)
				.map(zoomInfo -> toDocumentReference(zoomInfo, filterCaption));
	}

	public static DocumentReference toDocumentReference(
			@NonNull final ZoomInfo zoomInfo,
			@NonNull final ITranslatableString filterCaption)
	{
		return DocumentReference.builder()
				.id(DocumentReferenceId.ofZoomInfoId(zoomInfo.getId()))
				.internalName(zoomInfo.getInternalName())
				.caption(zoomInfo.getCaption())
				.targetWindow(toDocumentReferenceTargetWindow(zoomInfo.getTargetWindow()))
				.priority(zoomInfo.getPriority())
				.documentsCount(zoomInfo.getRecordCount())
				.filter(MQueryDocumentFilterHelper.createDocumentFilterFromMQuery(zoomInfo.getQuery(), filterCaption))
				.loadDuration(zoomInfo.getRecordCountDuration())
				.build();
	}

	private static DocumentReferenceTargetWindow toDocumentReferenceTargetWindow(@NonNull final ZoomTargetWindow zoomTargetWindow)
	{
		final WindowId windowId = WindowId.of(zoomTargetWindow.getAdWindowId());
		final String category = zoomTargetWindow.getCategory();
		return category != null
				? DocumentReferenceTargetWindow.ofWindowIdAndCategory(windowId, category)
				: DocumentReferenceTargetWindow.ofWindowId(windowId);
	}

}
