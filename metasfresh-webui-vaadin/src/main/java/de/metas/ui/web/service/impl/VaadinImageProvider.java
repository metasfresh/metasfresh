package de.metas.ui.web.service.impl;

import com.vaadin.server.Resource;

import de.metas.ui.web.service.IImageProvider;
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
		public static final VaadinImageResource of(final Resource resource)
		{
			if (resource == null)
			{
				return null;
			}
			return new VaadinImageResource(resource);
		}

		public static final Resource getResource(IImageResource imageResource)
		{
			if (imageResource == null)
			{
				return null;
			}

			final VaadinImageResource vaadinImageResource = (VaadinImageResource)imageResource;
			return vaadinImageResource.getResource();
		}

		private final Resource resource;

		private VaadinImageResource(Resource resource)
		{
			super();
			this.resource = resource;
		}

		public Resource getResource()
		{
			return resource;
		}
	}

	@Override
	public IImageResource getIconSmall(String name)
	{
		final Resource resource = Theme.getIconSmall(name);
		return VaadinImageResource.of(resource);
	}

	@Override
	public IImageResource getImageResourceForNameWithoutExt(String fileNameWithoutExtension)
	{
		final Resource resource = Theme.getImageResourceForNameWithoutExt(fileNameWithoutExtension);
		return VaadinImageResource.of(resource);
	}

}
