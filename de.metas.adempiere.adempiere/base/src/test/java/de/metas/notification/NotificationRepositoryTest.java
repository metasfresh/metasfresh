package de.metas.notification;

import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.collections.ListUtils;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Message;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.metas.event.Topic;
import de.metas.event.Type;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class NotificationRepositoryTest
{
	private NotificationRepository notificationRepo;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		final ObjectMapper jsonMapper = new ObjectMapper();
		jsonMapper.findAndRegisterModules();

		notificationRepo = new NotificationRepository(jsonMapper);
	}

	@Test
	public void test()
	{
		createAD_Message("subjectADMessage");
		createAD_Message("contentADMessage");

		final UserNotification notificationSaved = notificationRepo.save(UserNotificationRequest.builder()
				.topic(Topic.builder().name("topic1").type(Type.REMOTE).build())
				.recipientUserId(123)
				.important(true)
				//
				.subjectPlain("subjectPlain")
				.subjectADMessage("subjectADMessage")
				.subjectADMessageParam("string")
				.subjectADMessageParam(TableRecordReference.of("MyTable", 111))
				.subjectADMessageParam(BigDecimal.valueOf(123.44))
				//
				.contentPlain("contentPlain")
				.contentADMessage("contentADMessage")
				.contentADMessageParam("string")
				.contentADMessageParam(TableRecordReference.of("MyTable", 111))
				.contentADMessageParam(BigDecimal.valueOf(123.44))
				//
				.targetRecord(TableRecordReference.of("MyTable", 111))
				.targetRecordDisplayText("targetRecordDisplayText")
				.targetADWindowId(444)
				.build());

		final List<UserNotification> userNotifications = notificationRepo.getByUser(123, Integer.MAX_VALUE);
		final UserNotification userNotification = ListUtils.singleElement(userNotifications);
		assertThat(userNotification).isEqualTo(notificationSaved);
	}

	private void createAD_Message(final String adMessage)
	{
		final I_AD_Message record = newInstanceOutOfTrx(I_AD_Message.class);
		record.setValue(adMessage);
		record.setMsgText(adMessage + "_MsgText");
		save(record);
	}
}
