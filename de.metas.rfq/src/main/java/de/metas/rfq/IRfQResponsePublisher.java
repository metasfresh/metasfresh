package de.metas.rfq;

import de.metas.rfq.exceptions.RfQPublishException;
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

/**
 * Implementations of this interface are responsible for publishing draft {@link I_C_RfQResponse}s to various media (e.g. mail, some REST API etc).
 * 
 * To register your custom implementation, please use {@link IRfQConfiguration#addRfQResponsePublisher(IRfQResponsePublisher)}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IRfQResponsePublisher
{
	/**
	 * @return user friendly name
	 */
	String getDisplayName();

	void publish(RfQResponsePublisherRequest request) throws RfQPublishException;
}
