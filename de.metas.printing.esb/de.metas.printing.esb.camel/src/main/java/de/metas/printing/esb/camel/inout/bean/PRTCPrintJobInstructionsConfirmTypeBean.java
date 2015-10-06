package de.metas.printing.esb.camel.inout.bean;

/*
 * #%L
 * Metas :: Components :: Request-Response Framework for Mass Printing (SMX-4.5.2)
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import javax.xml.bind.JAXBElement;

import org.apache.camel.Body;
import org.apache.camel.Header;
import org.apache.camel.Message;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.impl.DefaultMessage;

import de.metas.printing.esb.api.PRTRestServiceConstants;
import de.metas.printing.esb.api.PrintJobInstructionsConfirm;
import de.metas.printing.esb.base.inout.bean.PRTCPrintJobInstructionsConfirmTypeConverter;
import de.metas.printing.esb.base.jaxb.generated.PRTCPrintJobInstructionsConfirmType;

public class PRTCPrintJobInstructionsConfirmTypeBean extends PRTCPrintJobInstructionsConfirmTypeConverter
{
	// @SuppressWarnings("unused")
	// private final Util util = new Util();

	// private static final Logger logger = Logger.getLogger(PRTCPrintJobInstructionsConfirmTypeBean.class.getName());

	public static final String METHOD_mkPRTADPrintPackageResponseRequest = "mkPRTADPrintPackageResponseRequest";

	public Message mkPRTADPrintPackageResponseRequest(
			@Body final PrintJobInstructionsConfirm response,
			@Header(PRTRestServiceConstants.PARAM_SessionId) final int sessionId)
	{
		final PRTCPrintJobInstructionsConfirmType xmlResponse;
		try
		{
			xmlResponse = createPRTADPrintPackageResponse(response, sessionId);
		}
		catch (RuntimeCamelException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new RuntimeCamelException(e.getLocalizedMessage(), e);
		}

		final Message result = mkPRTADPrintPackageResponseMessage(xmlResponse);

		return result;
	}

	private Message mkPRTADPrintPackageResponseMessage(final PRTCPrintJobInstructionsConfirmType response)
	{
		// Data conversion to XML element
		final JAXBElement<PRTCPrintJobInstructionsConfirmType> jaxbResponse = factory.createPRTCPrintJobInstructionsConfirm(response);

		// Now make a message out of the XML element and return it
		final Message message = new DefaultMessage();

		message.setBody(jaxbResponse);

		return message;
	}
}
