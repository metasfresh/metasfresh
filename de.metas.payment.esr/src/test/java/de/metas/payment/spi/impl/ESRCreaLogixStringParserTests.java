package de.metas.payment.spi.impl;

import org.compiere.util.Env;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import de.metas.banking.payment.IPaymentString;

/*
 * #%L
 * de.metas.payment.esr
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class ESRCreaLogixStringParserTests
{
	@Test
	public void parse()
	{
		final IPaymentString result = ESRCreaLogixStringParser.instance.parse(Env.getCtx(), "042>000000162591872680005352198+ 012000007>");
		assertThat(result.getPostAccountNo(), is("012000007"));
	}
}
