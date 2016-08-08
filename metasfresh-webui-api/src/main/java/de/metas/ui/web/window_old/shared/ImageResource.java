package de.metas.ui.web.window_old.shared;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

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

@SuppressWarnings("serial")
public final class ImageResource implements Serializable
{
	public static enum ResourceType
	{
		IconSmall, Image,
	}

	@JsonProperty("n")
	private final String resourceName;
	@JsonProperty("t")
	private final ImageResource.ResourceType resourceType;

	public ImageResource(@JsonProperty("n") final String resourceName, @JsonProperty("t") final ImageResource.ResourceType resourceType)
	{
		super();
		this.resourceName = resourceName;
		this.resourceType = resourceType;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("resourceName", resourceName)
				.add("resourceType", resourceType)
				.toString();
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(resourceName, resourceType);
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}

		if (!(obj instanceof ImageResource))
		{
			return false;
		}

		final ImageResource other = (ImageResource)obj;
		return Objects.equals(resourceName, other.resourceName)
				&& Objects.equals(resourceType, other.resourceType);
	}

	public String getResourceName()
	{
		return resourceName;
	}

	public ImageResource.ResourceType getResourceType()
	{
		return resourceType;
	}

}