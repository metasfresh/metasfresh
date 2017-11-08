package de.metas.event;

import org.junit.Assert;
import org.junit.Test;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class EventTest
{
	@Test
	public void test_EventNotEquals()
	{
		final Event event1 = Event.builder()
				.setSummary("Summary1")
				.setDetailPlain("Detail1")
				.setId("MyID1")
				.addRecipient_User_ID(10)
				.addRecipient_User_ID(20)
				.addRecipient_User_ID(40)
				.addRecipient_User_ID(30)
				.addRecipient_User_ID(15)
				.build();
		final Event event2 = Event.builder()
				.setSummary("Summary1")
				.setDetailPlain("Detail1")
				.setId("MyID1")
				.addRecipient_User_ID(10)
				.addRecipient_User_ID(20)
				.addRecipient_User_ID(40)
				.addRecipient_User_ID(30)
				.addRecipient_User_ID(16)
				.build();

		Assert.assertNotEquals(event1, event2);
	}
}
