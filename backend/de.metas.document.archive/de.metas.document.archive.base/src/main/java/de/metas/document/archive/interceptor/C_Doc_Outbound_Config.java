package de.metas.document.archive.interceptor;

/*
 * #%L
 * de.metas.document.archive.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import com.google.common.collect.ImmutableSet;
import de.metas.document.archive.api.IDocOutboundProducerService;
import de.metas.document.archive.config.DocOutboundConfigRepository;
import de.metas.document.archive.model.I_C_Doc_Outbound_Config;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.ad.table.api.AdTableId;
import org.compiere.SpringContextHolder;
import org.compiere.model.ModelValidationEngine;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Objects;

/**
 * Handles all jobs related to {@link I_C_Doc_Outbound_Config}:
 * <ul>
 * <li>on initialization, it registers all {@link DocOutboundProducerValidator} to {@link IDocOutboundProducerService}, one for each {@link org.adempiere.ad.table.api.AdTableId}
 * <li>automatically register/unregister {@link DocOutboundProducerValidator} when a particular {@link I_C_Doc_Outbound_Config} is created, was changed or was deleted
 * </ul>
 *
 * @author tsa
 *
 */
@Validator(I_C_Doc_Outbound_Config.class)
class C_Doc_Outbound_Config
{
	@NonNull private static final Logger logger = LogManager.getLogger(C_Doc_Outbound_Config.class);
	@NonNull private final IDocOutboundProducerService producerService = Services.get(IDocOutboundProducerService.class);
	@NonNull private final DocOutboundConfigRepository configRepository = SpringContextHolder.instance.getBean(DocOutboundConfigRepository.class);
	@Nullable private ModelValidationEngine engine;

	@NonNull private ImmutableSet<AdTableId> tableIds = ImmutableSet.of();
	@NonNull private final HashSet<AdTableId> registeredTableIds = new HashSet<>();

	@Init
	protected void onInit(@NonNull final IModelValidationEngine engine)
	{
		this.engine = (ModelValidationEngine)engine;
		configRepository.addCacheResetListener(this::updateFromRepository);

		updateFromRepository();
	}

	private synchronized void updateFromRepository()
	{
		final ImmutableSet<AdTableId> tableIdsPrev = this.tableIds;
		this.tableIds = configRepository.getDistinctConfigTableIds();
		if (Objects.equals(this.tableIds, tableIdsPrev))
		{
			return;
		}

		updateRegisteredInterceptors();
	}

	private synchronized void updateRegisteredInterceptors()
	{
		final HashSet<AdTableId> registeredTableIdsNoLongerNeeded = new HashSet<>(this.registeredTableIds);

		for (final AdTableId tableId : this.tableIds)
		{
			registeredTableIdsNoLongerNeeded.remove(tableId);

			if (registeredTableIds.contains(tableId))
			{
				// already registered
				continue;
			}

			registerOutboundProducer(tableId);
		}

		//
		// Remove no longer necessary interceptors
		for (final AdTableId tableId : registeredTableIdsNoLongerNeeded)
		{
			unregisterOutboundProducer(tableId);
		}
	}

	private void registerOutboundProducer(@NonNull final AdTableId tableId)
	{
		final ModelValidationEngine engine = Check.assumeNotNull(this.engine, "engine not null");
		producerService.registerProducer(new DocOutboundProducerValidator(engine, tableId));
		registeredTableIds.add(tableId);
		logger.info("Registered producer for {}", tableId);
	}

	private void unregisterOutboundProducer(@NonNull final AdTableId tableId)
	{
		producerService.unregisterProducerByTableId(tableId);
		registeredTableIds.remove(tableId);
		logger.info("Unregistered trigger for {}", tableId);
	}
}
