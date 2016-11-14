package de.metas.commission.process;

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


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.util.time.SystemTime;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

import de.metas.commission.model.I_C_AdvCommissionRelevantPO;
import de.metas.commission.model.MCAdvComRelevantPOType;
import de.metas.commission.model.MCAdvCommissionFactCand;
import de.metas.commission.model.MCAdvCommissionRelevantPO;

public class BuildRelevantPOQueue extends SvrProcess
{

	private int orgId;

	private Timestamp dateFrom;

	private Timestamp dateTo;

	@Override
	protected String doIt() throws Exception
	{

		final Collection<MCAdvCommissionRelevantPO> relevantPOs = MCAdvCommissionRelevantPO
				.retrieveAll(get_TrxName());

		addLog(0, SystemTime.asTimestamp(), null,
				"Going to create candidates for " + relevantPOs.size() + " "
						+ I_C_AdvCommissionRelevantPO.Table_Name + " record(s)");

		int counter = 0;

		for (final MCAdvCommissionRelevantPO relevantPO : relevantPOs)
		{
			final List<MCAdvComRelevantPOType> relPOTypes = MCAdvComRelevantPOType.retrieveFor(relevantPO);

			if (relPOTypes.isEmpty())
			{
				// nothing to to as there are no types to evaluate the candidate
				continue;
			}

			final String tableName = relevantPO.getTableName();

			final List<Object> params = new ArrayList<Object>();
			final String whereClause = setupQuery(relevantPO, params);

			final List<PO> pos =
					new Query(getCtx(), tableName, whereClause, get_TrxName())
							.setParameters(params)
							.setClient_ID()
							.list();

			for (final PO po : pos)
			{
				MCAdvCommissionFactCand.createOrUpdate(po, relevantPO);

				counter += 1;
				if (counter % 100 == 0)
				{
					commitEx();
				}
			}
			addLog(0, SystemTime.asTimestamp(), null, "Created or reset " + counter + " candidates for " + relevantPO);
		}
		return "@Success@";
	}

	@Override
	protected void prepare()
	{

		final ProcessInfoParameter[] para = getParametersAsArray();

		for (int i = 0; i < para.length; i++)
		{

			final String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
			{
				;
			}
			else if (name.equals("AD_Org_ID"))
			{

				orgId = ((BigDecimal)para[i].getParameter()).intValue();

			}
			else if (name.equals("DateFrom"))
			{

				dateFrom = (Timestamp)para[i].getParameter();

			}
			else if (name.equals("DateTo"))
			{

				dateTo = (Timestamp)para[i].getParameter();

			}
			else
			{

				log.error("Unknown Parameter: " + name);
			}
		}
	}

	private String setupQuery(final MCAdvCommissionRelevantPO relevantPO,
			final List<Object> params)
	{

		final StringBuilder whereClause = new StringBuilder();

		final String sqlWhere = relevantPO.getSQLWhere();

		if (sqlWhere != null)
		{
			whereClause.append(" ( ");
			whereClause.append(sqlWhere);
			whereClause.append(" ) ");
		}
		if (orgId > 0)
		{

			if (whereClause.length() > 0)
			{
				whereClause.append(" AND ");
			}
			whereClause.append(relevantPO.getTableName());
			whereClause.append(".");
			whereClause
					.append(I_C_AdvCommissionRelevantPO.COLUMNNAME_AD_Org_ID);
			whereClause.append("=?");

			params.add(orgId);
		}

		final String dateDocColumn = relevantPO.getDateDocColumn()
				.getColumnName();

		if (dateFrom != null)
		{
			if (whereClause.length() > 0)
			{
				whereClause.append(" AND ");
			}
			whereClause.append(relevantPO.getTableName());
			whereClause.append(".");
			whereClause.append(dateDocColumn);
			whereClause.append(">=?");

			params.add(dateFrom);
		}

		if (dateTo != null)
		{
			if (whereClause.length() > 0)
			{
				whereClause.append(" AND ");
			}
			whereClause.append(relevantPO.getTableName());
			whereClause.append(".");
			whereClause.append(dateDocColumn);
			whereClause.append("<=?");

			params.add(dateTo);
		}

		return whereClause.toString();
	}

}
