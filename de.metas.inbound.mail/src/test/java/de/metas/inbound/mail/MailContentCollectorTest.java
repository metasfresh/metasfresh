package de.metas.inbound.mail;

import org.junit.Assert;
import org.junit.Test;

/*
 * #%L
 * de.metas.inbound.mail
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

public class MailContentCollectorTest
{
	@Test
	public void test_collectFromMailContent()
	{
		final MailContent expected = MailContent.builder()
				.text("test text")
				.html("<div>some html</div>")
				.build();

		final MailContent actual = MailContentCollector.newInstance()
				.collectObject(expected)
				.toMailContent();

		Assert.assertEquals(expected, actual);
	}
}
