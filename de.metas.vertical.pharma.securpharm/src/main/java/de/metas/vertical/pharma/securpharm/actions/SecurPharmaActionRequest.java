package de.metas.vertical.pharma.securpharm.actions;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.inventory.InventoryId;
import de.metas.vertical.pharma.securpharm.product.SecurPharmProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-pharma.securpharm
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Value
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class SecurPharmaActionRequest
{
	@JsonProperty("action")
	SecurPharmAction action;

	@JsonProperty("inventoryId")
	InventoryId inventoryId;

	@JsonProperty("productId")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	SecurPharmProductId productId;

	@Builder
	@JsonCreator
	private SecurPharmaActionRequest(
			@JsonProperty("action") @NonNull final SecurPharmAction action,
			@JsonProperty("inventoryId") @NonNull final InventoryId inventoryId,
			@JsonProperty("productId") @Nullable SecurPharmProductId productId)

	{
		this.action = action;
		this.inventoryId = inventoryId;
		this.productId = productId;
	}

}
