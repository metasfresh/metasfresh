package de.metas.commission.model;

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


import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.DBUniqueConstraintException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.MBPartner;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.compiere.util.Env;
import org.compiere.util.TrxRunnable;

public class MCIncidentLineFact extends X_C_IncidentLineFact
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -572829886178045275L;

	private static final Logger logger = LogManager.getLogger(MCIncidentLineFact.class);

	public MCIncidentLineFact(final Properties ctx, final int C_AdvComProgressFact_ID, final String trxName)
	{
		super(ctx, C_AdvComProgressFact_ID, trxName);
	}

	public MCIncidentLineFact(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	/**
	 * 
	 * @param poLine
	 * @param bPartner
	 * @param cand only used if a new fact is created
	 * @param dateDoc
	 * @param incidentLine
	 * 
	 * @return
	 */
	public static I_C_IncidentLineFact retrieveOrCreate(
			final Object poLine,
			final MBPartner bPartner,
			final I_C_AdvCommissionFactCand cand,
			final Timestamp dateDoc,
			final I_C_IncidentLine incidentLine)
	{
		final StringBuilder wc = new StringBuilder();
		wc.append(I_C_IncidentLineFact.COLUMNNAME_AD_Table_ID).append("=? ");
		wc.append(" AND ");
		wc.append(I_C_IncidentLineFact.COLUMNNAME_Record_ID).append("=? ");
		wc.append(" AND ");
		wc.append(I_C_IncidentLineFact.COLUMNNAME_AD_Client_ID).append("=? ");

		final Object[] parameters = {
				InterfaceWrapperHelper.getModelTableId(poLine), InterfaceWrapperHelper.getId(poLine), Env.getAD_Client_ID(InterfaceWrapperHelper.getCtx(poLine, true))
		};

		final Properties ctx = InterfaceWrapperHelper.getCtx(poLine);
		final String trxName = InterfaceWrapperHelper.getTrxName(poLine);

		I_C_IncidentLineFact existingRecord =
				new Query(ctx, I_C_IncidentLineFact.Table_Name, wc.toString(), trxName)
						.setParameters(parameters)
						.firstOnly(I_C_IncidentLineFact.class);

		if (existingRecord != null)
		{
			if (existingRecord.getC_IncidentLine_ID() != incidentLine.getC_IncidentLine_ID())
			{
				// ignore existing record, because it belongs to another incident line. Example: one C_AllocationHdr can
				// belong to multiple C_OrderLines
				existingRecord = null;
			}
			else if (!existingRecord.getDateDoc().equals(dateDoc))
			{
				MCIncidentLineFact.logger.info("Changing DateDoc of " + existingRecord + " to " + dateDoc);
				existingRecord.setDateDoc(dateDoc);
				InterfaceWrapperHelper.save(existingRecord);
			}
			return existingRecord;
		}

		final I_C_IncidentLineFact[] newRecord = new I_C_IncidentLineFact[1];
		try
		{
			final ITrxManager trxManager = Services.get(ITrxManager.class);
			trxManager.run(trxName, new TrxRunnable()
			{
				@Override
				public void run(final String innerTrxName)
				{
					newRecord[0] = InterfaceWrapperHelper.newInstance(I_C_IncidentLineFact.class, poLine, true);

					newRecord[0].setAD_Table_ID(InterfaceWrapperHelper.getModelTableId(poLine));
					newRecord[0].setRecord_ID(InterfaceWrapperHelper.getId(poLine));

					newRecord[0].setC_BPartner_ID(bPartner.get_ID());
					newRecord[0].setDateDoc(dateDoc);
					newRecord[0].setC_AdvCommissionFactCand_ID(cand.getC_AdvCommissionFactCand_ID());
					newRecord[0].setC_AdvCommissionRelevantPO_ID(cand.getC_AdvCommissionRelevantPO_ID());
					newRecord[0].setC_IncidentLine_ID(incidentLine.getC_IncidentLine_ID());
					InterfaceWrapperHelper.save(newRecord[0]);
				}
			});
		}
		catch (final DBUniqueConstraintException e)
		{
			// assuming that there was an exception because the record has been created by someone else
			newRecord[0] = new Query(ctx, I_C_IncidentLineFact.Table_Name, wc.toString(), trxName)
					.setParameters(parameters)
					.firstOnly(I_C_IncidentLineFact.class);
			if (newRecord[0] == null)
			{
				throw e;
			}
		}
		return newRecord[0];
	}

	@Cached(keyProperties = { "AD_Table_ID", "Record_ID" })
	public PO retrievePO()
	{
		final PO result = MTable.get(getCtx(), getAD_Table_ID()).getPO(getRecord_ID(), get_TrxName());
		if (result == null)
		{
			MCIncidentLineFact.logger.warn("Referenced PO is gone. Returning null, and deleting this record (" + this + ")");
			this.deleteEx(false);
		}
		return result;
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder();

		sb.append("MCIncidentLineFact[Id=");
		sb.append(get_ID());
		sb.append(", AD_Table_ID=");
		sb.append(getAD_Table_ID());
		sb.append(", Record_ID=");
		sb.append(getRecord_ID());
		sb.append(", DateDoc=");
		sb.append(getDateDoc());
		sb.append(", C_IncidentLine_ID=");
		sb.append(getC_IncidentLine_ID());
		sb.append("]");

		return sb.toString();
	}

	/**
	 * Note: currently, {@link #retrievePO()} can delete a MCIncidentLineFact instance, so caching this method is not a good idea.
	 * 
	 * @param line
	 * @return
	 */
	public static List<MCIncidentLineFact> retrieveForLine(final MCIncidentLine line)
	{
		final String whereClause = I_C_IncidentLineFact.COLUMNNAME_C_IncidentLine_ID + "=?";

		final String orderBy = I_C_IncidentLineFact.COLUMNNAME_C_IncidentLineFact_ID;

		return new Query(line.getCtx(), I_C_IncidentLineFact.Table_Name, whereClause, line.get_TrxName())
				.setParameters(line.get_ID())
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.setOrderBy(orderBy)
				.list();
	}
}
