package de.metas.security.permissions;

import java.util.Properties;

import org.adempiere.ad.persistence.EntityTypesCache;
import org.compiere.util.Env;

import de.metas.security.IUserRolePermissions;
import lombok.NonNull;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Constraint used to determine if the UI elements (windows, tabs, fields, menu, processes etc) shall be displayed in UI.
 * 
 * @author metas-dev <dev@metasfresh.com>
 * Task http://dewiki908/mediawiki/index.php/09474_make_entity_type_disableable_and_hidable_for_certains_roles_%28107519600505%29
 */
public final class UIDisplayedEntityTypes extends Constraint
{
	/**
	 * @param showAllEntityTypes true if we shall display all UI elements, even if a entity type was configured to not show them
	 * @return new constraint instance
	 */
	public static UIDisplayedEntityTypes of(final boolean showAllEntityTypes)
	{
		return new UIDisplayedEntityTypes(showAllEntityTypes);
	}

	/**
	 * Convenient method to check if role allows a given entity type to be displayed in UI.
	 * 
	 * @param entityType entity type or <code>null</code>
	 * @return
	 *         <ul>
	 *         <li>true/false if current role has {@link UIDisplayedEntityTypes} constraint and the constraint allows or disallow it (see {@link #isDisplayedInUI(String)})
	 *         <li>true if current role does not have the {@link UIDisplayedEntityTypes} constraint.
	 *         </ul>
	 */
	public static boolean isEntityTypeDisplayedInUIOrTrueIfNull(
			@NonNull final Properties ctx,
			@NonNull final String entityType)
	{
		// Get the constraint of current logged in role.
		final IUserRolePermissions role = Env.getUserRolePermissionsOrNull(ctx);
		if(role == null)
		{
			// cannot extract the role from given context => consider the entity as displayed
			return true;
		}
		
		return isEntityTypeDisplayedInUIOrTrueIfNull(role, entityType);
	}

	public static boolean isEntityTypeDisplayedInUIOrTrueIfNull(
			@NonNull final IUserRolePermissions role,
			@NonNull final String entityType)
	{
		final UIDisplayedEntityTypes constraint = role
				.getConstraint(UIDisplayedEntityTypes.class)
				.orElse(null);

		// If no constraint => return default (true)
		if (constraint == null)
		{
			return true;
		}

		// Ask the constraint
		return constraint.isDisplayedInUI(entityType);
	}

	private final boolean showAllEntityTypes;

	private UIDisplayedEntityTypes(final boolean showAllEntityTypes)
	{
		super();
		this.showAllEntityTypes = showAllEntityTypes;
	}

	@Override
	public boolean isInheritable()
	{
		return false;
	}

	/**
	 * @return true if UI elements for given entity type shall be displayed
	 */
	public boolean isDisplayedInUI(final String entityType)
	{
		if (showAllEntityTypes)
		{
			return EntityTypesCache.instance.isActive(entityType);
		}
		else
		{
			return EntityTypesCache.instance.isDisplayedInUI(entityType);
		}
	}
}
