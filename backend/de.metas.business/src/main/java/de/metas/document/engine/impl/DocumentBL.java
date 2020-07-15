package de.metas.document.engine.impl;

import java.time.LocalDate;
import java.util.Properties;

import org.adempiere.ad.persistence.TableModelClassLoader;
import org.adempiere.ad.persistence.TableModelLoader;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.util.DB;

import de.metas.document.engine.IDocument;
import de.metas.util.Services;

public final class DocumentBL extends AbstractDocumentBL
{
	@Override
	protected IDocument getLegacyDocumentOrNull(final Object documentObj, final boolean throwEx)
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

		final PO po;
		if (documentObj instanceof ITableRecordReference)
		{
			final Object model = ((ITableRecordReference)documentObj).getModel();
			po = InterfaceWrapperHelper.getPO(model);
		}
		else
		{
			po = InterfaceWrapperHelper.getPO(documentObj);
		}

		//
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
	public LocalDate getDocumentDate(Properties ctx, int adTableID, int recordId)
	{
		final Object model = retrieveModelOrNull(ctx, adTableID, recordId);
		return getDocumentDate(model);
	}
}
