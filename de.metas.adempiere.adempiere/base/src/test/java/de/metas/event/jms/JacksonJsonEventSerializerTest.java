package de.metas.event.jms;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Invoice;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import de.metas.event.Event;
import de.metas.event.EventBusConstants;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class JacksonJsonEventSerializerTest
{
	private JacksonJsonEventSerializer jsonSerializer = JacksonJsonEventSerializer.instance;

	@Rule
	public TestName testName = new TestName();

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init(); // needed for ITableRecordReference
	}

	@Test
	public void test1()
	{
		testSerializeUnserialize(Event.builder()
				.setSummary("Summary1")
				.setDetailPlain("Detail1")
				.setDetailADMessage("Detail_AD_Message", "P1", "P2", "P3")
				.setUUID(UUID.randomUUID())
				.setWhen(Instant.now())
				.addRecipient_User_ID(10)
				.addRecipient_User_ID(20)
				.addRecipient_User_ID(40)
				.addRecipient_User_ID(30)
				.addRecipient_User_ID(15)
				.putProperty("Prop_BD", new BigDecimal("3.1415"))
				.putProperty("Prop_Bool_True", true)
				.putProperty("Prop_Bool_False", false)
				.putProperty("Prop_Date", new Date())
				.putProperty("Prop_TS", new Timestamp(System.currentTimeMillis()))
				.putProperty("Prop_Int", 13)
				.putProperty("Prop_Str", "string1")
				.putProperty("Prop_Ref", TableRecordReference.of(I_C_Invoice.Table_Name, 123456))
				.build());
	}

	private final void testSerializeUnserialize(final Event event)
	{
		System.out.println("================ TEST: " + testName.getMethodName() + " ===============================================");
		System.out.println("event=" + event);

		final String jsonEvent = jsonSerializer.toString(event);
		System.out.println("json=" + jsonEvent);

		final Event eventRestored = jsonSerializer.fromString(jsonEvent);
		System.out.println("eventRestored=" + eventRestored);

		final String jsonEventRestored = jsonSerializer.toString(eventRestored);
		System.out.println("json event restored=" + jsonEventRestored);

		Assert.assertEquals(event, eventRestored);
		Assert.assertEquals("Invalid SenderId", EventBusConstants.getSenderId(), eventRestored.getSenderId());
	}

}
