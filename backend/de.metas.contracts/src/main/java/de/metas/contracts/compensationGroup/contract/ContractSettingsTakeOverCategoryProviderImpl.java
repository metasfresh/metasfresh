package de.metas.contracts.compensationGroup.contract;

import com.google.common.collect.ImmutableMap;
import de.metas.order.compensationGroup.ContractSettingsTakeOverCategoryProvider;
import de.metas.product.ProductCategoryId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

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

/** Contracts-side implementation of {@link ContractSettingsTakeOverCategoryProvider}: reads the category off the take-over record. */
@Component
@RequiredArgsConstructor
public class ContractSettingsTakeOverCategoryProviderImpl implements ContractSettingsTakeOverCategoryProvider
{
	@NonNull private final ContractSettingsTakeOverRepository takeOverRepository;

	@Override
	public ImmutableMap<ContractSettingsTakeOverId, ProductCategoryId> getAppliesToCategories(@NonNull final Set<ContractSettingsTakeOverId> takeOverIds)
	{
		return takeOverRepository.getProductCategoryIds(takeOverIds);
	}
}
