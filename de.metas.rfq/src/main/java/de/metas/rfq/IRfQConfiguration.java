package de.metas.rfq;

import org.adempiere.util.ISingletonService;

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

/**
 * RfQ module configuration.
 * 
 * This is the central point for registering all your producers, factories etc.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IRfQConfiguration extends ISingletonService
{
	IRfQResponseProducer newRfQResponsesProducerFor(I_C_RfQ rfq);

	IRfQConfiguration addRfQResponsesProducerFactory(IRfQResponseProducerFactory factory);

	IRfQResponseRankingStrategy newRfQResponseRankingStrategyFor(I_C_RfQ rfq);

	IRfQResponsePublisher getRfQResponsePublisher();

	IRfQConfiguration addRfQResponsePublisher(IRfQResponsePublisher publisher);
}
