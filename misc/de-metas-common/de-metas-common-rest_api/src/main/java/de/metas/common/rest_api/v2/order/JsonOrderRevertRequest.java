/*
 * #%L
 * de-metas-common-rest_api
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.common.rest_api.v2.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonOrderRevertRequest
{
	@ApiModelProperty(position = 10)
	String orgCode;

	@NonNull
	@ApiModelProperty(position = 20, required = true, value = "This translates to 'C_Order.ExternalId'.\n"
			+ "'externalId' and 'dataSource' together need to be unique.")
	String externalId;

	@NonNull
	@ApiModelProperty(position = 30, required = true, value = "This translates to 'AD_InputDataSource.internalName' of the data source the order in question was added with.")
	String dataSource;
}
