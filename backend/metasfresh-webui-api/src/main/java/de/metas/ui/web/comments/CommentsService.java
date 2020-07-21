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

import de.metas.comments.CommentEntry;
import de.metas.comments.CommentEntryRepository;
import de.metas.ui.web.comments.json.JSONComment;
import de.metas.ui.web.comments.json.JSONCommentCreateRequest;
import de.metas.ui.web.window.datatypes.json.DateTimeConverters;
import de.metas.user.api.IUserDAO;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;

@Service
public class CommentsService
{
	private final CommentEntryRepository commentEntryRepository;
	final IUserDAO userDAO = Services.get(IUserDAO.class);

	public CommentsService(final CommentEntryRepository commentEntryRepository)
	{
		this.commentEntryRepository = commentEntryRepository;
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

	public void addComment(@NonNull final TableRecordReference tableRecordReference, @NonNull final JSONCommentCreateRequest jsonCommentCreateRequest)
	{
		commentEntryRepository.createCommentEntry(jsonCommentCreateRequest.getText(), tableRecordReference);
	}
}
