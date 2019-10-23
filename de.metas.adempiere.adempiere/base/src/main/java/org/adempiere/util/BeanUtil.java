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


import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class BeanUtil
{
	public static PropertyDescriptor getPropertyDescriptor(BeanInfo beanInfo, String propertyName)
	{
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		if (propertyDescriptors == null || propertyDescriptors.length == 0)
		{
			return null;
		}
		
		String propertyName2 = null;
		if (propertyName.toLowerCase().startsWith("is"))
		{
			propertyName2 = propertyName.substring(2);
		}
		PropertyDescriptor suggestion2 = null;
		
		for (PropertyDescriptor pd : propertyDescriptors)
		{
			if (pd.getName().equalsIgnoreCase(propertyName))
			{
				return pd;
			}

			if (pd.getName().equalsIgnoreCase(propertyName2))
			{
				suggestion2 = pd;
			}
			
		}
		
		return suggestion2;
	}
	
	public static Map<String, PropertyDescriptor> getPropertyDescriptorsMap(BeanInfo beanInfo, List<String> propertyNames)
	{
		final Map<String, PropertyDescriptor> map = new HashMap<String, PropertyDescriptor>();
		for (String propertyName : propertyNames)
		{
			PropertyDescriptor pd = getPropertyDescriptor(beanInfo, propertyName);
			if (pd != null)
			{
				map.put(propertyName, pd);
			}
		}
		return map;
	}
}
