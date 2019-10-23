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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Collection;

import com.google.common.base.Optional;

/**
 * Collection of {@link Permission}s.
 * 
 * Implementations of this interface could be window permissions, process permissions etc.
 * 
 * @author tsa
 *
 * @param <PermissionType>
 */
public interface Permissions<PermissionType extends Permission>
{
	/** @return how many {@link Permission}s do we have */
	int size();

	Collection<PermissionType> getPermissionsList();

	/**
	 * Checks if requested access is granted for resource.
	 * 
	 * To find the resource's permission, {@link #getPermissionOrDefault(Resource)} will be used.
	 * 
	 * @param resource
	 * @param access requested access
	 * @return true if access is granted.
	 */
	boolean hasAccess(Resource resource, Access access);

	/**
	 * Checks if given permision is contained in our permissions list.
	 * 
	 * @param permission
	 * @return true if we already have that permission.
	 */
	boolean hasPermission(Permission permission);

	/**
	 * Gets the actual permision of given resource, if any.
	 * 
	 * @param resource
	 * @return actual resource's permision or absent.
	 */
	Optional<PermissionType> getPermissionIfExists(Resource resource);

	/**
	 * Gets the permission of given resource.
	 * 
	 * It will check and return (in this order):
	 * <ul>
	 * <li>actual resource permision: permision for given resource, if any
	 * <li>default permision: permision of {@link #noPermission()}'s resource, if any
	 * <li>no permision: {@link #noPermission()}, if any
	 * <li><code>null</code>, in case the {@link #noPermission()} is returning <code>null</code>
	 * </ul>
	 * 
	 * @param resource
	 * @return resource permission or default permission or <code>null</code>
	 */
	PermissionType getPermissionOrDefault(Resource resource);

}
