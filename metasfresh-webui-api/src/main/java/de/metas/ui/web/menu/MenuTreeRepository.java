package de.metas.ui.web.menu;

import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.adempiere.ad.security.UserRolePermissionsKey;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.base.MoreObjects;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import de.metas.ui.web.session.UserSession;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Repository
public class MenuTreeRepository
{
	@Autowired
	private UserSession userSession;

	private final LoadingCache<MenuTreeKey, MenuTree> menuTrees = CacheBuilder.newBuilder()
			.expireAfterAccess(1, TimeUnit.HOURS)
			.build(new CacheLoader<MenuTreeKey, MenuTree>()
			{

				@Override
				public MenuTree load(final MenuTreeKey key) throws Exception
				{
					return MenuTreeLoader.newInstance()
							.setCtx(Env.getCtx())
							.setUserRolePermissionsKey(key.getUserRolePermissionsKey())
							.setAD_Language(key.getAD_Language())
							.load();
				}
			});

	public MenuTree getUserSessionMenuTree()
	{
		final UserRolePermissionsKey userRolePermissionsKey = userSession.getUserRolePermissionsKey();
		final String adLanguage = userSession.getAD_Language();
		return getMenuTree(userRolePermissionsKey, adLanguage);
	}

	public MenuTree getMenuTree(final UserRolePermissionsKey userRolePermissionsKey, final String adLanguage)
	{
		try
		{
			final MenuTreeKey key = new MenuTreeKey(userRolePermissionsKey, adLanguage);
			return menuTrees.get(key);
		}
		catch (final ExecutionException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	public void cacheReset()
	{
		menuTrees.invalidateAll();
		menuTrees.cleanUp();
	}

	private static final class MenuTreeKey
	{
		private final UserRolePermissionsKey userRolePermissionsKey;
		private final String adLanguage;

		private MenuTreeKey(final UserRolePermissionsKey userRolePermissionsKey, final String adLanguage)
		{
			super();
			this.userRolePermissionsKey = userRolePermissionsKey;
			this.adLanguage = adLanguage;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("adLanguage", adLanguage)
					.addValue(userRolePermissionsKey)
					.toString();
		}

		@Override
		public int hashCode()
		{
			return Objects.hash(userRolePermissionsKey, adLanguage);
		}

		@Override
		public boolean equals(final Object obj)
		{
			if (this == obj)
			{
				return true;
			}
			if (obj instanceof MenuTreeKey)
			{
				final MenuTreeKey other = (MenuTreeKey)obj;
				return Objects.equals(userRolePermissionsKey, other.userRolePermissionsKey)
						&& Objects.equals(adLanguage, other.adLanguage);
			}
			else
			{
				return false;
			}
		}

		public UserRolePermissionsKey getUserRolePermissionsKey()
		{
			return userRolePermissionsKey;
		}

		public String getAD_Language()
		{
			return adLanguage;
		}
	}
}
