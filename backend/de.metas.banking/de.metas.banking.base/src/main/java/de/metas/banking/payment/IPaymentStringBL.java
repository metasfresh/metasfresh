package de.metas.banking.payment;

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


import java.util.Properties;

import org.compiere.model.I_AD_SysConfig;

import de.metas.banking.payment.spi.IPaymentStringParser;
import de.metas.banking.payment.spi.exception.PaymentStringParseException;
import de.metas.util.ISingletonService;

/**
 * @author al
 */
public interface IPaymentStringBL extends ISingletonService
{
	String ERR_InvalidPaymentStringLength = "@InvalidPaymentStringLength@";

	/**
	 * @param sysConfig
	 * @return payment string parser loaded for given {@link I_AD_SysConfig#COLUMNNAME_Name}
	 */
	IPaymentStringParser getParserForSysConfig(String sysConfig);

	/**
	 * @param ctx
	 * @param paymentStringParser
	 * @param paymentStringText
	 *
	 * @return dataProvider implementation which allows fetching data for the parsed payment text
	 *
	 * @throws PaymentStringParseException
	 */
	IPaymentStringDataProvider getDataProvider(Properties ctx, IPaymentStringParser paymentStringParser, String paymentStringText) throws PaymentStringParseException;
}
