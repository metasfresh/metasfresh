package de.metas.notification;

import com.google.common.collect.ImmutableList;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.util.Services;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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

public class NotificationMessageFormatterTest
{
	private MockedMsgBL mockedMsgBL;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		mockedMsgBL = new MockedMsgBL();
		Services.registerService(IMsgBL.class, mockedMsgBL);
	}

	@After
	public void after()
	{
		mockedMsgBL = null;
		Services.clear();
	}

	@Test
	public void test_formatURLs()
	{
		final NotificationMessageFormatter formatter = NotificationMessageFormatter.newInstance()
				.html(true);

		final AdMessageKey adMessage = AdMessageKey.of("TestMessage");
		mockedMsgBL.putMsgText(adMessage, "the url is {0}.");
		final String result = formatter.format(
				adMessage,
				ImmutableList.<Object> of("http://www.metasfresh.com"));
		assertThat(result).isEqualTo("the url is <a href=\"http://www.metasfresh.com\">http://www.metasfresh.com</a>.");
	}

	@Test
	public void test_formatURLsWithCaption()
	{
		final NotificationMessageFormatter formatter = NotificationMessageFormatter.newInstance()
				.html(true);

		final AdMessageKey adMessage = AdMessageKey.of("TestMessage");
		mockedMsgBL.putMsgText(adMessage, "the url is {0}.");
		final String result = formatter.format(
				adMessage,
				ImmutableList.<Object> of(
						NotificationMessageFormatter.createUrlWithTitle("http://www.metasfresh.com", "metas gmbH") //
				));
		assertThat(result).isEqualTo("the url is <a href=\"http://www.metasfresh.com\">metas gmbH</a>.");
	}
}
