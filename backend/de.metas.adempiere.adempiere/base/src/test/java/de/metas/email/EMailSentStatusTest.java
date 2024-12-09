package de.metas.email;

<<<<<<< HEAD
import org.junit.Assert;
import org.junit.Test;
=======
import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.Test;

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
<<<<<<< HEAD
 * 
=======
 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
<<<<<<< HEAD
 * 
=======
 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class EMailSentStatusTest
{
	@Test
	public void test_ok()
	{
		final EMailSentStatus status = EMailSentStatus.ok("1234");
<<<<<<< HEAD
		Assert.assertEquals(true, status.isSentOK());
		Assert.assertEquals(false, status.isSentConnectionError());
		Assert.assertEquals(EMailSentStatus.SENT_OK, status.getSentMsg());
		Assert.assertEquals("1234", status.getMessageId());
	}

	@Test
	public void test_invalid()
	{
		final EMailSentStatus status = EMailSentStatus.invalid("invalidError1");
		Assert.assertEquals(false, status.isSentOK());
		Assert.assertEquals(false, status.isSentConnectionError());
		Assert.assertEquals("invalidError1", status.getSentMsg());
		Assert.assertEquals(null, status.getMessageId());
	}

	@Test
	public void test_errorWithMessage()
	{
		final EMailSentStatus status = EMailSentStatus.error("errorMsg1");
		Assert.assertEquals(false, status.isSentOK());
		Assert.assertEquals(false, status.isSentConnectionError());
		Assert.assertEquals("errorMsg1", status.getSentMsg());
		Assert.assertEquals(null, status.getMessageId());
	}

	@Test
	public void test_errorException_ConnectionError()
=======
		assertThat(status.isSentOK()).isTrue();
		assertThat(status.isSentConnectionError()).isFalse();
		assertThat(status.getMessageId()).isEqualTo("1234");
	}

	@Test
	public void test_invalidWithMessage()
	{
		final EMailSentStatus status = EMailSentStatus.invalid("invalidError1");
		assertThat(status.isSentOK()).isFalse();
		assertThat(status.isSentConnectionError()).isFalse();
		assertThat(status.getSentMsg()).isEqualTo("invalidError1");
		assertThat(status.getMessageId()).isNull();
	}
	@Test
	public void test_invalidWithException()
	{
		final EMailSentStatus status = EMailSentStatus.invalid(new AdempiereException("invalidError1"));
		assertThat(status.isSentOK()).isFalse();
		assertThat(status.isSentConnectionError()).isFalse();
		assertThat(status.getSentMsg()).isEqualTo("invalidError1");
		assertThat(status.getMessageId()).isNull();
	}

	@Test
	public void test_errorWithException()
	{
		final EMailSentStatus status = EMailSentStatus.error(new AdempiereException("errorMsg1"));
		assertThat(status.isSentOK()).isFalse();
		assertThat(status.isSentConnectionError()).isFalse();
		assertThat(status.getSentMsg()).isEqualTo("errorMsg1");
		assertThat(status.getMessageId()).isNull();
	}

	@Test
	public void test_errorWithConnectionException()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		final java.net.ConnectException connectionEx = new java.net.ConnectException("test connection error");
		final javax.mail.MessagingException exception = new javax.mail.MessagingException("test messaging exception", connectionEx);
		final String expectedSentMsg = "(ME): test messaging exception - Connection error: test connection error";
<<<<<<< HEAD
		
		final EMailSentStatus status = EMailSentStatus.error(exception);
		Assert.assertEquals(false, status.isSentOK());
		Assert.assertEquals(true, status.isSentConnectionError());
		Assert.assertEquals(expectedSentMsg, status.getSentMsg());
		Assert.assertEquals(null, status.getMessageId());
=======

		final EMailSentStatus status = EMailSentStatus.error(exception);
		assertThat(status.isSentOK()).isFalse();
		assertThat(status.isSentConnectionError()).isTrue();
		assertThat(status.getSentMsg()).isEqualTo(expectedSentMsg);
		assertThat(status.getMessageId()).isNull();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

}
