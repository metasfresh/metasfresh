/*
 * #%L
 * de-metas-camel-shipping
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.camel.shipping.tutorial;

import org.apache.camel.Exchange;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.apache.camel.test.junit5.TestSupport.deleteDirectory;
import static org.assertj.core.api.Assertions.assertThat;

class SampleRouteBuilderTest extends CamelTestSupport
{

	@Override
	protected RouteBuilder createRouteBuilder()
	{
		return new RouteBuilder()
		{
			public void configure()
			{
				from("file://target/inbox").to("file://target/outbox");
			}
		};
	}

	@BeforeEach
	public void beforeEach()
	{
		deleteDirectory("target/inbox");
		deleteDirectory("target/outbox");
	}

	@Test
	void testMoveFile()
	{
		final NotifyBuilder notify = new NotifyBuilder(context).whenDone(1).create(); // instead of waiting, go on whenever component one is ready
		template.sendBodyAndHeader("file://target/inbox", "Hello World",
				Exchange.FILE_NAME, "hello.txt");

		assertThat(notify.matchesWaitTime()).isTrue();

		final var target = new File("target/outbox/hello.txt");
		assertThat(target.exists()).as("File not moved").isTrue();
	}
}
