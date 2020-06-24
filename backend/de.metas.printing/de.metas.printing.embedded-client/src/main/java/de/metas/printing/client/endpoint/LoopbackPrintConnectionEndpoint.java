package de.metas.printing.client.endpoint;

/*
 * #%L
 * de.metas.printing.embedded-client
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


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.server.rpl.api.IIMPProcessorBL;
import org.adempiere.server.rpl.api.IImportHelper;
import org.compiere.util.Env;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import de.metas.printing.api.IPrinterBL;
import de.metas.printing.client.IPrintConnectionEndpoint;
import de.metas.printing.esb.api.LoginRequest;
import de.metas.printing.esb.api.LoginResponse;
import de.metas.printing.esb.api.PrintJobInstructionsConfirm;
import de.metas.printing.esb.api.PrintPackage;
import de.metas.printing.esb.api.PrinterHW;
import de.metas.printing.esb.api.PrinterHWList;
import de.metas.printing.esb.base.inout.bean.PRTADPrinterHWTypeConverter;
import de.metas.printing.esb.base.inout.bean.PRTCPrintJobInstructionsConfirmTypeConverter;
import de.metas.printing.esb.base.inout.bean.PRTCPrintPackageDataTypeConverter;
import de.metas.printing.esb.base.inout.bean.PRTCPrintPackageTypeConverter;
import de.metas.printing.esb.base.jaxb.JAXBConstants;
import de.metas.printing.esb.base.jaxb.ObjectFactoryHelper;
import de.metas.printing.esb.base.jaxb.generated.ObjectFactory;
import de.metas.printing.esb.base.jaxb.generated.PRTADPrinterHWType;
import de.metas.printing.esb.base.jaxb.generated.PRTCPrintJobInstructionsConfirmType;
import de.metas.printing.esb.base.jaxb.generated.PRTCPrintPackageDataType;
import de.metas.printing.esb.base.jaxb.generated.PRTCPrintPackageType;
import de.metas.printing.model.I_C_Print_Job_Instructions;
import de.metas.util.Services;

/**
 * Implementation of {@link IPrintConnectionEndpoint} which calls the replication module (i.e. {@link IImportHelper}) directly.
 * 
 * @author tsa
 * 
 */
public class LoopbackPrintConnectionEndpoint implements IPrintConnectionEndpoint
{
	private final Properties ctx;
	private final IImportHelper importHelper;

	private final PRTADPrinterHWTypeConverter converterPRTADPrinterHWType = new PRTADPrinterHWTypeConverter();
	private final PRTCPrintPackageTypeConverter converterPRTCPrintPackageType = new PRTCPrintPackageTypeConverter();
	private final PRTCPrintPackageDataTypeConverter converterPRTCPrintPackageDataType = new PRTCPrintPackageDataTypeConverter();
	private final PRTCPrintJobInstructionsConfirmTypeConverter converterPRTCPrintJobInstructionsConfirmType = new PRTCPrintJobInstructionsConfirmTypeConverter();

	private final JAXBContext jaxbContext;
	private final Marshaller jaxbMarshaller;
	private final Unmarshaller jaxbUnmarshaller;

	public LoopbackPrintConnectionEndpoint()
	{
		this.ctx = Env.getCtx();

		this.importHelper = Services.get(IIMPProcessorBL.class).createImportHelper(ctx);

		// Init JAXB
		try
		{
			this.jaxbContext = JAXBContext.newInstance(JAXBConstants.JAXB_ContextPath);
			this.jaxbMarshaller = jaxbContext.createMarshaller();
			this.jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		}
		catch (JAXBException e)
		{
			throw new AdempiereException(e.getLocalizedMessage(), e);
		}

	}

	private int getAD_Session_ID()
	{
		return Env.getAD_Session_ID(ctx);
	}

	@Override
	public void addPrinterHW(final PrinterHWList printerHWList)
	{
		final List<PrinterHW> printersHW = printerHWList.getHwPrinters();
		if (printersHW == null)
		{
			return;
		}

		final List<PRTADPrinterHWType> xmls = converterPRTADPrinterHWType.mkPRTADPrinterHWs(printerHWList, getAD_Session_ID());
		for (final Object xml : xmls)
		{
			sendXmlObject(xml);
		}
	}

