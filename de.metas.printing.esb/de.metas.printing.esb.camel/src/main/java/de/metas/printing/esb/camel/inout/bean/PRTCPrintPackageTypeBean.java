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
import java.util.logging.Logger;

import javax.xml.bind.JAXBElement;

import org.apache.camel.Exchange;
import org.apache.camel.Message;

import de.metas.printing.esb.api.PRTRestServiceConstants;
import de.metas.printing.esb.api.PrintPackage;
import de.metas.printing.esb.base.inout.bean.PRTCPrintPackageTypeConverter;
import de.metas.printing.esb.base.jaxb.generated.ObjectFactory;
import de.metas.printing.esb.base.jaxb.generated.PRTCPrintPackageType;

public class PRTCPrintPackageTypeBean extends PRTCPrintPackageTypeConverter
{
	public static final String BEANNAME = "printPckg2PC";

	private final ObjectFactory factory = new ObjectFactory();
	private static final Logger logger = Logger.getLogger(PRTCPrintPackageTypeBean.class.getName());

	public static final String METHOD_CreatePRTCPrintPackageTypeRequest = "createPRTCPrintPackageTypeRequest";

	public void createPRTCPrintPackageTypeRequest(Exchange exchange)
	{
		final Message inMessage = exchange.getIn();

		final Integer sessionId = (Integer)inMessage.getHeader(PRTRestServiceConstants.PARAM_SessionId);
		final String transactionId = (String)inMessage.getHeader(PRTRestServiceConstants.PARAM_TransactionId);

		logger.log(Level.FINE, "IN transactionId={0}", transactionId);

		final PRTCPrintPackageType printPackage = createPRTCPrintPackageTypeRequest(sessionId, transactionId);

		final JAXBElement<PRTCPrintPackageType> outBody = factory.createPRTCPrintPackage(printPackage);

		logger.log(Level.FINE, "OUT body: {0}", outBody);

		exchange.getOut().setBody(outBody);

		logger.log(Level.FINE, "AFTER createPRTCPrintPackageTypeRequest: initial request->PRTCPrintPackageType");
	}

	public void mkPrintPackageFromXMLBean(final Exchange exchange)
	{
		final PRTCPrintPackageType printPckgAD = exchange.getIn().getBody(PRTCPrintPackageType.class);
		
		final PrintPackage printPckgPC = createPackageTypeResponse(printPckgAD);

		logger.log(Level.FINE, "OUT body: {0}", printPckgPC);

		exchange.getOut().setBody(printPckgPC, PrintPackage.class);

		logger.log(Level.FINE, "AFTER mkPrintPackage: PRTCPrintPackageType->PrintPackage");
	}

}
