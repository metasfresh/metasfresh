/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

package de.metas.attachments.listener;

import com.google.common.collect.ImmutableList;
import de.metas.CreatedUpdatedInfo;
import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryType;
import de.metas.javaclasses.model.I_AD_JavaClass;
import de.metas.javaclasses.model.I_AD_JavaClass_Type;
import de.metas.user.UserId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_AD_Table_AttachmentListener;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.ZonedDateTime;

import static de.metas.attachments.listener.AttachmentListenerConstants.ListenerWorkStatus.SUCCESS;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

public class AttachmentListenerServiceTest
{
	private TableAttachmentListenerService tableAttachmentListenerService;

	private static final int DUMB_LISTENER_MOCK_JAVA_CLASS_ID = 1;
	private static final int ATTACHMENT_LISTENER_MOCK_JAVA_CLASS_TYPE_ID = 2;
	private static final int MOCK_AD_TABLE_ID = 3;
	private static final int MOCK_RECORD_ID = 4;
	private static final int SEQ_NO_TEN = 10;
	private static final String MOCK_TABLE_NAME = "MockTable";

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		final TableAttachmentListenerRepository tableAttachmentListenerRepository = new TableAttachmentListenerRepository();

		tableAttachmentListenerService = Mockito.spy(new TableAttachmentListenerService(tableAttachmentListenerRepository));

		prepareJavaClassTypeMockRecord();
		prepareJavaClassMockRecord();
		prepareAdTableMockRecord();
		prepareAttachmentListenerMockRecord();
	}

	@Test
	public void givenAttachmentEntry_whenNotifyAttachmentListeners_returnSuccess()
	{
		final TableRecordReference tableRecordReferenceMock = TableRecordReference.of(MOCK_AD_TABLE_ID, MOCK_RECORD_ID);
		final AttachmentEntry attachmentEntryMock = AttachmentEntry.builder()
				.type(AttachmentEntryType.Data)
				.linkedRecord(tableRecordReferenceMock)
				.createdUpdatedInfo(CreatedUpdatedInfo.createNew(UserId.ofRepoId(10), ZonedDateTime.now()))
				.build();

		Mockito.doNothing().when(tableAttachmentListenerService).notifyUser(any(), any(), any());

		final ImmutableList<AttachmentListenerActionResult> result = tableAttachmentListenerService.fireAfterRecordLinked(attachmentEntryMock, tableRecordReferenceMock);

		assertEquals(result.size(), 1);
		assertEquals(result.get(0).getStatus(), SUCCESS);
		assertEquals(result.get(0).getListener().getClass(), DumbAttachmentListener.class);
	}

	private void prepareJavaClassTypeMockRecord()
	{
		final I_AD_JavaClass_Type javaClassType = InterfaceWrapperHelper.newInstance(I_AD_JavaClass_Type.class);
		javaClassType.setAD_JavaClass_Type_ID(ATTACHMENT_LISTENER_MOCK_JAVA_CLASS_TYPE_ID);
		javaClassType.setName(AttachmentListener.class.getSimpleName());
		javaClassType.setClassname(AttachmentListener.class.getName());
		javaClassType.setIsActive(true);

		InterfaceWrapperHelper.save(javaClassType);
	}

	private void prepareJavaClassMockRecord()
	{
		final I_AD_JavaClass javaClassRecord = InterfaceWrapperHelper.newInstance(I_AD_JavaClass.class);

		javaClassRecord.setAD_JavaClass_ID(DUMB_LISTENER_MOCK_JAVA_CLASS_ID);
		javaClassRecord.setAD_JavaClass_Type_ID(ATTACHMENT_LISTENER_MOCK_JAVA_CLASS_TYPE_ID);
		javaClassRecord.setClassname(DumbAttachmentListener.class.getName());
		javaClassRecord.setName(DumbAttachmentListener.class.getSimpleName());
		javaClassRecord.setIsActive(true);

		InterfaceWrapperHelper.save(javaClassRecord);
	}

	private void prepareAdTableMockRecord()
	{
		final I_AD_Table adTable = InterfaceWrapperHelper.newInstance(I_AD_Table.class);

		adTable.setTableName(MOCK_TABLE_NAME);
		adTable.setName(MOCK_TABLE_NAME);
		adTable.setAD_Table_ID(MOCK_AD_TABLE_ID);

		InterfaceWrapperHelper.save(adTable);
	}

	private void prepareAttachmentListenerMockRecord()
	{
		final I_AD_Table_AttachmentListener mockRecord = InterfaceWrapperHelper.newInstance(I_AD_Table_AttachmentListener.class);

		mockRecord.setAD_JavaClass_ID(DUMB_LISTENER_MOCK_JAVA_CLASS_ID);
		mockRecord.setSeqNo(SEQ_NO_TEN);
		mockRecord.setIsActive(true);
		mockRecord.setIsSendNotification(false);
		mockRecord.setAD_Table_ID(MOCK_AD_TABLE_ID);

		InterfaceWrapperHelper.save(mockRecord);
	}
}
