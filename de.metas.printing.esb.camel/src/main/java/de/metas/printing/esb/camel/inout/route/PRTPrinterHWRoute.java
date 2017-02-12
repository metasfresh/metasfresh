package de.metas.printing.esb.camel.inout.route;

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

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.spi.DataFormat;

import de.metas.printing.esb.base.jaxb.JAXBConstants;
import de.metas.printing.esb.camel.commons.Constants;
import de.metas.printing.esb.camel.inout.bean.PRTADPrinterHWTypeBean;

/**
 * This route is used then a printing client adds another hardware printer it found locally.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class PRTPrinterHWRoute extends RouteBuilder
{
	@Override
	public void configure()
	{
		final DataFormat jaxb = new JaxbDataFormat(JAXBConstants.JAXB_ContextPath);

		onException(Exception.class)
				.handled(true)
				.transform(exceptionMessage())
				.to(PRTRestServiceRoute.EP_CXF_RS_ERROR);

		errorHandler(deadLetterChannel(PRTRestServiceRoute.EP_CXF_RS_ERROR));

		from(PRTRestServiceRoute.EP_PrinterHW)
				.routeId("add-printer-hw")
				
				// note: unmarshaling the POSTed arguments is already done by the cxf component
								
				// POST POJO to ADempiere POJO conversion
				.split().method(PRTADPrinterHWTypeBean.class, PRTADPrinterHWTypeBean.METHOD_mkPRTADPrinterHWRequest)
				// marshal to ADempiere request format
				.marshal(jaxb)
				// finally, send the data to the ADempiere Request Handler and listen to the response
				.to(Constants.EP_JMS_TO_AD);
	}
}
