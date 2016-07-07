package de.metas.procurement.webui.util;

import java.text.DateFormat;
import java.util.Locale;

import com.vaadin.ui.UI;

/*
 * #%L
 * metasfresh-procurement-webui
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@SuppressWarnings("serial")
public class StringToDateConverter extends com.vaadin.data.util.converter.StringToDateConverter
{
	public static final transient StringToDateConverter instance = new StringToDateConverter();

	private StringToDateConverter()
	{
		super();
	}

	@Override
	protected DateFormat getFormat(Locale locale)
	{
		if (locale == null)
		{
			final UI ui = UI.getCurrent();
			if (ui != null)
			{
				locale = ui.getLocale();
			}
		}
		if (locale == null)
		{
			locale = Locale.getDefault();
		}

		final DateFormat f = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
		f.setLenient(false);
		return f;
	}
}
