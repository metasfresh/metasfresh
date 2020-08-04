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
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.json.DateTimeConverters;
import de.metas.ui.web.window.descriptor.factory.DocumentDescriptorFactory;
import de.metas.user.api.IUserDAO;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentsService
{
	private final CommentEntryRepository commentEntryRepository;
	private final DocumentDescriptorFactory documentDescriptorFactory;
	final IUserDAO userDAO = Services.get(IUserDAO.class);

	public static final IdentityHashMap<IViewRow, Boolean> NO_COMMENTS = new IdentityHashMap<>(0);

	public CommentsService(final CommentEntryRepository commentEntryRepository, final DocumentDescriptorFactory documentDescriptorFactory)
	{
		this.commentEntryRepository = commentEntryRepository;
		this.documentDescriptorFactory = documentDescriptorFactory;
	}

	@NonNull
	public final IdentityHashMap<IViewRow, Boolean> hasComments(@Nullable final IViewRow row)
	{
		if (row == null)
		{
			return NO_COMMENTS;
		}

		return hasComments(Collections.singletonList(row));
	}

	@NonNull
	public final IdentityHashMap<IViewRow, Boolean> hasComments(@NonNull final Collection<? extends IViewRow> rows)
	{
		if (rows.isEmpty())
		{
			return NO_COMMENTS;
		}

		final ImmutableMap<IViewRow, TableRecordReference> rowsForReferences = rows.stream()
				.flatMap(IViewRow::streamRecursive)
				.map(row -> GuavaCollectors.entry(row, documentDescriptorFactory.getTableRecordReference(row.getDocumentPath())))
				.collect(GuavaCollectors.toImmutableMap());

		final Map<TableRecordReference, Boolean> referencesWithComments = commentEntryRepository.hasComments(rowsForReferences.values());

		final IdentityHashMap<IViewRow, Boolean> result = new IdentityHashMap<>();
		for (final IViewRow row : rowsForReferences.keySet())
		{
			final TableRecordReference ref = rowsForReferences.get(row);
			result.put(row, referencesWithComments.getOrDefault(ref, false));
		}

		return result;
	}

	@NonNull
	public Boolean hasComments(@NonNull final DocumentPath documentPath)
	{
		final TableRecordReference reference = documentDescriptorFactory.getTableRecordReference(documentPath);
		final Map<TableRecordReference, Boolean> referencesWithComments = commentEntryRepository.hasComments(ImmutableList.of(reference));
		return referencesWithComments.getOrDefault(reference, false);
	}

	@NonNull
	public List<JSONComment> getCommentsFor(@NonNull final TableRecordReference tableRecordReference, final ZoneId zoneId)
	{

		final List<CommentEntry> comments = commentEntryRepository.retrieveLastCommentEntries(tableRecordReference, 100);

		return comments.stream()
				.map(comment -> toJsonComment(comment, zoneId))
				.sorted(Comparator.comparing(JSONComment::getCreated).reversed())
				.collect(GuavaCollectors.toImmutableList());
	}

	public void addComment(@NonNull final TableRecordReference tableRecordReference, @NonNull final JSONCommentCreateRequest jsonCommentCreateRequest)
	{
		commentEntryRepository.createCommentEntry(jsonCommentCreateRequest.getText(), tableRecordReference);
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
