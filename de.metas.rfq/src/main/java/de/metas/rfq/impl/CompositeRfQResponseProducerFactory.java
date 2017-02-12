package de.metas.rfq.impl;

import java.util.concurrent.CopyOnWriteArrayList;

import org.adempiere.util.Check;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.rfq.IRfQResponseProducer;
import de.metas.rfq.IRfQResponseProducerFactory;
import de.metas.rfq.model.I_C_RfQ;

/*
 * #%L
 * de.metas.rfq
 * %%
 * Copyright (C) 2016 metas GmbH
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

final class CompositeRfQResponseProducerFactory implements IRfQResponseProducerFactory
{
	private static final Logger logger = LogManager.getLogger(CompositeRfQResponseProducerFactory.class);

	private final CopyOnWriteArrayList<IRfQResponseProducerFactory> factories = new CopyOnWriteArrayList<>();

	@Override
	public IRfQResponseProducer newRfQResponsesProducerFor(final I_C_RfQ rfq)
	{
		for (final IRfQResponseProducerFactory factory : factories)
		{
			final IRfQResponseProducer producer = factory.newRfQResponsesProducerFor(rfq);
			if (producer != null)
			{
				return producer;
			}
		}
		
		// No producer was created
		return null;
	}

	public void addRfQResponsesProducerFactory(final IRfQResponseProducerFactory factory)
	{
		Check.assumeNotNull(factory, "factory not null");
		final boolean added = factories.addIfAbsent(factory);
		if (!added)
		{
			logger.warn("Factory {} was already registered: {}", factory, factories);
		}
	}

}
