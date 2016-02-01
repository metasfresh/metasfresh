package de.metas.printing.esb.camel.inout.bean;

/*
 * #%L
 * de.metas.printing.esb.camel
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

import java.util.logging.Level;

import javax.xml.bind.JAXBElement;

import org.apache.camel.Exchange;
import org.apache.camel.Message;

import com.google.common.io.BaseEncoding;

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
	 * <li>OUT: String (base64 encoded data)</li>
	 * </ul>
	 *
	 * @param exchange
	 */
	public void mkPrintPackageDataFromXMLBean(Exchange exchange)
	{
		final PRTCPrintPackageDataType printPckgData = exchange
				.getIn()
				.getBody(PRTCPrintPackageDataType.class);

		if (printPckgData == null)
		{
			return;
		}

		final byte[] printData = printPckgData.getPrintData();

		final String encodedPrintData = BaseEncoding
				.base64()
				.withSeparator("\r\n", 76)
				.encode(printData);

		logger.log(Level.FINE, "OUT body: {0}", encodedPrintData);

		exchange
				.getOut()
				.setBody(encodedPrintData);

		logger.log(Level.FINE,
				"AFTER mkPrintPackageDataFromXMLBean: PRTCPrintPackageLookupTransactionIDType -> String(Base64.encodeBase64(PRTCPrintPackageLookupTransactionIDType.getPrintData())");
	}
}
