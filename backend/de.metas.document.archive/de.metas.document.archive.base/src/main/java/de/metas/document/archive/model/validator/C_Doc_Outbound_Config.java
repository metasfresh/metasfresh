package de.metas.document.archive.model.validator;

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


import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;

import de.metas.document.archive.api.IDocOutboundDAO;
import de.metas.document.archive.api.IDocOutboundProducerService;
import de.metas.document.archive.model.I_C_Doc_Outbound_Config;

/**
 * Handles all jobs related to {@link I_C_Doc_Outbound_Config}:
 * <ul>
 * <li>on initialization, it registers all {@link DocOutboundProducerValidator} to {@link IDocOutboundProducerService}, one for each {@link I_C_Doc_Outbound_Config}
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

	public C_Doc_Outbound_Config(final Archive_Main_Validator parent)
	{
		super();

		Check.assumeNotNull(parent, "parent is not null");
		Check.assumeNotNull(parent.getEngine(), "engine is available");

		this.parent = parent;
	}

	@Init
	public void registerAllOutboundProducers()
	{
		final List<I_C_Doc_Outbound_Config> configs = Services.get(IDocOutboundDAO.class).retrieveAllConfigs();
		for (final I_C_Doc_Outbound_Config config : configs)
		{
			registerOutboundProducer(config);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void registerOutboundProducer(final I_C_Doc_Outbound_Config config)
	{
		// Make sure is not registered
		unregisterOutboundProducer(config);

		if (!config.isActive())
		{
			// configuration is not active, no need to be (re-)registered
			return;
		}

		//
		// Register the producer to service
		final DocOutboundProducerValidator producer = new DocOutboundProducerValidator(parent.getEngine(), config);

		final IDocOutboundProducerService producerService = Services.get(IDocOutboundProducerService.class);
		producerService.registerProducer(producer);
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_DELETE)
	public void unregisterOutboundProducer(final I_C_Doc_Outbound_Config config)
	{
		final IDocOutboundProducerService producerService = Services.get(IDocOutboundProducerService.class);
		producerService.unregisterProducerByConfig(config);
	}
}
