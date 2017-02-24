package de.metas.ui.web.window.descriptor.factory;

import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentDescriptor;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public interface DocumentDescriptorFactory
{
	DocumentDescriptor getDocumentDescriptor(int AD_Window_ID) throws DocumentLayoutBuildException;

	default DocumentEntityDescriptor getDocumentEntityDescriptor(final int AD_Window_ID)
	{
		return getDocumentDescriptor(AD_Window_ID).getEntityDescriptor();
	}

	default String getTableNameOrNull(final int AD_Window_ID)
	{
		return getDocumentEntityDescriptor(AD_Window_ID).getTableName();
	}

	default String getTableNameOrNull(final int AD_Window_ID, final DetailId detailId)
	{
		final DocumentEntityDescriptor descriptor = getDocumentEntityDescriptor(AD_Window_ID);
		if (detailId == null)
		{
			return descriptor.getTableName();
		}
		else
		{
			return descriptor.getIncludedEntityByDetailId(detailId).getTableName();
		}
	}
}
