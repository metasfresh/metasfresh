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
import de.metas.process.Param;
import de.metas.util.Check;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.process.rpl.model.I_C_OLCand;
import org.adempiere.process.rpl.model.I_EXP_ReplicationTrx;

public class C_OLCand_Update_IsImportedWithIssues_SingleRplTrx extends C_OLCand_Update_IsImportedWithIssues_Base
{
	@Param(parameterName=I_C_OLCand.COLUMNNAME_EXP_ReplicationTrx_ID,mandatory = true)
	private int p_EXP_ReplicationTrx_ID;

	protected IQueryBuilder<I_C_OLCand> createOLCandQueryBuilder()
	{
		return queryBL.createQueryBuilder(I_C_OLCand.class, getCtx(), get_TrxName())
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_OLCand.COLUMNNAME_Processed, false)
				.addEqualsFilter(I_C_OLCand.COLUMNNAME_EXP_ReplicationTrx_ID, p_EXP_ReplicationTrx_ID);
	}

	protected ImmutableList<I_EXP_ReplicationTrx> extractRplTrxRecordsToSolve()
	{
		final I_EXP_ReplicationTrx rplTrxRecord = InterfaceWrapperHelper.create(getCtx(), p_EXP_ReplicationTrx_ID, I_EXP_ReplicationTrx.class, ITrx.TRXNAME_None);
		Check.assumeNotNull(rplTrxRecord, "@EXP_ReplicationTrx_ID@ exists for ID={}", p_EXP_ReplicationTrx_ID);

		return ImmutableList.of(rplTrxRecord);
	}
}
