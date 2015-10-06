/**
 * 
 */
package de.metas.adempiere.util;

/*
 * #%L
 * de.metas.swat.base
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


import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.POWrapper;
import org.compiere.model.MTable;
import org.compiere.model.MTree;
import org.compiere.model.Query;
import org.compiere.util.CLogger;

/**
 * @author tsa
 * 
 */
public class POTree<T>
{
//	private final CLogger log = CLogger.getCLogger(getClass());

	private final Class<T> nodeClass;
	private final int AD_Client_ID;
	private final String tableName;
	private final String keyColumnName;

	public POTree(Class<T> nodeClass, int AD_Client_ID)
	{
		this.nodeClass = nodeClass;
		this.AD_Client_ID = AD_Client_ID;
		this.tableName = POWrapper.getTableName(nodeClass);
		this.keyColumnName = this.tableName + "_ID"; // TODO
	}

	public int getAD_Tree_ID()
	{
		return MTree.getDefaultByTableName(AD_Client_ID, tableName);
	}

	public int getAD_Table_ID()
	{
		return MTable.getTable_ID(tableName);
	}

	private String getNodeTableName()
	{
		return MTree.getNodeTableName(getAD_Table_ID());
	}

	private int getId(T node)
	{
		return POWrapper.getPO(node).get_ValueAsInt(keyColumnName);
	}

	private int getUpdatedBy(T node)
	{
		return POWrapper.getPO(node).getUpdatedBy();
	}

	private String getTrxName(T node)
	{
		return POWrapper.getTrxName(node);
	}

	private CLogger getLogger(T node)
	{
		return POWrapper.getPO(node).get_Logger();
	}

	public Query getChildrenQuery(T parent)
	{
		final Properties ctx = POWrapper.getCtx(parent);
		final String trxName = POWrapper.getTrxName(parent);

		final int AD_Tree_ID = getAD_Tree_ID();
		final String nodeTableName = getNodeTableName();
		final String whereClause = keyColumnName + " IN ("
				+ "SELECT n.Node_ID FROM " + nodeTableName + " n WHERE n.AD_Tree_ID=? AND n.Parent_ID=?"
				+ ")";
		return new Query(ctx, tableName, whereClause, trxName)
				.setParameters(AD_Tree_ID, getId(parent))
				.setOrderBy(keyColumnName);
	}

	public List<T> getChildren(T parent)
	{
		List<T> list = getChildrenQuery(parent).list(nodeClass);
		return list;
	}

	public boolean hasChildren(T parent)
	{
		return getChildrenQuery(parent).match();
	}

	public T getParent(T node)
	{
		final Properties ctx = POWrapper.getCtx(node);
		final String trxName = POWrapper.getTrxName(node);

		final int nodeId = getId(node);
		final int AD_Tree_ID = getAD_Tree_ID();
		final String nodeTableName = getNodeTableName();
		final String whereClause = keyColumnName + " IN ("
				+ "SELECT n.Parent_ID FROM " + nodeTableName + " n WHERE n.AD_Tree_ID=? AND n.Node_ID=?"
				+ ")";
		T parent = new Query(ctx, tableName, whereClause, trxName)
				.setParameters(AD_Tree_ID, nodeId)
				.firstOnly(nodeClass);
		if (parent != null && getId(parent) == getId(node))
		{
			throw new AdempiereException("@InternalError@ Node " + node + " is parent to it self!");
		}
		return parent;
	}
}
