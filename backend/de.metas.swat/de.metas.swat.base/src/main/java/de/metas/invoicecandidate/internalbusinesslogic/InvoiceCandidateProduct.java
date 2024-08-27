package de.metas.invoicecandidate.internalbusinesslogic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.product.ProductId;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.swat.base
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

@Value
public class InvoiceCandidateProduct
{

	ProductId id;

	boolean stocked; // TODO I think this can be dropped

	boolean itemType;

	@JsonCreator
	public InvoiceCandidateProduct(
			@JsonProperty("id") @NonNull final ProductId id,
			@JsonProperty("stocked") @NonNull final Boolean stocked,
			@JsonProperty("itemType") @NonNull final Boolean shiippedAndReceived)
	{
		this.id = id;
		this.stocked = stocked;
		this.itemType = shiippedAndReceived;
	}


}
