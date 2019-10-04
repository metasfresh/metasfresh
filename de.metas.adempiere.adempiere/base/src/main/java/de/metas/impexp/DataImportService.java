package de.metas.impexp;

import java.util.Optional;

import org.springframework.stereotype.Service;

import de.metas.impexp.config.DataImportConfig;
import de.metas.impexp.config.DataImportConfigId;
import de.metas.impexp.config.DataImportConfigRepository;
import de.metas.impexp.format.ImpFormat;
import de.metas.impexp.format.ImpFormatRepository;
import de.metas.impexp.processing.IImportProcessFactory;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
public class DataImportService
{
	private final IImportProcessFactory importProcessFactory = Services.get(IImportProcessFactory.class);
	private final DataImportConfigRepository dataImportConfigsRepo;
	private final ImpFormatRepository importFormatsRepo;
	private final DataImportRunsService dataImportRunService;

	public DataImportService(
			@NonNull final DataImportConfigRepository dataImportConfigsRepo,
			@NonNull final ImpFormatRepository importFormatsRepo,
			@NonNull final DataImportRunsService dataImportRunService)
	{
		this.dataImportConfigsRepo = dataImportConfigsRepo;
		this.importFormatsRepo = importFormatsRepo;
		this.dataImportRunService = dataImportRunService;
	}

	public DataImportResult importData(@NonNull final DataImportRequest request)
	{
		final DataImportConfigId dataImportConfigId = request.getDataImportConfigId();
		final DataImportConfig dataImportConfig = dataImportConfigsRepo.getById(dataImportConfigId);
		final ImpFormat importFormat = importFormatsRepo.getById(dataImportConfig.getImpFormatId());

		return DataImportCommand.builder()
				.importProcessFactory(importProcessFactory)
				.dataImportRunService(dataImportRunService)
				//
				.clientId(request.getClientId())
				.orgId(request.getOrgId())
				.userId(request.getUserId())
				//
				.dataImportConfigId(request.getDataImportConfigId())
				.importFormat(importFormat)
				//
				.data(request.getData())
				//
				.build()
				//
				.execute();
	}

	public Optional<DataImportConfig> getDataImportConfigByInternalName(@NonNull final String internalName)
	{
		return dataImportConfigsRepo.getByInternalName(internalName);
	}
}
