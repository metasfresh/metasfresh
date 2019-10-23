package de.metas.cache.interceptor.testservices.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import org.adempiere.util.proxy.Cached;
import org.junit.Ignore;

import de.metas.cache.interceptor.testservices.ITestServiceWithMutableMethodParameter;

@Ignore
public class TestServiceWithMutableMethodParameter implements ITestServiceWithMutableMethodParameter
{

	@Override
	@Cached
	public String methodWithMutableCachedParameter(
			Properties ctx // NOTE: we are not adding @CacheCtx by intention => this is a mutable generic parameter
			)
	{
		return UUID.randomUUID().toString();
	}

	@Override
	@Cached
	public String methodWithDate(Date date)
	{
		return UUID.randomUUID().toString();
	}

	@Override
	@Cached
	public String methodWithTimestamp(Timestamp date)
	{
		return UUID.randomUUID().toString();
	}

	@Override
	@Cached
	public String methodWithBigDecimal(BigDecimal bd)
	{
		return UUID.randomUUID().toString();
	}
}
