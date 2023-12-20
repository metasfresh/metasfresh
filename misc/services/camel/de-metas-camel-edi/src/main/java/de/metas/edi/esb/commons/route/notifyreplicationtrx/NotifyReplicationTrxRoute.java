/*
 * #%L
 * de-metas-camel-edi
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.edi.esb.commons.route.notifyreplicationtrx;

import de.metas.common.util.StringUtils;
import de.metas.edi.esb.commons.Constants;
import de.metas.edi.esb.jaxb.metasfresh.EDIReplicationTrxUpdateType;
import de.metas.edi.esb.jaxb.metasfresh.ObjectFactory;
import de.metas.edi.esb.jaxb.metasfresh.ReplicationEventEnum;
import de.metas.edi.esb.jaxb.metasfresh.ReplicationModeEnum;
import de.metas.edi.esb.jaxb.metasfresh.ReplicationTypeEnum;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBElement;
import java.nio.charset.StandardCharsets;

import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class NotifyReplicationTrxRoute extends RouteBuilder
{
	public static final String NOTIFY_REPLICATION_TRX_UPDATE = "notify-replication-trx-update";

	private static final ObjectFactory factory = Constants.JAXB_ObjectFactory;

	@Override
	public void configure()
	{
		final JaxbDataFormat dataFormat = new JaxbDataFormat(EDIReplicationTrxUpdateType.class.getPackage().getName());
		dataFormat.setCamelContext(getContext());
		dataFormat.setEncoding(StandardCharsets.UTF_8.name());

		// @formatter:off
		from(direct(NOTIFY_REPLICATION_TRX_UPDATE))
				.choice()
					.when(bodyAs(NotifyReplicationTrxRequest.class).isNull())
						.log(LoggingLevel.INFO, "Nothing to do! NotifyReplicationTrxRequest is null!")
					.otherwise()
						.process(this::notifyReplicationTrxUpdateProcessor)
						.marshal(dataFormat)

						.log(LoggingLevel.INFO, "EDI: Sending EDI_ReplicationTrx_UpdateType document to metasfresh...")

						.to("{{" + Constants.EP_AMQP_TO_MF + "}}")
					.endChoice()
				.end();
		// @formatter:on
	}

	private void notifyReplicationTrxUpdateProcessor(final Exchange exchange)
	{
		final NotifyReplicationTrxRequest request = exchange.getIn().getBody(NotifyReplicationTrxRequest.class);

		final EDIReplicationTrxUpdateType document = factory.createEDIReplicationTrxUpdateType();
		document.setName(request.getTrxName());
		document.setIsReplicationTrxFinished(StringUtils.ofBooleanNonNull(request.isFinished()));
		document.setIsError(StringUtils.ofBooleanNonNull(request.isError()));

		document.setErrorMsg(request.getErrorMsg());

		document.setTrxNameAttr(request.getTrxName());
		document.setADClientValueAttr(request.getClientValue());
		document.setVersionAttr(Constants.EXP_FORMAT_GENERIC_VERSION);
		document.setReplicationEventAttr(ReplicationEventEnum.AfterChange);
		document.setReplicationModeAttr(ReplicationModeEnum.Table);
		document.setReplicationTypeAttr(ReplicationTypeEnum.Merge);

		final JAXBElement<EDIReplicationTrxUpdateType> jaxbElement = factory.createEDIReplicationTrxUpdate(document);

		exchange.getIn().setBody(jaxbElement);
	}
}
