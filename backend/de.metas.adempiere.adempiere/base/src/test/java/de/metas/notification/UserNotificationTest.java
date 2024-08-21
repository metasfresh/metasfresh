package de.metas.notification;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import de.metas.event.Event;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.Instant;

import static org.assertj.core.api.Assertions.*;

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

public class UserNotificationTest
{
	private ObjectMapper jsonMapper;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		jsonMapper = JsonObjectMapperHolder.newJsonObjectMapper();
	}

	@Test
	public void testSerializeDeserialize_TargetType_Window() throws IOException
	{
		testSerializeDeserialize(UserNotification.builder()
				.id(123)
				.timestamp(Instant.now())
				.important(true)
				.read(true)
				.recipientUserId(111)
				.detailPlain("detailPlain")
				.detailADMessage("detailADMessage")
				.detailADMessageParam("value1")
				.detailADMessageParam("value2")
				.targetType(UserNotificationTargetType.Window)
				.targetRecord(TableRecordReference.of("MyTable", 444))
				.targetWindowId(AdWindowId.ofRepoId(555))
				.build());
	}

	@Test
	public void testSerializeDeserialize_TargetType_View() throws IOException
	{
		testSerializeDeserialize(UserNotification.builder()
				.id(123)
				.timestamp(Instant.now())
				.important(true)
				.read(true)
				.recipientUserId(111)
				.detailPlain("detailPlain")
				.detailADMessage("detailADMessage")
				.detailADMessageParam("value1")
				.detailADMessageParam("value2")
				.targetType(UserNotificationTargetType.View)
				.targetWindowId(AdWindowId.ofRepoId(555))
				.targetViewId("555-123")
				.build());
	}

	private void testSerializeDeserialize(final UserNotification notification) throws IOException
	{
		testSerializeDeserializeObject(notification);

		final Event event = UserNotificationUtils.toEvent(notification);
		testSerializeDeserializeObject(event);
	}

	private void testSerializeDeserializeObject(final Object value) throws IOException
	{
		final Class<?> valueClass = value.getClass();
		final String json = jsonMapper.writeValueAsString(value);
		final Object value2 = jsonMapper.readValue(json, valueClass);
		assertThat(value2).isEqualTo(value);
	}

}
