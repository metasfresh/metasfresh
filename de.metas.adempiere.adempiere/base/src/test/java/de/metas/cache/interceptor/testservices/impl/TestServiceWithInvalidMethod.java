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


import java.util.UUID;

import org.adempiere.util.proxy.Cached;
import org.junit.Ignore;

import de.metas.cache.annotation.CacheCtx;
import de.metas.cache.interceptor.testservices.ITestServiceWithInvalidMethod;

@Ignore
public class TestServiceWithInvalidMethod implements ITestServiceWithInvalidMethod
{
	@Override
	@Cached
	public String invalidCachCtxParam(
			@CacheCtx String ctx // NOTE: CacheCtx cannot be applied to String
	)
	{
		return UUID.randomUUID().toString();
	}
}
