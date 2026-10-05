package de.metas.order.compensationGroup;

import com.google.common.collect.ImmutableMap;
import de.metas.contracts.compensationGroup.contract.ContractSettingsTakeOverId;
import de.metas.product.ProductCategoryId;
import lombok.NonNull;

import java.util.Set;

/*
 * #%L
 * de.metas.business
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

/** Provides the applies-to product category of contract take-over records, for compensation lines without a schema line. */
public interface ContractSettingsTakeOverCategoryProvider
{
	/** @return the applies-to product category of each given take-over record; a record that does not exist or has no category is absent */
	ImmutableMap<ContractSettingsTakeOverId, ProductCategoryId> getAppliesToProductCategoryIds(@NonNull Set<ContractSettingsTakeOverId> takeOverIds);
}
