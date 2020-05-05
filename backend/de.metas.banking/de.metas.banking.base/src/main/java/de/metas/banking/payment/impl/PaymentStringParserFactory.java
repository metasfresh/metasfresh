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


import java.util.HashMap;
import java.util.Map;

import de.metas.banking.payment.IPaymentStringParserFactory;
import de.metas.banking.payment.spi.IPaymentStringParser;
import de.metas.banking.payment.spi.exception.PaymentStringParseException;
import de.metas.util.Check;

public class PaymentStringParserFactory implements IPaymentStringParserFactory
{
	private final Map<String, IPaymentStringParser> type2Parser = new HashMap<>();

	@Override
	public void registerParser(final String type, final IPaymentStringParser parser)
	{
		Check.assumeNotEmpty(type, "type not empty");
		Check.assumeNotNull(parser, "parser not null");

		Check.assume(!type2Parser.containsKey(type), "Type {} is bound only once for {}, but an attempt was made to bind {}", type, type2Parser.get(type), parser);

		type2Parser.put(type, parser);
	}

	@Override
	public IPaymentStringParser getParser(final String type)
	{
		final IPaymentStringParser parser = type2Parser.get(type);
		if (parser == null)
		{
			throw new PaymentStringParseException("No " + IPaymentStringParser.class + " found for type '" + type + "'");
		}

		return parser;
	}
}
