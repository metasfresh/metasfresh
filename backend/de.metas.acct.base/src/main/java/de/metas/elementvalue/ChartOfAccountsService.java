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

import de.metas.acct.api.ChartOfAccountsId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.tree.AdTreeId;
import org.adempiere.service.ClientId;
import org.compiere.model.I_AD_Tree;
import org.compiere.model.I_C_ElementValue;
import org.compiere.model.X_AD_Tree;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ChartOfAccountsService
{
	private final IADTableDAO adTreeDAO = Services.get(IADTableDAO.class);
	private final ChartOfAccountsRepository chartOfAccountsRepository;

	public ChartOfAccountsService(
			@NonNull final ChartOfAccountsRepository chartOfAccountsRepository)
	{
		this.chartOfAccountsRepository = chartOfAccountsRepository;
	}

	public ChartOfAccounts getById(@NonNull final ChartOfAccountsId chartOfAccountsId)
	{
		return chartOfAccountsRepository.getById(chartOfAccountsId);
	}

	public List<ChartOfAccounts> getByIds(@NonNull final Set<ChartOfAccountsId> chartOfAccountsIds)
	{
		return chartOfAccountsRepository.getByIds(chartOfAccountsIds);
	}

	public Optional<ChartOfAccounts> getByName(@NonNull final String chartOfAccountsName, @NonNull final ClientId clientId)
	{
		return chartOfAccountsRepository.getByName(chartOfAccountsName, clientId);
	}

	public Optional<ChartOfAccounts> getByTreeId(@NonNull final AdTreeId treeId)
	{
		return chartOfAccountsRepository.getByTreeId(treeId);
	}

	public ChartOfAccounts createChartOfAccounts(@NonNull final ChartOfAccountsCreateRequest request)
	{
		final AdTreeId chartOfAccountsTreeId = createChartOfAccountsTree(request.getName());
		return chartOfAccountsRepository.createChartOfAccounts(request.getName(), request.getClientId(), chartOfAccountsTreeId);
	}

	private AdTreeId createChartOfAccountsTree(@NonNull final String name)
	{
		final I_AD_Tree tree = InterfaceWrapperHelper.newInstance(I_AD_Tree.class);
		tree.setAD_Table_ID(adTreeDAO.retrieveTableId(I_C_ElementValue.Table_Name));
		tree.setName(name);
		tree.setTreeType(X_AD_Tree.TREETYPE_ElementValue);
		tree.setIsAllNodes(true);
		InterfaceWrapperHelper.save(tree);
		return AdTreeId.ofRepoId(tree.getAD_Tree_ID());
	}


}
