package de.metas.ui.web.dataentry.window.descriptor.factory;

import org.springframework.stereotype.Service;

import de.metas.dataentry.data.DataEntryRecordRepository;
import de.metas.ui.web.window.descriptor.DocumentEntityDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.DocumentEntityDataBindingDescriptor.DocumentEntityDataBindingDescriptorBuilder;
import de.metas.ui.web.window.model.DocumentsRepository;
import lombok.Getter;
import lombok.NonNull;

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

@Service
public class DataEntrySubTabBindingDescriptorBuilder implements DocumentEntityDataBindingDescriptorBuilder
{
	private final DocumentEntityDataBindingDescriptor dataBinding;

	@Getter
	private final DataEntryWebuiTools dataEntryWebuiTools;

	public DataEntrySubTabBindingDescriptorBuilder(
			@NonNull final DataEntryRecordRepository dataEntryRecordRepository,
			@NonNull final DataEntryWebuiTools dataEntryWebuiTools)
	{
		this.dataEntryWebuiTools = dataEntryWebuiTools;

		final DataEntrySubTabBindingRepository dataEntrySubGroupBindingRepository //
				= new DataEntrySubTabBindingRepository(dataEntryRecordRepository, dataEntryWebuiTools);

		this.dataBinding = new DocumentEntityDataBindingDescriptor()
		{
			@Override
			public DocumentsRepository getDocumentsRepository()
			{
				return dataEntrySubGroupBindingRepository;
			}
		};

	}

	@Override
	public DocumentEntityDataBindingDescriptor getOrBuild()
	{
		return dataBinding;
	}

}
