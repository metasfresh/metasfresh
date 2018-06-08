package de.metas.email;

import java.net.URI;
import java.util.Random;

import org.compiere.Adempiere;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * Tests if {@link EMail} related objects are JSON serializable.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class EMailJsonTest
{
	private ObjectMapper jsonObjectMapper;
	private Random random;

	@Before
	public void init()
	{
		Adempiere.enableUnitTestMode(); // needed to display Mailbox passwords

		jsonObjectMapper = new ObjectMapper();
		jsonObjectMapper.enable(SerializationFeature.INDENT_OUTPUT); // pretty

		random = new Random(System.currentTimeMillis());
	}

	@Test
	public void test_EMailSentStatus() throws Exception
	{
		testSerializeDeserialize(EMailSentStatus.NOT_SENT);
		testSerializeDeserialize(EMailSentStatus.ok("1234"));
		testSerializeDeserialize(EMailSentStatus.error("some error"));
		testSerializeDeserialize(EMailSentStatus.invalid("some invalid error"));
	}

	@Test
	public void test_EMailAttachment() throws Exception
	{
		testJsonEquals(EMailAttachment.of("dummy.pdf", generateBytes(10)));
		testJsonEquals(EMailAttachment.of(new URI("http://dummy.org/file.pdf")));
	}

	@Test
	public void test_Mailbox() throws Exception
	{
		final Mailbox mailbox = Mailbox.builder()
				.setSmtpHost("smtpHost")
				.setEmail("from@email.com")
				.setSmtpAuthorization(true)
				.setUsername("username")
				.setPassword("password111")
				.setSendFromServer(true)
				.setAD_Client_ID(1234)
				.setAD_User_ID(5678)
				.setColumnUserTo("columnUserTo")
				.build();
		testJsonToStringEquals(mailbox);
	}

	@Test
	public void test_EMail() throws Exception
	{
		final Mailbox mailbox = Mailbox.builder()
				.setSmtpHost("smtpHost")
				.setEmail("from@email.com")
				.setSmtpAuthorization(true)
				.setUsername("username")
				.setPassword("password111")
				.setSendFromServer(true)
				.setAD_Client_ID(1234)
				.setAD_User_ID(5678)
				.setColumnUserTo("columnUserTo")
				.build();
		final String to = "to@email.com";
		final String subject = "test email subject";
		final String message = "dummy text message";
		final boolean html = false;
		final EMail email = new EMail(mailbox, to, subject, message, html);
		email.addTo("to2@email.com");
		email.addCc("cc1@email.com");
		email.addCc("cc2@email.com");
		email.addBcc("bcc1@email.com");
		email.addBcc("bcc2@email.com");
		email.setReplyTo("reply-to@email.com");
		email.addAttachment("dummy.pdf", generateBytes(20));
		email.addAttachment(new URI("http://dummy.com/report.pdf"));

		testJsonToStringEquals(email);
	}

	private final byte[] generateBytes(final int size)
	{
		final byte[] bytes = new byte[size];
		random.nextBytes(bytes);
		return bytes;
	}

	private <T> void testJsonToStringEquals(final T value) throws Exception
	{
		final T valueDeserialized = testSerializeDeserialize(value);

		final String valueStr = value == null ? null : value.toString();
		final String valueDeserializedStr = valueDeserialized == null ? null : valueDeserialized.toString();
		Assert.assertEquals(valueStr, valueDeserializedStr);
	}

	private <T> void testJsonEquals(final T value) throws Exception
	{
		final T valueDeserialized = testSerializeDeserialize(value);
		Assert.assertEquals(value, valueDeserialized);
	}

	private <T> T testSerializeDeserialize(final T value) throws Exception
	{
		@SuppressWarnings("unchecked")
		final Class<T> type = (Class<T>)value.getClass();

		final String jsonStr = jsonObjectMapper.writeValueAsString(value);
		System.out.println("\n\n-------------------------------------------------------------------------------------------");
		System.out.println("Value: " + value);
		System.out.println("Type: " + type);
		System.out.println("JSON: " + jsonStr);

		final T valueDeserialized = jsonObjectMapper.readValue(jsonStr, type);
		System.out.println("Value deserialized: " + valueDeserialized);
		System.out.println("-------------------------------------------------------------------------------------------");
		return valueDeserialized;
	}
}
