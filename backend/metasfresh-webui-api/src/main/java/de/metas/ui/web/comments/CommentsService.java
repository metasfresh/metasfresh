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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.comments.CommentEntry;
import de.metas.comments.CommentEntryRepository;
import de.metas.ui.web.comments.json.JSONComment;
import de.metas.ui.web.comments.json.JSONCommentCreateRequest;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.json.DateTimeConverters;
import de.metas.ui.web.window.descriptor.factory.DocumentDescriptorFactory;
import de.metas.user.api.IUserDAO;
import de.metas.util.GuavaCollectors;
import de.metas.util.ImmutableMapEntry;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class CommentsService
{
	private final CommentEntryRepository commentEntryRepository;
	private final DocumentDescriptorFactory documentDescriptorFactory;
	final IUserDAO userDAO = Services.get(IUserDAO.class);

	public CommentsService(final CommentEntryRepository commentEntryRepository, final DocumentDescriptorFactory documentDescriptorFactory)
	{
		this.commentEntryRepository = commentEntryRepository;
		this.documentDescriptorFactory = documentDescriptorFactory;
	}

	@NonNull
	public final ViewRowComments getRowComments(@NonNull final IViewRow row)
	{
		return getRowComments(Collections.singletonList(row));
	}

	@NonNull
	public final ViewRowComments getRowComments(@NonNull final Collection<? extends IViewRow> rows)
	{
		if (rows.isEmpty())
		{
			return ViewRowComments.EMPTY;
		}

		final ImmutableMap<IViewRow, TableRecordReference> rowsForReferences = rows.stream()
				.flatMap(IViewRow::streamRecursive)
				.flatMap(this::toStreamOfValidTableReferences)
				.collect(GuavaCollectors.toImmutableMap());

		final Map<TableRecordReference, Boolean> referencesWithComments = commentEntryRepository.hasComments(rowsForReferences.values());

		final ImmutableMap.Builder<DocumentId, Boolean> result = ImmutableMap.builder();
		for (final IViewRow row : rows)
		{
			final TableRecordReference ref = rowsForReferences.get(row);
			result.put(row.getId(), referencesWithComments.getOrDefault(ref, false));
		}

		return ViewRowComments.of(result.build());
	}

	public boolean getRowComments(@NonNull final DocumentPath documentPath)
	{
		final Optional<TableRecordReference> referenceOptional = documentDescriptorFactory.getTableRecordReferenceIfPossible(documentPath);
		if (!referenceOptional.isPresent())
		{
			return false;
		}

		final TableRecordReference reference = referenceOptional.get();
		final Map<TableRecordReference, Boolean> referencesWithComments = commentEntryRepository.hasComments(ImmutableList.of(reference));
		return referencesWithComments.getOrDefault(reference, false);
	}

	@NonNull
	public List<JSONComment> getComments(@NonNull final DocumentPath documentPath, final ZoneId zoneId)
	{
		final TableRecordReference tableRecordReference = documentDescriptorFactory.getTableRecordReference(documentPath);
		final List<CommentEntry> comments = commentEntryRepository.retrieveLastCommentEntries(tableRecordReference, 100);

		return comments.stream()
				.map(comment -> toJsonComment(comment, zoneId))
				.sorted(Comparator.comparing(JSONComment::getCreated).reversed())
				.collect(GuavaCollectors.toImmutableList());
	}

	public void addComment(@NonNull final DocumentPath documentPath, @NonNull final JSONCommentCreateRequest jsonCommentCreateRequest)
	{
		final TableRecordReference tableRecordReference = documentDescriptorFactory.getTableRecordReference(documentPath);

		commentEntryRepository.createCommentEntry(jsonCommentCreateRequest.getText(), tableRecordReference);
	}

	@NonNull
	private Stream<ImmutableMapEntry<IViewRow, TableRecordReference>> toStreamOfValidTableReferences(@NonNull final IViewRow row)
	{
		if (row.getDocumentPath() == null)
		{
			return Stream.empty();
		}
		final Optional<TableRecordReference> optionalReference = documentDescriptorFactory.getTableRecordReferenceIfPossible(row.getDocumentPath());
		return optionalReference.map(tableRecordReference -> Stream.of(GuavaCollectors.entry(row, tableRecordReference))).orElseGet(Stream::empty);
	}

	@NonNull
	private JSONComment toJsonComment(@NonNull final CommentEntry comment, final ZoneId zoneId)
	{
		final String text = comment.getText();
		final String created = DateTimeConverters.toJson(comment.getCreated(), zoneId);
		final String createdBy = userDAO.retrieveUserFullname(comment.getCreatedBy());

		return JSONComment.builder()
				.text(text)
				.created(created)
				.createdBy(createdBy)
				.build();
	}
}
