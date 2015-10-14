package org.adempiere.ad.modelvalidator;

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


import java.lang.reflect.Method;

import org.adempiere.util.lang.ObjectUtils;
import org.compiere.model.ModelValidationEngine;

/**
 * Model Interceptor initializer definition
 * 
 * @author tsa
 * 
 */
/* package */class InterceptorInit
{
	private final Method method;
	private boolean methodRequiresEngine = false;

	public InterceptorInit(Method method)
	{
		super();
		this.method = method;
	}

	public Method getMethod()
	{
		return method;
	}

	/**
	 * 
	 * @return true if method call requires {@link ModelValidationEngine} as first parameter
	 */
	public boolean isMethodRequiresEngine()
	{
		return methodRequiresEngine;
	}

	public void setMethodRequiresEngine(boolean methodRequiresEngine)
	{
		this.methodRequiresEngine = methodRequiresEngine;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}
}
