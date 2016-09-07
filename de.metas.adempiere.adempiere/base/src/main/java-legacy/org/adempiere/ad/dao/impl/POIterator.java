/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 * Copyright (C) 2007 Low Heng Sin hengsin@avantz.com                         * 
 * Contributor(s):                                                            *
 * __________________________________________                                 *
 *****************************************************************************/
package org.adempiere.ad.dao.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.persistence.TableModelLoader;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.PO;
import org.compiere.model.POInfo;

/**
 * Iterator implementation to fetch PO one at a time using a prefetch ID list.
 * 
 * @author Low Heng Sin
 *
 */
class POIterator<T> implements Iterator<T>
{

	private final Properties ctx;
	private final String tableName;
	private final Class<T> clazz;
	private final List<Object[]> idList;

	private int iteratorIndex = -1;
	private String trxName;

	private String _keysWhereClause = null;

	/**
	 * @param table
	 * @param idList
	 * @param trxName
	 */
	public POIterator(final Properties ctx, final String tableName, Class<T> clazz, List<Object[]> idList, String trxName)
	{
		super();
		this.ctx = ctx;
		this.tableName = tableName;
		this.clazz = clazz;
		this.idList = idList;
		this.trxName = trxName;
	}

	/**
	 * @see java.util.Iterator#hasNext()
	 */
	@Override
	public boolean hasNext()
	{
		return (iteratorIndex < (idList.size() - 1));
	}

	/**
	 * @see java.util.Iterator#next()
	 */
	@Override
	public T next()
	{
		if (iteratorIndex < (idList.size() - 1))
		{
			iteratorIndex++;
			return get(iteratorIndex);
		}
		else
		{
			return null;
		}
	}

	/**
	 * not supported.
	 */
	@Override
	public void remove()
	{
		throw new UnsupportedOperationException("Remove operation not supported.");
	}

	/**
	 * @return number of records
	 */
	public int size()
	{
		return idList.size();
	}

	/**
	 * @param index
	 * @return PO or null if index is invalid
	 */
	@SuppressWarnings("unchecked")
	public T get(int index)
	{
		if (index <= (idList.size() - 1))
		{
			final Object[] ids = idList.get(index);
			if (ids.length == 1 && (ids[0] instanceof Number))
			{
				final int recordId = ((Number)ids[0]).intValue();
				final PO o = TableModelLoader.instance.getPO(ctx, tableName, recordId, trxName);
				if (clazz != null && !o.getClass().isAssignableFrom(clazz))
				{
					return InterfaceWrapperHelper.create(o, clazz);
				}
				else
				{
					return (T)o;
				}
			}
			else
			{
				final String keyWhereClause = getKeysWhereClause();
				final PO o = TableModelLoader.instance.getPO(ctx, tableName, keyWhereClause, ids, trxName);
				if (clazz != null && !o.getClass().isAssignableFrom(clazz))
				{
					return InterfaceWrapperHelper.create(o, clazz);
				}
				else
				{
					return (T)o;
				}
			}
		}
		else
		{
			return null;
		}
	}

	private final String getKeysWhereClause()
	{
		if (_keysWhereClause == null)
		{
			final StringBuffer sqlBuffer = new StringBuffer();

			final List<String> keyColumnNames = POInfo.getPOInfo(tableName).getKeyColumnNames();
			for (final String keyColumnName : keyColumnNames)
			{
				if (sqlBuffer.length() > 0)
				{
					sqlBuffer.append(" AND ");
				}
				sqlBuffer.append(keyColumnName).append(" = ? ");
			}
			_keysWhereClause = sqlBuffer.toString();
		}

		return _keysWhereClause;
	}
}
