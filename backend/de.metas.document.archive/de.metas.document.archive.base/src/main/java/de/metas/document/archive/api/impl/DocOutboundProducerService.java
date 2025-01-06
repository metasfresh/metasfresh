package de.metas.document.archive.api.impl;

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


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import de.metas.document.archive.api.IDocOutboundProducerService;
import de.metas.document.archive.spi.IDocOutboundProducer;
import de.metas.util.Check;
import lombok.NonNull;
import org.compiere.model.I_C_Doc_Outbound_Config;

/**
 * Default implementation of {@link IDocOutboundProducerService}
 *
 * @author tsa
 *
 */
public class DocOutboundProducerService implements IDocOutboundProducerService
{
	// private static final transient Logger logger = CLogMgt.getLogger(DocOutboundProducerService.class);

	/**
	 * Map of Doc Outbound Producers (C_Doc_Outbound_Config_ID -> DocOutboundProducerValidator)
	 */
	private final Map<Integer, IDocOutboundProducer> outboundProducers = new HashMap<>();
	private final ReentrantLock outboundProducersLock = new ReentrantLock();

	public DocOutboundProducerService()
	{
		super();
	}

	@Override
	public void registerProducer(final IDocOutboundProducer producer)
	{
		outboundProducersLock.lock();
		try
		{
			registerProducer0(producer);
		}
		finally
		{
			outboundProducersLock.unlock();
		}
	}

	private void registerProducer0(@NonNull final IDocOutboundProducer producer)
	{
		final I_C_Doc_Outbound_Config config = producer.getC_Doc_Outbound_Config();
		Check.assumeNotNull(config, "Producer {} shall have a config", producer);

		unregisterProducerByConfig0(config);

		producer.init(this);
		outboundProducers.put(config.getC_Doc_Outbound_Config_ID(), producer);
	}

	@Override
	public void unregisterProducerByConfig(final I_C_Doc_Outbound_Config config)
	{
		outboundProducersLock.lock();
		try
		{
			unregisterProducerByConfig0(config);
		}
		finally
		{
			outboundProducersLock.unlock();
		}
	}

	private void unregisterProducerByConfig0(final I_C_Doc_Outbound_Config config)
	{
		Check.assumeNotNull(config, "config not null");
		final int configId = config.getC_Doc_Outbound_Config_ID();
		final IDocOutboundProducer producer = outboundProducers.get(configId);
		if (producer == null)
		{
			// no producer was not registered for given config, nothing to unregister
			return;
		}

		producer.destroy(this);
		outboundProducers.remove(configId);
	}

	private List<IDocOutboundProducer> getProducersList()
	{
		final List<IDocOutboundProducer> producersList = new ArrayList<>(outboundProducers.values());
		return producersList;
	}

	@Override
	public void createDocOutbound(@NonNull final Object model)
	{
		for (final IDocOutboundProducer producer : getProducersList())
		{
			if (!producer.accept(model))
			{
				continue;
			}

			producer.createDocOutbound(model);
		}
	}
}
