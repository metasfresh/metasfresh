package de.metas.impexp.processing.impl;

import java.util.concurrent.ConcurrentHashMap;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.reflect.ClassReference;

import de.metas.impexp.processing.IImportProcess;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.business
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

@ToString
final class ImportProcessDescriptorsMap
{
	private final ConcurrentHashMap<String, ImportProcessDescriptor> descriptorsByImportTableName = new ConcurrentHashMap<>();

	public <ImportRecordType> ImportProcessDescriptor register(
			@NonNull final Class<ImportRecordType> modelImportClass,
			@NonNull final Class<? extends IImportProcess<ImportRecordType>> importProcessClass)
	{
		final String importTableName = InterfaceWrapperHelper.getTableName(modelImportClass);

		final ImportProcessDescriptor descriptor = ImportProcessDescriptor.builder()
				.modelImportClass(ClassReference.of(modelImportClass))
				.importTableName(importTableName)
				.importProcessClass(ClassReference.of(importProcessClass))
				.build();

		descriptorsByImportTableName.put(importTableName, descriptor);

		return descriptor;
	}

	public Class<? extends IImportProcess<?>> getImportProcessClassByModelImportClassOrNull(@NonNull final Class<?> modelImportClass)
	{
		final String importTableName = InterfaceWrapperHelper.getTableName(modelImportClass);
		return getImportProcessClassByImportTableNameOrNull(importTableName);
	}

	public Class<? extends IImportProcess<?>> getImportProcessClassByImportTableNameOrNull(@NonNull final String importTableName)
	{
		final ImportProcessDescriptor descriptor = descriptorsByImportTableName.get(importTableName);
		if (descriptor == null)
		{
			return null;
		}

		return descriptor.getImportProcessClass().getReferencedClass();
	}
}
