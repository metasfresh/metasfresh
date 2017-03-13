package de.metas.ui.web.window.descriptor.factory.standard;

import org.compiere.model.I_AD_Window;
import org.compiere.util.CCache;
import org.springframework.stereotype.Service;

import de.metas.ui.web.window.descriptor.DocumentDescriptor;
import de.metas.ui.web.window.descriptor.factory.DocumentDescriptorFactory;
import de.metas.ui.web.window.exceptions.DocumentLayoutBuildException;

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

@Service
public class DefaultDocumentDescriptorFactory implements DocumentDescriptorFactory
{
	private final CCache<Integer, DocumentDescriptor> documentDescriptorsByWindowId = new CCache<>(I_AD_Window.Table_Name + "#DocumentDescriptor", 50);

	/* package */ DefaultDocumentDescriptorFactory()
	{
		super();
	}

	@Override
	public DocumentDescriptor getDocumentDescriptor(final int AD_Window_ID)
	{
		try
		{
			return documentDescriptorsByWindowId.getOrLoad(AD_Window_ID, () -> new DefaultDocumentDescriptorLoader(AD_Window_ID).load());
		}
		catch (final Exception e)
		{
			throw DocumentLayoutBuildException.wrapIfNeeded(e);
		}
	}
}
