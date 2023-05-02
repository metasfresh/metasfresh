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
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.tree.AdTreeId;
import org.adempiere.model.tree.spi.impl.DefaultPOTreeSupport;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_ElementValue;
import org.compiere.model.MTree_Base;
import org.compiere.model.PO;

import java.util.Optional;

public class MElementValueTreeSupport extends DefaultPOTreeSupport
{
	private ChartOfAccountsService _chartOfAccountsService; // lazy

	@Override
	public int getAD_Tree_ID(final PO po)
	{
		if (!InterfaceWrapperHelper.isInstanceOf(po, I_C_ElementValue.class))
		{
			return -1;
		}
		final I_C_ElementValue ev = InterfaceWrapperHelper.create(po, I_C_ElementValue.class);
		final ChartOfAccountsId chartOfAccountsId = ChartOfAccountsId.ofRepoId(ev.getC_Element_ID());

		final AdTreeId chartOfAccountsTreeId = chartOfAccountsService().getById(chartOfAccountsId).getTreeId();
		return chartOfAccountsTreeId.getRepoId();
	}

	private ChartOfAccountsService chartOfAccountsService()
	{
		ChartOfAccountsService chartOfAccountsService = this._chartOfAccountsService;
		if (chartOfAccountsService == null)
		{
			chartOfAccountsService = this._chartOfAccountsService = SpringContextHolder.instance.getBean(ChartOfAccountsService.class);
		}
		return chartOfAccountsService;
	}

	@Override
	public String getWhereClause(final MTree_Base tree)
	{
		final ChartOfAccountsId chartOfAccountsId = getChartOfAccountsId(tree).orElse(null);
		if (chartOfAccountsId == null)
		{
			// the tree is not yet referenced from any C_Element record. maybe it was just created
			return "0=1";
		}
		else
		{
			return I_C_ElementValue.COLUMNNAME_C_Element_ID + "=" + chartOfAccountsId.getRepoId();
		}
	}

	private Optional<ChartOfAccountsId> getChartOfAccountsId(final MTree_Base tree)
	{
		return chartOfAccountsService()
				.getByTreeId(AdTreeId.ofRepoId(tree.getAD_Tree_ID()))
				.map(ChartOfAccounts::getId);
	}
}
