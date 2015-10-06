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


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;

import javax.mail.MessagingException;
import javax.mail.internet.MimeUtility;
import javax.xml.bind.JAXBElement;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.RuntimeCamelException;

import de.metas.printing.esb.api.PRTRestServiceConstants;
import de.metas.printing.esb.base.inout.bean.PRTCPrintPackageDataTypeConverter;
import de.metas.printing.esb.base.jaxb.generated.PRTCPrintPackageDataType;

public class PRTCPrintPackageDataTypeBean extends PRTCPrintPackageDataTypeConverter
{
	public static final String BEANNAME = "printPckg2PC";

	public static final String METHOD_createPRTCPrintPackageDataTypeRequest = "createPRTCPrintPackageDataTypeRequest";

	/**
	 * <ul>
	 * <li>IN: header sessionId and transactionId
	 * <li>OUT: {@link PRTCPrintPackageDataType}
	 * </ul>
	 * 
	 * @param exchange
	 */
	public void createPRTCPrintPackageDataTypeRequest(Exchange exchange)
	{
		final Message inMessage = exchange.getIn();

		final Integer sessionId = (Integer)inMessage.getHeader(PRTRestServiceConstants.PARAM_SessionId);
		final String transactionId = (String)inMessage.getHeader(PRTRestServiceConstants.PARAM_TransactionId);

		logger.log(Level.FINE, "IN transactionId={0}", transactionId);

		final PRTCPrintPackageDataType printPackage = createPRTCPrintPackageDataTypeRequest(sessionId, transactionId);

		final JAXBElement<PRTCPrintPackageDataType> outBody = factory.createPRTCPrintPackageData(printPackage);

		logger.log(Level.INFO, "OUT body: {0}", outBody);

		exchange.getOut().setBody(outBody);

		logger.log(Level.INFO, "\n\n\n\n AFTER createPRTCPrintPackageDataTypeRequest: initial request->PRTCPrintPackageLookupTransactionIDType\n\n\n\n");
	}

	/**
	 * <ul>
	 * <li>IN: {@link PRTCPrintPackageDataType}</li>
	 * <li>OUT: byte[] (base64 encoded data)</li>
	 * </ul>
	 * 
	 * @param exchange
	 */
	public void mkPrintPackageDataFromXMLBean(Exchange exchange)
	{
		final PRTCPrintPackageDataType printPckgData = exchange.getIn().getBody(PRTCPrintPackageDataType.class);

		if (printPckgData == null)
		{
			return;
		}

		// task 05011: we use the javax.mail MimeUtility in the printing *client* because there were problems with large print jobs and MimeUtil supports streams (not supported by commons-codec)
		// despite still using a byte[] at this place (which could be dealt with by commons-codec), we use MimeUtility here, so that we use the same lib for decoding and encoding and thus hopefully
		// have less problems with unexpected data.
		final byte[] printData = printPckgData.getPrintData();

		final ByteArrayOutputStream base64ResultStream = new ByteArrayOutputStream();
		try
		{
			final OutputStream base64WrapperStream = MimeUtility.encode(base64ResultStream, "base64");
			base64WrapperStream.write(printData);
			base64WrapperStream.close();
		}
		catch (IOException e)
		{
			throw new RuntimeCamelException(e);
		}
		catch (MessagingException e)
		{
			throw new RuntimeCamelException(e);
		}

		final String encodedPrintData = new String(base64ResultStream.toByteArray());
		logger.log(Level.FINE, "OUT body: {0}", encodedPrintData);

		exchange.getOut().setBody(encodedPrintData);

		logger.log(Level.FINE,
				"\n\n\n\n AFTER mkPrintPackageDataFromXMLBean: PRTCPrintPackageLookupTransactionIDType -> String(Base64.encodeBase64(PRTCPrintPackageLookupTransactionIDType.getPrintData())\n\n\n\n");
	}
}
