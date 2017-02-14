package de.metas.ui.web.quickinput;

import org.compiere.model.I_C_OrderLine;
import org.compiere.util.CCache;
import org.compiere.util.Util.ArrayKey;
import org.springframework.stereotype.Service;

import de.metas.ui.web.quickinput.inout.EmptiesQuickInputDescriptorFactory;
import de.metas.ui.web.quickinput.orderline.OrderLineQuickInputDescriptorFactory;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Service
public class QuickInputDescriptorFactoryService
{
	private final CCache<ArrayKey, QuickInputDescriptor> descriptors = CCache.newCache("QuickInputDescriptors", 10, 0);

	public QuickInputDescriptor getQuickInputEntityDescriptor(final DocumentEntityDescriptor includedDocumentDescriptor)
	{
		final DocumentType documentType = includedDocumentDescriptor.getDocumentType();
		final DocumentId documentTypeId = includedDocumentDescriptor.getDocumentTypeId();
		final String tableName = includedDocumentDescriptor.getTableNameOrNull();
		final DetailId detailId = includedDocumentDescriptor.getDetailId();
		return getQuickInputEntityDescriptor(documentType, documentTypeId, tableName, detailId);
	}

	public QuickInputDescriptor getQuickInputEntityDescriptor(final DocumentType documentType, final DocumentId documentTypeId, final String tableName, final DetailId detailId)
	{
		final ArrayKey key = ArrayKey.of(documentType, documentTypeId, tableName, detailId);
		return descriptors.getOrLoad(key, () -> createQuickInputEntityDescriptor(documentType, documentTypeId, tableName, detailId));
	}

	private QuickInputDescriptor createQuickInputEntityDescriptor(final DocumentType documentType, final DocumentId documentTypeId, final String tableName, final DetailId detailId)
	{
		final IQuickInputDescriptorFactory quickInputDescriptorFactory = getQuickInputDescriptorFactory(documentType, documentTypeId, tableName);
		if (quickInputDescriptorFactory == null)
		{
			return null;
		}

		final QuickInputDescriptor quickInputDescriptor = quickInputDescriptorFactory.createQuickInputEntityDescriptor(documentType, documentTypeId, detailId);
		return quickInputDescriptor;
	}

	private IQuickInputDescriptorFactory getQuickInputDescriptorFactory(final DocumentType documentType, final DocumentId documentTypeId, final String tableName)
	{
		// FIXME uber HARDCODED

		if (I_C_OrderLine.Table_Name.equals(tableName))
		{
			return OrderLineQuickInputDescriptorFactory.instance;
		}
		else if (EmptiesQuickInputDescriptorFactory.instance.matches(documentType, documentTypeId, tableName))
		{
			return EmptiesQuickInputDescriptorFactory.instance;
		}
		else
		{
			return null;
		}

	}
}
