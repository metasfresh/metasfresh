package org.adempiere.util;

/*
 * #%L
 * org.adempiere.util
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



/**
 * Default implementation to be used at ADempiere runtime.
 *
 */
public class DefaultServiceNamePolicy implements IServiceNameAutoDetectPolicy
{

	@Override
	public String getServiceImplementationClassName(Class<? extends IService> clazz)
	{
		final String servicePackageName = clazz.getPackage().getName() + ".impl.";
	
		String serviceClassName;
		if (clazz.getSimpleName().startsWith("I"))
			serviceClassName = clazz.getSimpleName().substring(1);
		else
			serviceClassName = clazz.getSimpleName();
		
		final String serviceClassNameFQ = servicePackageName + serviceClassName;
		return serviceClassNameFQ;
		
	}

}
