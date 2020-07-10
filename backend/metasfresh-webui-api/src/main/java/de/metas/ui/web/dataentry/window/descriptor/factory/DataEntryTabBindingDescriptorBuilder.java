package de.metas.ui.web.dataentry.window.descriptor.factory;

import de.metas.ui.web.window.descriptor.DocumentEntityDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.DocumentEntityDataBindingDescriptor.DocumentEntityDataBindingDescriptorBuilder;
import de.metas.ui.web.window.model.DocumentsRepository;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

/**
 * "Empty" descriptor builder. Data entry groups have no records of themselves. Only their sub groups have.
 */
public class DataEntryTabBindingDescriptorBuilder implements DocumentEntityDataBindingDescriptorBuilder
{
	public static final transient DataEntryTabBindingDescriptorBuilder instance = new DataEntryTabBindingDescriptorBuilder();

	private static final DocumentEntityDataBindingDescriptor dataBinding = new DocumentEntityDataBindingDescriptor()
	{
		@Override
		public DocumentsRepository getDocumentsRepository()
		{
			throw new UnsupportedOperationException("DocumentEntityDataBindingDescriptor " + this + " has no DocumentsRepository");
		}
	};

	@Override
	public DocumentEntityDataBindingDescriptor getOrBuild()
	{
		return dataBinding;
	}

}
