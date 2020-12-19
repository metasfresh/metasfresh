/*
 * #%L
 * de-metas-common-procurement
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.common.procurement.sync.protocol.request_to_metasfresh;

import de.metas.common.procurement.sync.protocol.RequestToMetasfresh;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutProductsRequest;
import lombok.Value;

/**
 * Send from the procurement-webui to metasfresh to request a {@link PutProductsRequest} containing all products that shall be know to the procurement-webui.
 */
@Value
public class GetAllProductsRequest extends RequestToMetasfresh
{
	public static final GetAllProductsRequest INSTANCE = new GetAllProductsRequest();

	private GetAllProductsRequest(){};
}
