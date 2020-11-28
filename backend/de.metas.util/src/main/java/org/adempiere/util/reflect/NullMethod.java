package org.adempiere.util.reflect;

/*
 * #%L
 * de.metas.util
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


import java.lang.reflect.Method;

/**
 * Holder for null pattern {@link Method}.
 * 
 * @author tsa
 *
 */
public final class NullMethod
{
	public static final Method NULL;

	static
	{
		try
		{
			NULL = NullMethod.class.getMethod("nullMethod");
		}
		catch (Exception e)
		{
			throw new Error("Cannot load nullMethod", e);
		}
	}

	private NullMethod()
	{
		super();
	}

	public final void nullMethod()
	{
		throw new IllegalStateException("this method shall be never invoked");
	}
}