	@Override
	public PrintPackage getNextPrintPackage()
	{
		final int sessionId = getAD_Session_ID();
		final String transactionId = createTransactionId();

		final PRTCPrintPackageType xmlRequest = converterPRTCPrintPackageType.createPRTCPrintPackageTypeRequest(sessionId, transactionId);
		final PRTCPrintPackageType xmlResponse = sendXmlObject(xmlRequest);
		
		if (xmlResponse == null)
		{
			return null;
		}
		else if (xmlResponse.getCPrintJobInstructionsID()!= null && xmlResponse.getCPrintJobInstructionsID().intValue()>0) 
		{
			// check if we deal with a pdf printing
			final I_C_Print_Job_Instructions pji = InterfaceWrapperHelper.create(Env.getCtx(), xmlResponse.getCPrintJobInstructionsID().intValue(), I_C_Print_Job_Instructions.class, ITrx.TRXNAME_None);
			// the HW printer is set in instruction only when the printer is PDF type
			if (pji!= null && pji.getAD_PrinterHW_ID() > 0 && Services.get(IPrinterBL.class).isAttachToPrintPackagePrinter(pji.getAD_PrinterHW()))
			{
				return null;
			}
		}

		final PrintPackage printPackage = converterPRTCPrintPackageType.createPackageTypeResponse(xmlResponse);

		return printPackage;
	}

	@Override
	public InputStream getPrintPackageData(final PrintPackage printPackage)
	{
		final int sessionId = getAD_Session_ID();
		final String transactionId = printPackage.getTransactionId();

		final PRTCPrintPackageDataType xmlRequest = converterPRTCPrintPackageDataType.createPRTCPrintPackageDataTypeRequest(sessionId, transactionId);
		final PRTCPrintPackageDataType xmlResponse = sendXmlObject(xmlRequest);
		if (xmlResponse == null)
		{
			return null;
		}

		final byte[] data = xmlResponse.getPrintData();
		final ByteArrayInputStream in = new ByteArrayInputStream(data);

		return in;
	}

	@Override
	public void sendPrintPackageResponse(PrintPackage printPackage, PrintJobInstructionsConfirm response)
	{
		final int sessionId = getAD_Session_ID();
		final PRTCPrintJobInstructionsConfirmType xmlRequest = converterPRTCPrintJobInstructionsConfirmType.createPRTADPrintPackageResponse(response, sessionId);
		sendXmlObject(xmlRequest);
	}

	private <T> T sendXmlObject(final Object xmlRequestObj)
	{
		final Document xmlRequest = convertObjectToDocument(xmlRequestObj);

		final StringBuilder importInfo = new StringBuilder();
		final Document xmlResponse = importHelper.importXMLDocument(importInfo, xmlRequest, ITrx.TRXNAME_None);

		if (xmlResponse == null)
		{
			return null;
		}

		try
		{
			@SuppressWarnings("unchecked")
			final JAXBElement<T> jaxbElement = (JAXBElement<T>)jaxbUnmarshaller.unmarshal(xmlResponse);
			final T xmlResponseObj = jaxbElement.getValue();
			return xmlResponseObj;
		}
		catch (Exception e)
		{
			throw new AdempiereException("Cannot unmarshall xml response '" + xmlResponse + "' associated with request '" + xmlRequestObj + "'", e);
		}
	}

	private Document convertObjectToDocument(final Object xmlObj)
	{
		try
		{
			return convertObjectToDocument0(xmlObj);
		}
		catch (final Exception e)
		{
			throw new AdempiereException("Error while converting object '" + xmlObj + "' to XML document", e);
		}
	}

	private Document convertObjectToDocument0(final Object xmlObj) throws JAXBException, ParserConfigurationException, SAXException, IOException
	{
		final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilderFactory.setValidating(false);

		final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		final Document document = documentBuilder.newDocument();

		final JAXBElement<Object> jaxbElement = ObjectFactoryHelper.createJAXBElement(new ObjectFactory(), xmlObj);
		jaxbMarshaller.marshal(jaxbElement, document);

		return document;
	}

	private String createTransactionId()
	{
		final String transactionId = UUID.randomUUID().toString();
		return transactionId;
	}

	@Override
	public LoginResponse login(final LoginRequest loginRequest)
	{
		throw new UnsupportedOperationException();
		// final PRTLoginRequestType xmlLoginRequest = LoginRequestConverter.instance.convert(loginRequest);
		// final PRTLoginRequestType xmlLoginResponse = sendXmlObject(xmlLoginRequest);
		// final LoginResponse loginResponse = LoginResponseConverter.instance.convert(xmlLoginResponse);
		// return loginResponse;
	}

}
