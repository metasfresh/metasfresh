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
import de.metas.comments.CommentEntryId;
import de.metas.comments.CommentEntryRepository;
import de.metas.comments.CommentEntryParentId;
import de.metas.ui.web.comments.json.JSONComment;
import de.metas.ui.web.comments.json.JSONCommentCreateRequest;
import de.metas.ui.web.window.datatypes.json.DateTimeConverters;
import de.metas.user.UserId;
import de.metas.util.time.SystemTime;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.assertj.core.api.Assertions;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_CM_Chat;
import org.compiere.model.I_CM_ChatEntry;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.X_CM_ChatEntry;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class CommentsServiceTest
{
	public static final int AD_USER_ID = 10;
	public static final String THE_USER_NAME = "The User Name";
	public static final ZonedDateTime ZONED_DATE_TIME = ZonedDateTime.of(2020, Month.APRIL.getValue(), 23, 1, 1, 1, 0, ZoneId.of("UTC+8"));

	private CommentEntryRepository commentEntryRepository;
	private CommentsService commentsService;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		SystemTime.setTimeSource(() -> ZONED_DATE_TIME.toInstant().toEpochMilli());

		// all created POs will have this user
		Env.setLoggedUserId(Env.getCtx(), UserId.ofRepoId(AD_USER_ID));

		createDefaultUser();
		commentEntryRepository = new CommentEntryRepository();
		commentsService = new CommentsService(commentEntryRepository);
	}

	@Nested
	class TestCreateComments
	{
		@Test
		void create2Comments()
		{
			// create test data
			final TableRecordReference tableRecordReference = TableRecordReference.of("DummyTable", 1);

			apiAddComment(tableRecordReference, "comment1");
			apiAddComment(tableRecordReference, "comment2");

			// check the comments exist
			final List<CommentEntry> actual = commentEntryRepository.retrieveLastCommentEntries(tableRecordReference, 2);

			final List<CommentEntry> expected = Arrays.asList(
					createCommentEntry("comment1"),
					createCommentEntry("comment2")
			);

			Assertions.assertThat(actual)
					.usingElementComparatorIgnoringFields("id")
					.isEqualTo(expected);
		}

		private void apiAddComment(final TableRecordReference tableRecordReference, final String comment)
		{
			commentsService.addComment(tableRecordReference, new JSONCommentCreateRequest(comment));
		}

		private CommentEntry createCommentEntry(final String comment)
		{
			return CommentEntry.builder()
					.createdBy(UserId.ofRepoId(AD_USER_ID))
					.created(updateToSystemTimezone(ZONED_DATE_TIME))
					.text(comment)
					.id(CommentEntryId.ofRepoId(1))
					.build();
		}

		/**
		 * Since JDBC always uses the system local timezone, the expected result should also be compared in system timezone.
		 * <p>
		 * Basically this method is needed to make sure both strings have the same timezone.
		 */
		@SuppressWarnings("SameParameterValue")
		private ZonedDateTime updateToSystemTimezone(final ZonedDateTime zonedDateTime)
		{
			return TimeUtil.convertToTimeZone(zonedDateTime, ZoneId.systemDefault());
		}
	}

	@Nested
	class TestGetComments
	{
		@Test
		void commentsExist()
		{
			// create test data
			final TableRecordReference tableRecordReference = TableRecordReference.of("DummyTable", 1);
			final CommentEntryParentId commentEntryParentId = createChat(tableRecordReference);
			createChatEntry(commentEntryParentId, "comment1");
			createChatEntry(commentEntryParentId, "comment2");

			//
			final List<JSONComment> actual = commentsService.getCommentsFor(tableRecordReference, ZoneId.of("UTC+8"));
			System.out.println(actual);

			final String zonedDateTimeString = DateTimeConverters.toJson(ZONED_DATE_TIME, ZoneId.of("UTC+8"));

			final List<JSONComment> expected = Arrays.asList(
					createJsonComment(zonedDateTimeString, "comment1"),
					createJsonComment(zonedDateTimeString, "comment2")
			);

			Assertions.assertThat(actual).isEqualTo(expected);
		}

		@Test
		void noCommentsExist()
		{
			// create test data
			final TableRecordReference tableRecordReference = TableRecordReference.of("DummyTable", 1);

			//
			final List<JSONComment> actual = commentsService.getCommentsFor(tableRecordReference, ZoneId.of("UTC+8"));
			System.out.println(actual);

			final List<JSONComment> expected = Collections.emptyList();

			Assertions.assertThat(actual).isEqualTo(expected);
		}

		private JSONComment createJsonComment(final String zonedDateTimeString, final String comment)
		{
			return JSONComment.builder()
					.created(zonedDateTimeString)
					.text(comment)
					.createdBy(THE_USER_NAME)
					.build();
		}
	}

	/**
	 * Not necessary, but helpful to have an actual user name.
	 */
	private void createDefaultUser()
	{
		final I_AD_User user = InterfaceWrapperHelper.newInstance(I_AD_User.class);
		user.setAD_User_ID(AD_USER_ID);
		user.setName(THE_USER_NAME);
		InterfaceWrapperHelper.save(user);
	}

	private CommentEntryParentId createChat(final TableRecordReference tableRecordReference)
	{
		final I_CM_Chat chat = InterfaceWrapperHelper.newInstance(I_CM_Chat.class);
		chat.setDescription("Table name: " + I_C_BPartner.Table_Name);
		chat.setAD_Table_ID(tableRecordReference.getAD_Table_ID());
		chat.setRecord_ID(tableRecordReference.getRecord_ID());
		InterfaceWrapperHelper.save(chat);
		return CommentEntryParentId.ofRepoId(chat.getCM_Chat_ID());
	}

	private void createChatEntry(final CommentEntryParentId commentEntryParentId, final String characterData)
	{
		final I_CM_ChatEntry chatEntry = InterfaceWrapperHelper.newInstance(I_CM_ChatEntry.class);
		chatEntry.setCM_Chat_ID(commentEntryParentId.getRepoId());
		chatEntry.setConfidentialType(X_CM_ChatEntry.CONFIDENTIALTYPE_PublicInformation);
		chatEntry.setCharacterData(characterData);
		chatEntry.setChatEntryType(X_CM_ChatEntry.CHATENTRYTYPE_NoteFlat);
		InterfaceWrapperHelper.save(chatEntry);
		CommentEntryId.ofRepoId(chatEntry.getCM_ChatEntry_ID());
	}
}
