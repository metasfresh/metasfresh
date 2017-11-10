package de.metas.ui.web.handlingunits;

import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.model.X_M_HU;
import lombok.NonNull;
import lombok.Singular;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

@lombok.Builder(toBuilder = true)
@lombok.Value
public final class HUEditorRowFilter
{
	@NonNull
	private final Select select;

	@Singular
	private final ImmutableSet<String> onlyHUStatuses;

	public enum Select
	{
		ONLY_TOPLEVEL, ALL
	}

	public static final HUEditorRowFilter ALL = builder().select(Select.ALL).build();

	public static final HUEditorRowFilter select(Select select)
	{
		return builder().select(select).build();
	}

	public static final class HUEditorRowFilterBuilder
	{
		public HUEditorRowFilterBuilder onlyActiveHUs()
		{
			onlyHUStatus(X_M_HU.HUSTATUS_Active);
			return this;
		}
	}
}
