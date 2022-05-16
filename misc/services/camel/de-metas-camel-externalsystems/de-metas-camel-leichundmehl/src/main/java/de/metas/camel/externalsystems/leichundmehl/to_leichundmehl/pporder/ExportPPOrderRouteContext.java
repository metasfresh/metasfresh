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
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.ftp.FTPCredentials;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.manufacturing.v2.JsonResponseManufacturingOrder;
import de.metas.common.pricing.v2.productprice.JsonResponsePrice;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import org.apache.camel.RuntimeCamelException;

import javax.annotation.Nullable;

@Data
@Builder
public class ExportPPOrderRouteContext
{
	@NonNull
	private final JsonExternalSystemRequest jsonExternalSystemRequest;

	@NonNull
	private final FTPCredentials ftpCredentials;

	@Nullable
	@Getter(AccessLevel.NONE)
	private JsonResponseManufacturingOrder jsonResponseManufacturingOrder;

	@Nullable
	private JsonProductInfo.JsonProductInfoBuilder jsonProductBuilder;

	@Nullable
	private JsonBPartner jsonBPartner;

	@Nullable
	private final ImmutableList<JsonResponsePrice> prices;

	@Nullable
	public JsonMetasfreshId getBPartnerId()
	{
		return getManufacturingOrderNonNull().getBpartnerId();
	}

	@NonNull
	public JsonMetasfreshId getBPartnerIdNonNull()
	{
		if (this.getBPartnerId() == null)
		{
			throw new RuntimeCamelException("BPartnerId shouldn't be null at this stage!");
		}

		return this.getBPartnerId();
	}

	@NonNull
	public JsonResponseManufacturingOrder getManufacturingOrderNonNull()
	{
		if (this.jsonResponseManufacturingOrder == null)
		{
			throw new RuntimeCamelException("JsonResponseManufacturingOrder cannot be null!");
		}

		return this.jsonResponseManufacturingOrder;
	}

	@NonNull
	public JsonProductInfo.JsonProductInfoBuilder getProductInfoBuilderNonNull()
	{
		if (this.jsonProductBuilder == null)
		{
			throw new RuntimeCamelException("JsonProductInfo.JsonProductInfoBuilder cannot be null!");
		}

		return this.jsonProductBuilder;
	}
}
