package de.metas.rfq;

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
 * {@link IRfQResponseProducer} factory.
 * Implementations of this interface will create particular {@link IRfQResponseProducer}s for a given {@link I_C_RfQ}.
 * 
 * To register your implementation, please use {@link IRfQConfiguration#addRfQResponsesProducerFactory(IRfQResponseProducerFactory)}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IRfQResponseProducerFactory
{
	/**
	 * @param rfq
	 * @return
	 * 		<ul>
	 *         <li>actual producer instance
	 *         <li><code>null</code> if the producer does not support given RfQ
	 *         </ul>
	 */
	IRfQResponseProducer newRfQResponsesProducerFor(final I_C_RfQ rfq);
}
