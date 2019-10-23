package de.metas.security.permissions;

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

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.ObjectUtils;

public abstract class AbstractPermission implements Permission
{
	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	/**
	 * Checks if given permission is compatible with our permission and returns the given permission casted to our type.
	 * 
	 * @param permission
	 * @return permission, casted to our type; never returns null
	 * @throws AdempiereException if the permision is not compatible
	 */
	protected final <PermissionType extends Permission> PermissionType checkCompatibleAndCast(final Permission permission)
	{
		return PermissionInternalUtils.checkCompatibleAndCastToTarget(this, permission);
	}
}
