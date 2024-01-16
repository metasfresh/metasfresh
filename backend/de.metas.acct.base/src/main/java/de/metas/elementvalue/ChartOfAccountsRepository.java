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
import de.metas.acct.api.ChartOfAccountsId;
import de.metas.cache.CCache;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.tree.AdTreeId;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_Element;
import org.compiere.model.X_C_Element;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class ChartOfAccountsRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, ChartOfAccountsMap> cache = CCache.<Integer, ChartOfAccountsMap>builder()
			.tableName(I_C_Element.Table_Name)
			.build();

	private ChartOfAccountsMap getMap()
	{
		return cache.getOrLoad(0, this::retrieveMap);
	}

	private ChartOfAccountsMap retrieveMap()
	{
		return new ChartOfAccountsMap(
				queryBL.createQueryBuilder(I_C_Element.class)
						.addOnlyActiveRecordsFilter()
						.create()
						.stream()
						.map(ChartOfAccountsRepository::fromRecord)
						.collect(ImmutableList.toImmutableList()));
	}

	private static ChartOfAccounts fromRecord(@NonNull final I_C_Element record)
	{
		return ChartOfAccounts.builder()
				.id(ChartOfAccountsId.ofRepoId(record.getC_Element_ID()))
				.name(record.getName())
				.clientId(ClientId.ofRepoId(record.getAD_Client_ID()))
				.treeId(AdTreeId.ofRepoId(record.getAD_Tree_ID()))
				.build();
	}

	public ChartOfAccounts getById(final ChartOfAccountsId chartOfAccountsId)
	{
		return getMap().getById(chartOfAccountsId);
	}

	public List<ChartOfAccounts> getByIds(final Set<ChartOfAccountsId> chartOfAccountsIds)
	{
		return getMap().getByIds(chartOfAccountsIds);
	}

	public Optional<ChartOfAccounts> getByName(@NonNull final String chartOfAccountsName, @NonNull final ClientId clientId)
	{
		return getMap().getByName(chartOfAccountsName, clientId);
	}

	public Optional<ChartOfAccounts> getByTreeId(@NonNull final AdTreeId treeId)
	{
		return getMap().getByTreeId(treeId);
	}

	ChartOfAccounts createChartOfAccounts(
			@NonNull final String name,
			@NonNull final ClientId clientId,
			@NonNull final AdTreeId chartOfAccountsTreeId)
	{
		final I_C_Element record = InterfaceWrapperHelper.newInstance(I_C_Element.class);
		InterfaceWrapperHelper.setValue(record, I_C_Element.COLUMNNAME_AD_Client_ID, clientId.getRepoId());
		record.setAD_Org_ID(OrgId.ANY.getRepoId());
		record.setName(name);
		record.setAD_Tree_ID(chartOfAccountsTreeId.getRepoId());
		record.setElementType(X_C_Element.ELEMENTTYPE_Account);

		InterfaceWrapperHelper.save(record);

		return fromRecord(record);
	}
}
