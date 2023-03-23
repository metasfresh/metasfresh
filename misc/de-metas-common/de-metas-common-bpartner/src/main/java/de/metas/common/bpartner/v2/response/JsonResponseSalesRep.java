/*
 * #%L
 * de-metas-common-bpartner
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.common.bpartner.v2.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
public class JsonResponseSalesRep
{
	public static final String SALES_REP_VALUE = "salesRepValue";
	public static final String SALES_REP_ID = "salesRepId";

	@Schema(description = "This translates to `C_BPartner.C_BPartner_ID`.")
	@JsonProperty(SALES_REP_ID)
	JsonMetasfreshId salesRepId;

	@Schema(description = "This translates to `C_BPartner.Value`.")
	@JsonProperty(SALES_REP_VALUE)
	String salesRepValue;

	@JsonCreator
	@Builder
	private JsonResponseSalesRep(
			@JsonProperty(SALES_REP_ID) @NonNull final JsonMetasfreshId salesRepId,
			@JsonProperty(SALES_REP_VALUE) @NonNull final String salesRepValue)
	{
		this.salesRepId = salesRepId;
		this.salesRepValue = salesRepValue;
	}
}
