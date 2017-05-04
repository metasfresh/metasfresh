package org.adempiere.ad.security.permissions;

import java.util.Objects;

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


import javax.annotation.concurrent.Immutable;

import org.adempiere.ad.security.permissions.PermissionsBuilder.CollisionPolicy;
import org.adempiere.util.Check;

@Immutable
public final class ElementPermissions extends AbstractPermissions<ElementPermission>
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final String elementTableName;

	public ElementPermissions(final Builder builder)
	{
		super(builder);
		this.elementTableName = builder.getElementTableName();
	}

	public Builder asNewBuilder()
	{
		final Builder builder = builder();
		builder.setElementTableName(elementTableName);
		builder.addPermissions(this, CollisionPolicy.Override);
		return builder;
	}
	
	/** @return element permissions; never return null */
	public ElementPermission getPermission(final int elementId)
	{
		final ElementResource resource = ElementResource.of(elementTableName, elementId);
		final ElementPermission permission = getPermissionOrDefault(resource);
		return permission != null ? permission : ElementPermission.none(resource);
	}

	/**
	 * Gets {@link Access#READ}/{@link Access#WRITE} access of a given element, as Boolean.
	 * 
	 * @param elementId
	 * @return <ul>
	 *         <li> <code>true</code> if read-write permission
	 *         <li> <code>false</code> if read-only permission
	 *         <li> <code>null</code> if there is no such permission at all
	 *         </ul>
	 */
	public Boolean getReadWritePermission(final int elementId)
	{
		final ElementResource resource = ElementResource.of(elementTableName, elementId);
		final ElementPermission permission = getPermissionOrDefault(resource);
		if (permission == null)
		{
			return null;
		}

		if (permission.hasAccess(Access.WRITE))
		{
			return true;
		}
		else if (permission.hasAccess(Access.READ))
		{
			return false;
		}
		else
		{
			return null;
		}
	}

	public static class Builder extends PermissionsBuilder<ElementPermission, ElementPermissions>
	{
		private String elementTableName;

		@Override
		protected ElementPermissions createPermissionsInstance()
		{
			return new ElementPermissions(this);
		}

		public Builder setElementTableName(String elementTableName)
		{
			this.elementTableName = elementTableName;
			return this;
		}

		public String getElementTableName()
		{
			Check.assumeNotEmpty(elementTableName, "elementTableName not empty");
			return elementTableName;
		}

		@Override
		protected void assertValidPermissionToAdd(final ElementPermission permission)
		{
			final String elementTableName = getElementTableName();
			if (!Objects.equals(elementTableName, permission.getResource().getElementTableName()))
			{
				throw new IllegalArgumentException("Permission to add " + permission + " is not matching element table name '" + elementTableName + "'");
			}
		}

	}

}
