/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.contracts.modular.computing;

import com.google.common.collect.ImmutableSet;
import de.metas.contracts.modular.log.ModularContractLogEntryId;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Builder
@Value
public class ComputingResponse
{
	@NonNull Quantity qty;
	@NonNull ProductPrice price;
	@Nullable InvoiceCandidateId invoiceCandidateId;
	@NonNull ImmutableSet<ModularContractLogEntryId> ids;
	boolean hidePriceAndAmountOnPrint;
}
