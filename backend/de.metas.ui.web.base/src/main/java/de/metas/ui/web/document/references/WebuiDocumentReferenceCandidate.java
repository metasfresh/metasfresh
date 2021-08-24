package de.metas.ui.web.document.references;

import de.metas.document.references.related_documents.RelatedDocuments;
import de.metas.document.references.related_documents.RelatedDocumentsCandidateGroup;
import de.metas.document.references.related_documents.RelatedDocumentsEvaluationContext;
import de.metas.document.references.related_documents.RelatedDocumentsTargetWindow;
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
public class WebuiDocumentReferenceCandidate
{
	private final RelatedDocumentsCandidateGroup relatedDocumentsCandidateGroup;
	private final ITranslatableString filterCaption;

	public WebuiDocumentReferenceCandidate(
			@NonNull final RelatedDocumentsCandidateGroup relatedDocumentsCandidateGroup,
			@NonNull final ITranslatableString filterCaption)
	{
		this.relatedDocumentsCandidateGroup = relatedDocumentsCandidateGroup;
		this.filterCaption = filterCaption;
	}

	public Stream<WebuiDocumentReference> evaluateAndStream(@NonNull final RelatedDocumentsEvaluationContext context)
	{
		return relatedDocumentsCandidateGroup.evaluateAndStream(context)
				.map(relatedDocuments -> toDocumentReference(relatedDocuments, filterCaption));
	}

	public static WebuiDocumentReference toDocumentReference(
			@NonNull final RelatedDocuments relatedDocuments,
			@NonNull final ITranslatableString filterCaption)
	{
		return WebuiDocumentReference.builder()
				.id(WebuiDocumentReferenceId.ofRelatedDocumentsId(relatedDocuments.getId()))
				.internalName(relatedDocuments.getInternalName())
				.caption(relatedDocuments.getCaption())
				.targetWindow(toDocumentReferenceTargetWindow(relatedDocuments.getTargetWindow()))
				.priority(relatedDocuments.getPriority())
				.documentsCount(relatedDocuments.getRecordCount())
				.filter(MQueryDocumentFilterHelper.createDocumentFilterFromMQuery(relatedDocuments.getQuery(), filterCaption))
				.loadDuration(relatedDocuments.getRecordCountDuration())
				.build();
	}

	private static WebuiDocumentReferenceTargetWindow toDocumentReferenceTargetWindow(@NonNull final RelatedDocumentsTargetWindow relatedDocumentsTargetWindow)
	{
		final WindowId windowId = WindowId.of(relatedDocumentsTargetWindow.getAdWindowId());
		final String category = relatedDocumentsTargetWindow.getCategory();
		return category != null
				? WebuiDocumentReferenceTargetWindow.ofWindowIdAndCategory(windowId, category)
				: WebuiDocumentReferenceTargetWindow.ofWindowId(windowId);
	}

}
