/*
 * #%L
 * de-metas-camel-leichundmehl
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

package de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.pporder;

import com.google.common.collect.ImmutableList;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.api.model.JsonBPartner;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.api.model.JsonBPartnerProduct;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.api.model.JsonProductInfo;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.manufacturing.v2.JsonResponseManufacturingOrder;
import de.metas.common.pricing.v2.productprice.JsonResponsePrice;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.annotation.Nullable;

@Data
@Builder
public class ExportPPOrderRouteContext
{
	@NonNull
	JsonExternalSystemRequest jsonExternalSystemRequest;

	@Nullable
	JsonMetasfreshId bpartnerId;

	@Nullable
	JsonResponseManufacturingOrder jsonResponseManufacturingOrder;

	@Nullable
	JsonProductInfo jsonProduct;

	@Nullable
	JsonBPartnerProduct jsonBPartnerProduct;

	@Nullable
	JsonBPartner jsonBPartner;

	@Nullable
	ImmutableList<JsonResponsePrice> prices;
}
