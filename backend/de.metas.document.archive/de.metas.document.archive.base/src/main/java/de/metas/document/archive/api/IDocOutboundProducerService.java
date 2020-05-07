package de.metas.document.archive.api;

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


import org.adempiere.util.ISingletonService;

import de.metas.document.archive.model.I_C_Doc_Outbound_Config;

/**
 * Service responsible with managing registered {@link IDocOutboundProducer}s and picking the right one in order to perform document outbound actions.
 * 
 * @author tsa
 * 
 */
public interface IDocOutboundProducerService extends ISingletonService
{
	/**
	 * Registers the given document outbound producer.
	 * 
	 * NOTE: if a different {@link IDocOutboundProducer} is already registered for {@link IDocOutboundProducer#getC_Doc_Outbound_Config()} then the old one will be unregistered first
	 * 
	 * @param producer
	 */
	void registerProducer(IDocOutboundProducer producer);

	/**
	 * Unregisters the {@link IDocOutboundProducer} which was previously registered for given <code>config</code>.
	 * 
	 * If no producer was registered, this method does nothing.
	 * 
	 * @param config
	 */
	void unregisterProducerByConfig(I_C_Doc_Outbound_Config config);

	/**
	 * Creates the document outbound for given <code>model</code> by picking the right {@link IDocOutboundProducer} and delegating the work to it.
	 * 
	 * @param model
	 */
	void createDocOutbound(Object model);
}
