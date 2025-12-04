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
import org.adempiere.ad.table.api.AdTableId;

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
	private final Map<AdTableId, IDocOutboundProducer> outboundProducers = new HashMap<>();
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
		final AdTableId tableId = producer.getTableId();
		Check.assumeNotNull(tableId, "Producer {} shall have a tableId", producer);
		if(outboundProducers.containsKey(tableId))
		{
			return;
		}

		producer.init(this);
		outboundProducers.put(tableId, producer);
	}

	@Override
	public void unregisterProducerByTableId(@NonNull final AdTableId tableId)
	{
		outboundProducersLock.lock();
		try
		{
			unregisterProducerByTableId0(tableId);
		}
		finally
		{
			outboundProducersLock.unlock();
		}
	}

	private void unregisterProducerByTableId0(@NonNull final AdTableId tableId)
	{
		Check.assumeNotNull(tableId, "tableId not null");
		final IDocOutboundProducer producer = outboundProducers.get(tableId);
		if (producer == null)
		{
			// no producer was not registered for given config, nothing to unregister
			return;
		}

		producer.destroy(this);
		outboundProducers.remove(tableId);
	}

	private List<IDocOutboundProducer> getProducersList()
	{
		return new ArrayList<>(outboundProducers.values());
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
