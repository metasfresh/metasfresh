package de.metas.document.engine.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.ad.persistence.TableModelClassLoader;
import org.adempiere.ad.persistence.TableModelLoader;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.util.DB;

import de.metas.document.engine.DocumentHandler;
import de.metas.document.engine.DocumentHandlerProvider;
import de.metas.document.engine.DocumentWrapper;
import de.metas.document.engine.IDocument;

public final class DocumentBL extends AbstractDocumentBL
{
	@Override
	protected IDocument getDocument(final Object documentObj, final boolean throwEx)
	{
		if (documentObj == null)
		{
			if (throwEx)
			{
				throw new AdempiereException("document is null");
			}
			return null;
		}

		//
		if (documentObj instanceof IDocument)
		{
			return (IDocument)documentObj;
		}

		//
		final PO po = InterfaceWrapperHelper.getPO(documentObj);
		if (po == null)
		{
			if (throwEx)
			{
				throw new AdempiereException("Cannot extract " + PO.class + " from " + documentObj);
			}
			return null;
		}
		if (po instanceof IDocument)
		{
			return (IDocument)po;

		}

		//
		final String tableName = po.get_TableName();
		final DocumentHandlerProvider handlerProvider = getDocActionHandlerProviderByTableNameOrNull(tableName);
		if (handlerProvider != null)
		{
			final DocumentHandler handler = handlerProvider.provideForDocument(po);
			return DocumentWrapper.wrapModelUsingHandler(po, handler);
		}

		if (throwEx)
		{
			throw new AdempiereException("Cannot extract " + IDocument.class + " from " + documentObj);
		}
		return null;
	}

	@Override
	public boolean isDocumentTable(String tableName)
	{
		Class<?> clazz = TableModelClassLoader.instance.getClass(tableName);
		if (clazz == null)
		{
			return false;
		}

		if (!IDocument.class.isAssignableFrom(clazz))
		{
			return false;
		}

		return true;
	}

	@Override
	public int getC_DocType_ID(Properties ctx, int AD_Table_ID, int Record_ID)
	{
		if (AD_Table_ID <= 0 || Record_ID <= 0)
			return -1;

		final POInfo poInfo = POInfo.getPOInfo(ctx, AD_Table_ID);
		final String keyColumn = poInfo.getKeyColumnName();
		if (keyColumn == null)
		{
			return -1;
		}

		final String tableName = poInfo.getTableName();
		int C_DocType_ID = -1;
		if (poInfo.hasColumnName("C_DocType_ID"))
		{
			final String sql = "SELECT C_DocType_ID FROM " + tableName + " WHERE " + keyColumn + "=?";
			C_DocType_ID = DB.getSQLValueEx(ITrx.TRXNAME_None, sql, Record_ID);
		}
		if (C_DocType_ID <= 0 && poInfo.hasColumnName("C_DocTypeTarget_ID"))
		{
			final String sql = "SELECT C_DocTypeTarget_ID FROM " + tableName + " WHERE " + keyColumn + "=?";
			C_DocType_ID = DB.getSQLValueEx(ITrx.TRXNAME_None, sql, Record_ID);
		}
		if (C_DocType_ID <= 0)
		{
			C_DocType_ID = -1;
		}
		return C_DocType_ID;
	}

	@Override
	protected String retrieveString(final int adTableId, final int recordId, final String columnName)
	{
		if (adTableId <= 0 || recordId <= 0)
		{
			return null;
		}

		final POInfo poInfo = POInfo.getPOInfo(adTableId);
		if (poInfo == null)
		{
			throw new AdempiereException("No POInfo found for AD_Table_ID=" + adTableId);
		}
		
		final String keyColumn = poInfo.getKeyColumnName();
		if (keyColumn == null)
		{
			return null;
		}

		String value = null;

		if (poInfo.hasColumnName(columnName))
		{
			final String sql = "SELECT " + columnName + " FROM " + poInfo.getTableName() + " WHERE " + keyColumn + "=?";
			value = DB.getSQLValueStringEx(ITrx.TRXNAME_None, sql, recordId);
		}

		return value;
	}

	@Override
	protected Object retrieveModelOrNull(Properties ctx, int adTableId, int recordId)
	{
		final String tableName = Services.get(IADTableDAO.class).retrieveTableName(adTableId);
		final String trxName = ITrx.TRXNAME_None;
		return TableModelLoader.instance.getPO(ctx, tableName, recordId, trxName);
	}

	@Override
	public Timestamp getDocumentDate(Properties ctx, int adTableID, int recordId)
	{
		final Object model = retrieveModelOrNull(ctx, adTableID, recordId);
		return getDocumentDate(model);
	}
}
