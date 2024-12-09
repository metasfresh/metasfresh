package de.metas.email;

<<<<<<< HEAD
import java.net.URI;
import java.util.Random;

import org.compiere.Adempiere;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import de.metas.JsonObjectMapperHolder;
import de.metas.email.mailboxes.Mailbox;
=======
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.metas.JsonObjectMapperHolder;
import de.metas.email.mailboxes.Mailbox;
import de.metas.email.mailboxes.MailboxType;
import de.metas.email.mailboxes.SMTPConfig;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.Adempiere;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

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
<<<<<<< HEAD
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
=======
 *
 * @author metas-dev <dev@metasfresh.com>
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 */
public class EMailJsonTest
{
	private ObjectMapper jsonObjectMapper;
	private Random random;

<<<<<<< HEAD
	@Before
=======
	@BeforeEach
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	public void init()
	{
		Adempiere.enableUnitTestMode(); // needed to display Mailbox passwords

		jsonObjectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
		jsonObjectMapper.enable(SerializationFeature.INDENT_OUTPUT); // pretty

		random = new Random(System.currentTimeMillis());
	}

	@Test
	public void test_EMailSentStatus() throws Exception
	{
		testSerializeDeserialize(EMailSentStatus.NOT_SENT);
		testSerializeDeserialize(EMailSentStatus.ok("1234"));
<<<<<<< HEAD
		testSerializeDeserialize(EMailSentStatus.error("some error"));
		testSerializeDeserialize(EMailSentStatus.invalid("some invalid error"));
=======
		testSerializeDeserialize(EMailSentStatus.error(new AdempiereException("some error")));
		testSerializeDeserialize(EMailSentStatus.invalid("some invalid error"));
		testSerializeDeserialize(EMailSentStatus.invalid(new AdempiereException("some invalid error")));
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
		final Mailbox mailbox = Mailbox.builder()
				.smtpHost("smtpHost")
				.email(EMailAddress.ofString("from@email.com"))
				.smtpAuthorization(true)
				.username("username")
				.password("password111")
				.sendEmailsFromServer(true)
				.userToColumnName("userToColumnName")
				.build();
		testJsonToStringEquals(mailbox);
=======
		testJsonToStringEquals(
				Mailbox.builder()
						.email(EMailAddress.ofString("from@email.com"))
						.userToColumnName("userToColumnName")
						.type(MailboxType.SMTP)
						.smtpConfig(SMTPConfig.builder()
								.smtpHost("smtpHost")
								.smtpAuthorization(true)
								.username("username")
								.password("password111")
								.build())
						.build()
		);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@Test
	public void test_EMail() throws Exception
	{
		final Mailbox mailbox = Mailbox.builder()
<<<<<<< HEAD
				.smtpHost("smtpHost")
				.email(EMailAddress.ofString("from@email.com"))
				.smtpAuthorization(true)
				.username("username")
				.password("password111")
				.sendEmailsFromServer(true)
				.userToColumnName("userToColumnName")
=======
				.email(EMailAddress.ofString("from@email.com"))
				.userToColumnName("userToColumnName")
				.type(MailboxType.SMTP)
				.smtpConfig(SMTPConfig.builder()
						.smtpHost("smtpHost")
						.smtpAuthorization(true)
						.username("username")
						.password("password111")
						.build())
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
				.build();
		final EMailAddress to = EMailAddress.ofString("to@email.com");
		final String subject = "test email subject";
		final String message = "dummy text message";
		final boolean html = false;
		final EMail email = new EMail(mailbox, to, subject, message, html);
		email.addTo(EMailAddress.ofString("to2@email.com"));
		email.addCc(EMailAddress.ofString("cc1@email.com"));
		email.addCc(EMailAddress.ofString("cc2@email.com"));
		email.addBcc(EMailAddress.ofString("bcc1@email.com"));
		email.addBcc(EMailAddress.ofString("bcc2@email.com"));
		email.setReplyTo(EMailAddress.ofString("reply-to@email.com"));
		email.addAttachment("dummy.pdf", generateBytes(20));
		email.addAttachment(new URI("http://dummy.com/report.pdf"));

		testJsonToStringEquals(email);
	}

<<<<<<< HEAD
	private final byte[] generateBytes(final int size)
=======
	private byte[] generateBytes(final int size)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		final byte[] bytes = new byte[size];
		random.nextBytes(bytes);
		return bytes;
	}

	private <T> void testJsonToStringEquals(final T value) throws Exception
	{
		final T valueDeserialized = testSerializeDeserialize(value);

<<<<<<< HEAD
		final String valueStr = value == null ? null : value.toString();
		final String valueDeserializedStr = valueDeserialized == null ? null : valueDeserialized.toString();
		Assert.assertEquals(valueStr, valueDeserializedStr);
=======
		final String valueStr = value.toString();
		final String valueDeserializedStr = valueDeserialized == null ? null : valueDeserialized.toString();
		assertThat(valueDeserializedStr).isEqualTo(valueStr);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	private <T> void testJsonEquals(final T value) throws Exception
	{
		final T valueDeserialized = testSerializeDeserialize(value);
<<<<<<< HEAD
		Assert.assertEquals(value, valueDeserialized);
=======
		assertThat(valueDeserialized).isEqualTo(value);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	private <T> T testSerializeDeserialize(final T value) throws Exception
	{
<<<<<<< HEAD
		@SuppressWarnings("unchecked")
		final Class<T> type = (Class<T>)value.getClass();
=======
		@SuppressWarnings("unchecked") final Class<T> type = (Class<T>)value.getClass();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

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
