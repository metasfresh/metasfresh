package de.metas.banking.payment.impl;

/*
 * #%L
 * de.metas.banking.base
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


import java.util.List;
import java.util.Properties;

import org.adempiere.service.ISysConfigBL;
import org.compiere.util.Env;

import de.metas.banking.payment.IPaymentString;
import de.metas.banking.payment.IPaymentStringBL;
import de.metas.banking.payment.IPaymentStringDataProvider;
import de.metas.banking.payment.IPaymentStringParserFactory;
import de.metas.banking.payment.spi.IPaymentStringParser;
import de.metas.banking.payment.spi.exception.PaymentStringParseException;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 * @author al
 */
public class PaymentStringBL implements IPaymentStringBL
{
	@Override
	public IPaymentStringParser getParserForSysConfig(final String sysConfig)
	{
		final String parserType = Services.get(ISysConfigBL.class).getValue(sysConfig, Env.getAD_Client_ID(Env.getCtx()));
		Check.assumeNotEmpty(parserType, "paymentParserType not empty");

		final IPaymentStringParser paymentStringParser = Services.get(IPaymentStringParserFactory.class).getParser(parserType);
		return paymentStringParser;
	}

	@Override
	public IPaymentStringDataProvider getDataProvider(final Properties ctx, final IPaymentStringParser paymentStringParser, final String paymentStringText) throws PaymentStringParseException
	{
		Check.assumeNotNull(paymentStringParser, "paymentStringParser not null");
		Check.assumeNotEmpty(paymentStringText, "paymentStringText not empty");

		final IPaymentString paymentString;
		try
		{
			paymentString = paymentStringParser.parse(ctx, paymentStringText);
		}
		catch (final IndexOutOfBoundsException ex)
		{
			throw new PaymentStringParseException(ERR_InvalidPaymentStringLength, ex);
		}

		final List<String> collectedErrors = paymentString.getCollectedErrors();
		if (!collectedErrors.isEmpty())
		{
			final StringBuilder exceptions = new StringBuilder();
			for (final String exception : collectedErrors)
			{
				exceptions.append(exception)
						.append("\n");
			}

			throw new PaymentStringParseException(exceptions.toString());
		}

		final IPaymentStringDataProvider dataProvider = paymentString.getDataProvider();
		return dataProvider;
	}
}
