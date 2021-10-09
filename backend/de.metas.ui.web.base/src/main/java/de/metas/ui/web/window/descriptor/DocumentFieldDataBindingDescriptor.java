package de.metas.ui.web.window.descriptor;

/*
 * #%L
 * metasfresh-webui-api
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public interface DocumentFieldDataBindingDescriptor
{
	String getColumnName();

	/**
	 * @return true if mandatory in underlying database/repository
	 */
	boolean isMandatory();

	default <T extends DocumentFieldDataBindingDescriptor> T cast(final Class<T> bindingClass)
	{
		@SuppressWarnings("unchecked")
		final T thisCasted = (T)this;
		return thisCasted;
	}

	/**
	 * @return true if this field has ORDER BY instructions
	 */
	default boolean isDefaultOrderBy()
	{
		return false;
	}

	default int getDefaultOrderByPriority()
	{
		return 0;
	}

	default boolean isDefaultOrderByAscending()
	{
		return true;
	}
}
