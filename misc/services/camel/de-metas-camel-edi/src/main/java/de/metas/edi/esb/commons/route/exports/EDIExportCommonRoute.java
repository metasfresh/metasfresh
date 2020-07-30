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

package de.metas.edi.esb.commons.route.exports;

import java.text.DecimalFormat;

import de.metas.edi.esb.commons.route.AbstractEDIRoute;
import de.metas.edi.esb.desadvexport.compudata.CompuDataDesadvRoute;
import de.metas.edi.esb.desadvexport.ecosio.EcosioDesadvRoute;
import de.metas.edi.esb.commons.DesadvSettings;
import de.metas.edi.esb.desadvexport.stepcom.StepComXMLDesadvRoute;
import de.metas.edi.esb.invoicexport.compudata.CompuDataInvoicRoute;
import de.metas.edi.esb.invoicexport.ecosio.EcosioInvoicRoute;
import de.metas.edi.esb.commons.InvoicSettings;
import de.metas.edi.esb.commons.ClearingCenter;
import de.metas.edi.esb.invoicexport.stepcom.StepComXMLInvoicRoute;
import org.apache.camel.LoggingLevel;
import org.apache.camel.spi.DataFormat;
import org.springframework.stereotype.Component;

import de.metas.edi.esb.commons.Constants;
import de.metas.edi.esb.jaxb.metasfresh.EDICctopInvoicVType;
import de.metas.edi.esb.jaxb.metasfresh.EDIExpDesadvType;
import de.metas.edi.esb.commons.processor.feedback.helper.EDIXmlFeedbackHelper;

@Component
public class EDIExportCommonRoute extends AbstractEDIRoute
{
	@Override
	public void configureEDIRoute(final DataFormat jaxb, final DecimalFormat decimalFormat)
	{
		// disable spooling to disk
		// i believe our ram is sufficient, and when we don'T disable we get (at least in unit tests on windows) an exception
		// [...]
		// Caused by: org.apache.camel.RuntimeCamelException: Cannot reset stream from file [...]camel\camel-tmp-06f3b5eb-436c-480b-9eff-d307d31801e6\cos3829183424344574052.tmp
		// [...]
		getContext().getStreamCachingStrategy().setSpoolThreshold(-1);

		from(Constants.EP_AMQP_FROM_MF)
				.streamCaching()
				.routeId("XML-To-EDI-Common")

				.log(LoggingLevel.INFO, "EDI: Processing XML body:\r\n" + body())

				.setProperty(AbstractEDIRoute.IS_CREATE_XML_FEEDBACK, constant(true))
				.setProperty(Constants.DECIMAL_FORMAT, constant(decimalFormat))

				.log(LoggingLevel.INFO, "EDI: Unmarshalling XML...")
				.unmarshal(jaxb)

				.setHeader(EDIXmlFeedbackHelper.HEADER_OriginalXMLBody, body())

				// @formatter:off
				.choice()
					// INVOIC - figure out which clearing center we shall use
					.when(body().isInstanceOf(EDICctopInvoicVType.class))
						.process(exchange -> {
							final String receiverGLN = exchange.getIn().getBody(EDICctopInvoicVType.class).getReceivergln();
							final ClearingCenter clearingCenter = InvoicSettings.forReceiverGLN(exchange.getContext(), receiverGLN).getClearingCenter();
							exchange.getIn().setHeader("ClearingCenter", clearingCenter.toString());
						})
						.log(LoggingLevel.INFO, "EDI: ClearingCenter="+header("ClearingCenter"))
						.choice()
							.when(header("ClearingCenter").isEqualTo(ClearingCenter.STEPcom.toString()))
								.to(StepComXMLInvoicRoute.EP_EDI_STEPCOM_XML_INVOICE_CONSUMER)
							.when(header("ClearingCenter").isEqualTo(ClearingCenter.CompuData.toString()))
								.to(CompuDataInvoicRoute.EP_EDI_COMPUDATA_INVOICE_CONSUMER)
							.when(header("ClearingCenter").isEqualTo(ClearingCenter.ecosio.toString()))
								.to(EcosioInvoicRoute.EP_EDI_METASFRESH_XML_INVOIC_CONSUMER)
						.endChoice()
					.when(body().isInstanceOf(EDIExpDesadvType.class))
						// DESADV - figure out which clearing center we shall use
						.process(exchange -> {
							final String receiverGLN = exchange.getIn().getBody(EDIExpDesadvType.class).getCBPartnerID().getEdiRecipientGLN();
							final ClearingCenter clearingCenter = DesadvSettings.forReceiverGLN(exchange.getContext(), receiverGLN).getClearingCenter();
							exchange.getIn().setHeader("ClearingCenter", clearingCenter.toString());
						})
						.log(LoggingLevel.INFO, "EDI: ClearingCenter="+header("ClearingCenter"))
						.choice()
							.when(header("ClearingCenter").isEqualTo(ClearingCenter.STEPcom.toString()))
								.to(StepComXMLDesadvRoute.EP_EDI_STEPCOM_XML_DESADV_CONSUMER)
							.when(header("ClearingCenter").isEqualTo(ClearingCenter.CompuData.toString()))
								.to(CompuDataDesadvRoute.EP_EDI_COMPUDATA_DESADV_CONSUMER)
							.when(header("ClearingCenter").isEqualTo(ClearingCenter.ecosio.toString()))
								.to(EcosioDesadvRoute.EP_EDI_METASFRESH_XML_DESADV_CONSUMER)
						.endChoice()
				.end();
				// @formatter:on
	}
}
