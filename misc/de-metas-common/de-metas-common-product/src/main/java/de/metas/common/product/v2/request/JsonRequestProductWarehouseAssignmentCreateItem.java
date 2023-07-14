/*
 * #%L
 * de-metas-common-product
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.common.product.v2.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.metas.common.util.Check;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

import static de.metas.common.product.v2.request.constants.SwaggerDocConstants.WAREHOUSE_IDENTIFIER_DOC;
import static de.metas.common.util.Check.assume;

@Value
@ApiModel(description = "Contains warehouses to be assigned to.")
public class JsonRequestProductWarehouseAssignmentCreateItem
{
	@ApiModelProperty(position = 10, value = WAREHOUSE_IDENTIFIER_DOC)
	@Nullable
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String warehouseIdentifier;

	@ApiModelProperty(position = 20)
	@Nullable
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String name;
	
	@Builder
	@Jacksonized
	public JsonRequestProductWarehouseAssignmentCreateItem(
			@Nullable final String warehouseIdentifier,
			@Nullable final String name)
	{
		final boolean warehouseIdentifierSet = Check.isNotBlank(warehouseIdentifier);
		final boolean nameSet = Check.isNotBlank(name);
		assume(warehouseIdentifierSet || nameSet, "At least one of warehouseIdentifier or name has to be specified");

		this.warehouseIdentifier = warehouseIdentifier;
		this.name = name;
	}
}
