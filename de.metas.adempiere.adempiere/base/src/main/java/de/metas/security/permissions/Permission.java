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


/**
 * Defines a particular access to a resource.
 * 
 * e.g. read write access to window 12345.
 * 
 * @author tsa
 *
 */
public interface Permission
{
	/**
	 * @return resource for whom the permission is defined; never returns <code>null</code>.
	 */
	Resource getResource();

	/**
	 * Creates a new {@link Permission} by merging given access into this access.
	 * 
	 * @param accessFrom
	 * @return merged access
	 */
	Permission mergeWith(Permission accessFrom);

	/**
	 * Checks if this permission has the given access.
	 * 
	 * e.g. check if the table permission for a particular table resouce has the access of type "Write".
	 * 
	 * @param access
	 * @return true if required access is granted.
	 */
	boolean hasAccess(Access access);
}
