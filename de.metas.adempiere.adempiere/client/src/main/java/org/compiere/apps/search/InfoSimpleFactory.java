package org.compiere.apps.search;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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


import java.awt.Frame;
import java.util.Map;
import java.util.Map.Entry;

import org.adempiere.ad.service.IADInfoWindowDAO;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_InfoWindow;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;

import org.compiere.util.Env;

public class InfoSimpleFactory
{
	private static InfoSimpleFactory s_instance = new InfoSimpleFactory();

	private Logger log = LogManager.getLogger(getClass());

	public static InfoSimpleFactory get()
	{
		return s_instance;
	}

	private InfoSimpleFactory()
	{

	}

	public InfoSimple create(boolean modal, int windowNo, String tableName, boolean multiSelection, String whereClause, Map<String, Object> attributes)
	{
		final Frame frame = Env.getWindow(windowNo);
		final String keyColumn = tableName + "_ID";
		final String value = "";

		final I_AD_InfoWindow infoWindow = Services.get(IADInfoWindowDAO.class).retrieveInfoWindowByTableName(Env.getCtx(), tableName);
		if (infoWindow == null)
		{
			log.warn("No info window found for " + tableName);
			return null;
		}
		if (!infoWindow.isActive())
		{
			log.warn("No ACTIVE info window found for " + tableName + " (" + infoWindow + ")");
			return null;
		}

		String className = infoWindow.getClassname();
		if (Check.isEmpty(className))
			className = InfoSimple.class.getCanonicalName();

		try
		{
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			if (cl == null)
				cl = getClass().getClassLoader();

			@SuppressWarnings("unchecked")
			Class<InfoSimple> clazz = (Class<InfoSimple>)cl.loadClass(className);
			java.lang.reflect.Constructor<? extends Info> ctor = clazz.getConstructor(Frame.class);
			InfoSimple infoSimple = (InfoSimple)ctor.newInstance(frame);
			infoSimple.init(modal, windowNo, infoWindow, keyColumn, value, multiSelection, whereClause);
			if (attributes != null)
			{
				for (Entry<String, Object> e : attributes.entrySet())
					infoSimple.setCtxAttribute(e.getKey(), e.getValue());
			}
			return infoSimple;
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
	}

}
