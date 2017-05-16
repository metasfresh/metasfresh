package org.adempiere.ad.security.asp.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.concurrent.Immutable;

import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.ISqlQueryFilter;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.ad.security.asp.IASPFilters;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Form_Access;
import org.compiere.model.I_AD_Process_Access;
import org.compiere.model.I_AD_Process_Para;
import org.compiere.model.I_AD_Tab;
import org.compiere.model.I_AD_Task_Access;
import org.compiere.model.I_AD_Window_Access;
import org.compiere.model.I_AD_Workflow_Access;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

/**
 * Application Dictionary defined ASP filters.
 *
 * @author tsa
 * @see http://www.adempiere.com/ASP
 */
@Immutable
final class ASPFilters implements IASPFilters
{
	private final int adClientId;
	private final ImmutableMap<String, IQueryFilter<?>> accessTableName2aspFilter;

	public ASPFilters(final int adClientId)
	{
		super();
		this.adClientId = adClientId;

		final ImmutableMap.Builder<String, IQueryFilter<?>> aspFiltersBuilder = ImmutableMap.builder();

		aspFiltersBuilder.put(I_AD_Window_Access.Table_Name
				, TypedSqlQueryFilter.of("(   AD_Window_ID IN ( "
						// Just ASP subscribed windows for client "
						+ "              SELECT w.AD_Window_ID "
						+ "                FROM ASP_Window w, ASP_Level l, ASP_ClientLevel cl "
						+ "               WHERE w.ASP_Level_ID = l.ASP_Level_ID "
						+ "                 AND cl.AD_Client_ID = " + adClientId
						+ "                 AND cl.ASP_Level_ID = l.ASP_Level_ID "
						+ "                 AND w.IsActive = 'Y' "
						+ "                 AND l.IsActive = 'Y' "
						+ "                 AND cl.IsActive = 'Y' "
						+ "                 AND w.ASP_Status = 'S') " // Show
						+ "        OR AD_Window_ID IN ( "
						// + show ASP exceptions for client
						+ "              SELECT AD_Window_ID "
						+ "                FROM ASP_ClientException ce "
						+ "               WHERE ce.AD_Client_ID = " + adClientId
						+ "                 AND ce.IsActive = 'Y' "
						+ "                 AND ce.AD_Window_ID IS NOT NULL "
						+ "                 AND ce.AD_Tab_ID IS NULL "
						+ "                 AND ce.AD_Field_ID IS NULL "
						+ "                 AND ce.ASP_Status = 'S') " // Show
						+ "       ) "
						+ "   AND AD_Window_ID NOT IN ( "
						// minus hide ASP exceptions for client
						+ "          SELECT AD_Window_ID "
						+ "            FROM ASP_ClientException ce "
						+ "           WHERE ce.AD_Client_ID = " + adClientId
						+ "             AND ce.IsActive = 'Y' "
						+ "             AND ce.AD_Window_ID IS NOT NULL "
						+ "             AND ce.AD_Tab_ID IS NULL "
						+ "             AND ce.AD_Field_ID IS NULL "
						+ "             AND ce.ASP_Status = 'H')" // Hide
				));

		aspFiltersBuilder.put(I_AD_Process_Access.Table_Name
				, TypedSqlQueryFilter.of("(   AD_Process_ID IN ( "
						// Just ASP subscribed processes for client "
						+ "              SELECT p.AD_Process_ID "
						+ "                FROM ASP_Process p, ASP_Level l, ASP_ClientLevel cl "
						+ "               WHERE p.ASP_Level_ID = l.ASP_Level_ID "
						+ "                 AND cl.AD_Client_ID = " + adClientId
						+ "                 AND cl.ASP_Level_ID = l.ASP_Level_ID "
						+ "                 AND p.IsActive = 'Y' "
						+ "                 AND l.IsActive = 'Y' "
						+ "                 AND cl.IsActive = 'Y' "
						+ "                 AND p.ASP_Status = 'S') " // Show
						+ "        OR AD_Process_ID IN ( "
						// + show ASP exceptions for client
						+ "              SELECT AD_Process_ID "
						+ "                FROM ASP_ClientException ce "
						+ "               WHERE ce.AD_Client_ID = " + adClientId
						+ "                 AND ce.IsActive = 'Y' "
						+ "                 AND ce.AD_Process_ID IS NOT NULL "
						+ "                 AND ce.AD_Process_Para_ID IS NULL "
						+ "                 AND ce.ASP_Status = 'S') " // Show
						+ "       ) "
						+ "   AND AD_Process_ID NOT IN ( "
						// minus hide ASP exceptions for client
						+ "          SELECT AD_Process_ID "
						+ "            FROM ASP_ClientException ce "
						+ "           WHERE ce.AD_Client_ID = " + adClientId
						+ "             AND ce.IsActive = 'Y' "
						+ "             AND ce.AD_Process_ID IS NOT NULL "
						+ "             AND ce.AD_Process_Para_ID IS NULL "
						+ "             AND ce.ASP_Status = 'H')" // Hide
				));

		aspFiltersBuilder.put(I_AD_Task_Access.Table_Name
				, TypedSqlQueryFilter.of("(   AD_Task_ID IN ( "
						// Just ASP subscribed tasks for client "
						+ "              SELECT t.AD_Task_ID "
						+ "                FROM ASP_Task t, ASP_Level l, ASP_ClientLevel cl "
						+ "               WHERE t.ASP_Level_ID = l.ASP_Level_ID "
						+ "                 AND cl.AD_Client_ID = " + adClientId
						+ "                 AND cl.ASP_Level_ID = l.ASP_Level_ID "
						+ "                 AND t.IsActive = 'Y' "
						+ "                 AND l.IsActive = 'Y' "
						+ "                 AND cl.IsActive = 'Y' "
						+ "                 AND t.ASP_Status = 'S') " // Show
						+ "        OR AD_Task_ID IN ( "
						// + show ASP exceptions for client
						+ "              SELECT AD_Task_ID "
						+ "                FROM ASP_ClientException ce "
						+ "               WHERE ce.AD_Client_ID = " + adClientId
						+ "                 AND ce.IsActive = 'Y' "
						+ "                 AND ce.AD_Task_ID IS NOT NULL "
						+ "                 AND ce.ASP_Status = 'S') " // Show
						+ "       ) "
						+ "   AND AD_Task_ID NOT IN ( "
						// minus hide ASP exceptions for client
						+ "          SELECT AD_Task_ID "
						+ "            FROM ASP_ClientException ce "
						+ "           WHERE ce.AD_Client_ID = " + adClientId
						+ "             AND ce.IsActive = 'Y' "
						+ "             AND ce.AD_Task_ID IS NOT NULL "
						+ "             AND ce.ASP_Status = 'H')" // Hide
				));

		aspFiltersBuilder.put(I_AD_Form_Access.Table_Name
				, TypedSqlQueryFilter.of("(   AD_Form_ID IN ( "
						// Just ASP subscribed forms for client "
						+ "              SELECT f.AD_Form_ID "
						+ "                FROM ASP_Form f, ASP_Level l, ASP_ClientLevel cl "
						+ "               WHERE f.ASP_Level_ID = l.ASP_Level_ID "
						+ "                 AND cl.AD_Client_ID = " + adClientId
						+ "                 AND cl.ASP_Level_ID = l.ASP_Level_ID "
						+ "                 AND f.IsActive = 'Y' "
						+ "                 AND l.IsActive = 'Y' "
						+ "                 AND cl.IsActive = 'Y' "
						+ "                 AND f.ASP_Status = 'S') " // Show
						+ "        OR AD_Form_ID IN ( "
						// + show ASP exceptions for client
						+ "              SELECT AD_Form_ID "
						+ "                FROM ASP_ClientException ce "
						+ "               WHERE ce.AD_Client_ID = " + adClientId
						+ "                 AND ce.IsActive = 'Y' "
						+ "                 AND ce.AD_Form_ID IS NOT NULL "
						+ "                 AND ce.ASP_Status = 'S') " // Show
						+ "       ) "
						+ "   AND AD_Form_ID NOT IN ( "
						// minus hide ASP exceptions for client
						+ "          SELECT AD_Form_ID "
						+ "            FROM ASP_ClientException ce "
						+ "           WHERE ce.AD_Client_ID = " + adClientId
						+ "             AND ce.IsActive = 'Y' "
						+ "             AND ce.AD_Form_ID IS NOT NULL "
						+ "             AND ce.ASP_Status = 'H')" // Hide
				));

		aspFiltersBuilder.put(I_AD_Workflow_Access.Table_Name
				, TypedSqlQueryFilter.of("(   AD_Workflow_ID IN ( "
						// Just ASP subscribed workflows for client "
						+ "              SELECT w.AD_Workflow_ID "
						+ "                FROM ASP_Workflow w, ASP_Level l, ASP_ClientLevel cl "
						+ "               WHERE w.ASP_Level_ID = l.ASP_Level_ID "
						+ "                 AND cl.AD_Client_ID = " + adClientId
						+ "                 AND cl.ASP_Level_ID = l.ASP_Level_ID "
						+ "                 AND w.IsActive = 'Y' "
						+ "                 AND l.IsActive = 'Y' "
						+ "                 AND cl.IsActive = 'Y' "
						+ "                 AND w.ASP_Status = 'S') " // Show
						+ "        OR AD_Workflow_ID IN ( "
						// + show ASP exceptions for client
						+ "              SELECT AD_Workflow_ID "
						+ "                FROM ASP_ClientException ce "
						+ "               WHERE ce.AD_Client_ID = " + adClientId
						+ "                 AND ce.IsActive = 'Y' "
						+ "                 AND ce.AD_Workflow_ID IS NOT NULL "
						+ "                 AND ce.ASP_Status = 'S') " // Show
						+ "       ) "
						+ "   AND AD_Workflow_ID NOT IN ( "
						// minus hide ASP exceptions for client
						+ "          SELECT AD_Workflow_ID "
						+ "            FROM ASP_ClientException ce "
						+ "           WHERE ce.AD_Client_ID = " + adClientId
						+ "             AND ce.IsActive = 'Y' "
						+ "             AND ce.AD_Workflow_ID IS NOT NULL "
						+ "             AND ce.ASP_Status = 'H')" // Hide
				));

		aspFiltersBuilder.put(I_AD_Tab.Table_Name
				, TypedSqlQueryFilter.of(" (   AD_Tab_ID IN ( "
						// Just ASP subscribed tabs for client "
						+ "              SELECT t.AD_Tab_ID "
						+ "                FROM ASP_Tab t, ASP_Window w, ASP_Level l, ASP_ClientLevel cl "
						+ "               WHERE w.ASP_Level_ID = l.ASP_Level_ID "
						+ "                 AND cl.AD_Client_ID = " + adClientId
						+ "                 AND cl.ASP_Level_ID = l.ASP_Level_ID "
						+ "                 AND t.ASP_Window_ID = w.ASP_Window_ID "
						+ "                 AND t.IsActive = 'Y' "
						+ "                 AND w.IsActive = 'Y' "
						+ "                 AND l.IsActive = 'Y' "
						+ "                 AND cl.IsActive = 'Y' "
						+ "                 AND t.ASP_Status = 'S') " // Show
						+ "        OR AD_Tab_ID IN ( "
						// + show ASP exceptions for client
						+ "              SELECT AD_Tab_ID "
						+ "                FROM ASP_ClientException ce "
						+ "               WHERE ce.AD_Client_ID = " + adClientId
						+ "                 AND ce.IsActive = 'Y' "
						+ "                 AND ce.AD_Tab_ID IS NOT NULL "
						+ "                 AND ce.AD_Field_ID IS NULL "
						+ "                 AND ce.ASP_Status = 'S') " // Show
						+ "       ) "
						+ "   AND AD_Tab_ID NOT IN ( "
						// minus hide ASP exceptions for client
						+ "          SELECT AD_Tab_ID "
						+ "            FROM ASP_ClientException ce "
						+ "           WHERE ce.AD_Client_ID = " + adClientId
						+ "             AND ce.IsActive = 'Y' "
						+ "             AND ce.AD_Tab_ID IS NOT NULL "
						+ "             AND ce.AD_Field_ID IS NULL "
						+ "             AND ce.ASP_Status = 'H')" // Hide
				));

		// FIXME: get rid of "p." alias of AD_Process_Para.
		// Currently we need it because ProcessParameterPanelModel.createFields() is using it.
		// Else the AD_Process_Para_ID would be ambiguous when AD_Process_Param_Trl is used.
		aspFiltersBuilder.put(I_AD_Process_Para.Table_Name
				, TypedSqlQueryFilter.of("(   p.AD_Process_Para_ID IN ( "
						// Just ASP subscribed process parameters for client "
						+ "              SELECT pp.AD_Process_Para_ID "
						+ "                FROM ASP_Process_Para pp, ASP_Process p, ASP_Level l, ASP_ClientLevel cl "
						+ "               WHERE p.ASP_Level_ID = l.ASP_Level_ID "
						+ "                 AND cl.AD_Client_ID = " + adClientId
						+ "                 AND cl.ASP_Level_ID = l.ASP_Level_ID "
						+ "                 AND pp.ASP_Process_ID = p.ASP_Process_ID "
						+ "                 AND pp.IsActive = 'Y' "
						+ "                 AND p.IsActive = 'Y' "
						+ "                 AND l.IsActive = 'Y' "
						+ "                 AND cl.IsActive = 'Y' "
						+ "                 AND pp.ASP_Status = 'S') " // Show
						+ "        OR p.AD_Process_Para_ID IN ( "
						// + show ASP exceptions for client
						+ "              SELECT AD_Process_Para_ID "
						+ "                FROM ASP_ClientException ce "
						+ "               WHERE ce.AD_Client_ID = " + adClientId
						+ "                 AND ce.IsActive = 'Y' "
						+ "                 AND ce.AD_Process_Para_ID IS NOT NULL "
						+ "                 AND ce.AD_Tab_ID IS NULL "
						+ "                 AND ce.AD_Field_ID IS NULL "
						+ "                 AND ce.ASP_Status = 'S') " // Show
						+ "       ) "
						+ "   AND p.AD_Process_Para_ID NOT IN ( "
						// minus hide ASP exceptions for client
						+ "          SELECT AD_Process_Para_ID "
						+ "            FROM ASP_ClientException ce "
						+ "           WHERE ce.AD_Client_ID = " + adClientId
						+ "             AND ce.IsActive = 'Y' "
						+ "             AND ce.AD_Process_Para_ID IS NOT NULL "
						+ "             AND ce.AD_Tab_ID IS NULL "
						+ "             AND ce.AD_Field_ID IS NULL "
						+ "             AND ce.ASP_Status = 'H')" // Hide
				));

		accessTableName2aspFilter = aspFiltersBuilder.build();
	}

