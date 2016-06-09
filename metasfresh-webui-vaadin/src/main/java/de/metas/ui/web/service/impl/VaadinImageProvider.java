package de.metas.ui.web.service.impl;

import com.vaadin.server.Resource;

import de.metas.ui.web.service.IImageProvider;
import de.metas.ui.web.vaadin.theme.Theme;
import de.metas.ui.web.window.shared.ImageResource;
import de.metas.ui.web.window.shared.ImageResource.ResourceType;

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
	@Override
	public ImageResource getIconSmall(final String name)
	{
		return new ImageResource(name, ResourceType.IconSmall);
	}

	@Override
	public ImageResource getImageResourceForNameWithoutExt(final String fileNameWithoutExtension)
	{
		return new ImageResource(fileNameWithoutExtension, ResourceType.Image);
	}

	public static Resource getVaadinResource(final ImageResource imageResource)
	{
		if (imageResource == null)
		{
			return null;
		}

		final ResourceType resourceType = imageResource.getResourceType();
		if (resourceType == ResourceType.IconSmall)
		{
			return Theme.getIconSmall(imageResource.getResourceName());
		}
		else if (resourceType == ResourceType.Image)
		{
			return Theme.getImageResourceForNameWithoutExt(imageResource.getResourceName());
		}
		else
		{
			throw new IllegalStateException("Unknown resource type: " + resourceType);
		}
	}

}
