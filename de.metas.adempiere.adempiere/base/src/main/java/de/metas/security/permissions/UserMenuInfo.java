package de.metas.security.permissions;

import org.adempiere.model.tree.AdTreeId;

import de.metas.menu.AdMenuId;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Value
public final class UserMenuInfo
{
	public static final UserMenuInfo of(final AdTreeId adTreeId, final AdMenuId rootMenuId)
	{
		if (adTreeId == null)
		{
			return NONE;
		}
		else if (rootMenuId == null)
		{
			return ofAdTreeId(adTreeId);
		}
		else
		{
			return new UserMenuInfo(adTreeId, rootMenuId);
		}
	}

	public static final UserMenuInfo ofAdTreeId(@NonNull final AdTreeId adTreeId)
	{
		if (adTreeId.equals(DEFAULT_MENU.adTreeId))
		{
			return DEFAULT_MENU;
		}
		else
		{
			final AdMenuId rootMenuId = null;
			return new UserMenuInfo(adTreeId, rootMenuId);
		}
	}

	public static final UserMenuInfo DEFAULT_MENU = new UserMenuInfo(AdTreeId.DEFAULT_MENU_TREE_ID, (AdMenuId)null);
	public static final UserMenuInfo NONE = new UserMenuInfo((AdTreeId)null, (AdMenuId)null);

	AdTreeId adTreeId;
	AdMenuId rootMenuId;

	private UserMenuInfo(final AdTreeId adTreeId, final AdMenuId rootMenuId)
	{
		this.adTreeId = adTreeId;
		this.rootMenuId = rootMenuId;
	}

}
