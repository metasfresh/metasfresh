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

package de.metas.notes;

import com.google.common.collect.ImmutableList;
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
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.List;

/**
 * A Note is made of an {@link I_CM_Chat} as the parent storing the table and record IDs,
 * and an {@link I_CM_ChatEntry} as the children storing the text data of the note.
 * <p>
 * There can be multiple notes for a record.
 */
@Repository
public class NotesRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IADTableDAO tableDAO = Services.get(IADTableDAO.class);

	public void createNote(final @NonNull String characterData, @NonNull final TableRecordReference tableRecordReference)
	{
		final I_CM_Chat chat = getOrCreateChat(tableRecordReference);

		final I_CM_ChatEntry chatEntry = InterfaceWrapperHelper.newInstance(I_CM_ChatEntry.class);
		chatEntry.setCM_Chat_ID(chat.getCM_Chat_ID());
		chatEntry.setConfidentialType(X_CM_ChatEntry.CONFIDENTIALTYPE_PublicInformation);
		chatEntry.setCharacterData(characterData);
		chatEntry.setChatEntryType(X_CM_ChatEntry.CHATENTRYTYPE_NoteFlat);
		InterfaceWrapperHelper.save(chatEntry);
	}

	@NonNull
	public List<I_CM_ChatEntry> retrieveNotes(@NonNull final TableRecordReference tableRecordReference, final int maxNumberOfRecords)
	{
		final ChatId chatId = getChatIdOrNull(tableRecordReference);

		if (chatId == null)
		{
			return ImmutableList.of();
		}

		return queryBL.createQueryBuilder(I_CM_ChatEntry.class)
				.addEqualsFilter(I_CM_ChatEntry.COLUMNNAME_CM_Chat_ID, chatId)
				.orderByDescending(I_CM_ChatEntry.COLUMNNAME_Created)
				.setLimit(maxNumberOfRecords)
				.create()
				.listImmutable(I_CM_ChatEntry.class);
	}

	@NonNull
	private I_CM_Chat getOrCreateChat(final @NonNull TableRecordReference tableRecordReference)
	{
		final ChatId chatId = getChatIdOrNull(tableRecordReference);

		if (chatId != null)
		{
			return InterfaceWrapperHelper.load(chatId, I_CM_Chat.class);
		}

		final String tableName = tableDAO.retrieveTableName(AdTableId.ofRepoId(tableRecordReference.getAD_Table_ID()));

		final I_CM_Chat chat = InterfaceWrapperHelper.newInstance(I_CM_Chat.class);
		chat.setDescription("Table name: " + tableName);
		chat.setAD_Table_ID(tableRecordReference.getAD_Table_ID());
		chat.setRecord_ID(tableRecordReference.getRecord_ID());
		chat.setConfidentialType(X_CM_Chat.CONFIDENTIALTYPE_PublicInformation);
		chat.setModerationType(X_CM_Chat.MODERATIONTYPE_NotModerated);
		InterfaceWrapperHelper.save(chat);
		return chat;
	}

	@Nullable
	private ChatId getChatIdOrNull(final @NonNull TableRecordReference tableRecordReference)
	{
		return queryBL.createQueryBuilder(I_CM_Chat.class)
				.addEqualsFilter(I_CM_Chat.COLUMNNAME_AD_Table_ID, tableRecordReference.getAD_Table_ID())
				.addEqualsFilter(I_CM_Chat.COLUMNNAME_Record_ID, tableRecordReference.getRecord_ID())
				.create()
				.firstId(ChatId::ofRepoIdOrNull);
	}
}
