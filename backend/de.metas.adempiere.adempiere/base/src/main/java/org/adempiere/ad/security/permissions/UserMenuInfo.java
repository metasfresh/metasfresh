package org.adempiere.ad.security.permissions;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

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

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false)
public class UserMenuInfo
{

	public static final UserMenuInfo of(final int adTreeId, final int rootMenuId)
	{
		if (adTreeId <= 0)
		{
			return NONE;
		}
		return new UserMenuInfo(adTreeId, rootMenuId);
	}

	public static final UserMenuInfo NONE = new UserMenuInfo(-1, -1);

	private final int adTreeId;
	private final int rootMenuId;

	public int getAD_Tree_ID()
	{
		return adTreeId;
	}

	public int getRoot_Menu_ID()
	{
		return rootMenuId;
	}
}
