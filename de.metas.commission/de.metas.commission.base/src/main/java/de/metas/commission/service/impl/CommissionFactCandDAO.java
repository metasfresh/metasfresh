package de.metas.commission.service.impl;

/*
 * #%L
 * de.metas.commission.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import de.metas.commission.exception.CommissionException;
import de.metas.commission.model.I_C_AdvCommissionFactCand;
import de.metas.commission.model.MCAdvCommissionFactCand;
import de.metas.commission.service.ICommissionFactCandDAO;

public class CommissionFactCandDAO implements ICommissionFactCandDAO
{
	private static final Logger logger = LogManager.getLogger(MCAdvCommissionFactCand.class);

	@Override
	public MCAdvCommissionFactCand retrieveNext(final Properties ctx, final String trxName)
	{
		final String whereClause =
				I_C_AdvCommissionFactCand.COLUMNNAME_IsSubsequentProcessingDone + "='N' AND "
						+ I_C_AdvCommissionFactCand.COLUMNNAME_IsError + "='N'";

		// We return the candidates so that candidates 'y' that have been scheduled by a candidate 'x' are returned directly
		// after 'x', even if there are other candidates that are older.
		// This effectively means that if a candidate 'x' schedules a number of candidates, these new candidates are not
		// appended to the queue, but rather inserted right after 'x'.
		final String orderBy =
				" COALESCE (" + I_C_AdvCommissionFactCand.COLUMNNAME_C_AdvComFactCand_Cause_ID + "," + I_C_AdvCommissionFactCand.COLUMNNAME_C_AdvCommissionFactCand_ID + "),"
						+ I_C_AdvCommissionFactCand.COLUMNNAME_C_AdvCommissionFactCand_ID;

		final MCAdvCommissionFactCand result =
				new Query(ctx, I_C_AdvCommissionFactCand.Table_Name, whereClause, trxName)
						.setOnlyActiveRecords(true)
						.setOrderBy(orderBy)
						.first();

		CommissionFactCandDAO.logger.info("Retrieved " + result);
		return result;
	}

	// Caching is not save, because the PO could be changed outside
	// @Cached(keyProperties = { "AD_Table_ID", "Record_ID" })
	@Override
	public PO retrievePOFromDB(final I_C_AdvCommissionFactCand cand)
	{
		final int tableId = cand.getAD_Table_ID();
		final int recordId = cand.getRecord_ID();

		final PO po = MTable.get(InterfaceWrapperHelper.getCtx(cand), tableId).getPO(recordId, InterfaceWrapperHelper.getTrxName(cand));

		if (po == null || po.get_ID() != recordId)
		{
			// TODO -> AD_Message
			throw CommissionException.inconsistentData(
					"Der referenzierte Datensatz existiert nicht mehr", this);
		}
		return po;
	}
}
