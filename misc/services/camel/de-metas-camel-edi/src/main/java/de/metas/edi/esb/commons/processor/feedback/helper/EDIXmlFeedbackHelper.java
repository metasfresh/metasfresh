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

package de.metas.edi.esb.commons.processor.feedback.helper;

import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.commons.lang.exception.ExceptionUtils;

import de.metas.edi.esb.commons.Constants;
import de.metas.edi.esb.commons.Util;
import de.metas.edi.esb.jaxb.metasfresh.EDIExportStatusEnum;
import de.metas.edi.esb.jaxb.metasfresh.ReplicationEventEnum;
import de.metas.edi.esb.jaxb.metasfresh.ReplicationModeEnum;
import de.metas.edi.esb.jaxb.metasfresh.ReplicationTypeEnum;
import de.metas.edi.esb.commons.processor.exception.HeaderNotFoundException;

public final class EDIXmlFeedbackHelper
{
	private static final String EDI_FEEDBACK_CHAREST_NAME = "edi.feedback.charset.name";

	private static final String METHOD_setReplicationEventAttr = "setReplicationEventAttr";
	private static final String METHOD_setReplicationModeAttr = "setReplicationModeAttr";
	private static final String METHOD_setReplicationTypeAttr = "setReplicationTypeAttr";
	private static final String METHOD_setVersionAttr = "setVersionAttr";
	private static final String METHOD_setEDIExportStatus = "setEDIExportStatus";
	private static final String METHOD_setEDIErrorMsg = "setEDIErrorMsg";

	private static final transient Logger logger = Logger.getLogger(EDIXmlFeedbackHelper.class.getName());

	public static final String HEADER_OriginalXMLBody = "OriginalXMLBody";

	public static final String HEADER_ADClientValueAttr = "ADClientValueAttr";
	public static final String HEADER_RecordID = "RecordID";

	public static final String HEADER_ROUTE_ID = "CamelRouteID";

	private EDIXmlFeedbackHelper()
	{
		super();
	}

	public static <T> void createEDIFeedbackType(final Exchange exchange,
			final EDIExportStatusEnum status,
			final Class<T> feedbackType, final QName feedbackQName,
			final String recordIdSetter)
	{
		// make sure that the response sent to metasfresh has the correct CHARSET
		// NOTE that this will override the existing CHARSET, and that if you do stuff with other exchange content afterwards, you need to set it (again if necessary)
		exchange.setProperty(Exchange.CHARSET_NAME, Util.resolveProperty(exchange.getContext(), EDIXmlFeedbackHelper.EDI_FEEDBACK_CHAREST_NAME));

		final Message inMessage = exchange.getIn();

		// get all generic data and check it for nulls
		final Object originalXMLBody = inMessage.getHeader(EDIXmlFeedbackHelper.HEADER_OriginalXMLBody);
		if (originalXMLBody == null)
		{
			final HeaderNotFoundException ex = new HeaderNotFoundException("Header " + EDIXmlFeedbackHelper.HEADER_OriginalXMLBody + " not found.");
			logExceptionAndStopExchange(exchange, ex);
			return;
		}

		final String clientValueAttr = inMessage.getHeader(EDIXmlFeedbackHelper.HEADER_ADClientValueAttr, String.class);
		if (clientValueAttr == null)
		{
			final HeaderNotFoundException ex = new HeaderNotFoundException("Header " + EDIXmlFeedbackHelper.HEADER_ADClientValueAttr + " not found.");
			logExceptionAndStopExchange(exchange, ex);
			return;
		}

		final BigInteger recordId = inMessage.getHeader(EDIXmlFeedbackHelper.HEADER_RecordID, BigInteger.class);
		if (recordId == null)
		{
			final HeaderNotFoundException ex = new HeaderNotFoundException("Header " + EDIXmlFeedbackHelper.HEADER_RecordID + " not found.");
			logExceptionAndStopExchange(exchange, ex);
			return;
		}

		// Create feedback via reflection
		final T feedback;
		try
		{
			feedback = feedbackType.newInstance();

			Util.invokeSetterMethod(feedback, clientValueAttr, String.class, "setADClientValueAttr");
			Util.invokeSetterMethod(feedback, ReplicationEventEnum.AfterChange, ReplicationEventEnum.class, EDIXmlFeedbackHelper.METHOD_setReplicationEventAttr);
			Util.invokeSetterMethod(feedback, ReplicationModeEnum.Table, ReplicationModeEnum.class, EDIXmlFeedbackHelper.METHOD_setReplicationModeAttr);
			Util.invokeSetterMethod(feedback, ReplicationTypeEnum.Merge, ReplicationTypeEnum.class, EDIXmlFeedbackHelper.METHOD_setReplicationTypeAttr);
			Util.invokeSetterMethod(feedback, Constants.EXP_FORMAT_GENERIC_VERSION, String.class, EDIXmlFeedbackHelper.METHOD_setVersionAttr);
			Util.invokeSetterMethod(feedback, recordId, BigInteger.class, recordIdSetter);
			Util.invokeSetterMethod(feedback, status, EDIExportStatusEnum.class, EDIXmlFeedbackHelper.METHOD_setEDIExportStatus);
		}
		catch (final Exception e)
		{
			logExceptionAndStopExchange(exchange, e, "Error invoking feedback object");
			return;
		}

		// Retrieve error message, if any
		if (!EDIExportStatusEnum.Sent.equals(status))
		{
			// We also check for exceptions set by Camel via properties
			final Throwable cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Throwable.class);

			String errorMsg;
			if (cause == null)
			{
				errorMsg = inMessage.getBody(String.class);
			}
			else
			{
				errorMsg = ExceptionUtils.getRootCauseMessage(cause);
			}

			// Aside from the feedback, also log exception for ServiceMix to pick up
			EDIXmlFeedbackHelper.logger.log(Level.SEVERE, "Exception in the exchange. Exception message is: " + errorMsg);

			try
			{
				Util.invokeSetterMethod(feedback, errorMsg, String.class, EDIXmlFeedbackHelper.METHOD_setEDIErrorMsg);
			}
			catch (final Exception e)
			{
				logExceptionAndStopExchange(exchange, e, "Error invoking feedback object");
				return;
			}
		}

		final JAXBElement<T> feedbackBody = new JAXBElement<T>(feedbackQName, feedbackType, null, feedback);
		exchange.getOut().setBody(feedbackBody);

		// We want to keep the headers for future use
		exchange.getOut().setHeaders(inMessage.getHeaders());
	}

	/**
	 * Log exception and stop exchange.
	 *
	 * @see {@link EDIXmlFeedbackHelper#logExceptionAndStopExchange(Exchange, Throwable)} 
	 */
	private static void logExceptionAndStopExchange(final Exchange exchange, final Throwable e)
	{
		logExceptionAndStopExchange(exchange, e, e.getLocalizedMessage());
	}

	/**
	 * Log exception and stop exchange.
	 */
	private static void logExceptionAndStopExchange(final Exchange exchange, final Throwable e, final String errorMessage)
	{
		EDIXmlFeedbackHelper.logger.log(Level.SEVERE, errorMessage, e);

		exchange.setException(e);
		exchange.setProperty(Exchange.ROUTE_STOP, Boolean.TRUE);
	}
}
