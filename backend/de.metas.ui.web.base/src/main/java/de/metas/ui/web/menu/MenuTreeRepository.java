/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.ui.web.menu;

import com.google.common.base.MoreObjects;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import de.metas.document.references.zoom_into.CustomizedWindowInfoMapRepository;
import de.metas.logging.LogManager;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.security.UserRolePermissionsKey;
import de.metas.ui.web.session.UserSession;
import de.metas.user.UserId;
import de.metas.user.api.IUserMenuFavoritesDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Repository
public class MenuTreeRepository implements MenuNodeFavoriteProvider
{
	private static final Logger logger = LogManager.getLogger(MenuTreeRepository.class);
	private final UserSession userSession;
	private final CustomizedWindowInfoMapRepository customizedWindowInfoMapRepository;

	private final LoadingCache<MenuTreeKey, MenuTree> menuTrees = CacheBuilder.newBuilder().expireAfterAccess(1, TimeUnit.HOURS).build(new CacheLoader<MenuTreeKey, MenuTree>()
	{
		@Override
		public MenuTree load(final MenuTreeKey key)
		{
			return MenuTreeLoader
					.newInstance()
					.setCustomizedWindowInfoMap(customizedWindowInfoMapRepository.get())
					.setUserRolePermissionsKey(key.getUserRolePermissionsKey())
					.setAD_Language(key.getAD_Language())
					.load();
		}
	});

	private final LoadingCache<UserId, UserMenuFavorites> userMenuFavoritesByUserId = CacheBuilder.newBuilder()
			.expireAfterAccess(1, TimeUnit.HOURS)
			.build(new CacheLoader<UserId, UserMenuFavorites>()
			{
				@Override
				public UserMenuFavorites load(final UserId adUserId)
				{
					return retrieveFavoriteMenuIds(adUserId);
				}
			});

	public MenuTreeRepository(
			@NonNull final UserSession userSession,
			@NonNull final CustomizedWindowInfoMapRepository customizedWindowInfoMapRepository)
	{
		this.userSession = userSession;
		this.customizedWindowInfoMapRepository = customizedWindowInfoMapRepository;
	}

	public MenuTree getMenuTree(@NonNull final UserRolePermissionsKey userRolePermissionsKey)
	{
		return getMenuTree(userRolePermissionsKey, userSession.getAD_Language());
	}

	public MenuTree getMenuTree(final UserRolePermissionsKey userRolePermissionsKey, final String adLanguage)
	{
		try
		{
			final MenuTreeKey key = new MenuTreeKey(userRolePermissionsKey, adLanguage);
			MenuTree menuTree = menuTrees.get(key);

			//
			// If menuTree's version is not the current one, try re-acquiring it.
			int retry = 3;
			final long currentVersion = Services.get(IUserRolePermissionsDAO.class).getCacheVersion();
			while (menuTree.getVersion() != currentVersion)
			{
				menuTrees.invalidate(key);
				menuTree = menuTrees.get(key);

				retry--;
				if (retry <= 0)
				{
					break;
				}
			}
			if (menuTree.getVersion() != currentVersion)
			{
				logger.warn("Could not acquire menu tree version {}. Returning what we got... \nmenuTree: {}\nkey={}", currentVersion, menuTree, key);
			}

			return menuTree;
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

		userMenuFavoritesByUserId.invalidateAll();
		userMenuFavoritesByUserId.cleanUp();
	}

	public UserMenuFavorites getUserMenuFavorites()
	{
		final UserId adUserId = userSession.getLoggedUserId();
		try
		{
			return userMenuFavoritesByUserId.get(adUserId);
		}
		catch (final ExecutionException ex)
		{
			throw AdempiereException.wrapIfNeeded(ex);
		}
	}

	private UserMenuFavorites retrieveFavoriteMenuIds(final UserId adUserId)
	{
		final List<Integer> adMenuIds = Services.get(IUserMenuFavoritesDAO.class).retrieveMenuIdsForUser(adUserId);

		return UserMenuFavorites.builder()
				.adUserId(adUserId)
				.addMenuIds(adMenuIds)
				.build();
	}

	public void setFavorite(final MenuNode menuNode, final boolean favorite)
	{
		final int adMenuId = menuNode.getAD_Menu_ID();

		final UserMenuFavorites userMenuFavorites = getUserMenuFavorites();
		final UserId adUserId = userMenuFavorites.getAdUserId();

		// Update in database first
		if (favorite)
		{
			Services.get(IUserMenuFavoritesDAO.class).add(adUserId, adMenuId);
		}
		else
		{
			Services.get(IUserMenuFavoritesDAO.class).remove(adUserId, adMenuId);
		}

		// Update model
		userMenuFavorites.setFavorite(adMenuId, favorite);
	}

	@Override
	public boolean isFavorite(final MenuNode menuNode)
	{
		return getUserMenuFavorites().isFavorite(menuNode);
	}

	private static final class MenuTreeKey
	{
		private final UserRolePermissionsKey userRolePermissionsKey;
		private final String adLanguage;

		private MenuTreeKey(final UserRolePermissionsKey userRolePermissionsKey, final String adLanguage)
		{
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

	private static final class UserMenuFavorites
	{
		private static Builder builder()
		{
			return new Builder();
		}

		private final UserId adUserId;
		private final Set<Integer> menuIds = ConcurrentHashMap.newKeySet();

		private UserMenuFavorites(final Builder builder)
		{
			adUserId = builder.adUserId;
			Check.assumeNotNull(adUserId, "Parameter adUserId is not null");

			menuIds.addAll(builder.menuIds);
		}

		public UserId getAdUserId()
		{
			return adUserId;
		}

		public boolean isFavorite(final MenuNode menuNode)
		{
			return menuIds.contains(menuNode.getAD_Menu_ID());
		}

		public void setFavorite(final int adMenuId, final boolean favorite)
		{
			if (favorite)
			{
				menuIds.add(adMenuId);
			}
			else
			{
				menuIds.remove(adMenuId);
			}
		}

		public static class Builder
		{
			private UserId adUserId;
			private final Set<Integer> menuIds = new HashSet<>();

			private Builder()
			{
			}

			public MenuTreeRepository.UserMenuFavorites build()
			{
				return new UserMenuFavorites(this);
			}

			public Builder adUserId(final UserId adUserId)
			{
				this.adUserId = adUserId;
				return this;
			}

			public Builder addMenuIds(final List<Integer> adMenuIds)
			{
				if (adMenuIds.isEmpty())
				{
					return this;
				}

				menuIds.addAll(adMenuIds);
				return this;
			}
		}

	}
}
