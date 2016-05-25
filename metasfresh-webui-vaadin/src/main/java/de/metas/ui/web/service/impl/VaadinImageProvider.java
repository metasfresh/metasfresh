package de.metas.ui.web.service.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vaadin.server.Resource;

import de.metas.ui.web.service.IImageProvider;
import de.metas.ui.web.service.impl.VaadinImageProvider.VaadinImageResource.ResourceType;
import de.metas.ui.web.vaadin.theme.Theme;

/*
 * #%L
 * metasfresh-webui-vaadin
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

public class VaadinImageProvider implements IImageProvider
{
	public static final class VaadinImageResource implements IImageResource
	{
		public static final Resource getResource(final IImageResource imageResource)
		{
			if (imageResource == null)
			{
				return null;
			}

			final VaadinImageResource vaadinImageResource = (VaadinImageResource)imageResource;
			return vaadinImageResource.getResource();
		}
		
		public static enum ResourceType
		{
			IconSmall,
			Image,
		}

		@JsonProperty("n")
		private final String resourceName;
		@JsonProperty("t")
		private final ResourceType resourceType;
		
		public VaadinImageResource(@JsonProperty("n") final String resourceName, @JsonProperty("t") final ResourceType resourceType)
		{
			super();
			this.resourceName = resourceName;
			this.resourceType = resourceType;
		}
		
		public String getResourceName()
		{
			return resourceName;
		}
		
		public ResourceType getResourceType()
		{
			return resourceType;
		}

		protected Resource getResource()
		{
			if (resourceType == ResourceType.IconSmall)
			{
				return Theme.getIconSmall(getResourceName());
			}
			else if (resourceType == ResourceType.Image)
			{
				return Theme.getImageResourceForNameWithoutExt(getResourceName());
			}
			else
			{
				throw new IllegalStateException("Unknown resource type: "+resourceType);
			}
		}
	}

	@Override
	public IImageResource getIconSmall(final String name)
	{
		return new VaadinImageResource(name, ResourceType.IconSmall);
	}

	@Override
	public IImageResource getImageResourceForNameWithoutExt(final String fileNameWithoutExtension)
	{
		return new VaadinImageResource(fileNameWithoutExtension, ResourceType.Image);
	}

}
