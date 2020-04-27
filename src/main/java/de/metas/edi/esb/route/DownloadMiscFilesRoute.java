package de.metas.edi.esb.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import de.metas.edi.esb.commons.Util;

/*
 * #%L
 * metasfresh-edi
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

@Component
public class DownloadMiscFilesRoute extends RouteBuilder
{
	private static final String INPUT_FILE_REMOTE = "{{edi.file.misc.remote}}";

	private static final String OUTPUT_FILE_LOCAL = "{{edi.file.misc.local}}";

	@Override
	public final void configure()
	{
		final String remoteEndpoint = Util.resolveProperty(getContext(), INPUT_FILE_REMOTE, "");
		if (!Util.isEmpty(remoteEndpoint))
		{
			return;
		}
		from(INPUT_FILE_REMOTE)
				.routeId("STEPCOM-Remote-XML-Orders-To-Local")
				.log(LoggingLevel.TRACE, "Getting remote file")
				.to(OUTPUT_FILE_LOCAL);
	}
}
