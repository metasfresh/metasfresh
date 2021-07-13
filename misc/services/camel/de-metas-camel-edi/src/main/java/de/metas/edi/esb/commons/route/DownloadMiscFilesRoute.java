/*
 * #%L
 * de-metas-edi-esb-camel
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

package de.metas.edi.esb.commons.route;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import de.metas.edi.esb.commons.Util;

@Component
public class DownloadMiscFilesRoute extends RouteBuilder
{
	private static final String ROUTE_ID = "Remote-misc-files-To-Local";

	private static final String INPUT_FILE_REMOTE = "{{edi.file.misc.remote}}";

	private static final String OUTPUT_FILE_LOCAL = "{{edi.file.misc.local}}";

	private static final transient Logger logger = LoggerFactory.getLogger(ROUTE_ID);

	@Override
	public final void configure()
	{
		final String remoteEndpoint = Util.resolveProperty(getContext(), INPUT_FILE_REMOTE, "");
		if (Util.isEmpty(remoteEndpoint))
		{
			logger.info("remoteEndpoint " + INPUT_FILE_REMOTE + " is empty; -> not configuring " + DownloadMiscFilesRoute.class.getSimpleName());
			return;
		}
		from(INPUT_FILE_REMOTE)
				.routeId(ROUTE_ID)
				.log(LoggingLevel.DEBUG, "Getting remote file: " + header(Exchange.FILE_NAME))
				.to(OUTPUT_FILE_LOCAL);
	}
}
