/*
 * #%L
 * exchange-routes
 * %%
 * Copyright (C) 2022 metas GmbH
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

package com.adekia.exchange.camel.logger;

import com.adekia.exchange.context.Ctx;
import org.apache.camel.Exchange;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@ConditionalOnSingleCandidate(SimpleLog.class)
public class SimpleLog extends ArrayList<String>
{
	public static String CAMEL_PROPERTY_NAME = "camelLogs";

	public  static SimpleLog getLogger(Exchange exchange)
	{
		SimpleLog logs = (SimpleLog) exchange.getProperty(CAMEL_PROPERTY_NAME);
		if (logs == null)
		{
			logs = new SimpleLog();
			exchange.setProperty(CAMEL_PROPERTY_NAME, logs);
		}
		return logs;
	}

	@Override
	public boolean add(final String s)
	{
		String cleanString = s.replaceAll("\\s{2,}", " ");
		return super.add(cleanString);
	}
}
