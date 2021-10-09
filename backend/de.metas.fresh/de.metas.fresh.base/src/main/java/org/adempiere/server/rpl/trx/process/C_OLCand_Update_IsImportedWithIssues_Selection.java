/*
 * #%L
 * de.metas.fresh.base
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

package org.adempiere.server.rpl.trx.process;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.process.rpl.model.I_C_OLCand;
import org.adempiere.process.rpl.model.I_EXP_ReplicationTrx;

public class C_OLCand_Update_IsImportedWithIssues_Selection extends C_OLCand_Update_IsImportedWithIssues_Base
{
	private ImmutableSet<Integer> replTrxIds;

	@Override
	protected IQueryBuilder<I_C_OLCand> createOLCandQueryBuilder()
	{
		final ImmutableSet<Integer> replTrxIds = getOrLoadSelectedRplTrxIDs();

		return queryBL.createQueryBuilder(I_C_OLCand.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_OLCand.COLUMNNAME_Processed, false)
				.addInArrayFilter(I_C_OLCand.COLUMNNAME_EXP_ReplicationTrx_ID, replTrxIds);
	}

	@Override
	protected ImmutableList<I_EXP_ReplicationTrx> extractRplTrxRecordsToSolve()
	{
		final ImmutableSet<Integer> replTrxIds = getOrLoadSelectedRplTrxIDs();
		return ImmutableList.copyOf(InterfaceWrapperHelper.loadByIds(replTrxIds, I_EXP_ReplicationTrx.class));
	}

	private ImmutableSet<Integer> getOrLoadSelectedRplTrxIDs()
	{
		if (replTrxIds == null)
		{
			replTrxIds = ImmutableSet.copyOf(queryBL.createQueryBuilder(I_C_OLCand.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_C_OLCand.COLUMNNAME_Processed, false)
					.filter(getProcessInfo().getQueryFilterOrElseFalse())
					.create()
					.listDistinct(I_C_OLCand.COLUMNNAME_EXP_ReplicationTrx_ID, Integer.class));
		}
		return replTrxIds;
	}
}
