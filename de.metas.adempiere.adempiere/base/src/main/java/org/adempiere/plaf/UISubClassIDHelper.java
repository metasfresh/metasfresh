package org.adempiere.plaf;

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


import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.border.Border;

import com.google.common.base.MoreObjects;

import de.metas.util.Check;

/**
 * Helper class to handle components which have UISubClassID support (i.e. implement {@link IUISubClassIDAware}).
 *
 * @author tsa
 *
 */
public final class UISubClassIDHelper
{
	public static UISubClassIDHelper ofComponent(final JComponent comp)
	{
		if (comp instanceof IUISubClassIDAware)
		{
			final IUISubClassIDAware uiSubClassIDAware = (IUISubClassIDAware)comp;
			final String uiSubClassID = uiSubClassIDAware.getUISubClassID();
			return ofUISubClassID(uiSubClassID);
		}

		return DEFAULT;
	}

	public static UISubClassIDHelper ofUISubClassID(final String uiSubClassID)
	{
		if (Check.isEmpty(uiSubClassID, true))
		{
			return DEFAULT;
		}
		return new UISubClassIDHelper(uiSubClassID);
	}

	private static final UISubClassIDHelper DEFAULT = new UISubClassIDHelper(null);

	private final String uiSubClassID;

	private UISubClassIDHelper(final String uiSubClassID)
	{
		super();
		this.uiSubClassID = uiSubClassID;
	}
	
	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("uiSubClassID", uiSubClassID)
				.toString();
	}

	public final Color getColor(final String name, final Color defaultColor)
	{
		Color color = UIManager.getColor(buildUIDefaultsKey(name));
		if (color == null && uiSubClassID != null)
		{
			color = UIManager.getColor(name);
		}
		if (color == null)
		{
			color = defaultColor;
		}
		return color;
	}

	public final Color getColor(final String name)
	{
		final Color defaultColor = null;
		return getColor(name, defaultColor);
	}

	public final Border getBorder(final String name, final Border defaultBorder)
	{
		Border border = UIManager.getBorder(buildUIDefaultsKey(name));
		if (border == null && uiSubClassID != null)
		{
			border = UIManager.getBorder(name);
		}
		if (border == null)
		{
			border = defaultBorder;
		}
		return border;
	}

	private final String buildUIDefaultsKey(final String name)
	{
		return buildUIDefaultsKey(uiSubClassID, name);
	}

	private static final String buildUIDefaultsKey(final String uiSubClassID, final String name)
	{
		if (uiSubClassID == null)
		{
			return name;
		}
		else
		{
			return uiSubClassID + "." + name;
		}
	}

	public boolean getBoolean(final String name, final boolean defaultValue)
	{
		Object value = UIManager.getDefaults().get(buildUIDefaultsKey(name));
		if (value instanceof Boolean)
		{
			return (boolean)value;
		}

		if (uiSubClassID != null)
		{
			value = UIManager.getDefaults().get(name);
			if (value instanceof Boolean)
			{
				return (boolean)value;
			}
		}

		return defaultValue;
	}

	/**
	 * Convert all keys from given UI defaults key-value list by applying the <code>uiSubClassID</code> prefix to them.
	 *
	 * @param uiSubClassID
	 * @param keyValueList
	 * @return converted <code>keyValueList</code>
	 */
	public static final Object[] applyUISubClassID(final String uiSubClassID, final Object[] keyValueList)
	{
		if (keyValueList == null || keyValueList.length <= 0)
		{
			return keyValueList;
		}

		Check.assumeNotEmpty(uiSubClassID, "uiSubClassID not empty");

		final Object[] keyValueListConverted = new Object[keyValueList.length];
		for (int i = 0, max = keyValueList.length; i < max; i += 2)
		{
			final Object keyOrig = keyValueList[i];
			final Object value = keyValueList[i + 1];

			final Object key;
			if (keyOrig instanceof String)
			{
				key = buildUIDefaultsKey(uiSubClassID, (String)keyOrig);
			}
			else
			{
				key = keyOrig;
			}

			keyValueListConverted[i] = key;
			keyValueListConverted[i + 1] = value;
		}

		return keyValueListConverted;
	}
}
