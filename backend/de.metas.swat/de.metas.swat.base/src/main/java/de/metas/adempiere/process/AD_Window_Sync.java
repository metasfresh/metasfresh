/**
 *
 */
package de.metas.adempiere.process;

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


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.ad.persistence.EntityTypesCache;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.model.I_AD_Field;
import org.compiere.model.I_AD_Tab;
import org.compiere.model.Lookup;
import org.compiere.model.MField;
import org.compiere.model.MLookup;
import org.compiere.model.MTab;
import org.compiere.model.MWindow;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.util.DB;
import org.compiere.util.Util.ArrayKey;

import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

/**
 * @author tsa
 *
 */
public class AD_Window_Sync extends JavaProcess
{
	private int p_AD_Window_ID = -1;
	private int p_AD_WindowTo_ID = -1;
	private boolean p_IsTest = true;

//	/** Update ignore list: TableName -> list of ColumnName */
//	private Map<String, List<String>> ignoreList = new HashMap<String, List<String>>();

	private static class IdPair
	{
		@SuppressWarnings("unused")
		public String tableName = null;
		@SuppressWarnings("unused")
		public int idOld = -1;
		public int idNew = -1;

		public IdPair(String tableName, int idOld, int idNew)
		{
			super();
			this.tableName = tableName;
			this.idOld = idOld;
			this.idNew = idNew;
		}
	}

	private static class IdPairMap
	{
		private Map<ArrayKey, IdPair> map = new HashMap<ArrayKey, IdPair>();
		public void put(String tableName, int idOld, int idNew)
		{
			IdPair pair = new IdPair(tableName, idOld, idNew);
			ArrayKey key = new ArrayKey(tableName, idOld);
			map.put(key, pair);
		}
		public IdPair getIdPair(String tableName, int idOld)
		{
			ArrayKey key = new ArrayKey(tableName, idOld);
			return map.get(key);
		}
		public boolean hasNewId(String tableName, int idOld)
		{
			return getIdPair(tableName, idOld) != null;
		}
		public int getNewId(String tableName, int idOld)
		{
			IdPair pair = getIdPair(tableName, idOld);
			return pair == null ? -1 : pair.idNew;
		}
	}

	private final IdPairMap idsMap = new IdPairMap();
	/** Ignore columns: TableName -> list of ColumnNames */
	private final Map<String, List<String>> ignoreColumns = new HashMap<String, List<String>>();