	@Override
	public <AccessTableType> IQueryFilter<AccessTableType> getFilter(final Class<AccessTableType> accessTableClass)
	{
		final String tableName = InterfaceWrapperHelper.getTableName(accessTableClass);

		@SuppressWarnings("unchecked")
		final IQueryFilter<AccessTableType> filter = (IQueryFilter<AccessTableType>)accessTableName2aspFilter.get(tableName);

		if (filter == null)
		{
			return ConstantQueryFilter.of(true);
		}
		return filter;
	}

	@Override
	public <AccessTableType> String getSQLWhereClause(final Class<AccessTableType> accessTableClass)
	{
		final IQueryFilter<AccessTableType> filter = getFilter(accessTableClass);
		if (filter instanceof ISqlQueryFilter)
		{
			final ISqlQueryFilter sqlFilter = ISqlQueryFilter.cast(filter);
			final String sqlWhereClause = sqlFilter.getSql();
			final List<Object> sqlParams = sqlFilter.getSqlParams(Env.getCtx());
			if (sqlParams != null && !sqlParams.isEmpty())
			{
				throw new AdempiereException("Cannot get SQL where clause for ASP filter because it contains parameters and this is not supported."
						+ "\n AccessTableClass: " + accessTableClass
						+ "\n ASP Filter: " + filter
						+ "\n SQL Where Clause: " + sqlWhereClause
						+ "\n SQL Params: " + sqlParams);
			}

			return " AND (" + sqlWhereClause + ") ";
		}
		else
		{
			throw new AdempiereException("Cannot get SQL where clause because the ASP filter is not an SQL filter"
					+ "\n AccessTableClass: " + accessTableClass
					+ "\n ASP Filter: " + filter);
		}
	}

