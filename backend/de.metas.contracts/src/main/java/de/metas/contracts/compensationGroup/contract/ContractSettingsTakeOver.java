package de.metas.contracts.compensationGroup.contract;

import com.google.common.collect.ImmutableSet;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2026 metas GmbH
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

/**
 * One take-over record of a contract's compensation-group settings: for products of {@link #productCategoryId},
 * the customer's contract discount lines for {@link #listedCustomerProductIds} are taken over onto the purchase order
 * as a line of {@link #ownLineProductId}.
 */
@Value
@Builder
public class ContractSettingsTakeOver
{
	@NonNull ContractSettingsTakeOverId id;
	@NonNull ProductCategoryId productCategoryId;
	/** the discount product of the purchase order's own take-over line */
	@NonNull ProductId ownLineProductId;
	@NonNull @Singular ImmutableSet<ProductId> listedCustomerProductIds;
}
