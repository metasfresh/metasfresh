package de.metas.ui.web.menu;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.adempiere.ad.security.UserRolePermissionsKey;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;
import org.springframework.stereotype.Repository;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

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

@Repository
public class MenuTreeRepository
{
	private final LoadingCache<UserRolePermissionsKey, MenuTree> menuTrees = CacheBuilder.newBuilder()
			.expireAfterAccess(1, TimeUnit.HOURS)
			.build(new CacheLoader<UserRolePermissionsKey, MenuTree>()
			{

				@Override
				public MenuTree load(final UserRolePermissionsKey userRolePermissionsKey) throws Exception
				{
					return MenuTreeLoader.newInstance()
							.setCtx(Env.getCtx())
							.setUserRolePermissionsKey(userRolePermissionsKey)
							.load();
				}
			});

	public MenuTree getMenuTree(final UserRolePermissionsKey userRolePermissionsKey)
	{
		try
		{
			return menuTrees.get(userRolePermissionsKey);
		}
		catch (final ExecutionException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	public void cacheReset()
	{
		menuTrees.invalidateAll();
	}
}
