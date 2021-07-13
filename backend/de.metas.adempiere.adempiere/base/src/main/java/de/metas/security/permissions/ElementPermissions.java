package de.metas.security.permissions;

import de.metas.security.permissions.PermissionsBuilder.CollisionPolicy;
import de.metas.util.Check;

import javax.annotation.concurrent.Immutable;
import java.util.Objects;

@Immutable
public final class ElementPermissions extends AbstractPermissions<ElementPermission>
{
	public static Builder builder()
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

	/**
	 * @return element permissions; never return null
	 */
	public ElementPermission getPermission(final int elementId)
	{
		final ElementResource resource = elementResource(elementId);
		final ElementPermission permission = getPermissionOrDefault(resource);
		return permission != null ? permission : ElementPermission.none(resource);
	}

	public ElementResource elementResource(final int elementId)
	{
		return ElementResource.of(elementTableName, elementId);
	}

	public ElementPermission noPermissions(final int elementId)
	{
		return ElementPermission.none(elementResource(elementId));
	}

	public static class Builder extends PermissionsBuilder<ElementPermission, ElementPermissions>
	{
		private String elementTableName;

		@Override
		protected ElementPermissions createPermissionsInstance()
		{
			return new ElementPermissions(this);
		}

		public Builder setElementTableName(final String elementTableName)
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
