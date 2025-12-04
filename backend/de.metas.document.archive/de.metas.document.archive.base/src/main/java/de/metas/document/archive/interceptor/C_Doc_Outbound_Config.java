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


import de.metas.document.archive.config.DocOutboundConfigRepository;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.ModelValidator;

import de.metas.document.archive.api.IDocOutboundProducerService;
import de.metas.document.archive.model.I_C_Doc_Outbound_Config;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

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
	private final Archive_Main_Validator parent;
	private final IDocOutboundProducerService producerService = Services.get(IDocOutboundProducerService.class);
	private final DocOutboundConfigRepository docOutboundConfigRepository = SpringContextHolder.instance.getBean(DocOutboundConfigRepository.class);

	public C_Doc_Outbound_Config(@NonNull final Archive_Main_Validator parent)
	{
		Check.assumeNotNull(parent.getEngine(), "engine is available");

		this.parent = parent;
	}

	@Init
	public void registerAllOutboundProducers()
	{
		docOutboundConfigRepository.getDistinctConfigTableIds()
				.forEach(this::registerOutboundProducer);
	}

	private void registerOutboundProducer(@NonNull final AdTableId tableId)
	{
		producerService.registerProducer(new DocOutboundProducerValidator(parent.getEngine(), tableId));
	}

	private void unregisterOutboundProducer(@NonNull final AdTableId tableId)
	{
		docOutboundConfigRepository.resetCache(); // prevent raceConditions
		if (docOutboundConfigRepository.getByTableId(tableId).isEmpty())
		{
			producerService.unregisterProducerByTableId(tableId);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, })
	public void registerOutboundProducer(final I_C_Doc_Outbound_Config config)
	{
		registerOutboundProducer(AdTableId.ofRepoId(config.getAD_Table_ID()));
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = {
			I_C_Doc_Outbound_Config.COLUMNNAME_AD_Table_ID })
	public void onTableIdChange(final I_C_Doc_Outbound_Config config)
	{
		final AdTableId tableId = AdTableId.ofRepoId(config.getAD_Table_ID());
		final AdTableId oldAdTableId = AdTableId.ofRepoId(InterfaceWrapperHelper.createOld(config, I_C_Doc_Outbound_Config.class).getAD_Table_ID());
		if (!AdTableId.equals(tableId, oldAdTableId))
		{
			unregisterOutboundProducer(oldAdTableId);
			registerOutboundProducer(tableId);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = {
			I_C_Doc_Outbound_Config.COLUMNNAME_IsActive, })
	public void onIsActiveChange(final I_C_Doc_Outbound_Config config)
	{
		if(config.isActive())
		{
			registerOutboundProducer(AdTableId.ofRepoId(config.getAD_Table_ID()));
		}
		else
		{
			unregisterOutboundProducer(AdTableId.ofRepoId(config.getAD_Table_ID()));
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_DELETE)
	public void unregisterOutboundProducer(final I_C_Doc_Outbound_Config config)
	{
		unregisterOutboundProducer(AdTableId.ofRepoId(config.getAD_Table_ID()));
	}
}
