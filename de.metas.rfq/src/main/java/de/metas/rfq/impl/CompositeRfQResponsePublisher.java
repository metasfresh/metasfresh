package de.metas.rfq.impl;

import java.util.concurrent.CopyOnWriteArrayList;

import org.adempiere.util.Check;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;

import de.metas.logging.LogManager;
import de.metas.rfq.IRfQResponsePublisher;
import de.metas.rfq.model.I_C_RfQResponse;

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

final class CompositeRfQResponsePublisher implements IRfQResponsePublisher
{
	private static final Logger logger = LogManager.getLogger(CompositeRfQResponsePublisher.class);

	private final CopyOnWriteArrayList<IRfQResponsePublisher> publishers = new CopyOnWriteArrayList<>();

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(publishers)
				.toString();
	}

	@Override
	public void publish(final I_C_RfQResponse response)
	{
		for (final IRfQResponsePublisher publisher : publishers)
		{
			publisher.publish(response);
		}
	}

	public void addRfQResponsePublisher(final IRfQResponsePublisher publisher)
	{
		Check.assumeNotNull(publisher, "publisher not null");
		final boolean added = publishers.addIfAbsent(publisher);
		if (!added)
		{
			logger.warn("Publisher {} was already added to {}", publisher, publishers);
		}
	}

}
