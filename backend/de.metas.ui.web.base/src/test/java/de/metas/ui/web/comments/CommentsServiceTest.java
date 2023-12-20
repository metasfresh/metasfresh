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
import de.metas.comments.CommentEntryId;
import de.metas.comments.CommentEntryParentId;
import de.metas.comments.CommentsRepository;
import de.metas.common.util.time.SystemTime;
import de.metas.ui.web.comments.json.JSONComment;
import de.metas.ui.web.comments.json.JSONCommentCreateRequest;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProviderFactory;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvidersService;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.ViewRow;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.datatypes.json.DateTimeConverters;
import de.metas.ui.web.window.descriptor.CreateFiltersProviderContext;
import de.metas.ui.web.window.descriptor.DocumentDescriptor;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutSingleRow;
import de.metas.ui.web.window.descriptor.factory.DocumentDescriptorFactory;
import de.metas.ui.web.window.exceptions.DocumentLayoutBuildException;
import de.metas.user.UserId;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.assertj.core.api.Assertions;
import org.compiere.SpringContextHolder;
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

import javax.annotation.Nullable;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

class CommentsServiceTest
{
	public static final int AD_USER_ID = 10;
	public static final String THE_USER_NAME = "The User Name";
	public static final ZonedDateTime ZONED_DATE_TIME = ZonedDateTime.of(2020, Month.APRIL.getValue(), 23, 1, 1, 1, 0, ZoneId.of("UTC+8"));

	public static final String DUMMY_TABLE_NAME = "DummyTable";
	private static final WindowId WINDOW_ID = WindowId.of(123);

	private CommentsRepository commentsRepository;
	private CommentsService commentsService;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		SystemTime.setTimeSource(() -> ZONED_DATE_TIME.toInstant().toEpochMilli());

		// all created POs will have this user
		Env.setLoggedUserId(Env.getCtx(), UserId.ofRepoId(AD_USER_ID));
		createDefaultUser();

		final DocumentDescriptorFactory documentDescriptorFactory;
		{
			final DocumentFilterDescriptorsProviderFactory documentFilterDescriptorsProviderFactory = new DocumentFilterDescriptorsProviderFactory()
			{
				@Nullable
				@Override
				public DocumentFilterDescriptorsProvider createFiltersProvider(@NonNull final CreateFiltersProviderContext context, final @NonNull Collection<DocumentFieldDescriptor> fields)
				{
					return null;
				}
			};

			SpringContextHolder.registerJUnitBean(new DocumentFilterDescriptorsProvidersService(ImmutableList.of(documentFilterDescriptorsProviderFactory)));

			documentDescriptorFactory = new DocumentDescriptorFactory()
			{
				final DocumentDescriptor documentDescriptor = DocumentDescriptor.builder()
						.setLayout(DocumentLayoutDescriptor.builder()
								.setWindowId(WINDOW_ID)
								.setSingleRowLayout(DocumentLayoutSingleRow.builder())
								.setGridView(ViewLayout.builder())
								.setSideListView(ViewLayout.builder().build())
								.build())
						.setEntityDescriptor(DocumentEntityDescriptor.builder()
								.setDocumentType(WINDOW_ID.toAdWindowId())
								.setTableName(DUMMY_TABLE_NAME)
								.build())
						.build();

				@Override
				public boolean isWindowIdSupported(@Nullable final WindowId windowId)
				{
					return false;
				}

				@Override
				public DocumentDescriptor getDocumentDescriptor(final WindowId windowId) throws DocumentLayoutBuildException
				{
					return documentDescriptor;
				}

				@Override
				public void invalidateForWindow(final WindowId windowId)
				{

				}
			};
		}

