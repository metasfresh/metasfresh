package de.metas.acct.api;

import java.util.Map;
import java.util.Optional;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMap;

import de.metas.costing.CostingLevel;
import de.metas.costing.CostingMethod;
import de.metas.product.ProductCategoryId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.acct.base
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

@EqualsAndHashCode
@ToString
public class ProductCategoryAccounts
{
	@Getter
	@NonNull
	private final ProductCategoryId productCategoryId;
	@Getter
	@NonNull
	private final AcctSchemaId acctSchemaId;

	@Getter
	@Nullable
	private CostingLevel costingLevel;
	@Getter
	@Nullable
	private CostingMethod costingMethod;

	private final ImmutableMap<String, Optional<AccountId>> accountIdsByColumnName;

	@Builder
	private ProductCategoryAccounts(
			@NonNull final ProductCategoryId productCategoryId,
			@NonNull final AcctSchemaId acctSchemaId,
			@Nullable final CostingLevel costingLevel,
			@Nullable final CostingMethod costingMethod,
			@NonNull final Map<String, Optional<AccountId>> accountIdsByColumnName)
	{
		this.productCategoryId = productCategoryId;
		this.acctSchemaId = acctSchemaId;
		this.costingLevel = costingLevel;
		this.costingMethod = costingMethod;
		this.accountIdsByColumnName = ImmutableMap.copyOf(accountIdsByColumnName);
	}

	public Optional<AccountId> getAccountId(@NonNull final ProductAcctType acctType)
	{
		return accountIdsByColumnName.getOrDefault(acctType.getColumnName(), Optional.empty());
	}
}
