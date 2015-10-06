package de.metas.edi.esb.route;

/*
 * #%L
 * Metas :: Components :: EDI  (SMX-4.5.1)
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


import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.model.ModelCamelContext;
import org.apache.camel.spi.DataFormat;
import org.milyn.smooks.camel.dataformat.SmooksDataFormat;

import de.metas.edi.esb.commons.Constants;
import de.metas.edi.esb.commons.Util;

public abstract class AbstractEDIRoute extends RouteBuilder
{
	/**
	 * EDI LOG Exception Handler EndPoint
	 */
	public static final String EP_EDI_LOG_ExceptionHandler = "{{edi.log.exception.handler}}";

	public static final String EP_EDI_ERROR = "{{edi.error}}";
	protected static final String EP_EDI_DEADLETTER = "{{edi.deadletter}}";

	protected static final String IS_CREATE_XML_FEEDBACK = "IsCreateXMLFeedback";

	protected static final String EDI_GENERATED_CHARSET_NAME = "edi.generated.charset.name";

	/**
	 * The local folder where the EDI file will be stored if the conversion is successful
	 */
	protected static final String EP_EDI_LOCAL_Processed = "{{edi.local.processed}}";

	/**
	 * Replaces Camel's {@link RouteBuilder#configure()} when extending this class.
	 * 
	 * @param jaxb
	 */
	protected abstract void configureEDIRoute(final DataFormat jaxb, final DecimalFormat decimalFormat);

	/**
	 * <a href="http://www.smooks.org/mediawiki/index.php?title=V1.5:Smooks_v1.5_User_Guide#Apache_Camel_Integration"> Read more about Smooks Integration</a>
	 */
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
	 * @param propertiesConfigurationPath
	 * @return {@link SmooksDataFormat} data format for properties configuration
	 */
	protected final SmooksDataFormat getSDFForConfiguration(final String propertiesConfigurationPath)
	{
		final String smooksConfigurationPath = Util.resolvePropertyPlaceholders(getContext(), propertiesConfigurationPath);

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

		final ModelCamelContext context = getContext();
		decimalFormat.setMaximumFractionDigits(Integer.valueOf(Util.resolvePropertyPlaceholders(context, "edi.decimalformat.maximumFractionDigits")));

		final boolean isGroupingUsed = Boolean.valueOf(Util.resolvePropertyPlaceholders(context, "edi.decimalformat.isGroupingUsed"));
		decimalFormat.setGroupingUsed(isGroupingUsed);

		final DecimalFormatSymbols decimalFormatSymbols = decimalFormat.getDecimalFormatSymbols();

		if (isGroupingUsed)
		{
			final char groupingSeparator = Util.resolvePropertyPlaceholders(context, "edi.decimalformat.symbol.groupingSeparator").charAt(0);
			decimalFormatSymbols.setGroupingSeparator(groupingSeparator);
		}

		final char decimalSeparator = Util.resolvePropertyPlaceholders(context, "edi.decimalformat.symbol.decimalSeparator").charAt(0);
		decimalFormatSymbols.setDecimalSeparator(decimalSeparator);

		decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols); // though it seems redundant, it won't be set otherwise for some reason...

		return decimalFormat;
	}
}
