package de.metas.ui.web.document.references;

import de.metas.document.references.related_documents.ZoomInfo;
import de.metas.document.references.related_documents.ZoomInfoCandidateGroup;
import de.metas.document.references.related_documents.ZoomTargetWindow;
import de.metas.document.references.related_documents.ZoomTargetWindowEvaluationContext;
import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.document.filter.provider.userQuery.MQueryDocumentFilterHelper;
import de.metas.ui.web.window.datatypes.WindowId;
import lombok.NonNull;
import lombok.ToString;

import java.util.stream.Stream;

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
	private final ZoomInfoCandidateGroup zoomInfoCandidateGroup;
	private final ITranslatableString filterCaption;

	public DocumentReferenceCandidate(
			@NonNull final ZoomInfoCandidateGroup zoomInfoCandidateGroup,
			@NonNull final ITranslatableString filterCaption)
	{
		this.zoomInfoCandidateGroup = zoomInfoCandidateGroup;
		this.filterCaption = filterCaption;
	}

	public Stream<DocumentReference> evaluateAndStream()
	{
		return zoomInfoCandidateGroup.evaluateAndStream(null)
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
