package de.metas.web.component.trl;

/*
 * #%L
 * de.metas.swat.zkwebui
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
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.service.IADMessageDAO;
import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.ad.service.IDeveloperModeBL.ContextRunnable;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Element;
import org.compiere.model.I_AD_Message;
import org.compiere.model.M_Element;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.model.Query;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.compiere.util.CacheMgt;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.zkoss.lang.Objects;

/* package */class TrlElementHelper
{
	private static final Logger logger = LogManager.getLogger(TrlElementHelper.class);

	/**
	 * Create {@link TrlElement} from given PO.
	 * 
	 * @param ctx context
	 * @param valueColumnName record string identifier column name (e.g. in case of AD_Message the string identifier columnname is Value)
	 * @param po
	 * @return list of {@link TrlElementItem}s
	 */
	private static TrlElement createTrlElement(final Properties ctx, final String valueColumnName, final Object model)
	{
		final PO po = InterfaceWrapperHelper.getPO(model);
		final POInfo poInfo = POInfo.getPOInfo(ctx, po.get_Table_ID());

		final TrlElement trlElement = new TrlElement();
		trlElement.setTableName(po.get_TableName());
		trlElement.setRecordId(po.get_ID());
		trlElement.setName(po.get_ValueAsString(valueColumnName));
		trlElement.setDescription(getInfoString(po));

		for (int i = 0, size = poInfo.getColumnCount(); i < size; i++)
		{
			if (poInfo.isColumnTranslated(i))
			{
				final String columnName = poInfo.getColumnName(i);
				final String value = po.get_ValueAsString(columnName);
				final TrlElementItem item = new TrlElementItem();
				item.setColumnName(columnName);
				item.setValueOld(value);
				item.setValue(value);
				trlElement.getItems().add(item);
			}
		}

		return trlElement;
	}

	private static String getInfoString(final PO po)
	{
		if (po == null)
		{
			return "";
		}

		final StringBuffer sb = new StringBuffer();

		final int id = po.get_ID();
		final int entityTypeIdx = po.get_ColumnIndex("EntityType");
		final String entityType = entityTypeIdx >= 0 ? po.get_ValueAsString("EntityType") : null;

		sb.append(po.toString());
		if (id >= 0)
		{
			sb.append(", ID: " + id);
		}
		if (entityType != null)
		{
			sb.append(", EntityType: " + entityType);
		}

		sb.append(", Created=").append(po.getCreated());
		sb.append(", Updated=").append(po.getUpdated());

		return sb.toString();
	}

	public static List<TrlElement> parseAndCreateTrlElements(final Properties ctx, final String text)
	{
		final List<TrlElement> elements = new ArrayList<TrlElement>();
		if (text != null && text.indexOf("@") >= 0)
		{
			return parseElements(ctx, text);
		}
		else if (text != null && text.indexOf(" ") < 0)
		{
			elements.addAll(createTrlElementsForToken(ctx, text));
			return elements;
		}

		return elements;
	}

	private static List<TrlElement> parseElements(final Properties ctx, final String text)
	{
		final List<TrlElement> elements = new ArrayList<TrlElement>();

		if (text == null || text.length() == 0)
		{
			return elements;
		}

		String inStr = text;
		String token;
		// StringBuffer outStr = new StringBuffer();

		int i = inStr.indexOf('@');
		while (i != -1)
		{
			// outStr.append(inStr.substring(0, i)); // up to @
			inStr = inStr.substring(i + 1, inStr.length()); // from first @

			final int j = inStr.indexOf('@'); // next @
			if (j < 0) // no second tag
			{
				inStr = "@" + inStr;
				break;
			}

			token = inStr.substring(0, j);

			elements.addAll(createTrlElementsForToken(ctx, token));
			// outStr.append(translate(ctx, token)); // replace context

			inStr = inStr.substring(j + 1, inStr.length()); // from second @
			i = inStr.indexOf('@');
		}

		// outStr.append(inStr); // add remainder
		// return outStr.toString();
		return elements;
	} // parseTranslation

	private static List<TrlElement> createTrlElementsForToken(final Properties ctx, final String token)
	{
		final List<TrlElement> result = new ArrayList<TrlElement>();

		if (Check.isEmpty(token, true))
		{
			return result;
		}

		//
		// Check AD_Message
		final I_AD_Message adMessage = Services.get(IADMessageDAO.class).retrieveByValue(ctx, token);
		if (adMessage != null)
		{
			result.add(createTrlElement(ctx, I_AD_Message.COLUMNNAME_Value, adMessage));
		}

		//
		// Check AD_Element
		{
			String columnName = token;
			final int idx = token.indexOf("/");
			if (idx >= 0)
			{
				columnName = token.substring(0, idx);
			}
			final M_Element adElement = retrieveAD_Element(ctx, columnName);
			if (adElement != null)
			{
				result.add(createTrlElement(ctx, I_AD_Element.COLUMNNAME_ColumnName, adElement));
			}
		}

		return result;
	}

	private static M_Element retrieveAD_Element(final Properties ctx, final String columnName)
	{
		// First try: exact columnName
		M_Element adElement = M_Element.get(ctx, columnName);
		if (adElement != null)
		{
			return adElement;
		}

		// Second try: maybe it was a table name or an object getter/setter property, so lets try adding "_ID" to see what do we get
		// NOTE: this functionality was also added in org.compiere.util.Msg.translate(String, boolean, String)
		if (!columnName.endsWith("_ID"))
		{
			adElement = M_Element.get(ctx, columnName + "_ID");
		}

		return adElement;
	}

	public static void saveTrlElements(final Properties ctx, final List<TrlElement> elements)
	{
		final IDeveloperModeBL developerModeService = Services.get(IDeveloperModeBL.class);
		if (developerModeService != null && developerModeService.isEnabled())
		{
			developerModeService.executeAsSystem(new ContextRunnable()
			{

				@Override
				public void run(final Properties sysCtx)
				{
					saveTrlElements0(sysCtx, elements);
				}
			});
		}
		else
		{
			saveTrlElements0(ctx, elements);
		}
	}

	public static void saveTrlElements0(final Properties ctx, final List<TrlElement> elements)
	{
		if (elements == null || elements.isEmpty())
		{
			return;
		}

		for (final TrlElement e : elements)
		{
			saveTrlElement(ctx, e);
		}
	}

	private static void saveTrlElement(final Properties ctx, final TrlElement e)
	{
		if (!isChanged(e))
		{
			return;
		}

		final String trxName = null;
		final String tableName = e.getTableName();
		final int recordId = e.getRecordId();
		final String idColumnName = tableName + "_ID";

		final String adLanguage = Env.getAD_Language(ctx);
		if (Env.isBaseLanguage(adLanguage, tableName))
		{
			final PO po = new Query(ctx, tableName, idColumnName + "=?", trxName)
					.setParameters(recordId)
					.firstOnly();
			if (po == null)
			{
				throw new IllegalStateException("No PO found for tableName=" + tableName + ", id=" + recordId);
			}

			for (final TrlElementItem item : e.getItems())
			{
				po.set_ValueOfColumn(item.getColumnName(), item.getValue());
			}
			po.saveEx();
		}
		else
		{
			updateRecordTrl(tableName, recordId, adLanguage, e.getItems());
		}

	}

	private static void updateRecordTrl(final String tableName, final int recordId, final String adLanguage, final List<TrlElementItem> list)
	{
		if (list == null || list.isEmpty())
		{
			return;
		}

		final String trxName = null;

		final List<Object> params = new ArrayList<Object>();
		final StringBuffer sqlSet = new StringBuffer();
		for (final TrlElementItem item : list)
		{
			if (sqlSet.length() > 0)
			{
				sqlSet.append(", ");
			}
			sqlSet.append(item.getColumnName()).append("=?");
			params.add(item.getValue());
		}

		final String sql = "UPDATE " + tableName + "_Trl SET " + sqlSet
				+ " WHERE " + tableName + "_ID=? AND AD_Language=?";
		final int no = DB.executeUpdateEx(sql, params.toArray(), trxName);
		if (no != 1)
		{
			TrlElementHelper.logger.warn("Trl update error (sql=" + sql + ")");
		}

		// Do cache reset if we have changes
		if (no > 0)
		{
			CacheMgt.get().resetOnTrxCommit(trxName, tableName, recordId);
		}
	}

	private static boolean isChanged(final TrlElement e)
	{
		for (final TrlElementItem item : e.getItems())
		{
			if (isChanged(item))
			{
				return true;
			}
		}

		return false;
	}

	private static boolean isChanged(final TrlElementItem e)
	{
		final String valueOld = e.getValueOld();
		final String value = e.getValue();

		//
		// Null value or empty string shall be considered the same
		if (Check.isEmpty(valueOld) && Check.isEmpty(value))
		{
			return false;
		}

		return !Objects.equals(valueOld, value);
	}
}
