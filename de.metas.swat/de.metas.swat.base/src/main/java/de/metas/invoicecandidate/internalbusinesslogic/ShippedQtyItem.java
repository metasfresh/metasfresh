package de.metas.invoicecandidate.internalbusinesslogic;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.swat.base
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
public class ShippedQtyItem
{
	Quantity qtyInStockUom;

	Quantity qtyNominal;

	Quantity qtyCatch;

	Quantity qtyOverride;

	@Builder
	@JsonCreator
	private ShippedQtyItem(
			@JsonProperty("qtyInStockUom") @NonNull final Quantity qtyInStockUom,
			@JsonProperty("qtyNominal") @NonNull final Quantity qtyNominal,
			@JsonProperty("qtyCatch") @Nullable final Quantity qtyCatch,
			@JsonProperty("qtyOverride") @Nullable final Quantity qtyOverride)
	{
		this.qtyInStockUom = qtyInStockUom;
		this.qtyNominal = qtyNominal;
		this.qtyCatch = qtyCatch;
		this.qtyOverride = qtyOverride;
	}

}
