package org.adempiere.util;

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


import org.reflections.scanners.AbstractScanner;

public class ClassnameScanner extends AbstractScanner
{
	public ClassnameScanner()
	{
		super();
	}

	@Override
	@SuppressWarnings({ "unchecked" })
	public void scan(final Object cls)
	{
		final String classNameFQ = getMetadataAdapter().getClassName(cls);
		final String className = getClassName(classNameFQ);

		if (acceptResult(className))
		{
			getStore().put(className, classNameFQ);
		}
	}

	private final String getClassName(final String classNameFQ)
	{
		String className = classNameFQ;

		//
		// Get from last "." to the end
		{
			final int idx = className.lastIndexOf(".");
			if (idx >= 0)
			{
				className = className.substring(idx + 1);
			}
		}

		return className;
	}
}
