/*
 * #%L
 * de-metas-camel-leichundmehl
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.ftp;

import de.metas.camel.externalsystems.common.RouteBuilderHelper;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.LeichMehlConstants.HEADER_FTP_DIRECTORY;
import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.LeichMehlConstants.HEADER_FTP_FILENAME;
import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.LeichMehlConstants.HEADER_FTP_HOST;
import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.LeichMehlConstants.HEADER_FTP_PASSWORD;
import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.LeichMehlConstants.HEADER_FTP_PORT;
import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.LeichMehlConstants.HEADER_FTP_USERNAME;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class SendToFTPRouteBuilder extends RouteBuilder
{
	public static final String SEND_TO_FTP_ROUTE_ID = "LeichUndMehl-sendToFTP";

	@Override
	public void configure() throws Exception
	{
		//@formatter:off
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(direct(MF_ERROR_ROUTE_ID));

		from(direct(SEND_TO_FTP_ROUTE_ID))
				.routeId(SEND_TO_FTP_ROUTE_ID)
				.log("Route invoked!")
				.process(this::prepareAndSendToFTP)
				.marshal(RouteBuilderHelper.setupJacksonDataFormatFor(getContext(), String.class))
				.removeHeaders("CamelHttp*")
				.toD("ftp://${header.FTPHost}:${header.FTPPort}/${header.FTPDirectory}?username=${header.FTPUsername}&password=${header.FTPPassword}&fileName=${header.FTPFilename}");
		//@formatter:on
	}

	private void prepareAndSendToFTP(@NonNull final Exchange exchange)
	{
		final DispatchMessageRequest request = exchange.getIn().getBody(DispatchMessageRequest.class);

		final FTPCredentials ftpCredentials = request.getFtpCredentials();

		exchange.getIn().setBody(request.getFtpPayload(), String.class);
		exchange.getIn().setHeader(HEADER_FTP_HOST, ftpCredentials.getFtpHost());
		exchange.getIn().setHeader(HEADER_FTP_PORT, ftpCredentials.getFtpPort());
		exchange.getIn().setHeader(HEADER_FTP_USERNAME, ftpCredentials.getFtpUsername());
		exchange.getIn().setHeader(HEADER_FTP_PASSWORD, ftpCredentials.getFtpPassword());
		exchange.getIn().setHeader(HEADER_FTP_DIRECTORY, ftpCredentials.getFtpDirectory());
		exchange.getIn().setHeader(HEADER_FTP_FILENAME, ftpCredentials.getFtpFilename());
	}
}
