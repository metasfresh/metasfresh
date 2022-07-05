/*
 * #%L
 * de.metas.manufacturing.rest-api
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.manufacturing.rest_api.v2;

import de.metas.common.manufacturing.v2.JsonResponseManufacturingOrderBOMLine;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.organization.IOrgDAO;
import de.metas.product.ProductRepository;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eevolution.model.I_PP_Order;

import java.util.List;

@Builder
@Value
public class MapToJsonResponseManufacturingOrderRequest
{
	@NonNull
	I_PP_Order order;

	@NonNull
	IPPOrderBOMBL ppOrderBOMBL;

	@NonNull
	IOrgDAO orgDAO;

	@NonNull
	ProductRepository productRepository;

	@NonNull
	List<JsonResponseManufacturingOrderBOMLine> components;
}
