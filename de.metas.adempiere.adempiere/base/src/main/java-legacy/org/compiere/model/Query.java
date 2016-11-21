/*******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                        *
 * Copyright (C) 1999-2006 Adempiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it     *
 * under the terms version 2 of the GNU General Public License as published    *
 * by the Free Software Foundation. This program is distributed in the hope    *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied  *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.            *
 * See the GNU General Public License for more details.                        *
 * You should have received a copy of the GNU General Public License along     *
 * with this program; if not, write to the Free Software Foundation, Inc.,     *
 * 59 Temple Place, Suite 330, Boston, MA                                      *
 * 02111-1307 USA.                                                             *
 *                                                                             *
 * Copyright (C) 2007 Low Heng Sin hengsin@avantz.com                          *
 * Contributor(s):                                                             *
 *                 Teo Sarca, www.arhipac.ro                                   *
 * __________________________________________                                  *
 ******************************************************************************/
package org.compiere.model;

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.impl.TypedSqlQuery;
import org.adempiere.exceptions.DBException;

/**
 * 
 * @author Low Heng Sin
 * @author Teo Sarca, www.arhipac.ro <li>FR [ 1981760 ] Improve Query class <li>BF [ 2030280 ] org.compiere.model.Query apply access filter issue <li>FR [ 2041894 ] Add Query.match() method <li>FR [
 *         2107068 ] Query.setOrderBy should be more error tolerant <li>FR [ 2107109 ] Add method Query.setOnlyActiveRecords <li>FR [ 2421313 ] Introduce Query.firstOnly convenient method <li>FR [
 *         2546052 ] Introduce Query aggregate methods <li>FR [ 2726447 ] Query aggregate methods for all return types <li>FR [ 2818547 ] Implement Query.setOnlySelection
 *         https://sourceforge.net/tracker/?func=detail&aid=2818547&group_id=176962&atid=879335 <li>FR [ 2818646 ] Implement Query.firstId/firstIdOnly
 *         https://sourceforge.net/tracker/?func=detail&aid=2818646&group_id=176962&atid=879335
 * @author Redhuan D. Oon <li>FR: [ 2214883 ] Remove SQL code and Replace for Query // introducing SQL String prompt in log.info <li>FR: [ 2214883 ] - to introduce .setClient_ID
 */
public class Query extends TypedSqlQuery<Object>
{
	@SuppressWarnings("deprecation")
	public Query(Properties ctx, MTable table, String whereClause, String trxName)
	{
		super(ctx,
				Object.class, // modelClass 
				table == null ? null : table.getTableName(), // NOTE: an exception will be thrown if tableName is null
				whereClause,
				trxName);
	}

	@SuppressWarnings("deprecation")
	public Query(Properties ctx, String tableName, String whereClause, String trxName)
	{
		super(ctx, Object.class, tableName, whereClause, trxName);
	}

	@Override
	public Query setParameters(List<Object> parameters)
	{
		return (Query)super.setParameters(parameters);
	}

	@Override
	public Query setParameters(Object... parameters)
	{
		return (Query)super.setParameters(parameters);
	}

	@Override
	public Query setClient_ID()
	{
		return (Query)super.setClient_ID();
	}

	@Override
	public Query setOnlyActiveRecords(final boolean onlyActiveRecords)
	{
		return (Query)super.setOnlyActiveRecords(onlyActiveRecords);
	}

	@Override
	public Query setOrderBy(String orderBy)
	{
		return (Query)super.setOrderBy(orderBy);
	}

	@Override
	protected Query newInstance()
	{
		return new Query(getCtx(), getTableName(), getWhereClause(), getTrxName());
	}


	@Override
	public Query addWhereClause(final boolean joinByAnd, final String whereClause)
	{
		return (Query)super.addWhereClause(joinByAnd, whereClause);
	}

	@Override
	public <ET> List<ET> list() throws DBException
	{
		final List<ET> result = super.list();
		return result;
	}

	@Override
	public <ET> List<ET> list(Class<ET> clazz) throws DBException
	{
		final List<ET> result = super.list(clazz);
		return result;
	}

	@Override
	public Query setApplyAccessFilterRW(boolean RW)
	{
		return (Query)super.setApplyAccessFilterRW(RW);
	}

	@Override
	public Query setOption(String name, Object value)
	{
		return (Query)super.setOption(name, value);
	}

}
