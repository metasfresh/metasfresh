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

package de.metas.edi.esb.commons.route;

import de.metas.edi.esb.commons.Constants;
import de.metas.edi.esb.commons.Util;
import org.apache.camel.CamelContext;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.spi.DataFormat;
import org.milyn.smooks.camel.dataformat.SmooksDataFormat;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

public abstract class AbstractEDIRoute extends RouteBuilder
{
	/**
	 * EDI LOG Exception Handler EndPoint
	 */
	public static final String EP_EDI_LOG_ExceptionHandler = "{{edi.log.exception.handler}}";

	public static final String EP_EDI_ERROR = "{{edi.error}}";
	protected static final String EP_EDI_DEADLETTER = "{{edi.deadletter}}";

	protected static final String IS_CREATE_XML_FEEDBACK = "IsCreateXMLFeedback";

	public static final String EDI_STEPCOM_CHARSET_NAME = "edi.stepcom.charset.name";


	public static final String EDI_ORDER_EDIMessageDatePattern = "edi.order.edi_message_date_pattern";
	public static final String EDI_ORDER_ADClientValue = "edi.order.ad_client_value";
	public static final String EDI_ORDER_ADOrgID = "edi.order.ad_org_id";
	public static final String EDI_ORDER_ADInputDataDestination_InternalName = "edi.order.ad_input_datadestination_internalname";
	public static final String EDI_ORDER_ADInputDataSourceID = "edi.order.ad_input_datasource_id";
	public static final String EDI_ORDER_ADUserEnteredByID = "edi.order.ad_user_enteredby_id";
	public static final String EDI_ORDER_DELIVERY_RULE = "edi.order.deliveryrule";
	public static final String EDI_ORDER_DELIVERY_VIA_RULE = "edi.order.deliveryviarule";

	/**
	 * Replaces Camel's {@link RouteBuilder#configure()} when extending this class.
	 */
	protected abstract void configureEDIRoute(DataFormat jaxb, DecimalFormat decimalFormat);

	@Override
	public final void configure()
	{
		// Generic Exception and DeadLetterChannel handlers
		onException(Exception.class)
				.handled(true)
				.to(AbstractEDIRoute.EP_EDI_ERROR);

		errorHandler(deadLetterChannel(AbstractEDIRoute.EP_EDI_DEADLETTER)
				.useOriginalMessage()
				.disableRedelivery());

		final JaxbDataFormat jaxbDataFormat = new JaxbDataFormat(Constants.JAXB_ContextPath);
		final DecimalFormat decimalFormat = getDecimalFormatForConfiguration();
		configureEDIRoute(jaxbDataFormat, decimalFormat);
	}

	/**
	 * @return {@link SmooksDataFormat} data format for properties configuration
	 */
	protected final SmooksDataFormat getSDFForConfiguration(final String propertiesConfigurationPath)
	{
		final String smooksConfigurationPath = Util.resolveProperty(getContext(), propertiesConfigurationPath);

		try
		{
			return new SmooksDataFormat(smooksConfigurationPath);
		}
		catch (final Exception e)
		{
			throw new RuntimeCamelException(e);
		}
	}

	/**
	 * @return Decimal format from EDI configuration
	 */
	private DecimalFormat getDecimalFormatForConfiguration()
	{
		final DecimalFormat decimalFormat = (DecimalFormat)NumberFormat.getInstance();

		final CamelContext context = getContext();
		decimalFormat.setMaximumFractionDigits(Integer.parseInt(Util.resolveProperty(context, "edi.decimalformat.maximumFractionDigits")));

		final boolean isGroupingUsed = Boolean.parseBoolean(Util.resolveProperty(context, "edi.decimalformat.isGroupingUsed"));
		decimalFormat.setGroupingUsed(isGroupingUsed);

		final DecimalFormatSymbols decimalFormatSymbols = decimalFormat.getDecimalFormatSymbols();

		if (isGroupingUsed)
		{
			final char groupingSeparator = Util.resolveProperty(context, "edi.decimalformat.symbol.groupingSeparator").charAt(0);
			decimalFormatSymbols.setGroupingSeparator(groupingSeparator);
		}

		final char decimalSeparator = Util.resolveProperty(context, "edi.decimalformat.symbol.decimalSeparator").charAt(0);
		decimalFormatSymbols.setDecimalSeparator(decimalSeparator);

		decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols); // though it seems redundant, it won't be set otherwise for some reason...

		return decimalFormat;
	}
}