	@Override
	public boolean isDisplayField(int adFieldId)
	{
		final List<Integer> fieldAccesses = fieldAccessesSupplier.get();
		return Collections.binarySearch(fieldAccesses, adFieldId) >= 0;
	}
	
	private final Supplier<List<Integer>> fieldAccessesSupplier = Suppliers.memoize(new Supplier<List<Integer>>()
	{
		@Override
		public List<Integer> get()
		{
			final List<Integer> fieldAccess = new ArrayList<Integer>(11000);
			final String sqlvalidate =
				"SELECT AD_Field_ID "
				 + "  FROM AD_Field "
				 + " WHERE (   AD_Field_ID IN ( "
				 // ASP subscribed fields for client
				 + "              SELECT f.AD_Field_ID "
				 + "                FROM ASP_Field f, ASP_Tab t, ASP_Window w, ASP_Level l, ASP_ClientLevel cl "
				 + "               WHERE w.ASP_Level_ID = l.ASP_Level_ID "
				 + "                 AND cl.AD_Client_ID = " + adClientId
				 + "                 AND cl.ASP_Level_ID = l.ASP_Level_ID "
				 + "                 AND f.ASP_Tab_ID = t.ASP_Tab_ID "
				 + "                 AND t.ASP_Window_ID = w.ASP_Window_ID "
				 + "                 AND f.IsActive = 'Y' "
				 + "                 AND t.IsActive = 'Y' "
				 + "                 AND w.IsActive = 'Y' "
				 + "                 AND l.IsActive = 'Y' "
				 + "                 AND cl.IsActive = 'Y' "
				 + "                 AND f.ASP_Status = 'S') "
				 + "        OR AD_Tab_ID IN ( "
				 // ASP subscribed fields for client
				 + "              SELECT t.AD_Tab_ID "
				 + "                FROM ASP_Tab t, ASP_Window w, ASP_Level l, ASP_ClientLevel cl "
				 + "               WHERE w.ASP_Level_ID = l.ASP_Level_ID "
				 + "                 AND cl.AD_Client_ID = " + adClientId
				 + "                 AND cl.ASP_Level_ID = l.ASP_Level_ID "
				 + "                 AND t.ASP_Window_ID = w.ASP_Window_ID "
				 + "                 AND t.IsActive = 'Y' "
				 + "                 AND w.IsActive = 'Y' "
				 + "                 AND l.IsActive = 'Y' "
				 + "                 AND cl.IsActive = 'Y' "
				 + "                 AND t.AllFields = 'Y' "
				 + "                 AND t.ASP_Status = 'S') "
				 + "        OR AD_Field_ID IN ( "
				 // ASP show exceptions for client
				 + "              SELECT AD_Field_ID "
				 + "                FROM ASP_ClientException ce "
				 + "               WHERE ce.AD_Client_ID = " + adClientId
				 + "                 AND ce.IsActive = 'Y' "
				 + "                 AND ce.AD_Field_ID IS NOT NULL "
				 + "                 AND ce.ASP_Status = 'S') "
				 + "       ) "
				 + "   AND AD_Field_ID NOT IN ( "
				 // minus ASP hide exceptions for client
				 + "          SELECT AD_Field_ID "
				 + "            FROM ASP_ClientException ce "
				 + "           WHERE ce.AD_Client_ID = " + adClientId
				 + "             AND ce.IsActive = 'Y' "
				 + "             AND ce.AD_Field_ID IS NOT NULL "
				 + "             AND ce.ASP_Status = 'H')" 
				 + " ORDER BY AD_Field_ID";
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = DB.prepareStatement(sqlvalidate, ITrx.TRXNAME_None);
				rs = pstmt.executeQuery();
				while (rs.next())
				{
					fieldAccess.add(rs.getInt(1));
				}
			}
			catch (Exception e)
			{
				throw new DBException(e, sqlvalidate);
			}
			finally
			{
				DB.close(rs, pstmt);
			}
			
			return ImmutableList.copyOf(fieldAccess);
		}
	});

}
