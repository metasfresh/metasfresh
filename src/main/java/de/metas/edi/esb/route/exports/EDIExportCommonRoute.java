package de.metas.edi.esb.route.exports;

import java.text.DecimalFormat;

import org.apache.camel.LoggingLevel;
import org.apache.camel.spi.DataFormat;
import org.springframework.stereotype.Component;

/*
 * #%L
 * de.metas.edi.esb
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

import de.metas.edi.esb.commons.Constants;
import de.metas.edi.esb.commons.Util;
import de.metas.edi.esb.jaxb.metasfresh.EDICctopInvoicVType;
import de.metas.edi.esb.jaxb.metasfresh.EDIExpDesadvType;
import de.metas.edi.esb.processor.feedback.helper.EDIXmlFeedbackHelper;
import de.metas.edi.esb.route.AbstractEDIRoute;

@Component
public class EDIExportCommonRoute extends AbstractEDIRoute
{
	public static final String EDI_INVOICE_IS_STEPCOM_XML = "edi.props.invoic.isStepComXML";

	public static final String EDI_DESADV_IS_STEPCOM_XML = "edi.props.desadv.isStepComXML";

	@Override
	public void configureEDIRoute(final DataFormat jaxb, final DecimalFormat decimalFormat)
	{
		final String isXMLInvoice = Util.resolveProperty(getContext(), EDIExportCommonRoute.EDI_INVOICE_IS_STEPCOM_XML);
		final String isXMLDesadv = Util.resolveProperty(getContext(), EDIExportCommonRoute.EDI_DESADV_IS_STEPCOM_XML);
		from(Constants.EP_AMQP_FROM_MF)
				.routeId("XML-To-EDI-Common")

				.log(LoggingLevel.INFO, "EDI: Processing XML body:\r\n" + body())

				.setProperty(AbstractEDIRoute.IS_CREATE_XML_FEEDBACK, constant(true))
				.setProperty(Constants.DECIMAL_FORMAT, constant(decimalFormat))

				.log(LoggingLevel.INFO, "EDI: Unmarshalling XML...")
				.unmarshal(jaxb)

				.setHeader(EDIXmlFeedbackHelper.HEADER_OriginalXMLBody, body())

				// @formatter:off
				.choice()
					// Invoice
					.when(body().isInstanceOf(EDICctopInvoicVType.class))
						.choice()
							.when(isXML -> Boolean.valueOf(isXMLInvoice))
								.to(StepComXMLInvoicRoute.EP_EDI_STEPCOM_XML_INVOICE_CONSUMER)
							.otherwise()
								.to(CompuDataInvoicRoute.EP_EDI_COMPUDATA_INVOICE_CONSUMER)
						.endChoice()
					.when(body().isInstanceOf(EDIExpDesadvType.class))
						.choice()
							.when(isXML -> Boolean.valueOf(isXMLDesadv))
								.to(StepComXMLDesadvRoute.EP_EDI_STEPCOM_XML_DESADV_CONSUMER)
							.otherwise()
								.to(CompuDataDesadvRoute.EP_EDI_COMPUDATA_DESADV_CONSUMER)
						.endChoice()
				.end();
				// @formatter:on
	}
}
