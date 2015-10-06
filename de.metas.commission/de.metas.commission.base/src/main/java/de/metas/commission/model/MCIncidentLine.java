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

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.Query;

import de.metas.commission.exception.CommissionException;
import de.metas.commission.interfaces.I_C_BPartner;

public class MCIncidentLine extends X_C_IncidentLine
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7780013390465672651L;

	public MCIncidentLine(final Properties ctx, final int C_AdvComProgress_ID, final String trxName)
	{
		super(ctx, C_AdvComProgress_ID, trxName);
	}

	public MCIncidentLine(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	public static I_C_IncidentLine retrieveOrCreate(final Object poLine)
	{
		final MCIncidentLine existingRecord = retrieve(poLine, false);

		if (existingRecord != null)
		{
			return existingRecord;
		}

		final I_C_IncidentLine newRecord = InterfaceWrapperHelper.newInstance(I_C_IncidentLine.class, poLine, true);
		InterfaceWrapperHelper.save(newRecord);

		return newRecord;
	}

	public static MCIncidentLine retrieve(final Object poLine)
	{
		return retrieve(poLine, true);
	}

	private static MCIncidentLine retrieve(final Object poLine, final boolean throwEx)
	{
		final String colAdTableId = I_C_IncidentLineFact.COLUMNNAME_AD_Table_ID;
		final String colRecordId = I_C_IncidentLineFact.COLUMNNAME_Record_ID;

		final StringBuilder wc = new StringBuilder();
		wc.append(" EXISTS ( select *");
		wc.append(" from C_IncidentLineFact f ");
		wc.append(" where ");
		wc.append(" f.").append(colAdTableId).append("=? ");
		wc.append(" and ");
		wc.append(" f.").append(colRecordId).append("=? ");
		wc.append(" and ");
		wc.append(" f.C_IncidentLine_ID=C_IncidentLine.C_IncidentLine_ID");
		wc.append(" ) ");

		final Object[] parameters = { InterfaceWrapperHelper.getModelTableId(poLine), InterfaceWrapperHelper.getId(poLine) };

		final MCIncidentLine existingRecord =
				new Query(InterfaceWrapperHelper.getCtx(poLine), I_C_IncidentLine.Table_Name, wc.toString(), InterfaceWrapperHelper.getTrxName(poLine))
						.setParameters(parameters)
						.setClient_ID()
						.setOnlyActiveRecords(true)
						.firstOnly();

		if (existingRecord == null && throwEx)
		{
			throw CommissionException.inconsistentData("Missing commisison incident record for given PO", poLine);
		}

		return existingRecord;
	}

	/**
	 * Loads those incident lines (ordered by C_IncidentLine_ID) that have a least one fact with
	 * <ul>
	 * <li>The given <code>bPartner</code>'s ID</li>
	 * <li>DateDoc after the given <code>dateFrom</code></li>
	 * </ul>
	 * 
	 * @param bPartner
	 * @param dateFrom
	 * @return
	 */
	public static List<MCIncidentLine> retrieveForBPartner(
			final I_C_BPartner bPartner,
			final Timestamp dateFrom)
	{

		final Properties ctx = InterfaceWrapperHelper.getCtx(bPartner);
		final String trxName = InterfaceWrapperHelper.getTrxName(bPartner);

		final StringBuilder wc = new StringBuilder();
		wc.append(" EXISTS ( select *");
		wc.append(" from C_IncidentLineFact f ");
		wc.append(" where ");
		wc.append(" f.C_BPartner_ID=?");
		wc.append(" and ");
		wc.append(" f.DateDoc>=? ");
		wc.append(" and ");
		wc.append(" f.C_IncidentLine_ID=C_IncidentLine.C_IncidentLine_ID");
		wc.append(" ) ");

		final Object[] params = { bPartner.getC_BPartner_ID(), dateFrom };

		final String orderBy = I_C_IncidentLine.COLUMNNAME_C_IncidentLine_ID;

		return new Query(ctx, I_C_IncidentLine.Table_Name, wc.toString(), trxName)
				.setParameters(params)
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.setOrderBy(orderBy)
				.list();
	}
}
