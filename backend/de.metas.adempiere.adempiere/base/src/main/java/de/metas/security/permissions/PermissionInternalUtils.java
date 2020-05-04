package de.metas.security.permissions;

import org.adempiere.exceptions.AdempiereException;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

final class PermissionInternalUtils
{
	/**
	 * Checks if given permission is compatible with our permission and returns the given permission casted to our type.
	 * 
	 * @param from
	 * @return permission, casted to our type; never returns null
	 * @throws AdempiereException if the permision is not compatible
	 */
	public static final <PermissionType extends Permission> PermissionType checkCompatibleAndCastToTarget(final Permission target, final Permission from)
	{
		if (!sameResource(target, from))
		{
			throw new AdempiereException("Incompatible permission to be merged: " + target + ", " + from);
		}

		if (target.getClass() != from.getClass())
		{
			throw new AdempiereException("Incompatible permission to be merged: " + target + ", " + from
					+ " Required class: " + target.getClass()
					+ " Actual class: " + from.getClass());
		}

		@SuppressWarnings("unchecked")
		final PermissionType permissionCasted = (PermissionType)from;
		return permissionCasted;
	}

	private static final boolean sameResource(final Permission permission1, final Permission permission2)
	{
		return permission1.getResource().equals(permission2.getResource());
	}
}
