package de.metas.impexp.processing.impl;

import java.util.HashSet;
import java.util.Objects;

import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableSet;

import de.metas.logging.LogManager;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.process.RelatedProcessDescriptor.DisplayPlace;
import de.metas.util.Services;
import lombok.Getter;
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

final class ImportTablesRelatedProcessesRegistry
{
	private static final Logger logger = LogManager.getLogger(ImportTablesRelatedProcessesRegistry.class);
	private final IADProcessDAO adProcessesRepo = Services.get(IADProcessDAO.class);

	private final HashSet<String> importTableNames = new HashSet<>();
	private final ImportTableRelatedProcess deleteImportDataProcessRegistration = new ImportTableRelatedProcess("deleteImportDataProcess");

	public synchronized void registerImportTable(final String importTableName)
	{
		importTableNames.add(importTableName);
		registerProcessToImportTableNames(deleteImportDataProcessRegistration);
	}

	public synchronized void setDeleteImportDataProcessClass(@NonNull final Class<?> deleteImportDataProcessClass)
	{
		setProcessAndUpdate(deleteImportDataProcessRegistration, deleteImportDataProcessClass);
	}

	private void setProcessAndUpdate(
			@NonNull final ImportTableRelatedProcess registration,
			@NonNull final Class<?> processClass)
	{
		final AdProcessId processId;
		try
		{
			processId = adProcessesRepo.retrieveProcessIdByClass(processClass);
		}
		catch (Exception ex)
		{
			logger.warn("Failed fetching process ID for {}. Skip", processClass, ex);
			return;
		}

		registration.setProcessId(processId);
		logger.info("{}: set process class: {} ({})", registration.getName(), processClass, registration.getProcessId());

		registerProcessToImportTableNames(registration);
	}

	private void registerProcessToImportTableNames(@NonNull final ImportTableRelatedProcess registration)
	{
		if (registration.getProcessId() == null)
		{
			return;
		}

		for (final String importTableName : ImmutableSet.copyOf(this.importTableNames))
		{
			if (registration.hasTableName(importTableName))
			{
				continue;
			}

			registerRelatedProcessNoFail(importTableName, registration.getProcessId());
			registration.addTableName(importTableName);
		}
	}

	private void registerRelatedProcessNoFail(
			@NonNull final String importTableName,
			@NonNull final AdProcessId processId)
	{
		try
		{
			final IADTableDAO tablesRepo = Services.get(IADTableDAO.class);

			final AdTableId importTableId = AdTableId.ofRepoId(tablesRepo.retrieveTableId(importTableName));
			adProcessesRepo.registerTableProcess(RelatedProcessDescriptor.builder()
					.processId(processId)
					.tableId(importTableId)
					.displayPlace(DisplayPlace.ViewActionsMenu)
					.build());
		}
		catch (final Exception ex)
		{
			logger.warn("Cannot register process {} to {}. Skip", processId, importTableName, ex);
		}
	}

	@ToString
	private static class ImportTableRelatedProcess
	{
		@Getter
		private final String name;

		@Getter
		private AdProcessId processId;

		private final HashSet<String> registeredOnTableNames = new HashSet<>();

		public ImportTableRelatedProcess(@NonNull final String name)
		{
			this.name = name;
		}

		public void setProcessId(@NonNull final AdProcessId processId)
		{
			if (this.processId != null && !Objects.equals(this.processId, processId))
			{
				throw new AdempiereException("Changing process from " + this.processId + " to " + processId + " is not allowed.");
			}
			this.processId = processId;
		}

		public boolean hasTableName(@NonNull final String tableName)
		{
			return registeredOnTableNames.contains(tableName);
		}

		public void addTableName(@NonNull final String tableName)
		{
			registeredOnTableNames.add(tableName);
		}
	}
}
