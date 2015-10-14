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


import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.RuntimeTransformException;
import org.apache.camel.impl.DefaultMessage;

import de.metas.printing.esb.api.PRTRestServiceConstants;
import de.metas.printing.esb.api.PrinterHWList;
import de.metas.printing.esb.base.inout.bean.PRTADPrinterHWTypeConverter;
import de.metas.printing.esb.base.jaxb.generated.PRTADPrinterHWType;

public class PRTADPrinterHWTypeBean extends PRTADPrinterHWTypeConverter
{
	// @SuppressWarnings("unused")
	// private final Util util = new Util();

	public static final String METHOD_mkPRTADPrinterHWRequest = "mkPRTADPrinterHWRequest";

	public void mkPRTADPrinterHWRequest(Exchange exchange)
	{
		final PrinterHWList printerHWList = exchange.getIn().getBody(PrinterHWList.class);

		if (printerHWList == null || printerHWList.getHwPrinters().isEmpty())
		{
			return;
		}

		// get the sessionId from header
		final Integer sessionId = exchange.getIn().getHeader(PRTRestServiceConstants.PARAM_SessionId, Integer.class);
		if (sessionId == null)
		{
			throw new RuntimeTransformException("Header " + PRTRestServiceConstants.PARAM_SessionId + " not found in " + exchange);
		}

		final List<Message> result = new ArrayList<Message>();

		final List<PRTADPrinterHWType> xmlPrinterHWList;
		try
		{
			xmlPrinterHWList = mkPRTADPrinterHWs(printerHWList, sessionId);
		}
		catch (Exception e)
		{
			throw new RuntimeTransformException(e.getLocalizedMessage(), e);
		}

		for (final PRTADPrinterHWType xmlPrinterHW : xmlPrinterHWList)
		{
			result.add(mkPRTADPrinterHWMessage(xmlPrinterHW));
		}

		exchange.getOut().setBody(result);
	}

	private Message mkPRTADPrinterHWMessage(final PRTADPrinterHWType printerHW)
	{
		// Data conversion to XML element
		final JAXBElement<PRTADPrinterHWType> jaxbPrinterHW = factory.createPRTADPrinterHW(printerHW);

		// Now make a message out of the XML element and return it
		final Message message = new DefaultMessage();

		message.setBody(jaxbPrinterHW);

		return message;
	}
}
