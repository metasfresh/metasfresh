package de.metas.impexp.processing.impl;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;

import de.metas.impexp.processing.IImportProcess;
import de.metas.impexp.processing.IImportProcessFactory;
import de.metas.logging.LogManager;
import lombok.NonNull;

public class ImportProcessFactory implements IImportProcessFactory
{
	private static final Logger logger = LogManager.getLogger(ImportProcessFactory.class);

	private final ImportProcessDescriptorsMap importProcessDescriptorsMap = new ImportProcessDescriptorsMap();
	private final ImportTablesRelatedProcessesRegistry relatedProcessesRegistry = new ImportTablesRelatedProcessesRegistry();

	@Override
	public <ImportRecordType> void registerImportProcess(
			@NonNull final Class<ImportRecordType> modelImportClass,
			@NonNull final Class<? extends IImportProcess<ImportRecordType>> importProcessClass)
	{
		final ImportProcessDescriptor descriptor = importProcessDescriptorsMap.register(modelImportClass, importProcessClass);
		logger.info("Registered import process: {}", descriptor);

		relatedProcessesRegistry.registerImportTable(descriptor.getImportTableName());
	}

	@Override
	public void setDeleteImportDataProcessClass(@NonNull final Class<?> deleteImportDataProcessClass)
	{
		relatedProcessesRegistry.setDeleteImportDataProcessClass(deleteImportDataProcessClass);
	}

	@Override
	public <ImportRecordType> IImportProcess<ImportRecordType> newImportProcess(@NonNull final Class<ImportRecordType> modelImportClass)
	{
		final IImportProcess<ImportRecordType> importProcess = newImportProcessOrNull(modelImportClass);
		if (importProcess == null)
		{
			throw new AdempiereException("No import process found for " + modelImportClass);
		}
		return importProcess;
	}

	@Nullable
	private <ImportRecordType> IImportProcess<ImportRecordType> newImportProcessOrNull(@NonNull final Class<ImportRecordType> modelImportClass)
	{
		final Class<?> importProcessClass = importProcessDescriptorsMap.getImportProcessClassByModelImportClassOrNull(modelImportClass);
		if (importProcessClass == null)
		{
			return null;
		}

		return newInstance(importProcessClass);
	}

	private <ImportRecordType> IImportProcess<ImportRecordType> newInstance(final Class<?> importProcessClass)
	{
		try
		{
			@SuppressWarnings("unchecked")
			final IImportProcess<ImportRecordType> importProcess = (IImportProcess<ImportRecordType>)importProcessClass.newInstance();
			return importProcess;
		}
		catch (final Exception e)
		{
			throw new AdempiereException("Failed instantiating " + importProcessClass, e);
		}
	}

	@Nullable
	private <ImportRecordType> IImportProcess<ImportRecordType> newImportProcessForTableNameOrNull(@NonNull final String importTableName)
	{
		final Class<?> importProcessClass = importProcessDescriptorsMap.getImportProcessClassByImportTableNameOrNull(importTableName);
		if (importProcessClass == null)
		{
			return null;
		}

		return newInstance(importProcessClass);
	}

	@Override
	public <ImportRecordType> IImportProcess<ImportRecordType> newImportProcessForTableName(@NonNull final String importTableName)
	{
		final IImportProcess<ImportRecordType> importProcess = newImportProcessForTableNameOrNull(importTableName);
		if (importProcess == null)
		{
			throw new AdempiereException("No import process found for " + importTableName);
		}
		return importProcess;
	}
}