	@Override
	protected void prepare()
	{
		for (ProcessInfoParameter para : getParametersAsArray())
		{
			String name = para.getParameterName();
			if (para.getParameter() == null)
				;
			else if (name.equals("AD_Window_ID"))
			{
				p_AD_Window_ID = para.getParameterAsInt();
				p_AD_WindowTo_ID = para.getParameter_ToAsInt();
			}
			else if (name.equals("IsTest"))
			{
				p_IsTest = para.getParameterAsBoolean();
			}
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		MWindow window = loadWindow(p_AD_Window_ID);
		MWindow windowTo = loadWindow(p_AD_WindowTo_ID);
		if (p_AD_Window_ID == p_AD_WindowTo_ID)
		{
			throw new AdempiereException("Window from and to are the same. Nothing to do");
		}

		addIgnoreColumn(I_AD_Tab.Table_Name, I_AD_Tab.COLUMNNAME_WhereClause);

		syncRecords(window, null, windowTo, true);

		if(p_IsTest)
		{
			addLog("Doing again to see if we have more differences:");
			syncRecords(window, null, windowTo, true);
		}

		if (p_IsTest)
		{
			throw new AdempiereException("WARNING: this was just a test. Nothing was changed");
		}

		return "OK";
	}

	private MWindow loadWindow(int AD_Window_ID)
	{
		if (AD_Window_ID <= 0)
			throw new FillMandatoryException("AD_Window_ID");

		MWindow window = new MWindow(getCtx(), AD_Window_ID, get_TrxName());
		if (window.getAD_Window_ID() != AD_Window_ID)
			throw new AdempiereException("@NotFound@ @AD_Window_ID@: " + AD_Window_ID);
		return window;
	}

	private void addIgnoreColumn(String tableName, String columnName)
	{
		List<String> columns = ignoreColumns.get(tableName);
		if (columns == null)
		{
			columns = new ArrayList<String>();
			ignoreColumns.put(tableName, columns);
		}
		if (!columns.contains(columnName))
			columns.add(columnName);
	}
	private boolean isIgnoreColumn(String tableName, String columnName)
	{
		List<String> columns = ignoreColumns.get(tableName);
		if (columns == null)
			return false;
		return columns.contains(columnName);
	}

	private PO[] getChildren(PO parent)
	{
		if (parent instanceof MWindow)
		{
			return ((MWindow)parent).getTabs(true, parent.get_TrxName());
		}
		else if (parent instanceof MTab)
		{
			return ((MTab)parent).getFields(true, parent.get_TrxName());
		}
		else
		{
			return new PO[]{};
		}
	}

	private void syncRecords(PO from, PO parentTo, PO to, boolean onlyChildren)
	{
		if (!onlyChildren)
		{
			if (to == null)
			{
				to = createRecord(parentTo, from);
			}
			else
			{
				copyValues(from, to);
			}
			saveLog(to);
		}
		idsMap.put(to.get_TableName(), from.get_ID(), to.get_ID());

		PO[] childrenFrom = getChildren(from);
		PO[] childrenTo = getChildren(to);

		// Delete records that are missing from source array
		for (int toIndex = 0; toIndex < childrenTo.length; toIndex++)
		{
			PO childTo = childrenTo[toIndex];
			int fromIndex = getLikeIndex(childTo, childrenFrom);
			if (fromIndex < 0)
			{
				deletePO(childTo);
				childrenTo[toIndex] = null;
			}
		}

		// Copy records
		for (PO childFrom : childrenFrom)
		{
			int toIndex = getLikeIndex(childFrom, childrenTo);
			PO childTo = toIndex >= 0 ? childrenTo[toIndex] : null;
			syncRecords(childFrom, to, childTo, false);
			if (toIndex >= 0)
				childrenTo[toIndex] = null;
		}
		deleteLog(childrenTo); // deleting remaining records
	}


	private void deletePO(PO po)
	{
		boolean doDelete = true;
		if (po.get_ColumnIndex("EntityType") >= 0)
		{
			final String entityType = po.get_ValueAsString("EntityType");
			doDelete = !EntityTypesCache.instance.isSystemMaintained(entityType);
		}
		if (doDelete)
		{
			deleteLog(po);
		}
		else
		{
			if (po instanceof I_AD_Field)
			{
				I_AD_Field field = (I_AD_Field)po;
				field.setIsDisplayed(false);
			}
			po.setIsActive(false);
			saveLog(po);
		}
	}

	private PO createRecord(PO parentTo, PO from)
	{
		if (from instanceof MTab)
		{
			MWindow windowTo = (MWindow)parentTo;
			MTab tab = (MTab)from;
			MTab tabTo = new MTab(windowTo.getCtx(), 0, windowTo.get_TrxName());
			copyValues(tab, tabTo);
			tabTo.setAD_Org_ID(windowTo.getAD_Org_ID());
			tabTo.setAD_Window_ID(windowTo.getAD_Window_ID());
			tabTo.setEntityType(windowTo.getEntityType());
			return tabTo;
		}
		else if (from instanceof MField)
		{
			MTab tabTo = (MTab)parentTo;
			MField field = (MField)from;
			MField fieldTo = new MField(tabTo.getCtx(), 0, tabTo.get_TrxName());
			copyValues(field, fieldTo);
			fieldTo.setAD_Org_ID(tabTo.getAD_Org_ID());
			fieldTo.setAD_Tab_ID(tabTo.getAD_Tab_ID());
			fieldTo.setEntityType(tabTo.getEntityType());
			return field;
		}
		else
		{
			return null;
		}
	}

	private <T extends PO> int getLikeIndex(T po, T[] arr)
	{
		if (arr == null || arr.length == 0)
			return -1;
		for (int i = 0; i < arr.length; i++)
		{
			T po2 = arr[i];
			if (po2 == null)
				continue;

			if (isSimilar(po, po2))
				return i;
		}
		return -1;
	}
	private boolean isSimilar(PO po1, PO po2)
	{
		if (po1 instanceof I_AD_Tab)
		{
			if (((I_AD_Tab)po1).getAD_Table_ID() == ((I_AD_Tab)po2).getAD_Table_ID())
				return true;
		}
		else if (po1 instanceof I_AD_Field)
		{
			if (((I_AD_Field)po1).getAD_Column_ID() == ((I_AD_Field)po2).getAD_Column_ID())
				return true;
		}
		return false;
	}

	private void copyValues(PO from, PO to)
	{
		PO.copyValues(from, to);
		to.setIsActive(from.isActive());
		final POInfo poInfo = POInfo.getPOInfo(to.getCtx(), to.get_Table_ID(), to.get_TrxName());
		final String tableName = poInfo.getTableName();
		for (int i = 0 ; i < poInfo.getColumnCount(); i++)
		{
			final String columnName = poInfo.getColumnName(i);
			if (to.is_new() && isIgnoreColumn(tableName, columnName))
			{
				Object valueOld = to.get_ValueOld(i);
				to.set_ValueNoCheck(columnName, valueOld);
				continue;
			}

			String baseTableName = getColumnBaseTableName(poInfo, i);
			if (baseTableName == null)
				continue;

			int idOld = to.get_ValueAsInt(i);
			if (idsMap.hasNewId(baseTableName, idOld))
			{
				int idNew = idsMap.getNewId(baseTableName, idOld);
				to.set_ValueNoCheck(to.get_ColumnName(i), idNew);
			}
		}
	}

	private String getColumnBaseTableName(POInfo poInfo, int index)
	{
		Lookup lookup = poInfo.getColumnLookup(getCtx(), index);
		if (lookup instanceof MLookup)
		{
			MLookup mlookup = (MLookup)lookup;
			return mlookup.getTableName();
		}
		return null;
	}

	private void saveLog(PO po)
	{
		boolean isNew = po.is_new();
		boolean isChanged = po.is_Changed();
		String changelog = "";
		if (!isNew && isChanged)
		{
			changelog = getChangeLog(po);
		}

		po.saveEx();

		if (isNew)
		{
			addLog("Created[" + po.get_TableName() + "=" + po.get_ID() + "]: " + getPOSummary(po));
		}
		else if (isChanged)
		{
			addLog("Updated[" + po.get_TableName() + "=" + po.get_ID() + "]: " + getPOSummary(po) + " - " + changelog);
		}
	}

	private String getChangeLog(PO po)
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < po.get_ColumnCount(); i++)
		{
			if (!po.is_ValueChanged(i))
				continue;

			String columnName = po.get_ColumnName(i);
			Object valueOld = po.get_ValueOld(i);
			Object valueNew = po.get_Value(i);

			if (sb.length() > 0)
				sb.append("; ");
			sb.append(columnName).append(":[").append(valueOld).append("]->[").append(valueNew).append("]");
		}
		return sb.toString();
	}

	private void deleteLog(PO[] arr)
	{
		if (arr == null || arr.length == 0)
			return;
		for (PO po : arr)
		{
			if (po != null)
				deleteLog(po);
		}
	}

	private void deleteLog(PO po)
	{
		if (po == null)
			return;

		String msg = "Deleted["
				+ po.get_TableName()
				+ "=" + po.get_ID()
				+ "]: " + getPOSummary(po);

		po.deleteEx(false);
		addLog(msg);
	}

	private String getPOSummary(PO po)
	{
		if (po == null)
		{
			return "";
		}
		else if (po instanceof I_AD_Field)
		{
			I_AD_Field field = (I_AD_Field)po;
			String sql = "SELECT tt.Name||'-'||f.Name FROM AD_Field f, AD_Tab tt WHERE tt.AD_Tab_ID=f.AD_Tab_ID AND f.AD_Field_ID=?";
			return DB.getSQLValueStringEx(po.get_TrxName(), sql, field.getAD_Field_ID());
		}
		else if (po instanceof I_AD_Tab)
		{
			return ((I_AD_Tab)po).getName();
		}
		else
		{
			return po.toString();
		}
	}
}
