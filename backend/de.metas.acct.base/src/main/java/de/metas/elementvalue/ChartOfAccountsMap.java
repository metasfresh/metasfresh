/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.elementvalue;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.acct.api.ChartOfAccountsId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.tree.AdTreeId;
import org.adempiere.service.ClientId;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

final class ChartOfAccountsMap
{
	private final ImmutableList<ChartOfAccounts> list;
	private final ImmutableMap<ChartOfAccountsId, ChartOfAccounts> byId;

	ChartOfAccountsMap(@NonNull final List<ChartOfAccounts> list)
	{
		this.list = ImmutableList.copyOf(list);
		this.byId = Maps.uniqueIndex(list, ChartOfAccounts::getId);
	}

	public ChartOfAccounts getById(@NonNull final ChartOfAccountsId chartOfAccountsId)
	{
		ChartOfAccounts chartOfAccounts = byId.get(chartOfAccountsId);
		if (chartOfAccounts == null)
		{
			throw new AdempiereException("No Chart of Accounts found for " + chartOfAccountsId);
		}
		return chartOfAccounts;
	}

	public List<ChartOfAccounts> getByIds(@NonNull final Set<ChartOfAccountsId> chartOfAccountsIds)
	{
		return chartOfAccountsIds.stream()
				.map(this::getById)
				.collect(ImmutableList.toImmutableList());
	}

	public Optional<ChartOfAccounts> getByName(@NonNull final String chartOfAccountsName, @NonNull final ClientId clientId)
	{
		return list.stream()
				.filter(matchByName(chartOfAccountsName, clientId))
				.findFirst();
	}

	private static Predicate<ChartOfAccounts> matchByName(@NonNull final String chartOfAccountsName, @NonNull final ClientId clientId)
	{
		return chartOfAccounts ->
				Objects.equals(chartOfAccounts.getName(), chartOfAccountsName)
						&& ClientId.equals(chartOfAccounts.getClientId(), clientId);
	}

	public Optional<ChartOfAccounts> getByTreeId(@NonNull final AdTreeId treeId)
	{
		return list.stream()
				.filter(coa -> AdTreeId.equals(coa.getTreeId(), treeId))
				.findFirst();
	}
}
