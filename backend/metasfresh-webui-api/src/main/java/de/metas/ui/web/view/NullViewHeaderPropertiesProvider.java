package de.metas.ui.web.view;

import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

public final class NullViewHeaderPropertiesProvider implements ViewHeaderPropertiesProvider
{
	public static final transient NullViewHeaderPropertiesProvider instance = new NullViewHeaderPropertiesProvider();

	private NullViewHeaderPropertiesProvider()
	{
	}

	@Override
	public String getAppliesOnlyToTableName()
	{
		//noinspection ConstantConditions
		throw null;
	}

	@Override
	public @NonNull ViewHeaderProperties computeHeaderProperties(@NonNull final IView view)
	{
		return ViewHeaderProperties.EMPTY;
	}
}
