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


import de.metas.document.archive.model.I_C_Doc_Outbound_Config;

/**
 * Implementation of this interface is responsible for producing document outbound from given models.
 *
 * @author tsa
 *
 */
public interface IDocOutboundProducer
{

	I_C_Doc_Outbound_Config getC_Doc_Outbound_Config();

	/**
	 * Called by API when this producer is registered to an service.
	 *
	 * NOTE: never call it directly
	 *
	 * @param producerService
	 */
	void init(IDocOutboundProducerService producerService);

	/**
	 * Called by API when this producer is unregistered from service.
	 *
	 * NOTE: never call it directly
	 *
	 * @param producerService
	 */
	void destroy(IDocOutboundProducerService producerService);

	/**
	 * Checks if given model is handled by this producer
	 *
	 * @param model
	 * @return true if the model is handled by this producer
	 */
	boolean accept(Object model);

	/**
	 * Creates the document outbound for given model.
	 *
	 * NOTE: it is assumed that the API already asked this producer if the model is accepted (see {@link #accept(Object)}).
	 *
	 * @param model
	 */
	void createDocOutbound(Object model);

	/**
	 * Process the document outbound for given model - the printing queue more specific
	 *
	 * NOTE: it is assumed that the API already asked this producer if the model is accepted (see {@link #accept(Object)}).
	 *
	 * @param model
	 */
	void voidDocOutbound(Object model);
}
