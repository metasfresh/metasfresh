/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.comments;

import com.google.common.collect.ImmutableList;
import de.metas.user.UserId;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_CM_Chat;
import org.compiere.model.I_CM_ChatEntry;
import org.compiere.model.X_CM_Chat;
import org.compiere.model.X_CM_ChatEntry;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * A Comment is made of an {@link I_CM_Chat} as the parent storing the table and record IDs,
 * and an {@link I_CM_ChatEntry} as the children storing the text data of the Comment.
 * <p>
 * There can be multiple Comments for a record.
 */
@Repository
public class CommentEntryRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IADTableDAO tableDAO = Services.get(IADTableDAO.class);

	public void createCommentEntry(final @NonNull String characterData, @NonNull final TableRecordReference tableRecordReference)
	{
		final CommentId commentId = getOrCreateChat(tableRecordReference);

		final I_CM_ChatEntry chatEntry = InterfaceWrapperHelper.newInstance(I_CM_ChatEntry.class);
		chatEntry.setCM_Chat_ID(commentId.getRepoId());
		chatEntry.setConfidentialType(X_CM_ChatEntry.CONFIDENTIALTYPE_PublicInformation);
		chatEntry.setCharacterData(characterData);
		chatEntry.setChatEntryType(X_CM_ChatEntry.CHATENTRYTYPE_NoteFlat);
		InterfaceWrapperHelper.save(chatEntry);
	}

	@NonNull
	public List<CommentEntry> retrieveLastCommentEntries(@NonNull final TableRecordReference tableRecordReference, final int maxNumberOfRecords)
	{
		final CommentId commentId = getChatIdOrNull(tableRecordReference);

		if (commentId == null)
		{
			return ImmutableList.of();
		}

		return queryBL.createQueryBuilder(I_CM_ChatEntry.class)
				.addEqualsFilter(I_CM_ChatEntry.COLUMNNAME_CM_Chat_ID, commentId)
				.orderByDescending(I_CM_ChatEntry.COLUMNNAME_Created)
				.setLimit(maxNumberOfRecords)
				.create()
				.iterateAndStream()
				.map(CommentEntryRepository::toRecordComment)
				.collect(GuavaCollectors.toImmutableList());
	}

	@NonNull
	private static CommentEntry toRecordComment(@NonNull final I_CM_ChatEntry chatEntry)
	{
		final UserId createdBy = UserId.ofRepoId(chatEntry.getCreatedBy());
		final ZonedDateTime created = TimeUtil.asZonedDateTime(chatEntry.getCreated());
		final String text = chatEntry.getCharacterData();
		final CommentEntryId id = CommentEntryId.ofRepoId(chatEntry.getCM_ChatEntry_ID());

		return CommentEntry.builder()
				.created(created)
				.createdBy(createdBy)
				.text(text)
				.id(id)
				.build();
	}

	@NonNull
	private CommentId getOrCreateChat(final @NonNull TableRecordReference tableRecordReference)
	{
		final CommentId commentId = getChatIdOrNull(tableRecordReference);

		if (commentId != null)
		{
			return commentId;
		}

		final String tableName = tableDAO.retrieveTableName(AdTableId.ofRepoId(tableRecordReference.getAD_Table_ID()));

		final I_CM_Chat chat = InterfaceWrapperHelper.newInstance(I_CM_Chat.class);
		chat.setDescription("Table name: " + tableName);
		chat.setAD_Table_ID(tableRecordReference.getAD_Table_ID());
		chat.setRecord_ID(tableRecordReference.getRecord_ID());
		chat.setConfidentialType(X_CM_Chat.CONFIDENTIALTYPE_PublicInformation);
		chat.setModerationType(X_CM_Chat.MODERATIONTYPE_NotModerated);
		InterfaceWrapperHelper.save(chat);
		return CommentId.ofRepoId(chat.getCM_Chat_ID());
	}

	@Nullable
	private CommentId getChatIdOrNull(final @NonNull TableRecordReference tableRecordReference)
	{
		return queryBL.createQueryBuilder(I_CM_Chat.class)
				.addEqualsFilter(I_CM_Chat.COLUMNNAME_AD_Table_ID, tableRecordReference.getAD_Table_ID())
				.addEqualsFilter(I_CM_Chat.COLUMNNAME_Record_ID, tableRecordReference.getRecord_ID())
				.orderBy(I_CM_Chat.COLUMNNAME_CM_Chat_ID)
				.create()
				.firstId(CommentId::ofRepoIdOrNull);
	}
}
