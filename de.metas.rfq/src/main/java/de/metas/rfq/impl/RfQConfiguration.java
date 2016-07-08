package de.metas.rfq.impl;

import org.adempiere.util.Check;

import com.google.common.base.MoreObjects;

import de.metas.rfq.IRfQConfiguration;
import de.metas.rfq.IRfQResponseProducer;
import de.metas.rfq.IRfQResponseProducerFactory;
import de.metas.rfq.IRfQResponsePublisher;
import de.metas.rfq.IRfQResponseRankingStrategy;
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

public class RfQConfiguration implements IRfQConfiguration
{
	private final CompositeRfQResponseProducerFactory rfqResponsesProducerFactory = new CompositeRfQResponseProducerFactory();
	private final CompositeRfQResponsePublisher rfqResponsePublisher = new CompositeRfQResponsePublisher();

	public RfQConfiguration()
	{
		super();
		
		addRfQResponsePublisher(MailRfqResponsePublisher.instance);
	}
	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("rfqResponsesProducerFactory", rfqResponsesProducerFactory)
				.add("rfqResponsePublisher", rfqResponsePublisher)
				.toString();
	}

	@Override
	public IRfQResponseProducer newRfQResponsesProducerFor(final I_C_RfQ rfq)
	{
		Check.assumeNotNull(rfq, "rfq not null");

		final IRfQResponseProducer producer = rfqResponsesProducerFactory.newRfQResponsesProducerFor(rfq);
		if (producer != null)
		{
			return producer;
		}

		// Fallback to default producer
		return new DefaultRfQResponseProducer();
	}

	@Override
	public IRfQConfiguration addRfQResponsesProducerFactory(final IRfQResponseProducerFactory factory)
	{
		rfqResponsesProducerFactory.addRfQResponsesProducerFactory(factory);
		return this;
	}

	@Override
	public IRfQResponseRankingStrategy newRfQResponseRankingStrategyFor(final I_C_RfQ rfq)
	{
		// TODO: implement custom providers
		return new DefaultRfQResponseRankingStrategy();
	}

	@Override
	public IRfQResponsePublisher getRfQResponsePublisher()
	{
		return rfqResponsePublisher;
	}

	@Override
	public IRfQConfiguration addRfQResponsePublisher(final IRfQResponsePublisher publisher)
	{
		rfqResponsePublisher.addRfQResponsePublisher(publisher);
		return this;
	}
}
