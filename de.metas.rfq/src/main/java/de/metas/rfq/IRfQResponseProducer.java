package de.metas.rfq;

import java.util.List;

import de.metas.rfq.model.I_C_RfQ;
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
 * Implementations of this interface are responsible for creating {@link I_C_RfQResponse}s for a given {@link I_C_RfQ}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IRfQResponseProducer
{
	List<I_C_RfQResponse> create();

	/**
	 * @return how many RfQ responses were published
	 */
	int getCountPublished();

	IRfQResponseProducer setC_RfQ(I_C_RfQ rfq);

	/** Sets if we shall publish the responses after generating them */
	IRfQResponseProducer setPublish(boolean publish);
}
