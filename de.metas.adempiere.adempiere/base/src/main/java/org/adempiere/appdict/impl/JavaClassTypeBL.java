package org.adempiere.appdict.impl;

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


import org.adempiere.appdict.IJavaClassTypeBL;
import org.adempiere.appdict.model.I_AD_JavaClass_Type;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.compiere.util.Util;

public class JavaClassTypeBL implements IJavaClassTypeBL
{
	@Override
	public void checkClassName(final I_AD_JavaClass_Type javaClassType)
	{
		final String typeClassname = javaClassType.getClassname();

		if (Check.isEmpty(typeClassname))
		{
			return;// do nothing, there can be a type without classname
		}

		try
		{
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			if (classLoader == null)
			{
				classLoader = Util.class.getClassLoader();
			}
			classLoader.loadClass(typeClassname);

		}
		catch (ClassNotFoundException e)
		{
			throw new AdempiereException("Class not found: " + typeClassname, e);
		}

	}
}