		commentsRepository = new CommentsRepository();
		commentsService = new CommentsService(commentsRepository, documentDescriptorFactory);
	}

	@Nested
	class TestCreateComments
	{
		@Test
		void create2Comments()
		{
			// create test data
			final TableRecordReference tableRecordReference = TableRecordReference.of(DUMMY_TABLE_NAME, 1);

			apiAddComment(tableRecordReference, "comment1");
			apiAddComment(tableRecordReference, "comment2");

			// check the comments exist
			final List<CommentEntry> actual = commentsRepository.retrieveLastCommentEntries(tableRecordReference, 2);

			final List<CommentEntry> expected = Arrays.asList(
					createCommentEntry("comment1"),
					createCommentEntry("comment2"));

			Assertions.assertThat(actual)
					.usingElementComparatorIgnoringFields("id")
					.isEqualTo(expected);
		}

		private void apiAddComment(final TableRecordReference tableRecordReference, final String comment)
		{
			final DocumentPath documentPath = DocumentPath.rootDocumentPath(WINDOW_ID, tableRecordReference.getRecord_ID());
			commentsService.addComment(documentPath, new JSONCommentCreateRequest(comment));
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
			final TableRecordReference tableRecordReference = TableRecordReference.of(DUMMY_TABLE_NAME, 1);
			final CommentEntryParentId commentEntryParentId = createChatRecord(tableRecordReference);
			createChatEntryRecord(commentEntryParentId, "comment1");
			createChatEntryRecord(commentEntryParentId, "comment2");

			//
			final DocumentPath documentPath = DocumentPath.rootDocumentPath(WINDOW_ID, tableRecordReference.getRecord_ID());
			final List<JSONComment> actual = commentsService.getRowCommentsAsJson(documentPath, ZoneId.of("UTC+8"));

			final String zonedDateTimeString = DateTimeConverters.toJson(ZONED_DATE_TIME, ZoneId.of("UTC+8"));

			final List<JSONComment> expected = Arrays.asList(
					createJsonComment(zonedDateTimeString, "comment1"),
					createJsonComment(zonedDateTimeString, "comment2"));

			Assertions.assertThat(actual).isEqualTo(expected);
		}

		@Test
		void noCommentsExist()
		{
			// create test data
			final TableRecordReference tableRecordReference = TableRecordReference.of(DUMMY_TABLE_NAME, 1);

			//
			final DocumentPath documentPath = DocumentPath.rootDocumentPath(WINDOW_ID, tableRecordReference.getRecord_ID());
			final List<JSONComment> actual = commentsService.getRowCommentsAsJson(documentPath, ZoneId.of("UTC+8"));

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

	@Nested
	class Test_CommentRepository_HasComments
	{
		@Test
		void hasSomeComments()
		{
			createChatRecord(TableRecordReference.of(DUMMY_TABLE_NAME, 111));
			createChatRecord(TableRecordReference.of(DUMMY_TABLE_NAME, 112));
			createChatRecord(TableRecordReference.of(DUMMY_TABLE_NAME, 113));

			final TableRecordReference hasComments1 = TableRecordReference.of(DUMMY_TABLE_NAME, 11);
			createChatRecord(hasComments1);

			final TableRecordReference hasComments2 = TableRecordReference.of(DUMMY_TABLE_NAME, 12);
			createChatRecord(hasComments2);

			final ImmutableMap<TableRecordReference, Boolean> expected = ImmutableMap.of(
					TableRecordReference.of(DUMMY_TABLE_NAME, 21), false,
					TableRecordReference.of(DUMMY_TABLE_NAME, 22), false,
					hasComments1, true,
					hasComments2, true);

			final Map<TableRecordReference, Boolean> actual = commentsRepository.hasComments(expected.keySet());

			Assertions.assertThat(actual)
					.isEqualTo(expected)
					.hasSize(4);
		}

		@Test
		void emptyInputList()
		{
			final Map<TableRecordReference, Boolean> actual = commentsRepository.hasComments(ImmutableList.of());
			Assertions.assertThat(actual).isEmpty();
		}
	}

	@Nested
	class Test_CommentService_HasComments
	{
		@Test
		void usingDocumentPath_noComments()
		{
			final IViewRow row = createViewRow(10);
			final DocumentPath documentPath = row.getDocumentPath();

			final Boolean actual = commentsService.hasComments(documentPath);

			Assertions.assertThat(actual).isFalse();
		}

		@Test
		void usingDocumentPath_hasComments()
		{
			// create test data
			final TableRecordReference hasComments1 = TableRecordReference.of(DUMMY_TABLE_NAME, 10);
			createChatRecord(hasComments1);

			final IViewRow row = createViewRow(10);
			final DocumentPath documentPath = row.getDocumentPath();

			final Boolean actual = commentsService.hasComments(documentPath);

			Assertions.assertThat(actual).isTrue();
		}

		@Test
		void usingRowList_someComments()
		{
			// create test data
			createChatRecord(TableRecordReference.of(DUMMY_TABLE_NAME, 111));
			createChatRecord(TableRecordReference.of(DUMMY_TABLE_NAME, 112));
			createChatRecord(TableRecordReference.of(DUMMY_TABLE_NAME, 113));

			createChatRecord(TableRecordReference.of(DUMMY_TABLE_NAME, 11));
			final IViewRow row11Yes = createViewRow(11);

			createChatRecord(TableRecordReference.of(DUMMY_TABLE_NAME, 12));
			final IViewRow row12Yes = createViewRow(12);

			final IViewRow row21No = createViewRow(21);
			final IViewRow row22No = createViewRow(22);
			final IViewRow row23No = createViewRow(23);

			final ViewRowCommentsSummary expected = ViewRowCommentsSummary.ofMap(
					ImmutableMap.of(
							row11Yes.getId(), true,
							row12Yes.getId(), true,
							row21No.getId(), false,
							row22No.getId(), false,
							row23No.getId(), false));

			final ViewRowCommentsSummary actual = commentsService.getRowCommentsSummary(Arrays.asList(row11Yes, row12Yes, row21No, row22No, row23No));

			Assertions.assertThat(actual)
					.isEqualTo(expected);
		}

		@NonNull
		private IViewRow createViewRow(final int rowId)
		{
			return ViewRow.builder(WINDOW_ID)
					.setRowId(DocumentId.of(rowId))
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

	private CommentEntryParentId createChatRecord(final TableRecordReference recordRef)
	{
		final I_CM_Chat chat = InterfaceWrapperHelper.newInstance(I_CM_Chat.class);
		chat.setDescription("Table name: " + I_C_BPartner.Table_Name);
		chat.setAD_Table_ID(recordRef.getAD_Table_ID());
		chat.setRecord_ID(recordRef.getRecord_ID());
		InterfaceWrapperHelper.save(chat);
		return CommentEntryParentId.ofRepoId(chat.getCM_Chat_ID());
	}

	private void createChatEntryRecord(final CommentEntryParentId commentEntryParentId, final String characterData)
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
